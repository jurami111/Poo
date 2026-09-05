package Java.Poderes;
import Java.Power.Ipower;
public class powerSableLazer implements Ipower{

    public powerSableLazer() {
        System.out.println("Activando poder usar Sable Lazer!");
    }

    @Override
    public void dispararPoder() {
        System.out.println("Usando Sable Lazer!");
    }
}