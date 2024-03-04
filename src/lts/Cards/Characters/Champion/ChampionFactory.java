package lts.Cards.Characters.Champion;

import lts.utils.CardType;
import lts.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class ChampionFactory {
    private static Map<String, Champion> champions = new HashMap<>();

    public static Champion createChampion(String name) {
        // Use the champion's name as a key to ensure a singleton instance for each champion
        return champions.computeIfAbsent(name, k -> new Champion(name));
    }

}
