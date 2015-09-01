package net.sf.jabref.gui.actions;

import net.sf.jabref.gui.*;
import net.sf.jabref.gui.worker.AbstractWorker;
import net.sf.jabref.gui.worker.CallBack;
import net.sf.jabref.gui.worker.Worker;
import net.sf.jabref.logic.l10n.Localization;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Optional;

/**
 * Created by mg on 01.09.2015.
 */
public class GeneralAction extends MnemonicAwareAction {
    private JabRefFrame jabRefFrame;
    private final Log LOGGER = LogFactory.getLog(JabRefFrame.class);

    private Optional<BaseAction> baseAction;
    private Optional<AbstractWorker> abstractWorker;

    private final String command;

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text,
                         String description, URL icon) {
        super(new ImageIcon(icon));
        this.jabRefFrame = jabRefFrame;
        this.command = command;
        putValue(Action.NAME, text);
        putValue(Action.SHORT_DESCRIPTION, Localization.lang(description));
    }

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text,
                         String description, String imageName,
                         KeyStroke key) {
        super(GUIGlobals.getImage(imageName));
        this.jabRefFrame = jabRefFrame;
        this.command = command;
        putValue(Action.NAME, text);
        putValue(Action.ACCELERATOR_KEY, key);
        putValue(Action.SHORT_DESCRIPTION, Localization.lang(description));
    }

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text) {
        this.jabRefFrame = jabRefFrame;
        putValue(Action.NAME, text);
        this.command = command;
    }

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text, KeyStroke key) {
        this.jabRefFrame = jabRefFrame;
        this.command = command;
        putValue(Action.NAME, text);
        putValue(Action.ACCELERATOR_KEY, key);
    }

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text, String description) {
        this.jabRefFrame = jabRefFrame;
        this.command = command;
        ImageIcon icon = GUIGlobals.getImage(command);
        if (icon != null) {
            putValue(Action.SMALL_ICON, icon);
        }
        putValue(Action.NAME, text);
        putValue(Action.SHORT_DESCRIPTION, Localization.lang(description));
    }

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text, String description, KeyStroke key) {
        this.jabRefFrame = jabRefFrame;
        this.command = command;
        ImageIcon icon = GUIGlobals.getImage(command);
        if (icon != null) {
            putValue(Action.SMALL_ICON, icon);
        }
        putValue(Action.NAME, text);
        putValue(Action.SHORT_DESCRIPTION, Localization.lang(description));
        putValue(Action.ACCELERATOR_KEY, key);
    }

    public GeneralAction(JabRefFrame jabRefFrame, String command, String text, String description, KeyStroke key, String imageUrl) {
        this.jabRefFrame = jabRefFrame;
        this.command = command;
        ImageIcon icon = GUIGlobals.getImage(imageUrl);
        if (icon != null) {
            putValue(Action.SMALL_ICON, icon);
        }
        putValue(Action.NAME, text);
        putValue(Action.SHORT_DESCRIPTION, Localization.lang(description));
        putValue(Action.ACCELERATOR_KEY, key);
    }

    public String getCommand() {
        return command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jabRefFrame.tabbedPane.getTabCount() > 0) {
            try {
                ((BasePanel) jabRefFrame.tabbedPane.getSelectedComponent())
                        .runCommand(command);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        } else {
            LOGGER.info("Action '" + command + "' must be disabled when no database is open.");
        }
    }

    private void runImplementation() {
        try {
            if(baseAction.isPresent()) {
                baseAction.get().action();
            } else if (abstractWorker.isPresent()) {
                // This part uses Spin's features:
                Worker wrk = abstractWorker.get().getWorker();
                // The Worker returned by getWorker() has been wrapped
                // by Spin.off(), which makes its methods be run in
                // a different thread from the EDT.
                CallBack clb = abstractWorker.get().getCallBack();

                abstractWorker.get().init(); // This method runs in this same thread, the EDT.
                // Useful for initial GUI actions, like printing a message.

                // The CallBack returned by getCallBack() has been wrapped
                // by Spin.over(), which makes its methods be run on
                // the EDT.
                wrk.run(); // Runs the potentially time-consuming action
                // without freezing the GUI. The magic is that THIS line
                // of execution will not continue until run() is finished.
                clb.update(); // Runs the update() method on the EDT.
            } else {
                throw new IllegalStateException("Either a baseAction or an abstractWorker must be present");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
