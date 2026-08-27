package com.cauanlagrotta.service.client;

import com.cauanlagrotta.dto.UserDTO;
import com.cauanlagrotta.service_offering.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USER")
public interface UserFeignClient {

  @GetMapping("/api/users/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id);

  @GetMapping("/api/users/profile")
  public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt);
}
