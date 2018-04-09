package com.gildedrose.ob;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private @Getter @Setter String name;

    private @Getter @Setter int sellIn;

    private @Getter @Setter int quality;
}
