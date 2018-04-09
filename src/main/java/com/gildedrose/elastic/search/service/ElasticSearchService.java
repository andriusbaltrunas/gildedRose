package com.gildedrose.elastic.search.service;

import com.gildedrose.ob.Item;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
public interface ElasticSearchService {

    List<Item> searchItems(Map<String, String> params) throws IOException;

    Item getItem(int itemId);

    void createItem(Item item);

    void deleteItem();
}
