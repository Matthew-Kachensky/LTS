package lts.Cards.Characters.Hero;

import lts.Cards.Card;
import lts.Cards.Characters.Champion.Champion;
import lts.utils.CardType;
import lts.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class HeroFactory {
    private static Map<String, Hero> heroes = new HashMap<>();

    public static Hero createHero(String name) {
        // Use the hero's name as a key to ensure a singleton instance for each hero
        return heroes.computeIfAbsent(name, k -> new Hero(name));
    }
}
