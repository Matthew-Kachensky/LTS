package lts.Cards.Characters.Bosses;

import lts.Cards.Characters.Character;
import lts.utils.CardType;
import lts.utils.Constants;
import lts.utils.RegionType;

import javax.swing.plaf.synth.Region;

public class Boss extends Character {
    private int negRoll;
    private int posRoll;
    private String passive;
    private int minHeroes;
    private RegionType regionReq;

    public Boss(String name) {
        super(name, CardType.BOSS, false);
        this.passive = Constants.getAbility(name);
        this.negRoll = Constants.getNegRoll(name);
        this.posRoll = Constants.getPosRoll(name);
        this.minHeroes = Constants.getMinHeroes(name);
        this.regionReq = Constants.getRegion(name);
    }

    public int getNegRoll(){
        return this.negRoll;
    }
    public int getPosRoll(){
        return this.posRoll;
    }

    public RegionType getRegionReq(){
        return this.regionReq;
    }

}