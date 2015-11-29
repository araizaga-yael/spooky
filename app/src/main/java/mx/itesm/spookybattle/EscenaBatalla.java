package mx.itesm.spookybattle;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
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
    private ITextureRegion regionFondo;
    private ITextureRegion regionBtnAtk;
    private ITextureRegion regionBtnDef;
    private ITextureRegion regionBtnAtk1;
    private ITextureRegion regionBtnAtk2;
    private ITextureRegion regionBtnAtk3;
    private ITextureRegion regionBtnAtk4;
    private ITextureRegion regionBtnAtk5;

    //SpriteAnimado
    private AnimatedSprite spriteCurtisAnimado;
    private TiledTextureRegion regionCurtisAnimado;

    private AnimatedSprite spriteGeronimoAnimado;
    private TiledTextureRegion regionGeronimoAnimado;



    //cosas de texto
    private BitmapTextureAtlas mFontTexture;
    private Text  text;
    private Font  font;
    public static String s = "";

    private Font  fontCurt;
    private Font  fontGeronimo;
    private Font  fontHP;
    private Font  fontMP;

    private Text  TextPlayerName;
    private Text  TextPlayerHP;
    private Text  TextPlayerMP;

    private Text  TextAIName;
    private Text  TextAIHP;
    private Text  TextAIMP;


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


    //String SplayerName = player.toString();
    //String SAIName = ai.toString();

    String SplayerName;
    String SAIName;

