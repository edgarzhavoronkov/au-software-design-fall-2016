package ru.spbau.mit.command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of Command interface for Cd.
 * Command change the current working directory to a specific Folder.
 */
public class Cd implements Command {
    /**
     * {@link Cd}'s .execute implementation
     * @param input String with directory path
     * @return String with user directory path
     */
    @Override
    public String execute(String input) {
        Path workDirectory = Paths.get(System.getProperty("user.dir"));
        File directory = workDirectory.resolve(input)
                                      .toAbsolutePath()
                                      .normalize()
                                      .toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            return "Directory not found";
        }

        System.setProperty("user.dir", directory.toString());
        return directory.toString();
    }
}
