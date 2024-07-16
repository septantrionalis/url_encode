package org.tdod.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.service.DencoderService;

import java.util.Optional;


@SpringBootApplication
@RestController
public class Demo6Application {

    @Autowired
    DencoderService dencoderService;

    public static void main(String[] args) {
        SpringApplication.run(Demo6Application.class, args);
    }

    @GetMapping("/encode")
    public DencodeEntity encode(@RequestParam(value = "url") String normalUrl) {
        return dencoderService.encode(normalUrl);
    }

    @GetMapping("/decode")
    public DencodeEntity decode(@RequestParam(value = "url") String shortenedUrl) {
        return dencoderService.decode(shortenedUrl);
    }

    /**
     * Added debug methods to help me test the app below.
     */

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam(value = "key") String key,
                                      @RequestParam(value = "normal-url") String normalUrl) {
        dencoderService.addKey(key, normalUrl);

        return ResponseEntity.ok().build();
    }


}
