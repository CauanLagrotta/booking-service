package com.cauanlagrotta.controller;

import com.cauanlagrotta.domain.BookingStatus;
import com.cauanlagrotta.dto.*;
import com.cauanlagrotta.mapper.BookingMapper;
import com.cauanlagrotta.model.Booking;
import com.cauanlagrotta.model.SaloonReport;
import com.cauanlagrotta.service.BookingService;
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

  @PostMapping
  public ResponseEntity<Booking> create(@RequestParam Long saloonId, @RequestBody BookingRequest bookingRequest){
    UserDTO user = new UserDTO();
    user.setId(1L);

    SaloonDTO saloon = new SaloonDTO();
    saloon.setId(saloonId);
    saloon.setOpeningTime(LocalTime.of(8, 0));
    saloon.setClosingTime(LocalTime.of(18, 0));

    log.info("Saloon id = {}, opening time = {}, closing time = {}", saloon.getId(), saloon.getOpeningTime(), saloon.getClosingTime());

    Set<ServiceDTO> serviceDTOSet = new HashSet<>();

    ServiceDTO service = new ServiceDTO();
    service.setId(1L);
    service.setPrice(399);
    service.setDuration(45);
    service.setName("Haircut for men");

    serviceDTOSet.add(service);

    Booking booking = bookingService.create(bookingRequest, user, saloon, serviceDTOSet);

    return ResponseEntity.ok(booking);
  }

  @GetMapping("/customer")
  public ResponseEntity<Set<BookingDTO>> getByCustomerId(){

    List<Booking> bookings = bookingService.getByCustomerId(1L);
    return ResponseEntity.ok(getBookingDTOs(bookings));
  }

  @GetMapping("/saloon")
  public ResponseEntity<Set<BookingDTO>> getBySaloonId(){

    List<Booking> bookings = bookingService.getBySaloonId(1L);
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
  public ResponseEntity<SaloonReport> getSaloonReport(){

    SaloonReport report = bookingService.getSaloonReport(1L);
    return ResponseEntity.ok(report);
  }

}

