package lts.utils;

public enum CardType {
    ITEM("Item"), CHAMPION("Champion"), HERO("Hero"), BOSS("Boss"), CHALLENGE("Challenge"),
    BUFF("Buff"), NERF("Nerf"), BUFFNERF("BuffNerf"), SPELL("Spell"), MOD("Modifier");

    private String type;

    CardType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
