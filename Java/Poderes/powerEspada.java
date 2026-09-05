package Java.Poderes;
import Java.Power.Ipower;
public class powerEspada implements Ipower{

    public powerEspada() {
        System.out.println("Activando poder usar Espada!");
    }

    @Override
    public void dispararPoder() {
        System.out.println("Usando Espada!");
    }
}