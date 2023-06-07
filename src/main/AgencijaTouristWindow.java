package main;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
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
import java.util.ArrayList;
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
import mainStructure.TypeOfAccommodation;
import mainStructure.TypeOfArrangement;

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

        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Create the scroll pane
        JScrollPane scrollPane = new JScrollPane();
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create the inner panel with GridBagLayout
        JPanel innerPanel = new JPanel(new GridBagLayout());
        scrollPane.setViewportView(innerPanel);

        // Create the title label and center it
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
        gbc.insets = new Insets(10, 0, 30, 0); // Padding top and bottom
        innerPanel.add(titleLabel, gbc);

        // Load data from CSV file
        List<String[]> data = loadCSVData("src/data/arrangments.csv");

        // Create the cards
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

            int ordinal = Integer.parseInt(rowData[7]);
            TypeOfArrangement[] valuesArrangement = TypeOfArrangement.values();
            if (ordinal >= 0 && ordinal < valuesArrangement.length) {
                TypeOfArrangement enumValue = valuesArrangement[ordinal];
                JLabel typeOfArrangementJLabel = new JLabel("Arrangement type: " + enumValue);
                labelGbc.gridy = 3;
                labelPanel.add(typeOfArrangementJLabel, labelGbc);
            } else {
                System.out.println("Invalid ordinal position.");
            }

            int ordinal1 = Integer.parseInt(rowData[7]);
            TypeOfAccommodation[] valuesAccommodation = TypeOfAccommodation.values();
            if (ordinal1 >= 0 && ordinal1 < valuesAccommodation.length) {
                TypeOfAccommodation enumValue1 = valuesAccommodation[ordinal1];
                JLabel typeOfAccommodationJLabel = new JLabel("Accommodation type: " + enumValue1);
                labelGbc.gridy = 4;
                labelPanel.add(typeOfAccommodationJLabel, labelGbc);
            } else {
                System.out.println("Invalid ordinal position.");
            }

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

            card.add(labelPanel, BorderLayout.CENTER);

            JButton button = new JButton("Make Reservation");
//            final int cardIndex = i; // Number of clicked card or arrangement
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(Integer.parseInt(rowData[6]) != 0 || Integer.parseInt(rowData[6]) > 0) {
                		int numberOfPassengers = Integer.parseInt(numberOfPassengersField.getText());
                		int tripDuration = Integer.parseInt(tripDurationField.getText());
                		Reservation reservation = new Reservation(userId, rowData[0], rowData[1], numberOfPassengers, tripDuration);
                		String reservationData = reservation.getData();
                		writeLineToFile("src\\data\\reservations.csv", reservationData);
//                		System.out.println(reservationData);
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
