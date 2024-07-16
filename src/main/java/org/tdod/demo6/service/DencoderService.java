package org.tdod.demo6.service;

import org.tdod.demo6.entity.DencodeEntity;

import java.util.List;
import java.util.Optional;

public interface DencoderService {

    DencodeEntity encode(String url);

    DencodeEntity decode(String url);

    List<DencodeEntity> getAll();

    void addKey(String key, String url);

}
