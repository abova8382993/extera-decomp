package com.exteragram.messenger.preferences.utils;

import android.text.TextUtils;
import android.view.View;
import androidx.camera.core.ImageCapture$$ExternalSyntheticBackport1;
import com.android.tools.p010r8.RecordTag;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.p011ai.network.Client$ImagePayload$$ExternalSyntheticRecord1;
import com.exteragram.messenger.p011ai.p012ui.activities.AiPreferencesActivity;
import com.exteragram.messenger.pillstack.p017ui.PillStackPreferencesActivity;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.p018ui.PluginsInfoActivity;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.preferences.GeneralPreferencesActivity;
import com.exteragram.messenger.preferences.MainPreferencesActivity;
import com.exteragram.messenger.preferences.OtherPreferencesActivity;
import com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticBackport1;
import com.exteragram.messenger.preferences.appearance.AppNavigationPreferencesActivity;
import com.exteragram.messenger.preferences.appearance.AppearancePreferencesActivity;
import com.exteragram.messenger.preferences.chats.ChatsPreferencesActivity;
import com.exteragram.messenger.preferences.utils.SettingsRegistry;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import kotlin.time.DurationKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ChatEditActivity;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.p035ui.ProfileActivity;

/* JADX INFO: loaded from: classes.dex */
public class SettingsRegistry {
    private static final Map<Class<? extends BaseFragment>, Integer> categoriesIcons = OtherPreferencesActivity$$ExternalSyntheticBackport1.m280m(new Map.Entry[]{new AbstractMap.SimpleEntry(MainPreferencesActivity.class, Integer.valueOf(C2797R.drawable.extera_outline)), new AbstractMap.SimpleEntry(GeneralPreferencesActivity.class, Integer.valueOf(C2797R.drawable.msg_media)), new AbstractMap.SimpleEntry(AppearancePreferencesActivity.class, Integer.valueOf(C2797R.drawable.msg_theme)), new AbstractMap.SimpleEntry(ChatsPreferencesActivity.class, Integer.valueOf(C2797R.drawable.msg_discussion)), new AbstractMap.SimpleEntry(PluginsInfoActivity.class, Integer.valueOf(C2797R.drawable.msg_plugins)), new AbstractMap.SimpleEntry(OtherPreferencesActivity.class, Integer.valueOf(C2797R.drawable.msg_fave)), new AbstractMap.SimpleEntry(AiPreferencesActivity.class, Integer.valueOf(C2797R.drawable.msg_bot)), new AbstractMap.SimpleEntry(AppNavigationPreferencesActivity.class, Integer.valueOf(C2797R.drawable.msg_list)), new AbstractMap.SimpleEntry(PillStackPreferencesActivity.class, Integer.valueOf(C2797R.drawable.outline_header_search))});
    public static List<String> newFeatures = ImageCapture$$ExternalSyntheticBackport1.m73m(new String[]{"translationTargetLanguage", "relativeLastSeen", "hideArchiveFolder", "hideReactions", "disableGreetingSticker", "showOnlineStatus", "showResultsBeforeVoting", "Chats-MessageMenu-Repeat", "Camera-ExtendedSettings-SeamlessSwitching", "hideFloatingButton", "appNavigationSettings", "pillStack", "md3Styles", "predictiveBackAnimation", "bottomNavigationBarMode", "predictiveBackAnimation", "navigationDrawer", "pluginsDisableArtOpts", "Plugins-Python-SDK"});
    private boolean entriesFetched;
    private final ConcurrentHashMap<Integer, Entry> preparedEntries = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Entry> entriesStringAlias = new ConcurrentHashMap<>();

    public static class SingletonHolder {
        private static final SettingsRegistry INSTANCE = new SettingsRegistry();
    }

    public static SettingsRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static boolean isValidForSearch(UItem uItem) {
        if (uItem.f1708id != 0 && !TextUtils.isEmpty(uItem.text)) {
            return true;
        }
        Integer numValueOf = Integer.valueOf(uItem.f1708id);
        Integer numValueOf2 = Integer.valueOf(uItem.viewType);
        View view = uItem.view;
        FileLog.m1046e(String.format("[Extera] UItems with ID 0 or empty text cannot be added as search result. (UItem ID: %s; View type: %s; View: %s; Text: %s; Subtext: %s)", numValueOf, numValueOf2, view == null ? null : view.getClass().getName(), uItem.text, TextUtils.concat(uItem.subtext, uItem.animatedText)));
        return false;
    }

    public static boolean isValidForLinkAliases(UItem uItem) {
        int i = uItem.f1708id;
        if (i != 0) {
            return true;
        }
        Integer numValueOf = Integer.valueOf(i);
        Integer numValueOf2 = Integer.valueOf(uItem.viewType);
        View view = uItem.view;
        FileLog.m1046e(String.format("[Extera] Cannot set link aliases for UItems with ID 0. (UItem ID: %s; View type: %s; View: %s; Text: %s; Subtext: %s)", numValueOf, numValueOf2, view == null ? null : view.getClass().getName(), uItem.text, TextUtils.concat(uItem.subtext, uItem.animatedText)));
        return false;
    }

