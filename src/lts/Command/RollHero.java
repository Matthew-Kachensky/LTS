package lts.Command;

import lts.Cards.Characters.Hero.Hero;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Party;
import lts.Players.Player;
import lts.Strategy.ActiveStrategy;
import lts.Strategy.Strategy;
import lts.utils.RegionType;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RollHero implements Command {
    private final List<Player> players;
    private Strategy strategy;
    private final Deck deck;
    private final Discard discard;

    public RollHero(List<Player> players, Deck deck, Discard discard){
        this.players = players;
        this.deck = deck;
        this.discard = discard;
    }

    /**
     * Roll for a hero
     * @param i the player that is rolling
     * @return true if roll was hit, false otherwise
     */
    private boolean roll(int i){
        // Get the players party
        Party p = this.players.get(i).getParty();
        Player currPlayer = this.players.get(i);

        if (p.getHeros().isEmpty()){
            System.out.println("Player " + currPlayer.getName() + " does not have any heroes. Choose a different action.");
            return false;
        }

        // Print it
        System.out.println("Party: ");
        p.print();

        // Make them choose which one they are rolling for
        System.out.println("Choose a hero to roll");
        Scanner in = new Scanner(System.in);

        int input = in.nextInt() - 1;

        // Roll a pair of die for them
        Random rand = new Random();
        int roll = rand.nextInt(6) + rand.nextInt(6) + currPlayer.getBonusRoll();
        if(currPlayer.getParty().getChampion().getRegion() == RegionType.BANDLE || currPlayer.getParty().getChampion().getRegion() == RegionType.DEMACIA){
            roll += 1;
        }
        // Allow modifying here
        this.strategy = new ActiveStrategy(p.getHero(input).getAbility(),this.deck, this.discard, this.players, i);

        // If they hit the roll
        if(p.getHero(input).getMinRoll() > roll){
            System.out.println("Hero ability roll was hit!");
            System.out.println();
            this.strategy.execute();
            return true;
        }
        System.out.println("Hero ability was missed.");
        System.out.println();
        // Nothing happens otherwise
        return true;
    }

    /**
     * Rolling for a specific hero
     * @param i the player rolling
     * @param hero the hero of choice
     * @return true if roll was hit, false otherwise
     */
    private boolean roll(int i, int hero){
        Party p = this.players.get(i).getParty();

        if (p.getHeros().isEmpty()){
            System.out.println("Player " + this.players.get(i).getName() + " does not have any heroes. Choose a different action.");
            return false;
        }

        // Roll a pair of die for them
        Random rand = new Random();
        int roll = rand.nextInt(6) + rand.nextInt(6);

        // Allow modifying here

        // If they hit the roll
        if(p.getHero(hero).getMinRoll() > roll){
//            p.getHero(input).effect();
            return true;
        }
        // Nothing otherwise
        return false;
    }


    @Override
    public boolean execute(int i){
        return this.roll(i);
    }

    public boolean execute(int i, int hero){
        return this.roll(i, hero);
    }
}
