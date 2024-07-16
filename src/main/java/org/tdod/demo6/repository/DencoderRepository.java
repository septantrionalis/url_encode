package org.tdod.demo6.repository;

import java.util.Optional;

public interface DencoderRepository {

    String getShortenedUrlHost();

    boolean isKeyExists(String key);

    void addKey(String key, String normalUrl);

    Optional<String> getNormalUrl(String key);

}
