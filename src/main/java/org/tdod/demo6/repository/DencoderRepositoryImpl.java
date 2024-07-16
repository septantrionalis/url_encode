package org.tdod.demo6.repository;

import org.springframework.stereotype.Service;
import org.tdod.demo6.entity.DencodeEntity;

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
    public Optional<String> getNormalUrl(String key) {
        return Optional.ofNullable(shortenedUrlKeyStore.get(key));
    }

    @Override
    public void addKey(String key, String normalUrl) {
        shortenedUrlKeyStore.put(key, normalUrl);
    }

    @Override
    public Optional<String> getKey(String normalUrl) {

        Optional<String> test = shortenedUrlKeyStore.entrySet()
                .stream()
                .filter(e -> e.getValue().equalsIgnoreCase(normalUrl))
                .findFirst()
                .map(Map.Entry::getKey);

        return test;
    }

    public List<DencodeEntity> getAll() {
        List<DencodeEntity> list = new ArrayList<>();

        Iterator<Map.Entry<String, String>> itr = shortenedUrlKeyStore.entrySet().iterator();

        while(itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();

            String shortenedUrl = getShortenedUrlHost() + entry.getKey();
            String normalUrl = entry.getValue();
            DencodeEntity entity = new DencodeEntity(shortenedUrl, normalUrl);
            list.add(entity);
        }

        return list;
    }
}
