package p026j$.util;

import java.util.Date;
import p026j$.time.Instant;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class DesugarDate {
    public static Date from(Instant instant) {
        try {
            return new Date(instant.toEpochMilli());
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
