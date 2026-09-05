package Java.Profesiones;
import Java.Persona.Persona;
public class Teacher extends Persona {

    private String subject;

    public Teacher(String name, byte age, String subject){
        super(name,age);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void teach(){
        System.out.println("Teaching " + subject + "!");
    }
}