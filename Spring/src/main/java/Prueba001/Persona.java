package Prueba001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Persona {
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Prueba001.Direccion getDireccion() {
        return direccion;
    }


    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", direccion=" + direccion +
                '}';
    }

    public void setDireccion(Prueba001.Direccion direccion) {
        this.direccion = direccion;
    }

    private String nombre;

    @Autowired
    private Prueba001.Direccion direccion;

}
