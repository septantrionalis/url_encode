package org.tdod.demo6.repository;

import org.tdod.demo6.entity.DencodeEntity;

import java.util.List;
import java.util.Optional;

public interface DencoderRepository {

    String getShortenedUrlHost();

    boolean isKeyExists(String key);

    void addKey(String key, String normalUrl);

    Optional<String> getNormalUrl(String key);

    List<DencodeEntity> getAll();
}
