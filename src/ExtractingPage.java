import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class ExtractingPage {

	private JFrame frmExtractionSetting;
	private JButton btn1, btn2;
	private JLabel lbl1, lbl2;
	private JCheckBox checkjpg, checkpng, checkbmp;
	private JFileChooser file;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] mScript) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExtractingPage window = new ExtractingPage(mScript);
					window.frmExtractionSetting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ExtractingPage(String[] mScript) {
		initialize(mScript);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] mScript) {
		frmExtractionSetting = new JFrame();
		frmExtractionSetting.setResizable(false);
		frmExtractionSetting.setTitle("Extraction Setting");
		frmExtractionSetting.setBounds(100, 100, 762, 546);
		frmExtractionSetting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExtractionSetting.getContentPane().setLayout(null);
		
		lbl1 = new JLabel("File Format:");
		lbl1.setFont(new Font("Calibri", Font.BOLD, 24));
		lbl1.setBounds(51, 44, 125, 30);
		frmExtractionSetting.getContentPane().add(lbl1);
		
		checkjpg = new JCheckBox(".jpg");
		checkjpg.setBounds(187, 44, 75, 29);
		frmExtractionSetting.getContentPane().add(checkjpg);
		
		lbl2 = new JLabel("Set Folder:");
		lbl2.setFont(new Font("Calibri", Font.BOLD, 24));
		lbl2.setBounds(61, 117, 112, 21);
		frmExtractionSetting.getContentPane().add(lbl2);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(187, 113, 465, 27);
		frmExtractionSetting.getContentPane().add(textField);
		textField.setColumns(10);
		
		btn1 = new JButton("...");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				file = new JFileChooser();
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = file.showDialog(frmExtractionSetting, "Attach");
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File directory = file.getSelectedFile();
					textField.setText(directory.getPath());
				} else{
					// Do nothing; 
				}

			}
		});
		btn1.setBounds(650, 112, 42, 29);
		frmExtractionSetting.getContentPane().add(btn1);
		
		btn2 = new JButton("Start Extraction!");
		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isSettingValid()){
					startExtraction(mScript);
				} else{
					textArea.setText("Missing file type or directory.");
				}
			}
		});
		btn2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btn2.setBounds(525, 169, 167, 29);
		frmExtractionSetting.getContentPane().add(btn2);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		scroll = new JScrollPane(textArea);
		scroll.setBounds(61, 225, 631, 266);
		frmExtractionSetting.getContentPane().add(scroll);
		
		checkpng = new JCheckBox(".png");
		checkpng.setBounds(269, 44, 75, 29);
		frmExtractionSetting.getContentPane().add(checkpng);
		
		checkbmp = new JCheckBox(".bmp");
		checkbmp.setBounds(351, 44, 75, 29);
		frmExtractionSetting.getContentPane().add(checkbmp);
	}
	
	private boolean isSettingValid(){
		return (checkjpg.isSelected() || checkpng.isSelected() || checkbmp.isSelected()) &&
			   !textField.getText().toString().isEmpty();
	}
	
	private void startExtraction(String[] mScript){
		boolean[] selectedType = new boolean[3];
		if (checkjpg.isSelected()) selectedType[0] = true;
		if (checkpng.isSelected()) selectedType[1] = true;
		if (checkbmp.isSelected()) selectedType[2] = true;
		
		ArrayList<String> mLinks = new FileLinks().getFileLinks(mScript, selectedType);	
		
		new PictureDownloader().downloadImageBatch(mLinks, textField.getText().toString(), textArea);
	}
}
