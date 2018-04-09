package com.gildedrose.elastic.search.service.builder;

import com.gildedrose.ob.Item;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */

public interface ElasticSearchBuilderService {
    RestClient buildRestClient();

    Response executeRequest(String url, String method, Map<String, String> params) throws IOException;

    Response updateRequest(String url, Item item, String method) throws IOException;
}
