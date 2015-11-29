package mx.itesm.spookybattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

/**
 * Representa la escena del MENU PRINCIPAL
 *
 * @author Roberto Martínez Román
 */
public class SeleccionPersonaje extends EscenaBase
{
    // Regiones para las imágenes de la escena
    private ITextureRegion regionFondo;
    private ITextureRegion regionBtnCurtis;
    private ITextureRegion regionBtnGeronimo;
    private ITextureRegion regionBtnFrancisLocked;
    private ITextureRegion regionBtnFrancisUnlocked;


    private ITextureRegion regionImagenCurtis;
    private ITextureRegion regionNombreCurtis;
    private ITextureRegion regionAge;

    private ITextureRegion regionImagenFrancis;
    private ITextureRegion regionSelectTitle;

    private ITextureRegion regionPlay;

    private IMenuItem opcionFrancis;

    private IMenuItem imagenCurtis;
    private IMenuItem imagenFrancis;

    SharedPreferences preferencesCurrChar;
    int currChar;

    // Sprites sobre la escena
    private Sprite spriteFondo;

    // Un menú de tipo SceneMenu
    private MenuScene menu;     // Contenedor de las opciones
    // Constantes para cada opción de personaje
    private final int OPCION_CURTIS = 0;
    private final int OPCION_Geronimo = 1;
    private final int OPCION_Francis = 1;


    private final int OPCION_IMAGEN_CURTIS = 20;
    private final int OPCION_NOMBRE_CURTIS = 30;
    private final int OPCION_AGE = 40;

    private final int OPCION_IMAGEN_FRANCIS = 22;


    private final int OPCION_SELECT_TITLE = 9000;

    private final int OPCION_START = 9999;
    // Botones de cada opción

    //Cosas del texto
    private BitmapTextureAtlas mFontTexture;
    private Text  text;
    private Text  textFrancis;
    private Text  TextcurrCharLevel;
    private Font  fontCurtis;
    private Font  fontFrancis;
    private Font  fontLevel;
    private static String Curtisdesc = "Likes catsup, cramberry juice, and strawberry jam... Among 'other'\n" +
            "red liquids. He's got ancestors from Transylvania. Or so he says \n"+
            "Once, he tripped and accidentally bit someone...He strangely \n" +
            "liked it. His classmates are afraid of him since \n\n"+
            "Curtis is often described as a freak and a weirdo, \n" +
            "seeing as how he is an expert in flying mammals and \n" +
            "gothic horror novels, even for his young age";

    private static String Francisdesc = "Dislikes fire, plasma, and angry mobs... Among 'other'\n" +
            "red liquids. He's got ancestors from Transylvania. Or so he says \n"+
            "Once, he tripped and accidentally bit someone...He strangely \n" +
            "liked it. His classmates are afraid of him since \n\n"+
            "Curtis is often described as a freak and a weirdo, \n" +
            "seeing as how he is an expert in flying mammals and \n" +
            "gothic horror novels, even for his young age";

    @Override
    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("SelectScreen/FondoSelect.png");
        // Botones personajes
        regionBtnCurtis = cargarImagen("SelectScreen/DracoSelect.png");
        regionBtnGeronimo = cargarImagen("SelectScreen/MummySelect.png");
        regionBtnFrancisLocked = cargarImagen("SelectScreen/FrancisUnSelect.png");
        regionBtnFrancisUnlocked = cargarImagen("SelectScreen/FrancisSelect.png");
        //info personajes
        regionImagenCurtis = cargarImagen("SelectScreen/DracoStand.png");
        regionNombreCurtis = cargarImagen("SelectScreen/NameTitle.png");
        regionAge = cargarImagen("SelectScreen/AgeLevel.png");


        regionImagenFrancis =cargarImagen("SelectScreen/FrancisStand.png");


        //Select Character
        regionSelectTitle = cargarImagen("SelectScreen/SelectTitle.png");
        //Boton Play
        regionPlay = cargarImagen("BotonPlay.png");

