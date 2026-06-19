import java.util.*;

public class Greedy {

    private int candidatosConsiderados;
    private Map<Camion, List<Paquete>> asignacion;
    private List<Paquete> noAsignados;

    /*
     * Estrategia de resolucion: se ordenan los paquetes por peso descendente,
     * priorizando asignar primero los paquetes mas pesados (la idea es que
     * son los mas dificiles de ubicar mas adelante si quedan camiones con
     * poca capacidad libre). Luego, para cada paquete en ese orden, se
     * recorren los camiones en el orden en que fueron cargados y se asigna
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

        HashMap<Camion, Double> capacidadRestante = new HashMap<>();
        for (Camion c : camiones) {
            capacidadRestante.put(c, c.getCapacidadKg());
        }

        List<Paquete> paquetesOrdenados = new ArrayList<>(paquetes);
        paquetesOrdenados.sort((p1, p2) -> Double.compare(p2.getPesoKg(), p1.getPesoKg()));

        for (Paquete paquete : paquetesOrdenados) {
            Camion camionElegido = null;

            for (Camion camion : camiones) {
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