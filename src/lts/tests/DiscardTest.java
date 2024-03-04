package lts.tests;

import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Characters.Hero.HeroFactory;
import lts.Cards.Items.Item;
import lts.Cards.Items.ItemFactory;
import lts.CommunityCards.Discard;
import lts.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTest {

    @Test
    void discardTest() {
        Constants.updateData();
        Item i = ItemFactory.createItem("Banner of Command");
        Hero h = HeroFactory.createHero("Lux");

        h.addItem(i);

        Discard discard = new Discard();
        discard.discard(h);

        Assertions.assertEquals(i, discard.getCard(0));
        // discarding a card removes it from the discard pile, so we use index 0 again
        Assertions.assertEquals(h, discard.getCard(0));
    }

    @Test
    void checkItemTest() {
        Constants.updateData();
        Item i = ItemFactory.createItem("Banner of Command");
        Hero h = HeroFactory.createHero("Lux");

        h.addItem(i);

        Discard discard = new Discard();
        discard.checkItem(h);

        assertNull(h.getItem());
        Assertions.assertEquals(i, discard.getCard(0));
    }

    @Test
    void getCardTest() {

    }
}