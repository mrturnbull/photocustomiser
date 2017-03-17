import javax.imageio.ImageIO;

import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.JCheckBox;
import javax.swing.Timer;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.print.SimpleDoc;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.PrintException;

import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;

import javax.print.attribute.*;
import javax.print.attribute.standard.PrinterResolution;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobAttributeEvent;
import javax.print.event.PrintJobAttributeListener;
import javax.print.attribute.standard.JobMediaSheetsCompleted;

import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.JobAttributes;
import java.awt.PageAttributes;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Frame;
import java.awt.Font;
import java.awt.FlowLayout;

import java.io.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import java.lang.Thread;
import java.lang.Runnable;

import java.nio.channels.*;

//import com.apple.eawt.Application;

public class PhotoCustomiserMainWindow extends JFrame {

	private int TIMER_CYCLE_MS = 60000;

	private int frameW = 200;
	private int frameH = 200;
	private int leftMarginW = 0;
	private int leftBorderW = 0;
	private int topMarginH = 0;
	private int topBorderH = 0;
	private int rightMarginW = 0;
	private int rightBorderW = 0;
	private int bottomMarginH = 0;
	private int bottomBorderH = 0;

	String sep = "";
	
	JMenuBar menuBar = null;
	JMenu menu = null;
	JMenuItem menuItemDeveloper = null;
	
	ImageIcon iconBotom = null;

	JLabel lblPhotoSourceFolder = null;
	JLabel lblPhotoWithFrame = null;
	JLabel lblPhotoPrinted = null;

	JList listPhotoSourceFolder = null;
	JList listFramedPhoto = null;
	JList listPrintedPhoto = null;
	
	DefaultListModel listModelPhotoSourceFolder = null;
	DefaultListModel listModelFramedPhoto = null;
	DefaultListModel listModelPrintedPhoto = null;
	
	JTextField txtPhotoSourceFolderPath = null;
	JTextField txtFramedPhotoPath = null;
	
	JButton btnSetPhotoSourceFolderPath = null;
	JButton btnAddFrame = null;
	JButton btnPrint = null;
	JButton btnFileChooser = null;
	JButton btnFileChooserFramed = null;
	JButton btnRefreshPhotoSourceFolder = null;
	JCheckBox chkAutoRefreshPhotoSourceFolder = null;
	
	JPanel panelPhoto = null;
	JPanel panelTopControls = null;
	JPanel panelBottomControls = null;
	JPanel panelAllControls = null;
	JPanel panelLeft = null;
	JPanel panelCentre = null;
	JPanel panelRight = null;
	JPanel panelPSFP = null;
	JPanel panelFileChooserFramed = null;
	
	//private final String PHOTO_SOURCE_FOLDER = 
	JFileChooser fileChooser = null;
	
	private String photoSourceFolderPath = "";
	public  String framedPhotoPath = "";
	//private String photoSourceFolderPath = "/Volumes/dragon/a";
	
	RefreshPhotoSourceFolder_ActionListener rpsfActionListener = null;
	btnAddFrame_ActionListener afActionListener = null;
	
	public PhotoCustomiserMainWindow pMain = null;
	
	Timer timer = null;
	
