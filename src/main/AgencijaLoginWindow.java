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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
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

    //When user is added he has hashed password from string to byte to hex and same for salt these two values are stored on 6 and 7 index positions in csv file 
    //with this function they are converted from hex to bytes without losing value so that they could be passed for authentication when user tries to sign in 
    public static byte[] hexToBytes(String hexString) {
        int length = hexString.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
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
        
        JLabel incorrectPasswordJLabel = new JLabel("Incorrect Password!");
        incorrectPasswordJLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        incorrectPasswordJLabel.setForeground(Color.red);
        incorrectPasswordJLabel.setVisible(false);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
        	@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
        		String typedUsernameString = usernameField.getText();
        		String typedPasswordString = passwordField.getText();
        		if(typedPasswordString.length() >= 8 && typedUsernameString.length() >= 8) {
        			if(validation.isValidUsername(typedUsernameString) && validation.IsValidPassword(typedPasswordString)) {
        				try {
        			        String csvFile = "src/data/userdata.csv";
        			        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        			        String line;
        			        while ((line = reader.readLine()) != null) {
        			            String[] values = line.split("\\|");
        			            String checkUsername = values[7];
        			            if(checkUsername.equals(typedUsernameString)) {
        			            	//string > hex > byte 
        			            	byte[] hash = hexToBytes(values[8]);
        			            	byte[] salt = hexToBytes(values[9]);
        			            	if(auth.Pbkdf2.authenticate(typedPasswordString, hash,salt)) {
        			            		//Here open next window based on user role
        			            		String role = values[1];
        			            		if(role.equals("Turist")) {
        			            			AgencijaTouristWindow touristWindow = new AgencijaTouristWindow();
        			            			touristWindow.setVisible(true);
        			            			dispose();
        			            		}else if (role.equals("Administrator")) {
        			            			AgencijaAdministratorWindow adminWindow = new AgencijaAdministratorWindow();
        			            			adminWindow.setVisible(true);
        			            			dispose();
										}else if(role.equals("Agent")){
											AgencijaAgentWindow agentWindow = new AgencijaAgentWindow();
        			            			agentWindow.setVisible(true);
        			            			dispose();
										}else {
											System.out.println("Error in DB.");
											dispose();
										}
        			            		System.out.println("Proslo");
        			            		
        			            		
        			            	}else {
        			            		incorrectPasswordJLabel.setText("Password is incorrect!");
            	            			incorrectPasswordJLabel.setVisible(true);
        			            	}
        			            }
        			        }

        			        reader.close();
        			    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
        			        e1.printStackTrace();
        			    }
        			}else {
        				incorrectPasswordJLabel.setText("Password or Username is not in valid form please check type credentials.");
            			incorrectPasswordJLabel.setVisible(true);
        			}
        		}else {
        			incorrectPasswordJLabel.setText("Please input your username and password.");
        			incorrectPasswordJLabel.setVisible(true);
        		}
        	}
        });
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));

        contentPane.add(welcomeLabel,"wrap,span 2,align center");
        contentPane.add(usernameLabel);
        contentPane.add(usernameField, "wrap");
        contentPane.add(passwordLabel);
        contentPane.add(passwordField, "wrap");
        contentPane.add(incorrectPasswordJLabel,"wrap,span 2,align center");
        contentPane.add(loginButton, "span 2, align center");
    }
}
