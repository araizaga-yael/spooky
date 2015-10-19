package mx.itesm.spookybattle;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by rmroman on 11/09/15.
 */
public class SeleccionPersonaje extends EscenaBase
{
    // Regiones para imágenes

    private ITextureRegion regionFondo;
    private ITextureRegion regionAgeLevel;
    private ITextureRegion regionDracoSelect;
    private ITextureRegion regionDracoStand;
    private ITextureRegion regionInfoDraco;
    private ITextureRegion regionMummySelect;
    private ITextureRegion regionNameTitle;
    private ITextureRegion regionSelectTitle;
    // Sprite para el fondo
    private Sprite spriteFondo;

    @Override
    public void cargarRecursos() {
        regionFondo = cargarImagen("SelectScreen/FondoSelect.png");
        regionAgeLevel = cargarImagen("SelectScreen/AgeLevel.png");
        regionDracoSelect = cargarImagen("SelectScreen/DracoSelect.png");
        regionDracoStand = cargarImagen("SelectScreen/DracoStand.png");
        regionInfoDraco = cargarImagen("SelectScreen/InfoDraco.png");
        regionMummySelect = cargarImagen("SelectScreen/MummySelect.png");
        regionNameTitle = cargarImagen("SelectScreen/NameTitle.png");
        regionSelectTitle = cargarImagen("SelectScreen/SelectTitle.png");
    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);
        attachChild(spriteFondo);
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
        return TipoEscena.ESCENA_SELECCION_PERSONAJE;
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
