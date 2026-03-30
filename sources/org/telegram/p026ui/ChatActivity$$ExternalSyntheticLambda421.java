package org.telegram.p026ui;

import androidx.core.util.Consumer;
import java.util.List;
import org.telegram.p026ui.Components.ReactedUsersListView;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ChatActivity$$ExternalSyntheticLambda421 implements Consumer {
    public final /* synthetic */ ReactedUsersListView f$0;

    public /* synthetic */ ChatActivity$$ExternalSyntheticLambda421(ReactedUsersListView reactedUsersListView) {
        this.f$0 = reactedUsersListView;
    }

    @Override // androidx.core.util.Consumer
    public final void accept(Object obj) {
        this.f$0.setSeenUsers((List) obj);
    }
}
