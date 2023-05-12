package Enum;

import com.sun.nio.sctp.AbstractNotificationHandler;

public enum UnitState {
    STABLE,
    DEFENSIVE,
    OFFENSIVE;
    public static UnitState getUnitState(String state) {
        for (UnitState value : UnitState.values()) {
            if(value.name().compareToIgnoreCase(state) == 0) return value;
        }
        return null;
        //todo check
    }
}
