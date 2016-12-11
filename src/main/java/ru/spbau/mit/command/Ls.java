package ru.spbau.mit.command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of Command interface for Ls.
 * Command shows information about the files.
 */
public class Ls implements Command {
    /**
     * {@link Ls}'s .execute implementation
     * @param input Empty string or string with directory path
     * @return String with file names
     */
    @Override
    public String execute(String input) {
        String currentDirectory = input.equals("") ? System.getProperty("user.dir") : input;
        Path workDirectory = Paths.get(System.getProperty("user.dir"));
        File directory = workDirectory.resolve(currentDirectory)
                                        .toAbsolutePath()
                                        .normalize()
                                        .toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            return "Directory not found";
        }
        String[] files = directory.list();
        return String.join("\n", files);
    }
}
