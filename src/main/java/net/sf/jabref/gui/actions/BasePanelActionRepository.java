package net.sf.jabref.gui.actions;

import java.util.HashMap;
import java.util.Map;

public class BasePanelActionRepository {
    private Map<String, GeneralAction> registeredActions;

    public BasePanelActionRepository() {
        registeredActions = new HashMap<>();
    }

    public void registerNewAction(GeneralAction action) {
        registeredActions.put(action.getCommand() ,action);
    }
}
