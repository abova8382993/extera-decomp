package org.telegram.p035ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
public class RequestPeerRequirementsCell extends LinearLayout {
    private TLRPC.RequestPeerType requestPeerType;
    private ArrayList<Requirement> requirements;

    public RequestPeerRequirementsCell(Context context) {
        super(context);
        this.requirements = new ArrayList<>();
        setOrientation(1);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
    }

    public void set(TLRPC.RequestPeerType requestPeerType) {
        if (this.requestPeerType != requestPeerType) {
            this.requestPeerType = requestPeerType;
            removeAllViews();
            this.requirements.clear();
            if (requestPeerType instanceof TLRPC.TL_requestPeerTypeUser) {
                checkRequirement(((TLRPC.TL_requestPeerTypeUser) requestPeerType).premium, C2797R.string.PeerRequirementPremiumTrue, C2797R.string.PeerRequirementPremiumFalse);
            } else {
                boolean z = requestPeerType instanceof TLRPC.TL_requestPeerTypeBroadcast;
                if (z) {
                    checkRequirement(requestPeerType.has_username, C2797R.string.PeerRequirementChannelPublicTrue, C2797R.string.PeerRequirementChannelPublicFalse);
                    Boolean bool = requestPeerType.bot_participant;
                    if (bool != null && bool.booleanValue()) {
                        this.requirements.add(Requirement.make(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.PeerRequirementChannelBotParticipant))));
                    }
                    Boolean bool2 = requestPeerType.creator;
                    if (bool2 != null && bool2.booleanValue()) {
                        this.requirements.add(Requirement.make(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.PeerRequirementChannelCreatorTrue))));
                    }
                } else {
                    checkRequirement(requestPeerType.has_username, C2797R.string.PeerRequirementGroupPublicTrue, C2797R.string.PeerRequirementGroupPublicFalse);
                    checkRequirement(requestPeerType.forum, C2797R.string.PeerRequirementForumTrue, C2797R.string.PeerRequirementForumFalse);
                    Boolean bool3 = requestPeerType.bot_participant;
                    if (bool3 != null && bool3.booleanValue()) {
                        this.requirements.add(Requirement.make(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.PeerRequirementGroupBotParticipant))));
                    }
                    Boolean bool4 = requestPeerType.creator;
                    if (bool4 != null && bool4.booleanValue()) {
                        this.requirements.add(Requirement.make(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.PeerRequirementGroupCreatorTrue))));
                    }
                }
                Boolean bool5 = requestPeerType.creator;
                if (bool5 == null || !bool5.booleanValue()) {
                    checkAdminRights(requestPeerType.user_admin_rights, z, C2797R.string.PeerRequirementUserRights, C2797R.string.PeerRequirementUserRight);
                }
            }
            if (this.requirements.isEmpty()) {
                return;
            }
            HeaderCell headerCell = new HeaderCell(getContext(), 20);
            headerCell.setText(LocaleController.getString(C2797R.string.PeerRequirements));
            int i = Theme.key_windowBackgroundWhite;
            headerCell.setBackgroundColor(Theme.getColor(i));
            addView(headerCell, LayoutHelper.createLinear(-1, -2));
            addView(emptyView(9, Theme.getColor(i)), LayoutHelper.createLinear(-1, -2));
            ArrayList<Requirement> arrayList = this.requirements;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Requirement requirement = arrayList.get(i2);
                i2++;
                addView(new RequirementCell(getContext(), requirement), LayoutHelper.createLinear(-1, -2));
            }
            addView(emptyView(12, Theme.getColor(Theme.key_windowBackgroundWhite)), LayoutHelper.createLinear(-1, -2));
            addView(emptyView(12, Theme.getThemedDrawableByKey(getContext(), C2797R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow)), LayoutHelper.createLinear(-1, -2));
        }
    }

    private View emptyView(int i, int i2) {
        return emptyView(i, new ColorDrawable(i2));
    }

    /* JADX INFO: renamed from: org.telegram.ui.Cells.RequestPeerRequirementsCell$1 */
    public class C33451 extends View {
        final /* synthetic */ int val$heightDp;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C33451(Context context, int i) {
            super(context);
            i = i;
        }

        @Override // android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(i), TLObject.FLAG_30));
        }
    }

    private View emptyView(int i, Drawable drawable) {
        C33451 c33451 = new View(getContext()) { // from class: org.telegram.ui.Cells.RequestPeerRequirementsCell.1
            final /* synthetic */ int val$heightDp;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C33451(Context context, int i2) {
                super(context);
                i = i2;
            }

            @Override // android.view.View
            public void onMeasure(int i2, int i22) {
                super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(i), TLObject.FLAG_30));
            }
        };
        c33451.setBackground(drawable);
        return c33451;
    }

    private void checkRequirement(Boolean bool, int i, int i2) {
        if (bool != null) {
            boolean zBooleanValue = bool.booleanValue();
            ArrayList<Requirement> arrayList = this.requirements;
            if (zBooleanValue) {
                arrayList.add(Requirement.make(AndroidUtilities.replaceTags(LocaleController.getString(i))));
            } else {
                arrayList.add(Requirement.make(AndroidUtilities.replaceTags(LocaleController.getString(i2))));
            }
        }
    }

    public static CharSequence rightsToString(TLRPC.TL_chatAdminRights tL_chatAdminRights, boolean z) {
        String string;
        ArrayList arrayList = new ArrayList();
        if (tL_chatAdminRights.change_info) {
            if (z) {
                string = LocaleController.getString(C2797R.string.EditAdminChangeChannelInfo);
            } else {
                string = LocaleController.getString(C2797R.string.EditAdminChangeGroupInfo);
            }
            arrayList.add(Requirement.make(1, string));
        }
        if (tL_chatAdminRights.post_messages && z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminPostMessages)));
        }
        if (tL_chatAdminRights.edit_messages && z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminEditMessages)));
        }
        if (tL_chatAdminRights.delete_messages) {
            arrayList.add(Requirement.make(1, LocaleController.getString(z ? C2797R.string.EditAdminDeleteMessages : C2797R.string.EditAdminGroupDeleteMessages)));
        }
        if (tL_chatAdminRights.ban_users && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminBanUsers)));
        }
        if (tL_chatAdminRights.invite_users) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminAddUsers)));
        }
        if (tL_chatAdminRights.pin_messages && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminPinMessages)));
        }
        if (tL_chatAdminRights.add_admins) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminAddAdmins)));
        }
        if (tL_chatAdminRights.anonymous && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminSendAnonymously)));
        }
        if (tL_chatAdminRights.manage_call) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.StartVoipChatPermission)));
        }
        if (tL_chatAdminRights.manage_topics && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.ManageTopicsPermission)));
        }
        if (arrayList.size() == 1) {
            return ((Requirement) arrayList.get(0)).text.toString().toLowerCase();
        }
        if (!arrayList.isEmpty()) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i = 0; i < arrayList.size(); i++) {
                if (i > 0) {
                    spannableStringBuilder.append((CharSequence) ", ");
                }
                spannableStringBuilder.append((CharSequence) ((Requirement) arrayList.get(i)).text.toString().toLowerCase());
            }
            return spannableStringBuilder;
        }
        return _UrlKt.FRAGMENT_ENCODE_SET;
    }

    private void checkAdminRights(TLRPC.TL_chatAdminRights tL_chatAdminRights, boolean z, CharSequence charSequence, CharSequence charSequence2) {
        String string;
        if (tL_chatAdminRights == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (tL_chatAdminRights.change_info) {
            if (z) {
                string = LocaleController.getString(C2797R.string.EditAdminChangeChannelInfo);
            } else {
                string = LocaleController.getString(C2797R.string.EditAdminChangeGroupInfo);
            }
            arrayList.add(Requirement.make(1, string));
        }
        if (tL_chatAdminRights.post_messages && z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminPostMessages)));
        }
        if (tL_chatAdminRights.edit_messages && z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminEditMessages)));
        }
        if (tL_chatAdminRights.delete_messages) {
            arrayList.add(Requirement.make(1, LocaleController.getString(z ? C2797R.string.EditAdminDeleteMessages : C2797R.string.EditAdminGroupDeleteMessages)));
        }
        if (tL_chatAdminRights.ban_users && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminBanUsers)));
        }
        if (tL_chatAdminRights.invite_users) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminAddUsers)));
        }
        if (tL_chatAdminRights.pin_messages && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminPinMessages)));
        }
        if (tL_chatAdminRights.add_admins) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminAddAdmins)));
        }
        if (tL_chatAdminRights.anonymous && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.EditAdminSendAnonymously)));
        }
        if (tL_chatAdminRights.manage_call) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.StartVoipChatPermission)));
        }
        if (tL_chatAdminRights.manage_topics && !z) {
            arrayList.add(Requirement.make(1, LocaleController.getString(C2797R.string.ManageTopicsPermission)));
        }
        if (arrayList.size() == 1) {
            this.requirements.add(Requirement.make(TextUtils.concat(charSequence2, " ", ((Requirement) arrayList.get(0)).text)));
            return;
        }
        if (arrayList.isEmpty()) {
            return;
        }
        SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(charSequence);
        spannableStringBuilderValueOf.append((CharSequence) " ");
        for (int i = 0; i < arrayList.size(); i++) {
            if (i > 0) {
                spannableStringBuilderValueOf.append((CharSequence) ", ");
            }
            spannableStringBuilderValueOf.append((CharSequence) ((Requirement) arrayList.get(i)).text.toString().toLowerCase());
        }
        spannableStringBuilderValueOf.append((CharSequence) ".");
        this.requirements.add(Requirement.make(spannableStringBuilderValueOf));
    }

    private void checkAdminRights(TLRPC.TL_chatAdminRights tL_chatAdminRights, boolean z, int i, int i2) {
        checkAdminRights(tL_chatAdminRights, z, AndroidUtilities.replaceTags(LocaleController.getString(i)), AndroidUtilities.replaceTags(LocaleController.getString(i2)));
    }

    public class RequirementCell extends LinearLayout {
        private ImageView imageView;
        private TextView textView;

        public RequirementCell(Context context, Requirement requirement) {
            super(context);
            setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            setOrientation(0);
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            this.imageView.setImageResource(requirement.padding <= 0 ? C2797R.drawable.list_check : C2797R.drawable.list_circle);
            this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader), PorterDuff.Mode.MULTIPLY));
            addView(this.imageView, LayoutHelper.createLinear(20, 20, 0.0f, 51, (requirement.padding * 16) + 17, -1, 0, 0));
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextSize(1, 14.0f);
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
            this.textView.setSingleLine(false);
            this.textView.setText(requirement.text);
            addView(this.textView, LayoutHelper.createLinear(-1, -2, 1, 6, 4, 24, 4));
        }
    }
}
