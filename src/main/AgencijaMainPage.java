package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import mainStructure.Turist;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import validation.validation;

public class AgencijaMainPage {

	private JFrame frmTravelWithUs;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField passwordField_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgencijaMainPage window = new AgencijaMainPage();
					window.frmTravelWithUs.setVisible(true);
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
		frmTravelWithUs = new JFrame();
		frmTravelWithUs.setTitle("Travel with us");
		frmTravelWithUs.setBounds(100, 100, 1366, 768);
		frmTravelWithUs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTravelWithUs.getContentPane().setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 595, 731);
		frmTravelWithUs.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		panel_2.setVisible(false);

		JLabel lblNewLabel_6 = new JLabel("Registration");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_6.setBounds(150, 87, 261, 81);
		panel_2.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Name: ");
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNewLabel_7.setBounds(53, 219, 109, 26);
		panel_2.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Surname:");
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNewLabel_8.setBounds(53, 274, 109, 26);
		panel_2.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Address:");
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNewLabel_9.setBounds(53, 332, 109, 26);
		panel_2.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("JMBG:");
		lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNewLabel_10.setBounds(53, 391, 109, 26);
		panel_2.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Username:");
		lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNewLabel_11.setBounds(53, 454, 109, 26);
		panel_2.add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("Password:");
		lblNewLabel_12.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNewLabel_12.setBounds(53, 511, 109, 26);
		panel_2.add(lblNewLabel_12);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_1.setBounds(198, 219, 196, 26);
		panel_2.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_2.setBounds(198, 274, 196, 26);
		panel_2.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_3.setBounds(198, 332, 196, 26);
		panel_2.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_4.setBounds(198, 391, 196, 26);
		panel_2.add(textField_4);
		textField_4.setColumns(10);

		textField_5 = new JTextField();
		textField_5.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_5.setBounds(198, 454, 196, 26);
		panel_2.add(textField_5);
		textField_5.setColumns(10);

		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordField_1.setBounds(198, 511, 196, 26);
		panel_2.add(passwordField_1);

		JButton btnNewButton_2 = new JButton("Submit");
		btnNewButton_2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				// Here users data is submitted to csv file
				if (textField_1.getText().isEmpty() || textField_2.getText().isEmpty()
						|| textField_3.getText().isEmpty() || textField_4.getText().isEmpty()
						|| textField_5.getText().isEmpty() || passwordField_1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all the cells in the form.");
				} else {
					if (validation.IsValidNameSurname(textField_1.getText())
							&& validation.IsValidNameSurname(textField_2.getText())
							&& validation.isValidUsername(textField_5.getText())
							&& validation.isValidAdress(textField_3.getText())
							&& validation.isValidJMBG(textField_4.getText())
							&& validation.IsValidPassword(passwordField_1.getText())) {

						Turist turist_user = new Turist(textField_1.getText(), textField_2.getText(),
								textField_4.getText(), textField_3.getText(), textField_5.getText(),
								passwordField_1.getText());
						String[] userStrings = { turist_user.getName(), turist_user.getSurname(), turist_user.getJMBG(),
								turist_user.getAddress(), turist_user.getUsername(), passwordField_1.getText() };

						String filePath = "src/data/userdata.csv";

						try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
						    writer.write(String.join("|", userStrings));
						    writer.newLine();
						} catch (IOException e5) {
						    e5.printStackTrace();
						}
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						textField_4.setText("");
						textField_5.setText("");
						passwordField_1.setText("");
					}
				}
			}
		});
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.setBackground(new Color(0, 0, 160));
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 24));
		btnNewButton_2.setBounds(68, 620, 184, 40);
		panel_2.add(btnNewButton_2);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(215, 215, 215));
		panel.setBounds(0, 0, 595, 731);
		frmTravelWithUs.getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnNewButton_3 = new JButton("<");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				panel_2.setVisible(false);
			}
		});
		btnNewButton_3.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton_3.setBounds(20, 20, 40, 40);
		panel_2.add(btnNewButton_3);

		JLabel lblNewLabel_13 = new JLabel("Go back");
		lblNewLabel_13.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_13.setBounds(68, 27, 74, 28);
		panel_2.add(lblNewLabel_13);

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
									System.out.println("PROSLO" + " Prozor za : " + roleString);
									textField.setText("");
									passwordField.setText("");
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
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				panel_2.setVisible(true);
			}
		});
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
		panel_1.setBounds(593, 0, 759, 731);
		frmTravelWithUs.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		
		byte[] imageBytes = null;
		try {
		    imageBytes = Files.readAllBytes(Paths.get("src/image/picture.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		JLabel picLabel = new JLabel(imageIcon);
		picLabel.setSize(450, 450);
		picLabel.setLocation(145, 145);
		panel_1.add(picLabel);
		
	}
}
