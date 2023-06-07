package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javax.swing.border.EmptyBorder;

import mainStructure.TypeOfAccommodation;
import mainStructure.TypeOfArrangement;

public class AgencijaTouristWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2949615253496597662L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgencijaLoginWindow loginWindow = new AgencijaLoginWindow();
	                loginWindow.setVisible(true);
	                loginWindow.setTitle("Tourist Agency - Login");
	                loginWindow.setLocationRelativeTo(null);
//					AgencijaTouristWindow frame = new AgencijaTouristWindow();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private final String filePath = "src/data/userdata.csv";
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
	                String fullName = name +" "+surname;
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

	
	 public AgencijaTouristWindow() {
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
	        JLabel titleLabel = new JLabel("Arrangment Offers");
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

	            // Load and add the image centered
	            ImageIcon imageIcon = new ImageIcon(rowData[3]);
	            Image image = imageIcon.getImage().getScaledInstance(350, 250, Image.SCALE_DEFAULT);
	            JLabel imageLabel = new JLabel(new ImageIcon(image));
	            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	            card.add(imageLabel, BorderLayout.NORTH);

	            // Add the labels centered
	            JPanel labelPanel = new JPanel(new GridLayout(4, 1));
	            
	            String sellerName = getConcatenatedDataFromCSV(filePath,rowData[1]);
	            JLabel sellerNameJLabel = new JLabel("Tourist Agent:"+sellerName);
	            labelPanel.add(sellerNameJLabel);
	            
	            JLabel dateLabel = new JLabel("Available Date: "+rowData[4], SwingConstants.CENTER);
	            labelPanel.add(dateLabel);

	            JLabel roomsLabel = new JLabel("Available Rooms: " + rowData[6], SwingConstants.CENTER);
	            labelPanel.add(roomsLabel);
	            int ordinal = Integer.parseInt(rowData[7]);
	            TypeOfArrangement[] valuesArrangment = TypeOfArrangement.values();
	            if (ordinal >= 0 && ordinal < valuesArrangment.length) {
	                TypeOfArrangement enumValue = valuesArrangment[ordinal];
	                JLabel typeOfArrangmentJLabel = new JLabel("Type of accomodation: "+enumValue);	          
	                labelPanel.add(typeOfArrangmentJLabel);
	            } else {
	                System.out.println("Invalid ordinal position.");
	            }
	            int ordinal1 = Integer.parseInt(rowData[7]);
	            TypeOfAccommodation[] valuesArrangment1 = TypeOfAccommodation.values();
	            if (ordinal1 >= 0 && ordinal1 < valuesArrangment1.length) {
	                TypeOfAccommodation enumValue1 = valuesArrangment1[ordinal];
	                JLabel typeOfAccomodationJLabel = new JLabel("Type of accomodation: "+enumValue1);	 
	                labelPanel.add(typeOfAccomodationJLabel);
	            } else {
	                System.out.println("Invalid ordinal position.");
	            }
	            
	            
	            JLabel priceLabel = new JLabel("Price: " + rowData[8], SwingConstants.CENTER);
	            labelPanel.add(priceLabel);

	            JLabel discountLabel = new JLabel("Fair Discount: " + rowData[9], SwingConstants.CENTER);
	            labelPanel.add(discountLabel);

	            card.add(labelPanel, BorderLayout.CENTER);

	            
	            JButton button = new JButton("Make Reservation");
	            final int cardIndex = i; //broj kliknute kartice tj aranzmana
	            button.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    //Make reservation here 
	                   
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
