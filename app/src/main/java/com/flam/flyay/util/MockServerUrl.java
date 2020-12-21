package com.flam.flyay.util;

public enum MockServerUrl {

    LOGIN_OK("http://10.0.2.2:3000/user/signinOK"),
    LOGIN_KO("http://10.0.2.2:3000/user/signinKO");

    public final String url;

    MockServerUrl(String url) {
        this.url = url;
    }
}
