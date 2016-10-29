package ru.spbau.mit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.spbau.mit.command.Cat;
import ru.spbau.mit.command.Cd;
import ru.spbau.mit.command.Ls;
import ru.spbau.mit.command.Wc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Эдгар on 20.09.2016.
 */
public class ShellTest {
    private final Shell shell = new Shell.Builder()
            .command("cat", new Cat())
            .command("wc", new Wc())
            .command("cd", new Cd())
            .command("ls", new Ls())
            .command("echo", input -> input)
            .command("pwd", (input -> System.getProperty("user.dir")))
            .command("exit", input -> {
                System.exit(0);
                return "";
            })
            .init();

    @Test
    public void testSimplePipelines() throws Exception {
        String cmd1 = "echo 123 | echo 456";
        assertEquals("456", shell.execute(cmd1));
    }

    @Test
    public void testStrongQuoting() throws Exception {
        String cmd2 = "echo \'$X | cat abcd | ls\'";
        assertEquals("$X | cat abcd | ls", shell.execute(cmd2));
    }

    @Test
    public void testWeakQuoting() throws Exception {
        shell.execute("X=1");
        String cmd3 = "echo \"$X\"";
        assertEquals("1", shell.execute(cmd3));
    }

    @Test
    public void testGettingAndSettingEnvironmentVariables() throws Exception {
        shell.execute("Y=2");
        String cmd4 = "echo $Y";
        assertEquals("2", shell.execute(cmd4));
    }

    @Rule
    public TemporaryFolder workDirectory = new TemporaryFolder();

    @Test
    public void cdTest() throws IOException {
        TemporaryFolder workDirectory = new TemporaryFolder();
        File directory = workDirectory.newFolder();

        final String cmd = "cd " + directory.getAbsolutePath();
        String newUserDirectory = shell.execute(cmd);
        assertEquals(System.getProperty("user.dir"), newUserDirectory);
    }

    @Test
    public void lsTest() throws IOException {
        final List<String> files = Arrays.asList("tempFile1", "tempFile2");
        for (String name : files) {
            workDirectory.newFile(name);
        }
        System.setProperty("user.dir", workDirectory.getRoot().getAbsolutePath());

        final String cmd = "ls";

        assertEquals(String.join("\n", files), shell.execute(cmd));
    }
}