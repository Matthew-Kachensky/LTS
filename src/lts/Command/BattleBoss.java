package lts.Command;

import lts.Cards.Characters.Bosses.Boss;
import lts.CommunityCards.BossDeck;
import lts.Players.Player;
import lts.utils.Constants;
import lts.utils.RegionType;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleBoss implements Command {
    private final BossDeck bosses;
    private final List<Player> players;
    private final Command sacrifice;

    public BattleBoss(List<Player> players, BossDeck deck, Command sac){
        this.bosses = deck;
        this.players = players;
        this.sacrifice = sac;
    }

    /**
     * Fight a boss
     * @param i the person who is fighting a boss
     */
    private boolean fight(int i){
        Player currPlayer = this.players.get(i);
        System.out.println("Choose a boss to battle");
        this.bosses.print();
        System.out.println("0. Choose another option");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();

        if (input == 0) {
            return false;
        }
        RegionType req;
        Boss b;
        do {
            b = this.bosses.getFightable()[input - 1];
            req = b.getRegionReq();
        } while(!currPlayer.getParty().hasRegion(req));

        Random rand = new Random(new Date().getTime());
        int roll = rand.nextInt(6) + rand.nextInt(6) + currPlayer.getBonusRoll();
        if(currPlayer.getParty().getChampion().getRegion() == RegionType.FRELJORD){
            roll += 1;
        }
        System.out.println("Your roll: " + roll);

        // Allow modifying here

        if(roll >= b.getPosRoll()){ // Player beat the boss
            System.out.println("Player won, " + b.getName() + " defeated");
            // Add it to their party
            this.players.get(i).getParty().addBoss(b);
            return true;
        } else if (roll <= b.getNegRoll()){ // Player lost to the boss
            System.out.println("Monster won");
            // Something bad happens. Invoke command sacrifice?
            this.sacrifice.execute(i);
        } else { // It rolled between the values and thus nothing happens
            System.out.println("Both of you stood your ground");
        }
        return false;
    }

    private boolean fight(int i, int boss){
        this.bosses.print();

        Boss b = this.bosses.getFightable()[boss];
        Random rand = new Random(new Date().getTime());
        int roll = rand.nextInt(6) + rand.nextInt(6);
        System.out.println("Your roll: " + roll);

        // Allow modifying here

        if(roll >= b.getPosRoll()){ // Player beat the boss
            System.out.println("Player won, " + b.getName() + " defeated");
            // Add it to their party
            this.players.get(i).getParty().addBoss(b);
            return true;
        } else if (roll <= b.getNegRoll()){ // Player lost to the boss
            System.out.println("Monster won");
            // Something bad happens. Invoke command sacrifice?
            this.sacrifice.execute(i);
        } else { // It rolled between the values and thus nothing happens
            System.out.println("Both of you stood your ground");
        }

        return false;
    }


    @Override
    public boolean execute(int i){
        return this.fight(i);
    }

    public boolean execute(int i, int boss){
        return this.fight(i, boss);
    }
}
