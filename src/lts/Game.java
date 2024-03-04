package lts;

import lts.Cards.Card;
import lts.Cards.Characters.Bosses.Boss;
import lts.Cards.Characters.Champion.ChampionFactory;
import lts.Cards.Characters.Hero.Hero;
import lts.Command.*;
import lts.CommunityCards.BossDeck;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Player;
import lts.Strategy.ActiveStrategy;
import lts.Strategy.PassiveStrategy;
import lts.Strategy.Strategy;
import lts.utils.Constants;
import lts.utils.RegionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final List<Player> players;
    private final Deck deck;
    private final Discard discard;
    private final BossDeck bosses;
    private List<String> available;
    private int playerCount;
    private Command playCard;
    private Command drawCard;
    private Command battleBoss;
    private Command challengeCard;
    private Command modCard;
    private Command redraw;
    private Command rollHero;
    private Command sacrifice;
    private Command destroy;


    public Game(){
        Constants.updateData();

        available = new ArrayList<>(Constants.champNames);

        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.discard = new Discard();
        this.bosses = new BossDeck();

        // Gets amount of people, their champions, and their starting hand
        this.preGame();

        // When play of the game actually starts
        this.start();
    }

    /**
     * The game with all logic and such
     */
    private void start(){
        int playerTurn = 0;
        while(!this.end()) {
            int prevBonusRoll = this.players.get(playerTurn).getBonusRoll();

            for(Boss b : this.players.get(playerTurn).getParty().getBosses()){
                PassiveStrategy ps = new PassiveStrategy(Constants.getAbility(b.getName()), playerTurn, this.players.get(playerTurn), this.deck);
                ps.execute();
            }

            // Get whose player turn it is
            int actionPoints = 3;

            // Command to get what action they want to take
            while (actionPoints > 0) {
                System.out.println(this.players.get(playerTurn).getName() + " (" + this.players.get(playerTurn).getParty().getChampion().getName()+ ")'s turn");
                System.out.println(actionPoints + " action points remaining");

                // Do command for actions
                Scanner in = new Scanner(System.in);

                // This is the command portion
                System.out.println("""
                                1. Play a card
                                2. Draw a card
                                3. Redraw hand
                                4. Roll a hero
                                5. Battle a boss \s""" + (this.players.get(playerTurn).getParty().getChampion().getRegion() == RegionType.VOID ? "6. Steal a card" : ""));

                switch(in.nextInt()){
                    case 1 -> {
                        if(this.playCard.execute(playerTurn)) {
                            actionPoints -= 1;
                        }
                    }
                    case 2 -> {
                        if(this.drawCard.execute(playerTurn)) {
                            actionPoints -= 1;
                        }
                    }
                    case 3 -> {
                        if(actionPoints != 3){
                            System.out.println("Not enough action points. Pick another option: ");
                            break;
                        }

                        if(this.redraw.execute(playerTurn)) {
                            actionPoints -= 3;
                        }
                    }
                    case 4 -> {
                        if(this.rollHero.execute(playerTurn)){
                            actionPoints -= 1;
                        }
                    }
                    case 5 -> {
                        if(actionPoints < 2){
                            System.out.println("Not enough action points. Pick another option: ");
                            break;
                        }

                        this.battleBoss.execute(playerTurn);
                        actionPoints -= 2;
                    }
                    // Special case for void
                    case 6 -> {
                        if(this.players.get(playerTurn).getParty().getChampion().getRegion() == RegionType.VOID){
                            int ind = 1;
                            for(Player p : this.players){
                                System.out.println(ind++ + ". " + p.getName());
                            }
                            System.out.println("Choose a player to steal from");

                            Card c = this.players.get(in.nextInt()).getHand().stealCard();

                            this.players.get(playerTurn).getHand().addCard(c);

                            actionPoints -= 1;
                        } else {
                            System.out.println("Invalid option pick again");
                        }
                    }
                    default -> System.out.println("Invalid option pick again");
                }
            }

            this.players.get(playerTurn).setBonusRoll(prevBonusRoll);
            // Rotate whose turn it is
            playerTurn = (playerTurn + 1) % playerCount;
        }
        // If the game ended

    }

    /**
     * Checks for if an end condition has been reached
     * @return true if someone has won, false otherwise
     */
    private boolean end(){
        // Go through each player
        for(Player p : this.players){

            // Check if they have met the boss requirements
            if(p.getParty().getBosses().size() > 2){
                return true;
            }

            // If not, check all of the heros for unique type
            List<RegionType> uniqueTypes = new ArrayList<>();
            for(Hero h : p.getParty().getHeros()){
                if(!uniqueTypes.contains(h.getRegion())){
                    uniqueTypes.add(h.getRegion());
                }

            }
            // The champion does count
            if(!uniqueTypes.contains(p.getParty().getChampion().getRegion())){
                uniqueTypes.add(p.getParty().getChampion().getRegion());
            }

            // 6 unique types
            if(uniqueTypes.size() > 5){
                return true;
            }
        }

        // If non of the conditions have been met
        return false;
    }


    /**
     * Computes pregame stuff like champion selection
     */
    private void preGame(){
        this.getPlayerCount();

        // Get champions
        for(int i = 0; i < playerCount; i++){
            Scanner in = new Scanner(System.in);
            System.out.println("Player " + (i + 1) + " what is your name?");
            String playerName = in.nextLine();
            String name = this.getChamp();
            players.add(new Player(ChampionFactory.createChampion(name), playerName));
        }

        // Draw cards for the starting hand
        // For each player
        for(int i = 0; i < playerCount; i++){
            for(int j = 0; j < 5; j++){
                players.get(i).getHand().addCard(this.deck.Draw());
            }
        }

        // Set up commands
        this.drawCard = new DrawCard(this.players, this.deck);

        this.modCard = new ModifyCard(this.players);

        this.challengeCard = new ChallengeCard(this.players, this.modCard);

        this.playCard = new PlayCard(this.players, this.deck, this.discard, this.challengeCard);

        this.redraw = new RedrawHand(this.players, this.deck, this.discard);

        this.rollHero = new RollHero(this.players, this.deck, this.discard);

        this.sacrifice = new Sacrifice(this.players, this.discard);

        this.destroy = new Destroy(this.players ,this.discard);

        this.battleBoss = new BattleBoss(this.players, this.bosses, this.sacrifice);

    }

    /**
     * Gets the champion a player wants to play as out of 2 random champions
     * @return
     */
    private String getChamp(){
        Scanner in = new Scanner(System.in);
        String name = "";

        // Change seed later
        Random rand = new Random(0);

        // Get the two options for champions
        String one = available.get(rand.nextInt(available.size()));
        String two = available.get(rand.nextInt(available.size()));

        // Make sure they are not the same
        while(one == two){
            two = available.get(rand.nextInt(available.size()));
        }

        // Make them choose one of the two
        System.out.println("Choose one of the following:\n1. " + one + "\n2. " + two);

        do{
            switch(in.nextInt()){
                case 1:
                    available.remove(one);
                    return one;
                case 2:
                    available.remove(two);
                    return two;
                default:
                    System.out.println("Please enter a valid number");
            }
        } while(true);
    }

    /**
     * Gets the amount of players playing
     * @return the number of players, max 6
     */
    private void getPlayerCount(){
        // Grab user input
        System.out.println("Enter the number of players playing: ");
        Scanner in = new Scanner(System.in);
        playerCount = in.nextInt();

        // Make sure there are less players than the supported max
        if(playerCount > 5){
//            System.out.print("Game does not support more than 6 players");
            throw new RuntimeException("Game does not support more than 6 players");
        }
    }


    public static void main(String[] args){
        Game g = new Game();
    }
}
