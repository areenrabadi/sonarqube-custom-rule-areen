package com.test.emi.attachment.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*@FeignClient(name = "ftp-client", url = "${ftp-service.url}")
public interface FtpFeignClient {

    @PostMapping("/upload")
    String uploadFile(@RequestParam("file") String filePath, @RequestParam("remoteDir") String remoteDir);

    @GetMapping("/download")
    void downloadFile(@RequestParam("remoteFilePath") String remoteFilePath);
}
*/