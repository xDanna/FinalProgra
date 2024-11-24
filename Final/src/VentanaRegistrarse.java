import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class VentanaRegistrarse extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    protected static JTextField textUsuario;
    protected static JPasswordField textContrasena;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaRegistrarse frame = new VentanaRegistrarse();
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
    public VentanaRegistrarse() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new GridLayout(0, 1, 0, 20));
        
        JLabel lblUsuario = new JLabel("Ingrese Usuario:");
        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
        contentPane.add(lblUsuario);
        
        textUsuario = new JTextField();
        contentPane.add(textUsuario);
        textUsuario.setColumns(10);
        
        JLabel lblContraseña = new JLabel("Ingrese Contraseña:");
        lblContraseña.setFont(new Font("Tahoma", Font.BOLD, 15));
        contentPane.add(lblContraseña);
        
        textContrasena = new JPasswordField();
        contentPane.add(textContrasena);
        textContrasena.setColumns(10);
        
       
        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = textUsuario.getText().trim(); 
                    String con = new String(textContrasena.getPassword()).trim(); 

                   
                    if (nom.isEmpty() || con.isEmpty()) {
                        throw new IllegalArgumentException("Por favor, complete todos los campos.");
                    }

                    boolean tipo = false; 
                    String id = generarID(); 

                    Comprador c = new Comprador(nom, con, tipo, id);

                    if (c.escribirComprador(Archivo.archivoUsuario)) {
                        JOptionPane.showMessageDialog(null, "¡Registro exitoso!", "Registro", JOptionPane.INFORMATION_MESSAGE);
                        textUsuario.setText("");
                        textContrasena.setText("");

                        VistaPrincipal vistaPrincipal = new VistaPrincipal();
                        vistaPrincipal.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    
                    JOptionPane.showMessageDialog(null, "Ocurrió un error. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnRegistrar);
        
        setContentPane(contentPane);
    }
    
    /**
     * Genera un ID aleatorio de 5 dígitos.
     */
    public static String generarID() {
        Random random = new Random();
        int rand = random.nextInt(100000);
        return String.format("%05d", rand);
    }
}
