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
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn("https://short.est/");
        Mockito.when(dencoderRepository.isKeyExists(any())).thenReturn(false);

        String normalUrl = "https://example.com/library/react";
        DencodeEntity dencodeEntity = dencoderService.encode(normalUrl);

        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(dencodeEntity.getShortenedUrl()).isNotEmpty();
    }

    @Test
    void testDecodeValid() {
        String shortenedUrl = "https://short.est/";
        Optional<String> normalUrl = Optional.of("https://example.com/library/react");
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn(shortenedUrl);
        Mockito.when(dencoderRepository.getNormalUrl(any())).thenReturn(normalUrl);

        DencodeEntity dencodeEntity = dencoderService.decode(shortenedUrl);

        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl.get());
        assertThat(dencodeEntity.getShortenedUrl()).isNotEmpty();
    }

    @Test
    void encodeInvalidUrl() {
        boolean thrown = false;
        String normalUrl = "htt://example.com/library/react";
        try {
            DencodeEntity encodeEntity = dencoderService.encode(normalUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_URL)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

    @Test
    void encodeAlreadyShortenedUrl() {
        String shortenedUrl = "https://short.est/";
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn(shortenedUrl);

        boolean thrown = false;
        String normalUrl = shortenedUrl + "library/react";
        try {
            dencoderService.encode(normalUrl);
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
            dencoderService.decode(invalidShortenedUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.INVALID_SHORTENED_URL)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

    @Test
    void decodeShortUrlNotFound() {
        Optional<String> normalUrl = Optional.of("https://example.com/library/react");
        String shortenedUrl = "https://short.est/";
        Mockito.when(dencoderRepository.getShortenedUrlHost()).thenReturn(shortenedUrl);
        Mockito.when(dencoderRepository.getNormalUrl(any())).thenReturn(Optional.empty());

        boolean thrown = false;
        String invalidShortenedUrl = "https://short.est/dZ28x3";
        try {
            dencoderService.decode(invalidShortenedUrl);
        } catch (DencodeException e) {
            assertThat(e.getMessage().equals(Messages.SHORTENED_URL_NOT_FOUND)).isTrue();
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }
}
