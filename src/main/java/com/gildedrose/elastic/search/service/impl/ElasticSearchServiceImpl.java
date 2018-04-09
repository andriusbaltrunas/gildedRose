package com.gildedrose.elastic.search.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gildedrose.elastic.search.service.ElasticSearchService;
import com.gildedrose.elastic.search.service.builder.ElasticSearchBuilderService;
import com.gildedrose.ob.Item;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private static final String SEARCH = "_search";
    private static final String SOURCE = "_source";
    private static final String GET = "GET";

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

        nodes.forEach(node -> items.add(mapObject(Item.class, node)));
        return items;
    }

    @Override
    public Item getItem(int itemId) {
        return null;
    }

    @Override
    public void createItem(Item item) {

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
