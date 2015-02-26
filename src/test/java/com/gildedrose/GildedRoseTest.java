package com.gildedrose;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

  private static final String JUNK = "junk";
  private static final String AGED_BRIE = "Aged Brie";
  private GildedRose app;

  private void initialize(Item... items) {
    app = new GildedRose(items);
  }

  @Test
  public void itemsWhosQualityIsNegativeAreReportedAsQualityZero() {
    initialize(new Item(JUNK, 0, -1));
    assertThat(app.items[0].quality, is(0));
  }

  @Test
  public void ctItemsWhosQualityBecomesNegativeAreReportedAsQualityZero() {
    initialize(new Item(JUNK, 0, 0));
    app.updateQuality();
    assertThat(app.items[0].quality, is(0));
  }

  @Test
  public void ctSpecialItemsWhosQualityIs50CannotGetHigherQuality() {
    initialize(new Item(AGED_BRIE, 0, 50));
    app.updateQuality();
    assertThat(app.items[0].quality, is(50));
  }

  @Test
  public void ctSpecialItemsIncreaseInQualityWithAge() {
    initialize(new Item(AGED_BRIE, 11, 40));
    app.updateQuality();
    assertThat(app.items[0].quality, is(41));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByTwoOnItsExpirationDay() {
    initialize(new Item(AGED_BRIE, 0, 40));
    app.updateQuality();
    assertThat(app.items[0].quality, is(42));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByTwoAfterItsExpirationDay() {
    initialize(new Item(AGED_BRIE, -1, 40));
    app.updateQuality();
    assertThat(app.items[0].quality, is(42));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByOneBeforeItsExpirationDay() {
    initialize(new Item(AGED_BRIE, 1, 40));
    app.updateQuality();
    assertThat(app.items[0].quality, is(41));
  }
}
