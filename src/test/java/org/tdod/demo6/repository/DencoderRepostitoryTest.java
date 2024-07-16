package org.tdod.demo6.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DencoderRepostitoryTest {

    @Autowired
    DencoderRepository dencoderRepository;

    @Test
    void testGetShortenedHost() {
        String shortenedUrlHost = dencoderRepository.getShortenedUrlHost();

        assertThat(shortenedUrlHost).isEqualTo("https://short.est/");
    }

    @Test
    void testIsKeyExists() {
        String key = "key";
        String normalUrl = "https://example.com/library/react ";
        dencoderRepository.addKey(key, normalUrl);

        boolean exists = dencoderRepository.isKeyExists(key);

        assertThat(exists).isTrue();
    }

    @Test
    void testIsKeyDoesntExist() {
        String key = "key";
        String normalUrl = "https://example.com/library/react ";
        dencoderRepository.addKey(key, normalUrl);

        boolean exists = dencoderRepository.isKeyExists("key1");

        assertThat(exists).isFalse();
    }

    @Test
    void testGetValidKey() {
        String key = "key1";
        String normalUrl = "https://example.com/library/react ";
        dencoderRepository.addKey(key, normalUrl);
        dencoderRepository.addKey("key2", "normalUrl2");
        dencoderRepository.addKey("key3", "normalUrl3");

        Optional<String> result = dencoderRepository.getKey(normalUrl);

        assertThat(result.get()).isEqualTo(key);

    }
}
