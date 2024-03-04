package lts.tests;

import lts.Cards.Challenges.Challenge;
import lts.Cards.Characters.Bosses.Boss;
import lts.Cards.Characters.Champion.Champion;
import lts.Cards.Characters.Champion.ChampionFactory;
import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Characters.Hero.HeroFactory;
import lts.Cards.Items.Item;
import lts.Cards.Items.ItemFactory;
import lts.Command.*;
import lts.CommunityCards.BossDeck;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Party;
import lts.Players.Player;
import lts.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandTest {

    @Test
    public void BattleBossTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        Discard d = new Discard();

        Sacrifice sac = new Sacrifice(players, d);
        BossDeck bd = new BossDeck();


        BattleBoss bb = new BattleBoss(players, bd, sac);

        Boss[] bosses = bd.getFightable();

        Party p = players.get(0).getParty();

        // Keith fights a boss
        if(bb.execute(0, 0)){
            Assertions.assertEquals(p.getBosses().size(), 1);
            for(Boss boss : bosses){
                if(boss == p.getBosses().get(0)){
                    Assertions.assertEquals(boss, p.getBosses().get(0));
                }
            }
        } else {
            Assertions.assertEquals(players.get(0).getParty().getBosses().size(), 0);
        }
    }

    @Test
    public void ChallengeCardTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        // Give a player a challenge card
        players.get(1).getHand().addCard(Challenge.getInstance());

        ModifyCard modC = new ModifyCard(players);

        ChallengeCard toTest = new ChallengeCard(players, modC);

        if(toTest.execute(0)){
            Assertions.assertEquals(players.get(1).getHand().getChallenges(), 0);
        } else {
            Assertions.assertEquals(players.get(1).getHand().getChallenges(), 0);
        }
    }


    @Test
    public void DestroyTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        Hero lux = HeroFactory.createHero("Lux");
        players.get(0).getParty().addHero(lux);

        Assertions.assertTrue(players.get(0).getParty().getHeros().contains(lux));

        Discard d = new Discard();

        Destroy destroy = new Destroy(players, d);

        Assertions.assertTrue(destroy.execute(1, 1, 0));
        Assertions.assertEquals(d.getCard(0), lux);
        Assertions.assertFalse(players.get(0).getParty().getHeros().contains(lux));
    }

    @Test
    public void DrawCardTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        Deck deck = new Deck();

        DrawCard drawCard = new DrawCard(players, deck);

        Assertions.assertTrue(drawCard.execute(0));
        Assertions.assertEquals(1, players.get(0).getHand().size());
    }

    @Test
    public void PlayCardTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        Hero lux = HeroFactory.createHero("Lux");
        Challenge challenge = Challenge.getInstance();
        Item BOC = ItemFactory.createItem("Banner of Command");

        players.get(0).getHand().addCard(lux);
        players.get(0).getHand().addCard(challenge);
        players.get(0).getHand().addCard(BOC);

        Discard discard = new Discard();
        Deck deck = new Deck();

        ModifyCard modifyCard = new ModifyCard(players);
        ChallengeCard challengeCard = new ChallengeCard(players, modifyCard);

        PlayCard playCard = new PlayCard(players, deck, discard, challengeCard);

        Assertions.assertTrue(playCard.execute(0, 1));
        Assertions.assertEquals(lux, players.get(0).getParty().getHero(0));
        Assertions.assertTrue(playCard.execute(0, 2));
        Assertions.assertEquals(BOC, players.get(0).getParty().getHero(0).getItem());
    }

    @Test
    public void RedrawHandTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        Deck deck = new Deck();
        Discard discard = new Discard();

        RedrawHand redrawHand = new RedrawHand(players, deck, discard);

        Assertions.assertTrue(redrawHand.execute(0));
        Assertions.assertEquals(5, players.get(0).getHand().size());
    }

    @Test
    public void RollHeroTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        Deck deck = new Deck();
        Discard discard = new Discard();

        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        players.get(0).getParty().addHero(HeroFactory.createHero("Lux"));

        RollHero rollHero = new RollHero(players, deck, discard);

        Assertions.assertTrue(rollHero.execute(0, 0));
    }

    @Test
    public void SacrificeTest(){
        Constants.updateData();
        List<Player> players = new ArrayList<>();
        players.add(new Player(ChampionFactory.createChampion("Teemo"), "Keith" ));
        players.add(new Player(ChampionFactory.createChampion("Ashe"), "Matt" ));
        players.add(new Player(ChampionFactory.createChampion("Ahri"), "Jason" ));

        Hero lux = HeroFactory.createHero("Lux");
        players.get(0).getParty().addHero(lux);

        Discard d = new Discard();

        Sacrifice sac = new Sacrifice(players, d);

        Assertions.assertTrue(sac.execute(0, 0));
        Assertions.assertEquals(lux, d.getCard(0));
    }

}
