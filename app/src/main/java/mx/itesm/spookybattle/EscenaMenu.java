package mx.itesm.spookybattle;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.Timer;

/**
 * Representa la escena del MENU PRINCIPAL
 *
 * @author Roberto Martínez Román
 */
public class EscenaMenu extends EscenaBase
{
    // Regiones para las imágenes de la escena
    private ITextureRegion regionFondo;
    private ITextureRegion regionFondo2;
    private ITextureRegion regionFondo3;
    private ITextureRegion regionBtnAcercaDe;
    private ITextureRegion regionBtnJugar;
    private ITextureRegion regionBtnHowTo;
    private ITextureRegion regionTitulo;


    //Variables para timers
    private Timer tiempo;
    private Timer tiempo2;
    private boolean fondoPrincipalActivado = false;
    private boolean relampago = false;

    // Sprites sobre la escena
    private Sprite spriteFondo;
    private Sprite spriteFondo2;
    private Sprite spriteFondo3;

    // Un menú de tipo SceneMenu
    private MenuScene menu;     // Contenedor de las opciones
    // Constantes para cada opción
    private final int OPCION_ACERCA_DE = 0;
    private final int OPCION_JUGAR = 1;
    private final int OPCION_HOW_TO = 2;
    private final int OPCION_TITULO = 3;
    // Botones de cada opción
    private ButtonSprite btnAcercaDe;
    private ButtonSprite btnJugar;

    //Sonidos
    private Sound relampagoSonido;

    @Override
    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("MenuPrincipal1.png");
        regionFondo2 = cargarImagen("MenuPrincipal2.png");
        regionFondo3 = cargarImagen("Relampago.png");
        // Botones del menú
        regionBtnAcercaDe = cargarImagen("BotonAboutUs.png");
        regionBtnJugar = cargarImagen("BotonPlay.png");
        regionBtnHowTo = cargarImagen("BotonHowTo.png");
        regionTitulo = cargarImagen("Titulo.png");

        relampagoSonido = cargarEfecto("Sonidos/single_lightning_bolt.wav");
    }

    @Override
    public void crearEscena() {

        //tiempo = new Timer();

        // Creamos el sprite de manera óptima

        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);
        spriteFondo2 = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo2);
        spriteFondo3 = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo3);

        spriteFondo2.setColor(1,1,1,0);
        // Crea el fondo de la pantalla
        //fondo = new SpriteBackground(1, 1, 1, spriteFondo);
        //setBackground(fondo);
        //setBackgroundEnabled(true);


        // Mostrar un recuadro atrás del menú
        //agregarFondoMenu();
        // Mostrar opciones de menú
        beginTimer();
        agregarMenu();
        attachChild(spriteFondo3);
        attachChild(spriteFondo2);
        attachChild(spriteFondo);

    }
    private void beginTimer(){

        registerUpdateHandler(new TimerHandler(5, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (!fondoPrincipalActivado) {
                    beginTimer2();

                    fondoPrincipalActivado = true;
                    relampago = false;
                } else {
                    beginTimer2();

                    fondoPrincipalActivado = false;
                    relampago = false;

                }
                beginTimer();
            }
        }));

        /*tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!fondoPrincipalActivado) {
                            beginTimer2();

                            fondoPrincipalActivado=true;
                            relampago = false;
                        }
                        else {
                            beginTimer2();

                            fondoPrincipalActivado = false;
                            relampago = false;

                        }
                    }
                });
            }
        },10000,10000);*/
    }

    private void beginTimer2(){

        registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (!relampago) {
                    spriteFondo.setColor(1, 1, 1, 0);
                    spriteFondo2.setColor(1, 1, 1, 0);
                    spriteFondo3.setColor(1, 1, 1, 1);
                    relampagoSonido.play();
                    relampago = true;

                }
                else {
                    spriteFondo3.setColor(1, 1, 1, 0);
                    if(!fondoPrincipalActivado) {
                        spriteFondo.setColor(1, 1, 1, 1);
                        spriteFondo2.setColor(1, 1, 1, 0);
                    }else{
                        spriteFondo.setColor(1, 1, 1, 0);
                        spriteFondo2.setColor(1, 1, 1, 1);
                    }
                }
                beginTimer2();
            }
        }));

        /*tiempo2 = new Timer();

        tiempo2.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!relampago) {
                            spriteFondo.setColor(1, 1, 1, 0);
                            spriteFondo2.setColor(1, 1, 1, 0);
                            spriteFondo3.setColor(1, 1, 1, 1);
                            relampagoSonido.play();
                            relampago = true;

                        }
                        else {
                            spriteFondo3.setColor(1, 1, 1, 0);
                            if(!fondoPrincipalActivado) {
                                spriteFondo.setColor(1, 1, 1, 1);
                                spriteFondo2.setColor(1, 1, 1, 0);
                            }else{
                                spriteFondo.setColor(1, 1, 1, 0);
                                spriteFondo2.setColor(1, 1, 1, 1);
                            }
                            tiempo2.cancel();
                        }

                    }
                });
            }
        }, 400, 400);*/


    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        // Crea las opciones (por ahora, acerca de y jugar)
        IMenuItem opcionAcercaDe = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_ACERCA_DE,regionBtnAcercaDe, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionJugar = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_JUGAR,regionBtnJugar, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionHowTo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_HOW_TO,regionBtnHowTo, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem titulo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_TITULO,regionTitulo,actividadJuego.getVertexBufferObjectManager()),1,1);


        // Agrega las opciones al menú
        menu.addMenuItem(opcionAcercaDe);
        menu.addMenuItem(opcionJugar);
        menu.addMenuItem(opcionHowTo);
        menu.addMenuItem(titulo);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú huhh es (0,0)
        opcionAcercaDe.setPosition(0,-300);
        opcionJugar.setPosition(0, -30);
        opcionHowTo.setPosition(0,-150);
        titulo.setPosition(0,190);


        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_ACERCA_DE:
                        // Mostrar la escena de AcercaDe
                        admEscenas.crearEscenaAcercaDe();
                        admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
                        admEscenas.liberarEscenaMenu();

                        break;

                    case OPCION_JUGAR:
                        // Mostrar la pantalla de juego
                        admEscenas.crearEscenaSeleccionPersonaje();
                        admEscenas.setEscena(TipoEscena.ESCENA_SELECCION_PERSONAJE);
                        admEscenas.liberarEscenaMenu();

                        break;

                    case OPCION_HOW_TO:
                        admEscenas.crearNuclear();
                        admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
                        admEscenas.liberarEscenaMenu();

                        break;

                }
                return true;
            }
        });

        // Asigna este menú a la escena
        setChildScene(menu);
    }

    // La escena se debe actualizar en este método que se repite "varias" veces por segundo
    // Aquí es donde programan TODA la acción de la escena (movimientos, choques, disparos, etc.)
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }


    @Override
    public void onBackKeyPressed() {
        // Salir del juego, no hacemos nada
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_MENU;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
        liberarRecursos();
    }

    @Override
    public void liberarRecursos() {
        regionFondo.getTexture().unload();
        regionFondo = null;
        tiempo = null;
        tiempo2 = null;

    }
}
