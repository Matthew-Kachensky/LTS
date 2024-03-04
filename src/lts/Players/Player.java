package lts.Players;

import lts.Cards.Card;
import lts.Cards.Characters.Champion.Champion;
import lts.utils.CardType;

public class Player {
    private Hand hand;
    private Party party;
    private String name;

    private int bonusRoll;

    private boolean destroyHero;


    public Player(Champion c, String name){
        this.party = new Party(c);
        this.hand = new Hand();
        this.name = name;
        this.bonusRoll = 0;
        this.destroyHero = true;
    }

    /**
     * Get the name of the player
     * @return the string for the name
     */
    public String getName(){ return this.name;}

    /**
     * Get the hand for the player
     * @return the hand of the player
     */
    public Hand getHand(){
        return this.hand;
    }

    /**
     * Get the party for the player
     * @return the party of the player
     */
    public Party getParty() {
        return party;
    }

    public int getBonusRoll(){
        return this.bonusRoll;
    }

    public void setBonusRoll(int n){
        this.bonusRoll = n;
    }

    public boolean getDestroyHero(){
        return this.destroyHero;
    }

    public void setDestroyHero(boolean b){
        this.destroyHero = b;
    }

}
