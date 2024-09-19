package com.dgcash.emi.attachment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.dgcash"})
@EnableFeignClients(basePackages = {"com.dgcash"})
@EnableDiscoveryClient
@OpenAPIDefinition( servers = {
        @Server(url = "http://localhost:9097", description = "Attachment Service"),
        @Server(url = "http://localhost:8081/api/v1", description = "Attachment through gateway Service")},

        info = @Info(title = "Attachment Service", version = "1.0", description = "Attachment Service API"))
public class AttachmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentServiceApplication.class, args);
    }
}
