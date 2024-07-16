package org.tdod.demo6.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tdod.demo6.entity.DencodeEntity;
import org.tdod.demo6.exception.DencodeException;
import org.tdod.demo6.repository.DencoderRepository;
import org.tdod.demo6.util.Messages;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class DencoderServiceImpl implements DencoderService {

    Logger logger = LoggerFactory.getLogger(DencoderServiceImpl.class);

    // We could technically pull these into the DB or a property file for run time adjustment.
    private static final int SIZE = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Autowired
    DencoderRepository dencoderRepository;

    /**
     * Encodes a URL to a shortened URL
     * @param url the regular url to shorten.
     * @return a shortened version of the regular url.
     */
    @Override
    public DencodeEntity encode(String url) {
        // Do not process if url is invalid.
        if (!isValidURL(url)) {
            throw new DencodeException(Messages.INVALID_URL);
        }

        String host = extractUrlHost(url);
        // Can't shorten an already shortened URL
        if (host.equalsIgnoreCase(dencoderRepository.getShortenedUrlHost())) {
            throw new DencodeException(Messages.URL_ALREADY_SHORTENED);
        }

        // If the URL has already been shortened, do not generate and grab the old one.
        Optional<String> key = dencoderRepository.getKey(url);
        if (key.isPresent()) {
            String shortenedKey = dencoderRepository.getShortenedUrlHost() + key.get();
            return new DencodeEntity(shortenedKey, url);
        }

        // Generate a random key.  If not unique, retry.
        String randomKey = generateRandomString();
        while (dencoderRepository.isKeyExists(randomKey)) {
            logger.info("Found key {} in DB. Regenerating...", randomKey);
            randomKey = generateRandomString();
        }

        // Create the  shortened URL and add to the DB.
        String shortenedUrl = dencoderRepository.getShortenedUrlHost() + randomKey;
        dencoderRepository.addKey(randomKey, url);

        return new DencodeEntity(shortenedUrl, url);
    }

    /**
     * Decodes a shortened URL to its original URL.
     * @param url the shortened URL.
     * @return returns the original URL.
     */
    @Override
    public DencodeEntity decode(String url) {
        String key = extractUrlKey(url);
        String host = extractUrlHost(url);

        // Check if the URL is valid
        if (!host.equalsIgnoreCase(dencoderRepository.getShortenedUrlHost())) {
            throw new DencodeException(Messages.INVALID_SHORTENED_URL);
        }

        Optional<String> normalUrl = dencoderRepository.getNormalUrl(key);

        // Shortened URL is not found
        if (normalUrl.isEmpty()) {
            throw new DencodeException(Messages.SHORTENED_URL_NOT_FOUND);
        }

        return new DencodeEntity(url, normalUrl.get());
    }

    /**
     * Used for debug purposes!
     * Returns all data in the DB.
     * @return all data in the DB.
     */
    @Override
    public List<DencodeEntity> getAll() {
        return dencoderRepository.getAll();
    }

    /**
     * Used for debug purposes!
     * Adds a key for the specified url to the DB
     * @param key the key to add.
     * @param url the url to add.
     */
    @Override
    public void addKey(String key, String url) {
        dencoderRepository.addKey(key, url);
    }

    /**
     * Generates a random string of characters and numbers based on the size.
     * @return a random string of characters and numbers based on the size.
     */
    private String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(SIZE);

        for (int i = 0; i < SIZE; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    /**
     * Given a shortened URL in string format, return everything after the last backslash.
     *
     * @param urlString the URL in question
     * @return everything after the last backslash.
     */
    private String extractUrlKey(String urlString) {
        if (urlString == null || urlString.isEmpty()) {
            throw new DencodeException(Messages.URL_STRING_IS_EMPTY);
        }

        int lastIndex = urlString.lastIndexOf('/');
        if (lastIndex == -1) {
            throw new DencodeException(Messages.INVALID_URL);
        }

        return urlString.substring(lastIndex + 1);
    }

    /**
     * Given a URL in string format, return the host.
     *
     * @param urlString the URL in question.
     * @return the host of the URL
     */
    private String extractUrlHost(String urlString) {
        try {
            URL url = new URL(urlString);
            String protocol = url.getProtocol();
            String host = url.getHost();

            return protocol + "://" + host + "/";
        } catch (MalformedURLException e) {
            return null;
        }
    }
        /**
     * Checks if the URL in string format is a valid URL.
     * @param urlString the URL in question
     * @return true if the string appears to be a valid URL, false otherwise.
     */
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
