import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class VentanaOrden extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaOrden frame = new VentanaOrden();
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
	public VentanaOrden() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 391);
		panPrincipal = new JPanel();
		panPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panPrincipal);
		panPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panProducto = new JPanel();
		panPrincipal.add(panProducto);
		panProducto.setLayout(new BorderLayout(0, 0));
		
		JLabel lblProductos = new JLabel("Productos");
		panProducto.add(lblProductos, BorderLayout.NORTH);
		
		JPanel panMostrarProd = new JPanel();
		panProducto.add(panMostrarProd, BorderLayout.CENTER);
		
		JPanel panOpciones = new JPanel();
		panPrincipal.add(panOpciones, BorderLayout.EAST);
		panOpciones.setLayout(new BorderLayout(0, 0));
		
		JPanel panBtn = new JPanel();
		panOpciones.add(panBtn, BorderLayout.SOUTH);
		panBtn.setLayout(new GridLayout(4, 2, 0, 0));
		
		JButton btnEnv = new JButton("Ordenes Enviadas");
		panBtn.add(btnEnv);
		
		JButton btnConf = new JButton("Ordenes Confirmadas");
		panBtn.add(btnConf);
		
		JButton btnCanc = new JButton("Ordenes Canceladas");
		panBtn.add(btnCanc);
		
		JButton btnEdit = new JButton("Editar Estado");
		panBtn.add(btnEdit);
	}

}