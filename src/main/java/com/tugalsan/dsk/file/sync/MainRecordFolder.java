package com.tugalsan.dsk.file.sync;

import com.tugalsan.api.desktop.server.TS_DesktopPathUtils;
import com.tugalsan.api.file.properties.server.TS_FilePropertiesUtils;
import java.nio.file.Path;
import java.util.Optional;
import javax.swing.text.JTextComponent;

public class MainRecordFolder {

    public static Path pathLast = Main.pathCurDir;

    private static void thing(String param, JTextComponent tf) {
        MainAsync.run(() -> {
            Main.mainFrame.taConsole.setText("MainRecordFile.thing." + param + "." + tf.getText());
            var u = TS_DesktopPathUtils.chooseDirectory(param, Optional.of(pathLast));
            if (u.isExcuse()) {
                MainLog.add(param + ".cancelled: " + u.excuse().getMessage());
                return;
            }
            var selectedFile = u.value();
            MainLog.add(param + ".selected_as:" + selectedFile);
            tf.setText(selectedFile.toAbsolutePath().toString());
            TS_FilePropertiesUtils.setValue(Main.props, param, selectedFile);
            TS_FilePropertiesUtils.write(Main.props, Main.pathProperties);
        });
    }

    public static void src_0() {
        thing(PARAM_SRC_0, Main.mainFrame.tfSrcDir0);
    }
    final public static String PARAM_SRC_0 = "PARAM_SRC_0";

    public static void src_1() {
        thing(PARAM_SRC_1, Main.mainFrame.tfSrcDir1);
    }
    final public static String PARAM_SRC_1 = "PARAM_SRC_1";

    public static void src_2() {
        thing(PARAM_SRC_2, Main.mainFrame.tfSrcDir2);
    }
    final public static String PARAM_SRC_2 = "PARAM_SRC_2";

    public static void src_3() {
        thing(PARAM_SRC_3, Main.mainFrame.tfSrcDir3);
    }
    final public static String PARAM_SRC_3 = "PARAM_SRC_3";

    public static void src_4() {
        thing(PARAM_SRC_4, Main.mainFrame.tfSrcDir4);
    }
    final public static String PARAM_SRC_4 = "PARAM_SRC_4";

    public static void src_5() {
        thing(PARAM_SRC_5, Main.mainFrame.tfSrcDir5);
    }
    final public static String PARAM_SRC_5 = "PARAM_SRC_5";

    public static void dst_0() {
        thing(PARAM_DST_0, Main.mainFrame.tfDstDir0);
    }
    final public static String PARAM_DST_0 = "PARAM_DST_0";

    public static void dst_1() {
        thing(PARAM_DST_1, Main.mainFrame.tfDstDir1);
    }
    final public static String PARAM_DST_1 = "PARAM_DST_1";

    public static void dst_2() {
        thing(PARAM_DST_2, Main.mainFrame.tfDstDir2);
    }
    final public static String PARAM_DST_2 = "PARAM_DST_2";

    public static void dst_3() {
        thing(PARAM_DST_3, Main.mainFrame.tfDstDir3);
    }
    final public static String PARAM_DST_3 = "PARAM_DST_3";

    public static void dst_4() {
        thing(PARAM_DST_4, Main.mainFrame.tfDstDir4);
    }
    final public static String PARAM_DST_4 = "PARAM_DST_4";

    public static void dst_5() {
        thing(PARAM_DST_5, Main.mainFrame.tfDstDir5);
    }
    final public static String PARAM_DST_5 = "PARAM_DST_5";
}
