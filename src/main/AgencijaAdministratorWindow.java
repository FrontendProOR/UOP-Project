package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import mainStructure.Administrator;
import mainStructure.Agent;
import mainStructure.Turist;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import validation.validation;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgencijaAdministratorWindow extends JFrame {

	private static final long serialVersionUID = 3028218694305485178L;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgencijaLoginWindow loginWindow = new AgencijaLoginWindow();
					loginWindow.setVisible(true);
					loginWindow.setTitle("Tourist Agency - Login");
					loginWindow.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AgencijaAdministratorWindow() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 700);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Tourists", null, panel, null);

		table = new JTable();
		table.setEnabled(false);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForTourist = { "ID", "Role", "Name", "Surname", "JMBG", "Address", "Username" };
		DefaultTableModel tableModelTourist = new DefaultTableModel(columnNamesForTourist, 0);
		String[][] allTourists = new String[0][7];
		String csvFile = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfATourist = line.split("\\|");
				if ("Turist".equals(valueOfATourist[1])) {
					String[] touristStrings = { valueOfATourist[0], valueOfATourist[1], valueOfATourist[2],
							valueOfATourist[3], valueOfATourist[4], valueOfATourist[5], valueOfATourist[6] };
					allTourists = Arrays.copyOf(allTourists, allTourists.length + 1);
					allTourists[i] = touristStrings;
					tableModelTourist.addRow(touristStrings);
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		table.setModel(tableModelTourist);
		table.setVisible(true);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(new MigLayout("", "[]", "[][][][]"));

		JButton btnNewButton = new JButton("Change Tourist");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_5.add(btnNewButton, "cell 0 0,alignx center");

		JButton btnNewButton_1 = new JButton("Delete Tourist");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_5.add(btnNewButton_1, "cell 0 1,alignx center");

		JButton btnNewButton_2 = new JButton("Create Tourist");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTouristForm();
				// Here we create users
			}
		});
		panel_5.add(btnNewButton_2, "cell 0 2,alignx center");
		
		JButton btnNewButton_3 = new JButton("Add Tourist (Agent requested)");
		panel_5.add(btnNewButton_3, "cell 0 3");

		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, "cell 0 0, grow");

		//Frome here is admin tab with admins data
		
		JPanel panel1_1 = new JPanel();
		tabbedPane.addTab("Administrators", null, panel1_1, null);

		JTable table1 = new JTable();
		table1.setEnabled(false);
		table1.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForAdmins = { "ID", "Role", "Name", "Surname", "JMBG", "Address", "Username" };
		DefaultTableModel tableModelAdmin = new DefaultTableModel(columnNamesForAdmins, 0);
		String[][] allAdmins = new String[0][7];
		String csvFile1 = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile1))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAAdmin = line.split("\\|");
				if ("Administrator".equals(valueOfAAdmin[1])) {
					String[] adminsStrings = { valueOfAAdmin[0], valueOfAAdmin[1], valueOfAAdmin[2],
							valueOfAAdmin[3], valueOfAAdmin[4], valueOfAAdmin[5], valueOfAAdmin[6] };
					allAdmins = Arrays.copyOf(allAdmins, allAdmins.length + 1);
					allAdmins[i] = adminsStrings;
					tableModelAdmin.addRow(adminsStrings);
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		table1.setModel(tableModelAdmin);
		table1.setVisible(true);

		JPanel panel_6 = new JPanel();
		panel1_1.add(panel_6);
		panel_6.setLayout(new MigLayout("", "[]", "[][][][]"));

		JButton btnNewButton_6 = new JButton("Change Admin");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_6.add(btnNewButton_6, "cell 0 0,alignx center");

		JButton btnNewButton_4 = new JButton("Delete Admin");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_6.add(btnNewButton_4, "cell 0 1,alignx center");

		JButton btnNewButton_5 = new JButton("Create Admin");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAdminForm();
				// Here we create users
			}
		});
		panel_6.add(btnNewButton_5, "cell 0 2,alignx center");
		

		JScrollPane scrollPane1 = new JScrollPane(table1);
		panel1_1.add(scrollPane1, "cell 0 0, grow");

		
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Administrators", null, panel_2, null);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Arrangments", null, panel_3, null);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Reservations", null, panel_4, null);

