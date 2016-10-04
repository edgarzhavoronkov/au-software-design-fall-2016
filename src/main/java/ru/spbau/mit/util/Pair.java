package ru.spbau.mit.util;

/**
 * Created by Эдгар on 19.09.2016.
 *
 * Stupid pair class because fuck you, Map.Entry, that's why!
 * Clearly, i have no idea why i am writing this JavaDoc
 * Fields are final, so no way, you can change them
 */
public class Pair<A, B> {
    public final A fst;
    public final B snd;

    /**
     * Pair constructor
     * @param fst - first element of pair
     * @param snd - second element of pair
     */
    public Pair(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }
}
