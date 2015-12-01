package mx.itesm.spookybattle;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TiledTextureRegion;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

/**
 * Representa la escena de primer nivel
 *
 *
 */

public class EscenaBatalla extends EscenaBase
{
    // Regiones para las imágenes de la escena
    private ITextureRegion regionFondoCielo;
    private ITextureRegion regionFondoCasas;
    private ITextureRegion regionBtnAtk;
    private ITextureRegion regionBtnDef;
    private ITextureRegion regionBtnAtk1;
    private ITextureRegion regionBtnAtk2;
    private ITextureRegion regionBtnAtk3;
    private ITextureRegion regionBtnAtk4;
    private ITextureRegion regionBtnAtk5;

    private ITextureRegion regionLifeBarPlayer;
    private ITextureRegion regionMagicBarPlayer;
    private ITextureRegion regionLifeBarAI;
    private ITextureRegion regionMagicBarAI;

    private ITextureRegion regionDynamicLifeBarPlayer;
    private ITextureRegion regionDynamicMagicBarPlayer;
    private ITextureRegion regionDynamicLifeBarAI;
    private ITextureRegion regionDynamicMagicBarAI;

    private ITextureRegion regionNubeNegra;

    //SpriteAnimado
    private AnimatedSprite spriteCurtisAnimado;
    private TiledTextureRegion regionCurtisAnimado;

    private AnimatedSprite spriteGeronimoAnimado;
    private TiledTextureRegion regionGeronimoAnimado;

    private AnimatedSprite spriteGeronimoAnimadoPlayer;
    private TiledTextureRegion regionGeronimoAnimadoPlayer;

    private AnimatedSprite spriteFrancisAnimadoPlayer;
    private TiledTextureRegion regionFrancisAnimadoPlayer;

    private AnimatedSprite spriteGusAnimadoPlayer;
    private TiledTextureRegion regionGusAnimadoPlayer;

    private AnimatedSprite spriteGirlAnimado;
    private TiledTextureRegion regionGirlAnimado;




    //cosas de texto
    private BitmapTextureAtlas mFontTexture;
    private Text  text;
    private Font  font;
    public static String s = "";

    private Font  fontCurt;
    private Font  fontGeronimo;
    private Font  fontFrancis;
    private Font  fontGus;

    private Font  fontHP;
    private Font  fontMP;

    private Text  TextPlayerName;
    private Text  TextPlayerHP;
    private Text  TextPlayerMP;

    private Text  TextAIName;
    private Text  TextAIHP;
    private Text  TextAIMP;

    // Sprites sobre la escena
    private Sprite spriteFondoCielo;
    private Sprite spriteFondoCasas;

    // Un menú de tipo SceneMenu
    private MenuScene menu;     // Contenedor de las opciones
    // Constantes para cada opción
    private final int ATTACK = 1;
    private final int DEFEND = 2;
    //Ataques
    private final int ATTCK1 = 1;
    private final int ATTCK2 = 2;
    private final int ATTCK3 = 3;
    private final int ATTCK4 = 4;
    private final int SUPER = 0;
    //Barras
    private final int LIFEBARPLYR = 120;
    private final int MAGICBARPLYR = 220;
    private final int LIFEBARAI = 121;
    private final int MAGICBARAIA = 221;
    private final int DYNLIFEBARPLYR = 130;
    private final int DYNMAGICBARPLYR = 230;
    private final int DYNLIFEBARAI = 131;
    private final int DYNMAGICBARAIA = 231;

    private float universalWidth;

    //Logica del juego /////
    private boolean charChange = false;
    private int attackChoice = 0;
    private boolean winner = false;
    public static boolean playerwin = false;
    private int turn = 0;
    private boolean aiFirst = false;
    private boolean escena1 = true;
    private boolean escena2= false;

    int[] pStats;
    int[] aiStats;
    P_Character player;
    P_Character ai;

    String SplayerName;
    String SAIName;

    String SplayerstartingHP;
    String SAIStartingHP;

    String SplayerHP;
    String SAIHP;

    String SplayerMP;
    String SAIMP;

    //BOTONES
    IMenuItem opcionAttack;
    IMenuItem opcionDefense;

    IMenuItem opcionAttack1;
    IMenuItem opcionAttack2;
    IMenuItem opcionAttack3;
    IMenuItem opcionAttack4;
    IMenuItem opcionSuper;

    //Barras
    IMenuItem LifeBarPlayer;
    IMenuItem MagicBarPlayer;

    IMenuItem LifeBarAI;
    IMenuItem MagicBarAI;

    IMenuItem DynamicLifeBarPlayer;
    IMenuItem DynamicMagicBarPlayer;

    IMenuItem DynamicLifeBarAI;
    IMenuItem DynamicMagicBarAI;

    boolean attacking = false;

    //Animaciones
    private int numImagenes;
    private int numImagenesGeronimo;
    private int numImagenesGeronimoPlayer;
    private int numImagenesFrancisPlayer;
    private int numImagenesGusPlayer;

    private Sprite spriteFrame;
    private Sprite spriteFrameGeronimo;
    private Sprite spriteFrameGeronimoPlayer;
    private Sprite spriteFrameFrancisPlayer;
    private Sprite spriteFrameGusPlayer;
    private IEntity tagSpriteChild;

    private ArrayList<ITextureRegion> arrayImagenes;
    private ArrayList<ITextureRegion> arrayImagenesGeronimo;
    private ArrayList<ITextureRegion> arrayImagenesGeronimoPlayer;
    private ArrayList<ITextureRegion> arrayImagenesFrancisPlayer;
    private ArrayList<ITextureRegion> arrayImagenesGusPlayer;


    SharedPreferences preferencesCurrChar;
    int currChar;

