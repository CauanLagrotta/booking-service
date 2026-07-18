package com.cauanlagrotta.mapper;

import com.cauanlagrotta.dto.BookingDTO;
import com.cauanlagrotta.model.Booking;

public class BookingMapper {

  public static BookingDTO toDTO(Booking booking){
    BookingDTO bookingDTO = new BookingDTO();
    bookingDTO.setId(booking.getId());
    bookingDTO.setCustomerId(booking.getCustomerId());
    bookingDTO.setStatus(booking.getStatus());
    bookingDTO.setEndTime(booking.getEndTime());
    bookingDTO.setStartTime(booking.getStartTime());
    bookingDTO.setSaloonId(booking.getSaloonId());
    bookingDTO.setServiceIds(booking.getServiceIds());

    return bookingDTO;
  }
}
