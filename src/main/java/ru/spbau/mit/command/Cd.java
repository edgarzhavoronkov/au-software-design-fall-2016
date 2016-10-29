package ru.spbau.mit.command;

import java.io.File;

public class Cd implements Command {
    @Override
    public String execute(String input) {
        File directory = new File(input);
        if (!directory.exists() || !directory.isDirectory()) {
            return "Directory is not found";
        }

        System.setProperty("user.dir", input);
        return input;
    }
}
