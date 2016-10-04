package ru.spbau.mit.command;

import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Эдгар on 20.09.2016.
 */
public class ExternalCmdTest {
    @Test
    public void testExecute() throws Exception {
        Map<String, String> env = new HashMap<>();
        String cwd = System.getProperty("user.dir");
        String hostname = InetAddress.getLocalHost().getHostName();
        Command extern = new ExternalCmd(env, cwd);
        String result = extern.execute("hostname");
        assertEquals(hostname, result);
    }
}