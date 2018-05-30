package BrucknerD;
	
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.event.*;


/**
 * 
 * @author Dario
 * 
 * ClientGUI Klasse
 * 
 *
 */

		
public class ClientGUI extends JPanel {

	
	JButton jcomp1, jcomp2;
	JTextArea oberflaeche;
	JTextArea input, inputname;
	ChatClientSocket cl;
	ServerGUI ui;
	
	public ClientGUI() {
		
	}
	
	
	
	public ClientGUI(ChatClientSocket cl) {
		//construct components
		jcomp1 = new JButton ("send");
		oberflaeche = new JTextArea (5, 5);
        input = new JTextArea (5, 5);
        inputname = new JTextArea(5,5);
        //adjust size and set layout
        setPreferredSize (new Dimension (624, 367));
        setLayout (null);

        
        this.cl = cl;
        
        //add components
        add (jcomp1);
        add (oberflaeche);
        add (inputname);
        add (input);
        
        //add ActionHandler
        ActionHandler aha = new ActionHandler(); 
        jcomp1.addActionListener(aha);
              
        
        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (485, 335, 130, 25);
        oberflaeche.setBounds (15, 5, 595, 320);
        inputname.setBounds(15, 335, 100, 25);
        input.setBounds (135, 335, 340, 25);
        oberflaeche.setEditable(false);
        
	}
	
	/**
	 * 
	 * @author Dario
	 * 
	 * ActionHandlerklasse 
	 *
	 */
	
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent a){
			String back = input.getText();
			String name = inputname.getText();
			cl.sendtoserver(back,name);
			inputname.setEditable(false);
			
			//String output = oberflaeche.getText() + "Ich: " + back + "\n";
			//oberflaeche.setText(output);
			//oberflaeche.repaint();
			
			input.setText("");
		}
	}
	
	/**
	 * 
	 * @param input die Nachricht die geschickt wurde
	 * 
	 * Die gesendete Nachricht wird auf der GUI angegben
	 */
	
	public void showtext(String input) {
		String output = oberflaeche.getText() + input + "\n";
    	oberflaeche.setText(output);
    	oberflaeche.repaint();
	}
	
	
	
	public void exit() {
		System.exit(0);
	}
	
	
}
