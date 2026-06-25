package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.PostProcessor;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import com.exteragram.messenger.utils.AppUtils;
import com.google.android.gms.cast.framework.media.internal.zzm$$ExternalSyntheticApiModelOutline0;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.time.DurationKt;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.messenger.utils.tlutils.TlUtils;
import org.telegram.messenger.voip.VoIPGroupNotification;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AvatarDrawable;
import org.telegram.p035ui.Components.Forum.ForumUtilities;
import org.telegram.p035ui.Components.spoilers.SpoilerEffect;
import org.telegram.p035ui.PopupNotificationActivity;
import org.telegram.p035ui.Stories.recorder.StoryEntry;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;
import org.webrtc.MediaStreamTrack;

/* JADX INFO: loaded from: classes.dex */
public class NotificationsController extends BaseController implements NotificationCenter.NotificationCenterDelegate {
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    private static volatile NotificationsController[] Instance = null;
    public static String OTHER_NOTIFICATIONS_CHANNEL = null;
    public static final int SETTING_MUTE_2_DAYS = 2;
    public static final int SETTING_MUTE_8_HOURS = 1;
    public static final int SETTING_MUTE_CUSTOM = 5;
    public static final int SETTING_MUTE_FOREVER = 3;
    public static final int SETTING_MUTE_HOUR = 0;
    public static final int SETTING_MUTE_UNMUTE = 4;
    public static final int SETTING_SOUND_OFF = 1;
    public static final int SETTING_SOUND_ON = 0;
    public static final int TYPE_CHANNEL = 2;
    public static final int TYPE_GROUP = 0;
    public static final int TYPE_PRIVATE = 1;
    public static final int TYPE_REACTIONS_MESSAGES = 4;
    public static final int TYPE_REACTIONS_STORIES = 5;
    public static final int TYPE_STORIES = 3;
    protected static AudioManager audioManager;
    private static final Object[] lockObjects;
    private static NotificationManagerCompat notificationManager;
    private static final LongSparseArray<String> sharedPrefCachedKeys;
    private static NotificationManager systemNotificationManager;
    private AlarmManager alarmManager;
    private boolean channelGroupsCreated;
    private Runnable checkStoryPushesRunnable;
    private final ArrayList<MessageObject> delayedPushMessages;
    NotificationsSettingsFacade dialogsNotificationsFacade;
    private final LongSparseArray<MessageObject> fcmRandomMessagesDict;
    private Boolean groupsCreated;
    private boolean inChatSoundEnabled;
    private int lastBadgeCount;
    private int lastButtonId;
    public long lastNotificationChannelCreateTime;
    private int lastOnlineFromOtherDevice;
    private long lastSoundOutPlay;
    private long lastSoundPlay;
    private final LongSparseArray<Integer> lastWearNotifiedMessageId;
    private String launcherClassName;
    private SpoilerEffect mediaSpoilerEffect;
    private Runnable notificationDelayRunnable;
    private PowerManager.WakeLock notificationDelayWakelock;
    private String notificationGroup;
    private int notificationId;
    private boolean notifyCheck;
    private long openedDialogId;
    private final HashSet<Long> openedInBubbleDialogs;
    private long openedTopicId;
    private final HashSet<String> pendingVoiceLoads;
    private int personalCount;
    public final ArrayList<MessageObject> popupMessages;
    public ArrayList<MessageObject> popupReplyMessages;
    private final LongSparseArray<Integer> pushDialogs;
    private final LongSparseArray<Integer> pushDialogsOverrideMention;
    private final ArrayList<MessageObject> pushMessages;
    private final LongSparseArray<SparseArray<MessageObject>> pushMessagesDict;
    public boolean showBadgeMessages;
    public boolean showBadgeMuted;
    public boolean showBadgeNumber;
    private final LongSparseArray<Point> smartNotificationsDialogs;
    private int soundIn;
    private boolean soundInLoaded;
    private int soundOut;
    private boolean soundOutLoaded;
    private SoundPool soundPool;
    private int soundRecord;
    private boolean soundRecordLoaded;
    char[] spoilerChars;
    private final ArrayList<StoryNotification> storyPushMessages;
    private final LongSparseArray<StoryNotification> storyPushMessagesDict;
    private int total_unread_count;
    private final LongSparseArray<Integer> wearNotificationsIds;
    private static final DispatchQueue notificationsQueue = new DispatchQueue("notificationsQueue");
    public static long globalSecretChatId = DialogObject.makeEncryptedDialogId(1);

