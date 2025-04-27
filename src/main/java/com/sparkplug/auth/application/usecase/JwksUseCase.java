package com.sparkplug.auth.application.usecase;

import java.util.Map;

public interface JwksUseCase {

    Map<String, String> getJwk();
}
