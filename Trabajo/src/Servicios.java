import java.util.*;

public class Servicios {

    private HashMap<String, Paquete> paquetesPorCodigo = new HashMap<>();
    private Map<Boolean, List<Paquete>> paquetesPorAlimento;
    private List<Camion> camiones;


    //Costo computacional CSVReader.leerCamiones = O(n) donde n es cant. de camiones
    //Costo computacional CSVReader.leerPaquetes = O(m) donde m es cant. de paquetes
    //Costo computacional del constructor = O(n) + O(m) + O(m) = O(n + m)

    public Servicios(String pathCamiones, String pathPaquetes) {
        paquetesPorAlimento = new HashMap<>();
        paquetesPorAlimento.put(true, new ArrayList<>());
        paquetesPorAlimento.put(false, new ArrayList<>());

        camiones = CSVReader.leerCamiones(pathCamiones);

        List<Paquete> paquetesLeidos = CSVReader.leerPaquetes(pathPaquetes);
        for (Paquete p : paquetesLeidos) {
            paquetesPorCodigo.put(p.getCodigoPaquete(), p);
            paquetesPorAlimento.get(p.contieneAlimentos()).add(p);
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

    public List<Camion> getCamiones() {
        return camiones;
    }
}