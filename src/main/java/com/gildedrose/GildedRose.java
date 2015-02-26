package com.gildedrose;

class GildedRose {
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
    if (!isAgedBrie(item) && !isBackstagePass(item)) {
      if (item.quality > 0) {
        if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
          decrementQuality(item);
        }
      }
    } else {
      if (item.quality < 50) {
        incrementQuality(item);

        if (isBackstagePass(item)) {
          if (item.sellIn < 11) {
            if (item.quality < 50) {
              incrementQuality(item);
            }
          }

          if (item.sellIn < 6) {
            if (item.quality < 50) {
              incrementQuality(item);
            }
          }
        }
      }
    }

    if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
      item.sellIn = item.sellIn - 1;
    }

    if (item.sellIn < 0) {
      if (!isAgedBrie(item)) {
        if (!isBackstagePass(item)) {
          if (item.quality > 0) {
            if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
              decrementQuality(item);
            }
          }
        } else {
          makeWorthless(item);
        }
      } else {
        if (item.quality < 50) {
          incrementQuality(item);
        }
      }
    }
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
    item.quality = item.quality + 1;
  }

  private void decrementQuality(Item item) {
    item.quality = item.quality - 1;
  }
}
