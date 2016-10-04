package ru.spbau.mit.command;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Эдгар on 20.09.2016.
 * Implementation of Command interface for external command.
 * Here we add some more stuff into this class
 * since we need to be aware of environment and current working directory
 */
public class ExternalCmd implements Command {
    private Map<String, String> env = new HashMap<>();
    private String cwd;

    /**
     * Simple constructor
     * @param env environment in which we are currently running
     * @param cwd directory in which we are currently running
     */
    public ExternalCmd(Map<String, String> env, String cwd) {
        this.env = env;
        this.cwd = cwd;
    }

    /**
     * {@link Command}'s .execute implementation
     * @param input arguments for command
     * @return result of execution as a String
     * if process fails, then prints why it failed as a result
     */
    @Override
    public String execute(String input) {
        ProcessBuilder externalCmd = new ProcessBuilder(Arrays.asList(input.split("\\s+")));
        externalCmd.environment().putAll(env);
        externalCmd.directory(new File(cwd));
        try {
            Process proc = externalCmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ( (line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
