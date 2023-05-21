package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import validation.validation;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.FlowLayout;
import javax.swing.Icon;

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

		// This is tab for tourists
		JPanel panel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Tourists", null, panel, null);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setEnabled(true);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForTourist = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address", "Username" };
		DefaultTableModel tableModelTourist = new DefaultTableModel(columnNamesForTourist, 0);
		String[][] allTourists = new String[0][8];
		String csvFile = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfATourist = line.split("\\|");
				if ("Turist".equals(valueOfATourist[1])) {
					String[] touristStrings = { valueOfATourist[0], valueOfATourist[1], valueOfATourist[2],
							valueOfATourist[3], valueOfATourist[4], valueOfATourist[5], valueOfATourist[6],
							valueOfATourist[7] };
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
				int selectedRowPosition = table.getSelectedRow();
				String nameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 2);
				String surnameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 3);
				String jmbgString = (String) tableModelTourist.getValueAt(selectedRowPosition, 4);
				String addressString = (String) tableModelTourist.getValueAt(selectedRowPosition, 6);
				String usernameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 7);
				if (usernameString.length() != 0) {
					changeUserDataForm(nameString, surnameString, jmbgString, addressString, usernameString);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}

			}
		});
		panel_5.add(btnNewButton, "cell 0 0,alignx center");

		JButton btnNewButton_1 = new JButton("Delete Tourist");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDeletionFrame();

			}
		});
		panel_5.add(btnNewButton_1, "cell 0 1,alignx center");

		JButton btnNewButton_2 = new JButton("Create Tourist");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTouristForm();
			}
		});
		panel_5.add(btnNewButton_2, "cell 0 2,alignx center");

		JButton btnNewButton_3 = new JButton("Add Tourist (Agent requested)");
		panel_5.add(btnNewButton_3, "cell 0 3");

		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, "cell 0 0, grow");

		// Here is admin tab

		JPanel panel1_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel1_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Administrators", null, panel1_1, null);

		JTable table1 = new JTable();
		table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table1.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForAdmins = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address", "Username" };
		DefaultTableModel tableModelAdmin = new DefaultTableModel(columnNamesForAdmins, 0);
		String[][] allAdmins = new String[0][8];
		String csvFile1 = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile1))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAAdmin = line.split("\\|");
				if ("Administrator".equals(valueOfAAdmin[1])) {
					String[] adminsStrings = { valueOfAAdmin[0], valueOfAAdmin[1], valueOfAAdmin[2], valueOfAAdmin[3],
							valueOfAAdmin[4], valueOfAAdmin[5], valueOfAAdmin[6], valueOfAAdmin[7] };
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
				int selectedRowPosition = table1.getSelectedRow();
				String nameString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 2);
				String surnameString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 3);
				String jmbgString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 4);
				String addressString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 6);
				String usernameString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 7);
				if (usernameString.length() != 0) {
					changeUserDataForm(nameString, surnameString, jmbgString, addressString, usernameString);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}
			}
		});
		panel_6.add(btnNewButton_6, "cell 0 0,alignx center");

		JButton btnNewButton_4 = new JButton("Delete Admin");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDeletionFrame();
			}
		});
		panel_6.add(btnNewButton_4, "cell 0 1,alignx center");

		JButton btnNewButton_5 = new JButton("Create Admin");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAdminForm();
			}
		});
		panel_6.add(btnNewButton_5, "cell 0 2,alignx center");

		JScrollPane scrollPane1 = new JScrollPane(table1);
		panel1_1.add(scrollPane1, "cell 0 0, grow");

		// Here is Agents tab
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Agents", null, panel_2, null);

		JTable table2 = new JTable();
		table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table2.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForAgents = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address", "Username" };
		DefaultTableModel tableModelAgents = new DefaultTableModel(columnNamesForAgents, 0);
		String[][] allAgents = new String[0][8];
		String csvFile2 = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile2))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAAgent = line.split("\\|");
				if ("Agent".equals(valueOfAAgent[1])) {
					String[] agentStrings = { valueOfAAgent[0], valueOfAAgent[1], valueOfAAgent[2], valueOfAAgent[3],
							valueOfAAgent[4], valueOfAAgent[5], valueOfAAgent[6], valueOfAAgent[7] };
					allAgents = Arrays.copyOf(allAgents, allAgents.length + 1);
					allAgents[i] = agentStrings;
					tableModelAgents.addRow(agentStrings);
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		table2.setModel(tableModelAgents);
		table2.setVisible(true);

		JPanel panel_7 = new JPanel();
		panel_2.add(panel_7);
		panel_7.setLayout(new MigLayout("", "[]", "[][][][]"));

		JButton btnNewButton7 = new JButton("Change Agent");
		btnNewButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowPosition = table2.getSelectedRow();
				String nameString = (String) tableModelAgents.getValueAt(selectedRowPosition, 2);
				String surnameString = (String) tableModelAgents.getValueAt(selectedRowPosition, 3);
				String jmbgString = (String) tableModelAgents.getValueAt(selectedRowPosition, 4);
				String addressString = (String) tableModelAgents.getValueAt(selectedRowPosition, 6);
				String usernameString = (String) tableModelAgents.getValueAt(selectedRowPosition, 7);
				if (usernameString.length() != 0) {
					changeUserDataForm(nameString, surnameString, jmbgString, addressString, usernameString);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}
			}
		});
		panel_7.add(btnNewButton7, "cell 0 0,alignx center");

		JButton btnNewButton_7_1 = new JButton("Delete Agent");
		btnNewButton_7_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDeletionFrame();
			}
		});
		panel_7.add(btnNewButton_7_1, "cell 0 1,alignx center");

		JButton btnNewButton_7_2 = new JButton("Create Agent");
		btnNewButton_7_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAgentForm();
			}
		});
		panel_7.add(btnNewButton_7_2, "cell 0 2,alignx center");

		JScrollPane scrollPane2 = new JScrollPane(table2);
		panel_2.add(scrollPane2, "cell 0 0, grow");

