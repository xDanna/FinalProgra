import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class VentanaAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdmin frame = new VentanaAdmin();
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
	public VentanaAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panPrincipal = new JPanel();
		panPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panPrincipal);
		panPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panOpciones = new JPanel();
		panPrincipal.add(panOpciones, BorderLayout.CENTER);
		panOpciones.setLayout(new BorderLayout(0, 0));
		
		JPanel panBtn = new JPanel();
		panOpciones.add(panBtn, BorderLayout.CENTER);
		panBtn.setLayout(new GridLayout(3, 2, 0, 0));
		
		JButton btnVerInventario = new JButton("Ver Inventario");
		panBtn.add(btnVerInventario);
		
		JButton btnVerOrdenes = new JButton("Ver Ordenes");
		panBtn.add(btnVerOrdenes);
		
		JButton btnLogOut = new JButton("Cerrar Sesion");
		panBtn.add(btnLogOut);
		
		JLabel lblPanControl = new JLabel("Panel de Control");
		panPrincipal.add(lblPanControl, BorderLayout.NORTH);
	}

}
