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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<CompletableFuture<Void>> features = new ArrayList<>();
        IntStream.range(0, days).forEach(i -> features.add(CompletableFuture.runAsync(() -> runProcess(items, app, i))));

        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(features.toArray(new CompletableFuture[features.size()]));
        completableFuture.get();
        IntStream.range(0, days).forEach(i -> {
            CompletableFuture<Void> f = features.get(i);
            System.out.println("Feature " + i + " is done " + f.isDone());
        });
    }

    private void runProcess(List<Item> items, GildedRose app, int i) {
        System.out.println("-------- day " + i + " --------");
        System.out.println("name, sellIn, quality");
        for (Item item : items) {
            System.out.println("|DAY| " + i +" " +item);
            try {
                updateItem(item);
            } catch (Exception e) {
                //TODO LOG ERROR
            }
        }
        System.out.println();
        app.updateQuality();
    }

    public void updateItem(Item item) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(item);
        String url = UPDATE_PATH.replace("{itemId}", String.valueOf(item.getId()));
        mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(jsonString));
    }
}