///////////////////////////////////////////////////////////////////////////////////////////
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Arrangments", null, panel_3, null);
		panel_3.setLayout(new MigLayout("fill"));

		ImageIcon imageIcon = createResizedImageIcon("src/image/arrangment1.jpg", 400, 300);
		JLabel lblImage = new JLabel(imageIcon);

		JPanel labelsPanel = new JPanel(new MigLayout("fill, gapy 30"));
		JLabel lblTitle = new JLabel("Title");
		JLabel lblDescription = new JLabel("Description");
		JLabel lblPrice = new JLabel("Price");
		JLabel lblCapacity = new JLabel("Capacity");
		JLabel lblFairDiscount = new JLabel("Discount");
		labelsPanel.add(lblTitle, "wrap");
		labelsPanel.add(lblDescription, "wrap");
		labelsPanel.add(lblPrice, "wrap");
		labelsPanel.add(lblCapacity, "wrap");
		labelsPanel.add(lblFairDiscount, "wrap");

		JPanel imageLabelsPanel = new JPanel(new MigLayout("fill"));
		imageLabelsPanel.add(lblImage, "cell 0 0");
		imageLabelsPanel.add(labelsPanel, "cell 0 1,alignx center");

		panel_3.add(imageLabelsPanel, "cell 0 0, grow");

		JPanel buttonsPanel22 = new JPanel(new MigLayout("fill"));
		JButton btn1 = new JButton("Add Arrangment");
		JButton btn2 = new JButton("Edit Arrangment");
		JButton btn3 = new JButton("Delete Arrangment");
		JButton btn4 = new JButton("Approve Agent Arrangment");
		buttonsPanel22.add(btn1, "alignx right,wrap");
		buttonsPanel22.add(btn2, "alignx right,wrap");
		buttonsPanel22.add(btn3, "alignx right,wrap");
		buttonsPanel22.add(btn4, "alignx right,wrap");
		panel_3.add(buttonsPanel22, "cell 1 0 1 2, grow");

		String[] tableModel4 = { "ID", "Title", "Capacity", "Fair Discout", "Price", "Address" };
		DefaultTableModel tableModelArrangments = new DefaultTableModel(tableModel4, 0);
		JTable table5 = new JTable();
		String[][] allArrangments = new String[0][5];
		String csvFile3 = "src/data/arrangments.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile3))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAArrangment = line.split("\\|");
				String[] arrangmentsStrings = { valueOfAArrangment[0], valueOfAArrangment[3], valueOfAArrangment[5],
						valueOfAArrangment[7], valueOfAArrangment[6], valueOfAArrangment[1] };
				allArrangments = Arrays.copyOf(allArrangments, allArrangments.length + 1);
				allArrangments[i] = arrangmentsStrings;
				tableModelArrangments.addRow(arrangmentsStrings);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		table5.setModel(tableModelArrangments);
		table5.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && table5.getSelectedRow() != -1) {
					int selectedRow = table5.getSelectedRow();
					if (selectedRow != -1) {
						String imagePath = (String) table5.getValueAt(selectedRow, 5);
						if (imagePath != null && !imagePath.isEmpty()) {
							ImageIcon selectedImageIcon = createResizedImageIcon(imagePath, 400, 300);
							lblImage.setIcon(selectedImageIcon);
						}
					}
				}
			}
		});
		JScrollPane arrangmentScrollPane = new JScrollPane(table5);
		panel_3.add(arrangmentScrollPane, "cell 2 0 1 2, grow");

