package com.exteragram.messenger.api.p013db;

import com.exteragram.messenger.api.dto.AddedRegDateDTO;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH§@¢\u0006\u0002\u0010\u000b¨\u0006\fÀ\u0006\u0003"}, m877d2 = {"Lcom/exteragram/messenger/api/db/AddedRegDateDao;", _UrlKt.FRAGMENT_ENCODE_SET, "insert", _UrlKt.FRAGMENT_ENCODE_SET, "addedRegDate", "Lcom/exteragram/messenger/api/dto/AddedRegDateDTO;", "(Lcom/exteragram/messenger/api/dto/AddedRegDateDTO;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isAdded", _UrlKt.FRAGMENT_ENCODE_SET, "userId", _UrlKt.FRAGMENT_ENCODE_SET, "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public interface AddedRegDateDao {
    Object insert(AddedRegDateDTO addedRegDateDTO, Continuation<? super Unit> continuation);

    Object isAdded(long j, Continuation<? super Boolean> continuation);
}
