package BrucknerD;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class ServerFxGui extends Application{

	private static ServerFxGui gui;
	TextArea output,clients;
	
	public ServerFxGui() {
    	gui = this;
    }

	public ServerFxGui(String [] args) {

		Application.launch(args);
		//Platform.exit();
	}
	
	
	@Override
	public void start(Stage serverpage) throws Exception {
		serverpage.setTitle("server");
		
		
		clients = new TextArea();
		output = new TextArea();
		
		
		
		
		FlowPane layout = new FlowPane();
		
		output.setMaxSize(480, 300);
		clients.setMaxSize(100, 300);
		output.setMinHeight(380);
		clients.setMinHeight(380);
		output.setEditable(false);
		clients.setEditable(false);
		
		
		layout.setPadding(new Insets(20));
		layout.setVgap(20);
		layout.setHgap(20);
		
		
		layout.getChildren().add(output);
		layout.getChildren().add(clients);
		
		
		Scene scene = new Scene(layout, 650,420);
		serverpage.setScene(scene);
		serverpage.show();
		
	} 
	
	
	public void showClients(String Client){
    	String outputs = clients.getText()+ Client + "\n";
    	clients.setText(outputs);
    }
	
	
	public void texttogui(String input) {
    	String outputs = output.getText() + input + "\n";
    	output.setText(outputs);
    }
	
	public static ServerFxGui getGui() {
    	return gui;
    }
	
}
