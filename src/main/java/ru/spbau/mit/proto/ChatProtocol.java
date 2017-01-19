package ru.spbau.mit.proto;

import lombok.extern.log4j.Log4j2;
import ru.spbau.mit.chat.ChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 2:12
 * Simple protocol for sending and receiving messages
 */
@Log4j2
public class ChatProtocol {
    /**
     * Sends message to network
     * @param message message to send
     * @param serverPort source port
     * @param out stream to write
     * @throws IOException if write failed
     */
    public static void sendMessage(ChatMessage message, int serverPort, DataOutputStream out) throws IOException {
        log.info("Sending message from: " + message.getName() + " with text: " + message.getText());
        out.writeUTF(message.getName());
        out.writeUTF(message.getText());
        out.writeInt(serverPort);
    }

    /**
     * Receives message from net
     * @param in input stream to read from
     * @return new message
     * @throws IOException if read failed
     */
    public static ChatMessage receiveMessage(DataInputStream in) throws IOException {
        String name = in.readUTF();
        String text = in.readUTF();
        int serverPort = in.readInt();
        log.info("Received message from: " + name + " with text: " + text);
        return new ChatMessage(name, text, Calendar.getInstance().getTimeInMillis(), serverPort);
    }
}
