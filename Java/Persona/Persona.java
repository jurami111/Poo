//Nombre de la clase en Mayuscula
//Argumentos en minuscula y, si son varias palabras, se usa la letra mayuscula en cada palabra
package Java.Persona;
import Java.Power.Ipower;
public class Persona {
    protected String name;
    protected byte age;
    protected Ipower power;
    //Pubic signigica que cualquier archivo puede acceder a este metodo
    // Si fuera private, solo la clase Persona puede acceder a este metodo

    //Void: Es lo que retorna el metodo, aca como no hay return, se pone void
    //Si retornara un int, seria Pubic int getData(String name, byte age){
    //}
    //Y asi con los demas tipos de data

    public void printData(){
        System.out.println("Nombre:" + this.name + " Edad:" + this.age + "\n");
    }

    //Constructor que no recibe data
    public Persona(){
        this.name = "Julian";
        this.age = 19;
    }

    //Constructor que recibe data
    public Persona (byte pAge, String pName){
        this();
        this.name = pName;
        this.age = pAge;
    }

        public Persona(String pName, byte pAge){
        this.name = pName;
        this.age = pAge;
    }

    //Constructor de un solo parametro
    public Persona (String pName){
        this.name = pName;
        this.age = 0;
    }


    public String getName(){
        return this.name;
    }

    public byte getAge(){
        return this.age;
    }

    public void setName(String pName){
        this.name = pName;
    }

    public void setAge(byte pAge){
        this.age = pAge;
    }

    public void cantar(){
        System.out.println("Golden moon, diamond stars\nIn a moment, you and I\nSecond chance, shouganai\nMousukoshi matte, neowa naege hyanghagе\n");
        System.out.println("New Jeans - Supernatural\n");
    }

    public void setPower(Ipower pPower){
        this.power = pPower;
    }

    public void atacar(){
        if (this.power != null) {
            this.power.dispararPoder();
        } else {
            System.out.println("No tiene un poder asignado.");
        }
    }
}




