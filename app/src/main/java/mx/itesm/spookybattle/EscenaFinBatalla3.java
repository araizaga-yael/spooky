package mx.itesm.spookybattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 *
 * @author Roberto Martínez Román
 */


public class EscenaFinBatalla3 extends EscenaBase
{
    // Imágenes
    private ITextureRegion regionWinner;
    private ITextureRegion regionLoser;

    private ITextureRegion regionBtnContinue;
    private ITextureRegion regionLevelUp;
    private ITextureRegion regionCharacterUnlocked;
    private ITextureRegion regionCharacterGeronimo;
    private ITextureRegion regionCharacterFrancis;
    private ITextureRegion regionCharacterGus;



    IMenuItem levelUp;
    IMenuItem Unlock;
    IMenuItem CharacterGeronimo;
    IMenuItem CharacterFrancis;
    IMenuItem CharacterGus;

    // Sprites sobre la escena
    private Sprite SpriteWinner;
    private Sprite SpriteLoser;
    private SpriteBackground fondo;

    private MenuScene menu;

    private final int OPCION_CONTINUE = 9991;
    private final int OPCION_LEVELUP = 1337;
    private final int OPCION_UNLOCK= 1338;
    private final int OPCION_CHARAGER= 1338;
    private final int OPCION_CHARAFRAN= 1339;
    private final int OPCION_CHARAGUS= 13391;



    boolean playerwin;
    boolean didILevelUp;

    int currChar;

    // Carga todos los recursos para ESTA ESCENA.
    @Override
    public void cargarRecursos() {

        regionBtnContinue =cargarImagen("EscenasFinales/BotonContinue.png");
        regionLevelUp=cargarImagen("SelectScreen/BotonLevelUp.png");
        regionCharacterUnlocked=cargarImagen("SelectScreen/BotonCharacter.png");
        regionCharacterGeronimo= cargarImagen("SelectScreen/GeronimoSelected.png");
        regionCharacterFrancis= cargarImagen("SelectScreen/FrancisSelect.png");
        regionCharacterGus =cargarImagen("SelectScreen/GusSelected.png");
    }

