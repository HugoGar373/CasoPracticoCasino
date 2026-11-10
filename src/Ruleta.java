import java.util.Random;

public class Ruleta extends Thread {

    private Mesa mesa;
    private int tiempoEntreTiradas;
    private Random random;

    public Ruleta(Mesa mesa, int tiempoEntreTiradas) {
        setName("Ruleta");
        this.mesa = mesa;
        this.tiempoEntreTiradas = tiempoEntreTiradas;
        this.random = new Random();
    }

    @Override
    public void run() {
        boolean seguir = true;
        while (seguir) {
            try {
                Thread.sleep(tiempoEntreTiradas);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            seguir = mesa.esperarApuestas();

            if (seguir) {
                int numero = random.nextInt(37);
                mesa.publicarResultado(numero);
                seguir = mesa.cerrarTurno();
            }
        }
    }
}
