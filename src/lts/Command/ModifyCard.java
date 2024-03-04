package lts.Command;


import lts.Cards.Modifiers.Mod;
import lts.Players.Hand;
import lts.Players.Player;

import java.util.List;
import java.util.Scanner;

public class ModifyCard implements Command {
    private final List<Player> players;


    public ModifyCard(List<Player> players){
        this.players = players;
    }

    /**
     * Modify a card
     * @param i the person who is modifying
     * @return true if roll was modified, false otherwise
     */
    private boolean modify(int i){
        Hand h = this.players.get(i).getHand();
        h.printMod();
        List<Mod> ml = h.getMods();
        //System.out.println("Choose which mod to use");
        //Scanner in = new Scanner(System.in);
        //Mod m = ml.get(in.nextInt());
        // Create class that modifies the value?
        //h.removeCard(m);
        return true;
    }



    @Override
    public boolean execute(int i){
        return this.modify(i);
    }
}
