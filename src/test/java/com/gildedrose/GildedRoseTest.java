package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    private static final String JUNK = "junk";
    private static final String AGED_BRIE = "Aged Brie";

    @Test
    public void itemsWhosQualityIsNegativeAreReportedAsQualityZero() {
      Item[] items = new Item[] { new Item(JUNK, 0, -1)};
      GildedRose app = new GildedRose(items);
      assertEquals(0, app.items[0].quality);
    }
    
    @Test
    public void ctItemsWhosQualityBecomesNegativeAreReportedAsQualityZero() {
      Item[] items = new Item[] { new Item(JUNK, 0, 0)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertEquals(0, app.items[0].quality);
    }
    
    @Test
    public void ctSpecialItemsWhosQualityIs50CannotGetHigherQuality() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 0, 50)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertEquals(50, app.items[0].quality);
    }
    
    @Test
    public void ctSpecialItemsIncreaseInQualityWithAge() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 11, 40)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertEquals(41, app.items[0].quality);
    }
    
    @Test
    public void ctAgedBrieIncreasesInQualityByTwoOnItsExpirationDay() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 0, 40)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertEquals(42, app.items[0].quality);
    }
}
