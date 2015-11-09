package mx.itesm.spookybattle;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TiledTextureRegion;


import android.graphics.Color;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    private int attackChoice = 0;
    public boolean winner = false;
    private int turn = 1;
    private boolean aiFirst = false;

    int[] pStats;
    int[] aiStats;
    P_Character player = Main.dracula;
    P_Character ai = Main.mummy;

    //BOTONES
    IMenuItem opcionAttack1;
    IMenuItem opcionAttack2;
    IMenuItem opcionAttack3;
    IMenuItem opcionAttack4;
    boolean attacking = false;

    public Main batalla1 = new Main();

    //Animaciones
    private Timer tiempo;
    private int numImagenes;
    private Sprite spriteFrame;
    private IEntity tagSpriteChild;
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

        this.mFontTexture = new BitmapTextureAtlas(actividadJuego.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createFromAsset(actividadJuego.getFontManager(), actividadJuego.getTextureManager(), 1024, 1024, actividadJuego.getAssets(),
                "fontf.ttf", 50, true, Color.GREEN);
        font.load();

        initialization(Main.dracula, Main.ghost);
    }

    @Override
    public void crearEscena() {


        attacking = false;



        // Creamos el sprite de manera óptima
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);

        // Mostrar un recuadro atrás del menú

        // Mostrar opciones de menú

        int textLength = 200;
        text = new Text(0, 0, font,s,textLength, actividadJuego.getVertexBufferObjectManager());

        agregarMenu();


        spriteCurtisAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/6,ControlJuego.ALTO_CAMARA/2-100,regionCurtisAnimado,actividadJuego.getVertexBufferObjectManager());
        spriteCurtisAnimado.animate(500);
        attachChild(spriteFondo);
        attachChild(spriteCurtisAnimado);


        spriteGeronimoAnimado = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/2 + 350,ControlJuego.ALTO_CAMARA/2-80,regionGeronimoAnimado,actividadJuego.getVertexBufferObjectManager());
        spriteGeronimoAnimado.animate(500);
        attachChild(spriteGeronimoAnimado);
        //agregarCuadroVida();
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
        /*this.detachChild(text);
        text = new Text(0, 0, font,s, actividadJuego.getVertexBufferObjectManager());
        this.attachChild(text);
        text.setColor(0f, 1f, 0f);

        text.setPosition(500 - (text.getWidth() / 2), 700 - (text.getHeight() / 2));*/
    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/7);

        s= "Choose An Action";

        IMenuItem opcionAttack = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTACK,regionBtnAtk, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionDefense = new ScaleMenuItemDecorator(new SpriteMenuItem(DEFEND,regionBtnDef, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);

        // Agrega las opciones al menú
        menu.addMenuItem(opcionAttack);
        menu.addMenuItem(opcionDefense);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú huhh es (0,0)
        opcionAttack.setPosition(-200, -20);
        opcionDefense.setPosition(200, -20);



        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch (pMenuItem.getID()) {
                    case ATTACK:
                        menu.clearMenuItems();
                        menu.resetAnimations();
                        agregarMenu2();
                        attackChoice = pMenuItem.getID();
                        s = "Choose An Attack";
                        break;

                    case DEFEND:
                        defend(player);
                        s = "Curtis Defended";
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


        opcionAttack1 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK1,regionBtnAtk1,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionAttack2 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK2,regionBtnAtk2,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionAttack3 = new ScaleMenuItemDecorator(new SpriteMenuItem(ATTCK3,regionBtnAtk3,actividadJuego.getVertexBufferObjectManager()),1.5f,1);
        opcionAttack4 = new ScaleMenuItemDecorator(new SpriteMenuItem(SUPER,regionBtnAtk4,actividadJuego.getVertexBufferObjectManager()),1.5f,1);

        menu.addMenuItem(opcionAttack1);
        menu.addMenuItem(opcionAttack2);
        menu.addMenuItem(opcionAttack3);
        menu.addMenuItem(opcionAttack4);

        menu.buildAnimations();
        menu.setBackgroundEnabled(false);
        opcionAttack1.setPosition(-500, -20);
        opcionAttack2.setPosition(-200, -20);
        opcionAttack3.setPosition(200, -20);
        opcionAttack4.setPosition(500, -20);

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

                        if (aiFirst == true) aiMove(player, ai);

                        playerMove(player, ai, attackChoice);
                        break;

                    case ATTCK2:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) aiMove(player, ai);

                        playerMove(player, ai, attackChoice);
                        break;

                    case ATTCK3:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) aiMove(player, ai);

                        playerMove(player, ai, attackChoice);
                        break;
                    case SUPER:
                        if (attacking == true) break;
                        attacking = true;
                        hideButtons();
                        attackChoice = pMenuItem.getID();

                        if (aiFirst == true) aiMove(player, ai);

                        playerMove(player, ai, attackChoice);
                        break;

                }
                if (aiFirst == false) aiMove(player, ai);
                return true;
            }
        });


    }
    private void animacionSuper() {

        try {
            final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();
            numImagenes = 0;
            for (int i = 0; i < 8; i++) {
                ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Darkness/Darkness0" + (i) + ".png");
                arrayImagenes.add(i, imagen);

            }


            numImagenes = 0;
            tiempo = new Timer();
            tiempo.schedule(new TimerTask() {
                @Override
                public void run() {
                    actividadJuego.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                            if (numImagenes < 8) {
                                spriteCurtisAnimado.setAlpha(0);

                                if (tagSpriteChild != null)
                                    detachChild(tagSpriteChild);

                                spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                                attachChild(spriteFrame);
                                tagSpriteChild = spriteFrame;

                                numImagenes++;
                            } else {
                                for (int i = 0; i < 8; i++) {

                                    arrayImagenes.get(i).getTexture().unload();
                                }
                                reset();
                                crearEscena();
                                numImagenes = 0;
                                tiempo.cancel();
                            }


                        }
                    });
                }
            }, 300, 300);
        }catch(IndexOutOfBoundsException e){

            System.out.println("Error Super");
        }
    }
    private void animacionBattack(){
        try{
        final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();
        numImagenes= 0;
        for (int i = 0; i <6 ; i++) {
            ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Battack/Battack0"+(i) + ".png");
            arrayImagenes.add(i,imagen);

        }



        numImagenes= 0;
        tiempo = new Timer();
        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                actividadJuego.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                        if (numImagenes < 6) {
                            spriteCurtisAnimado.setAlpha(0);

                            if(tagSpriteChild!= null)
                                detachChild(tagSpriteChild);

                            spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                            attachChild(spriteFrame);
                            tagSpriteChild = spriteFrame;

                            numImagenes++;
                        } else {

                            for (int i = 0; i < 6 ; i++) {

                                arrayImagenes.get(i).getTexture().unload();
                            }
                            reset();
                            crearEscena();
                            numImagenes = 0;
                            tiempo.cancel();
                        }


                    }
                });
            }
        }, 300, 300);

    }catch(IndexOutOfBoundsException e){

            System.out.println("Error Battack");
        }
    }

    private void animacionNight(){
        try {
            final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();
            numImagenes = 0;
            for (int i = 0; i < 7; i++) {
                ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Night/Night0" + (i + 1) + ".png");
                arrayImagenes.add(i, imagen);

            }


            numImagenes = 0;
            tiempo = new Timer();
            tiempo.schedule(new TimerTask() {
                @Override
                public void run() {
                    actividadJuego.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer
                            if (numImagenes < 7) {
                                spriteCurtisAnimado.setAlpha(0);

                                if (tagSpriteChild != null)
                                    detachChild(tagSpriteChild);

                                spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                                attachChild(spriteFrame);
                                tagSpriteChild = spriteFrame;

                                numImagenes++;
                            } else {

                                for (int i = 0; i < 7; i++) {

                                    arrayImagenes.get(i).getTexture().unload();
                                }
                                reset();
                                crearEscena();
                                numImagenes = 0;
                                tiempo.cancel();
                            }


                        }
                    });
                }
            }, 300, 300);
        }catch(IndexOutOfBoundsException e){

            System.out.println("Error Night");
        }
    }
    private void animacionChoke(){
        try {
            final ArrayList<ITextureRegion> arrayImagenes = new ArrayList<>();
            numImagenes = 0;
            for (int i = 0; i < 7; i++) {
                ITextureRegion imagen = cargarImagen("AnimacionesCurtis/Choke/VampChoke0" + (i + 1) + ".png");
                arrayImagenes.add(i, imagen);

            }


            numImagenes = 0;

            tiempo = new Timer();

            tiempo.schedule(new TimerTask() {
                @Override
                public void run() {
                    actividadJuego.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //A partir de aqui puedes agregrar las instrucciones que quieres que realice el timer

                            if (numImagenes < 7) {
                                spriteCurtisAnimado.setAlpha(0);

                                if (tagSpriteChild != null)
                                    detachChild(tagSpriteChild);

                                spriteFrame = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, arrayImagenes.get(numImagenes));
                                attachChild(spriteFrame);
                                tagSpriteChild = spriteFrame;

                                numImagenes++;
                            } else {
                                for (int i = 0; i < 7; i++) {

                                    arrayImagenes.get(i).getTexture().unload();
                                }
                                reset();
                                crearEscena();
                                numImagenes = 0;
                                tiempo.cancel();
                            }


                        }
                    });
                }
            }, 300, 300);
        }catch(IndexOutOfBoundsException e){

            System.out.println("Error Choke");
        }

    }
    // La escena se debe actualizar en este método que se repite "varias" veces por segundo
    // Aquí es donde programan TODA la acción de la escena (movimientos, choques, disparos, etc.)
    @Override
    protected void onManagedUpdate(float pSecondsElapsed){
        super.onManagedUpdate(pSecondsElapsed);

        this.detachChild(text);
        text.setColor(0f, 1f, 0f);

        text.setPosition(500 - (text.getWidth() / 2), 700 - (text.getHeight() / 2));

        agregaTexto(s);


    }


    @Override
    public void onBackKeyPressed() {
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
        opcionAttack1.setVisible(false);
        opcionAttack2.setVisible(false);
        opcionAttack3.setVisible(false);
        opcionAttack4.setVisible(false);

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
        int aiHP = player.getHP();

        if(pHP <= 0){
            s= player.getName() + " wins";
            //abort
        }
        if(aiHP <= 0){
            s= ai.getName() + " wins";
            //abort
        }
    }

    private void checkTurn(P_Character player, P_Character ai){
        if (player.getSpd()< ai.getSpd())
            aiFirst = true;
        else
            aiFirst = false;
    }

    private void battleTurn(P_Character player, P_Character ai){
        int n = 0;
        boolean charChange = false;

        if(charChange == true){
            player.setDef(pStats[3]);
            charChange = false;
        }

        EscenaBatalla.s = "Choose an action: ";

        if(player.getSpd() > ai.getSpd()){

            switch(n){
                case 1:
                    //playerMove(player, ai);
                    s = "HP AI: " + ai.getHP() + " MP AI: " + ai.getMP() +
                            "\nHP Player: " + player.getHP() + " MP Player: " + player.getMP();
                    break;
                case 2:
                    s =player.getName() + " defended";
                    defend(player);
                    charChange = true;
                    break;

            }
            //if(ai.getHP() <= 0) break;

            //aiMove(player,ai);
            s = "HP AI: " + ai.getHP() + " MP AI: " + ai.getMP() +
                    "\nHP Player: " + player.getHP() + " MP Player: " + player.getMP();

        }
        else{

            switch(n){
                case 2:
                    s = player.getName() + " defended";
                    defend(player);
                    charChange = true;
                    break;
            }

            //aiMove(player,ai);
            s = "HP AI: " + ai.getHP() + " MP AI: " + ai.getMP() +
                    "\nHP Player: " + player.getHP() + " MP Player: " + player.getMP();

            //if(player.getHP() <= 0) break;

            //playerMove(player, ai);
            s = "HP AI: " + ai.getHP() + " MP AI: " + ai.getMP() +
                    "\nHP Player: " + player.getHP() + " MP Player: " + player.getMP();

        }
        turn++;
    }

    private static void defend(P_Character defender){
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
        int dmgToDeal = 0;
        String used = player.getName()+ " Used ";
        boolean enoughMP = false;

        //System.out.println("\nPlayer Choose An Attack: ");

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
                break;
            case 1:
                s=used + player.getAtk_list()[0].getName();
                animacionBattack();
                dmgToDeal = damageCalc(player.getAtk_list()[0].getBase_dmg(), ai.getDef());
                break;
            case 2:
                s=used + player.getAtk_list()[1].getName();
                animacionNight();
                dmgToDeal = damageCalc(player.getAtk_list()[1].getBase_dmg(), ai.getDef());
                break;
            case 3:
                s=used + player.getAtk_list()[2].getName();
                animacionChoke();
                dmgToDeal = damageCalc(player.getAtk_list()[2].getBase_dmg(), ai.getDef());
                break;
            case 4:
                s=used + player.getAtk_list()[3].getName();
                animacionSuper();
                dmgToDeal = damageCalc(player.getAtk_list()[3].getBase_dmg(), ai.getDef());
                break;
            case 5:
                s="Not enough MP";
                break;
        }


        if(n == 0){
            drainSuper(player);
            dealDmg(dmgToDeal, ai);
        }
        else if(n == 5){
        }
        else{
            drainGainMP(dmgToDeal, n, player);
            dealDmg(dmgToDeal, ai);
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
                break;
            case 1:
                s= used + ai.getAtk_list()[0].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[0].getBase_dmg(), player.getDef());
                break;
            case 2:
                s= used + ai.getAtk_list()[1].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[1].getBase_dmg(), player.getDef());
                break;
            case 3:
                s= used + ai.getAtk_list()[2].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[2].getBase_dmg(), player.getDef());
                break;
            case 4:
                s= used + ai.getAtk_list()[3].getName();
                dmgToDeal = damageCalc(ai.getAtk_list()[3].getBase_dmg(), player.getDef());
                break;
        }

        if(n == 0){
            drainSuper(ai);
            dealDmg(dmgToDeal, player);
        }
        else{
            drainGainMP(dmgToDeal, n, ai);
            dealDmg(dmgToDeal, player);
        }
    }

}