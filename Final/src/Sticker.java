import java.io.BufferedWriter;
import java.io.IOException;

public class Sticker extends Producto{
private String papel;
private boolean film;
private double tamaño;
public Sticker(String nombre, String tipo, double precioUnitario, int cantidad, String papel,
		boolean film, double tamaño) {
	super(nombre, tipo, precioUnitario, cantidad);
	this.papel = papel;
	this.film = film;
	this.tamaño = tamaño;
}
public String getPapel() {
	return papel;
}
public void setPapel(String papel) {
	this.papel = papel;
}
public boolean isFilm() {
	return film;
}
public void setFilm(boolean film) {
	this.film = film;
}
public double getTamaño() {
	return tamaño;
}
public void setTamaño(double tamaño) {
	this.tamaño = tamaño;
}
@Override
public String toString() {
	return "Sticker [papel=" + papel + ", film=" + film + ", tamaño=" + tamaño + ", nombre=" + nombre + ", tipo=" + tipo
			+ ", precioUnitario=" + precioUnitario + ", cantidad=" + cantidad + "]";
}
public static void escribirStickerEnArchivo(BufferedWriter bw, int i, javax.swing.table.DefaultTableModel modeloTabla) throws IOException {
	 String papel = modeloTabla.getValueAt(i, 5) != null ? modeloTabla.getValueAt(i, 5).toString() : "";
     String film = modeloTabla.getValueAt(i, 6) != null ? modeloTabla.getValueAt(i, 6).toString() : "";
     String tamaño = modeloTabla.getValueAt(i, 4) != null ? modeloTabla.getValueAt(i, 4).toString() : "";
     String nombre = modeloTabla.getValueAt(i, 0).toString();
     String precio = modeloTabla.getValueAt(i, 2).toString();
     String cantidad = modeloTabla.getValueAt(i, 3).toString();
     bw.write("Sticker [papel=" + papel + ", film=" + film + ", tamaño=" + tamaño +
             ", nombre=" + nombre + ", tipo=" + "Stiker" + ", precioUnitario=" + precio +
             ", cantidad=" + cantidad + "]");
}

}
