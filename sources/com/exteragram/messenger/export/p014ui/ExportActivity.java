package com.exteragram.messenger.export.p014ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.export.ExportSettings;
import com.exteragram.messenger.export.controllers.ExportController;
import com.exteragram.messenger.export.output.AbstractWriter;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.utils.p020ui.PopupUtils;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.p035ui.Cells.TextCheckCell2;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes4.dex */
public class ExportActivity extends BasePreferencesActivity implements NotificationCenter.NotificationCenterDelegate {
    private static final CharSequence[] formats = {"HTML", "JSON", "HTML and JSON"};
    private boolean chatsSettingsExpanded;
    private boolean exportSettingsExpanded;
    private boolean mediaSettingsExpanded;
    private final TLRPC.InputPeer peer;
    private final ExportSettings settings = new ExportSettings();

    public enum ExportItem {
        HEADER,
        EXPORT_SETTINGS,
        ACCOUNT_INFO,
        CONTACTS_LIST,
        STORY_ARCHIVE,
        ACTIVE_SESSIONS,
        CHATS_SETTINGS,
        PERSONAL_CHATS,
        BOT_CHATS,
        PRIVATE_GROUPS,
        PRIVATE_CHANNELS,
        PUBLIC_GROUPS,
        PUBLIC_CHANNELS,
        MEDIA_SETTINGS,
        PHOTOS,
        VIDEOS,
        VOICE_MESSAGES,
        VIDEO_MESSAGES,
        STICKERS,
        GIFS,
        FILES,
        FORMAT,
        START_EXPORT,
        VIEW_JSON_EXPORT;

        public int getId() {
            return ordinal() + 1;
        }
    }

