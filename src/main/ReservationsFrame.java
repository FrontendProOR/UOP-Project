package main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import mainStructure.Turist;

public class ReservationsFrame extends JFrame {
	public ReservationsFrame(long turistId) {
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

		JPanel rightPanel = new JPanel(new BorderLayout());
		String[] tableHeaders = { "ID", "Turist ID", "Seller ID", "Status", "Trip Duration", "Number Of Passengers",
				"Date" };

		DefaultTableModel tableModel = new DefaultTableModel(tableHeaders, 0);

		JTable table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[][] allReservations = new String[0][7];
		String csvFile = "src/data/reservations.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			int i = 0;
			Turist turista = new Turist();
			turista.updateListOfReservations(turistId);
			List<String> turistDataString = turista.getListOfReservations();

			while ((line = reader.readLine()) != null) {
				String[] valueOfReservation = line.split("\\|");
				if (turistDataString.contains(valueOfReservation[0])) {

					String[] reservationsStrings = { valueOfReservation[0], valueOfReservation[1],
							valueOfReservation[2], valueOfReservation[3], valueOfReservation[4], valueOfReservation[5],
							valueOfReservation[6] };

					allReservations = Arrays.copyOf(allReservations, allReservations.length + 1);
					allReservations[i] = reservationsStrings;
					tableModel.addRow(reservationsStrings);
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		table.setModel(tableModel);
		table.setVisible(true);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Here is change reservation button
				int selectedReservationRow = table.getSelectedRow();
				long selectedReservationId = Long
						.parseLong(tableModel.getValueAt(selectedReservationRow, 0).toString());
				ChangeReservation reservationFrame = new ChangeReservation();
				reservationFrame.createChangeReservationFrame1(selectedReservationId, table);

			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = table.getSelectedRow();
				String reservationIdTemp = table.getValueAt(selectedRowIndex, 0).toString();
				table.setValueAt(mainStructure.Status.Canceled.toString(), selectedRowIndex, 3);
				
				String csvFile = "src\\data\\reservations.csv";
				try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
					List<String> lines = new ArrayList<>();
					String line;
					while ((line = reader.readLine()) != null) {
						String[] values = line.split("\\|");
						if (values[0].equals(reservationIdTemp)) {
							if(values[3].equals(mainStructure.Status.Created.toString())) {								
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

		JScrollPane scrollPane = new JScrollPane(table);
		rightPanel.add(scrollPane, BorderLayout.CENTER);

		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(rightPanel, BorderLayout.CENTER);

		setContentPane(mainPanel);
	}
}
