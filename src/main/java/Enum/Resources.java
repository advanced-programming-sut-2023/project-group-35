package Enum;

public enum Resources {
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

    Resources(int buyPrice, int sellPrice) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
