import java.util.Random;

public class Priest extends Character {
    boolean isSmiteActive = false;
    int baseMagicDamage = 20;
    int shieldDuration = 0; 
	int shieldHPBonus = 0;
	final int TURN_SHIELD_VALUE = 10;

    public Priest(String name, int healthPotions, int manaPotions){
        
        super (name, 150, 150, 70, healthPotions, manaPotions);
        this.dodgeChance = 15; 
    }
	
    @Override
    public void Primary(Character target){
        if (MP >= 25){ 
            MP -= 25;
            isSmiteActive = true;
            shieldDuration = 2; 
            System.out.println("\n"+name + " uses Smite! Next attack will deal bonus magic damage and grant a temporary shield.");
        } else { 
            System.out.println("\nYou don't have enough MP. Use a potion to restore MP.");
        }  
    }

    @Override
    public void Secondary(Character target){
        if (MP >= 40){ 
            MP -= 40;
            
            int healAmount = random.nextInt(41) + 10; 
            HP += healAmount; 
            System.out.println("\n"+name + " uses Quick Heal. Health is restored by " + healAmount + ". Current HP: " +HP);  
        } else {
            System.out.println("\nYou don't have enough MP. Use a potion to restore MP.");
        }
    }

    @Override
    public void attack(Character target){
        if (SP >= 2){ 
            SP -= 2;
			
            if (shieldDuration > 0){
                
                if (shieldHPBonus == 0) {
                    shieldHPBonus = TURN_SHIELD_VALUE;
                    HP += shieldHPBonus;
                    System.out.println("\n"+name + " gains a temporary shield, increasing total health by " + shieldHPBonus + ".");
                }

               
                shieldDuration--;
                
                System.out.println("Shield duration: " + shieldDuration + " turn(s) remaining.");
                
                
                if (shieldDuration == 0) {
                    HP -= shieldHPBonus; 
                    System.out.println(name + "'s temporary shield wears off, reducing HP by " + shieldHPBonus + ". Current HP: " + HP);
                    shieldHPBonus = 0; // Reset the bonus
                }
            }
            int finalDamage = baseMagicDamage + random.nextInt(11); 
            
            if (isSmiteActive){
                int dmgBoost = random.nextInt(11) + 10; 
                finalDamage += dmgBoost;
                System.out.println("\n"+name + " smashes the target with a boosted holy attack!. Damage "+finalDamage);
                isSmiteActive = false; 
                
                target.takeDamage(finalDamage); 
            } else {
                System.out.println("\n"+name + " swings their weapon. Damage "+finalDamage);
               
                target.takeDamage(finalDamage); 
            }
            
        } else {
            System.out.println("\n"+name + " doesn't have enough stamina (SP) to attack.");
        }
    }
	
    @Override
    public void defend (){
        if (SP >= 3){ 
            SP -= 3;
            this.damageReduction = random.nextInt(11) + 15;
			this.isDefending = true;
			System.out.println("\n"+name + " enters a defensive stance, ready to mitigate up to " + this.damageReduction + " damage.");
        } else {
            System.out.println("\n"+name + " doesn't have enough stamina (SP) to defend.");
        }
    }
}