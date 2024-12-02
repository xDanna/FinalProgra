import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaPrincipal frame = new VistaPrincipal();
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
	public VistaPrincipal() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 587, 271);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("E-Commerce");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setEnabled(false);
		contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 20));
		
		JLabel lblBienvenida = new JLabel("Bienvenido a E-Commerce");
		lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblBienvenida);
		
		JButton btnIniciarSesion = new JButton("Iniciar sesión");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLogin ventanaLogin = new VentanaLogin();
                ventanaLogin.setVisible(true);
                dispose();

			}
		});
		btnIniciarSesion.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(btnIniciarSesion);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistrarse ventanaRegistrarse = new VentanaRegistrarse();
				ventanaRegistrarse.setVisible(true);
				dispose();

			}
		});
		btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(btnRegistrarse);
	}

}
