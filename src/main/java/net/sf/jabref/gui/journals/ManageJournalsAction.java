/*  Copyright (C) 2003-2011 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package net.sf.jabref.gui.journals;

import net.sf.jabref.gui.JabRefFrame;
import net.sf.jabref.gui.MnemonicAwareAction;
import net.sf.jabref.logic.l10n.Localization;
import net.sf.jabref.util.Util;

import java.awt.event.ActionEvent;

import javax.swing.Action;

/**
 * Created by IntelliJ IDEA.
 * User: alver
 * Date: Sep 22, 2005
 * Time: 10:45:02 PM
 * To browseOld this template use File | Settings | File Templates.
 */
public class ManageJournalsAction extends MnemonicAwareAction {

    private final JabRefFrame frame;

    public ManageJournalsAction(JabRefFrame frame) {
        super();
        putValue(Action.NAME, Localization.menuTitle("Manage journal abbreviations"));
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ManageJournalsPanel panel = new ManageJournalsPanel(frame);
        Util.placeDialog(panel.getDialog(), frame);
        panel.setValues();
        panel.getDialog().setVisible(true);
    }
}
