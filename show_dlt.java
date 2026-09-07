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
import javax.swing.JOptionPane;

public class show_dlt extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField idtxt;
	private JTextField nmtxt;
	private JTextField phonetxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show_dlt frame = new show_dlt();
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
	public show_dlt() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(315, 53, 519, 500);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NAME", "EMAIL", "CATEGORY", "PHONE_NUMBER", "PHONE_TYPE"
			}
		));

		JButton btnShow = new JButton("SHOW");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@//127.0.0.1:1521/FREE",
						"your username",
						"your password"
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
		btnShow.setBounds(541, 23, 84, 20);
		contentPane.add(btnShow);
		
		JLabel lblNewLabel = new JLabel("CONTACT ID:");
		lblNewLabel.setBounds(28, 151, 78, 12);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NAME: ");
		lblNewLabel_1.setBounds(28, 196, 78, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("NUMBER: ");
		lblNewLabel_2.setBounds(28, 245, 78, 12);
		contentPane.add(lblNewLabel_2);
		
		idtxt = new JTextField();
		idtxt.setBounds(159, 148, 123, 18);
		contentPane.add(idtxt);
		idtxt.setColumns(10);
		
		nmtxt = new JTextField();
		nmtxt.setBounds(159, 193, 123, 18);
		contentPane.add(nmtxt);
		nmtxt.setColumns(10);
		
		phonetxt = new JTextField();
		phonetxt.setBounds(159, 242, 123, 18);
		contentPane.add(phonetxt);
		phonetxt.setColumns(10);
		
		JButton btnNewButton = new JButton("DELETE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@//127.0.0.1:1521/FREE",
						"your username",
						"your password"
					);

					Statement stmt = con.createStatement();

					String id = idtxt.getText();
					String nm = nmtxt.getText();
					String phone = phonetxt.getText();

					// Delete the particular phone number
					String dq = "DELETE FROM Phone_Number " +
				            "WHERE contact_id = " + id +
				            " AND phone_number = '" + phone + "'";

					int rows = stmt.executeUpdate(dq);

					if (rows > 0) {

						// Check if the contact has any other phone number
						ResultSet rs = stmt.executeQuery(
							"SELECT COUNT(*) FROM Phone_Number " +
							"WHERE contact_id = " + id
						);

						rs.next();
						int count = rs.getInt(1);

						if (count == 0) {

							// Delete contact if no phone numbers remain
							stmt.executeUpdate(
								"DELETE FROM Contact " +
								"WHERE contact_id = " + id +
								" AND name = '" + nm + "'"
							);

							System.out.println("Phone Number and Contact Deleted");

						} else {

							System.out.println("Phone Number Deleted");

						}

						JOptionPane.showMessageDialog(
							null, "Deleted Successfully!"
						);

					} else {

						JOptionPane.showMessageDialog(
							null, "Contact or Phone Number not found!"
						);
					}

					con.close();

				} catch (Exception ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(
						null, "Error: " + ex.getMessage()
					);
				}
				 
			}
		});
		btnNewButton.setBounds(95, 308, 84, 20);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_menu menu = new main_menu();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(95, 350, 84, 20);
		contentPane.add(btnNewButton_1);
		

	}
}
