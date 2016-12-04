package ru.spbau.mit.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.spbau.mit.util.InputSplitter.splitBy;

/**
 * Created by Эдгар on 02.10.2016.
 * Implementation of {@link Command} interface
 * for grep command
 */
public class Grep implements Command {

    @Parameter(names = "-i", description = "Ignore case distinctions")
    private Boolean ignoreCase = false;

    @Parameter(names = "-w", description = "Select only those lines containing matches that form whole words")
    private Boolean wholeWords = false;

    @Parameter(names = "-A", description = "Select n extra lines after line containing match")
    private Integer extraLines = 0;

    @Parameter(description = "Pattern and input files")
    private List<String> inputs;

    private Pattern pattern;

    /**
     * Actual execution.
     * @param input - commands' arguments. Here user __must__ provide pattern to match
     *              and file name where to search
     * @return Matched lines, joined by '\n'
     */
    @Override
    public String execute(String input) {
        List<String> split = splitBy(input, ' ');
        String[] args = split.toArray(new String[split.size()]);
        new JCommander(this, args);

        String regex = inputs.get(0).replaceAll("(^\')|(^\")", "").replaceAll("(\'$)|(\"$)", "");

        pattern = createPattern(regex);

        try {
            File file = new File(inputs.get(1));
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    result.append(line);
                    result.append('\n');
                    int count = extraLines;
                    while (count > 0) {
                        line = reader.readLine();
                        result.append(line);
                        result.append('\n');
                        if (!line.isEmpty()) {
                            --count;
                        }
                    }
                }
            }
            resetState();
            return result.toString();
        } catch (IOException e) {
            resetState();
            return "Failed to open file for searching";
        } catch (IndexOutOfBoundsException e) {
            resetState();
            return "Wrong number of arguments! Provide at least pattern to search by and filename where to search";
        }
    }

    private void resetState() {
        this.ignoreCase = false;
        this.wholeWords = false;
        this.extraLines = 0;
    }

    private Pattern createPattern(String regex) {
        if (wholeWords) {
            regex = String.format("(\\s+|^)%s(\\s+|$)", regex);
        }
        if (ignoreCase) {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        return Pattern.compile(regex);
    }
}
