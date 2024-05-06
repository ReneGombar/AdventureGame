public class Item {
    String name, description;
    boolean visible;
    boolean takeable;
    final Item[] contains = new Item[1];

    Item(String name, String description, boolean visible, boolean takeable){
        this.name = name;
        this.description = description;
        this.visible = visible;
        this.takeable = takeable;
        this.contains[0] = null;
    }

    void hideItem(Item item){
        item.visible = false;
        this.contains[0] = (item);
    }
}
