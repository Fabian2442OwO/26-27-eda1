import java.util.LinkedList;
import java.util.Random;

public class Fila {
    private final LinkedList<Persona> personas;
    private final Random random;

    public Fila() {
        this.personas = new LinkedList<>();
        this.random = new Random();
    }

    public int getTamano() {
        return personas.size();
    }

    public boolean agregarPersona(Persona nuevaPersona) {

        if (personas.size() >= 30) {
            if (random.nextBoolean()) {
                System.out.println("Persona #" + nuevaPersona.getId() + " vio la fila muy larga (>30m) y desidio no entrar.");
                return false;
            }
        }

        if (nuevaPersona.esPreferente()) {
            insertarPreferente(nuevaPersona);
        } else if (nuevaPersona.getConocidoEnFila() != null && personas.contains(nuevaPersona.getConocidoEnFila())) {
            insertarDetrasDeConocido(nuevaPersona);
        } else {
            personas.addLast(nuevaPersona);
            System.out.println("Llegada normal: " + nuevaPersona + " al final de la fila.");
        }
        return true;
    }

    private void insertarPreferente(Persona nuevaPersona) {
        int posicionInsercion = 0;
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).esPreferente()) {
                posicionInsercion = i + 1;
            }
        }
        personas.add(posicionInsercion, nuevaPersona);
        System.out.println("Atencion Preferente: " + nuevaPersona + " se ubico en la posicion " + (posicionInsercion + 1) + ".");
    }

    private void insertarDetrasDeConocido(Persona nuevaPersona) {
        int indiceConocido = personas.indexOf(nuevaPersona.getConocidoEnFila());
        if (indiceConocido != -1) {
            personas.add(indiceConocido + 1, nuevaPersona);
            System.out.println("Colado ilicito: " + nuevaPersona + " se colo detras de su conocido " + personas.get(indiceConocido) + ".");
        } else {
            personas.addLast(nuevaPersona);
            System.out.println("Llegada normal: " + nuevaPersona + " (no encontro a su conocido).");
        }
    }

    public Persona atender() {
        if (!personas.isEmpty()) {
            return personas.removeFirst();
        }
        return null;
    }

    public int procesarAburrimiento(int minutoActual) {
        int personasIdas = 0;
        for (int i = personas.size() - 1; i >= 0; i--) {
            Persona p = personas.get(i);
            if ((minutoActual - p.getMinutoLlegada()) > 8) {
                if (random.nextDouble() < 0.30) {
                    System.out.println("ABURRIMIENTO! La persona " + p + " llevaba " + (minutoActual - p.getMinutoLlegada()) + " min en fila y se fue.");
                    personas.remove(i);
                    personasIdas++;
                }
            }
        }
        return personasIdas;
    }

    public void simularEntregaCompras() {
        if (personas.size() > 1 && random.nextDouble() < 0.10) {
            int indiceSalida = random.nextInt(personas.size());
            Persona p = personas.remove(indiceSalida);
            System.out.println("Entrega de compras: " + p + " le entrego sus articulos a otra persona y salio de la fila.");
        }
    }

    public int procesarAvisoCajaExtra() {
        int atendidosExtra = 0;
        if (personas.size() > 25) {
            System.out.println("[MEGAFONO CCCF]: \"Pasen por esta caja en orden de fila\"");
            int aAtender = Math.min(5, personas.size());
            for (int i = 0; i < aAtender; i++) {
                Persona p = atender();
                if (p != null) {
                    System.out.println(p + " paso a la NUEVA CAJA aperturada.");
                    atendidosExtra++;
                }
            }
        }
        return atendidosExtra;
    }

    public Persona obtenerPersonaAleatoria() {
        if (personas.isEmpty()) {
            return null;
        }
        return personas.get(random.nextInt(personas.size()));
    }

    public void mostrarEstadoFila() {
        StringBuilder sb = new StringBuilder();
        sb.append("   Fila (").append(personas.size()).append(" m) : [CAJA 1] <-- ");
        if (personas.isEmpty()) {
            sb.append("(Fila vacia)");
        } else {
            for (Persona p : personas) {
                sb.append(p.toString()).append(" ");
            }
        }
        System.out.println(sb.toString());
    }
}
