package ru.spbau.mit.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Эдгар on 03.01.2017.
 * Small factory for commands
 */
public class CommandProvider {
    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("cat", new Cat());
        commands.put("wc", new Wc());
        commands.put("echo", input -> input);
        commands.put("pwd", (input -> System.getProperty("user.dir")));
        commands.put("grep", new Grep());
    }

    /**
     * Gets concrete command by its' name or null if no command was found
     * @param name command name
     * @return concrete implementation or null
     */
    public static Command commandByName(String name) {
        return commands.get(name);
    }
}
