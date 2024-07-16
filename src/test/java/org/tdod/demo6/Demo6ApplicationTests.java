package org.tdod.demo6;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.repository.DencoderRepository;
import org.tdod.demo6.service.DencoderService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class Demo6ApplicationTests {

    @Autowired
    Demo6Application demo6Application;

    @MockBean
    DencoderService dencoderService;

    @Test
    void encodeValid() {
        // Setup
        String shortenedUrl = "https://short.est/";
        String normalUrl = "https://example.com/library/react";
        DencodeEntity resultEntity = new DencodeEntity(shortenedUrl, normalUrl);
        Mockito.when(dencoderService.encode(any())).thenReturn(resultEntity);

        // Execute
        DencodeEntity dencodeEntity = demo6Application.encode(normalUrl);

        // Verify
        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(dencodeEntity.getShortenedUrl().endsWith(shortenedUrl)).isTrue();
    }

    @Test
    void encodeDupe() {
        // Setup
        String shortenedUrl = "https://short.est/";
        String normalUrl = "https://example.com/library/react";
        DencodeEntity resultEntity = new DencodeEntity(shortenedUrl, normalUrl);
        Mockito.when(dencoderService.encode(any())).thenReturn(resultEntity);

        // Execute
        DencodeEntity dencodeEntity = demo6Application.encode(normalUrl);

        // Verify
        assertThat(dencodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(dencodeEntity.getShortenedUrl().endsWith(shortenedUrl)).isTrue();

        // Attempt to encode a second time.
        dencodeEntity = demo6Application.encode(normalUrl);

        // Verify
        assertThat(dencodeEntity.getShortenedUrl().endsWith(shortenedUrl)).isTrue();
    }

    @Test
    void decodeValid() {
        // Setup
        String shortenedUrl = "https://short.est/";
        String normalUrl = "https://example.com/library/react";
        DencodeEntity resultEntity = new DencodeEntity(shortenedUrl, normalUrl);
        Mockito.when(dencoderService.decode(any())).thenReturn(resultEntity);

        // Decode URL.
        DencodeEntity decodeEntity = demo6Application.decode(shortenedUrl);

        // Verify
        assertThat(decodeEntity.getNormalUrl()).isEqualTo(normalUrl);
        assertThat(decodeEntity.getShortenedUrl()).isEqualTo(shortenedUrl);
    }


}