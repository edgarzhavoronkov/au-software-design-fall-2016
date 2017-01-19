package ru.spbau.mit.client;

import lombok.Getter;
import lombok.Setter;
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
 *
 * Client side of chat
 */
@Log4j2
public class ChatClient {
    private final int portNumber;
    private final ChatManager manager;

    private InetSocketAddress serverAddress;
    private boolean isConnected;

    @Getter
    @Setter
    private String userName;

    /**
     * C-tor
     * @param userName our name
     * @param portNumber our port number
     * @param manager chat manager to manage chats
     */
    public ChatClient(String userName, int portNumber, ChatManager manager) {
        this.userName = userName;
        this.portNumber = portNumber;
        this.manager = manager;
    }

    /**
     * Set up connection to given host and port
     * @param host host name
     * @param port port number
     */
    public void connect(String host, int port) {
        if (!isConnected) {
            serverAddress = new InetSocketAddress(host, port);
            isConnected = true;
        }
    }

    /**
     * Sends message to address, given in connect()
     * @param message message to send
     * @throws IOException if protocol failed to send message
     */
    public void sendMessage(String message) throws IOException {
        Socket client = new Socket();
        client.connect(serverAddress, 100);

        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        ChatMessage msg = new ChatMessage(userName, message, Calendar.getInstance().getTimeInMillis(), portNumber);
        ChatProtocol.sendMessage(msg, portNumber, out);

        manager.updateChat(serverAddress, msg);
        client.close();
    }
}
