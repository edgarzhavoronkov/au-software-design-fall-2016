package ru.spbau.mit.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Эдгар on 17.09.2016.
 * Implementation of Command interface for Cat
 */
public class Cat implements Command {
    /**
     * {@link Command}'s .execute implementation
     * @param input String with file's name to display on screen
     * @return whole file as one line
     */
    @Override
    public String execute(String input) {
        try (Stream<String> lines = Files.lines(Paths.get(input))) {
            return String.join(" ", lines.collect(Collectors.toList()));
        } catch (IOException e) {
            return "Something gone wrong, or file not found!";
        }
    }
}
