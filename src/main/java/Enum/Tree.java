package Enum;

import javax.swing.plaf.PanelUI;

public enum Tree {
    TAMARIX,
    CHERRY,
    OLIVE,
    COCONUT,
    PALM;
    public static Tree getTreeByName(String name) {
        for (Tree value : Tree.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }

}
