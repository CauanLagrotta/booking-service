package com.cauanlagrotta.service.client;

import com.cauanlagrotta.domain.PaymentMethod;
import com.cauanlagrotta.dto.BookingDTO;
import com.cauanlagrotta.dto.PaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentFeignClient {

  @PostMapping("/api/payments/create")
  ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod);
}
