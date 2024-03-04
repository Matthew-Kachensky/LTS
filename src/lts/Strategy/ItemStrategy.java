package lts.Strategy;

import lts.Cards.Card;
import lts.Cards.Characters.Hero.Hero;
import lts.Command.Command;
import lts.CommunityCards.Deck;
import lts.CommunityCards.Discard;
import lts.Players.Player;
import lts.utils.RegionType;

import java.util.List;

public class ItemStrategy implements Strategy{
    private Hero hero;
    private String effect;

    public ItemStrategy(Hero hero, String effect) {
        this.hero = hero;
        this.effect = effect;
    }

    @Override
    public void execute(){
        switch (effect) {
            case "removeItem":
                this.hero.reset();
                break;
            case "heroIsBandle":
                this.hero.changeRegion(RegionType.BANDLE);
                break;
            case "heroIsVoid":
                this.hero.changeRegion(RegionType.VOID);
                break;
            case "heroIsDemacia":
                this.hero.changeRegion(RegionType.DEMACIA);
                break;
            case "heroIsFrelijord":
                this.hero.changeRegion(RegionType.FRELJORD);
                break;
            case "heroIsIonia":
                this.hero.changeRegion(RegionType.IONIA);
                break;
            case "heroIsNoxus":
                this.hero.changeRegion(RegionType.NOXUS);
                break;
            case "heroRollBuff":
                this.hero.changeMinRoll(-2);
                break;
            default:
                // Handle default case or leave effect as an empty string
                break;
        }
    }

}
