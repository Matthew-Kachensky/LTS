package lts.CommunityCards;

import lts.Cards.Characters.Bosses.Boss;
import lts.Cards.Characters.Bosses.BossFactory;
import lts.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BossDeck {
    private List<Boss> availableBosses;
    private Boss[] fightable;

    public BossDeck(){
        this.availableBosses = new ArrayList<>();
        fightable = new Boss[3];

        // Make the bosses
        for(String s : Constants.bossNames){
            availableBosses.add(BossFactory.createBoss(s));
        }

        // Shuffle the list of bosses
        Collections.shuffle(availableBosses);

        // Randomly choose the first 3 to flip over
        fightable[0] = availableBosses.remove(0);
        fightable[1] = availableBosses.remove(0);
        fightable[2] = availableBosses.remove(0);
    }

    public Boss[] getFightable(){
        return this.fightable;
    }

    /**
     * When a boss is defeated, flip over a new one at the same position
     * @param i the index 1-3 of which boss
     */
    public void newBoss(int i){
        // Make sure there is a boss to get
        if(availableBosses.isEmpty()){
            fightable[i] = null;
            return;
        }
        // Put down a new boss
        fightable[i] = availableBosses.remove(0);
    }

    /**
     * Print the fightable bosses
     */
    public void print(){
        for (int i = 0; i < 3; i++) {
            if(fightable[i] != null){
                System.out.println(i + 1 + ". " + fightable[i].getName());
                fightable[i].getDescription();
            } else {
                System.out.println(i + ". No more bosses");
            }
        }
    }
}
