import java.util.Random;

public class JugadorParImpar extends Jugador {

    private boolean apuestaPar;

    public JugadorParImpar(String nombre, Mesa mesa, int saldoInicial) {
        super(nombre, mesa, saldoInicial);
        Random random = new Random();
        this.apuestaPar = (random.nextInt(2) == 0);
    }

    @Override
    public int decidirApuesta() {
        return 10;
    }

    @Override
    public String describirApuesta() {
        if (apuestaPar) {
            return "a PAR";
        }
        return "a IMPAR";
    }

    @Override
    public int calcularPremio(int numeroGanador) {
        if (numeroGanador == 0) {
            return 0;
        }
        boolean esPar = (numeroGanador % 2 == 0);
        if (esPar == apuestaPar) {
            return 20;
        }
        return 0;
    }
}
