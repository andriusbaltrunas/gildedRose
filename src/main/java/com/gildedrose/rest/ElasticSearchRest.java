package com.gildedrose.rest;

import com.gildedrose.elastic.search.service.ElasticSearchService;
import com.gildedrose.ob.Item;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@RestController
@RequestMapping(value = "/elasticSearch")
public class ElasticSearchRest {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @RequestMapping(value = "/getItems", method = RequestMethod.GET)
    public List<Item> getItems(@PathParam("id") String id) {
        Map<String, String> params = new HashMap<>();
        if (id != null) {
            params.put("q", "_id:" + id);
        }
        List<Item> items = new ArrayList<>();
        try {
            items = elasticSearchService.searchItems(params);
        } catch (IOException e) {
            //TODO LOG ERROR GIVE RESPONSE TO USER
        }
        return items;
    }
}
