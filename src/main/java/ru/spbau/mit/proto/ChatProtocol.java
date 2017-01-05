package ru.spbau.mit.proto;

import ru.spbau.mit.chat.ChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 2:12
 */
public class ChatProtocol {
    public static void sendMessage(ChatMessage message, int serverPort, DataOutputStream out) throws IOException {
        out.writeUTF(message.getName());
        out.writeUTF(message.getText());
        out.writeInt(serverPort);
    }

    public static ChatMessage receiveMessage(DataInputStream in) throws IOException {
        String name = in.readUTF();
        String text = in.readUTF();
        int serverPort = in.readInt();
        return new ChatMessage(name, text, Calendar.getInstance().getTimeInMillis(), serverPort);
    }
}
