package com.gildedrose.elastic.search.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gildedrose.elastic.search.service.ElasticSearchService;
import com.gildedrose.elastic.search.service.builder.ElasticSearchBuilderService;
import com.gildedrose.ob.Item;
import org.apache.http.HttpEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private static final String SEARCH = "_search";
    private static final String ID = "_id";
    private static final String SOURCE = "_source";
    private static final String RESULT = "result";
    private static final String GET = "GET";
    private static final String POST = "POST";

    private final ElasticSearchBuilderService elasticSearchBuilderService;
    private final Environment environment;

    @Autowired
    ElasticSearchServiceImpl(final ElasticSearchBuilderService elasticSearchBuilderService, final Environment environment) {
        this.elasticSearchBuilderService = elasticSearchBuilderService;
        this.environment = environment;
    }

    @Override
    public List<Item> searchItems(Map<String, String> params) throws IOException {
        List<Item> items = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        String url = buildUrl(SEARCH);
        Response response = elasticSearchBuilderService.executeRequest(url, GET, params);

        JsonNode json = mapper.readTree(EntityUtils.toString(response.getEntity()));
        List<JsonNode> nodes = json.findValues(SOURCE);
        List<JsonNode> ids = json.findValues(ID);
        IntStream.range(0, nodes.size()).forEach(i -> {
            items.add(mapObject(Item.class, nodes.get(i)));
            items.get(i).setId(ids.get(i).asInt());
        });
        return items;
    }

    @Override
    public boolean updateItem(Item item) throws IOException {
        String url = buildUrl(String.valueOf(item.getId()));
        Response response = elasticSearchBuilderService.updateRequest(url, item, POST);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
        List<JsonNode> nodes = json.findValues(RESULT);
        return !nodes.isEmpty();
    }

    @Override
    public void deleteItem() {

    }

    private <T> T mapObject(Class<T> clazz, JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        T t = null;
        try {
            t = objectMapper.readValue(jsonNode.toString(), clazz);
        } catch (IOException e) {
            //TODO LOG ERROR
        }
        return t;
    }

    private String buildUrl(String suffix) {
        String urlPrefix = environment.getProperty("elastic.search.url.prefix");
        StringBuilder sb = new StringBuilder(urlPrefix);
        sb.append(suffix);
        return sb.toString();
    }
}
