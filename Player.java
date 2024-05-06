class Player {
    String name;
    final Item[] inventory = new Item[1];
    Room curentRoom;

    Player (String name, Room startingRoom){
        this.name = name;
        this.inventory[0] = null;
        this.curentRoom = startingRoom;
    }

    public Room getCurentRoom() {
        return this.curentRoom;
    }

    public void setCurrentRoom(Room nextRoom){
        this.curentRoom = nextRoom;
    }

    public void userInputHandler(String command, Room currentRoom) {
        String commandLow = command.toLowerCase();
        
        if (commandLow.startsWith("take")) {
            commandLow = commandLow.replace("take", "");
            commandLow = commandLow.replaceAll("\\s+", "");
            take(commandLow);
        }
        else if (commandLow.startsWith("drop")){
            commandLow = commandLow.replace("drop", "");
            commandLow = commandLow.replaceAll("\\s+", "");
            drop(commandLow);
        }
        else if (commandLow.startsWith("inspect")){
            commandLow = commandLow.replace("inspect", "");
            commandLow = commandLow.replaceAll("\\s+", "");
            inspect(commandLow);
        }
        else{
            switch (commandLow) {
                case "look":
                    lookAround();
                    break;
                case "north":
                case "south":
                case "east":
                case "west":
                    move(commandLow);
                    break;
                case "help":
                    printHelp();
                    break;
                case "inventory":
                    inventory();
                    break;
                case "quit":
                    System.out.println("Bye Bye!");
                    break;
                default:
                    System.out.println("Command not found!");
                    break;
            }
        }
    }

    public void inventory(){
        if (this.inventory[0] !=null){
            System.out.println("My inventory: "+ this.inventory[0].name);
        }
        else{
            System.out.println("My inventory is empty!");
            
        }
    }

    public void drop(String item){
        if (this.inventory[0] != null){
            if (this.inventory[0].name.equalsIgnoreCase(item)){
                Item oldItem = this.inventory[0];
                oldItem.visible = true;
                this.inventory[0] = null;
                getCurentRoom().items.add(oldItem);
                System.out.println("I dropped "+oldItem.name + " from my inventory.");
            }
        }
        else{
            System.out.println("Nothing to drop!");
        }
    }

    public void lookAround(){
        System.out.print(getCurentRoom().name+": "+getCurentRoom().getDescription());
        for (Item item : getCurentRoom().items){
            if (item.contains[0] != null && item.contains[0].visible)  System.out.print( ", "+item.contains[0].name);
        }
        System.out.println();
    }

    public void inspect (String item){
        Item findItem = null;
        for (Item itemObj : getCurentRoom().items){ // search room 
            if (itemObj.name.equalsIgnoreCase(item)){   // if item in room matches search item
                findItem = itemObj;     // assign the item to the found item
                
                if (findItem != null){
                    if ( findItem.contains[0] != null)   {// if the room item contains another item inside of it
                            findItem.contains[0].visible = true;
                    }
                }
            }
        }
        
        
        if (this.inventory[0] != null){ // search player inventory for a matching item name
            if (this.inventory[0].name.equalsIgnoreCase(item)){
                findItem = this.inventory[0];
            }
        }

        if (findItem != null){      // if an matching item was found in the room or inside another item or in player inventory
            if (findItem.visible) System.out.print("\t "+ findItem.description);

            if (findItem.contains[0] != null){
                findItem.contains[0].visible = true;
                findItem.contains[0].takeable = true;
                System.out.print(", " + findItem.contains[0].name);
            }
            System.out.println();
        }
        else{
            System.out.println(item + " - nothing found!");
        }
    }

    public void move(String direction) {
        Room nextRoom = this.getCurentRoom().getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door in that direction.");
        } else {
            this.curentRoom = nextRoom;
            System.out.println("I am now in the " + nextRoom.getName());
        }
    }

    public void take(String item){
        Item findItem = null;
        Item hiddenItem  = null;
        for (Item itemObj : getCurentRoom().items){
            if (itemObj.name.equalsIgnoreCase(item)){
                findItem = itemObj;
            }

            if (itemObj.contains[0] !=null){
                if (itemObj.contains[0].name.equalsIgnoreCase(item) && itemObj.contains[0].takeable){
                    findItem = itemObj;
                    hiddenItem = itemObj.contains[0];
                }
            }
        }

        if (findItem != null && hiddenItem == null){
            if (findItem.takeable){     //check if item is takeable
                if (this.inventory[0] !=null){  // check if inventory is not empty
                    Item oldItem = this.inventory[0];   // save old item in temp
                    this.inventory[0] = findItem;           // add the new item to player inventory
                    
                    getCurentRoom().items.add(oldItem);
                    System.out.println("I dropped: "+ oldItem.name+" and picked up "+findItem.name);
                    getCurentRoom().items.remove(findItem);
                }
                else{   // if inventory is empty
                    System.out.println("I took: "+ item);
                    this.inventory[0] = findItem;
                    getCurentRoom().items.remove(findItem);
                }
            }
            else{
                System.out.println(findItem.name +" can not be taken!");
            }
        }

        if (hiddenItem != null){
            if (hiddenItem.takeable){     //check if item is takeable
                if (this.inventory[0] !=null){  // check if inventory is not empty
                    Item oldItem = this.inventory[0];   // save old item in temp
                    this.inventory[0] = hiddenItem;           // add the new item to player inventory
                    
                    getCurentRoom().items.add(oldItem);
                    System.out.println("I dropped: "+ oldItem.name+" and picked up "+hiddenItem.name);
                    findItem.contains[0] = null;
                }
                else{   // if inventory is empty
                    System.out.println("I took: "+ item);
                    this.inventory[0] = hiddenItem;
                    findItem.contains[0] =null;
                }
            }
            else{
                System.out.println(findItem.name +" can not be taken!");
            }
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("look - Look around you.");
        System.out.println("north, south, east, west - Move in a direction.");
        System.out.println("take, drop <item> - Take or drop an item.");

        System.out.println("inventory - Display player inventory.");
        System.out.println("quit - Quit the game.");
    }
}
