package com.example.prans.sanskrit;

/**
 * Created by Pranshu on 16-07-2017.
 */

public class Word {

    private String defaultTranslation;
    private String sanskritTranslation;
    private int imageResourceId = No_IMAGE_PROVIDED;
    private static final int No_IMAGE_PROVIDED = -1;

    public int getAudioResourceId() {
        return audioResourceId;
    }

    private int audioResourceId;

    public Word(String defaultTranslation, String sanskritTranslation, int imageResourceId, int audioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.sanskritTranslation = sanskritTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getSanskritTranslation() {
        return sanskritTranslation;
    }

    public Word(String defaultTranslation, String sanskritTranslation, int audioResourceId) {

        this.defaultTranslation = defaultTranslation;
        this.sanskritTranslation = sanskritTranslation;
        this.audioResourceId = audioResourceId;
    }

    public boolean hasImage() {

        return imageResourceId != No_IMAGE_PROVIDED;
    }
}









