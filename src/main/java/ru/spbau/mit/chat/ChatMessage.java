package ru.spbau.mit.chat;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 0:36
 * Simple chat message
 *
 * Messages are ordered by time
 */
@Data
public class ChatMessage implements Comparable<ChatMessage> {
    private final String name;
    private final String text;
    private final Long time;
    private final int serverPortNumber;

    @Override
    public int compareTo(@NotNull ChatMessage o) {
        return time.compareTo(o.getTime());
    }
}
