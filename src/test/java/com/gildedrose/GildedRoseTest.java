package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void itemsWhosQualityIsNegativeAreReportedAsQualityZero() {
      Item[] items = new Item[] { new Item("junk", 0, -1)};
      GildedRose app = new GildedRose(items);
      assertEquals(app.items[0].quality, 0);
    }
}
