import java.io.BufferedWriter;
import java.io.IOException;

public class Print extends Producto{
private String papel;
private String tamaño;
public Print(String nombre, String tipo, double precioUnitario, int cantidad, String papel,
		String tamaño) {
	super(nombre, tipo, precioUnitario, cantidad);
	this.papel = papel;
	this.tamaño = tamaño;
}
public String getPapel() {
	return papel;
}
public void setPapel(String papel) {
	this.papel = papel;
}
public String getTamaño() {
	return tamaño;
}
public void setTamaño(String tamaño) {
	this.tamaño = tamaño;
}
@Override
public String toString() {
	return "Print [papel=" + papel + ", tamaño=" + tamaño + ", nombre=" + nombre + ", tipo=" + tipo
			+ ", precioUnitario=" + precioUnitario + ", cantidad=" + cantidad + "]";
}

public static void escribirPrintArchivo(BufferedWriter bw, int i, javax.swing.table.DefaultTableModel modeloTabla) throws IOException {
	String papel = modeloTabla.getValueAt(i, 5) != null ? modeloTabla.getValueAt(i, 5).toString() : "";
    String tamaño = modeloTabla.getValueAt(i, 4) != null ? modeloTabla.getValueAt(i, 4).toString() : "";
    String nombre = modeloTabla.getValueAt(i, 0).toString();
    String precio = modeloTabla.getValueAt(i, 2).toString();
    String cantidad = modeloTabla.getValueAt(i, 3).toString();
    bw.write("Print [papel=" + papel + ", tamaño=" + tamaño + ", nombre=" + nombre + ", tipo=" + "Print" +
            ", precioUnitario=" + precio + ", cantidad=" + cantidad + "]");

}

}
