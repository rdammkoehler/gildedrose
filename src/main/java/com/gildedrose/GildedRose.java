package com.gildedrose;

class GildedRose {
  private static final int QUALITY_FLOOR = 0;
  private static final int QUALITY_CEILING = 50;
  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
  Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
    for (Item item : items) {
      item.quality = (item.quality < 0) ? 0 : item.quality;
    }
  }

  public void updateQuality() {
    for (Item item : items) {
      updateItem(item);
    }
  }

  private void updateItem(Item item) {
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

    if (!isSulfurasHandOfRagnaros(item)) {
      decrementDaysRemainingToSell(item);
    }

    if (item.sellIn < 0) {
      if (!isAgedBrie(item)) {
        if (!isBackstagePass(item)) {
          decrementQuality(item);
        } else {
          makeWorthless(item);
        }
      } else {
        incrementQuality(item);
      }
    }
  }

  private void decrementDaysRemainingToSell(Item item) {
    item.sellIn = item.sellIn - 1;
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
    if (item.quality < QUALITY_CEILING) {
      item.quality = item.quality + 1;
    }
  }

  private void decrementQuality(Item item) {
    if (!isSulfurasHandOfRagnaros(item)) {
      item.quality = Math.max(QUALITY_FLOOR, item.quality - 1);
    }
  }
}
