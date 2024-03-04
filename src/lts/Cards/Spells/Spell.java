package lts.Cards.Spells;

import lts.Cards.Card;
import lts.utils.CardType;
import lts.utils.Constants;

public class Spell extends Card{
    private String effect;
    public Spell(String name){
        super(name, Constants.descriptions.get(name), CardType.SPELL, true);
        this.effect = Constants.abilities.get(name);
    }

    public String getEffect(){
        return this.effect;
    }

    public void print(){
        System.out.println(this.name);
        System.out.println(this.getDescription());
    }

}