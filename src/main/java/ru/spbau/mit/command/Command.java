package ru.spbau.mit.command;

/**
 * Created by Эдгар on 17.09.2016.
 */
public interface Command {
    /**
     * @param input
     * @return
     */
    String execute(String input);
}
