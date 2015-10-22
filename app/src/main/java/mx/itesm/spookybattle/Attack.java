package mx.itesm.spookybattle;

public class Attack {
	
	private String name;
	private String desc;
	private int base_dmg;
	private int mp_drain;
	
	public Attack(String name,String desc, int base_dmg, int mp_drain){
		this.name = name;
		this.desc = desc;
		this.base_dmg = base_dmg;
		this.mp_drain = mp_drain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getBase_dmg() {
		return base_dmg;
	}

	public void setBase_dmg(int base_dmg) {
		this.base_dmg = base_dmg;
	}

	public int getMp_drain() {
		return mp_drain;
	}

	public void setMp_drain(int mp_drain) {
		this.mp_drain = mp_drain;
	}

	@Override
	public String toString() {
		return "\n" + name + " (" + desc + ")" + " 1"
				+ "Damage: "+ base_dmg + " MP: " + mp_drain;
	}

	
}
