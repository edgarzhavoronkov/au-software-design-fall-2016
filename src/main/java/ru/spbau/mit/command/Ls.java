package ru.spbau.mit.command;

import java.io.File;

public class Ls implements Command {
    @Override
    public String execute(String input) {
        String currentDirectory = System.getProperty("user.dir");
        String[] files = new File(currentDirectory).list();
        return String.join("\n", files);
    }
}
