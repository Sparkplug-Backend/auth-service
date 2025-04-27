package com.sparkplug.auth.application.security;

import java.util.Map;

public interface JwkProvider {
    Map<String, String> getPublicKey();
}
