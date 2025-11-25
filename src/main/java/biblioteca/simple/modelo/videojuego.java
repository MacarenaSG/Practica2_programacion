package biblioteca.simple.modelo;

import biblioteca.simple.contratos.Prestable;

public class Videojuego extends Producto implements Prestable {

    //ATRIBUTOS PROPIOS DEL VIDEOJUEGO
    private String plataforma;   // Ej: PS5, Xbox, PC...
    private int edadMinima;      // PEGI o edad recomendada

    //Control de préstamo
    private boolean prestado = false;
    private Usuario prestadoA;

    //CONSTRUCTOR CON ID (igual que Libro/Pelicula)
    public Videojuego(int id, String titulo, String anho, Formato formato,
                      String plataforma, int edadMinima) {
        super(id, titulo, anho, formato);
        this.plataforma = plataforma;
        this.edadMinima = edadMinima;
    }

    //CONSTRUCTOR SIN ID (por si se crean desde app)
    public Videojuego(String titulo, String anho, Formato formato,
                      String plataforma, int edadMinima) {
        super(titulo, anho, formato);
        this.plataforma = plataforma;
        this.edadMinima = edadMinima;
    }

    //GETTERS
    public String getPlataforma() {
        return plataforma;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    //MÉTODOS DE PRESTABLE
    @Override
    public void prestar(Usuario u) {
        if (prestado) throw new IllegalStateException("El videojuego ya está prestado");
        prestado = true;
        prestadoA = u;
    }

    @Override
    public void devolver() {
        prestado = false;
        prestadoA = null;
    }

    @Override
    public boolean estaPrestado() {
        return prestado;
    }

    //toString()
    @Override
    public String toString() {
        return "Videojuego{" +
                "plataforma='" + plataforma + '\'' +
                ", edadMinima=" + edadMinima +
                ", id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anho='" + anho + '\'' +
                ", formato=" + formato +
                ", prestado=" + prestado +
                '}';
    }
}
