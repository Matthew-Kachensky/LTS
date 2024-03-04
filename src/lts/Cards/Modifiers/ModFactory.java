package lts.Cards.Modifiers;

import lts.Cards.Items.Item;
import lts.utils.Constants;

public class ModFactory {
    public static Mod createMod(String name) {
        return new Mod(name);
    }
}
