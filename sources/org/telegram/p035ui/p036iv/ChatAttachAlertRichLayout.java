package org.telegram.p035ui.p036iv;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.EditTextCell;
import org.telegram.p035ui.Cells.TextSelectionHelper;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.ChatAttachAlertLocationLayout;
import org.telegram.p035ui.Components.ColoredImageSpan;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.ScaleStateListAnimator;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.p035ui.Components.UniversalRecyclerView;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.p035ui.p036iv.RichDividerCell;
import org.telegram.p035ui.p036iv.RichMapCell;
import org.telegram.p035ui.p036iv.RichMathCell;
import org.telegram.p035ui.p036iv.RichMediaCell;
import org.telegram.p035ui.p036iv.RichMediaUploader;
import org.telegram.p035ui.p036iv.RichTableCell;
import org.telegram.p035ui.p036iv.RichTextCell;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_iv;
import ru.noties.jlatexmath.JLatexMathDrawable;

/* JADX INFO: loaded from: classes7.dex */
public class ChatAttachAlertRichLayout extends ChatAttachAlert.AttachAlertLayout {
    private RichTableCell activeCellSelectionTable;
    private final RichTextCell.Delegate cellDelegate;
    private ActionBarPopupWindow.ActionBarPopupWindowLayout cellPopupLayout;
    private LinearLayout cellPopupRow;
    private ActionBarPopupWindow cellPopupWindow;
    private final RichTableCell.CellSelectionListener cellSelectionListener;
    private final int currentAccount;
    private int currentItemTop;
    private TextView delColAction;
    private TextView delRowAction;
    private final RichDividerCell.Delegate dividerDelegate;
    private TextView headerAction;
    private boolean ignoreLayout;
    private final UniversalRecyclerView listView;
    private boolean longPressConsumed;
    private Runnable longPressRunnable;
    private final RichMapCell.Delegate mapDelegate;
    private final RichMathCell.Delegate mathDelegate;
    private final RichMediaCell.Delegate mediaDelegate;
    private TextView mergeAction;
    private final TextView newBlockButton;
    private final LinearLayout newBlockButtonContainer;
    private boolean pressMoved;
    private View pressTarget;
    private float pressX;
    private float pressY;
    private int restoreFocusCell;
    private int restoreFocusChildPosition;
    private int restoreFocusOffset;
    private final ArrayList<BlockRow> rows;
    private boolean sendButtonShown;
    private final RichTableCell.Delegate tableDelegate;
    private final TextSelectionHelper.ArticleTextSelectionHelper textSelectionHelper;
    private final TextSelectionHelper.TextSelectionOverlay textSelectionOverlay;
    private TextView unmergeAction;
    private final IdentityHashMap<BlockRow, RichMediaUploader> uploaders;

    public static /* synthetic */ void $r8$lambda$m6ehlcPY5jslLH1zHnGvWLnjPBY(View view) {
    }

