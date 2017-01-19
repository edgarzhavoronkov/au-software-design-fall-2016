package ru.spbau.mit.ui;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.chat.Chat;
import ru.spbau.mit.chat.ChatMessage;
import ru.spbau.mit.client.ChatClient;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Author - Эдгар
 * Date - 05.01.2017, 0:32
 * Tab controller for single chat
 */
@Log4j2
public class ChatTabController implements Observer {
    private final Tab tab;
    private final ChatClient client;
    private final Chat chat;

    private final ListView history = new ListView();
    private final TextField inputField = new TextField();

    /**
     * C-tor, sets everything up
     * @param tab tab to display
     * @param client client to chat
     * @param chat chat to observe
     */
    public ChatTabController(Tab tab, ChatClient client, Chat chat) {
        this.tab = tab;
        this.client = client;
        this.chat = chat;

        this.tab.setText(chat.getPeerName());

        inputField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                try {
                    String message = inputField.getText();
                    client.sendMessage(message);
                    inputField.clear();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        });

        VBox vbox = new VBox(history);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.vvalueProperty().bind(vbox.heightProperty());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(inputField);

        tab.setContent(root);
    }

    /**
     * Updates chat with new message
     * @param o object to update
     * @param arg new message
     */
    @Override
    public void update(Observable o, Object arg) {
        ChatMessage message = (ChatMessage) arg;
        Platform.runLater(() -> {
            String userName = message.getName();
            if (!tab.getText().equals("") && !tab.getText().equals(userName)) {
                printInfoMessage(tab.getText() + " changed name to " + userName);
            }
            tab.setText(userName);
            printMessage(message);
        });

    }

    @SuppressWarnings("unchecked")
    private void printMessage(ChatMessage message) {
        String name = message.getName().equals("") ? client.getUserName() : chat.getPeerName();
        String timestamp = fromMillis(message.getTime());
        String msg = message.getText();
        String formatted = String.format("[%s] %s: \n %s \n", timestamp, name, msg);
        Text text = new Text(formatted);
        Color color = name.equals(client.getUserName()) ? Color.BLUE : Color.RED;
        text.setFill(color);
        text.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 14));
        history.getItems().add(text);
    }

    @SuppressWarnings("unchecked")
    private void printInfoMessage(String s) {
        Text text = new Text(s);
        text.setFill(Color.GRAY);
        text.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 12));
        history.getItems().add(text);
    }

    @NotNull
    private String fromMillis(long time) {
        Date date = new Date(time);
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }
}
