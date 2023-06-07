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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import mainStructure.Administrator;
import mainStructure.Agent;
import mainStructure.Arrangment;
import mainStructure.Status;
import mainStructure.Turist;
import mainStructure.TypeOfAccommodation;
import mainStructure.TypeOfArrangement;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import validation.validation;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.FlowLayout;

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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(true);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForTourist = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address","Phone Number", "Username" };
		DefaultTableModel tableModelTourist = new DefaultTableModel(columnNamesForTourist, 0);
		String[][] allTourists = new String[0][9];
		String csvFile = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfATourist = line.split("\\|");
				if ("Turist".equals(valueOfATourist[1])) {
					String[] touristStrings = { valueOfATourist[0], valueOfATourist[1], valueOfATourist[2],
							valueOfATourist[3], valueOfATourist[4], valueOfATourist[5], valueOfATourist[6],
							valueOfATourist[7],valueOfATourist[8] };
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
				String phoneNumberString = (String) tableModelTourist.getValueAt(selectedRowPosition,7 );
				String usernameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 8);
				if (usernameString.length() != 0) {
					changeUserDataForm(nameString, surnameString, jmbgString, addressString,phoneNumberString ,usernameString);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}

			}
		});
		panel_5.add(btnNewButton, "cell 0 0,alignx center");

		JButton btnNewButton_1 = new JButton("Delete Tourist");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowPosition = table.getSelectedRow();
				String usernameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 8);
				deleteLineByUsername(usernameString);
				tableModelTourist.removeRow(selectedRowPosition);

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
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table1.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForAdmins = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address","Phone Number", "Username" };
		DefaultTableModel tableModelAdmin = new DefaultTableModel(columnNamesForAdmins, 0);
		String[][] allAdmins = new String[0][9];
		String csvFile1 = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile1))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAAdmin = line.split("\\|");
				if ("Administrator".equals(valueOfAAdmin[1])) {
					String[] adminsStrings = { valueOfAAdmin[0], valueOfAAdmin[1], valueOfAAdmin[2], valueOfAAdmin[3],
							valueOfAAdmin[4], valueOfAAdmin[5], valueOfAAdmin[6], valueOfAAdmin[7],valueOfAAdmin[8] };
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
				String phoneNumberString = (String)tableModelAdmin.getValueAt(selectedRowPosition, 7); 
				String usernameString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 8);
				if (usernameString.length() != 0) {
					changeUserDataForm(nameString, surnameString, jmbgString, addressString,phoneNumberString, usernameString);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}
			}
		});
		panel_6.add(btnNewButton_6, "cell 0 0,alignx center");

		JButton btnNewButton_4 = new JButton("Delete Admin");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowPosition = table1.getSelectedRow();
				String usernameString = (String) tableModelAdmin.getValueAt(selectedRowPosition, 8);
				deleteLineByUsername(usernameString);
				tableModelAdmin.removeRow(selectedRowPosition);
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
		table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table2.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForAgents = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address","Phone Number", "Username" };
		DefaultTableModel tableModelAgents = new DefaultTableModel(columnNamesForAgents, 0);
		String[][] allAgents = new String[0][9];
		String csvFile2 = "src/data/userdata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile2))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAAgent = line.split("\\|");
				if ("Agent".equals(valueOfAAgent[1])) {
					String[] agentStrings = { valueOfAAgent[0], valueOfAAgent[1], valueOfAAgent[2], valueOfAAgent[3],
							valueOfAAgent[4], valueOfAAgent[5], valueOfAAgent[6], valueOfAAgent[7],valueOfAAgent[8] };
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
				String phoneNumberString = (String)tableModelAgents.getValueAt(selectedRowPosition,7); 
				String usernameString = (String) tableModelAgents.getValueAt(selectedRowPosition, 8);
				if (usernameString.length() != 0) {
					changeUserDataForm(nameString, surnameString, jmbgString, addressString,phoneNumberString, usernameString);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}
			}
		});
		panel_7.add(btnNewButton7, "cell 0 0,alignx center");

		JButton btnNewButton_7_1 = new JButton("Delete Agent");
		btnNewButton_7_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowPosition = table2.getSelectedRow();
				String usernameString = (String) tableModelAgents.getValueAt(selectedRowPosition, 8);
				deleteLineByUsername(usernameString);
				tableModelAgents.removeRow(selectedRowPosition);
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

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Arrangments", null, panel_3, null);
		panel_3.setLayout(new MigLayout("fill"));

		ImageIcon imageIcon = createResizedImageIcon("src/image/arrangment1.jpg", 400, 300);
		JLabel lblImage = new JLabel(imageIcon);

		JPanel labelsPanel = new JPanel(new MigLayout("fill, gapy 30"));

		JLabel lblTypeOdArrangment = new JLabel("Type of Arrangment: ");
		labelsPanel.add(lblTypeOdArrangment, "wrap");

		JLabel lblTypeOfAccomodation = new JLabel("Type of Accomodation: ");
		labelsPanel.add(lblTypeOfAccomodation, "wrap");

		JLabel lblNumberOfOvernightStaysJLabel = new JLabel("Number of Overnight Stays: ");
		labelsPanel.add(lblNumberOfOvernightStaysJLabel, "wrap");

		JLabel lblAvailableDate = new JLabel("Available Date: ");
		labelsPanel.add(lblAvailableDate, "wrap");

		JLabel lblNumberOfRooms = new JLabel("Number of Available Rooms: ");
		labelsPanel.add(lblNumberOfRooms, "wrap");

		JLabel lblUnitPrice = new JLabel("Unit Price: ");
		labelsPanel.add(lblUnitPrice, "wrap");

		JLabel lblFairDiscount = new JLabel("Discount: ");
		labelsPanel.add(lblFairDiscount, "wrap");

		JPanel imageLabelsPanel = new JPanel(new MigLayout("fill"));
		imageLabelsPanel.add(lblImage, "cell 0 0");
		imageLabelsPanel.add(labelsPanel, "cell 0 1,alignx center");

		panel_3.add(imageLabelsPanel, "cell 0 0, grow");

		JPanel buttonsPanel22 = new JPanel(new MigLayout("fill"));
		JButton btn1 = new JButton("Add Arrangment");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createArrangmentForm();

			}
		});
		JButton btn2 = new JButton("Edit Arrangment");
		JButton btn3 = new JButton("Delete Arrangment");

		JButton btn4 = new JButton("Approve Agent Arrangment");
		buttonsPanel22.add(btn1, "alignx right,wrap");
		buttonsPanel22.add(btn2, "alignx right,wrap");
		buttonsPanel22.add(btn3, "alignx right,wrap");
		buttonsPanel22.add(btn4, "alignx right,wrap");
		panel_3.add(buttonsPanel22, "cell 1 0 1 2, grow");

		String[] tableModel4 = { "ID", "SellerID", "Type Arrangment", "Image", "Available Date",
				"Number Overnight Stays", "Number Of Rooms", "Type Accomodation", "Unit Price", "Fair Discout" };
		DefaultTableModel tableModelArrangments = new DefaultTableModel(tableModel4, 0);
		JTable table5 = new JTable();
		table5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[][] allArrangments = new String[0][8];
		String csvFile3 = "src/data/arrangments.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile3))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAArrangment = line.split("\\|");
				String[] arrangmentsStrings = { valueOfAArrangment[0], valueOfAArrangment[1], valueOfAArrangment[2],
						valueOfAArrangment[3], valueOfAArrangment[4], valueOfAArrangment[5], valueOfAArrangment[6],
						valueOfAArrangment[7], valueOfAArrangment[8], valueOfAArrangment[9] };

				allArrangments = Arrays.copyOf(allArrangments, allArrangments.length + 1);
				allArrangments[i] = arrangmentsStrings;
				tableModelArrangments.addRow(arrangmentsStrings);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowOfSelectedArrangmentID = table5.getSelectedRow();
				if (rowOfSelectedArrangmentID != -1) {
					int selectedArrangmentID = (int) rowOfSelectedArrangmentID;
					changeArrangmentData(selectedArrangmentID);
				}
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowOfSelectedArrangmentID = table5.getSelectedRow();
				if (rowOfSelectedArrangmentID != -1) {
					String selectedArrangmentID = (String) table5.getValueAt(rowOfSelectedArrangmentID, 0);
					deleteLineArrangment(selectedArrangmentID);
					tableModelArrangments.removeRow(rowOfSelectedArrangmentID);
				}
			}
		});

		table5.setModel(tableModelArrangments);
		table5.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && table5.getSelectedRow() != -1) {
					int selectedRow = table5.getSelectedRow();

					if (selectedRow != -1) {
						String imagePath = (String) table5.getValueAt(selectedRow, 3);
						if (imagePath != null && !imagePath.isEmpty()) {
							String typeOfArrangmentString = (String) tableModelArrangments.getValueAt(selectedRow, 2);
							String availableDateString = (String) tableModelArrangments.getValueAt(selectedRow, 4);
							String numberOfOvernightStayString = (String) tableModelArrangments.getValueAt(selectedRow,
									5);
							String numberOfRooms = (String) tableModelArrangments.getValueAt(selectedRow, 6);
							String typeOfAccomodationString = (String) tableModelArrangments.getValueAt(selectedRow, 7);
							String unitPrice = (String) tableModelArrangments.getValueAt(selectedRow, 8);
							String fairDiscount = (String) tableModelArrangments.getValueAt(selectedRow, 9);

							lblTypeOdArrangment.setText("Type of arrangment: " + typeOfArrangmentString);
							lblTypeOfAccomodation.setText("Type of Accomodation: " + typeOfAccomodationString);
							lblNumberOfRooms.setText("Number Of Rooms: " + numberOfRooms);
							lblNumberOfOvernightStaysJLabel
									.setText("Number Of Overnight Stays: " + numberOfOvernightStayString);
							lblAvailableDate.setText("Available Date: " + availableDateString);
							lblFairDiscount.setText("Fair Discount: " + fairDiscount);
							lblUnitPrice.setText("Unit Price: " + unitPrice);

							ImageIcon selectedImageIcon = createResizedImageIcon(imagePath, 400, 300);
							lblImage.setIcon(selectedImageIcon);
						}
					}
				}
			}
		});
		JScrollPane arrangmentScrollPane = new JScrollPane(table5);
		panel_3.add(arrangmentScrollPane, "cell 2 0 1 2, grow");

		
		JPanel panel_4 = new JPanel(new BorderLayout());
        tabbedPane.addTab("Reservations", null, panel_4, null);

        JPanel leftPanelReservation = new JPanel();
        leftPanelReservation.setLayout(new BoxLayout(leftPanelReservation, BoxLayout.Y_AXIS));
        panel_4.add(leftPanelReservation, BorderLayout.WEST);

        leftPanelReservation.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JButton approveReservationButton = new JButton("Approve Reservation");
        approveReservationButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, approveReservationButton.getPreferredSize().height));
        leftPanelReservation.add(approveReservationButton);

        leftPanelReservation.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JButton deleteReservationButton = new JButton("Delete Reservation");
        deleteReservationButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, deleteReservationButton.getPreferredSize().height));
        leftPanelReservation.add(deleteReservationButton);

        String[] columnNamesReservations = {"ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers", "Date and Time"};
        DefaultTableModel tableModelReservation = new DefaultTableModel(columnNamesReservations, 0);
        JTable reservationTable = new JTable(tableModelReservation);

        approveReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedReservationRow = reservationTable.getSelectedRow();
        		String arrangmentIdString = (String) reservationTable.getValueAt(selectedReservationRow, 1);
        		if (selectedReservationRow != -1) {
                    reservationTable.setValueAt(Status.Completed, selectedReservationRow, 3);
                    changeStatusOfReservation(selectedReservationRow,arrangmentIdString, Status.Completed,reservationTable);
                } else {
                    System.out.println("No row selected.");
                }
        	}
        });
        deleteReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedReservationRow = reservationTable.getSelectedRow();
        		String arrangmentIdString = (String) reservationTable.getValueAt(selectedReservationRow, 1);
        		if (selectedReservationRow != -1) {
                    
                    changeStatusOfReservation(selectedReservationRow, arrangmentIdString, Status.Canceled,reservationTable);  
                } else {
                    System.out.println("No row selected.");
                }
        	}
        });
        
        JScrollPane scrollReservationPane = new JScrollPane(reservationTable);
        panel_4.add(scrollReservationPane, BorderLayout.CENTER);
		
        String filePath = "src/data/reservations.csv";
        loadReservationData(filePath,reservationTable);
	}
	
	public static void incrementNumberOfRooms(String arrangementId) {
	    String filePath = "src/data/arrangments.csv";
	    int targetPosition = -1;

	    List<String> lines = new ArrayList<>();
	    try {
	        lines = Files.readAllLines(Paths.get(filePath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    for (int i = 0; i < lines.size(); i++) {
	        String line = lines.get(i);
	        String[] fields = line.split("\\|");

	        if (fields.length >= 1 && fields[0].equals(arrangementId)) {
	            targetPosition = i;
	            break;
	        }
	    }

	    if (targetPosition != -1) {
	        String line = lines.get(targetPosition);
	        String[] fields = line.split("\\|");

	        if (fields.length >= 7) {
	            int numRooms = Integer.parseInt(fields[6]);
	            numRooms++;
	            fields[6] = String.valueOf(numRooms);

	            String updatedLine = String.join("|", fields);
	            lines.set(targetPosition, updatedLine);

	            try {
	                Files.write(Paths.get(filePath), lines);
	                System.out.println("Number of rooms incremented successfully for arrangement ID: " + arrangementId);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    } else {
	        System.out.println("Arrangement ID not found: " + arrangementId);
	    }
	}
	
	public static void decrementNumberOfRooms(String arrangementId) {
	    // This function should decrement the number of rooms for a specific arrangement ID in the "arrangments.csv" file
	    String filePath = "src/data/arrangments.csv";
	    int targetPosition = -1;

	    // Read the contents of the CSV file into a list
	    List<String> lines = new ArrayList<>();
	    try {
	        lines = Files.readAllLines(Paths.get(filePath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Locate the line with the specified arrangementId
	    for (int i = 0; i < lines.size(); i++) {
	        String line = lines.get(i);
	        String[] fields = line.split("\\|");

	        if (fields.length >= 1 && fields[0].equals(arrangementId)) {
	            targetPosition = i;
	            break;
	        }
	    }

	    if (targetPosition != -1) {
	        String line = lines.get(targetPosition);
	        String[] fields = line.split("\\|");

	        if (fields.length >= 7) {
	            int numRooms = Integer.parseInt(fields[6]);
	            if (numRooms > 0) {
	                numRooms--; // Decrement the number of rooms by 1
	                fields[6] = String.valueOf(numRooms);

	                // Join the fields back into a line
	                String updatedLine = String.join("|", fields);
	                lines.set(targetPosition, updatedLine);

	                // Write the updated data back to the CSV file
	                try {
	                    Files.write(Paths.get(filePath), lines);
	                    System.out.println("Number of rooms decremented successfully for arrangement ID: " + arrangementId);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            } else {
//	            	this.status = Status.Failed;//So here if it fails it is going to update status as failed 
	                System.out.println("Cannot decrement number of rooms. The room count is already 0 for arrangement ID: " + arrangementId);
	            }
	        }
	    } else {
	        System.out.println("Arrangement ID not found: " + arrangementId);
	    }
	}
	
	protected int numberOfAvailableRooms(String arrangmentIdString) {
		String filePathForNumberOfAvailableRooms = "src/data/arrangments.csv";
        

        try (BufferedReader br = new BufferedReader(new FileReader(filePathForNumberOfAvailableRooms))) {
            String line1;
            while ((line1 = br.readLine()) != null) {
                String[] values = line1.split("\\|");

                if (values.length >= 6 && values[0].equals(arrangmentIdString)) {
                    final String numberOfAvailableRoomString = values[6];	                    
                    int numberOfAvailableRooms = Integer.parseInt(numberOfAvailableRoomString);
                    return numberOfAvailableRooms;
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return 0;
	}
	
	private void changeStatusOfReservation(int selectedReservationRow,String arrangmentIdString ,Status status,JTable reservationTable) {
	    if (selectedReservationRow != -1) {
	        String csvFile = "src/data/reservations.csv";
	        String tempFile = "src/data/tempReservation.csv";

	        
	        
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
	             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

	            String line;
	            int rowCounter = 0;

	            while ((line = br.readLine()) != null) {
	                if (rowCounter == selectedReservationRow) {
	                    String[] columns = line.split("\\|");
	                    if (status == Status.Canceled) {
	                        if (columns.length >= 4) {
	                        	if(columns[3].equals("Created")) {
	                        		columns[3] = "Failed";
	                        		line = String.join("|", columns);
	                        		incrementNumberOfRooms(arrangmentIdString);
	                        		reservationTable.setValueAt(Status.Failed, selectedReservationRow, 3);
	                        	}else if(columns[3].equals("Completed")) {
	                        		columns[3] = "Failed";
	                        		line = String.join("|", columns);
	                        		incrementNumberOfRooms(arrangmentIdString);
	                        		reservationTable.setValueAt(Status.Failed, selectedReservationRow, 3);
	                        	}else if(columns[3].equals("Canceled")) {
	                        		columns[3] = "Failed";
	                        		line = String.join("|", columns);
	                        		incrementNumberOfRooms(arrangmentIdString);
	                        		reservationTable.setValueAt(Status.Failed, selectedReservationRow, 3);
	                        	}
	                        	
	                        	
	                        } else {
	                            System.out.println("Invalid data format.");
	                            continue;
	                        }
	                    }
	                    if (status == Status.Completed) {
	                        if (columns.length >= 4) {
	                        	if(numberOfAvailableRooms(arrangmentIdString) != 0) {
	                        		columns[3] = "Completed";
	                        		line = String.join("|", columns);
	                        		//Here should decrement number of rooms
	                        		reservationTable.setValueAt(Status.Completed, selectedReservationRow, 3);
	                        		decrementNumberOfRooms(arrangmentIdString);	                        		
	                        	}else {
	                        		System.out.println("All rooms are occupied.");
	                        	}
	                        } else {
	                            System.out.println("Invalid data format.");
	                            continue;
	                        }
	                    }
	                }
	                bw.write(line);
	                bw.newLine();
	                rowCounter++;
	            }

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }

	        try {
	            Files.copy(Path.of(tempFile), Path.of(csvFile), StandardCopyOption.REPLACE_EXISTING);
	            Files.delete(Path.of(tempFile));
	            System.out.println("Status updated successfully.In case of enough rooms.");
	        } catch (IOException ex) {
	            System.out.println("Failed to update status.");
	            ex.printStackTrace();
	        }
	    } else {
	        System.out.println("No row selected.");
	    }
	}

	private void loadReservationData(String filePath,JTable reservationTable) {
        DefaultTableModel tableModel = (DefaultTableModel) reservationTable.getModel();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private ImageIcon createResizedImageIcon(String path, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}

	public static String[] loadRowData(String csvFilePath, int rowId) {
		String[] rowData = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
			String line;
			int currentRowId = 0;

			while ((line = reader.readLine()) != null) {
				if (currentRowId == rowId) {
					rowData = line.split("\\|");
					break;
				}

				currentRowId++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowData;
	}

	public static TypeOfAccommodation getTypeOfAccommodation(int ordinal) {
		if (ordinal >= 0 && ordinal < TypeOfAccommodation.values().length) {
			return TypeOfAccommodation.values()[ordinal];
		} else {

			return null;
		}
	}

	public static TypeOfArrangement getTypeOfArrangment(int ordinal) {
		if (ordinal >= 0 && ordinal < TypeOfArrangement.values().length) {
			return TypeOfArrangement.values()[ordinal];
		} else {

			return null;
		}
	}

	private static String formatDate(JDatePicker datePicker, Object date) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
			GregorianCalendar cal = new GregorianCalendar();
			String datum = format.format(datePicker.getModel().getValue());
			try {
				cal.setTime(format.parse(datum));
				String formattedDate = format.format(cal.getTime());
				return formattedDate;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "";
	}

	public static void changeArrangmentData(int selectedRowID) {
		JFrame frame = new JFrame("Arrangement Form");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(650, 700);
		frame.getContentPane().setLayout(new GridLayout(15, 2));

		String[] rowData = loadRowData("src\\data\\arrangments.csv", selectedRowID);

		JLabel idLabel = new JLabel("ID:");
		JTextField idTextField = new JTextField(10);

		JLabel sellerIDLabel = new JLabel("Seller ID:");
		JTextField sellerTextField = new JTextField(10);

		JLabel pictureLabel = new JLabel("Picture:");
		JTextField pictureTextField = new JTextField(10);

		JLabel typeOfAccomodationLabel = new JLabel("Type of Accomodation:");
		JComboBox<TypeOfAccommodation> accomodationBox = new JComboBox<>(TypeOfAccommodation.values());

		JLabel typeOfArrangmentLabel = new JLabel("Type of Arrangement:");
		JComboBox<TypeOfArrangement> arrangmentBox = new JComboBox<>(TypeOfArrangement.values());

		JLabel availableDateLabel = new JLabel("Available Date:");
		UtilDateModel dateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

		JLabel priceLabel = new JLabel("Price:");
		JTextField priceTextField = new JTextField(10);

		JLabel fairDiscountLabel = new JLabel("Fair Discount:");
		JTextField fairDiscountTextField = new JTextField(10);

		JLabel numberOfRoomsLabel = new JLabel("Number of Rooms:");
		JTextField numberOfRoomsTextField = new JTextField(10);

		JLabel numberOfOvernightStaysLabel = new JLabel("Number of Overnight Stays:");
		JTextField numberOfOvernightStaysTextField = new JTextField(10);

		if (rowData != null) {
			idTextField.setText(rowData[0]);
			sellerTextField.setText(rowData[1]);
			pictureTextField.setText(rowData[3]);
			int ordinalAccomodation = Integer.parseInt(rowData[7]);
			accomodationBox.setSelectedItem(getTypeOfAccommodation(ordinalAccomodation));

			int ordinalArrangment = Integer.parseInt(rowData[2]);
			arrangmentBox.setSelectedItem(getTypeOfArrangment(ordinalArrangment));
			String dayString = rowData[4].substring(0, 2);
			int day = Integer.parseInt(dayString);
			String monthString = rowData[4].substring(3, 5);
			int month = Integer.parseInt(monthString);
			String yearString = rowData[4].substring(6, 10);
			int year = Integer.parseInt(yearString);
			datePicker.getModel().setDay(day);
			datePicker.getModel().setMonth(month);
			datePicker.getModel().setYear(year);

			priceTextField.setText(rowData[8]);
			fairDiscountTextField.setText(rowData[9]);
			numberOfRoomsTextField.setText(rowData[6]);
			numberOfOvernightStaysTextField.setText(rowData[5]);
		} else {
			JOptionPane.showMessageDialog(frame, "Row not found.");
		}

		frame.getContentPane().add(idLabel);
		frame.getContentPane().add(idTextField);
		frame.getContentPane().add(sellerIDLabel);
		frame.getContentPane().add(sellerTextField);
		frame.getContentPane().add(pictureLabel);
		frame.getContentPane().add(pictureTextField);
		frame.getContentPane().add(typeOfAccomodationLabel);
		frame.getContentPane().add(accomodationBox);
		frame.getContentPane().add(typeOfArrangmentLabel);
		frame.getContentPane().add(arrangmentBox);
		frame.getContentPane().add(availableDateLabel);
		frame.getContentPane().add(datePicker);
		frame.getContentPane().add(priceLabel);
		frame.getContentPane().add(priceTextField);
		frame.getContentPane().add(fairDiscountLabel);
		frame.getContentPane().add(fairDiscountTextField);
		frame.getContentPane().add(numberOfRoomsLabel);
		frame.getContentPane().add(numberOfRoomsTextField);
		frame.getContentPane().add(numberOfOvernightStaysLabel);
		frame.getContentPane().add(numberOfOvernightStaysTextField);

		boolean deleted = false;
		JButton editButton = new JButton("Edit Arrangement");
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				StringBuilder csvData = new StringBuilder();

				try {
					List<String> lines = Files.readAllLines(Paths.get("src\\data\\arrangments.csv"));

					for (int i = 0; i < lines.size(); i++) {
						String line = lines.get(i);

						if (i == selectedRowID) {
							String newData = idTextField.getText() + "|" + sellerTextField.getText() + "|"
									+ arrangmentBox.getSelectedIndex() + "|" + pictureTextField.getText() + "|"
									+ formatDate(datePicker, datePicker.getModel().getValue()) + "|"
									+ numberOfOvernightStaysTextField.getText() + "|" + numberOfRoomsTextField.getText()
									+ "|" + accomodationBox.getSelectedIndex() + "|" + priceTextField.getText() + "|"
									+ fairDiscountTextField.getText() + "|" + deleted;

							csvData.append(newData).append("\n");
							frame.dispose();
						} else {

							csvData.append(line).append("\n");
						}
					}

					Files.write(Paths.get("src\\data\\arrangments.csv"), csvData.toString().getBytes());
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(editButton);
		frame.getContentPane().add(buttonPanel);

		frame.setVisible(true);
	}

	public static void deleteLineArrangment(String IdArrangmentToDelete) {
		String csvFile = "src/data/arrangments.csv";
		String tempFile = "src/data/temp1.csv";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] valuesOfALine = line.split("\\|");
				String username = valuesOfALine[0];

				if (!username.equals(IdArrangmentToDelete)) {
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

	private static void createArrangmentForm() {
		JFrame frame = new JFrame("Arrangement Form");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(650, 700);
		frame.getContentPane().setLayout(new GridLayout(15, 2));

		JLabel idLabel = new JLabel("ID:");
		JTextField idTextField = new JTextField(10);

		JLabel sellerIDLabel = new JLabel("Seller ID:");
		JTextField sellerTextField = new JTextField(10);

		JLabel pictureLabel = new JLabel("Picture:");
		JTextField pictureTextField = new JTextField(10);

		JLabel typeOfAccomodationLabel = new JLabel("Type of Accomodation:");
		JComboBox<TypeOfAccommodation> accomodationBox = new JComboBox<>(TypeOfAccommodation.values());

		JLabel typeOfArrangmentLabel = new JLabel("Type of Arrangement:");
		JComboBox<TypeOfArrangement> arrangmentBox = new JComboBox<>(TypeOfArrangement.values());

		JLabel availableDateLabel = new JLabel("Available Date:");
		UtilDateModel dateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

		JLabel priceLabel = new JLabel("Price:");
		JTextField priceTextField = new JTextField(10);

		JLabel fairDiscountLabel = new JLabel("Fair Discount:");
		JTextField fairDiscountTextField = new JTextField(10);

		JLabel numberOfRoomsLabel = new JLabel("Number of Rooms:");
		JTextField numberOfRoomsTextField = new JTextField(10);

		JLabel numberOfOvernightStaysLabel = new JLabel("Number of Overnight Stays:");
		JTextField numberOfOvernightStaysTextField = new JTextField(10);

		frame.getContentPane().add(idLabel);
		frame.getContentPane().add(idTextField);
		frame.getContentPane().add(sellerIDLabel);
		frame.getContentPane().add(sellerTextField);
		frame.getContentPane().add(pictureLabel);
		frame.getContentPane().add(pictureTextField);
		frame.getContentPane().add(typeOfAccomodationLabel);
		frame.getContentPane().add(accomodationBox);
		frame.getContentPane().add(typeOfArrangmentLabel);
		frame.getContentPane().add(arrangmentBox);
		frame.getContentPane().add(availableDateLabel);
		frame.getContentPane().add(datePicker);
		frame.getContentPane().add(priceLabel);
		frame.getContentPane().add(priceTextField);
		frame.getContentPane().add(fairDiscountLabel);
		frame.getContentPane().add(fairDiscountTextField);
		frame.getContentPane().add(numberOfRoomsLabel);
		frame.getContentPane().add(numberOfRoomsTextField);
		frame.getContentPane().add(numberOfOvernightStaysLabel);
		frame.getContentPane().add(numberOfOvernightStaysTextField);

		boolean deleted = false;
		JButton createButton = new JButton("Create Arrangement");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
				GregorianCalendar cal = new GregorianCalendar();
				String datum = format.format(datePicker.getModel().getValue());
				try {
					cal.setTime(format.parse(datum));
					String formattedDate = format.format(cal.getTime());
					long longIdPrimitive1 = Long.parseLong(idTextField.getText());
					long longSellerIdPrimitive1 = Long.parseLong(sellerTextField.getText());
					Arrangment arrangement = new Arrangment(longIdPrimitive1, longSellerIdPrimitive1,
							pictureTextField.getText(), (TypeOfArrangement) arrangmentBox.getSelectedItem(),
							formattedDate, priceTextField.getText(), fairDiscountTextField.getText(), deleted,
							(TypeOfAccommodation) accomodationBox.getSelectedItem(), numberOfRoomsTextField.getText(),
							numberOfOvernightStaysTextField.getText());
					String arrangmentInfoLineString = arrangement.getInfo();

					if (validation.isNumeric(idTextField.getText()) && validation.isNumeric(sellerTextField.getText())
							&& validation.isNumeric(numberOfOvernightStaysTextField.getText())
							&& validation.isNumeric(numberOfRoomsTextField.getText())
							&& validation.isNumeric(priceTextField.getText())
							&& validation.isNumeric(fairDiscountTextField.getText())) {
						handleCreateArrangement(arrangmentInfoLineString);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "Please fill in all required fields.");
					}

				} catch (ParseException e1) {
					e1.printStackTrace();
				}

			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(createButton);
		frame.getContentPane().add(buttonPanel);

		frame.setVisible(true);

	}

	private static void handleCreateArrangement(String arrangmentLineToWrite) {
		String CSV_FILE_PATH = "src/data/arrangments.csv";
		try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
			StringBuilder sb = new StringBuilder();
			sb.append(arrangmentLineToWrite);
			writer.write(sb.toString());
			writer.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		JLabel phoneNumberJLabel = new JLabel("Phone Number:");
		JTextField phoneNumberTextField = new JTextField();
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
		formPanel.add(phoneNumberJLabel);
		formPanel.add(phoneNumberTextField);
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
				String phoneNumberString = phoneNumberTextField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				String role = (String) roleComboBox.getSelectedItem();
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username) && validation.IsValidPassword(password) && validation.isValidPhoneNumber(phoneNumberString)) {
					String filePath = "src/data/userdata.csv";
					if (role == "Turist") {
						mainStructure.Turist user;
						try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
							String line;
							boolean notDuplicateUsernameOrJmbg = true;
							while ((line = reader.readLine()) != null) {
								String[] values = line.split("\\|");
								String checkUsername = values[8];
								String checkJMBG = values[4];
								if (username.equals(checkUsername) || jmbg.equals(checkJMBG)) {
									notDuplicateUsernameOrJmbg = false;
									String message = "Username or JMBG is already in use. Please try other credentials or contact support.";
									String title = "Information Dialog";
									JOptionPane.showMessageDialog(null, message, title,JOptionPane.INFORMATION_MESSAGE);
								}
							}
							if (notDuplicateUsernameOrJmbg) {
								try {
									user = new Turist(name, surname, jmbg, address,phoneNumberString, username, password);
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
		JLabel phoneNumberLabel = new JLabel("Phone Number");
		JTextField phoneNumberTextField = new JTextField();
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
		formPanel.add(phoneNumberLabel);
		formPanel.add(phoneNumberTextField);
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
				String phoneNumberString = phoneNumberTextField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				String role = (String) roleComboBox.getSelectedItem();
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username) && validation.isValidPhoneNumber(phoneNumberString) && validation.IsValidPassword(password)) {
					String filePath = "src/data/userdata.csv";
					if (role == "Administrator") {
						mainStructure.Administrator user;
						try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
							String line;
							boolean notDuplicateUsernameOrJmbg = true;
							while ((line = reader.readLine()) != null) {
								String[] values = line.split("\\|");
								String checkUsername = values[8];
								String checkJMBG = values[4];
								if (username.equals(checkUsername) || jmbg.equals(checkJMBG)) {
									notDuplicateUsernameOrJmbg = false;
									String message = "Username or JMBG is already in use. Please try other credentials or contact support.";
									String title = "Information Dialog";
									JOptionPane.showMessageDialog(null, message, title,JOptionPane.INFORMATION_MESSAGE);
								}
							}
							if (notDuplicateUsernameOrJmbg) {
								try {
									user = new Administrator(name, surname, jmbg, address,phoneNumberString, username, password);
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
		JLabel phoneNumberJLabel = new JLabel("Phone Number:");
		JTextField phoneNumberField = new JTextField();
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
		formPanel.add(phoneNumberJLabel);
		formPanel.add(phoneNumberField);
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
				String phoneNumberString = phoneNumberField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				String role = (String) roleComboBox.getSelectedItem();
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username) && validation.isValidPhoneNumber(phoneNumberString) && validation.IsValidPassword(password)) {
					String filePath = "src/data/userdata.csv";
					if (role == "Agent") {
						mainStructure.Agent user;

						try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
							String line;
							boolean notDuplicateUsernameOrJmbg = true;
							while ((line = reader.readLine()) != null) {
								String[] values = line.split("\\|");
								String checkUsername = values[8];
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
									user = new Agent(name, surname, jmbg, address,phoneNumberString, username, password);
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
				String username = valuesOfALine[8];

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

	public static void modifyUserData(String csvFile, String tempFile, String usernameToModify, String[] newData) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] valuesOfALine = line.split("\\|");
				String username = valuesOfALine[8];

				if (username.equals(usernameToModify)) {

					String modifiedLine = String.join("|", newData);

					if (newData.length == 6) {
						modifiedLine = valuesOfALine[0] + "|" + valuesOfALine[1] + "|" + newData[0] + "|" + newData[1]
								+ "|" + newData[2] + "|" + valuesOfALine[5] + "|" + newData[3] + "|" + newData[4] +"|"+newData[5]+ "|"
								+ valuesOfALine[9] + "|" + valuesOfALine[10];
						writer.write(modifiedLine);
					} else if (newData.length == 8) {
						modifiedLine = valuesOfALine[0] + "|" + valuesOfALine[1] + "|" + newData[0] + "|" + newData[1]
								+ "|" + newData[2] + "|" + valuesOfALine[5] + "|" + newData[3] + "|" + newData[4] + "|"
								+ newData[5] + "|" + newData[6] +"|"+newData[7];
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

	private static void changeUserDataForm(String name, String surname, String jmbg, String address,String phoneNumber,
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
		JLabel phoneNumberJLabel = new JLabel("Phone Number:");
		JTextField phoneNumberField = new JTextField();
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
		formPanel.add(phoneNumberJLabel);
		formPanel.add(phoneNumberField);
		formPanel.add(usernameLabel);
		formPanel.add(usernameTextField);
		formPanel.add(passwordLabel);
		formPanel.add(passwordField);
		JButton submitButton = new JButton("Submit");

		nameTextField.setText(name);
		surnameTextField.setText(surname);
		jmbgTextField.setText(jmbg);
		addressTextField.setText(address);
		phoneNumberField.setText(phoneNumber);
		usernameTextField.setText(tableUsername);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String surname = surnameTextField.getText();
				String jmbg = jmbgTextField.getText();
				String address = addressTextField.getText();
				String phoneNumberString = phoneNumberField.getText();
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				if (validation.IsValidNameSurname(name) && validation.IsValidNameSurname(surname)
						&& validation.isValidJMBG(jmbg) && validation.isValidAdress(address)
						&& validation.isValidUsername(username) && validation.isValidPhoneNumber(phoneNumberString)) {
					String unetaSifra = password;
					String filePath = "src/data/userdata.csv";
					String tempFilePathString = "src/data/temp.csv";

					try {
						if (unetaSifra.length() != 0) {
							byte[] salt = auth.Pbkdf2.generateSalt();
							byte[] hash = auth.Pbkdf2.getEncryptedPassword(unetaSifra, salt);
							System.out.println(tableUsername);
							String[] newLine = { name, surname, jmbg, address,phoneNumberString, username, auth.Pbkdf2.bytesToHex(hash),
									auth.Pbkdf2.bytesToHex(salt) };
							modifyUserData(filePath, tempFilePathString, tableUsername, newLine);
							System.out.println("Modifying...");
							frame.setVisible(false);
						} else {
							System.out.println(tableUsername);
							String[] newLine = { name, surname, jmbg, address,phoneNumberString, username };
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
