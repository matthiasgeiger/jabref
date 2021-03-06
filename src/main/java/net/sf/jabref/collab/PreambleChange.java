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
package net.sf.jabref.collab;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.sf.jabref.gui.BasePanel;
import net.sf.jabref.model.database.BibtexDatabase;
import net.sf.jabref.gui.undo.NamedCompound;
import net.sf.jabref.gui.undo.UndoablePreambleChange;
import net.sf.jabref.logic.l10n.Localization;

class PreambleChange extends Change {

    private final String mem;
    private final String disk;
    private final InfoPane tp = new InfoPane();
    private final JScrollPane sp = new JScrollPane(tp);


    public PreambleChange(String tmp, String mem, String disk) {
        super("Changed preamble");
        this.disk = disk;
        this.mem = mem;

        StringBuilder text = new StringBuilder();
        text.append("<FONT SIZE=3>");
        text.append("<H2>").append(Localization.lang("Changed preamble")).append("</H2>");

        if (disk != null && !disk.isEmpty()) {
            text.append("<H3>").append(Localization.lang("Value set externally")).append(":</H3>" + "<CODE>").append(disk).append("</CODE>");
        } else {
            text.append("<H3>").append(Localization.lang("Value cleared externally")).append("</H3>");
        }

        if (mem != null && !mem.isEmpty()) {
            text.append("<H3>").append(Localization.lang("Current value")).append(":</H3>" + "<CODE>").append(mem).append("</CODE>");
        }

        //tp.setContentType("text/html");
        tp.setText(text.toString());
    }

    @Override
    public boolean makeChange(BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit) {
        panel.database().setPreamble(disk);
        undoEdit.addEdit(new UndoablePreambleChange(panel.database(), panel, mem, disk));
        secondary.setPreamble(disk);
        return true;
    }

    @Override
    JComponent description() {
        return sp;
    }
}
