package com.gildedrose.elastic.search.service.impl;

import com.gildedrose.SpringBootStartUp;
import com.gildedrose.elastic.search.service.ElasticSearchService;
import com.gildedrose.ob.Item;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootStartUp.class)
public class ElasticSearchServiceImplTest {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void testGetAllSearchResults() throws IOException {
        List<Item> items = elasticSearchService.searchItems(null);
        assertNotNull(items);
        assertTrue(!items.isEmpty());
        items.forEach(item -> assertNotNull(item));
    }

    @Test
    public void testSearchItemById() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("q", "_id:1");
        List<Item> items = elasticSearchService.searchItems(params);
        assertNotNull(items);
        assertTrue(items.size() == 1);
        assertNotNull(items.get(0));
    }

    @Test
    public void testCreateNewItem() throws IOException {
        Item item = new Item("Aged Brie", 12, 34);
        item.setId(1);
        boolean isUpdated = elasticSearchService.updateItem(item);
        assertTrue(isUpdated);
    }

}