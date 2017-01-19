package ru.spbau.mit.chat;

import lombok.extern.log4j.Log4j2;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author - Эдгар
 * Date - 04.01.2017, 21:12
 * Manages all current chats. Observable since we want to notify UI with new chats
 */
@Log4j2
public class ChatManager extends Observable {
    private final Map<InetSocketAddress, Chat> chats = new ConcurrentHashMap<>();

    /**
     * Suddenly, updates chat with new message and notifies
     * @param address peer address
     * @param message incoming or outcoming message
     */
    public synchronized void updateChat(InetSocketAddress address, ChatMessage message) {
        if (!chats.containsKey(address)) {
            log.info("Updating chat with " + address + " with message: " + message.getText());
            Chat chat = new Chat(message.getName(), address);
            chats.put(address, chat);
            setChanged();
            notifyObservers(chat);
        }
        chats.get(address).addMessage(message);
    }
}
