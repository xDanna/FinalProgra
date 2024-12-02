import java.io.*;
import java.util.ArrayList;

public class Carrito {
    private String idUsuario;
    private ArrayList<Producto> productos;
    private String estado; 

    public Carrito(String idUsuario) {
        this.idUsuario = idUsuario;
        this.productos = new ArrayList<>();
        this.estado = "nuevo"; 
    }


    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

 
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void vaciarCarrito() {
        productos.clear();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo.archivoCarrito))) {
            writer.write("");  
        } catch (IOException e) {
            System.out.println("Error al limpiar el archivo: " + e.getMessage());
        }
        guardarCarrito(this);

    }
    
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getEstado() {
        return estado;
    }
    
    public ArrayList<Producto> getProductosCarrito() {
        return productos;
    }

     
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public static Carrito cargarCarritoUsuario(String usuarioId) {
        try (BufferedReader br = new BufferedReader(new FileReader(Archivo.archivoCarrito))) {
            String linea;
            Carrito carrito = null;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", 3);
                if (partes.length < 3) {
                    System.out.println("Línea mal formateada: " + linea);
                    continue;
                }
                String id = partes[0].trim();
                if (id.equals(usuarioId)) {
                    if (carrito != null) {
                        if (carrito.getEstado().equalsIgnoreCase("Confirmado")) {
                            System.out.println("El carrito ya está confirmado. No se pueden añadir más productos.");
                            return carrito;
                        }
                    } else {
                        carrito = new Carrito(id);
                    }

                    String productosStr = partes[1].replace("Productos{", "").replace("}", "").trim();
                    if (!productosStr.isEmpty()) {
                        String[] productosArray = productosStr.split("], \\s*");
                        for (String productoLinea : productosArray) {
                            Producto producto = cargarProductoDesdeLinea(productoLinea + "]");
                            if (producto != null) {
                                carrito.agregarProducto(producto);
                            }
                        }
                    }
                    carrito.setEstado(partes[2].trim());
                }
            }

            return carrito;
        } catch (Exception e) {
            System.out.println("Error cargando el carrito: " + e.getMessage());
            
            return null;
        }
    }

    public void guardarCarrito(Carrito carrito) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo.archivoCarrito, true))) {
            writer.write(carrito.getIdUsuario() + ";");
            writer.write("Productos{");
            ArrayList<Producto> productos = carrito.getProductos();
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                if (producto instanceof Sticker) {
                    Sticker sticker = (Sticker) producto;
                    writer.write("Sticker [papel=" + sticker.getPapel() +
                            ", film=" + sticker.isFilm() +
                            ", tamaño=" + sticker.getTamaño() +
                            ", nombre=" + sticker.getNombre() +
                            ", tipo=" + sticker.getTipo() +
                            ", precioUnitario=" + sticker.getPrecioUnitario() +
                            ", cantidad=" + sticker.getCantidad() + "]");
                } else if (producto instanceof Pin) {
                    Pin pin = (Pin) producto;
                    writer.write("Pin [tamaño=" + pin.getTamaño() +
                            ", nombre=" + pin.getNombre() +
                            ", tipo=" + pin.getTipo() +
                            ", precioUnitario=" + pin.getPrecioUnitario() +
                            ", cantidad=" + pin.getCantidad() + "]");
                } else if (producto instanceof Print) {
                    Print print = (Print) producto;
                    writer.write("Print [papel=" + print.getPapel() +
                            ", tamaño=" + print.getTamaño() +
                            ", nombre=" + print.getNombre() +
                            ", tipo=" + print.getTipo() +
                            ", precioUnitario=" + print.getPrecioUnitario() +
                            ", cantidad=" + print.getCantidad() + "]");
                }
                if (i < productos.size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("};");
            writer.write(carrito.getEstado());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el carrito: " + e.getMessage());
        }
    }

    private static Producto cargarProductoDesdeLinea(String lineaProducto) {
        try {
            lineaProducto = lineaProducto.trim();
            if (lineaProducto.endsWith("]")) {
                lineaProducto = lineaProducto.substring(0, lineaProducto.length() - 1);
            }
            if (lineaProducto.startsWith("Sticker")) {
                lineaProducto = lineaProducto.replace("Sticker [", "").trim();
                String[] atributos = lineaProducto.split(", ");
                String papel = atributos[0].split("=")[1];
                boolean film = Boolean.parseBoolean(atributos[1].split("=")[1]);
                double tamaño = Double.parseDouble(atributos[2].split("=")[1]);
                String nombre = atributos[3].split("=")[1];
                String tipo = atributos[4].split("=")[1];
                double precioUnitario = Double.parseDouble(atributos[5].split("=")[1]);
                int cantidad = Integer.parseInt(atributos[6].split("=")[1]);
                return new Sticker(nombre, tipo, precioUnitario, cantidad, papel, film, tamaño);
                
            } else if (lineaProducto.startsWith("Pin")) {
                lineaProducto = lineaProducto.replace("Pin [", "").trim();
                String[] atributos = lineaProducto.split(", ");
                int tamaño = Integer.parseInt(atributos[0].split("=")[1]);
                String nombre = atributos[1].split("=")[1];
                String tipo = atributos[2].split("=")[1];
                double precioUnitario = Double.parseDouble(atributos[3].split("=")[1]);
                int cantidad = Integer.parseInt(atributos[4].split("=")[1]);
                return new Pin(nombre, tipo, precioUnitario, cantidad, tamaño);
            } else if (lineaProducto.startsWith("Print")) {
                lineaProducto = lineaProducto.replace("Print [", "").trim();
                String[] atributos = lineaProducto.split(", ");
                String papel = atributos[0].split("=")[1];
                String tamaño = atributos[1].split("=")[1];
                String nombre = atributos[2].split("=")[1];
                String tipo = atributos[3].split("=")[1];
                double precioUnitario = Double.parseDouble(atributos[4].split("=")[1]);
                int cantidad = Integer.parseInt(atributos[5].split("=")[1]);
                return  new Print(nombre, tipo, precioUnitario, cantidad, papel, tamaño);
            } else {
                throw new IllegalArgumentException("Formato de producto desconocido: " + lineaProducto);
            }
        } catch (Exception e) {
            System.err.println("Error procesando el producto: " + lineaProducto + " - " + e.getMessage());
            return null;
        }
    }

    private static String obtenerValor(String linea, String clave) {
        String[] partes = linea.split(", ");
        for (String parte : partes) {
            if (parte.startsWith(clave + "=")) {
                return parte.split("=")[1];
            }
        }
        return "";
    }
    
    public double  carritoTotal(String Archivo) {
    	double total = 0;
    	for (Producto p:productos) {
    		total += p.getPrecioUnitario();
			}
		return total;
    	
    }
    
    public void confirmarCarrito() {
        if (this.estado.equalsIgnoreCase("nuevo")) {
            this.estado = "Confirmado";
        }
    }
    
    public void cancelarCarrito() {
        if (this.estado.equalsIgnoreCase("nuevo")) {
            this.estado = "Cancelado";
        }
    }
    
    public void cambiarEstadoAConfirmado() {
        if (this.estado.equalsIgnoreCase("nuevo") || this.estado.equalsIgnoreCase("pendiente")) {
            this.estado = "Confirmado";
            System.out.println("El estado del carrito ha sido cambiado a 'Confirmado'.");
            actualizarArchivo();
        } else {
            System.out.println("El carrito no se puede confirmar porque su estado actual es: " + this.estado);
        }
    }

    private void actualizarArchivo() {
        File archivoOriginal = new File(Archivo.archivoCarrito);
        File archivoTemporal = new File(Archivo.archivoCarrito + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", 3);
                if (partes.length >= 3 && partes[0].trim().equals(this.idUsuario)) {
                    partes[2] = "Confirmado";  
                    linea = partes[0] + ";" + partes[1] + ";" + partes[2];
                }
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo: " + e.getMessage());
        }

        if (archivoOriginal.delete()) {
            archivoTemporal.renameTo(archivoOriginal);
        } else {
            System.out.println("Error al reemplazar el archivo original.");
        }
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Carrito de " + idUsuario + " (" + estado + "):\n");
        for (Producto producto : productos) {
            sb.append(producto.toString()).append("\n");
        }
        return sb.toString();
    }
    

    
    
}
