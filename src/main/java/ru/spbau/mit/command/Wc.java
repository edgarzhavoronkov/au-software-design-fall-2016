package ru.spbau.mit.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Эдгар on 17.09.2016.
 */
public class Wc implements Command {
    /**
     * @param input
     * @return
     */
    @Override
    public String execute(String input) {
        try {
            long linesCount = Files.lines(Paths.get(input))
                    .filter(line -> !line.isEmpty())
                    .count();
            // GOD DAMN IT I FUCKING LOVE JAVA 8!!!
            long wordsCount = Files.lines(Paths.get(input))
                    .flatMap(line -> Stream.of(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .count();
            long bytesCount = Files.size(Paths.get(input));
            return String.format("%d\t%d\t%d", linesCount, wordsCount, bytesCount);
        } catch (IOException e) {
            return "Something gone wrong, or file not found!";
        }
    }
}
