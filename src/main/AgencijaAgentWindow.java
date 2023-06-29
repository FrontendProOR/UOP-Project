package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mainStructure.Reservation;
import mainStructure.Status;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import validation.validation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AgencijaAgentWindow extends JFrame {

	private static final long serialVersionUID = -1969293607747042365L;
	private JPanel contentPane;

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

	public AgencijaAgentWindow(String agentId) {
		Long agentIdLong = Long.parseLong(agentId);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel touristPanel = new JPanel();
		touristPanel.setLayout(new BorderLayout());

		JPanel touristButtonPanel = new JPanel();
		touristButtonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JButton createButton = new JButton("Create Tourist");
		touristButtonPanel.add(createButton, gbc);

		gbc.gridy = 1;
		JButton deleteButton = new JButton("Delete Tourist");
		touristButtonPanel.add(deleteButton, gbc);

		gbc.gridy = 2;
		JButton changeButton = new JButton("Change Tourist");
		touristButtonPanel.add(changeButton, gbc);

		JPanel scrollableTablePanel = new JPanel(new BorderLayout());

		JTable table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(true);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		String[] columnNamesForTourist = { "ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address", "Phone Number",
				"Username" };
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
							valueOfATourist[7], valueOfATourist[8] };
					allTourists = Arrays.copyOf(allTourists, allTourists.length + 1);
					allTourists[i] = touristStrings;
					tableModelTourist.addRow(touristStrings);
					i++;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		table.setModel(tableModelTourist);

		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowPosition = table.getSelectedRow();
				String nameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 2);
				String surnameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 3);
				String jmbgString = (String) tableModelTourist.getValueAt(selectedRowPosition, 4);
				String addressString = (String) tableModelTourist.getValueAt(selectedRowPosition, 6);
				String phoneNumberString = (String) tableModelTourist.getValueAt(selectedRowPosition, 7);
				String usernameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 8);

				if (usernameString.length() != 0) {
					AgencijaAdministratorWindow.changeUserDataForm(selectedRowPosition, nameString, surnameString,
							jmbgString, addressString, phoneNumberString, usernameString, tableModelTourist, table);
				} else {
					System.out.println("Please choose a user in table by clicking on a user row.");
				}

			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowPosition = table.getSelectedRow();
				String usernameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 8);
				AgencijaAdministratorWindow.deleteLineByUsername(usernameString);
				tableModelTourist.removeRow(selectedRowPosition);

			}
		});

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgencijaAdministratorWindow.createTouristForm(table, tableModelTourist);
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollableTablePanel.add(scrollPane, BorderLayout.CENTER);

		touristPanel.add(touristButtonPanel, BorderLayout.WEST);
		touristPanel.add(scrollableTablePanel, BorderLayout.CENTER);

		tabbedPane.addTab("Tourists", touristPanel);

		// From here starts arrangements panel

		JPanel arrangementsPanel = new JPanel();
		arrangementsPanel.setLayout(new BorderLayout());

		JPanel arrangementsButtonPanel = new JPanel();
		arrangementsButtonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.fill = GridBagConstraints.HORIZONTAL;

		JButton createArrangementButton = new JButton("Create Arrangement");
		arrangementsButtonPanel.add(createArrangementButton, gbc2);

		gbc2.gridy = 1;
		JButton editArrangementButton = new JButton("Edit Arrangement");
		arrangementsButtonPanel.add(editArrangementButton, gbc2);

		gbc2.gridy = 2;
		JButton deleteArrangementButton = new JButton("Delete Arrangement");
		arrangementsButtonPanel.add(deleteArrangementButton, gbc2);

		gbc2.gridy = 3;
		JButton showReportButton = new JButton("Show Report");
		arrangementsButtonPanel.add(showReportButton, gbc2);

		arrangementsPanel.add(arrangementsButtonPanel, BorderLayout.WEST);

		String[] tableModel4 = { "ID", "SellerID", "Type Arrangement", "Image", "Available Date",
				"Number Overnight Stays", "Number Of Rooms", "Type Accommodation", "Unit Price", "Fair Discount" };
		DefaultTableModel tableModelArrangements = new DefaultTableModel(tableModel4, 0);
		JTable tableArrangements = new JTable();
		tableArrangements.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[][] allArrangements = new String[0][10];
		String csvFile3 = "src/data/arrangments.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile3))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] valueOfAnArrangement = line.split("\\|");
				if (valueOfAnArrangement[1].equals(agentId)) {
					String[] arrangementStrings = { valueOfAnArrangement[0], valueOfAnArrangement[1],
							valueOfAnArrangement[2], valueOfAnArrangement[3], valueOfAnArrangement[4],
							valueOfAnArrangement[5], valueOfAnArrangement[6], valueOfAnArrangement[7],
							valueOfAnArrangement[8], valueOfAnArrangement[9] };

					allArrangements = Arrays.copyOf(allArrangements, allArrangements.length + 1);
					allArrangements[i] = arrangementStrings;
					tableModelArrangements.addRow(arrangementStrings);
					i++;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tableArrangements.setModel(tableModelArrangements);

		createArrangementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowOfSelectedArrangmentID = tableArrangements.getSelectedRow();
				main.AgencijaAdministratorWindow.createArrangmentForm(rowOfSelectedArrangmentID, tableArrangements,
						tableModelArrangements);
			}
		});
		editArrangementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowOfSelectedArrangmentID = tableArrangements.getSelectedRow();
				if (rowOfSelectedArrangmentID != -1) {
					int selectedArrangmentID = (int) rowOfSelectedArrangmentID;
					main.AgencijaAdministratorWindow.changeArrangmentData(selectedArrangmentID, tableArrangements,
							tableModelArrangements);
				}
			}
		});

		deleteArrangementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowOfSelectedArrangmentID = tableArrangements.getSelectedRow();
				if (rowOfSelectedArrangmentID != -1) {
					String selectedArrangmentID = (String) tableArrangements.getValueAt(rowOfSelectedArrangmentID, 0);
					main.AgencijaAdministratorWindow.deleteLineArrangment(selectedArrangmentID);
					tableModelArrangements.removeRow(rowOfSelectedArrangmentID);
				}
			}
		});

		showReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frame = new JFrame("Date Picker Example");
				frame.setTitle("Date Picker Example");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new GridLayout(3, 2, 10, 10));

				JLabel fromLabel = new JLabel("From Date:");
				frame.add(fromLabel);

				UtilDateModel fromDateModel = new UtilDateModel();
				JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromDateModel);
				JDatePickerImpl fromDatePicker = new JDatePickerImpl(fromDatePanel);
				frame.add(fromDatePicker);

				JLabel toLabel = new JLabel("Up to Date:");
				frame.add(toLabel);

				UtilDateModel toDateModel = new UtilDateModel();
				JDatePanelImpl toDatePanel = new JDatePanelImpl(toDateModel);
				JDatePickerImpl toDatePicker = new JDatePickerImpl(toDatePanel);
				frame.add(toDatePicker);

				JButton submitButton = new JButton("Submit");
				submitButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						JDatePickerImpl fromDatePickerImpl = (JDatePickerImpl) fromDatePicker;
						JDatePickerImpl toDatePickerImpl = (JDatePickerImpl) toDatePicker;

						SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);

						GregorianCalendar cal1 = new GregorianCalendar();
						String datum1 = format.format(fromDatePickerImpl.getModel().getValue());
						try {
							cal1.setTime(format.parse(datum1));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						String formattedDate1 = format.format(cal1.getTime());

						GregorianCalendar cal2 = new GregorianCalendar();
						String datum2 = format.format(toDatePickerImpl.getModel().getValue());
						try {
							cal2.setTime(format.parse(datum2));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						String formattedDate2 = format.format(cal2.getTime());

						Map<String, String> arrangementsInRange = new HashMap<>();

						for (int row = 0; row < tableModelArrangements.getRowCount(); row++) {
							String arrangementId = (String) tableModelArrangements.getValueAt(row, 0);
							String dateStr = (String) tableModelArrangements.getValueAt(row, 4);

							try {
								java.util.Date date = format.parse(dateStr);
								if (date.after(cal1.getTime()) && date.before(cal2.getTime())) {
									arrangementsInRange.put(arrangementId, dateStr);
									System.out.println("Arrangement ID: " + arrangementId + ", Date: " + dateStr
											+ " is within the range.");
								}
							} catch (ParseException e3) {
								e3.printStackTrace();
							}
						}
						frame.dispose();

						makeReportFrame(formattedDate1, formattedDate2, arrangementsInRange);

					}
				});

				frame.add(submitButton);
				frame.setLocationRelativeTo(null);
				frame.setSize(400, 200);
				frame.setVisible(true);
			}
		});

		JScrollPane scrollPaneArrangements = new JScrollPane(tableArrangements);
		scrollPaneArrangements.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		arrangementsPanel.add(scrollPaneArrangements, BorderLayout.CENTER);
		tabbedPane.addTab("Arrangements", arrangementsPanel);

		// From here starts reservations panel

		JPanel reservationsJPanel = new JPanel();
		reservationsJPanel.setLayout(new BorderLayout());

		JPanel reservationsButtonPanel = new JPanel();
		reservationsButtonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.anchor = GridBagConstraints.NORTHWEST;
		gbc3.gridx = 0;
		gbc3.gridy = 0;
		gbc3.weightx = 1.0;
		gbc3.weighty = 1.0;
		gbc3.fill = GridBagConstraints.HORIZONTAL;

		JButton makeReservationButton = new JButton("Make Reservation");
		reservationsButtonPanel.add(makeReservationButton, gbc3);

		gbc3.gridy = 1;
		JButton changeReservationButton = new JButton("Change Reservation");
		reservationsButtonPanel.add(changeReservationButton, gbc3);

		gbc3.gridy = 2;
		JButton cancelReservationButton = new JButton("Cancel Reservation");
		reservationsButtonPanel.add(cancelReservationButton, gbc3);

		gbc3.gridy = 3;
		JButton approveReservationButton = new JButton("Approve Reservation");
		reservationsButtonPanel.add(approveReservationButton, gbc3);

		reservationsJPanel.add(reservationsButtonPanel, BorderLayout.WEST);

		String filePath = "src/data/reservations.csv";
		List<String> emptyList = new ArrayList<>();
		String[] columnNamesReservations = { "ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration",
				"Number of Passengers", "Date and Time", "Turist ID", "Total Price" };
		DefaultTableModel tableModelReservation = new DefaultTableModel(columnNamesReservations, 0);
		JTable reservationTable = new JTable(tableModelReservation);
		main.AgencijaAdministratorWindow.loadReservationData(filePath, reservationTable, emptyList, false, agentIdLong);

		makeReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				makeReservationForm(reservationTable, tableModelReservation);
			}
		});
		changeReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedReservationRow = reservationTable.getSelectedRow();
				changeReservationForm(selectedReservationRow, reservationTable, tableModelReservation);
			}
		});
		cancelReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedReservationRow = reservationTable.getSelectedRow();
				long reservationId = Long
						.parseLong(tableModelReservation.getValueAt(selectedReservationRow, 0).toString());
				cancelReservationFunction(reservationId, reservationTable, tableModelReservation);
			}
		});
		approveReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedReservationRow = reservationTable.getSelectedRow();
				long reservationId = Long
						.parseLong(tableModelReservation.getValueAt(selectedReservationRow, 0).toString());
				approveReservationFunction(reservationId, reservationTable, tableModelReservation);
			}
		});

		JScrollPane scrollPaneReservations = new JScrollPane(reservationTable);
		scrollPaneReservations.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		reservationsJPanel.add(scrollPaneReservations, BorderLayout.CENTER);

		tabbedPane.addTab("Tourists Reservations", reservationsJPanel);

		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

	public void makeReportFrame(String fromDate, String toDate, Map<String, String> arrangementsInRange) {
		JFrame reportFrame = new JFrame();
		reportFrame.setTitle("Report");
		reportFrame.setSize(750, 450);
		reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		reportFrame.setLayout(new GridLayout(14, 1, 10, 10));

		JLabel fromDateLabel = new JLabel("From Date: " + fromDate);
		JLabel toDateLabel = new JLabel("To Date: " + toDate);
		reportFrame.add(fromDateLabel);
		reportFrame.add(toDateLabel);

		JLabel arrangementsLabel = new JLabel("Arrangements within range:");
		reportFrame.add(arrangementsLabel);

		for (Map.Entry<String, String> entry : arrangementsInRange.entrySet()) {
			String arrangementId = entry.getKey();
			String date = entry.getValue();
			JLabel arrangementLabel = new JLabel("Arrangement ID: " + arrangementId + ", Date: " + date);
			reportFrame.add(arrangementLabel);

			double arrangementTotalProfit = calculateTotalProfitForArrangement(arrangementId);
			JLabel arrangementProfitLabel = new JLabel("Total Profit for Arrangement: " + arrangementTotalProfit);
			reportFrame.add(arrangementProfitLabel);
		}

		List<Map.Entry<String, String>> sortedArrangements = sortArrangementsByPopularity(arrangementsInRange);

		for (int i = 0; i < sortedArrangements.size(); i++) {
			Map.Entry<String, String> entry = sortedArrangements.get(i);
			String arrangementId = entry.getKey();
			String date = entry.getValue();
			JLabel arrangementLabel = new JLabel((i + 1) + ". Arrangement ID: " + arrangementId + ", Date: " + date);
			reportFrame.add(arrangementLabel);

		}

		double totalProfitForRange = calculateTotalProfitForRange(arrangementsInRange.keySet());
		JLabel totalProfitForRangeLabel = new JLabel(
				"Total Profit for All Arrangements in Range: " + totalProfitForRange);
		reportFrame.add(totalProfitForRangeLabel);

		reportFrame.pack();
		reportFrame.setLocationRelativeTo(null);
		reportFrame.setVisible(true);
	}

	private List<Entry<String, String>> sortArrangementsByPopularity(Map<String, String> arrangementsInRange) {
		Map<String, Integer> arrangementCountMap = new HashMap<>();

		for (Map.Entry<String, String> entry : arrangementsInRange.entrySet()) {
			String arrangementId = entry.getKey();
			arrangementCountMap.put(arrangementId, 0);
		}

		String csvFile = "src/data/reservations.csv";
		String line;
		String cvsSplitBy = "\\|";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
				String[] data = line.split(cvsSplitBy);
				String arrangementId = data[1];
				if (arrangementsInRange.containsKey(arrangementId)) {
					arrangementCountMap.put(arrangementId, arrangementCountMap.get(arrangementId) + 1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Map.Entry<String, String>> sortedArrangements = new ArrayList<>(arrangementsInRange.entrySet());
		sortedArrangements
				.sort((e1, e2) -> arrangementCountMap.get(e2.getKey()).compareTo(arrangementCountMap.get(e1.getKey())));

		return sortedArrangements;
	}

	private double calculateTotalProfitForArrangement(String arrangementId) {
		String csvFile = "src\\data\\reservations.csv";
		String line;
		String cvsSplitBy = "\\|";
		double totalProfit = 0.0;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
				String[] data = line.split(cvsSplitBy);
				String currentArrangementId = data[1];
				double totalPrice = Double.parseDouble(data[8]);
				if (currentArrangementId.equals(arrangementId) && data[3].equals(Status.Completed.toString())) {
					totalProfit += totalPrice;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return totalProfit;
	}

	private double calculateTotalProfitForRange(Set<String> arrangementIds) {
		String csvFile = "src\\data\\reservations.csv";
		String line;
		String cvsSplitBy = "\\|";
		double totalProfit = 0.0;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
				String[] data = line.split(cvsSplitBy);
				String arrangementId = data[1];
				double totalPrice = Double.parseDouble(data[8]);
				if (arrangementIds.contains(arrangementId) && data[3].equals(Status.Completed.toString())) {
					totalProfit += totalPrice;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return totalProfit;
	}

	private static void makeReservationForm(JTable table, DefaultTableModel tableModel) {
		JFrame frame = new JFrame("Form Frame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));

		List<String> formData = new ArrayList<String>();

		String[] labels = { "ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers",
				"Date and Time", "Tourist ID" };

		for (String label : labels) {
			if (label.equals("Status")) {
				continue;
			}
			if (label.equals("Date and Time")) {
				JLabel jLabel = new JLabel(label);
				UtilDateModel dateModel = new UtilDateModel();
				JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
				panel.add(jLabel);
				panel.add(datePicker);
				continue;
			}
			JLabel jLabel = new JLabel(label);
			JTextField jTextField = new JTextField(20);
			panel.add(jLabel);
			panel.add(jTextField);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				formData.clear();
				Component[] components = panel.getComponents();
				for (Component component : components) {
					if (component instanceof JDatePickerImpl) {
						JDatePickerImpl datePickerImpl = (JDatePickerImpl) component;
						SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
						GregorianCalendar cal = new GregorianCalendar();
						 Object selectedDate = datePickerImpl.getModel().getValue();
						if (selectedDate == null) {
							JOptionPane.showMessageDialog(null, "Please select a valid date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
							return; // Exit the method if date is not selected
						}
						String datum = format.format(datePickerImpl.getModel().getValue());
						
						
						try {
							cal.setTime(format.parse(datum));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						String formattedDate = format.format(cal.getTime());

						formData.add(formattedDate);
					}
					if (component instanceof JTextField) {
						JTextField textField = (JTextField) component;
						formData.add(textField.getText());
					}
				}
//				Validation for create reservation 
				if (validation.isNumeric(formData.get(0).toString()) && validation.isNumeric(formData.get(1).toString())
						&& validation.isNumeric(formData.get(2).toString())
						&& validation.isNumeric(formData.get(3).toString())
						&& validation.isNumeric(formData.get(4).toString())
						&& validation.isNumeric(formData.get(6).toString())) {

					Reservation reservation = new Reservation(formData.get(6), formData.get(1), formData.get(2),
							Integer.parseInt(formData.get(4)), Integer.parseInt(formData.get(3)));
					reservation.setStatus(Status.Completed);
					reservation.setDateAndTime(formData.get(5));
					String lineString = reservation.getData();
					String totalPriceString = String.valueOf(reservation.getTotalPrice());
					writeReservation(lineString);
					String[] newRowStrings = new String[formData.size() + 2];

					for (int i = 0; i < 3; i++) {
						newRowStrings[i] = formData.get(i);
					}

					newRowStrings[3] = "Completed";

					for (int i = 3; i < formData.size(); i++) {
						newRowStrings[i + 1] = formData.get(i);
					}

					newRowStrings[newRowStrings.length - 1] = totalPriceString;

					tableModel.addRow(newRowStrings);
//              table.setModel(tableModel);
				}else {
					//Here dialog for wrong input
					JOptionPane.showMessageDialog(null, "Wrong data format. Please enter correct data.", "Data Format Error", JOptionPane.ERROR_MESSAGE);
					
				}
				frame.dispose();
			}
		});

		panel.add(submitButton);

		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public static void writeReservation(String reservationLine) {
		String csvFile = "src\\data\\reservations.csv";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
			writer.write(reservationLine);
			writer.newLine();
			System.out.println("Reservation written to CSV file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while writing to the CSV file: " + e.getMessage());
		}
	}

	public void changeReservationForm(int selectedRow, JTable table, DefaultTableModel tableModel) {
		JFrame frame = new JFrame("Form Frame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));

		List<JTextField> textFields = new ArrayList<>();
		String[] labels = { "ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers",
				"Date and Time", "Tourist ID" };

		Object[] rowData = new Object[labels.length];
		for (int i = 0; i < labels.length; i++) {
			rowData[i] = table.getValueAt(selectedRow, i);
		}

		for (int i = 0; i < labels.length; i++) {
			if (labels[i].equals("Status")) {
				continue;
			}
			if (labels[i].equals("Date and Time")) {
				JLabel jLabel = new JLabel(labels[i]);
				UtilDateModel dateModel = new UtilDateModel();
				JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
				panel.add(jLabel);
				panel.add(datePicker);
				textFields.add(null);
				continue;
			}
			JLabel jLabel = new JLabel(labels[i]);
			JTextField jTextField = new JTextField(rowData[i].toString(), 20);
			panel.add(jLabel);
			panel.add(jTextField);
			textFields.add(jTextField);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String formattedDate;
				String[] formData = new String[textFields.size()];
				for (int i = 0; i < textFields.size(); i++) {
					if (panel.getComponent(i * 2 + 1) instanceof JDatePickerImpl) {
						JDatePickerImpl datePicker = (JDatePickerImpl) panel.getComponent(i * 2 + 1);
						SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
						GregorianCalendar cal = new GregorianCalendar();
						
						Object selectedDate = datePicker.getModel().getValue();
						if (selectedDate == null) {
							JOptionPane.showMessageDialog(null, "Please select a valid date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
							return; // Exit the method if date is not selected
						}
						
						String datum = format.format(datePicker.getModel().getValue());
						try {
							cal.setTime(format.parse(datum));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						formattedDate = format.format(cal.getTime());
						formData[i] = formattedDate;
						System.out.println(formattedDate);
					} else {

						formData[i] = textFields.get(i).getText();
					}
//					tableModel.setValueAt(formData[i], selectedRow, i);
				}
				//Here is validation for data
				if (validation.isNumeric(formData[0]) && validation.isNumeric(formData[1])
						&& validation.isNumeric(formData[2])
						&& validation.isNumeric(formData[3])
						&& validation.isNumeric(formData[4])
						&& validation.isNumeric(formData[6])) {
					
					Reservation reservation = new Reservation(formData[6], formData[1], formData[2],
							Integer.parseInt(formData[4]), Integer.parseInt(formData[3]));
					reservation.setStatus(Status.Completed);
					reservation.setDateAndTime(formData[5]);
					int tableSelectedRow = table.getSelectedRow();
					Long oldIdLong = Long.valueOf(tableModel.getValueAt(tableSelectedRow, 0).toString());
					reservation.setId(oldIdLong);
					
					String newLine = reservation.getData();
					String reservationId = String.valueOf(reservation.getId());
					
					String[] newTableRowData = newLine.split("\\|");
					formData[0] = newTableRowData[0];
					formData[5] = newTableRowData[5];
					
					modifyReservationLine(reservationId, newLine, "src\\data\\reservations.csv", newTableRowData,
							tableModel, tableSelectedRow);
					
				}else {
					//Here dialog for wrong input
					JOptionPane.showMessageDialog(null, "Wrong data format. Please enter correct data.", "Data Format Error", JOptionPane.ERROR_MESSAGE);
					
				}

				frame.dispose();
			}
		});

		panel.add(submitButton);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void modifyReservationLine(String reservationId, String newLine, String filePath,
			String[] newTableRowData, DefaultTableModel tableModel, int selectedRow) {
		try {
			File file = new File(filePath);
			File tempFile = new File("temp4.csv");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineStrings = line.split("\\|");
				if (lineStrings[0].equals(reservationId)) {
					writer.write(newLine);
					writer.newLine();

				} else {
					writer.write(line);
					writer.newLine();
				}
			}

			reader.close();
			writer.close();

			if (file.delete()) {
				if (!tempFile.renameTo(file)) {
					throw new IOException("Failed to rename the temporary file to the original file");
				}
			} else {
				throw new IOException("Failed to delete the original file");
			}

			for (int i = 0; i < newTableRowData.length; i++) {
				tableModel.setValueAt(newTableRowData[i], selectedRow, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelReservationFunction(long reservationId, JTable reservationTable,
			DefaultTableModel tableModelReservation) {
		String filePath = "src/data/reservations.csv";
		List<String> reservationData = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] reservationValues = line.split("\\|");
				if (Long.parseLong(reservationValues[0]) == reservationId) {
					reservationValues[3] = "Failed";
				}
				reservationData.add(String.join("|", reservationValues));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String reservation : reservationData) {
				writer.write(reservation);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

//      tableModelReservation.removeRow(reservationTable.getSelectedRow());//This is deleting and this under is modifying
		tableModelReservation.setValueAt(Status.Failed.toString(), reservationTable.getSelectedRow(), 3);
		System.out.println("Reservation canceled and set to Failed successfully.");
	}

	protected void approveReservationFunction(long reservationId, JTable table, DefaultTableModel tableModel) {
		int selectedRowIndex = table.getSelectedRow();
		String reservationIdTemp = table.getValueAt(selectedRowIndex, 0).toString();

		String csvFile = "src\\data\\reservations.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			List<String> lines = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split("\\|");
				if (values[0].equals(reservationIdTemp)) {
					if (values[3].equals(mainStructure.Status.Created.toString())) {
						table.setValueAt(mainStructure.Status.Completed.toString(), selectedRowIndex, 3);
						values[3] = mainStructure.Status.Completed.toString();
						line = String.join("|", values);
					}
				}
				lines.add(line);
			}

			if (tableModel.getValueAt(selectedRowIndex, 3).toString().equals(Status.Failed.toString())) {
				System.out.println("Cannot update status to Completed because it is already Failed.");
				return;
			}

			tableModel.setValueAt(Status.Completed.toString(), selectedRowIndex, 3);
			table.setModel(tableModel);

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
				for (String modifiedLine : lines) {
					writer.write(modifiedLine);
					writer.newLine();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
