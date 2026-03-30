package com.exteragram.messenger.plugins.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chaquo.python.PyObject;
import com.exteragram.messenger.plugins.Plugin;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.PythonPluginsEngine;
import com.exteragram.messenger.plugins.models.CustomSetting;
import com.exteragram.messenger.plugins.models.DividerSetting;
import com.exteragram.messenger.plugins.models.EditTextSetting;
import com.exteragram.messenger.plugins.models.HeaderSetting;
import com.exteragram.messenger.plugins.models.InputSetting;
import com.exteragram.messenger.plugins.models.SelectorSetting;
import com.exteragram.messenger.plugins.models.SettingItem;
import com.exteragram.messenger.plugins.models.SwitchSetting;
import com.exteragram.messenger.plugins.models.TextSetting;
import com.exteragram.messenger.preferences.utils.SettingsRegistry;
import com.exteragram.messenger.utils.system.VibratorUtils;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.Utilities;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.NotificationsCheckCell;
import org.telegram.ui.Cells.RadioColorCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.ItemOptions;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;
import org.telegram.ui.Components.UniversalRecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class PluginSettingsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private final PyObject createSubFragmentCallback;
    private final String customTitle;
    protected LinearLayoutManager layoutManager;
    private UniversalRecyclerView listView;
    private final Plugin plugin;
    private ActionBarMenuItem resetItem;
    private List<SettingItem> settingItems;
    private String settingsLinkPrefix;
    private Integer targetSettingItemId;
    private String targetSettingName;

    public PluginSettingsActivity(Plugin plugin) {
        this.plugin = plugin;
        this.customTitle = null;
        this.settingItems = null;
        this.createSubFragmentCallback = null;
        this.targetSettingName = null;
        this.targetSettingItemId = null;
        this.settingsLinkPrefix = null;
    }

    public PluginSettingsActivity(Plugin plugin, String str) {
        this.plugin = plugin;
        this.customTitle = null;
        this.settingItems = null;
        this.createSubFragmentCallback = null;
        this.targetSettingName = str;
        this.targetSettingItemId = null;
        this.settingsLinkPrefix = null;
    }

    public PluginSettingsActivity(Plugin plugin, String str, List<SettingItem> list, PyObject pyObject) {
        this(plugin, str, list, pyObject, null);
    }

    public PluginSettingsActivity(Plugin plugin, String str, List<SettingItem> list, PyObject pyObject, String str2) {
        this.plugin = plugin;
        this.customTitle = str;
        this.settingItems = list;
        this.createSubFragmentCallback = pyObject;
        this.targetSettingName = str2;
        this.targetSettingItemId = null;
        this.settingsLinkPrefix = null;
    }

    public PluginSettingsActivity setSettingsLinkPrefix(String str) {
        this.settingsLinkPrefix = str;
        return this;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.pluginSettingsRegistered);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.pluginSettingsUnregistered);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.pluginSettingsRegistered);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.pluginSettingsUnregistered);
        super.onFragmentDestroy();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0011  */
    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void didReceivedNotification(int r2, int r3, java.lang.Object... r4) {
        /*
            r1 = this;
            int r3 = org.telegram.messenger.NotificationCenter.pluginSettingsRegistered
            r0 = 0
            if (r2 != r3) goto L53
            int r2 = r4.length
            if (r2 <= 0) goto L11
            r2 = r4[r0]
            boolean r3 = r2 instanceof java.lang.String
            if (r3 == 0) goto L11
            java.lang.String r2 = (java.lang.String) r2
            goto L12
        L11:
            r2 = 0
        L12:
            com.exteragram.messenger.plugins.Plugin r3 = r1.plugin
            if (r3 == 0) goto L83
            if (r2 == 0) goto L22
            java.lang.String r3 = r3.getId()
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L83
        L22:
            com.chaquo.python.PyObject r2 = r1.createSubFragmentCallback
            if (r2 == 0) goto L2f
            com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda7 r2 = new com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda7
            r2.<init>()
            com.exteragram.messenger.plugins.PluginsController.runOnPluginsQueue(r2)
            return
        L2f:
            org.telegram.ui.Components.UniversalRecyclerView r2 = r1.listView
            if (r2 == 0) goto L83
            org.telegram.ui.Components.UniversalAdapter r2 = r2.adapter
            if (r2 == 0) goto L83
            r3 = 1
            r2.update(r3)
            org.telegram.ui.ActionBar.ActionBarMenuItem r2 = r1.resetItem
            if (r2 == 0) goto L83
            com.exteragram.messenger.plugins.PluginsController r4 = com.exteragram.messenger.plugins.PluginsController.getInstance()
            com.exteragram.messenger.plugins.Plugin r0 = r1.plugin
            java.lang.String r0 = r0.getId()
            boolean r4 = r4.hasPluginSettingsPreferences(r0)
            r0 = 1056964608(0x3f000000, float:0.5)
            org.telegram.messenger.AndroidUtilities.updateViewVisibilityAnimated(r2, r4, r0, r3)
            return
        L53:
            int r3 = org.telegram.messenger.NotificationCenter.pluginSettingsUnregistered
            if (r2 != r3) goto L83
            int r2 = r4.length
            if (r2 <= 0) goto L83
            r2 = r4[r0]
            boolean r3 = r2 instanceof java.lang.String
            if (r3 == 0) goto L83
            java.lang.String r2 = (java.lang.String) r2
            com.exteragram.messenger.plugins.Plugin r3 = r1.plugin
            if (r3 == 0) goto L83
            java.lang.String r3 = r3.getId()
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L83
            com.exteragram.messenger.plugins.PluginsController r2 = com.exteragram.messenger.plugins.PluginsController.getInstance()
            com.exteragram.messenger.plugins.Plugin r3 = r1.plugin
            java.lang.String r3 = r3.getId()
            boolean r2 = r2.hasPluginSettings(r3)
            if (r2 != 0) goto L83
            r1.finishFragment()
        L83:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.ui.PluginSettingsActivity.didReceivedNotification(int, int, java.lang.Object[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$1() {
        final List<SettingItem> arrayList = new ArrayList<>();
        try {
            PyObject pyObjectCall = this.createSubFragmentCallback.call(new Object[0]);
            if (pyObjectCall != null) {
                PluginsController.PluginsEngine pluginsEngine = PluginsController.engines.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-11130271590504L));
                Objects.requireNonNull(pluginsEngine);
                arrayList = ((PythonPluginsEngine) pluginsEngine).parsePySettingDefinitions(pyObjectCall.asList());
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didReceivedNotification$0(arrayList);
                }
            });
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$0(List list) {
        UniversalAdapter universalAdapter;
        this.settingItems = list;
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView == null || (universalAdapter = universalRecyclerView.adapter) == null) {
            return;
        }
        universalAdapter.update(true);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        ActionBar actionBar = this.actionBar;
        String name = this.customTitle;
        if (name == null) {
            name = this.plugin.getName();
        }
        actionBar.setTitle(name);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    PluginSettingsActivity.this.finishFragment();
                }
            }
        });
        if (this.createSubFragmentCallback == null && this.plugin != null) {
            ActionBarMenuItem actionBarMenuItemAddItem = this.actionBar.createMenu().addItem(0, R.drawable.msg_reset);
            this.resetItem = actionBarMenuItemAddItem;
            actionBarMenuItemAddItem.setContentDescription(LocaleController.getString(R.string.Reset));
            AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, PluginsController.getInstance().hasPluginSettingsPreferences(this.plugin.getId()), 0.5f, false);
            this.resetItem.setTag(null);
            this.resetItem.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda15
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$createView$5(view);
                }
            });
        }
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda16
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda17
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, new Utilities.Callback5Return() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda18
            @Override // org.telegram.messenger.Utilities.Callback5Return
            public final Object run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return Boolean.valueOf(this.f$0.onLongClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue()));
            }
        });
        this.listView = universalRecyclerView;
        universalRecyclerView.setSections();
        this.actionBar.setAdaptiveBackground(this.listView);
        this.listView.adapter.setApplyBackground(false);
        UniversalRecyclerView universalRecyclerView2 = this.listView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        universalRecyclerView2.setLayoutManager(linearLayoutManager);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.fragmentView = frameLayout;
        return frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), getResourceProvider());
        builder.setTitle(LocaleController.getString(R.string.ResetSettings));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString(R.string.ResetPluginSettingsInfo, this.plugin.getName())));
        builder.setPositiveButton(LocaleController.getString(R.string.Reset), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$createView$4(alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(AlertDialog alertDialog, int i) {
        View viewFindFocus;
        View view = this.fragmentView;
        if (view != null && (viewFindFocus = view.findFocus()) != null) {
            viewFindFocus.clearFocus();
        }
        AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, false, 0.5f, true);
        PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3() {
        PluginsController.getInstance().clearPluginSettingsPreferences(this.plugin.getId());
        PluginsController.getInstance().loadPluginSettings(this.plugin.getId());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2() {
        BulletinFactory.of(this).createSimpleBulletin(R.raw.info, LocaleController.formatString(R.string.ResetPluginSettings, this.plugin.getName())).show();
    }

    public void checkTargetSetting() {
        UniversalRecyclerView universalRecyclerView;
        Integer num = this.targetSettingItemId;
        if (num != null && (universalRecyclerView = this.listView) != null && universalRecyclerView.adapter != null) {
            final int iFindPositionByItemId = universalRecyclerView.findPositionByItemId(num.intValue());
            if (iFindPositionByItemId >= 0 && iFindPositionByItemId < this.listView.adapter.getItemCount()) {
                this.listView.highlightRow(new RecyclerListView.IntReturnCallback() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda14
                    @Override // org.telegram.ui.Components.RecyclerListView.IntReturnCallback
                    public final int run() {
                        return this.f$0.lambda$checkTargetSetting$6(iFindPositionByItemId);
                    }
                });
            }
            this.targetSettingItemId = null;
            return;
        }
        SettingsRegistry.getInstance().onSettingNotFound(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$checkTargetSetting$6(int i) {
        this.layoutManager.scrollToPositionWithOffset(i, AndroidUtilities.dp(60.0f));
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0240  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillItems(java.util.ArrayList<org.telegram.ui.Components.UItem> r13, org.telegram.ui.Components.UniversalAdapter r14) {
        /*
            Method dump skipped, instruction units count: 694
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.ui.PluginSettingsActivity.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(final UItem uItem, View view, int i, float f, float f2) {
        Plugin plugin;
        if (uItem == null || (plugin = this.plugin) == null) {
            return;
        }
        SettingItem settingItem = uItem.settingItem;
        try {
            if (settingItem instanceof TextSetting) {
                final TextSetting textSetting = (TextSetting) settingItem;
                if (textSetting.createSubFragmentCallback != null) {
                    PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda20
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClick$8(textSetting, uItem);
                        }
                    });
                    return;
                }
                PyObject pyObject = textSetting.onClickCallback;
                if (pyObject != null) {
                    pyObject.call(view);
                    return;
                }
            } else if (settingItem instanceof CustomSetting) {
                CustomSetting customSetting = (CustomSetting) settingItem;
                CustomSetting.Factory<?> factory = customSetting.factory;
                if (factory != null) {
                    factory.onClick(plugin, uItem, view);
                    return;
                }
                PyObject pyObject2 = customSetting.onClickCallback;
                if (pyObject2 != null) {
                    pyObject2.call(view);
                    return;
                }
                return;
            }
            Object obj = uItem.object2;
            if (obj instanceof String) {
                final String str = (String) obj;
                if (view instanceof TextCheckCell) {
                    TextCheckCell textCheckCell = (TextCheckCell) view;
                    final boolean z = !textCheckCell.isChecked();
                    textCheckCell.setChecked(z);
                    uItem.setChecked(z);
                    PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClick$9(str, z, uItem);
                        }
                    });
                    return;
                }
                if (view instanceof NotificationsCheckCell) {
                    NotificationsCheckCell notificationsCheckCell = (NotificationsCheckCell) view;
                    final boolean z2 = !notificationsCheckCell.isChecked();
                    notificationsCheckCell.setChecked(z2);
                    uItem.setChecked(z2);
                    PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClick$10(str, z2, uItem);
                        }
                    });
                    return;
                }
                if (view instanceof TextCell) {
                    if (settingItem instanceof SelectorSetting) {
                        showSelectorDialog(uItem, view, str);
                    } else if (settingItem instanceof InputSetting) {
                        showStringInputDialog(uItem, view, str);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$8(final TextSetting textSetting, final UItem uItem) {
        final List<SettingItem> arrayList = new ArrayList<>();
        try {
            PyObject pyObjectCall = textSetting.createSubFragmentCallback.call(new Object[0]);
            if (pyObjectCall != null) {
                PluginsController.PluginsEngine pluginsEngine = PluginsController.engines.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-11087321917544L));
                Objects.requireNonNull(pluginsEngine);
                arrayList = ((PythonPluginsEngine) pluginsEngine).parsePySettingDefinitions(pyObjectCall.asList());
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onClick$7(arrayList, uItem, textSetting);
                }
            });
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$7(List list, UItem uItem, TextSetting textSetting) {
        String string;
        if (list.isEmpty()) {
            return;
        }
        PluginSettingsActivity pluginSettingsActivity = new PluginSettingsActivity(this.plugin, uItem.text.toString(), list, textSetting.createSubFragmentCallback);
        StringBuilder sb = new StringBuilder();
        if (this.settingsLinkPrefix == null) {
            string = Deobfuscator$exteraGramDev$TMessagesProj.getString(-11117386688616L);
        } else {
            string = this.settingsLinkPrefix + Deobfuscator$exteraGramDev$TMessagesProj.getString(-11121681655912L);
        }
        sb.append(string);
        sb.append(uItem.settingItem.linkAlias);
        presentFragment(pluginSettingsActivity.setSettingsLinkPrefix(sb.toString()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$9(String str, boolean z, UItem uItem) {
        PluginsController.getInstance().setPluginSetting(this.plugin.getId(), str, Boolean.valueOf(z));
        SettingItem settingItem = uItem.settingItem;
        if (settingItem instanceof SwitchSetting) {
            triggerOnChange(((SwitchSetting) settingItem).onChangeCallback, str, Boolean.valueOf(z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$10(String str, boolean z, UItem uItem) {
        PluginsController.getInstance().setPluginSetting(this.plugin.getId(), str, Boolean.valueOf(z));
        SettingItem settingItem = uItem.settingItem;
        if (settingItem instanceof SwitchSetting) {
            triggerOnChange(((SwitchSetting) settingItem).onChangeCallback, str, Boolean.valueOf(z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onLongClick(final UItem uItem, View view, int i, float f, float f2) {
        CustomSetting.Factory<?> factory;
        if (uItem != null && this.plugin != null) {
            SettingItem settingItem = uItem.settingItem;
            PyObject pyObject = settingItem.onLongClickCallback;
            if (pyObject != null) {
                try {
                    pyObject.call(view);
                } catch (Exception unused) {
                }
                return true;
            }
            String str = settingItem.linkAlias;
            if (str != null && !TextUtils.isEmpty(str)) {
                view.performHapticFeedback(VibratorUtils.getType(3), 1);
                ItemOptions.makeOptions(this, view).add(R.drawable.msg_copy, LocaleController.getString(R.string.CopyLink), new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onLongClick$11(uItem);
                    }
                }).show();
                return true;
            }
            SettingItem settingItem2 = uItem.settingItem;
            if ((settingItem2 instanceof CustomSetting) && (factory = ((CustomSetting) settingItem2).factory) != null) {
                try {
                    factory.onLongClick(this.plugin, uItem, view);
                } catch (Exception unused2) {
                }
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$11(UItem uItem) {
        if (AndroidUtilities.addToClipboard(uItem.settingItem.getLink(this.plugin.getId(), this.settingsLinkPrefix))) {
            BulletinFactory.of(this).createCopyBulletin(LocaleController.getString(R.string.LinkCopied)).show();
        }
    }

    private void showStringInputDialog(UItem uItem, final View view, final String str) {
        if (getParentActivity() != null) {
            SettingItem settingItem = uItem.settingItem;
            if (settingItem instanceof InputSetting) {
                final InputSetting inputSetting = (InputSetting) settingItem;
                final AlertDialog[] alertDialogArr = new AlertDialog[1];
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), getResourceProvider());
                builder.setTitle(uItem.text);
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(1);
                if (inputSetting.subtext != null) {
                    TextView textView = new TextView(getContext());
                    textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, getResourceProvider()));
                    textView.setTextSize(1, 16.0f);
                    textView.setText(inputSetting.subtext);
                    linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 24.0f, 5.0f, 24.0f, 12.0f));
                }
                final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(getContext());
                editTextBoldCursor.lineYFix = true;
                final Runnable runnable = new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$showStringInputDialog$13(editTextBoldCursor, alertDialogArr, view, str, inputSetting);
                    }
                };
                editTextBoldCursor.setTextSize(1, 18.0f);
                editTextBoldCursor.setText(PluginsController.getInstance().getPluginSettingString(this.plugin.getId(), str, inputSetting.defaultValue));
                editTextBoldCursor.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, getResourceProvider()));
                editTextBoldCursor.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText, getResourceProvider()));
                editTextBoldCursor.setHintText(LocaleController.getString(R.string.EnterValue));
                editTextBoldCursor.setFocusable(true);
                editTextBoldCursor.setInputType(147457);
                int i = Theme.key_windowBackgroundWhiteInputFieldActivated;
                editTextBoldCursor.setCursorColor(Theme.getColor(i, getResourceProvider()));
                editTextBoldCursor.setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField, getResourceProvider()), Theme.getColor(i, getResourceProvider()), Theme.getColor(Theme.key_text_RedRegular, getResourceProvider()));
                editTextBoldCursor.setBackgroundDrawable(null);
                editTextBoldCursor.setPadding(0, AndroidUtilities.dp(6.0f), 0, AndroidUtilities.dp(6.0f));
                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, -2, 24.0f, 0.0f, 24.0f, 10.0f));
                builder.makeCustomMaxHeight();
                builder.setView(linearLayout);
                builder.setWidth(AndroidUtilities.dp(292.0f));
                builder.setPositiveButton(LocaleController.getString(R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda10
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i2) {
                        runnable.run();
                    }
                });
                builder.setNegativeButton(LocaleController.getString(R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda11
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i2) {
                        alertDialog.dismiss();
                    }
                });
                AlertDialog alertDialogCreate = builder.create();
                alertDialogArr[0] = alertDialogCreate;
                alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda12
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AndroidUtilities.hideKeyboard(editTextBoldCursor);
                    }
                });
                alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda13
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        PluginSettingsActivity.$r8$lambda$M8i9Tfi03snw3u97WfwnIT6rmn8(editTextBoldCursor, dialogInterface);
                    }
                });
                alertDialogArr[0].setDismissDialogByButtons(false);
                showDialog(alertDialogArr[0]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showStringInputDialog$13(EditTextBoldCursor editTextBoldCursor, AlertDialog[] alertDialogArr, View view, final String str, final InputSetting inputSetting) {
        final String string = editTextBoldCursor.getText().toString();
        AlertDialog alertDialog = alertDialogArr[0];
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        ((TextCell) view).setValue(string, true);
        PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showStringInputDialog$12(str, string, inputSetting);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showStringInputDialog$12(String str, String str2, InputSetting inputSetting) {
        PluginsController.getInstance().setPluginSetting(this.plugin.getId(), str, str2);
        triggerOnChange(inputSetting.onChangeCallback, str, str2);
    }

    public static /* synthetic */ void $r8$lambda$M8i9Tfi03snw3u97WfwnIT6rmn8(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
        editTextBoldCursor.requestFocus();
        editTextBoldCursor.setSelection(editTextBoldCursor.length());
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    private void showSelectorDialog(UItem uItem, final View view, final String str) {
        if (getParentActivity() != null) {
            SettingItem settingItem = uItem.settingItem;
            if (settingItem instanceof SelectorSetting) {
                final SelectorSetting selectorSetting = (SelectorSetting) settingItem;
                final AtomicReference atomicReference = new AtomicReference();
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(1);
                final String[] strArr = selectorSetting.items;
                final int i = 0;
                while (i < strArr.length) {
                    RadioColorCell radioColorCell = new RadioColorCell(getParentActivity());
                    radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                    radioColorCell.setTextAndValue(strArr[i], PluginsController.getInstance().getPluginSettingInt(this.plugin.getId(), str, selectorSetting.defaultValue) == i);
                    radioColorCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), 2));
                    linearLayout.addView(radioColorCell);
                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda19
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.lambda$showSelectorDialog$19(atomicReference, view, strArr, i, str, selectorSetting, view2);
                        }
                    });
                    i++;
                }
                AlertDialog alertDialogCreate = new AlertDialog.Builder(getParentActivity()).setTitle(uItem.text).setView(linearLayout).setNegativeButton(LocaleController.getString(R.string.Cancel), null).create();
                atomicReference.set(alertDialogCreate);
                showDialog(alertDialogCreate);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectorDialog$19(AtomicReference atomicReference, View view, String[] strArr, final int i, final String str, final SelectorSetting selectorSetting, View view2) {
        if (atomicReference.get() != null) {
            ((Dialog) atomicReference.get()).dismiss();
        }
        ((TextCell) view).setValue(strArr[i], true);
        PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSelectorDialog$18(str, i, selectorSetting);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectorDialog$18(String str, int i, SelectorSetting selectorSetting) {
        PluginsController.getInstance().setPluginSetting(this.plugin.getId(), str, Integer.valueOf(i));
        triggerOnChange(selectorSetting.onChangeCallback, str, Integer.valueOf(i));
    }

    private int getStableId(SettingItem settingItem) {
        if (settingItem instanceof SwitchSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10675005057128L), ((SwitchSetting) settingItem).key);
        }
        if (settingItem instanceof InputSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10705069828200L), ((InputSetting) settingItem).key);
        }
        if (settingItem instanceof EditTextSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10730839631976L), ((EditTextSetting) settingItem).key);
        }
        if (settingItem instanceof SelectorSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10752314468456L), ((SelectorSetting) settingItem).key);
        }
        if (settingItem instanceof HeaderSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10790969174120L), ((HeaderSetting) settingItem).text);
        }
        if (settingItem instanceof DividerSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10821033945192L), ((DividerSetting) settingItem).text);
        }
        if (settingItem instanceof TextSetting) {
            return Objects.hash(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10855393683560L), ((TextSetting) settingItem).text);
        }
        if (settingItem instanceof CustomSetting) {
            CustomSetting customSetting = (CustomSetting) settingItem;
            String string = Deobfuscator$exteraGramDev$TMessagesProj.getString(-10876868520040L);
            UItem uItem = customSetting.item;
            return Objects.hash(string, Integer.valueOf(uItem != null ? uItem.id : customSetting.factory.hashCode()));
        }
        return settingItem.hashCode();
    }

    private void triggerOnChange(final PyObject pyObject, final String str, final Object obj) {
        PluginsController.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.PluginSettingsActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$triggerOnChange$20(pyObject, obj, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerOnChange$20(PyObject pyObject, Object obj, String str) {
        if (pyObject != null) {
            try {
                pyObject.call(obj);
            } catch (Exception e) {
                FileLog.e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-10906933291112L) + this.plugin.getId() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-11078731982952L) + str, e);
            }
        }
    }
}
