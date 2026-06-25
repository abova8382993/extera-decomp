package org.telegram.p035ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.SharedConfig;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.RadioButton;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ChatListCell extends LinearLayout {
    private ListView[] listView;

    public abstract void didSelectChatType(boolean z);

    public class ListView extends FrameLayout {
        private RadioButton button;
        private boolean isThreeLines;
        private RectF rect;
        private TextPaint textPaint;

        public ListView(Context context, boolean z) {
            super(context);
            this.rect = new RectF();
            boolean z2 = true;
            this.textPaint = new TextPaint(1);
            setWillNotDraw(false);
            this.isThreeLines = z;
            setContentDescription(LocaleController.getString(z ? C2797R.string.ChatListExpanded : C2797R.string.ChatListDefault));
            this.textPaint.setTextSize(AndroidUtilities.m1036dp(13.0f));
            RadioButton radioButton = new RadioButton(context) { // from class: org.telegram.ui.Cells.ChatListCell.ListView.1
                @Override // android.view.View
                public void invalidate() {
                    super.invalidate();
                    ListView.this.invalidate();
                }
            };
            this.button = radioButton;
            radioButton.setSize(AndroidUtilities.m1036dp(20.0f));
            addView(this.button, LayoutHelper.createFrame(22, 22.0f, 53, 0.0f, 26.0f, 10.0f, 0.0f));
            RadioButton radioButton2 = this.button;
            boolean z3 = this.isThreeLines;
            if ((!z3 || !SharedConfig.useThreeLinesLayout) && (z3 || SharedConfig.useThreeLinesLayout)) {
                z2 = false;
            }
            radioButton2.setChecked(z2, false);
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            int color = Theme.getColor(Theme.key_switchTrack);
            int iRed = Color.red(color);
            int iGreen = Color.green(color);
            int iBlue = Color.blue(color);
            this.button.setColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_radioBackgroundChecked));
            this.rect.set(AndroidUtilities.m1036dp(1.0f), AndroidUtilities.m1036dp(1.0f), getMeasuredWidth() - AndroidUtilities.m1036dp(1.0f), AndroidUtilities.m1036dp(73.0f));
            Theme.chat_instantViewRectPaint.setColor(Color.argb((int) (this.button.getProgress() * 43.0f), iRed, iGreen, iBlue));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), Theme.chat_instantViewRectPaint);
            this.rect.set(0.0f, 0.0f, getMeasuredWidth(), AndroidUtilities.m1036dp(74.0f));
            Theme.dialogs_onlineCirclePaint.setColor(Color.argb((int) ((1.0f - this.button.getProgress()) * 31.0f), iRed, iGreen, iBlue));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), Theme.dialogs_onlineCirclePaint);
            String string = LocaleController.getString(this.isThreeLines ? C2797R.string.ChatListExpanded : C2797R.string.ChatListDefault);
            int iCeil = (int) Math.ceil(this.textPaint.measureText(string));
            this.textPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            int measuredWidth = getMeasuredWidth() - iCeil;
            int i = 2;
            canvas.drawText(string, measuredWidth / 2, AndroidUtilities.m1036dp(96.0f), this.textPaint);
            int i2 = 0;
            while (i2 < i) {
                int iM1036dp = AndroidUtilities.m1036dp(i2 == 0 ? 21.0f : 53.0f);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(i2 == 0 ? 204 : 90, iRed, iGreen, iBlue));
                canvas.drawCircle(AndroidUtilities.m1036dp(22.0f), iM1036dp, AndroidUtilities.m1036dp(11.0f), Theme.dialogs_onlineCirclePaint);
                int i3 = 0;
                while (true) {
                    if (i3 < (this.isThreeLines ? 3 : i)) {
                        Theme.dialogs_onlineCirclePaint.setColor(Color.argb(i3 == 0 ? 204 : 90, iRed, iGreen, iBlue));
                        boolean z = this.isThreeLines;
                        RectF rectF = this.rect;
                        if (z) {
                            float f = i3 * 7;
                            rectF.set(AndroidUtilities.m1036dp(41.0f), iM1036dp - AndroidUtilities.m1036dp(8.3f - f), getMeasuredWidth() - AndroidUtilities.m1036dp(i3 == 0 ? 72.0f : 48.0f), iM1036dp - AndroidUtilities.m1036dp(5.3f - f));
                            canvas.drawRoundRect(this.rect, AndroidUtilities.dpf2(1.5f), AndroidUtilities.dpf2(1.5f), Theme.dialogs_onlineCirclePaint);
                        } else {
                            int i4 = i3 * 10;
                            rectF.set(AndroidUtilities.m1036dp(41.0f), iM1036dp - AndroidUtilities.m1036dp(7 - i4), getMeasuredWidth() - AndroidUtilities.m1036dp(i3 == 0 ? 72.0f : 48.0f), iM1036dp - AndroidUtilities.m1036dp(3 - i4));
                            canvas.drawRoundRect(this.rect, AndroidUtilities.m1036dp(2.0f), AndroidUtilities.m1036dp(2.0f), Theme.dialogs_onlineCirclePaint);
                        }
                        i3++;
                        i = 2;
                    }
                }
                i2++;
                i = 2;
            }
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(RadioButton.class.getName());
            accessibilityNodeInfo.setChecked(this.button.isChecked());
            accessibilityNodeInfo.setCheckable(true);
            accessibilityNodeInfo.setContentDescription(LocaleController.getString(this.isThreeLines ? C2797R.string.ChatListExpanded : C2797R.string.ChatListDefault));
        }
    }

    public ChatListCell(Context context) {
        super(context);
        this.listView = new ListView[2];
        setOrientation(0);
        setPadding(AndroidUtilities.m1036dp(21.0f), AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(21.0f), 0);
        int i = 0;
        while (true) {
            ListView[] listViewArr = this.listView;
            if (i >= listViewArr.length) {
                return;
            }
            final boolean z = i == 1;
            listViewArr[i] = new ListView(context, z);
            addView(this.listView[i], LayoutHelper.createLinear(-1, -1, 0.5f, i == 1 ? 10 : 0, 0, 0, 0));
            this.listView[i].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Cells.ChatListCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(z, view);
                }
            });
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(boolean z, View view) {
        for (int i = 0; i < 2; i++) {
            this.listView[i].button.setChecked(this.listView[i] == view, true);
        }
        didSelectChatType(z);
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        int i = 0;
        while (true) {
            ListView[] listViewArr = this.listView;
            if (i >= listViewArr.length) {
                return;
            }
            listViewArr[i].invalidate();
            i++;
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(123.0f), TLObject.FLAG_30));
    }
}
