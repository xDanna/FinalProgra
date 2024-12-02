import java.io.*;

public class Pin extends Producto{
private int tamaño;

public Pin(String nombre, String tipo, double precioUnitario, int cantidad,  int tamaño) {
	super(nombre, tipo, precioUnitario, cantidad);
	this.tamaño = tamaño;
}

public int getTamaño() {
	return tamaño;
}

public void setTamaño(int tamaño) {
	this.tamaño = tamaño;
}

@Override
public String toString() {
	return "Pin [tamaño=" + tamaño + ", nombre=" + nombre + ", tipo=" + tipo + ", precioUnitario=" + precioUnitario
			+ ", cantidad=" + cantidad + "]";
}
public static void escribirPinEnArchivo(BufferedWriter bw, int fila, javax.swing.table.DefaultTableModel modeloTabla) throws IOException {
    String tamaño = modeloTabla.getValueAt(fila, 4) != null ? modeloTabla.getValueAt(fila, 4).toString() : "";
    String nombre = modeloTabla.getValueAt(fila, 0).toString();
    String tipo = modeloTabla.getValueAt(fila, 1).toString();
    String precio = modeloTabla.getValueAt(fila, 2).toString();
    String cantidad = modeloTabla.getValueAt(fila, 3).toString();

    bw.write("Pin [tamaño=" + tamaño + ", nombre=" + nombre + ", tipo=" + tipo +
             ", precioUnitario=" + precio + ", cantidad=" + cantidad + "]");
}

}
