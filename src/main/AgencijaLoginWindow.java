package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import validation.validation;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionEvent;

public class AgencijaLoginWindow extends JFrame {

	private static final long serialVersionUID = 5919097081725955881L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgencijaLoginWindow frame = new AgencijaLoginWindow();
					frame.setVisible(true);
					frame.setTitle("Tourist Agency");
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static byte[] hexToBytes(String hexString) {
		int length = hexString.length();
		byte[] bytes = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return bytes;
	}

	public AgencijaLoginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(57, 75, 88));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("align center center, gap 20px 20px")); // Bigger gaps

		JLabel welcomeLabel = new JLabel("<html><pre> Welcome to our <br> Tourist Agency</pre></html>");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
		welcomeLabel.setForeground(new Color(255, 255, 255));

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(new Color(255, 255, 255));
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		usernameField = new JTextField(20);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
		passwordLabel.setForeground(new Color(255, 255, 255));
		passwordField = new JPasswordField(20);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performLogin();
			}
		});
		getRootPane().setDefaultButton(loginButton);

		contentPane.setFocusable(true);
		contentPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performLogin();
				}
			}
		});

		loginButton.setFont(new Font("Arial", Font.BOLD, 16));

		contentPane.add(welcomeLabel, "wrap,span 2,align center");
		contentPane.add(usernameLabel);
		contentPane.add(usernameField, "wrap");
		contentPane.add(passwordLabel);
		contentPane.add(passwordField, "wrap");
		contentPane.add(loginButton, "span 2, align center");
	}

	private void performLogin() {
		String typedUsernameString = usernameField.getText();
		@SuppressWarnings("deprecation")
		String typedPasswordString = passwordField.getText();
		if (typedPasswordString.length() >= 8 && typedUsernameString.length() >= 8) {
			if (validation.isValidUsername(typedUsernameString) && validation.IsValidPassword(typedPasswordString)) {
				try {
					String csvFile = "src/data/userdata.csv";
					BufferedReader reader = new BufferedReader(new FileReader(csvFile));
					String line;
					while ((line = reader.readLine()) != null) {
						String[] values = line.split("\\|");
						String checkUsername = values[8];
						if (checkUsername.equals(typedUsernameString)) {
							// string > hex > byte
							byte[] hash = hexToBytes(values[9]);
							byte[] salt = hexToBytes(values[10]);
							if (auth.Pbkdf2.authenticate(typedPasswordString, hash, salt)) {

								String role = values[1];
								if (role.equals("Turist")) {
									AgencijaTouristWindow touristWindow = new AgencijaTouristWindow(values[0]);
									touristWindow.setVisible(true);
									dispose();
								} else if (role.equals("Administrator")) {
									AgencijaAdministratorWindow adminWindow = new AgencijaAdministratorWindow();
									adminWindow.setVisible(true);
									dispose();
								} else if (role.equals("Agent")) {
									AgencijaAgentWindow agentWindow = new AgencijaAgentWindow();
									agentWindow.setVisible(true);
									dispose();
								} else {
									System.out.println("Error in DB.");
									dispose();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Information message.", "Password is incorrect!",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}

					reader.close();
				} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Information message.",
						"Password or Username is not in valid form please check type credentials.",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Information message.", "Please input your username and password.",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
