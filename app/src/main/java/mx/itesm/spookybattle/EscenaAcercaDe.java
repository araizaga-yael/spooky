package mx.itesm.spookybattle;

import android.content.Context;
import android.content.SharedPreferences;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by rmroman on 11/09/15.
 */
public class EscenaAcercaDe extends EscenaBase
{
    // Regiones para imágenes
    private ITextureRegion regionFondo;
    private ITextureRegion regionBtnCheat;
    private ITextureRegion regionBtnReset;


    // Sprite para el fondo
    private Sprite spriteFondo;

    private MenuScene menu;

    private final int OPCION_CHEAT = 707;
    private final int OPCION_RESET = 0;
    private int cont =0;
    private int contr =0;

    private Sound relampagoSonido;


    @Override
    public void cargarRecursos() {
        regionFondo = cargarImagen("AboutUs.png");
        regionBtnCheat = cargarImagen("BotonPlay.png");
        regionBtnReset = cargarImagen("BotonPlay.png");
        relampagoSonido = cargarEfecto("Sonidos/single_lightning_bolt.wav");
    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);
        attachChild(spriteFondo);

        agregaMenu();
    }

    private void agregaMenu(){
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);

        IMenuItem opcionCheat = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_CHEAT,regionBtnCheat,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        menu.addMenuItem(opcionCheat);

        IMenuItem opcionReset = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_RESET,regionBtnReset,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        menu.addMenuItem(opcionReset);


        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        opcionCheat.setPosition(400, -210);
        opcionCheat.setAlpha(0f);

        opcionReset.setPosition(-400, -210);
        opcionReset.setAlpha(0f);

        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_CHEAT:
                        cont++;
                        if(cont == 7){
                            //relampagoSonido.play();
                            unlockAll();
                            levelUpAllLevels();
                        }
                        break;
                    case OPCION_RESET:
                        contr++;
                        if(contr == 7){
                            //relampagoSonido.play();
                            initializeUnlocks();
                            resetAllLevels();

                        }
                        break;
                }
                return true;
            }
        });
        setChildScene(menu);
    }

    private void initializeUnlocks(){
        SharedPreferences unlockPreferences = actividadJuego.getSharedPreferences("UnlockedCharacters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = unlockPreferences.edit();
        editor.putBoolean("Geronimo", false);
        editor.putBoolean("Francis", false);
        editor.putBoolean("Gus", false);
        editor.commit();
    }

    private void unlockAll(){
        SharedPreferences unlockPreferences = actividadJuego.getSharedPreferences("UnlockedCharacters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = unlockPreferences.edit();
        editor.putBoolean("Geronimo", true);
        editor.putBoolean("Francis", true);
        editor.putBoolean("Gus", true);
        editor.commit();
    }
    private void resetAllLevels(){
        SharedPreferences preferences = actividadJuego.getSharedPreferences("levels", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Curtis", 1);
        editor.putInt("Geronimo", 1);
        editor.putInt("Francis", 1);
        editor.putInt("Gus", 1);
        editor.commit();
    }
    private void levelUpAllLevels(){
        SharedPreferences preferences = actividadJuego.getSharedPreferences("levels", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Curtis", 5);
        editor.putInt("Geronimo", 5);
        editor.putInt("Francis", 5);
        editor.putInt("Gus", 5);
        editor.commit();
    }

    @Override
    public void onBackKeyPressed() {
        // Regresar al menú principal
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaAcercaDe();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_ACERCA_DE;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();
        this.dispose();
    }

    @Override
    public void liberarRecursos() {
        regionFondo.getTexture().unload();
        regionFondo = null;
    }
}
