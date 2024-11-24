import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaComprador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPan;
    private JTable tabCatalogo;
    private DefaultTableModel modeloTabla;
    private Inventario inventario = new Inventario();
    private Carrito carrito;
    private String usuarioId;
    
    public VentanaComprador(Carrito carrito, String usuarioId) {
        this.carrito = carrito;
        this.usuarioId = usuarioId;
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 709, 512);
        contentPan = new JPanel();
        contentPan.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPan);
        contentPan.setLayout(new BorderLayout(0, 0));

        
        JLabel lblNewLabel = new JLabel("E-Commerce - Usuario: " + usuarioId);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setEnabled(false);
        contentPan.add(lblNewLabel, BorderLayout.NORTH);

        
        JPanel panBtn = new JPanel();
        panBtn.setBackground(new Color(240, 240, 240));
        contentPan.add(panBtn, BorderLayout.EAST);
        panBtn.setLayout(new BorderLayout(0, 0));

        JButton btnCarrito = new JButton("Ver Carrito");
        btnCarrito.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		VentanaCarrito ventanaCarrito = new VentanaCarrito();
        		ventanaCarrito.setVisible(true);
        	}
        });
       
        btnCarrito.setBackground(new Color(240, 240, 240));
        btnCarrito.setVerticalAlignment(SwingConstants.BOTTOM);
        panBtn.add(btnCarrito, BorderLayout.NORTH);

        
        JButton btnLogOut = new JButton("Cerrar Sesión");
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCarritoPendiente();
                JOptionPane.showMessageDialog(VentanaComprador.this, "Carrito guardado como pendiente. Cerrando sesión...");
                VistaPrincipal vistaPrincipal = new VistaPrincipal();
                vistaPrincipal.setVisible(true);
                dispose();
            }
        });
        panBtn.add(btnLogOut, BorderLayout.SOUTH);
        
        JPanel panel = new JPanel();
        panBtn.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        
        JPanel panTabla = new JPanel(new BorderLayout());
        contentPan.add(panTabla, BorderLayout.CENTER);

       
        String[] columnas = {"Nombre", "Tipo", "Precio Unitario", "Stock", "Tamaño", "Papel", "Film", "Acción"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; 
            }
        };
        tabCatalogo = new JTable(modeloTabla);
        tabCatalogo.getColumn("Acción").setCellRenderer(new ButtonRenderer());
        tabCatalogo.getColumn("Acción").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(tabCatalogo);
        panTabla.add(scrollPane, BorderLayout.CENTER);
        
        JButton button = new JButton("New button");
        scrollPane.setColumnHeaderView(button);

        
        cargarDatosArchivo(Archivo.archivoProducto);
    }

    private void guardarCarritoPendiente() {
        
        carrito.setEstado("Pendiente");
        carrito.guardarCarrito(carrito);
    }

  
    private void cargarDatosArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Pin")) {
                    String[] datosPin = inventario.parsearPin(linea);
                    agregarFilaConBoton(datosPin);
                } else if (linea.startsWith("Print")) {
                    String[] datosPrint = inventario.parsearPrint(linea);
                    agregarFilaConBoton(datosPrint);
                } else if (linea.startsWith("Sticker")) {
                    String[] datosSticker = inventario.parsearSticker(linea);
                    agregarFilaConBoton(datosSticker);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pueden cargar los datos del archivo.");
        }
    }

    
    private void agregarFilaConBoton(String[] datosProducto) {
        Object[] filaConBoton = new Object[datosProducto.length + 1];
        System.arraycopy(datosProducto, 0, filaConBoton, 0, datosProducto.length);
        filaConBoton[datosProducto.length] = "Añadir al carrito"; 
        modeloTabla.addRow(filaConBoton);
    }

   
    private void mostrarCarrito() {
        if (carrito.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
        } else {
            StringBuilder contenidoCarrito = new StringBuilder("Contenido del carrito:\n");
            for (Producto p : carrito.getProductos()) {
                contenidoCarrito.append(p.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, contenidoCarrito.toString());
        }
    }

    
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "Añadir");
            return this;
        }
    }

    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value != null) ? value.toString() : "Añadir";
            button.setText(label);
            isPushed = true;
            selectedRow = row; 
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                String nombreProducto = modeloTabla.getValueAt(selectedRow, 0).toString();
                Producto producto = inventario.buscarProductoPorNombre(nombreProducto, Archivo.archivoProducto);
                if (producto != null) {
                    carrito.agregarProducto(producto);
                    JOptionPane.showMessageDialog(VentanaComprador.this, "Producto añadido al carrito: " + producto.getNombre());
                }
            }
            isPushed = false;
            return label;
        }
    }
}
