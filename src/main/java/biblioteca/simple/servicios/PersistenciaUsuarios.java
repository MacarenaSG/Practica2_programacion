package biblioteca.simple.servicios;

import biblioteca.simple.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Clase de ayuda para guardar y cargar usuarios en un archivo JSON
public class PersistenciaUsuarios {

    // Nombre del archivo donde guardaremos los usuarios
    private static final String FICHERO = "usuarios.json";

    // Objeto Gson para convertir entre Java y JSON
    private static final Gson gson = new Gson();
    //Para que no eaparezcan los usuarios en la misma linea

    // EXPORTAR: guarda la lista de usuarios en usuarios.json
    public static void exportar(List<Usuario> usuarios) throws Exception {
        try (FileWriter fw = new FileWriter(FICHERO)) {
            gson.toJson(usuarios, fw);
        }
    }

    // IMPORTAR: lee usuarios.json y devuelve la lista de usuarios
    public static List<Usuario> importar() throws Exception {
        try (FileReader fr = new FileReader(FICHERO)) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>(){}.getType();
            List<Usuario> usuarios = gson.fromJson(fr, tipoLista);
            if (usuarios == null) {
                // Si el archivo existe pero está vacío o mal, devolvemos lista vacía
                return new ArrayList<>();
            }
            return usuarios;
        }
    }
}
