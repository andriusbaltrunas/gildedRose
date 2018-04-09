package com.gildedrose.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gildedrose.SpringBootStartUp;
import com.gildedrose.ob.Item;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootStartUp.class)
@WebAppConfiguration
public class ElasticSearchRestTest {

    private static final String PATH = "/elasticSearch/getItems";
    private static final String PATH_WITH_ITEM_ID = "/elasticSearch/getItems?q=_id:1";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllItems() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(PATH));
        List<Item> items = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(resultActions.andReturn().getResponse().getContentAsString());
        node.forEach(n -> {
            try {
                items.add(objectMapper.readValue(n.toString(), Item.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Assert.assertTrue(!items.isEmpty());

        items.forEach(i -> Assert.assertNotNull(i));
    }

    @Test
    public void testGetItemById() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(PATH_WITH_ITEM_ID));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(resultActions.andReturn().getResponse().getContentAsString());
        Item item = objectMapper.readValue(node.get(0).toString(), Item.class);
        Assert.assertNotNull(item);

    }

}