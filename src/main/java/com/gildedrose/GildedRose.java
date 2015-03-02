package com.gildedrose;

import static java.lang.Math.max;
import static java.lang.Math.min;

class GildedRose {

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
      ItemModifier modifier = wrap(item);
      modifier.step();
    }
  }

  private ItemModifier wrap(Item item) {
    if (AGED_BRIE.equals(item.name)) {
      return new AccruingItem(item);
    } else if (BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT.equals(item.name)) {
      return new AsomtoticItem(item);
    } else if (SULFURAS_HAND_OF_RAGNAROS.equals(item.name)) {
      return new AgelessItem(item);
    } else if (CONJURED_MANA_CAKE.equals(item.name)) {
      return new DoubleDecayingItem(item);
    } else {
      return new DecayingItem(item);
    }
  }

  abstract class ItemModifier {
    protected static final int DEFAULT_QUALITY_INCREASE_AMOUNT = 1;
    protected static final int DEFAULT_QUALITY_DECAY_AMOUNT = -1;
    protected static final int QUALITY_FLOOR = 0;
    protected static final int WORTHLESS = QUALITY_FLOOR;
    protected static final int QUALITY_CEILING = 50;
    private Item item;
    private Integer qualityAdjustment = DEFAULT_QUALITY_DECAY_AMOUNT;
    private Integer sellInAdjustment = -1;

    public ItemModifier(Item item, Integer qualityAdjustment, Integer sellInAdjustment) {
      this.item = item;
      this.qualityAdjustment = qualityAdjustment;
      this.sellInAdjustment = sellInAdjustment;
    }

    protected boolean pastSellBy() {
      return item.sellIn < 0;
    }

    protected void adjustQuality() {
      if (qualityAdjustment < 0) {
        item.quality = max(QUALITY_FLOOR, item.quality + qualityAdjustment);
      } else {
        item.quality = min(QUALITY_CEILING, item.quality + qualityAdjustment);
      }
    }

    protected void adjustSellIn() {
      item.sellIn = item.sellIn + sellInAdjustment;
    }

    public void step() {
      adjustQuality();
      adjustSellIn();
      if (pastSellBy()) {
        adjustQuality();
      }
    }
  }

  class DecayingItem extends ItemModifier {
    DecayingItem(Item item) {
      super(item, DEFAULT_QUALITY_DECAY_AMOUNT, -1);
    }

  }

  class DoubleDecayingItem extends ItemModifier {
    public DoubleDecayingItem(Item item) {
      super(item, 2 * DEFAULT_QUALITY_DECAY_AMOUNT, -1);
    }

  }

  class AccruingItem extends ItemModifier {
    AccruingItem(Item item) {
      super(item, DEFAULT_QUALITY_INCREASE_AMOUNT, -1);
    }

  }

  class AsomtoticItem extends ItemModifier {
    AsomtoticItem(Item item) {
      super(item, DEFAULT_QUALITY_INCREASE_AMOUNT, -1);
    }

    public void step() {
      adjustQuality();
      if (super.item.sellIn < 11) {
        adjustQuality();
      }
      if (super.item.sellIn < 6) {
        adjustQuality();
      }
      adjustSellIn();
      if (pastSellBy()) {
        super.item.quality = 0;
      }
    }
  }

  class AgelessItem extends ItemModifier {
    AgelessItem(Item item) {
      super(item, 0, 0);
    }
  }
}
