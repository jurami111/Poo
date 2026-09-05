package Java.Poderes;
import Java.Power.Ipower;
public class powerRayoLaser implements Ipower{

    public powerRayoLaser() {
        System.out.println("Activando poder rayo laser!");
    }

    @Override
    public void dispararPoder() {
        System.out.println("Disparando rayo laser!");
    }
}