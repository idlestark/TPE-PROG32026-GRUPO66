import java.util.*;

public class Backtracking {
    private HashMap<Camion, List<Paquete>> MejorSolucion = new HashMap<>();
    private double mejorPesoNoAsignado;
    private int estadosGenerados;

    private List<Camion> camiones;
    private List<Paquete> paquetes;


    /*
     * Estrategia de resolucion: se exploran todas las combinaciones posibles
     * de asignacion de paquetes a camiones mediante recursion. Para cada
     * camion se prueban todas las asignaciones validas de paquetes (que
     * cumplan las restricciones de capacidad y refrigeracion), y tambien
     * la opcion de no asignar un paquete a ese camion. Al llegar al ultimo
     * camion se evalua el peso total sin asignar y se actualiza la mejor
     * solucion encontrada. Al recorrerel espacio de soluciones,
     * garantiza encontrar la asignacion optima que minimiza el peso
     * no asignado. Cada llamada recursiva cuenta como un estado generado.
     */


    public Backtracking(HashMap<Camion, List<Paquete>> mejorSolucion, double mejorPesoNoAsignado, int estadosGenerados, List<Camion> camiones, List<Paquete> paquetes) {
        MejorSolucion = new HashMap<>();
        this.mejorPesoNoAsignado = mejorPesoNoAsignado;
        this.estadosGenerados = estadosGenerados;
        this.camiones = camiones;
        this.paquetes = paquetes;
    }


    public void asignarPaquetes(List<Camion> camiones, List<Paquete> paquetes) {
        if (!camiones.isEmpty() && !paquetes.isEmpty()) {
            HashMap<Camion, List<Paquete>> asignacion = new HashMap<>();
            for (Camion c : this.camiones) {
                asignacion.put(c, new ArrayList<>());
            }
            this.mejorPesoNoAsignado = this.paquetes.stream().mapToDouble(Paquete::getPesoKg).sum();
            backtracking(0, 0, asignacion, 0);
        }
    }

    private void backtracking(int camionActual, double pesoNoAsignado, HashMap<Camion, List<Paquete>> asignacion, int estados) {
        if (camionActual == camiones.size()) {
            double pesoSinAsignar = 0;
            for (Paquete p : this.paquetes) {
                boolean asignado = false;
                for (List<Paquete> lista : asignacion.values()) {
                    if (lista.contains(p)) {
                        asignado = true;
                        break;
                    }
                }
                if (!asignado) {
                    pesoSinAsignar += p.getPesoKg();
                }
            }
            if (pesoSinAsignar < mejorPesoNoAsignado) {
                mejorPesoNoAsignado = pesoSinAsignar;
                MejorSolucion = copiarAsignacion(asignacion);
                this.estadosGenerados = estados;
            }
        } else {
            for (Paquete p : this.paquetes) {
                boolean yaEstaAsignado = asignacion.values().stream().anyMatch(lista -> lista.contains(p));

                if (!yaEstaAsignado) {
                    if (!p.contieneAlimentos() || this.camiones.get(camionActual).estaRefrigerado()) {
                        if (getPeso(camionActual, asignacion) + p.getPesoKg() <= this.camiones.get(camionActual).getCapacidadKg()) {
                            asignacion.get(this.camiones.get(camionActual)).add(p);
                            backtracking(camionActual, pesoNoAsignado + p.getPesoKg(), asignacion, estados + 1);
                            asignacion.get(this.camiones.get(camionActual)).remove(p);
                        }
                    }
                }
            }
            backtracking(camionActual + 1, pesoNoAsignado, asignacion, estados + 1);
        }
    }

    private double getPeso(int indiceCamion, HashMap<Camion, List<Paquete>> asignacion) {
        List<Paquete> paquetesDelCamion = asignacion.get(this.camiones.get(indiceCamion));
        if (paquetesDelCamion == null) return 0;
        double total = 0;
        for (Paquete p : paquetesDelCamion) {
            total += p.getPesoKg();
        }
        return total;
    }

    private HashMap<Camion, List<Paquete>> copiarAsignacion(HashMap<Camion, List<Paquete>> asignacion) {
        HashMap<Camion, List<Paquete>> copia = new HashMap<>();
        for (Map.Entry<Camion, List<Paquete>> entry : asignacion.entrySet()) {
            copia.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copia;
    }

    public HashMap<Camion, List<Paquete>> getMejorSolucion() { return MejorSolucion; }
    public double getMejorPesoNoAsignado() { return mejorPesoNoAsignado; }
    public int getEstadosGenerados() { return estadosGenerados; }
}