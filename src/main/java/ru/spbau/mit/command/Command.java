package ru.spbau.mit.command;

/**
 * Created by Эдгар on 17.09.2016.
 * Interface for all commands.
 * Having one method gives me an opportunity
 * to implement simple commands with lambda-expressions
 */
public interface Command {
    /**
     * @param input commands' arguments
     * @return result as a String, since we are displaying everything in console
     */
    String execute(String input);
}
