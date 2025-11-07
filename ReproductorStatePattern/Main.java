// ===============================
// Patrón STATE aplicado a Reproductor de Música
// ===============================

public class Main {
    public static void main(String[] args) {
        ReproductorMusica mp3 = new ReproductorMusica();

        mp3.play();   // DETENIDO → REPRODUCIENDO
        mp3.pause();  // REPRODUCIENDO → PAUSADO
        mp3.next();   // sigue PAUSADO, cambia de pista
        mp3.play();   // PAUSADO → REPRODUCIENDO
        mp3.stop();   // REPRODUCIENDO → DETENIDO
        mp3.next();   // DETENIDO → REPRODUCIENDO
    }
}

/* =========================================
 * Contexto (ReproductorMusica)
 * ========================================= */
class ReproductorMusica {
    private Estado estado;

    public ReproductorMusica() {
        // Estado inicial: detenido
        setEstado(new DetenidoEstado());
    }

    public void setEstado(Estado nuevoEstado) {
        this.estado = nuevoEstado;
        this.estado.setContext(this);
    }

    // Métodos públicos que delegan al estado actual
    public void play()  { estado.play();  }
    public void pause() { estado.pause(); }
    public void stop()  { estado.stop();  }
    public void next()  { estado.next();  }
}

/* =========================================
 * Interfaz Estado (State)
 * ========================================= */
interface Estado {
    void setContext(ReproductorMusica reproductor);
    void play();
    void pause();
    void stop();
    void next();
}

/* =========================================
 * Estado concreto: Detenido
 * ========================================= */
class DetenidoEstado implements Estado {
    private ReproductorMusica reproductor;

    @Override
    public void setContext(ReproductorMusica reproductor) {
        this.reproductor = reproductor;
    }

    @Override
    public void play() {
        System.out.println("▶️  Iniciando reproducción…");
        reproductor.setEstado(new ReproduciendoEstado());
    }

    @Override
    public void pause() {
        System.out.println("⚠️  No se puede pausar: está detenido.");
    }

    @Override
    public void stop() {
        System.out.println("⚠️  Ya estaba detenido.");
    }

    @Override
    public void next() {
        System.out.println("⏭️  Saltar pista y reproducir.");
        reproductor.setEstado(new ReproduciendoEstado());
    }
}

/* =========================================
 * Estado concreto: Reproduciendo
 * ========================================= */
class ReproduciendoEstado implements Estado {
    private ReproductorMusica reproductor;

    @Override
    public void setContext(ReproductorMusica reproductor) {
        this.reproductor = reproductor;
    }

    @Override
    public void play() {
        System.out.println("⚠️  Ya se está reproduciendo.");
    }

    @Override
    public void pause() {
        System.out.println("⏸️  Pausando…");
        reproductor.setEstado(new PausadoEstado());
    }

    @Override
    public void stop() {
        System.out.println("⏹️  Deteniendo…");
        reproductor.setEstado(new DetenidoEstado());
    }

    @Override
    public void next() {
        System.out.println("⏭️  Siguiente pista…");
        // sigue reproduciendo
    }
}

/* =========================================
 * Estado concreto: Pausado
 * ========================================= */
class PausadoEstado implements Estado {
    private ReproductorMusica reproductor;

    @Override
    public void setContext(ReproductorMusica reproductor) {
        this.reproductor = reproductor;
    }

    @Override
    public void play() {
        System.out.println("▶️  Reanudando desde pausa…");
        reproductor.setEstado(new ReproduciendoEstado());
    }

    @Override
    public void pause() {
        System.out.println("⚠️  Ya estaba en pausa.");
    }

    @Override
    public void stop() {
        System.out.println("⏹️  Deteniendo…");
        reproductor.setEstado(new DetenidoEstado());
    }

    @Override
    public void next() {
        System.out.println("⏭️  Siguiente pista (seguirá en pausa)...");
        // sigue pausado
    }
}
