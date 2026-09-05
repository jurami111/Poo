package Java.Profesiones;
import Java.Persona.Persona;
public class Vendor extends Persona{

    private String product;

    public Vendor(String name, byte age, String product){
        super(name,age);
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product){
        this.product = product;
    }

    public void sell(){
        System.out.println("Selling " + product + "!");
    }
}