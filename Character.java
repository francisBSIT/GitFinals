import java.util.Random;

public abstract class Character {
    String name;
    int MP, HP, SP;
    int dodgeChance = 15;
	int healthPotions;
	int manaPotions;
	boolean isDefending = false;
	int damageReduction = 0;
	
    protected static final Random random = new Random(); 

    public Character(String name, int MP, int HP, int SP, int healthPotions, int manaPotions){
        this.name = name; 
        this.MP = MP;
        this.HP = HP;
        this.SP = SP;
		this.healthPotions = healthPotions;
		this.manaPotions = manaPotions;  
    }

    public boolean Alive(){
        return HP > 0;
    }

    public boolean attemptDodge(){
        
        int roll = random.nextInt(100) + 1; 
        
        return roll <= dodgeChance;
    }

    public void takeDamage(int damage){
        if (attemptDodge()){
            System.out.println(this.name + " dodged the attack!");
            return; 
        }
		
		int damageTaken = damage;
		if (isDefending){
		damageTaken = Math.max(0, damage - this.damageReduction);
		System.out.println(name + " mitigates " + (damage - damageTaken) + " damage!");
		this.damageReduction = 0;
        this.isDefending = false;
		}
		
        if (damageTaken > 0) {
            HP -= damageTaken;
            System.out.println(this.name + " took " + damageTaken + " damage. HP remaining: " + HP);
        } else if (damageTaken == 0) {
             System.out.println(this.name + " took no damage.");
        }
        
        if (!Alive()){
            System.out.println(this.name + " has been defeated!");
        }
    }	
   
    public void useHealthPotion(int amount){
        HP += amount;
        System.out.println(this.name + " uses a Health Potion, restoring " + amount + " HP. Current HP: " + this.HP);
    }

    public void useManaPotion(int amount){
        MP += amount;
        System.out.println(this.name + " uses a Mana Potion, restoring " + amount + " MP. Current MP: " + this.MP);
    }
	
	
    
    public abstract void attack(Character target);
    
    public abstract void defend ();

    public abstract void Primary(Character target);
    
    public abstract void Secondary(Character target);   
}