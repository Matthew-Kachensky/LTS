package lts.Cards.Items;

import lts.Cards.Characters.Character;
import lts.Cards.Card;
import lts.utils.CardType;
import lts.utils.Constants;

public class Item extends Card{
    private String effect;
    public Item(String name){
        super(name,Constants.descriptions.get(name), CardType.ITEM, true);
        this.effect = Constants.getAbility(name);
    }
    public String getEffect(){
        return this.effect;
    }
}