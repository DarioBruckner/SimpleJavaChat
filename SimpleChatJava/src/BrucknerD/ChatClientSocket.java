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
 * ChatClientSocket enthält alle Methoden für das Senden und Empfangen der Nachrichten für Clients
 * 
 * 
 * 
 */
public class ChatClientSocket extends JFrame{
	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	ClientGUI f;
	ChatClientSocket cli;
	ServerGUI gui = ServerGUI.getGui();
	
	
	public ChatClientSocket() {
		
	}
	
	public ChatClientSocket(int a) {
		cli = new ChatClientSocket();
		cli.startClient(cli);
	}
	
	/**
	 * 
	 * @param g Klassenobjekt in der ClientGUI auf diese Methoden zuzugreifen
	 * 
	 * Öffnet die GUI und Startet den MessageListener Thread
	 * 
	 */
	
	public void startClient(ChatClientSocket g){
		f = new ClientGUI(g);
		ChatClientSocket p = new ChatClientSocket();
		p.add(f);
		p.setSize(650, 420);
		p.setVisible(true);
	
		
		if(!createClient()){
			System.exit(0);
		}
		//Thread erstellen & Starten
		Thread t = new Thread(new MessagefromServerListener());
		t.start();
	
	}
	
	/**
	 * 
	 * @return gibt den Status zurück ob der Server Gestartet werden konnte.
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
					f.showtext(mes);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				f.showtext("FEHLER");
				
			}
			
		}
		
	}
}
