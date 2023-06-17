package main;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import main.AgencijaAdministratorWindow;
import mainStructure.Reservation;
import mainStructure.Status;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        String[] columnNamesForTourist = {"ID", "Role", "Name", "Surname", "JMBG", "Gender", "Address", "Phone Number", "Username"};
        DefaultTableModel tableModelTourist = new DefaultTableModel(columnNamesForTourist, 0);
        String[][] allTourists = new String[0][9];
        String csvFile = "src/data/userdata.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] valueOfATourist = line.split("\\|");
                if ("Turist".equals(valueOfATourist[1])) {
                    String[] touristStrings = {valueOfATourist[0], valueOfATourist[1], valueOfATourist[2], valueOfATourist[3], valueOfATourist[4], valueOfATourist[5], valueOfATourist[6], valueOfATourist[7], valueOfATourist[8]};
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
				String phoneNumberString = (String) tableModelTourist.getValueAt(selectedRowPosition,7 );
				String usernameString = (String) tableModelTourist.getValueAt(selectedRowPosition, 8);
				
				if (usernameString.length() != 0) {
					AgencijaAdministratorWindow.changeUserDataForm(selectedRowPosition,nameString, surnameString, jmbgString, addressString,phoneNumberString ,usernameString,tableModelTourist,table);
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
				AgencijaAdministratorWindow.createTouristForm(table,tableModelTourist);
			}
		});
        
        
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTablePanel.add(scrollPane, BorderLayout.CENTER);

        touristPanel.add(touristButtonPanel, BorderLayout.WEST);
        touristPanel.add(scrollableTablePanel, BorderLayout.CENTER);

        

        tabbedPane.addTab("Tourists", touristPanel);
        //////////////////////////////////////////////////////////////////
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
                if(valueOfAnArrangement[1].equals(agentId)) {                	
                	String[] arrangementStrings = { valueOfAnArrangement[0], valueOfAnArrangement[1], valueOfAnArrangement[2],
                			valueOfAnArrangement[3], valueOfAnArrangement[4], valueOfAnArrangement[5], valueOfAnArrangement[6],
                			valueOfAnArrangement[7], valueOfAnArrangement[8], valueOfAnArrangement[9] };
                	
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
				main.AgencijaAdministratorWindow.createArrangmentForm(rowOfSelectedArrangmentID,tableArrangements,tableModelArrangements);
			}
		});
        editArrangementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowOfSelectedArrangmentID = tableArrangements.getSelectedRow();
				if (rowOfSelectedArrangmentID != -1) {
					int selectedArrangmentID = (int) rowOfSelectedArrangmentID;
					main.AgencijaAdministratorWindow.changeArrangmentData(selectedArrangmentID,tableArrangements,tableModelArrangements);
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
        
        
        
        JScrollPane scrollPaneArrangements = new JScrollPane(tableArrangements);
        scrollPaneArrangements.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        arrangementsPanel.add(scrollPaneArrangements, BorderLayout.CENTER);
        tabbedPane.addTab("Arrangements", arrangementsPanel);
        //////////////////////////////////////////////////////////////////////////
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
        
        reservationsJPanel.add(reservationsButtonPanel,BorderLayout.WEST);
        
        
        String filePath = "src/data/reservations.csv";
        List<String> emptyList = new ArrayList<>();
        String[] columnNamesReservations = {"ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers", "Date and Time","Turist ID","Total Price"};
        DefaultTableModel tableModelReservation = new DefaultTableModel(columnNamesReservations, 0);
        JTable reservationTable = new JTable(tableModelReservation);
        main.AgencijaAdministratorWindow.loadReservationData(filePath,reservationTable,emptyList,false,agentIdLong);//Here loading the reservations for agent only
        
        makeReservationButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		makeReservationForm(reservationTable,tableModelReservation);
        	}
        });
        changeReservationButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int selectedReservationRow = reservationTable.getSelectedRow();
        		changeReservationForm(selectedReservationRow,reservationTable,tableModelReservation);
        	}
        });
        cancelReservationButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	int selectedReservationRow = reservationTable.getSelectedRow();
        	long reservationId = Long.parseLong(tableModelReservation.getValueAt(selectedReservationRow, 0).toString());
        	cancelReservationFunction(reservationId,reservationTable,tableModelReservation);
        	}
        });
        approveReservationButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int selectedReservationRow = reservationTable.getSelectedRow();
            	long reservationId = Long.parseLong(tableModelReservation.getValueAt(selectedReservationRow, 0).toString());
        		approveReservationFunction(reservationId, reservationTable, tableModelReservation);
        	}
        });
        
        JScrollPane scrollPaneReservations = new JScrollPane(reservationTable);
        scrollPaneReservations.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		  
        reservationsJPanel.add(scrollPaneReservations, BorderLayout.CENTER);
        
        tabbedPane.addTab("Tourists Reservations",reservationsJPanel );
        /////////////////////////////////////////////////////////////////////////
        
        JPanel reportsAndStatsPanel = new JPanel();
        reportsAndStatsPanel.setLayout(new BorderLayout());
        JPanel reportAndStatsButtonPanel = new JPanel();
        reportAndStatsButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.anchor = GridBagConstraints.NORTHWEST;
        gbc4.gridx = 0;
        gbc4.gridy = 0;
        gbc4.weightx = 1.0;
        gbc4.weighty = 1.0;
        gbc4.fill = GridBagConstraints.HORIZONTAL;

        JButton showReportButton = new JButton("Show Report");
        reportAndStatsButtonPanel.add(showReportButton, gbc4);
        
        gbc4.gridy = 1;
        JButton getTotalProfitButton = new JButton("Show Total Profit");
        reportAndStatsButtonPanel.add(getTotalProfitButton,gbc4);
        reportsAndStatsPanel.add(reportAndStatsButtonPanel,BorderLayout.WEST);
        
        JPanel reportJPanel = new JPanel();
        reportJPanel.setLayout(new GridBagLayout());
        reportJPanel.setForeground(Color.cyan);
        reportJPanel.setVisible(true);
        
        reportsAndStatsPanel.add(reportJPanel,BorderLayout.EAST);
        
        tabbedPane.addTab("Reports And Stats", reportsAndStatsPanel);
        
        //////////////////////////////////////////////////////////////////////////
        
        
        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }
    private static void makeReservationForm(JTable table,DefaultTableModel tableModel) {
        JFrame frame = new JFrame("Form Frame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));

        List<String> formData = new ArrayList<String>();
        
        String[] labels = {"ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers", "Date and Time", "Tourist ID"};

        for (String label : labels) {
        	if (label.equals("Status")) {
                continue; // Skip adding the "Status" label because it is completed automaticly
            }
        	if(label.equals("Date and Time")) {
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
                	if(component instanceof JDatePickerImpl) {
                		JDatePickerImpl datePickerImpl = (JDatePickerImpl) component;
                		SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
        				GregorianCalendar cal = new GregorianCalendar();
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
                Reservation reservation = new Reservation(formData.get(6),formData.get(1),formData.get(2),Integer.parseInt(formData.get(4)), Integer.parseInt(formData.get(3)));
                reservation.setStatus(Status.Completed);
                reservation.setDateAndTime(formData.get(5));
                String lineString = reservation.getData();
                String totalPriceString = String.valueOf(reservation.getTotalPrice());
                writeReservation(lineString);
//                reservation.setTotalPrice(formData.get(0),Long.valueOf(formData.get(1)));
//                String lineWithTotalPriceString = reservation.getData();
//                modifyReservationLine(String.valueOf(reservation.getId()),lineWithTotalPriceString, "src\\data\\reservations.csv");
                String[] newRowStrings = new String[formData.size()+2]; 

                for (int i = 0; i < 3; i++) {
                    newRowStrings[i] = formData.get(i);
                }
                
                newRowStrings[3] = "Completed"; // Set "Completed" at the fourth index
                
                for (int i = 3; i < formData.size(); i++) {
                    newRowStrings[i + 1] = formData.get(i);
                }
                
                newRowStrings[newRowStrings.length - 1] = totalPriceString;
                
                tableModel.addRow(newRowStrings);
//                table.setModel(tableModel);
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
        String[] labels = {"ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers", "Date and Time", "Tourist ID"};

        Object[] rowData = new Object[labels.length];
        for (int i = 0; i < labels.length; i++) {
            rowData[i] = table.getValueAt(selectedRow, i);
        }

        for (int i = 0; i < labels.length; i++) {
            if (labels[i].equals("Status")) {
                continue; // Skip adding the "Status" label because it is completed automatically
            }
            if (labels[i].equals("Date and Time")) {
                JLabel jLabel = new JLabel(labels[i]);
                UtilDateModel dateModel = new UtilDateModel();
                JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
                panel.add(jLabel);
                panel.add(datePicker);
                textFields.add(null); // Placeholder for date picker
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
                String[] formData = new String[textFields.size()];
                for (int i = 0; i < textFields.size(); i++) {
                    if (textFields.get(i) == null) {
                        // Handle date picker value
                        JDatePickerImpl datePicker = (JDatePickerImpl) panel.getComponent(i * 2 + 1);
                        SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
                        GregorianCalendar cal = new GregorianCalendar();
                        String datum = format.format(datePicker.getModel().getValue());
                        try {
                            cal.setTime(format.parse(datum));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        String formattedDate = format.format(cal.getTime());

                        formData[i] = formattedDate;
                    } else {
                        // Handle text field value
                        formData[i] = textFields.get(i).getText();
                    }
                }

                Reservation reservation = new Reservation(formData[6], formData[1], formData[2], Integer.parseInt(formData[4]), Integer.parseInt(formData[3]));
                reservation.setStatus(Status.Completed);
                String newLine = reservation.getData();
                String reservationId = formData[0].toString();
                
                String[] newTableRowData = newLine.split("\\|");
                formData[0] = newTableRowData[0];
                formData[5] = newTableRowData[5];
                modifyReservationLine(reservationId,newLine, "src\\data\\reservations.csv");

                // Dispose the frame after submitting the form
                frame.dispose();
            }
        });

        panel.add(submitButton);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    
    
    public static void modifyReservationLine(String reservationId, String newLine, String filePath) {
        try {
            File file = new File(filePath);
            File tempFile = new File("temp.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineStrings = line.split("\\|");
                if (lineStrings.length > 0 && lineStrings[0].equals(reservationId)) {
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
                    throw new IOException("Failed to rename temporary file to the original file");
                }
            } else {
                throw new IOException("Failed to delete the original file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    protected void cancelReservationFunction(long reservationId,JTable table,DefaultTableModel tableModel) {
//    	int selectedRowIndex = table.getSelectedRow();
//		String reservationIdTemp = table.getValueAt(selectedRowIndex, 0).toString();
//		
//		String csvFile = "src\\data\\reservations.csv";
//		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
//			List<String> lines = new ArrayList<>();
//			String line;
//			while ((line = reader.readLine()) != null) {
//				String[] values = line.split("\\|");
//				if (values[0].equals(reservationIdTemp)) {
//					if(values[3].equals(mainStructure.Status.Created.toString())) {																
//						table.setValueAt(mainStructure.Status.Failed.toString(), selectedRowIndex, 3);
//						values[3] = mainStructure.Status.Failed.toString();
//						line = String.join("|", values);						
//					}
//				}
//				lines.add(line);
//			}
//			tableModel.setValueAt(Status.Failed.toString(), selectedRowIndex, 3);
//			table.setModel(tableModel);
//			
//			try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
//				for (String modifiedLine : lines) {
//					writer.write(modifiedLine);
//					writer.newLine();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//    }
 // ...

    public void cancelReservationFunction(long reservationId, JTable reservationTable, DefaultTableModel tableModelReservation) {
        String filePath = "src/data/reservations.csv";
        List<String> reservationData = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reservationValues = line.split("\\|");
                if (Long.parseLong(reservationValues[0]) == reservationId) {
                    reservationValues[3] = "Failed"; // Update the status to Failed
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
        
        // Remove the canceled reservation from the table
//        tableModelReservation.removeRow(reservationTable.getSelectedRow());//This is deleting and this under is modifying
        tableModelReservation.setValueAt(Status.Failed.toString(),reservationTable.getSelectedRow(),3);
        System.out.println("Reservation canceled and set to Failed successfully.");
    }

    protected void approveReservationFunction(long reservationId,JTable table,DefaultTableModel tableModel) {
    	int selectedRowIndex = table.getSelectedRow();
		String reservationIdTemp = table.getValueAt(selectedRowIndex, 0).toString();
		
		String csvFile = "src\\data\\reservations.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			List<String> lines = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split("\\|");
				if (values[0].equals(reservationIdTemp)) {
					if(values[3].equals(mainStructure.Status.Created.toString())) {																
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
