import java.util.Random;
import java.util.Scanner;

public class CombatSimulator {

    private static final Scanner scanner = new Scanner(System.in);
    
    private static final int HEALTH_POTION_HEAL = 50;
    private static final int MANA_POTION_RESTORE = 50;
    
    public static void displayStats(Character char1, Character char2) {
        System.out.println("\n--- Current Battle Status ---");
        System.out.printf("%-10s | HP: %-5d | MP: %-5d | SP: %-5d | HP Potions: %d | MP Potions: %d\n", 
            char1.name, char1.HP, char1.MP, char1.SP, char1.healthPotions, char1.manaPotions); 
        System.out.printf("%-10s | HP: %-5d | MP: %-5d | SP: %-5d | HP Potions: %d | MP Potions: %d\n", 
            char2.name, char2.HP, char2.MP, char2.SP, char2.healthPotions, char2.manaPotions); 
	}

    public static int getPlayerChoice() {
        while (true) {
            System.out.println("\n--- Choose Your Class ---");
            System.out.println("1. Shaman");
            System.out.println("2. Priest");
            System.out.print("Enter choice (1 or 2): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1 || choice == 2) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    
    public static void takePlayerTurn(Character player, Character opponent) {
        while (true) {
            System.out.println("\n*** " + player.name + "'s Turn ***");
            System.out.println("Select Action:");
            System.out.println("1. Attack (2 SP)");
			System.out.println("2. Defend (3 SP)"); 
            System.out.println("3. Primary Skill");
            System.out.println("4. Secondary Skill");
            System.out.println("5. Use Item (Potions)");
            System.out.print("Enter action (1, 2, 3, 4 or 5): ");

            if (scanner.hasNextInt()) {
                int action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 1:
                        player.attack(opponent);
                        return;
					case 2:
						player.defend();
						return;
                    case 3:
                        player.Primary(opponent);
                        return;
                    case 4:
                        player.Secondary(opponent);
                        return;
                    case 5:
                        
                        if (handlePlayerItemUse(player)) {
                            return; 
                        }
                        break; 
                    default:
                        System.out.println("Invalid action. Please enter 1, 2, 3, 4 or 5.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public static boolean handlePlayerItemUse(Character player) {
        System.out.println("\n--- Use Item ---");
        System.out.println("1. Health Potion (" + player.healthPotions + " remaining) - Restores " + HEALTH_POTION_HEAL + " HP");
        System.out.println("2. Mana Potion (" + player.manaPotions + " remaining) - Restores " + MANA_POTION_RESTORE + " MP");
        System.out.println("3. Back to Actions");
        System.out.print("Choose potion to use (1, 2, or 3): ");

        if (scanner.hasNextInt()) {
            int itemChoice = scanner.nextInt();
            scanner.nextLine();
            
            if (itemChoice == 1) {
                if (player.healthPotions > 0) {
                    player.healthPotions--;
                    player.useHealthPotion(HEALTH_POTION_HEAL);
                    return true; 
                } else {
                    System.out.println("No Health Potions left!");
                    return false;
                }
            } else if (itemChoice == 2) {
                if (player.manaPotions > 0) {
                    player.manaPotions--;
                    player.useManaPotion(MANA_POTION_RESTORE);
                    return true; 
                } else {
                    System.out.println("No Mana Potions left!");
                    return false;
                }
            } else if (itemChoice == 3) {
                return false; 
            } else {
                System.out.println("Invalid item choice.");
                return false;
            }
        } else {
            System.out.println("Invalid input.");
            scanner.nextLine();
            return false;
        }
    }
 
    public static void takeCPUTurn(Character cpu, Character player) {
        System.out.println("\n*** " + cpu.name + "'s Turn ***");
        
        
        int maxHP = 150;
        int maxMP = 150;
        
        boolean potionUsed = false;
        
        if (((double)cpu.HP / maxHP) < 0.3 && cpu.healthPotions > 0) {
            System.out.print("CPU selects HEALTH POTION! -> ");
            cpu.healthPotions--; 
            cpu.useHealthPotion(HEALTH_POTION_HEAL);
            potionUsed = true;
        }
        
        else if (!potionUsed && ((double)cpu.MP / maxMP) < 0.3 && cpu.manaPotions > 0) {
            System.out.print("CPU selects MANA POTION! -> ");
            cpu.manaPotions--;
            cpu.useManaPotion(MANA_POTION_RESTORE);
            potionUsed = true;
        }

        if (potionUsed) {
            return; 
        }

        int action = cpu.random.nextInt(4); 

        switch (action) {
            case 0:
                System.out.print("CPU selects ATTACK! -> ");
                cpu.attack(player);
                break;
			case 1:
                System.out.print("CPU selects DEFEND! -> ");
                cpu.defend();
                break;	
            case 2:
                System.out.print("CPU selects PRIMARY SKILL! -> ");
                cpu.Primary(player);
                break;
            case 3:
                System.out.print("CPU selects SECONDARY SKILL! -> ");
                cpu.Secondary(player);
                break;
        }
    }
    public static void main(String[] args) {
        
		final int PLAYER_START_HP_POTIONS = 3; 
        final int PLAYER_START_MP_POTIONS = 3; 
        final int CPU_START_HP_POTIONS = 3; 
        final int CPU_START_MP_POTIONS = 3;
		
        Character player;
        Character opponent;

        int choice = getPlayerChoice();
        
        if (choice == 1) {
            player = new Shaman("Shaman (Player)", PLAYER_START_HP_POTIONS, PLAYER_START_MP_POTIONS);
            opponent = new Priest("Priest (CPU)", CPU_START_HP_POTIONS, CPU_START_MP_POTIONS);
            System.out.println("\nYou chose the **Shaman**! Your opponent is the **Priest**.");
        } else {
            player = new Priest("Priest (Player)", PLAYER_START_HP_POTIONS, PLAYER_START_MP_POTIONS);
            opponent = new Shaman("Shaman (CPU)", CPU_START_HP_POTIONS, CPU_START_MP_POTIONS);
            System.out.println("\nYou chose the **Priest**! Your opponent is the **Shaman**.");
        }
        
        int turnCount = 1;
        System.out.println("\n--- Combat Initiated: " + player.name + " vs. " + opponent.name + " ---");
        
        displayStats(player, opponent); 

        while (player.Alive() && opponent.Alive()) {
            System.out.println("\n======== TURN " + turnCount + " ========");

            takePlayerTurn(player, opponent);

            if (!opponent.Alive()) {
                break; 
            }

            takeCPUTurn(opponent, player);

            if (!player.Alive()) {
                break; 
            }

            displayStats(player, opponent);
            
            turnCount++;
        }

        System.out.println("\n\n=============== BATTLE ENDED ===============");
        if (player.Alive()) {
            System.out.println(player.name + " is victorious!");
        } else if (opponent.Alive()) {
            System.out.println(opponent.name + " is victorious!");
        } else {
            System.out.println("Both fighters were knocked out! It's a draw!");
        }
        System.out.println("==========================================");
        
        scanner.close();
    }
}