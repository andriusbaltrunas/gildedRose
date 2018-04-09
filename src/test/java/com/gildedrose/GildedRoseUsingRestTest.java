package com.gildedrose;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gildedrose.ob.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@ContextConfiguration(classes = SpringBootStartUp.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class GildedRoseUsingRestTest {
    private static final String PATH = "/elasticSearch/getItems";
    private static final String UPDATE_PATH = "/elasticSearch/{itemId}/updateItem";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void runGildedRoseWithRest() throws Exception {

        System.out.println("OMGHAI!");

        List<Item> items = new ArrayList<>();

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(PATH));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString());
        nodes.forEach(node -> {
            try {
                items.add(objectMapper.readValue(node.toString(), Item.class));
            } catch (IOException e) {
                //TODO LOG ERROR
            }
        });
        GildedRose app = new GildedRose(items);

        int days = 5;// day should be set manually
        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
                updateItem(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

    public void updateItem(Item item) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(item);
        String url = UPDATE_PATH.replace("{itemId}", String.valueOf(item.getId()));
        mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString));
    }
}