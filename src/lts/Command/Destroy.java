package lts.Command;

import lts.Cards.Characters.Hero.Hero;
import lts.CommunityCards.Discard;
import lts.Players.Player;

import java.util.List;
import java.util.Scanner;

public class Destroy implements Command {
    private final List<Player> players;
    private final Discard discard;

    public Destroy(List<Player> players, Discard disc){
        this.players = players;
        this.discard = disc;
    }

    /**
     * Destroys a hero from a players party
     * @param i integer 0 to discard item, 1 to take it
     * @return true always
     */
    private boolean destroy(int i){
        // Show all players
        System.out.println("List of players:");
        int ind = 1;
        for(Player p : players){
            System.out.println(ind++ + ". ");
            p.getParty().printHeroes();
        }

        // Target a player

        System.out.println("Choose a player to destroy:");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();

        // Target a hero
        Player p = players.get(input - 1);

        // Condition from monster
        while(!p.getDestroyHero()){
            System.out.println("Players heroes cannot be destroyed, choose someone else");
            input = in.nextInt();
            p = players.get(input - 1);
        }

        System.out.println("List of Heroes:");
        p.getParty().printHeroes();
        System.out.println("Choose a hero to destroy:");


        Hero h = p.getParty().removeHero(in.nextInt());
        // Destroy it
        if(i == 1 && h.hasItem()){
            System.out.println("Hero was destroyed and the item " + h.getItem().getName() + " has been added to your hand");
            p.getHand().addCard(h.getItem());
            this.discard.discard(p.getParty().removeHero(in.nextInt()));
        }else if (i == 1){
            System.out.println("Hero was destroyed but had no item.");
            this.discard.discard(p.getParty().removeHero(in.nextInt()));
        }else{ //i = 0 so just destroy the hero
            System.out.println("Hero was destroyed");
            this.discard.discard(p.getParty().removeHero(in.nextInt()));
        }

        return true;
    }

    private boolean destroy(int i, int toDestroy, int heroInd){
        // Show all players
        int ind = 1;
        for(Player p : players){
            System.out.println(ind++ + ". " + p.getName());
            p.getParty().printHeroes();
        }

        // Target a player
        int input = toDestroy;

        // Target a hero
        Player p = players.get(input - 1);
        System.out.println("Choose a hero to destroy");
        p.getParty().printHeroes();

        // Destroy it
        this.discard.discard(p.getParty().removeHero(heroInd));
        return true;
    }


    @Override
    public boolean execute(int i){
        return this.destroy(i);
    }

    public boolean execute(int i, int toDestroy, int heroInd){
        return this.destroy(i, toDestroy, heroInd);
    }
}
