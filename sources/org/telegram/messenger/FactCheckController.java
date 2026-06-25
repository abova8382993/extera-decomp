package org.telegram.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.LongSparseArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.AlertDialogDecor;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AnimatedColor;
import org.telegram.p035ui.Components.AnimatedTextView;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.EditTextCaption;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.SizeNotifierFrameLayout;
import org.telegram.p035ui.Components.TypefaceSpan;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.Vector;

/* JADX INFO: loaded from: classes.dex */
public class FactCheckController {
    private static AlertDialog currentDialog;
    private boolean clearedExpiredInDatabase;
    public final int currentAccount;
    private static volatile FactCheckController[] Instance = new FactCheckController[16];
    private static final Object[] lockObjects = new Object[16];
    private final LongSparseArray<TLRPC.TL_factCheck> localCache = new LongSparseArray<>();
    private final LongSparseArray<HashMap<Key, Utilities.Callback<TLRPC.TL_factCheck>>> toload = new LongSparseArray<>();
    private final ArrayList<Key> loading = new ArrayList<>();
    private final Runnable loadMissingRunnable = new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda17
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.loadMissing();
        }
    };

    static {
        for (int i = 0; i < 16; i++) {
            lockObjects[i] = new Object();
        }
    }

    public static FactCheckController getInstance(int i) {
        FactCheckController factCheckController;
        FactCheckController factCheckController2 = Instance[i];
        if (factCheckController2 != null) {
            return factCheckController2;
        }
        synchronized (lockObjects[i]) {
            try {
                factCheckController = Instance[i];
                if (factCheckController == null) {
                    FactCheckController[] factCheckControllerArr = Instance;
                    FactCheckController factCheckController3 = new FactCheckController(i);
                    factCheckControllerArr[i] = factCheckController3;
                    factCheckController = factCheckController3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return factCheckController;
    }

    private FactCheckController(int i) {
        this.currentAccount = i;
    }

    public TLRPC.TL_factCheck getFactCheck(final MessageObject messageObject) {
        TLRPC.Message message;
        TLRPC.TL_factCheck tL_factCheck;
        if (messageObject == null || (message = messageObject.messageOwner) == null || (tL_factCheck = message.factcheck) == null) {
            return null;
        }
        if (!tL_factCheck.need_check) {
            if (this.localCache.get(tL_factCheck.hash) == null) {
                LongSparseArray<TLRPC.TL_factCheck> longSparseArray = this.localCache;
                TLRPC.TL_factCheck tL_factCheck2 = messageObject.messageOwner.factcheck;
                longSparseArray.put(tL_factCheck2.hash, tL_factCheck2);
                saveToDatabase(messageObject.messageOwner.factcheck);
            }
            return messageObject.messageOwner.factcheck;
        }
        final Key keyM1044of = Key.m1044of(messageObject);
        if (keyM1044of == null || keyM1044of.messageId < 0) {
            return null;
        }
        TLRPC.TL_factCheck tL_factCheck3 = this.localCache.get(keyM1044of.hash);
        if (tL_factCheck3 != null) {
            messageObject.messageOwner.factcheck = tL_factCheck3;
            return tL_factCheck3;
        }
        if (this.loading.contains(keyM1044of)) {
            return messageObject.messageOwner.factcheck;
        }
        HashMap<Key, Utilities.Callback<TLRPC.TL_factCheck>> map = this.toload.get(keyM1044of.dialogId);
        if (map == null) {
            LongSparseArray<HashMap<Key, Utilities.Callback<TLRPC.TL_factCheck>>> longSparseArray2 = this.toload;
            long j = keyM1044of.dialogId;
            HashMap<Key, Utilities.Callback<TLRPC.TL_factCheck>> map2 = new HashMap<>();
            longSparseArray2.put(j, map2);
            map = map2;
        }
        if (!map.containsKey(keyM1044of)) {
            map.put(keyM1044of, new Utilities.Callback() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda15
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$getFactCheck$0(keyM1044of, messageObject, (TLRPC.TL_factCheck) obj);
                }
            });
            scheduleLoadMissing();
        }
        return messageObject.messageOwner.factcheck;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFactCheck$0(Key key, MessageObject messageObject, TLRPC.TL_factCheck tL_factCheck) {
        this.localCache.put(key.hash, tL_factCheck);
        messageObject.messageOwner.factcheck = tL_factCheck;
    }

    private void scheduleLoadMissing() {
        AndroidUtilities.cancelRunOnUIThread(this.loadMissingRunnable);
        AndroidUtilities.runOnUIThread(this.loadMissingRunnable, 80L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMissing() {
        while (true) {
            int size = this.toload.size();
            LongSparseArray<HashMap<Key, Utilities.Callback<TLRPC.TL_factCheck>>> longSparseArray = this.toload;
            if (size > 0) {
                final long jKeyAt = longSparseArray.keyAt(0);
                final HashMap<Key, Utilities.Callback<TLRPC.TL_factCheck>> mapValueAt = this.toload.valueAt(0);
                this.toload.removeAt(0);
                final ArrayList<Key> arrayList = new ArrayList<>(mapValueAt.keySet());
                this.loading.addAll(arrayList);
                getFromDatabase(arrayList, new Utilities.Callback() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda11
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$loadMissing$3(jKeyAt, arrayList, mapValueAt, (ArrayList) obj);
                    }
                });
            } else {
                longSparseArray.clear();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMissing$3(long j, ArrayList arrayList, final HashMap map, ArrayList arrayList2) {
        final TLRPC.TL_getFactCheck tL_getFactCheck = new TLRPC.TL_getFactCheck();
        tL_getFactCheck.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(j);
        final ArrayList arrayList3 = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            Key key = (Key) arrayList.get(i2);
            TLRPC.TL_factCheck tL_factCheck = (TLRPC.TL_factCheck) arrayList2.get(i2);
            if (tL_factCheck == null) {
                arrayList3.add(key);
                tL_getFactCheck.msg_id.add(Integer.valueOf(key.messageId));
            } else {
                this.loading.remove(key);
                Utilities.Callback callback = (Utilities.Callback) map.get(key);
                if (callback != null) {
                    callback.run(tL_factCheck);
                    i++;
                }
            }
        }
        if (i > 0) {
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.factCheckLoaded, new Object[0]);
        }
        if (tL_getFactCheck.msg_id.isEmpty()) {
            return;
        }
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_getFactCheck, new RequestDelegate() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda16
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$loadMissing$2(tL_getFactCheck, arrayList3, map, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMissing$2(final TLRPC.TL_getFactCheck tL_getFactCheck, final ArrayList arrayList, final HashMap map, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadMissing$1(tLObject, tL_getFactCheck, arrayList, map);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMissing$1(TLObject tLObject, TLRPC.TL_getFactCheck tL_getFactCheck, ArrayList arrayList, HashMap map) {
        ArrayList arrayList2 = new ArrayList();
        if (tLObject instanceof Vector) {
            ArrayList<T> arrayList3 = ((Vector) tLObject).objects;
            for (int i = 0; i < arrayList3.size(); i++) {
                if (arrayList3.get(i) instanceof TLRPC.TL_factCheck) {
                    arrayList2.add((TLRPC.TL_factCheck) arrayList3.get(i));
                }
            }
        }
        HashMap map2 = new HashMap();
        for (int i2 = 0; i2 < Math.min(tL_getFactCheck.msg_id.size(), arrayList2.size()); i2++) {
            Integer num = tL_getFactCheck.msg_id.get(i2);
            num.intValue();
            map2.put(num, (TLRPC.TL_factCheck) arrayList2.get(i2));
        }
        int i3 = 0;
        for (int i4 = 0; i4 < tL_getFactCheck.msg_id.size(); i4++) {
            Key key = (Key) arrayList.get(i4);
            Integer num2 = tL_getFactCheck.msg_id.get(i4);
            num2.intValue();
            TLRPC.TL_factCheck tL_factCheck = (TLRPC.TL_factCheck) map2.get(num2);
            Utilities.Callback callback = (Utilities.Callback) map.get(key);
            if (tL_factCheck != null && !tL_factCheck.need_check && callback != null) {
                callback.run(tL_factCheck);
                i3++;
                this.loading.remove(key);
            }
        }
        if (i3 > 0) {
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.factCheckLoaded, new Object[0]);
        }
    }

    /* JADX INFO: loaded from: classes5.dex */
    public static class Key {
        public final long dialogId;
        public final long hash;
        public final int messageId;

        private Key(long j, int i, long j2) {
            this.dialogId = j;
            this.messageId = i;
            this.hash = j2;
        }

        public int hashCode() {
            return Long.hashCode(this.hash);
        }

        /* JADX INFO: renamed from: of */
        public static Key m1044of(MessageObject messageObject) {
            TLRPC.Message message;
            if (messageObject == null || (message = messageObject.messageOwner) == null || message.factcheck == null) {
                return null;
            }
            return new Key(messageObject.getDialogId(), messageObject.getId(), messageObject.messageOwner.factcheck.hash);
        }
    }

    private void getFromDatabase(final ArrayList<Key> arrayList, final Utilities.Callback<ArrayList<TLRPC.TL_factCheck>> callback) {
        if (callback == null) {
            return;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            callback.run(new ArrayList<>());
        } else {
            final MessagesStorage messagesStorage = MessagesStorage.getInstance(this.currentAccount);
            messagesStorage.getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    FactCheckController.$r8$lambda$eRU3nxQjWPT8Oj30fqwMG3VVK3A(messagesStorage, arrayList, callback);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0074, code lost:
    
        r1 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0079, code lost:
    
        if (r4 >= r11.size()) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0087, code lost:
    
        if (r3.hash != ((org.telegram.messenger.FactCheckController.Key) r11.get(r4)).hash) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0089, code lost:
    
        r1 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008a, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0090, code lost:
    
        if (r1 < 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0096, code lost:
    
        if (r1 >= r0.size()) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0098, code lost:
    
        r0.set(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009b, code lost:
    
        r2.reuse();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void $r8$lambda$eRU3nxQjWPT8Oj30fqwMG3VVK3A(org.telegram.messenger.MessagesStorage r10, java.util.ArrayList r11, final org.telegram.messenger.Utilities.Callback r12) throws java.lang.Throwable {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r10 = r10.getDatabase()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r2.<init>()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            int r3 = r11.size()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r4 = 0
            r5 = r4
        L15:
            if (r5 >= r3) goto L32
            java.lang.Object r6 = r11.get(r5)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            int r5 = r5 + 1
            org.telegram.messenger.FactCheckController$Key r6 = (org.telegram.messenger.FactCheckController.Key) r6     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            long r6 = r6.hash     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r2.add(r6)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r0.add(r1)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            goto L15
        L2c:
            r10 = move-exception
            goto Lbb
        L2f:
            r10 = move-exception
            goto Laa
        L32:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r3.<init>()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.lang.String r5 = "SELECT data FROM fact_checks WHERE hash IN ("
            r3.append(r5)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.lang.String r5 = ", "
            java.lang.String r2 = android.text.TextUtils.join(r5, r2)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r3.append(r2)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.lang.String r2 = ")"
            r3.append(r2)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            org.telegram.SQLite.SQLiteCursor r10 = r10.queryFinalized(r2, r3)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
        L54:
            boolean r2 = r10.next()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            if (r2 == 0) goto La6
            org.telegram.tgnet.NativeByteBuffer r2 = r10.byteBufferValue(r4)     // Catch: java.lang.Throwable -> L9f
            int r3 = r2.readInt32(r4)     // Catch: java.lang.Throwable -> L8d
            org.telegram.tgnet.TLRPC$TL_factCheck r3 = org.telegram.tgnet.TLRPC.TL_factCheck.TLdeserialize(r2, r3, r4)     // Catch: java.lang.Throwable -> L8d
            if (r3 != 0) goto L74
            r2.reuse()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            goto L54
        L6c:
            r11 = move-exception
            r1 = r10
            r10 = r11
            goto Lbb
        L70:
            r11 = move-exception
            r1 = r10
            r10 = r11
            goto Laa
        L74:
            r1 = -1
        L75:
            int r5 = r11.size()     // Catch: java.lang.Throwable -> L8d
            if (r4 >= r5) goto L90
            java.lang.Object r5 = r11.get(r4)     // Catch: java.lang.Throwable -> L8d
            org.telegram.messenger.FactCheckController$Key r5 = (org.telegram.messenger.FactCheckController.Key) r5     // Catch: java.lang.Throwable -> L8d
            long r6 = r3.hash     // Catch: java.lang.Throwable -> L8d
            long r8 = r5.hash     // Catch: java.lang.Throwable -> L8d
            int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r5 != 0) goto L8a
            r1 = r4
        L8a:
            int r4 = r4 + 1
            goto L75
        L8d:
            r11 = move-exception
            r1 = r2
            goto La0
        L90:
            if (r1 < 0) goto L9b
            int r11 = r0.size()     // Catch: java.lang.Throwable -> L8d
            if (r1 >= r11) goto L9b
            r0.set(r1, r3)     // Catch: java.lang.Throwable -> L8d
        L9b:
            r2.reuse()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            goto La6
        L9f:
            r11 = move-exception
        La0:
            if (r1 == 0) goto La5
            r1.reuse()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
        La5:
            throw r11     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
        La6:
            r10.dispose()     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L70
            goto Lb2
        Laa:
            org.telegram.messenger.FileLog.m1048e(r10)     // Catch: java.lang.Throwable -> L2c
            if (r1 == 0) goto Lb2
            r1.dispose()
        Lb2:
            org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda9 r10 = new org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda9
            r10.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r10)
            return
        Lbb:
            if (r1 == 0) goto Lc0
            r1.dispose()
        Lc0:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FactCheckController.$r8$lambda$eRU3nxQjWPT8Oj30fqwMG3VVK3A(org.telegram.messenger.MessagesStorage, java.util.ArrayList, org.telegram.messenger.Utilities$Callback):void");
    }

    private void saveToDatabase(final TLRPC.TL_factCheck tL_factCheck) {
        if (tL_factCheck == null) {
            return;
        }
        final MessagesStorage messagesStorage = MessagesStorage.getInstance(this.currentAccount);
        messagesStorage.getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                FactCheckController.m5379$r8$lambda$hwZvWtn8X40ulbUsYqEavkirk8(messagesStorage, tL_factCheck);
            }
        });
        clearExpiredInDatabase();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0061  */
    /* JADX INFO: renamed from: $r8$lambda$hwZvWtn8X40ulbUsYqEavkir-k8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void m5379$r8$lambda$hwZvWtn8X40ulbUsYqEavkirk8(org.telegram.messenger.MessagesStorage r6, org.telegram.tgnet.TLRPC.TL_factCheck r7) throws java.lang.Throwable {
        /*
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r6 = r6.getDatabase()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.lang.String r1 = "REPLACE INTO fact_checks VALUES(?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r6 = r6.executeFast(r1)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r6.requery()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L43
            long r1 = r7.hash     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L43
            r3 = 1
            r6.bindLong(r3, r1)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L43
            org.telegram.tgnet.NativeByteBuffer r1 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L43
            int r2 = r7.getObjectSize()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L43
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L43
            r7.serializeToStream(r1)     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            r7 = 2
            r6.bindByteBuffer(r7, r1)     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            r4 = 889032704(0x34fd9000, double:4.39240517E-315)
            long r2 = r2 + r4
            r7 = 3
            r6.bindLong(r7, r2)     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            r6.step()     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            r6.dispose()     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            r1.reuse()
            return
        L3a:
            r7 = move-exception
        L3b:
            r0 = r6
            goto L5a
        L3d:
            r7 = move-exception
        L3e:
            r0 = r6
            goto L4b
        L40:
            r7 = move-exception
            r1 = r0
            goto L3b
        L43:
            r7 = move-exception
            r1 = r0
            goto L3e
        L46:
            r7 = move-exception
            r1 = r0
            goto L5a
        L49:
            r7 = move-exception
            r1 = r0
        L4b:
            org.telegram.messenger.FileLog.m1048e(r7)     // Catch: java.lang.Throwable -> L59
            if (r0 == 0) goto L53
            r0.dispose()
        L53:
            if (r1 == 0) goto L58
            r1.reuse()
        L58:
            return
        L59:
            r7 = move-exception
        L5a:
            if (r0 == 0) goto L5f
            r0.dispose()
        L5f:
            if (r1 == 0) goto L64
            r1.reuse()
        L64:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FactCheckController.m5379$r8$lambda$hwZvWtn8X40ulbUsYqEavkirk8(org.telegram.messenger.MessagesStorage, org.telegram.tgnet.TLRPC$TL_factCheck):void");
    }

    private void clearExpiredInDatabase() {
        if (this.clearedExpiredInDatabase) {
            return;
        }
        this.clearedExpiredInDatabase = true;
        final MessagesStorage messagesStorage = MessagesStorage.getInstance(this.currentAccount);
        messagesStorage.getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                FactCheckController.$r8$lambda$JpH7yHT6pnclw1XNRCmFTCCRSQU(messagesStorage);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$JpH7yHT6pnclw1XNRCmFTCCRSQU(MessagesStorage messagesStorage) {
        try {
            messagesStorage.getDatabase().executeFast("DELETE FROM fact_checks WHERE expires > " + System.currentTimeMillis()).stepThis().dispose();
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public void openFactCheckEditor(Context context, final Theme.ResourcesProvider resourcesProvider, final MessageObject messageObject, boolean z) {
        AlertDialog.Builder builder;
        TLRPC.TL_textWithEntities tL_textWithEntities;
        TLRPC.Message message;
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        final View currentFocus = activityFindActivity != null ? activityFindActivity.getCurrentFocus() : null;
        boolean z2 = lastFragment != null && (lastFragment.getFragmentView() instanceof SizeNotifierFrameLayout) && ((SizeNotifierFrameLayout) lastFragment.getFragmentView()).measureKeyboardHeight() > AndroidUtilities.m1036dp(20.0f) && !z;
        final AlertDialog[] alertDialogArr = new AlertDialog[1];
        if (z2) {
            builder = new AlertDialogDecor.Builder(context, resourcesProvider);
        } else {
            builder = new AlertDialog.Builder(context, resourcesProvider);
        }
        AlertDialog.Builder builder2 = builder;
        final TextView[] textViewArr = new TextView[1];
        boolean z3 = messageObject == null || (message = messageObject.messageOwner) == null || message.factcheck == null;
        builder2.setTitle(LocaleController.getString(C2797R.string.FactCheckDialog));
        final int i = MessagesController.getInstance(this.currentAccount).factcheckLengthLimit;
        final EditTextCaption editTextCaption = new EditTextCaption(context, resourcesProvider) { // from class: org.telegram.messenger.FactCheckController.1
            AnimatedTextView.AnimatedTextDrawable limit;
            AnimatedColor limitColor = new AnimatedColor(this);
            private int limitCount;

            {
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
                this.limit = animatedTextDrawable;
                animatedTextDrawable.setAnimationProperties(0.2f, 0L, 160L, CubicBezierInterpolator.EASE_OUT_QUINT);
                this.limit.setTextSize(AndroidUtilities.m1036dp(15.33f));
                this.limit.setCallback(this);
                this.limit.setGravity(5);
            }

            @Override // android.widget.TextView, android.view.View
            public boolean verifyDrawable(Drawable drawable) {
                return drawable == this.limit || super.verifyDrawable(drawable);
            }

            @Override // org.telegram.p035ui.Components.EditTextEffects, android.widget.TextView
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                super.onTextChanged(charSequence, i2, i3, i4);
                if (this.limit != null) {
                    this.limitCount = i - charSequence.length();
                    this.limit.cancelAnimation();
                    AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.limit;
                    int i5 = this.limitCount;
                    String str = _UrlKt.FRAGMENT_ENCODE_SET;
                    if (i5 <= 4) {
                        str = _UrlKt.FRAGMENT_ENCODE_SET + this.limitCount;
                    }
                    animatedTextDrawable.setText(str);
                }
            }

            @Override // org.telegram.p035ui.Components.EditTextBoldCursor
            public void extendActionMode(ActionMode actionMode, Menu menu) {
                if (menu.findItem(C2797R.id.menu_bold) != null) {
                    return;
                }
                menu.removeItem(android.R.id.shareText);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString(C2797R.string.Bold));
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilder.length(), 33);
                menu.add(C2797R.id.menu_groupbolditalic, C2797R.id.menu_bold, 6, spannableStringBuilder);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(LocaleController.getString(C2797R.string.Italic));
                spannableStringBuilder2.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM_ITALIC)), 0, spannableStringBuilder2.length(), 33);
                menu.add(C2797R.id.menu_groupbolditalic, C2797R.id.menu_italic, 7, spannableStringBuilder2);
                menu.add(C2797R.id.menu_groupbolditalic, C2797R.id.menu_link, 8, LocaleController.getString(C2797R.string.CreateLink));
                menu.add(C2797R.id.menu_groupbolditalic, C2797R.id.menu_regular, 9, LocaleController.getString(C2797R.string.Regular));
            }

            @Override // android.view.View
            public void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                this.limit.setTextColor(this.limitColor.set(Theme.getColor(this.limitCount < 0 ? Theme.key_text_RedRegular : Theme.key_dialogSearchHint, resourcesProvider)));
                this.limit.setBounds(getScrollX(), 0, getScrollX() + getWidth(), getHeight());
                this.limit.draw(canvas);
            }
        };
        editTextCaption.lineYFix = true;
        final boolean z4 = z3;
        editTextCaption.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.messenger.FactCheckController.2
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                if (i2 != 6) {
                    return false;
                }
                if (editTextCaption.getText().toString().length() > i) {
                    AndroidUtilities.shakeView(editTextCaption);
                    return true;
                }
                TLRPC.TL_textWithEntities tL_textWithEntities2 = new TLRPC.TL_textWithEntities();
                CharSequence[] charSequenceArr = {editTextCaption.getText()};
                tL_textWithEntities2.entities = MediaDataController.getInstance(FactCheckController.this.currentAccount).getEntities(charSequenceArr, true);
                CharSequence charSequence = charSequenceArr[0];
                tL_textWithEntities2.text = charSequence == null ? _UrlKt.FRAGMENT_ENCODE_SET : charSequence.toString();
                FactCheckController.this.applyFactCheck(messageObject, tL_textWithEntities2, z4);
                AlertDialog alertDialog = alertDialogArr[0];
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (alertDialogArr[0] == FactCheckController.currentDialog) {
                    FactCheckController.currentDialog = null;
                }
                View view = currentFocus;
                if (view != null) {
                    view.requestFocus();
                }
                return true;
            }
        });
        MediaDataController.getInstance(this.currentAccount).fetchNewEmojiKeywords(AndroidUtilities.getCurrentKeyboardLanguage(), true);
        editTextCaption.setTextSize(1, 18.0f);
        editTextCaption.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        editTextCaption.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText, resourcesProvider));
        editTextCaption.setHintText(LocaleController.getString(C2797R.string.FactCheckPlaceholder));
        editTextCaption.setFocusable(true);
        editTextCaption.setInputType(147457);
        editTextCaption.setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField, resourcesProvider), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated, resourcesProvider), Theme.getColor(Theme.key_text_RedRegular, resourcesProvider));
        editTextCaption.setImeOptions(6);
        editTextCaption.setBackgroundDrawable(null);
        editTextCaption.setPadding(0, AndroidUtilities.m1036dp(6.0f), 0, AndroidUtilities.m1036dp(6.0f));
        final TLRPC.TL_factCheck factCheck = messageObject.getFactCheck();
        if (factCheck != null && (tL_textWithEntities = factCheck.text) != null) {
            SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(tL_textWithEntities.text);
            MessageObject.addEntitiesToText(spannableStringBuilderValueOf, factCheck.text.entities, false, true, false, false);
            editTextCaption.setText(spannableStringBuilderValueOf);
        }
        editTextCaption.addTextChangedListener(new TextWatcher() { // from class: org.telegram.messenger.FactCheckController.3
            boolean ignoreTextChange;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (this.ignoreTextChange) {
                    return;
                }
                int length = editable.length();
                int i2 = i;
                boolean z5 = true;
                if (length > i2) {
                    this.ignoreTextChange = true;
                    editable.delete(i2, editable.length());
                    AndroidUtilities.shakeView(editTextCaption);
                    try {
                        editTextCaption.performHapticFeedback(3, 2);
                    } catch (Exception unused) {
                    }
                    this.ignoreTextChange = false;
                }
                if (textViewArr[0] != null) {
                    if (editable.length() <= 0 && factCheck != null) {
                        z5 = false;
                    }
                    textViewArr[0].setText(LocaleController.getString(z5 ? C2797R.string.Done : C2797R.string.Remove));
                    textViewArr[0].setTextColor(Theme.getColor(z5 ? Theme.key_dialogButton : Theme.key_text_RedBold));
                }
            }
        });
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.addView(editTextCaption, LayoutHelper.createLinear(-1, -2, 24.0f, 0.0f, 24.0f, 10.0f));
        builder2.makeCustomMaxHeight();
        builder2.setView(linearLayout);
        builder2.setWidth(AndroidUtilities.m1036dp(292.0f));
        builder2.setPositiveButton(LocaleController.getString(C2797R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                this.f$0.lambda$openFactCheckEditor$8(editTextCaption, i, messageObject, z4, alertDialog, i2);
            }
        });
        builder2.setNegativeButton(LocaleController.getString("Cancel", C2797R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                alertDialog.dismiss();
            }
        });
        if (z2) {
            AlertDialog alertDialogCreate = builder2.create();
            currentDialog = alertDialogCreate;
            alertDialogArr[0] = alertDialogCreate;
            alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    FactCheckController.$r8$lambda$covAdcOkR6_V4SG3w1tVgkD6jFY(currentFocus, dialogInterface);
                }
            });
            currentDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    FactCheckController.$r8$lambda$cSn0YY6kSzQ16psVIG73jVTJqRo(editTextCaption, dialogInterface);
                }
            });
            currentDialog.showDelayed(250L);
        } else {
            AlertDialog alertDialogCreate2 = builder2.create();
            alertDialogArr[0] = alertDialogCreate2;
            alertDialogCreate2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AndroidUtilities.hideKeyboard(editTextCaption);
                }
            });
            alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    FactCheckController.$r8$lambda$rCZDn9DTeD00226gcwmQQ1cnVzY(editTextCaption, dialogInterface);
                }
            });
            alertDialogArr[0].show();
        }
        alertDialogArr[0].setDismissDialogByButtons(false);
        View button = alertDialogArr[0].getButton(-1);
        if (button instanceof TextView) {
            textViewArr[0] = (TextView) button;
        }
        editTextCaption.setSelection(editTextCaption.getText().length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openFactCheckEditor$8(EditTextCaption editTextCaption, int i, MessageObject messageObject, boolean z, AlertDialog alertDialog, int i2) {
        if (editTextCaption.getText().toString().length() > i) {
            AndroidUtilities.shakeView(editTextCaption);
            return;
        }
        TLRPC.TL_textWithEntities tL_textWithEntities = new TLRPC.TL_textWithEntities();
        CharSequence[] charSequenceArr = {editTextCaption.getText()};
        tL_textWithEntities.entities = MediaDataController.getInstance(this.currentAccount).getEntities(charSequenceArr, true);
        CharSequence charSequence = charSequenceArr[0];
        tL_textWithEntities.text = charSequence == null ? _UrlKt.FRAGMENT_ENCODE_SET : charSequence.toString();
        applyFactCheck(messageObject, tL_textWithEntities, z);
        alertDialog.dismiss();
    }

    public static /* synthetic */ void $r8$lambda$covAdcOkR6_V4SG3w1tVgkD6jFY(View view, DialogInterface dialogInterface) {
        currentDialog = null;
        view.requestFocus();
    }

    public static /* synthetic */ void $r8$lambda$cSn0YY6kSzQ16psVIG73jVTJqRo(EditTextCaption editTextCaption, DialogInterface dialogInterface) {
        editTextCaption.requestFocus();
        AndroidUtilities.showKeyboard(editTextCaption);
    }

    public static /* synthetic */ void $r8$lambda$rCZDn9DTeD00226gcwmQQ1cnVzY(EditTextCaption editTextCaption, DialogInterface dialogInterface) {
        editTextCaption.requestFocus();
        AndroidUtilities.showKeyboard(editTextCaption);
    }

    public void applyFactCheck(MessageObject messageObject, final TLRPC.TL_textWithEntities tL_textWithEntities, final boolean z) {
        TLObject tLObject;
        if (tL_textWithEntities != null && !TextUtils.isEmpty(tL_textWithEntities.text)) {
            TLRPC.TL_editFactCheck tL_editFactCheck = new TLRPC.TL_editFactCheck();
            tL_editFactCheck.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(messageObject.getDialogId());
            tL_editFactCheck.msg_id = messageObject.getId();
            tL_editFactCheck.text = tL_textWithEntities;
            tLObject = tL_editFactCheck;
        } else {
            if (z) {
                return;
            }
            TLRPC.TL_deleteFactCheck tL_deleteFactCheck = new TLRPC.TL_deleteFactCheck();
            tL_deleteFactCheck.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(messageObject.getDialogId());
            tL_deleteFactCheck.msg_id = messageObject.getId();
            tLObject = tL_deleteFactCheck;
        }
        Context context = LaunchActivity.instance;
        if (context == null) {
            context = ApplicationLoader.applicationContext;
        }
        final AlertDialog alertDialog = new AlertDialog(context, 3);
        alertDialog.showDelayed(320L);
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLObject, new RequestDelegate() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda0
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                this.f$0.lambda$applyFactCheck$16(tL_textWithEntities, z, alertDialog, tLObject2, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyFactCheck$16(final TLRPC.TL_textWithEntities tL_textWithEntities, final boolean z, final AlertDialog alertDialog, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$applyFactCheck$15(tLObject, tL_textWithEntities, z, alertDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyFactCheck$15(final TLObject tLObject, TLRPC.TL_textWithEntities tL_textWithEntities, boolean z, AlertDialog alertDialog) {
        if (tLObject instanceof TLRPC.Updates) {
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FactCheckController$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$applyFactCheck$14(tLObject);
                }
            });
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            if (safeLastFragment != null) {
                boolean z2 = tL_textWithEntities == null || TextUtils.isEmpty(tL_textWithEntities.text);
                if (z2 || !z) {
                    BulletinFactory.m1143of(safeLastFragment).createSimpleBulletin(z2 ? C2797R.raw.ic_delete : C2797R.raw.contact_check, LocaleController.getString(z2 ? C2797R.string.FactCheckDeleted : C2797R.string.FactCheckEdited)).show();
                }
            }
        }
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyFactCheck$14(TLObject tLObject) {
        MessagesController.getInstance(this.currentAccount).processUpdates((TLRPC.Updates) tLObject, false);
    }
}
