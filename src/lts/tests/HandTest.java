package lts.tests;

import lts.Cards.Card;
import lts.Cards.Challenges.Challenge;
import lts.Cards.Characters.Hero.HeroFactory;
import lts.Cards.Items.ItemFactory;
import lts.Cards.Modifiers.ModFactory;
import lts.Cards.Spells.SpellFactory;
import lts.Players.Hand;
import lts.utils.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;


public class HandTest {

    @Test
    public void AddCardTest(){
        Constants.updateData();

        Hand hand = new Hand();
        Assert.assertTrue(hand.getAllCards().isEmpty());
        Assert.assertTrue(hand.getSpells().isEmpty());
        Assert.assertTrue(hand.getMods().isEmpty());
        Assert.assertTrue(hand.getHeros().isEmpty());
        Assert.assertTrue(hand.getItems().isEmpty());
        Assert.assertEquals(0, hand.getChallenges());

        Card c = HeroFactory.createHero("Lux");
        hand.addCard(c);

        Assert.assertFalse(hand.getHeros().isEmpty());
        Assert.assertEquals(c, hand.getHeros().get(0));
        Assert.assertEquals(c, hand.getAllCards().get(0));

        c = ItemFactory.createItem("Banner of Command");
        hand.addCard(c);

        Assert.assertFalse(hand.getItems().isEmpty());
        Assert.assertEquals(c, hand.getItems().get(0));
        Assert.assertEquals(c, hand.getAllCards().get(1));

        c = SpellFactory.createSpell("Rocket Grab");
        hand.addCard(c);

        Assert.assertFalse(hand.getSpells().isEmpty());
        Assert.assertEquals(c, hand.getSpells().get(0));
        Assert.assertEquals(c, hand.getAllCards().get(2));

        Assert.assertFalse(hand.canChallenge());
        c = Challenge.getInstance();
        hand.addCard(c);

        Assert.assertTrue(hand.canChallenge());
        Assert.assertTrue(hand.getChallenges() > 0);
        Assert.assertEquals(c, hand.getAllCards().get(3));

        c = ModFactory.createMod("+2/-2");
        hand.addCard(c);

        Assert.assertFalse(hand.getMods().isEmpty());
        Assert.assertEquals(c, hand.getMods().get(0));
        Assert.assertEquals(c, hand.getAllCards().get(4));


    }

    @Test
    public void removeCardTest(){
        Constants.updateData();
        Hand hand = new Hand();
        Card h = HeroFactory.createHero("Lux");
        hand.addCard(h);
        Card i = ItemFactory.createItem("Banner of Command");
        hand.addCard(i);
        Card s = SpellFactory.createSpell("Rocket Grab");
        hand.addCard(s);
        Card c = Challenge.getInstance();
        hand.addCard(c);
        Card m = ModFactory.createMod("+2/-2");
        hand.addCard(m);

        Assert.assertTrue(hand.getAllCards().size() > 3);

        int size = hand.getAllCards().size();
        hand.removeCard(h);
        Assert.assertTrue(hand.getHeros().isEmpty());
        Assert.assertFalse(hand.getAllCards().contains(h));
        Assert.assertEquals(size - 1, hand.getAllCards().size());

        size--;
        hand.removeCard(i);
        Assert.assertTrue(hand.getItems().isEmpty());
        Assert.assertFalse(hand.getAllCards().contains(h));
        Assert.assertEquals(size - 1, hand.getAllCards().size());

        size--;
        hand.removeCard(s);
        Assert.assertTrue(hand.getSpells().isEmpty());
        Assert.assertFalse(hand.getAllCards().contains(h));
        Assert.assertEquals(size - 1, hand.getAllCards().size());

        size--;
        hand.removeCard(c);
        Assert.assertEquals(hand.getChallenges(), 0);
        Assert.assertFalse(hand.getAllCards().contains(h));
        Assert.assertEquals(size - 1, hand.getAllCards().size());

        size--;
        hand.removeCard(m);
        Assert.assertTrue(hand.getSpells().isEmpty());
        Assert.assertFalse(hand.getAllCards().contains(h));
        Assert.assertEquals(size - 1, hand.getAllCards().size());

    }

    @Test
    public void stealCardTest(){
        Constants.updateData();
        Hand hand = new Hand();
        Card h = HeroFactory.createHero("Lux");
        hand.addCard(h);
        Card i = ItemFactory.createItem("Banner of Command");
        hand.addCard(i);
        Card s = SpellFactory.createSpell("Rocket Grab");
        hand.addCard(s);
        Card c = Challenge.getInstance();
        hand.addCard(c);
        Card m = ModFactory.createMod("+2/-2");
        hand.addCard(m);

        int size = hand.size();
        Random rand = new Random(42);
        Card steal = hand.stealCard(rand.nextInt(size));
        switch(steal.getCardType()){
            case SPELL -> {
                Assert.assertEquals(steal, s);
                Assert.assertTrue(hand.getSpells().isEmpty());
            }
            case ITEM -> {
                Assert.assertEquals(steal, i);
                Assert.assertTrue(hand.getItems().isEmpty());
            }
            case HERO -> {
                Assert.assertEquals(steal, h);
                Assert.assertTrue(hand.getHeros().isEmpty());
            }
            case CHALLENGE -> {
                Assert.assertEquals(steal, c);
                Assert.assertEquals(0, hand.getChallenges());
            }
            case MOD -> {
                Assert.assertEquals(steal, m);
                Assert.assertTrue(hand.getMods().isEmpty());
            }
        }
        Assert.assertEquals(size - 1, hand.size());


        size = hand.size();
        steal = hand.stealCard(rand.nextInt(size));
        switch(steal.getCardType()){
            case SPELL -> {
                Assert.assertEquals(steal, s);
                Assert.assertTrue(hand.getSpells().isEmpty());
            }
            case ITEM -> {
                Assert.assertEquals(steal, i);
                Assert.assertTrue(hand.getItems().isEmpty());
            }
            case HERO -> {
                Assert.assertEquals(steal, h);
                Assert.assertTrue(hand.getHeros().isEmpty());
            }
            case CHALLENGE -> {
                Assert.assertEquals(steal, c);
                Assert.assertEquals(0, hand.getChallenges());
            }
            case MOD -> {
                Assert.assertEquals(steal, m);
                Assert.assertTrue(hand.getMods().isEmpty());
            }
        }
        Assert.assertEquals(size - 1, hand.size());
    }



}