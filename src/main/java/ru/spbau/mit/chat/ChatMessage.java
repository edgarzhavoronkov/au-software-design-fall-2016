package ru.spbau.mit.chat;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 0:36
 */
public class ChatMessage implements Comparable<ChatMessage> {
    private final String name;
    private final String text;
    private final Long time;
    private final int serverPortNumber;

    public ChatMessage(String name, String text, long time, int serverPortNumber) {
        this.name = name;
        this.text = text;
        this.time = time;
        this.serverPortNumber = serverPortNumber;
    }

    public String getName() {
        return name;
    }

    public Long getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public int getServerPortNumber() {
        return serverPortNumber;
    }

    @Override
    public int compareTo(ChatMessage o) {
        return time.compareTo(o.getTime());
    }
}
