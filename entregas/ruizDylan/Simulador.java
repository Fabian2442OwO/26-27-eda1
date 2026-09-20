import java.util.Random;

public class Simulador {
    private Fila fila;
    private final Random random;
    private int atendidosTotales;
    private int abandonosTotales;

    public Simulador() {
        this.fila = new Fila();
        this.random = new Random();
        this.atendidosTotales = 0;
        this.abandonosTotales = 0;
    }

    public void ejecutarRetoExtendido(int retardoMs) {
        reiniciar();
        System.out.println("===============================================================");
        System.out.println("          INICIANDO SIMULACION CCCF (RETO EXTENDIDO)          ");
        System.out.println(" Leyenda: [N] Normal | [P] Preferente | [C] Colado ");
        System.out.println("===============================================================\n");

        for (int minuto = 1; minuto <= 120; minuto++) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("MINUTO " + minuto + " / 120");

            if (minuto >= 20) {
                if (minuto % 5 == 0) {
                    abandonosTotales += fila.procesarAburrimiento(minuto);
                }

                if (minuto % 15 == 0) {
                    atendidosTotales += fila.procesarAvisoCajaExtra();
                }

                fila.simularEntregaCompras();
            }

            if (random.nextDouble() < 0.60) {
                boolean preferente = (minuto >= 20) && (random.nextDouble() < 0.15);
                Persona conocido = null;

                if (minuto >= 20 && !preferente && random.nextDouble() < 0.20) {
                    conocido = fila.obtenerPersonaAleatoria();
                }

                Persona nueva = new Persona(minuto, preferente, conocido);
                fila.agregarPersona(nueva);
            }

            if (random.nextDouble() < 0.40) {
                Persona atendida = fila.atender();
                if (atendida != null) {
                    System.out.println("[CAJA 1] Atendio y finalizo la compra de " + atendida + ".");
                    atendidosTotales++;
                }
            }

            fila.mostrarEstadoFila();

            try {
                Thread.sleep(retardoMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        imprimirResultados(120);
    }

    private void reiniciar() {
        this.fila = new Fila();
        this.atendidosTotales = 0;
        this.abandonosTotales = 0;
    }

    private void imprimirResultados(int minutosTotales) {
        System.out.println("\n=======================================================");
        System.out.println("            RESUMEN AL CIERRE DEL CENTRO               ");
        System.out.println("=======================================================");
        System.out.println(" - Personas atendidas con exito: " + atendidosTotales);
        System.out.println(" - Personas que permanecen en fila: " + fila.getTamano() + " m");
        System.out.println(" - Personas que abandonaron por aburrimiento: " + abandonosTotales);
        System.out.println("=======================================================\n");
    }
}
