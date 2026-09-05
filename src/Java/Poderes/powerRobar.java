package Java.Poderes;
import Java.Power.Ipower;
public class powerRobar implements Ipower{

    public powerRobar() {
        System.out.println("Activando poder robar!");
    }

    @Override
    public void dispararPoder() {
        System.out.println("Usando poder robar!");
    }
}