	public PhotoCustomiserMainWindow(String photoSourceFolderPath){
	
		this.pMain = this;
	
		this.photoSourceFolderPath = photoSourceFolderPath;
	
		this.setTitle("PSDB 45 - Santinho do Perfil");
		
		//sep = System.getProperty("path.separator");
		sep = File.separator;
		
		timer = new Timer(TIMER_CYCLE_MS, new TimerRefresh_ActionListener());
		
		/****************************************************************/
		
		menuBar = new JMenuBar();
		menu = new JMenu("Sobre");
		menuItemDeveloper = new JMenuItem("Desenvolvedor");
		MenuAbout_ActionListener x = new MenuAbout_ActionListener(this);
		menuItemDeveloper.addActionListener(x);
		
		menu.add(menuItemDeveloper);
		menuBar.add(menu);
		
		this.setJMenuBar(menuBar);
		
		
		/****************************************************************/
		
		iconBotom = new ImageIcon("./botom.jpg", "a pretty but meaningless splat");
		
		/****************************************************************/
		panelAllControls = new JPanel();
		panelAllControls.setLayout(new BoxLayout(panelAllControls, BoxLayout.Y_AXIS));
		
		JPanel panelTopHeadline = new JPanel();
		panelTopHeadline.setLayout(new FlowLayout());
		
		JLabel headline1 = new JLabel("PSDB");
		headline1.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 120));
		headline1.setForeground(Color.BLUE);
		panelTopHeadline.add(headline1);
		
		JLabel headline2 = new JLabel("45");
		headline2.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 120));
		headline2.setForeground(Color.YELLOW);
		panelTopHeadline.add(headline2);
		
		panelAllControls.add(panelTopHeadline);
		
		panelTopControls = new JPanel();
		panelTopControls.setLayout(new BoxLayout(panelTopControls, BoxLayout.X_AXIS));
		
		panelPSFP = new JPanel();
		panelPSFP.setLayout(new BoxLayout(panelPSFP, BoxLayout.X_AXIS));
		
		panelFileChooserFramed = new JPanel();
		panelFileChooserFramed.setLayout(new BoxLayout(panelFileChooserFramed, BoxLayout.X_AXIS));
		
		//panelAllControls.add(new Box.createRigidArea(new Dimension(5,100)));
	
		panelLeft = new JPanel();
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		lblPhotoSourceFolder = new JLabel("FOTO INICIAL");
		lblPhotoSourceFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPhotoSourceFolder.setFont(new Font("Arial", Font.BOLD, 30));
		lblPhotoSourceFolder.setForeground(Color.BLUE);
		panelLeft.add(lblPhotoSourceFolder);
		
		listPhotoSourceFolder = new JList();
		
		JScrollPane listScroller1 = new JScrollPane(listPhotoSourceFolder);
		listScroller1.setPreferredSize(new Dimension(25, 500));
		panelLeft.add(listScroller1);
		
		btnFileChooser = new JButton("Pasta");
		panelPSFP.add(btnFileChooser);
		
		txtPhotoSourceFolderPath = new JTextField(photoSourceFolderPath);
		//txtPhotoSourceFolderPath.setMaximumSize(new Dimension(300, 25));
		//txtPhotoSourceFolderPath.setPreferredSize(new Dimension(300, 25));
		panelPSFP.add(txtPhotoSourceFolderPath);
		
		btnRefreshPhotoSourceFolder = new JButton("Atualizar");
		panelPSFP.add(btnRefreshPhotoSourceFolder);
		
		chkAutoRefreshPhotoSourceFolder = new JCheckBox("Auto Refresh");
		chkAutoRefreshPhotoSourceFolder.setVisible(false);
		panelPSFP.add(chkAutoRefreshPhotoSourceFolder);
				
		panelLeft.add(panelPSFP);
		
		
		JPanel panelPickLayout = new JPanel();
		JComboBox cmbPickLayout = new JComboBox(new String[]{"Moldura 400 x 400", "Moldura 200 x 200"});
		panelPickLayout.add(cmbPickLayout);
		
		panelLeft.add(panelPickLayout);
		
		panelTopControls.add(panelLeft);
		
		/* ------------- Coluna 2 ---------	*/
		
		btnAddFrame = new JButton(">");
		btnAddFrame.setToolTipText("Adicionar");
		panelTopControls.add(btnAddFrame);
	
		/* ------------- Coluna 3 ---------	*/
		
		panelCentre = new JPanel();		
		panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
		lblPhotoWithFrame = new JLabel("FOTO PRONTA");
		lblPhotoWithFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPhotoWithFrame.setFont(new Font("Arial", Font.BOLD, 30));
		lblPhotoWithFrame.setForeground(Color.BLUE);
		panelCentre.add(lblPhotoWithFrame);
		
		listFramedPhoto = new JList(new String[]{"Nenhuma"});
		JScrollPane listScroller2 = new JScrollPane(listFramedPhoto);
		listScroller2.setPreferredSize(new Dimension(25, 500));
		panelCentre.add(listScroller2);
		
		btnFileChooserFramed = new JButton("Pasta");
		panelFileChooserFramed.add(btnFileChooserFramed);
		
		txtFramedPhotoPath = new JTextField(framedPhotoPath);
		//txtFramedPhotoPath.setMaximumSize(new Dimension(300, 25));
		//txtFramedPhotoPath.setPreferredSize(new Dimension(300, 25));
		panelFileChooserFramed.add(txtFramedPhotoPath);
		
		panelCentre.add(panelFileChooserFramed);
		panelTopControls.add(panelCentre);
		
		/* ------------- Coluna 4 ---------	*/	
		
		btnPrint = new JButton(">");
		btnPrint.setToolTipText("Print");
		panelTopControls.add(btnPrint);

		/* ------------- Coluna 5 ---------	*/

		panelRight = new JPanel();
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		lblPhotoPrinted = new JLabel("IMPRESSÃO");
		lblPhotoPrinted.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPhotoPrinted.setFont(new Font("Arial", Font.BOLD, 30));
		lblPhotoPrinted.setForeground(Color.BLUE);
		panelRight.add(lblPhotoPrinted);
		listPrintedPhoto = new JList(new String[]{"Nenhuma"});
		JScrollPane listScroller3 = new JScrollPane(listPrintedPhoto);
		//listScroller3.setPreferredSize(new Dimension(100, 50));
		panelRight.add(listScroller3);
		panelTopControls.add(panelRight);
	
		panelAllControls.add(panelTopControls);
		
		//panelAllControls.add(new JLabel(new ImageIcon("/Users/marcelloturnbull/Desktop/Java/AddFrame/IMG_0687.JPG")));
	
		panelAllControls.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10));
	
		add(panelAllControls);
		
		
		
		
		//showDirectory("jpg", photoSourceFolderPath, this.listPhotoSourceFolder, this.listModelPhotoSourceFolder);
		//showDirectory(photoSourceFolderPath + sep + "framed", this.listFramedPhoto, this.listModelFramedPhoto);
		
		listPhotoSourceFolder.addMouseListener(new DoubleClickItemMouseAdapter(listPhotoSourceFolder));
		listFramedPhoto.addMouseListener(new DoubleClickItemMouseAdapterFramed(listFramedPhoto));
		
		//btnFileChooser.addActionListener(new RefreshPhotoSourceFolder_ActionListener(photoSourceFolderPath, this.listPhotoSourceFolder, this.listModelPhotoSourceFolder, listFramedPhoto, listModelFramedPhoto));
		btnRefreshPhotoSourceFolder.addActionListener(rpsfActionListener = new RefreshPhotoSourceFolder_ActionListener(photoSourceFolderPath, this.listPhotoSourceFolder, this.listModelPhotoSourceFolder, listFramedPhoto, listModelFramedPhoto));
		
		chkAutoRefreshPhotoSourceFolder.addActionListener(new chkAutoRefreshPhotoSourceFolder_ActionListener());
		
		btnAddFrame.addActionListener(new btnAddFrame_ActionListener());
		
		btnPrint.addActionListener(new btnPrint_ActionListener());
		
		btnFileChooser.addActionListener(new SetSourceFolderPath_ActionListener(txtPhotoSourceFolderPath, listPhotoSourceFolder, listModelPhotoSourceFolder));
		
		btnFileChooserFramed.addActionListener(new btnFileChooserFramed_ActionListener(this.framedPhotoPath));
	
	}
	
	public class chkAutoRefreshPhotoSourceFolder_ActionListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
		
			if (chkAutoRefreshPhotoSourceFolder.isSelected()){
				timer.start();
				
			}
			else {
				timer.stop();
				
			}
		
		}
	
	
	}
	
	
	public class TimerRefresh_ActionListener implements ActionListener {
	
		public TimerRefresh_ActionListener(){
		}
	
		public void actionPerformed(ActionEvent e){
		
			showDirectory("jpg", photoSourceFolderPath, listPhotoSourceFolder, listModelPhotoSourceFolder);
			
		}	
	
	}
	
	
	public class btnFileChooserFramed_ActionListener implements ActionListener {
	
		private JFileChooser fileChooserFramed = null;
		private String framedPhotoPath = "";
		
		public btnFileChooserFramed_ActionListener(String framedPhotoPath){
			this.framedPhotoPath = framedPhotoPath;
			fileChooserFramed = new JFileChooser();
			fileChooserFramed.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		public void actionPerformed(ActionEvent e){
		
			int returnVal = fileChooserFramed.showOpenDialog(PhotoCustomiserMainWindow.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dirFramed = fileChooserFramed.getSelectedFile();
                txtFramedPhotoPath.setText(dirFramed.getPath());
                pMain.framedPhotoPath = dirFramed.getPath();
				
				File dirPrinted = new File(pMain.framedPhotoPath + sep + "printed");
				if (!dirPrinted.isDirectory()) dirPrinted.mkdir();				
			
				showDirectory("png", pMain.framedPhotoPath, listFramedPhoto, listModelFramedPhoto);
				showDirectory("png", pMain.framedPhotoPath + sep + "printed", listPrintedPhoto, listModelPrintedPhoto);
                
            } else {
            
            }
		
		}
	
	}

	
	public class SetSourceFolderPath_ActionListener implements ActionListener {
	
		private JFileChooser fileChooser = null;
		private JTextField txt = null;
		private JList myList = null;
		private DefaultListModel myDefaultListModel = null;
	
		public SetSourceFolderPath_ActionListener(JTextField txt, JList myList, DefaultListModel myDefaultListModel){
			this.txt = txt;
			this.myList = myList;
			this.myDefaultListModel = myDefaultListModel;
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		public void actionPerformed(ActionEvent e){
		
			int returnVal = fileChooser.showOpenDialog(PhotoCustomiserMainWindow.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                
                File dirRoot = fileChooser.getSelectedFile();
                txt.setText(dirRoot.getPath());
                photoSourceFolderPath = dirRoot.getPath();
			
				
                btnRefreshPhotoSourceFolder.removeActionListener(rpsfActionListener);
                btnRefreshPhotoSourceFolder.addActionListener(rpsfActionListener = new RefreshPhotoSourceFolder_ActionListener(photoSourceFolderPath, myList, myDefaultListModel, listFramedPhoto, listModelFramedPhoto));
                
                
            } else {
            }
		
		}
	
	}
	
	
	
	
		
	
	
	
	
	
		
	
	
	
	public class PrintRunnable implements Runnable{
	
		BufferedImage biFramedPhoto = null;
		PageFormat pf = null;			
		Paper paper = null;
		String fileName = "";
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		
		public PrintRunnable(String fileName){
			this.fileName = fileName;		
		}
	
		public void run() {			
				
			File fFramed = new File(pMain.framedPhotoPath + sep + fileName);
			System.out.println(pMain.framedPhotoPath + sep + fileName);
			
			try {
				biFramedPhoto = ImageIO.read(fFramed);
			}
			catch(Exception ex){
				System.out.println(ex.getMessage());		
			}
			
			boolean isOldPrintingMethod = false;
			
			if (isOldPrintingMethod){
			
				PrinterJob job = PrinterJob.getPrinterJob();
			
				pf = job.defaultPage();
				pf.setOrientation(PageFormat.LANDSCAPE);
				paper = new Paper();
			
				//paper.setImageableArea(margin, margin, paper.getWidth() - 2 * margin, paper.getHeight() - 2 * margin);
				pf.setPaper(paper);
			
				job.setPrintable(new FramedPhoto(biFramedPhoto), pf);
			
				PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
				printAttributes.add(PrintQuality.HIGH);
				printAttributes.add(OrientationRequested.LANDSCAPE);
			
				try {
					job.print(printAttributes);
				}
				catch (PrinterException pex){
					System.out.println(pex.getMessage());
				}
				
			}
			else {
			
				PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
				printAttributes.add(PrintQuality.HIGH);
				printAttributes.add(OrientationRequested.LANDSCAPE);
			
				PrintService service = PrintServiceLookup.lookupDefaultPrintService();
							
				FileInputStream fis = null;
				
				DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE; //DocFlavor.INPUT_STREAM.PNG doesn't work
				Doc doc = new SimpleDoc(new FramedPhoto(biFramedPhoto), flavor, null);
				
				DocPrintJob job = service.createPrintJob();
				
				MyPrintJobListener printJobListener = new MyPrintJobListener();
				
				job.addPrintJobListener(printJobListener);			
								
				try {
				
					job.print(doc, printAttributes);
					printJobListener.waitForDone();
					int i = printJobListener.getStatus();
					if ((i == 4) || (i == 2)) {					
						
						File fPrinted = new File(framedPhotoPath + sep + "printed" + sep + fileName);
											
						inputChannel = new FileInputStream(fFramed).getChannel();
						outputChannel = new FileOutputStream(fPrinted).getChannel();
						outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
				
						inputChannel.close();
						outputChannel.close();

						fFramed.delete();
						
						EventQueue.invokeLater(new Runnable(){
							public void run(){		
								showDirectory("png", pMain.framedPhotoPath, listFramedPhoto, listModelFramedPhoto);
								showDirectory("png", pMain.framedPhotoPath + sep + "printed", listPrintedPhoto, listModelPrintedPhoto);
							}
						});	
					
					}
				}
				catch (IOException io){
					System.out.println(io.getMessage());
				}
				catch (PrintException pex){
					System.out.println(pex.getMessage());
				}
			
			}		
		
		}
	
	}
	
	public class MyPrintJobListener extends PrintJobAdapter{
				
		int iResult = 0;

		public void printDataTransferCompleted(PrintJobEvent pje) {
			// The print data has been transferred to the print service
		}

		public void printJobCanceled(PrintJobEvent pje) {
			// The print job was cancelled
			setStatus(1);
		}
		
		public void printJobCompleted(PrintJobEvent pje) {
			// The print job was completed
			setStatus(2);
		}
		
		public void printJobFailed(PrintJobEvent pje) {
		   // The print job has failed
		   setStatus(3);
		}
					
		public void printJobNoMoreEvents(PrintJobEvent pje) {
		    // No more events will be delivered from this
			// print service for this print job.
			// This event is fired in cases where the print service
			// is not able to determine when the job completes.
		    setStatus(4);
		}

		public void printJobRequiresAttention(PrintJobEvent pje) {
		   // The print service requires some attention to repair
		   // some problem.
		   // Example: running out of paper would cause this event
		   // to be fired.
		   setStatus(5);
		}

		void setStatus(int iStatus) {
		   synchronized (MyPrintJobListener.this) {
			  iResult = iStatus;
			  MyPrintJobListener.this.notify();
		   }
		}
		
		public int getStatus() {
		   synchronized (MyPrintJobListener.this) {
			  return iResult;			  
		   }
		}
					
		public synchronized int waitForDone() {
			try {
			   // If no event were executed or if only the data transfert event were
			   // launched executed or if the canceled job event were launched there
			   // is nothing to be done.
			   while (iResult==0)
					  wait();
			   
			}
			catch (InterruptedException e) {
			}
			return iResult;
		}
	
	}	
	
	
	public class btnPrint_ActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){	
		
			Object objFileName = "";
			String fileName = "";
		
			if ((objFileName = listFramedPhoto.getSelectedValue()) == null){				
				JOptionPane.showMessageDialog(PhotoCustomiserMainWindow.this, "Selecione uma foto pronta para impressão", "Atenção", JOptionPane.PLAIN_MESSAGE);
				return;				
			}
			
			fileName = objFileName.toString();
		
			Runnable printRunnable = new PrintRunnable(fileName);
			Thread threadPrint = new Thread(printRunnable);
			threadPrint.start();
			
		}
		
	}
	
	public class FramedPhoto implements Printable {
	
		BufferedImage bi = null;
	
		public FramedPhoto(BufferedImage bi){
			this.bi = bi;	
		}
		
		public int print(Graphics g, PageFormat pf, int pageIndex){
		
			double xScale = 0.24; // 72 dpi / 300 dpi
			double yScale = 0.24;
		
			if (pageIndex != 0) return NO_SUCH_PAGE;
			
			Graphics2D g2D = (Graphics2D) g;
			
			double xMargin = (pf.getImageableWidth() - bi.getWidth() * xScale)/2;
			double yMargin = (pf.getImageableHeight() - bi.getHeight() * yScale)/2;
			g2D.translate(pf.getImageableX() + xMargin, pf.getImageableY() + yMargin);

			g2D.scale(xScale, yScale);
			g2D.setPaint(Color.black);
			g2D.drawImage(bi, 0, 0, null);
			
			return PAGE_EXISTS;
		
		}
	
	} 
	
	public class btnAddFrame_ActionListener implements ActionListener{
	
		private String fileName = "";
		
		////////////////////////////////////
		
		
		public btnAddFrame_ActionListener(){
		
		}
		
		public void actionPerformed(ActionEvent e){
			
			Object objFileName = "";
		
			if ((objFileName = listPhotoSourceFolder.getSelectedValue()) == null){
				
				JOptionPane.showMessageDialog(PhotoCustomiserMainWindow.this, "Selecione uma foto inicial", "Atenção", JOptionPane.PLAIN_MESSAGE);
			
			}
			else { 
			
				fileName = objFileName.toString();
								
				if (fileName.length() > 0){					
			
					if (pMain.framedPhotoPath.length() > 0){
					
							double barWidth = 400.0;
							double barHeight = 200.0;
							
							JLabel barLbl = new JLabel(fileName);
	
							JProgressBar bar = new JProgressBar();
							bar.setIndeterminate(true);
							
							JPanel barPanel = new JPanel();
							barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.X_AXIS));
							barPanel.add(barLbl);
							barPanel.add(Box.createRigidArea(new Dimension(20, 0)));
							barPanel.add(bar);
							
							barPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
																		
							JDialog progressBarDlg = new JDialog(pMain, "Framing...", false);
							progressBarDlg.setContentPane(barPanel);
							progressBarDlg.setSize((int)barWidth, (int)barHeight);
							progressBarDlg.setLocationRelativeTo(null);
							progressBarDlg.setVisible(true);						
						
							Runnable addFrameRunnable = new AddFrameRunnable(fileName, progressBarDlg);
							Thread threadAddFrame = new Thread(addFrameRunnable);
							threadAddFrame.start();
						
			
					}
					else {
			
						JOptionPane.showMessageDialog(PhotoCustomiserMainWindow.this, "Escolha a pasta para salvar as fotos prontas", "Atenção", JOptionPane.PLAIN_MESSAGE);
			
					}
			
				}
				else {
			
					JOptionPane.showMessageDialog(PhotoCustomiserMainWindow.this, "Selecione uma foto", "Atenção", JOptionPane.PLAIN_MESSAGE);
			
				}
		
			}
			
		}
		
		
		
	}
	
	public class AddFrameRunnable implements Runnable {	
	
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
				
		BufferedImage frame = null;
		BufferedImage photo = null;
		BufferedImage resizedPhoto = null;
		BufferedImage frameBottom = null;
		BufferedImage frameTop = null;
		BufferedImage frameLeft = null;
		BufferedImage frameRight = null;		
		BufferedImage merged = null;
		
		Graphics g = null;
		Graphics2D g2D = null;	
		
		String path = "";
		String fileNamePNG = "";
		String fileName = "";		
		
		private int photoW = 0;
		private int photoH = 0;		
		private int holeW = 0;
		private int holeH = 0;
		
		private final JDialog progressBarDlg;
		
		public AddFrameRunnable(String fileName, JDialog progressBarDlg){		
			this.fileName = fileName;
			this.progressBarDlg = progressBarDlg;
		}
		
		
		public void run() {
		
			try {
						
				photo = ImageIO.read(new File(photoSourceFolderPath, fileName));
				
				File pngFile = new File(photoSourceFolderPath, fileName.substring(0, fileName.indexOf(".")) + ".png");
				ImageIO.write(photo, "png", pngFile);
				
				photo = ImageIO.read(pngFile);
				
				//frame = ImageIO.read(new File("." + sep + "images" + sep + "overlay.png"));
				frame = ImageIO.read(getClass().getResourceAsStream("/images/overlay.png"));
			
				if (photo == null){
					throw new Exception("Could not load original photo");
				}
				
				if (frame == null){
					throw new Exception("Não foi possível carregar a moldura");
				}
				
				photoW = photo.getWidth();
				photoH = photo.getHeight();				
				
				/*	
				if (photoW > photoH)
					if (Math.round((double)photoW / (double)photoH) != 1.5){
						throw new Exception("Original photo has wrong dimensions");
					}
					
				
				if (photoW < photoH)
					if (Math.round((double)photoH / (double)photoW)  != 1.5){
						throw new Exception("Original photo has wrong dimensions");
					}
				*/
										
				holeW = frameW - (leftMarginW + leftBorderW + rightMarginW  + rightBorderW);
				holeH = frameH - (topMarginH  + topBorderH  + bottomMarginH + bottomBorderH); 
				
				/*
				if (((photoW / holeW) < 1) || ((photoH / holeH) < 1)){
					throw new Exception("Original photo is smaller than overlay");
				}	
				*/
				////////////////////////////////////////////////////////////////////////
				//
				////////////////////////////////////////////////////////////////////////
				//if (((photoW / holeW) > 1) || ((photoH / holeH) > 1)){
			
					resizedPhoto = new BufferedImage(holeW, holeH, BufferedImage.TYPE_INT_RGB);
					g2D = resizedPhoto.createGraphics();
					g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
					g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					g2D.drawImage(photo, 0, 0, holeW, holeH, null);
					g2D.dispose();
					photo = resizedPhoto;
		
				//}
				
				merged = new BufferedImage(frameW, frameH, BufferedImage.TYPE_INT_RGB);
				
				g = merged.getGraphics();
				
				//g.drawImage(photo, 67, 37, null);
				g.drawImage(photo, 0, 0, null);
				g.drawImage(frame, 0, 0, null);
				
				ImageIO.write(merged, "PNG", new File(photoSourceFolderPath, fileName.substring(0, fileName.indexOf(".")) + ".png"));
				
				/////////////////////////////////////////////////////////////////////////
				//
				/////////////////////////////////////////////////////////////////////////
				
				fileNamePNG = fileName.substring(0, fileName.indexOf(".")) + ".png";

				File f = new File(photoSourceFolderPath + sep + fileName);
				File fPNG = new File(photoSourceFolderPath + sep + fileNamePNG);
				
				File fDestPNG = new File(new String(pMain.framedPhotoPath + sep + fileNamePNG));
									
				inputChannel = new FileInputStream(fPNG).getChannel();
				outputChannel = new FileOutputStream(fDestPNG).getChannel();
				outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		
				inputChannel.close();
				outputChannel.close();				
				
				f.delete();
				fPNG.delete();
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){	
						progressBarDlg.dispose();
						showDirectory("jpg", photoSourceFolderPath, listPhotoSourceFolder, listModelPhotoSourceFolder);		
						showDirectory("png", pMain.framedPhotoPath, listFramedPhoto, listModelFramedPhoto);
					}
				});	
			
			}
			catch(final IOException ioe){
				EventQueue.invokeLater(new Runnable(){
					public void run(){	
						JOptionPane.showMessageDialog(PhotoCustomiserMainWindow.this, ioe.getMessage());
					}
				});			
			}
			catch(final Exception e){
				EventQueue.invokeLater(new Runnable(){
					public void run(){	
						JOptionPane.showMessageDialog(PhotoCustomiserMainWindow.this, e.getMessage());
					}
				});			
			}
			
		}		
	
	}
	
	public class RefreshPhotoSourceFolder_ActionListener implements ActionListener{
	
		private String dirPathFrom = "";
		private JList myListFrom = null;
		private DefaultListModel myListModelFrom = null;
		
		private String dirPathFramed = "";
		private JList myListFramed = null;
		private DefaultListModel myListModelFramed = null;
		
		
		public RefreshPhotoSourceFolder_ActionListener(String dirPathFrom, JList myListFrom, DefaultListModel myListModelFrom, JList myListFramed, DefaultListModel myListModelFramed){
		
			this.dirPathFrom = dirPathFrom;
			this.myListFrom = myListFrom;
			this.myListModelFrom = myListModelFrom;
			
			this.myListFramed = myListFramed;
			this.myListModelFramed = myListModelFramed;

		
		}
		
		public void actionPerformed(ActionEvent e){
		
			showDirectory("jpg", dirPathFrom, myListFrom, myListModelFrom);
			
		}
		
	}
	
	
	
	public void showDirectory(String ext, String dirPath, JList myList, DefaultListModel myListModel ){
	
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();
	
		myListModel = new DefaultListModel();
		for (int i = 0; i < listOfFiles.length; i++){
 
		   if (listOfFiles[i].isFile()){
		   		String files = listOfFiles[i].getName();
			    if (files.endsWith("." + ext.toLowerCase())){
			       //System.out.println(files);
				   myListModel.addElement(files);
				}
		   }
		   
		}
	
		myList.setModel(myListModel);
	
	}	
	
	public class DoubleClickItemMouseAdapter extends MouseAdapter{
  		  
		private JList list = null;
		
		public DoubleClickItemMouseAdapter(JList l){
			this.list = l;
			
		}

		public void mouseClicked(MouseEvent e){
		
			if (e.getClickCount() == 2){
			
				int index = list.locationToIndex(e.getPoint());
				ListModel dlm = list.getModel();
				Object item = dlm.getElementAt(index);
				list.ensureIndexIsVisible(index);

				PhotoView x = new PhotoView(new String(photoSourceFolderPath + File.separator + item));

			}
			
		}

	}
	
	public class DoubleClickItemMouseAdapterFramed extends MouseAdapter{
  		  
		private JList list;
		
		public DoubleClickItemMouseAdapterFramed(JList l){
			this.list = l;
		}

		public void mouseClicked(MouseEvent e){
		
			if (e.getClickCount() == 2){
			
				int index = list.locationToIndex(e.getPoint());
				ListModel dlm = list.getModel();
				Object item = dlm.getElementAt(index);
				list.ensureIndexIsVisible(index);

				PhotoView x = new PhotoView(new String(pMain.framedPhotoPath + File.separator + item));

			}
		}
	}

}
	
