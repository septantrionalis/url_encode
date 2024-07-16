package org.tdod.demo6.entity;

public class DencodeEntity {

    private String shortenedUrl;
    private String normalUrl;

    public DencodeEntity(String shortenedUrl, String normalUrl) {
        this.shortenedUrl = shortenedUrl;
        this.normalUrl = normalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public String getNormalUrl() {
        return normalUrl;
    }

    public void setNormalUrl(String normalUrl) {
        this.normalUrl = normalUrl;
    }
}
