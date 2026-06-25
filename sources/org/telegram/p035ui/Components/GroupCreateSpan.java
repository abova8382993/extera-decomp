package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes3.dex */
public class GroupCreateSpan extends View {
    private AvatarDrawable avatarDrawable;
    private int[] colors;
    private String countryIso2;
    private ContactsController.Contact currentContact;
    private Drawable deleteDrawable;
    private boolean deleting;
    private boolean drawAvatarBackground;
    private ImageReceiver imageReceiver;
    public boolean isFlag;
    private String key;
    private long lastUpdateTime;
    private StaticLayout nameLayout;
    private float progress;
    private RectF rect;
    private Theme.ResourcesProvider resourcesProvider;
    private boolean small;
    private int textWidth;
    private float textX;
    private long uid;
    private static TextPaint textPaint = new TextPaint(1);
    private static Paint backPaint = new Paint(1);

    public GroupCreateSpan(Context context, Object obj) {
        this(context, obj, null);
    }

    public GroupCreateSpan(Context context, Object obj, ContactsController.Contact contact) {
        this(context, obj, contact, null);
    }

    public GroupCreateSpan(Context context, Object obj, ContactsController.Contact contact, Theme.ResourcesProvider resourcesProvider) {
        this(context, obj, contact, false, resourcesProvider);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x03b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GroupCreateSpan(android.content.Context r32, java.lang.Object r33, org.telegram.messenger.ContactsController.Contact r34, boolean r35, org.telegram.ui.ActionBar.Theme.ResourcesProvider r36) {
        /*
            Method dump skipped, instruction units count: 1020
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.GroupCreateSpan.<init>(android.content.Context, java.lang.Object, org.telegram.messenger.ContactsController$Contact, boolean, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public void updateColors() {
        int color = this.avatarDrawable.getColor();
        int iMultAlpha = Theme.multAlpha(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider), 0.05f);
        int color2 = Theme.getColor(Theme.key_groupcreate_spanDelete, this.resourcesProvider);
        this.colors[0] = Color.red(iMultAlpha);
        this.colors[1] = Color.red(color);
        this.colors[2] = Color.green(iMultAlpha);
        this.colors[3] = Color.green(color);
        this.colors[4] = Color.blue(iMultAlpha);
        this.colors[5] = Color.blue(color);
        this.colors[6] = Color.alpha(iMultAlpha);
        this.colors[7] = Color.alpha(color);
        this.deleteDrawable.setColorFilter(new PorterDuffColorFilter(color2, PorterDuff.Mode.MULTIPLY));
        backPaint.setColor(iMultAlpha);
    }

    public String getCountryIso2() {
        return this.countryIso2;
    }

    public boolean isDeleting() {
        return this.deleting;
    }

    public void startDeleteAnimation() {
        if (this.deleting) {
            return;
        }
        this.deleting = true;
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    public void cancelDeleteAnimation() {
        if (this.deleting) {
            this.deleting = false;
            this.lastUpdateTime = System.currentTimeMillis();
            invalidate();
        }
    }

    public long getUid() {
        return this.uid;
    }

    public String getKey() {
        return this.key;
    }

    public ContactsController.Contact getContact() {
        return this.currentContact;
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(AndroidUtilities.m1036dp((this.small ? 20 : 32) + 25) + this.textWidth, AndroidUtilities.m1036dp(this.small ? 28.0f : 32.0f));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        boolean z = this.deleting;
        if ((z && this.progress != 1.0f) || (!z && this.progress != 0.0f)) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.lastUpdateTime;
            if (jCurrentTimeMillis < 0 || jCurrentTimeMillis > 17) {
                jCurrentTimeMillis = 17;
            }
            boolean z2 = this.deleting;
            float f = this.progress;
            if (z2) {
                float f2 = f + (jCurrentTimeMillis / 120.0f);
                this.progress = f2;
                if (f2 >= 1.0f) {
                    this.progress = 1.0f;
                }
            } else {
                float f3 = f - (jCurrentTimeMillis / 120.0f);
                this.progress = f3;
                if (f3 < 0.0f) {
                    this.progress = 0.0f;
                }
            }
            invalidate();
        }
        canvas.save();
        this.rect.set(0.0f, 0.0f, getMeasuredWidth(), AndroidUtilities.m1036dp(this.small ? 28.0f : 32.0f));
        Paint paint = backPaint;
        int[] iArr = this.colors;
        int i = iArr[6];
        float f4 = iArr[7] - i;
        float f5 = this.progress;
        paint.setColor(Color.argb(i + ((int) (f4 * f5)), iArr[0] + ((int) ((iArr[1] - r8) * f5)), iArr[2] + ((int) ((iArr[3] - r11) * f5)), iArr[4] + ((int) ((iArr[5] - r12) * f5))));
        canvas.drawRoundRect(this.rect, ExteraConfig.getAvatarCorners(this.small ? 28.0f : 32.0f), ExteraConfig.getAvatarCorners(this.small ? 28.0f : 32.0f), backPaint);
        if (this.progress != 1.0f) {
            this.imageReceiver.draw(canvas);
        }
        if (this.progress != 0.0f) {
            backPaint.setColor(this.avatarDrawable.getColor());
            backPaint.setAlpha((int) (this.progress * 255.0f * (Color.alpha(r2) / 255.0f)));
            canvas.drawRoundRect(0.0f, 0.0f, AndroidUtilities.m1036dp(this.small ? 28.0f : 32.0f), AndroidUtilities.m1036dp(this.small ? 28.0f : 32.0f), ExteraConfig.getAvatarCorners(this.small ? 28.0f : 32.0f), ExteraConfig.getAvatarCorners(this.small ? 28.0f : 32.0f), backPaint);
            canvas.save();
            canvas.rotate((1.0f - this.progress) * 45.0f, AndroidUtilities.m1036dp(16.0f), AndroidUtilities.m1036dp(16.0f));
            this.deleteDrawable.setBounds(AndroidUtilities.m1036dp(this.small ? 9.0f : 11.0f), AndroidUtilities.m1036dp(this.small ? 9.0f : 11.0f), AndroidUtilities.m1036dp(this.small ? 19.0f : 21.0f), AndroidUtilities.m1036dp(this.small ? 19.0f : 21.0f));
            this.deleteDrawable.setAlpha((int) (this.progress * 255.0f));
            this.deleteDrawable.draw(canvas);
            canvas.restore();
        }
        canvas.translate(this.textX + AndroidUtilities.m1036dp((this.small ? 26 : 32) + 9), AndroidUtilities.m1036dp(this.small ? 6.0f : 8.0f));
        textPaint.setColor(ColorUtils.blendARGB(Theme.getColor(Theme.key_groupcreate_spanText, this.resourcesProvider), Theme.getColor(Theme.key_avatar_text, this.resourcesProvider), this.progress));
        this.nameLayout.draw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setText(this.nameLayout.getText());
        if (isDeleting()) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), LocaleController.getString(C2797R.string.Delete)));
        }
    }
}
