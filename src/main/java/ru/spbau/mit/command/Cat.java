package ru.spbau.mit.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Эдгар on 17.09.2016.
 */
public class Cat implements Command {
    /**
     * @param input
     * @return
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
