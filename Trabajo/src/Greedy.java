import java.util.*;

public class Greedy {

    private int candidatosConsiderados;
    private Map<Camion, List<Paquete>> asignacion;
    private List<Paquete> noAsignados;

    /*
     * Estrategia de resolucion: se ordenan los paquetes por peso descendente,
     * priorizando asignar primero los paquetes mas pesados (la idea es que
     * son los mas dificiles de ubicar mas adelante si quedan camiones con
     * poca capacidad libre). Tambien se ordenan los camiones dejando los
     * refrigerados al final, para reservarlos en lo posible solo para los
     * paquetes que efectivamente contienen alimentos, evitando que se
     * llenen con paquetes que no los necesitan y dejando sin lugar a
     * paquetes con alimentos mas adelante. Luego, para cada paquete en el
     * orden establecido, se recorren los camiones en ese orden y se asigna
     * al PRIMER camion que cumpla las dos restricciones: tener capacidad
     * libre suficiente y, si el paquete contiene alimentos, estar
     * refrigerado. Si ningun camion cumple, el paquete queda sin asignar. A
     * diferencia de Backtracking, la decision es local e inmediata (no se
     * reconsidera), por eso no garantiza la solucion optima, pero es mucho
     * mas rapida. Cada comparacion paquete-camion realizada para decidir si
     * un camion es apto se cuenta como un candidato considerado.
     */
    public Greedy() {
        this.asignacion = new HashMap<>();
        this.noAsignados = new ArrayList<>();
        this.candidatosConsiderados = 0;
    }

    public void greedy(List<Camion> camiones, List<Paquete> paquetes) {
        candidatosConsiderados = 0;
        asignacion = new HashMap<>();
        noAsignados = new ArrayList<>();


        List<Camion> camionesOrdenados = new ArrayList<>(camiones);
        camionesOrdenados.sort((c1, c2) -> Boolean.compare(c1.estaRefrigerado(), c2.estaRefrigerado()));

        HashMap<Camion, Double> capacidadRestante = new HashMap<>();
        for (Camion c : camionesOrdenados) {
            capacidadRestante.put(c, c.getCapacidadKg());
        }

        List<Paquete> paquetesOrdenados = new ArrayList<>(paquetes);
        paquetesOrdenados.sort((p1, p2) -> Double.compare(p2.getPesoKg(), p1.getPesoKg()));

        for (Paquete paquete : paquetesOrdenados) {
            Camion camionElegido = null;

            for (Camion camion : camionesOrdenados) {
                candidatosConsiderados++;

                boolean cabe = paquete.getPesoKg() <= capacidadRestante.get(camion);
                boolean cumpleRefrigeracion = !paquete.contieneAlimentos() || camion.estaRefrigerado();

                if (cabe && cumpleRefrigeracion) {
                    camionElegido = camion;
                    break;
                }
            }

            if (camionElegido != null) {
                List<Paquete> paquetesCamion = asignacion.get(camionElegido);
                if (paquetesCamion == null) {
                    paquetesCamion = new ArrayList<>();
                    asignacion.put(camionElegido, paquetesCamion);
                }
                paquetesCamion.add(paquete);
                capacidadRestante.put(camionElegido, capacidadRestante.get(camionElegido) - paquete.getPesoKg());
            } else {
                noAsignados.add(paquete);
            }
        }
    }

    public Map<Camion, List<Paquete>> getAsignacion() { return asignacion; }
    public List<Paquete> getNoAsignados() { return noAsignados; }
    public int getCandidatosConsiderados() { return candidatosConsiderados; }
    public double getPesoNoAsignado() {
        double total = 0;
        for (Paquete p : noAsignados) total += p.getPesoKg();
        return total;
    }
}