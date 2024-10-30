import com.google.gson.Gson;

public class GsonExample {
    public static void main(String[] args) {
        // Creamos un objeto Java
        Person person = new Person("John", 30, "john@example.com");

        // Creamos una instancia de Gson
        Gson gson = new Gson();

        // Convertimos el objeto Java a JSON
        String json = gson.toJson(person);
        System.out.println("Objeto convertido a JSON:");
        System.out.println(json);

        // Convertimos JSON a objeto Java
        String json2 = "{\"name\":\"Alice\",\"age\":25,\"email\":\"alice@example.com\"}";
        Person person2 = gson.fromJson(json2, Person.class);
        System.out.println("\nJSON convertido a objeto:");
        System.out.println("Nombre: " + person2.getName());
        System.out.println("Edad: " + person2.getAge());
        System.out.println("Correo electrónico: " + person2.getEmail());
    }
}

// Clase de ejemplo
class Person {
    private String name;
    private int age;
    private String email;

    // Constructor, getters y setters
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
