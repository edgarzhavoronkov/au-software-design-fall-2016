package ru.spbau.mit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbau.mit.chat.Chat;
import ru.spbau.mit.client.ChatClient;
import ru.spbau.mit.chat.ChatManager;
import ru.spbau.mit.server.ChatServer;
import ru.spbau.mit.ui.ChatTabController;

import java.io.IOException;
import java.util.*;

/**
 * Author - Эдгар
 * Date - 04.01.2017, 18:15
 *
 * Main class of the application, gets username and server port number from
 * command line and starts
 */
public class ChatMain extends Application implements Observer {
    private static String userName;
    private static int portNumber;

    private final TabPane chatTabs = new TabPane();
    private final Tab addTab = new Tab("+");

    private final ChatManager manager = new ChatManager();
    private final ChatServer server = new ChatServer(manager);

    private final List<ChatClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            System.exit(-1);
        }

        userName = args[0];
        try {
            portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("TinyChat 9000");
        primaryStage.setResizable(false);
        chatTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        chatTabs.getTabs().add(makeNewChatTab());

        chatTabs.getTabs().add(addTab);
        chatTabs.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    if (newValue.equals(addTab)) {
                        Tab newTab = makeNewChatTab();
                        insertTabBeforeEnd(newTab);
                    }
                });

        MenuBar menu = new MenuBar();
        Menu optionsMenu = new Menu("User");
        MenuItem changeNameItem = new MenuItem("Change user name");

        optionsMenu.getItems().add(changeNameItem);
        menu.getMenus().add(optionsMenu);

        changeNameItem.setOnAction(event -> showChangeNameDialog());

        VBox layout = new VBox(menu, chatTabs);
        primaryStage.setScene(new Scene(layout, 640, 480));

        manager.addObserver(this);
        server.start(portNumber);

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
        super.stop();
    }

    @Override
    public void update(Observable o, Object arg) {
        Chat chat = (Chat) arg;
        Platform.runLater(() -> {
            Tab newTab = makeExistingChatTab(chat);
            insertTabBeforeEnd(newTab);
        });
    }

    private synchronized void insertTabBeforeEnd(Tab newTab) {
        chatTabs.getTabs().add(newTab);
        chatTabs.getTabs().remove(addTab);
        chatTabs.getTabs().add(addTab);
        chatTabs.getSelectionModel().select(newTab);
    }

    private synchronized Tab makeExistingChatTab(Chat chat)  {
        Tab ret = new Tab();
        ChatClient client = new ChatClient(userName, portNumber, manager);
        client.connect(chat.getPeerHost(), chat.getPeerPortNumber());

        ChatTabController chatTabController = new ChatTabController(ret, client, chat);
        chat.addObserver(chatTabController);

        clients.add(client);
        return ret;
    }

    private void showChangeNameDialog() {
        TextInputDialog dialog = new TextInputDialog(userName);
        dialog.setTitle("Change user name");
        dialog.setContentText("Enter new name:");
        dialog.setHeaderText("NB: your name will be changed in all chats");
        Optional<String> res = dialog.showAndWait();
        if (res.isPresent()) {
            userName = res.get();
            clients.forEach(chatClient -> chatClient.setUserName(userName));
        }
    }

    private Tab makeNewChatTab() {
        Tab ret = new Tab("New chat");

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(15, 15, 15, 15));

        Text sceneTitle = new Text("Connect to new peer");
        layout.add(sceneTitle, 0, 0, 2, 1);

        Label hostnameLabel = new Label("Hostname:");
        Label portLabel = new Label("Port number:");

        TextField hostnameField = new TextField();
        TextField portField = new TextField();

        Button connectButton = new Button("Connect");

        layout.add(hostnameLabel, 0, 1);
        layout.add(hostnameField, 0, 2);

        layout.add(portLabel, 0, 3);
        layout.add(portField, 0, 4);

        layout.add(connectButton, 0, 5);

        connectButton.setOnAction(event -> {
            try {
                String host = hostnameField.getText();
                int port = Integer.parseInt(portField.getText());
                ChatClient client = new ChatClient(userName, portNumber, manager);
                client.connect(host, port);
                synchronized (this) {
                    client.sendMessage(userName + " is knocking");
                    chatTabs.getTabs().remove(ret);
                }
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });

        ret.setContent(layout);
        return ret;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                alert.close();
            }
        });
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar <path_to_app>.jar <user_name> <port_number>");
    }
}
