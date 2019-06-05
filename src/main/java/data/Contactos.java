package data;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Contactos implements Serializable {

    private String nombreApellido, correo, direccion;
    private long numeroTel;

    public Contactos() {
    }

    public Contactos(String nombreApellido, long numeroTel, String correo, String direccion) {
        this.nombreApellido = nombreApellido;
        this.numeroTel = numeroTel;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(long numeroTel) {
        this.numeroTel = numeroTel;
    }

    @Override
    public String toString() {
        return nombreApellido + ", " + numeroTel + ", " + correo + ", " + direccion;
    }
}