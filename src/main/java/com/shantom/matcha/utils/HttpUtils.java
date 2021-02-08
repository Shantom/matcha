package com.shantom.matcha.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    @Autowired
    private RestTemplate restTemplate;

    public String httpPost(JSONObject params, UrlEnum url) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.fullUrl(), params, String.class);
        String response = responseEntity.getBody();
        logger.debug(response);
        return response;
    }

}
