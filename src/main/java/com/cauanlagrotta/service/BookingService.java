package com.cauanlagrotta.service;

import com.cauanlagrotta.domain.BookingStatus;
import com.cauanlagrotta.dto.BookingRequest;
import com.cauanlagrotta.dto.SaloonDTO;
import com.cauanlagrotta.dto.ServiceDTO;
import com.cauanlagrotta.dto.UserDTO;
import com.cauanlagrotta.model.Booking;
import com.cauanlagrotta.model.SaloonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

  Booking create(BookingRequest booking, UserDTO user, SaloonDTO saloon, Set<ServiceDTO> serviceDTOSet);

  List<Booking> getByCustomerId(Long customerId);

  List<Booking> getBySaloonId(Long saloonId);

  Booking getById(Long bookingId);

  Booking update(Long bookingId, BookingStatus status);

  List<Booking> getByDateAndSaloonId(LocalDate date, Long saloonId);

  SaloonReport getSaloonReport(Long saloonId);
}
