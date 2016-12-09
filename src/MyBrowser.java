import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MyBrowser {

	private JFrame frmMaestrollsWebpageAnalyzer;
	private JTextField textField;
	private JLabel lbl;
	private JTextArea textArea;
	private JButton btn, btn1, btndev;
	private JScrollPane scroll;
	private Highlighter mHighlighter;
	private Highlighter.HighlightPainter mPaint;
	
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
		frmMaestrollsWebpageAnalyzer.setResizable(false);
		frmMaestrollsWebpageAnalyzer.setTitle("Maestroll's Webpage Analyzer ");
		frmMaestrollsWebpageAnalyzer.setBounds(100, 100, 827, 585);
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
		mHighlighter = textArea.getHighlighter();
		mPaint = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Detect right click
				if ((arg0.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK 
				    && isURLValid()){		
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
					ArrayList<Integer> position = new WordSearcher().
							getFoundWordPosition(textArea.getText().toString(), keywordForSearch);
					
					highlightWords(position, keywordForSearch.length(),
							textArea.getText().toString().length());
				} else{
					// Do nothing
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
				if (isURLValid()) 
					btn1.setEnabled(true);
				else
					btn1.setEnabled(false);
			}
		});
		btn.setBounds(592, 94, 123, 29);
		frmMaestrollsWebpageAnalyzer.getContentPane().add(btn);
		
		btn1 = new JButton("Extract!");
		btn1.setEnabled(false);
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmMaestrollsWebpageAnalyzer.setVisible(false);
				String[] passingData = new String[2];
				passingData[0] = textArea.getText().toString(); 
				passingData[1] = textField.getText().toString();
				
			    ExtractingPage.main(passingData);
			}
		});
		btn1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btn1.setBounds(98, 480, 123, 29);
		frmMaestrollsWebpageAnalyzer.getContentPane().add(btn1);
		
		btndev = new JButton("Save Scipt");
		btndev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					PrintWriter handout = new PrintWriter("filename.txt");
					handout.println(textArea.getText().toString());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		btndev.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btndev.setBounds(228, 479, 123, 29);
		frmMaestrollsWebpageAnalyzer.getContentPane().add(btndev);
	}
	
	private void highlightWords(ArrayList<Integer> position, int keywordLength, int length){
		textArea.getHighlighter().removeAllHighlights();
		textArea.setHighlighter(mHighlighter);
		
		for (int i = 0; i < position.size(); i++){
			try {
				mHighlighter.addHighlight(position.get(i).intValue(),
						position.get(i).intValue() + keywordLength, mPaint);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isURLValid(){
		return !textArea.getText().toString().isEmpty() && 
			   !textArea.getText().toString().equals("Faulty URL!");
	}
}
