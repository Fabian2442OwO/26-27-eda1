public class Persona {
    private static int contadorId = 1;

    private final int id;
    private final boolean preferente;
    private final int minutoLlegada;
    private final Persona conocidoEnFila;

    public Persona(int minutoLlegada, boolean preferente) {
        this.id = contadorId++;
        this.minutoLlegada = minutoLlegada;
        this.preferente = preferente;
        this.conocidoEnFila = null;
    }

    public Persona(int minutoLlegada, boolean preferente, Persona conocidoEnFila) {
        this.id = contadorId++;
        this.minutoLlegada = minutoLlegada;
        this.preferente = preferente;
        this.conocidoEnFila = conocidoEnFila;
    }

    public int getId() {
        return id;
    }

    public boolean esPreferente() {
        return preferente;
    }

    public int getMinutoLlegada() {
        return minutoLlegada;
    }

    public Persona getConocidoEnFila() {
        return conocidoEnFila;
    }

    @Override
    public String toString() {
        if (preferente) {
            return "[P" + id + "]"; 
        }
        if (conocidoEnFila != null) {
            return "[C" + id + "]"; 
        }
        return "[N" + id + "]"; 
    }
}