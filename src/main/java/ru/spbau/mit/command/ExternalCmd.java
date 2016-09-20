package ru.spbau.mit.command;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Эдгар on 20.09.2016.
 */
public class ExternalCmd implements Command {
    private Map<String, String> env = new HashMap<>();
    private String cwd;

    /**
     * @param env
     * @param cwd
     */
    public ExternalCmd(Map<String, String> env, String cwd) {
        this.env = env;
        this.cwd = cwd;
    }

    /**
     * @param input
     * @return
     */
    @Override
    public String execute(String input) {
        ProcessBuilder externalCmd = new ProcessBuilder(Arrays.asList(input.split("\\s+")));
        externalCmd.environment().putAll(env);
        externalCmd.directory(new File(cwd));
        externalCmd.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try {
            Process proc = externalCmd.start();
            InputStream inputStream = proc.getInputStream();
            try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                return result.toString("UTF-8");
            } catch (IOException e) {
                return e.getMessage();
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
