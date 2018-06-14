package BrucknerD;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * 
 * @author Dario
 * 
 * Main Klasse fuer den Server. Startet GUI und Funktionen
 * 
 */


public class SimpleChatServer extends Application {

    TextArea output,clients;
    Button conncect;
    ChatServerSocket c;
    SimpleChatServer serv;

    /**
     * @param args
     *
     * Main Methode
     */
    public static void main(String [] args){

		//SimpleChatServer p = new SimpleChatServer();
        launch(args);
		//Startet Server
		//ChatServerSocket chat = new ChatServerSocket(args);

	}

    /**
     * @param serverpage Die Gui
     * @throws Exception
     *
     * Started die Gui und den Server
     *
     */
    @Override
    public void start(Stage serverpage) throws Exception {
        serverpage.setTitle("server");

        clients = new TextArea();
        output = new TextArea();
        conncect = new Button("Start Server");
        conncect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        BorderPane border = new BorderPane();
        FlowPane layout = new FlowPane();

        VBox vbox = new VBox(2);

        output.setMaxSize(480, 300);
        clients.setMaxSize(100, 300);
        conncect.setMaxSize(100,20);
        output.setMinHeight(380);
        clients.setMinHeight(340);
        output.setEditable(false);
        clients.setEditable(false);



        layout.setPadding(new Insets(20));
        layout.setVgap(20);
        layout.setHgap(20);

        vbox.setPadding(new Insets(20,40,40,20));
        vbox.setSpacing(20);



        layout.getChildren().add(output);

        vbox.getChildren().add(clients);
        vbox.getChildren().add(conncect);


        border.setRight(vbox);
        border.setCenter(layout);

        serverpage.setOnCloseRequest(e -> {
            try {
                closeProgramm();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(border, 650,420);
        serverpage.setScene(scene);
        serverpage.show();

        serv = this;

        c = new ChatServerSocket(serv);

    }

    /**
     * @param list_clients Liste der Clients
     *
     * Gibt die Clients auf der Gui des Servers aus
     */

    public void showClients(ArrayList<String> list_clients){
        String output = "";
        for(int i = 0; i < list_clients.size(); i++){
            output = output + list_clients.get(i) + "\n";
        }


       // String outputs = clients.getText()+ Client + "\n";
        clients.setText(output);
    }

    /**
     * @param input input from client
     *
     * Gibt den input auf der Gui des Servers aus
     *
     */

    public void texttogui(String input) {
        String outputs = output.getText() + input + "\n";
        output.setText(outputs);
    }

    /**
     * @throws InterruptedException
     *
     * Schließt das Programm und die Prozesse
     */

    public void closeProgramm() throws InterruptedException {

        //Beendet alle Clientprozzesse
        c.sendtoClients("EXIT");

        try {
            c.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Beendet den acceptclients Thread
        c.r.join();

        c.shutdown();
        //Beendet den Clienthandler thread
        try {
            c.clientThread.join();
        }catch(NullPointerException e){

        }
    }

}