    public ExportActivity(TLRPC.InputPeer inputPeer) {
        this.peer = inputPeer;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.INITIALIZATING_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.DIALOGS_LIST_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.PERSONAL_INFO_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.USERPICS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.STORIES_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.CONTACTS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.SESSIONS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.OTHER_DATA_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.DIALOGS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, ExportController.FINISH_NOTIFICATION);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.INITIALIZATING_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.DIALOGS_LIST_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.PERSONAL_INFO_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.USERPICS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.STORIES_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.CONTACTS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.SESSIONS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.OTHER_DATA_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.DIALOGS_NOTIFICATION);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, ExportController.FINISH_NOTIFICATION);
        super.onFragmentDestroy();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        String str;
        if (i == ExportController.FINISH_NOTIFICATION) {
            BulletinFactory.m1143of(this).createSimpleBulletin(C2797R.raw.contact_check, "Export complete!").show();
            return;
        }
        ExportController.ProcessingState processingState = (ExportController.ProcessingState) objArr[0];
        float f = processingState.substepsPassed / processingState.substepsTotal;
        if (processingState.bytesCount > 0) {
            str = String.format(Locale.US, "Downloading %s\n%s / %s", processingState.bytesName, AndroidUtilities.formatFileSize(processingState.bytesLoaded), AndroidUtilities.formatFileSize(processingState.bytesCount));
        } else if (processingState.entityCount > 0) {
            str = String.format(Locale.US, "Exporting %s (%d / %d)\nMessage %d / %d", processingState.entityName, Integer.valueOf(processingState.entityIndex + 1), Integer.valueOf(processingState.entityCount), Integer.valueOf(processingState.itemIndex), Integer.valueOf(processingState.itemCount));
        } else {
            str = "Exporting " + processingState.step.name();
        }
        FileLog.m1046e("[EXPORT] " + str + ", " + f);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return "Export Chats";
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeader("Export settings"));
        int id = ExportItem.EXPORT_SETTINGS.getId();
        Locale locale = Locale.US;
        arrayList.add(UItem.asExteraExpandableSwitch(id, "Main settings", String.format(locale, "%d/4", Integer.valueOf(getExportSettingsCount())), new View.OnClickListener() { // from class: com.exteragram.messenger.export.ui.ExportActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleExportSettingsSwitchClick(view);
            }
        }).setChecked(getExportSettingsCount() > 0).setCollapsed(!this.exportSettingsExpanded));
        if (this.exportSettingsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ExportItem.ACCOUNT_INFO.getId(), "Account Info").setChecked((this.settings.types & 1) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.CONTACTS_LIST.getId(), "Contacts").setChecked((this.settings.types & 4) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.STORY_ARCHIVE.getId(), "Stories").setChecked((this.settings.types & 2048) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.ACTIVE_SESSIONS.getId(), "Sessions").setChecked((this.settings.types & 8) != 0).pad());
        }
        arrayList.add(UItem.asExteraExpandableSwitch(ExportItem.CHATS_SETTINGS.getId(), "Chats settings", String.format(locale, "%d/6", Integer.valueOf(getChatsSettingsCount())), new View.OnClickListener() { // from class: com.exteragram.messenger.export.ui.ExportActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleChatsSettingsSwitchClick(view);
            }
        }).setChecked(getChatsSettingsCount() > 0).setCollapsed(!this.chatsSettingsExpanded));
        if (this.chatsSettingsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ExportItem.PERSONAL_CHATS.getId(), "Personal chats").setChecked((this.settings.types & 32) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.BOT_CHATS.getId(), "Bots").setChecked((this.settings.types & 64) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.PRIVATE_GROUPS.getId(), "Private groups").setChecked((this.settings.types & 128) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.PRIVATE_CHANNELS.getId(), "Private channels").setChecked((this.settings.types & 512) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.PUBLIC_GROUPS.getId(), "Public groups").setChecked((this.settings.types & 256) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.PUBLIC_CHANNELS.getId(), "Public channels").setChecked((this.settings.types & 1024) != 0).pad());
        }
        arrayList.add(UItem.asExteraExpandableSwitch(ExportItem.MEDIA_SETTINGS.getId(), "Media settings", String.format(locale, "%d/7", Integer.valueOf(getMediaSettingsCount())), new View.OnClickListener() { // from class: com.exteragram.messenger.export.ui.ExportActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMediaSettingsSwitchClick(view);
            }
        }).setChecked(getMediaSettingsCount() > 0).setCollapsed(!this.mediaSettingsExpanded));
        if (this.mediaSettingsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ExportItem.PHOTOS.getId(), "Photos").setChecked((this.settings.media.type & 1) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.VIDEOS.getId(), "Videos").setChecked((this.settings.media.type & 2) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.VOICE_MESSAGES.getId(), "Voice messages").setChecked((this.settings.media.type & 4) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.VIDEO_MESSAGES.getId(), "Video messages").setChecked((this.settings.media.type & 8) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.STICKERS.getId(), "Stickers").setChecked((this.settings.media.type & 16) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.GIFS.getId(), "GIFs").setChecked((this.settings.media.type & 32) != 0).pad());
            arrayList.add(UItem.asRoundCheckbox(ExportItem.FILES.getId(), "Files").setChecked((this.settings.media.type & 64) != 0).pad());
        }
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asButton(ExportItem.FORMAT.getId(), "Select export result type", formats[getIndexOfFormat()]));
        arrayList.add(UItem.asButton(ExportItem.START_EXPORT.getId(), "Start Export"));
        arrayList.add(UItem.asButton(ExportItem.VIEW_JSON_EXPORT.getId(), "Open Json Export"));
        arrayList.add(UItem.asShadow("Here you can export your chats."));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f1708id;
        if (i2 <= 0 || i2 > ExportItem.values().length) {
            return;
        }
        switch (C11361.f334x2ec4b300[ExportItem.values()[uItem.f1708id - 1].ordinal()]) {
            case 1:
                this.exportSettingsExpanded = !this.exportSettingsExpanded;
                break;
            case 2:
                this.chatsSettingsExpanded = !this.chatsSettingsExpanded;
                break;
            case 3:
                this.mediaSettingsExpanded = !this.mediaSettingsExpanded;
                break;
            case 4:
                toggleMainSetting(1);
                break;
            case 5:
                toggleMainSetting(4);
                break;
            case 6:
                toggleMainSetting(2048);
                break;
            case 7:
                toggleMainSetting(8);
                break;
            case 8:
                toggleMainSetting(32);
                break;
            case 9:
                toggleMainSetting(64);
                break;
            case 10:
                toggleMainSetting(128);
                break;
            case 11:
                toggleMainSetting(512);
                break;
            case 12:
                toggleMainSetting(256);
                break;
            case 13:
                toggleMainSetting(1024);
                break;
            case 14:
                toggleMediaSetting(1);
                break;
            case 15:
                toggleMediaSetting(2);
                break;
            case 16:
                toggleMediaSetting(4);
                break;
            case 17:
                toggleMediaSetting(8);
                break;
            case 18:
                toggleMediaSetting(16);
                break;
            case 19:
                toggleMediaSetting(32);
                break;
            case 20:
                toggleMediaSetting(64);
                break;
            case 21:
                showListDialog(uItem, formats, "Select export result type", getIndexOfFormat(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.export.ui.ExportActivity$$ExternalSyntheticLambda0
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$0(i3);
                    }
                });
                break;
            case 22:
                TLRPC.InputPeer inputPeer = this.peer;
                if (inputPeer != null) {
                    this.settings.singlePeer = inputPeer;
                }
                BulletinFactory.m1143of(this).createErrorBulletin("Starting export...").show();
                this.settings.media.sizeLimit = 2097152000L;
                ExportController.getInstance(UserConfig.selectedAccount).startExport(this.settings);
                break;
            case 23:
                Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivityForResult(Intent.createChooser(intent, "Choose a directory"), 1337);
                break;
        }
        this.listView.adapter.update(true);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.export.ui.ExportActivity$1 */
    public static /* synthetic */ class C11361 {

        /* JADX INFO: renamed from: $SwitchMap$com$exteragram$messenger$export$ui$ExportActivity$ExportItem */
        static final /* synthetic */ int[] f334x2ec4b300;

        static {
            int[] iArr = new int[ExportItem.values().length];
            f334x2ec4b300 = iArr;
            try {
                iArr[ExportItem.EXPORT_SETTINGS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f334x2ec4b300[ExportItem.CHATS_SETTINGS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f334x2ec4b300[ExportItem.MEDIA_SETTINGS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f334x2ec4b300[ExportItem.ACCOUNT_INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f334x2ec4b300[ExportItem.CONTACTS_LIST.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f334x2ec4b300[ExportItem.STORY_ARCHIVE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f334x2ec4b300[ExportItem.ACTIVE_SESSIONS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f334x2ec4b300[ExportItem.PERSONAL_CHATS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f334x2ec4b300[ExportItem.BOT_CHATS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f334x2ec4b300[ExportItem.PRIVATE_GROUPS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f334x2ec4b300[ExportItem.PRIVATE_CHANNELS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f334x2ec4b300[ExportItem.PUBLIC_GROUPS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f334x2ec4b300[ExportItem.PUBLIC_CHANNELS.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f334x2ec4b300[ExportItem.PHOTOS.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f334x2ec4b300[ExportItem.VIDEOS.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f334x2ec4b300[ExportItem.VOICE_MESSAGES.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f334x2ec4b300[ExportItem.VIDEO_MESSAGES.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f334x2ec4b300[ExportItem.STICKERS.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f334x2ec4b300[ExportItem.GIFS.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f334x2ec4b300[ExportItem.FILES.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f334x2ec4b300[ExportItem.FORMAT.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f334x2ec4b300[ExportItem.START_EXPORT.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f334x2ec4b300[ExportItem.VIEW_JSON_EXPORT.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(int i) {
        if (i == 0) {
            this.settings.format = AbstractWriter.Format.Html;
            return;
        }
        ExportSettings exportSettings = this.settings;
        if (i == 1) {
            exportSettings.format = AbstractWriter.Format.Json;
        } else {
            exportSettings.format = AbstractWriter.Format.HtmlAndJson;
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        super.onActivityResultFragment(i, i2, intent);
        if (i == 1337 && i2 == -1) {
            try {
                Uri data = intent.getData();
                presentFragment(new DialogsView(AndroidPickerUtils.getPath(getParentActivity(), DocumentsContract.buildDocumentUriUsingTree(data, DocumentsContract.getTreeDocumentId(data)))));
            } catch (Exception e) {
                if (ExteraConfig.getUseGoogleCrashlytics()) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExportSettingsSwitchClick(View view) {
        setExportSettings(!((TextCheckCell2) view).isChecked());
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChatsSettingsSwitchClick(View view) {
        setChatsSettings(!((TextCheckCell2) view).isChecked());
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMediaSettingsSwitchClick(View view) {
        setMediaSettings(!((TextCheckCell2) view).isChecked());
        this.listView.adapter.update(true);
    }

    private void toggleMainSetting(int i) {
        ExportSettings exportSettings = this.settings;
        exportSettings.types = i ^ exportSettings.types;
    }

    private void toggleMediaSetting(int i) {
        ExportSettings.MediaSettings mediaSettings = this.settings.media;
        mediaSettings.type = i ^ mediaSettings.type;
    }

    private void setExportSettings(boolean z) {
        ExportSettings exportSettings = this.settings;
        if (z) {
            exportSettings.types |= 2061;
        } else {
            exportSettings.types &= -2062;
        }
    }

    private void setChatsSettings(boolean z) {
        ExportSettings exportSettings = this.settings;
        if (z) {
            exportSettings.types |= 2016;
        } else {
            exportSettings.types &= -2017;
        }
    }

    private void setMediaSettings(boolean z) {
        ExportSettings exportSettings = this.settings;
        if (z) {
            exportSettings.media.type |= 127;
        } else {
            exportSettings.media.type &= -128;
        }
    }

    private int getExportSettingsCount() {
        int i = this.settings.types;
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 4) != 0) {
            i2++;
        }
        if ((i & 2048) != 0) {
            i2++;
        }
        return (i & 8) != 0 ? i2 + 1 : i2;
    }

    private int getChatsSettingsCount() {
        int i = this.settings.types;
        int i2 = (i & 32) != 0 ? 1 : 0;
        if ((i & 64) != 0) {
            i2++;
        }
        if ((i & 128) != 0) {
            i2++;
        }
        if ((i & 512) != 0) {
            i2++;
        }
        if ((i & 256) != 0) {
            i2++;
        }
        return (i & 1024) != 0 ? i2 + 1 : i2;
    }

    private int getMediaSettingsCount() {
        int i = this.settings.media.type;
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 2) != 0) {
            i2++;
        }
        if ((i & 4) != 0) {
            i2++;
        }
        if ((i & 8) != 0) {
            i2++;
        }
        if ((i & 16) != 0) {
            i2++;
        }
        if ((i & 32) != 0) {
            i2++;
        }
        return (i & 64) != 0 ? i2 + 1 : i2;
    }

    private int getIndexOfFormat() {
        AbstractWriter.Format format = this.settings.format;
        if (format == AbstractWriter.Format.Json) {
            return 1;
        }
        return format == AbstractWriter.Format.HtmlAndJson ? 2 : 0;
    }
}
