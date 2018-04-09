package com.gildedrose;

import com.gildedrose.ob.Item;

import java.util.ArrayList;
import java.util.List;

public class TexttestFixture {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        List<Item> items = new ArrayList<>();
        items.add(new Item("+5 Dexterity Vest", 10, 20)); //
        items.add(new Item("Aged Brie", 2, 0)); //
        items.add(new Item("Elixir of the Mongoose", 5, 7)); //
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));//
        items.add(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49));
        // this conjured item does not work properly yet
        items.add(new Item("Conjured Mana Cake", 3, 6));

        GildedRose app = new GildedRose(items);

        int days = 5;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
