package com.exteragram.messenger.preferences.appearance;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.TabIconsMode;
import com.exteragram.messenger.icons.p015ui.IconPacksActivity;
import com.exteragram.messenger.pillstack.p017ui.PillStackPreferencesActivity;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.preferences.appearance.components.AvatarCornersPreviewCell;
import com.exteragram.messenger.preferences.appearance.components.ChatListPreviewCell;
import com.exteragram.messenger.preferences.appearance.components.FabShapeCell;
import com.exteragram.messenger.preferences.appearance.components.FilterTabsPreviewCell;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.exteragram.messenger.utils.p020ui.PopupUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.TextCheckCell2;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;

/* JADX INFO: loaded from: classes.dex */
public class AppearancePreferencesActivity extends BasePreferencesActivity {
    private AvatarCornersPreviewCell avatarCornersPreviewCell;
    private ChatListPreviewCell chatListPreviewCell;
    private FabShapeCell fabShapeCell;
    private FilterTabsPreviewCell filterTabsPreviewCell;
    private boolean md3StylesExpanded;
    private Parcelable recyclerViewState;
    private CharSequence[] tabIcons;
    private CharSequence[] titles;

    public enum AppearanceItem {
        AVATAR_CORNERS_PREVIEW,
        SINGLE_CORNER_RADIUS,
        CHAT_LIST_PREVIEW,
        FORCE_SNOW,
        HIDE_ACTION_BAR_STATUS,
        CENTER_TITLE,
        HIDE_STORIES,
        HIDE_FLOATING_BUTTON,
        HIDE_DIALOGS_SEARCH_BAR,
        SENDER_MINI_AVATARS,
        ACTION_BAR_TITLE,
        PILL_STACK,
        FOLDERS_PREVIEW,
        TAB_TITLE,
        TAB_COUNTER,
        HIDE_ALL_CHATS,
        APP_NAVIGATION_SETTINGS,
        ICON_PACKS,
        FAB_SHAPE,
        SEPARATED_HEADERS,
        DISABLE_DIVIDERS,
        TABLET_MODE,
        MD3_STYLES,
        NEW_LOADING_STYLE,
        NEW_SLIDER_STYLE,
        NEW_SWITCH_STYLE,
        USE_SYSTEM_FONTS,
        USE_SYSTEM_EMOJI,
        GOOEY_AVATAR_ANIMATION,
        CUSTOM_THEMES,
        PREDICTIVE_BACK_ANIMATION,
        SPRING_ANIMATIONS,
        GLARE_ON_ELEMENTS,
        FORCE_BLUR,
        NEW_CHAT_HEADER_STYLE,
        NEW_NAVIGATION_BAR_STYLE;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void initializeOptionStrings() {
        this.titles = new CharSequence[]{LocaleController.getString(C2797R.string.exteraAppName), LocaleController.getString(C2797R.string.ActionBarTitleUsername), LocaleController.getString(C2797R.string.ActionBarTitleName), LocaleController.getString(C2797R.string.FilterChats)};
        this.tabIcons = new CharSequence[]{LocaleController.getString(C2797R.string.TabTitleStyleTextWithIcons), LocaleController.getString(C2797R.string.TabTitleStyleTextOnly), LocaleController.getString(C2797R.string.TabTitleStyleIconsOnly)};
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.avatarCornersPreviewCell = new AvatarCornersPreviewCell(context, this, this.resourceProvider, RemoteUtils.getIntConfigValue("preferences_preview_style", 0).intValue());
        this.chatListPreviewCell = new ChatListPreviewCell(context);
        this.filterTabsPreviewCell = new FilterTabsPreviewCell(context);
        this.fabShapeCell = new FabShapeCell(context) { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity.1
            @Override // com.exteragram.messenger.preferences.appearance.components.FabShapeCell
            public void rebuildFragments() {
                ((BaseFragment) AppearancePreferencesActivity.this).parentLayout.rebuildFragments(0);
            }
        };
        return super.createView(context);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2797R.string.Appearance);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asCustom(AppearanceItem.AVATAR_CORNERS_PREVIEW.getId(), this.avatarCornersPreviewCell).setLinkAlias("avatarCorners", this));
        arrayList.add(UItem.asCheck(AppearanceItem.SINGLE_CORNER_RADIUS.getId(), LocaleController.getString(C2797R.string.SingleCornerRadius)).setChecked(ExteraConfig.getSingleCornerRadius()).setSearchable(this).setLinkAlias("singleCornerRadius", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.SingleCornerRadiusInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.ListOfChats)));
        arrayList.add(UItem.asCustom(AppearanceItem.CHAT_LIST_PREVIEW.getId(), this.chatListPreviewCell));
        arrayList.add(UItem.asCheck(AppearanceItem.FORCE_SNOW.getId(), LocaleController.getString(C2797R.string.ForceSnow), LocaleController.getString(C2797R.string.ForceSnowInfo), true).setChecked(ExteraConfig.getForceSnow()).setSearchable(this).setLinkAlias("forceSnow", this));
        if (getUserConfig().isPremium()) {
            arrayList.add(UItem.asCheck(AppearanceItem.HIDE_ACTION_BAR_STATUS.getId(), LocaleController.getString(C2797R.string.HideActionBarStatus)).setChecked(ExteraConfig.getHideActionBarStatus()).setSearchable(this).setLinkAlias("hideActionBarStatus", this));
        }
        arrayList.add(UItem.asCheck(AppearanceItem.CENTER_TITLE.getId(), LocaleController.getString(C2797R.string.CenterTitle)).setChecked(ExteraConfig.getCenterTitle()).setSearchable(this).setLinkAlias("centerTitle", this));
        arrayList.add(UItem.asCheck(AppearanceItem.HIDE_STORIES.getId(), LocaleController.getString(C2797R.string.HideStories)).setChecked(ExteraConfig.getHideStories()).setSearchable(this).setLinkAlias("hideStories", this));
        arrayList.add(UItem.asCheck(AppearanceItem.HIDE_FLOATING_BUTTON.getId(), LocaleController.getString(C2797R.string.HideFloatingButton)).setChecked(ExteraConfig.getHideFloatingButton()).setSearchable(this).setLinkAlias("hideFloatingButton", this));
        arrayList.add(UItem.asCheck(AppearanceItem.HIDE_DIALOGS_SEARCH_BAR.getId(), LocaleController.getString(C2797R.string.HideDialogsSearchBar)).setChecked(ExteraConfig.getHideDialogsSearchBar()).setSearchable(this).setLinkAlias("hideDialogsSearchBar", this));
        arrayList.add(UItem.asCheck(AppearanceItem.SENDER_MINI_AVATARS.getId(), LocaleController.getString(C2797R.string.SenderMiniAvatars)).setChecked(ExteraConfig.getSenderMiniAvatars()).setSearchable(this).setLinkAlias("senderMiniAvatars", this));
        arrayList.add(UItem.asButton(AppearanceItem.ACTION_BAR_TITLE.getId(), LocaleController.getString(C2797R.string.ActionBarTitle), this.titles[ExteraConfig.getTitleText()]).setSearchable(this).setLinkAlias("actionBarTitle", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.ListOfChatsInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.Filters)));
        arrayList.add(UItem.asCustom(AppearanceItem.FOLDERS_PREVIEW.getId(), this.filterTabsPreviewCell));
        arrayList.add(UItem.asButton(AppearanceItem.TAB_TITLE.getId(), LocaleController.getString(C2797R.string.TabTitleStyle), this.tabIcons[ExteraConfig.getTabIcons().ordinal()]).setSearchable(this).setLinkAlias("tabTitleStyle", this));
        arrayList.add(UItem.asCheck(AppearanceItem.TAB_COUNTER.getId(), LocaleController.getString(C2797R.string.TabCounter)).setChecked(ExteraConfig.getTabCounter()).setSearchable(this).setLinkAlias("tabCounter", this));
        arrayList.add(UItem.asCheck(AppearanceItem.HIDE_ALL_CHATS.getId(), LocaleController.formatString(C2797R.string.HideAllChats, LocaleController.getString(C2797R.string.FilterAllChats))).setChecked(ExteraConfig.getHideAllChats()).setSearchable(this).setLinkAlias("hideAllChats", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.FoldersInfo)));
        arrayList.add(UItem.asButtonWithSubtext(AppearanceItem.APP_NAVIGATION_SETTINGS.getId(), C2797R.drawable.msg_newphone, LocaleController.getString(C2797R.string.AppNavigation), LocaleController.getString(C2797R.string.AppNavigationInfo), 64, 60).setSearchable(this).setLinkAlias("appNavigationSettings", this));
        arrayList.add(UItem.asButtonWithSubtext(AppearanceItem.ICON_PACKS.getId(), C2797R.drawable.msg_sticker, LocaleController.getString(C2797R.string.IconPacks), LocaleController.getString(C2797R.string.IconPacksInfo), 64, 60).setSearchable(this).setLinkAlias("iconPacks", this));
        arrayList.add(UItem.asButtonWithSubtext(AppearanceItem.PILL_STACK.getId(), C2797R.drawable.outline_header_search, LocaleController.getString(C2797R.string.PillStackPills), LocaleController.getString(C2797R.string.PillStackPillsInfo), 64, 60).setSearchable(this).setLinkAlias("pillStack", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.Appearance)));
        arrayList.add(UItem.asCustom(AppearanceItem.FAB_SHAPE.getId(), this.fabShapeCell).setLinkAlias("fabShape", this));
        arrayList.add(UItem.asCheck(AppearanceItem.USE_SYSTEM_FONTS.getId(), LocaleController.getString(C2797R.string.UseSystemFonts)).setChecked(ExteraConfig.getUseSystemFonts()).setSearchable(this).setLinkAlias("useSystemFonts", this));
        arrayList.add(UItem.asCheck(AppearanceItem.USE_SYSTEM_EMOJI.getId(), LocaleController.getString(C2797R.string.UseSystemEmoji)).setChecked(SharedConfig.useSystemEmoji).setSearchable(this).setLinkAlias("useSystemEmoji", this));
        arrayList.add(UItem.asExteraExpandableSwitch(AppearanceItem.MD3_STYLES.getId(), LocaleController.getString(C2797R.string.MaterialDesign3), String.format(Locale.US, "%d/%d", Integer.valueOf(getMD3StylesSelectedCount(false)), Integer.valueOf(getMD3StylesSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMD3StylesSwitchClick(view);
            }
        }).setChecked(getMD3StylesSelectedCount(false) > 0).setCollapsed(!this.md3StylesExpanded).setSearchable(this).setLinkAlias("md3Styles", this));
        if (this.md3StylesExpanded) {
            arrayList.add(UItem.asRoundCheckbox(AppearanceItem.NEW_LOADING_STYLE.getId(), LocaleController.getString(C2797R.string.NewLoadingStyle)).setChecked(ExteraConfig.getNewLoadingStyle()).pad());
            arrayList.add(UItem.asRoundCheckbox(AppearanceItem.NEW_SLIDER_STYLE.getId(), LocaleController.getString(C2797R.string.NewSliderStyle)).setChecked(ExteraConfig.getNewSliderStyle()).pad());
            arrayList.add(UItem.asRoundCheckbox(AppearanceItem.NEW_SWITCH_STYLE.getId(), LocaleController.getString(C2797R.string.NewSwitchStyle)).setChecked(ExteraConfig.getNewSwitchStyle()).pad());
            arrayList.add(UItem.asRoundCheckbox(AppearanceItem.NEW_CHAT_HEADER_STYLE.getId(), LocaleController.getString(C2797R.string.ChatHeader)).setChecked(ExteraConfig.getNewChatHeaderStyle()).pad());
            arrayList.add(UItem.asRoundCheckbox(AppearanceItem.NEW_NAVIGATION_BAR_STYLE.getId(), LocaleController.getString(C2797R.string.BottomNavigationBarMode)).setChecked(ExteraConfig.getNewNavigationBarStyle()).pad());
        }
        arrayList.add(UItem.asCheck(AppearanceItem.SEPARATED_HEADERS.getId(), LocaleController.getString(C2797R.string.SeparateHeaders)).setChecked(ExteraConfig.getSectionsSeparatedHeaders()).setSearchable(this).setLinkAlias("sectionsSeparatedHeaders", this));
        arrayList.add(UItem.asCheck(AppearanceItem.DISABLE_DIVIDERS.getId(), LocaleController.getString(C2797R.string.DisableDividers)).setChecked(ExteraConfig.getDisableDividers()).setSearchable(this).setLinkAlias("disableDividers", this));
        arrayList.add(UItem.asCheck(AppearanceItem.GOOEY_AVATAR_ANIMATION.getId(), LocaleController.getString(C2797R.string.GooeyAvatarAnimation)).setChecked(ExteraConfig.getGooeyAvatarAnimation()).setSearchable(this).setLinkAlias("gooeyAvatarAnimation", this));
        arrayList.add(UItem.asCheck(AppearanceItem.CUSTOM_THEMES.getId(), LocaleController.getString(C2797R.string.CustomChatThemes)).setChecked(ExteraConfig.getCustomThemes()).setSearchable(this).setLinkAlias("customThemes", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.CustomChatThemesInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.BlurOptions)));
        arrayList.add(UItem.asCheck(AppearanceItem.GLARE_ON_ELEMENTS.getId(), LocaleController.getString(C2797R.string.GlareOnElements), LocaleController.getString(C2797R.string.GlareOnElementsInfo), true).setChecked(ExteraConfig.getGlareOnElements()).setSearchable(this).setLinkAlias("glareOnElements", this));
        arrayList.add(UItem.asCheck(AppearanceItem.FORCE_BLUR.getId(), LocaleController.getString(C2797R.string.ForceBlur)).setChecked(ExteraConfig.getForceBlur()).setSearchable(this).setLinkAlias("forceBlur", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.ForceBlurInfo)));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f1708id;
        if (i2 <= 0 || i2 > AppearanceItem.values().length) {
            return;
        }
        switch (C12052.f346x6ee27ce4[AppearanceItem.values()[uItem.f1708id - 1].ordinal()]) {
            case 1:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setSingleCornerRadius(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 2:
                showListDialog(uItem, this.titles, LocaleController.getString(C2797R.string.ActionBarTitle), ExteraConfig.getTitleText(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda11
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$0(i3);
                    }
                });
                break;
            case 3:
                presentFragment(new PillStackPreferencesActivity());
                break;
            case 4:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda16
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideStories(((Boolean) obj).booleanValue());
                    }
                });
                getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.storiesEnabledUpdate, new Object[0]);
                break;
            case 5:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda17
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideActionBarStatus(((Boolean) obj).booleanValue());
                    }
                });
                ChatListPreviewCell chatListPreviewCell = this.chatListPreviewCell;
                if (chatListPreviewCell != null) {
                    chatListPreviewCell.updateStatus(true);
                }
                this.parentLayout.rebuildFragments(0);
                break;
            case 6:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda18
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setCenterTitle(((Boolean) obj).booleanValue());
                    }
                });
                ChatListPreviewCell chatListPreviewCell2 = this.chatListPreviewCell;
                if (chatListPreviewCell2 != null) {
                    chatListPreviewCell2.updateCentered(true);
                }
                ActionBar actionBar = this.actionBar;
                if (actionBar != null) {
                    actionBar.refreshTitlePosition(true);
                }
                this.parentLayout.rebuildFragments(0);
                break;
            case 7:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda19
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideFloatingButton(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 8:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda20
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideDialogsSearchBar(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 9:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda21
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setSenderMiniAvatars(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 10:
                showListDialog(uItem, this.tabIcons, LocaleController.getString(C2797R.string.TabTitleStyle), ExteraConfig.getTabIcons().ordinal(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda22
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$1(i3);
                    }
                });
                break;
            case 11:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda23
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setTabCounter(((Boolean) obj).booleanValue());
                    }
                });
                handleTabCounterClick();
                break;
            case 12:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda1
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setHideAllChats(((Boolean) obj).booleanValue());
                    }
                });
                handleHideAllChatsClick();
                break;
            case 13:
                presentFragment(new AppNavigationPreferencesActivity());
                break;
            case 14:
                presentFragment(new IconPacksActivity());
                break;
            case 15:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda2
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setSectionsSeparatedHeaders(((Boolean) obj).booleanValue());
                    }
                });
                this.listView.invalidateItemDecorations();
                break;
            case 16:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda3
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setDisableDividers(((Boolean) obj).booleanValue());
                    }
                });
                Theme.applyCommonTheme();
                this.avatarCornersPreviewCell.invalidate();
                this.chatListPreviewCell.invalidate();
                this.fabShapeCell.invalidate();
                this.filterTabsPreviewCell.invalidate();
                this.parentLayout.rebuildFragments(0);
                break;
            case 17:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda4
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setUseSystemFonts(((Boolean) obj).booleanValue());
                    }
                });
                handleUseSystemFontsClick();
                break;
            case 18:
                handleUseSystemEmojiClick(uItem);
                break;
            case 19:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda5
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setNewSwitchStyle(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 20:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda6
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setNewLoadingStyle(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 21:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda7
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setNewChatHeaderStyle(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 22:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda8
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setNewNavigationBarStyle(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 23:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda9
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$onClick$2((Boolean) obj);
                    }
                });
                break;
            case 24:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda10
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setGooeyAvatarAnimation(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 25:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda12
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setCustomThemes(((Boolean) obj).booleanValue());
                    }
                });
                break;
            case 26:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda13
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setForceSnow(((Boolean) obj).booleanValue());
                    }
                });
                this.chatListPreviewCell.invalidate();
                break;
            case 27:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda14
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setGlareOnElements(((Boolean) obj).booleanValue());
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 28:
                toggleBooleanSettingAndRefresh(uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$$ExternalSyntheticLambda15
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.setForceBlur(((Boolean) obj).booleanValue());
                    }
                });
                handleForceBlurChange();
                break;
            case 29:
                boolean z = !this.md3StylesExpanded;
                this.md3StylesExpanded = z;
                uItem.setCollapsed(z);
                this.listView.adapter.update(true);
                break;
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity$2 */
    /* JADX INFO: loaded from: classes4.dex */
    public static /* synthetic */ class C12052 {

        /* JADX INFO: renamed from: $SwitchMap$com$exteragram$messenger$preferences$appearance$AppearancePreferencesActivity$AppearanceItem */
        static final /* synthetic */ int[] f346x6ee27ce4;

        static {
            int[] iArr = new int[AppearanceItem.values().length];
            f346x6ee27ce4 = iArr;
            try {
                iArr[AppearanceItem.SINGLE_CORNER_RADIUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.ACTION_BAR_TITLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.PILL_STACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.HIDE_STORIES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.HIDE_ACTION_BAR_STATUS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.CENTER_TITLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.HIDE_FLOATING_BUTTON.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.HIDE_DIALOGS_SEARCH_BAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.SENDER_MINI_AVATARS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.TAB_TITLE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.TAB_COUNTER.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.HIDE_ALL_CHATS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.APP_NAVIGATION_SETTINGS.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.ICON_PACKS.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.SEPARATED_HEADERS.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.DISABLE_DIVIDERS.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.USE_SYSTEM_FONTS.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.USE_SYSTEM_EMOJI.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.NEW_SWITCH_STYLE.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.NEW_LOADING_STYLE.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.NEW_CHAT_HEADER_STYLE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.NEW_NAVIGATION_BAR_STYLE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.NEW_SLIDER_STYLE.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.GOOEY_AVATAR_ANIMATION.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.CUSTOM_THEMES.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.FORCE_SNOW.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.GLARE_ON_ELEMENTS.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.FORCE_BLUR.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f346x6ee27ce4[AppearanceItem.MD3_STYLES.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(int i) {
        ExteraConfig.setTitleText(i);
        handleActionBarTitleClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(int i) {
        ExteraConfig.setTabIcons(TabIconsMode.getEntries().get(i));
        handleTabTitleClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(Boolean bool) {
        ExteraConfig.setNewSliderStyle(bool.booleanValue());
        AvatarCornersPreviewCell avatarCornersPreviewCell = this.avatarCornersPreviewCell;
        if (avatarCornersPreviewCell != null) {
            avatarCornersPreviewCell.updateSliderStyle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMD3StylesSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1513id);
        boolean z = !uItemFindItemByItemId.checked;
        ExteraConfig.setNewLoadingStyle(z);
        ExteraConfig.setNewSliderStyle(z);
        ExteraConfig.setNewSwitchStyle(z);
        ExteraConfig.setNewChatHeaderStyle(z);
        ExteraConfig.setNewNavigationBarStyle(z);
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
        AvatarCornersPreviewCell avatarCornersPreviewCell = this.avatarCornersPreviewCell;
        if (avatarCornersPreviewCell != null) {
            avatarCornersPreviewCell.updateSliderStyle();
        }
        this.parentLayout.rebuildFragments(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean, int] */
    private int getMD3StylesSelectedCount(boolean z) {
        if (z) {
            return 5;
        }
        ?? newLoadingStyle = ExteraConfig.getNewLoadingStyle();
        int i = newLoadingStyle;
        if (ExteraConfig.getNewSliderStyle()) {
            i = newLoadingStyle + 1;
        }
        int i2 = i;
        if (ExteraConfig.getNewSwitchStyle()) {
            i2 = i + 1;
        }
        int i3 = i2;
        if (ExteraConfig.getNewChatHeaderStyle()) {
            i3 = i2 + 1;
        }
        return ExteraConfig.getNewNavigationBarStyle() ? i3 + 1 : i3;
    }

    private void handleActionBarTitleClick() {
        this.chatListPreviewCell.updateStatus(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
    }

    private void handleTabTitleClick() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
    }

    private void handleTabCounterClick() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
    }

    private void handleHideAllChatsClick() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
    }

    private void handleUseSystemFontsClick() {
        AndroidUtilities.clearTypefaceCache();
        rebuildListWithStateRestore();
    }

    private void rebuildListWithStateRestore() {
        if (this.listView.getLayoutManager() != null) {
            this.recyclerViewState = this.listView.getLayoutManager().onSaveInstanceState();
        }
        this.parentLayout.rebuildFragments(1);
        if (this.listView.getLayoutManager() != null) {
            this.listView.getLayoutManager().onRestoreInstanceState(this.recyclerViewState);
        }
    }

    private void handleForceBlurChange() {
        if (SharedConfig.chatBlurEnabled() || !ExteraConfig.getForceBlur()) {
            return;
        }
        SharedConfig.toggleChatBlur();
    }

    private void handleUseSystemEmojiClick(UItem uItem) {
        SharedConfig.toggleUseSystemEmoji();
        uItem.setChecked(!SharedConfig.useSystemEmoji);
        this.parentLayout.rebuildFragments(0);
        this.listView.adapter.update(true);
    }
}
