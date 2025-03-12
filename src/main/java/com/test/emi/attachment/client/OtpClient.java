package com.test.emi.attachment.client;

import com.dgcash.emi.attachment.data.dto.request.CreateOtpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "otp-service")
public interface OtpClient {
    @GetMapping("/api/v1/internal/otps/entities/{entityId}")
    Boolean verifyOtp(@RequestParam String type,
                      @PathVariable String entityId,
                      @RequestParam String otp);
    @PostMapping("/api/v1/internal/otps/no-notification")
    String generateOtp(@RequestBody CreateOtpRequest createOtpRequest);
}