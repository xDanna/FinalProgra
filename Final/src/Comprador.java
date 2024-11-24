import java.io.*;
import java.util.ArrayList;

public class Comprador extends Usuario implements GeneralUsuarios {
    protected String id;

    public Comprador(String nombre, String contra, boolean tipo, String id) {
        super(nombre, contra, tipo);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre + ";" + contra + ";" + tipo + ";" + id;
    }

    public boolean escribirComprador(String archivo) {
        try (DataOutputStream escritor = new DataOutputStream(new FileOutputStream(archivo, true))) {
            escritor.writeUTF(this.nombre);
            escritor.writeUTF(this.contra);
            escritor.writeBoolean(this.tipo);
            escritor.writeUTF(this.id);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Comprador> leerUsuarios(String archivo) {
        ArrayList<Comprador> usuarios = new ArrayList<>();
        try (DataInputStream lector = new DataInputStream(new FileInputStream(archivo))) {
            while (lector.available() > 0) {
                String nombre = lector.readUTF();
                String contra = lector.readUTF();
                boolean tipo = lector.readBoolean();
                String id = lector.readUTF();
                usuarios.add(new Comprador(nombre, contra, tipo, id));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + archivo);
        } catch (IOException e) {
        }
        return usuarios;
    }

    @Override
    public void verCatalogo() {
       
    }

    @Override
    public void loginUsuarios() {
    }
}
