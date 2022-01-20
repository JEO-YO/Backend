package com.unittest.firebasepost.Model;


public class Post {
    private String key;
    private String classify;
    private String category;
    private String period;
    private String frequency;
    private String memberCnt;
    private String memberCondition;
    private String title;
    private String description;
    private String thumbnail = "";


    public Post() {
    }

    public Post(String key, String classify, String category, String period, String frequency, String memberCnt, String memberCondition, String title, String description, String thumbnail) {
        this.key = key;
        this.classify = classify;
        this.category = category;
        this.period = period;
        this.frequency = frequency;
        this.memberCnt = memberCnt;
        this.memberCondition = memberCondition;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(String memberCnt) {
        this.memberCnt = memberCnt;
    }

    public String getMemberCondition() {
        return memberCondition;
    }

    public void setMemberCondition(String memberCondition) {
        this.memberCondition = memberCondition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
