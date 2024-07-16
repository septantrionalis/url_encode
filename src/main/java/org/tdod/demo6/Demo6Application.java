package org.tdod.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tdod.demo6.service.DencoderService;


@SpringBootApplication
@RestController
public class Demo6Application {

    @Autowired
    DencoderService dencoderService;

    public static void main(String[] args) {
        SpringApplication.run(Demo6Application.class, args);
    }

    @GetMapping("/encode")
    public String encode(@RequestParam(value = "url") String normalUrl) {
        return dencoderService.encode(normalUrl);
    }

    @GetMapping("/decode")
    public String decode(@RequestParam(value = "url") String shortenedUrl) {
        return dencoderService.decode(shortenedUrl);
    }

}
