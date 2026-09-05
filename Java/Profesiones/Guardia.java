package Java.Profesiones;
import Java.Persona.Persona;
public class Guardia extends Persona{

    private int camarasDisponibles;

    public Guardia(String name, byte age, int camarasDisponibles){
        super(name,age);
        this.camarasDisponibles = 6;
    }

    public int getCamaras(){
        return this.camarasDisponibles;
    }

    public void setCamaras(int camarasDisponibles){
        this.camarasDisponibles = camarasDisponibles;
    }

    public void usarCamaras(){
        for (int i = 0; i < this.camarasDisponibles; i++) {
            System.out.println("Usando cámara " + (i + 1));
            int peligro = (int) (Math.random() * 10); // Genera un número aleatorio entre 0 y 9
            if (peligro < 5) {
                System.out.println("No hay peligro");
            } else {
                System.out.println("¡Peligro detectado!");
            }
        }
    }
}