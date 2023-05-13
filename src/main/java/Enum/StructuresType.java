package Enum;

public enum StructuresType {
    STAIRS(0,999,false,0,30,2,Resource.STONE),
    LADDER(0,999,false,0,10,1,Resource.WOOD),
    MOVING_SHIELD(0,150,true,0,5,1,Resource.WOOD), // سپر متحرک
    WALL_BREAKER(500,300,true,1,100,3,Resource.WOOD), //دیوار شکن
    SIEGE_TOWER(0,250,true,0,50,2,Resource.WOOD), // برج محاصره
    CATAPULT(200,200,true,5,150,2,Resource.WOOD), // منجنیق کوچیک
    TREBUCHET(350,100,false,7,150,2,Resource.WOOD), // منجنیق بزرگ
    FLAME_THROWER(300,200,false,4,150,2,Resource.WOOD); // بالیستا نمیتونه حرکت کنه؟؟؟
    private int damage;
    private int hp;
    private boolean canMove;
    private int range;
    private int amountOfMaterial;
    private int amountOfEngineer;
    public Resource resource;



    StructuresType(int damage, int hp, boolean canMove, int range, int amountOfMaterial, int amountOfEngineer, Resource resource) {
        this.damage = damage;
        this.hp = hp;
        this.canMove = canMove;
        this.range = range;
        this.amountOfMaterial = amountOfMaterial;
        this.amountOfEngineer = amountOfEngineer;
        this.resource = resource;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean CanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAmountOfMaterial() {
        return amountOfMaterial;
    }

    public void setAmountOfMaterial(int amountOfMaterial) {
        this.amountOfMaterial = amountOfMaterial;
    }

    public int getAmountOfEngineer() {
        return amountOfEngineer;
    }

    public void setAmountOfEngineer(int amountOfEngineer) {
        this.amountOfEngineer = amountOfEngineer;
    }


    public void setResource(Resource resource) {
        this.resource = resource;
    }
    public boolean checkForEquals(StructuresType... types) {
        for (StructuresType type : types) {
            if(type.equals(this)) return true;
        }
        return false;
    }
    public static StructuresType getType(String type) {
        for (StructuresType value : StructuresType.values()) {
            if(value.name().equals(type)) return value;
        }
        return null;
    }
}
