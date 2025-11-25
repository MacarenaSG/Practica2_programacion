package biblioteca.simple.servicios;

// Importamos la clase Producto, ya que el catálogo gestionará una lista de productos
import biblioteca.simple.modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector; // ← Import innecesario aquí (no se usa en el código actual)

// Clase que representa el catálogo general de la biblioteca.
// Se encarga de almacenar, buscar y listar los productos (libros, películas, etc.)
public class Catalogo {

    // --- ATRIBUTO PRINCIPAL ---
    // Lista que contiene todos los productos del catálogo.
    // Se declara como "final" porque la referencia no cambiará (aunque el contenido sí puede modificarse).
    private final List<Producto> productos = new ArrayList<>();


    // --- MÉTODO alta() ---
    // Añade un producto al catálogo.
    // Parámetro: 'p' es un objeto de tipo Producto (o una subclase como Libro o Pelicula).
    public void alta(Producto p) {
        productos.add(p); // Lo agrega al final de la lista
    }


    // --- MÉTODO listar() ---
    // Devuelve una copia de la lista de productos.
    // Se crea un nuevo ArrayList con los mismos elementos para evitar que se modifique la lista original desde fuera.
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }


    // --- MÉTODO buscar(String titulo) ---
    // Busca productos cuyo título contenga el texto indicado (ignorando mayúsculas/minúsculas).
    // Parámetro: 'titulo' es el texto a buscar.
    // Devuelve: una lista de productos que coinciden parcialmente con el título.
    public List<Producto> buscar(String titulo) {
        List<Producto> res = new ArrayList<>(); // Lista para guardar los resultados
        for (Producto p : productos) { // Recorre todos los productos del catálogo
            // Convierte ambos títulos a minúsculas para hacer la comparación insensible a mayúsculas
            if (p.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                res.add(p); // Si coincide, se agrega a la lista de resultados
            }
        }
        return res; // Devuelve la lista (vacía si no hay coincidencias)
    }


    // --- MÉTODO buscar(int anho) ---
    // Busca productos cuyo año de publicación coincida exactamente con el año indicado.
    // Convierte el año del producto (String) a entero para compararlo.
    public List<Producto> buscar(int anho) {
        List<Producto> res = new ArrayList<>();
        for (Producto p : productos) {
            // Convierte el atributo 'anho' de Producto a int y lo compara
            if (Integer.parseInt(p.getAnho()) == anho)
                res.add(p); // Si el año coincide, se añade a los resultados
        }
        return res;
    }
}
