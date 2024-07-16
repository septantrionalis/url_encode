package org.tdod.demo6;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.repository.DencoderRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Demo6ApplicationTests {

    @Autowired
    Demo6Application demo6Application;

    @Autowired
    DencoderRepository decoderRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void encodeValid() {
        String normalUrl = "https://example.com/library/react";
        DencodeEntity dencodeEntity = demo6Application.encode(normalUrl);

        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);

        String key = decoderRepository.getKey(normalUrl).get();
        assertThat(dencodeEntity.getShortenedUrl().endsWith(key)).isTrue();
    }

    @Test
    void decodeValid() {
        // Add to the DB.
        String normalUrl = "https://example.com/library/react";
        DencodeEntity encodeEntity = demo6Application.encode(normalUrl);

        // Dencode URL.
        String shortenedUrl = encodeEntity.getShortenedUrl();
        DencodeEntity decodeEntity = demo6Application.decode(shortenedUrl);

        assertThat(decodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(decodeEntity.getShortenedUrl()).isEqualTo(encodeEntity.getShortenedUrl());

    }
}