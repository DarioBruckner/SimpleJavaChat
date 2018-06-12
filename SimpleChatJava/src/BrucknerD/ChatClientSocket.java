package BrucknerD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;


/**
 * 
 * @author Dario
 *
 * ChatClientSocket enthaelt alle Methoden furr das Senden und Empfangen der Nachrichten fuer Clients
 * 
 * 
 * 
 */
public class ChatClientSocket {
	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	ClientFxGui fb;
	ServerFxGui gui = ServerFxGui.getGui();
	String [] args;
	SimpleChatClient i;
	
	public ChatClientSocket() {
		
	}
	
	public ChatClientSocket(SimpleChatClient i) {
		//cli = new ChatClientSocket();
		this.i = i;

		startClient();
	}
	
	/**
	 * 
	 *
	 * 
	 * oeffnet die GUI und Startet den MessageListener Thread
	 * 
	 */
	
	public void startClient(){

		if(!createClient()){
			System.exit(0);
		}
		//Thread erstellen & Starten
		Thread t = new Thread(new MessagefromServerListener());
		t.start();
	
	}
	
	/**
	 * 
	 * @return gibt den Status zur?ck ob der Server Gestartet werden konnte.
	 * 
	 * Erstellt den ClientSocket und verbindet ihn mit dem Server
	 */
	
	public boolean createClient() {
		try {
			//ClientSocket
			client = new Socket("localhost", 5050);
			
			//MessageReader
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			//MessageWriter
			writer = new PrintWriter(client.getOutputStream());
			

			return true;
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Host konnte nicht gefunden werden");
			e.printStackTrace();
			return false;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException");
			return false;
		}
	}	
	
	/**
	 * 
	 * @param input Nachricht des Clients
	 * @param name  Name des Clients
	 * 
	 * Sendet Nachricht und Name an den Server
	 */
	
	public void sendtoserver(String input,String name) {
		if(name.equals(null) || name.equals(" ")) {
			name = "Noname";
		}
		
		writer.write(name +": " + input + "\n");
		writer.flush();
		
	}
	
	/**
	 * 
	 * @author Dario
	 * 
	 * 
	 * Thread um Nachrichten vom Server zu bekommen und diese auf der GUI anzuzeige
	 */




	public class MessagefromServerListener implements Runnable{

		@Override
		public void run() {
			String mes;
			
			try {
				while((mes = reader.readLine()) != null) {
					//Zeigt die Nachricht an
					i.showtext(mes);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				i.showtext("FEHLER");
				
			}
			
		}
		
	}
}
