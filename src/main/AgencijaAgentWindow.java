package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import main.AgencijaAdministratorWindow;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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

        JScrollPane scrollPaneArrangements = new JScrollPane(tableArrangements);
        scrollPaneArrangements.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        arrangementsPanel.add(scrollPaneArrangements, BorderLayout.CENTER);
        tabbedPane.addTab("Arrangements", arrangementsPanel);
        //////////////////////////////////////////////////////////////////////////
        
        
        JPanel reportsAndStatsPanel = new JPanel();
        reportsAndStatsPanel.setLayout(new BorderLayout());
        JPanel reportAndStatsButtonPanel = new JPanel();
        reportAndStatsButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.anchor = GridBagConstraints.NORTHWEST;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.fill = GridBagConstraints.HORIZONTAL;

        JButton showReportButton = new JButton("Show Report");
        reportAndStatsButtonPanel.add(showReportButton, gbc3);
        
        gbc3.gridy = 1;
        JButton getTotalProfitButton = new JButton("Show Total Profit");
        reportAndStatsButtonPanel.add(getTotalProfitButton,gbc3);
        reportsAndStatsPanel.add(reportAndStatsButtonPanel,BorderLayout.WEST);
        
        JPanel reportJPanel = new JPanel();
        reportJPanel.setLayout(new GridBagLayout());
        reportJPanel.setForeground(Color.cyan);
        reportJPanel.setVisible(true);
        
        reportsAndStatsPanel.add(reportJPanel,BorderLayout.EAST);
        
        tabbedPane.addTab("Reports And Stats", reportsAndStatsPanel);
        
        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }
}
