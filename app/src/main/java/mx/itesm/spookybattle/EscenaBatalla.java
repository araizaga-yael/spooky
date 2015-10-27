package mx.itesm.spookybattle;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;



import android.graphics.Color;
import android.graphics.Typeface;

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

    private ITextureRegion regionBtnHowTo;
    private ITextureRegion regionTitulo;

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

    // Botones de cada opción
    private ButtonSprite btnAtk;
    private ButtonSprite btnDef;

    @Override
    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("Batalla1/FondosBatalla1.png");
        //regionBtnAtk = cargarImagen("BotonPlay.png");
        //regionBtnDef = cargarImagen("BotonPlay.png");
        // Botones de opcion
        /*regionBtnAcercaDe = cargarImagen("BotonAboutUs.png");
        regionBtnJugar = cargarImagen("BotonPlay.png");
        regionBtnHowTo = cargarImagen("BotonHowTo.png");
        regionTitulo = cargarImagen("Titulo.png");*/

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

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(1,1,1,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        /*text = new Text(0, 0, font, "Hello Android", actividadJuego.getVertexBufferObjectManager());
        this.attachChild(text);

        text.setPosition(ControlJuego.ANCHO_CAMARA/2 - (text.getWidth()/2), ControlJuego.ALTO_CAMARA/4 - (text.getHeight()/2));*/

        // Mostrar un recuadro atrás del menú
        //agregarFondoMenu();
        // Mostrar opciones de menú
        agregarMenu();
    }

    /*private void agregarFondoMenu() {
       // Rectangle cuadro = new Rectangle(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2,
         //       0.75f*ControlJuego.ANCHO_CAMARA, 0.75f*ControlJuego.ALTO_CAMARA, actividadJuego.getVertexBufferObjectManager());
        //cuadro.setColor(0.8f, 0.8f, 0.8f, 0.4f);
        //attachChild(cuadro);
    }*/

    public void agregaTexto(String s){
        text = new Text(0, 0, font,s, actividadJuego.getVertexBufferObjectManager());
        this.attachChild(text);

        text.setPosition(ControlJuego.ANCHO_CAMARA/2 - (text.getWidth()/2), ControlJuego.ALTO_CAMARA/4 - (text.getHeight()/2));
    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);


        // Crea las opciones (por ahora, acerca de y jugar)
        //IMenuItem opcionAcercaDe = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_ACERCA_DE,regionBtnAcercaDe, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        //IMenuItem opcionJugar = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_JUGAR,regionBtnJugar, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        //IMenuItem opcionHowTo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_HOW_TO,regionBtnHowTo, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        //IMenuItem titulo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_TITULO,regionTitulo,actividadJuego.getVertexBufferObjectManager()),1.5f,1);


        // Agrega las opciones al menú
        //menu.addMenuItem(opcionAcercaDe);
        //menu.addMenuItem(opcionJugar);
        //menu.addMenuItem(opcionHowTo);
        //menu.addMenuItem(titulo);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú huhh es (0,0)
       // opcionAcercaDe.setPosition(0,-300);
        //opcionJugar.setPosition(0, 0);
        //opcionHowTo.setPosition(0,-150);
        //titulo.setPosition(0,200);


        // Registra el Listener para atender las opciones
        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                   /* case OPCION_START:
                        // Mostrar la escena de AcercaDe
                        admEscenas.crearEscenaBatalla();
                        admEscenas.setEscena(TipoEscena.ESCENA_BATALLA);
                        admEscenas.liberarEscenaSeleccionPersonaje();
                        break;*/
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