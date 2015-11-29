package mx.itesm.spookybattle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 *
 * @author Roberto Martínez Román
 */


public class EscenaFinBatalla extends EscenaBase
{
    // Imágenes
    private ITextureRegion regionWinner;
    private ITextureRegion regionLoser;

    // Sprites sobre la escena
    private Sprite SpriteWinner;
    private Sprite SpriteLoser;
    private Sprite spriteFondo;


    // Carga todos los recursos para ESTA ESCENA.
    @Override
    public void cargarRecursos() {


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


            SpriteWinner = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionWinner);
            SpriteLoser = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionLoser);

        SharedPreferences preferencesWinner = actividadJuego.getSharedPreferences("winner", Context.MODE_PRIVATE);
        boolean playerwin = preferencesWinner.getBoolean("winner", true);


        if(playerwin == true){
            SpriteBackground fondo = new SpriteBackground(0.28f, 0.63f, 0.92f,SpriteWinner);
            setBackground(fondo);

        }
        else{
            SpriteBackground fondo = new SpriteBackground(0.28f, 0.63f, 0.92f,SpriteLoser);
            setBackground(fondo);

        }
        setBackgroundEnabled(true);

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
        admEscenas.liberarEscenaFinBatalla();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_FIN_BATALLA;
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
