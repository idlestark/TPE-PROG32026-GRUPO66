import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Servicios {

    private HashMap<String, Paquete> paquetesPorCodigo = new HashMap<>();

    //private TreeMap<Integer, List<Paquete>> paquetesPorUrgencia = new TreeMap<>(); //preguntar si usar un árbol en servicio 3
    public Servicios(String pathCamiones, String pathPaquetes) {
        cargarCamiones(pathCamiones);
        cargarPaquetes(pathPaquetes);
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
                new Paquete(id, codigo, peso, alimentos, urgencia);
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
     * Complejidad O(n) debido a que se utiliza un bucle for para recorrer el arreglo de paquetes.
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        List<Paquete> paquetes = new ArrayList<>();
        for(Paquete p : paquetesPorCodigo.values()) {
            if(p.contieneAlimentos() == contieneAlimentos) {
                paquetes.add(p);
            }
        }
        return paquetes;
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