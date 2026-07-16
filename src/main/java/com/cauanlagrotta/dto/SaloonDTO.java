package com.cauanlagrotta.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class SaloonDTO {

  private Long id;

  private String name;

  private List<String> images;

  private String address;

  private String phoneNumber;

  private String email;

  private String city;

  private Long ownerId;

  private LocalTime openingTime;

  private LocalTime closingTime;
}
