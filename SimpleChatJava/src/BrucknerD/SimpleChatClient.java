package BrucknerD;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;


/**
 * 
 * @author Dario
 *
 *	Main Klasse fuer Client. Startet GUI und Funktionen
 *
 */



public class SimpleChatClient extends Application {

    String input = null;
	PrintWriter writer;
	OutputStream out;
	Socket client;
	SimpleChatClient scl;

    private Button send;
    private TextField ginput,gclient;
    private TextArea output;
    private ChatClientSocket cl;

    /**
     * Main Methode
     *
     */

	public static void main(String [] args){
		
		//Startet einen Client
        launch(args);


	}

    /**
     * @param clientpage Die GUI
     * @throws Exception
     *
     * Started die Gui und den Client
     *
     */
    @Override
    public void start(Stage clientpage) throws Exception {

	    startclient();
	    clientpage.setTitle("client");

	    scl = this;

        cl = new ChatClientSocket(scl);

        send = new Button("Senden");
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cl.sendtoserver(ginput.getText(),gclient.getText());
                gclient.setEditable(false);
            }
        });
        //User Eingabe => Nachricht
        ginput = new TextField();
        //User Eingabe => Username
        gclient = new TextField();
        //Nachrichten ausgabe
        output = new TextArea();

        FlowPane layout = new FlowPane();

        output.setMinSize(610, 330);
        output.setEditable(false);

        gclient.setMinWidth(70);
        ginput.setMinWidth(320);
        send.setMinWidth(100);

        layout.setPadding(new Insets(20));
        layout.setVgap(20);
        layout.setHgap(20);


        layout.getChildren().add(output);
        layout.getChildren().add(gclient);
        layout.getChildren().add(ginput);
        layout.getChildren().add(send);

        clientpage.setOnCloseRequest(e -> {
            try {
                closeProgramm();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });


        Scene scene = new Scene(layout, 650,420);
        clientpage.setScene(scene);
        clientpage.show();

    }



	/**
	 * 
	 * @author Dario
	 * 
	 * oeffnet einen Socket und setzt den outputstream
	 * 
	 * 
	 */
	
	
	public void startclient(){
		try {
			
			//Client
			client = new Socket("localhost", 5050);
			out = client.getOutputStream();
			
			
			
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}


    /**
     * @throws InterruptedException
     *
     * Schließt das Programm und beendet den MessageListener Process
     */
	public void closeProgramm() throws InterruptedException {
        cl.shutdown(gclient.getText());
	    cl.t.join();
    }

    /**
     * Beendet die Gui
     */
    public void shutGUI(){
        Platform.exit();
    }

	
	/**
	 * @author Dario
	 * @param input des Clients auf der GUI
	 * 
	 * Sendet die Nachricht an den Outputstream 
	 */

    
    public void showtext(String input) {
        String outputs = output.getText() + input + "\n";
        output.setText(outputs);

    }
}
