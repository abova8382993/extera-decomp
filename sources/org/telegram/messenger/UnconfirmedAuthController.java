package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import kotlin.jvm.internal.LongCompanionObject;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.Business.BusinessChatbotController;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.OutputSerializedData;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLParseException;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;
import org.telegram.tgnet.p034tl.TL_update;

/* JADX INFO: loaded from: classes.dex */
public class UnconfirmedAuthController {
    private final int currentAccount;
    private boolean fetchedCache;
    private boolean fetchingCache;
    private boolean saveAfterFetch;
    private boolean savingCache;
    public final ArrayList<UnconfirmedAuth> auths = new ArrayList<>();
    private final Runnable checkExpiration = new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda5
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$2();
        }
    };
    private boolean debug = false;

    public UnconfirmedAuthController(int i) {
        this.currentAccount = i;
        readCache();
    }

    public void readCache() {
        if (this.fetchedCache || this.fetchingCache) {
            return;
        }
        this.fetchingCache = true;
        MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$readCache$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readCache$1() {
        final ArrayList<TLRPC.User> arrayList = new ArrayList<>();
        ArrayList<Long> arrayList2 = new ArrayList<>();
        final HashSet hashSet = new HashSet();
        final ArrayList arrayList3 = new ArrayList();
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = MessagesStorage.getInstance(this.currentAccount).getDatabase().queryFinalized(String.format(Locale.US, "SELECT data FROM unconfirmed_auth", new Object[0]), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    if (nativeByteBufferByteBufferValue != null) {
                        try {
                            UnconfirmedAuth unconfirmedAuth = new UnconfirmedAuth(nativeByteBufferByteBufferValue);
                            arrayList3.add(unconfirmedAuth);
                            hashSet.add(Long.valueOf(unconfirmedAuth.hash));
                            if (unconfirmedAuth.bot && !arrayList2.contains(Long.valueOf(unconfirmedAuth.bot_id))) {
                                arrayList2.add(Long.valueOf(unconfirmedAuth.bot_id));
                            }
                        } catch (Exception e) {
                            FileLog.m1048e(e);
                        }
                        nativeByteBufferByteBufferValue.reuse();
                    }
                }
                MessagesStorage.getInstance(this.currentAccount).getUsersInternal(arrayList2, arrayList);
            } catch (Exception e2) {
                FileLog.m1048e(e2);
                if (sQLiteCursorQueryFinalized != null) {
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$readCache$0(arrayList, hashSet, arrayList3);
                    }
                });
            }
            sQLiteCursorQueryFinalized.dispose();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$readCache$0(arrayList, hashSet, arrayList3);
                }
            });
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readCache$0(ArrayList arrayList, HashSet hashSet, ArrayList arrayList2) {
        ArrayList<UnconfirmedAuth> arrayList3;
        MessagesController.getInstance(this.currentAccount).putUsers(arrayList, true);
        boolean zIsEmpty = this.auths.isEmpty();
        int i = 0;
        while (true) {
            int size = this.auths.size();
            arrayList3 = this.auths;
            if (i >= size) {
                break;
            }
            UnconfirmedAuth unconfirmedAuth = arrayList3.get(i);
            if (unconfirmedAuth == null || unconfirmedAuth.expired() || hashSet.contains(Long.valueOf(unconfirmedAuth.hash))) {
                this.auths.remove(i);
                i--;
            }
            i++;
        }
        arrayList3.addAll(arrayList2);
        boolean zIsEmpty2 = this.auths.isEmpty();
        this.fetchedCache = true;
        this.fetchingCache = false;
        if (zIsEmpty != zIsEmpty2) {
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.unconfirmedAuthUpdate, new Object[0]);
        }
        scheduleAuthExpireCheck();
        if (this.saveAfterFetch) {
            this.saveAfterFetch = false;
            saveCache();
        }
    }

    private void scheduleAuthExpireCheck() {
        AndroidUtilities.cancelRunOnUIThread(this.checkExpiration);
        if (this.auths.isEmpty()) {
            return;
        }
        ArrayList<UnconfirmedAuth> arrayList = this.auths;
        int size = arrayList.size();
        int i = 0;
        long jMin = Long.MAX_VALUE;
        while (i < size) {
            UnconfirmedAuth unconfirmedAuth = arrayList.get(i);
            i++;
            jMin = Math.min(jMin, unconfirmedAuth.expiresAfter());
        }
        if (jMin == LongCompanionObject.MAX_VALUE) {
            return;
        }
        AndroidUtilities.runOnUIThread(this.checkExpiration, Math.max(0L, jMin * 1000));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        int i = 0;
        while (i < this.auths.size()) {
            if (this.auths.get(i).expired()) {
                this.auths.remove(i);
                i--;
            }
            i++;
        }
        saveCache();
    }

    public void putDebug() {
        this.debug = true;
        TL_update.TL_updateNewAuthorization tL_updateNewAuthorization = new TL_update.TL_updateNewAuthorization();
        tL_updateNewAuthorization.unconfirmed = true;
        tL_updateNewAuthorization.device = "device";
        tL_updateNewAuthorization.location = "location";
        tL_updateNewAuthorization.hash = 123L;
        processUpdate(tL_updateNewAuthorization);
    }

    public void processUpdate(TL_update.TL_updateNewAuthorization tL_updateNewAuthorization) {
        int i = 0;
        while (i < this.auths.size()) {
            UnconfirmedAuth unconfirmedAuth = this.auths.get(i);
            if (unconfirmedAuth != null && !unconfirmedAuth.bot && unconfirmedAuth.hash == tL_updateNewAuthorization.hash) {
                this.auths.remove(i);
                i--;
            }
            i++;
        }
        if (tL_updateNewAuthorization.unconfirmed) {
            this.auths.add(new UnconfirmedAuth(tL_updateNewAuthorization));
        }
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.unconfirmedAuthUpdate, new Object[0]);
        scheduleAuthExpireCheck();
        saveCache();
    }

    public void processUpdate(TL_update.TL_updateNewBotConnection tL_updateNewBotConnection) {
        int i = 0;
        while (true) {
            int size = this.auths.size();
            ArrayList<UnconfirmedAuth> arrayList = this.auths;
            if (i < size) {
                UnconfirmedAuth unconfirmedAuth = arrayList.get(i);
                if (unconfirmedAuth != null && unconfirmedAuth.bot && unconfirmedAuth.bot_id == tL_updateNewBotConnection.bot_id) {
                    this.auths.remove(i);
                    i--;
                }
                i++;
            } else {
                arrayList.add(new UnconfirmedAuth(tL_updateNewBotConnection));
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.unconfirmedAuthUpdate, new Object[0]);
                scheduleAuthExpireCheck();
                saveCache();
                return;
            }
        }
    }

    public void saveCache() {
        if (this.savingCache) {
            return;
        }
        if (this.fetchingCache) {
            this.saveAfterFetch = true;
        } else {
            this.savingCache = true;
            MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$saveCache$4();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$saveCache$4() throws java.lang.Throwable {
        /*
            r9 = this;
            int r0 = r9.currentAccount
            org.telegram.messenger.MessagesStorage r0 = org.telegram.messenger.MessagesStorage.getInstance(r0)
            org.telegram.SQLite.SQLiteDatabase r0 = r0.getDatabase()
            r1 = 0
            java.lang.String r2 = "DELETE FROM unconfirmed_auth WHERE 1"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r0.executeFast(r2)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.stepThis()     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            r2.dispose()     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            java.lang.String r2 = "REPLACE INTO unconfirmed_auth VALUES(?)"
            org.telegram.SQLite.SQLitePreparedStatement r0 = r0.executeFast(r2)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            java.util.ArrayList<org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth> r2 = r9.auths     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            int r3 = r2.size()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r4 = 0
        L25:
            if (r4 >= r3) goto L59
            java.lang.Object r5 = r2.get(r4)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            int r4 = r4 + 1
            org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth r5 = (org.telegram.messenger.UnconfirmedAuthController.UnconfirmedAuth) r5     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            r0.requery()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            org.telegram.tgnet.NativeByteBuffer r6 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L50
            int r7 = r5.getObjectSize()     // Catch: java.lang.Throwable -> L50
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L50
            r5.serializeToStream(r6)     // Catch: java.lang.Throwable -> L4e
            r5 = 1
            r0.bindByteBuffer(r5, r6)     // Catch: java.lang.Throwable -> L4e
            r0.step()     // Catch: java.lang.Throwable -> L4e
            r6.reuse()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
            goto L25
        L49:
            r9 = move-exception
            r1 = r0
            goto L74
        L4c:
            r1 = move-exception
            goto L65
        L4e:
            r1 = move-exception
            goto L53
        L50:
            r2 = move-exception
            r6 = r1
            r1 = r2
        L53:
            if (r6 == 0) goto L58
            r6.reuse()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
        L58:
            throw r1     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4c
        L59:
            if (r0 == 0) goto L6b
        L5b:
            r0.dispose()
            goto L6b
        L5f:
            r9 = move-exception
            goto L74
        L61:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L65:
            org.telegram.messenger.FileLog.m1048e(r1)     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto L6b
            goto L5b
        L6b:
            org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda4 r0 = new org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda4
            r0.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r0)
            return
        L74:
            if (r1 == 0) goto L79
            r1.dispose()
        L79:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.UnconfirmedAuthController.lambda$saveCache$4():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveCache$3() {
        this.savingCache = false;
    }

    public void cleanup() {
        this.auths.clear();
        saveCache();
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.unconfirmedAuthUpdate, new Object[0]);
        scheduleAuthExpireCheck();
    }

    private void updateList(final boolean z, ArrayList<UnconfirmedAuth> arrayList, final Utilities.Callback<ArrayList<UnconfirmedAuth>> callback) {
        final ArrayList arrayList2 = new ArrayList(arrayList);
        final boolean[] zArr = new boolean[arrayList2.size()];
        Utilities.Callback[] callbackArr = new Utilities.Callback[arrayList2.size()];
        for (final int i = 0; i < arrayList2.size(); i++) {
            final UnconfirmedAuth unconfirmedAuth = (UnconfirmedAuth) arrayList2.get(i);
            callbackArr[i] = new Utilities.Callback() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    UnconfirmedAuthController.m6354$r8$lambda$s03l1nRxgy6Lr_3nNXQXlCZPK0(zArr, i, z, unconfirmedAuth, (Runnable) obj);
                }
            };
        }
        Utilities.raceCallbacks(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateList$7(zArr, arrayList2, z, callback);
            }
        }, callbackArr);
        if (z) {
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                hashSet.add(Long.valueOf(((UnconfirmedAuth) arrayList2.get(i2)).hash));
            }
            int i3 = 0;
            while (i3 < this.auths.size()) {
                if (hashSet.contains(Long.valueOf(this.auths.get(i3).hash))) {
                    this.auths.remove(i3);
                    i3--;
                }
                i3++;
            }
            if (hashSet.isEmpty()) {
                return;
            }
            saveCache();
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.unconfirmedAuthUpdate, new Object[0]);
            scheduleAuthExpireCheck();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$NJhReUHqCliAHaxOC-3AKA_h2IU, reason: not valid java name */
    public static /* synthetic */ void m6352$r8$lambda$NJhReUHqCliAHaxOC3AKA_h2IU(boolean[] zArr, int i, Runnable runnable, Boolean bool) {
        zArr[i] = bool.booleanValue();
        runnable.run();
    }

    /* JADX INFO: renamed from: $r8$lambda$s03l1nRxg-y6Lr_3nNXQXlCZPK0, reason: not valid java name */
    public static /* synthetic */ void m6354$r8$lambda$s03l1nRxgy6Lr_3nNXQXlCZPK0(final boolean[] zArr, final int i, boolean z, UnconfirmedAuth unconfirmedAuth, final Runnable runnable) {
        Utilities.Callback<Boolean> callback = new Utilities.Callback() { // from class: org.telegram.messenger.UnconfirmedAuthController$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                UnconfirmedAuthController.m6352$r8$lambda$NJhReUHqCliAHaxOC3AKA_h2IU(zArr, i, runnable, (Boolean) obj);
            }
        };
        if (z) {
            unconfirmedAuth.confirm(callback);
        } else {
            unconfirmedAuth.deny(callback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateList$7(boolean[] zArr, ArrayList arrayList, boolean z, Utilities.Callback callback) {
        HashSet hashSet = new HashSet();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < zArr.length; i++) {
            if (zArr[i]) {
                UnconfirmedAuth unconfirmedAuth = (UnconfirmedAuth) arrayList.get(i);
                arrayList2.add(unconfirmedAuth);
                hashSet.add(Long.valueOf(unconfirmedAuth.hash));
            }
        }
        if (!z) {
            int i2 = 0;
            while (i2 < this.auths.size()) {
                if (hashSet.contains(Long.valueOf(this.auths.get(i2).hash))) {
                    this.auths.remove(i2);
                    i2--;
                }
                i2++;
            }
            if (!hashSet.isEmpty()) {
                saveCache();
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.unconfirmedAuthUpdate, new Object[0]);
                scheduleAuthExpireCheck();
            }
        }
        callback.run(arrayList2);
    }

    public void confirm(ArrayList<UnconfirmedAuth> arrayList, Utilities.Callback<ArrayList<UnconfirmedAuth>> callback) {
        updateList(true, arrayList, callback);
    }

    public void deny(ArrayList<UnconfirmedAuth> arrayList, Utilities.Callback<ArrayList<UnconfirmedAuth>> callback) {
        updateList(false, arrayList, callback);
    }

    /* JADX INFO: loaded from: classes5.dex */
    public class UnconfirmedAuth extends TLObject {
        public boolean bot;
        public long bot_id;
        public int date;
        public String device;
        public long hash;
        public String location;

        public UnconfirmedAuth(AbstractSerializedData abstractSerializedData) {
            int int32 = abstractSerializedData.readInt32(true);
            if (int32 == 2058772876) {
                this.hash = abstractSerializedData.readInt64(true);
                this.date = abstractSerializedData.readInt32(true);
                this.device = abstractSerializedData.readString(true);
                this.location = abstractSerializedData.readString(true);
                return;
            }
            if (int32 == 2058772877) {
                this.bot_id = abstractSerializedData.readInt64(true);
                this.date = abstractSerializedData.readInt32(true);
                this.device = abstractSerializedData.readString(true);
                this.location = abstractSerializedData.readString(true);
                return;
            }
            TLParseException.doThrowOrLog(abstractSerializedData, "UnconfirmedAuth", int32, true);
        }

        public UnconfirmedAuth(TL_update.TL_updateNewAuthorization tL_updateNewAuthorization) {
            this.hash = tL_updateNewAuthorization.hash;
            this.date = tL_updateNewAuthorization.date;
            this.device = tL_updateNewAuthorization.device;
            this.location = tL_updateNewAuthorization.location;
        }

        public UnconfirmedAuth(TL_update.TL_updateNewBotConnection tL_updateNewBotConnection) {
            this.bot = true;
            long j = tL_updateNewBotConnection.bot_id;
            this.bot_id = j;
            this.hash = j;
            this.date = tL_updateNewBotConnection.date;
            this.device = tL_updateNewBotConnection.device;
            this.location = tL_updateNewBotConnection.location;
        }

        @Override // org.telegram.tgnet.TLObject
        public void serializeToStream(OutputSerializedData outputSerializedData) {
            if (this.bot) {
                outputSerializedData.writeInt32(2058772877);
                outputSerializedData.writeInt64(this.bot_id);
                outputSerializedData.writeInt32(this.date);
                outputSerializedData.writeString(this.device);
                outputSerializedData.writeString(this.location);
                return;
            }
            outputSerializedData.writeInt32(2058772876);
            outputSerializedData.writeInt64(this.hash);
            outputSerializedData.writeInt32(this.date);
            outputSerializedData.writeString(this.device);
            outputSerializedData.writeString(this.location);
        }

        public long expiresAfter() {
            return (ConnectionsManager.getInstance(UnconfirmedAuthController.this.currentAccount).getCurrentTime() + MessagesController.getInstance(UnconfirmedAuthController.this.currentAccount).authorizationAutoconfirmPeriod) - this.date;
        }

        public boolean expired() {
            return expiresAfter() <= 0;
        }

        public void confirm(final Utilities.Callback<Boolean> callback) {
            if (this.bot) {
                TL_account.confirmBotConnection confirmbotconnection = new TL_account.confirmBotConnection();
                confirmbotconnection.bot_id = MessagesController.getInstance(UnconfirmedAuthController.this.currentAccount).getInputUser(this.bot_id);
                ConnectionsManager.getInstance(UnconfirmedAuthController.this.currentAccount).sendRequestTyped(confirmbotconnection, new Utilities.Callback2() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda5
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) {
                        this.f$0.lambda$confirm$0(callback, (TLRPC.Bool) obj, (TLRPC.TL_error) obj2);
                    }
                });
            } else {
                TL_account.changeAuthorizationSettings changeauthorizationsettings = new TL_account.changeAuthorizationSettings();
                changeauthorizationsettings.hash = this.hash;
                changeauthorizationsettings.confirmed = true;
                ConnectionsManager.getInstance(UnconfirmedAuthController.this.currentAccount).sendRequest(changeauthorizationsettings, new RequestDelegate() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda6
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$confirm$2(callback, tLObject, tL_error);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$0(Utilities.Callback callback, TLRPC.Bool bool, TLRPC.TL_error tL_error) {
            if (callback != null) {
                callback.run(Boolean.valueOf(((bool instanceof TLRPC.TL_boolTrue) && tL_error == null) || UnconfirmedAuthController.this.debug));
                UnconfirmedAuthController.this.debug = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$2(final Utilities.Callback callback, final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$confirm$1(callback, tLObject, tL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$1(Utilities.Callback callback, TLObject tLObject, TLRPC.TL_error tL_error) {
            if (callback != null) {
                callback.run(Boolean.valueOf(((tLObject instanceof TLRPC.TL_boolTrue) && tL_error == null) || UnconfirmedAuthController.this.debug));
                UnconfirmedAuthController.this.debug = false;
            }
        }

        public void deny(final Utilities.Callback<Boolean> callback) {
            if (this.bot) {
                TL_account.updateConnectedBot updateconnectedbot = new TL_account.updateConnectedBot();
                updateconnectedbot.deleted = true;
                updateconnectedbot.bot = MessagesController.getInstance(UnconfirmedAuthController.this.currentAccount).getInputUser(this.bot_id);
                updateconnectedbot.recipients = new TL_account.TL_inputBusinessBotRecipients();
                ConnectionsManager.getInstance(UnconfirmedAuthController.this.currentAccount).sendRequest(updateconnectedbot, new RequestDelegate() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$deny$4(callback, tLObject, tL_error);
                    }
                });
                return;
            }
            TL_account.resetAuthorization resetauthorization = new TL_account.resetAuthorization();
            resetauthorization.hash = this.hash;
            ConnectionsManager.getInstance(UnconfirmedAuthController.this.currentAccount).sendRequest(resetauthorization, new RequestDelegate() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda2
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$deny$6(callback, tLObject, tL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deny$4(final Utilities.Callback callback, final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$deny$3(tLObject, callback, tL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deny$3(TLObject tLObject, Utilities.Callback callback, TLRPC.TL_error tL_error) {
            boolean z = tLObject instanceof TLRPC.Updates;
            if (z) {
                MessagesController.getInstance(UnconfirmedAuthController.this.currentAccount).processUpdates((TLRPC.Updates) tLObject, false);
            }
            boolean z2 = true;
            BusinessChatbotController.getInstance(UnconfirmedAuthController.this.currentAccount).invalidate(true);
            if (callback != null) {
                if ((!z || tL_error != null) && !UnconfirmedAuthController.this.debug) {
                    z2 = false;
                }
                callback.run(Boolean.valueOf(z2));
                UnconfirmedAuthController.this.debug = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deny$6(final Utilities.Callback callback, final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UnconfirmedAuthController$UnconfirmedAuth$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$deny$5(callback, tLObject, tL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deny$5(Utilities.Callback callback, TLObject tLObject, TLRPC.TL_error tL_error) {
            if (callback != null) {
                callback.run(Boolean.valueOf(((tLObject instanceof TLRPC.TL_boolTrue) && tL_error == null) || UnconfirmedAuthController.this.debug));
                UnconfirmedAuthController.this.debug = false;
            }
        }
    }
}
