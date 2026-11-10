import java.util.Random;

public class JugadorMartingala extends Jugador {

    private int numero;
    private int cantidad;

    public JugadorMartingala(String nombre, Mesa mesa, int saldoInicial) {
        super(nombre, mesa, saldoInicial);
        Random random = new Random();
        this.numero = random.nextInt(36) + 1;
        this.cantidad = 10;
    }

    @Override
    public int decidirApuesta() {
        return cantidad;
    }

    @Override
    public String describirApuesta() {
        return "al número " + numero;
    }

    @Override
    public int calcularPremio(int numeroGanador) {
        if (numeroGanador == numero) {
            return cantidad * 36;
        }
        return 0;
    }

    @Override
    public void actualizarEstrategia(boolean haGanado) {
        if (haGanado) {
            cantidad = 10;
        } else {
            cantidad = cantidad * 2;
        }
    }
}
