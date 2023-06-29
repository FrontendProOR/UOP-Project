package main;

import javax.swing.*;

import validation.validation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChangeReservation {

	public static void createChangeReservationFrame1(long reservationId, JTable table) {
		JFrame changeReservationFrame = new JFrame("Change Reservation");
		changeReservationFrame.setSize(300, 200);
		changeReservationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(3, 2));

		JLabel passengersLabel = new JLabel("Number of Passengers:");
		JTextField passengersField = new JTextField();
		JLabel durationLabel = new JLabel("Trip Duration:");
		JTextField durationField = new JTextField();

		panel.add(passengersLabel);
		panel.add(passengersField);
		panel.add(durationLabel);
		panel.add(durationField);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String numOfPassengers = passengersField.getText();
		        String tripDuration = durationField.getText();

		        int selectedRowIndex = table.getSelectedRow();

		        String reservationIdTemp = table.getValueAt(selectedRowIndex, 0).toString();
		        

		        if (validation.isNumeric(numOfPassengers) && numOfPassengers != null && tripDuration != null && validation.isNumeric(tripDuration)) {
		            // Validation passed, update the table data
		            String csvFile = "src\\data\\reservations.csv";

		            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
		                List<String> lines = new ArrayList<>();
		                String line;
		                boolean isValid = true;
		                while ((line = reader.readLine()) != null) {
		                    String[] values = line.split("\\|");
		                    if (values[0].equals(reservationIdTemp)) {
		                        if (values[3].equals(mainStructure.Status.Created.toString())) {
		                            values[5] = numOfPassengers;
		                            values[4] = tripDuration;
		                            table.setValueAt(numOfPassengers, selectedRowIndex, 5);
		                            table.setValueAt(tripDuration, selectedRowIndex, 4);
		                            line = String.join("|", values);
		                        } else {
		                            isValid = false;
		                            JOptionPane.showMessageDialog(null, "Reservation status must be 'Created'.",
		                                    "Invalid Reservation Status", JOptionPane.ERROR_MESSAGE);
		                        }
		                    }
		                    lines.add(line);
		                }

		                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
		                    if (isValid) {
		                        for (String modifiedLine : lines) {
		                            writer.write(modifiedLine);
		                            writer.newLine();
		                        }
		                    }
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Wrong data format. Please enter correct data.",
		                    "Data Format Error", JOptionPane.ERROR_MESSAGE);
		        }

		        changeReservationFrame.dispose();
		    }
		});


		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);

		changeReservationFrame.add(panel, BorderLayout.CENTER);
		changeReservationFrame.add(buttonPanel, BorderLayout.SOUTH);
		changeReservationFrame.setVisible(true);
	}

}
