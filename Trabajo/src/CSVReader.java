import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {

    public static List<Camion> leerCamiones(String path) {
        List<Camion> camiones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int total = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < total; i++) {
                String[] partes = br.readLine().trim().split(";");
                int id = Integer.parseInt(partes[0]);
                String patente = partes[1];
                boolean refrigerado = partes[2].equals("1");
                double capacidad = Double.parseDouble(partes[3]);
                camiones.add(new Camion(id, patente, refrigerado, capacidad));
            }
        } catch (IOException e) {
            System.out.println("Error leyendo camiones: " + e.getMessage());
        }
        return camiones;
    }

    public static List<Paquete> leerPaquetes(String path) {
        List<Paquete> paquetes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int total = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < total; i++) {
                String[] partes = br.readLine().trim().split(";");
                int id = Integer.parseInt(partes[0]);
                String codigo = partes[1];
                double peso = Double.parseDouble(partes[2]);
                boolean alimentos = partes[3].equals("1");
                int urgencia = Integer.parseInt(partes[4]);
                paquetes.add(new Paquete(id, codigo, peso, alimentos, urgencia));
            }
        } catch (IOException e) {
            System.out.println("Error leyendo paquetes: " + e.getMessage());
        }
        return paquetes;
    }
}