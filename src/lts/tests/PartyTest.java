package lts.tests;

import lts.Cards.Characters.Bosses.Boss;
import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Characters.Hero.HeroFactory;
import lts.Players.Party;
import lts.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartyTest {

    @Test
    public void addHeroTest(){
        Constants.updateData();
        Party p = new Party();
        Assertions.assertEquals(p.getHeros().size(), 0);

        Hero lux = HeroFactory.createHero("Lux");
        Hero galio = HeroFactory.createHero("Galio");
        p.addHero(lux);
        p.addHero(galio);

        Assertions.assertEquals(p.getHeros().get(0), lux);
        Assertions.assertEquals(p.getHeros().get(1), galio);
    }

    @Test
    public void getCardTest(){
        Constants.updateData();
        Party p = new Party();
        Assertions.assertEquals(p.getHeros().size(), 0);

        Hero lux = HeroFactory.createHero("Lux");
        Hero galio = HeroFactory.createHero("Galio");
        p.addHero(lux);
        p.addHero(galio);

        Assertions.assertEquals(p.getHero(0), lux);
        Assertions.assertEquals(p.getHero(1), galio);
    }

    @Test
    public void addBossTest(){
        Constants.updateData();
        Party p = new Party();
        Assertions.assertEquals(p.getBosses().size(), 0);

        Boss b = new Boss("Baron");
        Boss b2 = new Boss("Infernal Dragon");
        p.addBoss(b);
        p.addBoss(b2);

        Assertions.assertEquals(p.getBosses().size(), 2);
        Assertions.assertEquals(p.getBosses().get(0), b);
        Assertions.assertEquals(p.getBosses().get(1), b2);
    }

    @Test
    public void removeHeroTest(){
        Constants.updateData();
        Party p = new Party();
        Assertions.assertEquals(p.getHeros().size(), 0);
        Hero lux = HeroFactory.createHero("Lux");
        p.addHero(lux);

        Assertions.assertFalse(p.getHeros().isEmpty());
        Assertions.assertEquals(p.getHeros().size(), 1);
        Assertions.assertEquals(p.getHeros().get(0), lux);

        p.removeHero(0);

        Assertions.assertEquals(p.getHeros().size(), 0);
        Assertions.assertFalse(p.getHeros().contains(lux));

        Hero galio = HeroFactory.createHero("Galio");
        p.addHero(lux);
        p.addHero(galio);

        p.removeHero(1);

        Assertions.assertEquals(p.getHeros().size(), 1);
        Assertions.assertFalse(p.getHeros().contains(lux));
        Assertions.assertTrue(p.getHeros().contains(galio));
    }
}