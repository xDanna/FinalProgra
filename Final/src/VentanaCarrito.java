import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import javax.swing.SpringLayout;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panPrincipal;
    private Carrito carrito;
    private JTextArea textTotal;
    protected static JTextField Usuariotxt;
    private Inventario inventario = new Inventario();
    private String usuarioId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaCarrito frame = new VentanaCarrito();
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
    public VentanaCarrito() {
    	carrito = new Carrito("00850"); 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        panPrincipal = new JPanel();
        panPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panPrincipal);
        panPrincipal.setLayout(new BorderLayout(0, 0));

        JPanel panProducto = new JPanel();
        panPrincipal.add(panProducto, BorderLayout.CENTER);
        panProducto.setLayout(new BorderLayout(0, 0));

        JLabel lblCarrito = new JLabel("Carrito");
        panProducto.add(lblCarrito, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        panProducto.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.NORTH);
        
        JPanel panel_3 = new JPanel();
        panel.add(panel_3, BorderLayout.EAST);
        
        JPanel panel_4 = new JPanel();
        panel.add(panel_4, BorderLayout.SOUTH);
        
        
        JTextArea textArea = new JTextArea();
        panel.add(textArea, BorderLayout.CENTER);
        
        carrito.toString();
        textArea.setText(carrito.toString());
        
       
        
        JPanel panOpciones = new JPanel();
        panPrincipal.add(panOpciones, BorderLayout.EAST);
        panOpciones.setLayout(new BorderLayout(0, 0));

      
        JPanel panBtn = new JPanel();
        panOpciones.add(panBtn, BorderLayout.SOUTH);
        panBtn.setLayout(new GridLayout(2, 1, 0, 0));

       
        JButton btnVaciarCarrito = new JButton("Vaciar Carrito");
        btnVaciarCarrito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                carrito.vaciarCarrito();
                actualizarCarrito(); 
                JOptionPane.showMessageDialog(null, "Su carrito se vacio exitosamente", "Carrito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panBtn.add(btnVaciarCarrito);
        JButton btnEnviarOrden = new JButton("Enviar Orden");
        btnEnviarOrden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Carrito miCarrito = new Carrito(carrito.getIdUsuario());
            	miCarrito.cambiarEstadoAConfirmado();
            	String mensaje = String.format(carrito.getIdUsuario()
                       
                    );
            	
            	JOptionPane.showMessageDialog(null, "Su orden a sido enviada exitosamente", mensaje, JOptionPane.INFORMATION_MESSAGE);
                
            }
        });
        panBtn.add(btnEnviarOrden);

        JPanel panTotal = new JPanel();
        panOpciones.add(panTotal, BorderLayout.NORTH);
        panTotal.setLayout(new GridLayout(2, 1, 0, 0));

        textTotal = new JTextArea();
        textTotal.setEditable(false);
        panTotal.add(textTotal);

        JButton btnTotal = new JButton("Calcular Total");
        btnTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
          
                double resultado = carrito.carritoTotal(Archivo.archivoCarrito);
                textTotal.setText(String.valueOf(resultado));
                
            }
        });
        panTotal.add(btnTotal);

        actualizarCarrito();
    }

    private void actualizarCarrito() {
        StringBuilder sb = new StringBuilder();
        for (Producto p : carrito.getProductosCarrito()) {
            sb.append(p.toString()).append("\n");
        }
    }
    
    
}
