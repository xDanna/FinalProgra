public class Usuario {
    protected String nombre;
    protected String contra;
    protected boolean tipo; 
    
    public Usuario(String nombre, String contra, boolean tipo) {
        this.nombre = nombre;
        this.contra = contra;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Usuario [nombre=" + nombre + ", contra=" + contra + ", tipo=" + tipo + "]";
    }
}
