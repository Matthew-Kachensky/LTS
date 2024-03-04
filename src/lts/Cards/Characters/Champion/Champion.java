package lts.Cards.Characters.Champion;

import lts.Cards.Characters.Character;
import lts.utils.CardType;
import lts.utils.Constants;

public class Champion extends Character {
    private String passive;

    public Champion(String name) {
        super(name, CardType.CHAMPION, false);
        this.passive = Constants.getAbility(name);
    }

    // passive?
    public String getPassive() {
        return this.passive;
    }

}