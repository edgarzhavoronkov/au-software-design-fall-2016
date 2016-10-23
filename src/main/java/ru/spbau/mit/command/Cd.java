package ru.spbau.mit.command;

public class Cd implements Command {
    @Override
    public String execute(String input) {
        System.setProperty("user.dir", input);
        return input;
    }
}
