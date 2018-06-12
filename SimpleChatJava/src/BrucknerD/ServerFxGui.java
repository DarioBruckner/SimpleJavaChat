package BrucknerD;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class ServerFxGui extends Application{

	private static ServerFxGui gui;
	TextArea output,clients;
	Button conncect;
	ChatServerSocket c;

	private static ChatServerSocket _instance;

	public ServerFxGui() {
    	gui = this;
    }

	public ServerFxGui(ChatServerSocket c , String [] args) {
		this.c = c;
		Application.launch(args);
		//Platform.exit();
	}

	
	@Override
	public void start(Stage serverpage) throws Exception {
		serverpage.setTitle("server");
		
		
		clients = new TextArea();
		output = new TextArea();
		conncect = new Button("Connect");
		conncect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				c.acceptclients();
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
		Scene scene = new Scene(border, 650,420);
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

    public static ChatServerSocket get_instance(){
		return _instance;
	}

	public static void set_instance(ChatServerSocket _instancee){
		_instance = _instancee;
	}
}
