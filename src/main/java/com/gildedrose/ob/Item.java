package com.gildedrose.ob;

import lombok.*;

@ToString
@NoArgsConstructor
public class Item {

    private @Getter @Setter int id;

    private @Getter @Setter String name;

    private @Getter @Setter int sellIn;

    private @Getter @Setter int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public String convertToJson() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"").append(name).append("\",\"sellIn\":").append(sellIn)
                .append(",\"quality\":").append(quality).append("}");
        return sb.toString();
    }
}
