package ru.spbau.mit.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Эдгар on 23.10.2016.
 */
public class GrepTest {
    private static final Command grep = new Grep();

    @Test
    public void testInsensitiveExecute() throws Exception {
        String input = "-i 'all[a-z]+' README.md";
        assertEquals(
                "__UPD 23.10.16__ NEW AND ALLMIGHTY SHELL! NOW WITH GREP! FREE! NO SMS! NO SIGN UP!\n"
                , grep.execute(input));
    }

    @Test
    public void testWholeWordsExecute() throws Exception {
        String input = "-w 'WITH' README.md";
        assertEquals(
                "__UPD 23.10.16__ NEW AND ALLMIGHTY SHELL! NOW WITH GREP! FREE! NO SMS! NO SIGN UP!\n",
                grep.execute(input)
        );
    }

    @Test
    public void testExtraLinesExecute() throws Exception {
        String input = "-A 2 'GREP.' README.md";
        assertEquals(
                "__UPD 23.10.16__ NEW AND ALLMIGHTY SHELL! NOW WITH GREP! FREE! NO SMS! NO SIGN UP!\n" +
                        "\n" +
                        "Also supports `grep` utility with keys:\n" +
                        "   * `-i` for case-insensitive matching\n"
                , grep.execute(input)
        );
    }
}