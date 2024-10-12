import java.util.concurrent.Semaphore;

public class Barberia {

    private Semaphore colaEspera;
    private Semaphore barberoListo;
    private Semaphore silla;

    public Barberia() {
        colaEspera = new Semaphore(5);
        barberoListo = new Semaphore(0);
        silla = new Semaphore(1);
    }

    public boolean entrarBarberia(String nombre) {
        if (colaEspera.tryAcquire()) {
            System.out.println("El cliente: " + nombre + "se sienta en la silla de espera");
            return true;
        } else {
            System.out.println("La barberia esta llena, el cliente: " + nombre + " se va");
            return false;
        }
    }

    public void despertarBarbero(String nombre) {
        try {
            silla.acquire();
            System.out.println("El cliente: " + nombre + " se sienta en la silla del barbero");
            System.out.println("El cliente: " + nombre + " despierta al barbero");
            colaEspera.release();
            barberoListo.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void barberoCortaPelo() {
        try {
            barberoListo.acquire();
            System.out.println("El barbero comienza a cortar el pelo del cliente");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminarCorte() {
        System.out.println("El cliente se va de la barberia. El barbero se acuesta a dormir");
        silla.release();
    }

}
