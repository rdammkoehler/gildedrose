package com.gildedrose;

import static java.lang.Math.max;
import static java.lang.Math.min;

class GildedRose {
  private static final int DEFAULT_QUALITY_INCREASE_AMOUNT = 1;
  private static final int DEFAULT_QUALITY_DECAY_AMOUNT = -1;
  private static final int QUALITY_FLOOR = 0;
  private static final int QUALITY_CEILING = 50;
  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
  private static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
  Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
    for (Item item : items) {
      item.quality = (item.quality < 0) ? 0 : item.quality;
    }
  }

  public void updateItems() {
    for (Item item : items) {
      updateItem(item);
    }
  }

  private void updateItem(Item item) {
    adjustQuality(item);
    decrementDaysRemainingToSell(item);
    makePostAgingQualityAdjustment(item);
  }

  private void makePostAgingQualityAdjustment(Item item) {
    if (pastSellBy(item)) {
      if (isAgedBrie(item)) {
        incrementQuality(item);
      } else {
        if (isBackstagePass(item)) {
          makeWorthless(item);
        } else {
          decrementQuality(item);
        }
      }
    }
  }

  private void adjustQuality(Item item) {
    if (isAgedBrie(item) || isBackstagePass(item)) {
      incrementQuality(item);
      if (isBackstagePass(item)) {
        if (item.sellIn < 11) {
          incrementQuality(item);
        }

        if (item.sellIn < 6) {
          incrementQuality(item);
        }
      }
    } else {
      decrementQuality(item);
    }
  }

  private boolean pastSellBy(Item item) {
    return item.sellIn < 0;
  }

  private void decrementDaysRemainingToSell(Item item) {
    if (!isSulfurasHandOfRagnaros(item)) {
      item.sellIn = item.sellIn - 1;
    }
  }

  private boolean isSulfurasHandOfRagnaros(Item item) {
    return item.name.equals(SULFURAS_HAND_OF_RAGNAROS);
  }

  private boolean isAgedBrie(Item item) {
    return item.name.equals(AGED_BRIE);
  }

  private boolean isBackstagePass(Item item) {
    return item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT);
  }

  private void makeWorthless(Item item) {
    item.quality = item.quality - item.quality;
  }

  private void incrementQuality(Item item) {
    item.quality = min(QUALITY_CEILING, item.quality + DEFAULT_QUALITY_INCREASE_AMOUNT);
  }

  private void decrementQuality(Item item) {
    if (!isSulfurasHandOfRagnaros(item)) {
      int adjustBy = calculateQualityDecayAmount(item);
      item.quality = max(QUALITY_FLOOR, item.quality + adjustBy);
    }
  }

  private int calculateQualityDecayAmount(Item item) {
    int adjustBy = DEFAULT_QUALITY_DECAY_AMOUNT;
    if (item.name.equals(CONJURED_MANA_CAKE)) {
      adjustBy *= 2;
    }
    return adjustBy;
  }
}
