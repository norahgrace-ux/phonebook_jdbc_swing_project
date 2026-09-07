package HELLO;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class main_menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main_menu frame = new main_menu();
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
	public main_menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("ADD & DISPLAY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_add add = new show_add();
			    add.setVisible(true);
			    dispose();
			}
		});
		btnNewButton.setBounds(371, 216, 160, 20);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("EDIT & DISPLAY");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_edit edit = new show_edit();
			    edit.setVisible(true);
			    dispose();
			}
		});
		btnNewButton_1.setBounds(371, 321, 160, 20);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("DELETE & DISPLAY");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_dlt dlt = new show_dlt();
			    dlt.setVisible(true);
			    dispose();
			}
		});
		btnNewButton_2.setBounds(371, 429, 160, 20);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Wecome to Phonebook Management System! Please choose an option:");
		lblNewLabel.setBounds(258, 74, 479, 44);
		contentPane.add(lblNewLabel);

	}
}
