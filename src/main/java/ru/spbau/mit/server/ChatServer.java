package ru.spbau.mit.server;

import lombok.extern.log4j.Log4j2;
import ru.spbau.mit.chat.ChatManager;
import ru.spbau.mit.chat.ChatMessage;
import ru.spbau.mit.proto.ChatProtocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author - Эдгар
 * Date - 04.01.2017, 21:36
 * Server side of a chat
 */
@Log4j2
public class ChatServer {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final ChatManager manager;

    private ServerSocket serverSocket;

    public ChatServer(ChatManager manager) {
        this.manager = manager;
    }

    /**
     * Starts server on given port, accepts and dispatches connections
     * @param portNumber port number
     * @throws IOException if internal socket is failed to open
     */
    public void start(int portNumber) throws IOException {
        log.info("Starting server at port " + portNumber);
        serverSocket = new ServerSocket(portNumber);
        executor.execute(() -> {
            try {
                while (!serverSocket.isClosed()) {
                    Socket connection = serverSocket.accept();
                    log.info("Incoming connection, proceed to handle");
                    executor.execute(new ConnectionHandler(connection));
                }
            } catch (SocketException ignored) {

            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        });
    }

    /**
     * Suddenly, stops server
     * @throws IOException if failed to close internal socket
     */
    public void stop() throws IOException {
        serverSocket.close();
        executor.shutdown();
    }

    private class ConnectionHandler implements Runnable {
        private final Socket connection;
        private final DataInputStream in;

        ConnectionHandler(Socket connection) throws IOException {
            this.connection = connection;
            in = new DataInputStream(connection.getInputStream());
        }

        @Override
        public void run() {
            try {
                log.info("Handling connection from " + connection.getInetAddress().toString());
                ChatMessage message = ChatProtocol.receiveMessage(in);
                manager.updateChat(
                        new InetSocketAddress(
                                connection.getInetAddress(),
                                message.getServerPortNumber()
                        )
                        , message
                );
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
    }
}
