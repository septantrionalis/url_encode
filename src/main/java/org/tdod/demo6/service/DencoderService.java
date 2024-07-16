package org.tdod.demo6.service;

import java.util.Optional;

public interface DencoderService {

    String encode(String url);

    Optional<String> decode(String url);

    void addKey(String key, String url);
}
