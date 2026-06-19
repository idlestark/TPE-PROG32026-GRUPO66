import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Servicios {

    private HashMap<String, Paquete> paquetesPorCodigo = new HashMap<>();
    private Map<Boolean, List<Paquete>> paquetesPorAlimento;

    //Costo computacional cargarCamiones = O(n) donde N es cant. de camiones y cargarPaquetes = O(m) donde M es cant. de paquetes
    //Costo computacional del constructor = O(n) + O(m) = O(n + m)
    public Servicios(String pathCamiones, String pathPaquetes, Map<Boolean, List<Paquete>> paquetesPorAlimento) {
        cargarCamiones(pathCamiones);
        cargarPaquetes(pathPaquetes);
        paquetesPorAlimento = new HashMap<>();
        paquetesPorAlimento.put(true, new ArrayList<>());
        paquetesPorAlimento.put(false, new ArrayList<>());
    }

    private void cargarCamiones(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int total = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < total; i++) {
                String[] partes = br.readLine().trim().split(";");
                int id = Integer.parseInt(partes[0]);
                String patente = partes[1];
                boolean refrigerado = partes[2].equals("1");
                double capacidad = Double.parseDouble(partes[3]);
                new Camion(id, patente, refrigerado, capacidad);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo camiones: " + e.getMessage());
        }
    }

    private void cargarPaquetes(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int total = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < total; i++) {
                String[] partes = br.readLine().trim().split(";");
                int id = Integer.parseInt(partes[0]);
                String codigo = partes[1];
                double peso = Double.parseDouble(partes[2]);
                boolean alimentos = partes[3].equals("1");
                int urgencia = Integer.parseInt(partes[4]);
                Paquete paquete = new Paquete(id, codigo, peso, alimentos, urgencia);
                //Ahora se carga la lista de factoreo
                paquetesPorCodigo.put(codigo, paquete);
                paquetesPorAlimento.get(alimentos).add(paquete);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo paquetes: " + e.getMessage());
        }
    }

    /*
     * Complejidad  O(1) debido a que es un acceso directo a un HashMap.
     */
    public Paquete servicio1(String codigoPaquete) {
        return paquetesPorCodigo.get(codigoPaquete);
    }



    /*
     * Complejidad O(m) en el peor caso (donde m es la cantidad total de
     * paquetes), ya que el acceso al HashMap es O(1) (solo 2 claves posibles),
     * pero copiar la lista resultado con new ArrayList<>() puede llegar a
     * recorrer hasta m elementos si todos los paquetes pertenecen al mismo
     * bucket (todos contienen alimentos o todos no contienen).
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        return new ArrayList<>(paquetesPorAlimento.get(contieneAlimentos));
    }



    /*
     * Complejidad O(n) debido a que se utiliza un bucle for para recorrer el arreglo de paquetes.
     */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> paquetes = new ArrayList<>();
        for(Paquete p : paquetesPorCodigo.values()) {
            if(p.getNivelUrgencia() >= urgenciaMinima && p.getNivelUrgencia() <= urgenciaMaxima) {
                paquetes.add(p);
            }
        }
        return paquetes;
    }
}