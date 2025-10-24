import java.util.Random;

public class Shaman extends Character {
    private int baseMagicDamage = 20;
    private boolean isPrepActive = false;
	int maxMana = 150;	
	int Maxboost = 30; 

    public Shaman(String name, int healthPotions, int manaPotions){
        
        super (name, 150, 150, 70, healthPotions, manaPotions); 
        this.dodgeChance = 15; 
    }

    @Override
    public void Primary(Character target){
        if (MP >= 30){ 
            MP -= 30;
            isPrepActive = true;
            
            // Calculate MP percentage (Max MP is 150)
            double mpPercentage = (double) MP / 150.0; 
            
            // Calculate a temporary damage boost (Max boost increased to 25)
            int dmgBoost = (int) (mpPercentage * 25); 
            
            System.out.println("\n"+name + " uses Lightning Bolt Prep. Next attack will be boosted by up to " + dmgBoost + " damage based on current MP.");
            
            baseMagicDamage += dmgBoost;
            
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
            System.out.println("\n"+name + " uses Quick Heal. Health is restored by " + healAmount + ". Current HP: " + HP);  
        } else {
            System.out.println("\nYou don't have enough MP. Use a potion to restore MP.");
        }
    }

    @Override
    public void attack(Character target){
        if (SP >= 2){ 
            SP -= 2;
            
            
            int finalDamage = baseMagicDamage + random.nextInt(11); 

            if (isPrepActive){
                System.out.println("\n"+name + " unleashes a powerful Magic Bolt! Damage "+ finalDamage);
                double manaPercentage = (double) MP / maxMana;	
                int boostValue = (int) (Maxboost * manaPercentage);  
                finalDamage += boostValue;
                isPrepActive = false;
            } else {
                System.out.println("\n"+name + " casts a basic Magic Bolt. Damage "+finalDamage);
            }
            
            target.takeDamage(finalDamage); 
            
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