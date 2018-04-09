package com.gildedrose;

class GildedRose {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            startProcess(items[i]);
            removeOneSellInItem(items[i]);

            if (items[i].getSellIn() < 0) {
               startProcess(items[i]);
            }
        }
    }

    private void startProcess(Item item) {
        if (!item.getName().equals(AGED_BRIE)) {
            minimizeQuality(item);
        } else {
            increaseQuantityByOne(item);
        }
    }

    private void minimizeQuality(Item item) {
        if (!item.getName().equals(BACKSTAGE_PASSES) && item.getQuality() > 0) {
            if (!item.getName().equals(SULFURAS)) {
                item.setQuality(item.getQuality() - 1);
            }
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
    public Item[] getItems() {
        return items;
    }
}