package biblioteca.simple.modelo;

// Clase abstracta que representa un producto general dentro de la biblioteca,
// como puede ser un libro, una película, un videojuego, etc.
// Al ser "abstracta", no puede instanciarse directamente (no puedes hacer new Producto()).
// Su función es servir como base para que otras clases hereden sus atributos y comportamiento.
public abstract class Producto {

    // --- ATRIBUTOS COMUNES ---
    // Se declaran como 'protected' para que las subclases (por ejemplo Libro o Pelicula)
    // puedan acceder directamente a ellos, pero sin exponerlos al exterior (no son public).

    protected int id;          // Identificador único del producto (usualmente asignado por una base de datos)
    protected String titulo;   // Título del producto, por ejemplo "El Quijote" o "Inception"
    protected String anho;     // Año de publicación, edición o lanzamiento (como texto por simplicidad)
    protected Formato formato; // Formato del producto: puede ser FISICO o DIGITAL (según el enum Formato)

    // --- CONSTRUCTORES ---

    // Constructor que recibe todos los atributos, incluido el id.
    // Se usa cuando el producto ya existe (por ejemplo, al importarlo de una base de datos o de un archivo).
    protected Producto(int id, String titulo, String anho, Formato formato) {
        this.id = id;             // Asigna el identificador existente
        this.titulo = titulo;     // Asigna el título del producto
        this.anho = anho;         // Asigna el año de publicación/lanzamiento
        this.formato = formato;   // Asigna el formato (FISICO o DIGITAL)
    }

    // Constructor alternativo pensado para crear un producto nuevo desde cero (sin id todavía).
    // Útil cuando el id será generado automáticamente (por ejemplo, al guardar en base de datos).
    protected Producto(String titulo, String anho, Formato formato) {
        this.titulo = titulo;
        this.anho = anho;
        this.formato = formato;
        // 'id' no se asigna aquí porque aún no existe.
    }

    // --- MÉTODOS GETTERS ---
    // Permiten acceder a los valores de los atributos de forma controlada.
    // En este caso se han hecho 'public' o 'protected' según necesidad:

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAnho() {
        return anho;
    }

    // Este getter es 'protected' porque probablemente no se necesite fuera del paquete o jerarquía.
    protected Formato getFormato() {
        return formato;
    }

    // --- MÉTODO toString() ---
    // Sobrescribe el método estándar de Object para devolver una representación textual del producto.
    // Es útil al imprimir en consola o al depurar para ver los valores de sus atributos.
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anho='" + anho + '\'' +
                ", formato=" + formato +
                '}';
    }
}
