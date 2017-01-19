package ru.spbau.mit.proto;

import org.junit.Test;
import ru.spbau.mit.chat.ChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Author - Эдгар
 * Date - 12.01.2017, 0:40
 */
public class ChatProtocolTest {
    @Test
    public void testSend() throws Exception {
        PipedOutputStream pout = new PipedOutputStream();
        PipedInputStream pin = new PipedInputStream(pout);

        DataOutputStream out = new DataOutputStream(pout);
        DataInputStream in = new DataInputStream(pin);

        ChatMessage message = new ChatMessage("Me", "text", System.currentTimeMillis(), 1234);
        ChatProtocol.sendMessage(message, 8081, out);

        String name = in.readUTF();
        String text = in.readUTF();
        int port = in.readInt();
        assertEquals(name, "Me");
        assertEquals(text, "text");
        assertEquals(port, 8081);
    }

    @Test
    public void testReceive() throws Exception {
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream(pin);

        DataOutputStream out = new DataOutputStream(pout);
        DataInputStream in = new DataInputStream(pin);

        out.writeUTF("Me");
        out.writeUTF("text");
        out.writeInt(8081);

        ChatMessage message = ChatProtocol.receiveMessage(in);

        assertEquals(message.getName(), "Me");
        assertEquals(message.getText(), "text");
        assertEquals(message.getServerPortNumber(), 8081);
    }
}