import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Main Klasse fuer den Server. Startet GUI und Funktionen
 *
 * @author Dario
 *
 */


public class SimpleChatServer extends Application {

    TextArea output,clients; //TextAreas
    ChatServerSocket c;
    SimpleChatServer serv;
    Button disconnect;


    /**
     * Main Methode
     * @param args
     *
     */
    public static void main(String[] args){


        launch(args);


	}

    /**
     * Started die Gui und den Server
     *
     * @param serverpage Die Gui
     * @throws Exception
     *
     */
    @Override
    public void start(Stage serverpage) throws Exception {
        serverpage.setTitle("server");

        clients = new TextArea();
        output = new TextArea();
        disconnect = new Button("disconnect");

        BorderPane border = new BorderPane();
        FlowPane layout = new FlowPane();


        VBox vbox = new VBox(2);

        output.setMaxSize(480, 300);
        clients.setMaxSize(100, 300);
        disconnect.setMaxSize(100,20);
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
        vbox.getChildren().add(disconnect);

        border.setRight(vbox);
        border.setCenter(layout);

        disconnect.setOnAction(e -> disconnected());

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
     *
     * Gibt die Clients auf der Gui des Servers aus
     *
     * @param list_clients Liste der Clients
     *
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
     * Gibt den input auf der Gui des Servers aus
     *
     * @param input input from client
     *
     */

    public void texttogui(String input) {
        String outputs = output.getText() + input + "\n";
        output.setText(outputs);
    }

    /**
     * disconnected alle clients vom server
     */

    public void disconnected(){
        c.sendtoClients("EXIT");
        c.list_clients.removeAll(c.list_clients);
        showClients(c.list_clients);
    }



    /**
     * Schliesst das Programm und die Prozesse
     *
     * @throws InterruptedException
     *
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
