import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class VentanaLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    protected static JTextField Usuariotxt;
    protected static JPasswordField Contrasena;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaLogin frame = new VentanaLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VentanaLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new GridLayout(0, 1, 0, 20));
        
        JLabel lblUsuario = new JLabel("Ingrese Usuario:");
        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
        contentPane.add(lblUsuario);
        
        Usuariotxt = new JTextField();
        contentPane.add(Usuariotxt);
        Usuariotxt.setColumns(10);
        
        JLabel lblContraseña = new JLabel("Ingrese Contraseña:");
        lblContraseña.setFont(new Font("Tahoma", Font.BOLD, 15));
        contentPane.add(lblContraseña);
        
        Contrasena = new JPasswordField();
        contentPane.add(Contrasena);
        Contrasena.setColumns(10);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (logIn(Archivo.archivoUsuario) == false) {
                    JOptionPane.showMessageDialog(null, "Usuario y Contraseña no válidos");
                } else {
                    JOptionPane.showMessageDialog(null, "Login exitoso", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                  
                    gestionarCarrito(Usuariotxt.getText());
                }
            }
        });
        contentPane.add(btnIngresar);

        setContentPane(contentPane);
    }

  
    public boolean logIn(String archivo) {
        String usuarioIngresado = Usuariotxt.getText();
        String contrasenaIngresada = new String(Contrasena.getPassword()); 

        ArrayList<Comprador> usuarios = Comprador.leerUsuarios(archivo);
        for (Comprador c : usuarios) {
            if (c.getNombre().equals(usuarioIngresado) && c.getContra().equals(contrasenaIngresada)) {
                JOptionPane.showMessageDialog(null, "Login exitoso:\n" + c, "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false; 
    }

    
    public void gestionarCarrito(String nombreUsuario) {
        ArrayList<Comprador> usuarios = Comprador.leerUsuarios(Archivo.archivoUsuario);
        String usuarioId = null;
        boolean esAdmin = false;

        for (Comprador c : usuarios) {
            if (c.getNombre().equals(nombreUsuario)) {
                esAdmin = c.isTipo(); 
                if (!esAdmin) { 
                    usuarioId = c.getId(); 
                }
                break;
            }
        }

        if (!esAdmin) {
            Carrito carrito = Carrito.cargarCarritoUsuario(usuarioId);

            if (carrito == null) {
                carrito = new Carrito(usuarioId);
                JOptionPane.showMessageDialog(null, "Carrito creado para el usuario: " + usuarioId);
                
            } else {
                JOptionPane.showMessageDialog(null, "Carrito cargado para el usuario: " + usuarioId + "\n" + carrito.toString());
            }

            VentanaComprador ventanaComprador = new VentanaComprador(carrito, usuarioId);
            ventanaComprador.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario + " es un administrador, no tiene carrito.");
            VentanaInventario ventanaInventario = new VentanaInventario();
            ventanaInventario.setVisible(true);
            dispose();
        }
    }

}
