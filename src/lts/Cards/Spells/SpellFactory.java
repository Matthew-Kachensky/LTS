package lts.Cards.Spells;

public class SpellFactory {
    public static Spell createSpell(String name) {
        return new Spell(name);
    }
}
