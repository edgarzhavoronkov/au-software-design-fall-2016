package ru.spbau.mit.util;

/**
 * Created by Эдгар on 19.09.2016.
 *
 * Stupid pair class because fuck you, Map.Entry, that's why! Clearly, i have no idea why i am writing this JavaDoc
 */
public class Pair<A, B> {
    public A fst;
    public B snd;

    /**
     * @param fst
     * @param snd
     */
    public Pair(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }
}
