package androidx.core.os;

import android.p001os.OutcomeReceiver;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007¨\u0006\u0006"}, m877d2 = {"asOutcomeReceiver", "Landroid/os/OutcomeReceiver;", "R", "E", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlin/coroutines/Continuation;", "core-ktx"}, m878k = 2, m879mv = {2, 1, 0}, m881xi = 48)
public abstract class OutcomeReceiverKt {
    public static final <R, E extends Throwable> OutcomeReceiver asOutcomeReceiver(Continuation<? super R> continuation) {
        return new ContinuationOutcomeReceiver(continuation);
    }
}
