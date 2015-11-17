package mx.itesm.spookybattle;

public class P_Character{
	
	private String name;
	private int lvl;
	private int HP;
	private int MP;
	private int base_MP;
	private int Spd;
	private int Def;
	private Attack[] atk_list; 
	private int[] base_Stats;
	private Attack superAtk;
	private String c_desc;
	

	public P_Character(String name, int lvl, int HP,int base_MP, int Spd, int Def, Attack[] atk_list, Attack superAtk, String c_desc){
		this.name = name;
		this.c_desc = c_desc;
		this.lvl = lvl;
		this.HP = HP;
		this.base_MP = base_MP;
		this.MP = (base_MP/2);
		this.Spd = Spd;
		this.Def = Def;
		this.atk_list = atk_list;
		this.base_Stats = saveBaseStats(HP, base_MP, Spd, Def);
		this.superAtk = superAtk;
	}
	
	@Override
	public String toString() {
		return "Level " + this.lvl + " " + this.name;
	}
	
	public String attacksString(){
		int n = this.atk_list.length;
		String s = " ";
		
		while(n > 0){
			s = s + this.atk_list[atk_list.length - n].toString();
			n--;
		}
		s+= "\nSUPER ATTACK: " + this.superAtk.toString();
		return s;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getLvl() {
		return lvl;
	}


	public void setLvl(int lvl) {
		this.lvl = lvl;
	}


	public int getHP() {
		return HP;
	}


	public void setHP(int hp) {
		HP = hp;
	}


	public int getMP() {
		return MP;
	}


	public void setMP(int mp) {
		int newMP = mp;
		if(newMP > (this.base_MP)) MP = this.base_MP;
		else
			MP = newMP;
	}


	public int getSpd() {
		return Spd;
	}


	public void setSpd(int spd) {
		Spd = spd;
	}


	public int getDef() {
		return Def;
	}


	public void setDef(int def) {
		Def = def;
	}


	public Attack[] getAtk_list() {
		return atk_list;
	}


	public void setAtk_list(Attack[] atk_list) {
		this.atk_list = atk_list;
	}


	public String getC_desc() {
		return c_desc;
	}


	public void setC_desc(String c_desc) {
		this.c_desc = c_desc;
	}
		
	public void resetStats(int[] stats){
		this.HP = stats[0];
		this.base_MP = stats[1];
		this.Spd = stats[2];
		this.Def = stats[3];
	}
	
	public int[] getStats(){
		int[] statsArr = {this.HP, this.base_MP, this.Spd, this.Def};
		return statsArr;
		
	}
	
	public int getbase_MP(){
		return this.base_MP;
	}
	
	public void setbase_MP(int newBaseMP){
		this.base_MP = newBaseMP;
	}
	
	public void MPRegen(){
		this.MP += getbase_MP() *.1;
	}
	
	public void levelUp(){
		if(this.lvl != 5){ 
			this.lvl += 1;
			this.HP += (this.HP * .246);
			this.base_MP += (this.base_MP * .246);
			this.Spd += 1;
			this.Def += (this.Def * .1);
		}
	}
	
	private int[] saveBaseStats(int HP,int base_MP, int Spd, int Def){
		int[] base_Stats ={HP,base_MP,Spd,Def};
		return base_Stats;
	}
	
	
	public void resetLevel(){
		this.lvl = 1;
		resetStats(base_Stats);
	}

	public Attack getSuperAtk() {
		return superAtk;
	}

	public void setSuperAtk(Attack superAtk) {
		this.superAtk = superAtk;
	}
	
	

}
