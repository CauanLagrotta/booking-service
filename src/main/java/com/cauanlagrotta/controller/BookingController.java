package com.cauanlagrotta.controller;

import com.cauanlagrotta.domain.BookingStatus;
import com.cauanlagrotta.domain.PaymentMethod;
import com.cauanlagrotta.dto.*;
import com.cauanlagrotta.mapper.BookingMapper;
import com.cauanlagrotta.model.Booking;
import com.cauanlagrotta.model.SaloonReport;
import com.cauanlagrotta.service.BookingService;
import com.cauanlagrotta.service.client.PaymentFeignClient;
import com.cauanlagrotta.service.client.SaloonFeignClient;
import com.cauanlagrotta.service.client.ServiceOfferingFeignClient;
import com.cauanlagrotta.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

  private static final Logger log = LoggerFactory.getLogger(BookingController.class);

  private final BookingService bookingService;
  private final SaloonFeignClient saloonFeignClient;
  private final UserFeignClient userFeignClient;
  private final ServiceOfferingFeignClient serviceOfferingFeignClient;
  private final PaymentFeignClient paymentFeignClient;

  @PostMapping
  public ResponseEntity<PaymentLinkResponse> create(@RequestParam Long saloonId,
                                        @RequestParam PaymentMethod paymentMethod,
                                        @RequestBody BookingRequest bookingRequest,
                                        @RequestHeader("Authorization") String jwt){

    UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

    SaloonDTO saloon = saloonFeignClient.getById(saloonId).getBody();

    log.info("Saloon id = {}, opening time = {}, closing time = {}", saloon.getId(), saloon.getOpeningTime(), saloon.getClosingTime());

    Set<ServiceDTO> serviceDTOSet = serviceOfferingFeignClient.getByIds(bookingRequest.getServiceIds()).getBody();

    Booking booking = bookingService.create(bookingRequest, user, saloon, serviceDTOSet);

    BookingDTO bookingDTO = BookingMapper.toDTO(booking);

    PaymentLinkResponse res = paymentFeignClient.createPaymentLink(bookingDTO, paymentMethod, jwt).getBody();

    return ResponseEntity.ok(res);
  }

  @GetMapping("/customer")
  public ResponseEntity<Set<BookingDTO>> getByCustomerId(@RequestHeader("Authorization") String jwt){

    UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

    if(user == null || user.getId() == null){
      throw new RuntimeException("User cannot be null");
    }

    List<Booking> bookings = bookingService.getByCustomerId(user.getId());
    return ResponseEntity.ok(getBookingDTOs(bookings));
  }

  @GetMapping("/saloon")
  public ResponseEntity<Set<BookingDTO>> getBySaloonId(@RequestHeader("Authorization") String jwt){

    SaloonDTO saloonDTO = saloonFeignClient.getByOwnerId(jwt).getBody();

    if(saloonDTO == null || saloonDTO.getId() == null){
      throw new RuntimeException("Saloon id cannot be null");
    }

    List<Booking> bookings = bookingService.getBySaloonId(saloonDTO.getId());
    return ResponseEntity.ok(getBookingDTOs(bookings));
  }

  @GetMapping("/{bookingId}")
  public ResponseEntity<BookingDTO> getById(@PathVariable Long bookingId){

    Booking booking = bookingService.getById(bookingId);
    return ResponseEntity.ok(BookingMapper.toDTO(booking));
  }

  @PutMapping("/{bookingId}/status")
  public ResponseEntity<BookingDTO> updateStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status){

    Booking booking = bookingService.update(bookingId, status);
    return ResponseEntity.ok(BookingMapper.toDTO(booking));
  }

  private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
    return bookings.stream().map(BookingMapper::toDTO).collect(Collectors.toSet());
  }

  @GetMapping("/slots/saloon/{saloonId}/date/{date}")
  public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@RequestParam(required = false) LocalDate date,
                                                            @PathVariable Long saloonId){

    List<Booking> bookings = bookingService.getByDateAndSaloonId(date, saloonId);

    List<BookingSlotDTO> slotsDTOs = bookings.stream().map(booking -> {
      BookingSlotDTO slotDTO = new BookingSlotDTO();

      slotDTO.setStartTime(booking.getStartTime());
      slotDTO.setEndTime(booking.getEndTime());
      return slotDTO;
    }).toList();

    return ResponseEntity.ok(slotsDTOs);
  }

  @GetMapping("/report")
  public ResponseEntity<SaloonReport> getSaloonReport(@RequestHeader("Authorization") String jwt){
    SaloonDTO saloonDTO = saloonFeignClient.getByOwnerId(jwt).getBody();

    if (saloonDTO == null || saloonDTO.getId() == null){
      throw new RuntimeException("saloonDTO or id cannot be null");
    }

    SaloonReport report = bookingService.getSaloonReport(saloonDTO.getId());
    return ResponseEntity.ok(report);
  }

}

