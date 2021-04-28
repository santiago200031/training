package Prueba001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractXmlApplicationContext;


@ComponentScan
public class App {
    public static void main(String[] args) {
        //ApplicationContext context = AbstractXmlApplicationContext;
        Persona p = new Persona();
        p.setNombre("Juan");
        System.out.println(p);
    }
}
