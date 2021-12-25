package com.fusionx.lending.transaction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionx.lending.transaction.core.DefaultRequestHeaders;
import com.fusionx.lending.transaction.enums.ServiceStatus;
import com.fusionx.lending.transaction.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Component
@Transactional(rollbackFor = Exception.class)
public class RemoteServiceImpl implements RemoteService {

    @Autowired
    private RestTemplate restTemplate;
    private String serviceStatus = "{\"serviceStatus\" : \"";

    @Value("${account-statement-status}")
    private String statementStatusUrl;

    @Value("${frequency}")
    private String urlFrequency;

    /**
     * get id and description from service
     *
     * @param Url
     * @param Description
     * @param tenantId
     * @return Object
     */
    @Override
    public Object checkIsExist(String tenantId, String id, String serviceUrl, Class<?> classObject) {
        try {
            HttpHeaders headers = DefaultRequestHeaders.getInstance().getHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(serviceUrl + tenantId + "/" + id, HttpMethod.GET, entity, classObject).getBody();
        } catch (RestClientException e) {
            String result = null;
            if (e.getMessage().contains("503")) {
                result = serviceStatus + ServiceStatus.NOT_AVAILABLE.toString() + "\"}";
            } else if (e.getMessage().contains("500")) {
                result = serviceStatus + ServiceStatus.EXCEPTION.toString() + "\"}";
            } else if (e.getMessage().contains("404")) {
                result = serviceStatus + ServiceStatus.NOT_FOUND.toString() + "\"}";
            } else if (e.getMessage().contains("400")) {
                result = serviceStatus + ServiceStatus.BAD_REQUEST.toString() + "\"}";
            } else if (e.getMessage().contains("504")) {
                result = serviceStatus + ServiceStatus.GATEWAY_TIME_OUT.toString() + "\"}";
            } else {
                result = serviceStatus + ServiceStatus.OTHER.toString() + "\"}";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(result, classObject);
            } catch (IOException e1) {
                return null;
            }
        }
    }


}
