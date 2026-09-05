package Java.Profesiones;
import Java.Persona.Persona;
public class Cocinero extends Persona{
    private int platosPreparados;

    public Cocinero(String name, byte age, int platosPreparados){
        super(name, age);
        this.platosPreparados = platosPreparados;
    }

    public int getPlatosPreparados(){
        return this.platosPreparados;
    }

    public void setPlatosPreparados(int platosPreparados){
        this.platosPreparados = platosPreparados;
    }

    public void cocinar(){
        this.platosPreparados++;
        System.out.println("Cocinando un plato. Total de platos preparados: " + this.platosPreparados);
    }
}