import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyBrowser {

	private JFrame frmMaestrollsWebpageAnalyzer;
	private JTextField textField;
	private JLabel lbl;
	private JTextArea textArea;
	private JButton btn;
	private JScrollPane scroll;
	
	private String keywordForSearch;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyBrowser window = new MyBrowser();
					window.frmMaestrollsWebpageAnalyzer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyBrowser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMaestrollsWebpageAnalyzer = new JFrame();
		frmMaestrollsWebpageAnalyzer.setTitle("Maestroll's Webpage Analyzer ");
		frmMaestrollsWebpageAnalyzer.setBounds(100, 100, 827, 552);
		frmMaestrollsWebpageAnalyzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMaestrollsWebpageAnalyzer.getContentPane().setLayout(null);
		
		lbl = new JLabel("URL:");
		lbl.setFont(new Font("Calibri", Font.BOLD, 24));
		lbl.setBounds(98, 50, 64, 29);
		frmMaestrollsWebpageAnalyzer.getContentPane().add(lbl);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setBounds(176, 49, 539, 30);
		frmMaestrollsWebpageAnalyzer.getContentPane().add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Detect right click
				if ((arg0.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK 
				    && !textArea.getText().toString().isEmpty()
				    && !textArea.getText().toString().equals("Faulty URL!")){		
					    keywordForSearch  = (String)JOptionPane.showInputDialog(
					    	frmMaestrollsWebpageAnalyzer,
		                    "Keyword you want to locate in the script:", 
		                    "Search", 
		                    JOptionPane.PLAIN_MESSAGE,
		                    null, 
		                    null, 
		                    "");			
				}
				
				if (!keywordForSearch.isEmpty()){
					// Need more!
				}
			}
		});
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textArea.setLineWrap(true);
		scroll = new JScrollPane(textArea);
		scroll.setBounds(98, 150, 617, 315);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		frmMaestrollsWebpageAnalyzer.getContentPane().add(scroll);
		
		btn = new JButton("Analyze!");
		btn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textArea.setText(new HttpExtractor().getLinkInfo(textField.getText().toString()));
			}
		});
		btn.setBounds(592, 94, 123, 29);
		frmMaestrollsWebpageAnalyzer.getContentPane().add(btn);
	}
}
