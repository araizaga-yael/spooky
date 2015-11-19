package mx.itesm.spookybattle;
import android.util.Log;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public Main(){};

	static Scanner sc = new Scanner(System.in);
	
	//Creando ataques de dracula
	static Attack battack = new Attack("Battack", "Summon attacking...bats", 10, 0);
	static Attack night = new Attack("I Am The Night", "Not like that other guy", 5, 2);
	static Attack choke = new Attack("Vamp Choke", "Sort of like a force choke, but vampiric", 15, 10);	
	static Attack darkness = new Attack("Submit To Darkness", "tbd", 10, 8);
	//metiendo ataques de dracula a un array
	static Attack[] dracula_atks = {battack, night, choke, darkness};
	//super ataque
	static Attack vacuum = new Attack("Blood Vacuum","Suck Them Dry...Of Candy", 100, 0);
	//Creando a dracula
	 public static P_Character dracula = new P_Character("Curtis", 1, 100, 100, 8, 40, dracula_atks, vacuum, "Curtis Dracovich el ninio vampiro");
	
	//Creando ataques de fantasma
	static Attack boom = new Attack("Boooom", "Scare The Bejeezus Out Of Em", 10, 0);
	static Attack booty = new Attack("Booty Kick", "tbh", 15, 10);	
	static Attack boogaloo = new Attack("Creepy Boogaloo", "Fear These Sick Moves", 5, 20);
	static Attack taboo = new Attack("Taboo Wail", "Rules Are Meant To Be Foresaken", 10, 80);
	//metiendo ataques de fantasma a un array
	static Attack[] ghost_atks = {boom, booty, boogaloo, taboo};
	//super ataque
	static Attack twospooky = new Attack("2Spooky4me","Become Fear Itself", 100, 0);
	//Creando a fantasma
	public static P_Character ghost = new P_Character("Gus", 1, 100, 100, 4, 0, ghost_atks, twospooky, "Un spooky dude");
	
	//Creando ataques de momia
	static Attack locust = new Attack("Locust Pocus", "tbh", 10, 0);
	static Attack mummify = new Attack("Mummify Mortify", "tbh", 15, 10);	
	static Attack anubis = new Attack("Anubis Eye", "tbh", 5, 2);
	static Attack osiris = new Attack("Osiris' Knights", "Strike With The Mighty Force Of the Underworld", 10, 8);
	//metiendo ataques de momia a un array
	static Attack[] mummy_atks = {locust, mummify, anubis, osiris};
	//super ataque
	static Attack plague = new Attack("11th Plague","Buy 10 Egyptian Plagues, Get One More For Free!", 100, 0);
	//Creando a momia
	public static P_Character mummy = new P_Character("Geronimo", 1, 100, 100, 4, 0, mummy_atks, plague, "Una momia cosa");
	
	//Turno del jugador
	private void playerMove(P_Character player,P_Character ai ){
		int dmgToDeal = 0;
		String used = player.getName()+ " Used ";
		boolean enoughMP = false;

		System.out.println("\nPlayer Choose An Attack: ");
		System.out.println( player.attacksString() + "\n\n");
		int n = 0;

		while(enoughMP == false){
			n = sc.nextInt(); if(n>= 5) n=4;
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
		
		
			switch(n){
			
			case 0:
				System.out.println(used + "SUPER ATTACK " + player.getSuperAtk().getName());
				dmgToDeal = superDamageCalc(player.getSuperAtk());
				break;
			case 1:
				System.out.println(used + player.getAtk_list()[0].getName());
				dmgToDeal = damageCalc(player.getAtk_list()[0].getBase_dmg(), ai.getDef());
				break;
			case 2:
				System.out.println(used + player.getAtk_list()[1].getName());
				dmgToDeal = damageCalc(player.getAtk_list()[1].getBase_dmg(), ai.getDef());
				break;
			case 3:
				System.out.println(used + player.getAtk_list()[2].getName());
				dmgToDeal = damageCalc(player.getAtk_list()[2].getBase_dmg(), ai.getDef());
				break;
			case 4:
				System.out.println(used + player.getAtk_list()[3].getName());
				dmgToDeal = damageCalc(player.getAtk_list()[3].getBase_dmg(), ai.getDef());
				break;
			case 5:
				System.out.println("Not enough MP");
				break;
			}
		}
		
		if(n == 0){
			drainSuper(player);
			dealDmg(dmgToDeal, ai);
		}
		else{
			drainGainMP(dmgToDeal, n, player);
			dealDmg(dmgToDeal, ai);		
		}
	}
	
	//Turno del AI
	private void aiMove(P_Character player,P_Character ai ){
		int dmgToDeal = 0;
		String used = ai.getName()+ " Used ";
		
		System.out.println(ai.attacksString() + "\n\n");
		
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
				System.out.println(used + "SUPER ATTACK " + ai.getSuperAtk().getName());
				dmgToDeal = superDamageCalc(ai.getSuperAtk());
				break;
			case 1:
				System.out.println(used + ai.getAtk_list()[0].getName());
				dmgToDeal = damageCalc(ai.getAtk_list()[0].getBase_dmg(), player.getDef());
				break;
			case 2:
				System.out.println(used + ai.getAtk_list()[1].getName());
				dmgToDeal = damageCalc(ai.getAtk_list()[1].getBase_dmg(), player.getDef());
				break;
			case 3:
				System.out.println(used + ai.getAtk_list()[2].getName());
				dmgToDeal = damageCalc(ai.getAtk_list()[2].getBase_dmg(), player.getDef());
				break;
			case 4:
				System.out.println(used + ai.getAtk_list()[3].getName());
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
	
	//metodo que inicia el combate
	private void battleSys(P_Character player,P_Character ai){
		int[] pStats = player.getStats();
		int[] aiStats = ai.getStats();
		int turn = 1;
		int n;
		boolean charChange = false;
        Log.d("First choice: ", "\nInicia combate oh yea   : ");
		
		while (player.getHP() > 0 && ai.getHP() > 0 ){
			
			if(charChange == true){
				player.setDef(pStats[3]);
				charChange = false;
			}
			
			System.out.println("\nChoose an action: ");
			System.out.println("Attack 1 ");
			System.out.println("Defend 2 ");
			
			n = sc.nextInt();
			if(n >= 3) n = 2;
			
			if(player.getSpd() > ai.getSpd()){
				
				switch(n){
					case 1:
					playerMove(player, ai);
					System.out.println("HP AI: " + ai.getHP() + " MP AI: " + ai.getMP());
					System.out.println("HP Player: " + player.getHP() + " MP Player: " + player.getMP());
					break;
					case 2:
					System.out.println(player.getName() + " defended");
					defend(player);
					charChange = true;
					break;
					
				}
				if(ai.getHP() <= 0) break;
				
				aiMove(player,ai);
				System.out.println("HP AI: " + ai.getHP()+ " MP AI: " + ai.getMP());
				System.out.println("HP Player: " + player.getHP()+ " MP Player: " + player.getMP());		
				
			}
			else{
				
				switch(n){
				case 2:
				System.out.println(player.getName() + " defended");
				defend(player);
				charChange = true;
				break;
				}
				
				aiMove(player,ai);
				System.out.println("HP AI: " + ai.getHP());
				System.out.println("HP Player: " + player.getHP());	
				
				if(player.getHP() <= 0) break;
				
				playerMove(player, ai);
				System.out.println("HP AI: " + ai.getHP());
				System.out.println("HP Player: " + player.getHP());
				
				}
			turn++;
				
		}		
		if(player.getHP() > 0) System.out.println("\n\n" + player.getName()+ " wins");
		else System.out.println(ai.getName()+ " wins");
		
		ai.resetStats(aiStats);
		player.resetStats(pStats);
		
		/* Prueba que subir de nivel y resetear nivel funciona
		System.out.println(player.toString());
		player.levelUp();
		System.out.println(player.toString());
		player.resetLevel();
		System.out.println(player.toString());*/
		
		sc.close();

	}
	
	
	//calcula damage de un ataque en un turno
	private  int damageCalc(int atk, int def){
		int dmg = (atk-def);
		if(dmg <=0) return 5;
		return dmg;
	}
	//calcula damage de super ataque
	private int superDamageCalc(Attack superAtk){
		int dmg = superAtk.getBase_dmg();
		return dmg;
	}
	//hace la damage calculada al oponente
	private  void dealDmg(int dmg, P_Character victim){
		victim.setHP(victim.getHP()-dmg);
	}
	//calcula la perdida y ganancia de MP
	private  void drainGainMP(int dmg, int n, P_Character dealer){
		int atkDrain = dealer.getAtk_list()[n-1].getMp_drain();
		dealer.setMP(dealer.getMP()-atkDrain);

		dealer.MPRegen();
		dealer.setMP(dealer.getMP() + (dmg/2));
	}
	//representa la perdida de MP por super ataque
	private void drainSuper(P_Character dealer){
		dealer.setMP(0);
	}
	//aumenta la defensa de el usuario
	private void defend(P_Character defender){
		double newDef = defender.getDef() * 1.3;
		defender.setDef((int)Math.ceil(newDef));
	}

    public static void battle(){

        EscenaBatalla.s = "LOL";
       /* System.out.println(dracula.toString());
        System.out.println(dracula.attacksString());

        System.out.println("\n" +  ghost.toString());
        System.out.println(ghost.attacksString());

        System.out.println("\n" +  mummy.toString());
        System.out.println(mummy.attacksString());*/
		/**/

      // battleSys(dracula, ghost);
    }
	

   // public static void main(String[] args) {

		/* Comprobando creaci�n correcta*/
       // System.out.println(dracula.toString());
        //System.out.println(dracula.attacksString());

        //System.out.println("\n" +  ghost.toString());
        //System.out.println(ghost.attacksString());

        //System.out.println("\n" +  mummy.toString());
        //System.out.println(mummy.attacksString());


        //battleSys(dracula, ghost);


    //}
	
	
	
	

}
