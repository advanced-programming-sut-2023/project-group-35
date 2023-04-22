package Enum;

public enum Resource {
    WOOD(20 , 18),
//    GOLD(),
//    STONE(),
//    WHEAT(),
//    APPLE(),
//    CHEESE(),
//    MEAT(),
//    PROCESSED_MEAT(),
//    BREAD(),
//    IRON(),
//    BARLEY(),
//    BEAR(),
//    FLOUR(),
//    HORSE(),
//    TAR(),
      BOW(100,50);
//    SPEAR(),
//    ARMOR(),
//    SWORD();



    private int buyPrice;
    private int sellPrice;

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
