package com.google.android.play.core.integrity;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
final class q implements ai {
    private Context a;

    private q() {
    }

    /* synthetic */ q(p pVar) {
    }

    public final q a(Context context) {
        context.getClass();
        this.a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.ai
    public final s b() {
        com.google.android.play.integrity.internal.ak.a(this.a, Context.class);
        return new s(this.a, null);
    }
}
