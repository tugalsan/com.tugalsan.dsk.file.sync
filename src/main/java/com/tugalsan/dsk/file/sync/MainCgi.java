package com.tugalsan.dsk.file.sync;

import com.tugalsan.api.file.properties.server.TS_FilePropertiesUtils;
import com.tugalsan.api.file.server.TS_DirectoryUtils;
import com.tugalsan.api.file.server.TS_PathUtils;
import com.tugalsan.api.function.client.TGS_Func_OutBool_In1;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.thread.server.async.TS_ThreadAsyncScheduled;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.time.client.TGS_Time;
import static com.tugalsan.dsk.file.sync.Main.pathCurDir;
import static com.tugalsan.dsk.file.sync.Main.pathProperties;
import java.nio.file.Path;
import java.time.Duration;

public class MainCgi {

    final private static TS_Log d = TS_Log.of(MainCgi.class);

    public static void run_all(TS_ThreadSyncTrigger threadKiller) {
        var scheduledHour = 23;
        TS_ThreadAsyncScheduled.everyDays_whenHourShow(threadKiller, Duration.ofHours(8), true, 1, scheduledHour, kt -> {
//        TS_ThreadAsyncScheduled.everyDays(threadKiller, Duration.ofHours(8), true, 1, kt -> {
            d.cr("run_all", "------------------------------------------------------");
            d.cr("run_all", "STARTING THE ROUND", TGS_Time.of());
            d.cr("run_all", "scheduledHour: " + scheduledHour);
            d.cr("run_all", "curDir: " + pathCurDir);
            d.cr("run_all", "pathProperties: " + pathProperties);
            var u_props = TS_FilePropertiesUtils.createPropertyReader(Main.pathProperties);
            if (u_props.isExcuse()) {
                d.ce("run_all", "ERROR: " + u_props.excuse().getMessage());
                Main.props = TS_FilePropertiesUtils.empty();
                TS_FilePropertiesUtils.write(Main.props, Main.pathCurDir);
            } else {
                d.ce("run_all", "props.read: OK");
                Main.props = u_props.value();
            }
            run(MainRecordFolder.PARAM_SRC_0, MainRecordFolder.PARAM_DST_0, false);
            run(MainRecordFolder.PARAM_SRC_1, MainRecordFolder.PARAM_DST_1, false);
            run(MainRecordFolder.PARAM_SRC_2, MainRecordFolder.PARAM_DST_2, false);
            run(MainRecordFolder.PARAM_SRC_3, MainRecordFolder.PARAM_DST_3, false);
            run(MainRecordFolder.PARAM_SRC_4, MainRecordFolder.PARAM_DST_4, false);
            run(MainRecordFolder.PARAM_SRC_5, MainRecordFolder.PARAM_DST_5, false);
            d.cr("run_all", "------------------------------------------------------");
            d.cr("run_all", "WAITING FOR THE NEXT ROUND...", TGS_Time.of());
            d.cr("run_all", "scheduledHour: " + scheduledHour);
        });
        while (true) {
            TS_ThreadWait.days("wait", threadKiller, 1);
            d.cr("run_all", "alive");
        }
    }

    public static void run(String param_src, String param_dst, boolean guiMode) {
        d.cr("run", "staring...", param_src, param_dst);
        if (guiMode) {
            MainLog.add("STARING " + param_src + " -- > -- " + param_dst);
        }
        var o_src = TS_FilePropertiesUtils.getValue(Main.props, param_src);
        if (o_src.isEmpty()) {
            d.ce("run", "SKIP", param_src, "not exists");
            if (guiMode) {
                MainLog.add("SKIP " + param_src + " not exists");
            }
            return;
        }
        var u_src = TS_PathUtils.toPath(o_src.orElseThrow());
        if (u_src.isExcuse()) {
            d.ce("run", "SKIP", param_src, "not path-able", o_src.orElseThrow(), u_src.excuse().getMessage());
            if (guiMode) {
                MainLog.add("SKIP " + param_src + " not path-able [" + o_src.orElseThrow() + "] " + u_src.excuse().getMessage());
            }
            return;
        }
        var p_src = u_src.value();
        if (!TS_DirectoryUtils.isExistDirectory(p_src)) {
            d.ce("run", "SKIP", param_src, "not exists", p_src.toAbsolutePath().toString());
            if (guiMode) {
                MainLog.add("SKIP " + param_src + " not exists [" + p_src.toAbsolutePath().toString() + "]");
            }
            return;
        }
        var o_dst = TS_FilePropertiesUtils.getValue(Main.props, param_dst);
        if (o_dst.isEmpty()) {
            d.ce("run", "SKIP", param_dst, " not exists");
            if (guiMode) {
                MainLog.add("SKIP " + param_dst + " not exists");
            }
            return;
        }
        var u_dst = TS_PathUtils.toPath(o_dst.orElseThrow());
        if (u_dst.isExcuse()) {
            d.ce("run", "SKIP", param_dst, "not path-able", o_dst.orElseThrow(), u_dst.excuse().getMessage());
            if (guiMode) {
                MainLog.add("SKIP " + param_dst + " not path-able [" + o_dst.orElseThrow() + "] " + u_dst.excuse().getMessage());
            }
            return;
        }
        var p_dst = u_dst.value();
        if (!TS_DirectoryUtils.isExistDirectory(p_dst)) {
            d.ce("run", "SKIP", param_dst, "not exists", p_dst.toAbsolutePath().toString());
            if (guiMode) {
                MainLog.add("SKIP " + param_dst + " not exists [" + p_dst.toAbsolutePath().toString() + "]");
            }
            return;
        }
        var overwrite = true;
        var parallel = true;
        var skipIfSameSizeAndDateAndTime = true;
        TGS_Func_OutBool_In1<Path> filter = path -> true;
        TS_DirectoryUtils.copyDirectory(p_src, p_dst, overwrite, parallel, filter, skipIfSameSizeAndDateAndTime);
        d.cr("run", "ended", param_src, param_dst);
        if (guiMode) {
            MainLog.add("ENDED " + param_src + " -- > -- " + param_dst);
        }
    }

}
