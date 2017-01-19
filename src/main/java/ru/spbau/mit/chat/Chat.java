package ru.spbau.mit.chat;

import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.Observable;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 0:00
 * Abstraction for chat. Observable, since we want to notify others when message is received
 */
public class Chat extends Observable {
    @Getter
    private String peerName;
    private final InetSocketAddress peerAddress;

    Chat(String peerName, InetSocketAddress peerAddress) {
        this.peerName = peerName;
        this.peerAddress = peerAddress;
    }

    void addMessage(ChatMessage message) {
        if (!"".equals(message.getName())) {
            peerName = message.getName();
        }
        setChanged();
        notifyObservers(message);
    }

    /**
     * Getter for peer host name
     * @return peer host name
     */
    public String getPeerHost() {
        return peerAddress.getHostName();
    }

    /**
     * Getter for peer port number
     * @return peer port number
     */
    public int getPeerPortNumber() {
        return peerAddress.getPort();
    }
}
