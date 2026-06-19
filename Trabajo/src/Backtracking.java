import java.util.*;

public class Backtracking {
    private List<Carga> MejorSolucion = new ArrayList<>();
    private double mejorPesoNoAsignado;
    private int estadosGenerados;


    private List<Camion> camiones;
    private List<Paquete> paquetes;
    private Carga cargaDescarte;


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


    public Backtracking(List<Carga> mejorSolucion, double mejorPesoNoAsignado, int estadosGenerados, List<Camion> camiones, List<Paquete> paquetes) {
        MejorSolucion = new ArrayList<>();
        this.mejorPesoNoAsignado = mejorPesoNoAsignado;
        this.estadosGenerados = 0;
        this.camiones = camiones;
        this.paquetes = paquetes;
    }


    public void asignarPaquetes(List<Camion> camiones, List<Paquete> paquetes) {
        if (!camiones.isEmpty() && !paquetes.isEmpty()) {
            List<Carga> cargas = new ArrayList<>();
            for (Camion c : this.camiones) {
                cargas.add(new Carga(c, new ArrayList<>(), 0));
            }
            Camion camionDescarte = new Camion(0, "", true, 1000000000);
            cargaDescarte = new Carga(camionDescarte, new ArrayList<>(), 0);
            cargas.add(cargaDescarte);

            backtracking(0, cargas);
        }
    }

    private void backtracking(int indiceP, List<Carga> cargas) {
        estadosGenerados++;

        if (indiceP == this.paquetes.size()) {
            double pesoNoAsignado = cargaDescarte.getPeso();
            if (pesoNoAsignado < mejorPesoNoAsignado) {
                mejorPesoNoAsignado = pesoNoAsignado;
                MejorSolucion = copiarCargas(cargas);
            }
        } else {
            Paquete paqueteActual = this.paquetes.get(indiceP);

            for (Carga carga : cargas) {
                Camion c = carga.getCamion();
                boolean cumpleRefrigeracion = !paqueteActual.contieneAlimentos() || c.estaRefrigerado();
                boolean entraEnCapacidad = carga.getPeso() + paqueteActual.getPesoKg() <= c.getCapacidadKg();

                // Poda: antes de agregar el paquete a esta carga, verificar si conviene seguir esta rama
                boolean estaPodado = cargaDescarte.getPeso() >= mejorPesoNoAsignado;

                if (cumpleRefrigeracion && entraEnCapacidad && !estaPodado) {
                    carga.getPaquetes().add(paqueteActual);
                    carga.setPeso(carga.getPeso() + paqueteActual.getPesoKg());

                    backtracking(indiceP + 1, cargas);

                    carga.getPaquetes().remove(paqueteActual);
                    carga.setPeso(carga.getPeso() - paqueteActual.getPesoKg());
                }
            }
        }
    }

    private List<Carga> copiarCargas(List<Carga> cargas) {
        List<Carga> copia = new ArrayList<>();
        for (Carga c : cargas) {
            copia.add(new Carga(c.getCamion(), new ArrayList<>(c.getPaquetes()), c.getPeso()));
        }
        return copia;
    }

    public List<Carga> getMejorSolucion() { return MejorSolucion; }
    public double getMejorPesoNoAsignado() { return mejorPesoNoAsignado; }
    public int getEstadosGenerados() { return estadosGenerados; }
}