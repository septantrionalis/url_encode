package org.tdod.demo6.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.exception.DecodeException;
import org.tdod.demo6.repository.DencoderRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class DencoderServiceImpl implements DencoderService {

    Logger logger = LoggerFactory.getLogger(DencoderServiceImpl.class);

    private static final int SIZE = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Autowired
    DencoderRepository dencoderRepository;

    @Override
    public DencodeEntity encode(String url) {
        if (!isValidURL(url)) {
            throw new DecodeException("Invalid URL");
        }

        String randomKey = generateRandomString();

        while (dencoderRepository.isKeyExists(randomKey)) {
            logger.info("Found key {} in DB. Regenerating...", randomKey);
            randomKey = generateRandomString();
        }

        String shortenedUrl = dencoderRepository.getShortenedUrlHost() + randomKey;
        dencoderRepository.addKey(randomKey, url);

        return new DencodeEntity(shortenedUrl, url);
    }

    @Override
    public DencodeEntity decode(String url) {
        String key = extractUrlKey(url);
        String host = extractUrlHost(url);

        if (!host.equalsIgnoreCase(dencoderRepository.getShortenedUrlHost())) {
            throw new DecodeException("This doesn't appear to be a valid shortened URL.");
        }

        Optional<String> normalUrl = dencoderRepository.getNormalUrl(key);

        if (normalUrl.isEmpty()) {
            throw new DecodeException("Shortened version of URL not found.");
        }

        return new DencodeEntity(url, normalUrl.get());
    }

    @Override
    public List<DencodeEntity> getAll() {
        return dencoderRepository.getAll();
    }

    @Override
    public void addKey(String key, String url) {
        dencoderRepository.addKey(key, url);
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

    private String extractUrlKey(String urlString) {
        if (urlString == null || urlString.isEmpty()) {
            throw new DecodeException("URL String is empty");
        }

        int lastIndex = urlString.lastIndexOf('/');
        if (lastIndex == -1) {
            throw new DecodeException("The URL doesn't appear to be valid");
        }

        return urlString.substring(lastIndex + 1);
    }

    private String extractUrlHost(String urlString) {
        if (urlString == null || urlString.isEmpty()) {
            throw new DecodeException("URL String is empty");
        }

        int lastIndex = urlString.lastIndexOf('/');
        if (lastIndex == -1) {
            throw new DecodeException("The URL doesn't appear to be valid");
        }

        return urlString.substring(0, lastIndex + 1);
    }

    private boolean isValidURL(String urlString) {
        if (urlString == null || urlString.isEmpty()) {
            return false;
        }

        try {
            new URL(urlString);
            // If no exception is thrown, the URL is valid
            return true;
        } catch (MalformedURLException e) {
            // If a MalformedURLException is thrown, the URL is invalid
            return false;
        }
    }


}
