package ru.spbau.mit.chat;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author - Эдгар
 * Date - 04.01.2017, 21:12
 */
public class ChatManager extends Observable {
    private final Map<InetSocketAddress, Chat> chats = new ConcurrentHashMap<>();

    public synchronized void updateChat(InetSocketAddress address, ChatMessage message) {
        if (!chats.containsKey(address)) {
            Chat chat = new Chat(message.getName(), address);
            chats.put(address, chat);
            setChanged();
            notifyObservers(chat);
        }

        chats.get(address).addMessage(message);
    }
}