//        String[] columnNamesForAdmin = {"ID", "Role", "Name", "Surname", "JMBG", "Address", "Username"};
//        DefaultTableModel tableModelForAdmin = new DefaultTableModel(columnNamesForAdmin, 0);
//        String[][] allAdmins = new String[0][7];
//        String csvFile1 = "src/data/userdata.csv";
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile1))) {
//            String line;
//            int i = 0;
//            while ((line = reader.readLine()) != null) {
//                String[] valueOfAdmin = line.split("\\|");
//                
//                if ("Administrator".equals(valueOfAdmin[1])) {
//                    String[] adminStrings = {valueOfAdmin[0], valueOfAdmin[1], valueOfAdmin[2], valueOfAdmin[3], valueOfAdmin[4], valueOfAdmin[5], valueOfAdmin[6]};
//                    allAdmins = Arrays.copyOf(allAdmins, allAdmins.length + 1);
//                    allAdmins[i] = adminStrings;
//                    tableModelForAdmin.addRow(adminStrings);
//                    i++;
//                }
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

//		String[] columnNamesForAgent = { "ID", "Role", "Name", "Surname", "JMBG", "Address", "Username" };
//		DefaultTableModel tableModelForAgent = new DefaultTableModel(columnNamesForAgent, 0);
//		String[][] allAgents = new String[0][7];
//		String csvFile2 = "src/data/userdata.csv";
//
//		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile2))) {
//			String line;
//			int i = 0;
//			while ((line = reader.readLine()) != null) {
//				String[] valueOfAgent = line.split("\\|");
//
//				if ("Administrator".equals(valueOfAgent[1])) {
//					String[] agentStrings = { valueOfAgent[0], valueOfAgent[1], valueOfAgent[2], valueOfAgent[3],
//							valueOfAgent[4], valueOfAgent[5], valueOfAgent[6] };
//					allAgents = Arrays.copyOf(allAgents, allAgents.length + 1);
//					allAgents[i] = agentStrings;
//					tableModelForAgent.addRow(agentStrings);
//					i++;
//				}
//			}
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}

	}

	private static void createTouristForm() {
		// Create the main frame
		JFrame frame = new JFrame("Create Tourist");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BorderLayout());

		// Create the form panel
		JPanel formPanel = new JPanel(new GridLayout(8, 2));

		// Create the labels and text fields
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameTextField = new JTextField();
		JLabel surnameLabel = new JLabel("Surname:");
		JTextField surnameTextField = new JTextField();
		JLabel jmbgLabel = new JLabel("JMBG:");
		JTextField jmbgTextField = new JTextField();
		JLabel addressLabel = new JLabel("Address:");
		JTextField addressTextField = new JTextField();
		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameTextField = new JTextField();
		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField();
		JLabel roleLabel = new JLabel("Role:");
		String[] roles = { "Turist" };
		JComboBox<String> roleComboBox = new JComboBox<>(roles);

		// Add the components to the form panel
		formPanel.add(nameLabel);
		formPanel.add(nameTextField);
		formPanel.add(surnameLabel);
		formPanel.add(surnameTextField);
		formPanel.add(jmbgLabel);
		formPanel.add(jmbgTextField);
		formPanel.add(addressLabel);
		formPanel.add(addressTextField);
		formPanel.add(usernameLabel);
		formPanel.add(usernameTextField);
		formPanel.add(passwordLabel);
		formPanel.add(passwordField);
		formPanel.add(roleLabel);
		formPanel.add(roleComboBox);
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String surname = surnameTextField.getText();
				String jmbg = jmbgTextField.getText();
				String address = addressTextField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				String role = (String) roleComboBox.getSelectedItem();
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username) && validation.IsValidPassword(password)) {
					String unetaSifra = password;// iz passwordfield-a
					String filePath = "src/data/userdata.csv";
					Long id1 = new Random().nextLong();
					byte[] salt;
					byte[] hash;
					if (role == "Turist") {
						mainStructure.Turist user = new Turist(name, surname, jmbg, address, username, password);
						try {
							salt = auth.Pbkdf2.generateSalt();
							hash = auth.Pbkdf2.getEncryptedPassword(unetaSifra, salt);
							String unos = id1 + "|" + user.getRole() + "|" + user.getName() + "|" + user.getSurname()
									+ "|" + user.getJMBG() + "|" + user.getAddress() + "|" + user.getUsername() + "|"
									+ auth.Pbkdf2.bytesToHex(hash) + "|" + auth.Pbkdf2.bytesToHex(salt);
							try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
								writer.write(unos);
								writer.newLine();
								System.out.println("Value written to the CSV file successfully.");

							} catch (IOException e2) {
								e2.printStackTrace();
							}
						} catch (NoSuchAlgorithmException e1) {
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("Greska -1");
					}

				}

			}
		});
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
	//From here is admin form generator
	private static void createAdminForm() {
		// Create the main frame
		JFrame frame = new JFrame("Create Tourist");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BorderLayout());

		// Create the form panel
		JPanel formPanel = new JPanel(new GridLayout(8, 2));

		// Create the labels and text fields
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameTextField = new JTextField();
		JLabel surnameLabel = new JLabel("Surname:");
		JTextField surnameTextField = new JTextField();
		JLabel jmbgLabel = new JLabel("JMBG:");
		JTextField jmbgTextField = new JTextField();
		JLabel addressLabel = new JLabel("Address:");
		JTextField addressTextField = new JTextField();
		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameTextField = new JTextField();
		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField();
		JLabel roleLabel = new JLabel("Role:");
		String[] roles = { "Administrator" };
		JComboBox<String> roleComboBox = new JComboBox<>(roles);

		// Add the components to the form panel
		formPanel.add(nameLabel);
		formPanel.add(nameTextField);
		formPanel.add(surnameLabel);
		formPanel.add(surnameTextField);
		formPanel.add(jmbgLabel);
		formPanel.add(jmbgTextField);
		formPanel.add(addressLabel);
		formPanel.add(addressTextField);
		formPanel.add(usernameLabel);
		formPanel.add(usernameTextField);
		formPanel.add(passwordLabel);
		formPanel.add(passwordField);
		formPanel.add(roleLabel);
		formPanel.add(roleComboBox);
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String surname = surnameTextField.getText();
				String jmbg = jmbgTextField.getText();
				String address = addressTextField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				String role = (String) roleComboBox.getSelectedItem();
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username) && validation.IsValidPassword(password)) {
					String unetaSifra = password;// iz passwordfield-a
					String filePath = "src/data/userdata.csv";
					Long id1 = new Random().nextLong();
					byte[] salt;
					byte[] hash;
					if (role == "Administrator") {
						mainStructure.Administrator user = new Administrator(name, surname, jmbg, address, username, password);
						try {
							salt = auth.Pbkdf2.generateSalt();
							hash = auth.Pbkdf2.getEncryptedPassword(unetaSifra, salt);
							String unos = id1 + "|" + user.getRole() + "|" + user.getName() + "|" + user.getSurname()
									+ "|" + user.getJMBG() + "|" + user.getAddress() + "|" + user.getUsername() + "|"
									+ auth.Pbkdf2.bytesToHex(hash) + "|" + auth.Pbkdf2.bytesToHex(salt);
							try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
								writer.write(unos);
								writer.newLine();
								System.out.println("Value written to the CSV file successfully.");

							} catch (IOException e2) {
								e2.printStackTrace();
							}
						} catch (NoSuchAlgorithmException e1) {
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("Greska -1");
					}

				}

			}
		});
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

}
