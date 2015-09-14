package net.sf.jabref.gui.labels;

import net.sf.jabref.Globals;
import net.sf.jabref.gui.IconTheme;
import net.sf.jabref.gui.keyboard.KeyBinds;
import net.sf.jabref.logic.l10n.Localization;

import javax.swing.*;
import java.util.Optional;

public enum MenuLabel {

    Abbreviate_journal_names_ISO("Abbreviate_journal_names_(ISO)"),
    Abbreviate_journal_names_MEDLINE("Abbreviate_journal_names_(MEDLINE)"),
    About_JabRef("About_JabRef", "About JabRef", "about"),
    Append_database("Append_database"),

    Help_Contents("Help_contents", "Help_contents", "helpContents"),
    JabRef_Help("JabRef help", "JabRef help", "help", KeyBinds.HELP);

    private String titleKey;
    private Optional<String> descriptionKey = Optional.empty();
    private Optional<ImageIcon> icon = Optional.empty();
    private Optional<KeyStroke> keyStroke = Optional.empty();

    MenuLabel(String titleKey) {
        this.titleKey = Localization.menuTitle(titleKey);
    }

    MenuLabel(String titleKey, String descriptionKey) {
        this.titleKey = Localization.menuTitle(titleKey);
        this.descriptionKey = Optional.of(Localization.lang(descriptionKey));
    }

    MenuLabel(String titleKey, String descriptionKey, String iconName) {
        this.titleKey = Localization.menuTitle(titleKey);
        this.descriptionKey = Optional.of(Localization.lang(descriptionKey));
        this.icon = Optional.of(IconTheme.getImage(iconName));
    }

    MenuLabel(String titleKey, String descriptionKey, String iconName, String keyStroke) {
        this.titleKey = Localization.menuTitle(titleKey);
        this.descriptionKey = Optional.of(Localization.lang(descriptionKey));
        this.icon = Optional.of(IconTheme.getImage(iconName));
        this.keyStroke = Optional.of(Globals.prefs.getKey(keyStroke));
    }

    public String getTitleKey() {
        return titleKey;
    }

    public Optional<String> getDescriptionKey() {
        return descriptionKey;
    }

    public Optional<ImageIcon> getIcon() {
        return icon;
    }

    public Optional<KeyStroke> getKeyStroke() {
        return keyStroke;
    }
}
