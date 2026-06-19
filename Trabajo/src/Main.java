import java.util.*;

public class Main {

    public static void main(String[] args) {


        List<Camion> camiones = CSVReader.leerCamiones("Trabajo/src/Camiones.csv");
        List<Paquete> paquetes = CSVReader.leerPaquetes("Trabajo/src/Paquetes.csv");

        //
        //      BACKTRACKING
        //
        List<Carga> mejorSolucionInicial = new ArrayList<>();
        Backtracking bt = new Backtracking(mejorSolucionInicial, Double.MAX_VALUE, 0, camiones, paquetes);
        bt.asignarPaquetes(camiones, paquetes);

        System.out.println("Backtracking");
        System.out.println("Solución obtenida:");
        for (Carga carga : bt.getMejorSolucion()) {
            System.out.println("  " + carga.getCamion() + " -> " + carga.getPaquetes());
        }
        System.out.println("Peso no asignado: " + bt.getMejorPesoNoAsignado() + " kg.");
        System.out.println("Costo de la solución (cantidad de estados generados): "
                + bt.getEstadosGenerados());

        System.out.println();

        //
        //        GREEDY
        //

        Greedy g = new Greedy();
        g.greedy(camiones, paquetes);
        g.getAsignacion().forEach((c, ps) -> System.out.println("  " + c + " -> " + ps));
        System.out.println("Peso no asignado: " + g.getPesoNoAsignado() + " kg.");
        System.out.println("Candidatos considerados: " + g.getCandidatosConsiderados());
    }
}