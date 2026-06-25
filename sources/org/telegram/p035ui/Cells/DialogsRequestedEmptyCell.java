package org.telegram.p035ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.BackupImageView;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
public abstract class DialogsRequestedEmptyCell extends LinearLayout implements NotificationCenter.NotificationCenterDelegate {
    TextView buttonView;
    int currentAccount;
    BackupImageView stickerView;
    TextView subtitleView;
    TextView titleView;

    public abstract void onButtonClick();

    public DialogsRequestedEmptyCell(Context context) {
        super(context);
        this.currentAccount = UserConfig.selectedAccount;
        setOrientation(1);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        C32971 c32971 = new LinearLayout(context) { // from class: org.telegram.ui.Cells.DialogsRequestedEmptyCell.1
            Paint paint;
            Path path = new Path();

            public C32971(Context context2) {
                super(context2);
                this.path = new Path();
                Paint paint = new Paint(1);
                this.paint = paint;
                paint.setColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                this.paint.setShadowLayer(AndroidUtilities.m1036dp(1.33f), 0.0f, AndroidUtilities.m1036dp(0.33f), 503316480);
            }

            @Override // android.widget.LinearLayout, android.view.View
            public void onDraw(Canvas canvas) {
                canvas.drawPath(this.path, this.paint);
                super.onDraw(canvas);
            }

            @Override // android.widget.LinearLayout, android.view.View
            public void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                this.path.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(6.0f), getMeasuredWidth() - AndroidUtilities.m1036dp(12.0f), getMeasuredHeight() - AndroidUtilities.m1036dp(12.0f));
                this.path.addRoundRect(rectF, AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(10.0f), Path.Direction.CW);
            }
        };
        c32971.setWillNotDraw(false);
        c32971.setOrientation(1);
        c32971.setPadding(AndroidUtilities.m1036dp(32.0f), AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(32.0f), AndroidUtilities.m1036dp(32.0f));
        BackupImageView backupImageView = new BackupImageView(context2);
        this.stickerView = backupImageView;
        backupImageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Cells.DialogsRequestedEmptyCell$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        updateSticker();
        c32971.addView(this.stickerView, LayoutHelper.createLinear(130, 130, 49));
        TextView textView = new TextView(context2);
        this.titleView = textView;
        textView.setGravity(17);
        this.titleView.setTextSize(1, 18.0f);
        this.titleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.titleView.setTypeface(AndroidUtilities.bold());
        c32971.addView(this.titleView, LayoutHelper.createLinear(-1, -2, 49, 0, 6, 0, 0));
        TextView textView2 = new TextView(context2);
        this.subtitleView = textView2;
        textView2.setGravity(17);
        this.subtitleView.setTextSize(1, 14.0f);
        this.subtitleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        c32971.addView(this.subtitleView, LayoutHelper.createLinear(-1, -2, 49, 0, 7, 0, 0));
        TextView textView3 = new TextView(context2);
        this.buttonView = textView3;
        textView3.setGravity(17);
        this.buttonView.setBackground(Theme.AdaptiveRipple.filledRectByKey(Theme.key_featuredStickers_addButton, 8.0f));
        this.buttonView.setTextSize(1, 14.0f);
        this.buttonView.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        this.buttonView.setTypeface(AndroidUtilities.bold());
        this.buttonView.setPadding(AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(14.0f), AndroidUtilities.m1036dp(14.0f));
        this.buttonView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Cells.DialogsRequestedEmptyCell$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(view);
            }
        });
        c32971.addView(this.buttonView, LayoutHelper.createLinear(-1, -2, 49, 0, 18, 0, 0));
        addView(c32971, LayoutHelper.createLinear(-1, -2));
        set(null);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.DialogsRequestedEmptyCell$1 */
    public class C32971 extends LinearLayout {
        Paint paint;
        Path path = new Path();

        public C32971(Context context2) {
            super(context2);
            this.path = new Path();
            Paint paint = new Paint(1);
            this.paint = paint;
            paint.setColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.paint.setShadowLayer(AndroidUtilities.m1036dp(1.33f), 0.0f, AndroidUtilities.m1036dp(0.33f), 503316480);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onDraw(Canvas canvas) {
            canvas.drawPath(this.path, this.paint);
            super.onDraw(canvas);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            this.path.rewind();
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(6.0f), getMeasuredWidth() - AndroidUtilities.m1036dp(12.0f), getMeasuredHeight() - AndroidUtilities.m1036dp(12.0f));
            this.path.addRoundRect(rectF, AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(10.0f), Path.Direction.CW);
        }
    }

    public /* synthetic */ void lambda$new$0(View view) {
        this.stickerView.getImageReceiver().startAnimation();
    }

    public /* synthetic */ void lambda$new$1(View view) {
        onButtonClick();
    }

    public void set(TLRPC.RequestPeerType requestPeerType) {
        if (requestPeerType instanceof TLRPC.TL_requestPeerTypeBroadcast) {
            this.titleView.setText(LocaleController.getString(C2797R.string.NoSuchChannels));
            this.subtitleView.setText(LocaleController.getString(C2797R.string.NoSuchChannelsInfo));
            this.buttonView.setVisibility(0);
            this.buttonView.setText(LocaleController.getString(C2797R.string.CreateChannelForThis));
            return;
        }
        boolean z = requestPeerType instanceof TLRPC.TL_requestPeerTypeChat;
        TextView textView = this.titleView;
        if (z) {
            textView.setText(LocaleController.getString(C2797R.string.NoSuchGroups));
            this.subtitleView.setText(LocaleController.getString(C2797R.string.NoSuchGroupsInfo));
            this.buttonView.setVisibility(0);
            this.buttonView.setText(LocaleController.getString(C2797R.string.CreateGroupForThis));
            return;
        }
        textView.setText(LocaleController.getString(C2797R.string.NoSuchUsers));
        this.subtitleView.setText(LocaleController.getString(C2797R.string.NoSuchUsersInfo));
        this.buttonView.setVisibility(8);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.diceStickersDidLoad);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.diceStickersDidLoad);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.diceStickersDidLoad && AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME.equals((String) objArr[0]) && getVisibility() == 0) {
            updateSticker();
        }
    }

    private void updateSticker() {
        TLRPC.TL_messages_stickerSet stickerSetByName = MediaDataController.getInstance(this.currentAccount).getStickerSetByName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME);
        if (stickerSetByName == null) {
            stickerSetByName = MediaDataController.getInstance(this.currentAccount).getStickerSetByEmojiOrName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME);
        }
        TLRPC.TL_messages_stickerSet tL_messages_stickerSet = stickerSetByName;
        TLRPC.Document document = (tL_messages_stickerSet == null || 1 >= tL_messages_stickerSet.documents.size()) ? null : tL_messages_stickerSet.documents.get(1);
        if (document != null) {
            SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(document.thumbs, Theme.key_windowBackgroundGray, 0.2f);
            if (svgThumb != null) {
                svgThumb.overrideWidthAndHeight(512, 512);
            }
            this.stickerView.setImage(ImageLocation.getForDocument(document), "130_130", "tgs", svgThumb, tL_messages_stickerSet);
            this.stickerView.getImageReceiver().setAutoRepeat(2);
            return;
        }
        MediaDataController.getInstance(this.currentAccount).loadStickersByEmojiOrName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME, false, tL_messages_stickerSet == null);
        this.stickerView.getImageReceiver().clearImage();
    }
}
