package com.flam.flyay.util;

public enum MockServerUrl {

    SIGNIN_OK("http://10.0.2.2:3000/user/signinOK"),
    SIGNIN_KO("http://10.0.2.2:3000/user/signinKO"),
    EVENT_DAY("http://10.0.2.2:3000/event/day"),
    PROFILE_USER("http://10.0.2.2:3000/user"),
    LIST("http://10.0.2.2:3000/list"),
    SUBCATEGORY_ITEMS("http://10.0.2.2:3000/subcategory/items");

    public final String url;

    MockServerUrl(String url) {
        this.url = url;
    }
}
