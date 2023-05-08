package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AgencijaMainPage {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgencijaMainPage window = new AgencijaMainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public AgencijaMainPage() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1366, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(215, 215, 215));
		panel.setBounds(0, 0, 595, 731);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("<html><pre>Welcome to our\nTraveling Agency</pre></html>");
		lblNewLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 36));
		lblNewLabel.setBounds(107, 32, 367, 246);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setBounds(42, 288, 88, 32);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_2.setBounds(42, 361, 88, 35);
		panel.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(143, 289, 252, 35);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Sign in");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("deprecation")
				String passwordForValidation = passwordField.getText();
				String usernameString = textField.getText();
				String line = "";
				String splitBy = ",";
				try {
					if (8 <= passwordForValidation.length() && !passwordForValidation.isEmpty()) {
						BufferedReader br = new BufferedReader(new FileReader("src\\data\\userdata.csv"));
						while ((line = br.readLine()) != null) {
							String[] usersData = line.split(splitBy);
							if (usersData[5].equals(usernameString)) {
								byte[] hash = auth.Pbkdf2.getEncryptedPassword(usersData[7], "salt".getBytes());
								if (auth.Pbkdf2.authenticate(passwordForValidation, hash, "salt".getBytes())) {
			        				String roleString = usersData[6];
									System.out.println("PROSLO"+" Prozor za : "+roleString);
									textField.setText("");
									passwordField.setText("");
//			        				panel.hide();
								} else {
									System.out.println("Nije proslo");
								}
							}
						}
						br.close();
					}
				} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 160));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewButton.setBounds(143, 485, 123, 42);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Sign up");
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(0, 0, 160));
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewButton_1.setBounds(276, 485, 119, 42);
		panel.add(btnNewButton_1);

		JLabel lblNewLabel_3 = new JLabel("Forgot password?");
		lblNewLabel_3.setForeground(new Color(0, 0, 255));
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_3.setBounds(152, 431, 189, 30);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Help");
		lblNewLabel_4.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(266, 680, 75, 30);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Terms and Service");
		lblNewLabel_5.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(370, 680, 189, 30);
		panel.add(lblNewLabel_5);

		passwordField = new JPasswordField();
		passwordField.setBounds(143, 363, 252, 35);
		panel.add(passwordField);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(594, 0, 758, 731);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		//make byte stream here out of picture.jpg
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("src//image//picture.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setSize(450, 450);
		picLabel.setLocation(145, 145);
		panel_1.add(picLabel);
	}
}
