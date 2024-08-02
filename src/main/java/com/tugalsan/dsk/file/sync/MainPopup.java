package com.tugalsan.dsk.file.sync;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class MainPopup {

    public static void selectAll_copy_togather(JTextComponent t) {
        MainAsync.run(() -> {
//                mainFrame.taConsole.setText("MainPopup.selectAll_copy_togather");
            var popup = new JPopupMenu();
            var copyAction = new AbstractAction("Hepsini seç ve kopyala") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    t.selectAll();
                    t.copy();
                }
            };
            popup.add(copyAction);
            t.setComponentPopupMenu(popup);
        });
    }

    public static void selectAll_copy_seperated(JTextComponent t) {
        MainAsync.run(() -> {
//                mainFrame.taConsole.setText("MainPopup.selectAll_copy_seperated");
            var popup = new JPopupMenu();
            var selectAllAction = new AbstractAction("Hepsini seç") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    t.selectAll();
                }
            };
            var copyAction = new AbstractAction("Kopyala") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    t.copy();
                }
            };
            popup.add(selectAllAction);
            popup.add(copyAction);
            t.setComponentPopupMenu(popup);
        });
    }
}
