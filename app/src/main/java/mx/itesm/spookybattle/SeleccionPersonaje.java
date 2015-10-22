package mx.itesm.spookybattle;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Representa la escena del MENU PRINCIPAL
 *
 * @author Roberto Martínez Román
 */
public class SeleccionPersonaje extends EscenaBase
{
    // Regiones para las imágenes de la escena
    private ITextureRegion regionFondo;
    private ITextureRegion regionBtnAcercaDe;
    private ITextureRegion regionBtnJugar;
    private ITextureRegion regionBtnHowTo;
    private ITextureRegion regionTitulo;
    private ITextureRegion regionAge;
    private ITextureRegion regionInfo;
    private ITextureRegion regionSelectTitle;



    // Sprites sobre la escena
    private Sprite spriteFondo;

    // Un menú de tipo SceneMenu
    private MenuScene menu;     // Contenedor de las opciones
    // Constantes para cada opción
    private final int OPCION_ACERCA_DE = 0;
    private final int OPCION_JUGAR = 1;
    private final int OPCION_HOW_TO = 2;
    private final int OPCION_TITULO = 3;
    private final int OPCION_AGE = 4;
    private final int OPCION_INFO = 5;
    private final int OPCION_SELECT_TITLE = 6;
    // Botones de cada opción
    private ButtonSprite btnAcercaDe;
    private ButtonSprite btnJugar;

    @Override
    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("SelectScreen/FondoSelect.png");
        // Botones del menú
        regionBtnAcercaDe = cargarImagen("SelectScreen/DracoSelect.png");
        regionBtnJugar = cargarImagen("SelectScreen/MummySelect.png");
        regionBtnHowTo = cargarImagen("SelectScreen/DracoStand.png");
        regionTitulo = cargarImagen("SelectScreen/NameTitle.png");
        regionAge = cargarImagen("SelectScreen/AgeLevel.png");
        regionInfo = cargarImagen("SelectScreen/InfoDraco.png");
        regionSelectTitle = cargarImagen("SelectScreen/SelectTitle.png");
    }

    @Override
    public void crearEscena() {


        // Creamos el sprite de manera óptima
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(1,1,1,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        // Mostrar un recuadro atrás del menú
        agregarFondoMenu();
        // Mostrar opciones de menú
        agregarMenu();
    }

    private void agregarFondoMenu() {
       // Rectangle cuadro = new Rectangle(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2,
         //       0.75f*ControlJuego.ANCHO_CAMARA, 0.75f*ControlJuego.ALTO_CAMARA, actividadJuego.getVertexBufferObjectManager());
        //cuadro.setColor(0.8f, 0.8f, 0.8f, 0.4f);
        //attachChild(cuadro);
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
        IMenuItem titulo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_TITULO,regionTitulo,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        IMenuItem opcionAge = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_AGE,regionAge,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        IMenuItem opcionInfo = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_INFO,regionInfo,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        IMenuItem opcionSelectTitle = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_SELECT_TITLE,regionSelectTitle,actividadJuego.getVertexBufferObjectManager()),1.5f,1);



        // Agrega las opciones al menú
        menu.addMenuItem(opcionAcercaDe);
        menu.addMenuItem(opcionJugar);
        menu.addMenuItem(opcionHowTo);
        menu.addMenuItem(titulo);
        menu.addMenuItem(opcionAge);
        menu.addMenuItem(opcionInfo);
        menu.addMenuItem(opcionSelectTitle);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú huhh es (0,0)
        opcionAcercaDe.setPosition(-450,-290);
        opcionJugar.setPosition(-250, -290);
        opcionHowTo.setPosition(450,100);
        titulo.setPosition(-250,350);
        opcionAge.setPosition(425,-120);
        opcionInfo.setPosition(-200,70);
        opcionSelectTitle.setPosition(-290, -175);



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
                        admEscenas.crearSeleccionPersonaje();
                        admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
                        admEscenas.liberarEscenaMenu();
                        break;

                    case OPCION_HOW_TO:
                        admEscenas.crearNuclear();
                        admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
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
