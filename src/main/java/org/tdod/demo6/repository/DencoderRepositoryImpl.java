package org.tdod.demo6.repository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DencoderRepositoryImpl implements DencoderRepository {

    private Set<String> shortenedUrlKeyStore = new HashSet();

    @Override
    public String getShortenedUrlHost() {
        return "https://short.est/";
    }

    @Override
    public boolean isKeyExists(String key) {
        if (shortenedUrlKeyStore.contains(key)) {
            return true;
        }

        return false;
    }


}
