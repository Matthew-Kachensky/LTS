package lts.Strategy;

import lts.Cards.Card;
import lts.Cards.Characters.Hero.Hero;
import lts.Command.Command;
import lts.Command.Destroy;
import lts.Command.DrawCard;
import lts.Command.Destroy;
import lts.Command.Sacrifice;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Hand;
import lts.Players.Party;
import lts.Players.Player;
import lts.utils.CardType;
import lts.utils.RegionType;

import java.util.List;
import java.util.Scanner;

// for spells and hero effects
public class ActiveStrategy implements Strategy{
    private String effect;
    private Deck deck;
    private Discard discard;
    private List<Player> players;
    private int playerIndex;

    public ActiveStrategy(String effect, Deck deck, Discard discard, List<Player> players, int playerIndex) {
        this.deck = deck;
        this.effect = effect;
        this.discard = discard;
        this.players = players;
        this.playerIndex = playerIndex;
    }

    @Override
    public void execute(){
        Player currentPlayer = this.players.get(this.playerIndex);
        Party currentParty = currentPlayer.getParty();
        Hand currentHand = currentPlayer.getHand();

        Scanner in = new Scanner(System.in);

        Card c;
        Party p;
        int i;

        switch (effect) {
            //DRAW 3 cards and DISCARD a card.
            case "drawThreeDiscardOne":
                // have the player draw three and discard one card
                for(i = 0; i < 3; i++){
                    System.out.println(currentPlayer.getName() + " draws three cards.");
                    System.out.println();
                    currentHand.addCard(this.deck.Draw());
                }

                // have the player discard one card
                System.out.println(currentPlayer.getName() + "'s current Hand:");
                currentHand.print();
                System.out.println();
                System.out.println("Enter the card you want to discard");
                c = currentHand.playCard(in.nextInt() - 1);
                discard.discard(c);

                break;
            //Search the discard pile for a Hero card and add it to your hand.
            case "searchDiscardHero":
                // only allow this effect when there are heroes in the discard pile to choose from
                if(!this.discard.hasHeroes()){
                    System.out.println("There are no heroes in the discard pile currently.");
                    break;
                }

                this.discard.printHeroes();

                // prompt player until chosen card is indeed a hero
                do {
                    System.out.println("Enter the hero you want from the discard pile");

                    c = discard.getHero(in.nextInt() - 1);

                    if (c == null) {
                        System.out.println("Hero not found in the discard pile. Try again.");
                    }
                } while (c == null);

                // add hero to player's hand
                currentHand.addCard(c);

                break;
            //Choose a player. STEAL a Hero card from that player's Party, then move a Hero card from your Party to that player's Party.
            case "heroSwitch":
                this.printParties();
                System.out.println("Enter the player you want to steal a hero from.");
                p = this.players.get(in.nextInt()-1).getParty();

                if (p.getHeros().isEmpty()){
                    System.out.println("Cannot steal from an empty party.");
                    break;
                }

                p.printHeroes();
                // Make them choose which hero to take
                System.out.println("Choose a hero to add to your party.");

                // Add hero to current player's party
                currentParty.addHero(p.removeHero(in.nextInt()-1));

                currentParty.print();
                // Make them choose which hero to give
                System.out.println("Choose a hero to give back.");

                // Add hero to current player's party
                p.addHero(currentParty.removeHero(in.nextInt()-1));

                break;
            case "destroyHeroTakeItem":
                Destroy destroy = new Destroy(this.players, this.discard);

                destroy.execute(1);

                break;
            //trying command here
            case "choosePlayerSacrifice":
                Sacrifice sac = new Sacrifice(this.players, this.discard);

                this.printParties();

                System.out.println("Enter the player you want to sacrifice a hero.");
                sac.execute(in.nextInt()-1);

                break;
            case "drawTwoChallengeDestroy":
                break;
            case "turnRollBuff":
                break;
            case "partyProtection":
                break;
            // Discard a card to destroy a hero
            // Spell
            case "discardDestroy":
                currentPlayer.getHand().print();
                System.out.println("Choose a card to discard");
                this.discard.discard(currentPlayer.getHand().playCard(in.nextInt()));

                Destroy dest = new Destroy(this.players, this.discard);
                dest.execute(playerIndex);
                break;
            // Hero: destroy a hero, and draw a card
            // Frelj
            case "destroyDraw":
                Destroy destHero = new Destroy(this.players, this.discard);
                destHero.execute(playerIndex);

                DrawCard dc = new DrawCard(this.players, this.deck);
                dc.execute(playerIndex);

                break;
            // Hero: search discard pile for a magic card
            // Ionia
            case "bunbun":
                if(!this.discard.hasSpells()){
                    System.out.println("No spells in the discard");
                    return;
                }

                this.discard.printSpells();
                System.out.println("Choose a spell to get");

                do {
                    System.out.println("Enter the spell you want from the discard pile");

                    c = discard.getSpell(in.nextInt() - 1);

                    if (c == null) {
                        System.out.println("Spell not found in the discard pile. Try again.");
                    }
                } while (c == null);

                currentHand.addCard(c);

                break;
            // Hero: Destroy a hero card
            // Noxus
            case "badaxe":
                Destroy badaxe = new Destroy(this.players, this.discard);
                badaxe.execute(playerIndex);
                break;

            // Draw a card, if card is a hero then play immediately
            // Bandle
            case "mellowdee":
//                DrawCard mellow = new DrawCard(this.players, this.deck);
//                mellow.execute(playerIndex);
                // Not utilizing DrawCard, because we need to check card type
                Card mellow = this.deck.Draw();
                if(mellow.getCardType() == CardType.HERO){
                    // Play the hero immediately
                    currentPlayer.getParty().addHero((Hero) mellow);
                } else {
                    // It is not a hero, so just regular draw action
                    currentPlayer.getHand().addCard(mellow);
                }
                break;
            case "silentshadow":
                System.out.println("Choose a player to steal a card from");
                int counter = 1;
                for(Player player : this.players){
                    System.out.println(counter + ". " + player.getName());
                }
                int ind = in.nextInt() - 1;
                Hand toSteal = this.players.get(ind).getHand();
                toSteal.print();
                System.out.println("Choose a card to steal");

                ind = in.nextInt() - 1;

                // Remove from the other players hand and give it to the player that stole it
                currentPlayer.getHand().addCard(toSteal.stealCard(ind));
                break;
            // +5 to a players rolls until the end of the turn
            case "vibrantglow":
                currentPlayer.setBonusRoll(currentPlayer.getBonusRoll() + 5);
                break;
            default:
                // Handle default case or leave effect as an empty string
                System.out.println("Invalid strategy name");
                break;
        }
    }

    public void printParties(){
        System.out.println("List of player parties: ");
        int i = 1;
        for (Player player: this.players){
            System.out.println(String.valueOf(i) + ". " + player.getName() + "'s Party: ");
            player.getParty().printNoNums();
            System.out.println();
            i = i + 1;
        }
    }
}