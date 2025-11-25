package biblioteca.simple.contratos;

import biblioteca.simple.modelo.Usuario;

// La interfaz Prestable define el comportamiento que deben tener
// los objetos que pueden ser prestados (como libros, películas, etc.)
public interface Prestable {

    // Método para prestar el objeto a un usuario.
    // Cada clase que implemente esta interfaz decidirá cómo gestionarlo internamente.
    void prestar(Usuario u);

    // Método para devolver el objeto prestado.
    //void debe dejar el objeto como "no prestado".
    void devolver();

    // Método que indica si el objeto está actualmente prestado o no.
    boolean estaPrestado();
}
