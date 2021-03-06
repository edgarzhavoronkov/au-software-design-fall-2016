package ru.spbau.mit;

import ru.spbau.mit.command.Command;
import ru.spbau.mit.command.ExternalCmd;
import ru.spbau.mit.util.Pair;

import java.util.*;

/**
 * Created by Эдгар on 17.09.2016.
 * Main class for shell
 */
public class Shell {

    private Map<String, Command> commands = new HashMap<>();
    private Map<String, String> env = new HashMap<>();

    /**
     * Does literally nothing, see Builder
     */
    private Shell() { }

    /**
     * Method for executing commands
     * Splits raw input into pipelines, then sets all variables into environment if finds any
     * then performs expansion of all variables if they are present in the environment
     * only after this executes whole pipeline
     *
     * Namely, gives output of a first command as an input to a second
     * If second command has any arguments, then input is taken from arguments
     *
     * @param input raw input to execute
     * @return output of single command or whole pipeline
     */
    public String execute(String input) {
        List<String> pipeline = createPipeline(input);
        for (int i = 0; i < pipeline.size();) {
            Pair<String, String> assignment =
                    getAllVariablesFromCommand(pipeline.get(i));
            if (assignment == null) {
                pipeline.set(i, expandVars(pipeline.get(i)));
                ++i;
            } else {
                env.put(assignment.fst, assignment.snd);
                pipeline.remove(i);
            }
        }

        String output = "";

        if (!pipeline.isEmpty()) {
            String firstCmd = pipeline.get(0);
            Pair<Command, String> parsed = parseCommand(firstCmd);
            output = parsed.fst.execute(parsed.snd);
            for (int i = 1; i < pipeline.size(); ++i) {
                String cmd = pipeline.get(i);
                String tmp;
                parsed = parseCommand(cmd);
                if (parsed.snd == null) {
                    tmp = parsed.fst.execute(output);
                } else {
                    tmp = parsed.fst.execute(parsed.snd);
                }
                output = tmp;
            }
        }
        return output;
    }

    private List<String> createPipeline(String rawInput) {
        List<String> pipeline = new ArrayList<>();
        boolean inStrongQuotes = false;
        boolean inWeakQuotes = false;
        int left = 0;
        for (int right = 0; right < rawInput.length(); ++right) {
            char chr = rawInput.charAt(right);
            if (!inStrongQuotes && !inWeakQuotes && chr == '|') {
                pipeline.add(rawInput.substring(left, right));
                left = right + 1;
            }
            if (chr == '\'') {
                inStrongQuotes ^= true;
            }
            if (chr == '\"') {
                inWeakQuotes ^= true;
            }
        }
        pipeline.add(rawInput.substring(left));
        pipeline.replaceAll(String::trim);
        return pipeline;
    }

    private String expandVars(String rawCommand) {
        List<Integer> placeholders = new ArrayList<>();
        boolean inStrongQuotes = false;
        for (int i = 0; i < rawCommand.length(); ++i) {
            if (!inStrongQuotes && rawCommand.charAt(i) == '$') {
                placeholders.add(i);
            }
            if (rawCommand.charAt(i) == '\'') {
                inStrongQuotes ^= true;
            }
        }

        StringBuilder expanded = new StringBuilder();
        Set<Character> delimiters = new HashSet<Character>()
        {{
            add(' ');
            add('"');
            add('=');
            add('\\');
        }};
        int left = 0;
        for (Integer right : placeholders) {
            expanded.append(rawCommand.substring(left, right));
            left = right + 1;
            while (left < rawCommand.length() && !delimiters.contains(rawCommand.charAt(left))) {
                ++left;
            }
            String name = rawCommand.substring(right + 1, left);
            if (env.get(name) != null) {
                expanded.append(env.get(name));
            } else {
                expanded.append("");
            }
        }
        return expanded.append(rawCommand.substring(left)).toString();
    }

    private Pair<String, String> getAllVariablesFromCommand(String rawCommand) {
        List<String> tmp = new ArrayList<>(Arrays.asList(rawCommand.split("\\s+")));
        tmp.removeAll(Collections.singleton(""));
        if (tmp.size() > 1) {
            return null;
        }
        String tmpString = tmp.get(0);
        tmp = Arrays.asList(tmpString.split("="));

        if (tmp.size() != 2) {
            return null;
        }

        return new Pair<>(tmp.get(0), tmp.get(1));
    }

    private Pair<Command, String> parseCommand(String rawCommand) {
        String[] split = rawCommand.split("\\s+");
        String cmdName = split[0];
        List<String> args = new ArrayList<>();
        args.addAll(Arrays.asList(split).subList(1, split.length));
        if (commands.get(cmdName) != null) {
            String input = String.join(" ", args).replaceAll("(^\')|(^\")", "").replaceAll("(\'$)|(\"$)", "");

            return new Pair<>(commands.get(cmdName), input);
        } else {
            String cwd = commands.get("pwd").execute("");
            return new Pair<>(new ExternalCmd(env, cwd), rawCommand);
        }
    }

    /**
     * Class for creating the shell
     */
    public static class Builder {
        private Shell shell;

        /**
         * Creates empty shell(with no commands)
         */
        public Builder() {
            shell = new Shell();
        }

        /**
         * Adds new command to the shell
         * @param name - name of a command
         * @param impl - implementation of a command
         *             can be passed as an object, implementing interface of a Command
         *             or a single lambda-expression (String input) -> {...}
         * @return Builder with modified shell
         */
        public Builder command(String name, Command impl) {
            shell.commands.put(name, impl);
            return this;
        }

        /**
         * Returns shell in current state
         * @return Shell in particular state
         */
        public Shell init() {
            return shell;
        }
    }
}
