package lts.Command;

import lts.Cards.Challenges.Challenge;
import lts.Players.Player;
import lts.utils.RegionType;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ChallengeCard implements Command {
    private List<Player> players;
    private Command modify;

    public ChallengeCard(List<Player> players, Command modify){
        this.players = players;
        this.modify = modify;
    }

    /**
     * Ask the lobby if anyone wants to challenge playing a card
     * @param i the player that might get challenged
     * @return true if challenged and challenged lost, false if not challenged or won at least 1 challenge
     */
    private boolean challenge(int i){
        Player challenged = this.players.get(i);
        for(Player p : this.players){
            if(p == challenged){
                continue;
            }
            if(p.getHand().canChallenge()){
                System.out.println(p.getName() + " do you want to challenge? [y/n]");
                Scanner in = new Scanner(System.in);
                switch(in.nextLine().toLowerCase()){
                    case "y":
                    case "yes":
                        p.getHand().removeCard(Challenge.getInstance());
                        if(this.fight(p, challenged)){
                            return true;
                        }
                        // House rule: allow a card to only be challenged once
//                        else {
//                            return false;
//                        }
                        break;
                    case "n":
                    case "no":
                        continue;
                    default:
                }
            }
        }
        return false;
    }

    /**
     * if someone wants to challenge
     * @param challenger the person who played the challenge
     * @param challenged the person who is being challenged
     * @return true if challenger won, false otherwise
     */
    private boolean fight(Player challenger, Player challenged){
        Random roll = new Random(new Date().getTime());

        // Roll for challenger
        int challengerRoll = roll.nextInt(6) + roll.nextInt(6);
        if(challenger.getParty().getChampion().getRegion() == RegionType.NOXUS){
            challengerRoll += 2;
        }
        System.out.println(challenger.getName() + " rolled a " + challengerRoll);
        // Check for any modifications
        this.modify.execute(this.players.indexOf(challenger));

        // Roll for challenged
        int challengedRoll = roll.nextInt(6) + roll.nextInt(6) + challenged.getBonusRoll();
        if(challenged.getParty().getChampion().getRegion() == RegionType.NOXUS){
            challengedRoll += 2;
        }
        System.out.println(challenged.getName() + " rolled a " + challengedRoll);
        // Check for any modifications
        this.modify.execute(this.players.indexOf(challenged));

        if(challengerRoll >= challengedRoll){
            return true;
        }
        return false;
    }

    @Override
    public boolean execute(int i){
        return this.challenge(i);
    }
}
