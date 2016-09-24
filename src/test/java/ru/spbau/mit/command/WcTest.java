package ru.spbau.mit.command;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Эдгар on 20.09.2016.
 */
public class WcTest {
    @Test
    public void testExecute() throws Exception {
        Command wc = new Wc();
        String filename = "src/test/resources/test";
        assertEquals("1\t3\t16", wc.execute(filename));
    }

}