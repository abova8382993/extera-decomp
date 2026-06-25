package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.PublishedApi;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"kotlinx/coroutines/channels/ChannelsKt__ChannelsKt", "kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt"}, m877d2 = {}, m878k = 4, m879mv = {2, 1, 0}, m881xi = 48)
public abstract class ChannelsKt {
    @PublishedApi
    public static final void cancelConsumed(ReceiveChannel<?> receiveChannel, Throwable th) {
        ChannelsKt__Channels_commonKt.cancelConsumed(receiveChannel, th);
    }

    public static final <E> Object trySendBlocking(SendChannel<? super E> sendChannel, E e) {
        return ChannelsKt__ChannelsKt.trySendBlocking(sendChannel, e);
    }
}
