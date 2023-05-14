package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AgencijaTouristWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2949615253496597662L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgencijaLoginWindow loginWindow = new AgencijaLoginWindow();
	                loginWindow.setVisible(true);
	                loginWindow.setTitle("Tourist Agency - Login");
	                loginWindow.setLocationRelativeTo(null);
//					AgencijaTouristWindow frame = new AgencijaTouristWindow();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AgencijaTouristWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
