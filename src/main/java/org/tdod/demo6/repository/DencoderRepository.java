package org.tdod.demo6.repository;

public interface DencoderRepository {

    String getShortenedUrlHost();

    boolean isKeyExists(String key);

    void addKey(String key);

}
