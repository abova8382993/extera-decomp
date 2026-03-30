package j$.util;

import j$.time.Instant;
import java.util.Date;
import org.telegram.messenger.MediaDataController;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class DateRetargetClass {
    public static Instant toInstant(Date date) {
        long time = date.getTime();
        Instant instant = Instant.c;
        long j = MediaDataController.MAX_STYLE_RUNS_COUNT;
        return Instant.v(j$.com.android.tools.r8.a.T(time, j), ((int) j$.com.android.tools.r8.a.S(time, j)) * 1000000);
    }
}
