package com.tugalsan.dsk.file.sync;

public class MainRun {

    private static void thing(String param_src, String param_dst) {
        MainAsync.run(() -> {
            MainCgi.run_params(param_src, param_dst, true);
        });
    }

    public static void run_0() {
        thing(MainRecordFolder.PARAM_SRC_0, MainRecordFolder.PARAM_DST_0);
    }

    public static void run_1() {
        thing(MainRecordFolder.PARAM_SRC_1, MainRecordFolder.PARAM_DST_1);
    }

    public static void run_2() {
        thing(MainRecordFolder.PARAM_SRC_2, MainRecordFolder.PARAM_DST_2);
    }

    public static void run_3() {
        thing(MainRecordFolder.PARAM_SRC_3, MainRecordFolder.PARAM_DST_3);
    }

    public static void run_4() {
        thing(MainRecordFolder.PARAM_SRC_4, MainRecordFolder.PARAM_DST_4);
    }

    public static void run_5() {
        thing(MainRecordFolder.PARAM_SRC_5, MainRecordFolder.PARAM_DST_5);
    }

    public static void run_all() {
        run_0();
        run_1();
        run_2();
        run_3();
        run_4();
        run_5();
    }

}
