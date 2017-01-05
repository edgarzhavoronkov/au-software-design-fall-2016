package ru.spbau.mit.client;

import lombok.extern.log4j.Log4j2;
import ru.spbau.mit.chat.ChatManager;
import ru.spbau.mit.chat.ChatMessage;
import ru.spbau.mit.proto.ChatProtocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;

/**
 * Author - Эдгар
 * Date - 04.01.2017, 22:12
 */
@Log4j2
public class ChatClient {
    private final int portNumber;
    private final ChatManager manager;

    private String userName;
    private InetSocketAddress serverAddress;
    private boolean isConnected;
    private DataOutputStream out;

    public ChatClient(String userName, int portNumber, ChatManager manager) {
        this.userName = userName;
        this.portNumber = portNumber;
        this.manager = manager;
    }

    public void setUserName(String newName) {
        this.userName = newName;
    }

    public String getUserName() {
        return userName;
    }

    public void connect(String host, int port) {
        if (!isConnected) {
            serverAddress = new InetSocketAddress(host, port);
            isConnected = true;
        }
    }

    public void disconnect() throws IOException {
        if (isConnected) {
            out.close();
            isConnected = false;
        }
    }

    public void sendMessage(String message) throws IOException {
        Socket client = new Socket();
        client.connect(serverAddress);
        out = new DataOutputStream(client.getOutputStream());
        ChatMessage msg = new ChatMessage(userName, message, Calendar.getInstance().getTimeInMillis(), portNumber);
        ChatProtocol.sendMessage(msg, portNumber, out);
        manager.updateChat(serverAddress, msg);
        out.close();
    }
}
