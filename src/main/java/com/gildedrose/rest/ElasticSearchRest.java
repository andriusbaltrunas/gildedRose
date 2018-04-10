package com.gildedrose.rest;

import com.gildedrose.elastic.search.service.ElasticSearchService;
import com.gildedrose.ob.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchRest.class);

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
            log.info("Successfully got items from elastic search, result size is " + items.size());
        } catch (IOException e) {
            log.error("Can`t get items from elastic search service", e);
        }
        return items;
    }

    @RequestMapping(value = "/{itemId}/updateItem", method = RequestMethod.POST)
    public boolean updateItem(@PathVariable("itemId") String itemId, @RequestBody Item item) {
        boolean isUpdated = false;
        if (item != null) {
            item.setId(Integer.valueOf(itemId));
            try {
                isUpdated = elasticSearchService.updateItem(item);
                log.info("item successfully updated " + item.convertToJson());
            } catch (IOException e) {
                log.error("Can`t update elastic seatch item",e);
            }
        }
        return isUpdated;
    }
}
