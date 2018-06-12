package BrucknerD;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class ClientFxGui extends Application implements EventHandler<ActionEvent>{


	private Button send;
	private TextField input,client;
	private TextArea output;
	private ChatClientSocket cl;

	
	public ClientFxGui(String [] args, ChatClientSocket cl) {
		Application.launch(args);
	}

	//public static void setCl(ChatClientSocket cl){
	//	_instance.cl = cl;
	//}

	@Override
	public void start(Stage clientpage) throws Exception {
		clientpage.setTitle("client");
		
		send = new Button("Senden");
		send.setOnAction(this);
		input = new TextField();
		client = new TextField();
		output = new TextArea();

		FlowPane layout = new FlowPane();
		
		output.setMinSize(610, 330);
		output.setEditable(false);
		
		client.setMinWidth(70);
		input.setMinWidth(320);
		send.setMinWidth(100);
		
		layout.setPadding(new Insets(20));
		layout.setVgap(20);
		layout.setHgap(20);
		
		
		layout.getChildren().add(output);
		layout.getChildren().add(client);
		layout.getChildren().add(input);
		layout.getChildren().add(send);
		
		Scene scene = new Scene(layout, 650,420);
		clientpage.setScene(scene);
		clientpage.show();
		
	}


	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()== send) {
			String back = input.getText();
			String name = client.getText();
			client.setEditable(false);
			cl.sendtoserver(back,name);
		}	
	}
	
	
	public void showtext(String input) {
		String outputs = output.getText() + input + "\n";
    	output.setText(outputs);
	}
}
