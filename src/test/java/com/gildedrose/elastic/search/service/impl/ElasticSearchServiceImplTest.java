package com.gildedrose.elastic.search.service.impl;

import com.gildedrose.SpringBootStartUp;
import com.gildedrose.elastic.search.service.ElasticSearchService;
import com.gildedrose.ob.Item;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

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
        Assert.assertNotNull(items);
        Assert.assertTrue(!items.isEmpty());
        items.forEach(item -> Assert.assertNotNull(item));
    }

}