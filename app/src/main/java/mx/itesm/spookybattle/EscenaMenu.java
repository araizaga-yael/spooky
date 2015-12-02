package mx.itesm.spookybattle;

import android.content.Context;
import android.content.SharedPreferences;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ResourceBundle;
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

    //Sprites Fondos

    private Sprite spriteFondoAdultos;
    private Sprite spriteFondoNinos;
    private Sprite spriteFondoCielo;
    private Sprite spriteFondoCasas;
    private Sprite spriteNubeNegra;

    //Regiones Fondo

    private ITextureRegion regionFondoAdultos;
    private ITextureRegion regionFondoNinos;
    private ITextureRegion regionFondoCasas;
    private ITextureRegion regionFondoCielo;
    private ITextureRegion regionNubeNegra;

    //Variables para timers
    private Timer tiempo;
    private Timer tiempo2;
    private boolean fondoPrincipalActivado = false;
    private boolean relampago = false;
    private boolean reiniciarTimer2=false;

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
        //regionFondo = cargarImagen("MenuPrincipal1.png");
        //regionFondo2 = cargarImagen("MenuPrincipal2.png");
        regionFondo3 = cargarImagen("Relampago.png");
        // Botones del menú
        regionBtnAcercaDe = cargarImagen("BotonAboutUs.png");
        regionBtnJugar = cargarImagen("BotonPlay.png");
        regionBtnHowTo = cargarImagen("BotonHowTo.png");
        regionTitulo = cargarImagen("Titulo.png");

        regionFondoAdultos = cargarImagen("MenuPrincipal/MenuPrincipalAdultos.png");
        regionFondoCasas = cargarImagen("MenuPrincipal/MenuPrincipalCasas.png");
        regionFondoCielo = cargarImagen("MenuPrincipal/MenuPrincipalCielo.png");
        regionFondoNinos = cargarImagen("MenuPrincipal/Menu.png");
        regionNubeNegra = cargarImagen("MenuPrincipal/cloud_black.png");

        relampagoSonido = cargarEfecto("Sonidos/single_lightning_bolt.wav");

        //initializeUnlocks();
    }

    @Override
    public void crearEscena() {

        spriteFondoAdultos = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondoAdultos);
        spriteFondoNinos = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondoNinos);
        spriteFondoCielo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondoCielo);
        spriteFondoCasas = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondoCasas);
        spriteFondo3 = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo3);

        spriteFondoAdultos.setColor(1,1,1,0);
        spriteFondo3.setColor(1,1,1,0);
        beginTimer();
        agregarMenu();
        attachChild(spriteFondoCielo);
        agregarHumo();
        attachChild(spriteFondoCasas);
        attachChild(spriteFondo3);
        attachChild(spriteFondoAdultos);
        attachChild(spriteFondoNinos);

        actividadJuego.reproducirMusica("Musica/01_Creepy_Town.ogg", true);

    }

    private void agregarHumo() {

        CircleParticleEmitter circulo = new CircleParticleEmitter(ControlJuego.ANCHO_CAMARA/5,
                ControlJuego.ALTO_CAMARA/2,10);
        BatchedSpriteParticleSystem sistema = new BatchedSpriteParticleSystem(circulo,
                20, 30, 200, regionNubeNegra, actividadJuego.getVertexBufferObjectManager());

        // Velocidad de las partículas minX, maxX, minY, maxY
        sistema.addParticleInitializer(new VelocityParticleInitializer<UncoloredSprite>(50,20,20,-20));
        // Aceleración
        sistema.addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(0,0));

        float tiempoVida = 15;   // Segundos de vida de cada partícula
        // Tiempo para que las partículas expiren.
        sistema.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(tiempoVida));
        // Escala
        sistema.addParticleInitializer(new ScaleParticleInitializer<UncoloredSprite>(0.5f, 1.0f));
        // Rotación
        //sistema.addParticleModifier(new RotationParticleModifier<UncoloredSprite>(1, 4, 0, 360));
        // Alpha de las partículas, recibe el rango de tiempo y el rango de alpha
        sistema.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(tiempoVida - 2, tiempoVida + 1, 1, 0.3f));

        // Se agrega a la escena, como cualquier Sprite
        attachChild(sistema);
    }
    private void beginTimer(){

        registerUpdateHandler(new TimerHandler(5, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (!fondoPrincipalActivado) {
                    beginTimer2();

                    fondoPrincipalActivado = true;
                    relampago = false;
                    reiniciarTimer2=false;
                } else {
                    beginTimer2();

                    fondoPrincipalActivado = false;
                    relampago = false;
                    reiniciarTimer2=false;

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

        registerUpdateHandler(new TimerHandler(0.6f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (!relampago) {
                    spriteFondoNinos.setColor(1, 1, 1, 0);
                    spriteFondoAdultos.setColor(1, 1, 1, 0);
                    spriteFondo3.setColor(1, 1, 1, 1);
                    relampagoSonido.play();
                    relampago = true;

                }
                else {
                    spriteFondo3.setColor(1, 1, 1, 0);
                    if(!fondoPrincipalActivado) {
                        spriteFondoNinos.setColor(1, 1, 1, 1);
                        spriteFondoAdultos.setColor(1, 1, 1, 0);
                    }else{
                        spriteFondoNinos.setColor(1, 1, 1, 0);
                        spriteFondoAdultos.setColor(1, 1, 1, 1);
                    }
                    reiniciarTimer2=true;
                }
                if(!reiniciarTimer2)
                    beginTimer2();
            }
        }));
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
                        admEscenas.crearEscenaHowTo();
                        admEscenas.setEscena(TipoEscena.ESCENA_HOW_TO);
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

    private void initializeUnlocks(){
        SharedPreferences unlockPreferences = actividadJuego.getSharedPreferences("UnlockedCharacters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = unlockPreferences.edit();
        editor.putBoolean("Geronimo", false);
        editor.putBoolean("Francis", false);
        editor.putBoolean("Gus", false);
        editor.commit();
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
        regionFondoAdultos.getTexture().unload();
        regionFondoAdultos = null;
        regionFondoNinos.getTexture().unload();
        regionFondoNinos = null;
        regionFondo3.getTexture().unload();
        regionFondo3 = null;

        tiempo = null;
        tiempo2 = null;

    }
}
