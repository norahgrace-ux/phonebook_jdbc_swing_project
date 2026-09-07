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

public class show_edit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField idtxt;
	private JTextField nmtxt;
	private JTextField emailtxt;
	private JTextField oldphonetxt;
	private JTextField newphonetxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show_edit frame = new show_edit();
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
	public show_edit() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(289, 69, 536, 484);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NAME", "EMAIL", "CATEGORY", "NUMBER", "TYPE"
			}
		));
		
		JButton btnShow = new JButton("SHOW");
		btnShow.addActionListener(new ActionListener() {
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
						"SELECT c.contact_id, c.name, c.email, " +
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
		btnShow.setBounds(528, 39, 84, 20);
		contentPane.add(btnShow);
		
		JLabel lblNewLabel = new JLabel("CONTACT ID: ");
		lblNewLabel.setBounds(24, 138, 84, 12);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NAME: ");
		lblNewLabel_1.setBounds(24, 186, 84, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("EMAIL: ");
		lblNewLabel_2.setBounds(24, 238, 84, 12);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("CATEGORY: ");
		lblNewLabel_3.setBounds(24, 295, 84, 12);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("OLD NUMBER: ");
		lblNewLabel_4.setBounds(24, 351, 114, 12);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("NEW NUMBER: ");
		lblNewLabel_5.setBounds(24, 407, 114, 12);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("PHONE TYPE: ");
		lblNewLabel_6.setBounds(24, 463, 114, 12);
		contentPane.add(lblNewLabel_6);
		
		JComboBox categorycmb = new JComboBox();
		categorycmb.setModel(new DefaultComboBoxModel(new String[] {"Family", "Friends", "Work", "Other", "Emergency"}));
		categorycmb.setBounds(148, 291, 96, 20);
		contentPane.add(categorycmb);
		
		JComboBox phonetypecmb = new JComboBox();
		phonetypecmb.setModel(new DefaultComboBoxModel(new String[] {"Mobile", "Home", "Work"}));
		phonetypecmb.setBounds(148, 459, 96, 20);
		contentPane.add(phonetypecmb);
		
		idtxt = new JTextField();
		idtxt.setBounds(148, 135, 96, 18);
		contentPane.add(idtxt);
		idtxt.setColumns(10);
		
		nmtxt = new JTextField();
		nmtxt.setBounds(148, 183, 96, 18);
		contentPane.add(nmtxt);
		nmtxt.setColumns(10);
		
		emailtxt = new JTextField();
		emailtxt.setBounds(148, 235, 96, 18);
		contentPane.add(emailtxt);
		emailtxt.setColumns(10);
		
		oldphonetxt = new JTextField();
		oldphonetxt.setBounds(148, 348, 96, 18);
		contentPane.add(oldphonetxt);
		oldphonetxt.setColumns(10);
		
		newphonetxt = new JTextField();
		newphonetxt.setBounds(148, 404, 96, 18);
		contentPane.add(newphonetxt);
		newphonetxt.setColumns(10);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
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
					String oldphone = oldphonetxt.getText();
					String newphone = newphonetxt.getText();
					String phonetype = (String) phonetypecmb.getSelectedItem();

					// CHECK CONTACT ID
	                ResultSet rs = stmt.executeQuery(
	                        "SELECT * FROM Contact WHERE contact_id = " + id
	                );

	                if (!rs.next()) {

	                    JOptionPane.showMessageDialog(null, "Person not found!");

	                }else {

	                    // CHECK OLD PHONE NUMBER
	                    ResultSet rs2 = stmt.executeQuery(
	                            "SELECT * FROM Phone_Number WHERE contact_id = "
	                            + id + " AND phone_number = '" + oldphone + "'"
	                    );

	                    if (!rs2.next()) {

	                        JOptionPane.showMessageDialog(null,
	                                "Old phone number not found!");

	                    } else {
	                    	// GET CATEGORY ID
	                        ResultSet rs3 = stmt.executeQuery(
	                                "SELECT category_id FROM Category WHERE category_name = '"
	                                + category + "'"
	                        );

	                        rs3.next();
	                        int categoryId = rs3.getInt("category_id");


	                        // UPDATE CONTACT DETAILS
	                        String uq = "UPDATE Contact SET "
	                                + "name = '" + nm + "', "
	                                + "email = '" + email + "', "
	                                + "category_id = " + categoryId
	                                + " WHERE contact_id = " + id;

	                        stmt.executeUpdate(uq);


	                        // UPDATE PHONE NUMBER
	                        String pq = "UPDATE Phone_Number SET "
	                                + "phone_number = '" + newphone + "', "
	                                + "phone_type = '" + phonetype + "' "
	                                + "WHERE contact_id = " + id
	                                + " AND phone_number = '" + oldphone + "'";

	                        stmt.executeUpdate(pq);

	                        JOptionPane.showMessageDialog(null,
	                                "Contact updated successfully!");

	                    }
	                }

	                con.close();

	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
			}
		});
		btnEdit.setBounds(89, 502, 84, 20);
		contentPane.add(btnEdit);
		
		JButton btnNewButton = new JButton("BACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_menu menu = new main_menu();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(89, 532, 84, 20);
		contentPane.add(btnNewButton);

	}
}
