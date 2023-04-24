package Enum;

public enum Resource {
    WOOD(20 , 18),
//    GOLD(),
    STONE(20, 10),
//    WHEAT(),
//    APPLE(),
//    CHEESE(),
//    MEAT(),
//    PROCESSED_MEAT(),
//    BREAD(),
    IRON(10 , 20),
//    BARLEY(),
//    BEAR(),
//    FLOUR(),
//    HORSE(),
//    TAR(),
      BOW(100,50);
//    SPEAR(),
//    ARMOR(),
//    SWORD();



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
