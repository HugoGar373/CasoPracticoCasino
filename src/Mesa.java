public class Mesa {

    private Banca banca;
    private int jugadoresActivos;
    private int apuestasHechas;
    private int resultadosComprobados;
    private int numeroGanador;
    private int turno;
    private boolean resultadoListo;
    private boolean activa;

    public Mesa(Banca banca, int numeroJugadores) {
        this.banca = banca;
        this.jugadoresActivos = numeroJugadores;
        this.apuestasHechas = 0;
        this.resultadosComprobados = 0;
        this.turno = 0;
        this.resultadoListo = false;
        this.activa = true;
        abrirTurno();
    }

    private void abrirTurno() {
        turno++;
        System.out.println();
        System.out.println("Turno " + turno);
    }

    public synchronized boolean apostar(Jugador jugador) {
        while (resultadoListo && activa) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!activa) {
            return false;
        }

        int cantidad = jugador.decidirApuesta();
        if (cantidad > jugador.getSaldo()) {
            System.out.println(jugador.getName() + " se retira: no tiene saldo para apostar "
                    + cantidad + " € (saldo: " + jugador.getSaldo() + " €)");
            jugadoresActivos--;
            if (jugadoresActivos == 0) {
                activa = false;
            }
            notifyAll();
            return false;
        }

        jugador.restarSaldo(cantidad);
        banca.recibirApuesta(cantidad);
        System.out.println(jugador.getName() + " apuesta " + cantidad + " € "
                + jugador.describirApuesta());
        apuestasHechas++;
        if (apuestasHechas == jugadoresActivos) {
            notifyAll();
        }
        return true;
    }

    public synchronized void esperarResultado() {
        while (!resultadoListo && activa) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void comprobarResultado(Jugador jugador) {
        int premio = jugador.calcularPremio(numeroGanador);
        if (premio > 0) {
            int cobrado = banca.pagar(premio);
            jugador.sumarSaldo(cobrado);
            if (cobrado < premio) {
                System.out.println(jugador.getName() + " gana, pero la banca solo puede pagar " + cobrado + " € de " + premio + " €");
            } else {
                System.out.println(jugador.getName() + " gana " + cobrado + " €");
            }
            jugador.actualizarEstrategia(true);
        } else {
            System.out.println(jugador.getName() + " pierde");
            jugador.actualizarEstrategia(false);
        }
        System.out.println("Saldo de " + jugador.getName() + ": " + jugador.getSaldo() + " €");
        resultadosComprobados++;
        if (resultadosComprobados == jugadoresActivos) {
            notifyAll();
        }
    }

    public synchronized boolean esperarApuestas() {
        while (apuestasHechas < jugadoresActivos && activa) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return activa;
    }

    public synchronized void publicarResultado(int numero) {
        numeroGanador = numero;
        resultadoListo = true;
        System.out.println();
        System.out.println("Nueva tirada: " + numero);
        System.out.println("Resultado de la tirada: " + numero);
        notifyAll();
    }

    public synchronized boolean cerrarTurno() {
        while (resultadosComprobados < jugadoresActivos) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Saldo de la banca: " + banca.getSaldo() + " €");

        if (banca.isSinFondos()) {
            System.out.println();
            System.out.println("La banca ya no tiene dinero.");
            activa = false;
        } else {
            resultadoListo = false;
            apuestasHechas = 0;
            resultadosComprobados = 0;
            abrirTurno();
        }
        notifyAll();
        return activa;
    }
}
