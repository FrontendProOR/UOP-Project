package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import mainStructure.Reservation;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import validation.validation;

public class AgencijaTouristWindow extends JFrame {

	private static final long serialVersionUID = -2949615253496597662L;
//    private JPanel contentPane;
	private final String filePath = "src/data/userdata.csv";

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

	public String getConcatenatedDataFromCSV(String filePath, String sellerId) {
		StringBuilder concatenatedData = new StringBuilder();

		try {
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] record = line.split("\\|");

				if (record.length >= 4 && record[0].equals(sellerId)) {
					String name = record[2];
					String surname = record[3];
					String fullName = name + " " + surname;
					concatenatedData.append(fullName).append("\n");
					break; // Exit the loop after finding the matching seller ID
				}
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return concatenatedData.toString();
	}

	public void writeLineToFile(String filePath, String line) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AgencijaTouristWindow(String userId) {
		setTitle("Scroll Panel Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 850);

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel(new GridBagLayout());

		JPanel buttonJPanel = new JPanel();

		GridBagConstraints buttonPanelGbc = new GridBagConstraints();
		buttonPanelGbc.gridx = 0;
		buttonPanelGbc.gridy = 0;
		buttonPanelGbc.weightx = 0.2;
		buttonPanelGbc.weighty = 1.0;
		buttonPanelGbc.fill = GridBagConstraints.BOTH;
		topPanel.add(buttonJPanel, buttonPanelGbc);

		JButton button1 = new JButton("My reservations");
		buttonJPanel.add(button1);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Long turistIdLong = Long.valueOf(userId);
				ReservationsFrame reservationsFrame = new ReservationsFrame(turistIdLong, true);

				reservationsFrame.setVisible(true);
			}
		});

		getContentPane().add(mainPanel);

		mainPanel.add(buttonJPanel, BorderLayout.WEST);

		JScrollPane scrollPane = new JScrollPane();
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel innerPanel = new JPanel(new GridBagLayout());
		scrollPane.setViewportView(innerPanel);

		JLabel titleLabel = new JLabel("Arrangement Offers");
		Font font = new Font("Arial", Font.BOLD, 22);
		titleLabel.setFont(font);
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 30, 0);
		innerPanel.add(titleLabel, gbc);

		List<String[]> data = loadCSVData("src/data/arrangments.csv");

		// Here are cards generated
		for (int i = 0; i < data.size(); i++) {
			String[] rowData = data.get(i);
			JPanel card = new JPanel();
			card.setLayout(new BorderLayout());

			ImageIcon imageIcon = new ImageIcon(rowData[3]);
			Image image = imageIcon.getImage().getScaledInstance(350, 250, Image.SCALE_DEFAULT);
			JLabel imageLabel = new JLabel(new ImageIcon(image));
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			card.add(imageLabel, BorderLayout.NORTH);

			JPanel labelPanel = new JPanel(new GridBagLayout());

			String sellerName = getConcatenatedDataFromCSV(filePath, rowData[1]);
			JLabel sellerNameJLabel = new JLabel("Tourist Agent: " + sellerName);
			GridBagConstraints labelGbc = new GridBagConstraints();
			labelGbc.gridx = 0;
			labelGbc.gridy = 0;
			labelGbc.anchor = GridBagConstraints.LINE_START;
			labelGbc.insets = new Insets(5, 10, 5, 10);
			labelPanel.add(sellerNameJLabel, labelGbc);

			JLabel dateLabel = new JLabel("Available Date: " + rowData[4]);
			labelGbc.gridy = 1;
			labelPanel.add(dateLabel, labelGbc);

			JLabel roomsLabel = new JLabel("Available Rooms: " + rowData[6]);
			labelGbc.gridy = 2;
			labelPanel.add(roomsLabel, labelGbc);

			JLabel typeOfArrangementJLabel = new JLabel("Arrangement type: " + rowData[2]);
			labelGbc.gridy = 3;
			labelPanel.add(typeOfArrangementJLabel, labelGbc);

			JLabel typeOfAccommodationJLabel = new JLabel("Accommodation type: " + rowData[7]);
			labelGbc.gridy = 4;
			labelPanel.add(typeOfAccommodationJLabel, labelGbc);

			JLabel priceLabel = new JLabel("Price: " + rowData[8] + "$");
			labelGbc.gridy = 5;
			labelPanel.add(priceLabel, labelGbc);

			JLabel discountLabel = new JLabel("Fair Discount: " + rowData[9]);
			labelGbc.gridy = 6;
			labelPanel.add(discountLabel, labelGbc);

			labelGbc.gridy = 7;
			labelPanel.add(new JLabel(), labelGbc); // Empty label for spacing

			labelGbc.gridy = 8;
			JLabel numberOfPassengersJLabel = new JLabel("Number of Passengers:");
			JTextField numberOfPassengersField = new JTextField(10);
			labelPanel.add(numberOfPassengersJLabel, labelGbc);
			labelGbc.gridy = 9;
			labelPanel.add(numberOfPassengersField, labelGbc);

			labelGbc.gridy = 10;
			labelPanel.add(new JLabel(), labelGbc); // Empty label for spacing

			labelGbc.gridy = 11;
			JLabel tripDurationJLabel = new JLabel("Trip duration:");
			JTextField tripDurationField = new JTextField(10);
			labelPanel.add(tripDurationJLabel, labelGbc);
			labelGbc.gridy = 12;
			labelPanel.add(tripDurationField, labelGbc);

			// Here ad Date and Time box and
			labelGbc.gridy = 13;
			JLabel dateForReservationJLabel = new JLabel("Reservation Date:");
			UtilDateModel dateModel = new UtilDateModel();
			JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
			labelPanel.add(dateForReservationJLabel, labelGbc);
			labelGbc.gridx = 1;
			labelPanel.add(datePicker, labelGbc);

			card.add(labelPanel, BorderLayout.CENTER);

			JButton button = new JButton("Make Reservation");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Integer.parseInt(rowData[6]) != 0 || Integer.parseInt(rowData[6]) > 0) {
						if (validation.isNumeric(numberOfPassengersField.getText())
								&& validation.isNumeric(tripDurationField.getText())) {
							// number == (int) number
							int numberOfPassengers = Integer.parseInt(numberOfPassengersField.getText());
							int tripDuration = Integer.parseInt(tripDurationField.getText());
							Reservation reservation = new Reservation(userId, rowData[0], rowData[1],
									numberOfPassengers, tripDuration);
							SimpleDateFormat format = new SimpleDateFormat(util.Util.DATE_FORMAT);
							GregorianCalendar cal = new GregorianCalendar();
							String datum = format.format(datePicker.getModel().getValue());
							try {
								cal.setTime(format.parse(datum));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							String formattedDate = format.format(cal.getTime());
							reservation.setDateAndTime(formattedDate);
							String reservationData = reservation.getData();
							writeLineToFile("src\\data\\reservations.csv", reservationData);
							numberOfPassengersField.setText("");
							tripDurationField.setText("");
						}
					}
				}
			});
			card.add(button, BorderLayout.SOUTH);

			gbc = new GridBagConstraints();
			gbc.gridx = i % 2;
			gbc.gridy = i / 2 + 1;
			gbc.weightx = 1.0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(10, 10, 10, 10); // Padding around cards
			innerPanel.add(card, gbc);
		}

		setVisible(true);
	}

	private List<String[]> loadCSVData(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] rowData = line.split("\\|");
				data.add(rowData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
