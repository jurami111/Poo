import Java.Persona.Persona;
import Java.Power.Ipower;
import Java.Poderes.powerDisparos;
import Java.Poderes.powerRayoLaser;
import Java.Poderes.powerRobar;
import Java.Poderes.powerSableLazer;
import Java.Poderes.powerEspada;
import Java.Profesiones.Cocinero;
import Java.Profesiones.Guardia;
import Java.Profesiones.Stockholder;
import Java.Profesiones.Teacher;
import Java.Profesiones.Vendor;

public class quickstart {
    public static void main(String[] args) {
        System.out.println("Hello, World! \n");
    
        Persona persona = new Persona();
        persona.printData();
        String n1 = persona.getName(); //n1 almacena el resultado de persona.getName

        Persona persona2 = new Persona((byte) 25, "Carlos");
        persona2.printData();
        String n2 = persona2.getName(); //n2 almacena el resultado de persona2.getName

        System.out.println("Nombre 1: " + n1 + "\nNombre 2: " + n2 + "\n"); //Imprime los nombres de las dos personas
        persona.cantar();

        Persona persona3 = persona2; //Persona3 es una referencia a persona2, no una copia de persona2
        persona3.printData(); //Siempre va a imprimir lo mismo que persona2

        persona2.setName("Juan");
        persona2.setAge((byte) 30);
        persona2.printData();

        Stockholder stockholder1 = new Stockholder("Alice", (byte) 10, 100);
        stockholder1.printData();
        stockholder1.cantar();
        stockholder1.Sell(50);
        stockholder1.Buy(50);

        Persona profesionales[] = new Persona[10];
        Ipower poderesDisponibles[] = {new powerDisparos(), new powerRayoLaser(), new powerRobar(), new powerSableLazer(), new powerEspada()};

        for (int i = 0; i < 10; i++) {
            int tipoProfesion = (int)(Math.random()*5);
            switch (tipoProfesion) {
                case 0:
                    profesionales[i] = new Teacher("Teacher "+i,(byte) 40, "Ciencias");
                    break;
                case 1:
                    profesionales[i] = new Stockholder("Stockholder "+i, (byte)  55, 1030);
                    break;
                case 2:
                    profesionales[i] = new Vendor("Vendedor "+i, (byte)  10 , "Producto"+i);
                    break;
                case 3:
                    profesionales[i] = new Cocinero("Cocinero "+i,(byte) 35, 10);
                    break;
                case 4:
                    profesionales[i] = new Guardia("Guardia "+i,(byte) 45, 6);
                    break;
                default:
                    profesionales[i] = new Teacher("Default "+i,(byte) 40, "Mate");
            }
            profesionales[i].setPower(poderesDisponibles[(int)(Math.random()*5)]);
            profesionales[i].printData();
        }

        for(Persona p : profesionales) {
            System.out.println("Ataca " + p.getName());
            p.atacar();
            System.out.println("\n--------------------\n");
        }

    }
}