/*
    String SplayerstartingHP = player.getHP() +"";
    String SAIStartingHP = ai.getHP()+ "";

    String SplayerHP = player.getHP() +"";
    String SAIHP = ai.getHP()+ "";
    */

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
    boolean attacking = false;

    public Main batalla1 = new Main();

    //Animaciones
    private Timer tiempo;
    private int numImagenes;
    private int numImagenesGeronimo;
    private Sprite spriteFrame;
    private Sprite spriteFrameGeronimo;
    private IEntity tagSpriteChild;

    private ArrayList<ITextureRegion> arrayImagenes;
    private ArrayList<ITextureRegion> arrayImagenesGeronimo;
    @Override
    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("Batalla1/FondosBatalla1.png");

        regionCurtisAnimado = cargarImagenMosaico("AnimacionesCurtis/WaitingBattle/CurtisWaitingSheet.png", 1986, 331, 1, 6);
        regionGeronimoAnimado =cargarImagenMosaico("AnimacionesGeronimo/GeronimoWaitingSheet.png", 2723,389 ,1,7);

        regionBtnAtk = cargarImagen("BotonesCurtis/BotonAttack.png");
        regionBtnDef = cargarImagen("BotonesCurtis/BotonDefense.png");

        regionBtnAtk1 = cargarImagen("BotonesCurtis/BotonCurtisAtt4.png");
        regionBtnAtk2 = cargarImagen("BotonesCurtis/BotonCurtisAtt3.png");
        regionBtnAtk3 = cargarImagen("BotonesCurtis/BotonCurtisAtt2.png");
        regionBtnAtk4 = cargarImagen("BotonesCurtis/BotonCurtisAtt1.png");
        regionBtnAtk5 = cargarImagen("BotonesCurtis/BotonSuper.png");

        numImagenes = 0;

        this.mFontTexture = new BitmapTextureAtlas(actividadJuego.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true, Color.GREEN);
        fontCurt = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true, Color.parseColor("#FF00FF"));
        fontGeronimo = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "spookyfont.ttf", 50, true, Color.parseColor("#FFFF4D"));

        fontHP  = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "numbers.ttf", 50, true, Color.RED);
        fontMP = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "numbers.ttf", 50, true, Color.BLUE);
        font.load();
        fontHP.load();
        fontMP.load();

        //IF CURTIS
        fontCurt.load();

        //IF GERONIMO
        fontGeronimo.load();

        player = Main.dracula;
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

        ai = Main.mummy;

        SplayerName = player.toString();
        SAIName = ai.toString();

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

            attacking = false;

            if(charChange == true){
                player.setDef(pStats[3]);
                charChange = false;
                checkTurn(player,ai);
            }


            // Creamos el sprite de manera óptima
            spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);

            // Mostrar un recuadro atrás del menú

            // Mostrar opciones de menú

            int textLength = 200;
            text = new Text(0, 0, font, s, textLength, actividadJuego.getVertexBufferObjectManager());
            TextPlayerHP = new Text(0, 0, fontHP, SplayerHP + "/" + SplayerstartingHP, textLength, actividadJuego.getVertexBufferObjectManager());
            TextAIHP = new Text(0, 0, fontHP, SAIHP + "/" + SAIStartingHP, textLength, actividadJuego.getVertexBufferObjectManager());
            TextPlayerMP = new Text(0, 0, fontMP, SplayerMP + "/" + player.getbase_MP(), textLength, actividadJuego.getVertexBufferObjectManager());
            TextAIMP = new Text(0, 0, fontMP, SAIMP + "/" + ai.getbase_MP(), textLength, actividadJuego.getVertexBufferObjectManager());


            TextPlayerName = new Text(0, 0, fontCurt, SplayerName, actividadJuego.getVertexBufferObjectManager());
            TextAIName = new Text(0, 0, fontGeronimo, SAIName, actividadJuego.getVertexBufferObjectManager());

            agregarMenu();


            spriteCurtisAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 6, ControlJuego.ALTO_CAMARA / 2 - 100, regionCurtisAnimado, actividadJuego.getVertexBufferObjectManager());
            spriteCurtisAnimado.animate(500);
            attachChild(spriteFondo);
            attachChild(spriteCurtisAnimado);


            spriteGeronimoAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA / 2 + 350, ControlJuego.ALTO_CAMARA / 2 - 80, regionGeronimoAnimado, actividadJuego.getVertexBufferObjectManager());
            spriteGeronimoAnimado.animate(500);
            attachChild(spriteGeronimoAnimado);

        }   //agregarCuadroVida();

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

            text.setPosition(800 - (text.getWidth() / 2), 750 - (text.getHeight() / 2));
            TextPlayerHP.setPosition(280, 555);
            TextAIHP.setPosition(1040, 555);

            TextPlayerMP.setPosition(280, 525);
            TextAIMP.setPosition(1040, 525);


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
        regionFondo.getTexture().unload();
        regionFondo = null;
    }

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
                checkHP(player, ai);

                getImagesCurtis(5);
                animacionSuperCurtis();
                break;
            case 1:
                s=used + player.getAtk_list()[0].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[0].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);
                checkHP(player, ai);

                getImagesCurtis(1);
                animacionBattack();
                break;
            case 2:
                s=used + player.getAtk_list()[1].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[1].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);
                checkHP(player, ai);

                getImagesCurtis(2);
                animacionNight();
                break;
            case 3:
                s=used + player.getAtk_list()[2].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[2].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);
                checkHP(player, ai);

                getImagesCurtis(3);
                animacionChoke();
                break;
            case 4:
                s=used + player.getAtk_list()[3].getName();
                dmgToDeal = damageCalc(player.getAtk_list()[3].getBase_dmg(), ai.getDef());
                drainGainMP(dmgToDeal, n, player);
                dealDmg(dmgToDeal, ai);
                checkHP(player, ai);

                getImagesCurtis(4);
                animacionDarkness();
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
                checkHP(player,ai);


                break;
            case 1:
                s= used + ai.getAtk_list()[0].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[0].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);
                checkHP(player,ai);

                getImagesGeronimo(1);
                animacionLotusAI();
                break;
            case 2:
                s= used + ai.getAtk_list()[1].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[1].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);
                checkHP(player,ai);

                getImagesGeronimo(2);
                animacionMomifyAI();
                break;
            case 3:
                s= used + ai.getAtk_list()[2].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[2].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);
                checkHP(player,ai);

                getImagesGeronimo(3);
                animacionAnubisAI();
                break;
            case 4:
                s= used + ai.getAtk_list()[3].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[3].getBase_dmg(), player.getDef());
                drainGainMP(dmgToDeal, n, ai);
                dealDmg(dmgToDeal, player);
                checkHP(player,ai);

                getImagesGeronimo(1);
                animacionLotusAI();
                break;
        }
    }

}