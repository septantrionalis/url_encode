package org.tdod.demo6.service;

import org.springframework.stereotype.Service;

@Service
public class DencoderServiceImpl implements DencoderService {

    @Override
    public String encode(String url) {
        return "encoded url: " + url;
    }

    @Override
    public String decode(String url) {
        return "decoded url: " + url;
    }


}
