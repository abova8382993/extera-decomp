package com.google.firebase;

import com.chaquo.python.internal.Common;
import kotlin.Metadata;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/google/firebase/Firebase;", "Lcom/google/firebase/FirebaseApp;", "getApp", "(Lcom/google/firebase/Firebase;)Lcom/google/firebase/FirebaseApp;", Common.ASSET_APP, "com.google.firebase-firebase-common"}, m878k = 2, m879mv = {2, 0, 0}, m881xi = 48)
public abstract class FirebaseKt {
    public static final FirebaseApp getApp(Firebase firebase) {
        return FirebaseApp.getInstance();
    }
}
