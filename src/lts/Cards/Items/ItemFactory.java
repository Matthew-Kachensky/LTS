package lts.Cards.Items;

import lts.Cards.Characters.Hero.Hero;
import lts.utils.CardType;
import lts.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    public static Item createItem(String name) {
        return new Item(name);
    }
}
