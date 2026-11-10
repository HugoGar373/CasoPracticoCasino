public class Banca {

    private int saldo;
    private boolean sinFondos;

    public Banca(int saldoInicial) {
        this.saldo = saldoInicial;
        this.sinFondos = false;
    }

    public synchronized void recibirApuesta(int cantidad) {
        saldo = saldo + cantidad;
    }

    public synchronized int pagar(int premio) {
        if (premio > saldo) {
            int pagado = saldo;
            saldo = 0;
            sinFondos = true;
            return pagado;
        }
        saldo = saldo - premio;
        return premio;
    }

    public synchronized int getSaldo() {
        return saldo;
    }

    public synchronized boolean isSinFondos() {
        return sinFondos;
    }
}
