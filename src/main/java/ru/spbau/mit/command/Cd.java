package ru.spbau.mit.command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cd implements Command {
    @Override
    public String execute(String input) {
        Path workDirectory = Paths.get(System.getProperty("user.dir"));
        File directory = workDirectory.resolve(input)
                                      .toAbsolutePath()
                                      .normalize()
                                      .toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            return "Directory is not found";
        }

        System.setProperty("user.dir", directory.toString());
        return directory.toString();
    }
}
