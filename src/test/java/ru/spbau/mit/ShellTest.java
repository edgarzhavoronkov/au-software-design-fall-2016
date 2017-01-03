package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Эдгар on 20.09.2016.
 */
public class ShellTest {
    private final Shell shell = Shell.getInstance();

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




}