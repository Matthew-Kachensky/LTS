package lts.Command;

import lts.CommunityCards.Discard;
import lts.Players.Party;
import lts.Players.Player;

import java.util.List;
import java.util.Scanner;

public class Sacrifice implements Command {
    private final List<Player> players;
    private final Discard discard;

    public Sacrifice(List<Player> players, Discard disc){
        this.players = players;
        this.discard = disc;
    }

    /**
     * Make player i sacrifice a hero
     * @param i the player who needs to sacrifice
     * @return true always
     */
    private boolean sacrifice(int i){
        // Print only their heros
        Party p = this.players.get(i).getParty();

        if (p.getHeros().isEmpty()){
            System.out.println("Nobody in the party to sacrifice. Moving on...");
            return true;
        }

        p.printHeroes();

        // Make them choose which one
        System.out.println("Choose a hero to sacrifice");
        Scanner in = new Scanner(System.in);

        // Discard the hero
        this.discard.discard(p.removeHero(in.nextInt() - 1));
        return true;
    }

    /**
     * Make player i sacrifice a hero
     * @param i the player who needs to sacrifice
     * @param sac the hero to be sacrificed
     * @return true always
     */
    private boolean sacrifice(int i, int sac){
        // Print only their heros
        Party p = this.players.get(i).getParty();
        p.printHeroes();

        // Discard the hero
        this.discard.discard(p.removeHero(sac));
        return true;
    }

    @Override
    public boolean execute(int i){
        return this.sacrifice(i);
    }

    public boolean execute(int i, int sac){
        return this.sacrifice(i, sac);
    }
}