    // Arma la escena que se presentará en pantalla
    @Override
    public void crearEscena() {

        SharedPreferences preferencesCurrChar = actividadJuego.getSharedPreferences("CurrentChar", Context.MODE_PRIVATE);
        currChar = preferencesCurrChar.getInt("Currentcharacter",1);

        if(currChar == 1) {
            regionWinner = cargarImagen("EscenasFinales/PantallaGanadora.png");
            regionLoser = cargarImagen("EscenasFinales/PantallaPerdedora.png");
        }

        if(currChar == 2) {
            regionWinner = cargarImagen("EscenasFinales/GeronimoGanador.png");
            regionLoser = cargarImagen("EscenasFinales/GeronimoPerdedor.png");
        }
        else if (currChar == 3){
            regionWinner = cargarImagen("EscenasFinales/FrancisGanador.png");
            regionLoser = cargarImagen("EscenasFinales/FrancisPerdedor.png");
        }
        if(currChar == 4) {
            regionWinner = cargarImagen("EscenasFinales/GusGanador.png");
            regionLoser = cargarImagen("EscenasFinales/GusPerdedor.png");
        }


            SpriteWinner = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionWinner);
            SpriteLoser = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionLoser);

        SharedPreferences preferencesWinner = actividadJuego.getSharedPreferences("winner", Context.MODE_PRIVATE);
        playerwin = preferencesWinner.getBoolean("winner", true);
        didILevelUp = preferencesWinner.getBoolean("didILevelUp", false);

        Log.i("well, did he?", didILevelUp + "");


        if(playerwin == true){
            actividadJuego.reproducirMusica("Musica/06_The_End.ogg", true);
            if(currChar == 1) {
                fondo = new SpriteBackground(0.5f, 0f, 0.49f, SpriteWinner);
            }
            if(currChar == 2) {
                fondo = new SpriteBackground(.8f, .6f, 0f, SpriteWinner);
            }
            if(currChar == 3) {
                fondo = new SpriteBackground(0f, 0.3f, 0f, SpriteWinner);
            }
            if(currChar == 4) {
                fondo = new SpriteBackground(0f, 0f, 0.8f, SpriteWinner);
            }
            setBackground(fondo);

        }
        else{
            actividadJuego.reproducirMusica("Musica/_Victory.ogg", false);

            if(currChar == 1) {
                fondo = new SpriteBackground(0.5f, 0f, 0.49f, SpriteLoser);
            }
            if(currChar == 2) {
                fondo = new SpriteBackground(.8f, 0.6f, 0f, SpriteLoser);
            }
            if(currChar == 3) {
                fondo = new SpriteBackground(0f, 0.3f, 0f, SpriteLoser);
            }
            if(currChar == 4) {
                fondo = new SpriteBackground(0f, 0f, 0.8f, SpriteLoser);
            }
            setBackground(fondo);

        }
        setBackgroundEnabled(true);

        agregaMenu();

    }

    private void agregaMenu(){
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);

        IMenuItem opcionContinue = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_CONTINUE,regionBtnContinue,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        menu.addMenuItem(opcionContinue);
        levelUp = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_LEVELUP,regionLevelUp,actividadJuego.getVertexBufferObjectManager()),1,1);
        menu.addMenuItem(levelUp);
        levelUp.setVisible(false);

        if(didILevelUp == true){
            levelUp.setVisible(true);
        }

        Unlock =new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_UNLOCK,regionCharacterUnlocked,actividadJuego.getVertexBufferObjectManager()),1,1);
        menu.addMenuItem(Unlock);
        Unlock.setVisible(false);

        CharacterGeronimo =new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_CHARAGER,regionCharacterGeronimo,actividadJuego.getVertexBufferObjectManager()),1,1);
        menu.addMenuItem(CharacterGeronimo);
        CharacterGeronimo.setVisible(false);

        CharacterFrancis =new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_CHARAFRAN,regionCharacterFrancis,actividadJuego.getVertexBufferObjectManager()),1,1);
        menu.addMenuItem(CharacterFrancis);
        CharacterFrancis.setVisible(false);

        CharacterGus =new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_CHARAGUS,regionCharacterGus,actividadJuego.getVertexBufferObjectManager()),1,1);
        menu.addMenuItem(CharacterGus);
        CharacterGus.setVisible(false);

        SharedPreferences unlockPreferences = actividadJuego.getSharedPreferences("UnlockedCharacters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = unlockPreferences.edit();
        boolean FrancisUnlocked = unlockPreferences.getBoolean("Francis",false);
        boolean GusUnlocked = unlockPreferences.getBoolean("Gus",false);
        boolean GeronimoUnlocked = unlockPreferences.getBoolean("Geronimo",false);

        if(playerwin == true){
            if(currChar == 1) {
                if(GeronimoUnlocked == false){
                    CharacterGeronimo.setVisible(true);
                    Unlock.setVisible(true);
                    editor.putBoolean("Geronimo", true);
                }
            }
            if(currChar == 2) {
                if(FrancisUnlocked == false){
                    CharacterFrancis.setVisible(true);
                    Unlock.setVisible(true);
                    editor.putBoolean("Francis", true);
                }
            }
            if(currChar == 3) {
                if(GusUnlocked == false){
                    CharacterGus.setVisible(true);
                    Unlock.setVisible(true);
                    editor.putBoolean("Gus", true);
                }
            }
            editor.commit();
        }



        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        opcionContinue.setPosition(450, -240);
        levelUp.setPosition(450, -110);

        CharacterGeronimo.setPosition(-350, -130);
        CharacterFrancis.setPosition(-350, -130);
        CharacterGus.setPosition(-350, -130);
        Unlock.setPosition(-350, 10);

        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_CONTINUE:
                        if(playerwin== true) {
                            admEscenas.crearEscenaSeleccionPersonaje();
                            admEscenas.setEscena(TipoEscena.ESCENA_SELECCION_PERSONAJE);
                            admEscenas.liberarEscenaFinBatalla3();
                        }
                        else if(playerwin== false){
                            admEscenas.crearEscenaBatalla3();
                            admEscenas.setEscena(TipoEscena.ESCENA_BATALLA3);
                            admEscenas.liberarEscenaFinBatalla3();
                        }
                        break;
                }
                return true;
            }
        });
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
        // Regresar al menú principal
        admEscenas.crearEscenaSeleccionPersonaje();
        admEscenas.setEscena(TipoEscena.ESCENA_SELECCION_PERSONAJE);
        admEscenas.liberarEscenaFinBatalla3();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_FIN_BATALLA3;
    }

    // Libera la escena misma del engine
    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria

        // Libera las imágenes
        liberarRecursos();
    }

    // Libera cada una de las regiones asignadas para esta escena
    @Override
    public void liberarRecursos() {
        // Estas dos instrucciones por cada región inicializada
        regionWinner.getTexture().unload();
        regionLoser.getTexture().unload();
        regionWinner = null;
        regionLoser= null;
    }
}
