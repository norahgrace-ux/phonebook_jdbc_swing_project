package HELLO;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernm;
	private JTextField passwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.setBounds(382, 438, 100, 27);
		contentPane.add(btnNewButton);
		
		usernm = new JTextField();
		usernm.setBounds(488, 235, 126, 27);
		contentPane.add(usernm);
		usernm.setColumns(10);
		
		passwd = new JTextField();
		passwd.setBounds(488, 329, 126, 27);
		contentPane.add(passwd);
		passwd.setColumns(10);
		
		JLabel label1 = new JLabel("ENTER USERNAME: ");
		label1.setBounds(247, 232, 175, 32);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("ENTER PASSWORD: ");
		label2.setBounds(247, 329, 175, 27);
		contentPane.add(label2);
		
		JLabel lblNewLabel = new JLabel("PHONEBOOK MANAGEMENT SYSTEM");
		lblNewLabel.setBounds(312, 108, 234, 12);
		contentPane.add(lblNewLabel);
		
		btnNewButton.addActionListener(e -> {

		    String username = usernm.getText();
		    String password = passwd.getText();

		    try {

		        Connection con = DriverManager.getConnection(
		            "jdbc:oracle:thin:@//127.0.0.1:1521/FREE",
		            "your username",
		            "your password"
		        );

		        // First check whether Java can see the table
		        String sql = "SELECT usernm, passwd FROM login";

		        PreparedStatement ps = con.prepareStatement(sql);

		        ResultSet rs = ps.executeQuery();

		        boolean found = false;

		        while (rs.next()) {

		            String dbUser = rs.getString("usernm");
		            String dbPass = rs.getString("passwd");

		            if (username.equals(dbUser) && password.equals(dbPass)) {
		                found = true;
		            }
		        }

		        if (found) {
		            JOptionPane.showMessageDialog(
		                null,
		                "Login Successful!"
		            );

		            main_menu menu = new main_menu();
		            menu.setVisible(true);
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(
		                null,
		                "Invalid Username or Password"
		            );
		        }

		        rs.close();
		        ps.close();
		        con.close();

		    } catch (Exception ex) {

		        ex.printStackTrace();

		        JOptionPane.showMessageDialog(
		            null,
		            "Database connection failed!\n" + ex.getMessage()
		        );
		    }
		});
	}
}
