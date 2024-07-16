package org.tdod.demo6.service;

import org.tdod.demo6.entity.DencodeEntity;

import java.util.Optional;

public interface DencoderService {

    DencodeEntity encode(String url);

    DencodeEntity decode(String url);

    void addKey(String key, String url);
}
