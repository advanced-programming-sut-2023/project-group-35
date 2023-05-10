package Enum;

public enum Resource {
    WOOD(20 , 18),
    GOLD(1,1),
    STONE(30 , 20),
    WHEAT(10,5),
    APPLE(15,10),
    CHEESE(20,15),
    MEAT(20,15),
    PROCESSED_MEAT(25,18),
    BREAD(20,15),
    IRON(40 , 30),
    WINE(10,5),
    BARLEY(5,3),
    FLOUR(12,7),
    HORSE(50,40),
    TAR(20,10),
    BOW(30,20),
    SPEAR(30,20),
    ARMOR(50,45),
    SWORD(50,45);



    public int buyPrice;
    public int sellPrice;

    Resource(int buyPrice, int sellPrice) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
    public static Resource getResourceByName(String name) {
        for (Resource value : Resource.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
}
