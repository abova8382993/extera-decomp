package androidx.camera.camera2.pipe.compat;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.sync.Mutex;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001:\u0001\rB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00020\nH\u0086@¢\u0006\u0002\u0010\u000bJ\u0006\u0010\f\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/SessionSequencer;", _UrlKt.FRAGMENT_ENCODE_SET, "concurrentSequencer", "Landroidx/camera/camera2/pipe/compat/ConcurrentSessionSequencer;", "<init>", "(Landroidx/camera/camera2/pipe/compat/ConcurrentSessionSequencer;)V", "state", "Lkotlinx/atomicfu/AtomicRef;", "Landroidx/camera/camera2/pipe/compat/SessionSequencer$State;", "awaitSessionLock", _UrlKt.FRAGMENT_ENCODE_SET, "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "release", "State", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class SessionSequencer {
    private final ConcurrentSessionSequencer concurrentSequencer;
    private final AtomicRef<State> state = AtomicFU.atomic(State.PENDING);

    /* JADX INFO: renamed from: androidx.camera.camera2.pipe.compat.SessionSequencer$awaitSessionLock$1 */
    @Metadata(m878k = 3, m879mv = {2, 1, 0}, m881xi = 48)
    @DebugMetadata(m895c = "androidx.camera.camera2.pipe.compat.SessionSequencer", m896f = "ConcurrentSessionSequencers.kt", m897i = {}, m898l = {98}, m899m = "awaitSessionLock", m900n = {}, m902s = {}, m903v = 1)
    public static final class C02181 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C02181(Continuation<? super C02181> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SessionSequencer.this.awaitSessionLock(this);
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m877d2 = {"Landroidx/camera/camera2/pipe/compat/SessionSequencer$State;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;I)V", "PENDING", "CREATING", "CREATED", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class State extends Enum<State> {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ State[] $VALUES;
        public static final State PENDING = new State("PENDING", 0);
        public static final State CREATING = new State("CREATING", 1);
        public static final State CREATED = new State("CREATED", 2);

        private static final /* synthetic */ State[] $values() {
            return new State[]{PENDING, CREATING, CREATED};
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }

        private State(String str, int i) {
            super(str, i);
        }

        static {
            State[] stateArr$values = $values();
            $VALUES = stateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(stateArr$values);
        }
    }

    public SessionSequencer(ConcurrentSessionSequencer concurrentSessionSequencer) {
        this.concurrentSequencer = concurrentSessionSequencer;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object awaitSessionLock(kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof androidx.camera.camera2.pipe.compat.SessionSequencer.C02181
            if (r0 == 0) goto L13
            r0 = r6
            androidx.camera.camera2.pipe.compat.SessionSequencer$awaitSessionLock$1 r0 = (androidx.camera.camera2.pipe.compat.SessionSequencer.C02181) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.camera.camera2.pipe.compat.SessionSequencer$awaitSessionLock$1 r0 = new androidx.camera.camera2.pipe.compat.SessionSequencer$awaitSessionLock$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L30
            if (r2 != r4) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)
            goto L42
        L2a:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            return r3
        L30:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.camera.camera2.pipe.compat.ConcurrentSessionSequencer r6 = r5.concurrentSequencer
            kotlinx.coroutines.sync.Mutex r6 = r6.getSharedMutex()
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.sync.Mutex.DefaultImpls.lock$default(r6, r3, r0, r4, r3)
            if (r6 != r1) goto L42
            return r1
        L42:
            kotlinx.atomicfu.AtomicRef<androidx.camera.camera2.pipe.compat.SessionSequencer$State> r6 = r5.state
            androidx.camera.camera2.pipe.compat.SessionSequencer$State r0 = androidx.camera.camera2.pipe.compat.SessionSequencer.State.PENDING
            androidx.camera.camera2.pipe.compat.SessionSequencer$State r1 = androidx.camera.camera2.pipe.compat.SessionSequencer.State.CREATING
            boolean r6 = r6.compareAndSet(r0, r1)
            if (r6 != 0) goto L57
            androidx.camera.camera2.pipe.compat.ConcurrentSessionSequencer r5 = r5.concurrentSequencer
            kotlinx.coroutines.sync.Mutex r5 = r5.getSharedMutex()
            kotlinx.coroutines.sync.Mutex.DefaultImpls.unlock$default(r5, r3, r4, r3)
        L57:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.SessionSequencer.awaitSessionLock(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void release() {
        if (this.state.getAndSet(State.CREATED) == State.CREATING) {
            Mutex.DefaultImpls.unlock$default(this.concurrentSequencer.getSharedMutex(), null, 1, null);
        }
    }
}
