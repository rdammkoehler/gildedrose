package com.gildedrose;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

  private static final String BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
  private static final String JUNK = "junk";
  private static final String AGED_BRIE = "Aged Brie";
  private GildedRose app;

  private void initialize(Item... items) {
    app = new GildedRose(items);
  }

  @Test
  public void itemsWhosQualityIsNegativeAreReportedAsQualityZero() {
    initialize(new Item(JUNK, 0, -1));
    assertThat(itemOne().quality, is(0));
  }

  @Test
  public void ctItemsWhosQualityBecomesNegativeAreReportedAsQualityZero() {
    initialize(new Item(JUNK, 0, 0));
    updateQuality();
    assertThat(itemOne().quality, is(0));
  }

  @Test
  public void ctSpecialItemsWhosQualityIs50CannotGetHigherQuality() {
    initialize(new Item(AGED_BRIE, 0, 50));
    updateQuality();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctSpecialItemsIncreaseInQualityWithAge() {
    initialize(new Item(AGED_BRIE, 11, 40));
    updateQuality();
    assertThat(itemOne().quality, is(41));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByTwoOnItsExpirationDay() {
    initialize(new Item(AGED_BRIE, 0, 40));
    updateQuality();
    assertThat(itemOne().quality, is(42));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByTwoAfterItsExpirationDay() {
    initialize(new Item(AGED_BRIE, -1, 40));
    updateQuality();
    assertThat(itemOne().quality, is(42));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByOneBeforeItsExpirationDay() {
    initialize(new Item(AGED_BRIE, 1, 40));
    updateQuality();
    assertThat(itemOne().quality, is(41));
  }

  @Test
  public void ctConcertPassIncreasesInQualityByOneWhenMoreThan10DaysBeforeItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 11, 20));
    updateQuality();
    assertThat(itemOne().quality, is(21));
  }
  
  @Test
  public void ctConcertPassIncreasesInQualityByTwoWhen10DaysBeforeItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 10, 20));
    updateQuality();
    assertThat(itemOne().quality, is(22));
  }

  @Test
  public void ctConcertPassIncreasesInQualityByThreeWhen5DaysBeforeItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 5, 20));
    updateQuality();
    assertThat(itemOne().quality, is(23));
  }
  
  @Test
  public void ctConcertPassIsWorthlessWhenAfterItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, -1, 20));
    updateQuality();
    assertThat(itemOne().quality, is(0));
  }
  
  @Test
  public void ctConcertPassCannotExceedQuality50Before10DaysToSellIn() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT,11,50));
    updateQuality();
    assertThat(itemOne().quality,is(50));
  }
  
  @Test
  public void ctConcertPassCannotExceedQuality50Before5DaysToSellIn() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT,5,50));
    updateQuality();
    assertThat(itemOne().quality,is(50));
  }
  
  @Test
  public void ctConcertPassCannotExceedQuality50Before0DaysToSellIn() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT,1,50));
    updateQuality();
    assertThat(itemOne().quality,is(50));
  }
  
  private void updateQuality() {
    app.updateQuality();
  }

  private Item itemOne() {
    return app.items[0];
  }
}
