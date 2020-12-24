package com.flam.flyay.services;

import org.json.JSONObject;

/**
 * This interface allows us to generalize server calls
 */
public interface ServerCallback {

    void onSuccess(Object result);
}
