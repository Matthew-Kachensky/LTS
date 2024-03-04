package lts.Command;

import lts.Cards.Card;
import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Items.Item;
import lts.Cards.Spells.Spell;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Hand;
import lts.Players.Party;
import lts.Players.Player;
import lts.Strategy.ActiveStrategy;
import lts.Strategy.ItemStrategy;
import lts.Strategy.Strategy;
import lts.utils.RegionType;

import java.util.List;
import java.util.Scanner;

public class PlayCard implements Command {
    private final List<Player> players;
    private final Discard discardPile;
    private final Command challenge;

    private final Deck deck;

    private Strategy strategy;

    public PlayCard(List<Player> players, Deck deck, Discard disc, Command challenge) {
        this.players = players;
        this.deck = deck;
        this.discardPile = disc;
        this.challenge = challenge;
    }

    /**
     * Play a card
     * 
     * @param i the player who is playing a card
     * @return true if the player wants
     */
    private boolean play(int i) {
        System.out.println("Your current Hand contains the following cards: ");
        this.players.get(i).getHand().print();
        System.out.println("0. Choose another option");
        System.out.println("Enter the number associated with the card you want to play. (Heroes, Spells, and Items only)");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        if (input == 0) {
            return false;
        }

        Player currentPlayer = this.players.get(i);
        Hand currentHand = currentPlayer.getHand();
        Party currentParty = currentPlayer.getParty();
        Card c = currentHand.playCard(input - 1);

        if(this.challenge.execute(i)){
            return true;
        }

        while (true) {
            switch (c.getCardType()) {
                case HERO -> {
                    this.strategy = new ActiveStrategy(((Hero) c).getAbility(),this.deck, this.discardPile, this.players, i);

                    currentParty.addHero((Hero) c);

                    RollHero rollHero = new RollHero(this.players, this.deck, this.discardPile);
                    Boolean rollHit = rollHero.execute(i, currentParty.getHeroIndex((Hero) c));
                    System.out.print("Rolling for hero ability...");

                    if(rollHit){
                        System.out.println("Hero ability roll was hit!");
                        System.out.println();
                        this.strategy.execute();
                    }else {
                        System.out.println("Hero ability was missed.");
                        System.out.println();
                    }

                    return true;
                }
                case ITEM -> {
                    currentParty.print();
                    System.out.println("Choose a hero that does not have an item:");
                    int index = in.nextInt() - 1;
                    while (currentParty.getHeros().get(index).hasItem()) {
                        System.out.println("Choose a different hero:");
                        index = in.nextInt();
                    }
                    Hero h = currentParty.getHero(index);
                    this.strategy = new ItemStrategy(h, h.getAbility());
                    this.strategy.execute();

                    Hero hero = currentParty.getHeros().get(index);
                    Item item = (Item) c;
                    hero.addItem(item);
                    return true;
                } // End item
                case SPELL -> {
                    this.strategy = new ActiveStrategy(((Spell) c).getEffect(),this.deck, this.discardPile, this.players, i);
                    this.strategy.execute();
                    if(currentPlayer.getParty().getChampion().getRegion() == RegionType.IONIA){
                        DrawCard dc = new DrawCard(this.players, this.deck);
                        dc.execute(i);
                    }
                    return true;
                }
                default -> {
                    System.out.println("Choose a valid card");
                    return true;
                }
            }
        }
    }

    private boolean play(int i, int card) {
        this.players.get(i).getHand().print();
        System.out.println("0. Choose another option");
        Card c = this.players.get(i).getHand().playCard(card - 1);

        while (true) {
            switch (c.getCardType()) {
                case HERO -> {
                    this.players.get(i).getParty().addHero((Hero) c);
                    return true;
                }
                case ITEM -> {
                    // this.strategy = new ItemStrategy(((Hero) c), ((Hero) c).getAbility());
                    // this.strategy.execute(i);

                    this.players.get(i).getParty().print();
                    int index = 0;

                    Hero hero = this.players.get(i).getParty().getHeros().get(index);
                    Item item = (Item) c;
                    hero.addItem(item);
                    return true;
                } // End item
                // case SPELL -> ((Spell) c).effect();
                // this.discard(c);
                // return true;

                default -> {
                    System.out.println("Choose a valid card");
                }
            }
        }
    }

    private void discard(Card c) {
        this.discardPile.discard(c);
    }

    @Override
    public boolean execute(int i) {
        return this.play(i);
    }

    public boolean execute(int i, int card) {
        return this.play(i, card);
    }
}
