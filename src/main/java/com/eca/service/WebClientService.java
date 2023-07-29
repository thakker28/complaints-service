package com.eca.service;

import java.util.Map;

public interface WebClientService {

    <T> T get(String uri, Map<String, String> headers, Class<T> responseType);
}