//////////////////////////////////////////////////////////////////////////////////////////

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Reservations", null, panel_4, null);
	}

	private ImageIcon createResizedImageIcon(String path, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}

	private static void createTouristForm() {

		JFrame frame = new JFrame("Create Tourist");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(8, 2));

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
					String filePath = "src/data/userdata.csv";
					if (role == "Turist") {
						mainStructure.Turist user;
						try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
							String line;
							boolean notDuplicateUsernameOrJmbg = true;
							while ((line = reader.readLine()) != null) {
								String[] values = line.split("\\|");
								String checkUsername = values[7];
								String checkJMBG = values[4];
								if (username.equals(checkUsername) || jmbg.equals(checkJMBG)) {
									notDuplicateUsernameOrJmbg = false;
									String message = "Username or JMBG is already in use. Please try other credentials or contact support.";
									String title = "Information Dialog";
									JOptionPane.showMessageDialog(null, message, title,
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
							if (notDuplicateUsernameOrJmbg) {
								try {
									user = new Turist(name, surname, jmbg, address, username, password);
									String unos = user.userInfo();
									try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
										writer.write(unos);
										writer.newLine();
										System.out.println("Value written to the CSV file successfully.");
										reader.close();
										writer.close();
										frame.setVisible(false);
									} catch (IOException e2) {
										e2.printStackTrace();
									}
								} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
									e1.printStackTrace();
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("Error");
					}

				}

			}
		});
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private static void createAdminForm() {
		JFrame frame = new JFrame("Create Tourist");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(8, 2));

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
					String filePath = "src/data/userdata.csv";
					if (role == "Administrator") {
						mainStructure.Administrator user;
						try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
							String line;
							boolean notDuplicateUsernameOrJmbg = true;
							while ((line = reader.readLine()) != null) {
								String[] values = line.split("\\|");
								String checkUsername = values[7];
								String checkJMBG = values[4];
								if (username.equals(checkUsername) || jmbg.equals(checkJMBG)) {
									notDuplicateUsernameOrJmbg = false;
									String message = "Username or JMBG is already in use. Please try other credentials or contact support.";
									String title = "Information Dialog";
									JOptionPane.showMessageDialog(null, message, title,
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
							if (notDuplicateUsernameOrJmbg) {
								try {
									user = new Administrator(name, surname, jmbg, address, username, password);
									String unos = user.userInfo();
									try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
										writer.write(unos);
										writer.newLine();
										System.out.println("Value written to the CSV file successfully.");
										reader.close();
										writer.close();
										frame.setVisible(false);
									} catch (IOException e2) {
										e2.printStackTrace();
									}
								} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
									e1.printStackTrace();
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("Error");
					}

				}

			}
		});
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private static void createAgentForm() {
		JFrame frame = new JFrame("Create Agent");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(8, 2));

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
		String[] roles = { "Agent" };
		JComboBox<String> roleComboBox = new JComboBox<>(roles);

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
					String filePath = "src/data/userdata.csv";
					if (role == "Agent") {
						mainStructure.Agent user;

						try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
							String line;
							boolean notDuplicateUsernameOrJmbg = true;
							while ((line = reader.readLine()) != null) {
								String[] values = line.split("\\|");
								String checkUsername = values[7];
								String checkJMBG = values[4];
								if (username.equals(checkUsername) || jmbg.equals(checkJMBG)) {
									notDuplicateUsernameOrJmbg = false;
									String message = "Username or JMBG is already in use. Please try other credentials or contact support.";
									String title = "Information Dialog";
									JOptionPane.showMessageDialog(null, message, title,
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
							if (notDuplicateUsernameOrJmbg) {
								try {
									user = new Agent(name, surname, jmbg, address, username, password);
									String unos = user.userInfo();
									try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
										writer.write(unos);
										writer.newLine();
										System.out.println("Value written to the CSV file successfully.");
										reader.close();
										writer.close();
										frame.setVisible(false);
									} catch (IOException e2) {
										e2.printStackTrace();
									}
								} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
									e1.printStackTrace();
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("Error");
					}

				}

			}
		});
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	public static void deleteLineByUsername(String usernameToDelete) {
		String csvFile = "src/data/userdata.csv";
		String tempFile = "src/data/temp.csv";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] valuesOfALine = line.split("\\|");
				String username = valuesOfALine[7];

				if (!username.equals(usernameToDelete)) {
					writer.write(line);
					writer.newLine();
				}
			}

			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path source = Paths.get(tempFile);
		Path destination = Paths.get(csvFile);

		try {
			Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Success");
		} catch (IOException e) {
			System.out.println("Error renaming the file: " + e.getMessage());
		}
	}

	public void UserDeletionFrame() {
		JFrame frameForDeletionOfUser = new JFrame("Delete Tourist");
		frameForDeletionOfUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameForDeletionOfUser.setSize(400, 300);
		frameForDeletionOfUser.getContentPane().setLayout(new BorderLayout());

		// Create the form panel
		JPanel delPanel = new JPanel(new GridLayout(2, 1));

		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameTextField = new JTextField(20);
		JButton deleteButton = new JButton("Delete");

		delPanel.add(usernameLabel);
		delPanel.add(usernameTextField);

		frameForDeletionOfUser.getContentPane().add(delPanel, BorderLayout.CENTER);
		frameForDeletionOfUser.getContentPane().add(deleteButton, BorderLayout.SOUTH);

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				deleteLineByUsername(username);
				usernameTextField.setText("");
			}
		});

		frameForDeletionOfUser.setVisible(true);
	}

	public static void modifyUserData(String csvFile, String tempFile, String usernameToModify, String[] newData) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] valuesOfALine = line.split("\\|");
				String username = valuesOfALine[7];

				if (username.equals(usernameToModify)) {

					String modifiedLine = String.join("|", newData);

					if (newData.length == 5) {
						modifiedLine = valuesOfALine[0] + "|" + valuesOfALine[1] + "|" + newData[0] + "|" + newData[1]
								+ "|" + newData[2] + "|" + valuesOfALine[5] + "|" + newData[3] + "|" + newData[4] + "|"
								+ valuesOfALine[8] + "|" + valuesOfALine[9];
						writer.write(modifiedLine);
					} else if (newData.length == 7) {
						modifiedLine = valuesOfALine[0] + "|" + valuesOfALine[1] + "|" + newData[0] + "|" + newData[1]
								+ "|" + newData[2] + "|" + valuesOfALine[5] + "|" + newData[3] + "|" + newData[4] + "|"
								+ newData[5] + "|" + newData[6];
						writer.write(modifiedLine);
					}
				} else {

					writer.write(line);
				}
				writer.newLine();
			}

			reader.close();
			writer.close();

			Path source = Paths.get(tempFile);
			Path destination = Paths.get(csvFile);
			Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("User data modified successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void changeUserDataForm(String name, String surname, String jmbg, String address,
			String tableUsername) {

		JFrame frame = new JFrame("Change User Data");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(8, 2));

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
		JButton submitButton = new JButton("Submit");

		nameTextField.setText(name);
		surnameTextField.setText(surname);
		jmbgTextField.setText(jmbg);
		addressTextField.setText(address);
		usernameTextField.setText(tableUsername);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String surname = surnameTextField.getText();
				String jmbg = jmbgTextField.getText();
				String address = addressTextField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username)) {
					String unetaSifra = password;
					String filePath = "src/data/userdata.csv";
					String tempFilePathString = "src/data/temp.csv";

					try {
						if (unetaSifra.length() != 0) {
							byte[] salt = auth.Pbkdf2.generateSalt();
							byte[] hash = auth.Pbkdf2.getEncryptedPassword(unetaSifra, salt);
							System.out.println(tableUsername);
							String[] newLine = { name, surname, jmbg, address, username, auth.Pbkdf2.bytesToHex(hash),
									auth.Pbkdf2.bytesToHex(salt) };
							modifyUserData(filePath, tempFilePathString, tableUsername, newLine);
							System.out.println("Modifying...");
							frame.setVisible(false);
						} else {
							System.out.println(tableUsername);
							String[] newLine = { name, surname, jmbg, address, username };
							modifyUserData(filePath, tempFilePathString, tableUsername, newLine);
							System.out.println("Modifying...");
							frame.setVisible(false);
						}
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
