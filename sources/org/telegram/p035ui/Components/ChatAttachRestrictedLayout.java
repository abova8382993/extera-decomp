package org.telegram.p035ui.Components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class ChatAttachRestrictedLayout extends ChatAttachAlert.AttachAlertLayout {
    private final RecyclerView.Adapter adapter;
    private int gridExtraSpace;

    /* JADX INFO: renamed from: id */
    public final int f1544id;
    private final RecyclerListView listView;
    private final EmptyTextProgressView progressView;

    public ChatAttachRestrictedLayout(int i, ChatAttachAlert chatAttachAlert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(chatAttachAlert, context, resourcesProvider);
        this.f1544id = i;
        EmptyTextProgressView emptyTextProgressView = new EmptyTextProgressView(context, null, resourcesProvider);
        this.progressView = emptyTextProgressView;
        emptyTextProgressView.setText(LocaleController.getString(C2797R.string.NoPhotos));
        emptyTextProgressView.setOnTouchListener(null);
        emptyTextProgressView.setTextSize(16);
        addView(emptyTextProgressView, LayoutHelper.createFrame(-1, -2.0f));
        emptyTextProgressView.setLottie(C2797R.raw.media_forbidden, 150, 150);
        TLRPC.Chat chat = this.parentAlert.getChat();
        if (i == 1) {
            emptyTextProgressView.setText(ChatObject.getRestrictedErrorText(chat, 7));
        } else if (i == 3) {
            emptyTextProgressView.setText(ChatObject.getRestrictedErrorText(chat, 18));
        } else if (i == 4) {
            emptyTextProgressView.setText(ChatObject.getRestrictedErrorText(chat, 19));
        } else {
            emptyTextProgressView.setText(ChatObject.getRestrictedErrorText(chat, 22));
        }
        emptyTextProgressView.showTextView();
        RecyclerListView recyclerListView = new RecyclerListView(context, resourcesProvider);
        this.listView = recyclerListView;
        recyclerListView.setSectionsType(2);
        recyclerListView.setVerticalScrollBarEnabled(false);
        recyclerListView.setLayoutManager(new LinearLayoutManager(context));
        recyclerListView.setClipToPadding(false);
        C41591 c41591 = new RecyclerView.Adapter() { // from class: org.telegram.ui.Components.ChatAttachRestrictedLayout.1
            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return 1;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
            }

            public C41591() {
            }

            /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachRestrictedLayout$1$1 */
            public class AnonymousClass1 extends View {
                public AnonymousClass1(Context context) {
                    super(context);
                }

                @Override // android.view.View
                public void onMeasure(int i, int i2) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(ChatAttachRestrictedLayout.this.gridExtraSpace, TLObject.FLAG_30));
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                return new RecyclerListView.Holder(new View(ChatAttachRestrictedLayout.this.getContext()) { // from class: org.telegram.ui.Components.ChatAttachRestrictedLayout.1.1
                    public AnonymousClass1(Context context2) {
                        super(context2);
                    }

                    @Override // android.view.View
                    public void onMeasure(int i3, int i22) {
                        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(ChatAttachRestrictedLayout.this.gridExtraSpace, TLObject.FLAG_30));
                    }
                });
            }
        };
        this.adapter = c41591;
        recyclerListView.setAdapter(c41591);
        recyclerListView.setPadding(0, 0, 0, AndroidUtilities.m1036dp(48.0f));
        recyclerListView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ChatAttachRestrictedLayout.2
            public C41602() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                ChatAttachRestrictedLayout chatAttachRestrictedLayout = ChatAttachRestrictedLayout.this;
                chatAttachRestrictedLayout.parentAlert.updateLayout(chatAttachRestrictedLayout, true, i3);
            }
        });
        addView(recyclerListView, LayoutHelper.createFrame(-1, -1.0f));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachRestrictedLayout$1 */
    public class C41591 extends RecyclerView.Adapter {
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
        }

        public C41591() {
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachRestrictedLayout$1$1 */
        public class AnonymousClass1 extends View {
            public AnonymousClass1(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void onMeasure(int i3, int i22) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(ChatAttachRestrictedLayout.this.gridExtraSpace, TLObject.FLAG_30));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
            return new RecyclerListView.Holder(new View(ChatAttachRestrictedLayout.this.getContext()) { // from class: org.telegram.ui.Components.ChatAttachRestrictedLayout.1.1
                public AnonymousClass1(Context context2) {
                    super(context2);
                }

                @Override // android.view.View
                public void onMeasure(int i3, int i22) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(ChatAttachRestrictedLayout.this.gridExtraSpace, TLObject.FLAG_30));
                }
            });
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachRestrictedLayout$2 */
    public class C41602 extends RecyclerView.OnScrollListener {
        public C41602() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
            ChatAttachRestrictedLayout chatAttachRestrictedLayout = ChatAttachRestrictedLayout.this;
            chatAttachRestrictedLayout.parentAlert.updateLayout(chatAttachRestrictedLayout, true, i3);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCurrentItemTop() {
        if (this.listView.getChildCount() <= 0) {
            return Integer.MAX_VALUE;
        }
        int i = 0;
        View childAt = this.listView.getChildAt(0);
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.listView.findContainingViewHolder(childAt);
        int top = childAt.getTop() - AndroidUtilities.m1036dp(8.0f);
        if (top > 0 && holder != null && holder.getAdapterPosition() == 0) {
            i = top;
        }
        if (top < 0 || holder == null || holder.getAdapterPosition() != 0) {
            top = i;
        }
        this.progressView.setTranslationY(((((getMeasuredHeight() - top) - AndroidUtilities.m1036dp(50.0f)) - this.progressView.getMeasuredHeight()) / 2) + top);
        return top + AndroidUtilities.m1036dp(12.0f);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.m1036dp(4.0f);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getListTopPadding() {
        return this.listView.getPaddingTop();
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x002d  */
    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPreMeasure(int r3, int r4) {
        /*
            r2 = this;
            super.onPreMeasure(r3, r4)
            int r3 = org.telegram.p035ui.ActionBar.ActionBar.getCurrentActionBarHeight()
            int r3 = r4 - r3
            r0 = 0
            int r3 = java.lang.Math.max(r0, r3)
            int r1 = r2.gridExtraSpace
            if (r1 == r3) goto L19
            r2.gridExtraSpace = r3
            androidx.recyclerview.widget.RecyclerView$Adapter r3 = r2.adapter
            r3.notifyDataSetChanged()
        L19:
            boolean r3 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r3 != 0) goto L2d
            android.graphics.Point r3 = org.telegram.messenger.AndroidUtilities.displaySize
            int r1 = r3.x
            int r3 = r3.y
            if (r1 <= r3) goto L2d
            float r3 = (float) r4
            r4 = 1080033280(0x40600000, float:3.5)
            float r3 = r3 / r4
            int r3 = (int) r3
            goto L31
        L2d:
            int r4 = r4 / 5
            int r3 = r4 * 2
        L31:
            r4 = 1112539136(0x42500000, float:52.0)
            int r4 = org.telegram.messenger.AndroidUtilities.m1036dp(r4)
            int r3 = r3 - r4
            if (r3 >= 0) goto L3b
            goto L3c
        L3b:
            r0 = r3
        L3c:
            org.telegram.ui.Components.RecyclerListView r3 = r2.listView
            int r3 = r3.getPaddingTop()
            if (r3 == r0) goto L59
            org.telegram.ui.Components.RecyclerListView r2 = r2.listView
            r3 = 1086324736(0x40c00000, float:6.0)
            int r4 = org.telegram.messenger.AndroidUtilities.m1036dp(r3)
            int r3 = org.telegram.messenger.AndroidUtilities.m1036dp(r3)
            r1 = 1111490560(0x42400000, float:48.0)
            int r1 = org.telegram.messenger.AndroidUtilities.m1036dp(r1)
            r2.setPadding(r4, r0, r3, r1)
        L59:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachRestrictedLayout.onPreMeasure(int, int):void");
    }
}
