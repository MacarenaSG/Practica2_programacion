package biblioteca.simple.app;

import biblioteca.simple.contratos.Prestable;
import biblioteca.simple.modelo.*;
import biblioteca.simple.servicios.Catalogo;

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

        usuarios.add(new Usuario(1, "Juan"));
        usuarios.add(new Usuario(2, "María"));

    }

    private static void menu(){

        int op;

        do {

            System.out.println("\n===Menú de Biblioteca===");
            System.out.println("1. Listar");
            System.out.println("2. Buscar por título");
            System.out.println("3. Buscar por año");
            System.out.println("4. Prestar Producto");
            System.out.println("5. Devolver Producto");
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
                case 0 -> System.out.println("Sayonara!");
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

        System.out.println("==Lista de productos ===");

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

    private static void prestar(){

        // 1)mostrar productos disponibles

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


                 listarUsuarios();

                 System.out.println("Ingresa código de usurio");

                 int cUsuario = sc.nextInt();
                 sc.nextLine();
                 Usuario u1 = getUsuarioPorCodigo(cUsuario);

                 if (u1 == null){
                     System.out.println("Usuari ono encontrado");
                 }

                 Prestable pPrestable = (Prestable) pEncontrado;
                 pPrestable.prestar(u1);

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



}