    @Override
    public void cargarRecursos() {

        preferencesCurrChar = actividadJuego.getSharedPreferences("CurrentChar", Context.MODE_PRIVATE);
        currChar = preferencesCurrChar.getInt("Currentcharacter",1);

        Log.i("Personaje Actual", currChar + "");

        // Fondo
        regionFondoCielo = cargarImagen("MenuPrincipal/MenuPrincipalCielo.png");
        regionFondoCasas =  cargarImagen("Batalla1/CasasBatalla1.png");
        regionNubeNegra = cargarImagen("MenuPrincipal/cloud_black.png");

        if(currChar == 1) {
            regionCurtisAnimado = cargarImagenMosaico("AnimacionesCurtis/WaitingBattle/CurtisWaitingSheet.png", 1986, 331, 1, 6);
            regionBtnAtk = cargarImagen("BotonesCurtis/BotonAttack.png");
            regionBtnDef = cargarImagen("BotonesCurtis/BotonDefense.png");

            regionBtnAtk1 = cargarImagen("BotonesCurtis/BotonCurtisAtt4.png");
            regionBtnAtk2 = cargarImagen("BotonesCurtis/BotonCurtisAtt3.png");
            regionBtnAtk3 = cargarImagen("BotonesCurtis/BotonCurtisAtt2.png");
            regionBtnAtk4 = cargarImagen("BotonesCurtis/BotonCurtisAtt1.png");
            regionBtnAtk5 = cargarImagen("BotonesCurtis/BotonSuper.png");
        }
        else if(currChar==2){
            regionGeronimoAnimadoPlayer = cargarImagenMosaico("AnimacionesGeronimo/GeronimoWaitingSheet.png", 2723,389 ,1,7);

            regionCurtisAnimado = cargarImagenMosaico("AnimacionesCurtis/WaitingBattle/CurtisWaitingSheet.png", 1986, 331, 1, 6);
            regionBtnAtk = cargarImagen("BotonesCurtis/BotonAttack.png");
            regionBtnDef = cargarImagen("BotonesCurtis/BotonDefense.png");

            regionBtnAtk1 = cargarImagen("BotonesCurtis/BotonCurtisAtt4.png");
            regionBtnAtk2 = cargarImagen("BotonesCurtis/BotonCurtisAtt3.png");
            regionBtnAtk3 = cargarImagen("BotonesCurtis/BotonCurtisAtt2.png");
            regionBtnAtk4 = cargarImagen("BotonesCurtis/BotonCurtisAtt1.png");
            regionBtnAtk5 = cargarImagen("BotonesCurtis/BotonSuper.png");
        }
        else if(currChar ==3){
            regionFrancisAnimadoPlayer = cargarImagenMosaico("AnimacionesFrancis/FrancisWaitingSheet.png", 2010,337 ,1,6);
            regionBtnAtk = cargarImagen("BotonesFrancis/BotonAttack.png");
            regionBtnDef = cargarImagen("BotonesFrancis/BotonDefense.png");

            regionBtnAtk1 = cargarImagen("BotonesFrancis/BotonFire.png");
            regionBtnAtk2 = cargarImagen("BotonesFrancis/BotonShock.png");
            regionBtnAtk3 = cargarImagen("BotonesFrancis/BotonAlive.png");
            regionBtnAtk4 = cargarImagen("BotonesFrancis/BotonSmash.png");
            regionBtnAtk5 = cargarImagen("BotonesFrancis/BotonSuperFrancis.png");
        }
        else if(currChar ==4){
            regionGusAnimadoPlayer = cargarImagenMosaico("AnimacionesGuss/GusWaitingSheet.png", 2010,337 ,1,6);
        }

        regionGeronimoAnimado =cargarImagenMosaico("AnimacionesGeronimo/GeronimoWaitingSheet.png", 2723, 389, 1, 7);

        regionGirlAnimado = cargarImagenMosaico("Batalla1/GirlSpriteSheet.png", 1026, 171, 1, 6);

        //regionBtnAtk = cargarImagen("BotonesCurtis/BotonAttack.png");
        //regionBtnDef = cargarImagen("BotonesCurtis/BotonDefense.png");

        //regionBtnAtk1 = cargarImagen("BotonesCurtis/BotonCurtisAtt4.png");
        //regionBtnAtk2 = cargarImagen("BotonesCurtis/BotonCurtisAtt3.png");
        //regionBtnAtk3 = cargarImagen("BotonesCurtis/BotonCurtisAtt2.png");
        //regionBtnAtk4 = cargarImagen("BotonesCurtis/BotonCurtisAtt1.png");
        //regionBtnAtk5 = cargarImagen("BotonesCurtis/BotonSuper.png");

        regionLifeBarPlayer = cargarImagen("Batalla1/LifeBarBlack.png");
        regionMagicBarPlayer = cargarImagen("Batalla1/MagicBarBlack.png");
        regionLifeBarAI = cargarImagen("Batalla1/LifeBarBlack.png");
        regionMagicBarAI = cargarImagen("Batalla1/MagicBarBlack.png");

        regionDynamicLifeBarPlayer =cargarImagen("Batalla1/LifeBar.png");
        regionDynamicMagicBarPlayer =cargarImagen("Batalla1/MagicBar.png");
        regionDynamicLifeBarAI =cargarImagen("Batalla1/LifeBar.png");
        regionDynamicMagicBarAI =cargarImagen("Batalla1/MagicBar.png");

        numImagenes = 0;

        this.mFontTexture = new BitmapTextureAtlas(actividadJuego.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true, Color.GREEN);
        fontCurt = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true, Color.parseColor("#FF00FF"));
        fontGeronimo = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true, Color.parseColor("#FFFF4D"));
        fontFrancis = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true,Color.parseColor("#5CD65C"));
        fontGus = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true,Color.parseColor("#80DFFF"));

        fontHP  = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "numbers.ttf", 50, true, Color.RED);
        fontMP = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "numbers.ttf", 50, true, Color.parseColor("#1AC6FF"));
        font.load();
        fontHP.load();
        fontMP.load();

        //IF CURTIS
        fontCurt.load();

        //IF GERONIMO
        fontGeronimo.load();

        //IF FRANCIS
        fontFrancis.load();

        //IF GUS
        fontGus.load();


        if(currChar == 1) {
            player = Main.dracula;
        }
        else if(currChar==2 ){
            player = Main.mummy;
        }
        else if(currChar == 3){
            player = Main.frankenstein;
        }
        else if(currChar == 4){
            player = Main.ghost;
        }
       // Para resetear nivel
        //player.resetLevel();
        //savelevel(player);

        SharedPreferences preferences = actividadJuego.getSharedPreferences("levels", Context.MODE_PRIVATE);
        int playerLvl = preferences.getInt(player.getName(),1);

        for(int ilvl = 1;ilvl <= playerLvl-1; ilvl++){
            player.levelUp();
            Log.i("Level UP", "UP = " + "1");
        }

        Log.i("Leyendo nivel", "Nivel = " + playerLvl);
        Log.i("Nivel real", "Real = " + player.getLvl());

        ai = Main.mummyAI;

        SplayerName = player.toString();
        SAIName = ai.toString();

        //MODIFICAR
        if(ai.getName().toLowerCase().equalsIgnoreCase(player.getName())){SAIName += "?"; }
        ////

        SplayerstartingHP = player.getHP() +"";
        SAIStartingHP = ai.getHP()+ "";

        SplayerHP = player.getHP() +"";
        SAIHP = ai.getHP()+ "";

        SplayerMP = player.getMP()+"";
        SAIMP = ai.getMP()+"";

        initialization(player, ai);
    }

    @Override
    public void crearEscena() {
        turn++;
        if(!winner) {

            attacking = false;

            if (charChange == true) {
                player.setDef(pStats[3]);
                charChange = false;
                checkTurn(player, ai);
            }


            // Creamos el sprite de manera óptima
            spriteFondoCielo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondoCielo);
            spriteFondoCasas = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondoCasas);

            // Mostrar un recuadro atrás del menú

            // Mostrar opciones de menú

            int textLength = 200;
            text = new Text(0, 0, font, s, textLength, actividadJuego.getVertexBufferObjectManager());
            TextPlayerHP = new Text(0, 0, fontHP, SplayerHP + "/" + SplayerstartingHP, textLength, actividadJuego.getVertexBufferObjectManager());
            TextAIHP = new Text(0, 0, fontHP, SAIHP + "/" + SAIStartingHP, textLength, actividadJuego.getVertexBufferObjectManager());
            TextPlayerMP = new Text(0, 0, fontMP, SplayerMP + "/" + player.getbase_MP(), textLength, actividadJuego.getVertexBufferObjectManager());
            TextAIMP = new Text(0, 0, fontMP, SAIMP + "/" + ai.getbase_MP(), textLength, actividadJuego.getVertexBufferObjectManager());

            if(currChar == 1) {
                TextPlayerName = new Text(0, 0, fontCurt, SplayerName, actividadJuego.getVertexBufferObjectManager());
            }
            else if(currChar == 2){
                TextPlayerName = new Text(0, 0, fontGeronimo, SplayerName, actividadJuego.getVertexBufferObjectManager());
            }
            else if(currChar == 3){
                TextPlayerName = new Text(0, 0, fontFrancis, SplayerName, actividadJuego.getVertexBufferObjectManager());
            }
            else if(currChar == 4){
                TextPlayerName = new Text(0, 0, fontGus, SplayerName, actividadJuego.getVertexBufferObjectManager());
            }

            TextAIName = new Text(0, 0, fontGeronimo, SAIName, actividadJuego.getVertexBufferObjectManager());

            agregarMenu();
            attachChild(spriteFondoCielo);
            //agregarHumo();
            //agregarHumo2();
            attachChild(spriteFondoCasas);

            if(currChar ==1) {
                spriteCurtisAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 6, ControlJuego.ALTO_CAMARA / 2 - 100, regionCurtisAnimado, actividadJuego.getVertexBufferObjectManager());
                spriteCurtisAnimado.animate(500);
                attachChild(spriteCurtisAnimado);
            }
            else if (currChar == 2){
                spriteGeronimoAnimadoPlayer =new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 6, ControlJuego.ALTO_CAMARA / 2 - 100, regionGeronimoAnimadoPlayer, actividadJuego.getVertexBufferObjectManager());
                spriteGeronimoAnimadoPlayer.animate(500);
                spriteGeronimoAnimadoPlayer.setFlippedHorizontal(true);
                attachChild(spriteGeronimoAnimadoPlayer);
            }
            else if (currChar == 3){
                spriteFrancisAnimadoPlayer =new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 6, ControlJuego.ALTO_CAMARA / 2 - 100, regionFrancisAnimadoPlayer, actividadJuego.getVertexBufferObjectManager());
                spriteFrancisAnimadoPlayer.animate(500);
                spriteFrancisAnimadoPlayer.setFlippedHorizontal(true);
                attachChild(spriteFrancisAnimadoPlayer);
            }
            else if (currChar == 4){
                spriteGusAnimadoPlayer =new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 6, ControlJuego.ALTO_CAMARA / 2 - 100, regionGusAnimadoPlayer, actividadJuego.getVertexBufferObjectManager());
                spriteGusAnimadoPlayer.animate(500);
                spriteGusAnimadoPlayer.setFlippedHorizontal(true);
                attachChild(spriteGusAnimadoPlayer);
            }

            spriteGeronimoAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 2 + 350, ControlJuego.ALTO_CAMARA / 2 - 80, regionGeronimoAnimado, actividadJuego.getVertexBufferObjectManager());
            spriteGeronimoAnimado.animate(500);
            attachChild(spriteGeronimoAnimado);

        }

        spriteGirlAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/2-110,ControlJuego.ALTO_CAMARA/2-120,regionGirlAnimado, actividadJuego.getVertexBufferObjectManager());
        spriteGirlAnimado.animate(500);
        attachChild(spriteGirlAnimado);
    }   //agregarCuadroVida();

    private void agregarHumo() {

        CircleParticleEmitter circulo = new CircleParticleEmitter(ControlJuego.ANCHO_CAMARA/5,
                ControlJuego.ALTO_CAMARA,10);
        BatchedSpriteParticleSystem sistema = new BatchedSpriteParticleSystem(circulo,
                20, 30, 200, regionNubeNegra, actividadJuego.getVertexBufferObjectManager());

        // Velocidad de las partículas minX, maxX, minY, maxY
        sistema.addParticleInitializer(new VelocityParticleInitializer<UncoloredSprite>(50,20,20,-20));
        // Aceleración
        sistema.addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(0,0));

        float tiempoVida = 15;   // Segundos de vida de cada partícula
        // Tiempo para que las partículas expiren.
        sistema.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(tiempoVida));
        // Escala
        sistema.addParticleInitializer(new ScaleParticleInitializer<UncoloredSprite>(0.5f, 1.0f));
        // Rotación
        //sistema.addParticleModifier(new RotationParticleModifier<UncoloredSprite>(1, 4, 0, 360));
        // Alpha de las partículas, recibe el rango de tiempo y el rango de alpha
        sistema.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(tiempoVida - 2, tiempoVida + 1, 1, 0.3f));

        // Se agrega a la escena, como cualquier Sprite
        attachChild(sistema);
    }

    private void agregarHumo2() {

        CircleParticleEmitter circulo = new CircleParticleEmitter(ControlJuego.ANCHO_CAMARA/5,
                ControlJuego.ALTO_CAMARA/2+200,10);
        BatchedSpriteParticleSystem sistema = new BatchedSpriteParticleSystem(circulo,
                20, 30, 200, regionNubeNegra, actividadJuego.getVertexBufferObjectManager());

        // Velocidad de las partículas minX, maxX, minY, maxY
        sistema.addParticleInitializer(new VelocityParticleInitializer<UncoloredSprite>(50,20,20,-20));
        // Aceleración
        sistema.addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(0,0));

        float tiempoVida = 15;   // Segundos de vida de cada partícula
        // Tiempo para que las partículas expiren.
        sistema.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(tiempoVida));
        // Escala
        sistema.addParticleInitializer(new ScaleParticleInitializer<UncoloredSprite>(0.5f, 1.0f));
        // Rotación
        //sistema.addParticleModifier(new RotationParticleModifier<UncoloredSprite>(1, 4, 0, 360));
        // Alpha de las partículas, recibe el rango de tiempo y el rango de alpha
        sistema.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(tiempoVida - 2, tiempoVida + 1, 1, 0.3f));

        // Se agrega a la escena, como cualquier Sprite
        attachChild(sistema);
    }
    private void savelevel(P_Character player) {
        SharedPreferences preferences = actividadJuego.getSharedPreferences("levels", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(player.getName(), player.getLvl());
        editor.commit();
    }


    /*private void agregarCuadroVida() {
       Rectangle cuadro = new Rectangle(ControlJuego.ANCHO_CAMARA/7, ControlJuego.ALTO_CAMARA/4,
                ControlJuego.ANCHO_CAMARA/10, ControlJuego.ALTO_CAMARA/7, actividadJuego.getVertexBufferObjectManager());
        cuadro.setColor(0.8f, 0.8f, 0.8f, 1);


        attachChild(cuadro);
    }*/

    public void agregaTexto(String s){
        text.setText(s);
        this.attachChild(text);

        if(winner == false) {
            TextPlayerHP.setText(player.getHP() + "/" + SplayerstartingHP);
            TextAIHP.setText(ai.getHP() + "/" + SAIStartingHP);

            TextPlayerMP.setText(player.getMP() + "/" + player.getbase_MP());
            TextAIMP.setText(ai.getMP() + "/" + ai.getbase_MP());
        }
        else{
            TextPlayerHP.setText("");
            TextAIHP.setText("");

            TextPlayerMP.setText("");
            TextAIMP.setText("");

        }

        this.attachChild(TextPlayerHP);
        this.attachChild(TextAIHP);
        this.attachChild(TextPlayerMP);
        this.attachChild(TextAIMP);

    }

    private void agregarMenu() {
        escena2 = false;
        escena1 = true;

        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/7);

        if(turn == 1) {
            s = "Choose An Action";
        }

        opcionAttack = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTACK,regionBtnAtk, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        opcionDefense = new ScaleMenuItemDecorator(new SpriteMenuItem(DEFEND,regionBtnDef, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);

        // Agrega las opciones al menú
        menu.addMenuItem(opcionAttack);
        menu.addMenuItem(opcionDefense);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú huhh es (0,0)
        opcionAttack.setPosition(-200, -20);
        opcionDefense.setPosition(200, -20);

        TextPlayerName.setPosition(-320 - (TextPlayerName.getWidth() / 2), 520 - (TextPlayerName.getHeight() / 2));
        TextAIName.setPosition(460 - (TextAIName.getWidth() / 2), 520 - (TextAIName.getHeight() / 2));

        //Barras de vida y magica
        LifeBarPlayer = new ScaleMenuItemDecorator(new SpriteMenuItem(LIFEBARPLYR,regionLifeBarPlayer, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        LifeBarPlayer.setScale(.6f);
        LifeBarPlayer.setPosition(-450, 470);
        menu.attachChild(LifeBarPlayer);
        MagicBarPlayer =  new ScaleMenuItemDecorator(new SpriteMenuItem(MAGICBARPLYR,regionMagicBarPlayer, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        MagicBarPlayer.setScale(.6f);
        MagicBarPlayer.setPosition(-450, 425);
        menu.attachChild(MagicBarPlayer);

        LifeBarAI = new ScaleMenuItemDecorator(new SpriteMenuItem(LIFEBARAI,regionLifeBarAI, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        LifeBarAI.setScale(.6f);
        LifeBarAI.setPosition(310, 470);
        menu.attachChild(LifeBarAI);
        MagicBarAI =  new ScaleMenuItemDecorator(new SpriteMenuItem(MAGICBARAIA,regionMagicBarAI, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        MagicBarAI.setScale(.6f);
        MagicBarAI.setPosition(310, 425);
        menu.attachChild(MagicBarAI);

        DynamicLifeBarPlayer = new ScaleMenuItemDecorator(new SpriteMenuItem(DYNLIFEBARPLYR,regionDynamicLifeBarPlayer, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        DynamicLifeBarPlayer.setScale(.6f);
        DynamicLifeBarPlayer.setPosition(-450, 458);
        menu.attachChild(DynamicLifeBarPlayer);
        DynamicMagicBarPlayer =  new ScaleMenuItemDecorator(new SpriteMenuItem(DYNMAGICBARPLYR,regionDynamicMagicBarPlayer, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        DynamicMagicBarPlayer.setScale(.6f);
        DynamicMagicBarPlayer.setPosition(-450, 413);
        menu.attachChild(DynamicMagicBarPlayer);

        DynamicLifeBarAI = new ScaleMenuItemDecorator(new SpriteMenuItem(DYNLIFEBARAI,regionDynamicLifeBarAI, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        DynamicLifeBarAI.setScale(.6f);
        DynamicLifeBarAI.setPosition(310, 458);
        menu.attachChild(DynamicLifeBarAI);
        DynamicMagicBarAI =  new ScaleMenuItemDecorator(new SpriteMenuItem(DYNMAGICBARAIA,regionDynamicMagicBarAI, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        DynamicMagicBarAI.setScale(.6f);
        DynamicMagicBarAI.setPosition(310, 413);
        menu.attachChild(DynamicMagicBarAI);

        ///POINTER
        /*
        float prevPos = DynamicLifeBarPlayer.getX();
        DynamicLifeBarPlayer.setWidth(479f /2);
        DynamicLifeBarPlayer.setX(prevPos - (479f /2));
        */
        if(turn == 1) {
            universalWidth = DynamicMagicBarPlayer.getWidth();
        }

        menu.attachChild(TextPlayerName);
        menu.attachChild(TextAIName);


        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch (pMenuItem.getID()) {
                    case ATTACK:
                        //Esto solo se usa una vez
                        if(turn == 1) turn++;

                        //accion del boton
                        menu.clearMenuItems();
                        menu.resetAnimations();
                        agregarMenu2();
                        attackChoice = pMenuItem.getID();
                        s = "Choose An Attack";
                        checkTurn(player,ai);
                        break;

                    case DEFEND:
                        //Esto solo se usa una vez
                        if(turn == 1) turn++;
                        //accion del boton
                        defend(player);
                        hideButtons();
                        s = player.getName() + " Defended";
                        aiFirst = false;
                        aiMove(player, ai);
                        break;


                }
                return true;
            }
        });
        // Asigna este menú a la escena
        setChildScene(menu);
    }

    private void agregarMenu2(){
        boolean enoughMP = false;
        escena1 = false;
        escena2= true;


        opcionAttack1 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK1,regionBtnAtk1,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionAttack2 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK2,regionBtnAtk2,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionAttack3 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK3,regionBtnAtk3,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionAttack4 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK4,regionBtnAtk4,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionSuper = new ScaleMenuItemDecorator(new SpriteMenuItem(SUPER,regionBtnAtk5,actividadJuego.getVertexBufferObjectManager()),1.5f,1);

        menu.addMenuItem(opcionAttack1);
        menu.addMenuItem(opcionAttack2);
        menu.addMenuItem(opcionAttack3);
        menu.addMenuItem(opcionAttack4);
        menu.addMenuItem(opcionSuper);

        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        opcionAttack1.setPosition(-500, -20);
        opcionAttack2.setPosition(-200, -20);
        opcionAttack3.setPosition(200, -20);
        opcionAttack4.setPosition(500, -20);
        opcionSuper.setPosition(0, 150);

        if (player.getMP() != player.getbase_MP()){
            opcionSuper.setVisible(false);
        }

        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida

                switch (pMenuItem.getID()) {
                    case ATTCK1:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) {
                            aiMove(player, ai);
                        }
                        else {
                            playerMove(player, ai, attackChoice);
                        }
                        break;

                    case ATTCK2:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) {
                            aiMove(player, ai);
                        }
                        else {
                            playerMove(player, ai, attackChoice);
                        }
                        break;

                    case ATTCK3:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) {
                            aiMove(player, ai);
                        }
                        else {
                            playerMove(player, ai, attackChoice);
                        }
                        break;
                    case ATTCK4:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) {
                            aiMove(player, ai);
                        }
                        else {
                            playerMove(player, ai, attackChoice);
                        }
                        break;
                    case SUPER:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) {
                            aiMove(player, ai);
                        }
                        else {
                            playerMove(player, ai, attackChoice);
                        }
                        break;

                }

                return true;
            }
        });


    }

    private void getImagesGeronimo(int opcionAtaque)
    {
        arrayImagenesGeronimo = new ArrayList<>();
        switch(opcionAtaque){

            case 1:
                for (int i = 0; i < 10; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/LotusPocus/Geronimo0" + (i) + ".png");
                    arrayImagenesGeronimo.add(i, imagen);
                }
                break;

            case 2:
                for (int i = 0; i < 10; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/Momify/Momify0" + (i) + ".png");
                    arrayImagenesGeronimo.add(i, imagen);
                }
                break;
            case 3:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/Anubis/Anubis" + (i + 1) + ".png");
                    arrayImagenesGeronimo.add(i, imagen);
                }
                break;
            case 4:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Darkness/Darkness0" + (i+1) + ".png");
                    arrayImagenesGeronimo.add(i, imagen);

                }
                break;
            case 5:
                for (int i = 0; i < 11; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/Plague/SuperGer0" + (i) + ".png");
                    arrayImagenesGeronimo.add(i, imagen);

                }
                break;

        }
    }



    private void animacionLotusAI(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                if (numImagenesGeronimo < 10) {
                    spriteGeronimoAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimo.get(numImagenesGeronimo));
                    attachChild(spriteFrameGeronimo);
                    tagSpriteChild = spriteFrameGeronimo;

                    numImagenesGeronimo++;
                    animacionLotusAI();
                } else {

                    for (int i = 0; i < 10; i++) {

                        arrayImagenesGeronimo.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimo.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == true){
                        playerMove(player, ai, attackChoice);
                        crearEscena();
                        hideButtons();
                    }
                    else {
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimo = 0;
                }


            }
        }));
    }

    private void animacionMomifyAI(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGeronimo < 10) {
                    spriteGeronimoAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimo.get(numImagenesGeronimo));
                    attachChild(spriteFrameGeronimo);
                    tagSpriteChild = spriteFrameGeronimo;

                    numImagenesGeronimo++;
                    animacionMomifyAI();
                } else {

                    for (int i = 0; i < 10; i++) {

                        arrayImagenesGeronimo.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimo.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == true){
                        playerMove(player, ai, attackChoice);
                        crearEscena();
                        hideButtons();
                    }
                    else {
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimo = 0;
                }


            }
        }));
    }

    private void animacionAnubisAI(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGeronimo < 8) {
                    spriteGeronimoAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimo.get(numImagenesGeronimo));
                    attachChild(spriteFrameGeronimo);
                    tagSpriteChild = spriteFrameGeronimo;

                    numImagenesGeronimo++;
                    animacionAnubisAI();
                } else {

                    for (int i = 0; i < 8; i++) {
                        arrayImagenesGeronimo.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimo.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == true){
                        playerMove(player, ai, attackChoice);
                        crearEscena();
                        hideButtons();
                    }
                    else {
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimo = 0;
                }


            }
        }));
    }

    private void animacionPlagueAI(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGeronimo < 11) {
                    spriteGeronimoAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimo.get(numImagenesGeronimo));
                    attachChild(spriteFrameGeronimo);
                    tagSpriteChild = spriteFrameGeronimo;

                    numImagenesGeronimo++;
                    animacionPlagueAI();
                } else {

                    for (int i = 0; i < 11; i++) {
                        arrayImagenesGeronimo.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimo.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == true){
                        playerMove(player, ai, attackChoice);
                        crearEscena();
                        hideButtons();
                    }
                    else {
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimo = 0;
                }


            }
        }));
    }


    ///ANIMACIONES CURTIS/////
    private void getImagesCurtis(int opcionAtaque)
    {
        arrayImagenes = new ArrayList<>();
        switch(opcionAtaque){

            case 1:
                for (int i = 0; i < 6; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Battack/Battack0" + (i) + ".png");
                    arrayImagenes.add(i, imagen);
                }
                break;

            case 2:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Night/Night0" + (i + 1) + ".png");
                    arrayImagenes.add(i, imagen);
                }
                break;
            case 3:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Choke/VampChoke0" + (i + 1) + ".png");
                    arrayImagenes.add(i, imagen);
                }
                break;
            case 4:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Darkness/Darkness0" + (i+1) + ".png");
                    arrayImagenes.add(i, imagen);

                }
                break;
            case 5:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Super/Super0" + (i) + ".png");
                    arrayImagenes.add(i, imagen);

                }
                break;

        }
    }
    private void animacionSuperCurtis() {

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenes < 8) {
                    spriteCurtisAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                    attachChild(spriteFrame);
                    tagSpriteChild = spriteFrame;

                    numImagenes++;
                    animacionSuperCurtis();
                } else {

                    if(arrayImagenes.size()!=0) {
                        for (int i = 0; i < 8; i++) {

                            arrayImagenes.get(i).getTexture().unload();
                        }
                        arrayImagenes.clear();
                        reset();
                        checkHP(player,ai);

                        if(aiFirst == false) {
                            aiMove(player, ai);
                            crearEscena();
                            hideButtons();
                        }
                        else{
                            crearEscena();
                            s="Choose an action";
                        }
                        numImagenes = 0;

                    }
                }

            }
            }));

    }
    private void animacionBattack(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                    if (numImagenes < 6) {
                        spriteCurtisAnimado.setAlpha(0);

                        if (tagSpriteChild != null)
                            detachChild(tagSpriteChild);

                        spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                        attachChild(spriteFrame);
                        tagSpriteChild = spriteFrame;

                        numImagenes++;
                        animacionBattack();
                    } else {

                        if(arrayImagenes.size()!=0) {
                            for (int i = 0; i < 6; i++) {

                                arrayImagenes.get(i).getTexture().unload();
                            }
                            arrayImagenes.clear();
                            reset();
                            checkHP(player,ai);

                            if(aiFirst == false) {
                                aiMove(player, ai);
                                crearEscena();
                                hideButtons();
                            }
                            else{
                                crearEscena();
                                s="Choose an action";
                            }
                            numImagenes = 0;

                        }
                    }


                }
            }));
        }

    private void animacionNight(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                        if (numImagenes < 7) {
                            spriteCurtisAnimado.setAlpha(0);

                            if (tagSpriteChild != null)
                                detachChild(tagSpriteChild);

                            spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                            attachChild(spriteFrame);
                            tagSpriteChild = spriteFrame;

                            numImagenes++;
                            animacionNight();
                        } else {

                            for (int i = 0; i < 7; i++) {

                                arrayImagenes.get(i).getTexture().unload();
                            }
                            arrayImagenes.clear();
                            reset();
                            checkHP(player,ai);

                            if(aiFirst == false) {
                                aiMove(player, ai);
                                crearEscena();
                                hideButtons();
                            }
                            else{
                                crearEscena();
                                s="Choose an action";
                            }
                            numImagenes = 0;

                        }


                    }
                }));

    }
    private void animacionChoke() {

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer

                if (numImagenes < 7) {
                    spriteCurtisAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                    attachChild(spriteFrame);
                    tagSpriteChild = spriteFrame;

                    numImagenes++;
                    animacionChoke();
                } else {
                    for (int i = 0; i < 7; i++) {

                        arrayImagenes.get(i).getTexture().unload();
                    }
                    arrayImagenes.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenes = 0;

                }


            }
        }));

    }
    private void animacionDarkness() {

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer

                if (numImagenes < 7) {
                    spriteCurtisAnimado.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                    attachChild(spriteFrame);
                    tagSpriteChild = spriteFrame;

                    numImagenes++;
                    animacionChoke();
                } else {
                    for (int i = 0; i < 7; i++) {

                        arrayImagenes.get(i).getTexture().unload();
                    }
                    arrayImagenes.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }

                    numImagenes = 0;

                }


            }
        }));

    }

    ///Animaciones Geronimo Player////
    private void getImagesGeronimoPlayer(int opcionAtaque)
    {
        arrayImagenesGeronimoPlayer = new ArrayList<>();
        switch(opcionAtaque){

            case 1:
                for (int i = 0; i < 10; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/LotusPocus/Geronimo0" + (i) + ".png");
                    arrayImagenesGeronimoPlayer.add(i, imagen);
                }
                break;

            case 2:
                for (int i = 0; i < 10; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/Momify/Momify0" + (i) + ".png");
                    arrayImagenesGeronimoPlayer.add(i, imagen);
                }
                break;
            case 3:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/Anubis/Anubis" + (i + 1) + ".png");
                    arrayImagenesGeronimoPlayer.add(i, imagen);
                }
                break;
            case 4:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Darkness/Darkness0" + (i+1) + ".png");
                    arrayImagenesGeronimoPlayer.add(i, imagen);

                }
                break;
            case 5:
                for (int i = 0; i < 11; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGeronimo/Plague/SuperGer0" + (i) + ".png");
                    arrayImagenesGeronimoPlayer.add(i, imagen);

                }
                break;

        }
    }

    private void animacionPlaguePlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGeronimoPlayer < 11) {
                    spriteGeronimoAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimoPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimoPlayer.get(numImagenesGeronimoPlayer));
                    spriteFrameGeronimoPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameGeronimoPlayer);
                    tagSpriteChild = spriteFrameGeronimoPlayer;

                    numImagenesGeronimoPlayer++;
                    animacionPlaguePlayer();
                } else {

                    for (int i = 0; i < 11; i++) {
                        arrayImagenesGeronimoPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimoPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimoPlayer = 0;
                }


            }
        }));
    }


    private void animacionLotusPlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                if (numImagenesGeronimoPlayer < 10) {
                    spriteGeronimoAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimoPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimoPlayer.get(numImagenesGeronimoPlayer));
                    spriteFrameGeronimoPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameGeronimoPlayer);
                    tagSpriteChild = spriteFrameGeronimoPlayer;

                    numImagenesGeronimoPlayer++;
                    animacionLotusPlayer();
                } else {

                    for (int i = 0; i < 10; i++) {

                        arrayImagenesGeronimoPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimoPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimoPlayer = 0;
                }


            }
        }));
    }

    private void animacionMomifyPlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGeronimoPlayer < 10) {
                    spriteGeronimoAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimoPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimoPlayer.get(numImagenesGeronimoPlayer));
                    spriteFrameGeronimoPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameGeronimoPlayer);
                    tagSpriteChild = spriteFrameGeronimoPlayer;

                    numImagenesGeronimoPlayer++;
                    animacionMomifyPlayer();
                } else {

                    for (int i = 0; i < 10; i++) {

                        arrayImagenesGeronimoPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimoPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimoPlayer = 0;
                }


            }
        }));
    }

    private void animacionAnubisPlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGeronimoPlayer < 8) {
                    spriteGeronimoAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGeronimoPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGeronimoPlayer.get(numImagenesGeronimoPlayer));
                    spriteFrameGeronimoPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameGeronimoPlayer);
                    tagSpriteChild = spriteFrameGeronimoPlayer;

                    numImagenesGeronimoPlayer++;
                    animacionAnubisPlayer();
                } else {

                    for (int i = 0; i < 8; i++) {
                        arrayImagenesGeronimoPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesGeronimoPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGeronimoPlayer = 0;
                }


            }
        }));
    }

    ///Animaciones Francis Player///

    private void getImagesFrancisPlayer(int opcionAtaque)
    {
        arrayImagenesFrancisPlayer = new ArrayList<>();
        switch(opcionAtaque){

            case 1:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesFrancis/Fire/Fire0" + (i) + ".png");
                    arrayImagenesFrancisPlayer.add(i, imagen);
                }
                break;

            case 2:
                for (int i = 0; i < 9; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesFrancis/Lightning/Lightning0" + (i) + ".png");
                    arrayImagenesFrancisPlayer.add(i, imagen);
                }
                break;
            case 3:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesFrancis/LightningBolt/LigthningBolt0" + (i) + ".png");
                    arrayImagenesFrancisPlayer.add(i, imagen);
                }
                break;
            case 4:
                for (int i = 0; i < 6; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesFrancis/Rocks/Rocks0" + (i) + ".png");
                    arrayImagenesFrancisPlayer.add(i, imagen);

                }
                break;
            case 5:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesFrancis/Super/Super0" + (i) + ".png");
                    arrayImagenesFrancisPlayer.add(i, imagen);

                }
                break;

        }
    }

    private void animacionStrikePlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesFrancisPlayer< 8) {
                    spriteFrancisAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameFrancisPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesFrancisPlayer.get(numImagenesFrancisPlayer));
                    spriteFrameFrancisPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameFrancisPlayer);
                    tagSpriteChild = spriteFrameFrancisPlayer;

                    numImagenesFrancisPlayer++;
                    animacionStrikePlayer();
                } else {

                    for (int i = 0; i < 8; i++) {
                        arrayImagenesFrancisPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesFrancisPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesFrancisPlayer = 0;
                }


            }
        }));
    }

    private void animacionFirePlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesFrancisPlayer< 7) {
                    spriteFrancisAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameFrancisPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesFrancisPlayer.get(numImagenesFrancisPlayer));
                    spriteFrameFrancisPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameFrancisPlayer);
                    tagSpriteChild = spriteFrameFrancisPlayer;

                    numImagenesFrancisPlayer++;
                    animacionFirePlayer();
                } else {

                    for (int i = 0; i < 7; i++) {
                        arrayImagenesFrancisPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesFrancisPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesFrancisPlayer = 0;
                }


            }
        }));
    }

    private void animacionAlivePlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesFrancisPlayer< 9) {
                    spriteFrancisAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameFrancisPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesFrancisPlayer.get(numImagenesFrancisPlayer));
                    spriteFrameFrancisPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameFrancisPlayer);
                    tagSpriteChild = spriteFrameFrancisPlayer;

                    numImagenesFrancisPlayer++;
                    animacionAlivePlayer();
                } else {

                    for (int i = 0; i < 9; i++) {
                        arrayImagenesFrancisPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesFrancisPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesFrancisPlayer = 0;
                }


            }
        }));
    }
    private void animacionShockPlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesFrancisPlayer< 8) {
                    spriteFrancisAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameFrancisPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesFrancisPlayer.get(numImagenesFrancisPlayer));
                    spriteFrameFrancisPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameFrancisPlayer);
                    tagSpriteChild = spriteFrameFrancisPlayer;

                    numImagenesFrancisPlayer++;
                    animacionShockPlayer();
                } else {

                    for (int i = 0; i < 8; i++) {
                        arrayImagenesFrancisPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesFrancisPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesFrancisPlayer = 0;
                }


            }
        }));
    }

    private void animacionSmashPlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesFrancisPlayer< 6) {
                    spriteFrancisAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameFrancisPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesFrancisPlayer.get(numImagenesFrancisPlayer));
                    spriteFrameFrancisPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameFrancisPlayer);
                    tagSpriteChild = spriteFrameFrancisPlayer;

                    numImagenesFrancisPlayer++;
                    animacionSmashPlayer();
                } else {

                    for (int i = 0; i < 6; i++) {
                        arrayImagenesFrancisPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesFrancisPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesFrancisPlayer = 0;
                }


            }
        }));
    }

    ///Animaciones Gus Player///

    private void getImagesGusPlayer(int opcionAtaque)
    {
        arrayImagenesGusPlayer = new ArrayList<>();
        switch(opcionAtaque){

            case 1:
                for (int i = 0; i < 7; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGus/Boom/Boom0" + (i) + ".png");
                    arrayImagenesGusPlayer.add(i, imagen);
                }
                break;

            case 2:
                for (int i = 0; i < 9; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGus/Booty/Booty0" + (i) + ".png");
                    arrayImagenesGusPlayer.add(i, imagen);
                }
                break;
            case 3:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGus/Boogaloo/Boogaloo0" + (i) + ".png");
                    arrayImagenesGusPlayer.add(i, imagen);
                }
                break;
            case 4:
                for (int i = 0; i < 6; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGus/Taboo/Taboo0" + (i) + ".png");
                    arrayImagenesGusPlayer.add(i, imagen);

                }
                break;
            case 5:
                for (int i = 0; i < 8; i++) {
                    ITextureRegion imagen = cargarImagen("AnimacionesGus/Super/Super0" + (i) + ".png");
                    arrayImagenesGusPlayer.add(i, imagen);

                }
                break;

        }
    }

    private void animacionBoomPlayer(){

        registerUpdateHandler(new TimerHandler(0.3f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (numImagenesGusPlayer< 6) {
                    spriteGusAnimadoPlayer.setAlpha(0);

                    if (tagSpriteChild != null)
                        detachChild(tagSpriteChild);

                    spriteFrameGusPlayer = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenesGusPlayer.get(numImagenesGusPlayer));
                    spriteFrameGusPlayer.setFlippedHorizontal(true);
                    attachChild(spriteFrameGusPlayer);
                    tagSpriteChild = spriteFrameGusPlayer;

                    numImagenesFrancisPlayer++;
                    animacionBoomPlayer();
                } else {

                    for (int i = 0; i < 6; i++) {
                        arrayImagenesGusPlayer.get(i).getTexture().unload();
                    }
                    arrayImagenesGusPlayer.clear();
                    reset();
                    checkHP(player,ai);

                    if(aiFirst == false) {
                        aiMove(player, ai);
                        crearEscena();
                        hideButtons();
                    }
                    else{
                        crearEscena();
                        s="Choose an action";
                    }
                    numImagenesGusPlayer = 0;
                }


            }
        }));
    }


    // La escena se debe actualizar en este método que se repite "varias" veces por segundo
    // Aquí es donde programan TODA la acción de la escena (movimientos, choques, disparos, etc.)
    @Override
    protected void onManagedUpdate(float pSecondsElapsed){
        super.onManagedUpdate(pSecondsElapsed);

        if(!winner) {
            this.detachChild(text);
            this.detachChild(TextPlayerHP);
            this.detachChild(TextAIHP);
            this.detachChild(TextPlayerMP);
            this.detachChild(TextAIMP);

            text.setColor(0f, 1f, 0f);

            //text.setPosition(800 - (text.getWidth() / 2), 750 - (text.getHeight() / 2));
            text.setPosition(650 , 750);

            TextPlayerHP.setPosition(400, 570);
            TextAIHP.setPosition(1160, 570);

            TextPlayerMP.setPosition(400, 525);
            TextAIMP.setPosition(1160, 525);

            //DynamicLifeBarPlayer.setWidth(universalWidth -(universalWidth *(player.getHP()/pStats[0])));

            agregaTexto(s);
        }


    }


    @Override
    public void onBackKeyPressed() {
        finishBattle(player,ai);
        player.resetLevel();
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
        regionFondoCielo.getTexture().unload();
        regionFondoCielo = null;
        regionFondoCasas.getTexture().unload();
        regionFondoCasas = null;
    }

    //POINTER
    private void hideButtons(){

        // BUG Nota, esto regresa null pointer si se llama al metodo cuando se defiende despues de el super de una enemigo

        if(escena2 == true) {
            opcionAttack1.setVisible(false);
            opcionAttack2.setVisible(false);
            opcionAttack3.setVisible(false);
            opcionAttack4.setVisible(false);
            opcionSuper.setVisible(false);
        }
        if(escena1 == true){
            opcionAttack.setVisible(false);
            opcionDefense.setVisible(false);
        }

        TextPlayerName.setVisible(false);
        TextAIName.setVisible(false);
        TextPlayerHP.setVisible(false);
        TextPlayerMP.setVisible(false);
        TextAIHP.setVisible(false);
        TextAIMP.setVisible(false);

        LifeBarPlayer.setVisible(false);
        MagicBarPlayer.setVisible(false);
        LifeBarAI.setVisible(false);
        MagicBarAI.setVisible(false);

        DynamicLifeBarPlayer.setVisible(false);
        DynamicMagicBarPlayer.setVisible(false);
        DynamicLifeBarAI.setVisible(false);
        DynamicMagicBarAI.setVisible(false);
    }

    /////////LOGICA DEL JUEGO //////////////////

    private void initialization(P_Character player, P_Character ai){
        pStats = player.getStats();
        aiStats = ai.getStats();
    }

    private void finishBattle(P_Character player, P_Character ai){
        ai.resetStats(aiStats);
        player.resetStats(pStats);
    }

    private void checkHP(P_Character player, P_Character ai){
        int pHP=  player.getHP();
        int aiHP = ai.getHP();

        if(aiHP <= 0){
            s= "";
            hideButtons();
            playerwin = true;
            setWinner(1);
            winner = true;

            finishBattle(player,ai);
            if(player.getLvl() == 1) {
                player.levelUp();
            }
            savelevel(player);
            player.resetLevel();

            admEscenas.crearEscenaFinBatalla();
            admEscenas.setEscena(TipoEscena.ESCENA_FIN_BATALLA);
            admEscenas.liberarEscenaBatalla();

        }
        if(pHP <= 0){
            s= "";
            hideButtons();
            playerwin = false;
            setWinner(0);
            winner = true;

            finishBattle(player,ai);

            admEscenas.crearEscenaFinBatalla();
            admEscenas.setEscena(TipoEscena.ESCENA_FIN_BATALLA);
            admEscenas.liberarEscenaBatalla();

        }
    }

    private void setWinner(int i){
        SharedPreferences preferences = actividadJuego.getSharedPreferences("winner", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(i ==1) {
            editor.putBoolean("winner", true);
        }
        else if(i==0){
            editor.putBoolean("winner", false);
        }
        editor.commit();
    }

    private void checkTurn(P_Character player, P_Character ai){
        if(player.getSpd() >= ai.getSpd()) {
            aiFirst = false;
        }
        else {
            aiFirst = true;
        }
    }

    private void defend(P_Character defender){
        charChange = true;
        double newDef = defender.getDef() * 1.3;
        defender.setDef((int)Math.ceil(newDef));
    }

    private static void drainSuper(P_Character dealer){
        dealer.setMP(0);
    }

    private static void drainGainMP(int dmg, int n, P_Character dealer){
        int atkDrain = dealer.getAtk_list()[n-1].getMp_drain();
        dealer.setMP(dealer.getMP()-atkDrain);

        dealer.MPRegen();
        dealer.setMP(dealer.getMP() + (dmg/2));
    }

    private static void dealDmg(int dmg, P_Character victim){
        victim.setHP(victim.getHP()-dmg);
    }

    private static int superDamageCalc(Attack superAtk){
        int dmg = superAtk.getBase_dmg();
        return dmg;
    }

    private static int damageCalc(int atk, int def){
        int dmg = (atk-def);
        if(dmg <=0) return 5;
        return dmg;
    }

    private  void playerMove(P_Character player,P_Character ai , int n){
        turn++;
        int dmgToDeal = 0;
        String used = player.getName()+ " Used ";
        boolean enoughMP = false;

        if(n == 0 && (player.getMP() != player.getbase_MP())){
            n = 5;
        }
        else if(n == 0 && player.getMP() == player.getbase_MP()){
            enoughMP = true;
        }
        else if(n >= 1){
            if(player.getAtk_list()[n-1].getMp_drain() > player.getMP()) n = 5;
            else enoughMP = true;
        }

        if(enoughMP == false){n = 5;}


        switch(n){

            case 0:
                s= used + "SUPER ATTACK " + player.getSuperAtk().getName();

                dmgToDeal = superDamageCalc(player.getSuperAtk());
                drainSuper(player);
                dealDmg(dmgToDeal, ai);

                if(currChar ==1) {
                    getImagesCurtis(5);
                    animacionSuperCurtis();
                }
                else if(currChar==2){
                    getImagesGeronimoPlayer(5);
                    animacionPlaguePlayer();
                }
                else if(currChar==3){
                    getImagesFrancisPlayer(5);
                    animacionStrikePlayer();
                }
                break;
            case 1:
                s=used + player.getAtk_list()[0].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[0].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);

                if(currChar == 1) {
                    getImagesCurtis(1);
                    animacionBattack();
                }
                else if(currChar == 2){
                    getImagesGeronimoPlayer(1);
                    animacionLotusPlayer();
                }
                else if(currChar == 3){
                    getImagesFrancisPlayer(1);
                    animacionFirePlayer();
                }
                break;
            case 2:
                s=used + player.getAtk_list()[1].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[1].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);

                if(currChar == 1) {
                    getImagesCurtis(2);
                    animacionNight();
                }
                else if(currChar == 2){
                    getImagesGeronimoPlayer(2);
                    animacionMomifyPlayer();
                }
                else if(currChar==3){
                    getImagesFrancisPlayer(2);
                    animacionAlivePlayer();
                }
                break;
            case 3:
                s=used + player.getAtk_list()[2].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[2].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);

                if(currChar==1) {
                    getImagesCurtis(3);
                    animacionChoke();
                }
                else if(currChar == 2){
                    getImagesGeronimoPlayer(3);
                    animacionAnubisPlayer();
                }
                else if(currChar==3){
                    getImagesFrancisPlayer(3);
                    animacionShockPlayer();
                }
                break;
            case 4:
                s=used + player.getAtk_list()[3].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[3].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);

                if(currChar ==1) {
                    getImagesCurtis(4);
                    animacionDarkness();
                }
                else if(currChar == 2){
                    getImagesGeronimoPlayer(1);
                    animacionLotusPlayer();
                }
                else if(currChar==3){
                    getImagesFrancisPlayer(4);
                    animacionSmashPlayer();
                }
                break;
            case 5:
                s="Not enough MP";
                break;
        }
    }

    private void aiMove(P_Character player,P_Character ai ){

        int dmgToDeal = 0;
        String used = ai.getName()+ " Used ";

        s= ai.attacksString() + "\n\n";

        //ai no escoge ataques que no puede hacer
        Random rn = new Random();
        int n = rn.nextInt(4) + 1;
        boolean enoughMP = false;

        while(enoughMP == false){
            n = rn.nextInt(4) + 1;
            if(ai.getAtk_list()[n-1].getMp_drain() > ai.getMP()) n = (n+ 1)%4;
            else enoughMP = true;

            if(ai.getMP() == ai.getbase_MP()) n = 0;
        }
        switch(n){
            case 0:
                s= used + "SUPER ATTACK " + ai.getSuperAtk().getName();
                dmgToDeal = superDamageCalc(ai.getSuperAtk());
                drainSuper(ai);
                dealDmg(dmgToDeal, player);

                getImagesGeronimo(5);
                animacionPlagueAI();
                break;
            case 1:
                s= used + ai.getAtk_list()[0].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[0].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);

                getImagesGeronimo(1);
                animacionLotusAI();
                break;
            case 2:
                s= used + ai.getAtk_list()[1].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[1].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);

                getImagesGeronimo(2);
                animacionMomifyAI();
                break;
            case 3:
                s= used + ai.getAtk_list()[2].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[2].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);

                getImagesGeronimo(3);
                animacionAnubisAI();
                break;
            case 4:
                s= used + ai.getAtk_list()[3].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[3].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);

                getImagesGeronimo(1);
                animacionLotusAI();
                break;
        }
    }

}