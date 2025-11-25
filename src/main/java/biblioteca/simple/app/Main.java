package biblioteca.simple.app;

import biblioteca.simple.contratos.Prestable;
import biblioteca.simple.modelo.*;
import biblioteca.simple.servicios.Catalogo;
import biblioteca.simple.servicios.PersistenciaUsuarios;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {


    private static final Catalogo catalogo = new Catalogo();

    private static final List<Usuario> usuarios =new ArrayList<>();


    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        cargarDatos();
        menu();
    }

    private static void cargarDatos(){
        catalogo.alta(new Libro(1, "El Quijote", "1608", Formato.FISICO, "25225", "Cervantes"));
        catalogo.alta(new Libro(2, "El nombre del viento", "2007", Formato.FISICO, "9788401352836", "Patrick Rothfuss"));
        catalogo.alta(new Pelicula(3, "El Padrino", "1972", Formato.FISICO, "rancis Ford Coppola", 175));
        catalogo.alta(new Pelicula(4, "Parásitos", "2019", Formato.FISICO, "Bong Joon-ho", 132));
        // Añadir los videojuegos
        catalogo.alta(new Videojuego(5, "Assassin's Creed II", "2009", Formato.FISICO, "Xbox", 18));
        catalogo.alta(new Videojuego(6, "The Legend of Zelda: Breath of the Wild", "2017", Formato.FISICO, "Switch", 12));

        usuarios.add(new Usuario(1, "Juan"));
        usuarios.add(new Usuario(2, "María"));

    }

    private static void menu(){

        int op;

        do {

            System.out.println("\n=== Menú de Biblioteca ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Buscar por título");
            System.out.println("3. Buscar por año");
            System.out.println("4. Prestar Producto");
            System.out.println("5. Devolver Producto");
            System.out.println("6. Listar usuarios");
            System.out.println("7. Crear nuevo usuario");
            System.out.println("8. Exportar usuarios");
            System.out.println("9. Importar usuarios");
            System.out.println("0. Salir");
            while(!sc.hasNextInt()) sc.next();
            op = sc.nextInt();

            sc.nextLine();

            switch (op){
                case 1 -> listar();
                case 2 -> buscarPorTitulo();
                case 3 -> buscarPorAnio();
                case 4 -> prestar();
                case 5 -> devolver();
                case 6 -> listarUsuarios();
                case 7 -> crearUsuario();
                case 8 -> exportarUsuarios();
                case 9 -> importarUsuarios();
                case 0 -> System.out.println("Hasta pronto!");
                default -> System.out.println("Opción no válida");
            }

        } while(op != 0);
    }

    private static void listar(){
        List<Producto> lista = catalogo.listar();

        if(lista.isEmpty()){
            System.out.println("Catálogo vacío");
            return;
        }

        System.out.println("=== Lista de productos ===");

        for(Producto p : lista) System.out.println("- " + p);


    }

    private static void buscarPorTitulo(){
        System.out.println("Título (escribe parte del título): ");
        String t = sc.nextLine();
        catalogo.buscar(t).forEach(p -> System.out.println("- " + p));
    }

    private static void buscarPorAnio(){
        System.out.println("Año: ");
        int a = sc.nextInt();
        sc.nextLine();
        catalogo.buscar(a).forEach(p -> System.out.println("- " + p));
    }

    private static void listarUsuarios(){
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
            return;
        }
        System.out.println("Lista usuarios");
        usuarios.forEach( u ->
                        System.out.println("- Código : " + u.getId() + "| Nombre: " + u.getNombre() )
        );
    }

    private static Usuario getUsuarioPorCodigo(int id){
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // CREAR NUEVO USUARIO MANUALMENTE (código + nombre)
    private static Usuario crearUsuario() {

        System.out.println("== Crear nuevo usuario ==");

        // Pedimos el código (id) del usuario
        System.out.print("Introduce el código (id) del usuario: ");
                while (!sc.hasNextInt()) sc.next();   // por si mete texto
        int id = sc.nextInt();
        sc.nextLine();

        // Comprobamos si ya existe un usuario con ese id
        Usuario existente = getUsuarioPorCodigo(id);
        if (existente != null) {
            System.out.println("Ya existe un usuario con ese código: " + existente.getNombre());
            return existente; // devolvemos el ya existente
        }

        // Pedimos el nombre
        System.out.print("Introduce el nombre del usuario: ");
        String nombre = sc.nextLine();

        // Creamos y añadimos el nuevo usuario
        Usuario nuevo = new Usuario(id, nombre);
        usuarios.add(nuevo);

        System.out.println("Usuario creado correctamente: " + nuevo.getNombre() + " (código " + nuevo.getId() + ")");

        return nuevo;
    }


    private static void prestar(){

        // 1) Mostrar productos disponibles (Prestable y NO prestados)
        List<Producto> disponibles = catalogo.listar().stream()
                .filter(p -> p instanceof Prestable pN && !pN.estaPrestado())
                .collect(Collectors.toList());

        if ( disponibles.isEmpty() ) {
            System.out.println("No hay productos para prestar");
            return;
        }

        System.out.println("-- PRODUCTOS DISPONIBLES --");
        disponibles.forEach( p -> System.out.println("- ID: " + p.getId() + " | " + p));

        System.out.println("Escribe el id del producto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Producto pEncontrado = disponibles.stream()
                .filter(p -> {
                    try {
                        return p.getId() == id;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);

        if (pEncontrado == null){
            System.out.println("El id no existe");
            return;
        }

        // 2) SELECCIÓN DE USUARIO
        Usuario u1 = null;

        // Bucle hasta que tengamos un usuario válido
        while (u1 == null) {

            listarUsuarios(); // mostramos lista actual
            System.out.println("Ingresa código de usuario (id): ");
            System.out.print("Sino eres ninguno de los de la lista teclea otro número para registrarte");
            while (!sc.hasNextInt()) sc.next();
            int cUsuario = sc.nextInt();
            sc.nextLine();

            u1 = getUsuarioPorCodigo(cUsuario);

            if (u1 == null) {
                System.out.println("Usuario no encontrado.");
                System.out.println("¿Quieres crear un nuevo usuario con ese código? (s/n)");
                String resp = sc.nextLine().trim().toLowerCase();

                if (resp.equals("s")) {
                    // Creamos el usuario nuevo y lo usamos en el préstamo
                    // aquí podrías reutilizar el id introducido si quisieras)
                    u1 = crearUsuario();
                } else {
                    System.out.println("Volvamos a intentarlo. Introduce un código de usuario válido.");
                    // u1 sigue siendo null → el while se repite
                }
            }
        }

        // 3) Realizar el préstamo
        Prestable pPrestable = (Prestable) pEncontrado;
        pPrestable.prestar(u1);

        System.out.println("Préstamo realizado correctamente.");
        System.out.println("Producto: " + pEncontrado);
        System.out.println("Usuario: " + u1.getNombre() + " (id " + u1.getId() + ")");
    }



    public static void devolver(){


        List<Producto> pPrestados = catalogo.listar().stream()
                .filter(p -> p instanceof Prestable pN && pN.estaPrestado())
                .collect(Collectors.toList());

        if ( pPrestados.isEmpty() ) {
            System.out.println("No hay productos para prestar");
            return;
        }

        System.out.println("-- PRODUCTOS PRESTADOS --");
        pPrestados.forEach( p -> System.out.println("- ID: " + p.getId() + " | " + p));

        System.out.println("Escribe el id del producto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Producto pEncontrado = pPrestados.stream()
                .filter(p -> {
                    try {
                        return p.getId() == id;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })

                .findFirst()
                .orElse(null);

        if (pEncontrado == null){
            System.out.println("El id no existe");
            return;
        }

        Prestable pE = (Prestable) pEncontrado;
        pE.devolver();
        System.out.println("Devuelto correctamente");

    }

    // EXPORTAR USUARIOS A JSON
    private static void exportarUsuarios() {
        try {
            PersistenciaUsuarios.exportar(usuarios);
            System.out.println("Usuarios exportados correctamente al archivo usuarios.json");
        } catch (Exception e) {
            System.out.println("Error al exportar usuarios: " + e.getMessage());
        }
    }

    // IMPORTAR USUARIOS DESDE JSON
    private static void importarUsuarios() {
        try {
            List<Usuario> cargados = PersistenciaUsuarios.importar();

            // Primero vaciamos la lista actual y luego metemos los cargados
            usuarios.clear();
            usuarios.addAll(cargados);

            System.out.println("Usuarios importados correctamente desde usuarios.json");
        } catch (Exception e) {
            System.out.println("Error al importar usuarios: " + e.getMessage());
        }
    }


}
