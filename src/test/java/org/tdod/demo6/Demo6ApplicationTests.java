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
    void encodeValid() {
        // Attempt to shorten a URL.
        String normalUrl = "https://example.com/library/react";
        DencodeEntity dencodeEntity = demo6Application.encode(normalUrl);

        // Verify
        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);

        String key = decoderRepository.getKey(normalUrl).get();
        assertThat(dencodeEntity.getShortenedUrl().endsWith(key)).isTrue();
    }

    @Test
    void encodeDupe() {
        // Attempt to shorten a URL.
        String normalUrl = "https://example.com/library/react";
        DencodeEntity dencodeEntity = demo6Application.encode(normalUrl);

        // Verify
        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        String key = decoderRepository.getKey(normalUrl).get();
        assertThat(dencodeEntity.getShortenedUrl().endsWith(key)).isTrue();

        // Attempt to encode a second time.
        dencodeEntity = demo6Application.encode(normalUrl);

        // Verify
        assertThat(dencodeEntity.getShortenedUrl().endsWith(key)).isTrue();
    }

    @Test
    void decodeValid() {
        // Add to the DB.
        String normalUrl = "https://example.com/library/react";
        DencodeEntity encodeEntity = demo6Application.encode(normalUrl);

        // Decode URL.
        String shortenedUrl = encodeEntity.getShortenedUrl();
        DencodeEntity decodeEntity = demo6Application.decode(shortenedUrl);

        // Verify
        assertThat(decodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(decodeEntity.getShortenedUrl()).isEqualTo(encodeEntity.getShortenedUrl());
    }

    @Test
    void encodeInvalidUrl() {
        boolean thrown = false;
        String normalUrl = "htt://example.com/library/react";
        try {
            DencodeEntity encodeEntity = demo6Application.encode(normalUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_URL)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

    @Test
    void encodeAlreadyShortenedUrl() {
        boolean thrown = false;
        String shortenedUrl = decoderRepository.getShortenedUrlHost();
        String normalUrl = shortenedUrl + "library/react";
        try {
            demo6Application.encode(normalUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.URL_ALREADY_SHORTENED)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

    @Test
    void decodeInvalidUrl() {
        boolean thrown = false;
        String invalidShortenedUrl = "http://tdod.org/";
        try {
            demo6Application.decode(invalidShortenedUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_SHORTENED_URL)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

    @Test
    void decodeShortUrlNotFound() {
        boolean thrown = false;
        String invalidShortenedUrl = "https://short.est/dZ28x3";
        try {
            demo6Application.decode(invalidShortenedUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.SHORTENED_URL_NOT_FOUND)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }
}