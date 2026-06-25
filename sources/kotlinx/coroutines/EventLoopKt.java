package kotlinx.coroutines;

import kotlin.Metadata;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000f\u0010\u0001\u001a\u00020\u0000H\u0000¢\u0006\u0004\b\u0001\u0010\u0002¨\u0006\u0003"}, m877d2 = {"Lkotlinx/coroutines/EventLoop;", "createEventLoop", "()Lkotlinx/coroutines/EventLoop;", "kotlinx-coroutines-core"}, m878k = 2, m879mv = {2, 1, 0}, m881xi = 48)
public abstract class EventLoopKt {
    public static final EventLoop createEventLoop() {
        return new BlockingEventLoop(Thread.currentThread());
    }
}
