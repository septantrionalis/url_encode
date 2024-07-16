package org.tdod.demo6.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.exception.DencodeException;
import org.tdod.demo6.repository.DencoderRepository;
import org.tdod.demo6.util.Messages;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class DencoderServiceTest {

    @Autowired
    DencoderService dencoderService;

    @MockBean
    DencoderRepository dencoderRepository;

    @Test
    void testEncodeValid() {
        // Setup
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn("https://short.est/");
        Mockito.when(dencoderRepository.isKeyExists(any())).thenReturn(false);
        String normalUrl = "https://example.com/library/react";

        // Execute
        DencodeEntity dencodeEntity = dencoderService.encode(normalUrl);

        // Assert
        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(dencodeEntity.getShortenedUrl()).isNotEmpty();
    }

    @Test
    void testDecodeValid() {
        // Setup
        String shortenedUrl = "https://short.est/";
        Optional<String> normalUrl = Optional.of("https://example.com/library/react");
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn(shortenedUrl);
        Mockito.when(dencoderRepository.getNormalUrl(any())).thenReturn(normalUrl);

        // Execute
        DencodeEntity dencodeEntity = dencoderService.decode(shortenedUrl);

        // Assert
        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl.get());
        assertThat(dencodeEntity.getShortenedUrl()).isNotEmpty();
    }

    @Test
    void encodeInvalidUrl() {
        // Setup
        boolean thrown = false;
        String normalUrl = "htt://example.com/library/react";

        // Execute
        try {
            DencodeEntity encodeEntity = dencoderService.encode(normalUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_URL)).isTrue();
            thrown = true;
        }

        // Assert
        assertThat(thrown).isTrue();
    }

    @Test
    void encodeAlreadyShortenedUrl() {
        // Setup
        String shortenedUrl = "https://short.est/";
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn(shortenedUrl);
        boolean thrown = false;
        String normalUrl = shortenedUrl + "library/react";

        // Execute
        try {
            dencoderService.encode(normalUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.URL_ALREADY_SHORTENED)).isTrue();
            thrown = true;
        }

        // Assert
        assertThat(thrown).isTrue();
    }

    @Test
    void decodeInvalidUrl() {
        // Setup
        boolean thrown = false;
        String invalidShortenedUrl = "http://tdod.org/";

        // Execute
        try {
            dencoderService.decode(invalidShortenedUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_SHORTENED_URL)).isTrue();
            thrown = true;
        }

        // Assert
        assertThat(thrown).isTrue();
    }

    @Test
    void decodeShortUrlNotFound() {
        // Setup
        Optional<String> normalUrl = Optional.of("https://example.com/library/react");
        String shortenedUrl = "https://short.est/";
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn(shortenedUrl);
        Mockito.when(dencoderRepository.getNormalUrl(any())).thenReturn(Optional.empty());
        boolean thrown = false;
        String invalidShortenedUrl = "https://short.est/dZ28x3";

        // Execute
        try {
            dencoderService.decode(invalidShortenedUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.SHORTENED_URL_NOT_FOUND)).isTrue();
            thrown = true;
        }

        // Assert
        assertThat(thrown).isTrue();
    }
}
