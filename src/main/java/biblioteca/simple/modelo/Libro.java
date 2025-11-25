package biblioteca.simple.modelo;

import biblioteca.simple.contratos.Prestable;

// La clase Libro hereda de Producto (título, id, año, formato)
// e implementa la interfaz Prestable (prestar, devolver, estaPrestado)
public class Libro extends Producto implements Prestable {

    // Atributos específicos de un Libro
    private String ISBN;
    private String autor;

    // Control del estado de préstamo
    private boolean prestado;      // Indica si el libro está prestado o no
    private Usuario prestadoA;     // Usuario al que se le ha prestado el libro

    // Constructor para objetos que ya existen en la base de datos
    public Libro(int id, String titulo, String anho, Formato formato, String ISBN, String autor) {
        // Llama al constructor de Producto que recibe id
        super(id, titulo, anho, formato);
        this.ISBN = ISBN;
        this.autor = autor;
    }

    // Constructor para crear libros nuevos (sin id inicial)
    public Libro(String titulo, String anho, Formato formato, String ISBN, String autor) {
        // Llama al constructor de Producto que NO tiene id
        super(titulo, anho, formato);
        this.ISBN = ISBN;
        this.autor = autor;
    }

    // Getters para obtener información específica del libro
    public String getISBN() {
        return ISBN;
    }

    public String getAutor() {
        return autor;
    }

    // Implementación del método de la interfaz Prestable
    @Override
    public void prestar(Usuario u) {
        // No se puede prestar si ya está prestado
        if (prestado) throw new IllegalStateException("Ya prestado");

        this.prestado = true;
        this.prestadoA = u;
    }

    // Devuelve el libro (lo marca como no prestado)
    @Override
    public void devolver() {
        this.prestado = false;
        this.prestadoA = null;
    }

    // Comprueba si está prestado
    @Override
    public boolean estaPrestado() {
        return this.prestado;
    }

    // Representación en texto útil para impresión o depuración
    @Override
    public String toString() {
        return "Libro{" +
                "ISBN='" + ISBN + '\'' +
                ", autor='" + autor + '\'' +
                ", id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anho='" + anho + '\'' +
                ", formato=" + formato +
                '}';
    }
}
