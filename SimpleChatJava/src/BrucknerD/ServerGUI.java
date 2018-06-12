package BrucknerD;
import java.awt.Dimension;

import javax.swing.*;



/**
 *  
 * @author Dario
 * 
 * GUI Klasse
 * 
 */


public class ServerGUI extends JPanel {
    
	private static ServerGUI gui;
    private JTextArea oberflaeche;
    private JTextArea aussehen;
 
 
    public ServerGUI() {
    	gui = this;
    }
    
    public ServerGUI(String mes) {
        //construct components
    	gui = this;
        
        oberflaeche = new JTextArea (5, 5);
        aussehen = new JTextArea (5, 5);

        //adjust size and set layout
        setPreferredSize (new Dimension (624, 367));
        setLayout (null);

        //add components
        add (oberflaeche);
        add (aussehen);

        //set component bounds
        oberflaeche.setBounds (15, 5, 461, 364);
        aussehen.setBounds (480, 5, 130, 325);

        oberflaeche.setEditable(false);
        aussehen.setEditable(false);
        
        
        
    }
    
    /**
     * 
     * @author Dario
 
   
    /**
     * 
     * @param input fuer die GUI
     * 
     * Zeigt die Nachricht in der GUI an
     * 
     */
    
    public void texttogui(String input) {
    	String output = oberflaeche.getText() + input + "\n";
    	oberflaeche.setText(output);
    	oberflaeche.repaint();
    	
    }
    
    /**
     * 
     * @param Client
     *
     * Zeigt die Clients in der GUI an
     */
    
    public void showClients(String Client){
    	String output = aussehen.getText()+ Client + "\n";
    	aussehen.setText(output);
    	aussehen.repaint();
    }
    
    public static ServerGUI getGui() {
    	return gui;
    }
}