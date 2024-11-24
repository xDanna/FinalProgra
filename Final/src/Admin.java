import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Admin extends Usuario implements GeneralUsuarios {

    public Admin(String nombre, String contra, boolean tipo) {
        super(nombre, contra, tipo);
    }

    @Override
    public void verCatalogo() {
        
    }

    @Override
    public void loginUsuarios() {
        
    }

     
    public boolean escribirAdmin(String archivo) {
        try (DataOutputStream escritor = new DataOutputStream(new FileOutputStream(archivo, true))) {
            escritor.writeUTF(this.nombre);
            escritor.writeUTF(this.contra);
            escritor.writeBoolean(this.tipo); 
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public void verOrden(String archivo) {
        Carrito carrito = new Carrito(archivo);

        if (carrito != null && carrito.getEstado().equals("Confirmado")) {
            System.out.println("El carrito del usuario " + carrito.getIdUsuario() + " está confirmado. Productos en el carrito:");
  
                System.out.println(carrito.toString());
           
        } else {
            System.out.println("No hay carritos confirmados ");
        }
    }
 
    
}
