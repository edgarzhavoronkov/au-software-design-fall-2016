package ru.spbau.mit.chat;

import java.net.InetSocketAddress;
import java.util.Observable;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 0:00
 */
public class Chat extends Observable {
    private String peerName;
    private final InetSocketAddress peerAddress;

    public Chat(String peerName, InetSocketAddress peerAddress) {
        this.peerName = peerName;
        this.peerAddress = peerAddress;
    }

    public void addMessage(ChatMessage message) {
        if (!"".equals(message.getName())) {
            peerName = message.getName();
        }
        setChanged();
        notifyObservers(message);
    }

    public String getPeerHost() {
        return peerAddress.getHostName();
    }

    public int getPeerPortNumber() {
        return peerAddress.getPort();
    }

    public String getPeerName() {
        return peerName;
    }
}
