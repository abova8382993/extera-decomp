package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0004\b\u0007\u0010\bR&\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\t8\u0002X\u0082\u0004¢\u0006\f\n\u0004\b\n\u0010\u000b\u0012\u0004\b\f\u0010\u0004R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, m877d2 = {"Landroidx/datastore/core/DataStoreInMemoryCache;", "T", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Landroidx/datastore/core/State;", "newState", "tryUpdate", "(Landroidx/datastore/core/State;)Landroidx/datastore/core/State;", "Lkotlinx/coroutines/flow/MutableStateFlow;", "cachedValue", "Lkotlinx/coroutines/flow/MutableStateFlow;", "getCachedValue$annotations", "getCurrentState", "()Landroidx/datastore/core/State;", "currentState", "Lkotlinx/coroutines/flow/Flow;", "getFlow", "()Lkotlinx/coroutines/flow/Flow;", "flow", "datastore-core_release"}, m878k = 1, m879mv = {1, 8, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nDataStoreInMemoryCache.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataStoreInMemoryCache.kt\nandroidx/datastore/core/DataStoreInMemoryCache\n+ 2 StateFlow.kt\nkotlinx/coroutines/flow/StateFlowKt\n*L\n1#1,79:1\n198#2,5:80\n*S KotlinDebug\n*F\n+ 1 DataStoreInMemoryCache.kt\nandroidx/datastore/core/DataStoreInMemoryCache\n*L\n45#1:80,5\n*E\n"})
public final class DataStoreInMemoryCache<T> {
    private final MutableStateFlow<State<T>> cachedValue = StateFlowKt.MutableStateFlow(UnInitialized.INSTANCE);

    public final State<T> getCurrentState() {
        return this.cachedValue.getValue();
    }

    public final Flow<State<T>> getFlow() {
        return this.cachedValue;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.datastore.core.State<T> tryUpdate(androidx.datastore.core.State<T> r5) {
        /*
            r4 = this;
            kotlinx.coroutines.flow.MutableStateFlow<androidx.datastore.core.State<T>> r4 = r4.cachedValue
        L2:
            java.lang.Object r0 = r4.getValue()
            r1 = r0
            androidx.datastore.core.State r1 = (androidx.datastore.core.State) r1
            boolean r2 = r1 instanceof androidx.datastore.core.ReadException
            if (r2 == 0) goto Lf
            r2 = 1
            goto L15
        Lf:
            androidx.datastore.core.UnInitialized r2 = androidx.datastore.core.UnInitialized.INSTANCE
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
        L15:
            if (r2 == 0) goto L18
            goto L26
        L18:
            boolean r2 = r1 instanceof androidx.datastore.core.Data
            if (r2 == 0) goto L28
            int r2 = r5.getVersion()
            int r3 = r1.getVersion()
            if (r2 <= r3) goto L2c
        L26:
            r1 = r5
            goto L2c
        L28:
            boolean r2 = r1 instanceof androidx.datastore.core.Final
            if (r2 == 0) goto L33
        L2c:
            boolean r0 = r4.compareAndSet(r0, r1)
            if (r0 == 0) goto L2
            return r1
        L33:
            kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m()
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreInMemoryCache.tryUpdate(androidx.datastore.core.State):androidx.datastore.core.State");
    }
}
