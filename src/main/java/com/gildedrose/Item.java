package com.gildedrose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Item {

    private @Getter @Setter String name;

    private @Getter @Setter int sellIn;

    private @Getter @Setter int quality;
}
