package lts.Cards.Modifiers;

import lts.Cards.Card;
import lts.utils.CardType;
import lts.utils.Constants;

public class Mod extends Card{
    private int buffValue;
    private int nerfValue;
    public Mod(String name){
        super(name,
                "Adds " + String.valueOf(Constants.buffs.get(name))+ " or subtracts "
                        + String.valueOf(Constants.nerfs.get(name)) + " from a roll.", CardType.MOD, false);
        this.buffValue = Constants.buffs.get(name);
        this.nerfValue = Constants.nerfs.get(name);
    }

    public int getBuffValue() {
        return this.buffValue;
    }
    public int getNerfValue(){
        return this.nerfValue;
    }

    public void print(){
        System.out.println(this.name);
    }
}