package org.tdod.demo6.repository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

}
