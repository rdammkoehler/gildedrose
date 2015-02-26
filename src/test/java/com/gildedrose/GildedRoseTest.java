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
    
    @Test
    public void ctItemsWhosQualityBecomesNegativeAreReportedAsQualityZero() {
      Item[] items = new Item[] { new Item("junk", 0, 0)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertEquals(app.items[0].quality, 0);
    }
    
    @Test
    public void ctSpecialItemsWhosQualityIs50CannotGetHigherQuality() {
      Item[] items = new Item[] { new Item("Aged Brie", 0, 50)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertEquals(app.items[0].quality,50);
    }
}
