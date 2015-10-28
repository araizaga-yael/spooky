package mx.itesm.spookybattle;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TiledTextureRegion;


import android.graphics.Color;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa la escena de primer nivel
 *
 *
 */

public class EscenaBatalla extends EscenaBase
{
    // Regiones para las imágenes de la escena
    private ITextureRegion regionFondo;
    private ITextureRegion regionBtnAtk;
    private ITextureRegion regionBtnDef;
    private ITextureRegion regionBtnAtk1;
    private ITextureRegion regionBtnAtk2;
    private ITextureRegion regionBtnAtk3;
    private ITextureRegion regionBtnAtk4;


    //SpriteAnimado
    private AnimatedSprite spriteCurtisAnimado;
    private TiledTextureRegion regionCurtisAnimado;



    //cosas de texto
    private BitmapTextureAtlas mFontTexture;
    private Text  text;
    private Font  font;
    public static String s = "";

    // Sprites sobre la escena
    private Sprite spriteFondo;

    // Un menú de tipo SceneMenu
    private MenuScene menu;     // Contenedor de las opciones
    // Constantes para cada opción
    private final int ATTACK = 1;
    private final int DEFEND = 2;
    private final int ATTCK1 = 1;
    private final int ATTCK2 = 2;
    private final int ATTCK3 = 3;
    private final int ATTCK4 = 4;
    private final int SUPER = 0;

    public Main batalla1 = new Main();

    private Timer tiempo;
    private int numImagenes;
    private Sprite spriteFrame;
    private IEntity tagSpriteChild;
    @Override
    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("Batalla1/FondosBatalla1.png");
        regionCurtisAnimado = cargarImagenMosaico("AnimacionesCurtis/WaitingBattle/CurtisWaitingSheet.png", 1986, 331, 1, 6);
        regionBtnAtk = cargarImagen("BotonesCurtis/BotonAttack.png");
        regionBtnDef = cargarImagen("BotonesCurtis/BotonDefense.png");
        regionBtnAtk1 = cargarImagen("BotonesCurtis/BotonCurtisAtt3.png");
        regionBtnAtk2 = cargarImagen("BotonesCurtis/BotonCurtisAtt2.png");
        regionBtnAtk3 = cargarImagen("BotonesCurtis/BotonCurtisAtt4.png");
        regionBtnAtk4 = cargarImagen("BotonesCurtis/BotonCurtisAtt1.png");

