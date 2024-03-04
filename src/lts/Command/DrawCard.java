package lts.Command;

import lts.Cards.Card;
import lts.CommunityCards.Deck;
import lts.Players.Player;

import java.util.List;

public class DrawCard implements Command {
    private final List<Player> players;
    private final Deck deck;

    public DrawCard(List<Player> players, Deck deck){
        this.players = players;
        this.deck = deck;
    }

    /**
     * Draws a card
     * @param i the player who is drawing
     * @return true always
     */
    private boolean draw(int i){
        Card c = deck.Draw();
        players.get(i).getHand().addCard(c);
        return true;
    }

    @Override
    public boolean execute(int i){
        return this.draw(i);
    }

}
