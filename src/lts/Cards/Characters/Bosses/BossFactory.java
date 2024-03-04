package lts.Cards.Characters.Bosses;


import lts.Cards.Characters.Champion.Champion;
import lts.utils.CardType;
import lts.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class BossFactory {
    private static Map<String, Boss> bosses = new HashMap<>();

    public static Boss createBoss(String name) {
        // Use the boss's name as a key to ensure a singleton instance for each Boss
        return bosses.computeIfAbsent(name, k -> new Boss(name));
    }

}
