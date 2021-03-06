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

import net.sf.jabref.*;
import net.sf.jabref.gui.BasePanel;
import net.sf.jabref.gui.PreviewPanel;
import net.sf.jabref.gui.undo.NamedCompound;
import net.sf.jabref.gui.undo.UndoableInsertEntry;
import net.sf.jabref.logic.id.IdGenerator;
import net.sf.jabref.model.database.BibtexDatabase;
import net.sf.jabref.model.entry.BibtexEntry;

class EntryAddChange extends Change {

    private final BibtexEntry diskEntry;
    private final JScrollPane sp;


    public EntryAddChange(BibtexEntry diskEntry) {
        super("Added entry");
        this.diskEntry = diskEntry;

        PreviewPanel pp = new PreviewPanel(null, diskEntry, null, new MetaData(), Globals.prefs.get(JabRefPreferences.PREVIEW_0));
        sp = new JScrollPane(pp);
    }

    @Override
    public boolean makeChange(BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit) {
        diskEntry.setId(IdGenerator.next());
        panel.database().insertEntry(diskEntry);
        secondary.insertEntry(diskEntry);
        undoEdit.addEdit(new UndoableInsertEntry(panel.database(), diskEntry, panel));
        return true;
    }

    @Override
    JComponent description() {
        return sp;
    }
}
