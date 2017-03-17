import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MenuAbout_ActionListener implements ActionListener{
		
	JFrame ptrFrame = null;
	
	public MenuAbout_ActionListener(JFrame ptrFrame){
		this.ptrFrame = ptrFrame;		
	}
	
	public void actionPerformed(ActionEvent e){
	
		//JOptionPane.showMessageDialog(ptrFrame, "Desenvolvido por DUNEDIN SOFTWARE LTDA\nContato: marketing@dunedin.com.br", "DUNEDIN SOFTWARE LTDA", JOptionPane.INFORMATION_MESSAGE, iconBotom);
		JOptionPane.showMessageDialog(ptrFrame, "\nDUNEDIN SOFTWARE LTDA\n\nmarketing@dunedin.com.br", "Desenvolvedor", JOptionPane.PLAIN_MESSAGE);
		
	}
	
}