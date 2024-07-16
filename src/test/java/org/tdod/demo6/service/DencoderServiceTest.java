package org.tdod.demo6.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.repository.DencoderRepository;

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
}
