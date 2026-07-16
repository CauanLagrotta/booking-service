package com.cauanlagrotta.model;

import lombok.Data;

@Data
public class SaloonModel {

  private Long saloonId;

  private String saloonName;

  private Double totalEarnings;

  private Integer totalBookings;

  private Integer cancelledBookings;

  private Double totalRefund;
}