    /* JADX INFO: renamed from: $r8$lambda$1t1axbSYGQIU_GMVkHn-zrj3Llc */
    public static /* synthetic */ void m6146$r8$lambda$1t1axbSYGQIU_GMVkHnzrj3Llc(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    /* JADX INFO: renamed from: $r8$lambda$H_bZLJEQVx9OdWW6-ZrpVB2xjp0 */
    public static /* synthetic */ void m6151$r8$lambda$H_bZLJEQVx9OdWW6ZrpVB2xjp0(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static /* synthetic */ void $r8$lambda$TMEsjTkj9lYdR59uaNuAf1n8IoU(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public void processReadStories() {
    }

    static {
        notificationManager = null;
        systemNotificationManager = null;
        if (Build.VERSION.SDK_INT >= 26 && ApplicationLoader.applicationContext != null) {
            notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
            systemNotificationManager = (NotificationManager) ApplicationLoader.applicationContext.getSystemService("notification");
            checkOtherNotificationsChannel();
        }
        audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        Instance = new NotificationsController[16];
        lockObjects = new Object[16];
        for (int i = 0; i < 16; i++) {
            lockObjects[i] = new Object();
        }
        sharedPrefCachedKeys = new LongSparseArray<>();
    }

    public static NotificationsController getInstance(int i) {
        NotificationsController notificationsController;
        NotificationsController notificationsController2 = Instance[i];
        if (notificationsController2 != null) {
            return notificationsController2;
        }
        synchronized (lockObjects[i]) {
            try {
                notificationsController = Instance[i];
                if (notificationsController == null) {
                    NotificationsController[] notificationsControllerArr = Instance;
                    NotificationsController notificationsController3 = new NotificationsController(i);
                    notificationsControllerArr[i] = notificationsController3;
                    notificationsController = notificationsController3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return notificationsController;
    }

    public NotificationsController(int i) {
        super(i);
        this.pushMessages = new ArrayList<>();
        this.delayedPushMessages = new ArrayList<>();
        this.pushMessagesDict = new LongSparseArray<>();
        this.fcmRandomMessagesDict = new LongSparseArray<>();
        this.smartNotificationsDialogs = new LongSparseArray<>();
        this.pushDialogs = new LongSparseArray<>();
        this.wearNotificationsIds = new LongSparseArray<>();
        this.lastWearNotifiedMessageId = new LongSparseArray<>();
        this.pushDialogsOverrideMention = new LongSparseArray<>();
        this.pendingVoiceLoads = new HashSet<>();
        this.popupMessages = new ArrayList<>();
        this.popupReplyMessages = new ArrayList<>();
        this.openedInBubbleDialogs = new HashSet<>();
        this.storyPushMessages = new ArrayList<>();
        this.storyPushMessagesDict = new LongSparseArray<>();
        this.openedDialogId = 0L;
        this.openedTopicId = 0L;
        this.lastButtonId = 5000;
        this.total_unread_count = 0;
        this.personalCount = 0;
        this.notifyCheck = false;
        this.lastOnlineFromOtherDevice = 0;
        this.lastBadgeCount = -1;
        this.mediaSpoilerEffect = new SpoilerEffect();
        this.spoilerChars = new char[]{10252, 10338, 10385, 10280, 10277, 10286, 10321};
        this.checkStoryPushesRunnable = new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.checkStoryPushes();
            }
        };
        this.notificationId = this.currentAccount + 1;
        StringBuilder sb = new StringBuilder("messages");
        int i2 = this.currentAccount;
        sb.append(i2 == 0 ? _UrlKt.FRAGMENT_ENCODE_SET : Integer.valueOf(i2));
        this.notificationGroup = sb.toString();
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        this.inChatSoundEnabled = notificationsSettings.getBoolean("EnableInChatSound", true);
        this.showBadgeNumber = notificationsSettings.getBoolean("badgeNumber", true);
        this.showBadgeMuted = notificationsSettings.getBoolean("badgeNumberMuted", false);
        this.showBadgeMessages = notificationsSettings.getBoolean("badgeNumberMessages", true);
        notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
        systemNotificationManager = (NotificationManager) ApplicationLoader.applicationContext.getSystemService("notification");
        try {
            audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        try {
            this.alarmManager = (AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm");
        } catch (Exception e2) {
            FileLog.m1048e(e2);
        }
        try {
            PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(1, "telegram:notification_delay_lock");
            this.notificationDelayWakelock = wakeLockNewWakeLock;
            wakeLockNewWakeLock.setReferenceCounted(false);
        } catch (Exception e3) {
            FileLog.m1048e(e3);
        }
        this.notificationDelayRunnable = new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
        this.dialogsNotificationsFacade = new NotificationsSettingsFacade(this.currentAccount);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1();
            }
        });
    }

    public /* synthetic */ void lambda$new$0() {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("delay reached");
        }
        if (!this.delayedPushMessages.isEmpty()) {
            showOrUpdateNotification(true);
            this.delayedPushMessages.clear();
        }
        try {
            if (this.notificationDelayWakelock.isHeld()) {
                this.notificationDelayWakelock.release();
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public /* synthetic */ void lambda$new$1() {
        getNotificationCenter().addObserver(this, NotificationCenter.fileLoaded);
    }

    public static void checkOtherNotificationsChannel() {
        SharedPreferences sharedPreferences;
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        if (OTHER_NOTIFICATIONS_CHANNEL == null) {
            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            OTHER_NOTIFICATIONS_CHANNEL = sharedPreferences.getString("OtherKey", "Other3");
        } else {
            sharedPreferences = null;
        }
        NotificationChannel notificationChannel = systemNotificationManager.getNotificationChannel(OTHER_NOTIFICATIONS_CHANNEL);
        if (notificationChannel != null && notificationChannel.getImportance() == 0) {
            try {
                systemNotificationManager.deleteNotificationChannel(OTHER_NOTIFICATIONS_CHANNEL);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            OTHER_NOTIFICATIONS_CHANNEL = null;
            notificationChannel = null;
        }
        if (OTHER_NOTIFICATIONS_CHANNEL == null) {
            if (sharedPreferences == null) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            }
            OTHER_NOTIFICATIONS_CHANNEL = "Other" + Utilities.random.nextLong();
            sharedPreferences.edit().putString("OtherKey", OTHER_NOTIFICATIONS_CHANNEL).apply();
        }
        if (notificationChannel == null) {
            NotificationsController$$ExternalSyntheticApiModelOutline0.m1067m();
            NotificationChannel notificationChannelM332m = zzm$$ExternalSyntheticApiModelOutline0.m332m(OTHER_NOTIFICATIONS_CHANNEL, "Internal notifications", 3);
            notificationChannelM332m.enableLights(false);
            notificationChannelM332m.enableVibration(false);
            notificationChannelM332m.setSound(null, null);
            try {
                systemNotificationManager.createNotificationChannel(notificationChannelM332m);
            } catch (Exception e2) {
                FileLog.m1048e(e2);
            }
        }
    }

    public static String getSharedPrefKey(long j, long j2) {
        return getSharedPrefKey(j, j2, false);
    }

    public static String getSharedPrefKey(long j, long j2, boolean z) {
        String strValueOf;
        if (z) {
            if (j2 != 0) {
                return String.format(Locale.US, "%d_%d", Long.valueOf(j), Long.valueOf(j2));
            }
            return String.valueOf(j);
        }
        long j3 = (j2 << 12) + j;
        LongSparseArray<String> longSparseArray = sharedPrefCachedKeys;
        int iIndexOfKey = longSparseArray.indexOfKey(j3);
        if (iIndexOfKey >= 0) {
            return longSparseArray.valueAt(iIndexOfKey);
        }
        if (j2 != 0) {
            strValueOf = String.format(Locale.US, "%d_%d", Long.valueOf(j), Long.valueOf(j2));
        } else {
            strValueOf = String.valueOf(j);
        }
        longSparseArray.put(j3, strValueOf);
        return strValueOf;
    }

    public void muteUntil(long j, long j2, int i) {
        long j3;
        if (j != 0) {
            SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
            boolean z = j2 != 0;
            boolean zIsGlobalNotificationsEnabled = getInstance(this.currentAccount).isGlobalNotificationsEnabled(j, false, false);
            String sharedPrefKey = getSharedPrefKey(j, j2);
            if (i != Integer.MAX_VALUE) {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey, 3);
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + sharedPrefKey, getConnectionsManager().getCurrentTime() + i);
                j3 = (((long) i) << 32) | 1;
            } else if (!zIsGlobalNotificationsEnabled && !z) {
                editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey);
                j3 = 0;
            } else {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey, 2);
                j3 = 1L;
            }
            editorEdit.apply();
            if (j2 == 0) {
                getInstance(this.currentAccount).removeNotificationsForDialog(j);
                MessagesStorage.getInstance(this.currentAccount).setDialogFlags(j, j3);
                TLRPC.Dialog dialog = MessagesController.getInstance(this.currentAccount).dialogs_dict.get(j);
                if (dialog != null) {
                    TLRPC.TL_peerNotifySettings tL_peerNotifySettings = new TLRPC.TL_peerNotifySettings();
                    dialog.notify_settings = tL_peerNotifySettings;
                    if (i != Integer.MAX_VALUE || zIsGlobalNotificationsEnabled) {
                        tL_peerNotifySettings.mute_until = i;
                    }
                }
            }
            getInstance(this.currentAccount).updateServerNotificationsSettings(j, j2);
        }
    }

    public void cleanup() {
        this.popupMessages.clear();
        this.popupReplyMessages.clear();
        this.channelGroupsCreated = false;
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$cleanup$2();
            }
        });
    }

    public /* synthetic */ void lambda$cleanup$2() {
        this.openedDialogId = 0L;
        this.openedTopicId = 0L;
        this.total_unread_count = 0;
        this.personalCount = 0;
        this.pushMessages.clear();
        this.pushMessagesDict.clear();
        this.fcmRandomMessagesDict.clear();
        this.pushDialogs.clear();
        this.wearNotificationsIds.clear();
        this.lastWearNotifiedMessageId.clear();
        this.openedInBubbleDialogs.clear();
        this.delayedPushMessages.clear();
        this.notifyCheck = false;
        this.lastBadgeCount = 0;
        try {
            if (this.notificationDelayWakelock.isHeld()) {
                this.notificationDelayWakelock.release();
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        dismissNotification();
        setBadge(getTotalAllUnreadCount());
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        editorEdit.clear();
        editorEdit.apply();
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                systemNotificationManager.deleteNotificationChannelGroup("channels" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("groups" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("private" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("stories" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("other" + this.currentAccount);
                String str = this.currentAccount + "channel";
                List<NotificationChannel> notificationChannels = systemNotificationManager.getNotificationChannels();
                int size = notificationChannels.size();
                for (int i = 0; i < size; i++) {
                    String id = NotificationsController$$ExternalSyntheticApiModelOutline3.m1070m(notificationChannels.get(i)).getId();
                    if (id.startsWith(str)) {
                        try {
                            systemNotificationManager.deleteNotificationChannel(id);
                        } catch (Exception e2) {
                            FileLog.m1048e(e2);
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1045d("delete channel cleanup " + id);
                        }
                    }
                }
            } catch (Throwable th) {
                FileLog.m1048e(th);
            }
        }
    }

    public void setInChatSoundEnabled(boolean z) {
        this.inChatSoundEnabled = z;
    }

    public void setOpenedDialogId(final long j, final long j2) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setOpenedDialogId$3(j, j2);
            }
        });
    }

    public /* synthetic */ void lambda$setOpenedDialogId$3(long j, long j2) {
        this.openedDialogId = j;
        this.openedTopicId = j2;
    }

    public void setOpenedInBubble(final long j, final boolean z) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setOpenedInBubble$4(z, j);
            }
        });
    }

    public /* synthetic */ void lambda$setOpenedInBubble$4(boolean z, long j) {
        HashSet<Long> hashSet = this.openedInBubbleDialogs;
        if (z) {
            hashSet.add(Long.valueOf(j));
        } else {
            hashSet.remove(Long.valueOf(j));
        }
    }

    public void setLastOnlineFromOtherDevice(final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setLastOnlineFromOtherDevice$5(i);
            }
        });
    }

    public /* synthetic */ void lambda$setLastOnlineFromOtherDevice$5(int i) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("set last online from other device = " + i);
        }
        this.lastOnlineFromOtherDevice = i;
    }

    public void removeNotificationsForDialog(long j) {
        processReadMessages(null, j, 0, Integer.MAX_VALUE, false);
        LongSparseIntArray longSparseIntArray = new LongSparseIntArray();
        longSparseIntArray.put(j, 0);
        processDialogsUpdateRead(longSparseIntArray);
    }

    public boolean hasMessagesToReply() {
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            if (!messageObject.isReactionPush) {
                TLRPC.Message message = messageObject.messageOwner;
                if ((!message.mentioned || !(message.action instanceof TLRPC.TL_messageActionPinMessage)) && !DialogObject.isEncryptedDialog(dialogId) && ((messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup()) && dialogId != UserObject.VERIFY && dialogId != UserObject.OAUTH)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void forceShowPopupForReply() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$forceShowPopupForReply$7();
            }
        });
    }

    public /* synthetic */ void lambda$forceShowPopupForReply$7() {
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            TLRPC.Message message = messageObject.messageOwner;
            if ((!message.mentioned || !(message.action instanceof TLRPC.TL_messageActionPinMessage)) && !DialogObject.isEncryptedDialog(dialogId) && (messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup())) {
                arrayList.add(0, messageObject);
            }
        }
        if (arrayList.isEmpty() || AndroidUtilities.needShowPasscode() || SharedConfig.isWaitingForPasscodeEnter) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$forceShowPopupForReply$6(arrayList);
            }
        });
    }

    public /* synthetic */ void lambda$forceShowPopupForReply$6(ArrayList arrayList) {
        this.popupReplyMessages = arrayList;
        Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) PopupNotificationActivity.class);
        intent.putExtra("force", true);
        intent.putExtra("currentAccount", this.currentAccount);
        intent.setFlags(268763140);
        ApplicationLoader.applicationContext.startActivity(intent);
        ApplicationLoader.applicationContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public void removeDeletedMessagesFromNotifications(final LongSparseArray<ArrayList<Integer>> longSparseArray, final boolean z) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda54
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeDeletedMessagesFromNotifications$10(longSparseArray, z, arrayList);
            }
        });
    }

    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$10(LongSparseArray longSparseArray, boolean z, final ArrayList arrayList) {
        Integer num;
        int i;
        Integer num2;
        int i2;
        Integer num3;
        LongSparseArray longSparseArray2 = longSparseArray;
        int i3 = this.total_unread_count;
        getAccountInstance().getNotificationsSettings();
        int i4 = 0;
        Integer num4 = 0;
        int i5 = 0;
        while (i5 < longSparseArray2.size()) {
            long jKeyAt = longSparseArray2.keyAt(i5);
            SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(jKeyAt);
            if (sparseArray == null) {
                num = num4;
                i = i5;
            } else {
                ArrayList arrayList2 = (ArrayList) longSparseArray2.get(jKeyAt);
                int size = arrayList2.size();
                int i6 = i4;
                while (i6 < size) {
                    int iIntValue = ((Integer) arrayList2.get(i6)).intValue();
                    MessageObject messageObject = sparseArray.get(iIntValue);
                    if (messageObject == null) {
                        num2 = num4;
                        i2 = i5;
                    } else if (!messageObject.isStoryReactionPush && (!z || messageObject.isReactionPush)) {
                        num2 = num4;
                        long dialogId = messageObject.getDialogId();
                        Integer num5 = this.pushDialogs.get(dialogId);
                        if (num5 == null) {
                            num5 = num2;
                        }
                        int iIntValue2 = num5.intValue() - 1;
                        Integer numValueOf = Integer.valueOf(iIntValue2);
                        if (iIntValue2 <= 0) {
                            this.smartNotificationsDialogs.remove(dialogId);
                            num3 = num2;
                        } else {
                            num3 = numValueOf;
                        }
                        if (num3.equals(num5)) {
                            i2 = i5;
                        } else {
                            i2 = i5;
                            boolean zIsForum = getMessagesController().isForum(dialogId);
                            int i7 = this.total_unread_count;
                            if (zIsForum) {
                                int i8 = i7 - (num5.intValue() > 0 ? 1 : 0);
                                this.total_unread_count = i8;
                                this.total_unread_count = i8 + (num3.intValue() > 0 ? 1 : 0);
                            } else {
                                int iIntValue3 = i7 - num5.intValue();
                                this.total_unread_count = iIntValue3;
                                this.total_unread_count = iIntValue3 + num3.intValue();
                            }
                            this.pushDialogs.put(dialogId, num3);
                        }
                        if (num3.intValue() == 0) {
                            this.pushDialogs.remove(dialogId);
                            this.pushDialogsOverrideMention.remove(dialogId);
                        }
                        sparseArray.remove(iIntValue);
                        this.delayedPushMessages.remove(messageObject);
                        this.pushMessages.remove(messageObject);
                        if (isPersonalMessage(messageObject)) {
                            this.personalCount--;
                        }
                        arrayList.add(messageObject);
                    } else {
                        num2 = num4;
                        i2 = i5;
                    }
                    i6++;
                    num4 = num2;
                    i5 = i2;
                }
                num = num4;
                i = i5;
                if (sparseArray.size() == 0) {
                    this.pushMessagesDict.remove(jKeyAt);
                }
            }
            i5 = i + 1;
            longSparseArray2 = longSparseArray;
            num4 = num;
            i4 = 0;
        }
        if (!arrayList.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedMessagesFromNotifications$8(arrayList);
                }
            });
        }
        if (i3 != this.total_unread_count) {
            if (!this.notifyCheck) {
                this.delayedPushMessages.clear();
                showOrUpdateNotification(this.notifyCheck);
            } else {
                scheduleNotificationDelay(this.lastOnlineFromOtherDevice > getConnectionsManager().getCurrentTime());
            }
            final int size2 = this.pushDialogs.size();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedMessagesFromNotifications$9(size2);
                }
            });
        }
        this.notifyCheck = false;
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$8(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$9(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void removeDeletedHisoryFromNotifications(final LongSparseIntArray longSparseIntArray) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeDeletedHisoryFromNotifications$13(longSparseIntArray, arrayList);
            }
        });
    }

    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$13(LongSparseIntArray longSparseIntArray, final ArrayList arrayList) {
        Integer num;
        int i = this.total_unread_count;
        getAccountInstance().getNotificationsSettings();
        int i2 = 0;
        Integer num2 = 0;
        int i3 = 0;
        while (i3 < longSparseIntArray.size()) {
            long jKeyAt = longSparseIntArray.keyAt(i3);
            long j = -jKeyAt;
            long j2 = longSparseIntArray.get(jKeyAt);
            Integer num3 = this.pushDialogs.get(j);
            if (num3 == null) {
                num3 = num2;
            }
            int i4 = i2;
            Integer numValueOf = num3;
            while (i4 < this.pushMessages.size()) {
                MessageObject messageObject = this.pushMessages.get(i4);
                if (messageObject.getDialogId() == j) {
                    num = num2;
                    if (messageObject.getId() <= j2) {
                        SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(j);
                        if (sparseArray != null) {
                            sparseArray.remove(messageObject.getId());
                            if (sparseArray.size() == 0) {
                                this.pushMessagesDict.remove(j);
                            }
                        }
                        this.delayedPushMessages.remove(messageObject);
                        this.pushMessages.remove(messageObject);
                        i4--;
                        if (isPersonalMessage(messageObject)) {
                            this.personalCount--;
                        }
                        arrayList.add(messageObject);
                        numValueOf = Integer.valueOf(numValueOf.intValue() - 1);
                    }
                } else {
                    num = num2;
                }
                i4++;
                num2 = num;
            }
            Integer num4 = num2;
            if (numValueOf.intValue() <= 0) {
                this.smartNotificationsDialogs.remove(j);
                numValueOf = num4;
            }
            if (!numValueOf.equals(num3)) {
                boolean zIsForum = getMessagesController().isForum(j);
                int i5 = this.total_unread_count;
                if (zIsForum) {
                    int i6 = i5 - (num3.intValue() > 0 ? 1 : 0);
                    this.total_unread_count = i6;
                    this.total_unread_count = i6 + (numValueOf.intValue() > 0 ? 1 : 0);
                } else {
                    int iIntValue = i5 - num3.intValue();
                    this.total_unread_count = iIntValue;
                    this.total_unread_count = iIntValue + numValueOf.intValue();
                }
                this.pushDialogs.put(j, numValueOf);
            }
            if (numValueOf.intValue() == 0) {
                this.pushDialogs.remove(j);
                this.pushDialogsOverrideMention.remove(j);
            }
            i3++;
            num2 = num4;
            i2 = 0;
        }
        if (arrayList.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedHisoryFromNotifications$11(arrayList);
                }
            });
        }
        if (i != this.total_unread_count) {
            if (!this.notifyCheck) {
                this.delayedPushMessages.clear();
                showOrUpdateNotification(this.notifyCheck);
            } else {
                scheduleNotificationDelay(this.lastOnlineFromOtherDevice > getConnectionsManager().getCurrentTime());
            }
            final int size = this.pushDialogs.size();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedHisoryFromNotifications$12(size);
                }
            });
        }
        this.notifyCheck = false;
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$11(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$12(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void processSeenStoryReactions(long j, final int i) {
        if (j != getUserConfig().getClientUserId()) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processSeenStoryReactions$14(i);
            }
        });
    }

    public /* synthetic */ void lambda$processSeenStoryReactions$14(int i) {
        int i2 = 0;
        boolean z = false;
        while (i2 < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i2);
            if (messageObject.isStoryReactionPush && Math.abs(messageObject.getId()) == i) {
                this.pushMessages.remove(i2);
                SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(Integer.valueOf(messageObject.getId()));
                getMessagesStorage().deletePushMessages(messageObject.getDialogId(), arrayList);
                i2--;
                z = true;
            }
            i2++;
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processDeleteStory(final long j, final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDeleteStory$15(j, i);
            }
        });
    }

    public /* synthetic */ void lambda$processDeleteStory$15(long j, int i) {
        boolean z;
        StoryNotification storyNotification = this.storyPushMessagesDict.get(j);
        if (storyNotification != null) {
            storyNotification.dateByIds.remove(Integer.valueOf(i));
            if (storyNotification.dateByIds.isEmpty()) {
                this.storyPushMessagesDict.remove(j);
                this.storyPushMessages.remove(storyNotification);
                getMessagesStorage().deleteStoryPushMessage(j);
                z = true;
            } else {
                getMessagesStorage().putStoryPushMessage(storyNotification);
                z = false;
            }
        } else {
            z = false;
        }
        int i2 = 0;
        while (i2 < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i2);
            if (messageObject != null && messageObject.isLiveStoryPush && messageObject.getId() == i) {
                this.pushMessages.remove(i2);
                i2--;
                SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                z = true;
            }
            i2++;
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processReadStories(final long j, final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda42
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processReadStories$16(j, i);
            }
        });
    }

    public /* synthetic */ void lambda$processReadStories$16(long j, int i) {
        boolean z;
        StoryNotification storyNotification = this.storyPushMessagesDict.get(j);
        if (storyNotification != null) {
            this.storyPushMessagesDict.remove(j);
            this.storyPushMessages.remove(storyNotification);
            getMessagesStorage().deleteStoryPushMessage(j);
            z = true;
        } else {
            z = false;
        }
        int i2 = 0;
        while (i2 < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i2);
            if (messageObject != null && messageObject.isLiveStoryPush && messageObject.getId() <= i) {
                this.pushMessages.remove(i2);
                i2--;
                SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                z = true;
            }
            i2++;
        }
        if (z) {
            showOrUpdateNotification(false);
            updateStoryPushesRunnable();
        }
    }

    public void processIgnoreStories() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processIgnoreStories$17();
            }
        });
    }

    public /* synthetic */ void lambda$processIgnoreStories$17() {
        boolean zIsEmpty = this.storyPushMessages.isEmpty();
        this.storyPushMessages.clear();
        this.storyPushMessagesDict.clear();
        getMessagesStorage().deleteAllStoryPushMessages();
        if (zIsEmpty) {
            return;
        }
        showOrUpdateNotification(false);
    }

    public void processIgnoreStoryReactions() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processIgnoreStoryReactions$18();
            }
        });
    }

    public /* synthetic */ void lambda$processIgnoreStoryReactions$18() {
        int i = 0;
        boolean z = false;
        while (i < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i);
            if (messageObject != null && messageObject.isStoryReactionPush) {
                this.pushMessages.remove(i);
                i--;
                SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                z = true;
            }
            i++;
        }
        getMessagesStorage().deleteAllStoryReactionPushMessages();
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processIgnoreStories(final long j) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processIgnoreStories$19(j);
            }
        });
    }

    public /* synthetic */ void lambda$processIgnoreStories$19(long j) {
        boolean zIsEmpty = this.storyPushMessages.isEmpty();
        this.storyPushMessages.clear();
        this.storyPushMessagesDict.clear();
        getMessagesStorage().deleteStoryPushMessage(j);
        if (zIsEmpty) {
            return;
        }
        showOrUpdateNotification(false);
    }

    public void processReadMessages(final LongSparseIntArray longSparseIntArray, final long j, final int i, final int i2, final boolean z) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processReadMessages$21(longSparseIntArray, arrayList, j, i2, i, z);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processReadMessages$21(org.telegram.messenger.support.LongSparseIntArray r20, final java.util.ArrayList r21, long r22, int r24, int r25, boolean r26) {
        /*
            Method dump skipped, instruction units count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processReadMessages$21(org.telegram.messenger.support.LongSparseIntArray, java.util.ArrayList, long, int, int, boolean):void");
    }

    public /* synthetic */ void lambda$processReadMessages$20(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int addToPopupMessages(java.util.ArrayList<org.telegram.messenger.MessageObject> r3, org.telegram.messenger.MessageObject r4, long r5, boolean r7, android.content.SharedPreferences r8) {
        /*
            r2 = this;
            boolean r2 = r4.isStoryReactionPush
            r0 = 0
            if (r2 == 0) goto L6
            return r0
        L6:
            boolean r2 = org.telegram.messenger.DialogObject.isEncryptedDialog(r5)
            if (r2 != 0) goto L5b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r1 = "custom_"
            r2.<init>(r1)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            boolean r2 = r8.getBoolean(r2, r0)
            if (r2 == 0) goto L34
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r1 = "popup_"
            r2.<init>(r1)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            int r2 = r8.getInt(r2, r0)
            goto L35
        L34:
            r2 = r0
        L35:
            if (r2 != 0) goto L53
            if (r7 == 0) goto L41
            java.lang.String r2 = "popupChannel"
            int r2 = r8.getInt(r2, r0)
            goto L5c
        L41:
            boolean r2 = org.telegram.messenger.DialogObject.isChatDialog(r5)
            if (r2 == 0) goto L4b
            java.lang.String r2 = "popupGroup"
            goto L4e
        L4b:
            java.lang.String r2 = "popupAll"
        L4e:
            int r2 = r8.getInt(r2, r0)
            goto L5c
        L53:
            r5 = 1
            if (r2 != r5) goto L58
            r2 = 3
            goto L5c
        L58:
            r5 = 2
            if (r2 != r5) goto L5c
        L5b:
            r2 = r0
        L5c:
            if (r2 == 0) goto L71
            org.telegram.tgnet.TLRPC$Message r5 = r4.messageOwner
            org.telegram.tgnet.TLRPC$Peer r5 = r5.peer_id
            long r5 = r5.channel_id
            r7 = 0
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L71
            boolean r5 = r4.isSupergroup()
            if (r5 != 0) goto L71
            r2 = r0
        L71:
            if (r2 == 0) goto L76
            r3.add(r0, r4)
        L76:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.addToPopupMessages(java.util.ArrayList, org.telegram.messenger.MessageObject, long, boolean, android.content.SharedPreferences):int");
    }

    public void processEditedMessages(final LongSparseArray<ArrayList<MessageObject>> longSparseArray) {
        TLRPC.Message message;
        if (longSparseArray == null || longSparseArray.size() == 0) {
            return;
        }
        for (int i = 0; i < longSparseArray.size(); i++) {
            ArrayList<MessageObject> arrayListValueAt = longSparseArray.valueAt(i);
            if (arrayListValueAt != null) {
                for (int i2 = 0; i2 < arrayListValueAt.size(); i2++) {
                    MessageObject messageObject = arrayListValueAt.get(i2);
                    if (messageObject != null && (message = messageObject.messageOwner) != null) {
                        TLRPC.MessageAction messageAction = message.action;
                        if (messageAction instanceof TLRPC.TL_messageActionConferenceCall) {
                            TLRPC.TL_messageActionConferenceCall tL_messageActionConferenceCall = (TLRPC.TL_messageActionConferenceCall) messageAction;
                            if (tL_messageActionConferenceCall.active || tL_messageActionConferenceCall.missed) {
                                VoIPGroupNotification.hide(ApplicationLoader.applicationContext, this.currentAccount, messageObject.getId());
                            }
                        }
                    }
                }
            }
        }
        new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processEditedMessages$22(longSparseArray);
            }
        });
    }

    public /* synthetic */ void lambda$processEditedMessages$22(LongSparseArray longSparseArray) {
        long dialogId;
        int size = longSparseArray.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            longSparseArray.keyAt(i);
            ArrayList arrayList = (ArrayList) longSparseArray.valueAt(i);
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                if (messageObject.isStoryReactionPush) {
                    dialogId = messageObject.getDialogId();
                } else {
                    long j = messageObject.messageOwner.peer_id.channel_id;
                    dialogId = j != 0 ? -j : 0L;
                }
                SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(dialogId);
                if (sparseArray == null) {
                    break;
                }
                MessageObject messageObject2 = sparseArray.get(messageObject.getId());
                if (messageObject2 != null && (messageObject2.isReactionPush || messageObject2.isStoryReactionPush)) {
                    messageObject2 = null;
                }
                if (messageObject2 != null) {
                    sparseArray.put(messageObject.getId(), messageObject);
                    int iIndexOf = this.pushMessages.indexOf(messageObject2);
                    if (iIndexOf >= 0) {
                        this.pushMessages.set(iIndexOf, messageObject);
                    }
                    int iIndexOf2 = this.delayedPushMessages.indexOf(messageObject2);
                    if (iIndexOf2 >= 0) {
                        this.delayedPushMessages.set(iIndexOf2, messageObject);
                    }
                    z = true;
                }
            }
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processNewMessages(final ArrayList<MessageObject> arrayList, boolean z, boolean z2, final CountDownLatch countDownLatch) {
        final boolean z3;
        final boolean z4;
        if (BuildVars.LOGS_ENABLED) {
            StringBuilder sb = new StringBuilder("NotificationsController: processNewMessages msgs.size()=");
            sb.append(arrayList == null ? "null" : Integer.valueOf(arrayList.size()));
            sb.append(" isLast=");
            z3 = z;
            sb.append(z3);
            sb.append(" isFcm=");
            z4 = z2;
            sb.append(z4);
            sb.append(")");
            FileLog.m1045d(sb.toString());
        } else {
            z3 = z;
            z4 = z2;
        }
        if (arrayList != null) {
            int i = 0;
            while (i < arrayList.size()) {
                MessageObject messageObject = arrayList.get(i);
                if (messageObject != null && messageObject.messageOwner != null && !messageObject.isOutOwner()) {
                    TLRPC.MessageAction messageAction = messageObject.messageOwner.action;
                    if (messageAction instanceof TLRPC.TL_messageActionConferenceCall) {
                        TLRPC.TL_messageActionConferenceCall tL_messageActionConferenceCall = (TLRPC.TL_messageActionConferenceCall) messageAction;
                        if (!tL_messageActionConferenceCall.active && !tL_messageActionConferenceCall.missed && getConnectionsManager().getCurrentTime() - messageObject.messageOwner.date < ((long) getMessagesController().callRingTimeout) / 1000) {
                            HashSet hashSet = new HashSet();
                            hashSet.add(Long.valueOf(messageObject.getDialogId()));
                            ArrayList<TLRPC.Peer> arrayList2 = tL_messageActionConferenceCall.other_participants;
                            int size = arrayList2.size();
                            int i2 = 0;
                            while (i2 < size) {
                                TLRPC.Peer peer = arrayList2.get(i2);
                                i2++;
                                hashSet.add(Long.valueOf(DialogObject.getPeerDialogId(peer)));
                            }
                            StringBuilder sb2 = new StringBuilder();
                            Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                                long jLongValue = ((Long) it.next()).longValue();
                                if (sb2.length() > 0) {
                                    sb2.append(", ");
                                }
                                sb2.append(DialogObject.getShortName(this.currentAccount, jLongValue));
                            }
                            VoIPGroupNotification.request(ApplicationLoader.applicationContext, this.currentAccount, messageObject.getDialogId(), sb2.toString(), tL_messageActionConferenceCall.call_id, messageObject.getId(), tL_messageActionConferenceCall.video);
                            arrayList.remove(i);
                            i--;
                        } else {
                            VoIPGroupNotification.hide(ApplicationLoader.applicationContext, this.currentAccount, messageObject.getId());
                        }
                    }
                }
                i++;
            }
        }
        if (!arrayList.isEmpty()) {
            final ArrayList arrayList3 = new ArrayList(0);
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$processNewMessages$27(arrayList, arrayList3, z4, z3, countDownLatch);
                }
            });
        } else if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:295:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0178  */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v5, types: [int] */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r24v4 */
    /* JADX WARN: Type inference failed for: r24v5, types: [int] */
    /* JADX WARN: Type inference failed for: r24v6 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [int] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processNewMessages$27(java.util.ArrayList r40, final java.util.ArrayList r41, boolean r42, boolean r43, java.util.concurrent.CountDownLatch r44) {
        /*
            Method dump skipped, instruction units count: 1442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processNewMessages$27(java.util.ArrayList, java.util.ArrayList, boolean, boolean, java.util.concurrent.CountDownLatch):void");
    }

    public /* synthetic */ void lambda$processNewMessages$24(int i) {
        LongSparseArray<ArrayList<Integer>> longSparseArray = new LongSparseArray<>();
        longSparseArray.put(0L, Lists.newArrayList(Integer.valueOf(i)));
        removeDeletedMessagesFromNotifications(longSparseArray, false);
    }

    public /* synthetic */ void lambda$processNewMessages$25(ArrayList arrayList, int i) {
        this.popupMessages.addAll(0, arrayList);
        if (ApplicationLoader.mainInterfacePaused || !ApplicationLoader.isScreenOn) {
            if (i == 3 || ((i == 1 && ApplicationLoader.isScreenOn) || (i == 2 && !ApplicationLoader.isScreenOn))) {
                Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) PopupNotificationActivity.class);
                intent.setFlags(268763140);
                try {
                    ApplicationLoader.applicationContext.startActivity(intent);
                } catch (Throwable unused) {
                }
            }
        }
    }

    public /* synthetic */ void lambda$processNewMessages$26(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    private void appendMessage(MessageObject messageObject) {
        int i = 0;
        while (true) {
            int size = this.pushMessages.size();
            ArrayList<MessageObject> arrayList = this.pushMessages;
            if (i < size) {
                if (arrayList.get(i).getId() == messageObject.getId() && this.pushMessages.get(i).getDialogId() == messageObject.getDialogId() && this.pushMessages.get(i).isStoryPush == messageObject.isStoryPush) {
                    return;
                } else {
                    i++;
                }
            } else {
                arrayList.add(0, messageObject);
                return;
            }
        }
    }

    public int getTotalUnreadCount() {
        return this.total_unread_count;
    }

    public void processDialogsUpdateRead(final LongSparseIntArray longSparseIntArray) {
        final ArrayList arrayList = new ArrayList();
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDialogsUpdateRead$30(longSparseIntArray, arrayList);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x0050 A[PHI: r4
  0x0050: PHI (r4v3 int) = (r4v2 int), (r4v23 int) binds: [B:118:0x002e, B:126:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processDialogsUpdateRead$30(org.telegram.messenger.support.LongSparseIntArray r18, final java.util.ArrayList r19) {
        /*
            Method dump skipped, instruction units count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processDialogsUpdateRead$30(org.telegram.messenger.support.LongSparseIntArray, java.util.ArrayList):void");
    }

    public /* synthetic */ void lambda$processDialogsUpdateRead$28(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    public /* synthetic */ void lambda$processDialogsUpdateRead$29(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void processLoadedUnreadMessages(final LongSparseArray<Integer> longSparseArray, final ArrayList<TLRPC.Message> arrayList, final ArrayList<MessageObject> arrayList2, ArrayList<TLRPC.User> arrayList3, ArrayList<TLRPC.Chat> arrayList4, ArrayList<TLRPC.EncryptedChat> arrayList5, final Collection<StoryNotification> collection) {
        getMessagesController().putUsers(arrayList3, true);
        getMessagesController().putChats(arrayList4, true);
        getMessagesController().putEncryptedChats(arrayList5, true);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processLoadedUnreadMessages$33(arrayList, longSparseArray, arrayList2, collection);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:221:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processLoadedUnreadMessages$33(java.util.ArrayList r27, androidx.collection.LongSparseArray r28, java.util.ArrayList r29, java.util.Collection r30) {
        /*
            Method dump skipped, instruction units count: 908
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processLoadedUnreadMessages$33(java.util.ArrayList, androidx.collection.LongSparseArray, java.util.ArrayList, java.util.Collection):void");
    }

    public /* synthetic */ void lambda$processLoadedUnreadMessages$32(int i) {
        if (this.total_unread_count == 0) {
            this.popupMessages.clear();
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    private int getTotalAllUnreadCount() {
        int size;
        int dialogUnreadCount = 0;
        for (int i = 0; i < 16; i++) {
            if (UserConfig.getInstance(i).isClientActivated() && (SharedConfig.showNotificationsForAllAccounts || UserConfig.selectedAccount == i)) {
                NotificationsController notificationsController = getInstance(i);
                if (notificationsController.showBadgeNumber) {
                    boolean z = notificationsController.showBadgeMessages;
                    boolean z2 = notificationsController.showBadgeMuted;
                    if (z) {
                        if (z2) {
                            try {
                                ArrayList arrayList = new ArrayList(MessagesController.getInstance(i).allDialogs);
                                int size2 = arrayList.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    TLRPC.Dialog dialog = (TLRPC.Dialog) arrayList.get(i2);
                                    if ((dialog == null || !DialogObject.isChatDialog(dialog.f1251id) || !ChatObject.isNotInChat(getMessagesController().getChat(Long.valueOf(-dialog.f1251id)))) && dialog != null) {
                                        dialogUnreadCount += MessagesController.getInstance(i).getDialogUnreadCount(dialog);
                                    }
                                }
                            } catch (Exception e) {
                                FileLog.m1048e(e);
                            }
                        } else {
                            size = notificationsController.total_unread_count;
                            dialogUnreadCount += size;
                        }
                    } else if (z2) {
                        try {
                            int size3 = MessagesController.getInstance(i).allDialogs.size();
                            for (int i3 = 0; i3 < size3; i3++) {
                                TLRPC.Dialog dialog2 = MessagesController.getInstance(i).allDialogs.get(i3);
                                if ((!DialogObject.isChatDialog(dialog2.f1251id) || !ChatObject.isNotInChat(getMessagesController().getChat(Long.valueOf(-dialog2.f1251id)))) && MessagesController.getInstance(i).getDialogUnreadCount(dialog2) != 0) {
                                    dialogUnreadCount++;
                                }
                            }
                        } catch (Exception e2) {
                            FileLog.m1048e(e2);
                        }
                    } else {
                        size = notificationsController.pushDialogs.size();
                        dialogUnreadCount += size;
                    }
                }
            }
        }
        return dialogUnreadCount;
    }

    public /* synthetic */ void lambda$updateBadge$34() {
        setBadge(getTotalAllUnreadCount());
    }

    public void updateBadge() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateBadge$34();
            }
        });
    }

    private void setBadge(int i) {
        if (this.lastBadgeCount == i) {
            return;
        }
        FileLog.m1045d("setBadge " + i);
        this.lastBadgeCount = i;
        NotificationBadge.applyCount(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:1079:0x0235, code lost:
    
        if (r12.getBoolean("EnablePreviewChannel", r6) != false) goto L1080;
     */
    /* JADX WARN: Removed duplicated region for block: B:1082:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:1651:0x0df3  */
    /* JADX WARN: Removed duplicated region for block: B:1671:0x0e49  */
    /* JADX WARN: Removed duplicated region for block: B:1672:0x0e4b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getShortStringForMessage(org.telegram.messenger.MessageObject r34, java.lang.String[] r35, boolean[] r36) {
        /*
            Method dump skipped, instruction units count: 4216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.getShortStringForMessage(org.telegram.messenger.MessageObject, java.lang.String[], boolean[]):java.lang.String");
    }

    private String replaceSpoilers(MessageObject messageObject) {
        TLRPC.Message message;
        String str;
        if (messageObject == null || (message = messageObject.messageOwner) == null || (str = message.message) == null || message.entities == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        if (messageObject.didSpoilLoginCode()) {
            return sb.toString();
        }
        for (int i = 0; i < messageObject.messageOwner.entities.size(); i++) {
            if (messageObject.messageOwner.entities.get(i) instanceof TLRPC.TL_messageEntitySpoiler) {
                TLRPC.TL_messageEntitySpoiler tL_messageEntitySpoiler = (TLRPC.TL_messageEntitySpoiler) messageObject.messageOwner.entities.get(i);
                for (int i2 = 0; i2 < tL_messageEntitySpoiler.length; i2++) {
                    int i3 = tL_messageEntitySpoiler.offset + i2;
                    char[] cArr = this.spoilerChars;
                    sb.setCharAt(i3, cArr[i2 % cArr.length]);
                }
            }
        }
        return sb.toString();
    }

    private String getStringForMessage(MessageObject messageObject, boolean z, boolean[] zArr, boolean[] zArr2) {
        long j;
        String string;
        TLRPC.Chat chat;
        String string2;
        String userName;
        if (AndroidUtilities.needShowPasscode() || SharedConfig.isWaitingForPasscodeEnter) {
            return LocaleController.getString(C2797R.string.YouHaveNewMessage);
        }
        if (messageObject.isStoryPush || messageObject.isStoryMentionPush) {
            return "!" + messageObject.messageOwner.message;
        }
        TLRPC.Message message = messageObject.messageOwner;
        long j2 = message.dialog_id;
        TLRPC.Peer peer = message.peer_id;
        long j3 = peer.chat_id;
        if (j3 == 0) {
            j3 = peer.channel_id;
        }
        long fromChatId = peer.user_id;
        if (zArr2 != null) {
            zArr2[0] = true;
        }
        if (messageObject.getDialogId() == UserObject.VERIFY && messageObject.getForwardedFromId() != null) {
            fromChatId = messageObject.getForwardedFromId().longValue();
            j3 = fromChatId < 0 ? -fromChatId : 0L;
        }
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        boolean z2 = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_CONTENT_PREVIEW + j2, true);
        if (messageObject.isFcmMessage()) {
            if (j3 == 0 && fromChatId != 0) {
                if (!z2 || !notificationsSettings.getBoolean("EnablePreviewAll", true)) {
                    if (zArr2 != null) {
                        zArr2[0] = false;
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessageNoText, messageObject.localName);
                }
            } else if (j3 != 0 && (!z2 || ((!messageObject.localChannel && !notificationsSettings.getBoolean("EnablePreviewGroup", true)) || (messageObject.localChannel && !notificationsSettings.getBoolean("EnablePreviewChannel", true))))) {
                if (zArr2 != null) {
                    zArr2[0] = false;
                }
                if (messageObject.messageOwner.peer_id.channel_id != 0 && !messageObject.isSupergroup()) {
                    return LocaleController.formatString(C2797R.string.ChannelMessageNoText, messageObject.localName);
                }
                return LocaleController.formatString(C2797R.string.NotificationMessageGroupNoText, messageObject.localUserName, messageObject.localName);
            }
            zArr[0] = true;
            return (String) messageObject.messageText;
        }
        long clientUserId = getUserConfig().getClientUserId();
        if (fromChatId == 0) {
            fromChatId = messageObject.getFromChatId();
            if (fromChatId == 0) {
                fromChatId = -j3;
            }
        } else if (fromChatId == clientUserId) {
            fromChatId = messageObject.getFromChatId();
        }
        if (j2 == 0) {
            if (j3 != 0) {
                j2 = -j3;
            } else if (fromChatId != 0) {
                j2 = fromChatId;
            }
        }
        if (messageObject.getDialogId() == UserObject.OAUTH || messageObject.isOauthPush) {
            j = j2;
            string = LocaleController.getString(C2797R.string.BotAuthNotificationTitle);
        } else if (fromChatId > 0) {
            if (!messageObject.messageOwner.from_scheduled) {
                TLRPC.User user = getMessagesController().getUser(Long.valueOf(fromChatId));
                userName = user != null ? UserObject.getUserName(user) : null;
            } else if (j2 == clientUserId) {
                userName = LocaleController.getString(C2797R.string.MessageScheduledReminderNotification);
            } else {
                userName = LocaleController.getString(C2797R.string.NotificationMessageScheduledName);
            }
            j = j2;
            string = userName;
        } else {
            j = j2;
            TLRPC.Chat chat2 = getMessagesController().getChat(Long.valueOf(-fromChatId));
            string = chat2 != null ? getTitle(chat2) : null;
        }
        if (string == null) {
            return null;
        }
        if (j3 != 0) {
            chat = getMessagesController().getChat(Long.valueOf(j3));
            if (chat == null) {
                return null;
            }
        } else {
            chat = null;
        }
        if (DialogObject.isEncryptedDialog(j)) {
            return LocaleController.getString(C2797R.string.YouHaveNewMessage);
        }
        if (j3 != 0 || fromChatId == 0) {
            if (j3 != 0) {
                boolean z3 = ChatObject.isChannel(chat) && !chat.megagroup;
                if (z2 && ((!z3 && notificationsSettings.getBoolean("EnablePreviewGroup", true)) || (z3 && notificationsSettings.getBoolean("EnablePreviewChannel", true)))) {
                    TLRPC.Message message2 = messageObject.messageOwner;
                    if (message2 instanceof TLRPC.TL_messageService) {
                        TLRPC.MessageAction messageAction = message2.action;
                        if (messageAction instanceof TLRPC.TL_messageActionChatAddUser) {
                            long jLongValue = messageAction.user_id;
                            if (jLongValue == 0 && messageAction.users.size() == 1) {
                                jLongValue = messageObject.messageOwner.action.users.get(0).longValue();
                            }
                            if (jLongValue == 0) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < messageObject.messageOwner.action.users.size(); i++) {
                                    TLRPC.User user2 = getMessagesController().getUser(messageObject.messageOwner.action.users.get(i));
                                    if (user2 != null) {
                                        String userName2 = UserObject.getUserName(user2);
                                        if (sb.length() != 0) {
                                            sb.append(", ");
                                        }
                                        sb.append(userName2);
                                    }
                                }
                                return LocaleController.formatString(C2797R.string.NotificationGroupAddMember, string, getTitle(chat), sb.toString());
                            }
                            if (messageObject.messageOwner.peer_id.channel_id != 0 && !chat.megagroup) {
                                return LocaleController.formatString(C2797R.string.ChannelAddedByNotification, string, getTitle(chat));
                            }
                            if (jLongValue == clientUserId) {
                                return LocaleController.formatString(C2797R.string.NotificationInvitedToGroup, string, getTitle(chat));
                            }
                            TLRPC.User user3 = getMessagesController().getUser(Long.valueOf(jLongValue));
                            if (user3 == null) {
                                return null;
                            }
                            if (fromChatId == user3.f1407id) {
                                if (chat.megagroup) {
                                    return LocaleController.formatString(C2797R.string.NotificationGroupAddSelfMega, string, getTitle(chat));
                                }
                                return LocaleController.formatString(C2797R.string.NotificationGroupAddSelf, string, getTitle(chat));
                            }
                            return LocaleController.formatString(C2797R.string.NotificationGroupAddMember, string, getTitle(chat), UserObject.getUserName(user3));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionGroupCall) {
                            if (messageAction.duration != 0) {
                                return LocaleController.formatString(C2797R.string.NotificationGroupEndedCall, string, getTitle(chat));
                            }
                            return LocaleController.formatString(C2797R.string.NotificationGroupCreatedCall, string, getTitle(chat));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionGroupCallScheduled) {
                            return messageObject.messageText.toString();
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionInviteToGroupCall) {
                            long jLongValue2 = messageAction.user_id;
                            if (jLongValue2 == 0 && messageAction.users.size() == 1) {
                                jLongValue2 = messageObject.messageOwner.action.users.get(0).longValue();
                            }
                            if (jLongValue2 != 0) {
                                if (jLongValue2 == clientUserId) {
                                    return LocaleController.formatString(C2797R.string.NotificationGroupInvitedYouToCall, string, getTitle(chat));
                                }
                                TLRPC.User user4 = getMessagesController().getUser(Long.valueOf(jLongValue2));
                                if (user4 == null) {
                                    return null;
                                }
                                return LocaleController.formatString(C2797R.string.NotificationGroupInvitedToCall, string, getTitle(chat), UserObject.getUserName(user4));
                            }
                            StringBuilder sb2 = new StringBuilder();
                            for (int i2 = 0; i2 < messageObject.messageOwner.action.users.size(); i2++) {
                                TLRPC.User user5 = getMessagesController().getUser(messageObject.messageOwner.action.users.get(i2));
                                if (user5 != null) {
                                    String userName3 = UserObject.getUserName(user5);
                                    if (sb2.length() != 0) {
                                        sb2.append(", ");
                                    }
                                    sb2.append(userName3);
                                }
                            }
                            return LocaleController.formatString(C2797R.string.NotificationGroupInvitedToCall, string, getTitle(chat), sb2.toString());
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionGiftCode) {
                            TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) messageAction;
                            TLRPC.Chat chat3 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-DialogObject.getPeerDialogId(tL_messageActionGiftCode.boost_peer)));
                            String title = chat3 != null ? getTitle(chat3) : null;
                            if (title == null) {
                                return LocaleController.getString(C2797R.string.BoostingReceivedGiftNoName);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationMessageGiftCode, title, LocaleController.formatPluralString("Months", tL_messageActionGiftCode.months, new Object[0]));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChatJoinedByLink) {
                            return LocaleController.formatString(C2797R.string.NotificationInvitedToGroupByLink, string, getTitle(chat));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChatEditTitle) {
                            return LocaleController.formatString(C2797R.string.NotificationEditedGroupName, string, messageAction.title);
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionTodoCompletions) {
                            return messageObject.messageText.toString();
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionTodoAppendTasks) {
                            return messageObject.messageText.toString();
                        }
                        if ((messageAction instanceof TLRPC.TL_messageActionChatEditPhoto) || (messageAction instanceof TLRPC.TL_messageActionChatDeletePhoto)) {
                            if (message2.peer_id.channel_id != 0 && !chat.megagroup) {
                                if (messageObject.isVideoAvatar()) {
                                    return LocaleController.formatString(C2797R.string.ChannelVideoEditNotification, getTitle(chat));
                                }
                                return LocaleController.formatString(C2797R.string.ChannelPhotoEditNotification, getTitle(chat));
                            }
                            if (messageObject.isVideoAvatar()) {
                                return LocaleController.formatString(C2797R.string.NotificationEditedGroupVideo, string, getTitle(chat));
                            }
                            return LocaleController.formatString(C2797R.string.NotificationEditedGroupPhoto, string, getTitle(chat));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChatDeleteUser) {
                            long j4 = messageAction.user_id;
                            if (j4 == clientUserId) {
                                return LocaleController.formatString(C2797R.string.NotificationGroupKickYou, string, getTitle(chat));
                            }
                            if (j4 == fromChatId) {
                                return LocaleController.formatString(C2797R.string.NotificationGroupLeftMember, string, getTitle(chat));
                            }
                            TLRPC.User user6 = getMessagesController().getUser(Long.valueOf(messageObject.messageOwner.action.user_id));
                            if (user6 == null) {
                                return null;
                            }
                            return LocaleController.formatString(C2797R.string.NotificationGroupKickMember, string, getTitle(chat), UserObject.getUserName(user6));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChatCreate) {
                            return messageObject.messageText.toString();
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChannelCreate) {
                            return messageObject.messageText.toString();
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChatMigrateTo) {
                            return LocaleController.formatString(C2797R.string.ActionMigrateFromGroupNotify, getTitle(chat));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChannelMigrateFrom) {
                            return LocaleController.formatString(C2797R.string.ActionMigrateFromGroupNotify, messageAction.title);
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionScreenshotTaken) {
                            return messageObject.messageText.toString();
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionPinMessage) {
                            if (!ChatObject.isChannel(chat) || chat.megagroup) {
                                MessageObject messageObject2 = messageObject.replyMessageObject;
                                if (messageObject2 == null) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedNoText, string, getTitle(chat));
                                }
                                if (messageObject2.isMusic()) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedMusic, string, getTitle(chat));
                                }
                                if (messageObject2.isVideo()) {
                                    if (!TextUtils.isEmpty(messageObject2.messageOwner.message)) {
                                        return LocaleController.formatString(C2797R.string.NotificationActionPinnedText, string, "📹 " + messageObject2.messageOwner.message, getTitle(chat));
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedVideo, string, getTitle(chat));
                                }
                                if (messageObject2.isGif()) {
                                    if (!TextUtils.isEmpty(messageObject2.messageOwner.message)) {
                                        return LocaleController.formatString(C2797R.string.NotificationActionPinnedText, string, "🎬 " + messageObject2.messageOwner.message, getTitle(chat));
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedGif, string, getTitle(chat));
                                }
                                if (messageObject2.isVoice()) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedVoice, string, getTitle(chat));
                                }
                                if (messageObject2.isRoundVideo()) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedRound, string, getTitle(chat));
                                }
                                if (messageObject2.isSticker() || messageObject2.isAnimatedSticker()) {
                                    String stickerEmoji = messageObject2.getStickerEmoji();
                                    if (stickerEmoji != null) {
                                        return LocaleController.formatString(C2797R.string.NotificationActionPinnedStickerEmoji, string, getTitle(chat), stickerEmoji);
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedSticker, string, getTitle(chat));
                                }
                                TLRPC.Message message3 = messageObject2.messageOwner;
                                TLRPC.MessageMedia messageMedia = message3.media;
                                if (messageMedia instanceof TLRPC.TL_messageMediaDocument) {
                                    if (!TextUtils.isEmpty(message3.message)) {
                                        return LocaleController.formatString(C2797R.string.NotificationActionPinnedText, string, "📎 " + messageObject2.messageOwner.message, getTitle(chat));
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedFile, string, getTitle(chat));
                                }
                                if ((messageMedia instanceof TLRPC.TL_messageMediaGeo) || (messageMedia instanceof TLRPC.TL_messageMediaVenue)) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedGeo, string, getTitle(chat));
                                }
                                if (messageMedia instanceof TLRPC.TL_messageMediaGeoLive) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedGeoLive, string, getTitle(chat));
                                }
                                if (messageMedia instanceof TLRPC.TL_messageMediaContact) {
                                    TLRPC.TL_messageMediaContact tL_messageMediaContact = (TLRPC.TL_messageMediaContact) messageObject.messageOwner.media;
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedContact2, string, getTitle(chat), ContactsController.formatName(tL_messageMediaContact.first_name, tL_messageMediaContact.last_name));
                                }
                                if (messageMedia instanceof TLRPC.TL_messageMediaPoll) {
                                    TLRPC.TL_messageMediaPoll tL_messageMediaPoll = (TLRPC.TL_messageMediaPoll) messageMedia;
                                    if (tL_messageMediaPoll.poll.quiz) {
                                        return LocaleController.formatString(C2797R.string.NotificationActionPinnedQuiz2, string, getTitle(chat), tL_messageMediaPoll.poll.question.text);
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedPoll2, string, getTitle(chat), tL_messageMediaPoll.poll.question.text);
                                }
                                if (messageMedia instanceof TLRPC.TL_messageMediaToDo) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedTodo2, string, getTitle(chat), ((TLRPC.TL_messageMediaToDo) messageMedia).todo.title.text);
                                }
                                if (messageMedia instanceof TLRPC.TL_messageMediaPhoto) {
                                    if (!TextUtils.isEmpty(message3.message)) {
                                        return LocaleController.formatString(C2797R.string.NotificationActionPinnedText, string, "🖼 " + messageObject2.messageOwner.message, getTitle(chat));
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedPhoto, string, getTitle(chat));
                                }
                                if (messageMedia instanceof TLRPC.TL_messageMediaGame) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedGame, string, getTitle(chat));
                                }
                                CharSequence charSequence = messageObject2.messageText;
                                if (charSequence != null && charSequence.length() > 0) {
                                    CharSequence charSequence2 = messageObject2.messageText;
                                    if (charSequence2.length() > 20) {
                                        charSequence2 = ((Object) charSequence2.subSequence(0, 20)) + "...";
                                    }
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedText, string, charSequence2, getTitle(chat));
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedNoText, string, getTitle(chat));
                            }
                            MessageObject messageObject3 = messageObject.replyMessageObject;
                            if (messageObject3 == null) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedNoTextChannel, getTitle(chat));
                            }
                            if (messageObject3.isMusic()) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedMusicChannel, getTitle(chat));
                            }
                            if (messageObject3.isVideo()) {
                                if (!TextUtils.isEmpty(messageObject3.messageOwner.message)) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedTextChannel, getTitle(chat), "📹 " + messageObject3.messageOwner.message);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedVideoChannel, getTitle(chat));
                            }
                            if (messageObject3.isGif()) {
                                if (!TextUtils.isEmpty(messageObject3.messageOwner.message)) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedTextChannel, getTitle(chat), "🎬 " + messageObject3.messageOwner.message);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedGifChannel, getTitle(chat));
                            }
                            if (messageObject3.isVoice()) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedVoiceChannel, getTitle(chat));
                            }
                            if (messageObject3.isRoundVideo()) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedRoundChannel, getTitle(chat));
                            }
                            if (messageObject3.isSticker() || messageObject3.isAnimatedSticker()) {
                                String stickerEmoji2 = messageObject3.getStickerEmoji();
                                if (stickerEmoji2 != null) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedStickerEmojiChannel, getTitle(chat), stickerEmoji2);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedStickerChannel, getTitle(chat));
                            }
                            TLRPC.Message message4 = messageObject3.messageOwner;
                            TLRPC.MessageMedia messageMedia2 = message4.media;
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaDocument) {
                                if (!TextUtils.isEmpty(message4.message)) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedTextChannel, getTitle(chat), "📎 " + messageObject3.messageOwner.message);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedFileChannel, getTitle(chat));
                            }
                            if ((messageMedia2 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia2 instanceof TLRPC.TL_messageMediaVenue)) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedGeoChannel, getTitle(chat));
                            }
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaGeoLive) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedGeoLiveChannel, getTitle(chat));
                            }
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaContact) {
                                TLRPC.TL_messageMediaContact tL_messageMediaContact2 = (TLRPC.TL_messageMediaContact) messageObject.messageOwner.media;
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedContactChannel2, getTitle(chat), ContactsController.formatName(tL_messageMediaContact2.first_name, tL_messageMediaContact2.last_name));
                            }
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaPoll) {
                                TLRPC.TL_messageMediaPoll tL_messageMediaPoll2 = (TLRPC.TL_messageMediaPoll) messageMedia2;
                                if (tL_messageMediaPoll2.poll.quiz) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedQuizChannel2, getTitle(chat), tL_messageMediaPoll2.poll.question.text);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedPollChannel2, getTitle(chat), tL_messageMediaPoll2.poll.question.text);
                            }
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaToDo) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedTodoChannel2, getTitle(chat), ((TLRPC.TL_messageMediaToDo) messageMedia2).todo.title.text);
                            }
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaPhoto) {
                                if (!TextUtils.isEmpty(message4.message)) {
                                    return LocaleController.formatString(C2797R.string.NotificationActionPinnedTextChannel, getTitle(chat), "🖼 " + messageObject3.messageOwner.message);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedPhotoChannel, getTitle(chat));
                            }
                            if (messageMedia2 instanceof TLRPC.TL_messageMediaGame) {
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedGameChannel, getTitle(chat));
                            }
                            CharSequence charSequence3 = messageObject3.messageText;
                            if (charSequence3 != null && charSequence3.length() > 0) {
                                CharSequence charSequence4 = messageObject3.messageText;
                                if (charSequence4.length() > 20) {
                                    charSequence4 = ((Object) charSequence4.subSequence(0, 20)) + "...";
                                }
                                return LocaleController.formatString(C2797R.string.NotificationActionPinnedTextChannel, getTitle(chat), charSequence4);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationActionPinnedNoTextChannel, getTitle(chat));
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionGameScore) {
                            return messageObject.messageText.toString();
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionSetChatTheme) {
                            String themeEmoticonOrGiftTitle = TlUtils.getThemeEmoticonOrGiftTitle(((TLRPC.TL_messageActionSetChatTheme) messageAction).theme);
                            if (TextUtils.isEmpty(themeEmoticonOrGiftTitle)) {
                                if (j == clientUserId) {
                                    return LocaleController.formatString(C2797R.string.ChatThemeDisabledYou, new Object[0]);
                                }
                                return LocaleController.formatString("ChatThemeDisabled", C2797R.string.ChatThemeDisabled, string, themeEmoticonOrGiftTitle);
                            }
                            if (j == clientUserId) {
                                return LocaleController.formatString(C2797R.string.ChatThemeChangedYou, themeEmoticonOrGiftTitle);
                            }
                            return LocaleController.formatString(C2797R.string.ChatThemeChangedTo, string, themeEmoticonOrGiftTitle);
                        }
                        if (messageAction instanceof TLRPC.TL_messageActionChatJoinedByRequest) {
                            return messageObject.messageText.toString();
                        }
                    } else {
                        if (ChatObject.isChannel(chat) && !chat.megagroup) {
                            if (messageObject.isMediaEmpty()) {
                                if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                    String string3 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, messageObject.messageOwner.message);
                                    zArr[0] = true;
                                    return string3;
                                }
                                return LocaleController.formatString(C2797R.string.ChannelMessageNoText, string);
                            }
                            if (messageObject.type == 29 && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPaidMedia)) {
                                return LocaleController.formatPluralString("NotificationChannelMessagePaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject)).stars_amount, getTitle(chat));
                            }
                            TLRPC.Message message5 = messageObject.messageOwner;
                            if (message5.media instanceof TLRPC.TL_messageMediaPhoto) {
                                if (!z && !TextUtils.isEmpty(message5.message)) {
                                    String string4 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "🖼 " + messageObject.messageOwner.message);
                                    zArr[0] = true;
                                    return string4;
                                }
                                return LocaleController.formatString(C2797R.string.ChannelMessagePhoto, string);
                            }
                            if (messageObject.isVideo()) {
                                if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                    String string5 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "📹 " + messageObject.messageOwner.message);
                                    zArr[0] = true;
                                    return string5;
                                }
                                return LocaleController.formatString(C2797R.string.ChannelMessageVideo, string);
                            }
                            if (messageObject.isVoice()) {
                                return LocaleController.formatString(C2797R.string.ChannelMessageAudio, string);
                            }
                            if (messageObject.isRoundVideo()) {
                                return LocaleController.formatString(C2797R.string.ChannelMessageRound, string);
                            }
                            if (messageObject.isMusic()) {
                                return LocaleController.formatString(C2797R.string.ChannelMessageMusic, string);
                            }
                            TLRPC.MessageMedia messageMedia3 = messageObject.messageOwner.media;
                            if (messageMedia3 instanceof TLRPC.TL_messageMediaContact) {
                                TLRPC.TL_messageMediaContact tL_messageMediaContact3 = (TLRPC.TL_messageMediaContact) messageMedia3;
                                return LocaleController.formatString(C2797R.string.ChannelMessageContact2, string, ContactsController.formatName(tL_messageMediaContact3.first_name, tL_messageMediaContact3.last_name));
                            }
                            if (messageMedia3 instanceof TLRPC.TL_messageMediaPoll) {
                                TLRPC.Poll poll = ((TLRPC.TL_messageMediaPoll) messageMedia3).poll;
                                if (poll.quiz) {
                                    return LocaleController.formatString(C2797R.string.ChannelMessageQuiz2, string, poll.question.text);
                                }
                                return LocaleController.formatString(C2797R.string.ChannelMessagePoll2, string, poll.question.text);
                            }
                            if (messageMedia3 instanceof TLRPC.TL_messageMediaToDo) {
                                return LocaleController.formatString(C2797R.string.ChannelMessageTodo2, string, ((TLRPC.TL_messageMediaToDo) messageMedia3).todo.title.text);
                            }
                            if (messageMedia3 instanceof TLRPC.TL_messageMediaGiveaway) {
                                TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway = (TLRPC.TL_messageMediaGiveaway) messageMedia3;
                                return LocaleController.formatString(C2797R.string.NotificationMessageChannelGiveaway, getTitle(chat), Integer.valueOf(tL_messageMediaGiveaway.quantity), Integer.valueOf(tL_messageMediaGiveaway.months));
                            }
                            if ((messageMedia3 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia3 instanceof TLRPC.TL_messageMediaVenue)) {
                                return LocaleController.formatString(C2797R.string.ChannelMessageMap, string);
                            }
                            if (messageMedia3 instanceof TLRPC.TL_messageMediaGeoLive) {
                                return LocaleController.formatString(C2797R.string.ChannelMessageLiveLocation, string);
                            }
                            if (messageMedia3 instanceof TLRPC.TL_messageMediaDocument) {
                                if (messageObject.isSticker() || messageObject.isAnimatedSticker()) {
                                    String stickerEmoji3 = messageObject.getStickerEmoji();
                                    if (stickerEmoji3 != null) {
                                        return LocaleController.formatString(C2797R.string.ChannelMessageStickerEmoji, string, stickerEmoji3);
                                    }
                                    return LocaleController.formatString(C2797R.string.ChannelMessageSticker, string);
                                }
                                if (messageObject.isGif()) {
                                    if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                        String string6 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "🎬 " + messageObject.messageOwner.message);
                                        zArr[0] = true;
                                        return string6;
                                    }
                                    return LocaleController.formatString(C2797R.string.ChannelMessageGIF, string);
                                }
                                if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                    String string7 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "📎 " + messageObject.messageOwner.message);
                                    zArr[0] = true;
                                    return string7;
                                }
                                return LocaleController.formatString(C2797R.string.ChannelMessageDocument, string);
                            }
                            if (!z && !TextUtils.isEmpty(messageObject.messageText)) {
                                String string8 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, messageObject.messageText);
                                zArr[0] = true;
                                return string8;
                            }
                            return LocaleController.formatString(C2797R.string.ChannelMessageNoText, string);
                        }
                        if (messageObject.isMediaEmpty()) {
                            if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupText, string, getTitle(chat), messageObject.messageOwner.message);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupNoText, string, getTitle(chat));
                        }
                        if (messageObject.type == 29 && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPaidMedia)) {
                            return LocaleController.formatPluralString("NotificationChatMessagePaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject)).stars_amount, string, getTitle(chat));
                        }
                        TLRPC.Message message6 = messageObject.messageOwner;
                        if (message6.media instanceof TLRPC.TL_messageMediaPhoto) {
                            if (!z && !TextUtils.isEmpty(message6.message)) {
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupText, string, getTitle(chat), "🖼 " + messageObject.messageOwner.message);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupPhoto, string, getTitle(chat));
                        }
                        if (messageObject.isVideo()) {
                            if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupText, string, getTitle(chat), "📹 " + messageObject.messageOwner.message);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupVideo, string, getTitle(chat));
                        }
                        if (messageObject.isVoice()) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupAudio, string, getTitle(chat));
                        }
                        if (messageObject.isRoundVideo()) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupRound, string, getTitle(chat));
                        }
                        if (messageObject.isMusic()) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupMusic, string, getTitle(chat));
                        }
                        TLRPC.MessageMedia messageMedia4 = messageObject.messageOwner.media;
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaContact) {
                            TLRPC.TL_messageMediaContact tL_messageMediaContact4 = (TLRPC.TL_messageMediaContact) messageMedia4;
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupContact2, string, getTitle(chat), ContactsController.formatName(tL_messageMediaContact4.first_name, tL_messageMediaContact4.last_name));
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaPoll) {
                            TLRPC.TL_messageMediaPoll tL_messageMediaPoll3 = (TLRPC.TL_messageMediaPoll) messageMedia4;
                            if (tL_messageMediaPoll3.poll.quiz) {
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupQuiz2, string, getTitle(chat), tL_messageMediaPoll3.poll.question.text);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupPoll2, string, getTitle(chat), tL_messageMediaPoll3.poll.question.text);
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaToDo) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupTodo2, string, getTitle(chat), ((TLRPC.TL_messageMediaToDo) messageMedia4).todo.title.text);
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaGame) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupGame, string, getTitle(chat), messageObject.messageOwner.media.game.title);
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaGiveaway) {
                            TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway2 = (TLRPC.TL_messageMediaGiveaway) messageMedia4;
                            return LocaleController.formatString(C2797R.string.NotificationMessageChannelGiveaway, getTitle(chat), Integer.valueOf(tL_messageMediaGiveaway2.quantity), Integer.valueOf(tL_messageMediaGiveaway2.months));
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaGiveawayResults) {
                            return LocaleController.formatString(C2797R.string.BoostingGiveawayResults, new Object[0]);
                        }
                        if ((messageMedia4 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia4 instanceof TLRPC.TL_messageMediaVenue)) {
                            return LocaleController.formatString("NotificationMessageGroupMap", C2797R.string.NotificationMessageGroupMap, string, getTitle(chat));
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaGeoLive) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupLiveLocation, string, getTitle(chat));
                        }
                        if (messageMedia4 instanceof TLRPC.TL_messageMediaDocument) {
                            if (messageObject.isSticker() || messageObject.isAnimatedSticker()) {
                                String stickerEmoji4 = messageObject.getStickerEmoji();
                                if (stickerEmoji4 != null) {
                                    return LocaleController.formatString(C2797R.string.NotificationMessageGroupStickerEmoji, string, getTitle(chat), stickerEmoji4);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupSticker, string, getTitle(chat));
                            }
                            if (messageObject.isGif()) {
                                if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                    return LocaleController.formatString(C2797R.string.NotificationMessageGroupText, string, getTitle(chat), "🎬 " + messageObject.messageOwner.message);
                                }
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupGif, string, getTitle(chat));
                            }
                            if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                return LocaleController.formatString(C2797R.string.NotificationMessageGroupText, string, getTitle(chat), "📎 " + messageObject.messageOwner.message);
                            }
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupDocument, string, getTitle(chat));
                        }
                        if (!z && !TextUtils.isEmpty(messageObject.messageText)) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageGroupText, string, getTitle(chat), messageObject.messageText);
                        }
                        return LocaleController.formatString(C2797R.string.NotificationMessageGroupNoText, string, getTitle(chat));
                    }
                } else {
                    if (zArr2 != null) {
                        zArr2[0] = false;
                    }
                    if (ChatObject.isChannel(chat) && !chat.megagroup) {
                        return LocaleController.formatString(C2797R.string.ChannelMessageNoText, string);
                    }
                    if (messageObject.type == 29 && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPaidMedia)) {
                        return LocaleController.formatPluralString("NotificationMessagePaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject)).stars_amount, string);
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessageGroupNoText, string, getTitle(chat));
                }
            }
        } else if (z2 && notificationsSettings.getBoolean("EnablePreviewAll", true)) {
            TLRPC.Message message7 = messageObject.messageOwner;
            if (message7 instanceof TLRPC.TL_messageService) {
                TLRPC.MessageAction messageAction2 = message7.action;
                if ((messageAction2 instanceof TLRPC.TL_messageActionChangeCreator) || (messageAction2 instanceof TLRPC.TL_messageActionNewCreatorPending)) {
                    return messageObject.messageText.toString();
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionSetSameChatWallPaper) {
                    return LocaleController.getString(C2797R.string.WallpaperSameNotification);
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionSetChatWallPaper) {
                    return LocaleController.getString(C2797R.string.WallpaperNotification);
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionGeoProximityReached) {
                    return messageObject.messageText.toString();
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionTodoCompletions) {
                    return messageObject.messageText.toString();
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionTodoAppendTasks) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction2 instanceof TLRPC.TL_messageActionUserJoined) || (messageAction2 instanceof TLRPC.TL_messageActionContactSignUp)) {
                    return LocaleController.formatString(C2797R.string.NotificationContactJoined, string);
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionUserUpdatedPhoto) {
                    return LocaleController.formatString(C2797R.string.NotificationContactNewPhoto, string);
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionLoginUnknownLocation) {
                    String string9 = LocaleController.formatString(C2797R.string.formatDateAtTime, LocaleController.getInstance().getFormatterYear().format(((long) messageObject.messageOwner.date) * 1000), LocaleController.getInstance().getFormatterDay().format(((long) messageObject.messageOwner.date) * 1000));
                    int i3 = C2797R.string.NotificationUnrecognizedDevice;
                    String str = getUserConfig().getCurrentUser().first_name;
                    TLRPC.MessageAction messageAction3 = messageObject.messageOwner.action;
                    return LocaleController.formatString(i3, str, string9, messageAction3.title, messageAction3.address);
                }
                if ((messageAction2 instanceof TLRPC.TL_messageActionGameScore) || (messageAction2 instanceof TLRPC.TL_messageActionPaymentSent) || (messageAction2 instanceof TLRPC.TL_messageActionPaymentSentMe)) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction2 instanceof TLRPC.TL_messageActionStarGift) || (messageAction2 instanceof TLRPC.TL_messageActionGiftPremium) || (messageAction2 instanceof TLRPC.TL_messageActionGiftTon)) {
                    return messageObject.messageText.toString();
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionStarGiftUnique) {
                    return messageObject.messageText.toString();
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionSuggestBirthday) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction2 instanceof TLRPC.TL_messageActionPaidMessagesRefunded) || (messageAction2 instanceof TLRPC.TL_messageActionPaidMessagesPrice)) {
                    return messageObject.messageText.toString();
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionPhoneCall) {
                    if (messageAction2.video) {
                        return LocaleController.getString(C2797R.string.CallMessageVideoIncomingMissed);
                    }
                    return LocaleController.getString(C2797R.string.CallMessageIncomingMissed);
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionConferenceCall) {
                    if (messageAction2.video) {
                        return LocaleController.getString(C2797R.string.CallMessageVideoIncomingConferenceMissed);
                    }
                    return LocaleController.getString(C2797R.string.CallMessageIncomingConferenceMissed);
                }
                if (messageAction2 instanceof TLRPC.TL_messageActionSetChatTheme) {
                    String themeEmoticonOrGiftTitle2 = TlUtils.getThemeEmoticonOrGiftTitle(((TLRPC.TL_messageActionSetChatTheme) messageAction2).theme);
                    if (TextUtils.isEmpty(themeEmoticonOrGiftTitle2)) {
                        if (j == clientUserId) {
                            string2 = LocaleController.formatString(C2797R.string.ChatThemeDisabledYou, new Object[0]);
                        } else {
                            string2 = LocaleController.formatString(C2797R.string.ChatThemeDisabled, string, themeEmoticonOrGiftTitle2);
                        }
                    } else if (j == clientUserId) {
                        string2 = LocaleController.formatString(C2797R.string.ChatThemeChangedYou, themeEmoticonOrGiftTitle2);
                    } else {
                        string2 = LocaleController.formatString(C2797R.string.ChatThemeChangedTo, string, themeEmoticonOrGiftTitle2);
                    }
                    zArr[0] = true;
                    return string2;
                }
            } else {
                if (messageObject.isMediaEmpty()) {
                    if (!z) {
                        if (!TextUtils.isEmpty(messageObject.messageOwner.message)) {
                            String string10 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, messageObject.messageOwner.message);
                            zArr[0] = true;
                            return string10;
                        }
                        return LocaleController.formatString(C2797R.string.NotificationMessageNoText, string);
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessageNoText, string);
                }
                TLRPC.Message message8 = messageObject.messageOwner;
                if (message8.media instanceof TLRPC.TL_messageMediaPhoto) {
                    if (!z && !TextUtils.isEmpty(message8.message)) {
                        String string11 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "🖼 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string11;
                    }
                    if (messageObject.messageOwner.media.ttl_seconds != 0) {
                        return LocaleController.formatString(C2797R.string.NotificationMessageSDPhoto, string);
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessagePhoto, string);
                }
                if (messageObject.isVideo()) {
                    if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        String string12 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "📹 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string12;
                    }
                    if (messageObject.messageOwner.media.ttl_seconds != 0) {
                        return LocaleController.formatString(C2797R.string.NotificationMessageSDVideo, string);
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessageVideo, string);
                }
                if (messageObject.isGame()) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageGame, string, messageObject.messageOwner.media.game.title);
                }
                if (messageObject.isVoice()) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageAudio, string);
                }
                if (messageObject.isRoundVideo()) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageRound, string);
                }
                if (messageObject.isMusic()) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageMusic, string);
                }
                TLRPC.MessageMedia messageMedia5 = messageObject.messageOwner.media;
                if (messageMedia5 instanceof TLRPC.TL_messageMediaContact) {
                    TLRPC.TL_messageMediaContact tL_messageMediaContact5 = (TLRPC.TL_messageMediaContact) messageMedia5;
                    return LocaleController.formatString(C2797R.string.NotificationMessageContact2, string, ContactsController.formatName(tL_messageMediaContact5.first_name, tL_messageMediaContact5.last_name));
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGiveaway) {
                    TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway3 = (TLRPC.TL_messageMediaGiveaway) messageMedia5;
                    return LocaleController.formatString(C2797R.string.NotificationMessageChannelGiveaway, string, Integer.valueOf(tL_messageMediaGiveaway3.quantity), Integer.valueOf(tL_messageMediaGiveaway3.months));
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGiveawayResults) {
                    return LocaleController.formatString(C2797R.string.BoostingGiveawayResults, new Object[0]);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaPoll) {
                    TLRPC.Poll poll2 = ((TLRPC.TL_messageMediaPoll) messageMedia5).poll;
                    if (poll2.quiz) {
                        return LocaleController.formatString(C2797R.string.NotificationMessageQuiz2, string, poll2.question.text);
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessagePoll2, string, poll2.question.text);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaToDo) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageTodo2, string, ((TLRPC.TL_messageMediaToDo) messageMedia5).todo.title.text);
                }
                if ((messageMedia5 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia5 instanceof TLRPC.TL_messageMediaVenue)) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageMap, string);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGeoLive) {
                    return LocaleController.formatString(C2797R.string.NotificationMessageLiveLocation, string);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaDocument) {
                    if (messageObject.isSticker() || messageObject.isAnimatedSticker()) {
                        String stickerEmoji5 = messageObject.getStickerEmoji();
                        if (stickerEmoji5 != null) {
                            return LocaleController.formatString(C2797R.string.NotificationMessageStickerEmoji, string, stickerEmoji5);
                        }
                        return LocaleController.formatString(C2797R.string.NotificationMessageSticker, string);
                    }
                    if (messageObject.isGif()) {
                        if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                            String string13 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "🎬 " + messageObject.messageOwner.message);
                            zArr[0] = true;
                            return string13;
                        }
                        return LocaleController.formatString(C2797R.string.NotificationMessageGif, string);
                    }
                    if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        String string14 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, "📎 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string14;
                    }
                    return LocaleController.formatString(C2797R.string.NotificationMessageDocument, string);
                }
                if (!z && !TextUtils.isEmpty(messageObject.messageText)) {
                    String string15 = LocaleController.formatString(C2797R.string.NotificationMessageText, string, messageObject.messageText);
                    zArr[0] = true;
                    return string15;
                }
                return LocaleController.formatString(C2797R.string.NotificationMessageNoText, string);
            }
        } else {
            if (zArr2 != null) {
                zArr2[0] = false;
            }
            return LocaleController.formatString(C2797R.string.NotificationMessageNoText, string);
        }
        return null;
    }

    private void scheduleNotificationRepeat() {
        try {
            Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) NotificationRepeat.class);
            intent.putExtra("currentAccount", this.currentAccount);
            PendingIntent service = PendingIntent.getService(ApplicationLoader.applicationContext, 0, intent, 33554432);
            int i = getAccountInstance().getNotificationsSettings().getInt("repeat_messages", 60);
            if (i > 0 && this.personalCount > 0) {
                this.alarmManager.set(2, SystemClock.elapsedRealtime() + ((long) (i * 60000)), service);
            } else {
                this.alarmManager.cancel(service);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    private boolean isPersonalMessage(MessageObject messageObject) {
        TLRPC.MessageAction messageAction;
        TLRPC.Message message = messageObject.messageOwner;
        TLRPC.Peer peer = message.peer_id;
        return (peer != null && peer.chat_id == 0 && peer.channel_id == 0 && ((messageAction = message.action) == null || (messageAction instanceof TLRPC.TL_messageActionEmpty))) || messageObject.isStoryReactionPush;
    }

    private int getNotifyOverride(SharedPreferences sharedPreferences, long j, long j2) {
        int property = this.dialogsNotificationsFacade.getProperty(NotificationsSettingsFacade.PROPERTY_NOTIFY, j, j2, -1);
        if (property != 3 || this.dialogsNotificationsFacade.getProperty(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL, j, j2, 0) < getConnectionsManager().getCurrentTime()) {
            return property;
        }
        return 2;
    }

    public /* synthetic */ void lambda$showNotifications$35() {
        showOrUpdateNotification(false);
    }

    public void showNotifications() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showNotifications$35();
            }
        });
    }

    public void hideNotifications() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hideNotifications$36();
            }
        });
    }

    public /* synthetic */ void lambda$hideNotifications$36() {
        notificationManager.cancel(this.notificationId);
        this.lastWearNotifiedMessageId.clear();
        int i = 0;
        while (true) {
            int size = this.wearNotificationsIds.size();
            LongSparseArray<Integer> longSparseArray = this.wearNotificationsIds;
            if (i < size) {
                notificationManager.cancel(longSparseArray.valueAt(i).intValue());
                i++;
            } else {
                longSparseArray.clear();
                return;
            }
        }
    }

    private void dismissNotification() {
        FileLog.m1045d("NotificationsController dismissNotification");
        try {
            notificationManager.cancel(this.notificationId);
            this.pushMessages.clear();
            this.pushMessagesDict.clear();
            this.lastWearNotifiedMessageId.clear();
            int i = 0;
            while (true) {
                int size = this.wearNotificationsIds.size();
                LongSparseArray<Integer> longSparseArray = this.wearNotificationsIds;
                if (i < size) {
                    if (!this.openedInBubbleDialogs.contains(Long.valueOf(longSparseArray.keyAt(i)))) {
                        notificationManager.cancel(this.wearNotificationsIds.valueAt(i).intValue());
                    }
                    i++;
                } else {
                    longSparseArray.clear();
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
                        }
                    });
                    return;
                }
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public ArrayList<MessageObject> getPushMessagesSnapshot() {
        ArrayList<MessageObject> arrayList;
        synchronized (this) {
            arrayList = new ArrayList<>(this.pushMessages);
        }
        return arrayList;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.fileLoaded) {
            final String str = (String) objArr[0];
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda52
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didReceivedNotification$38(str);
                }
            });
        }
    }

    public /* synthetic */ void lambda$didReceivedNotification$38(String str) {
        if (this.pendingVoiceLoads.remove(str)) {
            showOrUpdateNotification(true);
        }
    }

    private void playInChatSound() {
        if (!this.inChatSoundEnabled || MediaController.getInstance().isRecordingAudio()) {
            return;
        }
        try {
            if (audioManager.getRingerMode() == 0) {
                return;
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        try {
            if (getNotifyOverride(getAccountInstance().getNotificationsSettings(), this.openedDialogId, this.openedTopicId) == 2) {
                return;
            }
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$playInChatSound$40();
                }
            });
        } catch (Exception e2) {
            FileLog.m1048e(e2);
        }
    }

    public /* synthetic */ void lambda$playInChatSound$40() {
        if (Math.abs(SystemClock.elapsedRealtime() - this.lastSoundPlay) <= 500) {
            return;
        }
        try {
            if (this.soundPool == null) {
                SoundPool soundPool = new SoundPool(3, 1, 0);
                this.soundPool = soundPool;
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda57
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                        NotificationsController.$r8$lambda$FC_HwUM3QBEiCRgKZAe2XqW3hzc(soundPool2, i, i2);
                    }
                });
            }
            if (this.soundIn == 0 && !this.soundInLoaded) {
                this.soundInLoaded = true;
                this.soundIn = this.soundPool.load(ApplicationLoader.applicationContext, C2797R.raw.sound_in, 1);
            }
            int i = this.soundIn;
            if (i != 0) {
                try {
                    this.soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
            }
        } catch (Exception e2) {
            FileLog.m1048e(e2);
        }
    }

    public static /* synthetic */ void $r8$lambda$FC_HwUM3QBEiCRgKZAe2XqW3hzc(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            try {
                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    private void scheduleNotificationDelay(boolean z) {
        try {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1045d("delay notification start, onlineReason = " + z);
            }
            this.notificationDelayWakelock.acquire(10000L);
            DispatchQueue dispatchQueue = notificationsQueue;
            dispatchQueue.cancelRunnable(this.notificationDelayRunnable);
            dispatchQueue.postRunnable(this.notificationDelayRunnable, z ? 3000 : MediaDataController.MAX_STYLE_RUNS_COUNT);
        } catch (Exception e) {
            FileLog.m1048e(e);
            showOrUpdateNotification(this.notifyCheck);
        }
    }

    public void repeatNotificationMaybe() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$repeatNotificationMaybe$41();
            }
        });
    }

    public /* synthetic */ void lambda$repeatNotificationMaybe$41() {
        int i = Calendar.getInstance().get(11);
        if (i >= 11 && i <= 22) {
            notificationManager.cancel(this.notificationId);
            showOrUpdateNotification(true);
        } else {
            scheduleNotificationRepeat();
        }
    }

    private boolean isEmptyVibration(long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            return false;
        }
        for (long j : jArr) {
            if (j != 0) {
                return false;
            }
        }
        return true;
    }

    public void deleteNotificationChannel(long j, long j2) {
        deleteNotificationChannel(j, j2, -1);
    }

    /* JADX INFO: renamed from: deleteNotificationChannelInternal */
    public void lambda$deleteNotificationChannel$42(long j, long j2, int i) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            SharedPreferences.Editor editorEdit = notificationsSettings.edit();
            if (i == 0 || i == -1) {
                String str = "org.telegram.key" + j;
                if (j2 != 0) {
                    str = str + ".topic" + j2;
                }
                String string = notificationsSettings.getString(str, null);
                if (string != null) {
                    editorEdit.remove(str).remove(str.concat("_s"));
                    try {
                        systemNotificationManager.deleteNotificationChannel(string);
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("delete channel internal ".concat(string));
                    }
                }
            }
            if (i == 1 || i == -1) {
                String str2 = "org.telegram.keyia" + j;
                String string2 = notificationsSettings.getString(str2, null);
                if (string2 != null) {
                    editorEdit.remove(str2).remove(str2.concat("_s"));
                    try {
                        systemNotificationManager.deleteNotificationChannel(string2);
                    } catch (Exception e2) {
                        FileLog.m1048e(e2);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("delete channel internal ".concat(string2));
                    }
                }
            }
            editorEdit.apply();
        } catch (Exception e3) {
            FileLog.m1048e(e3);
        }
    }

    public void deleteNotificationChannel(final long j, final long j2, final int i) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteNotificationChannel$42(j, j2, i);
            }
        });
    }

    public void deleteNotificationChannelGlobal(int i) {
        deleteNotificationChannelGlobal(i, -1);
    }

    /* JADX INFO: renamed from: deleteNotificationChannelGlobalInternal */
    public void lambda$deleteNotificationChannelGlobal$43(int i, int i2) {
        String str;
        String str2;
        String str3;
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            SharedPreferences.Editor editorEdit = notificationsSettings.edit();
            if (i2 == 0 || i2 == -1) {
                if (i == 2) {
                    str = "channels";
                } else if (i == 0) {
                    str = "groups";
                } else if (i == 3) {
                    str = "stories";
                } else if (i == 4 || i == 5) {
                    str = "reactions";
                } else {
                    str = "private";
                }
                String string = notificationsSettings.getString(str, null);
                if (string != null) {
                    editorEdit.remove(str).remove(str.concat("_s"));
                    try {
                        systemNotificationManager.deleteNotificationChannel(string);
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("delete channel global internal ".concat(string));
                    }
                }
            }
            if (i2 == 1 || i2 == -1) {
                if (i == 2) {
                    str2 = "channels_ia";
                } else if (i == 0) {
                    str2 = "groups_ia";
                } else if (i == 3) {
                    str2 = "stories_ia";
                } else if (i == 4 || i == 5) {
                    str2 = "reactions_ia";
                } else {
                    str2 = "private_ia";
                }
                String string2 = notificationsSettings.getString(str2, null);
                if (string2 != null) {
                    editorEdit.remove(str2).remove(str2.concat("_s"));
                    try {
                        systemNotificationManager.deleteNotificationChannel(string2);
                    } catch (Exception e2) {
                        FileLog.m1048e(e2);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1045d("delete channel global internal ".concat(string2));
                    }
                }
            }
            if (i == 2) {
                str3 = "overwrite_channel";
            } else if (i == 0) {
                str3 = "overwrite_group";
            } else if (i == 3) {
                str3 = "overwrite_stories";
            } else if (i == 4 || i == 5) {
                str3 = "overwrite_reactions";
            } else {
                str3 = "overwrite_private";
            }
            editorEdit.remove(str3);
            editorEdit.apply();
        } catch (Exception e3) {
            FileLog.m1048e(e3);
        }
    }

    public void deleteNotificationChannelGlobal(final int i, final int i2) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteNotificationChannelGlobal$43(i, i2);
            }
        });
    }

    public void deleteAllNotificationChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteAllNotificationChannels$44();
            }
        });
    }

    public /* synthetic */ void lambda$deleteAllNotificationChannels$44() {
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            Map<String, ?> all = notificationsSettings.getAll();
            SharedPreferences.Editor editorEdit = notificationsSettings.edit();
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("org.telegram.key")) {
                    if (!key.endsWith("_s")) {
                        String str = (String) entry.getValue();
                        systemNotificationManager.deleteNotificationChannel(str);
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1045d("delete all channel " + str);
                        }
                    }
                    editorEdit.remove(key);
                }
            }
            editorEdit.apply();
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    private boolean unsupportedNotificationShortcut() {
        return Build.VERSION.SDK_INT < 29 || !SharedConfig.chatBubbles;
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x00ba  */
    @android.annotation.SuppressLint({"RestrictedApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String createNotificationShortcut(androidx.core.app.NotificationCompat.Builder r17, long r18, java.lang.String r20, org.telegram.tgnet.TLRPC.User r21, org.telegram.tgnet.TLRPC.Chat r22, androidx.core.app.Person r23, boolean r24) {
        /*
            Method dump skipped, instruction units count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.createNotificationShortcut(androidx.core.app.NotificationCompat$Builder, long, java.lang.String, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, androidx.core.app.Person, boolean):java.lang.String");
    }

    @TargetApi(26)
    public void ensureGroupsCreated() {
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        if (this.groupsCreated == null) {
            this.groupsCreated = Boolean.valueOf(notificationsSettings.getBoolean("groupsCreated5", false));
        }
        if (!this.groupsCreated.booleanValue()) {
            try {
                String str = this.currentAccount + "channel";
                List<NotificationChannel> notificationChannels = systemNotificationManager.getNotificationChannels();
                int size = notificationChannels.size();
                SharedPreferences.Editor editorEdit = null;
                for (int i = 0; i < size; i++) {
                    NotificationChannel notificationChannelM1070m = NotificationsController$$ExternalSyntheticApiModelOutline3.m1070m(notificationChannels.get(i));
                    String id = notificationChannelM1070m.getId();
                    if (id.startsWith(str)) {
                        int importance = notificationChannelM1070m.getImportance();
                        if (importance != 4 && importance != 5 && !id.contains("_ia_")) {
                            if (id.contains("_channels_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_channel").remove("vibrate_channel").remove("ChannelSoundPath").remove("ChannelSound");
                            } else if (id.contains("_reactions_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_react").remove("vibrate_react").remove("ReactionSoundPath").remove("ReactionSound");
                            } else if (id.contains("_groups_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_group").remove("vibrate_group").remove("GroupSoundPath").remove("GroupSound");
                            } else if (id.contains("_private_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_messages");
                                editorEdit.remove("priority_group").remove("vibrate_messages").remove("GlobalSoundPath").remove("GlobalSound");
                            } else {
                                long jLongValue = Utilities.parseLong(id.substring(9, id.indexOf(95, 9))).longValue();
                                if (jLongValue != 0) {
                                    if (editorEdit == null) {
                                        editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                    }
                                    editorEdit.remove("priority_" + jLongValue).remove("vibrate_" + jLongValue).remove("sound_path_" + jLongValue).remove("sound_" + jLongValue);
                                }
                            }
                        }
                        systemNotificationManager.deleteNotificationChannel(id);
                    }
                }
                if (editorEdit != null) {
                    editorEdit.apply();
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            notificationsSettings.edit().putBoolean("groupsCreated5", true).apply();
            this.groupsCreated = Boolean.TRUE;
        }
        if (this.channelGroupsCreated) {
            return;
        }
        List<NotificationChannelGroup> notificationChannelGroups = systemNotificationManager.getNotificationChannelGroups();
        String str2 = "channels" + this.currentAccount;
        String str3 = "groups" + this.currentAccount;
        String str4 = "private" + this.currentAccount;
        String str5 = "stories" + this.currentAccount;
        String str6 = "reactions" + this.currentAccount;
        String str7 = "other" + this.currentAccount;
        int size2 = notificationChannelGroups.size();
        String str8 = str7;
        String str9 = str6;
        String str10 = str5;
        String str11 = str4;
        for (int i2 = 0; i2 < size2; i2++) {
            String id2 = NotificationsController$$ExternalSyntheticApiModelOutline4.m1071m(notificationChannelGroups.get(i2)).getId();
            if (str2 != null && str2.equals(id2)) {
                str2 = null;
            } else if (str3 != null && str3.equals(id2)) {
                str3 = null;
            } else if (str10 != null && str10.equals(id2)) {
                str10 = null;
            } else if (str9 != null && str9.equals(id2)) {
                str9 = null;
            } else if (str11 != null && str11.equals(id2)) {
                str11 = null;
            } else if (str8 != null && str8.equals(id2)) {
                str8 = null;
            }
            if (str2 == null && str10 == null && str9 == null && str3 == null && str11 == null && str8 == null) {
                break;
            }
        }
        if (str2 != null || str3 != null || str9 != null || str10 != null || str11 != null || str8 != null) {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId()));
            if (user == null) {
                getUserConfig().getCurrentUser();
            }
            String str12 = user != null ? " (" + ContactsController.formatName(user.first_name, user.last_name) + ")" : _UrlKt.FRAGMENT_ENCODE_SET;
            ArrayList arrayList = new ArrayList();
            if (str2 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1069m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1068m(str2, LocaleController.getString(C2797R.string.NotificationsChannels) + str12));
            }
            if (str3 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1069m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1068m(str3, LocaleController.getString(C2797R.string.NotificationsGroups) + str12));
            }
            if (str10 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1069m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1068m(str10, LocaleController.getString(C2797R.string.NotificationsStories) + str12));
            }
            if (str9 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1069m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1068m(str9, LocaleController.getString(C2797R.string.NotificationsReactions) + str12));
            }
            if (str11 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1069m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1068m(str11, LocaleController.getString(C2797R.string.NotificationsPrivateChats) + str12));
            }
            if (str8 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1069m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1068m(str8, LocaleController.getString(C2797R.string.NotificationsOther) + str12));
            }
            systemNotificationManager.createNotificationChannelGroups(arrayList);
        }
        this.channelGroupsCreated = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:502:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:603:0x0561  */
    /* JADX WARN: Removed duplicated region for block: B:610:0x056f  */
    /* JADX WARN: Removed duplicated region for block: B:613:0x0573 A[LOOP:1: B:611:0x0570->B:613:0x0573, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:616:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:636:0x05c2  */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.app.NotificationManager] */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.app.NotificationChannel] */
    /* JADX WARN: Type inference failed for: r4v66 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9, types: [int] */
    /* JADX WARN: Type inference failed for: r5v19, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v56 */
    /* JADX WARN: Type inference failed for: r5v57 */
    /* JADX WARN: Type inference failed for: r5v58 */
    @android.annotation.TargetApi(26)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String validateChannelId(long r30, long r32, java.lang.String r34, long[] r35, int r36, android.net.Uri r37, int r38, boolean r39, boolean r40, boolean r41, int r42) {
        /*
            Method dump skipped, instruction units count: 1680
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.validateChannelId(long, long, java.lang.String, long[], int, android.net.Uri, int, boolean, boolean, boolean, int):java.lang.String");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(76:716|(1:718)(1:719)|720|(1:723)|722|724|(1:730)|731|(2:735|(1:737)(1:738))|739|(3:741|(2:747|(1:752)(1:751))(1:745)|746)(1:753)|754|(1:756)(2:757|(1:759)(1:760))|(1:(1:768)(1:769))(1:766)|770|(1:777)(1:775)|776|778|(2:(2:781|(1:783))(1:784)|(1:786)(57:787|795|(2:801|(1:803))(1:800)|(4:805|(2:807|(1:809)(1:810))(1:811)|812|(1:814)(3:815|816|(1:818)(1:819)))(1:820)|821|822|(3:826|853|(2:855|1276)(4:(1:(2:860|(1:862)(1:863))(1:859))(1:864)|865|(1:867)|868))(1:(4:825|826|853|(0)(0))(4:827|(3:829|(2:835|1269)(6:836|(1:838)|839|(1:848)(1:(1:843)(2:844|(1:846)(1:847)))|849|1268)|850)|1267|851))|852|(1:877)(1:876)|(1:898)(49:882|(1:884)(1:885)|(1:890)(2:887|(2:889|890)(2:891|(1:893)(2:894|(1:896)(1:897))))|(2:901|(1:903))(1:904)|905|(47:907|(1:909)(1:910)|911|(1:913)|914|917|(6:921|953|(1:955)(1:956)|957|(1:959)(1:960)|961)(1:(1:(3:924|(1:926)(1:927)|928)(3:930|(1:932)(1:933)|934))(41:935|(6:(1:938)(1:939)|940|(1:942)(2:(1:944)(1:945)|946)|947|(1:949)(1:950)|951)(1:952)|(1:963)(1:964)|965|(1:971)(1:969)|970|(1:974)|(1:978)|(1:984)(1:983)|(6:986|(1:988)|989|(1:991)(1:992)|993|(1:995)(1:996))(1:997)|(3:1253|1001|(1:1005))|(1:1009)(1:1010)|1011|(1:1013)|1014|1015|(1:1017)(3:1019|1020|(2:(2:1023|1024)(2:1025|(1:1027))|1028)(25:1029|(4:1031|(2:1034|1032)|1274|1035)(25:1036|1037|(4:1039|(1:(1:1042)(2:1043|(1:1045)))|1046|(0)(1:(3:1056|(22:1064|1083|(1:1085)|1086|(1:1093)|1255|1094|(1:1096)|1099|(3:1101|1102|1103)(1:1106)|1107|(1:1111)(10:(2:1114|(1:1116)(3:1257|1118|(4:1120|(1:1122)(1:1123)|1124|(1:1126))))|1127|(4:1131|1156|(2:1158|1152)|1159)(1:(3:1133|1134|(1:1136)(1:1159))(2:1138|(2:1153|(1:1155)(0))(2:1140|(3:1142|1153|(0)(0))(3:1143|(2:1145|(1:1147))(2:1148|(2:1150|(1:1152)))|1159))))|1137|(1:1217)(2:(3:1166|(1:1168)(1:1169)|1170)|(5:1197|(1:1199)(1:1201)|1200|(1:1203)(2:1205|(1:1207)(1:(2:1211|1216)(2:1212|(1:1214)(1:1215))))|1204)(3:1174|1175|(6:1177|(1:1185)(1:(1:1183)(1:1184))|(0)(0)|1200|(0)(0)|1204)(7:1186|(1:1188)(2:1189|(1:1196)(2:1251|1193))|1197|(0)(0)|1200|(0)(0)|1204)))|1218|(1:1242)(4:1226|(4:1228|(3:1230|(4:1232|(1:1234)(1:1235)|1236|1273)(2:1237|1272)|1238)|1271|1239)|1270|1240)|1241|1243|1277)|1112|1127|(5:1129|1131|1156|(0)|1159)(0)|1137|(1:1217)(0)|1218|(2:1220|1242)(0)|1241|1243|1277)|1065)(2:1066|(23:1068|(0)(1:1076)|1083|(0)|1086|(3:1089|1091|1093)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277)(1:1065))))(2:1078|(1:1082))|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277)|1050|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277))|1018|1050|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277))|929|(0)(0)|965|(2:967|971)(0)|970|(1:974)|(2:976|978)|(2:980|984)(0)|(0)(0)|(4:999|1253|1001|(2:1003|1005))|(0)(0)|1011|(0)|1014|1015|(0)(0)|1018|1050|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277)(1:915)|916|914|917|(8:919|921|953|(0)(0)|957|(0)(0)|961|929)(0)|(0)(0)|965|(0)(0)|970|(0)|(0)|(0)(0)|(0)(0)|(0)|(0)(0)|1011|(0)|1014|1015|(0)(0)|1018|1050|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277)|899|(0)(0)|905|(0)(0)|916|914|917|(0)(0)|(0)(0)|965|(0)(0)|970|(0)|(0)|(0)(0)|(0)(0)|(0)|(0)(0)|1011|(0)|1014|1015|(0)(0)|1018|1050|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277))(1:788)|(1:(1:791)(1:792))(1:793)|794|795|(4:797|799|801|(0))(0)|(0)(0)|821|822|(0)(0)|852|(1:877)(0)|(3:879|898|899)(0)|(0)(0)|905|(0)(0)|916|914|917|(0)(0)|(0)(0)|965|(0)(0)|970|(0)|(0)|(0)(0)|(0)(0)|(0)|(0)(0)|1011|(0)|1014|1015|(0)(0)|1018|1050|1077|1083|(0)|1086|(0)|1255|1094|(0)|1099|(0)(0)|1107|(0)(0)|1112|1127|(0)(0)|1137|(0)(0)|1218|(0)(0)|1241|1243|1277) */
    /* JADX WARN: Code restructure failed: missing block: B:1097:0x0b26, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1109:0x0b44, code lost:
    
        org.telegram.messenger.FileLog.m1048e(r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1009:0x0923  */
    /* JADX WARN: Removed duplicated region for block: B:1010:0x0929  */
    /* JADX WARN: Removed duplicated region for block: B:1013:0x095e A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1017:0x096d A[Catch: Exception -> 0x0df2, TRY_ENTER, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1019:0x0982 A[Catch: Exception -> 0x0df2, TRY_LEAVE, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1085:0x0ad0 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1088:0x0adf A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1096:0x0b1e A[Catch: all -> 0x0b26, TryCatch #2 {all -> 0x0b26, blocks: (B:1094:0x0b02, B:1096:0x0b1e, B:1099:0x0b28, B:1103:0x0b30, B:1107:0x0b38), top: B:1255:0x0b02 }] */
    /* JADX WARN: Removed duplicated region for block: B:1101:0x0b2c  */
    /* JADX WARN: Removed duplicated region for block: B:1106:0x0b37  */
    /* JADX WARN: Removed duplicated region for block: B:1111:0x0b49 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1113:0x0b50  */
    /* JADX WARN: Removed duplicated region for block: B:1129:0x0ba1  */
    /* JADX WARN: Removed duplicated region for block: B:1131:0x0ba4  */
    /* JADX WARN: Removed duplicated region for block: B:1155:0x0bdc  */
    /* JADX WARN: Removed duplicated region for block: B:1158:0x0be5  */
    /* JADX WARN: Removed duplicated region for block: B:1159:0x0be6  */
    /* JADX WARN: Removed duplicated region for block: B:1161:0x0bea A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1199:0x0ce1 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1201:0x0cec  */
    /* JADX WARN: Removed duplicated region for block: B:1203:0x0cf1 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1205:0x0cfb  */
    /* JADX WARN: Removed duplicated region for block: B:1217:0x0d25 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1220:0x0d38 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:1242:0x0de6  */
    /* JADX WARN: Removed duplicated region for block: B:801:0x03c5 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:803:0x03d0 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:805:0x03d8 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:820:0x047f  */
    /* JADX WARN: Removed duplicated region for block: B:824:0x049d  */
    /* JADX WARN: Removed duplicated region for block: B:826:0x04a0 A[PHI: r14
  0x04a0: PHI (r14v7 int A[IMMUTABLE_TYPE]) = (r14v6 int), (r14v33 int) binds: [B:823:0x049b, B:825:0x049f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:855:0x057a  */
    /* JADX WARN: Removed duplicated region for block: B:856:0x057c  */
    /* JADX WARN: Removed duplicated region for block: B:877:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:898:0x0697  */
    /* JADX WARN: Removed duplicated region for block: B:901:0x06a1 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:904:0x06c2  */
    /* JADX WARN: Removed duplicated region for block: B:907:0x06e3 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:915:0x0741  */
    /* JADX WARN: Removed duplicated region for block: B:919:0x074f A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:921:0x0753  */
    /* JADX WARN: Removed duplicated region for block: B:955:0x086d A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:956:0x0879 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:959:0x08a1  */
    /* JADX WARN: Removed duplicated region for block: B:960:0x08a3  */
    /* JADX WARN: Removed duplicated region for block: B:963:0x08ab  */
    /* JADX WARN: Removed duplicated region for block: B:964:0x08ae  */
    /* JADX WARN: Removed duplicated region for block: B:967:0x08b6 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:971:0x08c0  */
    /* JADX WARN: Removed duplicated region for block: B:973:0x08c8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:976:0x08cf A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:980:0x08dd  */
    /* JADX WARN: Removed duplicated region for block: B:984:0x08e5  */
    /* JADX WARN: Removed duplicated region for block: B:986:0x08e8 A[Catch: Exception -> 0x0df2, TryCatch #4 {Exception -> 0x0df2, blocks: (B:652:0x002c, B:653:0x0038, B:655:0x0040, B:657:0x0051, B:658:0x0053, B:660:0x0057, B:662:0x005f, B:664:0x0071, B:665:0x0074, B:669:0x007d, B:672:0x0085, B:673:0x009b, B:675:0x00a3, B:676:0x00d9, B:678:0x00fb, B:681:0x0103, B:683:0x010b, B:686:0x0112, B:690:0x0126, B:708:0x01e2, B:710:0x0214, B:712:0x0226, B:714:0x022c, B:716:0x0230, B:718:0x024c, B:720:0x0253, B:724:0x0266, B:728:0x0272, B:730:0x027e, B:731:0x0284, B:733:0x028f, B:735:0x0295, B:737:0x02a1, B:738:0x02ad, B:739:0x02b7, B:741:0x02c7, B:743:0x02d7, B:745:0x02dd, B:754:0x0313, B:756:0x031e, B:764:0x034e, B:766:0x0354, B:770:0x0362, B:772:0x0368, B:778:0x0373, B:781:0x0386, B:795:0x03b9, B:797:0x03bd, B:805:0x03d8, B:807:0x03df, B:809:0x03e7, B:812:0x0415, B:821:0x0487, B:827:0x04aa, B:829:0x04ce, B:831:0x04e4, B:833:0x04e8, B:838:0x04f4, B:839:0x04fa, B:843:0x0507, B:849:0x054f, B:850:0x0552, B:844:0x051d, B:846:0x0525, B:847:0x0539, B:851:0x055d, B:871:0x05dd, B:882:0x05f6, B:884:0x0614, B:887:0x0645, B:889:0x064f, B:891:0x0665, B:893:0x0676, B:901:0x06a1, B:905:0x06c4, B:907:0x06e3, B:909:0x070e, B:911:0x072a, B:913:0x073a, B:917:0x0749, B:919:0x074f, B:924:0x075f, B:926:0x0771, B:928:0x0784, B:965:0x08b0, B:967:0x08b6, B:976:0x08cf, B:978:0x08d5, B:986:0x08e8, B:989:0x08f2, B:993:0x08fd, B:1007:0x091e, B:1011:0x092e, B:1013:0x095e, B:1014:0x0966, B:1017:0x096d, B:1083:0x0a83, B:1085:0x0ad0, B:1086:0x0ad7, B:1089:0x0ae1, B:1091:0x0ae5, B:1093:0x0aeb, B:1111:0x0b49, B:1134:0x0ba9, B:1163:0x0bee, B:1172:0x0c2d, B:1174:0x0c35, B:1177:0x0c3d, B:1179:0x0c45, B:1183:0x0c50, B:1199:0x0ce1, B:1203:0x0cf1, B:1218:0x0d32, B:1220:0x0d38, B:1222:0x0d3c, B:1224:0x0d47, B:1226:0x0d4d, B:1228:0x0d56, B:1230:0x0d65, B:1232:0x0d71, B:1234:0x0d90, B:1236:0x0d9a, B:1238:0x0dc0, B:1239:0x0dc9, B:1243:0x0deb, B:1207:0x0cfe, B:1214:0x0d12, B:1216:0x0d1e, B:1184:0x0c75, B:1185:0x0c7a, B:1186:0x0c7d, B:1188:0x0c85, B:1189:0x0c8c, B:1191:0x0c94, B:1195:0x0ccd, B:1196:0x0cd6, B:1166:0x0bf8, B:1168:0x0c00, B:1170:0x0c28, B:1217:0x0d25, B:1145:0x0bbe, B:1150:0x0bcb, B:1153:0x0bd5, B:1156:0x0bde, B:1114:0x0b52, B:1116:0x0b5f, B:1109:0x0b44, B:1019:0x0982, B:1024:0x0992, B:1028:0x09a4, B:1027:0x099f, B:1029:0x09b4, B:1031:0x09c2, B:1032:0x09cb, B:1034:0x09d3, B:1035:0x09e2, B:1036:0x09eb, B:1039:0x09f5, B:1042:0x0a00, B:1045:0x0a0a, B:1046:0x0a0d, B:1048:0x0a13, B:1051:0x0a1c, B:1053:0x0a25, B:1056:0x0a2d, B:1058:0x0a33, B:1060:0x0a37, B:1062:0x0a3f, B:1068:0x0a4d, B:1070:0x0a53, B:1072:0x0a57, B:1074:0x0a5f, B:1078:0x0a66, B:1080:0x0a73, B:1082:0x0a79, B:927:0x077d, B:930:0x07ac, B:932:0x07be, B:934:0x07d1, B:933:0x07ca, B:940:0x0805, B:942:0x080d, B:947:0x0825, B:946:0x0820, B:953:0x0861, B:955:0x086d, B:957:0x0880, B:956:0x0879, B:910:0x071b, B:894:0x0682, B:896:0x0686, B:853:0x056c, B:859:0x0582, B:865:0x05c5, B:868:0x05cb, B:860:0x0596, B:862:0x059c, B:863:0x05b0, B:815:0x0425, B:818:0x0431, B:819:0x044c, B:810:0x03f4, B:801:0x03c5, B:803:0x03d0, B:791:0x03a3, B:792:0x03aa, B:793:0x03b1, B:768:0x0359, B:769:0x035e, B:747:0x02f3, B:749:0x02f9, B:723:0x0263, B:691:0x0134, B:693:0x013a, B:694:0x0140, B:697:0x014a, B:698:0x0154, B:699:0x0166, B:701:0x016c, B:702:0x0181, B:704:0x0188, B:706:0x0190, B:707:0x01bb, B:688:0x011b, B:709:0x0202, B:1193:0x0c9e, B:1001:0x0910), top: B:1259:0x002c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:997:0x0909  */
    /* JADX WARN: Removed duplicated region for block: B:999:0x090d  */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v15, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v28 */
    /* JADX WARN: Type inference failed for: r14v29 */
    /* JADX WARN: Type inference failed for: r14v30 */
    /* JADX WARN: Type inference failed for: r14v31 */
    /* JADX WARN: Type inference failed for: r14v40 */
    /* JADX WARN: Type inference failed for: r14v41 */
    /* JADX WARN: Type inference failed for: r14v42 */
    /* JADX WARN: Type inference failed for: r14v43 */
    /* JADX WARN: Type inference failed for: r14v44 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.telegram.messenger.BaseController, org.telegram.messenger.NotificationsController] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showOrUpdateNotification(boolean r53) {
        /*
            Method dump skipped, instruction units count: 3636
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showOrUpdateNotification(boolean):void");
    }

    private int getNotificationColor() {
        return AppUtils.getNotificationColor();
    }

    private boolean isSilentMessage(MessageObject messageObject) {
        return messageObject.messageOwner.silent || messageObject.isReactionPush;
    }

    @SuppressLint({"NewApi"})
    private void setNotificationChannel(Notification notification, NotificationCompat.Builder builder, boolean z) {
        if (z) {
            builder.setChannelId(OTHER_NOTIFICATIONS_CHANNEL);
        } else {
            builder.setChannelId(notification.getChannelId());
        }
    }

    public void resetNotificationSound(NotificationCompat.Builder builder, long j, long j2, String str, long[] jArr, int i, Uri uri, int i2, boolean z, boolean z2, boolean z3, int i3) {
        FileLog.m1045d("resetNotificationSound");
        Uri uri2 = Settings.System.DEFAULT_RINGTONE_URI;
        if (uri2 == null || uri == null || TextUtils.equals(uri2.toString(), uri.toString())) {
            return;
        }
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        String string = uri2.toString();
        String string2 = LocaleController.getString(C2797R.string.DefaultRingtone);
        if (z) {
            if (i3 == 2) {
                editorEdit.putString("ChannelSound", string2);
            } else if (i3 == 0) {
                editorEdit.putString("GroupSound", string2);
            } else if (i3 == 1) {
                editorEdit.putString("GlobalSound", string2);
            } else if (i3 == 3) {
                editorEdit.putString("StoriesSound", string2);
            } else if (i3 == 4 || i3 == 5) {
                editorEdit.putString("ReactionSound", string2);
            }
            if (i3 == 2) {
                editorEdit.putString("ChannelSoundPath", string);
            } else if (i3 == 0) {
                editorEdit.putString("GroupSoundPath", string);
            } else if (i3 == 1) {
                editorEdit.putString("GlobalSoundPath", string);
            } else if (i3 == 3) {
                editorEdit.putString("StoriesSoundPath", string);
            } else if (i3 == 4 || i3 == 5) {
                editorEdit.putString("ReactionSound", string);
            }
            getNotificationsController().lambda$deleteNotificationChannelGlobal$43(i3, -1);
        } else {
            editorEdit.putString("sound_" + getSharedPrefKey(j, j2), string2);
            editorEdit.putString("sound_path_" + getSharedPrefKey(j, j2), string);
            lambda$deleteNotificationChannel$42(j, j2, -1);
        }
        editorEdit.apply();
        builder.setChannelId(validateChannelId(j, j2, str, jArr, i, uri2, i2, z, z2, z3, i3));
        notificationManager.notify(this.notificationId, builder.build());
    }

    /* JADX WARN: Can't wrap try/catch for region: R(35:1291|1295|(1:1301)(1:1300)|1302|(1:1306)(1:1305)|1307|(1:1316)|1315|(1:1325)(1:1324)|1326|(8:1328|(1:1330)(2:1332|(3:1334|990|1824)(2:1335|(1:(1:1338)(1:1339))(10:1340|(1:1342)(2:1343|(1:1348)(1:1347))|1349|(2:1352|1350)|1830|1353|(1:1357)(1:1356)|1358|(1:1360)(1:1361)|1362)))|1331|1349|(1:1350)|1830|1353|(4:1357|1358|(0)(0)|1362)(0))(4:1364|(6:1366|(1:1368)(3:1370|(1:1372)(2:1373|(2:1378|(1:1380)(2:1381|(1:1385)))(1:1377))|(3:1387|(1:1389)|1390)(15:1391|(1:1393)|1394|(2:1402|(1:1404)(1:1405))(1:1400)|1401|1406|(3:1411|(1:(1:1415)(2:1416|(1:1418)(1:1419)))(0)|1588)(1:1410)|1420|(1:1435)(2:(1:1433)(3:1423|(1:(2:1426|(1:1428))(1:1429))(2:1430|(1:1432))|1588)|1434)|(3:1441|(1:1499)(4:1447|(2:1450|1468)(4:1451|(1:1455)|(1:1466)(2:1461|(0)(1:1465))|1467)|(2:1486|1498)(3:1473|(2:1475|(0)(2:1481|(1:1485)))(2:1487|(1:1497))|1498)|1588)|1500)(1:1440)|1501|(7:1503|(1:1517)(8:1518|(1:1538)(2:1522|(9:1813|1524|1525|1805|1526|1527|1807|1528|1529)(1:1537))|1539|(1:1541)(1:1543)|1542|1544|(4:1815|1546|1547|(5:1555|(1:1557)|1558|1561|(1:1575)(2:1566|(3:1568|(3:1803|1570|(1:1574))(2:1576|(2:1578|(1:1580)))|1575)(0)))(1:1559))(3:1551|(1:1553)(1:1550)|(0)(0))|1588)|1560|1558|1561|(1:1575)(0)|1588)(1:1581)|1582|(2:1587|1842)(2:1586|1843)|1588))|1369|1587|1842|1588)|1841|1589)|1363|1590|(2:1596|(2:1601|(38:1611|(4:1613|(2:1616|1614)|1840|1617)(2:1618|(1:1620)(2:1621|(1:1623)(1:1624)))|1625|(1:1627)|1628|(1:1630)|1631|(2:1633|(1:1635)(1:1636))(2:1637|(1:1639)(1:1640))|(1:1642)(1:1643)|1644|(4:1646|(2:1649|1647)|1831|1650)(1:1651)|1652|(1:1654)|1655|(1:1657)|1799|1658|(1:1660)|(1:1666)|1667|(1:1671)|(1:1690)(3:1677|(4:1680|(2:1681|(1:1834)(2:1683|(2:1835|1685)(1:1686)))|(1:1832)(1:1689)|1678)|1833)|(1:1692)|1693|(2:(1:1698)|(1:1705))|1706|(1:1713)(1:1712)|1714|(1:1716)|(1:1718)|1719|(3:1724|(4:1726|(3:1728|(4:1730|(1:1732)|1733|1839)(2:1734|1838)|1735)|1837|1736)|1836)|1737|(1:1745)(2:1740|(1:1744))|1746|(1:1748)|1749|1823)(3:1605|(1:1607)(1:(1:1609))|1610))(1:1600))(1:1594)|1595|1625|(0)|1628|(0)|1631|(0)(0)|(0)(0)|1644|(0)(0)|1652|(0)|1655|(0)|1799|1658|(0)|(2:1664|1666)|1667|(0)|(7:1673|1690|(0)|1693|(3:1695|(0)|(2:1700|1705))|1706|(9:1708|1713|1714|(0)|(0)|1719|(4:1721|1724|(0)|1836)|1737|(5:1745|1746|(0)|1749|1823)(0))(0))(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:1661:0x12bf, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1669:0x12de, code lost:
    
        org.telegram.messenger.FileLog.m1048e(r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:1071:0x0402  */
    /* JADX WARN: Removed duplicated region for block: B:1074:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:1111:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:1114:0x04dd  */
    /* JADX WARN: Removed duplicated region for block: B:1116:0x04e2  */
    /* JADX WARN: Removed duplicated region for block: B:1120:0x04f1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1143:0x0549 A[PHI: r0 r9
  0x0549: PHI (r0v80 org.telegram.tgnet.TLRPC$FileLocation) = (r0v79 org.telegram.tgnet.TLRPC$FileLocation), (r0v84 org.telegram.tgnet.TLRPC$FileLocation) binds: [B:1142:0x0547, B:1132:0x0524] A[DONT_GENERATE, DONT_INLINE]
  0x0549: PHI (r9v26 boolean) = (r9v25 boolean), (r9v27 boolean) binds: [B:1142:0x0547, B:1132:0x0524] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:1144:0x054c  */
    /* JADX WARN: Removed duplicated region for block: B:1146:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:1147:0x055e  */
    /* JADX WARN: Removed duplicated region for block: B:1172:0x0610  */
    /* JADX WARN: Removed duplicated region for block: B:1180:0x062c  */
    /* JADX WARN: Removed duplicated region for block: B:1197:0x0684  */
    /* JADX WARN: Removed duplicated region for block: B:1199:0x068d  */
    /* JADX WARN: Removed duplicated region for block: B:1207:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:1223:0x06fa  */
    /* JADX WARN: Removed duplicated region for block: B:1251:0x07e1  */
    /* JADX WARN: Removed duplicated region for block: B:1255:0x07f4  */
    /* JADX WARN: Removed duplicated region for block: B:1258:0x0800  */
    /* JADX WARN: Removed duplicated region for block: B:1260:0x0808  */
    /* JADX WARN: Removed duplicated region for block: B:1267:0x0835  */
    /* JADX WARN: Removed duplicated region for block: B:1291:0x08ad  */
    /* JADX WARN: Removed duplicated region for block: B:1297:0x08ba  */
    /* JADX WARN: Removed duplicated region for block: B:1301:0x08c5  */
    /* JADX WARN: Removed duplicated region for block: B:1306:0x08d2  */
    /* JADX WARN: Removed duplicated region for block: B:1316:0x08ef  */
    /* JADX WARN: Removed duplicated region for block: B:1325:0x0906  */
    /* JADX WARN: Removed duplicated region for block: B:1328:0x0921  */
    /* JADX WARN: Removed duplicated region for block: B:1352:0x0a3f A[LOOP:5: B:1350:0x0a37->B:1352:0x0a3f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:1357:0x0a70  */
    /* JADX WARN: Removed duplicated region for block: B:1360:0x0a8a  */
    /* JADX WARN: Removed duplicated region for block: B:1361:0x0a90  */
    /* JADX WARN: Removed duplicated region for block: B:1364:0x0aa2  */
    /* JADX WARN: Removed duplicated region for block: B:1419:0x0c12  */
    /* JADX WARN: Removed duplicated region for block: B:1486:0x0d26  */
    /* JADX WARN: Removed duplicated region for block: B:1541:0x0e76  */
    /* JADX WARN: Removed duplicated region for block: B:1543:0x0e7b  */
    /* JADX WARN: Removed duplicated region for block: B:1551:0x0ea9  */
    /* JADX WARN: Removed duplicated region for block: B:1555:0x0f00  */
    /* JADX WARN: Removed duplicated region for block: B:1559:0x0f31  */
    /* JADX WARN: Removed duplicated region for block: B:1575:0x0f94  */
    /* JADX WARN: Removed duplicated region for block: B:1596:0x104a  */
    /* JADX WARN: Removed duplicated region for block: B:1601:0x105f  */
    /* JADX WARN: Removed duplicated region for block: B:1613:0x108a  */
    /* JADX WARN: Removed duplicated region for block: B:1618:0x10b1  */
    /* JADX WARN: Removed duplicated region for block: B:1627:0x10ef  */
    /* JADX WARN: Removed duplicated region for block: B:1630:0x1110  */
    /* JADX WARN: Removed duplicated region for block: B:1633:0x116d  */
    /* JADX WARN: Removed duplicated region for block: B:1637:0x11a6  */
    /* JADX WARN: Removed duplicated region for block: B:1642:0x11cc  */
    /* JADX WARN: Removed duplicated region for block: B:1643:0x11e4  */
    /* JADX WARN: Removed duplicated region for block: B:1646:0x11fe  */
    /* JADX WARN: Removed duplicated region for block: B:1651:0x1220  */
    /* JADX WARN: Removed duplicated region for block: B:1654:0x1256  */
    /* JADX WARN: Removed duplicated region for block: B:1657:0x1291  */
    /* JADX WARN: Removed duplicated region for block: B:1660:0x12b7 A[Catch: Exception -> 0x12bf, TryCatch #2 {Exception -> 0x12bf, blocks: (B:1658:0x1298, B:1660:0x12b7, B:1664:0x12c3, B:1666:0x12c7, B:1667:0x12ce), top: B:1799:0x1298 }] */
    /* JADX WARN: Removed duplicated region for block: B:1664:0x12c3 A[Catch: Exception -> 0x12bf, TryCatch #2 {Exception -> 0x12bf, blocks: (B:1658:0x1298, B:1660:0x12b7, B:1664:0x12c3, B:1666:0x12c7, B:1667:0x12ce), top: B:1799:0x1298 }] */
    /* JADX WARN: Removed duplicated region for block: B:1671:0x12e3  */
    /* JADX WARN: Removed duplicated region for block: B:1673:0x12ee  */
    /* JADX WARN: Removed duplicated region for block: B:1690:0x133e  */
    /* JADX WARN: Removed duplicated region for block: B:1692:0x1341  */
    /* JADX WARN: Removed duplicated region for block: B:1698:0x138a  */
    /* JADX WARN: Removed duplicated region for block: B:1713:0x13b5  */
    /* JADX WARN: Removed duplicated region for block: B:1716:0x13be  */
    /* JADX WARN: Removed duplicated region for block: B:1718:0x13c3  */
    /* JADX WARN: Removed duplicated region for block: B:1726:0x13dd  */
    /* JADX WARN: Removed duplicated region for block: B:1745:0x147d  */
    /* JADX WARN: Removed duplicated region for block: B:1748:0x1489  */
    /* JADX WARN: Removed duplicated region for block: B:1815:0x0e85 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:963:0x0156  */
    @android.annotation.SuppressLint({"InlinedApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showExtraNotifications(androidx.core.app.NotificationCompat.Builder r81, java.lang.String r82, long r83, long r85, java.lang.String r87, long[] r88, int r89, android.net.Uri r90, int r91, boolean r92, boolean r93, boolean r94, int r95) {
        /*
            Method dump skipped, instruction units count: 5735
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showExtraNotifications(androidx.core.app.NotificationCompat$Builder, java.lang.String, long, long, java.lang.String, long[], int, android.net.Uri, int, boolean, boolean, boolean, int):void");
    }

    /* JADX INFO: renamed from: org.telegram.messenger.NotificationsController$1NotificationHolder */
    public class C1NotificationHolder {
        TLRPC.Chat chat;
        long dialogId;

        /* JADX INFO: renamed from: id */
        int f1161id;
        String name;
        NotificationCompat.Builder notification;
        boolean story;
        long topicId;
        TLRPC.User user;
        final /* synthetic */ String val$chatName;
        final /* synthetic */ int val$chatType;
        final /* synthetic */ int val$importance;
        final /* synthetic */ boolean val$isDefault;
        final /* synthetic */ boolean val$isInApp;
        final /* synthetic */ boolean val$isSilent;
        final /* synthetic */ long val$lastTopicId;
        final /* synthetic */ int val$ledColor;
        final /* synthetic */ Uri val$sound;
        final /* synthetic */ long[] val$vibrationPattern;

        public C1NotificationHolder(int i, long j, boolean z, long j2, String str, TLRPC.User user, TLRPC.Chat chat, NotificationCompat.Builder builder, long j3, String str2, long[] jArr, int i2, Uri uri, int i3, boolean z2, boolean z3, boolean z4, int i4) {
            this.val$lastTopicId = j3;
            this.val$chatName = str2;
            this.val$vibrationPattern = jArr;
            this.val$ledColor = i2;
            this.val$sound = uri;
            this.val$importance = i3;
            this.val$isDefault = z2;
            this.val$isInApp = z3;
            this.val$isSilent = z4;
            this.val$chatType = i4;
            this.f1161id = i;
            this.name = str;
            this.user = user;
            this.chat = chat;
            this.notification = builder;
            this.dialogId = j;
            this.story = z;
            this.topicId = j2;
        }

        public void call() {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1049w("show dialog notification with id " + this.f1161id + " " + this.dialogId + " user=" + this.user + " chat=" + this.chat);
            }
            try {
                NotificationsController.notificationManager.notify(this.f1161id, this.notification.build());
            } catch (SecurityException e) {
                FileLog.m1048e(e);
                NotificationsController.this.resetNotificationSound(this.notification, this.dialogId, this.val$lastTopicId, this.val$chatName, this.val$vibrationPattern, this.val$ledColor, this.val$sound, this.val$importance, this.val$isDefault, this.val$isInApp, this.val$isSilent, this.val$chatType);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$gQghIUHs5ZKeIK8dHwFQ_2pzquE(Uri uri, File file) {
        try {
            ApplicationLoader.applicationContext.revokeUriPermission(uri, 1);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        if (file != null) {
            try {
                file.delete();
            } catch (Exception e2) {
                FileLog.m1048e(e2);
            }
        }
    }

    private String cutLastName(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf >= 0) {
            return str.substring(0, iIndexOf).concat(str.endsWith("…") ? "…" : _UrlKt.FRAGMENT_ENCODE_SET);
        }
        return str;
    }

    private Pair<Integer, Boolean> parseStoryPushes(ArrayList<String> arrayList, ArrayList<Object> arrayList2) {
        String strConcat;
        TLRPC.FileLocation fileLocation;
        int iMin = Math.min(3, this.storyPushMessages.size());
        boolean z = false;
        int size = 0;
        for (int i = 0; i < iMin; i++) {
            StoryNotification storyNotification = this.storyPushMessages.get(i);
            size += storyNotification.dateByIds.size();
            z |= storyNotification.hidden;
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(storyNotification.dialogId));
            if (user == null && (user = getMessagesStorage().getUserSync(storyNotification.dialogId)) != null) {
                getMessagesController().putUser(user, true);
            }
            Object obj = null;
            if (user != null) {
                strConcat = UserObject.getUserName(user);
                TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
                if (userProfilePhoto != null && (fileLocation = userProfilePhoto.photo_small) != null && fileLocation.volume_id != 0 && fileLocation.local_id != 0) {
                    File pathToAttach = getFileLoader().getPathToAttach(user.photo.photo_small, true);
                    if (!pathToAttach.exists()) {
                        pathToAttach = user.photo.photo_big != null ? getFileLoader().getPathToAttach(user.photo.photo_big, true) : null;
                        if (pathToAttach != null && !pathToAttach.exists()) {
                            pathToAttach = null;
                        }
                    }
                    if (pathToAttach != null) {
                        obj = pathToAttach;
                    }
                }
            } else {
                strConcat = storyNotification.localName;
                if (strConcat != null) {
                }
            }
            if (strConcat.length() > 50) {
                strConcat = strConcat.substring(0, 25).concat("…");
            }
            arrayList.add(strConcat);
            if (obj == null && user != null) {
                arrayList2.add(user);
            } else if (obj != null) {
                arrayList2.add(obj);
            }
        }
        if (z) {
            arrayList2.clear();
        }
        return new Pair<>(Integer.valueOf(size), Boolean.valueOf(z));
    }

    public static Person.Builder loadRoundAvatar(long j, File file, Person.Builder builder) {
        if (j == UserObject.OAUTH) {
            builder.setIcon(IconCompat.createWithResource(ApplicationLoader.applicationContext, C2797R.drawable.ic_launcher_dr));
            return builder;
        }
        if (file != null && Build.VERSION.SDK_INT >= 28) {
            try {
                builder.setIcon(IconCompat.createWithBitmap(ImageDecoder.decodeBitmap(ImageDecoder.createSource(file), new ImageDecoder.OnHeaderDecodedListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda21
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        imageDecoder.setPostProcessor(new PostProcessor() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda44
                            @Override // android.graphics.PostProcessor
                            public final int onPostProcess(Canvas canvas) {
                                return NotificationsController.$r8$lambda$Z_KcXEDiTLz__0aNAPod1LbZsYY(canvas);
                            }
                        });
                    }
                })));
            } catch (Throwable unused) {
            }
        }
        return builder;
    }

    public static /* synthetic */ int $r8$lambda$Z_KcXEDiTLz__0aNAPod1LbZsYY(Canvas canvas) {
        Path path = new Path();
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        int width = canvas.getWidth();
        float f = width / 2;
        path.addRoundRect(0.0f, 0.0f, width, canvas.getHeight(), f, f, Path.Direction.CW);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawPath(path, paint);
        return -3;
    }

    public static Bitmap loadMultipleAvatars(ArrayList<Object> arrayList) {
        int i;
        Bitmap bitmap;
        Paint paint;
        boolean z;
        float f;
        char c2;
        Object obj;
        int[] iArr;
        TextPaint textPaint;
        ArrayList<Object> arrayList2 = arrayList;
        if (Build.VERSION.SDK_INT < 28 || arrayList2 == null || arrayList2.size() == 0) {
            return null;
        }
        int iM1036dp = AndroidUtilities.m1036dp(64.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iM1036dp, iM1036dp, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Matrix matrix = new Matrix();
        Paint paint2 = new Paint(3);
        boolean z2 = true;
        Paint paint3 = new Paint(1);
        Rect rect = new Rect();
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        char c3 = 2;
        float f2 = arrayList2.size() == 1 ? 1.0f : arrayList2.size() == 2 ? 0.65f : 0.5f;
        int i2 = 0;
        TextPaint textPaint2 = null;
        while (i2 < arrayList2.size()) {
            float f3 = iM1036dp;
            float f4 = (1.0f - f2) * f3;
            try {
                float size = (f4 / arrayList2.size()) * ((arrayList2.size() - 1) - i2);
                try {
                    float size2 = i2 * (f4 / arrayList2.size());
                    float f5 = f3 * f2;
                    float f6 = f5 / 2.0f;
                    i = iM1036dp;
                    float f7 = size + f6;
                    bitmap = bitmapCreateBitmap;
                    float f8 = size2 + f6;
                    f = f2;
                    try {
                        canvas.drawCircle(f7, f8, AndroidUtilities.m1036dp(2.0f) + f6, paint3);
                        obj = arrayList2.get(i2);
                        paint = paint3;
                    } catch (Throwable unused) {
                        paint = paint3;
                    }
                    if (obj instanceof File) {
                        String absolutePath = ((File) arrayList2.get(i2)).getAbsolutePath();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        z = true;
                        try {
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(absolutePath, options);
                            int i3 = (int) f5;
                            options.inSampleSize = StoryEntry.calculateInSampleSize(options, i3, i3);
                            try {
                                options.inJustDecodeBounds = false;
                                options.inDither = true;
                                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(absolutePath, options);
                                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                                BitmapShader bitmapShader = new BitmapShader(bitmapDecodeFile, tileMode, tileMode);
                                matrix.reset();
                                matrix.postScale(f5 / bitmapDecodeFile.getWidth(), f5 / bitmapDecodeFile.getHeight());
                                matrix.postTranslate(size, size2);
                                bitmapShader.setLocalMatrix(matrix);
                                paint2.setShader(bitmapShader);
                                canvas.drawCircle(f7, f8, f6, paint2);
                                bitmapDecodeFile.recycle();
                                c2 = 2;
                            } catch (Throwable unused2) {
                                c2 = 2;
                            }
                            z = true;
                        } catch (Throwable unused3) {
                            c2 = 2;
                        }
                    } else {
                        if (obj instanceof TLRPC.User) {
                            TLRPC.User user = (TLRPC.User) obj;
                            c2 = 2;
                            try {
                                iArr = new int[2];
                                try {
                                    iArr[0] = Theme.getColor(Theme.keys_avatar_background[AvatarDrawable.getColorIndex(user.f1407id)]);
                                } catch (Throwable unused4) {
                                    c2 = 2;
                                    z = true;
                                }
                            } catch (Throwable unused5) {
                                z = true;
                            }
                            try {
                                iArr[1] = Theme.getColor(Theme.keys_avatar_background2[AvatarDrawable.getColorIndex(user.f1407id)]);
                                float f9 = size2 + f5;
                                c2 = 2;
                                try {
                                    float[] fArr = new float[2];
                                    try {
                                        fArr[0] = 0.0f;
                                        fArr[1] = 1.0f;
                                        paint2.setShader(new LinearGradient(size, size2, size, f9, iArr, fArr, Shader.TileMode.CLAMP));
                                        canvas.drawCircle(f7, f8, f6, paint2);
                                        if (textPaint2 == null) {
                                            try {
                                                z = true;
                                                try {
                                                    textPaint = new TextPaint(1);
                                                } catch (Throwable unused6) {
                                                }
                                            } catch (Throwable unused7) {
                                                z = true;
                                            }
                                            try {
                                                textPaint.setTypeface(AndroidUtilities.bold());
                                                textPaint.setTextSize(0.25f * f3);
                                                textPaint.setColor(-1);
                                                textPaint2 = textPaint;
                                            } catch (Throwable unused8) {
                                                textPaint2 = textPaint;
                                            }
                                        } else {
                                            z = true;
                                        }
                                        StringBuilder sb = new StringBuilder();
                                        AvatarDrawable.getAvatarSymbols(user.first_name, user.last_name, null, sb);
                                        String string = sb.toString();
                                        try {
                                            textPaint2.getTextBounds(string, 0, string.length(), rect);
                                            canvas.drawText(string, (f7 - (rect.width() / 2.0f)) - rect.left, (f8 - (rect.height() / 2.0f)) - rect.top, textPaint2);
                                        } catch (Throwable unused9) {
                                        }
                                    } catch (Throwable unused10) {
                                        z = true;
                                    }
                                } catch (Throwable unused11) {
                                    z = true;
                                }
                            } catch (Throwable unused12) {
                                z = true;
                                c2 = 2;
                                i2++;
                                c3 = c2;
                                z2 = z;
                                iM1036dp = i;
                                bitmapCreateBitmap = bitmap;
                                f2 = f;
                                paint3 = paint;
                                arrayList2 = arrayList;
                            }
                        } else {
                            c2 = 2;
                        }
                        z = true;
                    }
                } catch (Throwable unused13) {
                    i = iM1036dp;
                    bitmap = bitmapCreateBitmap;
                    paint = paint3;
                    z = z2;
                    f = f2;
                }
            } catch (Throwable unused14) {
                i = iM1036dp;
                bitmap = bitmapCreateBitmap;
                paint = paint3;
                z = z2;
                f = f2;
                c2 = c3;
            }
            i2++;
            c3 = c2;
            z2 = z;
            iM1036dp = i;
            bitmapCreateBitmap = bitmap;
            f2 = f;
            paint3 = paint;
            arrayList2 = arrayList;
        }
        return bitmapCreateBitmap;
    }

    public void playOutChatSound() {
        if (!this.inChatSoundEnabled || MediaController.getInstance().isRecordingAudio()) {
            return;
        }
        try {
            if (audioManager.getRingerMode() == 0) {
                return;
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$playOutChatSound$49();
            }
        });
    }

    public /* synthetic */ void lambda$playOutChatSound$49() {
        try {
            if (Math.abs(SystemClock.elapsedRealtime() - this.lastSoundOutPlay) <= 100) {
                return;
            }
            this.lastSoundOutPlay = SystemClock.elapsedRealtime();
            if (this.soundPool == null) {
                SoundPool soundPool = new SoundPool(3, 1, 0);
                this.soundPool = soundPool;
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda23
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                        NotificationsController.$r8$lambda$el0xJHhpeENCEpDJ0w96gt65p0Q(soundPool2, i, i2);
                    }
                });
            }
            if (this.soundOut == 0 && !this.soundOutLoaded) {
                this.soundOutLoaded = true;
                this.soundOut = this.soundPool.load(ApplicationLoader.applicationContext, C2797R.raw.sound_out, 1);
            }
            int i = this.soundOut;
            if (i != 0) {
                try {
                    this.soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                } catch (Exception e) {
                    FileLog.m1048e(e);
                }
            }
        } catch (Exception e2) {
            FileLog.m1048e(e2);
        }
    }

    public static /* synthetic */ void $r8$lambda$el0xJHhpeENCEpDJ0w96gt65p0Q(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            try {
                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
    }

    public void clearDialogNotificationsSettings(long j, long j2) {
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        String sharedPrefKey = getSharedPrefKey(j, j2);
        editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey).remove(NotificationsSettingsFacade.PROPERTY_CUSTOM + sharedPrefKey);
        getMessagesStorage().setDialogFlags(j, 0L);
        TLRPC.Dialog dialog = getMessagesController().dialogs_dict.get(j);
        if (dialog != null) {
            dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
        }
        editorEdit.apply();
        getNotificationsController().updateServerNotificationsSettings(j, j2, true);
    }

    public void setDialogNotificationsSettings(long j, long j2, int i) {
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        TLRPC.Dialog dialog = MessagesController.getInstance(UserConfig.selectedAccount).dialogs_dict.get(j);
        if (i == 4) {
            if (isGlobalNotificationsEnabled(j, false, false)) {
                editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2));
            } else {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 0);
            }
            getMessagesStorage().setDialogFlags(j, 0L);
            if (dialog != null) {
                dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
            }
        } else {
            int currentTime = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentTime();
            if (i == 0) {
                currentTime += 3600;
            } else if (i == 1) {
                currentTime += 28800;
            } else if (i == 2) {
                currentTime += 172800;
            } else if (i == 3) {
                currentTime = Integer.MAX_VALUE;
            }
            long j3 = 1;
            if (i == 3) {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 2);
            } else {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 3);
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + getSharedPrefKey(j, j2), currentTime);
                j3 = 1 | (((long) currentTime) << 32);
            }
            getInstance(UserConfig.selectedAccount).removeNotificationsForDialog(j);
            MessagesStorage.getInstance(UserConfig.selectedAccount).setDialogFlags(j, j3);
            if (dialog != null) {
                TLRPC.TL_peerNotifySettings tL_peerNotifySettings = new TLRPC.TL_peerNotifySettings();
                dialog.notify_settings = tL_peerNotifySettings;
                tL_peerNotifySettings.mute_until = currentTime;
            }
        }
        editorEdit.apply();
        updateServerNotificationsSettings(j, j2);
    }

    public void updateServerNotificationsSettings(long j, long j2) {
        updateServerNotificationsSettings(j, j2, true);
    }

    public void updateServerNotificationsSettings(long j, long j2, boolean z) {
        if (z) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        }
        if (DialogObject.isEncryptedDialog(j)) {
            return;
        }
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        TL_account.updateNotifySettings updatenotifysettings = new TL_account.updateNotifySettings();
        updatenotifysettings.settings = new TLRPC.TL_inputPeerNotifySettings();
        String sharedPrefKey = getSharedPrefKey(j, j2);
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings = updatenotifysettings.settings;
        tL_inputPeerNotifySettings.flags |= 1;
        tL_inputPeerNotifySettings.show_previews = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_CONTENT_PREVIEW + sharedPrefKey, true);
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings2 = updatenotifysettings.settings;
        tL_inputPeerNotifySettings2.flags = tL_inputPeerNotifySettings2.flags | 2;
        tL_inputPeerNotifySettings2.silent = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + sharedPrefKey, false);
        if (notificationsSettings.contains(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + sharedPrefKey)) {
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings3 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings3.flags |= 64;
            tL_inputPeerNotifySettings3.stories_muted = !notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + sharedPrefKey, true);
        }
        int i = notificationsSettings.getInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), -1);
        if (i != -1) {
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings4 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings4.flags |= 4;
            if (i == 3) {
                tL_inputPeerNotifySettings4.mute_until = notificationsSettings.getInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + getSharedPrefKey(j, j2), 0);
            } else {
                tL_inputPeerNotifySettings4.mute_until = i == 2 ? Integer.MAX_VALUE : 0;
            }
        }
        long j3 = notificationsSettings.getLong("sound_document_id_" + getSharedPrefKey(j, j2), 0L);
        String string = notificationsSettings.getString("sound_path_" + getSharedPrefKey(j, j2), null);
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings5 = updatenotifysettings.settings;
        tL_inputPeerNotifySettings5.flags = tL_inputPeerNotifySettings5.flags | 8;
        if (j3 != 0) {
            TLRPC.TL_notificationSoundRingtone tL_notificationSoundRingtone = new TLRPC.TL_notificationSoundRingtone();
            tL_notificationSoundRingtone.f1383id = j3;
            updatenotifysettings.settings.sound = tL_notificationSoundRingtone;
        } else if (string != null) {
            if (string.equalsIgnoreCase("NoSound")) {
                updatenotifysettings.settings.sound = new TLRPC.TL_notificationSoundNone();
            } else {
                TLRPC.TL_notificationSoundLocal tL_notificationSoundLocal = new TLRPC.TL_notificationSoundLocal();
                tL_notificationSoundLocal.title = notificationsSettings.getString("sound_" + getSharedPrefKey(j, j2), null);
                tL_notificationSoundLocal.data = string;
                updatenotifysettings.settings.sound = tL_notificationSoundLocal;
            }
        } else {
            tL_inputPeerNotifySettings5.sound = new TLRPC.TL_notificationSoundDefault();
        }
        if (j2 != 0 && j != getUserConfig().getClientUserId()) {
            TLRPC.TL_inputNotifyForumTopic tL_inputNotifyForumTopic = new TLRPC.TL_inputNotifyForumTopic();
            tL_inputNotifyForumTopic.peer = getMessagesController().getInputPeer(j);
            tL_inputNotifyForumTopic.top_msg_id = (int) j2;
            updatenotifysettings.peer = tL_inputNotifyForumTopic;
        } else {
            TLRPC.TL_inputNotifyPeer tL_inputNotifyPeer = new TLRPC.TL_inputNotifyPeer();
            updatenotifysettings.peer = tL_inputNotifyPeer;
            tL_inputNotifyPeer.peer = getMessagesController().getInputPeer(j);
        }
        getConnectionsManager().sendRequest(updatenotifysettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda56
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                NotificationsController.m6146$r8$lambda$1t1axbSYGQIU_GMVkHnzrj3Llc(tLObject, tL_error);
            }
        });
    }

    public void updateServerNotificationsSettings(int i) {
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        if (i == 4 || i == 5) {
            TL_account.setReactionsNotifySettings setreactionsnotifysettings = new TL_account.setReactionsNotifySettings();
            setreactionsnotifysettings.settings = new TL_account.TL_reactionsNotifySettings();
            if (notificationsSettings.getBoolean("EnableReactionsMessages", true)) {
                setreactionsnotifysettings.settings.flags |= 1;
                boolean z = notificationsSettings.getBoolean("EnableReactionsMessagesContacts", false);
                TL_account.TL_reactionsNotifySettings tL_reactionsNotifySettings = setreactionsnotifysettings.settings;
                if (z) {
                    tL_reactionsNotifySettings.messages_notify_from = new TL_account.TL_reactionNotificationsFromContacts();
                } else {
                    tL_reactionsNotifySettings.messages_notify_from = new TL_account.TL_reactionNotificationsFromAll();
                }
            }
            if (notificationsSettings.getBoolean("EnableReactionsStories", true)) {
                setreactionsnotifysettings.settings.flags |= 2;
                boolean z2 = notificationsSettings.getBoolean("EnableReactionsStoriesContacts", false);
                TL_account.TL_reactionsNotifySettings tL_reactionsNotifySettings2 = setreactionsnotifysettings.settings;
                if (z2) {
                    tL_reactionsNotifySettings2.stories_notify_from = new TL_account.TL_reactionNotificationsFromContacts();
                } else {
                    tL_reactionsNotifySettings2.stories_notify_from = new TL_account.TL_reactionNotificationsFromAll();
                }
            }
            setreactionsnotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnableReactionsPreview", true);
            setreactionsnotifysettings.settings.sound = getInputSound(notificationsSettings, "ReactionSound", "ReactionSoundDocId", "ReactionSoundPath");
            getConnectionsManager().sendRequest(setreactionsnotifysettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda31
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    NotificationsController.m6151$r8$lambda$H_bZLJEQVx9OdWW6ZrpVB2xjp0(tLObject, tL_error);
                }
            });
            return;
        }
        TL_account.updateNotifySettings updatenotifysettings = new TL_account.updateNotifySettings();
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings = new TLRPC.TL_inputPeerNotifySettings();
        updatenotifysettings.settings = tL_inputPeerNotifySettings;
        tL_inputPeerNotifySettings.flags = 5;
        if (i == 0) {
            updatenotifysettings.peer = new TLRPC.TL_inputNotifyChats();
            updatenotifysettings.settings.mute_until = notificationsSettings.getInt("EnableGroup2", 0);
            updatenotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewGroup", true);
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings2 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings2.flags |= 8;
            tL_inputPeerNotifySettings2.sound = getInputSound(notificationsSettings, "GroupSound", "GroupSoundDocId", "GroupSoundPath");
        } else if (i == 1 || i == 3) {
            updatenotifysettings.peer = new TLRPC.TL_inputNotifyUsers();
            updatenotifysettings.settings.mute_until = notificationsSettings.getInt("EnableAll2", 0);
            updatenotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewAll", true);
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings3 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings3.flags |= 128;
            tL_inputPeerNotifySettings3.stories_hide_sender = notificationsSettings.getBoolean("EnableHideStoriesSenders", false);
            if (notificationsSettings.contains("EnableAllStories")) {
                TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings4 = updatenotifysettings.settings;
                tL_inputPeerNotifySettings4.flags |= 64;
                tL_inputPeerNotifySettings4.stories_muted = !notificationsSettings.getBoolean("EnableAllStories", true);
            }
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings5 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings5.flags |= 8;
            tL_inputPeerNotifySettings5.sound = getInputSound(notificationsSettings, "GlobalSound", "GlobalSoundDocId", "GlobalSoundPath");
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings6 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings6.flags |= 256;
            tL_inputPeerNotifySettings6.stories_sound = getInputSound(notificationsSettings, "StoriesSound", "StoriesSoundDocId", "StoriesSoundPath");
        } else {
            updatenotifysettings.peer = new TLRPC.TL_inputNotifyBroadcasts();
            updatenotifysettings.settings.mute_until = notificationsSettings.getInt("EnableChannel2", 0);
            updatenotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewChannel", true);
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings7 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings7.flags |= 8;
            tL_inputPeerNotifySettings7.sound = getInputSound(notificationsSettings, "ChannelSound", "ChannelSoundDocId", "ChannelSoundPath");
        }
        getConnectionsManager().sendRequest(updatenotifysettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda32
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                NotificationsController.$r8$lambda$TMEsjTkj9lYdR59uaNuAf1n8IoU(tLObject, tL_error);
            }
        });
    }

    private TLRPC.NotificationSound getInputSound(SharedPreferences sharedPreferences, String str, String str2, String str3) {
        long j = sharedPreferences.getLong(str2, 0L);
        String string = sharedPreferences.getString(str3, "NoSound");
        if (j != 0) {
            TLRPC.TL_notificationSoundRingtone tL_notificationSoundRingtone = new TLRPC.TL_notificationSoundRingtone();
            tL_notificationSoundRingtone.f1383id = j;
            return tL_notificationSoundRingtone;
        }
        if (string != null) {
            if (string.equalsIgnoreCase("NoSound")) {
                return new TLRPC.TL_notificationSoundNone();
            }
            TLRPC.TL_notificationSoundLocal tL_notificationSoundLocal = new TLRPC.TL_notificationSoundLocal();
            tL_notificationSoundLocal.title = sharedPreferences.getString(str, null);
            tL_notificationSoundLocal.data = string;
            return tL_notificationSoundLocal;
        }
        return new TLRPC.TL_notificationSoundDefault();
    }

    public boolean isGlobalNotificationsEnabled(long j, boolean z, boolean z2) {
        return isGlobalNotificationsEnabled(j, null, z, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isGlobalNotificationsEnabled(long r1, java.lang.Boolean r3, boolean r4, boolean r5) {
        /*
            r0 = this;
            if (r4 == 0) goto L4
            r1 = 4
            goto L35
        L4:
            if (r5 == 0) goto L8
            r1 = 5
            goto L35
        L8:
            boolean r4 = org.telegram.messenger.DialogObject.isChatDialog(r1)
            if (r4 == 0) goto L34
            r4 = 0
            r5 = 2
            if (r3 == 0) goto L1c
            boolean r1 = r3.booleanValue()
            if (r1 == 0) goto L1a
        L18:
            r1 = r5
            goto L35
        L1a:
            r1 = r4
            goto L35
        L1c:
            org.telegram.messenger.MessagesController r3 = r0.getMessagesController()
            long r1 = -r1
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            org.telegram.tgnet.TLRPC$Chat r1 = r3.getChat(r1)
            boolean r2 = org.telegram.messenger.ChatObject.isChannel(r1)
            if (r2 == 0) goto L1a
            boolean r1 = r1.megagroup
            if (r1 != 0) goto L1a
            goto L18
        L34:
            r1 = 1
        L35:
            boolean r0 = r0.isGlobalNotificationsEnabled(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.isGlobalNotificationsEnabled(long, java.lang.Boolean, boolean, boolean):boolean");
    }

    public boolean isGlobalNotificationsEnabled(int i) {
        if (i == 4) {
            return getAccountInstance().getNotificationsSettings().getBoolean("EnableReactionsMessages", true);
        }
        if (i == 5) {
            return getAccountInstance().getNotificationsSettings().getBoolean("EnableReactionsStories", true);
        }
        if (i == 3) {
            return getAccountInstance().getNotificationsSettings().getBoolean("EnableAllStories", true);
        }
        return getAccountInstance().getNotificationsSettings().getInt(getGlobalNotificationsKey(i), 0) < getConnectionsManager().getCurrentTime();
    }

    public void setGlobalNotificationsEnabled(int i, int i2) {
        getAccountInstance().getNotificationsSettings().edit().putInt(getGlobalNotificationsKey(i), i2).apply();
        updateServerNotificationsSettings(i);
        getMessagesStorage().updateMutedDialogsFiltersCounters();
        deleteNotificationChannelGlobal(i);
    }

    public static String getGlobalNotificationsKey(int i) {
        if (i == 0) {
            return "EnableGroup2";
        }
        if (i == 1) {
            return "EnableAll2";
        }
        return "EnableChannel2";
    }

    public void muteDialog(long j, long j2, boolean z) {
        int i = this.currentAccount;
        if (z) {
            getInstance(i).muteUntil(j, j2, Integer.MAX_VALUE);
            return;
        }
        boolean zIsGlobalNotificationsEnabled = getInstance(i).isGlobalNotificationsEnabled(j, false, false);
        boolean z2 = j2 != 0;
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
        if (zIsGlobalNotificationsEnabled && !z2) {
            editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2));
        } else {
            editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 0);
        }
        if (j2 == 0) {
            getMessagesStorage().setDialogFlags(j, 0L);
            TLRPC.Dialog dialog = getMessagesController().dialogs_dict.get(j);
            if (dialog != null) {
                dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
            }
        }
        editorEdit.apply();
        updateServerNotificationsSettings(j, j2);
    }

    public NotificationsSettingsFacade getNotificationsSettingsFacade() {
        return this.dialogsNotificationsFacade;
    }

    public void loadTopicsNotificationsExceptions(final long j, final Consumer<HashSet<Integer>> consumer) {
        getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadTopicsNotificationsExceptions$54(j, consumer);
            }
        });
    }

    public /* synthetic */ void lambda$loadTopicsNotificationsExceptions$54(long j, final Consumer consumer) {
        final HashSet hashSet = new HashSet();
        Iterator<Map.Entry<String, ?>> it = MessagesController.getNotificationsSettings(this.currentAccount).getAll().entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            if (key != null) {
                if (key.startsWith(NotificationsSettingsFacade.PROPERTY_NOTIFY + j)) {
                    Integer num = Utilities.parseInt((CharSequence) key.replace(NotificationsSettingsFacade.PROPERTY_NOTIFY + j, _UrlKt.FRAGMENT_ENCODE_SET));
                    int iIntValue = num.intValue();
                    if (iIntValue != 0 && getMessagesController().isDialogMuted(j, iIntValue) != getMessagesController().isDialogMuted(j, 0L)) {
                        hashSet.add(num);
                    }
                }
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.$r8$lambda$LakEptqhCv9OhMJsIqb7LZKKyO8(consumer, hashSet);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$LakEptqhCv9OhMJsIqb7LZKKyO8(Consumer consumer, HashSet hashSet) {
        if (consumer != null) {
            consumer.accept(hashSet);
        }
    }

    public static class DialogKey {
        final long dialogId;
        final boolean story;
        final long topicId;

        public /* synthetic */ DialogKey(long j, long j2, boolean z, NotificationsControllerIA notificationsControllerIA) {
            this(j, j2, z);
        }

        private DialogKey(long j, long j2, boolean z) {
            this.dialogId = j;
            this.topicId = j2;
            this.story = z;
        }
    }

    public static class StoryNotification {
        public long date;
        final HashMap<Integer, Pair<Long, Long>> dateByIds;
        final long dialogId;
        boolean hidden;
        String localName;

        public StoryNotification(long j, String str, int i, long j2) {
            this(j, str, i, j2, j2 + DurationKt.MILLIS_IN_DAY);
        }

        public StoryNotification(long j, String str, int i, long j2, long j3) {
            HashMap<Integer, Pair<Long, Long>> map = new HashMap<>();
            this.dateByIds = map;
            this.dialogId = j;
            this.localName = str;
            map.put(Integer.valueOf(i), new Pair<>(Long.valueOf(j2), Long.valueOf(j3)));
            this.date = j2;
        }

        public long getLeastDate() {
            long jLongValue = -1;
            for (Pair<Long, Long> pair : this.dateByIds.values()) {
                if (jLongValue == -1 || jLongValue > ((Long) pair.first).longValue()) {
                    jLongValue = ((Long) pair.first).longValue();
                }
            }
            return jLongValue;
        }
    }

    public void checkStoryPushes() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = 0;
        boolean z = false;
        while (i < this.storyPushMessages.size()) {
            StoryNotification storyNotification = this.storyPushMessages.get(i);
            Iterator<Map.Entry<Integer, Pair<Long, Long>>> it = storyNotification.dateByIds.entrySet().iterator();
            while (it.hasNext()) {
                if (jCurrentTimeMillis >= ((Long) it.next().getValue().second).longValue()) {
                    it.remove();
                    z = true;
                }
            }
            if (z) {
                if (storyNotification.dateByIds.isEmpty()) {
                    getMessagesStorage().deleteStoryPushMessage(storyNotification.dialogId);
                    this.storyPushMessages.remove(i);
                    i--;
                } else {
                    getMessagesStorage().putStoryPushMessage(storyNotification);
                }
            }
            i++;
        }
        if (z) {
            showOrUpdateNotification(false);
        }
        updateStoryPushesRunnable();
    }

    private void updateStoryPushesRunnable() {
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < this.storyPushMessages.size(); i++) {
            Iterator<Pair<Long, Long>> it = this.storyPushMessages.get(i).dateByIds.values().iterator();
            while (it.hasNext()) {
                jMin = Math.min(jMin, ((Long) it.next().second).longValue());
            }
        }
        DispatchQueue dispatchQueue = notificationsQueue;
        dispatchQueue.cancelRunnable(this.checkStoryPushesRunnable);
        long jCurrentTimeMillis = jMin - System.currentTimeMillis();
        if (jMin != LongCompanionObject.MAX_VALUE) {
            dispatchQueue.postRunnable(this.checkStoryPushesRunnable, Math.max(0L, jCurrentTimeMillis));
        }
    }

    private String getTitle(TLRPC.Chat chat) {
        if (chat == null) {
            return null;
        }
        if (chat.monoforum) {
            return ForumUtilities.getMonoForumTitle(this.currentAccount, chat);
        }
        return chat.title;
    }
}
