package org.tdod.demo6.repository;

import org.springframework.stereotype.Service;
import org.tdod.demo6.entity.DencodeEntity;

import java.util.*;

@Service
public class DencoderRepositoryImpl implements DencoderRepository {

    private Map<String, String> shortenedUrlKeyStore = new HashMap();

    /**
     * Gets the shortened host name.
     * @return the shortened host name.
     */
    @Override
    public String getShortenedUrlHost() {
        return "https://short.est/";
    }

    /**
     * Checks if a key exists in the DB.
     * @param key the key in question.
     * @return true if the key exists, false otherwise.
     */
    @Override
    public boolean isKeyExists(String key) {
        if (shortenedUrlKeyStore.containsKey(key)) {
            return true;
        }

        return false;
    }

    /**
     * Gets a normal URL that is stored in the DB.
     * @param key the key to get.
     * @return a normal URL. Optional will be empty if it doesn't exist.
     */
    @Override
    public Optional<String> getNormalUrl(String key) {
        return Optional.ofNullable(shortenedUrlKeyStore.get(key));
    }

    /**
     * Adds a normalUrl and key to the DB.
     * @param key the key to add.
     * @param normalUrl the normal url to add.
     */
    @Override
    public void addKey(String key, String normalUrl) {
        shortenedUrlKeyStore.put(key, normalUrl);
    }

    /**
     * Returns the key of a normal url. Optional will be empty if it doesn't exist.
     * @param normalUrl the normal url to query.
     * @return the key of a normal url. Optional will be empty if it doesn't exist.
     */
    @Override
    public Optional<String> getKey(String normalUrl) {

        Optional<String> test = shortenedUrlKeyStore.entrySet()
                .stream()
                .filter(e -> e.getValue().equalsIgnoreCase(normalUrl))
                .findFirst()
                .map(Map.Entry::getKey);

        return test;
    }

    /**
     * Used for debug purposes!
     * Returns all data in the DB.
     * @return all data in the DB.
     */
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
