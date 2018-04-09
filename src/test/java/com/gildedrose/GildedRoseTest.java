package com.gildedrose;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    @Test
    public void foo() {
        GildedRose gildedRose = gildedRose = new GildedRose(createMockedData());
        gildedRose.updateQuality();
        assertEquals("foo", gildedRose.getItems().get(0).getName());
    }

    @Test
    public void testDifferentProductSellInAndQuality() {
        GildedRose gildedRose = new GildedRose(createMockedData());
         int sellIn = 5;
        int quality = 10;

        for(int i = 0; i < 3; i++){

           Item item = gildedRose.getItems().get(1);
            assertEquals(sellIn, item.getSellIn());
            assertEquals(quality, item.getQuality());
            assertEquals("iphone", item.getName());
            sellIn --;
            quality --;
            gildedRose.updateQuality();

        }
    }

    @Test
    public void testDifferentProductSellInAndQualityWithBackstage() {
        GildedRose gildedRose = new GildedRose(createMockedData());
        int sellIn = 10;
        int quality = 10;
        for(int i = 0; i < 4; i++){
            Item item = gildedRose.getItems().get(2);
            assertEquals(sellIn, item.getSellIn());
            assertEquals(quality, item.getQuality());
            assertEquals(GildedRose.BACKSTAGE_PASSES, item.getName());
            sellIn --;
            quality+=2;
            gildedRose.updateQuality();

        }
    }

    @Test
    public void testDifferentProductSellInAndQualityWithBrie() {
        GildedRose gildedRose = new GildedRose(createMockedData());
        int sellIn = 9;
        int quality = 49;
        for(int i = 0; i < 4; i++){
            Item item = gildedRose.getItems().get(3);
            assertEquals(sellIn, item.getSellIn());
            assertEquals(quality, item.getQuality());
            assertEquals(GildedRose.AGED_BRIE, item.getName());
            sellIn --;
            quality = 50;
            gildedRose.updateQuality();

        }
    }
    private List<Item> createMockedData() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("foo", 0, 0));
        items.add(new Item("iphone", 5, 10));
        items.add(new Item(GildedRose.BACKSTAGE_PASSES, 10, 10));
        items.add(new Item(GildedRose.AGED_BRIE, 9, 49));
        return items;
    }
}