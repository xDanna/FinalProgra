import java.util.*;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public  class Inventario {
 
	public boolean escribirInventario(Producto producto, String archivo) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
	        writer.write(producto.toString());
	        writer.newLine(); 
	    } catch (IOException e) {
	       
	        System.err.println("Error al escribir el archivo");
	        return false;
	    }
	    return true;
	}

	 
	 
	    public String obtenerValor(String linea, String atributo) {
	        int inicio = linea.indexOf(atributo + "=");
	        if (inicio == -1) return "";
	        inicio += atributo.length() + 1; 
	        int fin = linea.indexOf(",", inicio);
	        if (fin == -1) fin = linea.indexOf("]", inicio);

	        return linea.substring(inicio, fin).trim();
	    }
	    public String[] parsearSticker(String linea) {
	        String[] datos = new String[7];
	        datos[0] = obtenerValor(linea, "nombre");
	        datos[1] = "Sticker";
	        datos[2] = obtenerValor(linea, "precioUnitario");
	        datos[3] = obtenerValor(linea, "cantidad");
	        datos[4] = obtenerValor(linea, "tamaño");
	        datos[5] = obtenerValor(linea, "papel");
	        datos[6] = obtenerValor(linea, "film");
	        return datos;
	    }
	   
	    public String[] parsearPrint(String linea) {
	        String[] datos = new String[7];
	        datos[0] = obtenerValor(linea, "nombre");
	        datos[1] = "Print";
	        datos[2] = obtenerValor(linea, "precioUnitario");
	        datos[3] = obtenerValor(linea, "cantidad");
	        datos[4] = obtenerValor(linea, "tamaño");
	        datos[5] = obtenerValor(linea, "papel");
	        datos[6] = ""; 
	        return datos;
	    }

	    public String[] parsearPin(String linea) {
	        String[] datos = new String[7];
	        datos[0] = obtenerValor(linea, "nombre");
	        datos[1] = "Pin";
	        datos[2] = obtenerValor(linea, "precioUnitario");
	        datos[3] = obtenerValor(linea, "cantidad");
	        datos[4] = obtenerValor(linea, "tamaño");
	        datos[5] = ""; 
	        datos[6] = ""; 
	        return datos;
	    }
	    
	    public ArrayList<Producto> leerInventario(String archivo) {
	        ArrayList<Producto> inventario = new ArrayList<Producto>();
	        String linea;
	        
	        try {
	            BufferedReader lector = new BufferedReader(new FileReader(archivo));
	            
	            while ((linea = lector.readLine()) != null) {
	                
	                System.out.println("Leyendo línea: " + linea);
	                
	                String nombre = obtenerValor(linea, "nombre");
	                String tipo = obtenerValor(linea, "tipo").trim();  
	                
	                System.out.println("Tipo obtenido: '" + tipo + "'");
	                
	                double precio = Double.parseDouble(obtenerValor(linea, "precioUnitario"));
	                int cantidad = Integer.parseInt(obtenerValor(linea, "cantidad"));
	                System.out.println(precio);
	                System.out.println(cantidad);
	                
	                Producto producto = null;

	               
	                switch (tipo) {
	                    case "Sticker":
	                        String papelSticker = obtenerValor(linea, "papel");
	                        boolean filmSticker = obtenerValor(linea, "film").equalsIgnoreCase("Si");
	                        double tamañoSticker = Double.parseDouble(obtenerValor(linea, "tamaño"));
	                        producto = new Sticker(nombre, tipo, precio, cantidad, papelSticker, filmSticker, tamañoSticker);
	                        break;
	                        
	                    case "Print":
	                        String papelPrint = obtenerValor(linea, "papel");
	                        String tamañoPrint = obtenerValor(linea, "tamaño");
	                        producto = new Print(nombre, tipo, precio, cantidad, papelPrint, tamañoPrint);
	                        break;
	                        
	                    case "Pin":
	                        int tamañoPin = Integer.parseInt(obtenerValor(linea, "tamaño"));
	                        producto = new Pin(nombre, tipo, precio, cantidad, tamañoPin);
	                        break;
	                        
	                    default:
	                        System.out.println("Tipo de producto desconocido: " + tipo);
	                        break;
	                }

	              
	                if (producto != null) {
	                    inventario.add(producto);
	                }
	            }

	            lector.close();
	        } catch (Exception e) {
	            System.out.println("No se pudo leer el archivo.");
	            e.printStackTrace();
	        }

	        return inventario;
	    }

	    
	    public boolean sobreescribirArchivo(ArrayList<Producto> listaProductos, String archivo) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
	            for (Producto producto : listaProductos) {
	                writer.write(producto.toString());
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            System.err.println("Error al sobrescribir el archivo");
	            return false;
	        }
	        return true;
	    }
	    
	 
	    public double obtenerValorPorTipo(ArrayList<Producto> inventario, String tipoProducto) {
	        double valorTotalTipo = 0.0;
	        for (Producto producto : inventario) {
	            if (producto.tipo.equalsIgnoreCase(tipoProducto)) {
	                double valorProducto = producto.cantidad * producto.precioUnitario;
	                valorTotalTipo += valorProducto;
	            }
	        }
	        
	        return valorTotalTipo;
	    }

	  
	    public double obtenerValorTotalInventario(ArrayList<Producto> inventario) {
	        double valorTotalInventario = 0.0;
	        for (Producto producto : inventario) {
	            double valorProducto = producto.cantidad * producto.precioUnitario;
	            valorTotalInventario += valorProducto;
	        }
	        
	        return valorTotalInventario;
	    }
	    
	    public Producto buscarProductoPorNombre(String nombre, String archivo) {
	        ArrayList<Producto> inventario = leerInventario(archivo);
	        for (Producto producto : inventario) {
	            if (producto.getNombre().equalsIgnoreCase(nombre)) {
	                return producto;
	            }
	        }
	        return null; 
	    }
}