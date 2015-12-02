package mx.itesm.spookybattle;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by rmroman on 11/09/15.
 */
public class EscenaHowTo extends EscenaBase
{
    // Regiones para imágenes
    private ITextureRegion regionFondo;
    private ITextureRegion regionCosas;
    // Sprite para el fondo
    private Sprite spriteFondo;
    private Sprite spriteCosas;

    @Override
    public void cargarRecursos() {
        regionFondo = cargarImagen("instructions/Fondo.png");
        regionCosas = cargarImagen("instructions/HowToPlay.png");
    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);
        attachChild(spriteFondo);
        spriteCosas = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionCosas);
        attachChild(spriteCosas);

        actividadJuego.reproducirMusica("Musica/05_Unstopable_Masacre.ogg", true);

    }

    @Override
    public void onBackKeyPressed() {
        // Regresar al menú principal
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaHowTo();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_HOW_TO;
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
