public abstract class Jugador extends Thread {

    private Mesa mesa;
    private int saldo;

    public Jugador(String nombre, Mesa mesa, int saldoInicial) {
        setName(nombre);
        this.mesa = mesa;
        this.saldo = saldoInicial;
    }

    public int getSaldo() {
        return saldo;
    }

    public void restarSaldo(int cantidad) {
        saldo = saldo - cantidad;
    }

    public void sumarSaldo(int cantidad) {
        saldo = saldo + cantidad;
    }

    public abstract int decidirApuesta();

    public abstract String describirApuesta();

    public abstract int calcularPremio(int numeroGanador);

    public void actualizarEstrategia(boolean haGanado) {
    }

    @Override
    public void run() {
        boolean continuar = true;
        while (continuar) {
            continuar = mesa.apostar(this);
            if (continuar) {
                mesa.esperarResultado();
                mesa.comprobarResultado(this);
            }
        }
    }
}