    public void addSearchEntry(BaseFragment baseFragment, UItem uItem) {
        if (isValidForSearch(uItem)) {
            Entry entryFromUItem = Entry.fromUItem(baseFragment, uItem);
            if (!this.preparedEntries.containsKey(Integer.valueOf(generateGUIDForUItem(baseFragment.getClass(), uItem)))) {
                FileLog.m1045d("[Extera] Added an entry: " + entryFromUItem);
            }
            this.preparedEntries.putIfAbsent(Integer.valueOf(entryFromUItem.guid), entryFromUItem);
        }
    }

    public static boolean markAsNewFeature(String str) {
        if (!newFeatures.contains(str) || ExteraConfig.getDoNotMarkAsNew().contains(str)) {
            return false;
        }
        Long l = ExteraConfig.getNewFeaturesShowedAt().get(str);
        if (l == null || l.longValue() == 0) {
            ExteraConfig.getNewFeaturesShowedAt().put(str, Long.valueOf(System.currentTimeMillis()));
            ExteraConfig.getEditor().putString("newFeaturesShowedAt", ExteraConfig.getGSON().toJson(ExteraConfig.getNewFeaturesShowedAt())).apply();
            return true;
        }
        if (Math.abs(System.currentTimeMillis() - l.longValue()) <= DurationKt.MILLIS_IN_DAY) {
            return true;
        }
        ExteraConfig.getNewFeaturesShowedAt().remove(str);
        ExteraConfig.getEditor().putString("newFeaturesShowedAt", ExteraConfig.getGSON().toJson(ExteraConfig.getNewFeaturesShowedAt()));
        ExteraConfig.getDoNotMarkAsNew().add(str);
        ExteraConfig.getEditor().putString("doNotMarkAsNew", ExteraConfig.getGSON().toJson(ExteraConfig.getDoNotMarkAsNew())).apply();
        return false;
    }

    public void addLinkAliasForOption(String str, BaseFragment baseFragment, UItem uItem) {
        CharSequence charSequence;
        if (isValidForLinkAliases(uItem)) {
            if (markAsNewFeature(str) && (charSequence = uItem.text) != null && charSequence.length() > 0 && uItem.text.toString().charAt(uItem.text.toString().length() - 1) != 'd') {
                uItem.text = ChatEditActivity.applyNewSpan(uItem.text.toString());
            }
            if (this.entriesStringAlias.containsKey(str)) {
                FileLog.m1045d("[Extera] Key '" + str + "' already linked to an entry.");
                return;
            }
            Entry entryFromUItem = this.preparedEntries.get(Integer.valueOf(generateGUIDForUItem(baseFragment.getClass(), uItem)));
            if (entryFromUItem == null) {
                entryFromUItem = Entry.fromUItem(baseFragment, uItem);
            }
            FileLog.m1045d(String.format("[Extera] Added link alias %s for an entry %s", str, entryFromUItem));
            this.entriesStringAlias.put(str, entryFromUItem);
        }
    }

    public void handleLink(String str, String str2) {
        FileLog.m1045d("[Extera] Setting link handler called with alias " + str);
        if (str2 != null && !TextUtils.isEmpty(str2)) {
            PluginsController.openPluginSettings(str2, str);
            return;
        }
        createEntriesIfNeeded();
        Entry entry = this.entriesStringAlias.get(str);
        if (entry == null) {
            onSettingNotFound();
            return;
        }
        FileLog.m1045d("[Extera] Found entry for alias: " + entry);
        FileLog.m1045d("[Extera] Opening fragment...");
        openActivity(entry.fragmentClass, Integer.valueOf(entry.itemId));
    }

    public void onSettingNotFound() {
        onSettingNotFound(LaunchActivity.getLastFragment());
    }

    public void onSettingNotFound(BaseFragment baseFragment) {
        BulletinFactory.m1143of(baseFragment).createEmojiBulletin("🤷\u200d♂️", LocaleController.getString(C2797R.string.NoSuchSetting)).show();
    }

