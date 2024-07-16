package org.tdod.demo6.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class DencoderServiceImpl implements DencoderService {

    private static final int SIZE = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public String encode(String url) {
        return generateRandomString();
    }

    @Override
    public String decode(String url) {
        return "decoded url: " + url;
    }

    private String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(SIZE);

        for (int i = 0; i < SIZE; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }


}