        this.mFontTexture = new BitmapTextureAtlas(actividadJuego.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "fontf.ttf", 50, true, android.graphics.Color.BLACK);
        font.load();
    }

    @Override
    public void crearEscena() {


        Main.battle();

        // Creamos el sprite de manera óptima
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);

        // Mostrar un recuadro atrás del menú

        // Mostrar opciones de menú

        agregarMenu();


        spriteCurtisAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/6,ControlJuego.ALTO_CAMARA/2-100,regionCurtisAnimado,actividadJuego.getVertexBufferObjectManager());
        spriteCurtisAnimado.animate(500);
        attachChild(spriteFondo);
        attachChild(spriteCurtisAnimado);
        //agregarCuadroVida();
    }

    /*private void agregarCuadroVida() {
       Rectangle cuadro = new Rectangle(ControlJuego.ANCHO_CAMARA/7, ControlJuego.ALTO_CAMARA/4,
                ControlJuego.ANCHO_CAMARA/10, ControlJuego.ALTO_CAMARA/7, actividadJuego.getVertexBufferObjectManager());
        cuadro.setColor(0.8f, 0.8f, 0.8f, 1);


        attachChild(cuadro);
    }*/

    public void agregaTexto(String s){
        text = new Text(0, 0, font,s, actividadJuego.getVertexBufferObjectManager());
        this.attachChild(text);

        text.setPosition(ControlJuego.ANCHO_CAMARA / 2 - (text.getWidth() / 2), ControlJuego.ALTO_CAMARA / 4 - (text.getHeight() / 2));
    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/7);


        // Crea las opciones (por ahora, acerca de y jugar)
        IMenuItem opcionAttack = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTACK,regionBtnAtk, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionDefense = new ScaleMenuItemDecorator(new SpriteMenuItem(DEFEND,regionBtnDef, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);


        // Agrega las opciones al menú
        menu.addMenuItem(opcionAttack);
        menu.addMenuItem(opcionDefense);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú huhh es (0,0)
        opcionAttack.setPosition(-200, -20);
        opcionDefense.setPosition(200, -20);



        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case ATTACK:

                        menu.clearMenuItems();
                        menu.resetAnimations();
                        agregarMenu2();
                        break;

                    case DEFEND:


                        break;



                }
                return true;
            }
        });
        // Asigna este menú a la escena
        setChildScene(menu);
    }

    private void agregarMenu2(){


        IMenuItem opcionAttack1 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK1,regionBtnAtk1,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        IMenuItem opcionAttack2 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK2,regionBtnAtk2,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        IMenuItem opcionAttack3 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK3,regionBtnAtk3,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        IMenuItem opcionAttack4 = new ScaleMenuItemDecorator(new SpriteMenuItem(SUPER,regionBtnAtk4,actividadJuego.getVertexBufferObjectManager()),1.5f,1);

        menu.addMenuItem(opcionAttack1);
        menu.addMenuItem(opcionAttack2);
        menu.addMenuItem(opcionAttack3);
        menu.addMenuItem(opcionAttack4);

        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        opcionAttack1.setPosition(-500, -20);
        opcionAttack2.setPosition(-200, -20);
        opcionAttack3.setPosition(200, -20);
        opcionAttack4.setPosition(500, -20);

        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch (pMenuItem.getID()) {
                    case ATTCK1:
                        animacionNight();
                        break;

                    case ATTCK2:

                        animacionChoke();
                        break;
                    case ATTCK3:
                        animacionBattack();
                        break;
                    case SUPER:
                        animacionSuper();
                        break;

                }
                return true;
            }
        });


    }
    private void animacionSuper() {
        final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Darkness/Darkness0" + (i) + ".png");
            arrayImagenes.add(i, imagen);

        }


        numImagenes = 0;
        tiempo = new Timer();
        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                        if (numImagenes < 8) {
                            spriteCurtisAnimado.setAlpha(0);

                            if (tagSpriteChild != null)
                                detachChild(tagSpriteChild);

                            spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                            attachChild(spriteFrame);
                            tagSpriteChild = spriteFrame;

                            numImagenes++;
                        } else {

                            reset();
                            crearEscena();
                            numImagenes = 0;
                            tiempo.cancel();
                        }


                    }
                });
            }
        }, 300, 300);
    }
    private void animacionBattack(){
        final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();

        for (int i = 0; i <6 ; i++) {
            ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Battack/Battack0"+(i) + ".png");
            arrayImagenes.add(i,imagen);

        }



        numImagenes= 0;
        tiempo = new Timer();
        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                        if (numImagenes < 6) {
                            spriteCurtisAnimado.setAlpha(0);

                            if(tagSpriteChild!= null)
                                detachChild(tagSpriteChild);

                            spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                            attachChild(spriteFrame);
                            tagSpriteChild = spriteFrame;

                            numImagenes++;
                        } else {

                            reset();
                            crearEscena();
                            numImagenes = 0;
                            tiempo.cancel();
                        }


                    }
                });
            }
        }, 300, 300);

    }
    private void animacionNight(){
        final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();

        for (int i = 0; i <7 ; i++) {
            ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Night/Night0"+(i+1) + ".png");
            arrayImagenes.add(i,imagen);

        }



        numImagenes= 0;
        tiempo = new Timer();
        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                        if (numImagenes < 7) {
                            spriteCurtisAnimado.setAlpha(0);

                            if(tagSpriteChild!= null)
                                detachChild(tagSpriteChild);

                            spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                            attachChild(spriteFrame);
                            tagSpriteChild = spriteFrame;

                            numImagenes++;
                        } else {

                            reset();
                            crearEscena();
                            numImagenes = 0;
                            tiempo.cancel();
                        }


                    }
                });
            }
        }, 300, 300);

    }
    private void animacionChoke(){
        final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();

        for (int i = 0; i <7 ; i++) {
            ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Choke/VampChoke0"+(i+1) + ".png");
            arrayImagenes.add(i,imagen);

        }



        numImagenes= 0;

        tiempo = new Timer();

        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer

                        if(numImagenes<7) {
                            spriteCurtisAnimado.setAlpha(0);

                            if(tagSpriteChild!= null)
                                detachChild(tagSpriteChild);

                            spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                            attachChild(spriteFrame);
                            tagSpriteChild = spriteFrame;

                            numImagenes++;
                        }
                        else{

                            reset();
                            crearEscena();
                            numImagenes = 0;
                            tiempo.cancel();
                        }


                    }
                });
            }
        },300,300);

    }
    // La escena se debe actualizar en este método que se repite "varias" veces por segundo
    // Aquí es donde programan TODA la acción de la escena (movimientos, choques, disparos, etc.)
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        agregaTexto(s);

    }


    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaSeleccionPersonaje();
        admEscenas.setEscena(TipoEscena.ESCENA_SELECCION_PERSONAJE);
        admEscenas.liberarEscenaBatalla();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_BATALLA;
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
    }
}