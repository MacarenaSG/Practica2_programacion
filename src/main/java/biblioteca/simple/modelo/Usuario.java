package biblioteca.simple.modelo;

// Clase que representa a un usuario de la biblioteca (persona que puede pedir materiales)
public class Usuario {
    // Atributos privados (encapsulación):
    private int id;         // Identificador único del usuario
    private String nombre;  // Nombre del usuario

    // Constructor que recibe solo el nombre.
    // Útil si el ID lo generamos después (por ejemplo, automáticamente).
    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    // Constructor que recibe el id y el nombre.
    // Útil cuando ya conocemos ambos datos al crear el usuario.
    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getter para el ID (no hay setter, por lo que no se modifica después de asignarse)
    public int getId() {
        return id;
    }

    // Getter para leer el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Setter para cambiar el nombre del usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
