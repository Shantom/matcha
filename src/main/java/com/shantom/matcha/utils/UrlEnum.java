package com.shantom.matcha.utils;

public enum UrlEnum {
    ANF("/a/stock/fs/non_financial"),
    ;

    private String uri;

    UrlEnum(String uri) {
        this.uri = uri;
    }

    public String fullUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://open.lixinger.com/api").append(uri);
        return sb.toString();
    }
}
