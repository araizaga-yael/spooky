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


public class EscenaFinBatalla2 extends EscenaBase
{
    // Imágenes
    private ITextureRegion regionWinner;
    private ITextureRegion regionLoser;

    private ITextureRegion regionBtnContinue;
    private ITextureRegion regionLevelUp;
    IMenuItem levelUp;

    // Sprites sobre la escena
    private Sprite SpriteWinner;
    private Sprite SpriteLoser;
    private Sprite spriteFondo;
    private SpriteBackground fondo;

    private MenuScene menu;

    private final int OPCION_CONTINUE = 9991;
    private final int OPCION_LEVELUP = 1337;
    boolean playerwin;
    boolean didILevelUp;



    // Carga todos los recursos para ESTA ESCENA.
    @Override
    public void cargarRecursos() {

        regionBtnContinue =cargarImagen("EscenasFinales/BotonContinue.png");
        regionLevelUp=cargarImagen("SelectScreen/BotonLevelUp.png");
    }

    // Arma la escena que se presentará en pantalla
    @Override
    public void crearEscena() {
        SharedPreferences preferencesCurrChar = actividadJuego.getSharedPreferences("CurrentChar", Context.MODE_PRIVATE);
        int currChar = preferencesCurrChar.getInt("Currentcharacter",1);

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
            actividadJuego.reproducirMusica("Musica/04_Victory.ogg", false);
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

        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        opcionContinue.setPosition(450, -240);
        levelUp.setPosition(450, -110);

        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_CONTINUE:
                        if(playerwin== true) {
                            admEscenas.crearEscenaBatalla3();
                            admEscenas.setEscena(TipoEscena.ESCENA_BATALLA3);
                            admEscenas.liberarEscenaFinBatalla2();
                        }
                        else if(playerwin== false){
                            admEscenas.crearEscenaBatalla2();
                            admEscenas.setEscena(TipoEscena.ESCENA_BATALLA2);
                            admEscenas.liberarEscenaFinBatalla2();
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
        admEscenas.liberarEscenaFinBatalla2();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_FIN_BATALLA2;
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
