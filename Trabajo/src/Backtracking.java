import java.util.*;



//Reemplazar con clase Carga para eliminar el getPeso(), agregar poda de si nuestra solucion actual tiene
// más peso en el camion
//de descarte que la mejor solucion


public class Backtracking {
    private HashMap<Camion, List<Paquete>> MejorSolucion = new HashMap<>();
    private double mejorPesoNoAsignado;
    private int estadosGenerados;


    private List<Camion> camiones;
    private List<Paquete> paquetes;
    private Camion camionDescarte;


    /*
     * Estrategia de resolucion: se exploran todas las combinaciones posibles
     * de asignacion de paquetes a camiones mediante recursion. Para cada
     * paquete se prueban todos los camiones validos (que cumplan las
     * restricciones de capacidad y refrigeracion), y tambien se incluye un
     * "camion de descarte" ficticio que representa la opcion de dejar el
     * paquete sin asignar. Al llegar al ultimo paquete se evalua el peso
     * total acumulado en el camion de descarte y se actualiza la mejor
     * solucion encontrada si es menor a la actual. Al recorrer todo el
     * espacio de soluciones, garantiza encontrar la asignacion optima que
     * minimiza el peso no asignado. Cada llamada recursiva cuenta como un
     * estado generado.
     */


    public Backtracking(HashMap<Camion, List<Paquete>> mejorSolucion, double mejorPesoNoAsignado, int estadosGenerados, List<Camion> camiones, List<Paquete> paquetes) {
        MejorSolucion = new HashMap<>();
        this.mejorPesoNoAsignado = mejorPesoNoAsignado;
        this.estadosGenerados = 0;
        this.camiones = camiones;
        this.paquetes = paquetes;
    }


    public void asignarPaquetes(List<Camion> camiones, List<Paquete> paquetes) {
        if (!camiones.isEmpty() && !paquetes.isEmpty()) {
            HashMap<Camion, List<Paquete>> asignacion = new HashMap<>();
            for (Camion c : this.camiones) {
                asignacion.put(c, new ArrayList<>());
            }
            camionDescarte = new Camion(0, "", true, 1000000000);
            asignacion.put(camionDescarte, new ArrayList<>());

            backtracking(0, asignacion);
        }
    }

    private void backtracking(int indiceP, HashMap<Camion, List<Paquete>> asignacion) {
        estadosGenerados++;

        if (indiceP == this.paquetes.size()) {
            double pesoNoAsignado = getPeso(camionDescarte, asignacion);
            if (pesoNoAsignado < mejorPesoNoAsignado) {
                mejorPesoNoAsignado = pesoNoAsignado;
                MejorSolucion = copiarAsignacion(asignacion);

            }
        } else {
            Paquete paqueteActual = this.paquetes.get(indiceP);

            for (Camion c : asignacion.keySet()) {
                boolean cumpleRefrigeracion = !paqueteActual.contieneAlimentos() || c.estaRefrigerado();
                boolean entraEnCapacidad = getPeso(c, asignacion) + paqueteActual.getPesoKg() <= c.getCapacidadKg();

                if (cumpleRefrigeracion && entraEnCapacidad) {
                    asignacion.get(c).add(paqueteActual);
                    backtracking(indiceP + 1, asignacion);
                    asignacion.get(c).remove(paqueteActual);
                }
            }



        }
    }

    private double getPeso(Camion camion, HashMap<Camion, List<Paquete>> asignacion) {
        List<Paquete> paquetesDelCamion = asignacion.get(camion);
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