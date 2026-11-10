public class Principal {

    public static void main(String[] args) {
        int saldoJugadores = 1000;
        int saldoBanca = 50000;
        int tiempoTirada = 3000;

        Banca banca = new Banca(saldoBanca);
        Jugador[] jugadores = new Jugador[12];
        Mesa mesa = new Mesa(banca, jugadores.length);

        for (int i = 0; i < 4; i++) {
            jugadores[i] = new JugadorNumero("JugadorNumero " + (i + 1), mesa, saldoJugadores);
        }
        for (int i = 4; i < 8; i++) {
            jugadores[i] = new JugadorParImpar("JugadorParImpar " + (i + 1), mesa, saldoJugadores);
        }
        for (int i = 8; i < 12; i++) {
            jugadores[i] = new JugadorMartingala("JugadorMartingala " + (i + 1), mesa, saldoJugadores);
        }

        Ruleta ruleta = new Ruleta(mesa, tiempoTirada);
        ruleta.start();
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i].start();
        }

        try {
            ruleta.join();
            for (int i = 0; i < jugadores.length; i++) {
                jugadores[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Resumen");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println(jugadores[i].getName() + ": " + jugadores[i].getSaldo() + " €");
        }
        System.out.println("Banca: " + banca.getSaldo() + " €");
    }
}
