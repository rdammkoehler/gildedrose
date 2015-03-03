GildedRose Exercise
===================

* Started with the code from [https://github.com/emilybache/GildedRose-Refactoring-Kata]

* Added rule that quality cannot be less than zero as a pre-processing rule

* Added Characterization Tests until line coverage was 100%

* Added Characterization Tests until branch coverage was 100%

* Refactored

* * for(i) -> for(Item)

* * String Literals -> Constants

* * Minimized QUAlITY_CEILING rule

* * Minimized QUALIY_FLOOR rule

* * Applied Math.min/max to QUALITY_* rules

* * Extracted pastSellBy(Item) method

* * Reversed various bits of logic to avoid negation expressions (Readability)

* * Minimized SULFUROUS Item rule

* * Extracted adjustQuality(Item) method

* * Extracted makePostAgingQualityAdjustment(Item) method

* * Rename in public interface updateQuality() -> updateItems() [Because updateQuality is only part of the activity]

* * Formatted and Cleaned Up code

* * Added Conjured Items Double Decay Rule

* * Refactor: Method Extract: Logic and Calculation

* * Enable Sonarqube

* * Make WORTHLESS constant = QUALITY_FLOOR

* * Configured JaCoCo

* * Radical Modification (remove the brittle bits as much as possible)

* * * Created Sub-Classes for each kind of object that wrap Item

* * * Remove Item from state of sub-class

* * * Collapse sub-classes into a parameterized class

* * * Switch from chained-if to Map lookup for ItemModifier

* Added Travis

* Added rule about Sulfurous (Legendary) Item always having a quality of 80

* Moved Map initilization from instance initializer to constructor

* Played with Sonarqube rules

Why Not Have Sub-Classes and Specialization?
============================================

Initially I think the sub-classes approach is OK as long as we know we will never have a broad range of specializations. 
But as soon as we consider the idea that new items could be added that also have specialization we need to look at making 
the basic design of the system more flexible. The hard part is balancing over-design with getting the code delivered. I 
chose not to make any significant changes until after I had delivered the desired feature to the business. I incurred 
tech-debt if you will, in the idea that I knew the design was still brittle but at least more easily modifiable when I 
said "ship it!".
 
Once the feature is out I can come back and make design modifications and pay down my tech-debt. I chose to use the ItemModifier
because as a parameterized class I've narrowed the 'new item' problem to classifying that item as one of the existing 'kinds'
of item or necessitating a new 'kind' [Where 'kind' is just a configuration of the ItemModifier]. In the case of Backstage
Passes I was forced to override the adjustQuality() method to handle this particular case. If another 'kind' of item with
a similar rule was added to the system I could then take the time to enhance that design to be still more flexible. This
extension would most likely be a full-on extension of ItemModifier. But until that requirement exists there is no need to 
'fix' the design; it isn't yet insufficient. 
  