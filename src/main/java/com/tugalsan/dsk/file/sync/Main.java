package com.tugalsan.dsk.file.sync;

import com.tugalsan.api.cast.client.TGS_CastUtils;
import com.tugalsan.api.desktop.server.*;
import com.tugalsan.api.file.properties.server.TS_FilePropertiesUtils;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.file.server.TS_PathUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.IntStream;
import javax.swing.*;

//WHEN RUNNING IN NETBEANS, ALL DEPENDENCIES SHOULD HAVE TARGET FOLDER!
//cd C:\git\dsk\com.tugalsan.dsk.file.sync
//java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.dsk.file.sync-1.0-SNAPSHOT-jar-with-dependencies.jar
public class Main {

    final private static TS_Log d = TS_Log.of(Main.class);
    final public static TS_ThreadSyncTrigger threadKiller = TS_ThreadSyncTrigger.of();

    public static Path pathCurDir = TS_DesktopPathUtils.currentFolder();
    public static Path pathProperties = pathCurDir.resolve(Main.class.getPackageName() + ".properties");
    public static Path pathJar = pathCurDir.resolve("home").resolve("target").resolve(Main.class.getPackageName() + "-jar-with-dependencies_cleaned-and-migrated.jar");
    public static Properties props;
    public static MainFrame mainFrame;

    public static void main(String[] args) {
//        { //CHECK ADMIN
//            d.ce("main", "this program requires admin rights; if not, the program will terminate now...");
//            if (!TS_OsProcessUtils.isRunningAsAdministrator()) {
//                System.exit(1);
//            }
//            MainLog.add("admin rights detected, starting program...");
//        }

        if (args.length > 0) {
            d.cr("main", "arguments detected");
            IntStream.range(0, args.length).forEachOrdered(i -> {
                d.cr("main", "arg", i, args[i]);
            });

            var u_config = TS_PathUtils.toPath(args[0]);
            if (u_config.isPresent() && TS_FileUtils.isExistFile(u_config.value())) {
                pathProperties = u_config.value();
                pathCurDir = pathProperties.getParent();
                if (args.length > 1) {
                    if (args[1].equals("run_all")) {
                        MainCgi.run_all(threadKiller);
                        return;
                    } else {
                        d.ce("main", "ERROR unknown arg[1]", args[1]);
                        return;
                    }
                }
                //return; //no return because gui mode!
            } else if (args[0].equals("run_all")) {
                MainCgi.run_all(threadKiller);
                return;
            } else if (args[0].equals("run_idx")) {
                if (args.length != 2) {
                    d.ce("main", "ERROR args.length != 2", args.length);
                    return;
                }
                var idx = TGS_CastUtils.toInteger(args[1]);
                if (idx == null) {
                    d.ce("main", "ERROR args[1] as idx == null", args[1]);
                    return;
                }
                MainCgi.run_idx(idx);
                return;
            } else {
                d.ce("main", "ERROR unknown arg[0]", args[0]);
                return;
            }
        }

        TGS_UnSafe.run(() -> {
            TS_DesktopMainUtils.setThemeAndinvokeLaterAndFixTheme(() -> {
                {//CREATE FRAME
                    mainFrame = new MainFrame();
                    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    TS_DesktopWindowAndFrameUtils.showAlwaysInTop(mainFrame, false);
                }
                printLocation();
                readProperties();
                initPopUp();
                //INIT FRAME
                TS_DesktopWindowAndFrameUtils.setTitleSizeCenterWithMenuBar(mainFrame, Main.class.getPackageName(), TS_DesktopJMenuButtonBar.of(
                        TS_DesktopJMenuButton.of("Exit", mx -> {
                            System.exit(0);
                        }),
                        TS_DesktopJMenuButton.of("About", mx -> {
                            MainAsync.run(() -> {
                                TS_DesktopDialogInfoUtils.show("About", "Written by Tuğasan Karabacak (tugalsan@gmail.com)\nFor mesametal.cloud and mebosa.cloud");
                            });
                        })
                ));
                mainFrame.setSize(1000, 690);
                return mainFrame;
            });

        }, e -> TS_DesktopDialogInfoUtils.show("error", e.toString()));
    }

    private static void initPopUp() {
        MainPopup.selectAll_copy_togather(mainFrame.tfSrcDir0);
        MainPopup.selectAll_copy_togather(mainFrame.tfSrcDir1);
        MainPopup.selectAll_copy_togather(mainFrame.tfSrcDir2);
        MainPopup.selectAll_copy_togather(mainFrame.tfSrcDir3);
        MainPopup.selectAll_copy_togather(mainFrame.tfSrcDir4);
        MainPopup.selectAll_copy_togather(mainFrame.tfSrcDir5);
        MainPopup.selectAll_copy_togather(mainFrame.tfDstDir0);
        MainPopup.selectAll_copy_togather(mainFrame.tfDstDir1);
        MainPopup.selectAll_copy_togather(mainFrame.tfDstDir2);
        MainPopup.selectAll_copy_togather(mainFrame.tfDstDir3);
        MainPopup.selectAll_copy_togather(mainFrame.tfDstDir4);
        MainPopup.selectAll_copy_togather(mainFrame.tfDstDir5);
        MainPopup.selectAll_copy_togather(mainFrame.taConsole);
    }

    private static void printLocation() {
        MainLog.add("curDir: " + pathCurDir);
        MainLog.add("pathProperties: " + pathProperties);
    }

    public static void readProperties() {
        MainLog.add("props.reading...");
        if (!TS_FileUtils.isExistFile(pathProperties)) {
            d.ce("readProperties", "properties file not exists", pathProperties.toAbsolutePath().toString());
            return;
        }
        var u_props = TS_FilePropertiesUtils.createPropertyReader(pathProperties);
        if (u_props.isExcuse()) {
            MainLog.add("ERROR: " + u_props.excuse().getMessage());
            props = TS_FilePropertiesUtils.empty();
            TS_FilePropertiesUtils.write(props, pathCurDir);
        } else {
            MainLog.add("props.read: OK");
            props = u_props.value();
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_SRC_0);
                if (u.isPresent()) {
                    mainFrame.tfSrcDir0.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_SRC_1);
                if (u.isPresent()) {
                    mainFrame.tfSrcDir1.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_SRC_2);
                if (u.isPresent()) {
                    mainFrame.tfSrcDir2.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_SRC_3);
                if (u.isPresent()) {
                    mainFrame.tfSrcDir3.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_SRC_4);
                if (u.isPresent()) {
                    mainFrame.tfSrcDir4.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_SRC_5);
                if (u.isPresent()) {
                    mainFrame.tfSrcDir5.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_DST_0);
                if (u.isPresent()) {
                    mainFrame.tfDstDir0.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_DST_1);
                if (u.isPresent()) {
                    mainFrame.tfDstDir1.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_DST_2);
                if (u.isPresent()) {
                    mainFrame.tfDstDir2.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_DST_3);
                if (u.isPresent()) {
                    mainFrame.tfDstDir3.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_DST_4);
                if (u.isPresent()) {
                    mainFrame.tfDstDir4.setText(u.orElseThrow());
                }
            }
            {
                var u = TS_FilePropertiesUtils.getValue(props, MainRecordFolder.PARAM_DST_5);
                if (u.isPresent()) {
                    mainFrame.tfDstDir5.setText(u.orElseThrow());
                }
            }
        }
    }
}
