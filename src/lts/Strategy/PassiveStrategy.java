package lts.Strategy;

import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Player;

import java.util.List;

public class PassiveStrategy implements Strategy{

    private String effect;
    private Deck deck;
    private Discard discard;
    private List<Player> players;

    private Player currPlayer;
    private int playerIndex;

    public PassiveStrategy(String effect, int index, Player p, Deck deck){
        this.effect = effect;
        this.playerIndex = index;
        this.currPlayer = p;
        this.deck = deck;
    }

    @Override
    public void execute() {

        switch(this.effect){
            // Person has permanent +1 to all rolls
            case "+1all":
                currPlayer.setBonusRoll(currPlayer.getBonusRoll() + 1);
                break;
            // Play can draw a card for free each turn
            case "DrawOne":
                currPlayer.getHand().addCard(deck.Draw());
                break;
            // Players heroes can no longer be destroyed
            case "noDestroy":
                currPlayer.setDestroyHero(false);
                break;
        }
    }
}
