package org.telegram.p035ui.Components.poll;

import androidx.collection.LongSparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.telegram.messenger.AiTonesController$$ExternalSyntheticLambda0;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;

/* JADX INFO: loaded from: classes7.dex */
public class WebPageLoader {
    private final int currentAccount;
    private final HashMap<String, TLRPC.WebPage> pages = new HashMap<>();
    private final HashMap<String, ArrayList<Utilities.Callback2<TLRPC.WebPage, TLObject>>> callbacks = new HashMap<>();

    public WebPageLoader(int i) {
        this.currentAccount = i;
    }

    public boolean isLoading(String str) {
        return this.callbacks.containsKey(str);
    }

    public TLRPC.WebPage getWebPage(String str) {
        return this.pages.get(str);
    }

    public void get(final String str, Utilities.Callback2<TLRPC.WebPage, TLObject> callback2) {
        if (this.pages.containsKey(str)) {
            callback2.run(this.pages.get(str), null);
            return;
        }
        boolean zContainsKey = this.callbacks.containsKey(str);
        ArrayList<Utilities.Callback2<TLRPC.WebPage, TLObject>> arrayList = this.callbacks.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.callbacks.put(str, arrayList);
        }
        arrayList.add(callback2);
        if (zContainsKey) {
            return;
        }
        TL_account.getWebPagePreview getwebpagepreview = new TL_account.getWebPagePreview();
        getwebpagepreview.message = str;
        ConnectionsManager.getInstance(this.currentAccount).sendRequestTyped(getwebpagepreview, new AiTonesController$$ExternalSyntheticLambda0(), new Utilities.Callback2() { // from class: org.telegram.ui.Components.poll.WebPageLoader$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$get$0(str, (TL_account.webPagePreview) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$get$0(java.lang.String r4, org.telegram.tgnet.tl.TL_account.webPagePreview r5, org.telegram.tgnet.TLRPC.TL_error r6) {
        /*
            r3 = this;
            r0 = 0
            if (r5 == 0) goto L20
            int r1 = r3.currentAccount
            org.telegram.messenger.MessagesController r1 = org.telegram.messenger.MessagesController.getInstance(r1)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$User> r2 = r5.users
            r1.putUsers(r2, r0)
            int r1 = r3.currentAccount
            org.telegram.messenger.MessagesController r1 = org.telegram.messenger.MessagesController.getInstance(r1)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r2 = r5.chats
            r1.putChats(r2, r0)
            org.telegram.tgnet.TLRPC$MessageMedia r5 = r5.media
            if (r5 == 0) goto L20
            org.telegram.tgnet.TLRPC$WebPage r5 = r5.webpage
            goto L21
        L20:
            r5 = 0
        L21:
            java.util.HashMap<java.lang.String, org.telegram.tgnet.TLRPC$WebPage> r1 = r3.pages
            r1.put(r4, r5)
            java.util.HashMap<java.lang.String, java.util.ArrayList<org.telegram.messenger.Utilities$Callback2<org.telegram.tgnet.TLRPC$WebPage, org.telegram.tgnet.TLObject>>> r3 = r3.callbacks
            java.lang.Object r3 = r3.remove(r4)
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            if (r3 == 0) goto L42
            int r4 = r3.size()
        L34:
            if (r0 >= r4) goto L42
            java.lang.Object r1 = r3.get(r0)
            int r0 = r0 + 1
            org.telegram.messenger.Utilities$Callback2 r1 = (org.telegram.messenger.Utilities.Callback2) r1
            r1.run(r5, r6)
            goto L34
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.poll.WebPageLoader.lambda$get$0(java.lang.String, org.telegram.tgnet.tl.TL_account$webPagePreview, org.telegram.tgnet.TLRPC$TL_error):void");
    }

    public void apply(LongSparseArray<TLRPC.WebPage> longSparseArray) {
        TLRPC.WebPage webPage;
        for (Map.Entry<String, TLRPC.WebPage> entry : this.pages.entrySet()) {
            if (entry.getValue() != null && (webPage = longSparseArray.get(entry.getValue().f1416id)) != null) {
                entry.setValue(webPage);
            }
        }
    }
}
