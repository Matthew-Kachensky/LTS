package lts.Cards.Characters.Hero;

import lts.Cards.Characters.Character;
import lts.Cards.Items.Item;
import lts.utils.CardType;
import lts.utils.Constants;
import lts.utils.RegionType;

import javax.swing.plaf.synth.Region;

public class Hero extends Character {
    protected int minRoll;
    private Item item;
    private String ability;

    private RegionType prevRegion;
    public Hero(String name) {
        super(name, CardType.HERO, true);
        this.ability = Constants.getAbility(name);
        this.minRoll = Constants.getMinRoll(name);
    }

    /**
     * Adds an item to the hero
     * @param region RegionType to change to
     */
    public void changeRegion(RegionType region){
        this.prevRegion = this.region;
        this.region = region;
    }

    /**
     * revert hero region to original region
     */
    public void reset(){
        this.region = Constants.getRegion(name);
        this.minRoll = Constants.getMinRoll(name);
    }

    /**
     * changes hero minRoll value
     * @param i integer value to add to minRoll
     */
    public void changeMinRoll(int i){
        this.minRoll = this.minRoll + i;
    }

    /**
     * Adds an item to the hero
     * @param i the item to add
     */
    public void addItem(Item i){
        this.item = i;
    }

    /**
     * Checks if the hero has an item
     * @return true if they do, false otherwise
     */
    public boolean hasItem(){
        return item != null;
    }

    /**
     * Get the item of the hero
     * @return the item the hero is holding
     */
    public Item getItem(){
        return this.item;
    }

    /**
     * Get the hero's minimum roll
     * @return the minimum roll that needs to be achieved
     */
    public int getMinRoll(){
        return this.minRoll;
    }

    /**
     * Get the hero's ability name
     * @return the hero's ability name as a string
     */
    public String getAbility(){
        return this.ability;
    }

    public void print(){
        if(this.hasItem()) {
            System.out.println(this.name);
            System.out.println("Hero Items: " + this.item.getName());
        } else {
            System.out.println(this.name);
        }
        // Print the description
        System.out.println("Hero Ability: " + this.getDescription());
    }
}