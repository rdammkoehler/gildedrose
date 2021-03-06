package com.gildedrose;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

  private static final Integer QUALITY_OF_SULFURAS_HAND_OF_RAGNAROS = 80;
  private static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
  private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
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
    updateItems();
    assertThat(itemOne().quality, is(0));
  }

  @Test
  public void ctSpecialItemsWhosQualityIs50CannotGetHigherQuality() {
    initialize(new Item(AGED_BRIE, -10, 50));
    updateItems();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctSpecialItemsIncreaseInQualityWithAge() {
    initialize(new Item(AGED_BRIE, 11, 40));
    updateItems();
    assertThat(itemOne().quality, is(41));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByTwoOnItsExpirationDay() {
    initialize(new Item(AGED_BRIE, 0, 40));
    updateItems();
    assertThat(itemOne().quality, is(42));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByTwoAfterItsExpirationDay() {
    initialize(new Item(AGED_BRIE, -1, 40));
    updateItems();
    assertThat(itemOne().quality, is(42));
  }

  @Test
  public void ctAgedBrieIncreasesInQualityByOneBeforeItsExpirationDay() {
    initialize(new Item(AGED_BRIE, 1, 40));
    updateItems();
    assertThat(itemOne().quality, is(41));
  }

  @Test
  public void ctConcertPassIncreasesInQualityByOneWhenMoreThan10DaysBeforeItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 11, 20));
    updateItems();
    assertThat(itemOne().quality, is(21));
  }

  @Test
  public void ctConcertPassIncreasesInQualityByTwoWhen10DaysBeforeItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 10, 20));
    updateItems();
    assertThat(itemOne().quality, is(22));
  }

  @Test
  public void ctConcertPassIncreasesInQualityByThreeWhen5DaysBeforeItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 5, 20));
    updateItems();
    assertThat(itemOne().quality, is(23));
  }

  @Test
  public void ctConcertPassIsWorthlessWhenAfterItsExpirationDay() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, -1, 20));
    updateItems();
    assertThat(itemOne().quality, is(0));
  }

  @Test
  public void ctConcertPassCannotExceedQuality50Before10DaysToSellIn() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 11, 50));
    updateItems();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctConcertPassCannotExceedQuality50Before5DaysToSellIn() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 5, 50));
    updateItems();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctConcertPassCannotExceedQuality50Before0DaysToSellIn() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 1, 50));
    updateItems();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctConcertPassCannotExceedQualtiy50() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 2, 48));
    updateItems();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctHandOfRangnarosQualityNeverDecreases() {
    initialize(new Item(SULFURAS_HAND_OF_RAGNAROS, 10, 10));
    updateItems();
    assertThat(itemOne().quality, is(QUALITY_OF_SULFURAS_HAND_OF_RAGNAROS));
  }

  @Test
  public void ctHandOfRangnarosNeverExpires() {
    initialize(new Item(SULFURAS_HAND_OF_RAGNAROS, 10, 10));
    updateItems();
    assertThat(itemOne().sellIn, is(10));
  }

  @Test
  public void ctCommonItemsQualityDecreasesByTwoAfterExpiration() {
    initialize(new Item(JUNK, -2, 5));
    updateItems();
    assertThat(itemOne().quality, is(3));
  }

  @Test
  public void ctConcertPassWithQuality49SellIn10DoesNotExceedQuality50() {
    initialize(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, 10, 49));
    updateItems();
    assertThat(itemOne().quality, is(50));
  }

  @Test
  public void ctHandOfRangnarosSellInLessThanZeroQualityRemainsUnchanged() {
    initialize(new Item(SULFURAS_HAND_OF_RAGNAROS, -1, 10));
    updateItems();
    assertThat(itemOne().quality, is(QUALITY_OF_SULFURAS_HAND_OF_RAGNAROS));
  }

  @Test
  public void conjuredItemsDegradeInQualityTwiceAsFast() {
    initialize(new Item(CONJURED_MANA_CAKE, 5, 2));
    updateItems();
    assertThat(itemOne().quality, is(0));
  }

  @Test
  public void handOfRangnarosAlwaysHasQuality80() {
    initialize(new Item(SULFURAS_HAND_OF_RAGNAROS, -1, 10));
    assertThat(itemOne().quality, is(QUALITY_OF_SULFURAS_HAND_OF_RAGNAROS));
  }
  
  private void updateItems() {
    app.updateItems();
  }

  private Item itemOne() {
    return app.items[0];
  }
}
