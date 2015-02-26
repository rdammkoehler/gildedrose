package com.gildedrose;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

    private static final String JUNK = "junk";
    private static final String AGED_BRIE = "Aged Brie";

    @Test
    public void itemsWhosQualityIsNegativeAreReportedAsQualityZero() {
      Item[] items = new Item[] { new Item(JUNK, 0, -1)};
      GildedRose app = new GildedRose(items);
      assertThat(app.items[0].quality, is(0));
    }
    
    @Test
    public void ctItemsWhosQualityBecomesNegativeAreReportedAsQualityZero() {
      Item[] items = new Item[] { new Item(JUNK, 0, 0)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertThat(app.items[0].quality, is(0));
    }
    
    @Test
    public void ctSpecialItemsWhosQualityIs50CannotGetHigherQuality() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 0, 50)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertThat(app.items[0].quality, is(50));
    }
    
    @Test
    public void ctSpecialItemsIncreaseInQualityWithAge() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 11, 40)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertThat(app.items[0].quality, is(41));
    }
    
    @Test
    public void ctAgedBrieIncreasesInQualityByTwoOnItsExpirationDay() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 0, 40)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertThat(app.items[0].quality, is(42));
    }
    
    @Test
    public void ctAgedBrieIncreasesInQualityByTwoAfterItsExpirationDay() {
      Item[] items = new Item[] { new Item(AGED_BRIE, -1, 40)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertThat(app.items[0].quality, is(42));
    }
    
    @Test
    public void ctAgedBrieIncreasesInQualityByOneBeforeItsExpirationDay() {
      Item[] items = new Item[] { new Item(AGED_BRIE, 1, 40)};
      GildedRose app = new GildedRose(items);
      app.updateQuality();
      assertThat(app.items[0].quality, is(41));
    }
}