    public String getFirstSettingLink(Class<? extends BaseFragment> cls, UItem uItem) {
        final int iGenerateGUIDForUItem = generateGUIDForUItem(cls, uItem);
        Map.Entry<String, Entry> entryOrElse = this.entriesStringAlias.entrySet().stream().filter(new Predicate() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SettingsRegistry.$r8$lambda$p4BQB7CEiJF3IYJpUIO4QjqPKlE(iGenerateGUIDForUItem, (Map.Entry) obj);
            }
        }).findFirst().orElse(null);
        if (entryOrElse == null) {
            return null;
        }
        return "https://t.me/exteraSettings?s=" + entryOrElse.getKey();
    }

    public static /* synthetic */ boolean $r8$lambda$p4BQB7CEiJF3IYJpUIO4QjqPKlE(int i, Map.Entry entry) {
        return ((Entry) entry.getValue()).guid == i;
    }

    public ProfileActivity.SearchAdapter.SearchResult[] getSearchResults(final ProfileActivity.SearchAdapter searchAdapter) {
        createEntriesIfNeeded();
        return (ProfileActivity.SearchAdapter.SearchResult[]) this.preparedEntries.values().stream().map(new Function() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SettingsRegistry.Entry) obj).toSearchResult(searchAdapter);
            }
        }).toArray(new IntFunction() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return SettingsRegistry.$r8$lambda$Uq61hhP3TlTJmft55hT5WjpJsx0(i);
            }
        });
    }

    public static /* synthetic */ ProfileActivity.SearchAdapter.SearchResult[] $r8$lambda$Uq61hhP3TlTJmft55hT5WjpJsx0(int i) {
        return new ProfileActivity.SearchAdapter.SearchResult[i];
    }

    public int getCategoryIcon(Class<? extends BaseFragment> cls) {
        return ((Integer) SettingsRegistry$$ExternalSyntheticBackport1.m285m(categoriesIcons.get(cls), 0)).intValue();
    }

    public BaseFragment initiateFragment(Class<? extends BaseFragment> cls) {
        try {
            BaseFragment lastFragment = LaunchActivity.getLastFragment();
            if (lastFragment == null) {
                return null;
            }
            BaseFragment baseFragmentNewInstance = cls.getDeclaredConstructor(null).newInstance(null);
            baseFragmentNewInstance.setParentFragment(lastFragment);
            baseFragmentNewInstance.createActionBar(lastFragment.getContext());
            return baseFragmentNewInstance;
        } catch (Exception e) {
            FileLog.m1048e(e);
            return null;
        }
    }

    public void openActivity(Class<? extends BaseFragment> cls, final Integer num) {
        final BaseFragment baseFragmentInitiateFragment;
        final BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment == null || (baseFragmentInitiateFragment = initiateFragment(cls)) == null) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                lastFragment.presentFragment(baseFragmentInitiateFragment);
            }
        });
        if (num == null || !(baseFragmentInitiateFragment instanceof BasePreferencesActivity)) {
            return;
        }
        final BasePreferencesActivity basePreferencesActivity = (BasePreferencesActivity) baseFragmentInitiateFragment;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                basePreferencesActivity.scrollToItem(num.intValue());
            }
        });
    }

    private void createEntriesIfNeeded() {
        if (this.entriesFetched) {
            return;
        }
        FileLog.m1045d("[Extera] Initialising activities...");
        categoriesIcons.keySet().forEach(new Consumer() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.initiateFragment((Class) obj);
            }
        });
        this.entriesFetched = true;
    }

    public static int generateGUIDForUItem(Class<?> cls, UItem uItem) {
        return Objects.hash(cls.getName(), Integer.valueOf(uItem.f1708id));
    }

    public static final class Entry extends RecordTag {
        private final Class<? extends BaseFragment> fragmentClass;
        private final int guid;
        private final int icon;
        private final int itemId;
        private final String subtext;
        private final String title;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.guid == entry.guid && this.itemId == entry.itemId && this.icon == entry.icon && Objects.equals(this.title, entry.title) && Objects.equals(this.subtext, entry.subtext) && Objects.equals(this.fragmentClass, entry.fragmentClass);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.guid), Integer.valueOf(this.itemId), this.title, this.subtext, Integer.valueOf(this.icon), this.fragmentClass};
        }

        private Entry(int i, int i2, String str, String str2, int i3, Class<? extends BaseFragment> cls) {
            this.guid = i;
            this.itemId = i2;
            this.title = str;
            this.subtext = str2;
            this.icon = i3;
            this.fragmentClass = cls;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return SettingsRegistry$Entry$$ExternalSyntheticRecord0.m286m(this.guid, this.itemId, this.icon, this.title, this.subtext, this.fragmentClass);
        }

        public final String toString() {
            return Client$ImagePayload$$ExternalSyntheticRecord1.m245m($record$getFieldsAsObjects(), Entry.class, "guid;itemId;title;subtext;icon;fragmentClass");
        }

        public static Entry fromUItem(BaseFragment baseFragment, UItem uItem) {
            Class<?> cls = baseFragment.getClass();
            CharSequence charSequence = uItem.text;
            return new Entry(SettingsRegistry.generateGUIDForUItem(cls, uItem), uItem.f1708id, charSequence == null ? null : String.valueOf(charSequence), baseFragment instanceof BasePreferencesActivity ? ((BasePreferencesActivity) baseFragment).getTitle() : null, SettingsRegistry.getInstance().getCategoryIcon(cls), cls);
        }

        public ProfileActivity.SearchAdapter.SearchResult toSearchResult(ProfileActivity.SearchAdapter searchAdapter) {
            Objects.requireNonNull(searchAdapter);
            return new ProfileActivity.SearchAdapter.SearchResult(searchAdapter, this.guid, this.title, String.valueOf(this.itemId), this.subtext, this.icon, new Runnable() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$Entry$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$toSearchResult$0();
                }
            });
        }

        public /* synthetic */ void lambda$toSearchResult$0() {
            SettingsRegistry.getInstance().openActivity(this.fragmentClass, Integer.valueOf(this.itemId));
        }
    }
}
