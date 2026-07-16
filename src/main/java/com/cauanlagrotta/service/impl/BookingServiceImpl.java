package com.cauanlagrotta.service.impl;

import com.cauanlagrotta.domain.BookingStatus;
import com.cauanlagrotta.dto.BookingRequest;
import com.cauanlagrotta.dto.SaloonDTO;
import com.cauanlagrotta.dto.ServiceDTO;
import com.cauanlagrotta.dto.UserDTO;
import com.cauanlagrotta.model.Booking;
import com.cauanlagrotta.model.SaloonReport;
import com.cauanlagrotta.repository.BookingRepository;
import com.cauanlagrotta.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;

  @Override
  public Booking create(BookingRequest booking, UserDTO user, SaloonDTO saloon, Set<ServiceDTO> serviceDTOSet) {

    int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

    LocalDateTime bookingStartTime = booking.getStartTime();
    LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

    Boolean isSlotAvailable = isTimeSlotAvailable(saloon, bookingStartTime, bookingEndTime);

    int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

    Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

    Booking newBooking = new Booking();
    newBooking.setCustomerId(user.getId());
    newBooking.setSaloonId(saloon.getId());
    newBooking.setStartTime(bookingStartTime);
    newBooking.setEndTime(bookingEndTime);
    newBooking.setServiceIds(idList);
    newBooking.setTotalPrice(totalPrice);
    newBooking.setStatus(BookingStatus.PENDING);

    return bookingRepository.save(newBooking);
  }

  public Boolean isTimeSlotAvailable(SaloonDTO saloonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime){

    List<Booking> existingBookings = getBySaloonId(saloonDTO.getId());

    LocalDateTime saloonOpenTime = saloonDTO.getOpeningTime().atDate(bookingStartTime.toLocalDate());
    LocalDateTime saloonCloseTime = saloonDTO.getClosingTime().atDate(bookingEndTime.toLocalDate());

    if(bookingStartTime.isBefore(saloonOpenTime) || bookingEndTime.isAfter(saloonCloseTime)){
      throw new RuntimeException("Booking time must be within saloon's working hours.");
    }

    for(Booking existingBooking : existingBookings){
      LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
      LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

      if(bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){
        throw new RuntimeException("Slot available. Choose another one.");
      }

      if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)){
        throw new RuntimeException("Slot available. Choose another one.");
      }
    }

    return true;
  }

  @Override
  public List<Booking> getByCustomerId(Long customerId) {
    return bookingRepository.findByCustomerId(customerId);
  }

  @Override
  public List<Booking> getBySaloonId(Long saloonId) {
    return bookingRepository.findBySaloonId(saloonId);
  }

  @Override
  public Booking getById(Long bookingId) {
    return null;
  }

  @Override
  public Booking update(Booking booking, BookingStatus status) {
    return null;
  }

  @Override
  public List<Booking> getByDateAndSaloonId(LocalDate date, Long saloonId) {
    return List.of();
  }

  @Override
  public SaloonReport getSaloonReport(Long saloonId) {
    return null;
  }
}
