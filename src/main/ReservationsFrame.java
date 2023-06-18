package main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import mainStructure.Turist;

public class ReservationsFrame extends JFrame {
	public ReservationsFrame(long turistId,boolean isTurist) {
		setTitle("Reservations");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel leftPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JButton button1 = new JButton("Change Reservation");
		leftPanel.add(button1, gbc);
		gbc.gridy = 1;
		JButton button2 = new JButton("Cancel Reservation");
		leftPanel.add(button2, gbc);
		gbc.gridy = 2;
		JButton button3 = new JButton("Spent up to now");
		leftPanel.add(button3,gbc);
		
		JPanel rightPanel = new JPanel(new BorderLayout());


		Turist turista = new Turist();
		turista.updateListOfReservations(turistId);
		List<String> turistDataString = turista.getListOfReservations();
//		System.out.println(turistDataString);

		String[] tableHeaders = {"ID", "Arrangement ID", "Seller ID", "Status", "Trip Duration", "Number of Passengers", "Date and Time","Turist ID","Total Price"};
        DefaultTableModel tableModel = new DefaultTableModel(tableHeaders, 0);
        JTable reservationTable = new JTable(tableModel);
        String filePath = "src/data/reservations.csv";
        if(isTurist == true) {
        	main.AgencijaAdministratorWindow.loadReservationData(filePath,reservationTable,turistDataString,false,-1L);        	
        }else {
        	main.AgencijaAdministratorWindow.loadReservationData(filePath,reservationTable,turistDataString,true,-1L);///////////////////////////////////here -1L is just to avoid method overloading and making more methods
        }
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Here is change reservation button
				int selectedReservationRow = reservationTable.getSelectedRow();
				long selectedReservationId = Long.parseLong(tableModel.getValueAt(selectedReservationRow, 0).toString());
				ChangeReservation reservationFrame = new ChangeReservation();
				reservationFrame.createChangeReservationFrame1(selectedReservationId, reservationTable);

			}
		});

		
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = reservationTable.getSelectedRow();
				String reservationIdTemp = reservationTable.getValueAt(selectedRowIndex, 0).toString();
				
				String csvFile = "src\\data\\reservations.csv";
				try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
					List<String> lines = new ArrayList<>();
					String line;
					while ((line = reader.readLine()) != null) {
						String[] values = line.split("\\|");
						if (values[0].equals(reservationIdTemp)) {
							if(values[3].equals(mainStructure.Status.Created.toString())) {																
								reservationTable.setValueAt(mainStructure.Status.Canceled.toString(), selectedRowIndex, 3);
								values[3] = mainStructure.Status.Canceled.toString();
								line = String.join("|", values);						
							}//add other statuses if needed 
						}
						lines.add(line);
					}

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
		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			generateReportOnTotalMoneySpent(reservationTable);
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(reservationTable);
		rightPanel.add(scrollPane, BorderLayout.CENTER);

		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(rightPanel, BorderLayout.CENTER);

		setContentPane(mainPanel);
	}
	private void generateReportOnTotalMoneySpent(JTable table) {
		JFrame reportFrame = new JFrame();
	    reportFrame.setTitle("Report");
	    reportFrame.setSize(750, 450);
	    reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    reportFrame.setLayout(new GridLayout(14, 1, 10, 10));
	    
	    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	    
	    int rowCount = tableModel.getRowCount();
	    List<String> completedReservationIds = new ArrayList<>();
	    List<String> completedTotalPricesOfReservationList = new ArrayList<>();
	    for (int row = 0; row < rowCount; row++) {
	        String status = (String) tableModel.getValueAt(row, 3);
	        if (status.equals("Completed")) {
	            String arrangementId = (String) tableModel.getValueAt(row, 0);
	            completedReservationIds.add(arrangementId);
	            String totalPrice =(String)tableModel.getValueAt(row, 8);
	            completedTotalPricesOfReservationList.add(totalPrice);
	        }
	    }
	    double totalPriceDouble = 0;
	    for (String totalPrice : completedTotalPricesOfReservationList) {
	        double price = Double.parseDouble(totalPrice);
	        totalPriceDouble += price;
	    }
	    JLabel totalPriceJLabel = new JLabel("You have spent up to now on all of your reservations: "+totalPriceDouble);
	    
	    reportFrame.add(totalPriceJLabel);
	    reportFrame.pack();
	    reportFrame.setLocationRelativeTo(null);
	    reportFrame.setVisible(true);
	}
}