    private static boolean isArrowKey(int i) {
        return i == 21 || i == 22 || i == 19 || i == 20;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int needsActionBar() {
        return 1;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onHidden() {
    }

    public ChatAttachAlertRichLayout(ChatAttachAlert chatAttachAlert, Context context, int i, Theme.ResourcesProvider resourcesProvider) {
        super(chatAttachAlert, context, resourcesProvider);
        ArrayList<BlockRow> arrayList = new ArrayList<>();
        this.rows = arrayList;
        this.restoreFocusCell = -1;
        this.restoreFocusOffset = -1;
        this.restoreFocusChildPosition = 0;
        this.dividerDelegate = new RichDividerCell.Delegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.iv.RichDividerCell.Delegate
            public final TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
                return this.f$0.getTextSelectionHelper();
            }
        };
        this.mediaDelegate = new RichMediaCell.Delegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.4
            public C75114() {
            }

            @Override // org.telegram.ui.iv.RichMediaCell.Delegate
            public void onMediaPick(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.openMediaPicker(blockRow);
            }

            @Override // org.telegram.ui.iv.RichMediaCell.Delegate
            public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
                return ChatAttachAlertRichLayout.this.textSelectionHelper;
            }
        };
        this.mapDelegate = new RichMapCell.Delegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.5
            public C75125() {
            }

            @Override // org.telegram.ui.iv.RichMapCell.Delegate
            public void onPickLocation(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.lambda$transformRow$8(blockRow);
            }

            @Override // org.telegram.ui.iv.RichMapCell.Delegate
            public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
                return ChatAttachAlertRichLayout.this.textSelectionHelper;
            }
        };
        this.mathDelegate = new RichMathCell.Delegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.6
            public C75136() {
            }

            @Override // org.telegram.ui.iv.RichMathCell.Delegate
            public void onEditMath(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.lambda$transformRow$7(blockRow);
            }

            @Override // org.telegram.ui.iv.RichMathCell.Delegate
            public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
                return ChatAttachAlertRichLayout.this.textSelectionHelper;
            }
        };
        this.tableDelegate = new RichTableCell.Delegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.7
            public C75147() {
            }

            @Override // org.telegram.ui.iv.RichTableCell.Delegate
            public void onTextChanged(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.updateSendButton(true);
            }

            @Override // org.telegram.ui.iv.RichTableCell.Delegate
            public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
                return ChatAttachAlertRichLayout.this.textSelectionHelper;
            }

            @Override // org.telegram.ui.iv.RichTableCell.Delegate
            public void onRequestWindowFocusable(RichEditText richEditText, boolean z) {
                ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.makeFocusable(richEditText, z);
            }
        };
        this.cellSelectionListener = new RichTableCell.CellSelectionListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.8
            public C75158() {
            }

            @Override // org.telegram.ui.iv.RichTableCell.CellSelectionListener
            public void onCellSelectionChanged(RichTableCell richTableCell) {
                if (richTableCell == ChatAttachAlertRichLayout.this.activeCellSelectionTable && !richTableCell.hasCellSelection()) {
                    ChatAttachAlertRichLayout.this.exitCellSelectionMode();
                } else {
                    ChatAttachAlertRichLayout.this.updateCellActionBar();
                }
            }
        };
        this.cellDelegate = new RichTextCell.Delegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.9
            public C75169() {
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public void onEnter(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.onCellEnter(blockRow);
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public void onBackspace(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.onCellBackspace(blockRow);
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public boolean onBackspaceAtStart(BlockRow blockRow) {
                return ChatAttachAlertRichLayout.this.onCellBackspaceAtStart(blockRow);
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public void onTextChanged(BlockRow blockRow) {
                ChatAttachAlertRichLayout.this.updateSendButton(true);
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public void onTransform(BlockRow blockRow, TL_iv.PageBlock pageBlock, int i2, int i3) {
                ChatAttachAlertRichLayout.this.transformRow(blockRow, pageBlock, i2, i3);
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
                return ChatAttachAlertRichLayout.this.textSelectionHelper;
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public boolean onIndent(BlockRow blockRow, boolean z) {
                return ChatAttachAlertRichLayout.this.onCellIndent(blockRow, z);
            }

            @Override // org.telegram.ui.iv.RichTextCell.Delegate
            public void onRequestWindowFocusable(RichEditText richEditText, boolean z) {
                ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.makeFocusable(richEditText, z);
            }
        };
        this.uploaders = new IdentityHashMap<>();
        this.currentAccount = i;
        arrayList.add(new BlockRow(new TL_iv.pageBlockHeading1()));
        arrayList.add(new BlockRow(new TL_iv.pageBlockParagraph()));
        LinearLayout linearLayout = new LinearLayout(context);
        this.newBlockButtonContainer = linearLayout;
        linearLayout.setOrientation(0);
        linearLayout.setPadding(AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(6.0f));
        TextView textView = new TextView(context);
        this.newBlockButton = textView;
        textView.setTextSize(1, 16.0f);
        textView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText));
        textView.setTypeface(AndroidUtilities.bold());
        textView.setPadding(AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(4.0f));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("+ Add");
        ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2797R.drawable.poll_add_plus);
        coloredImageSpan.spaceScaleX = 0.9f;
        coloredImageSpan.translate(0.0f, AndroidUtilities.dpf2(0.4f));
        spannableStringBuilder.setSpan(coloredImageSpan, 0, 1, 33);
        textView.setText(spannableStringBuilder);
        textView.setBackground(Theme.createRadSelectorDrawable(getThemedColor(Theme.key_listSelector), 10, 10));
        ScaleStateListAnimator.apply(textView);
        linearLayout.addView(textView);
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChatAttachAlertRichLayout.$r8$lambda$m6ehlcPY5jslLH1zHnGvWLnjPBY(view);
            }
        });
        C75041 c75041 = new UniversalRecyclerView(context, i, 0, new Utilities.Callback2() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda3
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda4
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onItemClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, null, resourcesProvider) { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.1
            public C75041(Context context2, int i2, int i3, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider2) {
                super(context2, i2, i3, callback2, callback5, callback5Return, resourcesProvider2);
            }

            @Override // org.telegram.p035ui.Components.UniversalRecyclerView
            public void onLayoutUpdate() {
                if (ChatAttachAlertRichLayout.this.getCurrentItemTop() != ChatAttachAlertRichLayout.this.currentItemTop) {
                    ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.updateLayout(ChatAttachAlertRichLayout.this, true, 0);
                }
            }
        };
        this.listView = c75041;
        c75041.adapter.setApplyBackground(false);
        c75041.setClipToPadding(false);
        c75041.setPadding(0, AndroidUtilities.m1036dp(8.0f), 0, AndroidUtilities.m1036dp(8.0f));
        addView(c75041, LayoutHelper.createFrame(-1, -1, 119));
        TextSelectionHelper.ArticleTextSelectionHelper articleTextSelectionHelper = new TextSelectionHelper.ArticleTextSelectionHelper();
        this.textSelectionHelper = articleTextSelectionHelper;
        articleTextSelectionHelper.setParentView(c75041);
        articleTextSelectionHelper.layoutManager = c75041.layoutManager;
        TextSelectionHelper<Cell>.TextSelectionOverlay overlayView = articleTextSelectionHelper.getOverlayView(context2);
        this.textSelectionOverlay = overlayView;
        AndroidUtilities.removeFromParent(overlayView);
        addView(overlayView, LayoutHelper.createFrame(-1, -1.0f));
        setFocusable(true);
        setFocusableInTouchMode(true);
        if (Build.VERSION.SDK_INT >= 26) {
            setDefaultFocusHighlightEnabled(false);
        }
        setBackground(null);
        setForeground(null);
        articleTextSelectionHelper.setCallback(new C75092());
        c75041.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.3
            public C75103() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.updateLayout(ChatAttachAlertRichLayout.this, true, i3);
                ChatAttachAlertRichLayout.this.textSelectionHelper.onParentScrolled();
                if (ChatAttachAlertRichLayout.this.cellPopupWindow == null || !ChatAttachAlertRichLayout.this.cellPopupWindow.isShowing() || ChatAttachAlertRichLayout.this.activeCellSelectionTable == null) {
                    return;
                }
                ChatAttachAlertRichLayout.this.showOrUpdateCellPopup();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i2) {
                if (i2 == 0) {
                    ChatAttachAlertRichLayout.this.textSelectionHelper.stopScrolling();
                }
            }
        });
        buildCellPopup(context2);
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$1 */
    public class C75041 extends UniversalRecyclerView {
        public C75041(Context context2, int i2, int i3, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider2) {
            super(context2, i2, i3, callback2, callback5, callback5Return, resourcesProvider2);
        }

        @Override // org.telegram.p035ui.Components.UniversalRecyclerView
        public void onLayoutUpdate() {
            if (ChatAttachAlertRichLayout.this.getCurrentItemTop() != ChatAttachAlertRichLayout.this.currentItemTop) {
                ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.updateLayout(ChatAttachAlertRichLayout.this, true, 0);
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$2 */
    public class C75092 extends TextSelectionHelper.Callback {
        public C75092() {
        }

        @Override // org.telegram.ui.Cells.TextSelectionHelper.Callback
        public void onStateChanged(boolean z) {
            Log.d("RICHED", "helper.onStateChanged isSelected=" + z);
            ChatAttachAlertRichLayout chatAttachAlertRichLayout = ChatAttachAlertRichLayout.this;
            if (z) {
                if (chatAttachAlertRichLayout.activeCellSelectionTable != null) {
                    ChatAttachAlertRichLayout.this.exitCellSelectionMode();
                }
                ChatAttachAlertRichLayout chatAttachAlertRichLayout2 = ChatAttachAlertRichLayout.this;
                chatAttachAlertRichLayout2.restoreFocusCell = chatAttachAlertRichLayout2.textSelectionHelper.getAnchorCell();
                ChatAttachAlertRichLayout chatAttachAlertRichLayout3 = ChatAttachAlertRichLayout.this;
                chatAttachAlertRichLayout3.restoreFocusOffset = chatAttachAlertRichLayout3.textSelectionHelper.getAnchorOffset();
                ChatAttachAlertRichLayout chatAttachAlertRichLayout4 = ChatAttachAlertRichLayout.this;
                chatAttachAlertRichLayout4.restoreFocusChildPosition = chatAttachAlertRichLayout4.textSelectionHelper.getAnchorChildPosition();
                ChatAttachAlertRichLayout.this.setEditTextsLocked(true);
                ChatAttachAlertRichLayout.this.requestFocus();
                Log.d("RICHED", "RichLayout requestFocus -> hasFocus=" + ChatAttachAlertRichLayout.this.hasFocus() + " isFocused=" + ChatAttachAlertRichLayout.this.isFocused() + " restore=(" + ChatAttachAlertRichLayout.this.restoreFocusCell + "," + ChatAttachAlertRichLayout.this.restoreFocusChildPosition + "," + ChatAttachAlertRichLayout.this.restoreFocusOffset + ")");
                return;
            }
            final int i = chatAttachAlertRichLayout.restoreFocusCell;
            final int i2 = ChatAttachAlertRichLayout.this.restoreFocusOffset;
            final int i3 = ChatAttachAlertRichLayout.this.restoreFocusChildPosition;
            ChatAttachAlertRichLayout.this.restoreFocusCell = -1;
            ChatAttachAlertRichLayout.this.restoreFocusOffset = -1;
            ChatAttachAlertRichLayout.this.restoreFocusChildPosition = 0;
            ChatAttachAlertRichLayout.this.setEditTextsLocked(false);
            if (i >= 0) {
                ChatAttachAlertRichLayout.this.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onStateChanged$0(i, i3, i2);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onStateChanged$0(int i, int i2, int i3) {
            ChatAttachAlertRichLayout.this.restoreFocusAt(i, i2, i3);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$3 */
    public class C75103 extends RecyclerView.OnScrollListener {
        public C75103() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
            ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.updateLayout(ChatAttachAlertRichLayout.this, true, i3);
            ChatAttachAlertRichLayout.this.textSelectionHelper.onParentScrolled();
            if (ChatAttachAlertRichLayout.this.cellPopupWindow == null || !ChatAttachAlertRichLayout.this.cellPopupWindow.isShowing() || ChatAttachAlertRichLayout.this.activeCellSelectionTable == null) {
                return;
            }
            ChatAttachAlertRichLayout.this.showOrUpdateCellPopup();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i2) {
            if (i2 == 0) {
                ChatAttachAlertRichLayout.this.textSelectionHelper.stopScrolling();
            }
        }
    }

    private void buildCellPopup(Context context) {
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(context);
        this.cellPopupLayout = actionBarPopupWindowLayout;
        actionBarPopupWindowLayout.setPadding(AndroidUtilities.m1036dp(1.0f), AndroidUtilities.m1036dp(1.0f), AndroidUtilities.m1036dp(1.0f), AndroidUtilities.m1036dp(1.0f));
        this.cellPopupLayout.setBackgroundDrawable(context.getResources().getDrawable(C2797R.drawable.menu_copy));
        this.cellPopupLayout.setAnimationEnabled(false);
        this.cellPopupLayout.setShownFromBottom(false);
        LinearLayout linearLayout = new LinearLayout(context);
        this.cellPopupRow = linearLayout;
        linearLayout.setOrientation(0);
        this.headerAction = makePopupItem(context, "Mark Header", new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildCellPopup$1(view);
            }
        });
        this.mergeAction = makePopupItem(context, "Merge", new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildCellPopup$2(view);
            }
        });
        this.unmergeAction = makePopupItem(context, "Unmerge", new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildCellPopup$3(view);
            }
        });
        this.delRowAction = makePopupItem(context, "Delete Row", new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildCellPopup$4(view);
            }
        });
        this.delColAction = makePopupItem(context, "Delete Column", new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildCellPopup$5(view);
            }
        });
        this.cellPopupRow.addView(this.headerAction, LayoutHelper.createLinear(-2, 48));
        this.cellPopupRow.addView(this.mergeAction, LayoutHelper.createLinear(-2, 48));
        this.cellPopupRow.addView(this.unmergeAction, LayoutHelper.createLinear(-2, 48));
        this.cellPopupRow.addView(this.delRowAction, LayoutHelper.createLinear(-2, 48));
        this.cellPopupRow.addView(this.delColAction, LayoutHelper.createLinear(-2, 48));
        this.cellPopupLayout.addView((View) this.cellPopupRow, LayoutHelper.createLinear(-2, 48));
        this.cellPopupLayout.setBackgroundColor(getThemedColor(Theme.key_actionBarDefaultSubmenuBackground));
        ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(this.cellPopupLayout, -2, -2);
        this.cellPopupWindow = actionBarPopupWindow;
        actionBarPopupWindow.setAnimationEnabled(false);
        this.cellPopupWindow.setAnimationStyle(C2797R.style.PopupContextAnimation);
        this.cellPopupWindow.setOutsideTouchable(true);
    }

    public /* synthetic */ void lambda$buildCellPopup$1(View view) {
        handleCellActionHeader();
    }

    public /* synthetic */ void lambda$buildCellPopup$2(View view) {
        handleCellActionMerge();
    }

    public /* synthetic */ void lambda$buildCellPopup$3(View view) {
        handleCellActionUnmerge();
    }

    public /* synthetic */ void lambda$buildCellPopup$4(View view) {
        handleCellActionDeleteRow();
    }

    public /* synthetic */ void lambda$buildCellPopup$5(View view) {
        handleCellActionDeleteCol();
    }

    private TextView makePopupItem(Context context, String str, View.OnClickListener onClickListener) {
        TextView textView = new TextView(context);
        textView.setBackground(Theme.createSelectorDrawable(getThemedColor(Theme.key_listSelector), 2));
        textView.setGravity(16);
        textView.setPadding(AndroidUtilities.m1036dp(20.0f), 0, AndroidUtilities.m1036dp(20.0f), 0);
        textView.setTextSize(1, 15.0f);
        textView.setTypeface(AndroidUtilities.bold());
        textView.setTextColor(getThemedColor(Theme.key_actionBarDefaultSubmenuItem));
        textView.setText(str);
        textView.setOnClickListener(onClickListener);
        return textView;
    }

    private void handleCellActionHeader() {
        if (this.activeCellSelectionTable == null) {
            return;
        }
        this.activeCellSelectionTable.applyHeaderToggle(!r0.allSelectedHeader());
        exitCellSelectionMode();
        updateSendButton(true);
    }

    private void handleCellActionMerge() {
        RichTableCell richTableCell = this.activeCellSelectionTable;
        if (richTableCell != null && richTableCell.applyMergeFromSelection()) {
            updateCellActionBar();
            updateSendButton(true);
        }
    }

    private void handleCellActionUnmerge() {
        RichTableCell richTableCell = this.activeCellSelectionTable;
        if (richTableCell != null && richTableCell.applyUnmergeFromSelection()) {
            updateCellActionBar();
            updateSendButton(true);
        }
    }

    private void handleCellActionDeleteRow() {
        RichTableCell richTableCell = this.activeCellSelectionTable;
        if (richTableCell == null) {
            return;
        }
        richTableCell.applyDeleteRowsFromSelection();
        finalizeAfterTableStructureChange(richTableCell);
    }

    private void handleCellActionDeleteCol() {
        RichTableCell richTableCell = this.activeCellSelectionTable;
        if (richTableCell == null) {
            return;
        }
        richTableCell.applyDeleteColumnsFromSelection();
        finalizeAfterTableStructureChange(richTableCell);
    }

    private void finalizeAfterTableStructureChange(RichTableCell richTableCell) {
        BlockRow row;
        int iIndexOf;
        exitCellSelectionMode();
        if (richTableCell.isEmpty() && (row = richTableCell.getRow()) != null && (iIndexOf = this.rows.indexOf(row)) >= 0) {
            this.rows.remove(iIndexOf);
            if (this.rows.isEmpty()) {
                this.rows.add(new BlockRow(new TL_iv.pageBlockParagraph()));
            }
            this.listView.adapter.update(true);
        }
        updateSendButton(true);
    }

    public TextSelectionHelper.ArticleTextSelectionHelper getTextSelectionHelper() {
        return this.textSelectionHelper;
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$4 */
    public class C75114 implements RichMediaCell.Delegate {
        public C75114() {
        }

        @Override // org.telegram.ui.iv.RichMediaCell.Delegate
        public void onMediaPick(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.openMediaPicker(blockRow);
        }

        @Override // org.telegram.ui.iv.RichMediaCell.Delegate
        public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
            return ChatAttachAlertRichLayout.this.textSelectionHelper;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$5 */
    public class C75125 implements RichMapCell.Delegate {
        public C75125() {
        }

        @Override // org.telegram.ui.iv.RichMapCell.Delegate
        public void onPickLocation(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.lambda$transformRow$8(blockRow);
        }

        @Override // org.telegram.ui.iv.RichMapCell.Delegate
        public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
            return ChatAttachAlertRichLayout.this.textSelectionHelper;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$6 */
    public class C75136 implements RichMathCell.Delegate {
        public C75136() {
        }

        @Override // org.telegram.ui.iv.RichMathCell.Delegate
        public void onEditMath(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.lambda$transformRow$7(blockRow);
        }

        @Override // org.telegram.ui.iv.RichMathCell.Delegate
        public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
            return ChatAttachAlertRichLayout.this.textSelectionHelper;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$7 */
    public class C75147 implements RichTableCell.Delegate {
        public C75147() {
        }

        @Override // org.telegram.ui.iv.RichTableCell.Delegate
        public void onTextChanged(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.updateSendButton(true);
        }

        @Override // org.telegram.ui.iv.RichTableCell.Delegate
        public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
            return ChatAttachAlertRichLayout.this.textSelectionHelper;
        }

        @Override // org.telegram.ui.iv.RichTableCell.Delegate
        public void onRequestWindowFocusable(RichEditText richEditText, boolean z) {
            ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.makeFocusable(richEditText, z);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$8 */
    public class C75158 implements RichTableCell.CellSelectionListener {
        public C75158() {
        }

        @Override // org.telegram.ui.iv.RichTableCell.CellSelectionListener
        public void onCellSelectionChanged(RichTableCell richTableCell) {
            if (richTableCell == ChatAttachAlertRichLayout.this.activeCellSelectionTable && !richTableCell.hasCellSelection()) {
                ChatAttachAlertRichLayout.this.exitCellSelectionMode();
            } else {
                ChatAttachAlertRichLayout.this.updateCellActionBar();
            }
        }
    }

    private void enterCellSelectionMode(RichTableCell richTableCell, TL_iv.pageTableCell pagetablecell) {
        RichTableCell richTableCell2 = this.activeCellSelectionTable;
        if (richTableCell2 != null && richTableCell2 != richTableCell) {
            richTableCell2.clearCellSelection();
        }
        this.activeCellSelectionTable = richTableCell;
        richTableCell.setCellSelectionListener(this.cellSelectionListener);
        if (this.textSelectionHelper.isInSelectionMode()) {
            this.textSelectionHelper.clear();
        }
        setEditTextsLocked(true);
        richTableCell.addCellToSelection(pagetablecell);
        updateCellActionBar();
    }

    public void exitCellSelectionMode() {
        RichTableCell richTableCell = this.activeCellSelectionTable;
        if (richTableCell != null) {
            richTableCell.clearCellSelection();
            this.activeCellSelectionTable = null;
        }
        setEditTextsLocked(false);
        updateCellActionBar();
    }

    public void updateCellActionBar() {
        if (this.cellPopupWindow == null) {
            return;
        }
        RichTableCell richTableCell = this.activeCellSelectionTable;
        if (richTableCell == null || !richTableCell.hasCellSelection()) {
            if (this.cellPopupWindow.isShowing()) {
                this.cellPopupWindow.dismiss();
                return;
            }
            return;
        }
        Set<TL_iv.pageTableCell> selectedCells = this.activeCellSelectionTable.getSelectedCells();
        int size = selectedCells.size();
        boolean z = size >= 2 && computeCanMerge(this.activeCellSelectionTable, selectedCells);
        boolean z2 = size == 1 && computeHasSpan(selectedCells.iterator().next());
        boolean zComputeSpansFullRows = computeSpansFullRows(this.activeCellSelectionTable, selectedCells);
        boolean zComputeSpansFullColumns = computeSpansFullColumns(this.activeCellSelectionTable, selectedCells);
        this.headerAction.setVisibility(0);
        this.headerAction.setText(this.activeCellSelectionTable.allSelectedHeader() ? "Unmark Header" : "Mark Header");
        this.mergeAction.setVisibility(z ? 0 : 8);
        this.unmergeAction.setVisibility(z2 ? 0 : 8);
        this.delRowAction.setVisibility(zComputeSpansFullRows ? 0 : 8);
        this.delColAction.setVisibility(zComputeSpansFullColumns ? 0 : 8);
        showOrUpdateCellPopup();
    }

    public void showOrUpdateCellPopup() {
        if (this.cellPopupWindow == null || this.activeCellSelectionTable == null) {
            return;
        }
        this.cellPopupRow.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        this.cellPopupLayout.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        int[] iArrComputeCellPopupTopLeft = computeCellPopupTopLeft(this.cellPopupLayout.getMeasuredWidth(), this.cellPopupLayout.getMeasuredHeight());
        boolean zIsShowing = this.cellPopupWindow.isShowing();
        ActionBarPopupWindow actionBarPopupWindow = this.cellPopupWindow;
        if (zIsShowing) {
            actionBarPopupWindow.update(iArrComputeCellPopupTopLeft[0], iArrComputeCellPopupTopLeft[1], -1, -1);
        } else {
            actionBarPopupWindow.showAtLocation(this, 0, iArrComputeCellPopupTopLeft[0], iArrComputeCellPopupTopLeft[1]);
            this.cellPopupWindow.startAnimation();
        }
    }

    private int[] computeCellPopupTopLeft(int i, int i2) {
        int[] iArr = new int[2];
        Iterator<TL_iv.pageTableCell> it = this.activeCellSelectionTable.getSelectedCells().iterator();
        int width = Integer.MIN_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MAX_VALUE;
        while (it.hasNext()) {
            RichTableCellHost richTableCellHostHostForAnchor = this.activeCellSelectionTable.getGrid().hostForAnchor(it.next());
            if (richTableCellHostHostForAnchor != null) {
                richTableCellHostHostForAnchor.getLocationOnScreen(iArr);
                int i5 = iArr[1];
                if (i5 < i3) {
                    i3 = i5;
                }
                int i6 = iArr[0];
                if (i6 < i4) {
                    i4 = i6;
                }
                if (i6 + richTableCellHostHostForAnchor.getWidth() > width) {
                    width = iArr[0] + richTableCellHostHostForAnchor.getWidth();
                }
            }
        }
        if (i3 == Integer.MAX_VALUE) {
            this.activeCellSelectionTable.getLocationOnScreen(iArr);
            i3 = iArr[1];
            i4 = iArr[0];
            width = i4 + this.activeCellSelectionTable.getWidth();
        }
        int iM1036dp = ((i4 + width) / 2) - (i / 2);
        int iM1036dp2 = (i3 - i2) - AndroidUtilities.m1036dp(8.0f);
        int i7 = AndroidUtilities.displaySize.x;
        if (iM1036dp < AndroidUtilities.m1036dp(8.0f)) {
            iM1036dp = AndroidUtilities.m1036dp(8.0f);
        }
        if (iM1036dp + i > i7 - AndroidUtilities.m1036dp(8.0f)) {
            iM1036dp = (i7 - AndroidUtilities.m1036dp(8.0f)) - i;
        }
        if (iM1036dp2 < AndroidUtilities.m1036dp(8.0f)) {
            iM1036dp2 = AndroidUtilities.m1036dp(8.0f);
        }
        return new int[]{iM1036dp, iM1036dp2};
    }

    private static boolean computeHasSpan(TL_iv.pageTableCell pagetablecell) {
        return TableModel.spanCol(pagetablecell) > 1 || TableModel.spanRow(pagetablecell) > 1;
    }

    private static boolean computeCanMerge(RichTableCell richTableCell, Set<TL_iv.pageTableCell> set) {
        TableModel model = richTableCell.getModel();
        if (model == null) {
            return false;
        }
        int iMax = -1;
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        int iMax2 = -1;
        for (TL_iv.pageTableCell pagetablecell : set) {
            int iAnchorRowOf = model.anchorRowOf(pagetablecell);
            int iAnchorColOf = model.anchorColOf(pagetablecell);
            int iSpanRow = TableModel.spanRow(pagetablecell);
            int iSpanCol = TableModel.spanCol(pagetablecell);
            iMin = Math.min(iMin, iAnchorRowOf);
            iMin2 = Math.min(iMin2, iAnchorColOf);
            iMax = Math.max(iMax, (iAnchorRowOf + iSpanRow) - 1);
            iMax2 = Math.max(iMax2, (iAnchorColOf + iSpanCol) - 1);
        }
        HashSet hashSet = new HashSet();
        while (iMin <= iMax) {
            for (int i = iMin2; i <= iMax2; i++) {
                if (iMin < 0 || i < 0 || iMin >= model.rowCount || i >= model.colCount) {
                    return false;
                }
                hashSet.add(model.grid[iMin][i]);
            }
            iMin++;
        }
        return hashSet.equals(new HashSet(set));
    }

    private static boolean computeSpansFullRows(RichTableCell richTableCell, Set<TL_iv.pageTableCell> set) {
        TableModel model = richTableCell.getModel();
        if (model == null) {
            return false;
        }
        HashSet hashSet = new HashSet();
        Iterator<TL_iv.pageTableCell> it = set.iterator();
        while (it.hasNext()) {
            hashSet.add(Integer.valueOf(model.anchorRowOf(it.next())));
        }
        if (hashSet.isEmpty()) {
            return false;
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            int iIntValue = ((Integer) it2.next()).intValue();
            for (int i = 0; i < model.colCount; i++) {
                if (model.anchorR[iIntValue][i] != iIntValue || !set.contains(model.grid[iIntValue][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean computeSpansFullColumns(RichTableCell richTableCell, Set<TL_iv.pageTableCell> set) {
        TableModel model = richTableCell.getModel();
        if (model == null) {
            return false;
        }
        HashSet hashSet = new HashSet();
        Iterator<TL_iv.pageTableCell> it = set.iterator();
        while (it.hasNext()) {
            hashSet.add(Integer.valueOf(model.anchorColOf(it.next())));
        }
        if (hashSet.isEmpty()) {
            return false;
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            int iIntValue = ((Integer) it2.next()).intValue();
            for (int i = 0; i < model.rowCount; i++) {
                if (model.anchorC[i][iIntValue] != iIntValue || !set.contains(model.grid[i][iIntValue])) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$9 */
    public class C75169 implements RichTextCell.Delegate {
        public C75169() {
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public void onEnter(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.onCellEnter(blockRow);
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public void onBackspace(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.onCellBackspace(blockRow);
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public boolean onBackspaceAtStart(BlockRow blockRow) {
            return ChatAttachAlertRichLayout.this.onCellBackspaceAtStart(blockRow);
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public void onTextChanged(BlockRow blockRow) {
            ChatAttachAlertRichLayout.this.updateSendButton(true);
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public void onTransform(BlockRow blockRow, TL_iv.PageBlock pageBlock, int i2, int i3) {
            ChatAttachAlertRichLayout.this.transformRow(blockRow, pageBlock, i2, i3);
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper() {
            return ChatAttachAlertRichLayout.this.textSelectionHelper;
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public boolean onIndent(BlockRow blockRow, boolean z) {
            return ChatAttachAlertRichLayout.this.onCellIndent(blockRow, z);
        }

        @Override // org.telegram.ui.iv.RichTextCell.Delegate
        public void onRequestWindowFocusable(RichEditText richEditText, boolean z) {
            ((ChatAttachAlert.AttachAlertLayout) ChatAttachAlertRichLayout.this).parentAlert.makeFocusable(richEditText, z);
        }
    }

    public boolean onCellBackspaceAtStart(final BlockRow blockRow) {
        int iIndexOf = this.rows.indexOf(blockRow);
        if (iIndexOf <= 0) {
            return false;
        }
        int i = iIndexOf - 1;
        if (!(this.rows.get(i).block instanceof TL_iv.pageBlockDivider)) {
            return false;
        }
        this.rows.remove(i);
        renumberAllRuns();
        this.listView.adapter.update(true);
        this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCellBackspaceAtStart$6(blockRow);
            }
        });
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void transformRow(final org.telegram.p035ui.p036iv.BlockRow r3, org.telegram.tgnet.tl.TL_iv.PageBlock r4, int r5, int r6) {
        /*
            r2 = this;
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r0 = r2.rows
            int r0 = r0.indexOf(r3)
            if (r0 >= 0) goto L9
            return
        L9:
            r3.block = r4
            r3.level = r5
            r3.num = r6
            r2.renumberAllRuns()
            boolean r5 = r4 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockTable
            if (r5 == 0) goto L40
            int r0 = r0 + 1
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r5 = r2.rows
            int r5 = r5.size()
            if (r0 >= r5) goto L30
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r5 = r2.rows
            java.lang.Object r5 = r5.get(r0)
            org.telegram.ui.iv.BlockRow r5 = (org.telegram.p035ui.p036iv.BlockRow) r5
            org.telegram.tgnet.tl.TL_iv$PageBlock r5 = r5.block
            boolean r5 = isNonText(r5)
            if (r5 == 0) goto L8a
        L30:
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r5 = r2.rows
            org.telegram.ui.iv.BlockRow r6 = new org.telegram.ui.iv.BlockRow
            org.telegram.tgnet.tl.TL_iv$pageBlockParagraph r1 = new org.telegram.tgnet.tl.TL_iv$pageBlockParagraph
            r1.<init>()
            r6.<init>(r1)
            r5.add(r0, r6)
            goto L8a
        L40:
            boolean r5 = isNonText(r4)
            if (r5 == 0) goto L8a
            boolean r5 = isMedia(r4)
            if (r5 == 0) goto L57
            org.telegram.ui.iv.MediaUploadState r5 = r3.media
            if (r5 != 0) goto L57
            org.telegram.ui.iv.MediaUploadState r5 = new org.telegram.ui.iv.MediaUploadState
            r5.<init>()
            r3.media = r5
        L57:
            int r0 = r0 + 1
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r5 = r2.rows
            int r5 = r5.size()
            if (r0 >= r5) goto L7a
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r5 = r2.rows
            java.lang.Object r5 = r5.get(r0)
            org.telegram.ui.iv.BlockRow r5 = (org.telegram.p035ui.p036iv.BlockRow) r5
            org.telegram.tgnet.tl.TL_iv$PageBlock r5 = r5.block
            boolean r5 = isNonText(r5)
            if (r5 != 0) goto L7a
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r5 = r2.rows
            java.lang.Object r5 = r5.get(r0)
            org.telegram.ui.iv.BlockRow r5 = (org.telegram.p035ui.p036iv.BlockRow) r5
            goto L8b
        L7a:
            org.telegram.ui.iv.BlockRow r5 = new org.telegram.ui.iv.BlockRow
            org.telegram.tgnet.tl.TL_iv$pageBlockParagraph r6 = new org.telegram.tgnet.tl.TL_iv$pageBlockParagraph
            r6.<init>()
            r5.<init>(r6)
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r6 = r2.rows
            r6.add(r0, r5)
            goto L8b
        L8a:
            r5 = r3
        L8b:
            org.telegram.ui.Components.UniversalRecyclerView r6 = r2.listView
            org.telegram.ui.Components.UniversalAdapter r6 = r6.adapter
            r0 = 0
            r6.update(r0)
            boolean r6 = r4 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockMath
            if (r6 == 0) goto La2
            org.telegram.ui.Components.UniversalRecyclerView r4 = r2.listView
            org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda23 r5 = new org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda23
            r5.<init>()
            r4.post(r5)
            return
        La2:
            boolean r4 = r4 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockMap
            org.telegram.ui.Components.UniversalRecyclerView r6 = r2.listView
            if (r4 == 0) goto Lb1
            org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda24 r4 = new org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda24
            r4.<init>()
            r6.post(r4)
            return
        Lb1:
            org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda25 r3 = new org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda25
            r3.<init>()
            r6.post(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.p036iv.ChatAttachAlertRichLayout.transformRow(org.telegram.ui.iv.BlockRow, org.telegram.tgnet.tl.TL_iv$PageBlock, int, int):void");
    }

    public /* synthetic */ void lambda$transformRow$9(BlockRow blockRow) {
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject instanceof RichTextCell) {
            RichTextCell richTextCell = (RichTextCell) viewFindViewByItemObject;
            richTextCell.requestEditFocus();
            richTextCell.getEditText().setSelection(richTextCell.getEditText().length());
            return;
        }
        if (viewFindViewByItemObject instanceof RichTableCell) {
            RichTableCell richTableCell = (RichTableCell) viewFindViewByItemObject;
            if (richTableCell.getGrid().getChildCount() > 0) {
                View childAt = richTableCell.getGrid().getChildAt(0);
                if (childAt instanceof RichTableCellHost) {
                    ((RichTableCellHost) childAt).editText.requestEditFocus();
                }
            }
        }
    }

    private static boolean isNonText(TL_iv.PageBlock pageBlock) {
        return (pageBlock instanceof TL_iv.pageBlockDivider) || (pageBlock instanceof TL_iv.pageBlockPhoto) || (pageBlock instanceof TL_iv.pageBlockVideo) || (pageBlock instanceof TL_iv.pageBlockMath) || (pageBlock instanceof TL_iv.pageBlockMap) || (pageBlock instanceof TL_iv.pageBlockTable);
    }

    private static boolean isMedia(TL_iv.PageBlock pageBlock) {
        return (pageBlock instanceof TL_iv.pageBlockPhoto) || (pageBlock instanceof TL_iv.pageBlockVideo);
    }

    private boolean hasAnyText() {
        MediaUploadState mediaUploadState;
        for (int i = 0; i < this.rows.size(); i++) {
            BlockRow blockRow = this.rows.get(i);
            if (!RichTextCell.readPlainText(blockRow.block).isEmpty()) {
                return true;
            }
            if (isMedia(blockRow.block) && (mediaUploadState = blockRow.media) != null && (mediaUploadState.isReady() || blockRow.media.isPending())) {
                return true;
            }
            TL_iv.PageBlock pageBlock = blockRow.block;
            if ((pageBlock instanceof TL_iv.pageBlockMath) && !TextUtils.isEmpty(((TL_iv.pageBlockMath) pageBlock).source)) {
                return true;
            }
            TL_iv.PageBlock pageBlock2 = blockRow.block;
            if ((pageBlock2 instanceof TL_iv.pageBlockMap) && ((TL_iv.pageBlockMap) pageBlock2).geo != null) {
                return true;
            }
            if ((pageBlock2 instanceof TL_iv.pageBlockTable) && tableHasText((TL_iv.pageBlockTable) pageBlock2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean tableHasText(TL_iv.pageBlockTable pageblocktable) {
        if (pageblocktable.rows == null) {
            return false;
        }
        for (int i = 0; i < pageblocktable.rows.size(); i++) {
            TL_iv.pageTableRow pagetablerow = pageblocktable.rows.get(i);
            for (int i2 = 0; i2 < pagetablerow.cells.size(); i2++) {
                if (!TableModel.readPlainText(pagetablerow.cells.get(i2)).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPendingUploads() {
        for (int i = 0; i < this.rows.size(); i++) {
            MediaUploadState mediaUploadState = this.rows.get(i).media;
            if (mediaUploadState != null && mediaUploadState.isPending()) {
                return true;
            }
        }
        return false;
    }

    public void updateSendButton(boolean z) {
        boolean zHasAnyText = hasAnyText();
        if (zHasAnyText == this.sendButtonShown) {
            return;
        }
        this.sendButtonShown = zHasAnyText;
        this.parentAlert.showSendButtonOnly(zHasAnyText, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillItems(java.util.ArrayList<org.telegram.p035ui.Components.UItem> r5, org.telegram.p035ui.Components.UniversalAdapter r6) {
        /*
            r4 = this;
            r6 = 0
            r0 = r6
        L2:
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r1 = r4.rows
            int r1 = r1.size()
            if (r0 >= r1) goto L8d
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r1 = r4.rows
            java.lang.Object r1 = r1.get(r0)
            org.telegram.ui.iv.BlockRow r1 = (org.telegram.p035ui.p036iv.BlockRow) r1
            org.telegram.tgnet.tl.TL_iv$PageBlock r2 = r1.block
            boolean r3 = r2 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockDivider
            if (r3 == 0) goto L22
            org.telegram.ui.iv.RichDividerCell$Delegate r2 = r4.dividerDelegate
            org.telegram.ui.Components.UItem r1 = org.telegram.ui.iv.RichDividerCell.Factory.m1234of(r1, r2)
            r5.add(r1)
            goto L89
        L22:
            boolean r2 = isMedia(r2)
            if (r2 == 0) goto L32
            org.telegram.ui.iv.RichMediaCell$Delegate r2 = r4.mediaDelegate
            org.telegram.ui.Components.UItem r1 = org.telegram.ui.iv.RichMediaCell.Factory.m1237of(r1, r2)
            r5.add(r1)
            goto L89
        L32:
            org.telegram.tgnet.tl.TL_iv$PageBlock r2 = r1.block
            boolean r3 = r2 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockMap
            if (r3 == 0) goto L42
            org.telegram.ui.iv.RichMapCell$Delegate r2 = r4.mapDelegate
            org.telegram.ui.Components.UItem r1 = org.telegram.ui.iv.RichMapCell.Factory.m1235of(r1, r2)
            r5.add(r1)
            goto L89
        L42:
            boolean r3 = r2 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockMath
            if (r3 == 0) goto L50
            org.telegram.ui.iv.RichMathCell$Delegate r2 = r4.mathDelegate
            org.telegram.ui.Components.UItem r1 = org.telegram.ui.iv.RichMathCell.Factory.m1236of(r1, r2)
            r5.add(r1)
            goto L89
        L50:
            boolean r2 = r2 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockTable
            if (r2 == 0) goto L5e
            org.telegram.ui.iv.RichTableCell$Delegate r2 = r4.tableDelegate
            org.telegram.ui.Components.UItem r1 = org.telegram.ui.iv.RichTableCell.Factory.m1238of(r1, r2)
            r5.add(r1)
            goto L89
        L5e:
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r2 = r4.rows
            int r2 = r2.size()
            r3 = 2
            if (r2 != r3) goto L7f
            r2 = 1
            if (r0 != r2) goto L7f
            org.telegram.tgnet.tl.TL_iv$PageBlock r3 = r1.block
            boolean r3 = r3 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockParagraph
            if (r3 == 0) goto L7f
            java.util.ArrayList<org.telegram.ui.iv.BlockRow> r3 = r4.rows
            java.lang.Object r3 = r3.get(r6)
            org.telegram.ui.iv.BlockRow r3 = (org.telegram.p035ui.p036iv.BlockRow) r3
            org.telegram.tgnet.tl.TL_iv$PageBlock r3 = r3.block
            boolean r3 = r3 instanceof org.telegram.tgnet.tl.TL_iv.pageBlockHeading1
            if (r3 == 0) goto L7f
            goto L80
        L7f:
            r2 = r6
        L80:
            org.telegram.ui.iv.RichTextCell$Delegate r3 = r4.cellDelegate
            org.telegram.ui.Components.UItem r1 = org.telegram.ui.iv.RichTextCell.Factory.m1239of(r1, r3, r2)
            r5.add(r1)
        L89:
            int r0 = r0 + 1
            goto L2
        L8d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.p036iv.ChatAttachAlertRichLayout.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    public void onItemClick(UItem uItem, View view, int i, float f, float f2) {
        if (view instanceof RichTextCell) {
            ((RichTextCell) view).requestEditFocus();
        }
    }

    public void onCellEnter(final BlockRow blockRow) {
        int iIndexOf = this.rows.indexOf(blockRow);
        if (iIndexOf < 0) {
            return;
        }
        if (RichTextCell.readPlainText(blockRow.block).isEmpty() && blockRow.level > 0) {
            cascadeOutdent(iIndexOf);
            renumberAllRuns();
            this.listView.adapter.update(false);
            this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCellEnter$10(blockRow);
                }
            });
            return;
        }
        TL_iv.pageBlockParagraph pageblockparagraph = new TL_iv.pageBlockParagraph();
        int i = blockRow.num;
        if (i > 0) {
            i++;
        }
        final BlockRow blockRow2 = new BlockRow(pageblockparagraph, blockRow.level, i);
        this.rows.add(iIndexOf + 1, blockRow2);
        renumberAllRuns();
        this.listView.adapter.update(true);
        this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCellEnter$11(blockRow2);
            }
        });
    }

    public void onCellBackspace(final BlockRow blockRow) {
        int iIndexOf = this.rows.indexOf(blockRow);
        if (iIndexOf < 0) {
            return;
        }
        if (blockRow.level > 0) {
            cascadeOutdent(iIndexOf);
            renumberAllRuns();
            this.listView.adapter.update(false);
            this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCellBackspace$12(blockRow);
                }
            });
            return;
        }
        if (iIndexOf <= 0) {
            return;
        }
        final BlockRow blockRow2 = this.rows.get(iIndexOf - 1);
        this.rows.remove(iIndexOf);
        renumberAllRuns();
        this.listView.adapter.update(true);
        this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCellBackspace$13(blockRow2);
            }
        });
    }

    public boolean onCellIndent(BlockRow blockRow, boolean z) {
        int iIndexOf = this.rows.indexOf(blockRow);
        StringBuilder sb = new StringBuilder("onCellIndent idx=");
        sb.append(iIndexOf);
        sb.append(" outdent=");
        sb.append(z);
        sb.append(" level=");
        sb.append(blockRow != null ? blockRow.level : -99);
        sb.append(" num=");
        sb.append(blockRow != null ? blockRow.num : -99);
        Log.d("RICHED", sb.toString());
        if (iIndexOf < 0) {
            return false;
        }
        int iCaptureCaret = captureCaret(blockRow);
        if (!indentRow(iIndexOf, z, false)) {
            return false;
        }
        renumberAllRuns();
        this.listView.adapter.update(false);
        restoreCaret(blockRow, iCaptureCaret);
        return true;
    }

    private boolean rangeIndent(int i, int i2, boolean z) {
        boolean z2;
        if (i < 0 || i2 < i || i2 >= this.rows.size()) {
            return false;
        }
        Log.d("RICHED", "rangeIndent sCell=" + i + " eCell=" + i2 + " outdent=" + z);
        if (z) {
            z2 = false;
            while (i2 >= i) {
                if (indentRow(i2, true, true)) {
                    z2 = true;
                }
                i2--;
            }
        } else {
            BlockRow blockRow = this.rows.get(i);
            if (blockRow.level >= 1 && (i == 0 || this.rows.get(i - 1).level < blockRow.level)) {
                Log.d("RICHED", "  rangeIndent reject: orphan on first row");
                return false;
            }
            z2 = false;
            while (i <= i2) {
                if (indentRow(i, false, true)) {
                    z2 = true;
                }
                i++;
            }
        }
        if (z2) {
            renumberAllRuns();
            this.listView.adapter.update(false);
        }
        return z2;
    }

    private boolean indentRow(int i, boolean z, boolean z2) {
        if (i < 0 || i >= this.rows.size()) {
            return false;
        }
        BlockRow blockRow = this.rows.get(i);
        if (z) {
            if (blockRow.level <= 0) {
                return false;
            }
            cascadeOutdent(i);
            return true;
        }
        if (blockRow.level == 0) {
            if (!(blockRow.block instanceof TL_iv.pageBlockParagraph)) {
                return false;
            }
            blockRow.level = 1;
            if (i > 0) {
                blockRow.num = this.rows.get(i - 1).num > 0 ? 1 : 0;
            } else {
                blockRow.num = 0;
            }
            return true;
        }
        if (!z2 && (i == 0 || this.rows.get(i - 1).level < blockRow.level)) {
            return false;
        }
        blockRow.level++;
        return true;
    }

    private void cascadeOutdent(int i) {
        BlockRow blockRow;
        int i2;
        BlockRow blockRow2 = this.rows.get(i);
        int i3 = blockRow2.level;
        if (i3 <= 0) {
            return;
        }
        int i4 = i3 - 1;
        blockRow2.level = i4;
        if (i4 == 0) {
            blockRow2.num = 0;
        }
        while (true) {
            i++;
            if (i >= this.rows.size() || (i2 = (blockRow = this.rows.get(i)).level) <= i3) {
                return;
            } else {
                blockRow.level = i2 - 1;
            }
        }
    }

    private int captureCaret(BlockRow blockRow) {
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (!(viewFindViewByItemObject instanceof RichTextCell)) {
            return -1;
        }
        RichTextCell richTextCell = (RichTextCell) viewFindViewByItemObject;
        if (richTextCell.getEditText().isFocused()) {
            return richTextCell.getEditText().getSelectionEnd();
        }
        return -1;
    }

    private void restoreCaret(final BlockRow blockRow, final int i) {
        if (i < 0) {
            return;
        }
        this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$restoreCaret$14(blockRow, i);
            }
        });
    }

    public /* synthetic */ void lambda$restoreCaret$14(BlockRow blockRow, int i) {
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject instanceof RichTextCell) {
            RichTextCell richTextCell = (RichTextCell) viewFindViewByItemObject;
            richTextCell.requestEditFocus();
            richTextCell.getEditText().setSelection(Math.max(0, Math.min(i, richTextCell.getEditText().length())));
        }
    }

    /* JADX INFO: renamed from: focusRow, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$tryPlainArrowAcrossCells$17(BlockRow blockRow) {
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject instanceof RichTextCell) {
            ((RichTextCell) viewFindViewByItemObject).requestEditFocus();
        }
    }

    public void restoreFocusAt(int i, int i2, int i3) {
        RichTableCell richTableCell;
        TableModel model;
        if (i < 0) {
            return;
        }
        View viewFindViewByPosition = this.listView.layoutManager.findViewByPosition(i);
        if (viewFindViewByPosition instanceof RichTextCell) {
            RichTextCell richTextCell = (RichTextCell) viewFindViewByPosition;
            richTextCell.requestEditFocus();
            richTextCell.getEditText().setSelection(Math.max(0, Math.min(i3, richTextCell.getEditText().length())));
        } else {
            if (!(viewFindViewByPosition instanceof RichTableCell) || (model = (richTableCell = (RichTableCell) viewFindViewByPosition).getModel()) == null) {
                return;
            }
            if (i2 < 0 || i2 >= model.anchors().size()) {
                i2 = 0;
            }
            RichTableCellHost richTableCellHostHostForAnchor = richTableCell.getGrid().hostForAnchor(model.anchors().get(i2));
            if (richTableCellHostHostForAnchor == null) {
                return;
            }
            richTableCellHostHostForAnchor.editText.requestEditFocus();
            richTableCellHostHostForAnchor.editText.setSelection(Math.max(0, Math.min(i3, richTableCellHostHostForAnchor.editText.length())));
        }
    }

    private void renumberAllRuns() {
        for (int i = 0; i < this.rows.size(); i++) {
            BlockRow blockRow = this.rows.get(i);
            int i2 = blockRow.level;
            if (i2 > 0 && blockRow.num > 0) {
                int i3 = 1;
                for (int i4 = i - 1; i4 >= 0; i4--) {
                    BlockRow blockRow2 = this.rows.get(i4);
                    int i5 = blockRow2.level;
                    if (i5 < i2) {
                        break;
                    }
                    if (i5 == i2) {
                        if (blockRow2.num <= 0) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
                blockRow.num = i3;
            }
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getListTopPadding() {
        return this.listView.getPaddingTop();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCurrentItemTop() {
        if (this.listView.getChildCount() <= 0) {
            UniversalRecyclerView universalRecyclerView = this.listView;
            int paddingTop = universalRecyclerView.getPaddingTop();
            this.currentItemTop = paddingTop;
            universalRecyclerView.setTopGlowOffset(paddingTop);
            return Integer.MAX_VALUE;
        }
        boolean z = false;
        int y = Integer.MAX_VALUE;
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            int childAdapterPosition = this.listView.getChildAdapterPosition(childAt);
            if (childAdapterPosition == 0) {
                z = true;
            }
            if (childAdapterPosition >= 0 && childAt.getTop() < y) {
                y = (int) childAt.getY();
            }
        }
        if (y == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        int iM1036dp = AndroidUtilities.m1036dp(7.0f);
        if (y < AndroidUtilities.m1036dp(7.0f) || !z) {
            y = iM1036dp;
        }
        this.listView.setTopGlowOffset(y);
        this.currentItemTop = y;
        return y;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.m1036dp(56.0f);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.ignoreLayout) {
            return;
        }
        super.requestLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x003e  */
    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPreMeasure(int r5, int r6) {
        /*
            r4 = this;
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r5 = (android.widget.FrameLayout.LayoutParams) r5
            int r0 = org.telegram.p035ui.ActionBar.ActionBar.getCurrentActionBarHeight()
            r5.topMargin = r0
            org.telegram.ui.Components.ChatAttachAlert r5 = r4.parentAlert
            org.telegram.ui.Components.SizeNotifierFrameLayout r5 = r5.sizeNotifierFrameLayout
            int r5 = r5.measureKeyboardHeight()
            r0 = 1101004800(0x41a00000, float:20.0)
            int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
            r1 = 1
            r2 = 1112539136(0x42500000, float:52.0)
            r3 = 0
            if (r5 <= r0) goto L2a
            int r5 = org.telegram.messenger.AndroidUtilities.m1036dp(r2)
            org.telegram.ui.Components.ChatAttachAlert r6 = r4.parentAlert
            r6.setAllowNestedScroll(r3)
            goto L4f
        L2a:
            boolean r5 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r5 != 0) goto L3e
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r5.x
            int r5 = r5.y
            if (r0 <= r5) goto L3e
            float r5 = (float) r6
            r6 = 1080033280(0x40600000, float:3.5)
            float r5 = r5 / r6
            int r5 = (int) r5
            goto L42
        L3e:
            int r6 = r6 / 5
            int r5 = r6 * 2
        L42:
            int r6 = org.telegram.messenger.AndroidUtilities.m1036dp(r2)
            int r5 = r5 - r6
            if (r5 >= 0) goto L4a
            r5 = r3
        L4a:
            org.telegram.ui.Components.ChatAttachAlert r6 = r4.parentAlert
            r6.setAllowNestedScroll(r1)
        L4f:
            org.telegram.ui.Components.UniversalRecyclerView r6 = r4.listView
            int r6 = r6.getPaddingTop()
            if (r6 != r5) goto L63
            org.telegram.ui.Components.UniversalRecyclerView r6 = r4.listView
            int r6 = r6.getPaddingBottom()
            int r0 = r4.listPaddingBottom
            if (r6 == r0) goto L62
            goto L63
        L62:
            return
        L63:
            r4.ignoreLayout = r1
            org.telegram.ui.Components.UniversalRecyclerView r6 = r4.listView
            int r0 = r4.listPaddingBottom
            r6.setPaddingWithoutRequestLayout(r3, r5, r3, r0)
            r4.ignoreLayout = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.p036iv.ChatAttachAlertRichLayout.onPreMeasure(int, int):void");
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void scrollToTop() {
        this.listView.smoothScrollToPosition(0);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        RichTableCell richTableCell;
        if (this.textSelectionHelper.isInSelectionMode() && this.textSelectionOverlay.onTouchEvent(motionEvent)) {
            return true;
        }
        if (this.textSelectionOverlay.checkOnTap(motionEvent)) {
            motionEvent.setAction(3);
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.pressX = motionEvent.getX();
            float y = motionEvent.getY();
            this.pressY = y;
            this.pressMoved = false;
            this.longPressConsumed = false;
            View viewFindCellUnder = findCellUnder((int) this.pressX, (int) y);
            this.pressTarget = viewFindCellUnder;
            if (viewFindCellUnder instanceof TextSelectionHelper.ArticleSelectableView) {
                Runnable runnable = this.longPressRunnable;
                if (runnable != null) {
                    removeCallbacks(runnable);
                }
                Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$dispatchTouchEvent$15();
                    }
                };
                this.longPressRunnable = runnable2;
                postDelayed(runnable2, ViewConfiguration.getLongPressTimeout());
            }
        } else if (action == 1) {
            Runnable runnable3 = this.longPressRunnable;
            if (runnable3 != null) {
                removeCallbacks(runnable3);
                this.longPressRunnable = null;
            }
            if (!this.pressMoved && !this.longPressConsumed && (richTableCell = this.activeCellSelectionTable) != null) {
                View view = this.pressTarget;
                if (view == richTableCell) {
                    RichTableCell richTableCell2 = (RichTableCell) view;
                    TL_iv.pageTableCell pagetablecellFindCellAt = richTableCell2.findCellAt((int) ((motionEvent.getX() - richTableCell2.getLeft()) - this.listView.getLeft()), (int) ((motionEvent.getY() - richTableCell2.getTop()) - this.listView.getTop()));
                    if (pagetablecellFindCellAt != null) {
                        richTableCell2.toggleCellSelection(pagetablecellFindCellAt);
                        this.pressTarget = null;
                        return true;
                    }
                } else if (view != null && view != richTableCell) {
                    exitCellSelectionMode();
                }
            }
            this.pressTarget = null;
            this.longPressConsumed = false;
        } else if (action == 2) {
            float x = motionEvent.getX() - this.pressX;
            float y2 = motionEvent.getY() - this.pressY;
            if ((x * x) + (y2 * y2) > AndroidUtilities.m1036dp(8.0f) * AndroidUtilities.m1036dp(8.0f)) {
                this.pressMoved = true;
                Runnable runnable4 = this.longPressRunnable;
                if (runnable4 != null) {
                    removeCallbacks(runnable4);
                    this.longPressRunnable = null;
                }
            }
        } else if (action == 3) {
            Runnable runnable5 = this.longPressRunnable;
            if (runnable5 != null) {
                removeCallbacks(runnable5);
                this.longPressRunnable = null;
            }
            this.pressTarget = null;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public /* synthetic */ void lambda$dispatchTouchEvent$15() {
        if (this.pressTarget == null || this.textSelectionHelper.isInSelectionMode()) {
            return;
        }
        View view = this.pressTarget;
        if (view instanceof RichTextCell) {
            RichTextCell richTextCell = (RichTextCell) view;
            this.textSelectionHelper.setMaybeView((int) ((this.pressX - richTextCell.getLeft()) - this.listView.getLeft()), (int) ((this.pressY - richTextCell.getTop()) - this.listView.getTop()), richTextCell);
            this.textSelectionHelper.trySelect(richTextCell);
            this.longPressConsumed = true;
            return;
        }
        if (view instanceof RichTableCell) {
            RichTableCell richTableCell = (RichTableCell) view;
            int left = (int) ((this.pressX - richTableCell.getLeft()) - this.listView.getLeft());
            int top = (int) ((this.pressY - richTableCell.getTop()) - this.listView.getTop());
            if (richTableCell.isPressOnText(left, top)) {
                this.textSelectionHelper.setMaybeView(left, top, richTableCell);
                this.textSelectionHelper.trySelect(richTableCell);
            } else {
                TL_iv.pageTableCell pagetablecellFindCellAt = richTableCell.findCellAt(left, top);
                if (pagetablecellFindCellAt != null) {
                    enterCellSelectionMode(richTableCell, pagetablecellFindCellAt);
                }
            }
            this.longPressConsumed = true;
            return;
        }
        if (view instanceof RichDividerCell) {
            this.textSelectionHelper.selectRangeOf((RichDividerCell) view, 0, 1);
            this.longPressConsumed = true;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        RichTableCell richTableCellFindTableCellAncestor;
        int unicodeChar;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            boolean zIsShiftPressed = keyEvent.isShiftPressed();
            boolean zIsCtrlPressed = keyEvent.isCtrlPressed();
            boolean zIsAltPressed = keyEvent.isAltPressed();
            boolean zIsInSelectionMode = this.textSelectionHelper.isInSelectionMode();
            Log.d("RICHED", "dispatchKeyEvent code=" + keyCode + " shift=" + zIsShiftPressed + " ctrl=" + zIsCtrlPressed + " alt=" + zIsAltPressed + " helperActive=" + zIsInSelectionMode + " isFocused=" + isFocused() + " hasFocus=" + hasFocus());
            if (keyCode == 111 && zIsInSelectionMode) {
                Log.d("RICHED", "ESC -> helper.clear()");
                this.textSelectionHelper.clear();
                return true;
            }
            if (zIsInSelectionMode) {
                if (zIsCtrlPressed && !zIsShiftPressed && keyCode == 31) {
                    Log.d("RICHED", "Ctrl+C copy");
                    copyHelperSelection();
                    return true;
                }
                if (zIsCtrlPressed && !zIsShiftPressed && keyCode == 52) {
                    Log.d("RICHED", "Ctrl+X cut");
                    cutHelperSelection();
                    return true;
                }
                if (zIsCtrlPressed && keyCode == 50) {
                    Log.d("RICHED", "Ctrl+V paste");
                    pasteAtHelperSelection();
                    return true;
                }
                if (keyCode == 67 || keyCode == 112) {
                    Log.d("RICHED", "DEL/BACKSPACE -> deleteHelperSelection");
                    deleteHelperSelection();
                    return true;
                }
                if (keyCode == 66) {
                    Log.d("RICHED", "ENTER -> replace with newline");
                    replaceHelperSelectionWith("\n");
                    return true;
                }
                if (!zIsCtrlPressed && !zIsAltPressed && (unicodeChar = keyEvent.getUnicodeChar(keyEvent.getMetaState())) >= 32) {
                    Log.d("RICHED", "printable uc=" + unicodeChar + " -> replace");
                    replaceHelperSelectionWith(String.valueOf((char) unicodeChar));
                    return true;
                }
            }
            if (zIsInSelectionMode && zIsShiftPressed && isArrowKey(keyCode)) {
                Log.d("RICHED", "shift+arrow code=" + keyCode + " -> tryExtendSelectionAcrossCells");
                if (tryExtendSelectionAcrossCells(keyCode, zIsCtrlPressed || zIsAltPressed)) {
                    return true;
                }
            }
            if (zIsInSelectionMode && !zIsShiftPressed && !zIsCtrlPressed && !zIsAltPressed && isArrowKey(keyCode)) {
                boolean z = keyCode == 22 || keyCode == 20;
                TextSelectionHelper.ArticleTextSelectionHelper articleTextSelectionHelper = this.textSelectionHelper;
                this.restoreFocusCell = z ? articleTextSelectionHelper.getEndCell() : articleTextSelectionHelper.getStartCell();
                TextSelectionHelper.ArticleTextSelectionHelper articleTextSelectionHelper2 = this.textSelectionHelper;
                this.restoreFocusOffset = z ? articleTextSelectionHelper2.getEndOffset() : articleTextSelectionHelper2.getStartOffset();
                TextSelectionHelper.ArticleTextSelectionHelper articleTextSelectionHelper3 = this.textSelectionHelper;
                this.restoreFocusChildPosition = z ? articleTextSelectionHelper3.getEndChildPosition() : articleTextSelectionHelper3.getStartChildPosition();
                Log.d("RICHED", "plain arrow collapse toEnd=" + z + " -> cell=" + this.restoreFocusCell + " child=" + this.restoreFocusChildPosition + " off=" + this.restoreFocusOffset);
                this.textSelectionHelper.clear();
                return true;
            }
            if (zIsCtrlPressed && keyCode == 29) {
                Log.d("RICHED", "ctrl+a -> tryEscalateSelectAll");
                if (tryEscalateSelectAll()) {
                    return true;
                }
            }
            if (!zIsInSelectionMode && !zIsShiftPressed && !zIsCtrlPressed && !zIsAltPressed && ((keyCode == 20 || keyCode == 19) && tryPlainArrowAcrossCells(keyCode))) {
                return true;
            }
            if (!zIsInSelectionMode && !zIsCtrlPressed && !zIsAltPressed && keyCode == 61) {
                View viewFindFocus = findFocus();
                StringBuilder sb = new StringBuilder("outer-pre TAB shift=");
                sb.append(zIsShiftPressed);
                sb.append(" focused=");
                sb.append(viewFindFocus != null ? viewFindFocus.getClass().getSimpleName() : "null");
                Log.d("RICHED", sb.toString());
                if ((viewFindFocus instanceof RichEditText) && (richTableCellFindTableCellAncestor = findTableCellAncestor(viewFindFocus)) != null) {
                    RichTableCellHost richTableCellHostFindHostContaining = richTableCellFindTableCellAncestor.findHostContaining(viewFindFocus);
                    StringBuilder sb2 = new StringBuilder("outer-pre TAB table=");
                    sb2.append(true);
                    sb2.append(" host=");
                    sb2.append(richTableCellHostFindHostContaining != null);
                    Log.d("RICHED", sb2.toString());
                    if (richTableCellHostFindHostContaining != null && richTableCellFindTableCellAncestor.moveFocusByTab(richTableCellHostFindHostContaining, zIsShiftPressed)) {
                        return true;
                    }
                }
            }
            if (keyCode == 61 && isFocused()) {
                if (zIsInSelectionMode) {
                    int startCell = this.textSelectionHelper.getStartCell();
                    int endCell = this.textSelectionHelper.getEndCell();
                    Log.d("RICHED", "outer TAB shift=" + zIsShiftPressed + " helper s=" + startCell + " e=" + endCell);
                    if (startCell >= 0 && endCell >= startCell) {
                        if (startCell == endCell) {
                            onCellIndent(this.rows.get(startCell), zIsShiftPressed);
                        } else {
                            rangeIndent(startCell, endCell, zIsShiftPressed);
                        }
                    }
                } else {
                    BlockRow blockRowFindFocusedRow = findFocusedRow();
                    StringBuilder sb3 = new StringBuilder("outer TAB shift=");
                    sb3.append(zIsShiftPressed);
                    sb3.append(" focused row=");
                    sb3.append(blockRowFindFocusedRow != null ? this.rows.indexOf(blockRowFindFocusedRow) : -1);
                    Log.d("RICHED", sb3.toString());
                    if (blockRowFindFocusedRow != null) {
                        onCellIndent(blockRowFindFocusedRow, zIsShiftPressed);
                    }
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    private boolean tryPlainArrowAcrossCells(int i) {
        int iIndexOf;
        int iFindNextTextRow;
        BlockRow blockRowFindFocusedRow = findFocusedRow();
        if (blockRowFindFocusedRow == null || (iIndexOf = this.rows.indexOf(blockRowFindFocusedRow)) < 0) {
            return false;
        }
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRowFindFocusedRow);
        if (!(viewFindViewByItemObject instanceof RichTextCell)) {
            return false;
        }
        RichTextCell richTextCell = (RichTextCell) viewFindViewByItemObject;
        Layout layout = richTextCell.getEditText().getLayout();
        if (layout == null) {
            return false;
        }
        int lineForOffset = layout.getLineForOffset(richTextCell.getEditText().getSelectionEnd());
        if (i != 20) {
            if (lineForOffset > 0 || (iFindNextTextRow = findNextTextRow(iIndexOf - 1, -1)) < 0) {
                return false;
            }
            final BlockRow blockRow = this.rows.get(iFindNextTextRow);
            this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$tryPlainArrowAcrossCells$18(blockRow);
                }
            });
            return true;
        }
        if (lineForOffset < layout.getLineCount() - 1) {
            return false;
        }
        int iFindNextTextRow2 = findNextTextRow(iIndexOf + 1, 1);
        if (iFindNextTextRow2 < 0) {
            final BlockRow blockRow2 = new BlockRow(new TL_iv.pageBlockParagraph());
            this.rows.add(blockRow2);
            this.listView.adapter.update(true);
            this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$tryPlainArrowAcrossCells$16(blockRow2);
                }
            });
        } else {
            final BlockRow blockRow3 = this.rows.get(iFindNextTextRow2);
            this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$tryPlainArrowAcrossCells$17(blockRow3);
                }
            });
        }
        return true;
    }

    public /* synthetic */ void lambda$tryPlainArrowAcrossCells$18(BlockRow blockRow) {
        lambda$tryPlainArrowAcrossCells$17(blockRow);
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject instanceof RichTextCell) {
            RichEditText editText = ((RichTextCell) viewFindViewByItemObject).getEditText();
            editText.setSelection(editText.length());
        }
    }

    private RichTableCell findTableCellAncestor(View view) {
        for (ViewParent parent = view == null ? null : view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof RichTableCell) {
                return (RichTableCell) parent;
            }
        }
        return null;
    }

    private int findNextTextRow(int i, int i2) {
        while (i >= 0 && i < this.rows.size()) {
            if (!isNonText(this.rows.get(i).block)) {
                return i;
            }
            i += i2;
        }
        return -1;
    }

    private BlockRow findFocusedRow() {
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof RichTextCell) {
                RichTextCell richTextCell = (RichTextCell) childAt;
                if (richTextCell.getEditText().isFocused()) {
                    return richTextCell.getRow();
                }
            }
        }
        return null;
    }

    private RichTextCell cellAt(int i) {
        if (i < 0) {
            return null;
        }
        View viewFindViewByPosition = this.listView.layoutManager.findViewByPosition(i);
        if (viewFindViewByPosition instanceof RichTextCell) {
            return (RichTextCell) viewFindViewByPosition;
        }
        return null;
    }

    private View selectableAt(int i) {
        if (i < 0) {
            return null;
        }
        return this.listView.layoutManager.findViewByPosition(i);
    }

    private int prevTextOffset(int i) {
        Layout layout;
        View viewSelectableAt = selectableAt(i);
        if (!(viewSelectableAt instanceof RichTextCell) || (layout = ((RichTextCell) viewSelectableAt).getEditText().getLayout()) == null) {
            return 0;
        }
        return layout.getText().length();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0132, code lost:
    
        if (r2 < r16.rows.size()) goto L227;
     */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0256 A[PHI: r1 r3
  0x0256: PHI (r1v5 int) = (r1v2 int), (r1v3 int), (r1v8 int), (r1v11 int), (r1v15 int) binds: [B:298:0x02b3, B:294:0x02ac, B:283:0x028e, B:275:0x0274, B:269:0x0252] A[DONT_GENERATE, DONT_INLINE]
  0x0256: PHI (r3v4 int) = (r3v3 int), (r3v2 int), (r3v7 int), (r3v2 int), (r3v14 int) binds: [B:298:0x02b3, B:294:0x02ac, B:283:0x028e, B:275:0x0274, B:269:0x0252] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean tryExtendSelectionAcrossCells(int r17, boolean r18) {
        /*
            Method dump skipped, instruction units count: 828
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.p036iv.ChatAttachAlertRichLayout.tryExtendSelectionAcrossCells(int, boolean):boolean");
    }

    public /* synthetic */ void lambda$tryExtendSelectionAcrossCells$19(int i, int i2, int i3) {
        KeyEvent.Callback callbackSelectableAt = selectableAt(i);
        if (callbackSelectableAt instanceof TextSelectionHelper.ArticleSelectableView) {
            this.textSelectionHelper.extendSelectionTo((TextSelectionHelper.ArticleSelectableView) callbackSelectableAt, i2, i3);
        }
    }

    private int findTableAnchorBelow(TableModel tableModel, int i) {
        if (i < 0 || i >= tableModel.anchors().size()) {
            return -1;
        }
        TL_iv.pageTableCell pagetablecell = tableModel.anchors().get(i);
        int iAnchorRowOf = tableModel.anchorRowOf(pagetablecell);
        int iAnchorColOf = tableModel.anchorColOf(pagetablecell);
        int iMax = iAnchorRowOf + Math.max(1, TableModel.spanRow(pagetablecell));
        if (iMax >= tableModel.rowCount) {
            return -1;
        }
        return tableModel.flatIndexOfAnchor(tableModel.grid[iMax][Math.min(iAnchorColOf, tableModel.colCount - 1)]);
    }

    private int findTableAnchorAbove(TableModel tableModel, int i) {
        if (i < 0 || i >= tableModel.anchors().size()) {
            return -1;
        }
        TL_iv.pageTableCell pagetablecell = tableModel.anchors().get(i);
        int iAnchorRowOf = tableModel.anchorRowOf(pagetablecell);
        int iAnchorColOf = tableModel.anchorColOf(pagetablecell);
        int i2 = iAnchorRowOf - 1;
        if (i2 < 0) {
            return -1;
        }
        return tableModel.flatIndexOfAnchor(tableModel.grid[i2][Math.min(iAnchorColOf, tableModel.colCount - 1)]);
    }

    private static int wordRight(CharSequence charSequence, int i) {
        BreakIterator wordInstance = BreakIterator.getWordInstance();
        wordInstance.setText(charSequence.toString());
        int iFollowing = wordInstance.following(Math.min(i, charSequence.length()));
        return iFollowing == -1 ? charSequence.length() : iFollowing;
    }

    private static int wordLeft(CharSequence charSequence, int i) {
        BreakIterator wordInstance = BreakIterator.getWordInstance();
        wordInstance.setText(charSequence.toString());
        int iPreceding = wordInstance.preceding(Math.max(0, Math.min(i, charSequence.length())));
        if (iPreceding == -1) {
            return 0;
        }
        return iPreceding;
    }

    private boolean tryEscalateSelectAll() {
        if (this.rows.isEmpty()) {
            return false;
        }
        int i = 0;
        while (true) {
            int size = this.rows.size();
            TextSelectionHelper.ArticleTextSelectionHelper articleTextSelectionHelper = this.textSelectionHelper;
            if (i < size) {
                articleTextSelectionHelper.cacheText(i, RichTextCell.readPlainText(this.rows.get(i).block), null);
                i++;
            } else {
                articleTextSelectionHelper.selectAllBlocksRange(0, this.rows.size() - 1);
                Log.d("RICHED", "selectAllBlocksRange [0.." + (this.rows.size() - 1) + "] done");
                return true;
            }
        }
    }

    private void copyHelperSelection() {
        CharSequence selectedTextPublic = this.textSelectionHelper.getSelectedTextPublic();
        if (selectedTextPublic == null || selectedTextPublic.length() == 0) {
            return;
        }
        AndroidUtilities.addToClipboard(selectedTextPublic);
    }

    private void cutHelperSelection() {
        CharSequence selectedTextPublic = this.textSelectionHelper.getSelectedTextPublic();
        if (selectedTextPublic != null && selectedTextPublic.length() > 0) {
            AndroidUtilities.addToClipboard(selectedTextPublic);
        }
        deleteHelperSelection();
    }

    private void pasteAtHelperSelection() {
        ClipData primaryClip;
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        if (clipboardManager == null || !clipboardManager.hasPrimaryClip() || (primaryClip = clipboardManager.getPrimaryClip()) == null || primaryClip.getItemCount() == 0) {
            return;
        }
        CharSequence charSequenceCoerceToText = primaryClip.getItemAt(0).coerceToText(getContext());
        if (charSequenceCoerceToText == null) {
            charSequenceCoerceToText = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        applyEditRange(this.textSelectionHelper.getStartCell(), this.textSelectionHelper.getStartOffset(), this.textSelectionHelper.getEndCell(), this.textSelectionHelper.getEndOffset(), charSequenceCoerceToText.toString().split("\n", -1));
    }

    private void deleteHelperSelection() {
        applyEditRange(this.textSelectionHelper.getStartCell(), this.textSelectionHelper.getStartOffset(), this.textSelectionHelper.getEndCell(), this.textSelectionHelper.getEndOffset(), new String[]{_UrlKt.FRAGMENT_ENCODE_SET});
    }

    private void replaceHelperSelectionWith(String str) {
        applyEditRange(this.textSelectionHelper.getStartCell(), this.textSelectionHelper.getStartOffset(), this.textSelectionHelper.getEndCell(), this.textSelectionHelper.getEndOffset(), str.split("\n", -1));
    }

    private void applyEditRange(final int i, int i2, int i3, int i4, String[] strArr) {
        final int length;
        if (i < 0 || i3 < 0 || i >= this.rows.size() || i3 >= this.rows.size()) {
            return;
        }
        BlockRow blockRow = this.rows.get(i);
        BlockRow blockRow2 = this.rows.get(i3);
        TL_iv.PageBlock pageBlock = blockRow.block;
        boolean z = pageBlock instanceof TL_iv.pageBlockTable;
        boolean z2 = blockRow2.block instanceof TL_iv.pageBlockTable;
        if (z || z2) {
            if (z && z2 && i == i3) {
                applyEditInsideTable(blockRow, this.textSelectionHelper.getStartChildPosition(), i2, this.textSelectionHelper.getEndChildPosition(), i4, strArr);
                return;
            }
            return;
        }
        String plainText = RichTextCell.readPlainText(pageBlock);
        String plainText2 = i == i3 ? plainText : RichTextCell.readPlainText(blockRow2.block);
        int iMax = Math.max(0, Math.min(i2, plainText.length()));
        int iMax2 = Math.max(0, Math.min(i4, plainText2.length()));
        String strSubstring = plainText.substring(0, iMax);
        String strSubstring2 = plainText2.substring(iMax2);
        if (strArr.length <= 1) {
            String str = strArr.length == 0 ? _UrlKt.FRAGMENT_ENCODE_SET : strArr[0];
            RichTextCell.applyTextToBlock(blockRow.block, strSubstring + str + strSubstring2);
            if (i3 > i) {
                while (i3 > i) {
                    this.rows.remove(i3);
                    i3--;
                }
            }
            length = strSubstring.length() + str.length();
        } else {
            RichTextCell.applyTextToBlock(blockRow.block, strSubstring + strArr[0]);
            if (i3 > i) {
                while (i3 > i) {
                    this.rows.remove(i3);
                    i3--;
                }
            }
            for (int i5 = 1; i5 < strArr.length - 1; i5++) {
                TL_iv.pageBlockParagraph pageblockparagraph = new TL_iv.pageBlockParagraph();
                applyPlainText(pageblockparagraph, strArr[i5]);
                ArrayList<BlockRow> arrayList = this.rows;
                int i6 = i + i5;
                int i7 = blockRow.level;
                int i8 = blockRow.num;
                if (i8 > 0) {
                    i8 += i5;
                }
                arrayList.add(i6, new BlockRow(pageblockparagraph, i7, i8));
            }
            String str2 = strArr[strArr.length - 1];
            TL_iv.pageBlockParagraph pageblockparagraph2 = new TL_iv.pageBlockParagraph();
            applyPlainText(pageblockparagraph2, str2 + strSubstring2);
            ArrayList<BlockRow> arrayList2 = this.rows;
            int length2 = (strArr.length + i) - 1;
            int i9 = blockRow.level;
            int length3 = blockRow.num;
            if (length3 > 0) {
                length3 = (length3 + strArr.length) - 1;
            }
            arrayList2.add(length2, new BlockRow(pageblockparagraph2, i9, length3));
            i = (i + strArr.length) - 1;
            length = str2.length();
        }
        renumberAllRuns();
        this.textSelectionHelper.clear();
        this.listView.adapter.update(true);
        updateSendButton(true);
        this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$applyEditRange$20(i, length);
            }
        });
    }

    public /* synthetic */ void lambda$applyEditRange$20(int i, int i2) {
        RichTextCell richTextCellCellAt = cellAt(i);
        if (richTextCellCellAt != null) {
            richTextCellCellAt.requestEditFocus();
            richTextCellCellAt.getEditText().setSelection(Math.min(i2, richTextCellCellAt.getEditText().length()));
        }
    }

    private static void applyPlainText(TL_iv.PageBlock pageBlock, String str) {
        TL_iv.textPlain textplain = new TL_iv.textPlain();
        textplain.text = str;
        if (pageBlock instanceof TL_iv.pageBlockParagraph) {
            ((TL_iv.pageBlockParagraph) pageBlock).text = textplain;
        }
    }

    private void applyEditInsideTable(BlockRow blockRow, int i, int i2, int i3, int i4, String[] strArr) {
        final RichTableCell richTableCell;
        TableModel model;
        int iMax;
        int length;
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if ((viewFindViewByItemObject instanceof RichTableCell) && (model = (richTableCell = (RichTableCell) viewFindViewByItemObject).getModel()) != null) {
            int size = model.anchors().size();
            if (i < 0 || i >= size || i3 < 0 || i3 >= size) {
                return;
            }
            if (i > i3 || (i == i3 && i2 > i4)) {
                i3 = i;
                i = i3;
                i4 = i2;
                i2 = i4;
            }
            StringBuilder sb = new StringBuilder();
            for (int i5 = 0; i5 < strArr.length; i5++) {
                if (i5 > 0) {
                    sb.append('\n');
                }
                sb.append(strArr[i5]);
            }
            String string = sb.toString();
            final TL_iv.pageTableCell pagetablecell = model.anchors().get(i);
            TL_iv.pageTableCell pagetablecell2 = model.anchors().get(i3);
            if (i == i3) {
                String plainText = TableModel.readPlainText(pagetablecell);
                iMax = Math.max(0, Math.min(i2, plainText.length()));
                String str = plainText.substring(0, iMax) + string + plainText.substring(Math.max(0, Math.min(i4, plainText.length())));
                TableModel.applyPlainText(pagetablecell, str);
                RichTableCellHost richTableCellHostHostForAnchor = richTableCell.getGrid().hostForAnchor(pagetablecell);
                if (richTableCellHostHostForAnchor != null) {
                    richTableCellHostHostForAnchor.editText.setTextSilently(str);
                }
                length = string.length();
            } else {
                String plainText2 = TableModel.readPlainText(pagetablecell);
                iMax = Math.max(0, Math.min(i2, plainText2.length()));
                String strConcat = plainText2.substring(0, iMax).concat(string);
                TableModel.applyPlainText(pagetablecell, strConcat);
                RichTableCellHost richTableCellHostHostForAnchor2 = richTableCell.getGrid().hostForAnchor(pagetablecell);
                if (richTableCellHostHostForAnchor2 != null) {
                    richTableCellHostHostForAnchor2.editText.setTextSilently(strConcat);
                }
                for (int i6 = i + 1; i6 < i3; i6++) {
                    TL_iv.pageTableCell pagetablecell3 = model.anchors().get(i6);
                    TableModel.applyPlainText(pagetablecell3, _UrlKt.FRAGMENT_ENCODE_SET);
                    RichTableCellHost richTableCellHostHostForAnchor3 = richTableCell.getGrid().hostForAnchor(pagetablecell3);
                    if (richTableCellHostHostForAnchor3 != null) {
                        richTableCellHostHostForAnchor3.editText.setTextSilently(_UrlKt.FRAGMENT_ENCODE_SET);
                    }
                }
                String plainText3 = TableModel.readPlainText(pagetablecell2);
                String strSubstring = plainText3.substring(Math.max(0, Math.min(i4, plainText3.length())));
                TableModel.applyPlainText(pagetablecell2, strSubstring);
                RichTableCellHost richTableCellHostHostForAnchor4 = richTableCell.getGrid().hostForAnchor(pagetablecell2);
                if (richTableCellHostHostForAnchor4 != null) {
                    richTableCellHostHostForAnchor4.editText.setTextSilently(strSubstring);
                }
                length = string.length();
            }
            final int i7 = iMax + length;
            this.textSelectionHelper.clear();
            updateSendButton(true);
            this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ChatAttachAlertRichLayout.$r8$lambda$RgEl8wTwN4U_gP8z4o2sOfh_Iag(richTableCell, pagetablecell, i7);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$RgEl8wTwN4U_gP8z4o2sOfh_Iag(RichTableCell richTableCell, TL_iv.pageTableCell pagetablecell, int i) {
        RichTableCellHost richTableCellHostHostForAnchor = richTableCell.getGrid().hostForAnchor(pagetablecell);
        if (richTableCellHostHostForAnchor == null) {
            return;
        }
        richTableCellHostHostForAnchor.editText.requestEditFocus();
        richTableCellHostHostForAnchor.editText.setSelection(Math.max(0, Math.min(i, richTableCellHostHostForAnchor.editText.length())));
    }

    public void setEditTextsLocked(boolean z) {
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof RichTextCell) {
                ((RichTextCell) childAt).setLocked(z);
            } else if (childAt instanceof RichTableCell) {
                ((RichTableCell) childAt).setLocked(z);
            } else if (childAt instanceof RichDividerCell) {
                childAt.invalidate();
            }
        }
    }

    private View findCellUnder(int i, int i2) {
        int top = i2 - this.listView.getTop();
        for (int i3 = 0; i3 < this.listView.getChildCount(); i3++) {
            View childAt = this.listView.getChildAt(i3);
            if (top >= childAt.getTop() && top < childAt.getBottom() && i >= childAt.getLeft() && i < childAt.getRight()) {
                return childAt;
            }
        }
        return null;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        try {
            this.parentAlert.actionBar.getTitleTextView().setBuildFullLayout(true);
        } catch (Exception unused) {
        }
        this.parentAlert.actionBar.setTitle("Article");
        this.listView.adapter.update(false);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onHide() {
        if (this.sendButtonShown) {
            this.sendButtonShown = false;
            this.parentAlert.showSendButtonOnly(false, true);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean sendSelectedItems(boolean z, int i, int i2, long j, boolean z2) {
        long sendMonoForumPeerId;
        MessageObject messageObject;
        MessageObject messageObject2;
        int quickReplyId;
        if (!hasAnyText() || hasPendingUploads()) {
            return false;
        }
        ArrayList<TL_iv.PageBlock> arrayListFlattenRowsToBlocks = flattenRowsToBlocks();
        if (arrayListFlattenRowsToBlocks.isEmpty()) {
            return false;
        }
        ArrayList<TLRPC.InputPhoto> arrayListCollectInputPhotos = collectInputPhotos();
        ArrayList<TLRPC.InputDocument> arrayListCollectInputDocuments = collectInputDocuments();
        BaseFragment baseFragment = this.parentAlert.baseFragment;
        if (baseFragment instanceof ChatActivity) {
            ChatActivity chatActivity = (ChatActivity) baseFragment;
            MessageObject replyMessage = chatActivity.getReplyMessage();
            MessageObject threadMessage = chatActivity.getThreadMessage();
            sendMonoForumPeerId = chatActivity.getSendMonoForumPeerId();
            quickReplyId = chatActivity.getQuickReplyId();
            messageObject = replyMessage;
            messageObject2 = threadMessage;
        } else {
            sendMonoForumPeerId = 0;
            messageObject = null;
            messageObject2 = null;
            quickReplyId = 0;
        }
        SendMessagesHelper.prepareSendingArticle(AccountInstance.getInstance(this.parentAlert.currentAccount), arrayListFlattenRowsToBlocks, arrayListCollectInputPhotos, arrayListCollectInputDocuments, null, false, this.parentAlert.getDialogId(), messageObject, messageObject2, z, i, i2, null, quickReplyId, j, sendMonoForumPeerId, 0L);
        this.parentAlert.dismiss(true);
        return true;
    }

    private ArrayList<TLRPC.InputPhoto> collectInputPhotos() {
        MediaUploadState mediaUploadState;
        TLRPC.Photo photo;
        ArrayList<TLRPC.InputPhoto> arrayList = new ArrayList<>();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < this.rows.size(); i++) {
            BlockRow blockRow = this.rows.get(i);
            if ((blockRow.block instanceof TL_iv.pageBlockPhoto) && (mediaUploadState = blockRow.media) != null && mediaUploadState.isReady() && (photo = blockRow.media.photo) != null && hashSet.add(Long.valueOf(photo.f1276id))) {
                TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
                tL_inputPhoto.f1269id = photo.f1276id;
                tL_inputPhoto.access_hash = photo.access_hash;
                byte[] bArr = photo.file_reference;
                if (bArr == null) {
                    bArr = new byte[0];
                }
                tL_inputPhoto.file_reference = bArr;
                arrayList.add(tL_inputPhoto);
            }
        }
        return arrayList;
    }

    private ArrayList<TLRPC.InputDocument> collectInputDocuments() {
        MediaUploadState mediaUploadState;
        TLRPC.Document document;
        ArrayList<TLRPC.InputDocument> arrayList = new ArrayList<>();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < this.rows.size(); i++) {
            BlockRow blockRow = this.rows.get(i);
            if ((blockRow.block instanceof TL_iv.pageBlockVideo) && (mediaUploadState = blockRow.media) != null && mediaUploadState.isReady() && (document = blockRow.media.document) != null && hashSet.add(Long.valueOf(document.f1253id))) {
                TLRPC.TL_inputDocument tL_inputDocument = new TLRPC.TL_inputDocument();
                tL_inputDocument.f1262id = document.f1253id;
                tL_inputDocument.access_hash = document.access_hash;
                byte[] bArr = document.file_reference;
                if (bArr == null) {
                    bArr = new byte[0];
                }
                tL_inputDocument.file_reference = bArr;
                arrayList.add(tL_inputDocument);
            }
        }
        return arrayList;
    }

    private ArrayList<TL_iv.PageBlock> flattenRowsToBlocks() {
        Log.d("RICHED", "=== flattenRowsToBlocks rows.size=" + this.rows.size());
        for (int i = 0; i < this.rows.size(); i++) {
            BlockRow blockRow = this.rows.get(i);
            Log.d("RICHED", "  row[" + i + "] level=" + blockRow.level + " num=" + blockRow.num + " block=" + blockRow.block.getClass().getSimpleName() + " text='" + RichTextCell.readPlainText(blockRow.block) + "'");
        }
        ArrayList<TL_iv.PageBlock> arrayList = new ArrayList<>();
        int size = 0;
        while (size < this.rows.size()) {
            BlockRow blockRow2 = this.rows.get(size);
            int i2 = blockRow2.level;
            if (i2 <= 0) {
                TL_iv.PageBlock pageBlock = blockRow2.block;
                if (pageBlock instanceof TL_iv.pageBlockDivider) {
                    arrayList.add(pageBlock);
                } else if (pageBlock instanceof TL_iv.pageBlockPhoto) {
                    MediaUploadState mediaUploadState = blockRow2.media;
                    if (mediaUploadState != null && mediaUploadState.isReady()) {
                        TL_iv.PageBlock pageBlock2 = blockRow2.block;
                        if (((TL_iv.pageBlockPhoto) pageBlock2).photo_id != 0) {
                            if (((TL_iv.pageBlockPhoto) pageBlock2).caption == null) {
                                ((TL_iv.pageBlockPhoto) pageBlock2).caption = new TL_iv.PageCaption();
                                ((TL_iv.pageBlockPhoto) blockRow2.block).caption.text = new TL_iv.textEmpty();
                                ((TL_iv.pageBlockPhoto) blockRow2.block).caption.credit = new TL_iv.textEmpty();
                            }
                            arrayList.add(blockRow2.block);
                        }
                    }
                } else if (pageBlock instanceof TL_iv.pageBlockVideo) {
                    MediaUploadState mediaUploadState2 = blockRow2.media;
                    if (mediaUploadState2 != null && mediaUploadState2.isReady()) {
                        TL_iv.PageBlock pageBlock3 = blockRow2.block;
                        if (((TL_iv.pageBlockVideo) pageBlock3).video_id != 0) {
                            if (((TL_iv.pageBlockVideo) pageBlock3).caption == null) {
                                ((TL_iv.pageBlockVideo) pageBlock3).caption = new TL_iv.PageCaption();
                                ((TL_iv.pageBlockVideo) blockRow2.block).caption.text = new TL_iv.textEmpty();
                                ((TL_iv.pageBlockVideo) blockRow2.block).caption.credit = new TL_iv.textEmpty();
                            }
                            arrayList.add(blockRow2.block);
                        }
                    }
                } else if (pageBlock instanceof TL_iv.pageBlockMap) {
                    TL_iv.pageBlockMap pageblockmap = (TL_iv.pageBlockMap) pageBlock;
                    if (pageblockmap.geo != null) {
                        if (pageblockmap.caption == null) {
                            TL_iv.PageCaption pageCaption = new TL_iv.PageCaption();
                            pageblockmap.caption = pageCaption;
                            pageCaption.text = new TL_iv.textEmpty();
                            pageblockmap.caption.credit = new TL_iv.textEmpty();
                        }
                        arrayList.add(pageblockmap);
                    }
                } else if (pageBlock instanceof TL_iv.pageBlockMath) {
                    if (!TextUtils.isEmpty(((TL_iv.pageBlockMath) pageBlock).source)) {
                        arrayList.add(blockRow2.block);
                    }
                } else if (pageBlock instanceof TL_iv.pageBlockTable) {
                    TL_iv.pageBlockTable pageblocktable = (TL_iv.pageBlockTable) pageBlock;
                    TableModel.normalizeForSend(pageblocktable);
                    if (tableHasText(pageblocktable)) {
                        arrayList.add(pageblocktable);
                    }
                } else if (!RichTextCell.readPlainText(pageBlock).isEmpty()) {
                    arrayList.add(blockRow2.block);
                }
                size++;
            } else {
                int[] iArr = {size};
                TL_iv.PageBlock pageBlockBuildListBlock = buildListBlock(size, i2, blockRow2.num > 0, iArr);
                if (pageBlockBuildListBlock != null) {
                    arrayList.add(pageBlockBuildListBlock);
                }
                size = iArr[0];
                if (size <= 0) {
                    size = this.rows.size();
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.telegram.tgnet.tl.TL_iv.PageBlock buildListBlock(int r18, int r19, boolean r20, int[] r21) {
        /*
            Method dump skipped, instruction units count: 517
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.p036iv.ChatAttachAlertRichLayout.buildListBlock(int, int, boolean, int[]):org.telegram.tgnet.tl.TL_iv$PageBlock");
    }

    public void invalidateMediaCellForRow(BlockRow blockRow) {
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject != null) {
            viewFindViewByItemObject.invalidate();
        }
    }

    /* JADX INFO: renamed from: openMathEditor */
    public void lambda$transformRow$7(final BlockRow blockRow) {
        if (blockRow != null) {
            TL_iv.PageBlock pageBlock = blockRow.block;
            if (pageBlock instanceof TL_iv.pageBlockMath) {
                final TL_iv.pageBlockMath pageblockmath = (TL_iv.pageBlockMath) pageBlock;
                Context context = getContext();
                String str = pageblockmath.source;
                if (str == null) {
                    str = _UrlKt.FRAGMENT_ENCODE_SET;
                }
                showEditLatexSheet(context, str, new Utilities.Callback() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda22
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$openMathEditor$22(pageblockmath, blockRow, (String) obj);
                    }
                }, this.resourcesProvider);
            }
        }
    }

    public /* synthetic */ void lambda$openMathEditor$22(TL_iv.pageBlockMath pageblockmath, BlockRow blockRow, String str) {
        pageblockmath.source = str;
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject instanceof RichMathCell) {
            ((RichMathCell) viewFindViewByItemObject).rebuild();
        } else {
            this.listView.adapter.update(false);
        }
        updateSendButton(true);
    }

    /* JADX INFO: renamed from: openLocationPicker */
    public void lambda$transformRow$8(final BlockRow blockRow) {
        BaseFragment baseFragment = this.parentAlert.baseFragment;
        if (baseFragment != null && blockRow != null && (blockRow.block instanceof TL_iv.pageBlockMap) && AndroidUtilities.isMapsInstalled(baseFragment)) {
            final ChatAttachAlert chatAttachAlert = new ChatAttachAlert(getContext(), this.parentAlert.baseFragment, false, false, false, null);
            chatAttachAlert.setDelegate(new ChatAttachAlert.ChatAttachViewDelegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.10
                @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
                public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
                }

                public C750510() {
                }
            });
            chatAttachAlert.setLocationPicker();
            chatAttachAlert.setLocationActivityDelegate(new ChatAttachAlertLocationLayout.LocationActivityDelegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda17
                @Override // org.telegram.ui.Components.ChatAttachAlertLocationLayout.LocationActivityDelegate
                public final void didSelectLocation(TLRPC.MessageMedia messageMedia, int i, boolean z, int i2, long j) {
                    this.f$0.lambda$openLocationPicker$24(blockRow, chatAttachAlert, messageMedia, i, z, i2, j);
                }
            });
            chatAttachAlert.init();
            chatAttachAlert.show();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$10 */
    public class C750510 implements ChatAttachAlert.ChatAttachViewDelegate {
        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
        }

        public C750510() {
        }
    }

    public /* synthetic */ void lambda$openLocationPicker$24(final BlockRow blockRow, ChatAttachAlert chatAttachAlert, TLRPC.MessageMedia messageMedia, int i, boolean z, int i2, long j) {
        TLRPC.GeoPoint geoPoint;
        if (messageMedia == null || (geoPoint = messageMedia.geo) == null) {
            return;
        }
        TL_iv.pageBlockMap pageblockmap = (TL_iv.pageBlockMap) blockRow.block;
        pageblockmap.geo = geoPoint;
        pageblockmap.zoom = 15;
        if (pageblockmap.f1436w <= 0 || pageblockmap.f1435h <= 0) {
            pageblockmap.f1436w = 600;
            pageblockmap.f1435h = 400;
        }
        updateSendButton(true);
        chatAttachAlert.dismiss(true);
        this.listView.post(new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openLocationPicker$23(blockRow);
            }
        });
    }

    public /* synthetic */ void lambda$openLocationPicker$23(BlockRow blockRow) {
        View viewFindViewByItemObject = this.listView.findViewByItemObject(blockRow);
        if (viewFindViewByItemObject instanceof RichMapCell) {
            ((RichMapCell) viewFindViewByItemObject).bind(blockRow, this.mapDelegate);
        } else {
            this.listView.adapter.update(false);
        }
    }

    public void openMediaPicker(BlockRow blockRow) {
        if (this.parentAlert.baseFragment == null) {
            return;
        }
        ChatAttachAlert chatAttachAlert = new ChatAttachAlert(getContext(), this.parentAlert.baseFragment, false, false, false, null);
        chatAttachAlert.setMaxSelectedPhotos(1, false);
        chatAttachAlert.setStoryMediaPicker();
        chatAttachAlert.getPhotoLayout().loadGalleryPhotos();
        chatAttachAlert.setDelegate(new ChatAttachAlert.ChatAttachViewDelegate() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.11
            final /* synthetic */ ChatAttachAlert val$pickerAlert;
            final /* synthetic */ BlockRow val$row;

            public C750611(ChatAttachAlert chatAttachAlert2, BlockRow blockRow2) {
                chatAttachAlert = chatAttachAlert2;
                blockRow = blockRow2;
            }

            @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
            public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
                String str;
                HashMap<Object, Object> selectedPhotos = chatAttachAlert.getPhotoLayout().getSelectedPhotos();
                ArrayList<Object> selectedPhotosOrder = chatAttachAlert.getPhotoLayout().getSelectedPhotosOrder();
                if (!selectedPhotos.isEmpty() && !selectedPhotosOrder.isEmpty()) {
                    Object obj = selectedPhotos.get(selectedPhotosOrder.get(0));
                    if (obj instanceof MediaController.PhotoEntry) {
                        MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                        StringBuilder sb = new StringBuilder("picker.didPressed isVideo=");
                        sb.append(photoEntry.isVideo);
                        sb.append(" path=");
                        sb.append(photoEntry.path);
                        sb.append(" imagePath=");
                        sb.append(photoEntry.imagePath);
                        sb.append(" thumbPath=");
                        sb.append(photoEntry.thumbPath);
                        sb.append(" w=");
                        sb.append(photoEntry.width);
                        sb.append(" h=");
                        sb.append(photoEntry.height);
                        sb.append(" dur=");
                        sb.append(photoEntry.duration);
                        sb.append(" editedInfo=");
                        sb.append(photoEntry.editedInfo != null);
                        Log.d("RICHED", sb.toString());
                        if (photoEntry.isVideo || (str = photoEntry.imagePath) == null) {
                            str = photoEntry.path;
                        }
                        String str2 = str;
                        if (str2 != null) {
                            Log.d("RICHED", "  startMediaUpload uploadPath=" + str2 + " imageId=" + photoEntry.imageId);
                            boolean z5 = photoEntry.isVideo;
                            ChatAttachAlertRichLayout chatAttachAlertRichLayout = ChatAttachAlertRichLayout.this;
                            if (z5) {
                                chatAttachAlertRichLayout.startMediaUpload(blockRow, str2, photoEntry.thumbPath, photoEntry.imageId, true, photoEntry.width, photoEntry.height, photoEntry.duration);
                            } else {
                                chatAttachAlertRichLayout.startMediaUpload(blockRow, str2, photoEntry.thumbPath, photoEntry.imageId, false, 0, 0, 0);
                            }
                        }
                    }
                }
                chatAttachAlert.dismiss(true);
            }
        });
        chatAttachAlert2.init();
        chatAttachAlert2.show();
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$11 */
    public class C750611 implements ChatAttachAlert.ChatAttachViewDelegate {
        final /* synthetic */ ChatAttachAlert val$pickerAlert;
        final /* synthetic */ BlockRow val$row;

        public C750611(ChatAttachAlert chatAttachAlert2, BlockRow blockRow2) {
            chatAttachAlert = chatAttachAlert2;
            blockRow = blockRow2;
        }

        @Override // org.telegram.ui.Components.ChatAttachAlert.ChatAttachViewDelegate
        public void didPressedButton(int i, boolean z, boolean z2, int i2, int i3, long j, boolean z3, boolean z4, long j2) {
            String str;
            HashMap<Object, Object> selectedPhotos = chatAttachAlert.getPhotoLayout().getSelectedPhotos();
            ArrayList<Object> selectedPhotosOrder = chatAttachAlert.getPhotoLayout().getSelectedPhotosOrder();
            if (!selectedPhotos.isEmpty() && !selectedPhotosOrder.isEmpty()) {
                Object obj = selectedPhotos.get(selectedPhotosOrder.get(0));
                if (obj instanceof MediaController.PhotoEntry) {
                    MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                    StringBuilder sb = new StringBuilder("picker.didPressed isVideo=");
                    sb.append(photoEntry.isVideo);
                    sb.append(" path=");
                    sb.append(photoEntry.path);
                    sb.append(" imagePath=");
                    sb.append(photoEntry.imagePath);
                    sb.append(" thumbPath=");
                    sb.append(photoEntry.thumbPath);
                    sb.append(" w=");
                    sb.append(photoEntry.width);
                    sb.append(" h=");
                    sb.append(photoEntry.height);
                    sb.append(" dur=");
                    sb.append(photoEntry.duration);
                    sb.append(" editedInfo=");
                    sb.append(photoEntry.editedInfo != null);
                    Log.d("RICHED", sb.toString());
                    if (photoEntry.isVideo || (str = photoEntry.imagePath) == null) {
                        str = photoEntry.path;
                    }
                    String str2 = str;
                    if (str2 != null) {
                        Log.d("RICHED", "  startMediaUpload uploadPath=" + str2 + " imageId=" + photoEntry.imageId);
                        boolean z5 = photoEntry.isVideo;
                        ChatAttachAlertRichLayout chatAttachAlertRichLayout = ChatAttachAlertRichLayout.this;
                        if (z5) {
                            chatAttachAlertRichLayout.startMediaUpload(blockRow, str2, photoEntry.thumbPath, photoEntry.imageId, true, photoEntry.width, photoEntry.height, photoEntry.duration);
                        } else {
                            chatAttachAlertRichLayout.startMediaUpload(blockRow, str2, photoEntry.thumbPath, photoEntry.imageId, false, 0, 0, 0);
                        }
                    }
                }
            }
            chatAttachAlert.dismiss(true);
        }
    }

    public void startMediaUpload(BlockRow blockRow, String str, String str2, int i, boolean z, int i2, int i3, int i4) {
        String str3;
        RichMediaUploader richMediaUploaderRemove = this.uploaders.remove(blockRow);
        if (richMediaUploaderRemove != null) {
            richMediaUploaderRemove.cancel();
        }
        if (blockRow.media == null) {
            blockRow.media = new MediaUploadState();
        }
        MediaUploadState mediaUploadState = blockRow.media;
        mediaUploadState.state = 1;
        mediaUploadState.isVideo = z;
        mediaUploadState.localPath = str;
        mediaUploadState.thumbPath = str2;
        mediaUploadState.imageId = i;
        if (z) {
            mediaUploadState.localThumbBitmap = extractFirstFrame(str);
            if (blockRow.media.localThumbBitmap != null) {
                str3 = blockRow.media.localThumbBitmap.getWidth() + "x" + blockRow.media.localThumbBitmap.getHeight();
            } else {
                str3 = "null";
            }
            Log.d("RICHED", "extractFirstFrame bitmap=".concat(str3));
        }
        Log.d("RICHED", "startMediaUpload row=" + System.identityHashCode(blockRow) + " isVideo=" + z + " path=" + str + " thumb=" + str2 + " imageId=" + i + " w=" + i2 + " h=" + i3 + " dur=" + i4);
        MediaUploadState mediaUploadState2 = blockRow.media;
        mediaUploadState2.progress = 0.0f;
        mediaUploadState2.photo = null;
        mediaUploadState2.document = null;
        if (z) {
            mediaUploadState2.width = i2;
            mediaUploadState2.height = i3;
            mediaUploadState2.duration = i4;
        }
        if (z && !(blockRow.block instanceof TL_iv.pageBlockVideo)) {
            blockRow.block = new TL_iv.pageBlockVideo();
        } else if (!z && !(blockRow.block instanceof TL_iv.pageBlockPhoto)) {
            blockRow.block = new TL_iv.pageBlockPhoto();
        }
        RichMediaUploader richMediaUploader = new RichMediaUploader(this.currentAccount, str, z, i2, i3, i4, new RichMediaUploader.Listener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.12
            final /* synthetic */ BlockRow val$row;

            public C750712(BlockRow blockRow2) {
                blockRow = blockRow2;
            }

            @Override // org.telegram.ui.iv.RichMediaUploader.Listener
            public void onWidthHeightResolved(int i5, int i6) {
                BlockRow blockRow2 = blockRow;
                MediaUploadState mediaUploadState3 = blockRow2.media;
                mediaUploadState3.width = i5;
                mediaUploadState3.height = i6;
                ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow2);
                ChatAttachAlertRichLayout.this.listView.adapter.update(false);
            }

            @Override // org.telegram.ui.iv.RichMediaUploader.Listener
            public void onProgress(float f) {
                BlockRow blockRow2 = blockRow;
                blockRow2.media.progress = f;
                ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow2);
            }

            @Override // org.telegram.ui.iv.RichMediaUploader.Listener
            public void onPhotoUploaded(TLRPC.Photo photo) {
                BlockRow blockRow2 = blockRow;
                MediaUploadState mediaUploadState3 = blockRow2.media;
                mediaUploadState3.photo = photo;
                mediaUploadState3.state = 2;
                TL_iv.PageBlock pageBlock = blockRow2.block;
                if (pageBlock instanceof TL_iv.pageBlockPhoto) {
                    ((TL_iv.pageBlockPhoto) pageBlock).photo_id = photo.f1276id;
                }
                ChatAttachAlertRichLayout.this.uploaders.remove(blockRow);
                ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow);
                ChatAttachAlertRichLayout.this.updateSendButton(true);
            }

            @Override // org.telegram.ui.iv.RichMediaUploader.Listener
            public void onVideoUploaded(TLRPC.Document document) {
                BlockRow blockRow2 = blockRow;
                MediaUploadState mediaUploadState3 = blockRow2.media;
                mediaUploadState3.document = document;
                mediaUploadState3.state = 2;
                TL_iv.PageBlock pageBlock = blockRow2.block;
                if (pageBlock instanceof TL_iv.pageBlockVideo) {
                    ((TL_iv.pageBlockVideo) pageBlock).video_id = document.f1253id;
                }
                ChatAttachAlertRichLayout.this.uploaders.remove(blockRow);
                ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow);
                ChatAttachAlertRichLayout.this.updateSendButton(true);
            }

            @Override // org.telegram.ui.iv.RichMediaUploader.Listener
            public void onError() {
                blockRow.media.state = 3;
                ChatAttachAlertRichLayout.this.uploaders.remove(blockRow);
                ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow);
                ChatAttachAlertRichLayout.this.updateSendButton(true);
            }
        });
        this.uploaders.put(blockRow2, richMediaUploader);
        richMediaUploader.start();
        invalidateMediaCellForRow(blockRow2);
        this.listView.adapter.update(false);
        updateSendButton(true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$12 */
    public class C750712 implements RichMediaUploader.Listener {
        final /* synthetic */ BlockRow val$row;

        public C750712(BlockRow blockRow2) {
            blockRow = blockRow2;
        }

        @Override // org.telegram.ui.iv.RichMediaUploader.Listener
        public void onWidthHeightResolved(int i5, int i6) {
            BlockRow blockRow2 = blockRow;
            MediaUploadState mediaUploadState3 = blockRow2.media;
            mediaUploadState3.width = i5;
            mediaUploadState3.height = i6;
            ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow2);
            ChatAttachAlertRichLayout.this.listView.adapter.update(false);
        }

        @Override // org.telegram.ui.iv.RichMediaUploader.Listener
        public void onProgress(float f) {
            BlockRow blockRow2 = blockRow;
            blockRow2.media.progress = f;
            ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow2);
        }

        @Override // org.telegram.ui.iv.RichMediaUploader.Listener
        public void onPhotoUploaded(TLRPC.Photo photo) {
            BlockRow blockRow2 = blockRow;
            MediaUploadState mediaUploadState3 = blockRow2.media;
            mediaUploadState3.photo = photo;
            mediaUploadState3.state = 2;
            TL_iv.PageBlock pageBlock = blockRow2.block;
            if (pageBlock instanceof TL_iv.pageBlockPhoto) {
                ((TL_iv.pageBlockPhoto) pageBlock).photo_id = photo.f1276id;
            }
            ChatAttachAlertRichLayout.this.uploaders.remove(blockRow);
            ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow);
            ChatAttachAlertRichLayout.this.updateSendButton(true);
        }

        @Override // org.telegram.ui.iv.RichMediaUploader.Listener
        public void onVideoUploaded(TLRPC.Document document) {
            BlockRow blockRow2 = blockRow;
            MediaUploadState mediaUploadState3 = blockRow2.media;
            mediaUploadState3.document = document;
            mediaUploadState3.state = 2;
            TL_iv.PageBlock pageBlock = blockRow2.block;
            if (pageBlock instanceof TL_iv.pageBlockVideo) {
                ((TL_iv.pageBlockVideo) pageBlock).video_id = document.f1253id;
            }
            ChatAttachAlertRichLayout.this.uploaders.remove(blockRow);
            ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow);
            ChatAttachAlertRichLayout.this.updateSendButton(true);
        }

        @Override // org.telegram.ui.iv.RichMediaUploader.Listener
        public void onError() {
            blockRow.media.state = 3;
            ChatAttachAlertRichLayout.this.uploaders.remove(blockRow);
            ChatAttachAlertRichLayout.this.invalidateMediaCellForRow(blockRow);
            ChatAttachAlertRichLayout.this.updateSendButton(true);
        }
    }

    private static Bitmap extractFirstFrame(String str) {
        MediaMetadataRetriever mediaMetadataRetriever;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
        } catch (Throwable th) {
            th = th;
            mediaMetadataRetriever = null;
        }
        try {
            mediaMetadataRetriever.setDataSource(str);
            Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L, 2);
            try {
                mediaMetadataRetriever.release();
            } catch (Throwable unused) {
            }
            return frameAtTime;
        } catch (Throwable th2) {
            th = th2;
            try {
                Log.e("RICHED", "extractFirstFrame failed for " + str, th);
                if (mediaMetadataRetriever != null) {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Throwable unused2) {
                    }
                }
                return null;
            } catch (Throwable th3) {
                if (mediaMetadataRetriever != null) {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Throwable unused3) {
                    }
                }
                throw th3;
            }
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onDestroy() {
        Iterator<RichMediaUploader> it = this.uploaders.values().iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
        this.uploaders.clear();
    }

    public static void showEditLatexSheet(Context context, String str, final Utilities.Callback<String> callback, final Theme.ResourcesProvider resourcesProvider) {
        BottomSheet.Builder builder = new BottomSheet.Builder(context, true, resourcesProvider);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        final String[] strArr = {str};
        final ImageView imageView = new ImageView(context);
        imageView.setPadding(AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f));
        imageView.setBackground(Theme.createRoundRectDrawable(8, Theme.getColor(Theme.key_dialogBackgroundGray, resourcesProvider)));
        linearLayout.addView(imageView, LayoutHelper.createLinear(-2, -2, 49, 0, 2, 0, 8));
        final boolean[] zArr = {false};
        final int[] iArr = {6};
        final Runnable runnable = new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertRichLayout.m22617$r8$lambda$z2H0njsv5Cll0USODhqAL8nwnw(zArr, strArr, imageView, resourcesProvider, iArr);
            }
        };
        new Runnable() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertRichLayout.$r8$lambda$p_pLxb9IiZpMVEBAlo2geNJH9RM(runnable);
            }
        };
        EditTextCell editTextCell = new EditTextCell(context, "LaTeX Equation", true, false, -1, resourcesProvider);
        editTextCell.editText.setImeOptions(6);
        editTextCell.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(16.0f), Theme.getColor(Theme.key_windowBackgroundWhite, resourcesProvider)));
        editTextCell.setText(strArr[0]);
        editTextCell.editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout.13
            final /* synthetic */ String[] val$source;
            final /* synthetic */ Runnable val$update;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public C750813(final String[] strArr2, final Runnable runnable2) {
                strArr = strArr2;
                runnable = runnable2;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                strArr[0] = editable.toString();
                runnable.run();
            }
        });
        linearLayout.addView(editTextCell, LayoutHelper.createLinear(-1, -2, 55, 12, 0, 12, 0));
        ButtonWithCounterView round = new ButtonWithCounterView(context, resourcesProvider).setRound();
        round.setText(LocaleController.getString(C2797R.string.Done));
        linearLayout.addView(round, LayoutHelper.createLinear(-1, 48, 55, 12, 8, 12, 12));
        runnable2.run();
        builder.setCustomView(linearLayout);
        final BottomSheet bottomSheetShow = builder.show();
        round.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.iv.ChatAttachAlertRichLayout$$ExternalSyntheticLambda30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChatAttachAlertRichLayout.m22606$r8$lambda$Dp5GstcNNe375ZPSWlNAOR1_TE(callback, strArr2, bottomSheetShow, view);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$z2H0njsv5Cll0USODhqAL8-nwnw */
    public static /* synthetic */ void m22617$r8$lambda$z2H0njsv5Cll0USODhqAL8nwnw(boolean[] zArr, String[] strArr, ImageView imageView, Theme.ResourcesProvider resourcesProvider, int[] iArr) {
        boolean z = zArr[0];
        zArr[0] = false;
        try {
            JLatexMathDrawable jLatexMathDrawableBuild = JLatexMathDrawable.builder(strArr[0]).textSize(AndroidUtilities.m1036dp(26.0f)).build();
            int intrinsicWidth = jLatexMathDrawableBuild.getIntrinsicWidth();
            int intrinsicHeight = jLatexMathDrawableBuild.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ALPHA_8);
                jLatexMathDrawableBuild.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                jLatexMathDrawableBuild.draw(new Canvas(bitmapCreateBitmap));
                imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider), PorterDuff.Mode.SRC_IN));
                imageView.setImageBitmap(bitmapCreateBitmap);
            } else {
                zArr[0] = true;
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
            zArr[0] = true;
        }
        if (zArr[0]) {
            imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_text_RedBold, resourcesProvider), PorterDuff.Mode.SRC_IN));
            if (!z) {
                int i = -iArr[0];
                iArr[0] = i;
                AndroidUtilities.shakeViewSpring(imageView, i);
            }
            try {
                JLatexMathDrawable jLatexMathDrawableBuild2 = JLatexMathDrawable.builder("Error").textSize(AndroidUtilities.m1036dp(26.0f)).build();
                int intrinsicWidth2 = jLatexMathDrawableBuild2.getIntrinsicWidth();
                int intrinsicHeight2 = jLatexMathDrawableBuild2.getIntrinsicHeight();
                if (intrinsicWidth2 <= 0 || intrinsicHeight2 <= 0) {
                    return;
                }
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(intrinsicWidth2, intrinsicHeight2, Bitmap.Config.ALPHA_8);
                jLatexMathDrawableBuild2.setBounds(0, 0, intrinsicWidth2, intrinsicHeight2);
                jLatexMathDrawableBuild2.draw(new Canvas(bitmapCreateBitmap2));
                imageView.setImageBitmap(bitmapCreateBitmap2);
            } catch (Exception e2) {
                FileLog.m1048e(e2);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$p_pLxb9IiZpMVEBAlo2geNJH9RM(Runnable runnable) {
        AndroidUtilities.cancelRunOnUIThread(runnable);
        AndroidUtilities.runOnUIThread(runnable, 1000L);
    }

    /* JADX INFO: renamed from: org.telegram.ui.iv.ChatAttachAlertRichLayout$13 */
    public class C750813 implements TextWatcher {
        final /* synthetic */ String[] val$source;
        final /* synthetic */ Runnable val$update;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public C750813(final String[] strArr2, final Runnable runnable2) {
            strArr = strArr2;
            runnable = runnable2;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            strArr[0] = editable.toString();
            runnable.run();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$Dp5Gstc-NNe375ZPSWlNAOR1_TE */
    public static /* synthetic */ void m22606$r8$lambda$Dp5GstcNNe375ZPSWlNAOR1_TE(Utilities.Callback callback, String[] strArr, BottomSheet bottomSheet, View view) {
        callback.run(strArr[0]);
        bottomSheet.lambda$new$0();
    }
}
