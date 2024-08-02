package com.tugalsan.dsk.file.sync;

import com.tugalsan.api.thread.server.async.TS_ThreadAsync;
import com.tugalsan.api.thread.server.async.TS_ThreadAsyncAwait;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import com.tugalsan.api.unsafe.client.TGS_UnSafeRunnable;
import java.time.Duration;

public class MainAsync {

    final public static Duration RUN_SECONDS = Duration.ofSeconds(10);

    public static void run(TGS_UnSafeRunnable exe) {
        run(exe, RUN_SECONDS);
    }

    public static void run(TGS_UnSafeRunnable exe, Duration until) {
        TS_ThreadAsync.now(null, kt -> {
//            var u = TS_ThreadAsyncAwait.runUntil(kt, until, kt2 -> {
//                MainLog.add("AWAIT.BEGIN...");
                TGS_UnSafe.run(exe, e -> MainLog.add("ERROR: " + e.getMessage()));
//            });
//            if (u.timeout()) {
//                MainLog.add("AWAIT.ERROR: TIMEOUT");
//            } else if (u.hasError()) {
//                MainLog.add("AWAIT.ERROR: " + u.exceptionIfFailed.orElseThrow().getMessage());
//            } else {
//                MainLog.add("AWAIT.END");
//            }
        });
    }
}
