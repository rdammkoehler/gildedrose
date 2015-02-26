package com.gildedrose;

class GildedRose {
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
      if (!item.name.equals("Aged Brie") && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        if (item.quality > 0) {
          if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            item.quality = item.quality - 1;
          }
        }
      } else {
        if (item.quality < 50) {
          item.quality = item.quality + 1;

          if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.sellIn < 11) {
              if (item.quality < 50) {
                item.quality = item.quality + 1;
              }
            }

            if (item.sellIn < 6) {
              if (item.quality < 50) {
                item.quality = item.quality + 1;
              }
            }
          }
        }
      }

      if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
        item.sellIn = item.sellIn - 1;
      }

      if (item.sellIn < 0) {
        if (!item.name.equals("Aged Brie")) {
          if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.quality > 0) {
              if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                item.quality = item.quality - 1;
              }
            }
          } else {
            item.quality = item.quality - item.quality;
          }
        } else {
          if (item.quality < 50) {
            item.quality = item.quality + 1;
          }
        }
      }
    }
  }
}
