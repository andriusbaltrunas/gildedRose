package com.gildedrose.elastic.search.service.builder.impl;

import com.gildedrose.elastic.search.service.builder.ElasticSearchBuilderService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@Service
public class ElasticSearchBuilderServiceImpl implements ElasticSearchBuilderService {

    @Autowired
    private Environment environment;

    @Override
    public RestClient buildRestClient() {
        RestClient restClient = RestClient.builder(new HttpHost(
                environment.getProperty("elastic.search.host"),
                Integer.parseInt(environment.getProperty("elastic.search.port")),
                environment.getProperty("elastic.search.http"))).build();
        return restClient;
    }

    @Override
    public Response executeRequest(String url, String method, Map<String, String> params) throws IOException {
        RestClient restClient = buildRestClient();
        Response response;
        if (params != null) {
            response = restClient.performRequest(method, url, params);
        } else {
            response = restClient.performRequest(method, url);
        }
        return response;
    }
}
