package com.gildedrose;

import com.gildedrose.ob.Item;

import java.util.List;

class GildedRose {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private List<Item> items;

    public GildedRose(List<Item> items) {
        this.items = items;
    }

    public void updateQuality() {

        items.forEach(item -> {
            startProcess(item);
            removeOneSellInItem(item);
            if (item.getSellIn() < 0) {
                startProcess(item);
            }
        });
    }

    private void startProcess(Item item) {
        if (!item.getName().equals(AGED_BRIE) && !item.getName().equals(BACKSTAGE_PASSES)) {
            minimizeQuality(item);
        } else {
            increaseQuantityByOne(item);
        }
    }

    private void minimizeQuality(Item item) {
        if (item.getQuality() > 0 && !item.getName().equals(SULFURAS)) {
            item.setQuality(item.getQuality() - 1);
        }
    }

    private void increaseQuantityByOne(Item item) {
        if (item.getQuality() < 50) {
            item.setQuality(item.getQuality() + 1);
            if (item.getName().equals(BACKSTAGE_PASSES) && item.getSellIn() > 0) {
                increaseQualityByGivenNumb(item, 11);
                increaseQualityByGivenNumb(item, 6);
            }
        }
    }

    private void increaseQualityByGivenNumb(Item item, int checkNumb) {
        if (item.getSellIn() < checkNumb) {
            item.setQuality(item.getQuality() + 1);
        }
    }

    private void removeOneSellInItem(Item item) {
        if (item.getSellIn() > 0) {
            item.setSellIn(item.getSellIn() - 1);
        } else {
            item.setSellIn(0);
        }
    }

    // only for tests
    public List<Item> getItems() {
        return items;
    }
}