package com.gildedrose;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;
import java.util.Map;

class GildedRose {
  private static final int DEFAULT_QUALITY_INCREASE_AMOUNT = 1;
  private static final int DEFAULT_QUALITY_DECAY_AMOUNT = -1;
  private static final int QUALITY_FLOOR = 0;
  private static final int QUALITY_CEILING = 50;

  private static final ItemModifier AGELESS_ITEM_MODIFIER = new ItemModifier(0, 0);
  private static final ItemModifier BACKSTAGE_PASS_ITEM_MODIFIER = new ItemModifier(DEFAULT_QUALITY_INCREASE_AMOUNT, -1) {
    public void step(Item item) {
      adjustQuality(item);
      if (item.sellIn < 11) {
        adjustQuality(item);
      }
      if (item.sellIn < 6) {
        adjustQuality(item);
      }
      adjustSellIn(item);
      if (pastSellBy(item)) {
        item.quality = 0;
      }
    }
  };
  private static final ItemModifier BRIE_ITEM_MODIFIER = new ItemModifier(DEFAULT_QUALITY_INCREASE_AMOUNT, -1);
  private static final ItemModifier DECAYING_ITEM_MODIFIER = new ItemModifier(DEFAULT_QUALITY_DECAY_AMOUNT, -1);
  private static final ItemModifier DOUBLE_DECAYING_ITEM_MODIFIER = new ItemModifier(2 * DEFAULT_QUALITY_DECAY_AMOUNT,
      -1);

  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
  private static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
  Item[] items;
  private static final Map<String, ItemModifier> MODIFIER_MAP = new HashMap<String, ItemModifier>() {
    private static final long serialVersionUID = -8102026157041850052L;
    {
      put(AGED_BRIE, BRIE_ITEM_MODIFIER);
      put(BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT, BACKSTAGE_PASS_ITEM_MODIFIER);
      put(SULFURAS_HAND_OF_RAGNAROS, AGELESS_ITEM_MODIFIER);
      put(CONJURED_MANA_CAKE, DOUBLE_DECAYING_ITEM_MODIFIER);
    }

    @Override
    public ItemModifier get(Object key) {
      ItemModifier modifier = DECAYING_ITEM_MODIFIER;
      if (containsKey(key)) {
        modifier = super.get(key);
      }
      return modifier;
    }
  };

  public GildedRose(Item[] items) {
    this.items = items;
    for (Item item : items) {
      item.quality = (item.quality < 0) ? 0 : item.quality;
    }

  }

  public void updateItems() {
    for (Item item : items) {
      ItemModifier modifier = MODIFIER_MAP.get(item.name);
      modifier.step(item);
    }
  }

  static class ItemModifier {
    private Integer qualityAdjustment = DEFAULT_QUALITY_DECAY_AMOUNT;
    private Integer sellInAdjustment = -1;

    public ItemModifier(Integer qualityAdjustment, Integer sellInAdjustment) {
      this.qualityAdjustment = qualityAdjustment;
      this.sellInAdjustment = sellInAdjustment;
    }

    protected boolean pastSellBy(Item item) {
      return item.sellIn < 0;
    }

    protected void adjustQuality(Item item) {
      if (qualityAdjustment < 0) {
        item.quality = max(QUALITY_FLOOR, item.quality + qualityAdjustment);
      } else {
        item.quality = min(QUALITY_CEILING, item.quality + qualityAdjustment);
      }
    }

    protected void adjustSellIn(Item item) {
      item.sellIn = item.sellIn + sellInAdjustment;
    }

    public void step(Item item) {
      adjustQuality(item);
      adjustSellIn(item);
      if (pastSellBy(item)) {
        adjustQuality(item);
      }
    }
  }
}
