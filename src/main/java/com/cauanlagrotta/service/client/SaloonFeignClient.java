package com.cauanlagrotta.service.client;

import com.cauanlagrotta.dto.SaloonDTO;
import com.cauanlagrotta.service_offering.dto.SaloonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("SALOON-SERVICE")
public interface SaloonFeignClient {

  @GetMapping("/api/saloons/owner")
  ResponseEntity<SaloonDTO> getByOwnerId(@RequestHeader("Authorization") String jwt);

  @GetMapping("/api/saloons/{saloonId}")
  ResponseEntity<SaloonDTO> getById(@PathVariable("saloonId") Long saloonId);
}
