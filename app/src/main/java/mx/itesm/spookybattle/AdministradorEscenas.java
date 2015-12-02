package mx.itesm.spookybattle;

import org.andengine.engine.Engine;

/**
 * Administra la escena que se verá en la pantalla
 */
public class AdministradorEscenas
{
    // Instancia única
    private static final AdministradorEscenas INSTANCE =
            new AdministradorEscenas();
    protected ControlJuego actividadJuego;

    // Declara las distintas escenas que forman el juego
    private EscenaBase escenaSplash;
    private EscenaBase escenaMenu;
    private EscenaBase escenaAcercaDe;
    private EscenaBase escenaHowTo;
    private EscenaBase escenaBatalla;
    private EscenaBase escenaBatalla2;
    private EscenaBase escenaBatalla3;
    private EscenaBase escenaSeleccion;
    private EscenaBase escenaFinBatalla;
    private EscenaBase escenaFinBatalla2;
    private EscenaBase escenaFinBatalla3;




    // El tipo de escena que se está mostrando
    private TipoEscena tipoEscenaActual = TipoEscena.ESCENA_SPLASH;
    // La escena que se está mostrando
    private EscenaBase escenaActual;
    // El engine para hacer el cambio de escenas
    private Engine engine;

    // Asigna valores iniciales del administrador
    public static void inicializarAdministrador(ControlJuego actividadJuego, Engine engine) {
        getInstance().actividadJuego = actividadJuego;
        getInstance().engine = engine;
    }

    // Regresa la instancia del administrador de escenas
    public static AdministradorEscenas getInstance() {
        return INSTANCE;
    }

    // Regresa el tipo de la escena actual
    public TipoEscena getTipoEscenaActual() {
        return tipoEscenaActual;
    }

    // Regresa la escena actual
    public EscenaBase getEscenaActual() {
        return escenaActual;
    }

    /*
     * Pone en la pantalla la escena que llega como parámetro y guarda el nuevo estado
     */
    private void setEscenaBase(EscenaBase nueva) {
        engine.setScene(nueva);
        escenaActual = nueva;
        tipoEscenaActual = nueva.getTipoEscena();
    }

    /**
     * Cambia a la escena especificada en el parámetro
     * @param nuevoTipo la nueva escena que se quiere mostrar
     */
    public void setEscena(TipoEscena nuevoTipo) {
        switch (nuevoTipo) {
            case ESCENA_SPLASH:
                setEscenaBase(escenaSplash);
                break;
            case ESCENA_MENU:
                setEscenaBase(escenaMenu);
                break;
            case ESCENA_ACERCA_DE:
                setEscenaBase(escenaAcercaDe);
                break;
            case ESCENA_HOW_TO:
                setEscenaBase(escenaHowTo);
                break;
            case ESCENA_SELECCION_PERSONAJE:
                setEscenaBase(escenaSeleccion);
                break;
            case ESCENA_BATALLA:
                setEscenaBase(escenaBatalla);
                break;
            case ESCENA_BATALLA2:
                setEscenaBase(escenaBatalla2);
                break;
            case ESCENA_BATALLA3:
                setEscenaBase(escenaBatalla3);
                break;
            case ESCENA_FIN_BATALLA:
                setEscenaBase(escenaFinBatalla);
                break;
            case ESCENA_FIN_BATALLA2:
                setEscenaBase(escenaFinBatalla2);
                break;
            case ESCENA_FIN_BATALLA3:
                setEscenaBase(escenaFinBatalla3);
                break;
        }
    }

    //*** Crea la escena de Splash
    public void crearEscenaSplash() {
        // Carga los recursos
        escenaSplash = new EscenaSplash();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaSplash() {
        escenaSplash.liberarEscena();
        escenaSplash = null;
    }

    // ** MENU
    //*** Crea la escena de MENU
    public void crearEscenaMenu() {
        // Carga los recursos
        escenaMenu = new EscenaMenu();
    }

    //*** Libera la escena de MENU
    public void liberarEscenaMenu() {
        escenaMenu.liberarEscena();
        escenaMenu = null;
    }

    //*** Crea la escena de Acerca De
    public void crearEscenaAcercaDe() {
        // Carga los recursos
        escenaAcercaDe = new EscenaAcercaDe();
    }

    //*** Libera la escena de AcercDe
    public void liberarEscenaAcercaDe() {
        escenaAcercaDe.liberarEscena();
        escenaAcercaDe = null;
    }

    public void crearEscenaHowTo() {
        // Carga los recursos
        escenaHowTo = new EscenaHowTo();
    }

    public void liberarEscenaHowTo() {
        escenaHowTo.liberarEscena();
        escenaHowTo = null;
    }

    public void crearEscenaSeleccionPersonaje(){
        //Cargar los recursos
        escenaSeleccion = new SeleccionPersonaje();

    }
    public void liberarEscenaSeleccionPersonaje() {

        escenaSeleccion.liberarEscena();
        escenaSeleccion = null;
    }

    public void crearEscenaFinBatalla() {
        escenaFinBatalla = new EscenaFinBatalla();
    }

    public void liberarEscenaFinBatalla() {
        escenaFinBatalla.liberarEscena();
        escenaFinBatalla = null;
    }
    public void crearEscenaFinBatalla2() {
       escenaFinBatalla2 = new EscenaFinBatalla2();
    }

    public void liberarEscenaFinBatalla2() {
        escenaFinBatalla2.liberarEscena();
        escenaFinBatalla2 = null;
    }
    public void crearEscenaFinBatalla3() {
     //   escenaFinBatalla3 = new EscenaFinBatalla3();
    }

    public void liberarEscenaFinBatalla3() {
        escenaFinBatalla3.liberarEscena();
        escenaFinBatalla3 = null;
    }

    public void crearEscenaBatalla() {
        escenaBatalla = new EscenaBatalla();
    }
    public void liberarEscenaBatalla() {
        escenaBatalla.liberarEscena();
        escenaBatalla = null;
    }

    public void crearEscenaBatalla2() {
        escenaBatalla2 = new EscenaBatalla2();
    }
    public void liberarEscenaBatalla2() {
        escenaBatalla2.liberarEscena();
        escenaBatalla2 = null;
    }
    public void crearEscenaBatalla3() {
        escenaBatalla2 = new EscenaBatalla2();
    }
    public void liberarEscenaBatalla3() {
        escenaBatalla3.liberarEscena();
        escenaBatalla3 = null;
    }


}