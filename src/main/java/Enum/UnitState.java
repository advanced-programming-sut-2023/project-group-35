package Enum;

import com.sun.nio.sctp.AbstractNotificationHandler;

public enum UnitState {
    STABLE(1),
    DEFENSIVE(4),
    OFFENSIVE(100);

    private int range;

    UnitState(int range) {
        this.range = range;
    }
    public int getRange() {
        return range;
    }

    public static UnitState getUnitState(String state) {
        for (UnitState value : UnitState.values()) {
            if (value.name().compareToIgnoreCase(state) == 0) return value;
        }
        return null;
    }
}
