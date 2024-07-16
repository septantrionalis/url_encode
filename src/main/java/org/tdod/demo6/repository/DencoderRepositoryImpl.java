package org.tdod.demo6.repository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DencoderRepositoryImpl implements DencoderRepository {

    private Map<String, String> shortenedUrlKeyStore = new HashMap();

    @Override
    public String getShortenedUrlHost() {
        return "https://short.est/";
    }

    @Override
    public boolean isKeyExists(String key) {
        if (shortenedUrlKeyStore.containsKey(key)) {
            return true;
        }

        return false;
    }

    @Override
    public void addKey(String key, String normalUrl) {
        shortenedUrlKeyStore.put(key, normalUrl);
    }

    @Override
    public Optional<String> getNormalUrl(String key) {

        /* Optional<String> normalUrl = shortenedUrlKeyStore.entrySet()
                .stream()
                .filter(e -> e.getValue().equalsIgnoreCase(key))
                .findFirst()
                .map(Map.Entry::getValue);*/

        Optional<String> normalUrl = Optional.ofNullable(shortenedUrlKeyStore.get(key));

        return normalUrl;
    }
}
