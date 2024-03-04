package lts.Command;

import lts.Cards.Card;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Player;

import java.util.List;

public class RedrawHand implements Command {
    private final List<Player> players;
    private final Deck deck;
    private final Discard discardPile;

    public RedrawHand(List<Player> players, Deck deck, Discard disc){
        this.players = players;
        this.deck = deck;
        this.discardPile = disc;
    }

    /**
     * Redraw a players hand
     * @param i the player who is redrawing
     * @return true always
     */
    private boolean redraw(int i){
        int size = this.players.get(i).getHand().size();
        for(int ind = 0; ind < size; ind++){
            // Just always remove the first card until the entire hand is gone
            this.discard(this.players.get(i).getHand().playCard(0));
        }

        // Draw 5 new cards
        for(int ind = 0; ind < 5; ind++) {
            Card c = deck.Draw();
            players.get(i).getHand().addCard(c);
        }

        return true;
    }

    private void discard(Card c){
        this.discardPile.discard(c);
    }

    @Override
    public boolean execute(int i){
        return this.redraw(i);
    }
}
