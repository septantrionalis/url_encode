package org.tdod.demo6.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tdod.demo6.repository.DencoderRepository;

import java.security.SecureRandom;

@Service
public class DencoderServiceImpl implements DencoderService {

    Logger logger = LoggerFactory.getLogger(DencoderServiceImpl.class);

    private static final int SIZE = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Autowired
    DencoderRepository dencoderRepository;

    @Override
    public String encode(String url) {
        String randomKey = generateRandomString();

        while (dencoderRepository.isKeyExists(randomKey)) {
            logger.info("Found key {} in DB. Regenerating...", randomKey);
            randomKey = generateRandomString();
        }

        dencoderRepository.addKey(randomKey);

        return randomKey;
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