        //font del texto
        this.mFontTexture = new BitmapTextureAtlas(actividadJuego.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        fontCurtis = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true,Color.parseColor("#FF00FF"));
        fontFrancis = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true,Color.parseColor("#5CD65C"));
        fontLevel = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 65, true,Color.parseColor("#800000"));


        fontCurtis.load();
        fontFrancis.load();
        fontLevel.load();

        currentCharacter(1);
    }

    @Override
    public void crearEscena() {


        // Creamos el sprite de manera óptima
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(1,1,1,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);


        text = new Text(0, 0, fontCurtis,Curtisdesc, actividadJuego.getVertexBufferObjectManager());
        textFrancis = new Text(0, 0, fontFrancis,Francisdesc, actividadJuego.getVertexBufferObjectManager());

        SharedPreferences preferences = actividadJuego.getSharedPreferences("levels", Context.MODE_PRIVATE);
        int CurtisLevel = preferences.getInt("Curtis",1);

        TextcurrCharLevel =new Text(0, 0, fontLevel,CurtisLevel +"", actividadJuego.getVertexBufferObjectManager());

        // Mostrar opciones de menú
        agregarMenu();
        currentCharacter(1);

        preferencesCurrChar = actividadJuego.getSharedPreferences("CurrentChar", Context.MODE_PRIVATE);
        currChar = preferencesCurrChar.getInt("Currentcharacter",1);

            //Log.i("CURRENT CHAR Principio", currChar+"");

    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);

        //Leyendo que personajes estan desbloqueados
        SharedPreferences unlockPreferences = actividadJuego.getSharedPreferences("UnlockedCharacters", Context.MODE_PRIVATE);
        boolean FrancisUnlocked = unlockPreferences.getBoolean("Francis",false);


        // Crea las opciones de personaje
        IMenuItem opcionCurtis = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_CURTIS,regionBtnCurtis, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionGeronimo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_Geronimo,regionBtnGeronimo, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);

        if(FrancisUnlocked == true){
            opcionFrancis = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_Francis, regionBtnFrancisUnlocked, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        }
        else {
            opcionFrancis = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_Francis, regionBtnFrancisLocked, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        }

        //info Curtis
        imagenCurtis = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_IMAGEN_CURTIS,regionImagenCurtis, actividadJuego.getVertexBufferObjectManager()), 1, 1);
        IMenuItem nombreCurtis = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_NOMBRE_CURTIS,regionNombreCurtis,actividadJuego.getVertexBufferObjectManager()),1,1);
        IMenuItem opcionAge = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_AGE,regionAge,actividadJuego.getVertexBufferObjectManager()),1,1);

        //infro Francis
        imagenFrancis = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_IMAGEN_FRANCIS,regionImagenFrancis, actividadJuego.getVertexBufferObjectManager()), 1, 1);


        //Infor general
        IMenuItem opcionSelectTitle = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_SELECT_TITLE,regionSelectTitle,actividadJuego.getVertexBufferObjectManager()),1,1);
        IMenuItem opcionPlay = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_START,regionPlay,actividadJuego.getVertexBufferObjectManager()),1,1);



        // Agrega las opciones de personaje
        menu.addMenuItem(opcionCurtis);
        menu.addMenuItem(opcionGeronimo);
        menu.addMenuItem(opcionFrancis);
        //Curtis info
        menu.addMenuItem(imagenCurtis);
        menu.addMenuItem(nombreCurtis);
        menu.addMenuItem(opcionAge);

        //Francis info
        menu.addMenuItem(imagenFrancis);

        //general
        menu.addMenuItem(opcionSelectTitle);
        menu.addMenuItem(opcionPlay);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú es (0,0)
        opcionCurtis.setPosition(-450,-290);
        opcionGeronimo.setPosition(-250, -290);
        opcionFrancis.setPosition(-50, -290);

        //Curtis
        imagenCurtis.setPosition(450,100);
        nombreCurtis.setPosition(-250,350);
        opcionAge.setPosition(425,-120);

        //Francis
        imagenFrancis.setPosition(450,100);
        imagenFrancis.setVisible(false);

        opcionSelectTitle.setPosition(-290, -175);
        opcionPlay.setPosition(450, -290);


        text.setPosition(370 - (text.getWidth() / 2), 290 - (text.getHeight() / 2));
        menu.attachChild(text);

        textFrancis.setPosition(370 - (text.getWidth() / 2), 290 - (text.getHeight() / 2));
        menu.attachChild(textFrancis);
        textFrancis.setVisible(false);

        TextcurrCharLevel.setPosition(570,-120);
        menu.attachChild(TextcurrCharLevel);



        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_START:
                        // Mostrar la escena de AcercaDe
                        admEscenas.crearEscenaBatalla();
                        admEscenas.setEscena(TipoEscena.ESCENA_BATALLA);
                        admEscenas.liberarEscenaSeleccionPersonaje();
                        break;
                    case OPCION_CURTIS:
                        switchCharacter(currChar);
                        imagenCurtis.setVisible(true);
                        text.setVisible(true);
                        text.setText(Curtisdesc);
                        currentCharacter(1);
                        //Log.i("CURRENT CHAR al presionar Curtis", currChar+"");
                        break;
                    case OPCION_Francis:
                        switchCharacter(currChar);
                        imagenFrancis.setVisible(true);
                        textFrancis.setVisible(true);
                        text.setText(Francisdesc);
                        currentCharacter(3);
                        //Log.i("CURRENT CHAR al presionar Francis", currChar+"");
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
        currChar = preferencesCurrChar.getInt("Currentcharacter",1);
    }

    private void currentCharacter(int i){
        SharedPreferences preferences = actividadJuego.getSharedPreferences("CurrentChar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Currentcharacter", i);
        editor.commit();
    }

    private void switchCharacter(int currChar){

        switch (currChar){
            case 1:
                imagenCurtis.setVisible(false);
                text.setVisible(false);
                break;
            case 3:
                imagenFrancis.setVisible(false);
                textFrancis.setVisible(false);
                break;
        }
    }


    @Override
    public void onBackKeyPressed() {
        // Regresar al menú principal
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaSeleccionPersonaje();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_SELECCION_PERSONAJE;
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
