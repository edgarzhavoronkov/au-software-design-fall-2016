package ru.spbau.mit.command;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Эдгар on 20.09.2016.
 */
public class CatTest {
    @Test
    public void testExecute() throws Exception {
        Command cat = new Cat();
        String filename = "src/test/resources/test";
        assertEquals("test test test", cat.execute(filename));
    }

}