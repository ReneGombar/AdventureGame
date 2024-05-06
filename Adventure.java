import java.util.HashMap;
import java.util.Scanner;

public class Adventure {
    static Room curentRoom;
    
    static HashMap <String, Room> gameMap;

    static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String userInput;
        
        gameMap = Room.createMap();

        // System.out.println(gameMap.keySet());
        // System.out.println(gameMap.values());
        
        Player player1 = new Player("Rene",gameMap.get("Mudroom"));
        
        // System.out.println(player1.getCurentRoom());
        // System.out.println(gameMap.values());


        System.out.println("Labyrinth Game");
        System.out.println("Type 'help' for a list of commands.");
        
        do {
            System.out.print("> ");
            userInput = input.nextLine();
            player1.userInputHandler(userInput, player1.getCurentRoom());
            
        } while (!userInput.equalsIgnoreCase("quit"));
    }
}
