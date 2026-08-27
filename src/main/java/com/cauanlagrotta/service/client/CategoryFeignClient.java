package com.cauanlagrotta.service.client;

import com.cauanlagrotta.service_offering.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient
public interface CategoryFeignClient {

//  @GetMapping("/api/categories/{categoryId}")
//  ResponseEntity<CategoryDTO> getById(@PathVariable Long categoryId);

  @GetMapping("api/categories/saloon-owner/saloon/{saloonId}/category/{categoryId}")
  ResponseEntity<CategoryDTO> getCategoriesBySaloon(@PathVariable Long categoryId,
                                                    @PathVariable Long saloonId);
}
