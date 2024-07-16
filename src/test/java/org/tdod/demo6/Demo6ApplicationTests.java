package org.tdod.demo6;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.exception.DencodeException;
import org.tdod.demo6.repository.DencoderRepository;
import org.tdod.demo6.util.Messages;

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

    @Test
    void encodeInvalidUrl() {
        String normalUrl = "htt://example.com/library/react";
        try {
            DencodeEntity encodeEntity = demo6Application.encode(normalUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_URL)).isTrue();
        }
    }
}