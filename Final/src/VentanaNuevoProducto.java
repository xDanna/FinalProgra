import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class VentanaNuevoProducto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panPrincipal;
    private JTextField textNombre;
    private JTextField textPrecio;
    private JTextField textCant;

    private JComboBox<String> comboBoxTipo;
    private JComboBox<String> comboBoxPapel;
    private JComboBox<String> comboBoxFilm;
    private JComboBox<String> comboBoxTam;

   
    Inventario inventario= new Inventario();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaNuevoProducto frame = new VentanaNuevoProducto();
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
    public VentanaNuevoProducto() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 307, 587);
        panPrincipal = new JPanel();
        panPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(panPrincipal);
        panPrincipal.setLayout(new GridLayout(0, 1, 0, 0));

        JLabel lblNombre = new JLabel("Nombre producto");
        panPrincipal.add(lblNombre);

        textNombre = new JTextField();
        panPrincipal.add(textNombre);
        textNombre.setColumns(10);

        JLabel lblTipo = new JLabel("Tipo de Producto");
        panPrincipal.add(lblTipo);

   
        comboBoxTipo = new JComboBox<>(new String[] {"Sticker", "Print", "Pin"});
        comboBoxTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCamposSegunTipo();
            }
        });
        panPrincipal.add(comboBoxTipo);

        JLabel lblPrecio = new JLabel("Precio Unitario");
        panPrincipal.add(lblPrecio);

        textPrecio = new JTextField();
        panPrincipal.add(textPrecio);
        textPrecio.setColumns(10);

        JLabel lblCantidad = new JLabel("Cantidad de Productos");
        panPrincipal.add(lblCantidad);

        textCant = new JTextField();
        panPrincipal.add(textCant);
        textCant.setColumns(10);

  
        JLabel lblPapel = new JLabel("Tipo de Papel");
        panPrincipal.add(lblPapel);
        comboBoxPapel = new JComboBox<>();
        panPrincipal.add(comboBoxPapel);

        
        JLabel lblFilm = new JLabel("Tipo de Film");
        panPrincipal.add(lblFilm);
        comboBoxFilm = new JComboBox<>(new String[] {"Si", "No"});
        panPrincipal.add(comboBoxFilm);

        
        JLabel lblTamaño = new JLabel("Tamaño de Producto");
        panPrincipal.add(lblTamaño);
        comboBoxTam = new JComboBox<>();
        panPrincipal.add(comboBoxTam);

        JPanel panBtn = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panBtn.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        panPrincipal.add(panBtn);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                 
                    String nombre = textNombre.getText();
                    String tipo = comboBoxTipo.getSelectedItem().toString();
                    double precio = Double.parseDouble(textPrecio.getText());
                    int cantidad = Integer.parseInt(textCant.getText());

                    Producto nuevoProducto = null;

                   
                    switch (tipo) {
                        case "Sticker":
                            String papel = comboBoxPapel.getSelectedItem().toString();
                            boolean film = comboBoxFilm.getSelectedItem().toString().equals("Si");
                            double tamaño = Double.parseDouble(comboBoxTam.getSelectedItem().toString());
                            nuevoProducto = new Sticker(nombre, tipo, precio, cantidad, papel, film, tamaño);
                            break;
                        case "Print":
                            String papelPrint = comboBoxPapel.getSelectedItem().toString();
                            String tamañoPrint = comboBoxTam.getSelectedItem().toString();
                            nuevoProducto = new Print(nombre, tipo, precio, cantidad, papelPrint, tamañoPrint);
                            break;
                        case "Pin":
                            int tamañoPin = Integer.parseInt(comboBoxTam.getSelectedItem().toString());
                            nuevoProducto = new Pin(nombre, tipo, precio, cantidad, tamañoPin);
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de producto no válido.");
                    }

                    
                    if (nuevoProducto != null) {
                        inventario.escribirInventario(nuevoProducto, Archivo.archivoProducto);
                        limpiarCampos();
                        JOptionPane.showMessageDialog(null, "Se ha guardado exitosamente su producto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panBtn.add(btnGuardar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        panBtn.add(btnLimpiar);
        actualizarCamposSegunTipo();
    }
    private void actualizarCamposSegunTipo() {
        String tipo = comboBoxTipo.getSelectedItem().toString();

        if (tipo.equals("Print")) {
            comboBoxFilm.setEnabled(false);
            comboBoxPapel.setModel(new JComboBox<>(new String[] {"Fotografico", "Matte"}).getModel());  
            comboBoxTam.setModel(new JComboBox<>(new String[] {"A3", "A4", "A5"}).getModel()); 
        } else if (tipo.equals("Pin")) {
            comboBoxFilm.setEnabled(false);  
            comboBoxPapel.setEnabled(false);  
            comboBoxTam.setModel(new JComboBox<>(new String[] {"5", "10"}).getModel());  
        } else {  
            comboBoxFilm.setEnabled(true);   
            comboBoxPapel.setEnabled(true);  
            comboBoxPapel.setModel(new JComboBox<>(new String[] {"Metalico", "Matte", "Fotografico", "Brilloso", "Holografico"}).getModel()); 
            comboBoxTam.setModel(new JComboBox<>(new String[] {"3.5", "5", "7.5", "10"}).getModel());  
        }
    }

    private void limpiarCampos() {
        textNombre.setText("");
        textPrecio.setText("");
        textCant.setText("");
        comboBoxTipo.setSelectedIndex(0);
        comboBoxPapel.setSelectedIndex(0);
        comboBoxFilm.setSelectedIndex(0);
        comboBoxTam.setSelectedIndex(0);
    }
}


