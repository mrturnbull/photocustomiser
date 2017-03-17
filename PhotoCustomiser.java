import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PhotoCustomiser extends JFrame{

	static JFrame ptr = null;
	
	public PhotoCustomiser(){
		
		setVisible(false);
		ptr = this;
	
	}


	public static void main(String[] args){
	
		PhotoCustomiserMainWindow pcmw = new PhotoCustomiserMainWindow(null);
		pcmw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pcmw.setExtendedState(JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
		pcmw.setVisible(true);
			
		
	}

}