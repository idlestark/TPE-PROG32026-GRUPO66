import java.util.*;

public class Main {

    public static void main(String[] args) {

        // --- Datos de prueba ---
        List<Camion> camiones = new ArrayList<>();
        camiones.add(new Camion(100, "AAA000A", true,  100));
        camiones.add(new Camion(101, "AAA001B", false, 500));
        camiones.add(new Camion(102, "AAA002C", true,  115));

        List<Paquete> paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1, "P001", 30,  true,  80));
        paquetes.add(new Paquete(2, "P002", 100, false, 2));
        paquetes.add(new Paquete(3, "P003", 80,  false, 10));
        paquetes.add(new Paquete(4, "P004", 25,  true,  100));
        paquetes.add(new Paquete(5, "P005", 200, false, 50));
        paquetes.add(new Paquete(6, "P006", 90,  true,  70));
        paquetes.add(new Paquete(7, "P007", 60,  true,  40));
        paquetes.add(new Paquete(8, "P008", 150, false, 60));
        paquetes.add(new Paquete(9, "P009", 45,  true,  90));
        paquetes.add(new Paquete(10,"P010", 110, false, 35));


        //
        //      BACKTRACKING
        //
        HashMap<Camion, List<Paquete>> asignacionInicial = new HashMap<>();
        Backtracking bt = new Backtracking(asignacionInicial, Double.MAX_VALUE, 0, camiones, paquetes);
        bt.asignarPaquetes(camiones, paquetes);

        System.out.println("Backtracking");
        System.out.println("Solución obtenida:");
        bt.getMejorSolucion().forEach((c, ps) -> System.out.println("  " + c + " -> " + ps));
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