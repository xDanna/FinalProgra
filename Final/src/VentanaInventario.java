import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInventario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panPrincipal;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    Inventario inventario = new Inventario();
    JTextArea textArea = new JTextArea();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                VentanaInventario frame = new VentanaInventario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public VentanaInventario() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        panPrincipal = new JPanel();
        panPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panPrincipal);
        panPrincipal.setLayout(new BorderLayout(0, 0));

        JPanel panProducto = new JPanel();
        panPrincipal.add(panProducto, BorderLayout.CENTER);
        panProducto.setLayout(new BorderLayout(0, 0));

        JLabel lblInventario = new JLabel("Inventario");
        panProducto.add(lblInventario, BorderLayout.NORTH);

        String[] columnas = {"Nombre", "Tipo", "Precio Unitario", "Cantidad", "Tamaño", "Papel", "Film"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        panProducto.add(scrollPane, BorderLayout.CENTER);

        JPanel panOpciones = new JPanel();
        panPrincipal.add(panOpciones, BorderLayout.EAST);
        panOpciones.setLayout(new BorderLayout(0, 0));

        JPanel panBtn = new JPanel();
        panOpciones.add(panBtn, BorderLayout.NORTH);
        panBtn.setLayout(new GridLayout(0, 1, 0, 0));
        
        
        JLabel lblFiltro = new JLabel("Filtro");
        panBtn.add(lblFiltro);
        
        JComboBox comboBoxFIltros = new JComboBox();
        comboBoxFIltros.addActionListener(e -> {
            String filtroSeleccionado = comboBoxFIltros.getSelectedItem().toString();
            filtrarProductos(filtroSeleccionado);
            
        });

        comboBoxFIltros.setModel(new DefaultComboBoxModel(new String[] {"Todos", "Pin", "Print", "Sticker", "Sin Stock"}));
        lblFiltro.setLabelFor(comboBoxFIltros);
        panBtn.add(comboBoxFIltros);
        
        JButton btnCalcTotal = new JButton("Calcular Total");
        btnCalcTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Suponiendo que ya tienes la lista de productos cargada
                ArrayList<Producto> inv = inventario.leerInventario(Archivo.archivoProducto); // Lee los productos desde el archivo

                // Obtener valor total por tipo de producto
                double valorTotalSticker = inventario.obtenerValorPorTipo(inv, "Sticker");
                double valorTotalPrint = inventario.obtenerValorPorTipo(inv, "Print");
                double valorTotalPin = inventario.obtenerValorPorTipo(inv, "Pin");

                // Obtener valor total general del inventario
                double valorTotalInventario = inventario.obtenerValorTotalInventario(inv);

                // Mostrar los resultados en un showMessageDialog
                String mensaje = String.format(
                    "Valor total en Sticker: %.2f\n" +
                    "Valor total en Print: %.2f\n" +
                    "Valor total en Pin: %.2f\n" +
                    "Valor Total del Inventario: %.2f",
                    valorTotalSticker, valorTotalPrint, valorTotalPin, valorTotalInventario
                );
                textArea.setText(mensaje);
                
               
                
            }
        });
        
        JButton btnCrearNuevoProd = new JButton("Nuevo Producto");
        btnCrearNuevoProd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		VentanaNuevoProducto ventanaNuevoProducto = new VentanaNuevoProducto();
        		ventanaNuevoProducto.setVisible(true);
        	}
        });
        panBtn.add(btnCrearNuevoProd);
        
                JButton btnVerOrden = new JButton("Ver ordenes");
                btnVerOrden.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        VentanaOrdenes ventanaOrdenes = new VentanaOrdenes(null, null);
                        ventanaOrdenes.setVisible(true);
                    }
                });
                panBtn.add(btnVerOrden);
                panBtn.add(btnVerOrden);
        panBtn.add(btnCalcTotal);

        JButton btnGuardar = new JButton("Guardar Cambios");
        panOpciones.add(btnGuardar, BorderLayout.SOUTH);
        
        
        panOpciones.add(textArea, BorderLayout.CENTER);
        btnGuardar.addActionListener(e -> guardarCambios());
        cargarDatosArchivo(Archivo.archivoProducto);
        
    }

    public void cargarDatosArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Pin")) {
                    String[] datosPin = inventario.parsearPin(linea);
                    modeloTabla.addRow(datosPin);
                } else if (linea.startsWith("Print")) {
                    String[] datosPrint = inventario.parsearPrint(linea);
                    modeloTabla.addRow(datosPrint);
                } else if (linea.startsWith("Sticker")) {
                    String[] datosSticker = inventario.parsearSticker(linea);
                    modeloTabla.addRow(datosSticker);
                }
            }
        } catch (IOException e) {
            System.out.println("NO se puede cargar los datos");
        }
    }

    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Archivo.archivoProducto))) {
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                // Recuperamos la columna "Tipo" para determinar cómo formatear la línea
                String tipo = modeloTabla.getValueAt(i, 1).toString();

                if (tipo.equalsIgnoreCase("Pin")) {
                    // Formato específico para "Pin"
                	Pin.escribirPinEnArchivo(bw, i, modeloTabla);
                  
                } else if (tipo.equalsIgnoreCase("Print")) {
                    // Formato específico para "Print"
                    Print.escribirPrintArchivo(bw, i, modeloTabla);
                } else if (tipo.equalsIgnoreCase("Sticker")) {
                    // Formato específico para "Sticker"
                   Sticker.escribirStickerEnArchivo(bw, i, modeloTabla);
                }
                bw.newLine(); // Agregamos un salto de línea después de cada producto
            }
            JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos.");
        }
    }
    
    public void filtrarProductos(String filtro) {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar los nuevos datos

        try (BufferedReader br = new BufferedReader(new FileReader(Archivo.archivoProducto))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosProducto = null;

                if (filtro.equals("Todos")) {
                    // Cargar todos los productos
                    if (linea.startsWith("Pin")) {
                        datosProducto = inventario.parsearPin(linea);
                    } else if (linea.startsWith("Print")) {
                        datosProducto = inventario.parsearPrint(linea);
                    } else if (linea.startsWith("Sticker")) {
                        datosProducto = inventario.parsearSticker(linea);
                    }
                } else if (filtro.equals("Pin") && linea.startsWith("Pin")) {
                    datosProducto = inventario.parsearPin(linea);
                } else if (filtro.equals("Print") && linea.startsWith("Print")) {
                    datosProducto = inventario.parsearPrint(linea);
                } else if (filtro.equals("Sticker") && linea.startsWith("Sticker")) {
                    datosProducto = inventario.parsearSticker(linea);
                } else if (filtro.equals("Sin Stock")) {
                    // Filtro por productos con cantidad en 0
                    if (linea.startsWith("Pin") && Integer.parseInt(inventario.obtenerValor(linea, "cantidad")) == 0) {
                        datosProducto = inventario.parsearPin(linea);
                    } else if (linea.startsWith("Print") && Integer.parseInt(inventario.obtenerValor(linea, "cantidad")) == 0) {
                        datosProducto = inventario.parsearPrint(linea);
                    } else if (linea.startsWith("Sticker") && Integer.parseInt(inventario.obtenerValor(linea, "cantidad")) == 0) {
                        datosProducto = inventario.parsearSticker(linea);
                    }
                }

                if (datosProducto != null) {
                    modeloTabla.addRow(datosProducto);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pueden cargar los datos.");
        }
    }

}
