package com.cauanlagrotta.model;

import com.cauanlagrotta.domain.PaymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity 
@Data 
public class PaymentOrder {

  @Id 
  @GeneratedValue (strategy = GenerationType.AUTO)
  private Long id;

  @Column (nullable = false)
  private Long amount;

  @Column(nullable = false)
  private PaymentMethod paymentMethod;

  private String paymentLinkId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long bookingId;

  @Column(nullable = false)
  private Long saloonId;
}
