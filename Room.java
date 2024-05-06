
import java.util.ArrayList;
import java.util.HashMap;

class Room {
    final String name, description;
    final HashMap <String, Room> gates;
    final ArrayList <Item> items;
    static HashMap <String, Room> tempMap = new HashMap<>();


    public Room(String name, String description){
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.gates = new HashMap<>();
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        String combinedItems = "";
        if (this.items.size() !=0){
            for (Item item : this.items){
                if (item.visible)
                    combinedItems += ", "+item.name;
            }
        }
        return this.description + combinedItems ;
    }

    public void setGate (String direction, Room nextRoom){
        gates.put(direction, nextRoom);
    }

    public Room getExit (String direction){
        return gates.get(direction);
    }

    static Room getRoom(String name){
        return tempMap.get(name);
    }

    public void addItem (Item item){
        items.add(item);
    }

    @Override public String toString() {
        return ("\t"+ this.description);
    }

    static HashMap<String, Room> createMap(){
        Room mudroom = new Room("Mudroom","Shoes on the floor, rainjackets on hangers");
        Room hallway = new Room("Hallway","Narrow hallway");
        Room livingRoom = new Room("Living room","Carpet, flat screen TV, big window");

        //setup gates
        mudroom.setGate("east",hallway);
        hallway.setGate("west",mudroom);

        hallway.setGate("north", livingRoom);
        livingRoom.setGate("south", hallway);

        //create the rooms in the map and return the map
        tempMap.put(mudroom.getName(), mudroom);
        tempMap.put(hallway.getName(),hallway);
        tempMap.put(livingRoom.getName(),livingRoom);

        //Setup interactive items in rooms
        Item umbrela = new Item("umbrella", "Brand new large, black, umbrella" , true ,false);
        Item key = new Item("key","looks like a room key",false,false);
        mudroom.addItem(umbrela);
        umbrela.hideItem(key);

        return tempMap;
    }
}
