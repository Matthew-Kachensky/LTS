package lts.Players;

import lts.Cards.Card;
import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Items.Item;
import lts.Cards.Modifiers.Mod;
import lts.Cards.Spells.Spell;
import lts.utils.CardType;

import java.util.*;

public class Hand {
    private final List<Card> allCards;
    private final List<Mod> mods;
    private final List<Hero> heros;
    private final List<Spell> spells;
    private final List<Item> items;

    private int challenges;

    public Hand(){
        this.mods = new ArrayList<>();
        this.spells = new ArrayList<>();
        this.items = new ArrayList<>();
        this.heros = new ArrayList<>();
        this.allCards = new ArrayList<>();
    }

    public List<Card> getAllCards(){
        return this.allCards;
    }

    public List<Hero> getHeros(){
        return this.heros;
    }

    public int getChallenges(){
        return this.challenges;
    }

    public boolean canChallenge(){ return this.challenges > 0; }

    public boolean canMod(){ return this.mods.size() > 0; }

    public List<Mod> getMods() {
        return this.mods;
    }

    public List<Spell> getSpells(){
        return this.spells;
    }

    public List<Item> getItems(){
        return this.items;
    }

    public int size(){
        return this.allCards.size();
    }

    /**
     * Adds a card to a players hand
     * @param c the card to be added
     */
    public void addCard(Card c){
        this.allCards.add(c);

        switch(c.getCardType()){
            case SPELL -> this.spells.add((Spell) c);
            case ITEM -> this.items.add((Item) c);
            case HERO -> this.heros.add((Hero) c);
            case MOD -> this.mods.add((Mod) c);
            case CHALLENGE -> this.challenges += 1;
        }
    }


    /**
     * When the player wants to play a certain card, we remove it form the their hand and add it to the party.
     * In this case, the returned card will be added to the party
     * @param i the index of the card selected
     * @return the card to be added to the party
     */
    public Card playCard(int i){
        Card c = this.allCards.remove(i);
        this.findRemove(c);
        return c;
    }

    public void removeCard(Card c){
        this.allCards.remove(c);
        this.findRemove(c);
    }

    /**
     * Given a specific cards, removes it from the internal list
     * @param c the card to remove
     */
    private void findRemove(Card c){
        switch(c.getCardType()){
            case SPELL -> this.spells.remove((Spell) c);
            case ITEM -> this.items.remove((Item) c);
            case HERO -> this.heros.remove((Hero) c);
            case MOD -> this.mods.remove((Mod) c);
            case CHALLENGE -> this.challenges -= 1;
        }
    }
    /**
     * When someone is stealing from the hand of the player
     * @param i the index of the card
     * @return the card stolen
     */
    public Card stealCard(int i){
        // Shuffle the cards in the players hand
        Collections.shuffle(allCards);

        // Remove and return the card from the players hand
        Card c = this.allCards.remove(i);
        this.findRemove(c);
        return c;
    }

    public Card stealCard(){
        // Shuffle the cards in the players hand
        Collections.shuffle(allCards);
        Random rand = new Random(new Date().getTime());
        int i = rand.nextInt(this.allCards.size());
        // Remove and return the card from the players hand
        Card c = this.allCards.remove(i);
        this.findRemove(c);
        return c;
    }

    public void printMod(){
        int i = 0;
        for(Mod m : this.mods){
            System.out.print(i++ + ". ");
            m.print();
        }
    }

    /**
     * Prints out the current hand
     * ex.
     * 1. Item Dorans ring
     * 2. Hero Anivia
     * ...
     */
    public void print(){
        int i = 1;
        for(Card c : this.allCards){
            System.out.println(i + ". " + c.getCardType().getType() + ": " + c.getName());
            System.out.println("Card Description: " + c.getDescription());
            i++;
        }


        /*

        Better looking output prototype, incomplete

        String format = "%24s%24s%24s%24s";
        int max = max(this.heros.size(), this.challenges, this.mods.size(), this.items.size(), this.spells.size());

        System.out.format(format, "Heroes", "Items", "Spells", "Modifications", "Challenges");
        for(int i = 0; i < max; i++){
            String[] s = new String[5];
            try{
                s[0] = this.heros.get(i).getName();
            } catch (IndexOutOfBoundsException i){
                s[0] = "";
            }

            try{
                //
                // s[1] = this.items.get(i);
                s[1] = "";
            } catch (IndexOutOfBoundsException i){
                s[1] = "";
            }

            try{

            } catch (IndexOutOfBoundsException i){
                s[2] = "";
            }

            System.out.format(format, s[0], s[1], s[2], s[3], s[4], s[5]);
        }

         */
    }

}
