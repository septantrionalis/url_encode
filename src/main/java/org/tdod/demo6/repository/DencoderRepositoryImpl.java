package org.tdod.demo6.repository;

import org.springframework.stereotype.Service;

@Service
public class DencoderRepositoryImpl implements DencoderRepository {

    @Override
    public String getShortenedUrlHost() {
        return "https://short.est/";
    }

}
