package org.tdod.demo6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.service.DencoderService;
import org.tdod.demo6.service.DencoderServiceImpl;

import java.util.List;


@SpringBootApplication
@RestController
public class Demo6Application {

    private Logger logger = LoggerFactory.getLogger(DencoderServiceImpl.class);

    @Autowired
    DencoderService dencoderService;

    public static void main(String[] args) {
        SpringApplication.run(Demo6Application.class, args);
    }

    /**
     * Encodes a URL to a shortened URL
     * @param normalUrl the regular url to shorten.
     * @return a shortened URL
     */
    @GetMapping("/encode")
    public DencodeEntity encode(@RequestParam(value = "url") String normalUrl) {
        logger.debug("encode(): {}", normalUrl);
        return dencoderService.encode(normalUrl);
    }

    /**
     * Decodes a shortened URL to its original URL.
     * @param shortenedUrl The shortened URL to convert to a regular URL.
     * @return The shortened URL.
     */
    @GetMapping("/decode")
    public DencodeEntity decode(@RequestParam(value = "url") String shortenedUrl) {
        logger.debug("decode(): {}", shortenedUrl);
        return dencoderService.decode(shortenedUrl);
    }

    /*
     * Added debug methods to help me test the app below.
     */

    /**
     * Used for debug purposes!
     * Adds a key for the specified url to the DB
     * @param key the key to add.
     * @param normalUrl the url to add.
     */
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam(value = "key") String key,
                                      @RequestParam(value = "normal-url") String normalUrl) {
        logger.debug("add(): key={}, normal-url={}", key, normalUrl);
        dencoderService.addKey(key, normalUrl);

        return ResponseEntity.ok().build();
    }

    /**
     * Used for debug purposes!
     * Returns all data in the DB.
     * @return all data in the DB.
     */
    @GetMapping("/get")
    public List<DencodeEntity> getAll() {
        logger.debug("getAll()");
        return dencoderService.getAll();
    }
}
