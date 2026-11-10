import java.util.Random;

public class JugadorNumero extends Jugador {

    private int numero;

    public JugadorNumero(String nombre, Mesa mesa, int saldoInicial) {
        super(nombre, mesa, saldoInicial);
        Random random = new Random();
        this.numero = random.nextInt(36) + 1; // entre 1 y 36 (el 0 no se puede elegir)
    }

    @Override
    public int decidirApuesta() {
        return 10;
    }

    @Override
    public String describirApuesta() {
        return "al número " + numero;
    }

    @Override
    public int calcularPremio(int numeroGanador) {
        if (numeroGanador == numero) {
            return 360;
        }
        return 0;
    }
}
