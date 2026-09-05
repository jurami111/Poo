
package Java.Poderes;
import Java.Power.Ipower;

public class powerDisparos implements Ipower{

    public void powerDisparos() {
        System.out.println("Activando poder disparos!");
    }

    @Override
    public void dispararPoder() {
        System.out.println("Disparando disparos!");
    }
}