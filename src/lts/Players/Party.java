package lts.Players;



import lts.Cards.Characters.Champion.Champion;
import lts.Cards.Characters.Bosses.Boss;
import lts.Cards.Characters.Hero.Hero;
import lts.utils.RegionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Party {
    private Champion champion;
    private List<Boss> bosses;
    private List<Hero> heros;

    public Party(){
        // Super makes the list of heros
        super();
        this.bosses = new ArrayList<>();
        this.heros = new ArrayList<>();
    }

    public Party(Champion c){
        super();
        this.champion = c;
        this.bosses = new ArrayList<>();
        this.heros = new ArrayList<>();
    }

    /**
     * Checks if a players party has a region
     * @param r the region to check for
     * @return true if they have it or there is no requirement, false otherwise
     */
    public boolean hasRegion(RegionType r){
        // Edge case for if the boss does not have a region requirement
        if(r == RegionType.NONE){
            return true;
        }
        for(Hero h : this.heros){
            if(h.getRegion() == r){
                return true;
            }
        }
        return false;
    }
    public Champion getChampion(){
        return this.champion;
    }

    public List<Boss> getBosses(){
        return this.bosses;
    }

    public List<Hero> getHeros(){
        return this.heros;
    }

    public Hero getHero(int i){
        return this.heros.get(i);
    }

    public int getHeroIndex(Hero h){
        for(Hero hero: this.heros){
            if (hero.equals(h)) {
                return this.heros.indexOf(h);
            }
        }
        return -1;
    }

    public Hero removeHero(int i){
        return this.heros.remove(i);
    }




    /**
     * Get a specific hero
     * @param i the index of the hero
     * @return the hero
     */
    public Hero getCard(int i){
        return this.heros.get(i);
    }

    /**
     * Adds a hero to the list of heros
     * @param h the hero to add
     */
    public void addHero(Hero h){
        this.heros.add(h);
    }

    public void addBoss(Boss b){
        this.bosses.add(b);
    }

    /**
     * Prints only the heros of the party
     */
    public void printHeroes(){

        if (this.heros.isEmpty()){
            System.out.print( "Party is empty...");
            return;
        }

        int i = 1;
        for(Hero h : this.heros) {
            System.out.print( String.valueOf(i) + " (Hero). ");
            h.print();
            i = i + 1;
        }
        System.out.println();
    }

    public void printHeroesNoNums(){

        if (this.heros.isEmpty()){
            System.out.print( "Party is empty...");
            return;
        }

        int i = 1;
        for(Hero h : this.heros) {
            System.out.print( "Hero: ");
            h.print();
            i = i + 1;
        }
        System.out.println();
    }


    /**
     * Prints the party of the given player
     */
    public void print(){
        // Print the champion
        System.out.println("Champion: " + this.champion.getName());
        System.out.println("Champion Passive: " + this.champion.getDescription());
        int i = 1;

        // Print the list of heros and their items
        // As well what their rolling ability does
        this.printHeroes();

        i = 1;
        // Print any bosses the player has
        // And their passive is
        for(Boss b : this.bosses){
            System.out.println(i++ + ". " + b.getName());
            System.out.println("Boss Passive: " + b.getDescription());
            i = i+1;
        }
    }

    public void printNoNums() {
        // Print the champion
        System.out.println("Champion: " + this.champion.getName());
        System.out.println("Champion Passive: " + this.champion.getDescription());

        // Print the list of heros and their items
        // As well what their rolling ability does
        this.printHeroesNoNums();

        // Print any bosses the player has
        // And their passive is
        for (Boss b : this.bosses) {
            System.out.println("Boss: " + b.getName());
            System.out.println("Boss Passive: " + b.getDescription());
        }
    }
}
