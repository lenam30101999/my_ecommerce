package com.demo.elk.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Log4j2
public class RequestUtil {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T> T request(
            HttpMethod method, String requestUrl, Class<T> tClass, Object entity, Map<String, String> headerParam) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            if (headerParam != null) {
                for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                    headers.add(entry.getKey(), entry.getValue());
                }
            }

            HttpEntity<Object> data = new HttpEntity<>(entity, headers);
            ResponseEntity<String> json = restTemplate.exchange(requestUrl, method, data, String.class);

            return JsonParser.entity(json.getBody(), tClass);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
