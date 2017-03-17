import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class PhotoView extends JFrame{

	private JLabel lblDummy = null;
	
	public PhotoView(String fileName){
	
		
		lblDummy = new JLabel(new ImageIcon(fileName));
		lblDummy.setVisible(true);
		add(new JScrollPane(lblDummy));
		setTitle(fileName);
		setSize(500, 500);
		setVisible(true);
		
	
	
	}
	

}