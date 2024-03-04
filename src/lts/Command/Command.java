package lts.Command;

public interface Command {
    /**
     * Execute a given command
     * @param i the player who is invoking the command
     * @// TODO: 12/7/2023 Consider changing return value to boolean, so the player can back out of the action and 
     * @// TODO: 12/7/2023 perform a different command without losing action point
     */
    boolean execute(int i);
}
