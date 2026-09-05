package Java.Profesiones;
import Java.Persona.Persona;
public class Stockholder extends Persona {

    private int AmmountOfStocks;

    public Stockholder(String name, byte age, int ammountOfStocks){
        super(name,age);
        this.AmmountOfStocks = ammountOfStocks;
    }

    public int getAmmountOfStocks() {
        return AmmountOfStocks;
    }

    public void setAmmountOfStocks(int ammountOfStocks){
        this.AmmountOfStocks = ammountOfStocks;
    }

    public void Buy(int buy){
        this.AmmountOfStocks += buy;
        System.out.println("Compro " + buy + " stocks. Ahora tiene: " + this.AmmountOfStocks);
    }

    public void Sell(int sell){
        if (sell > AmmountOfStocks){
            System.out.println("No se puede vender más stocks de los que se poseen.");
            return;
        }
        this.AmmountOfStocks -= sell;
        System.out.println("Vendido " + sell + " stocks. Ahora tiene: " + this.AmmountOfStocks);
    }
}