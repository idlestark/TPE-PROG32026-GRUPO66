import java.util.ArrayList;

public class Carga {
    private Camion camion;
    private ArrayList<Paquete> paquetes;
    private double peso;

    public Carga(Camion camion, ArrayList<Paquete> paquetes, double peso) {
        this.camion = camion;
        this.paquetes = paquetes;
        this.peso = peso;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public ArrayList<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(ArrayList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
