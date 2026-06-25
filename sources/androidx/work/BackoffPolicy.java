package androidx.work;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, m877d2 = {"Landroidx/work/BackoffPolicy;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;I)V", "EXPONENTIAL", "LINEAR", "work-runtime_release"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class BackoffPolicy extends Enum<BackoffPolicy> {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ BackoffPolicy[] $VALUES;
    public static final BackoffPolicy EXPONENTIAL = new BackoffPolicy("EXPONENTIAL", 0);
    public static final BackoffPolicy LINEAR = new BackoffPolicy("LINEAR", 1);

    private static final /* synthetic */ BackoffPolicy[] $values() {
        return new BackoffPolicy[]{EXPONENTIAL, LINEAR};
    }

    public static BackoffPolicy valueOf(String str) {
        return (BackoffPolicy) Enum.valueOf(BackoffPolicy.class, str);
    }

    public static BackoffPolicy[] values() {
        return (BackoffPolicy[]) $VALUES.clone();
    }

    private BackoffPolicy(String str, int i) {
        super(str, i);
    }

    static {
        BackoffPolicy[] backoffPolicyArr$values = $values();
        $VALUES = backoffPolicyArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(backoffPolicyArr$values);
    }
}
