package HELLO;

import java.awt.EventQueue;
import net.proteanit.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;


public class show_add extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField idtxt;
	private JTextField nmtxt;
	private JTextField emailtxt;
	private JTextField phonetxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show_add frame = new show_add();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public show_add() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(364, 48, 512, 505);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NAME", "EMAIL", "CATEGORY", "PHONE", "TYPE"
			}
		));
		
		JButton SHOW = new JButton("SHOW");
		SHOW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@//127.0.0.1:1521/FREE",
						"c##norah",
						"norah123"
					);

					Statement stmt = con.createStatement();

					ResultSet rs = stmt.executeQuery(
						"SELECT c.contact_id, c.name, c.email," +
						"ca.category_name, p.phone_number, p.phone_type " +
						"FROM Contact c " +
						"JOIN Category ca ON c.category_id = ca.category_id " +
						"JOIN Phone_Number p ON c.contact_id = p.contact_id"
					);

					table.setModel(DbUtils.resultSetToTableModel(rs));

					con.close();

				} catch (Exception exp) {
					System.out.println(exp);
					JOptionPane.showMessageDialog(null, "Error: " + exp.getMessage());
				}
			}
		});
		SHOW.setBounds(591, 18, 84, 20);
		contentPane.add(SHOW);
		
		idtxt = new JTextField();
		idtxt.setBounds(192, 116, 96, 18);
		contentPane.add(idtxt);
		idtxt.setColumns(10);
		
		nmtxt = new JTextField();
		nmtxt.setBounds(192, 170, 96, 18);
		contentPane.add(nmtxt);
		nmtxt.setColumns(10);
		
		emailtxt = new JTextField();
		emailtxt.setBounds(192, 224, 96, 18);
		contentPane.add(emailtxt);
		emailtxt.setColumns(10);
		
		phonetxt = new JTextField();
		phonetxt.setBounds(192, 344, 96, 18);
		contentPane.add(phonetxt);
		phonetxt.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CONTACT ID :");
		lblNewLabel.setBounds(31, 119, 103, 12);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NAME :");
		lblNewLabel_1.setBounds(31, 173, 113, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("EMAIL :");
		lblNewLabel_2.setBounds(31, 227, 113, 12);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("CATEGORY :");
		lblNewLabel_4.setBounds(31, 286, 103, 12);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("NUMBER :");
		lblNewLabel_5.setBounds(31, 344, 103, 12);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("PHONE TYPE :");
		lblNewLabel_6.setBounds(31, 400, 103, 12);
		contentPane.add(lblNewLabel_6);
		
		JComboBox categorycmb = new JComboBox();
		categorycmb.setModel(new DefaultComboBoxModel(new String[] {"Family", "Friends", "Work", "Other", "Emergency"}));
		categorycmb.setBounds(192, 282, 96, 20);
		contentPane.add(categorycmb);
		
		JComboBox phonetypecmb = new JComboBox();
		phonetypecmb.setModel(new DefaultComboBoxModel(new String[] {"Mobile", "Home", "Work"}));
		phonetypecmb.setBounds(192, 396, 96, 20);
		contentPane.add(phonetypecmb);

		JButton btnAddContact = new JButton("ADD_CONTACT");
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@//127.0.0.1:1521/FREE",
						"c##norah",
						"norah123"
					);

					Statement stmt = con.createStatement();

					String id = idtxt.getText();
					String nm = nmtxt.getText();
					String email = emailtxt.getText();
					String category = (String) categorycmb.getSelectedItem();
					String phone = phonetxt.getText();
					String phonetype = (String) phonetypecmb.getSelectedItem();

					// Get category ID
					ResultSet rs = stmt.executeQuery(
						"SELECT category_id FROM Category " +
						"WHERE category_name='" + category + "'"
					);

					rs.next();
					int categoryId = rs.getInt("category_id");

					// Insert contact
					String iq = "INSERT INTO Contact VALUES(" +
						id + ",'" + nm + "','" + email + "'," + categoryId + ")";

					stmt.executeUpdate(iq);

					// Insert phone number
					String iq2 = "INSERT INTO Phone_Number VALUES('" +
						    phone + "','" + phonetype + "'," + id + ")";

					stmt.executeUpdate(iq2);

					System.out.println("Contact Added");

					JOptionPane.showMessageDialog(null, "Contact Added Successfully!");

					con.close();

				} catch (Exception ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		});
		btnAddContact.setBounds(83, 461, 127, 20);
		contentPane.add(btnAddContact);
		
		JButton btnAddPhone = new JButton("ADD_PHONE");
		btnAddPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@//127.0.0.1:1521/FREE",
						"c##norah",
						"norah123"
					);

					Statement stmt = con.createStatement();

					String id = idtxt.getText();
					String phone = phonetxt.getText();
					String phonetype = (String) phonetypecmb.getSelectedItem();

					String iq = "INSERT INTO Phone_Number VALUES('" +
						    phone + "','" + phonetype + "'," + id + ")";

					stmt.executeUpdate(iq);

					System.out.println("Phone Number Added");

					JOptionPane.showMessageDialog(null, "Phone Number Added Successfully!");

					con.close();

				} catch (Exception ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		});
		btnAddPhone.setBounds(83, 491, 127, 20);
		contentPane.add(btnAddPhone);
		
		JLabel lblNewLabel_3 = new JLabel("To add another number for existing contact enter only:");
		lblNewLabel_3.setBounds(25, 51, 313, 20);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_7 = new JLabel("Contact ID,Phone Number,Type and click ADD_PHONE.");
		lblNewLabel_7.setBounds(25, 75, 313, 20);
		contentPane.add(lblNewLabel_7);
		
		JButton btnNewButton = new JButton("BACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_menu menu = new main_menu();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(83, 521, 127, 20);
		contentPane.add(btnNewButton);
	}
}
