import java.util.ArrayList;

public abstract class Producto {
	protected String nombre;
	protected String tipo;
	protected double precioUnitario;
	protected int cantidad;

	public Producto(String nombre, String tipo, double precioUnitario, int cantidad) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", tipo=" + tipo + ", precioUnitario=" + precioUnitario + ", cantidad="
				+ cantidad + "]";
	}

	public static Producto crearProducto(String nombre, String tipo, double precioUnitario, int cantidad, String papel,
			boolean film, Object tamaño) {
		Producto producto = null;

		switch (tipo) {
		case "Sticker":
			if (tamaño instanceof Double) {
				producto = new Sticker(nombre, tipo, precioUnitario, cantidad, papel, film, (Double) tamaño);
			} else {
				System.out.println("Tamaño incorrecto para un Sticker. Se esperaba un Double.");
			}
			break;

		case "Print":
			if (tamaño instanceof String) {
				producto = new Print(nombre, tipo, precioUnitario, cantidad, papel, (String) tamaño);
			} else {
				System.out.println("Tamaño incorrecto para un Print. Se esperaba un String.");
			}
			break;

		case "Pin":
			if (tamaño instanceof Integer) {
				producto = new Pin(nombre, tipo, precioUnitario, cantidad, (Integer) tamaño);
			} else {
				System.out.println("Tamaño incorrecto para un Pin. Se esperaba un Integer.");
			}
			break;

		default:
			System.out.println("Tipo de producto no reconocido.");
			break;
		}

		return producto;
	}
	
}
