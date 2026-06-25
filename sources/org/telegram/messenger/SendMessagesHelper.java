package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.collection.LongSparseArray;
import androidx.core.view.inputmethod.InputContentInfoCompat;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.hooks.PluginsHooks;
import com.exteragram.messenger.utils.text.LocaleUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.utils.tlutils.AmountUtils$Amount;
import org.telegram.messenger.utils.tlutils.AmountUtils$Currency;
import org.telegram.messenger.utils.tlutils.TlUtils;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Business.QuickRepliesController;
import org.telegram.p035ui.Cells.ChatMessageCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.AlertsCreator;
import org.telegram.p035ui.Components.AnimatedEmojiSpan;
import org.telegram.p035ui.Components.AnimatedFileDrawable;
import org.telegram.p035ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p035ui.Components.Reactions.ReactionsUtils;
import org.telegram.p035ui.Components.poll.PollAttachedMedia;
import org.telegram.p035ui.Components.poll.PollAttachedMediaPack;
import org.telegram.p035ui.Components.poll.PollSendParams;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaFile;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaGallery;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaLink;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaLocation;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaMusic;
import org.telegram.p035ui.Components.poll.attached.PollAttachedMediaSticker;
import org.telegram.p035ui.OAuthSheet;
import org.telegram.p035ui.Stars.StarsController;
import org.telegram.p035ui.TON.TONIntroActivity;
import org.telegram.p035ui.TwoStepVerificationActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.QuickAckDelegate;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_account;
import org.telegram.tgnet.p034tl.TL_iv;
import org.telegram.tgnet.p034tl.TL_stories;
import org.telegram.tgnet.p034tl.TL_update;

/* JADX INFO: loaded from: classes.dex */
public class SendMessagesHelper extends BaseController implements NotificationCenter.NotificationCenterDelegate {
    private static final int ERROR_TYPE_FILE_TOO_LARGE = 2;
    private static final int ERROR_TYPE_UNSUPPORTED = 1;
    private static volatile SendMessagesHelper[] Instance = null;
    public static final int MEDIA_TYPE_DICE = 11;
    public static final int MEDIA_TYPE_RICH = 13;
    public static final int MEDIA_TYPE_STORY = 12;
    private static DispatchQueue mediaSendQueue = new DispatchQueue("mediaSendQueue");
    private static ThreadPoolExecutor mediaSendThreadPool;
    private final HashMap<String, ArrayList<DelayedMessage>> delayedMessages;
    private final SparseArray<TLRPC.Message> editingMessages;
    private final PluginsHooks hooks;
    private final HashMap<String, ImportingHistory> importingHistoryFiles;
    private final LongSparseArray<ImportingHistory> importingHistoryMap;
    private final HashMap<String, ImportingStickers> importingStickersFiles;
    private final HashMap<String, ImportingStickers> importingStickersMap;
    private LocationProvider locationProvider;
    private final SparseArray<TLRPC.Message> sendingMessages;
    private final LongSparseArray<Integer> sendingMessagesIdDialogs;
    private final SparseArray<MessageObject> unsentMessages;
    private final SparseArray<TLRPC.Message> uploadMessages;
    private final LongSparseArray<Integer> uploadingMessagesIdDialogs;
    private final LongSparseArray<Long> voteSendTime;
    private final HashMap<String, Boolean> waitingForCallback;
    private final HashMap<String, List<String>> waitingForCallbackMap;
    private final HashMap<String, MessageObject> waitingForLocation;
    private final HashMap<Integer, Boolean> waitingForTodoUpdate;
    private final HashMap<String, byte[]> waitingForVote;

    /* JADX INFO: loaded from: classes5.dex */
    public static class SendingMediaInfo {
        public boolean canDeleteAfter;
        public String caption;
        public String coverPath;
        public TLRPC.Photo coverPhoto;
        public boolean discardLivePhoto;
        public TLRPC.VideoSize emojiMarkup;
        public ArrayList<TLRPC.MessageEntity> entities;
        public boolean forceImage;
        public boolean hasMediaSpoilers;
        public boolean highQuality;
        public String imagePath;
        public TLRPC.BotInlineResult inlineResult;
        public boolean isLivePhoto;
        public boolean isVideo;
        public long livePhotoTimestampUs;
        public long livePhotoVideoOffset;
        public ArrayList<TLRPC.InputDocument> masks;
        public MediaController.PhotoEntry originalPhotoEntry;
        public String paintPath;
        public HashMap<String, String> params;
        public String path;
        public int pollIndex;
        public MediaController.SearchImage searchImage;
        public long stars;
        public String thumbPath;
        public int ttl;
        public boolean updateStickersOrder;
        public Uri uri;
        public VideoEditedInfo videoEditedInfo;
    }

    public static /* synthetic */ void $r8$lambda$Jsn7QuvOY3CC3oxBrSTYyfurp3Q(String str) {
    }

    private static TL_iv.PageBlock toInputPageBlock(TL_iv.PageBlock pageBlock) {
        return pageBlock;
    }

    public static boolean checkUpdateStickersOrder(CharSequence charSequence) {
        if (charSequence instanceof Spannable) {
            for (AnimatedEmojiSpan animatedEmojiSpan : (AnimatedEmojiSpan[]) ((Spannable) charSequence).getSpans(0, charSequence.length(), AnimatedEmojiSpan.class)) {
                if (animatedEmojiSpan.fromEmojiKeyboard) {
                    return true;
                }
            }
        }
        return false;
    }

    public TLRPC.InputReplyTo createReplyInput(TL_stories.StoryItem storyItem) {
        TLRPC.TL_inputReplyToStory tL_inputReplyToStory = new TLRPC.TL_inputReplyToStory();
        tL_inputReplyToStory.story_id = storyItem.f1454id;
        tL_inputReplyToStory.peer = getMessagesController().getInputPeer(storyItem.dialogId);
        return tL_inputReplyToStory;
    }

    public TLRPC.InputReplyTo createReplyInput(int i) {
        return createReplyInput(null, i, 0, null);
    }

    public TLRPC.InputReplyTo createReplyInput(TLRPC.InputPeer inputPeer, int i, int i2, ChatActivity.ReplyQuote replyQuote) {
        TLRPC.TodoItem todoItem;
        MessageObject messageObject;
        TLRPC.PollAnswer pollAnswer;
        TLRPC.TL_inputReplyToMessage tL_inputReplyToMessage = new TLRPC.TL_inputReplyToMessage();
        tL_inputReplyToMessage.reply_to_msg_id = i;
        if (i2 != 0) {
            tL_inputReplyToMessage.flags |= 1;
            tL_inputReplyToMessage.top_msg_id = i2;
        }
        if (replyQuote != null && replyQuote.poll && (pollAnswer = replyQuote.answer) != null) {
            tL_inputReplyToMessage.poll_option = pollAnswer.option;
        } else if (replyQuote != null && replyQuote.todo && (todoItem = replyQuote.task) != null) {
            tL_inputReplyToMessage.flags |= 64;
            tL_inputReplyToMessage.todo_item_id = todoItem.f1405id;
        } else if (replyQuote != null && !replyQuote.todo && !replyQuote.poll) {
            String text = replyQuote.getText();
            tL_inputReplyToMessage.quote_text = text;
            if (!TextUtils.isEmpty(text)) {
                tL_inputReplyToMessage.flags |= 4;
                ArrayList<TLRPC.MessageEntity> filteredEntities = replyQuote.getFilteredEntities();
                tL_inputReplyToMessage.quote_entities = filteredEntities;
                if (filteredEntities != null && !filteredEntities.isEmpty()) {
                    tL_inputReplyToMessage.quote_entities = new ArrayList<>(tL_inputReplyToMessage.quote_entities);
                    tL_inputReplyToMessage.flags |= 8;
                }
                tL_inputReplyToMessage.flags |= 16;
                tL_inputReplyToMessage.quote_offset = replyQuote.start;
            }
        }
        if (replyQuote != null && (messageObject = replyQuote.message) != null) {
            TLRPC.InputPeer inputPeer2 = getMessagesController().getInputPeer(messageObject.getDialogId());
            if (inputPeer2 != null && !MessageObject.peersEqual(inputPeer2, inputPeer)) {
                tL_inputReplyToMessage.flags |= 2;
                tL_inputReplyToMessage.reply_to_peer_id = inputPeer2;
            }
        }
        return tL_inputReplyToMessage;
    }

    public TLRPC.InputReplyTo createReplyInput(TLRPC.TL_messageReplyHeader tL_messageReplyHeader) {
        TLRPC.TL_inputReplyToMessage tL_inputReplyToMessage = new TLRPC.TL_inputReplyToMessage();
        tL_inputReplyToMessage.reply_to_msg_id = tL_messageReplyHeader.reply_to_msg_id;
        int i = tL_messageReplyHeader.flags;
        if ((i & 2) != 0) {
            tL_inputReplyToMessage.flags |= 1;
            tL_inputReplyToMessage.top_msg_id = tL_messageReplyHeader.reply_to_top_id;
        }
        if ((i & 1) != 0) {
            tL_inputReplyToMessage.flags |= 2;
            tL_inputReplyToMessage.reply_to_peer_id = MessagesController.getInstance(this.currentAccount).getInputPeer(tL_messageReplyHeader.reply_to_peer_id);
        }
        if (tL_messageReplyHeader.quote) {
            int i2 = tL_messageReplyHeader.flags;
            if ((i2 & 64) != 0) {
                tL_inputReplyToMessage.flags |= 4;
                tL_inputReplyToMessage.quote_text = tL_messageReplyHeader.quote_text;
            }
            if ((i2 & 128) != 0) {
                tL_inputReplyToMessage.flags |= 8;
                tL_inputReplyToMessage.quote_entities = tL_messageReplyHeader.quote_entities;
            }
            if ((i2 & 1024) != 0) {
                tL_inputReplyToMessage.flags |= 16;
                tL_inputReplyToMessage.quote_offset = tL_messageReplyHeader.quote_offset;
            }
        }
        if ((tL_messageReplyHeader.flags & 2048) != 0) {
            tL_inputReplyToMessage.flags |= 64;
            tL_inputReplyToMessage.todo_item_id = tL_messageReplyHeader.todo_item_id;
        }
        byte[] bArr = tL_messageReplyHeader.poll_option;
        if (bArr != null) {
            tL_inputReplyToMessage.poll_option = bArr;
        }
        return tL_inputReplyToMessage;
    }

    public class ImportingHistory {
        public long dialogId;
        public double estimatedUploadSpeed;
        public String historyPath;
        public long importId;
        private long lastUploadSize;
        private long lastUploadTime;
        public TLRPC.InputPeer peer;
        public long totalSize;
        public int uploadProgress;
        public long uploadedSize;
        public ArrayList<Uri> mediaPaths = new ArrayList<>();
        public HashSet<String> uploadSet = new HashSet<>();
        public HashMap<String, Float> uploadProgresses = new HashMap<>();
        public HashMap<String, Long> uploadSize = new HashMap<>();
        public ArrayList<String> uploadMedia = new ArrayList<>();
        public int timeUntilFinish = Integer.MAX_VALUE;

        public ImportingHistory() {
        }

        public void initImport(TLRPC.InputFile inputFile) {
            TLRPC.TL_messages_initHistoryImport tL_messages_initHistoryImport = new TLRPC.TL_messages_initHistoryImport();
            tL_messages_initHistoryImport.file = inputFile;
            tL_messages_initHistoryImport.media_count = this.mediaPaths.size();
            tL_messages_initHistoryImport.peer = this.peer;
            SendMessagesHelper.this.getConnectionsManager().sendRequest(tL_messages_initHistoryImport, new C28041(tL_messages_initHistoryImport), 2);
        }

        /* JADX INFO: renamed from: org.telegram.messenger.SendMessagesHelper$ImportingHistory$1 */
        /* JADX INFO: loaded from: classes5.dex */
        public class C28041 implements RequestDelegate {
            final /* synthetic */ TLRPC.TL_messages_initHistoryImport val$req;

            public C28041(TLRPC.TL_messages_initHistoryImport tL_messages_initHistoryImport) {
                this.val$req = tL_messages_initHistoryImport;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC.TL_error tL_error) {
                final TLRPC.TL_messages_initHistoryImport tL_messages_initHistoryImport = this.val$req;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$ImportingHistory$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(tLObject, tL_messages_initHistoryImport, tL_error);
                    }
                });
            }

            public /* synthetic */ void lambda$run$0(TLObject tLObject, TLRPC.TL_messages_initHistoryImport tL_messages_initHistoryImport, TLRPC.TL_error tL_error) {
                boolean z = tLObject instanceof TLRPC.TL_messages_historyImport;
                ImportingHistory importingHistory = ImportingHistory.this;
                if (z) {
                    importingHistory.importId = ((TLRPC.TL_messages_historyImport) tLObject).f1359id;
                    importingHistory.uploadSet.remove(importingHistory.historyPath);
                    SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(ImportingHistory.this.dialogId));
                    if (ImportingHistory.this.uploadSet.isEmpty()) {
                        ImportingHistory.this.startImport();
                    }
                    ImportingHistory.this.lastUploadTime = SystemClock.elapsedRealtime();
                    int size = ImportingHistory.this.uploadMedia.size();
                    for (int i = 0; i < size; i++) {
                        SendMessagesHelper.this.getFileLoader().uploadFile(ImportingHistory.this.uploadMedia.get(i), false, true, 67108864);
                    }
                    return;
                }
                SendMessagesHelper.this.importingHistoryMap.remove(ImportingHistory.this.dialogId);
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(ImportingHistory.this.dialogId), tL_messages_initHistoryImport, tL_error);
            }
        }

        public long getUploadedCount() {
            return this.uploadedSize;
        }

        public long getTotalCount() {
            return this.totalSize;
        }

        public void onFileFailedToUpload(String str) {
            if (str.equals(this.historyPath)) {
                SendMessagesHelper.this.importingHistoryMap.remove(this.dialogId);
                TLRPC.TL_error tL_error = new TLRPC.TL_error();
                tL_error.code = 400;
                tL_error.text = "IMPORT_UPLOAD_FAILED";
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(this.dialogId), new TLRPC.TL_messages_initHistoryImport(), tL_error);
                return;
            }
            this.uploadSet.remove(str);
        }

        public void addUploadProgress(String str, long j, float f) {
            this.uploadProgresses.put(str, Float.valueOf(f));
            this.uploadSize.put(str, Long.valueOf(j));
            this.uploadedSize = 0L;
            Iterator<Map.Entry<String, Long>> it = this.uploadSize.entrySet().iterator();
            while (it.hasNext()) {
                this.uploadedSize += it.next().getValue().longValue();
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (!str.equals(this.historyPath)) {
                long j2 = this.uploadedSize;
                if (j2 != this.lastUploadSize) {
                    if (jElapsedRealtime != this.lastUploadTime) {
                        double d = (j2 - r2) / ((jElapsedRealtime - r4) / 1000.0d);
                        double d2 = this.estimatedUploadSpeed;
                        if (d2 == 0.0d) {
                            this.estimatedUploadSpeed = d;
                        } else {
                            this.estimatedUploadSpeed = (d * 0.01d) + (0.99d * d2);
                        }
                        this.timeUntilFinish = (int) (((this.totalSize - j2) * 1000) / this.estimatedUploadSpeed);
                        this.lastUploadSize = j2;
                        this.lastUploadTime = jElapsedRealtime;
                    }
                }
            }
            int uploadedCount = (int) ((getUploadedCount() / getTotalCount()) * 100.0f);
            if (this.uploadProgress != uploadedCount) {
                this.uploadProgress = uploadedCount;
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(this.dialogId));
            }
        }

        public void onMediaImport(String str, long j, TLRPC.InputFile inputFile) {
            String lowerCase;
            addUploadProgress(str, j, 1.0f);
            TLRPC.TL_messages_uploadImportedMedia tL_messages_uploadImportedMedia = new TLRPC.TL_messages_uploadImportedMedia();
            tL_messages_uploadImportedMedia.peer = this.peer;
            tL_messages_uploadImportedMedia.import_id = this.importId;
            tL_messages_uploadImportedMedia.file_name = new File(str).getName();
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            int iLastIndexOf = tL_messages_uploadImportedMedia.file_name.lastIndexOf(46);
            if (iLastIndexOf == -1) {
                lowerCase = "txt";
            } else {
                lowerCase = tL_messages_uploadImportedMedia.file_name.substring(iLastIndexOf + 1).toLowerCase();
            }
            String mimeTypeFromExtension = singleton.getMimeTypeFromExtension(lowerCase);
            if (mimeTypeFromExtension == null) {
                if ("opus".equals(lowerCase)) {
                    mimeTypeFromExtension = "audio/opus";
                } else if ("webp".equals(lowerCase)) {
                    mimeTypeFromExtension = "image/webp";
                } else {
                    mimeTypeFromExtension = "text/plain";
                }
            }
            if (mimeTypeFromExtension.equals("image/jpg") || mimeTypeFromExtension.equals("image/jpeg")) {
                TLRPC.TL_inputMediaUploadedPhoto tL_inputMediaUploadedPhoto = new TLRPC.TL_inputMediaUploadedPhoto();
                tL_inputMediaUploadedPhoto.file = inputFile;
                tL_messages_uploadImportedMedia.media = tL_inputMediaUploadedPhoto;
            } else {
                TLRPC.TL_inputMediaUploadedDocument tL_inputMediaUploadedDocument = new TLRPC.TL_inputMediaUploadedDocument();
                tL_inputMediaUploadedDocument.file = inputFile;
                tL_inputMediaUploadedDocument.mime_type = mimeTypeFromExtension;
                tL_messages_uploadImportedMedia.media = tL_inputMediaUploadedDocument;
            }
            SendMessagesHelper.this.getConnectionsManager().sendRequest(tL_messages_uploadImportedMedia, new C28052(str), 2);
        }

        /* JADX INFO: renamed from: org.telegram.messenger.SendMessagesHelper$ImportingHistory$2 */
        /* JADX INFO: loaded from: classes5.dex */
        public class C28052 implements RequestDelegate {
            final /* synthetic */ String val$path;

            public C28052(String str) {
                this.val$path = str;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                final String str = this.val$path;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$ImportingHistory$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(str);
                    }
                });
            }

            public /* synthetic */ void lambda$run$0(String str) {
                ImportingHistory.this.uploadSet.remove(str);
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(ImportingHistory.this.dialogId));
                if (ImportingHistory.this.uploadSet.isEmpty()) {
                    ImportingHistory.this.startImport();
                }
            }
        }

        public void startImport() {
            TLRPC.TL_messages_startHistoryImport tL_messages_startHistoryImport = new TLRPC.TL_messages_startHistoryImport();
            tL_messages_startHistoryImport.peer = this.peer;
            tL_messages_startHistoryImport.import_id = this.importId;
            SendMessagesHelper.this.getConnectionsManager().sendRequest(tL_messages_startHistoryImport, new C28063(tL_messages_startHistoryImport));
        }

        /* JADX INFO: renamed from: org.telegram.messenger.SendMessagesHelper$ImportingHistory$3 */
        /* JADX INFO: loaded from: classes5.dex */
        public class C28063 implements RequestDelegate {
            final /* synthetic */ TLRPC.TL_messages_startHistoryImport val$req;

            public C28063(TLRPC.TL_messages_startHistoryImport tL_messages_startHistoryImport) {
                this.val$req = tL_messages_startHistoryImport;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(TLObject tLObject, final TLRPC.TL_error tL_error) {
                final TLRPC.TL_messages_startHistoryImport tL_messages_startHistoryImport = this.val$req;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$ImportingHistory$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(tL_error, tL_messages_startHistoryImport);
                    }
                });
            }

            public /* synthetic */ void lambda$run$0(TLRPC.TL_error tL_error, TLRPC.TL_messages_startHistoryImport tL_messages_startHistoryImport) {
                SendMessagesHelper.this.importingHistoryMap.remove(ImportingHistory.this.dialogId);
                ImportingHistory importingHistory = ImportingHistory.this;
                if (tL_error == null) {
                    SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(ImportingHistory.this.dialogId));
                } else {
                    SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(ImportingHistory.this.dialogId), tL_messages_startHistoryImport, tL_error);
                }
            }
        }

        public void setImportProgress(int i) {
            if (i == 100) {
                SendMessagesHelper.this.importingHistoryMap.remove(this.dialogId);
            }
            SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(this.dialogId));
        }
    }

    /* JADX INFO: loaded from: classes5.dex */
    public static class ImportingSticker {
        public boolean animated;
        public String emoji;
        public TLRPC.TL_inputStickerSetItem item;
        public String mimeType;
        public String path;
        public boolean validated;
        public VideoEditedInfo videoEditedInfo;

        public void uploadMedia(int i, TLRPC.InputFile inputFile, Runnable runnable) {
            TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia = new TLRPC.TL_messages_uploadMedia();
            tL_messages_uploadMedia.peer = new TLRPC.TL_inputPeerSelf();
            TLRPC.TL_inputMediaUploadedDocument tL_inputMediaUploadedDocument = new TLRPC.TL_inputMediaUploadedDocument();
            tL_messages_uploadMedia.media = tL_inputMediaUploadedDocument;
            tL_inputMediaUploadedDocument.file = inputFile;
            tL_inputMediaUploadedDocument.mime_type = this.mimeType;
            ConnectionsManager.getInstance(i).sendRequest(tL_messages_uploadMedia, new C28071(runnable), 2);
        }

        /* JADX INFO: renamed from: org.telegram.messenger.SendMessagesHelper$ImportingSticker$1 */
        public class C28071 implements RequestDelegate {
            final /* synthetic */ Runnable val$onFinish;

            public C28071(Runnable runnable) {
                this.val$onFinish = runnable;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, TLRPC.TL_error tL_error) {
                final Runnable runnable = this.val$onFinish;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$ImportingSticker$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(tLObject, runnable);
                    }
                });
            }

            public /* synthetic */ void lambda$run$0(TLObject tLObject, Runnable runnable) {
                boolean z = tLObject instanceof TLRPC.TL_messageMediaDocument;
                ImportingSticker importingSticker = ImportingSticker.this;
                if (z) {
                    importingSticker.item = new TLRPC.TL_inputStickerSetItem();
                    ImportingSticker.this.item.document = new TLRPC.TL_inputDocument();
                    ImportingSticker importingSticker2 = ImportingSticker.this;
                    TLRPC.TL_inputStickerSetItem tL_inputStickerSetItem = importingSticker2.item;
                    TLRPC.InputDocument inputDocument = tL_inputStickerSetItem.document;
                    TLRPC.Document document = ((TLRPC.TL_messageMediaDocument) tLObject).document;
                    inputDocument.f1262id = document.f1253id;
                    inputDocument.access_hash = document.access_hash;
                    inputDocument.file_reference = document.file_reference;
                    String str = importingSticker2.emoji;
                    if (str == null) {
                        str = _UrlKt.FRAGMENT_ENCODE_SET;
                    }
                    tL_inputStickerSetItem.emoji = str;
                    importingSticker2.mimeType = document.mime_type;
                } else if (importingSticker.animated) {
                    importingSticker.mimeType = "application/x-bad-tgsticker";
                }
                runnable.run();
            }
        }
    }

    public class ImportingStickers {
        public double estimatedUploadSpeed;
        private long lastUploadSize;
        private long lastUploadTime;
        public String shortName;
        public String software;
        public String title;
        public long totalSize;
        public int uploadProgress;
        public long uploadedSize;
        public HashMap<String, ImportingSticker> uploadSet = new HashMap<>();
        public HashMap<String, Float> uploadProgresses = new HashMap<>();
        public HashMap<String, Long> uploadSize = new HashMap<>();
        public ArrayList<ImportingSticker> uploadMedia = new ArrayList<>();
        public int timeUntilFinish = Integer.MAX_VALUE;

        public ImportingStickers() {
        }

        public void initImport() {
            SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersImportProgressChanged, this.shortName);
            this.lastUploadTime = SystemClock.elapsedRealtime();
            int size = this.uploadMedia.size();
            for (int i = 0; i < size; i++) {
                SendMessagesHelper.this.getFileLoader().uploadFile(this.uploadMedia.get(i).path, false, true, 67108864);
            }
        }

        public long getUploadedCount() {
            return this.uploadedSize;
        }

        public long getTotalCount() {
            return this.totalSize;
        }

        public void onFileFailedToUpload(String str) {
            ImportingSticker importingStickerRemove = this.uploadSet.remove(str);
            if (importingStickerRemove != null) {
                this.uploadMedia.remove(importingStickerRemove);
            }
        }

        public void addUploadProgress(String str, long j, float f) {
            this.uploadProgresses.put(str, Float.valueOf(f));
            this.uploadSize.put(str, Long.valueOf(j));
            this.uploadedSize = 0L;
            Iterator<Map.Entry<String, Long>> it = this.uploadSize.entrySet().iterator();
            while (it.hasNext()) {
                this.uploadedSize += it.next().getValue().longValue();
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j2 = this.uploadedSize;
            if (j2 != this.lastUploadSize) {
                if (jElapsedRealtime != this.lastUploadTime) {
                    double d = (j2 - r0) / ((jElapsedRealtime - r2) / 1000.0d);
                    double d2 = this.estimatedUploadSpeed;
                    if (d2 == 0.0d) {
                        this.estimatedUploadSpeed = d;
                    } else {
                        this.estimatedUploadSpeed = (d * 0.01d) + (0.99d * d2);
                    }
                    this.timeUntilFinish = (int) (((this.totalSize - j2) * 1000) / this.estimatedUploadSpeed);
                    this.lastUploadSize = j2;
                    this.lastUploadTime = jElapsedRealtime;
                }
            }
            int uploadedCount = (int) ((getUploadedCount() / getTotalCount()) * 100.0f);
            if (this.uploadProgress != uploadedCount) {
                this.uploadProgress = uploadedCount;
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersImportProgressChanged, this.shortName);
            }
        }

        public void onMediaImport(final String str, long j, TLRPC.InputFile inputFile) {
            addUploadProgress(str, j, 1.0f);
            ImportingSticker importingSticker = this.uploadSet.get(str);
            if (importingSticker == null) {
                return;
            }
            importingSticker.uploadMedia(SendMessagesHelper.this.currentAccount, inputFile, new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$ImportingStickers$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMediaImport$0(str);
                }
            });
        }

        public /* synthetic */ void lambda$onMediaImport$0(String str) {
            this.uploadSet.remove(str);
            SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersImportProgressChanged, this.shortName);
            if (this.uploadSet.isEmpty()) {
                startImport();
            }
        }

        public void startImport() {
            TLRPC.TL_stickers_createStickerSet tL_stickers_createStickerSet = new TLRPC.TL_stickers_createStickerSet();
            tL_stickers_createStickerSet.user_id = new TLRPC.TL_inputUserSelf();
            tL_stickers_createStickerSet.title = this.title;
            tL_stickers_createStickerSet.short_name = this.shortName;
            String str = this.software;
            if (str != null) {
                tL_stickers_createStickerSet.software = str;
                tL_stickers_createStickerSet.flags |= 8;
            }
            int size = this.uploadMedia.size();
            for (int i = 0; i < size; i++) {
                TLRPC.TL_inputStickerSetItem tL_inputStickerSetItem = this.uploadMedia.get(i).item;
                if (tL_inputStickerSetItem != null) {
                    tL_stickers_createStickerSet.stickers.add(tL_inputStickerSetItem);
                }
            }
            SendMessagesHelper.this.getConnectionsManager().sendRequest(tL_stickers_createStickerSet, new C28081(tL_stickers_createStickerSet));
        }

        /* JADX INFO: renamed from: org.telegram.messenger.SendMessagesHelper$ImportingStickers$1 */
        /* JADX INFO: loaded from: classes5.dex */
        public class C28081 implements RequestDelegate {
            final /* synthetic */ TLRPC.TL_stickers_createStickerSet val$req;

            public C28081(TLRPC.TL_stickers_createStickerSet tL_stickers_createStickerSet) {
                this.val$req = tL_stickers_createStickerSet;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC.TL_error tL_error) {
                final TLRPC.TL_stickers_createStickerSet tL_stickers_createStickerSet = this.val$req;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$ImportingStickers$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(tL_error, tL_stickers_createStickerSet, tLObject);
                    }
                });
            }

            public /* synthetic */ void lambda$run$0(TLRPC.TL_error tL_error, TLRPC.TL_stickers_createStickerSet tL_stickers_createStickerSet, TLObject tLObject) {
                SendMessagesHelper.this.importingStickersMap.remove(ImportingStickers.this.shortName);
                ImportingStickers importingStickers = ImportingStickers.this;
                if (tL_error == null) {
                    SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersImportProgressChanged, ImportingStickers.this.shortName);
                } else {
                    SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersImportProgressChanged, ImportingStickers.this.shortName, tL_stickers_createStickerSet, tL_error);
                }
                if (tLObject instanceof TLRPC.TL_messages_stickerSet) {
                    NotificationCenter notificationCenter = SendMessagesHelper.this.getNotificationCenter();
                    int i = NotificationCenter.stickersImportComplete;
                    boolean zHasObservers = notificationCenter.hasObservers(i);
                    ImportingStickers importingStickers2 = ImportingStickers.this;
                    if (zHasObservers) {
                        SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(i, tLObject);
                    } else {
                        SendMessagesHelper.this.getMediaDataController().toggleStickerSet(null, tLObject, 2, null, false, false);
                    }
                }
            }
        }

        public void setImportProgress(int i) {
            if (i == 100) {
                SendMessagesHelper.this.importingStickersMap.remove(this.shortName);
            }
            SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.stickersImportProgressChanged, this.shortName);
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        mediaSendThreadPool = new ThreadPoolExecutor(iAvailableProcessors, iAvailableProcessors, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        Instance = new SendMessagesHelper[16];
    }

    /* JADX INFO: loaded from: classes5.dex */
    public static class MediaSendPrepareWorker {
        public volatile String parentObject;
        public volatile TLRPC.TL_photo photo;
        public CountDownLatch sync;

        public /* synthetic */ MediaSendPrepareWorker(SendMessagesHelperIA sendMessagesHelperIA) {
            this();
        }

        private MediaSendPrepareWorker() {
        }
    }

    /* JADX INFO: renamed from: org.telegram.messenger.SendMessagesHelper$1 */
    public class C28031 implements LocationProvider.LocationProviderDelegate {
        public C28031() {
        }

        @Override // org.telegram.messenger.SendMessagesHelper.LocationProvider.LocationProviderDelegate
        public void onLocationAcquired(Location location) {
            SendMessagesHelper.this.sendLocation(location);
            SendMessagesHelper.this.waitingForLocation.clear();
        }

        @Override // org.telegram.messenger.SendMessagesHelper.LocationProvider.LocationProviderDelegate
        public void onUnableLocationAcquire() {
            SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.wasUnableToFindCurrentLocation, new HashMap(SendMessagesHelper.this.waitingForLocation));
            SendMessagesHelper.this.waitingForLocation.clear();
        }
    }

    @SuppressLint({"MissingPermission"})
    public static class LocationProvider {
        private LocationProviderDelegate delegate;
        private Location lastKnownLocation;
        private LocationManager locationManager;
        private Runnable locationQueryCancelRunnable;
        private GpsLocationListener gpsLocationListener = new GpsLocationListener();
        private GpsLocationListener networkLocationListener = new GpsLocationListener();

        public interface LocationProviderDelegate {
            void onLocationAcquired(Location location);

            void onUnableLocationAcquire();
        }

        public class GpsLocationListener implements LocationListener {
            public /* synthetic */ GpsLocationListener(LocationProvider locationProvider, SendMessagesHelperIA sendMessagesHelperIA) {
                this();
            }

            @Override // android.location.LocationListener
            public void onProviderDisabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(String str, int i, Bundle bundle) {
            }

            private GpsLocationListener() {
            }

            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) {
                if (location == null || LocationProvider.this.locationQueryCancelRunnable == null) {
                    return;
                }
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1045d("found location " + location);
                }
                LocationProvider.this.lastKnownLocation = location;
                if (location.getAccuracy() < 100.0f) {
                    if (LocationProvider.this.delegate != null) {
                        LocationProvider.this.delegate.onLocationAcquired(location);
                    }
                    if (LocationProvider.this.locationQueryCancelRunnable != null) {
                        AndroidUtilities.cancelRunOnUIThread(LocationProvider.this.locationQueryCancelRunnable);
                    }
                    LocationProvider.this.cleanup();
                }
            }
        }

        public LocationProvider() {
        }

        public LocationProvider(LocationProviderDelegate locationProviderDelegate) {
            this.delegate = locationProviderDelegate;
        }

        public void setDelegate(LocationProviderDelegate locationProviderDelegate) {
            this.delegate = locationProviderDelegate;
        }

        public void cleanup() {
            this.locationManager.removeUpdates(this.gpsLocationListener);
            this.locationManager.removeUpdates(this.networkLocationListener);
            this.lastKnownLocation = null;
            this.locationQueryCancelRunnable = null;
        }

        public void start() {
            if (this.locationManager == null) {
                this.locationManager = (LocationManager) ApplicationLoader.applicationContext.getSystemService("location");
            }
            try {
                this.locationManager.requestLocationUpdates("gps", 1L, 0.0f, this.gpsLocationListener);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
            try {
                this.locationManager.requestLocationUpdates("network", 1L, 0.0f, this.networkLocationListener);
            } catch (Exception e2) {
                FileLog.m1048e(e2);
            }
            try {
                Location lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
                this.lastKnownLocation = lastKnownLocation;
                if (lastKnownLocation == null) {
                    this.lastKnownLocation = this.locationManager.getLastKnownLocation("network");
                }
            } catch (Exception e3) {
                FileLog.m1048e(e3);
            }
            Runnable runnable = this.locationQueryCancelRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            Runnable runnable2 = new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$LocationProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$start$0();
                }
            };
            this.locationQueryCancelRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, 5000L);
        }

        public /* synthetic */ void lambda$start$0() {
            LocationProviderDelegate locationProviderDelegate = this.delegate;
            if (locationProviderDelegate != null) {
                Location location = this.lastKnownLocation;
                if (location != null) {
                    locationProviderDelegate.onLocationAcquired(location);
                } else {
                    locationProviderDelegate.onUnableLocationAcquire();
                }
            }
            cleanup();
        }

        public void stop() {
            if (this.locationManager == null) {
                return;
            }
            Runnable runnable = this.locationQueryCancelRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            cleanup();
        }
    }

    /* JADX INFO: loaded from: classes5.dex */
    public class DelayedMessageSendAfterRequest {
        public DelayedMessage delayedMessage;
        public MessageObject msgObj;
        public ArrayList<MessageObject> msgObjs;
        public String originalPath;
        public ArrayList<String> originalPaths;
        public Object parentObject;
        public ArrayList<Object> parentObjects;
        public TLObject request;
        public boolean scheduled;

        public DelayedMessageSendAfterRequest() {
        }
    }

    /* JADX INFO: loaded from: classes5.dex */
    public class DelayedMessage {
        public TLRPC.InputFile coverFile;
        public TLRPC.PhotoSize coverPhotoSize;
        public TLRPC.EncryptedChat encryptedChat;
        public HashMap<Object, Object> extraHashMap;
        public int finalGroupMessage;
        public boolean forceReupload;
        public long groupId;
        public String httpLocation;
        public ArrayList<String> httpLocations;
        public ArrayList<TLRPC.InputMedia> inputMedias;
        public TLRPC.InputMedia inputUploadMedia;
        public boolean isLivePhoto;
        public ArrayList<Boolean> livePhotoIndexes;
        public TLObject locationParent;
        public ArrayList<TLRPC.PhotoSize> locations;
        public ArrayList<MessageObject> messageObjects;
        public ArrayList<TLRPC.Message> messages;
        public MessageObject obj;
        public String originalPath;
        public ArrayList<String> originalPaths;
        public boolean paidMedia;
        public Object parentObject;
        public ArrayList<Object> parentObjects;
        public long peer;
        public boolean performCoverUpload;
        public boolean performMediaUpload;
        public TLRPC.PhotoSize photoSize;
        public ArrayList<Integer> pollIndexes;
        public boolean pollMedia;
        ArrayList<DelayedMessageSendAfterRequest> requests;
        private boolean retriedToSend;
        public boolean[] retriedToSendArray;
        public boolean scheduled;
        public TLObject sendEncryptedRequest;
        public TLObject sendRequest;
        public int topMessageId;
        public int type;
        public VideoEditedInfo videoEditedInfo;
        public ArrayList<VideoEditedInfo> videoEditedInfos;

        public boolean getRetriedToSend(int i) {
            boolean[] zArr;
            if (i < 0 || (zArr = this.retriedToSendArray) == null || i >= zArr.length) {
                return this.retriedToSend;
            }
            return zArr[i];
        }

        public void setRetriedToSend(int i, boolean z) {
            if (i < 0) {
                this.retriedToSend = z;
                return;
            }
            if (this.retriedToSendArray == null) {
                this.retriedToSendArray = new boolean[this.messageObjects.size()];
            }
            this.retriedToSendArray[i] = z;
        }

        public DelayedMessage(long j) {
            this.peer = j;
        }

        public void initForGroup(long j) {
            this.type = 4;
            this.groupId = j;
            this.messageObjects = new ArrayList<>();
            this.messages = new ArrayList<>();
            this.inputMedias = new ArrayList<>();
            this.originalPaths = new ArrayList<>();
            this.parentObjects = new ArrayList<>();
            this.extraHashMap = new HashMap<>();
            this.locations = new ArrayList<>();
            this.httpLocations = new ArrayList<>();
            this.videoEditedInfos = new ArrayList<>();
            this.pollIndexes = new ArrayList<>();
            this.livePhotoIndexes = new ArrayList<>();
        }

        public void addDelayedRequest(TLObject tLObject, MessageObject messageObject, String str, Object obj, DelayedMessage delayedMessage, boolean z) {
            DelayedMessageSendAfterRequest delayedMessageSendAfterRequest = SendMessagesHelper.this.new DelayedMessageSendAfterRequest();
            delayedMessageSendAfterRequest.request = tLObject;
            delayedMessageSendAfterRequest.msgObj = messageObject;
            delayedMessageSendAfterRequest.originalPath = str;
            delayedMessageSendAfterRequest.delayedMessage = delayedMessage;
            delayedMessageSendAfterRequest.parentObject = obj;
            delayedMessageSendAfterRequest.scheduled = z;
            if (this.requests == null) {
                this.requests = new ArrayList<>();
            }
            this.requests.add(delayedMessageSendAfterRequest);
        }

        public void addDelayedRequest(TLObject tLObject, ArrayList<MessageObject> arrayList, ArrayList<String> arrayList2, ArrayList<Object> arrayList3, DelayedMessage delayedMessage, boolean z) {
            DelayedMessageSendAfterRequest delayedMessageSendAfterRequest = SendMessagesHelper.this.new DelayedMessageSendAfterRequest();
            delayedMessageSendAfterRequest.request = tLObject;
            delayedMessageSendAfterRequest.msgObjs = arrayList;
            delayedMessageSendAfterRequest.originalPaths = arrayList2;
            delayedMessageSendAfterRequest.delayedMessage = delayedMessage;
            delayedMessageSendAfterRequest.parentObjects = arrayList3;
            delayedMessageSendAfterRequest.scheduled = z;
            if (this.requests == null) {
                this.requests = new ArrayList<>();
            }
            this.requests.add(delayedMessageSendAfterRequest);
        }

        public void sendDelayedRequests() {
            ArrayList<DelayedMessageSendAfterRequest> arrayList = this.requests;
            if (arrayList != null) {
                int i = this.type;
                if (i == 4 || i == 0) {
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        DelayedMessageSendAfterRequest delayedMessageSendAfterRequest = this.requests.get(i2);
                        TLObject tLObject = delayedMessageSendAfterRequest.request;
                        if (tLObject instanceof TLRPC.TL_messages_sendEncryptedMultiMedia) {
                            SendMessagesHelper.this.getSecretChatHelper().performSendEncryptedRequest((TLRPC.TL_messages_sendEncryptedMultiMedia) delayedMessageSendAfterRequest.request, this);
                        } else if (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) {
                            SendMessagesHelper.this.lambda$performSendMessageRequestMulti$57((TLRPC.TL_messages_sendMultiMedia) tLObject, delayedMessageSendAfterRequest.msgObjs, delayedMessageSendAfterRequest.originalPaths, delayedMessageSendAfterRequest.parentObjects, delayedMessageSendAfterRequest.delayedMessage, delayedMessageSendAfterRequest.scheduled);
                        } else if ((tLObject instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject).media instanceof TLRPC.TL_inputMediaPaidMedia)) {
                            SendMessagesHelper.this.lambda$performSendMessageRequestMulti$57((TLRPC.TL_messages_sendMedia) tLObject, delayedMessageSendAfterRequest.msgObjs, delayedMessageSendAfterRequest.originalPaths, delayedMessageSendAfterRequest.parentObjects, delayedMessageSendAfterRequest.delayedMessage, delayedMessageSendAfterRequest.scheduled);
                        } else if ((tLObject instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject).media instanceof TLRPC.TL_inputMediaPoll)) {
                            SendMessagesHelper.this.lambda$performSendMessageRequestMulti$57((TLRPC.TL_messages_sendMedia) tLObject, delayedMessageSendAfterRequest.msgObjs, delayedMessageSendAfterRequest.originalPaths, delayedMessageSendAfterRequest.parentObjects, delayedMessageSendAfterRequest.delayedMessage, delayedMessageSendAfterRequest.scheduled);
                        } else {
                            SendMessagesHelper.this.performSendMessageRequest(tLObject, delayedMessageSendAfterRequest.msgObj, delayedMessageSendAfterRequest.originalPath, delayedMessageSendAfterRequest.delayedMessage, delayedMessageSendAfterRequest.parentObject, null, delayedMessageSendAfterRequest.scheduled);
                        }
                    }
                    this.requests = null;
                }
            }
        }

        public void markAsError() {
            if (this.type == 4) {
                for (int i = 0; i < this.messageObjects.size(); i++) {
                    MessageObject messageObject = this.messageObjects.get(i);
                    SendMessagesHelper.this.getMessagesStorage().markMessageAsSendError(messageObject.messageOwner, messageObject.scheduled ? 1 : 0);
                    TLRPC.Message message = messageObject.messageOwner;
                    message.send_state = 2;
                    message.errorAllowedPriceStars = 0L;
                    message.errorNewPriceStars = 0L;
                    SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageSendError, Integer.valueOf(messageObject.getId()));
                    SendMessagesHelper.this.processSentMessage(messageObject.getId());
                    SendMessagesHelper.this.removeFromUploadingMessages(messageObject.getId(), this.scheduled);
                }
                SendMessagesHelper.this.delayedMessages.remove("group_" + this.groupId);
            } else {
                MessagesStorage messagesStorage = SendMessagesHelper.this.getMessagesStorage();
                MessageObject messageObject2 = this.obj;
                messagesStorage.markMessageAsSendError(messageObject2.messageOwner, messageObject2.scheduled ? 1 : 0);
                TLRPC.Message message2 = this.obj.messageOwner;
                message2.send_state = 2;
                message2.errorAllowedPriceStars = 0L;
                message2.errorNewPriceStars = 0L;
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageSendError, Integer.valueOf(this.obj.getId()));
                SendMessagesHelper.this.processSentMessage(this.obj.getId());
                SendMessagesHelper.this.removeFromUploadingMessages(this.obj.getId(), this.scheduled);
            }
            sendDelayedRequests();
        }
    }

    public static SendMessagesHelper getInstance(int i) {
        SendMessagesHelper sendMessagesHelper;
        SendMessagesHelper sendMessagesHelper2 = Instance[i];
        if (sendMessagesHelper2 != null) {
            return sendMessagesHelper2;
        }
        synchronized (SendMessagesHelper.class) {
            try {
                sendMessagesHelper = Instance[i];
                if (sendMessagesHelper == null) {
                    SendMessagesHelper[] sendMessagesHelperArr = Instance;
                    SendMessagesHelper sendMessagesHelper3 = new SendMessagesHelper(i);
                    sendMessagesHelperArr[i] = sendMessagesHelper3;
                    sendMessagesHelper = sendMessagesHelper3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sendMessagesHelper;
    }

    public SendMessagesHelper(int i) {
        super(i);
        this.delayedMessages = new HashMap<>();
        this.unsentMessages = new SparseArray<>();
        this.sendingMessages = new SparseArray<>();
        this.editingMessages = new SparseArray<>();
        this.uploadMessages = new SparseArray<>();
        this.sendingMessagesIdDialogs = new LongSparseArray<>();
        this.uploadingMessagesIdDialogs = new LongSparseArray<>();
        this.waitingForLocation = new HashMap<>();
        this.waitingForCallback = new HashMap<>();
        this.waitingForCallbackMap = new HashMap<>();
        this.waitingForVote = new HashMap<>();
        this.voteSendTime = new LongSparseArray<>();
        this.importingHistoryFiles = new HashMap<>();
        this.importingHistoryMap = new LongSparseArray<>();
        this.importingStickersFiles = new HashMap<>();
        this.importingStickersMap = new HashMap<>();
        this.locationProvider = new LocationProvider(new LocationProvider.LocationProviderDelegate() { // from class: org.telegram.messenger.SendMessagesHelper.1
            public C28031() {
            }

            @Override // org.telegram.messenger.SendMessagesHelper.LocationProvider.LocationProviderDelegate
            public void onLocationAcquired(Location location) {
                SendMessagesHelper.this.sendLocation(location);
                SendMessagesHelper.this.waitingForLocation.clear();
            }

            @Override // org.telegram.messenger.SendMessagesHelper.LocationProvider.LocationProviderDelegate
            public void onUnableLocationAcquire() {
                SendMessagesHelper.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.wasUnableToFindCurrentLocation, new HashMap(SendMessagesHelper.this.waitingForLocation));
                SendMessagesHelper.this.waitingForLocation.clear();
            }
        });
        this.waitingForTodoUpdate = new HashMap<>();
        this.hooks = PluginsController.getInstance();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
    }

    public /* synthetic */ void lambda$new$0() {
        getNotificationCenter().addObserver(this, NotificationCenter.fileUploaded);
        getNotificationCenter().addObserver(this, NotificationCenter.fileUploadProgressChanged);
        getNotificationCenter().addObserver(this, NotificationCenter.fileUploadFailed);
        getNotificationCenter().addObserver(this, NotificationCenter.filePreparingStarted);
        getNotificationCenter().addObserver(this, NotificationCenter.fileNewChunkAvailable);
        getNotificationCenter().addObserver(this, NotificationCenter.filePreparingFailed);
        getNotificationCenter().addObserver(this, NotificationCenter.httpFileDidFailedLoad);
        getNotificationCenter().addObserver(this, NotificationCenter.httpFileDidLoad);
        getNotificationCenter().addObserver(this, NotificationCenter.fileLoaded);
        getNotificationCenter().addObserver(this, NotificationCenter.fileLoadFailed);
    }

    public void cleanup() {
        this.delayedMessages.clear();
        this.unsentMessages.clear();
        this.sendingMessages.clear();
        this.editingMessages.clear();
        this.sendingMessagesIdDialogs.clear();
        this.uploadMessages.clear();
        this.uploadingMessagesIdDialogs.clear();
        this.waitingForLocation.clear();
        this.waitingForCallback.clear();
        this.waitingForVote.clear();
        this.importingHistoryFiles.clear();
        this.importingHistoryMap.clear();
        this.importingStickersFiles.clear();
        this.importingStickersMap.clear();
        this.locationProvider.stop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:592:0x03e4  */
    /* JADX WARN: Removed duplicated region for block: B:807:0x08c4  */
    /* JADX WARN: Removed duplicated region for block: B:809:0x0902  */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v65 */
    /* JADX WARN: Type inference failed for: r1v66 */
    /* JADX WARN: Type inference failed for: r1v77 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19, types: [int] */
    /* JADX WARN: Type inference failed for: r9v30 */
    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void didReceivedNotification(int r31, int r32, java.lang.Object... r33) {
        /*
            Method dump skipped, instruction units count: 2453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.didReceivedNotification(int, int, java.lang.Object[]):void");
    }

    public /* synthetic */ void lambda$didReceivedNotification$2(final File file, final MessageObject messageObject, final DelayedMessage delayedMessage, final String str) {
        final TLRPC.TL_photo tL_photoGeneratePhotoSizes = generatePhotoSizes(file.toString(), null);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didReceivedNotification$1(tL_photoGeneratePhotoSizes, messageObject, file, delayedMessage, str);
            }
        });
    }

    public /* synthetic */ void lambda$didReceivedNotification$1(TLRPC.TL_photo tL_photo, MessageObject messageObject, File file, DelayedMessage delayedMessage, String str) {
        if (tL_photo != null) {
            TLRPC.Message message = messageObject.messageOwner;
            message.media.photo = tL_photo;
            message.attachPath = file.toString();
            ArrayList<TLRPC.Message> arrayList = new ArrayList<>();
            arrayList.add(messageObject.messageOwner);
            getMessagesStorage().putMessages(arrayList, false, true, false, 0, messageObject.scheduled ? 1 : 0, 0L);
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateMessageMedia, messageObject.messageOwner);
            ArrayList<TLRPC.PhotoSize> arrayList2 = tL_photo.sizes;
            delayedMessage.photoSize = arrayList2.get(arrayList2.size() - 1);
            delayedMessage.locationParent = tL_photo;
            delayedMessage.httpLocation = null;
            if (delayedMessage.type == 4) {
                delayedMessage.performMediaUpload = true;
                performSendDelayedMessage(delayedMessage, delayedMessage.messageObjects.indexOf(messageObject));
                return;
            } else {
                performSendDelayedMessage(delayedMessage);
                return;
            }
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1046e("can't load image " + str + " to file " + file.toString());
        }
        delayedMessage.markAsError();
    }

    public /* synthetic */ void lambda$didReceivedNotification$4(final DelayedMessage delayedMessage, final File file, final MessageObject messageObject) {
        final TLRPC.Document document = delayedMessage.obj.getDocument();
        if (document.thumbs.isEmpty() || (document.thumbs.get(0).location instanceof TLRPC.TL_fileLocationUnavailable)) {
            try {
                Bitmap bitmapLoadBitmap = ImageLoader.loadBitmap(file.getAbsolutePath(), null, 90.0f, 90.0f, true);
                if (bitmapLoadBitmap != null) {
                    document.thumbs.clear();
                    document.thumbs.add(ImageLoader.scaleAndSaveImage(bitmapLoadBitmap, 90.0f, 90.0f, 55, delayedMessage.sendEncryptedRequest != null));
                    bitmapLoadBitmap.recycle();
                }
            } catch (Exception e) {
                document.thumbs.clear();
                FileLog.m1048e(e);
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda88
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didReceivedNotification$3(delayedMessage, file, document, messageObject);
            }
        });
    }

    public /* synthetic */ void lambda$didReceivedNotification$3(DelayedMessage delayedMessage, File file, TLRPC.Document document, MessageObject messageObject) {
        delayedMessage.httpLocation = null;
        delayedMessage.obj.messageOwner.attachPath = file.toString();
        if (!document.thumbs.isEmpty()) {
            TLRPC.PhotoSize photoSize = document.thumbs.get(0);
            if (!(photoSize instanceof TLRPC.TL_photoStrippedSize)) {
                delayedMessage.photoSize = photoSize;
                delayedMessage.locationParent = document;
            }
        }
        ArrayList<TLRPC.Message> arrayList = new ArrayList<>();
        arrayList.add(messageObject.messageOwner);
        getMessagesStorage().putMessages(arrayList, false, true, false, 0, messageObject.scheduled ? 1 : 0, 0L);
        delayedMessage.performMediaUpload = true;
        performSendDelayedMessage(delayedMessage);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateMessageMedia, delayedMessage.obj.messageOwner);
    }

    private void revertEditingMessageObject(MessageObject messageObject) {
        messageObject.cancelEditing = true;
        TLRPC.Message message = messageObject.messageOwner;
        message.media = messageObject.previousMedia;
        message.message = messageObject.previousMessage;
        ArrayList<TLRPC.MessageEntity> arrayList = messageObject.previousMessageEntities;
        message.entities = arrayList;
        message.attachPath = messageObject.previousAttachPath;
        message.send_state = 0;
        if (arrayList != null) {
            message.flags |= 128;
        } else {
            message.flags &= -129;
        }
        messageObject.previousMedia = null;
        messageObject.previousMessage = null;
        messageObject.previousMessageEntities = null;
        messageObject.previousAttachPath = null;
        messageObject.videoEditedInfo = null;
        messageObject.type = -1;
        messageObject.setType();
        messageObject.caption = null;
        if (messageObject.type != 0) {
            messageObject.generateCaption();
        } else {
            messageObject.resetLayout();
        }
        ArrayList<TLRPC.Message> arrayList2 = new ArrayList<>();
        arrayList2.add(messageObject.messageOwner);
        getMessagesStorage().putMessages(arrayList2, false, true, false, 0, messageObject.scheduled ? 1 : 0, 0L);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(messageObject);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.replaceMessagesObjects, Long.valueOf(messageObject.getDialogId()), arrayList3);
    }

    public void cancelSendingMessage(MessageObject messageObject) {
        ArrayList<MessageObject> arrayList = new ArrayList<>();
        arrayList.add(messageObject);
        if (messageObject != null && messageObject.type == 29) {
            Iterator<Map.Entry<String, ArrayList<DelayedMessage>>> it = this.delayedMessages.entrySet().iterator();
            DelayedMessage delayedMessage = null;
            while (it.hasNext()) {
                ArrayList<DelayedMessage> value = it.next().getValue();
                for (int i = 0; i < value.size(); i++) {
                    DelayedMessage delayedMessage2 = value.get(i);
                    if (delayedMessage2.type == 4) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= delayedMessage2.messageObjects.size()) {
                                break;
                            }
                            if (delayedMessage2.messageObjects.get(i2).getId() == messageObject.getId()) {
                                delayedMessage = delayedMessage2;
                                break;
                            }
                            i2++;
                        }
                    }
                    if (delayedMessage != null) {
                        break;
                    }
                }
            }
            if (delayedMessage != null) {
                arrayList.clear();
                arrayList.addAll(delayedMessage.messageObjects);
            }
        }
        cancelSendingMessage(arrayList);
    }

    public void cancelSendingMessage(ArrayList<MessageObject> arrayList) {
        ArrayList<Integer> arrayList2;
        int i;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList<Integer> arrayList5 = new ArrayList<>();
        long j = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        int quickReplyId = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= arrayList.size()) {
                break;
            }
            MessageObject messageObject = arrayList.get(i2);
            int i4 = messageObject.scheduled ? 1 : i3;
            long dialogId = messageObject.getDialogId();
            arrayList5.add(Integer.valueOf(messageObject.getId()));
            if (messageObject.isQuickReply()) {
                quickReplyId = messageObject.getQuickReplyId();
            }
            TLRPC.Message messageRemoveFromSendingMessages = removeFromSendingMessages(messageObject.getId(), messageObject.scheduled);
            if (messageRemoveFromSendingMessages != null) {
                getConnectionsManager().cancelRequest(messageRemoveFromSendingMessages.reqId, true);
            }
            StarsController.getInstance(this.currentAccount).hidePaidMessageToast(messageObject);
            for (Map.Entry<String, ArrayList<DelayedMessage>> entry : this.delayedMessages.entrySet()) {
                ArrayList<DelayedMessage> value = entry.getValue();
                boolean z3 = z2;
                int i5 = 0;
                while (true) {
                    if (i5 >= value.size()) {
                        arrayList2 = arrayList5;
                        i = i2;
                        break;
                    }
                    DelayedMessage delayedMessage = value.get(i5);
                    arrayList2 = arrayList5;
                    i = i2;
                    if (delayedMessage.type == 4) {
                        MessageObject messageObject2 = null;
                        int i6 = 0;
                        while (true) {
                            if (i6 >= delayedMessage.messageObjects.size()) {
                                i6 = -1;
                                break;
                            }
                            messageObject2 = delayedMessage.messageObjects.get(i6);
                            if (messageObject2.getId() == messageObject.getId()) {
                                removeFromUploadingMessages(messageObject.getId(), messageObject.scheduled);
                                break;
                            }
                            i6++;
                        }
                        if (i6 >= 0) {
                            delayedMessage.messageObjects.remove(i6);
                            delayedMessage.messages.remove(i6);
                            delayedMessage.originalPaths.remove(i6);
                            if (!delayedMessage.parentObjects.isEmpty()) {
                                delayedMessage.parentObjects.remove(i6);
                            }
                            TLObject tLObject = delayedMessage.sendRequest;
                            if (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) {
                                ((TLRPC.TL_messages_sendMultiMedia) tLObject).multi_media.remove(i6);
                            } else if ((tLObject instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject).media instanceof TLRPC.TL_inputMediaPaidMedia)) {
                                ((TLRPC.TL_inputMediaPaidMedia) ((TLRPC.TL_messages_sendMedia) tLObject).media).extended_media.remove(i6);
                            } else if ((tLObject instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject).media instanceof TLRPC.TL_inputMediaPoll)) {
                                PollAttachedMediaPack.removeInputMedia((TLRPC.TL_inputMediaPoll) ((TLRPC.TL_messages_sendMedia) tLObject).media, delayedMessage.pollIndexes.get(i6).intValue());
                            } else {
                                TLRPC.TL_messages_sendEncryptedMultiMedia tL_messages_sendEncryptedMultiMedia = (TLRPC.TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
                                tL_messages_sendEncryptedMultiMedia.messages.remove(i6);
                                tL_messages_sendEncryptedMultiMedia.files.remove(i6);
                            }
                            MediaController.getInstance().cancelVideoConvert(messageObject);
                            String str = (String) delayedMessage.extraHashMap.get(messageObject2);
                            if (str != null) {
                                arrayList3.add(str);
                            }
                            if (delayedMessage.messageObjects.isEmpty()) {
                                delayedMessage.sendDelayedRequests();
                            } else {
                                if (delayedMessage.finalGroupMessage == messageObject.getId()) {
                                    MessageObject messageObject3 = delayedMessage.messageObjects.get(r4.size() - 1);
                                    delayedMessage.finalGroupMessage = messageObject3.getId();
                                    messageObject3.messageOwner.params.put("final", "1");
                                    TLRPC.TL_messages_messages tL_messages_messages = new TLRPC.TL_messages_messages();
                                    tL_messages_messages.messages.add(messageObject3.messageOwner);
                                    getMessagesStorage().putMessages((TLRPC.messages_Messages) tL_messages_messages, delayedMessage.peer, -2, 0, false, i4, 0L);
                                }
                                if (!arrayList4.contains(delayedMessage)) {
                                    arrayList4.add(delayedMessage);
                                }
                            }
                        }
                    } else if (delayedMessage.obj.getId() == messageObject.getId()) {
                        removeFromUploadingMessages(messageObject.getId(), messageObject.scheduled);
                        value.remove(i5);
                        delayedMessage.sendDelayedRequests();
                        MediaController.getInstance().cancelVideoConvert(delayedMessage.obj);
                        if (value.size() == 0) {
                            arrayList3.add(entry.getKey());
                            if (delayedMessage.sendEncryptedRequest != null) {
                                z = z3;
                            }
                        }
                    } else {
                        i5++;
                        arrayList5 = arrayList2;
                        i2 = i;
                    }
                }
                z2 = z3;
                arrayList5 = arrayList2;
                i2 = i;
            }
            i2++;
            j = dialogId;
            i3 = i4;
        }
        ArrayList<Integer> arrayList6 = arrayList5;
        for (int i7 = 0; i7 < arrayList3.size(); i7++) {
            String str2 = (String) arrayList3.get(i7);
            if (str2.startsWith("http")) {
                ImageLoader.getInstance().cancelLoadHttpFile(str2);
            } else {
                getFileLoader().cancelFileUpload(str2, z);
            }
            this.delayedMessages.remove(str2);
        }
        int size = arrayList4.size();
        for (int i8 = 0; i8 < size; i8++) {
            sendReadyToSendGroup((DelayedMessage) arrayList4.get(i8), false, true);
        }
        if (arrayList.size() == 1 && arrayList.get(0).isEditing() && arrayList.get(0).previousMedia != null) {
            revertEditingMessageObject(arrayList.get(0));
        } else {
            if (arrayList.size() == 1 && arrayList.get(0).isEditing() && arrayList.get(0).isPoll()) {
                return;
            }
            getMessagesController().deleteMessages(arrayList6, null, null, j, quickReplyId, false, (arrayList.isEmpty() || !arrayList.get(0).isQuickReply()) ? i3 != 0 ? 1 : 0 : 5);
        }
    }

    public boolean retrySendMessage(MessageObject messageObject, boolean z, long j) {
        if (messageObject.getId() >= 0) {
            if (messageObject.isEditing()) {
                editMessage(messageObject, null, null, null, null, null, null, true, messageObject.hasMediaSpoilers(), messageObject);
            }
            return false;
        }
        TLRPC.MessageAction messageAction = messageObject.messageOwner.action;
        if (messageAction instanceof TLRPC.TL_messageEncryptedAction) {
            TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(messageObject.getDialogId())));
            if (encryptedChat == null) {
                getMessagesStorage().markMessageAsSendError(messageObject.messageOwner, messageObject.scheduled ? 1 : 0);
                messageObject.messageOwner.send_state = 2;
                getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageSendError, Integer.valueOf(messageObject.getId()));
                processSentMessage(messageObject.getId());
                return false;
            }
            TLRPC.Message message = messageObject.messageOwner;
            if (message.random_id == 0) {
                message.random_id = getNextRandomId();
            }
            TLRPC.DecryptedMessageAction decryptedMessageAction = messageObject.messageOwner.action.encryptedAction;
            if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionSetMessageTTL) {
                getSecretChatHelper().sendTTLMessage(encryptedChat, messageObject.messageOwner);
            } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionDeleteMessages) {
                getSecretChatHelper().sendMessagesDeleteMessage(encryptedChat, null, messageObject.messageOwner);
            } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionFlushHistory) {
                getSecretChatHelper().sendClearHistoryMessage(encryptedChat, messageObject.messageOwner);
            } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionNotifyLayer) {
                getSecretChatHelper().sendNotifyLayerMessage(encryptedChat, messageObject.messageOwner);
            } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionReadMessages) {
                getSecretChatHelper().sendMessagesReadMessage(encryptedChat, null, messageObject.messageOwner);
            } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionScreenshotMessages) {
                getSecretChatHelper().sendScreenshotMessage(encryptedChat, null, messageObject.messageOwner);
            } else if (!(decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionTyping)) {
                if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionResend) {
                    getSecretChatHelper().sendResendMessage(encryptedChat, 0, 0, messageObject.messageOwner);
                } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionCommitKey) {
                    getSecretChatHelper().sendCommitKeyMessage(encryptedChat, messageObject.messageOwner);
                } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionAbortKey) {
                    getSecretChatHelper().sendAbortKeyMessage(encryptedChat, messageObject.messageOwner, 0L);
                } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionRequestKey) {
                    getSecretChatHelper().sendRequestKeyMessage(encryptedChat, messageObject.messageOwner);
                } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionAcceptKey) {
                    getSecretChatHelper().sendAcceptKeyMessage(encryptedChat, messageObject.messageOwner);
                } else if (decryptedMessageAction instanceof TLRPC.TL_decryptedMessageActionNoop) {
                    getSecretChatHelper().sendNoopMessage(encryptedChat, messageObject.messageOwner);
                }
            }
            return true;
        }
        if (messageAction instanceof TLRPC.TL_messageActionScreenshotTaken) {
            sendScreenshotMessage(getMessagesController().getUser(Long.valueOf(messageObject.getDialogId())), messageObject.getReplyMsgId(), messageObject.messageOwner);
        }
        if (z) {
            this.unsentMessages.put(messageObject.getId(), messageObject);
        }
        SendMessageParams sendMessageParamsM1078of = SendMessageParams.m1078of(messageObject);
        sendMessageParamsM1078of.payStars = j;
        sendMessage(sendMessageParamsM1078of);
        return true;
    }

    public void processSentMessage(int i) {
        int size = this.unsentMessages.size();
        this.unsentMessages.remove(i);
        if (size == 0 || this.unsentMessages.size() != 0) {
            return;
        }
        checkUnsentMessages();
    }

    public void processForwardFromMyName(MessageObject messageObject, long j, long j2, long j3, MessageSuggestionParams messageSuggestionParams) {
        if (messageObject == null) {
            return;
        }
        TLRPC.Message message = messageObject.messageOwner;
        TLRPC.MessageMedia messageMedia = message.media;
        ArrayList arrayList = null;
        map = null;
        map = null;
        HashMap map = null;
        arrayList = null;
        if (messageMedia != null && !(messageMedia instanceof TLRPC.TL_messageMediaEmpty) && !(messageMedia instanceof TLRPC.TL_messageMediaWebPage) && !(messageMedia instanceof TLRPC.TL_messageMediaGame) && !(messageMedia instanceof TLRPC.TL_messageMediaInvoice)) {
            if (DialogObject.isEncryptedDialog(j)) {
                TLRPC.Message message2 = messageObject.messageOwner;
                if (message2.peer_id != null) {
                    TLRPC.MessageMedia messageMedia2 = message2.media;
                    if ((messageMedia2.photo instanceof TLRPC.TL_photo) || (messageMedia2.document instanceof TLRPC.TL_document)) {
                        map = new HashMap();
                        map.put("parentObject", "sent_" + messageObject.messageOwner.peer_id.channel_id + "_" + messageObject.getId() + "_" + messageObject.getDialogId() + "_" + messageObject.type + "_" + messageObject.getSize());
                    }
                }
            }
            HashMap map2 = map;
            TLRPC.Message message3 = messageObject.messageOwner;
            TLRPC.MessageMedia messageMedia3 = message3.media;
            TLRPC.Photo photo = messageMedia3.photo;
            if (photo instanceof TLRPC.TL_photo) {
                SendMessageParams sendMessageParamsM1085of = SendMessageParams.m1085of((TLRPC.TL_photo) photo, null, j, messageObject.replyMessageObject, null, message3.message, message3.entities, null, map2, true, 0, 0, messageMedia3.ttl_seconds, messageObject, false);
                sendMessageParamsM1085of.payStars = j2;
                sendMessageParamsM1085of.monoForumPeer = j3;
                sendMessageParamsM1085of.suggestionParams = messageSuggestionParams;
                sendMessage(sendMessageParamsM1085of);
                return;
            }
            TLRPC.Document document = messageMedia3.document;
            if (document instanceof TLRPC.TL_document) {
                SendMessageParams sendMessageParamsM1080of = SendMessageParams.m1080of((TLRPC.TL_document) document, null, message3.attachPath, j, messageObject.replyMessageObject, null, message3.message, message3.entities, null, map2, true, 0, 0, messageMedia3.ttl_seconds, messageObject, null, false);
                sendMessageParamsM1080of.payStars = j2;
                sendMessageParamsM1080of.monoForumPeer = j3;
                sendMessageParamsM1080of.suggestionParams = messageSuggestionParams;
                sendMessage(sendMessageParamsM1080of);
                return;
            }
            if ((messageMedia3 instanceof TLRPC.TL_messageMediaVenue) || (messageMedia3 instanceof TLRPC.TL_messageMediaGeo)) {
                SendMessageParams sendMessageParamsM1079of = SendMessageParams.m1079of(messageMedia3, j, messageObject.replyMessageObject, (MessageObject) null, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, true, 0, 0);
                sendMessageParamsM1079of.payStars = j2;
                sendMessageParamsM1079of.monoForumPeer = j3;
                sendMessageParamsM1079of.suggestionParams = messageSuggestionParams;
                sendMessage(sendMessageParamsM1079of);
                return;
            }
            if (messageMedia3.phone_number != null) {
                TLRPC.TL_userContact_old2 tL_userContact_old2 = new TLRPC.TL_userContact_old2();
                TLRPC.MessageMedia messageMedia4 = messageObject.messageOwner.media;
                tL_userContact_old2.phone = messageMedia4.phone_number;
                tL_userContact_old2.first_name = messageMedia4.first_name;
                tL_userContact_old2.last_name = messageMedia4.last_name;
                tL_userContact_old2.f1407id = messageMedia4.user_id;
                SendMessageParams sendMessageParamsM1087of = SendMessageParams.m1087of((TLRPC.User) tL_userContact_old2, j, messageObject.replyMessageObject, (MessageObject) null, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, true, 0, 0);
                sendMessageParamsM1087of.monoForumPeer = j3;
                sendMessageParamsM1087of.suggestionParams = messageSuggestionParams;
                sendMessageParamsM1087of.payStars = j2;
                sendMessage(sendMessageParamsM1087of);
                return;
            }
            if (DialogObject.isEncryptedDialog(j)) {
                return;
            }
            ArrayList<MessageObject> arrayList2 = new ArrayList<>();
            arrayList2.add(messageObject);
            sendMessage(arrayList2, j, true, false, true, 0, 0, null, -1, j2, j3, messageSuggestionParams);
            return;
        }
        if (message.message != null) {
            TLRPC.WebPage webPage = messageMedia instanceof TLRPC.TL_messageMediaWebPage ? messageMedia.webpage : null;
            ArrayList<TLRPC.MessageEntity> arrayList3 = message.entities;
            if (arrayList3 != null && !arrayList3.isEmpty()) {
                arrayList = new ArrayList();
                for (int i = 0; i < messageObject.messageOwner.entities.size(); i++) {
                    TLRPC.MessageEntity messageEntity = messageObject.messageOwner.entities.get(i);
                    if ((messageEntity instanceof TLRPC.TL_messageEntityBold) || (messageEntity instanceof TLRPC.TL_messageEntityItalic) || (messageEntity instanceof TLRPC.TL_messageEntityPre) || (messageEntity instanceof TLRPC.TL_messageEntityCode) || (messageEntity instanceof TLRPC.TL_messageEntityTextUrl) || (messageEntity instanceof TLRPC.TL_messageEntitySpoiler) || (messageEntity instanceof TLRPC.TL_messageEntityCustomEmoji)) {
                        arrayList.add(messageEntity);
                    }
                }
            }
            SendMessageParams sendMessageParamsM1075of = SendMessageParams.m1075of(messageObject.messageOwner.message, j, messageObject.replyMessageObject, null, webPage, true, arrayList, null, null, true, 0, 0, null, false);
            sendMessageParamsM1075of.payStars = j2;
            sendMessageParamsM1075of.monoForumPeer = j3;
            sendMessageParamsM1075of.suggestionParams = messageSuggestionParams;
            sendMessage(sendMessageParamsM1075of);
            return;
        }
        if (DialogObject.isEncryptedDialog(j)) {
            ArrayList<MessageObject> arrayList4 = new ArrayList<>();
            arrayList4.add(messageObject);
            sendMessage(arrayList4, j, true, false, true, 0, 0, null, -1, j2, j3, messageSuggestionParams);
        }
    }

    public void sendScreenshotMessage(TLRPC.User user, int i, TLRPC.Message message) {
        TLRPC.Message tL_messageService = message;
        if (user == null || i == 0 || user.f1407id == getUserConfig().getClientUserId()) {
            return;
        }
        TLRPC.TL_messages_sendScreenshotNotification tL_messages_sendScreenshotNotification = new TLRPC.TL_messages_sendScreenshotNotification();
        TLRPC.TL_inputPeerUser tL_inputPeerUser = new TLRPC.TL_inputPeerUser();
        tL_messages_sendScreenshotNotification.peer = tL_inputPeerUser;
        tL_inputPeerUser.access_hash = user.access_hash;
        tL_inputPeerUser.user_id = user.f1407id;
        if (tL_messageService != null) {
            tL_messages_sendScreenshotNotification.reply_to = createReplyInput(i);
            tL_messages_sendScreenshotNotification.random_id = tL_messageService.random_id;
        } else {
            tL_messageService = new TLRPC.TL_messageService();
            tL_messageService.random_id = getNextRandomId();
            tL_messageService.dialog_id = user.f1407id;
            tL_messageService.unread = true;
            tL_messageService.out = true;
            int newMessageId = getUserConfig().getNewMessageId();
            tL_messageService.f1271id = newMessageId;
            tL_messageService.local_id = newMessageId;
            TLRPC.TL_peerUser tL_peerUser = new TLRPC.TL_peerUser();
            tL_messageService.from_id = tL_peerUser;
            tL_peerUser.user_id = getUserConfig().getClientUserId();
            tL_messageService.flags |= 264;
            TLRPC.TL_messageReplyHeader tL_messageReplyHeader = new TLRPC.TL_messageReplyHeader();
            tL_messageService.reply_to = tL_messageReplyHeader;
            tL_messageReplyHeader.flags |= 16;
            tL_messageReplyHeader.reply_to_msg_id = i;
            TLRPC.TL_peerUser tL_peerUser2 = new TLRPC.TL_peerUser();
            tL_messageService.peer_id = tL_peerUser2;
            tL_peerUser2.user_id = user.f1407id;
            tL_messageService.date = getConnectionsManager().getCurrentTime();
            tL_messageService.action = new TLRPC.TL_messageActionScreenshotTaken();
            getUserConfig().saveConfig(false);
        }
        tL_messages_sendScreenshotNotification.random_id = tL_messageService.random_id;
        MessageObject messageObject = new MessageObject(this.currentAccount, tL_messageService, false, true);
        messageObject.messageOwner.send_state = 1;
        messageObject.wasJustSent = true;
        ArrayList<MessageObject> arrayList = new ArrayList<>();
        arrayList.add(messageObject);
        getMessagesController().updateInterfaceWithMessages(tL_messageService.dialog_id, arrayList, 0);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsNeedReload, new Object[0]);
        ArrayList<TLRPC.Message> arrayList2 = new ArrayList<>();
        arrayList2.add(tL_messageService);
        getMessagesStorage().putMessages(arrayList2, false, true, false, 0, false, 0, 0L);
        performSendMessageRequest(tL_messages_sendScreenshotNotification, messageObject, null, null, null, null, false);
    }

    public void sendSticker(TLRPC.Document document, String str, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, MessageObject.SendAnimationData sendAnimationData, boolean z, int i, int i2, boolean z2, Object obj, String str2, int i3, long j2, long j3, MessageSuggestionParams messageSuggestionParams) {
        sendSticker(document, str, j, null, null, messageObject, messageObject2, storyItem, replyQuote, sendAnimationData, z, i, i2, z2, obj, str2, i3, j2, j3, messageSuggestionParams);
    }

    public void sendSticker(TLRPC.Document document, String str, long j, CharSequence charSequence, VideoEditedInfo videoEditedInfo, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, MessageObject.SendAnimationData sendAnimationData, boolean z, int i, int i2, boolean z2, Object obj, String str2, int i3, long j2, long j3, MessageSuggestionParams messageSuggestionParams) {
        sendSticker(document, str, j, charSequence, videoEditedInfo, messageObject, messageObject2, storyItem, replyQuote, sendAnimationData, z, i, i2, z2, obj, str2, i3, j2, j3, messageSuggestionParams, false);
    }

    public void sendSticker(TLRPC.Document document, String str, final long j, final CharSequence charSequence, final VideoEditedInfo videoEditedInfo, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final MessageObject.SendAnimationData sendAnimationData, final boolean z, final int i, final int i2, boolean z2, final Object obj, final String str2, final int i3, final long j2, final long j3, final MessageSuggestionParams messageSuggestionParams, final boolean z3) {
        final TLRPC.Document tL_document_layer82;
        HashMap map;
        TLRPC.PhotoSize tL_photoStrippedSize;
        byte[] bArr;
        if (document == null) {
            return;
        }
        if (DialogObject.isEncryptedDialog(j)) {
            if (getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j))) == null) {
                return;
            }
            tL_document_layer82 = new TLRPC.TL_document_layer82();
            tL_document_layer82.f1253id = document.f1253id;
            tL_document_layer82.access_hash = document.access_hash;
            tL_document_layer82.date = document.date;
            tL_document_layer82.mime_type = document.mime_type;
            byte[] bArr2 = document.file_reference;
            tL_document_layer82.file_reference = bArr2;
            if (bArr2 == null) {
                tL_document_layer82.file_reference = new byte[0];
            }
            tL_document_layer82.size = document.size;
            tL_document_layer82.dc_id = document.dc_id;
            tL_document_layer82.attributes = new ArrayList<>();
            for (int i4 = 0; i4 < document.attributes.size(); i4++) {
                TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i4);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                    TLRPC.TL_documentAttributeVideo_layer159 tL_documentAttributeVideo_layer159 = new TLRPC.TL_documentAttributeVideo_layer159();
                    tL_documentAttributeVideo_layer159.flags = documentAttribute.flags;
                    tL_documentAttributeVideo_layer159.round_message = documentAttribute.round_message;
                    tL_documentAttributeVideo_layer159.supports_streaming = documentAttribute.supports_streaming;
                    tL_documentAttributeVideo_layer159.duration = documentAttribute.duration;
                    tL_documentAttributeVideo_layer159.f1256w = documentAttribute.f1256w;
                    tL_documentAttributeVideo_layer159.f1255h = documentAttribute.f1255h;
                    tL_document_layer82.attributes.add(tL_documentAttributeVideo_layer159);
                } else {
                    tL_document_layer82.attributes.add(documentAttribute);
                }
            }
            if (tL_document_layer82.mime_type == null) {
                tL_document_layer82.mime_type = _UrlKt.FRAGMENT_ENCODE_SET;
            }
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 10);
            if ((closestPhotoSizeWithSize instanceof TLRPC.TL_photoSize) || (closestPhotoSizeWithSize instanceof TLRPC.TL_photoSizeProgressive) || (closestPhotoSizeWithSize instanceof TLRPC.TL_photoStrippedSize)) {
                File pathToAttach = FileLoader.getInstance(this.currentAccount).getPathToAttach(closestPhotoSizeWithSize, true);
                if ((closestPhotoSizeWithSize instanceof TLRPC.TL_photoStrippedSize) || pathToAttach.exists()) {
                    try {
                        if (closestPhotoSizeWithSize instanceof TLRPC.TL_photoStrippedSize) {
                            tL_photoStrippedSize = new TLRPC.TL_photoStrippedSize();
                            bArr = closestPhotoSizeWithSize.bytes;
                        } else {
                            TLRPC.TL_photoCachedSize tL_photoCachedSize = new TLRPC.TL_photoCachedSize();
                            pathToAttach.length();
                            byte[] bArr3 = new byte[(int) pathToAttach.length()];
                            new RandomAccessFile(pathToAttach, "r").readFully(bArr3);
                            tL_photoStrippedSize = tL_photoCachedSize;
                            bArr = bArr3;
                        }
                        TLRPC.TL_fileLocation_layer82 tL_fileLocation_layer82 = new TLRPC.TL_fileLocation_layer82();
                        TLRPC.FileLocation fileLocation = closestPhotoSizeWithSize.location;
                        tL_fileLocation_layer82.dc_id = fileLocation.dc_id;
                        tL_fileLocation_layer82.volume_id = fileLocation.volume_id;
                        tL_fileLocation_layer82.local_id = fileLocation.local_id;
                        tL_fileLocation_layer82.secret = fileLocation.secret;
                        tL_photoStrippedSize.location = tL_fileLocation_layer82;
                        tL_photoStrippedSize.size = closestPhotoSizeWithSize.size;
                        tL_photoStrippedSize.f1278w = closestPhotoSizeWithSize.f1278w;
                        tL_photoStrippedSize.f1277h = closestPhotoSizeWithSize.f1277h;
                        tL_photoStrippedSize.type = closestPhotoSizeWithSize.type;
                        tL_photoStrippedSize.bytes = bArr;
                        tL_document_layer82.thumbs.add(tL_photoStrippedSize);
                        tL_document_layer82.flags |= 1;
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
            }
            if (tL_document_layer82.thumbs.isEmpty()) {
                TLRPC.TL_photoSizeEmpty tL_photoSizeEmpty = new TLRPC.TL_photoSizeEmpty();
                tL_photoSizeEmpty.type = "s";
                tL_document_layer82.thumbs.add(tL_photoSizeEmpty);
            }
        } else {
            tL_document_layer82 = document;
        }
        if (MessageObject.isGifDocument(tL_document_layer82)) {
            mediaSendQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendSticker$6(tL_document_layer82, videoEditedInfo, j, messageObject, messageObject2, z, i, i2, obj, sendAnimationData, storyItem, replyQuote, str2, i3, j2, j3, messageSuggestionParams, charSequence, z3);
                }
            });
            return;
        }
        if (TextUtils.isEmpty(str)) {
            map = null;
        } else {
            map = new HashMap();
            map.put("query", str);
        }
        SendMessageParams sendMessageParamsM1080of = SendMessageParams.m1080of((TLRPC.TL_document) tL_document_layer82, null, null, j, messageObject, messageObject2, null, null, null, map, z, i, i2, 0, obj, sendAnimationData, z2);
        sendMessageParamsM1080of.replyToStoryItem = storyItem;
        sendMessageParamsM1080of.replyQuote = replyQuote;
        sendMessageParamsM1080of.quick_reply_shortcut = str2;
        sendMessageParamsM1080of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1080of.payStars = j2;
        sendMessageParamsM1080of.monoForumPeer = j3;
        sendMessageParamsM1080of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1080of.invert_media = z3;
        sendMessage(sendMessageParamsM1080of);
    }

    public /* synthetic */ void lambda$sendSticker$6(final TLRPC.Document document, final VideoEditedInfo videoEditedInfo, final long j, final MessageObject messageObject, final MessageObject messageObject2, final boolean z, final int i, final int i2, final Object obj, final MessageObject.SendAnimationData sendAnimationData, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final String str, final int i3, final long j2, final long j3, final MessageSuggestionParams messageSuggestionParams, final CharSequence charSequence, final boolean z2) {
        String str2;
        final Bitmap[] bitmapArr = new Bitmap[1];
        String key = ImageLocation.getForDocument(document).getKey(null, null, false);
        if ("video/mp4".equals(document.mime_type)) {
            str2 = ".mp4";
        } else if ("video/x-matroska".equals(document.mime_type)) {
            str2 = ".mkv";
        } else {
            str2 = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        File file = new File(FileLoader.getDirectory(3), key + str2);
        if (!file.exists()) {
            file = new File(FileLoader.getDirectory(2), key + str2);
        }
        ensureMediaThumbExists(getAccountInstance(), false, document, file.getAbsolutePath(), null, 0L);
        final String[] strArr = {getKeyForPhotoSize(getAccountInstance(), FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 320), bitmapArr, true, true)};
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendSticker$5(bitmapArr, strArr, document, videoEditedInfo, j, messageObject, messageObject2, z, i, i2, obj, sendAnimationData, storyItem, replyQuote, str, i3, j2, j3, messageSuggestionParams, charSequence, z2);
            }
        });
    }

    public /* synthetic */ void lambda$sendSticker$5(Bitmap[] bitmapArr, String[] strArr, TLRPC.Document document, VideoEditedInfo videoEditedInfo, long j, MessageObject messageObject, MessageObject messageObject2, boolean z, int i, int i2, Object obj, MessageObject.SendAnimationData sendAnimationData, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, String str, int i3, long j2, long j3, MessageSuggestionParams messageSuggestionParams, CharSequence charSequence, boolean z2) {
        if (bitmapArr[0] != null && strArr[0] != null) {
            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmapArr[0]), strArr[0], false);
        }
        SendMessageParams sendMessageParamsM1080of = SendMessageParams.m1080of((TLRPC.TL_document) document, videoEditedInfo, null, j, messageObject, messageObject2, null, null, null, null, z, i, i2, 0, obj, sendAnimationData, false);
        sendMessageParamsM1080of.replyToStoryItem = storyItem;
        sendMessageParamsM1080of.replyQuote = replyQuote;
        sendMessageParamsM1080of.quick_reply_shortcut = str;
        sendMessageParamsM1080of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1080of.payStars = j2;
        sendMessageParamsM1080of.monoForumPeer = j3;
        sendMessageParamsM1080of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1080of.caption = charSequence != null ? charSequence.toString() : null;
        sendMessageParamsM1080of.invert_media = z2;
        sendMessage(sendMessageParamsM1080of);
    }

    public int sendMessage(ArrayList<MessageObject> arrayList, long j, boolean z, boolean z2, boolean z3, int i, long j2) {
        return sendMessage(arrayList, j, z, z2, z3, i, null, -1, j2);
    }

    public int sendMessage(ArrayList<MessageObject> arrayList, long j, boolean z, boolean z2, boolean z3, int i, MessageObject messageObject, int i2, long j2) {
        return sendMessage(arrayList, j, z, z2, z3, i, 0, messageObject, i2, j2, 0L, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:700:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:721:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:748:0x04be  */
    /* JADX WARN: Removed duplicated region for block: B:752:0x04de  */
    /* JADX WARN: Removed duplicated region for block: B:819:0x067e  */
    /* JADX WARN: Removed duplicated region for block: B:822:0x0688  */
    /* JADX WARN: Removed duplicated region for block: B:825:0x06a1  */
    /* JADX WARN: Removed duplicated region for block: B:831:0x06cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:837:0x06ee  */
    /* JADX WARN: Removed duplicated region for block: B:839:0x070a  */
    /* JADX WARN: Removed duplicated region for block: B:840:0x071c  */
    /* JADX WARN: Removed duplicated region for block: B:845:0x0737  */
    /* JADX WARN: Removed duplicated region for block: B:848:0x075a  */
    /* JADX WARN: Removed duplicated region for block: B:849:0x075d  */
    /* JADX WARN: Removed duplicated region for block: B:855:0x077b  */
    /* JADX WARN: Removed duplicated region for block: B:864:0x07a2  */
    /* JADX WARN: Removed duplicated region for block: B:868:0x07b3  */
    /* JADX WARN: Removed duplicated region for block: B:877:0x07cc  */
    /* JADX WARN: Removed duplicated region for block: B:881:0x07e4  */
    /* JADX WARN: Removed duplicated region for block: B:884:0x07f6  */
    /* JADX WARN: Removed duplicated region for block: B:886:0x0808  */
    /* JADX WARN: Removed duplicated region for block: B:889:0x0812  */
    /* JADX WARN: Removed duplicated region for block: B:890:0x0827  */
    /* JADX WARN: Removed duplicated region for block: B:892:0x082b  */
    /* JADX WARN: Removed duplicated region for block: B:895:0x0843  */
    /* JADX WARN: Removed duplicated region for block: B:896:0x0845  */
    /* JADX WARN: Removed duplicated region for block: B:899:0x086a  */
    /* JADX WARN: Removed duplicated region for block: B:906:0x0898  */
    /* JADX WARN: Removed duplicated region for block: B:908:0x089c  */
    /* JADX WARN: Removed duplicated region for block: B:909:0x089e  */
    /* JADX WARN: Removed duplicated region for block: B:912:0x08a7  */
    /* JADX WARN: Removed duplicated region for block: B:919:0x0908  */
    /* JADX WARN: Removed duplicated region for block: B:922:0x0913  */
    /* JADX WARN: Removed duplicated region for block: B:930:0x0956  */
    /* JADX WARN: Removed duplicated region for block: B:933:0x0963  */
    /* JADX WARN: Removed duplicated region for block: B:934:0x0966  */
    /* JADX WARN: Removed duplicated region for block: B:937:0x097d  */
    /* JADX WARN: Removed duplicated region for block: B:938:0x097f  */
    /* JADX WARN: Removed duplicated region for block: B:941:0x09a1  */
    /* JADX WARN: Removed duplicated region for block: B:945:0x09c0  */
    /* JADX WARN: Type inference failed for: r10v33 */
    /* JADX WARN: Type inference failed for: r10v34, types: [int] */
    /* JADX WARN: Type inference failed for: r10v38 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v21, types: [int] */
    /* JADX WARN: Type inference failed for: r13v26 */
    /* JADX WARN: Type inference failed for: r14v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v27 */
    /* JADX WARN: Type inference failed for: r14v28 */
    /* JADX WARN: Type inference failed for: r14v31 */
    /* JADX WARN: Type inference failed for: r14v32 */
    /* JADX WARN: Type inference failed for: r14v38 */
    /* JADX WARN: Type inference failed for: r14v39 */
    /* JADX WARN: Type inference failed for: r14v48 */
    /* JADX WARN: Type inference failed for: r14v49 */
    /* JADX WARN: Type inference failed for: r14v54 */
    /* JADX WARN: Type inference failed for: r14v55 */
    /* JADX WARN: Type inference failed for: r14v56 */
    /* JADX WARN: Type inference failed for: r14v57 */
    /* JADX WARN: Type inference failed for: r14v58 */
    /* JADX WARN: Type inference failed for: r2v111 */
    /* JADX WARN: Type inference failed for: r2v112 */
    /* JADX WARN: Type inference failed for: r2v113 */
    /* JADX WARN: Type inference failed for: r2v45 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v51 */
    /* JADX WARN: Type inference failed for: r35v1 */
    /* JADX WARN: Type inference failed for: r35v11 */
    /* JADX WARN: Type inference failed for: r35v12 */
    /* JADX WARN: Type inference failed for: r35v2, types: [int] */
    /* JADX WARN: Type inference failed for: r35v3 */
    /* JADX WARN: Type inference failed for: r35v4 */
    /* JADX WARN: Type inference failed for: r35v5 */
    /* JADX WARN: Type inference failed for: r35v6 */
    /* JADX WARN: Type inference failed for: r35v7 */
    /* JADX WARN: Type inference failed for: r35v8 */
    /* JADX WARN: Type inference failed for: r35v9 */
    /* JADX WARN: Type inference failed for: r48v18 */
    /* JADX WARN: Type inference failed for: r48v7 */
    /* JADX WARN: Type inference failed for: r48v8, types: [int] */
    /* JADX WARN: Type inference failed for: r48v9 */
    /* JADX WARN: Type inference failed for: r50v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int sendMessage(final java.util.ArrayList<org.telegram.messenger.MessageObject> r61, final long r62, final boolean r64, final boolean r65, final boolean r66, final int r67, final int r68, final org.telegram.messenger.MessageObject r69, final int r70, long r71, final long r73, final org.telegram.messenger.MessageSuggestionParams r75) {
        /*
            Method dump skipped, instruction units count: 3117
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.sendMessage(java.util.ArrayList, long, boolean, boolean, boolean, int, int, org.telegram.messenger.MessageObject, int, long, long, org.telegram.messenger.MessageSuggestionParams):int");
    }

    public /* synthetic */ void lambda$sendMessage$7(ArrayList arrayList, long j, boolean z, boolean z2, boolean z3, int i, int i2, MessageObject messageObject, int i3, long j2, MessageSuggestionParams messageSuggestionParams, Long l) {
        sendMessage(arrayList, j, z, z2, z3, i, i2, messageObject, i3, l.longValue(), j2, messageSuggestionParams);
    }

    public /* synthetic */ void lambda$sendMessage$17(final TLRPC.TL_messages_forwardMessages tL_messages_forwardMessages, final long j, final int i, final boolean z, final boolean z2, final LongSparseArray longSparseArray, final ArrayList arrayList, final ArrayList arrayList2, final MessageObject messageObject, final TLRPC.Peer peer) {
        getConnectionsManager().sendRequest(tL_messages_forwardMessages, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda22
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendMessage$16(j, i, z, z2, longSparseArray, arrayList, arrayList2, messageObject, peer, tL_messages_forwardMessages, tLObject, tL_error);
            }
        }, 68);
    }

    /* JADX WARN: Removed duplicated region for block: B:166:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$sendMessage$16(final long r23, final int r25, boolean r26, boolean r27, androidx.collection.LongSparseArray r28, java.util.ArrayList r29, final java.util.ArrayList r30, final org.telegram.messenger.MessageObject r31, final org.telegram.tgnet.TLRPC.Peer r32, final org.telegram.tgnet.TLRPC.TL_messages_forwardMessages r33, org.telegram.tgnet.TLObject r34, final org.telegram.tgnet.TLRPC.TL_error r35) {
        /*
            Method dump skipped, instruction units count: 672
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$sendMessage$16(long, int, boolean, boolean, androidx.collection.LongSparseArray, java.util.ArrayList, java.util.ArrayList, org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$Peer, org.telegram.tgnet.TLRPC$TL_messages_forwardMessages, org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$TL_error):void");
    }

    public /* synthetic */ void lambda$sendMessage$10(final ArrayList arrayList, final int i, final int i2, final TLRPC.Message message, final int i3, final TLRPC.Message message2, final MessageObject messageObject, final int i4) {
        getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendMessage$9(arrayList, i, i2, message, i3, message2, messageObject, i4);
            }
        });
    }

    public /* synthetic */ void lambda$sendMessage$9(ArrayList arrayList, final int i, final int i2, final TLRPC.Message message, final int i3, final TLRPC.Message message2, final MessageObject messageObject, final int i4) {
        getMessagesStorage().putMessages((ArrayList<TLRPC.Message>) arrayList, true, false, false, 0, i, 0L);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda86
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendMessage$8(i2, message, i3, i, message2, messageObject, i4);
            }
        });
    }

    public /* synthetic */ void lambda$sendMessage$8(int i, TLRPC.Message message, int i2, int i3, TLRPC.Message message2, MessageObject messageObject, int i4) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(i));
        boolean z = true;
        getMessagesController().deleteMessages(arrayList, null, null, message.dialog_id, false, i2, false, 0L, null, 0, i3 == 1, message2.f1271id);
        ArrayList<MessageObject> arrayList2 = new ArrayList<>();
        arrayList2.add(new MessageObject(messageObject.currentAccount, messageObject.messageOwner, true, true));
        getMessagesController().updateInterfaceWithMessages(message.dialog_id, arrayList2, i3);
        getMediaDataController().increasePeerRaiting(message.dialog_id);
        processSentMessage(i);
        if (i4 == 0) {
            z = false;
        }
        removeFromSendingMessages(i, z);
    }

    public /* synthetic */ void lambda$sendMessage$12(final int i, final TLRPC.Message message, final TLRPC.Message message2, TLRPC.Peer peer, final int i2, ArrayList arrayList, final long j, final int i3) {
        int i4 = i != 0 ? 1 : 0;
        if (message.quick_reply_shortcut_id != 0 || message.quick_reply_shortcut != null) {
            i4 = 5;
        }
        int i5 = i4;
        getMessagesStorage().updateMessageStateAndId(message2.random_id, MessageObject.getPeerId(peer), Integer.valueOf(i2), message2.f1271id, 0, false, i != 0 ? 1 : 0, message.quick_reply_shortcut_id);
        getMessagesStorage().putMessages((ArrayList<TLRPC.Message>) arrayList, true, false, false, 0, i5, message.quick_reply_shortcut_id);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendMessage$11(message2, j, i2, message, i3, i);
            }
        });
    }

    public /* synthetic */ void lambda$sendMessage$11(TLRPC.Message message, long j, int i, TLRPC.Message message2, int i2, int i3) {
        message.send_state = 0;
        getMediaDataController().increasePeerRaiting(j);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer, Integer.valueOf(i), Integer.valueOf(message2.f1271id), message2, Long.valueOf(j), 0L, Integer.valueOf(i2), Boolean.valueOf(i3 != 0));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer2, Integer.valueOf(i), Integer.valueOf(message2.f1271id), message2, Long.valueOf(j), 0L, Integer.valueOf(i2), Boolean.valueOf(i3 != 0));
        processSentMessage(i);
        removeFromSendingMessages(i, i3 != 0);
    }

    public /* synthetic */ void lambda$sendMessage$13(TLRPC.TL_error tL_error, TLRPC.TL_messages_forwardMessages tL_messages_forwardMessages) {
        AlertsCreator.processError(this.currentAccount, tL_error, null, tL_messages_forwardMessages, new Object[0]);
    }

    public /* synthetic */ void lambda$sendMessage$14(TLRPC.Message message, int i) {
        message.send_state = 2;
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageSendError, Integer.valueOf(message.f1271id));
        processSentMessage(message.f1271id);
        removeFromSendingMessages(message.f1271id, i != 0);
    }

    public /* synthetic */ void lambda$sendMessage$15(ArrayList arrayList) {
        StarsController.getInstance(this.currentAccount).showPriceChangedToast(arrayList);
    }

    public /* synthetic */ void lambda$sendMessage$18(TLRPC.TL_messages_forwardMessages tL_messages_forwardMessages, ArrayList arrayList, Runnable runnable) {
        if (BotForumHelper.getInstance(this.currentAccount).beforeSendingFinalRequest(tL_messages_forwardMessages, arrayList, runnable)) {
            runnable.run();
        }
    }

    public static int canSendMessageToChat(TLRPC.Chat chat, MessageObject messageObject) {
        boolean zCanSendStickers = ChatObject.canSendStickers(chat);
        boolean zCanSendPhoto = ChatObject.canSendPhoto(chat);
        boolean zCanSendVideo = ChatObject.canSendVideo(chat);
        boolean zCanSendDocument = ChatObject.canSendDocument(chat);
        ChatObject.canSendEmbed(chat);
        boolean zCanSendPolls = ChatObject.canSendPolls(chat);
        boolean zCanSendRoundVideo = ChatObject.canSendRoundVideo(chat);
        boolean zCanSendVoice = ChatObject.canSendVoice(chat);
        boolean zCanSendMusic = ChatObject.canSendMusic(chat);
        boolean z = messageObject.isSticker() || messageObject.isAnimatedSticker() || messageObject.isGif() || messageObject.isGame();
        if (!zCanSendStickers && z) {
            return ChatObject.isActionBannedByDefault(chat, 8) ? 4 : 1;
        }
        if (!zCanSendPhoto && (messageObject.messageOwner.media instanceof TLRPC.TL_messageMediaPhoto) && !messageObject.isVideo() && !z) {
            return ChatObject.isActionBannedByDefault(chat, 16) ? 10 : 12;
        }
        if (!zCanSendMusic && messageObject.isMusic()) {
            return ChatObject.isActionBannedByDefault(chat, 18) ? 19 : 20;
        }
        if (!zCanSendVideo && messageObject.isVideo() && !z) {
            return ChatObject.isActionBannedByDefault(chat, 17) ? 9 : 11;
        }
        if (!zCanSendPolls && (messageObject.messageOwner.media instanceof TLRPC.TL_messageMediaPoll)) {
            return ChatObject.isActionBannedByDefault(chat, 10) ? 6 : 3;
        }
        if (!zCanSendPolls && (messageObject.messageOwner.media instanceof TLRPC.TL_messageMediaToDo)) {
            return ChatObject.isActionBannedByDefault(chat, 10) ? 21 : 22;
        }
        if (!zCanSendVoice && MessageObject.isVoiceMessage(messageObject.messageOwner)) {
            return ChatObject.isActionBannedByDefault(chat, 20) ? 13 : 14;
        }
        if (!zCanSendRoundVideo && MessageObject.isRoundVideoMessage(messageObject.messageOwner)) {
            return ChatObject.isActionBannedByDefault(chat, 21) ? 15 : 16;
        }
        if (zCanSendDocument || !(messageObject.messageOwner.media instanceof TLRPC.TL_messageMediaDocument) || z) {
            return 0;
        }
        return ChatObject.isActionBannedByDefault(chat, 19) ? 17 : 18;
    }

    private void writePreviousMessageData(TLRPC.Message message, SerializedData serializedData) {
        TLRPC.MessageMedia messageMedia = message.media;
        if (messageMedia == null) {
            new TLRPC.TL_messageMediaEmpty().serializeToStream(serializedData);
        } else {
            messageMedia.serializeToStream(serializedData);
        }
        String str = message.message;
        String str2 = _UrlKt.FRAGMENT_ENCODE_SET;
        if (str == null) {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        serializedData.writeString(str);
        String str3 = message.attachPath;
        if (str3 != null) {
            str2 = str3;
        }
        serializedData.writeString(str2);
        int size = message.entities.size();
        serializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            message.entities.get(i).serializeToStream(serializedData);
        }
    }

    public void editMessage(MessageObject messageObject, TLRPC.TL_photo tL_photo, VideoEditedInfo videoEditedInfo, TLRPC.TL_document tL_document, String str, TLRPC.PhotoSize photoSize, HashMap<String, String> map, boolean z, boolean z2, Object obj) {
        editMessage(messageObject, null, tL_photo, videoEditedInfo, tL_document, str, photoSize, map, z, z2, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:602:0x03ba A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:444:0x0034, B:446:0x003e, B:448:0x0050, B:458:0x0068, B:461:0x0072, B:464:0x0077, B:466:0x007b, B:481:0x00a8, B:484:0x00ae, B:486:0x00b4, B:489:0x00be, B:556:0x02dc, B:558:0x02e0, B:559:0x02e4, B:567:0x02fc, B:572:0x0305, B:574:0x0309, B:578:0x031d, B:582:0x0332, B:584:0x0336, B:586:0x033c, B:600:0x0387, B:602:0x03ba, B:604:0x03c2, B:607:0x03c7, B:608:0x03ce, B:612:0x03d6, B:615:0x03f9, B:617:0x0401, B:633:0x0424, B:749:0x06c2, B:751:0x06c6, B:757:0x06d1, B:759:0x06ec, B:798:0x07af, B:805:0x07d2, B:812:0x07e0, B:816:0x07f6, B:820:0x07fd, B:837:0x0839, B:844:0x0848, B:848:0x085e, B:852:0x0866, B:856:0x087c, B:761:0x06f1, B:763:0x070e, B:767:0x071e, B:769:0x0722, B:771:0x0732, B:772:0x073a, B:774:0x0741, B:775:0x074c, B:777:0x0750, B:779:0x0768, B:786:0x0793, B:781:0x0770, B:783:0x0784, B:785:0x078a, B:788:0x0799, B:764:0x0717, B:766:0x071b, B:638:0x0434, B:640:0x0438, B:643:0x043e, B:649:0x0450, B:651:0x0454, B:645:0x0444, B:647:0x0448, B:656:0x0478, B:658:0x0481, B:660:0x0489, B:662:0x049c, B:663:0x04b3, B:665:0x04c1, B:672:0x04ee, B:674:0x0506, B:676:0x050c, B:678:0x0512, B:679:0x0515, B:668:0x04cc, B:670:0x04e6, B:671:0x04eb, B:684:0x0537, B:686:0x0540, B:688:0x0548, B:690:0x0559, B:691:0x0572, B:693:0x0582, B:696:0x0592, B:700:0x059d, B:702:0x05a3, B:704:0x05ac, B:711:0x05da, B:713:0x05f2, B:715:0x05ff, B:716:0x0603, B:718:0x060b, B:724:0x0623, B:720:0x061a, B:722:0x061e, B:707:0x05b7, B:709:0x05d1, B:710:0x05d6, B:727:0x0633, B:734:0x066f, B:737:0x0685, B:739:0x0689, B:741:0x0696, B:742:0x069a, B:730:0x064d, B:732:0x0667, B:733:0x066c, B:745:0x06a7, B:747:0x06b1, B:579:0x0326, B:581:0x032c, B:589:0x0344, B:598:0x0380, B:590:0x034f, B:592:0x0363, B:594:0x0369, B:595:0x0372, B:597:0x037a, B:564:0x02f1, B:566:0x02f9, B:467:0x0083, B:469:0x0087, B:475:0x0097, B:476:0x009a, B:490:0x00d0, B:492:0x00e4, B:493:0x00e9, B:496:0x0117, B:498:0x013d, B:500:0x0150, B:502:0x0156, B:504:0x015c, B:555:0x02cb, B:505:0x0162, B:508:0x019a, B:515:0x01bc, B:516:0x01c3, B:518:0x01cc, B:520:0x01de, B:521:0x0202, B:528:0x0215, B:530:0x0228, B:532:0x022e, B:534:0x0234, B:535:0x0237, B:538:0x025c, B:545:0x027c, B:546:0x0283, B:548:0x0289, B:550:0x029b, B:551:0x02bf), top: B:861:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:610:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:611:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:618:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:624:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:751:0x06c6 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:444:0x0034, B:446:0x003e, B:448:0x0050, B:458:0x0068, B:461:0x0072, B:464:0x0077, B:466:0x007b, B:481:0x00a8, B:484:0x00ae, B:486:0x00b4, B:489:0x00be, B:556:0x02dc, B:558:0x02e0, B:559:0x02e4, B:567:0x02fc, B:572:0x0305, B:574:0x0309, B:578:0x031d, B:582:0x0332, B:584:0x0336, B:586:0x033c, B:600:0x0387, B:602:0x03ba, B:604:0x03c2, B:607:0x03c7, B:608:0x03ce, B:612:0x03d6, B:615:0x03f9, B:617:0x0401, B:633:0x0424, B:749:0x06c2, B:751:0x06c6, B:757:0x06d1, B:759:0x06ec, B:798:0x07af, B:805:0x07d2, B:812:0x07e0, B:816:0x07f6, B:820:0x07fd, B:837:0x0839, B:844:0x0848, B:848:0x085e, B:852:0x0866, B:856:0x087c, B:761:0x06f1, B:763:0x070e, B:767:0x071e, B:769:0x0722, B:771:0x0732, B:772:0x073a, B:774:0x0741, B:775:0x074c, B:777:0x0750, B:779:0x0768, B:786:0x0793, B:781:0x0770, B:783:0x0784, B:785:0x078a, B:788:0x0799, B:764:0x0717, B:766:0x071b, B:638:0x0434, B:640:0x0438, B:643:0x043e, B:649:0x0450, B:651:0x0454, B:645:0x0444, B:647:0x0448, B:656:0x0478, B:658:0x0481, B:660:0x0489, B:662:0x049c, B:663:0x04b3, B:665:0x04c1, B:672:0x04ee, B:674:0x0506, B:676:0x050c, B:678:0x0512, B:679:0x0515, B:668:0x04cc, B:670:0x04e6, B:671:0x04eb, B:684:0x0537, B:686:0x0540, B:688:0x0548, B:690:0x0559, B:691:0x0572, B:693:0x0582, B:696:0x0592, B:700:0x059d, B:702:0x05a3, B:704:0x05ac, B:711:0x05da, B:713:0x05f2, B:715:0x05ff, B:716:0x0603, B:718:0x060b, B:724:0x0623, B:720:0x061a, B:722:0x061e, B:707:0x05b7, B:709:0x05d1, B:710:0x05d6, B:727:0x0633, B:734:0x066f, B:737:0x0685, B:739:0x0689, B:741:0x0696, B:742:0x069a, B:730:0x064d, B:732:0x0667, B:733:0x066c, B:745:0x06a7, B:747:0x06b1, B:579:0x0326, B:581:0x032c, B:589:0x0344, B:598:0x0380, B:590:0x034f, B:592:0x0363, B:594:0x0369, B:595:0x0372, B:597:0x037a, B:564:0x02f1, B:566:0x02f9, B:467:0x0083, B:469:0x0087, B:475:0x0097, B:476:0x009a, B:490:0x00d0, B:492:0x00e4, B:493:0x00e9, B:496:0x0117, B:498:0x013d, B:500:0x0150, B:502:0x0156, B:504:0x015c, B:555:0x02cb, B:505:0x0162, B:508:0x019a, B:515:0x01bc, B:516:0x01c3, B:518:0x01cc, B:520:0x01de, B:521:0x0202, B:528:0x0215, B:530:0x0228, B:532:0x022e, B:534:0x0234, B:535:0x0237, B:538:0x025c, B:545:0x027c, B:546:0x0283, B:548:0x0289, B:550:0x029b, B:551:0x02bf), top: B:861:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:755:0x06ce  */
    /* JADX WARN: Removed duplicated region for block: B:757:0x06d1 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:444:0x0034, B:446:0x003e, B:448:0x0050, B:458:0x0068, B:461:0x0072, B:464:0x0077, B:466:0x007b, B:481:0x00a8, B:484:0x00ae, B:486:0x00b4, B:489:0x00be, B:556:0x02dc, B:558:0x02e0, B:559:0x02e4, B:567:0x02fc, B:572:0x0305, B:574:0x0309, B:578:0x031d, B:582:0x0332, B:584:0x0336, B:586:0x033c, B:600:0x0387, B:602:0x03ba, B:604:0x03c2, B:607:0x03c7, B:608:0x03ce, B:612:0x03d6, B:615:0x03f9, B:617:0x0401, B:633:0x0424, B:749:0x06c2, B:751:0x06c6, B:757:0x06d1, B:759:0x06ec, B:798:0x07af, B:805:0x07d2, B:812:0x07e0, B:816:0x07f6, B:820:0x07fd, B:837:0x0839, B:844:0x0848, B:848:0x085e, B:852:0x0866, B:856:0x087c, B:761:0x06f1, B:763:0x070e, B:767:0x071e, B:769:0x0722, B:771:0x0732, B:772:0x073a, B:774:0x0741, B:775:0x074c, B:777:0x0750, B:779:0x0768, B:786:0x0793, B:781:0x0770, B:783:0x0784, B:785:0x078a, B:788:0x0799, B:764:0x0717, B:766:0x071b, B:638:0x0434, B:640:0x0438, B:643:0x043e, B:649:0x0450, B:651:0x0454, B:645:0x0444, B:647:0x0448, B:656:0x0478, B:658:0x0481, B:660:0x0489, B:662:0x049c, B:663:0x04b3, B:665:0x04c1, B:672:0x04ee, B:674:0x0506, B:676:0x050c, B:678:0x0512, B:679:0x0515, B:668:0x04cc, B:670:0x04e6, B:671:0x04eb, B:684:0x0537, B:686:0x0540, B:688:0x0548, B:690:0x0559, B:691:0x0572, B:693:0x0582, B:696:0x0592, B:700:0x059d, B:702:0x05a3, B:704:0x05ac, B:711:0x05da, B:713:0x05f2, B:715:0x05ff, B:716:0x0603, B:718:0x060b, B:724:0x0623, B:720:0x061a, B:722:0x061e, B:707:0x05b7, B:709:0x05d1, B:710:0x05d6, B:727:0x0633, B:734:0x066f, B:737:0x0685, B:739:0x0689, B:741:0x0696, B:742:0x069a, B:730:0x064d, B:732:0x0667, B:733:0x066c, B:745:0x06a7, B:747:0x06b1, B:579:0x0326, B:581:0x032c, B:589:0x0344, B:598:0x0380, B:590:0x034f, B:592:0x0363, B:594:0x0369, B:595:0x0372, B:597:0x037a, B:564:0x02f1, B:566:0x02f9, B:467:0x0083, B:469:0x0087, B:475:0x0097, B:476:0x009a, B:490:0x00d0, B:492:0x00e4, B:493:0x00e9, B:496:0x0117, B:498:0x013d, B:500:0x0150, B:502:0x0156, B:504:0x015c, B:555:0x02cb, B:505:0x0162, B:508:0x019a, B:515:0x01bc, B:516:0x01c3, B:518:0x01cc, B:520:0x01de, B:521:0x0202, B:528:0x0215, B:530:0x0228, B:532:0x022e, B:534:0x0234, B:535:0x0237, B:538:0x025c, B:545:0x027c, B:546:0x0283, B:548:0x0289, B:550:0x029b, B:551:0x02bf), top: B:861:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:761:0x06f1 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:444:0x0034, B:446:0x003e, B:448:0x0050, B:458:0x0068, B:461:0x0072, B:464:0x0077, B:466:0x007b, B:481:0x00a8, B:484:0x00ae, B:486:0x00b4, B:489:0x00be, B:556:0x02dc, B:558:0x02e0, B:559:0x02e4, B:567:0x02fc, B:572:0x0305, B:574:0x0309, B:578:0x031d, B:582:0x0332, B:584:0x0336, B:586:0x033c, B:600:0x0387, B:602:0x03ba, B:604:0x03c2, B:607:0x03c7, B:608:0x03ce, B:612:0x03d6, B:615:0x03f9, B:617:0x0401, B:633:0x0424, B:749:0x06c2, B:751:0x06c6, B:757:0x06d1, B:759:0x06ec, B:798:0x07af, B:805:0x07d2, B:812:0x07e0, B:816:0x07f6, B:820:0x07fd, B:837:0x0839, B:844:0x0848, B:848:0x085e, B:852:0x0866, B:856:0x087c, B:761:0x06f1, B:763:0x070e, B:767:0x071e, B:769:0x0722, B:771:0x0732, B:772:0x073a, B:774:0x0741, B:775:0x074c, B:777:0x0750, B:779:0x0768, B:786:0x0793, B:781:0x0770, B:783:0x0784, B:785:0x078a, B:788:0x0799, B:764:0x0717, B:766:0x071b, B:638:0x0434, B:640:0x0438, B:643:0x043e, B:649:0x0450, B:651:0x0454, B:645:0x0444, B:647:0x0448, B:656:0x0478, B:658:0x0481, B:660:0x0489, B:662:0x049c, B:663:0x04b3, B:665:0x04c1, B:672:0x04ee, B:674:0x0506, B:676:0x050c, B:678:0x0512, B:679:0x0515, B:668:0x04cc, B:670:0x04e6, B:671:0x04eb, B:684:0x0537, B:686:0x0540, B:688:0x0548, B:690:0x0559, B:691:0x0572, B:693:0x0582, B:696:0x0592, B:700:0x059d, B:702:0x05a3, B:704:0x05ac, B:711:0x05da, B:713:0x05f2, B:715:0x05ff, B:716:0x0603, B:718:0x060b, B:724:0x0623, B:720:0x061a, B:722:0x061e, B:707:0x05b7, B:709:0x05d1, B:710:0x05d6, B:727:0x0633, B:734:0x066f, B:737:0x0685, B:739:0x0689, B:741:0x0696, B:742:0x069a, B:730:0x064d, B:732:0x0667, B:733:0x066c, B:745:0x06a7, B:747:0x06b1, B:579:0x0326, B:581:0x032c, B:589:0x0344, B:598:0x0380, B:590:0x034f, B:592:0x0363, B:594:0x0369, B:595:0x0372, B:597:0x037a, B:564:0x02f1, B:566:0x02f9, B:467:0x0083, B:469:0x0087, B:475:0x0097, B:476:0x009a, B:490:0x00d0, B:492:0x00e4, B:493:0x00e9, B:496:0x0117, B:498:0x013d, B:500:0x0150, B:502:0x0156, B:504:0x015c, B:555:0x02cb, B:505:0x0162, B:508:0x019a, B:515:0x01bc, B:516:0x01c3, B:518:0x01cc, B:520:0x01de, B:521:0x0202, B:528:0x0215, B:530:0x0228, B:532:0x022e, B:534:0x0234, B:535:0x0237, B:538:0x025c, B:545:0x027c, B:546:0x0283, B:548:0x0289, B:550:0x029b, B:551:0x02bf), top: B:861:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:790:0x079f  */
    /* JADX WARN: Type inference failed for: r33v0, types: [org.telegram.messenger.BaseController, org.telegram.messenger.SendMessagesHelper] */
    /* JADX WARN: Type inference failed for: r36v1, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r36v2, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r36v3, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r36v4, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r36v5, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r36v6, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r36v7, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r5v33, types: [org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$TL_messages_editMessage] */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35, types: [org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$TL_messages_addPollAnswer] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void editMessage(org.telegram.messenger.MessageObject r34, org.telegram.tgnet.TLRPC.TL_inputPollAnswer r35, org.telegram.tgnet.TLRPC.TL_photo r36, org.telegram.messenger.VideoEditedInfo r37, org.telegram.tgnet.TLRPC.TL_document r38, java.lang.String r39, org.telegram.tgnet.TLRPC.PhotoSize r40, java.util.HashMap<java.lang.String, java.lang.String> r41, boolean r42, boolean r43, java.lang.Object r44) {
        /*
            Method dump skipped, instruction units count: 2202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.editMessage(org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$TL_inputPollAnswer, org.telegram.tgnet.TLRPC$TL_photo, org.telegram.messenger.VideoEditedInfo, org.telegram.tgnet.TLRPC$TL_document, java.lang.String, org.telegram.tgnet.TLRPC$PhotoSize, java.util.HashMap, boolean, boolean, java.lang.Object):void");
    }

    public int editMessage(MessageObject messageObject, String str, boolean z, final BaseFragment baseFragment, ArrayList<TLRPC.MessageEntity> arrayList, int i, int i2) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return 0;
        }
        LocaleUtils.replaceCustomEmojis(this.currentAccount, messageObject.getDialogId(), arrayList);
        final TLRPC.TL_messages_editMessage tL_messages_editMessage = new TLRPC.TL_messages_editMessage();
        tL_messages_editMessage.peer = getMessagesController().getInputPeer(messageObject.getDialogId());
        if (str != null) {
            tL_messages_editMessage.message = str;
            tL_messages_editMessage.flags |= 2048;
            tL_messages_editMessage.no_webpage = !z;
        }
        tL_messages_editMessage.f1341id = messageObject.getId();
        TLRPC.Message message = messageObject.messageOwner;
        if (message != null && (message.flags & TLObject.FLAG_30) != 0) {
            tL_messages_editMessage.quick_reply_shortcut_id = message.quick_reply_shortcut_id;
            tL_messages_editMessage.flags |= 131072;
        }
        if (arrayList != null) {
            tL_messages_editMessage.entities = arrayList;
            tL_messages_editMessage.flags |= 8;
        }
        if (i != 0) {
            tL_messages_editMessage.schedule_date = i;
            int i3 = tL_messages_editMessage.flags;
            tL_messages_editMessage.flags = 32768 | i3;
            if (i2 != 0) {
                tL_messages_editMessage.schedule_repeat_period = i2;
                tL_messages_editMessage.flags = i3 | 294912;
            }
        }
        return getConnectionsManager().sendRequest(tL_messages_editMessage, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda63
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$editMessage$20(baseFragment, tL_messages_editMessage, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$editMessage$20(final BaseFragment baseFragment, final TLRPC.TL_messages_editMessage tL_messages_editMessage, TLObject tLObject, final TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$editMessage$19(tL_error, baseFragment, tL_messages_editMessage);
                }
            });
        }
    }

    public /* synthetic */ void lambda$editMessage$19(TLRPC.TL_error tL_error, BaseFragment baseFragment, TLRPC.TL_messages_editMessage tL_messages_editMessage) {
        AlertsCreator.processError(this.currentAccount, tL_error, baseFragment, tL_messages_editMessage, new Object[0]);
    }

    public void deletePollOption(MessageObject messageObject, byte[] bArr) {
        if (messageObject != null && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPoll)) {
            long dialogId = messageObject.getDialogId();
            int id = messageObject.getId();
            TLRPC.TL_messages_deletePollAnswer tL_messages_deletePollAnswer = new TLRPC.TL_messages_deletePollAnswer();
            tL_messages_deletePollAnswer.peer = getMessagesController().getInputPeer(dialogId);
            tL_messages_deletePollAnswer.msg_id = id;
            tL_messages_deletePollAnswer.option = bArr;
            ConnectionsManager connectionsManager = getConnectionsManager();
            DispatchQueue dispatchQueue = Utilities.stageQueue;
            Objects.requireNonNull(dispatchQueue);
            connectionsManager.sendRequestTyped(tL_messages_deletePollAnswer, new ChatThemeController$$ExternalSyntheticLambda6(dispatchQueue), new Utilities.Callback2() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda26
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$deletePollOption$21((TLRPC.Updates) obj, (TLRPC.TL_error) obj2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$deletePollOption$21(TLRPC.Updates updates, TLRPC.TL_error tL_error) {
        if (updates != null) {
            getMessagesController().processUpdates(updates, false);
        }
    }

    public void addPollOption(MessageObject messageObject, CharSequence charSequence, PollAttachedMedia pollAttachedMedia) {
        if (messageObject == null) {
            return;
        }
        TLRPC.MessageMedia media = MessageObject.getMedia(messageObject);
        if (media instanceof TLRPC.TL_messageMediaPoll) {
            long dialogId = messageObject.getDialogId();
            messageObject.getId();
            TLRPC.TL_inputPollAnswer tL_inputPollAnswer = new TLRPC.TL_inputPollAnswer();
            tL_inputPollAnswer.text = new TLRPC.TL_textWithEntities();
            tL_inputPollAnswer.option = new byte[]{(byte) (((TLRPC.TL_messageMediaPoll) media).poll.answers.size() + 48)};
            if (charSequence != null) {
                CharSequence[] charSequenceArr = {charSequence};
                tL_inputPollAnswer.text.entities = getMediaDataController().getEntities(charSequenceArr, true);
                tL_inputPollAnswer.text.text = charSequenceArr[0].toString();
            }
            if (pollAttachedMedia instanceof PollAttachedMediaGallery) {
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(((PollAttachedMediaGallery) pollAttachedMedia).sendingMediaInfo);
                prepareSendingMedia(getAccountInstance(), arrayList, dialogId, null, null, null, null, false, false, messageObject, tL_inputPollAnswer, false, 0, 0, 0, false, null, null, 0, 0L, false, 0L, 0L, null);
                return;
            }
            if (pollAttachedMedia instanceof PollAttachedMediaSticker) {
                PollAttachedMediaSticker pollAttachedMediaSticker = (PollAttachedMediaSticker) pollAttachedMedia;
                editMessage(messageObject, tL_inputPollAnswer, null, null, (TLRPC.TL_document) pollAttachedMediaSticker.sticker, null, null, null, false, false, pollAttachedMediaSticker.parent);
                return;
            }
            if (pollAttachedMedia instanceof PollAttachedMediaLocation) {
                tL_inputPollAnswer.input_media = TlUtils.toInputMediaGeo(((PollAttachedMediaLocation) pollAttachedMedia).media);
                editMessage(messageObject, tL_inputPollAnswer, null, null, null, null, null, null, false, false, null);
            } else {
                if (pollAttachedMedia instanceof PollAttachedMediaLink) {
                    TLRPC.TL_inputMediaWebPage tL_inputMediaWebPage = new TLRPC.TL_inputMediaWebPage();
                    tL_inputMediaWebPage.url = ((PollAttachedMediaLink) pollAttachedMedia).url;
                    tL_inputMediaWebPage.optional = true;
                    tL_inputPollAnswer.input_media = tL_inputMediaWebPage;
                    editMessage(messageObject, tL_inputPollAnswer, null, null, null, null, null, null, false, false, null);
                    return;
                }
                editMessage(messageObject, tL_inputPollAnswer, null, null, null, null, null, null, false, false, null);
            }
        }
    }

    public void sendLocation(Location location) {
        TLRPC.TL_messageMediaGeo tL_messageMediaGeo = new TLRPC.TL_messageMediaGeo();
        TLRPC.TL_geoPoint tL_geoPoint = new TLRPC.TL_geoPoint();
        tL_messageMediaGeo.geo = tL_geoPoint;
        tL_geoPoint.lat = AndroidUtilities.fixLocationCoord(location.getLatitude());
        tL_messageMediaGeo.geo._long = AndroidUtilities.fixLocationCoord(location.getLongitude());
        Iterator<Map.Entry<String, MessageObject>> it = this.waitingForLocation.entrySet().iterator();
        while (it.hasNext()) {
            MessageObject value = it.next().getValue();
            sendMessage(SendMessageParams.m1079of((TLRPC.MessageMedia) tL_messageMediaGeo, value.getDialogId(), value, (MessageObject) null, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, true, 0, 0));
        }
    }

    public void sendCurrentLocation(MessageObject messageObject, TLRPC.KeyboardButton keyboardButton) {
        if (messageObject == null || keyboardButton == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(messageObject.getDialogId());
        sb.append("_");
        sb.append(messageObject.getId());
        sb.append("_");
        sb.append(Utilities.bytesToHex(keyboardButton.data));
        sb.append("_");
        sb.append(keyboardButton instanceof TLRPC.TL_keyboardButtonGame ? "1" : MVEL.VERSION_SUB);
        this.waitingForLocation.put(sb.toString(), messageObject);
        this.locationProvider.start();
    }

    public boolean isSendingCurrentLocation(MessageObject messageObject, TLRPC.KeyboardButton keyboardButton) {
        if (messageObject == null || keyboardButton == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(messageObject.getDialogId());
        sb.append("_");
        sb.append(messageObject.getId());
        sb.append("_");
        sb.append(Utilities.bytesToHex(keyboardButton.data));
        sb.append("_");
        sb.append(keyboardButton instanceof TLRPC.TL_keyboardButtonGame ? "1" : MVEL.VERSION_SUB);
        return this.waitingForLocation.containsKey(sb.toString());
    }

    public void sendNotificationCallback(final long j, final int i, final byte[] bArr) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendNotificationCallback$24(j, i, bArr);
            }
        });
    }

    public /* synthetic */ void lambda$sendNotificationCallback$24(long j, int i, byte[] bArr) {
        TLRPC.Chat chatSync;
        TLRPC.User userSync;
        final String str = j + "_" + i + "_" + Utilities.bytesToHex(bArr) + "_0";
        this.waitingForCallback.put(str, Boolean.TRUE);
        final List<String> list = this.waitingForCallbackMap.get(j + "_" + i);
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            this.waitingForCallbackMap.put(j + "_" + i, arrayList);
            list = arrayList;
        }
        list.add(str);
        if (DialogObject.isUserDialog(j)) {
            if (getMessagesController().getUser(Long.valueOf(j)) == null && (userSync = getMessagesStorage().getUserSync(j)) != null) {
                getMessagesController().putUser(userSync, true);
            }
        } else {
            long j2 = -j;
            if (getMessagesController().getChat(Long.valueOf(j2)) == null && (chatSync = getMessagesStorage().getChatSync(j2)) != null) {
                getMessagesController().putChat(chatSync, true);
            }
        }
        TLRPC.TL_messages_getBotCallbackAnswer tL_messages_getBotCallbackAnswer = new TLRPC.TL_messages_getBotCallbackAnswer();
        tL_messages_getBotCallbackAnswer.peer = getMessagesController().getInputPeer(j);
        tL_messages_getBotCallbackAnswer.msg_id = i;
        tL_messages_getBotCallbackAnswer.game = false;
        if (bArr != null) {
            tL_messages_getBotCallbackAnswer.flags = 1 | tL_messages_getBotCallbackAnswer.flags;
            tL_messages_getBotCallbackAnswer.data = bArr;
        }
        getConnectionsManager().sendRequest(tL_messages_getBotCallbackAnswer, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda51
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendNotificationCallback$23(str, list, tLObject, tL_error);
            }
        }, 2);
        getMessagesController().markDialogAsRead(j, i, i, 0, false, 0L, 0, true, 0);
    }

    public /* synthetic */ void lambda$sendNotificationCallback$23(final String str, final List list, TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendNotificationCallback$22(str, list);
            }
        });
    }

    public /* synthetic */ void lambda$sendNotificationCallback$22(String str, List list) {
        this.waitingForCallback.remove(str);
        list.remove(str);
    }

    public void onMessageEdited(TLRPC.Message message) {
        if (message == null || message.reply_markup == null) {
            return;
        }
        List<String> listRemove = this.waitingForCallbackMap.remove(message.dialog_id + "_" + message.f1271id);
        if (listRemove != null) {
            Iterator<String> it = listRemove.iterator();
            while (it.hasNext()) {
                this.waitingForCallback.remove(it.next());
            }
        }
    }

    public byte[] isSendingVote(MessageObject messageObject) {
        if (messageObject == null) {
            return null;
        }
        return this.waitingForVote.get("poll_" + messageObject.getPollId());
    }

    public int sendVote(MessageObject messageObject, ArrayList<TLRPC.PollAnswer> arrayList, Runnable runnable) {
        return sendVote(messageObject, arrayList, runnable, true);
    }

    public int sendVote(final MessageObject messageObject, ArrayList<TLRPC.PollAnswer> arrayList, final Runnable runnable, final boolean z) {
        byte[] bArr;
        if (messageObject == null) {
            return 0;
        }
        final String str = "poll_" + messageObject.getPollId();
        if (this.waitingForCallback.containsKey(str)) {
            return 0;
        }
        TLRPC.TL_messages_sendVote tL_messages_sendVote = new TLRPC.TL_messages_sendVote();
        tL_messages_sendVote.msg_id = messageObject.getId();
        tL_messages_sendVote.peer = getMessagesController().getInputPeer(messageObject.getDialogId());
        if (arrayList != null) {
            bArr = new byte[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                TLRPC.PollAnswer pollAnswer = arrayList.get(i);
                if (pollAnswer != null) {
                    tL_messages_sendVote.options.add(pollAnswer.option);
                    bArr[i] = pollAnswer.option[0];
                }
            }
        } else {
            bArr = new byte[0];
        }
        this.waitingForVote.put(str, bArr);
        return getConnectionsManager().sendRequest(tL_messages_sendVote, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda62
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendVote$26(messageObject, z, str, runnable, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$sendVote$26(MessageObject messageObject, boolean z, final String str, final Runnable runnable, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            this.voteSendTime.put(messageObject.getPollId(), 0L);
            if (z) {
                getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
            }
            this.voteSendTime.put(messageObject.getPollId(), Long.valueOf(SystemClock.elapsedRealtime()));
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendVote$25(str, runnable);
            }
        });
    }

    public /* synthetic */ void lambda$sendVote$25(String str, Runnable runnable) {
        this.waitingForVote.remove(str);
        if (runnable != null) {
            runnable.run();
        }
    }

    public Boolean getSendingTodoValue(MessageObject messageObject, TLRPC.TodoItem todoItem) {
        return this.waitingForTodoUpdate.get(Integer.valueOf(Objects.hash(Long.valueOf(messageObject.getDialogId()), Integer.valueOf(messageObject.getId()), Integer.valueOf(todoItem.f1405id))));
    }

    public int toggleTodo(final long j, final MessageObject messageObject, final TLRPC.TodoItem todoItem, final boolean z, final Runnable runnable) {
        if (messageObject == null) {
            return 0;
        }
        final int iHash = Objects.hash(Long.valueOf(messageObject.getDialogId()), Integer.valueOf(messageObject.getId()), Integer.valueOf(todoItem.f1405id));
        this.waitingForTodoUpdate.put(Integer.valueOf(iHash), Boolean.valueOf(z));
        TLRPC.TL_messages_toggleTodoCompleted tL_messages_toggleTodoCompleted = new TLRPC.TL_messages_toggleTodoCompleted();
        tL_messages_toggleTodoCompleted.msg_id = messageObject.getId();
        tL_messages_toggleTodoCompleted.peer = getMessagesController().getInputPeer(messageObject.getDialogId());
        if (z) {
            tL_messages_toggleTodoCompleted.completed.add(Integer.valueOf(todoItem.f1405id));
        } else {
            tL_messages_toggleTodoCompleted.incompleted.add(Integer.valueOf(todoItem.f1405id));
        }
        return getConnectionsManager().sendRequest(tL_messages_toggleTodoCompleted, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda90
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$toggleTodo$28(messageObject, todoItem, z, j, iHash, runnable, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$toggleTodo$28(MessageObject messageObject, TLRPC.TodoItem todoItem, final boolean z, long j, final int i, final Runnable runnable, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            getMessagesStorage().toggleTodo(messageObject.getDialogId(), messageObject.getId(), todoItem.f1405id, z, j);
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda126
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleTodo$27(i, z, runnable);
            }
        });
    }

    public /* synthetic */ void lambda$toggleTodo$27(int i, boolean z, Runnable runnable) {
        Boolean bool = this.waitingForTodoUpdate.get(Integer.valueOf(i));
        if (bool != null && bool.booleanValue() == z) {
            this.waitingForTodoUpdate.remove(Integer.valueOf(i));
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public long getVoteSendTime(long j) {
        return this.voteSendTime.get(j, 0L).longValue();
    }

    public void sendReaction(MessageObject messageObject, ArrayList<ReactionsLayoutInBubble.VisibleReaction> arrayList, ReactionsLayoutInBubble.VisibleReaction visibleReaction, boolean z, boolean z2, BaseFragment baseFragment, final Runnable runnable) {
        if (messageObject == null || baseFragment == null) {
            return;
        }
        TLRPC.TL_messages_sendReaction tL_messages_sendReaction = new TLRPC.TL_messages_sendReaction();
        TLRPC.Message message = messageObject.messageOwner;
        if (message.isThreadMessage && message.fwd_from != null) {
            tL_messages_sendReaction.peer = getMessagesController().getInputPeer(messageObject.getFromChatId());
            tL_messages_sendReaction.msg_id = messageObject.messageOwner.fwd_from.saved_from_msg_id;
        } else {
            tL_messages_sendReaction.peer = getMessagesController().getInputPeer(messageObject.getDialogId());
            tL_messages_sendReaction.msg_id = messageObject.getId();
        }
        tL_messages_sendReaction.add_to_recent = z2;
        if (z2 && visibleReaction != null) {
            MediaDataController.getInstance(this.currentAccount).recentReactions.add(0, ReactionsUtils.toTLReaction(visibleReaction));
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                ReactionsLayoutInBubble.VisibleReaction visibleReaction2 = arrayList.get(i);
                if (visibleReaction2.documentId != 0) {
                    TLRPC.TL_reactionCustomEmoji tL_reactionCustomEmoji = new TLRPC.TL_reactionCustomEmoji();
                    tL_reactionCustomEmoji.document_id = visibleReaction2.documentId;
                    tL_messages_sendReaction.reaction.add(tL_reactionCustomEmoji);
                    tL_messages_sendReaction.flags |= 1;
                } else if (visibleReaction2.emojicon != null) {
                    TLRPC.TL_reactionEmoji tL_reactionEmoji = new TLRPC.TL_reactionEmoji();
                    tL_reactionEmoji.emoticon = visibleReaction2.emojicon;
                    tL_messages_sendReaction.reaction.add(tL_reactionEmoji);
                    tL_messages_sendReaction.flags |= 1;
                }
            }
        }
        if (z) {
            tL_messages_sendReaction.flags |= 2;
            tL_messages_sendReaction.big = true;
        }
        getConnectionsManager().sendRequest(tL_messages_sendReaction, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda118
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendReaction$29(runnable, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$sendReaction$29(Runnable runnable, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject != null) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
            if (runnable != null) {
                AndroidUtilities.runOnUIThread(runnable);
            }
        }
    }

    public void requestUrlAuth(final String str, final ChatActivity chatActivity, final boolean z) {
        final TLRPC.TL_messages_requestUrlAuth tL_messages_requestUrlAuth = new TLRPC.TL_messages_requestUrlAuth();
        tL_messages_requestUrlAuth.url = str;
        tL_messages_requestUrlAuth.flags |= 4;
        getConnectionsManager().sendRequest(tL_messages_requestUrlAuth, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda108
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$requestUrlAuth$31(tL_messages_requestUrlAuth, chatActivity, str, z, tLObject, tL_error);
            }
        }, 2);
    }

    public /* synthetic */ void lambda$requestUrlAuth$31(final TLRPC.TL_messages_requestUrlAuth tL_messages_requestUrlAuth, final ChatActivity chatActivity, final String str, final boolean z, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda61
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestUrlAuth$30(tLObject, tL_messages_requestUrlAuth, chatActivity, str, z);
            }
        });
    }

    public /* synthetic */ void lambda$requestUrlAuth$30(TLObject tLObject, TLRPC.TL_messages_requestUrlAuth tL_messages_requestUrlAuth, ChatActivity chatActivity, String str, boolean z) {
        if (tLObject != null) {
            if (tLObject instanceof TLRPC.TL_urlAuthResultRequest) {
                OAuthSheet.handle(false, this.currentAccount, tL_messages_requestUrlAuth, (TLRPC.TL_urlAuthResultRequest) tLObject);
                return;
            } else if (tLObject instanceof TLRPC.TL_urlAuthResultAccepted) {
                OAuthSheet.handle(false, this.currentAccount, tL_messages_requestUrlAuth, (TLRPC.TL_urlAuthResultAccepted) tLObject);
                return;
            } else {
                if (tLObject instanceof TLRPC.TL_urlAuthResultDefault) {
                    AlertsCreator.showOpenUrlAlert(chatActivity, str, false, z);
                    return;
                }
                return;
            }
        }
        AlertsCreator.showOpenUrlAlert(chatActivity, str, false, z);
    }

    public void sendCallback(boolean z, MessageObject messageObject, TLRPC.KeyboardButton keyboardButton, ChatActivity chatActivity) {
        lambda$sendCallback$34(z, messageObject, keyboardButton, null, null, chatActivity);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00c5  */
    /* JADX INFO: renamed from: sendCallback */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$sendCallback$34(final boolean r17, final org.telegram.messenger.MessageObject r18, final org.telegram.tgnet.TLRPC.KeyboardButton r19, final org.telegram.tgnet.TLRPC.InputCheckPasswordSRP r20, final org.telegram.p035ui.TwoStepVerificationActivity r21, final org.telegram.p035ui.ChatActivity r22) {
        /*
            Method dump skipped, instruction units count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$sendCallback$34(boolean, org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$KeyboardButton, org.telegram.tgnet.TLRPC$InputCheckPasswordSRP, org.telegram.ui.TwoStepVerificationActivity, org.telegram.ui.ChatActivity):void");
    }

    public /* synthetic */ void lambda$sendCallback$40(final String str, final List list, final boolean z, final MessageObject messageObject, final TLRPC.KeyboardButton keyboardButton, final ChatActivity chatActivity, final TwoStepVerificationActivity twoStepVerificationActivity, final TLObject[] tLObjectArr, final TLRPC.InputCheckPasswordSRP inputCheckPasswordSRP, final boolean z2, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda111
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendCallback$39(str, list, z, tLObject, messageObject, keyboardButton, chatActivity, twoStepVerificationActivity, tLObjectArr, tL_error, inputCheckPasswordSRP, z2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:211:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$sendCallback$39(final java.lang.String r26, final java.util.List r27, boolean r28, org.telegram.tgnet.TLObject r29, final org.telegram.messenger.MessageObject r30, final org.telegram.tgnet.TLRPC.KeyboardButton r31, final org.telegram.p035ui.ChatActivity r32, final org.telegram.p035ui.TwoStepVerificationActivity r33, org.telegram.tgnet.TLObject[] r34, org.telegram.tgnet.TLRPC.TL_error r35, org.telegram.tgnet.TLRPC.InputCheckPasswordSRP r36, final boolean r37) {
        /*
            Method dump skipped, instruction units count: 1247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$sendCallback$39(java.lang.String, java.util.List, boolean, org.telegram.tgnet.TLObject, org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$KeyboardButton, org.telegram.ui.ChatActivity, org.telegram.ui.TwoStepVerificationActivity, org.telegram.tgnet.TLObject[], org.telegram.tgnet.TLRPC$TL_error, org.telegram.tgnet.TLRPC$InputCheckPasswordSRP, boolean):void");
    }

    public /* synthetic */ void lambda$sendCallback$32(String str, List list) {
        this.waitingForCallback.remove(str);
        list.remove(str);
    }

    public /* synthetic */ void lambda$sendCallback$35(final boolean z, final MessageObject messageObject, final TLRPC.KeyboardButton keyboardButton, final ChatActivity chatActivity, AlertDialog alertDialog, int i) {
        final TwoStepVerificationActivity twoStepVerificationActivity = new TwoStepVerificationActivity();
        twoStepVerificationActivity.setDelegate(0, new TwoStepVerificationActivity.TwoStepVerificationActivityDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda122
            @Override // org.telegram.ui.TwoStepVerificationActivity.TwoStepVerificationActivityDelegate
            public final void didEnterPassword(TLRPC.InputCheckPasswordSRP inputCheckPasswordSRP) {
                this.f$0.lambda$sendCallback$34(z, messageObject, keyboardButton, twoStepVerificationActivity, chatActivity, inputCheckPasswordSRP);
            }
        });
        chatActivity.presentFragment(twoStepVerificationActivity);
    }

    public /* synthetic */ void lambda$sendCallback$38(final TwoStepVerificationActivity twoStepVerificationActivity, final boolean z, final MessageObject messageObject, final TLRPC.KeyboardButton keyboardButton, final ChatActivity chatActivity, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda123
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendCallback$37(tL_error, tLObject, twoStepVerificationActivity, z, messageObject, keyboardButton, chatActivity);
            }
        });
    }

    public /* synthetic */ void lambda$sendCallback$37(TLRPC.TL_error tL_error, TLObject tLObject, TwoStepVerificationActivity twoStepVerificationActivity, boolean z, MessageObject messageObject, TLRPC.KeyboardButton keyboardButton, ChatActivity chatActivity) {
        if (tL_error == null) {
            TL_account.Password password = (TL_account.Password) tLObject;
            twoStepVerificationActivity.setCurrentPasswordInfo(null, password);
            TwoStepVerificationActivity.initPasswordNewAlgo(password);
            lambda$sendCallback$34(z, messageObject, keyboardButton, twoStepVerificationActivity.getNewSrpPassword(), twoStepVerificationActivity, chatActivity);
        }
    }

    public boolean isSendingCallback(MessageObject messageObject, TLRPC.KeyboardButton keyboardButton) {
        int i = 0;
        if (messageObject == null || keyboardButton == null) {
            return false;
        }
        if (keyboardButton instanceof TLRPC.TL_keyboardButtonUrlAuth) {
            i = 3;
        } else if (keyboardButton instanceof TLRPC.TL_keyboardButtonGame) {
            i = 1;
        } else if (keyboardButton instanceof TLRPC.TL_keyboardButtonBuy) {
            i = 2;
        }
        return this.waitingForCallback.containsKey(messageObject.getDialogId() + "_" + messageObject.getId() + "_" + Utilities.bytesToHex(keyboardButton.data) + "_" + i);
    }

    public void sendGame(TLRPC.InputPeer inputPeer, TLRPC.TL_inputMediaGame tL_inputMediaGame, long j, final long j2) {
        NativeByteBuffer nativeByteBuffer;
        if (inputPeer == null || tL_inputMediaGame == null) {
            return;
        }
        TLRPC.TL_messages_sendMedia tL_messages_sendMedia = new TLRPC.TL_messages_sendMedia();
        tL_messages_sendMedia.peer = inputPeer;
        if (inputPeer instanceof TLRPC.TL_inputPeerChannel) {
            tL_messages_sendMedia.silent = MessagesController.getNotificationsSettings(this.currentAccount).getBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + (-inputPeer.channel_id), false);
        } else {
            boolean z = inputPeer instanceof TLRPC.TL_inputPeerChat;
            int i = this.currentAccount;
            if (z) {
                tL_messages_sendMedia.silent = MessagesController.getNotificationsSettings(i).getBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + (-inputPeer.chat_id), false);
            } else {
                tL_messages_sendMedia.silent = MessagesController.getNotificationsSettings(i).getBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + inputPeer.user_id, false);
            }
        }
        tL_messages_sendMedia.random_id = j != 0 ? j : getNextRandomId();
        tL_messages_sendMedia.message = _UrlKt.FRAGMENT_ENCODE_SET;
        tL_messages_sendMedia.media = tL_inputMediaGame;
        long sendAsPeerId = ChatObject.getSendAsPeerId(getMessagesController().getChat(Long.valueOf(inputPeer.chat_id)), getMessagesController().getChatFull(inputPeer.chat_id));
        if (sendAsPeerId != UserConfig.getInstance(this.currentAccount).getClientUserId()) {
            tL_messages_sendMedia.send_as = getMessagesController().getInputPeer(sendAsPeerId);
        }
        long sendPaidMessagesStars = getMessagesController().getSendPaidMessagesStars(DialogObject.getPeerDialogId(inputPeer));
        if (sendPaidMessagesStars <= 0) {
            sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(getMessagesController().isUserContactBlocked(DialogObject.getPeerDialogId(inputPeer)));
        }
        if (sendPaidMessagesStars > 0) {
            tL_messages_sendMedia.flags |= TLObject.FLAG_21;
            tL_messages_sendMedia.allow_paid_stars = sendPaidMessagesStars;
        }
        if (j2 == 0) {
            NativeByteBuffer nativeByteBuffer2 = null;
            try {
                nativeByteBuffer = new NativeByteBuffer(inputPeer.getObjectSize() + tL_inputMediaGame.getObjectSize() + 12);
                try {
                    nativeByteBuffer.writeInt32(3);
                    nativeByteBuffer.writeInt64(j);
                    inputPeer.serializeToStream(nativeByteBuffer);
                    tL_inputMediaGame.serializeToStream(nativeByteBuffer);
                } catch (Exception e) {
                    e = e;
                    nativeByteBuffer2 = nativeByteBuffer;
                    FileLog.m1048e(e);
                    nativeByteBuffer = nativeByteBuffer2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            j2 = getMessagesStorage().createPendingTask(nativeByteBuffer);
        }
        getConnectionsManager().sendRequest(tL_messages_sendMedia, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda121
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendGame$41(j2, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$sendGame$41(long j, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        if (j != 0) {
            getMessagesStorage().removePendingTask(j);
        }
    }

    /* JADX WARN: Not initialized variable reg: 69, insn: 0x23d0: MOVE (r3 I:??[OBJECT, ARRAY]) = (r69 I:??[OBJECT, ARRAY]), block:B:4290:0x23d0 */
    /* JADX WARN: Not initialized variable reg: 69, insn: 0x2656: MOVE (r3 I:??[OBJECT, ARRAY]) = (r69 I:??[OBJECT, ARRAY]), block:B:4403:0x2656 */
    /*  JADX ERROR: Type inference failed with stack overflow
        jadx.core.utils.exceptions.JadxOverflowException
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public void sendMessage(org.telegram.messenger.SendMessagesHelper.SendMessageParams r93) {
        /*
            Method dump skipped, instruction units count: 11388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.sendMessage(org.telegram.messenger.SendMessagesHelper$SendMessageParams):void");
    }

    public /* synthetic */ void lambda$sendMessage$42(SendMessageParams sendMessageParams, Long l) {
        sendMessageParams.payStars = l.longValue();
        sendMessage(sendMessageParams);
    }

    private void performSendDelayedMessage(DelayedMessage delayedMessage) {
        performSendDelayedMessage(delayedMessage, -1);
    }

    private ArrayList<TLRPC.DocumentAttribute> copyAttributesForSecretChat(ArrayList<TLRPC.DocumentAttribute> arrayList) {
        ArrayList<TLRPC.DocumentAttribute> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                TLRPC.DocumentAttribute documentAttribute = arrayList.get(i);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                    TLRPC.TL_documentAttributeVideo_layer159 tL_documentAttributeVideo_layer159 = new TLRPC.TL_documentAttributeVideo_layer159();
                    tL_documentAttributeVideo_layer159.flags = documentAttribute.flags;
                    tL_documentAttributeVideo_layer159.round_message = documentAttribute.round_message;
                    tL_documentAttributeVideo_layer159.supports_streaming = documentAttribute.supports_streaming;
                    tL_documentAttributeVideo_layer159.duration = documentAttribute.duration;
                    tL_documentAttributeVideo_layer159.f1256w = documentAttribute.f1256w;
                    tL_documentAttributeVideo_layer159.f1255h = documentAttribute.f1255h;
                    arrayList2.add(tL_documentAttributeVideo_layer159);
                } else {
                    arrayList2.add(documentAttribute);
                }
            }
        }
        return arrayList2;
    }

    private TLRPC.PhotoSize getThumbForSecretChat(ArrayList<TLRPC.PhotoSize> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                TLRPC.PhotoSize photoSize = arrayList.get(i);
                if (photoSize != null && !(photoSize instanceof TLRPC.TL_photoPathSize) && !(photoSize instanceof TLRPC.TL_photoSizeEmpty) && photoSize.location != null) {
                    if (photoSize instanceof TLRPC.TL_photoStrippedSize) {
                        return photoSize;
                    }
                    TLRPC.TL_photoSize_layer127 tL_photoSize_layer127 = new TLRPC.TL_photoSize_layer127();
                    tL_photoSize_layer127.type = photoSize.type;
                    tL_photoSize_layer127.f1278w = photoSize.f1278w;
                    tL_photoSize_layer127.f1277h = photoSize.f1277h;
                    tL_photoSize_layer127.size = photoSize.size;
                    byte[] bArr = photoSize.bytes;
                    tL_photoSize_layer127.bytes = bArr;
                    if (bArr == null) {
                        tL_photoSize_layer127.bytes = new byte[0];
                    }
                    TLRPC.TL_fileLocation_layer82 tL_fileLocation_layer82 = new TLRPC.TL_fileLocation_layer82();
                    tL_photoSize_layer127.location = tL_fileLocation_layer82;
                    TLRPC.FileLocation fileLocation = photoSize.location;
                    tL_fileLocation_layer82.dc_id = fileLocation.dc_id;
                    tL_fileLocation_layer82.volume_id = fileLocation.volume_id;
                    tL_fileLocation_layer82.local_id = fileLocation.local_id;
                    tL_fileLocation_layer82.secret = fileLocation.secret;
                    return tL_photoSize_layer127;
                }
            }
        }
        return null;
    }

    private void performSendDelayedMessage(final DelayedMessage delayedMessage, int i) {
        boolean z;
        TLRPC.InputEncryptedFile inputMedia;
        boolean z2;
        TLRPC.InputFile inputFile;
        boolean z3;
        String str;
        final TLRPC.InputMedia inputMedia2;
        TLRPC.InputPeer inputPeer;
        ArrayList<Boolean> arrayList;
        TLRPC.InputMedia inputMedia3;
        TLRPC.PhotoSize photoSize;
        int i2;
        final TLRPC.InputMedia inputMedia4;
        TLRPC.InputPeer inputPeer2;
        String str2;
        VideoEditedInfo videoEditedInfo;
        TLRPC.InputMedia firstInputMedia;
        TLRPC.InputMedia inputMedia5;
        TLRPC.PhotoSize photoSize2;
        final SendMessagesHelper sendMessagesHelper = this;
        final DelayedMessage delayedMessage2 = delayedMessage;
        int i3 = delayedMessage2.type;
        if (i3 == 0) {
            String str3 = delayedMessage2.httpLocation;
            if (str3 != null) {
                sendMessagesHelper.putToDelayedMessages(str3, delayedMessage2);
                ImageLoader.getInstance().loadHttpFile(delayedMessage2.httpLocation, "file", sendMessagesHelper.currentAccount);
                return;
            }
            TLObject tLObject = delayedMessage2.sendRequest;
            int i4 = sendMessagesHelper.currentAccount;
            if (tLObject != null) {
                String string = FileLoader.getInstance(i4).getPathToAttach(delayedMessage2.photoSize).toString();
                sendMessagesHelper.putToDelayedMessages(string, delayedMessage2);
                sendMessagesHelper.getFileLoader().uploadFile(string, false, true, 16777216);
                sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                return;
            }
            String string2 = FileLoader.getInstance(i4).getPathToAttach(delayedMessage2.photoSize).toString();
            if (delayedMessage2.sendEncryptedRequest != null && (photoSize2 = delayedMessage2.photoSize) != null && photoSize2.location.dc_id != 0) {
                File file = new File(string2);
                if (!file.exists()) {
                    string2 = FileLoader.getInstance(sendMessagesHelper.currentAccount).getPathToAttach(delayedMessage2.photoSize, true).toString();
                    file = new File(string2);
                }
                if (!file.exists()) {
                    sendMessagesHelper.putToDelayedMessages(FileLoader.getAttachFileName(delayedMessage2.photoSize), delayedMessage2);
                    sendMessagesHelper.getFileLoader().loadFile(ImageLocation.getForObject(delayedMessage2.photoSize, delayedMessage2.locationParent), delayedMessage2.parentObject, "jpg", 3, 0);
                    return;
                }
            }
            sendMessagesHelper.putToDelayedMessages(string2, delayedMessage2);
            sendMessagesHelper.getFileLoader().uploadFile(string2, true, true, 16777216);
            sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
            return;
        }
        if (i3 == 1) {
            VideoEditedInfo videoEditedInfo2 = delayedMessage2.videoEditedInfo;
            if (videoEditedInfo2 != null && videoEditedInfo2.needConvert() && delayedMessage2.performMediaUpload) {
                MessageObject messageObject = delayedMessage2.obj;
                String string3 = messageObject.messageOwner.attachPath;
                TLRPC.Document document = messageObject.getDocument();
                if (string3 == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(FileLoader.getDirectory(4));
                    sb.append("/");
                    sb.append(document.f1253id);
                    sb.append(".");
                    sb.append(delayedMessage2.videoEditedInfo.isSticker ? "webm" : "mp4");
                    string3 = sb.toString();
                }
                sendMessagesHelper.putToDelayedMessages(string3, delayedMessage2);
                if (!delayedMessage2.videoEditedInfo.alreadyScheduledConverting) {
                    MediaController.getInstance().scheduleVideoConvert(delayedMessage2.obj);
                }
                sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                return;
            }
            VideoEditedInfo videoEditedInfo3 = delayedMessage2.videoEditedInfo;
            if (videoEditedInfo3 != null) {
                TLRPC.InputFile inputFile2 = videoEditedInfo3.file;
                if (inputFile2 != null) {
                    i2 = 4;
                    TLObject tLObject2 = delayedMessage2.sendRequest;
                    if (tLObject2 instanceof TLRPC.TL_messages_sendMedia) {
                        inputMedia5 = ((TLRPC.TL_messages_sendMedia) tLObject2).media;
                    } else if (tLObject2 instanceof TLRPC.TL_messages_addPollAnswer) {
                        inputMedia5 = ((TLRPC.TL_messages_addPollAnswer) tLObject2).answer.input_media;
                    } else {
                        inputMedia5 = ((TLRPC.TL_messages_editMessage) tLObject2).media;
                    }
                    inputMedia5.file = inputFile2;
                    videoEditedInfo3.file = null;
                } else {
                    i2 = 4;
                    if (videoEditedInfo3.encryptedFile != null) {
                        TLRPC.TL_decryptedMessage tL_decryptedMessage = (TLRPC.TL_decryptedMessage) delayedMessage2.sendEncryptedRequest;
                        TLRPC.DecryptedMessageMedia decryptedMessageMedia = tL_decryptedMessage.media;
                        decryptedMessageMedia.size = videoEditedInfo3.estimatedSize;
                        decryptedMessageMedia.key = videoEditedInfo3.key;
                        decryptedMessageMedia.f1249iv = videoEditedInfo3.f1184iv;
                        SecretChatHelper secretChatHelper = sendMessagesHelper.getSecretChatHelper();
                        MessageObject messageObject2 = delayedMessage2.obj;
                        secretChatHelper.performSendEncryptedRequest(tL_decryptedMessage, messageObject2.messageOwner, delayedMessage2.encryptedChat, delayedMessage2.videoEditedInfo.encryptedFile, delayedMessage2.originalPath, messageObject2);
                        delayedMessage2.videoEditedInfo.encryptedFile = null;
                        return;
                    }
                }
            } else {
                i2 = 4;
            }
            TLObject tLObject3 = delayedMessage2.sendRequest;
            if (tLObject3 != null) {
                if (tLObject3 instanceof TLRPC.TL_messages_sendMedia) {
                    TLRPC.TL_messages_sendMedia tL_messages_sendMedia = (TLRPC.TL_messages_sendMedia) tLObject3;
                    inputMedia4 = tL_messages_sendMedia.media;
                    inputPeer2 = tL_messages_sendMedia.peer;
                } else if (tLObject3 instanceof TLRPC.TL_messages_addPollAnswer) {
                    TLRPC.TL_messages_addPollAnswer tL_messages_addPollAnswer = (TLRPC.TL_messages_addPollAnswer) tLObject3;
                    inputMedia4 = tL_messages_addPollAnswer.answer.input_media;
                    inputPeer2 = tL_messages_addPollAnswer.peer;
                } else {
                    TLRPC.TL_messages_editMessage tL_messages_editMessage = (TLRPC.TL_messages_editMessage) tLObject3;
                    inputMedia4 = tL_messages_editMessage.media;
                    inputPeer2 = tL_messages_editMessage.peer;
                }
                if (inputMedia4 instanceof TLRPC.TL_inputMediaPaidMedia) {
                    TLRPC.TL_inputMediaPaidMedia tL_inputMediaPaidMedia = (TLRPC.TL_inputMediaPaidMedia) inputMedia4;
                    if (!tL_inputMediaPaidMedia.extended_media.isEmpty()) {
                        inputMedia4 = tL_inputMediaPaidMedia.extended_media.get(0);
                    }
                }
                if ((inputMedia4 instanceof TLRPC.TL_inputMediaPoll) && (firstInputMedia = PollAttachedMediaPack.getFirstInputMedia((TLRPC.TL_inputMediaPoll) inputMedia4)) != null) {
                    inputMedia4 = firstInputMedia;
                }
                TLRPC.InputFile inputFile3 = inputMedia4.file;
                if (inputFile3 == null && !(inputMedia4 instanceof TLRPC.TL_inputMediaDocument) && delayedMessage2.performMediaUpload) {
                    MessageObject messageObject3 = delayedMessage2.obj;
                    String str4 = messageObject3.messageOwner.attachPath;
                    TLRPC.Document document2 = messageObject3.getDocument();
                    TLRPC.Document document3 = delayedMessage2.obj.documentToPollAddOption;
                    if (document3 != null) {
                        document2 = document3;
                    }
                    if (str4 == null) {
                        str4 = FileLoader.getDirectory(i2) + "/" + document2.f1253id + ".mp4";
                    }
                    String str5 = str4;
                    sendMessagesHelper.putToDelayedMessages(str5, delayedMessage2);
                    VideoEditedInfo videoEditedInfo4 = delayedMessage2.obj.videoEditedInfo;
                    if (videoEditedInfo4 == null || !videoEditedInfo4.notReadyYet) {
                        if (videoEditedInfo4 != null && videoEditedInfo4.needConvert()) {
                            sendMessagesHelper.getFileLoader().uploadFile(str5, false, false, document2.size, 33554432, false);
                        } else {
                            sendMessagesHelper.getFileLoader().uploadFile(str5, false, false, 33554432);
                        }
                    }
                    sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                    return;
                }
                TLRPC.InputPhoto inputPhoto = inputMedia4.video_cover;
                if (inputPhoto == null && delayedMessage2.coverFile == null && delayedMessage2.coverPhotoSize != null && delayedMessage2.performCoverUpload) {
                    String str6 = FileLoader.getDirectory(i2) + "/" + delayedMessage2.coverPhotoSize.location.volume_id + "_" + delayedMessage2.coverPhotoSize.location.local_id + ".jpg";
                    sendMessagesHelper.putToDelayedMessages(str6, delayedMessage2);
                    sendMessagesHelper.getFileLoader().uploadFile(str6, false, true, 16777216);
                    sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                    return;
                }
                if (delayedMessage2.isLivePhoto && (inputMedia4 instanceof TLRPC.TL_inputMediaUploadedDocument) && inputFile3 != null && delayedMessage2.coverFile != null) {
                    TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia = new TLRPC.TL_messages_uploadMedia();
                    tL_messages_uploadMedia.peer = inputPeer2;
                    inputMedia4.flags &= -65;
                    inputMedia4.video_cover = null;
                    tL_messages_uploadMedia.media = inputMedia4;
                    sendMessagesHelper.getConnectionsManager().sendRequest(tL_messages_uploadMedia, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda91
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject4, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$performSendDelayedMessage$44(delayedMessage2, tLObject4, tL_error);
                        }
                    });
                    return;
                }
                if (inputPhoto == null && delayedMessage2.coverFile != null && delayedMessage2.performCoverUpload) {
                    TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia2 = new TLRPC.TL_messages_uploadMedia();
                    tL_messages_uploadMedia2.peer = inputPeer2;
                    TLRPC.TL_inputMediaUploadedPhoto tL_inputMediaUploadedPhoto = new TLRPC.TL_inputMediaUploadedPhoto();
                    tL_inputMediaUploadedPhoto.file = delayedMessage2.coverFile;
                    tL_messages_uploadMedia2.media = tL_inputMediaUploadedPhoto;
                    sendMessagesHelper.getConnectionsManager().sendRequest(tL_messages_uploadMedia2, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda92
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject4, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$performSendDelayedMessage$46(inputMedia4, delayedMessage2, tLObject4, tL_error);
                        }
                    });
                    return;
                }
                MessageObject messageObject4 = delayedMessage2.obj;
                if (messageObject4 != null && (videoEditedInfo = messageObject4.videoEditedInfo) != null && videoEditedInfo.isSticker) {
                    str2 = "webp";
                } else {
                    str2 = "jpg";
                }
                String str7 = FileLoader.getDirectory(i2) + "/" + delayedMessage2.photoSize.location.volume_id + "_" + delayedMessage2.photoSize.location.local_id + "." + str2;
                sendMessagesHelper.putToDelayedMessages(str7, delayedMessage2);
                sendMessagesHelper.getFileLoader().uploadFile(str7, false, true, 16777216);
                sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                return;
            }
            MessageObject messageObject5 = delayedMessage2.obj;
            String str8 = messageObject5.messageOwner.attachPath;
            TLRPC.Document document4 = messageObject5.getDocument();
            if (str8 == null) {
                str8 = FileLoader.getDirectory(i2) + "/" + document4.f1253id + ".mp4";
            }
            if (delayedMessage2.sendEncryptedRequest != null && document4.dc_id != 0) {
                File file2 = new File(str8);
                if (!file2.exists() && (file2 = sendMessagesHelper.getFileLoader().getPathToMessage(delayedMessage2.obj.messageOwner)) != null && file2.exists()) {
                    TLRPC.Message message = delayedMessage2.obj.messageOwner;
                    String absolutePath = file2.getAbsolutePath();
                    message.attachPath = absolutePath;
                    delayedMessage2.obj.attachPathExists = true;
                    str8 = absolutePath;
                }
                if ((file2 == null || (!file2.exists() && delayedMessage2.obj.getDocument() != null)) && (file2 = sendMessagesHelper.getFileLoader().getPathToAttach(delayedMessage2.obj.getDocument(), false)) != null && file2.exists()) {
                    TLRPC.Message message2 = delayedMessage2.obj.messageOwner;
                    String absolutePath2 = file2.getAbsolutePath();
                    message2.attachPath = absolutePath2;
                    delayedMessage2.obj.attachPathExists = true;
                    str8 = absolutePath2;
                }
                if (file2 == null || !file2.exists()) {
                    sendMessagesHelper.putToDelayedMessages(FileLoader.getAttachFileName(document4), delayedMessage2);
                    sendMessagesHelper.getFileLoader().loadFile(document4, delayedMessage2.parentObject, 3, 0);
                    return;
                }
            }
            String str9 = str8;
            sendMessagesHelper.putToDelayedMessages(str9, delayedMessage2);
            VideoEditedInfo videoEditedInfo5 = delayedMessage2.obj.videoEditedInfo;
            if (videoEditedInfo5 == null || !videoEditedInfo5.notReadyYet) {
                if (videoEditedInfo5 != null && videoEditedInfo5.needConvert()) {
                    sendMessagesHelper.getFileLoader().uploadFile(str9, true, false, document4.size, 33554432, false);
                } else {
                    sendMessagesHelper.getFileLoader().uploadFile(str9, true, false, 33554432);
                }
            }
            sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
            return;
        }
        if (i3 == 2) {
            String str10 = delayedMessage2.httpLocation;
            if (str10 != null) {
                sendMessagesHelper.putToDelayedMessages(str10, delayedMessage2);
                ImageLoader.getInstance().loadHttpFile(delayedMessage2.httpLocation, "gif", sendMessagesHelper.currentAccount);
                return;
            }
            TLObject tLObject4 = delayedMessage2.sendRequest;
            if (tLObject4 != null) {
                if (tLObject4 instanceof TLRPC.TL_messages_sendMedia) {
                    inputMedia3 = ((TLRPC.TL_messages_sendMedia) tLObject4).media;
                } else if (tLObject4 instanceof TLRPC.TL_messages_addPollAnswer) {
                    inputMedia3 = ((TLRPC.TL_messages_addPollAnswer) tLObject4).answer.input_media;
                } else {
                    inputMedia3 = ((TLRPC.TL_messages_editMessage) tLObject4).media;
                }
                if (inputMedia3.file == null) {
                    String str11 = delayedMessage2.obj.messageOwner.attachPath;
                    sendMessagesHelper.putToDelayedMessages(str11, delayedMessage2);
                    sendMessagesHelper.getFileLoader().uploadFile(str11, delayedMessage2.sendRequest == null, false, 67108864);
                    sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                    return;
                }
                if (inputMedia3.thumb != null || (photoSize = delayedMessage2.photoSize) == null || (photoSize instanceof TLRPC.TL_photoStrippedSize)) {
                    return;
                }
                String str12 = FileLoader.getDirectory(4) + "/" + delayedMessage2.photoSize.location.volume_id + "_" + delayedMessage2.photoSize.location.local_id + ".jpg";
                sendMessagesHelper.putToDelayedMessages(str12, delayedMessage2);
                sendMessagesHelper.getFileLoader().uploadFile(str12, false, true, 16777216);
                sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
                return;
            }
            MessageObject messageObject6 = delayedMessage2.obj;
            String str13 = messageObject6.messageOwner.attachPath;
            TLRPC.Document document5 = messageObject6.getDocument();
            if (delayedMessage2.sendEncryptedRequest != null && document5.dc_id != 0) {
                File file3 = new File(str13);
                if (!file3.exists() && (file3 = sendMessagesHelper.getFileLoader().getPathToMessage(delayedMessage2.obj.messageOwner)) != null && file3.exists()) {
                    TLRPC.Message message3 = delayedMessage2.obj.messageOwner;
                    String absolutePath3 = file3.getAbsolutePath();
                    message3.attachPath = absolutePath3;
                    delayedMessage2.obj.attachPathExists = true;
                    str13 = absolutePath3;
                }
                if ((file3 == null || (!file3.exists() && delayedMessage2.obj.getDocument() != null)) && (file3 = sendMessagesHelper.getFileLoader().getPathToAttach(delayedMessage2.obj.getDocument(), false)) != null && file3.exists()) {
                    TLRPC.Message message4 = delayedMessage2.obj.messageOwner;
                    String absolutePath4 = file3.getAbsolutePath();
                    message4.attachPath = absolutePath4;
                    delayedMessage2.obj.attachPathExists = true;
                    str13 = absolutePath4;
                }
                if (file3 == null || !file3.exists()) {
                    sendMessagesHelper.putToDelayedMessages(FileLoader.getAttachFileName(document5), delayedMessage2);
                    sendMessagesHelper.getFileLoader().loadFile(document5, delayedMessage2.parentObject, 3, 0);
                    return;
                }
            }
            sendMessagesHelper.putToDelayedMessages(str13, delayedMessage2);
            sendMessagesHelper.getFileLoader().uploadFile(str13, true, false, 67108864);
            sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
            return;
        }
        if (i3 == 3) {
            String str14 = delayedMessage2.obj.messageOwner.attachPath;
            sendMessagesHelper.putToDelayedMessages(str14, delayedMessage2);
            sendMessagesHelper.getFileLoader().uploadFile(str14, delayedMessage2.sendRequest == null, true, ConnectionsManager.FileTypeAudio);
            sendMessagesHelper.putToUploadingMessages(delayedMessage2.obj);
            return;
        }
        if (i3 != 4) {
            if (i3 == 5) {
                final String str15 = "stickerset_" + delayedMessage2.obj.getId();
                TLRPC.TL_messages_getStickerSet tL_messages_getStickerSet = new TLRPC.TL_messages_getStickerSet();
                tL_messages_getStickerSet.stickerset = (TLRPC.InputStickerSet) delayedMessage2.parentObject;
                sendMessagesHelper.getConnectionsManager().sendRequest(tL_messages_getStickerSet, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda95
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject5, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$performSendDelayedMessage$52(delayedMessage2, str15, tLObject5, tL_error);
                    }
                });
                sendMessagesHelper.putToDelayedMessages(str15, delayedMessage2);
                return;
            }
            return;
        }
        boolean z4 = i < 0;
        if (delayedMessage2.performMediaUpload || delayedMessage2.performCoverUpload) {
            final int size = i < 0 ? delayedMessage2.messageObjects.size() - 1 : i;
            final MessageObject messageObject7 = delayedMessage2.messageObjects.get(size);
            TLRPC.Document document6 = messageObject7.getDocument();
            if (document6 == null && (MessageObject.getMedia(messageObject7) instanceof TLRPC.TL_messageMediaPaidMedia)) {
                TLRPC.TL_messageMediaPaidMedia tL_messageMediaPaidMedia = (TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject7);
                TLRPC.MessageExtendedMedia messageExtendedMedia = size >= tL_messageMediaPaidMedia.extended_media.size() ? null : tL_messageMediaPaidMedia.extended_media.get(size);
                TLRPC.MessageMedia messageMedia = messageExtendedMedia instanceof TLRPC.TL_messageExtendedMedia ? ((TLRPC.TL_messageExtendedMedia) messageExtendedMedia).media : null;
                document6 = messageMedia == null ? null : messageMedia.document;
            }
            if (document6 == null && (MessageObject.getMedia(messageObject7) instanceof TLRPC.TL_messageMediaPoll)) {
                TLRPC.MessageMedia media = PollAttachedMediaPack.getMedia((TLRPC.TL_messageMediaPoll) MessageObject.getMedia(messageObject7), delayedMessage2.pollIndexes.get(size).intValue());
                document6 = media == null ? null : media.document;
            }
            if (document6 != null) {
                VideoEditedInfo videoEditedInfo6 = delayedMessage2.videoEditedInfo;
                if (videoEditedInfo6 != null && videoEditedInfo6.needConvert() && delayedMessage2.performMediaUpload) {
                    String str16 = messageObject7.messageOwner.attachPath;
                    if (str16 == null) {
                        str16 = FileLoader.getDirectory(4) + "/" + document6.f1253id + ".mp4";
                    }
                    sendMessagesHelper.putToDelayedMessages(str16, delayedMessage2);
                    delayedMessage2.extraHashMap.put(messageObject7, str16);
                    delayedMessage2.extraHashMap.put(str16.concat("_i"), messageObject7);
                    TLRPC.PhotoSize photoSize3 = delayedMessage2.photoSize;
                    if (photoSize3 != null && photoSize3.location != null) {
                        delayedMessage2.extraHashMap.put(str16.concat("_t"), delayedMessage2.photoSize);
                    }
                    TLRPC.PhotoSize photoSize4 = delayedMessage2.coverPhotoSize;
                    if (photoSize4 != null && photoSize4.location != null) {
                        delayedMessage2.extraHashMap.put(str16.concat("_ct"), delayedMessage2.coverPhotoSize);
                    }
                    if (!delayedMessage2.videoEditedInfo.alreadyScheduledConverting) {
                        MediaController.getInstance().scheduleVideoConvert(messageObject7);
                    }
                    delayedMessage2.obj = messageObject7;
                    sendMessagesHelper.putToUploadingMessages(messageObject7);
                } else {
                    String string4 = messageObject7.messageOwner.attachPath;
                    if (string4 == null) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(FileLoader.getDirectory(4));
                        sb2.append("/");
                        str = "_i";
                        sb2.append(document6.f1253id);
                        sb2.append(".mp4");
                        string4 = sb2.toString();
                    } else {
                        str = "_i";
                    }
                    final String str17 = string4;
                    TLObject tLObject5 = delayedMessage2.sendRequest;
                    if (tLObject5 != null) {
                        if (tLObject5 instanceof TLRPC.TL_messages_sendMultiMedia) {
                            TLRPC.TL_messages_sendMultiMedia tL_messages_sendMultiMedia = (TLRPC.TL_messages_sendMultiMedia) tLObject5;
                            inputPeer = tL_messages_sendMultiMedia.peer;
                            inputMedia2 = tL_messages_sendMultiMedia.multi_media.get(size).media;
                        } else if (tLObject5 instanceof TLRPC.TL_messages_sendMedia) {
                            TLRPC.TL_messages_sendMedia tL_messages_sendMedia2 = (TLRPC.TL_messages_sendMedia) tLObject5;
                            inputPeer = tL_messages_sendMedia2.peer;
                            TLRPC.InputMedia inputMedia6 = tL_messages_sendMedia2.media;
                            if (inputMedia6 instanceof TLRPC.TL_inputMediaPaidMedia) {
                                inputMedia2 = ((TLRPC.TL_inputMediaPaidMedia) inputMedia6).extended_media.get(size);
                            } else {
                                inputMedia2 = inputMedia6 instanceof TLRPC.TL_inputMediaPoll ? PollAttachedMediaPack.getInputMedia((TLRPC.TL_inputMediaPoll) inputMedia6, delayedMessage2.pollIndexes.get(size).intValue()) : null;
                            }
                        } else {
                            inputMedia2 = null;
                            inputPeer = null;
                        }
                        if (inputMedia2 != null && inputMedia2.file == null && !(inputMedia2 instanceof TLRPC.TL_inputMediaDocument) && delayedMessage2.performMediaUpload) {
                            sendMessagesHelper.putToDelayedMessages(str17, delayedMessage2);
                            delayedMessage2.extraHashMap.put(messageObject7, str17);
                            delayedMessage2.extraHashMap.put(str17, inputMedia2);
                            delayedMessage2.extraHashMap.put(str17.concat(str), messageObject7);
                            TLRPC.PhotoSize photoSize5 = delayedMessage2.photoSize;
                            if (photoSize5 != null && photoSize5.location != null) {
                                delayedMessage2.extraHashMap.put(str17.concat("_t"), delayedMessage2.photoSize);
                            }
                            TLRPC.PhotoSize photoSize6 = delayedMessage2.coverPhotoSize;
                            if (photoSize6 != null && photoSize6.location != null) {
                                String str18 = FileLoader.getDirectory(4) + "/" + delayedMessage2.coverPhotoSize.location.volume_id + "_" + delayedMessage2.coverPhotoSize.location.local_id + ".jpg";
                                delayedMessage2.extraHashMap.put(str17.concat("_ct"), delayedMessage2.coverPhotoSize);
                                delayedMessage2.extraHashMap.put(str18.concat("_doc"), str17);
                            }
                            VideoEditedInfo videoEditedInfo7 = messageObject7.videoEditedInfo;
                            if (videoEditedInfo7 != null && videoEditedInfo7.needConvert()) {
                                sendMessagesHelper.getFileLoader().uploadFile(str17, false, false, document6.size, 33554432, false);
                            } else {
                                sendMessagesHelper.getFileLoader().uploadFile(str17, false, false, 33554432);
                            }
                            sendMessagesHelper.putToUploadingMessages(messageObject7);
                        } else {
                            String str19 = str;
                            TLRPC.PhotoSize photoSize7 = delayedMessage2.coverPhotoSize;
                            if (photoSize7 != null && delayedMessage2.coverFile == null && inputMedia2 != null && inputMedia2.video_cover == null) {
                                String str20 = FileLoader.getDirectory(4) + "/" + delayedMessage2.coverPhotoSize.location.volume_id + "_" + delayedMessage2.coverPhotoSize.location.local_id + ".jpg";
                                sendMessagesHelper.putToDelayedMessages(str20, delayedMessage2);
                                TLRPC.PhotoSize photoSize8 = delayedMessage2.coverPhotoSize;
                                if (photoSize8 == null || photoSize8.location == null) {
                                    z = z4;
                                } else {
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(FileLoader.getDirectory(4));
                                    sb3.append("/");
                                    z = z4;
                                    sb3.append(delayedMessage2.coverPhotoSize.location.volume_id);
                                    sb3.append("_");
                                    sb3.append(delayedMessage2.coverPhotoSize.location.local_id);
                                    sb3.append(".jpg");
                                    String string5 = sb3.toString();
                                    delayedMessage2.extraHashMap.put(str17.concat("_ct"), delayedMessage2.coverPhotoSize);
                                    delayedMessage2.extraHashMap.put(string5.concat("_doc"), str17);
                                }
                                delayedMessage2.extraHashMap.put(str20.concat("_o"), str17);
                                delayedMessage2.extraHashMap.put(str17.concat(str19), messageObject7);
                                delayedMessage2.extraHashMap.put(messageObject7, str20);
                                delayedMessage2.extraHashMap.put(str20, inputMedia2);
                                sendMessagesHelper.getFileLoader().uploadFile(str20, false, true, 16777216);
                                sendMessagesHelper.putToUploadingMessages(messageObject7);
                            } else {
                                z = z4;
                                if (photoSize7 != null && delayedMessage2.coverFile != null && inputMedia2 != null && inputMedia2.video_cover == null && (inputMedia2 instanceof TLRPC.TL_inputMediaUploadedDocument) && inputMedia2.file != null && (delayedMessage2.sendRequest instanceof TLRPC.TL_messages_sendMultiMedia) && (arrayList = delayedMessage2.livePhotoIndexes) != null && size >= 0 && size < arrayList.size() && Boolean.TRUE.equals(delayedMessage2.livePhotoIndexes.get(size))) {
                                    final TLRPC.InputFile inputFile4 = delayedMessage2.coverFile;
                                    inputMedia2.flags &= -65;
                                    inputMedia2.video_cover = null;
                                    TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia3 = new TLRPC.TL_messages_uploadMedia();
                                    tL_messages_uploadMedia3.peer = inputPeer;
                                    tL_messages_uploadMedia3.media = inputMedia2;
                                    ConnectionsManager connectionsManager = sendMessagesHelper.getConnectionsManager();
                                    final TLRPC.InputMedia inputMedia7 = inputMedia2;
                                    RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda93
                                        @Override // org.telegram.tgnet.RequestDelegate
                                        public final void run(TLObject tLObject6, TLRPC.TL_error tL_error) {
                                            this.f$0.lambda$performSendDelayedMessage$48(inputFile4, inputMedia7, delayedMessage, size, str17, tLObject6, tL_error);
                                        }
                                    };
                                    delayedMessage2 = delayedMessage;
                                    connectionsManager.sendRequest(tL_messages_uploadMedia3, requestDelegate);
                                    sendMessagesHelper = this;
                                } else if (delayedMessage2.coverPhotoSize != null && delayedMessage2.coverFile != null && inputMedia2 != null && inputMedia2.video_cover == null) {
                                    TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia4 = new TLRPC.TL_messages_uploadMedia();
                                    tL_messages_uploadMedia4.peer = inputPeer;
                                    TLRPC.TL_inputMediaUploadedPhoto tL_inputMediaUploadedPhoto2 = new TLRPC.TL_inputMediaUploadedPhoto();
                                    tL_inputMediaUploadedPhoto2.file = delayedMessage2.coverFile;
                                    tL_messages_uploadMedia4.media = tL_inputMediaUploadedPhoto2;
                                    sendMessagesHelper = this;
                                    getConnectionsManager().sendRequest(tL_messages_uploadMedia4, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda94
                                        @Override // org.telegram.tgnet.RequestDelegate
                                        public final void run(TLObject tLObject6, TLRPC.TL_error tL_error) {
                                            this.f$0.lambda$performSendDelayedMessage$50(inputMedia2, delayedMessage2, str17, messageObject7, tLObject6, tL_error);
                                        }
                                    });
                                } else {
                                    sendMessagesHelper = this;
                                    if (delayedMessage2.photoSize != null) {
                                        String str21 = FileLoader.getDirectory(4) + "/" + delayedMessage2.photoSize.location.volume_id + "_" + delayedMessage2.photoSize.location.local_id + ".jpg";
                                        sendMessagesHelper.putToDelayedMessages(str21, delayedMessage2);
                                        delayedMessage2.extraHashMap.put(str21.concat("_o"), str17);
                                        delayedMessage2.extraHashMap.put(messageObject7, str21);
                                        delayedMessage2.extraHashMap.put(str21, inputMedia2);
                                        sendMessagesHelper.getFileLoader().uploadFile(str21, false, true, 16777216);
                                        sendMessagesHelper.putToUploadingMessages(messageObject7);
                                    }
                                }
                            }
                        }
                    } else {
                        z = z4;
                        TLRPC.TL_messages_sendEncryptedMultiMedia tL_messages_sendEncryptedMultiMedia = (TLRPC.TL_messages_sendEncryptedMultiMedia) delayedMessage2.sendEncryptedRequest;
                        sendMessagesHelper.putToDelayedMessages(str17, delayedMessage2);
                        delayedMessage2.extraHashMap.put(messageObject7, str17);
                        delayedMessage2.extraHashMap.put(str17, tL_messages_sendEncryptedMultiMedia.files.get(size));
                        delayedMessage2.extraHashMap.put(str17.concat(str), messageObject7);
                        TLRPC.PhotoSize photoSize9 = delayedMessage2.photoSize;
                        if (photoSize9 != null && photoSize9.location != null) {
                            delayedMessage2.extraHashMap.put(str17.concat("_t"), delayedMessage2.photoSize);
                        }
                        VideoEditedInfo videoEditedInfo8 = messageObject7.videoEditedInfo;
                        if (videoEditedInfo8 != null && videoEditedInfo8.needConvert()) {
                            sendMessagesHelper.getFileLoader().uploadFile(str17, true, false, document6.size, 33554432, false);
                        } else {
                            sendMessagesHelper.getFileLoader().uploadFile(str17, true, false, 33554432);
                        }
                        sendMessagesHelper.putToUploadingMessages(messageObject7);
                    }
                    inputFile = null;
                    delayedMessage2.videoEditedInfo = null;
                    delayedMessage2.photoSize = null;
                    delayedMessage2.coverPhotoSize = null;
                }
                z = z4;
                inputFile = null;
                delayedMessage2.videoEditedInfo = null;
                delayedMessage2.photoSize = null;
                delayedMessage2.coverPhotoSize = null;
            } else {
                int i5 = size;
                z = z4;
                String str22 = delayedMessage2.httpLocation;
                if (str22 != null) {
                    sendMessagesHelper.putToDelayedMessages(str22, delayedMessage2);
                    delayedMessage2.extraHashMap.put(messageObject7, delayedMessage2.httpLocation);
                    delayedMessage2.extraHashMap.put(delayedMessage2.httpLocation, messageObject7);
                    ImageLoader.getInstance().loadHttpFile(delayedMessage2.httpLocation, "file", sendMessagesHelper.currentAccount);
                    inputFile = null;
                    delayedMessage2.httpLocation = null;
                } else {
                    TLObject tLObject6 = delayedMessage2.sendRequest;
                    if (tLObject6 instanceof TLRPC.TL_messages_sendMultiMedia) {
                        inputMedia = ((TLRPC.TL_messages_sendMultiMedia) tLObject6).multi_media.get(i5).media;
                    } else if ((tLObject6 instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject6).media instanceof TLRPC.TL_inputMediaPaidMedia)) {
                        inputMedia = ((TLRPC.TL_inputMediaPaidMedia) ((TLRPC.TL_messages_sendMedia) tLObject6).media).extended_media.get(i5);
                    } else if ((tLObject6 instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject6).media instanceof TLRPC.TL_inputMediaPoll)) {
                        inputMedia = PollAttachedMediaPack.getInputMedia((TLRPC.TL_inputMediaPoll) ((TLRPC.TL_messages_sendMedia) tLObject6).media, delayedMessage2.pollIndexes.get(i5).intValue());
                    } else {
                        inputMedia = ((TLRPC.TL_messages_sendEncryptedMultiMedia) delayedMessage2.sendEncryptedRequest).files.get(i5);
                    }
                    String string6 = FileLoader.getInstance(sendMessagesHelper.currentAccount).getPathToAttach(delayedMessage2.photoSize).toString();
                    sendMessagesHelper.putToDelayedMessages(string6, delayedMessage2);
                    delayedMessage2.extraHashMap.put(string6, inputMedia);
                    delayedMessage2.extraHashMap.put(messageObject7, string6);
                    z2 = true;
                    sendMessagesHelper.getFileLoader().uploadFile(string6, delayedMessage2.sendEncryptedRequest != null, true, 16777216);
                    sendMessagesHelper.putToUploadingMessages(messageObject7);
                    inputFile = null;
                    delayedMessage2.photoSize = null;
                    delayedMessage2.coverFile = inputFile;
                    delayedMessage2.performMediaUpload = false;
                    delayedMessage2.performCoverUpload = false;
                    z3 = z;
                }
            }
            z2 = true;
            delayedMessage2.coverFile = inputFile;
            delayedMessage2.performMediaUpload = false;
            delayedMessage2.performCoverUpload = false;
            z3 = z;
        } else {
            if (!delayedMessage2.messageObjects.isEmpty()) {
                ArrayList<MessageObject> arrayList2 = delayedMessage2.messageObjects;
                sendMessagesHelper.putToSendingMessages(arrayList2.get(arrayList2.size() - 1).messageOwner, delayedMessage2.finalGroupMessage != 0);
            }
            z2 = true;
            z3 = z4;
        }
        sendMessagesHelper.sendReadyToSendGroup(delayedMessage2, z3, z2);
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$44(final DelayedMessage delayedMessage, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendDelayedMessage$43(tLObject, delayedMessage);
            }
        });
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$43(TLObject tLObject, DelayedMessage delayedMessage) {
        if (tLObject instanceof TLRPC.TL_messageMediaDocument) {
            TLRPC.TL_inputMediaUploadedPhoto tL_inputMediaUploadedPhoto = new TLRPC.TL_inputMediaUploadedPhoto();
            tL_inputMediaUploadedPhoto.live_photo = true;
            tL_inputMediaUploadedPhoto.file = delayedMessage.coverFile;
            TLRPC.TL_inputDocument tL_inputDocument = new TLRPC.TL_inputDocument();
            tL_inputMediaUploadedPhoto.video = tL_inputDocument;
            TLRPC.Document document = ((TLRPC.TL_messageMediaDocument) tLObject).document;
            tL_inputDocument.f1262id = document.f1253id;
            tL_inputDocument.access_hash = document.access_hash;
            tL_inputDocument.file_reference = document.file_reference;
            TLObject tLObject2 = delayedMessage.sendRequest;
            if (tLObject2 instanceof TLRPC.TL_messages_sendMedia) {
                ((TLRPC.TL_messages_sendMedia) tLObject2).media = tL_inputMediaUploadedPhoto;
            } else if (tLObject2 instanceof TLRPC.TL_messages_addPollAnswer) {
                ((TLRPC.TL_messages_addPollAnswer) tLObject2).answer.input_media = tL_inputMediaUploadedPhoto;
            } else {
                ((TLRPC.TL_messages_editMessage) tLObject2).media = tL_inputMediaUploadedPhoto;
            }
            performSendMessageRequest(tLObject2, delayedMessage.obj, delayedMessage.originalPath, delayedMessage, delayedMessage.parentObject, null, delayedMessage.scheduled);
            return;
        }
        delayedMessage.markAsError();
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$46(final TLRPC.InputMedia inputMedia, final DelayedMessage delayedMessage, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda125
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendDelayedMessage$45(tLObject, inputMedia, delayedMessage);
            }
        });
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$45(TLObject tLObject, TLRPC.InputMedia inputMedia, DelayedMessage delayedMessage) {
        TLRPC.PhotoSize photoSize;
        MessageObject messageObject;
        VideoEditedInfo videoEditedInfo;
        if (tLObject instanceof TLRPC.TL_messageMediaPhoto) {
            TLRPC.Photo photo = ((TLRPC.TL_messageMediaPhoto) tLObject).photo;
            TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
            tL_inputPhoto.f1269id = photo.f1276id;
            tL_inputPhoto.access_hash = photo.access_hash;
            tL_inputPhoto.file_reference = photo.file_reference;
            if (inputMedia instanceof TLRPC.TL_inputMediaUploadedDocument) {
                inputMedia.flags |= 64;
                inputMedia.video_cover = tL_inputPhoto;
            } else if (inputMedia instanceof TLRPC.TL_inputMediaDocument) {
                inputMedia.flags |= 8;
                inputMedia.video_cover = tL_inputPhoto;
            }
            TLRPC.InputMedia inputMedia2 = delayedMessage.inputUploadMedia;
            if (inputMedia2 instanceof TLRPC.TL_inputMediaUploadedDocument) {
                inputMedia2.flags |= 64;
                inputMedia2.video_cover = tL_inputPhoto;
            }
            if (delayedMessage.performMediaUpload && inputMedia.thumb == null && (photoSize = delayedMessage.photoSize) != null && photoSize.location != null && ((messageObject = delayedMessage.obj) == null || (videoEditedInfo = messageObject.videoEditedInfo) == null || !videoEditedInfo.isSticker)) {
                performSendDelayedMessage(delayedMessage);
                return;
            } else {
                performSendMessageRequest(delayedMessage.sendRequest, delayedMessage.obj, delayedMessage.originalPath, delayedMessage, delayedMessage.parentObject, null, delayedMessage.scheduled);
                return;
            }
        }
        delayedMessage.markAsError();
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$48(final TLRPC.InputFile inputFile, final TLRPC.InputMedia inputMedia, final DelayedMessage delayedMessage, final int i, final String str, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendDelayedMessage$47(tLObject, inputFile, inputMedia, delayedMessage, i, str);
            }
        });
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$47(TLObject tLObject, TLRPC.InputFile inputFile, TLRPC.InputMedia inputMedia, DelayedMessage delayedMessage, int i, String str) {
        if (tLObject instanceof TLRPC.TL_messageMediaDocument) {
            TLRPC.TL_inputMediaUploadedPhoto tL_inputMediaUploadedPhoto = new TLRPC.TL_inputMediaUploadedPhoto();
            tL_inputMediaUploadedPhoto.live_photo = true;
            tL_inputMediaUploadedPhoto.file = inputFile;
            tL_inputMediaUploadedPhoto.spoiler = inputMedia.spoiler;
            TLRPC.TL_inputDocument tL_inputDocument = new TLRPC.TL_inputDocument();
            tL_inputMediaUploadedPhoto.video = tL_inputDocument;
            TLRPC.Document document = ((TLRPC.TL_messageMediaDocument) tLObject).document;
            tL_inputDocument.f1262id = document.f1253id;
            tL_inputDocument.access_hash = document.access_hash;
            byte[] bArr = document.file_reference;
            tL_inputDocument.file_reference = bArr;
            if (bArr == null) {
                tL_inputDocument.file_reference = new byte[0];
            }
            TLRPC.TL_messages_sendMultiMedia tL_messages_sendMultiMedia = (TLRPC.TL_messages_sendMultiMedia) delayedMessage.sendRequest;
            if (i >= 0 && i < tL_messages_sendMultiMedia.multi_media.size()) {
                tL_messages_sendMultiMedia.multi_media.get(i).media = tL_inputMediaUploadedPhoto;
            }
            ArrayList<TLRPC.InputMedia> arrayList = delayedMessage.inputMedias;
            if (arrayList != null && i >= 0 && i < arrayList.size()) {
                delayedMessage.inputMedias.set(i, tL_inputMediaUploadedPhoto);
            }
            delayedMessage.coverFile = null;
            delayedMessage.coverPhotoSize = null;
            HashMap<Object, Object> map = delayedMessage.extraHashMap;
            if (map != null) {
                map.remove(str + "_ct");
            }
            uploadMultiMedia(delayedMessage, tL_inputMediaUploadedPhoto, null, str);
            return;
        }
        delayedMessage.markAsError();
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$50(final TLRPC.InputMedia inputMedia, final DelayedMessage delayedMessage, final String str, final MessageObject messageObject, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendDelayedMessage$49(tLObject, inputMedia, delayedMessage, str, messageObject);
            }
        });
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$49(TLObject tLObject, TLRPC.InputMedia inputMedia, DelayedMessage delayedMessage, String str, MessageObject messageObject) {
        if (tLObject instanceof TLRPC.TL_messageMediaPhoto) {
            TLRPC.Photo photo = ((TLRPC.TL_messageMediaPhoto) tLObject).photo;
            TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
            tL_inputPhoto.f1269id = photo.f1276id;
            tL_inputPhoto.access_hash = photo.access_hash;
            tL_inputPhoto.file_reference = photo.file_reference;
            if (inputMedia instanceof TLRPC.TL_inputMediaUploadedDocument) {
                inputMedia.flags |= 64;
                inputMedia.video_cover = tL_inputPhoto;
            } else if (inputMedia instanceof TLRPC.TL_inputMediaDocument) {
                inputMedia.flags |= 8;
                inputMedia.video_cover = tL_inputPhoto;
            }
            TLRPC.PhotoSize photoSize = null;
            delayedMessage.coverFile = null;
            delayedMessage.coverPhotoSize = null;
            HashMap<Object, Object> map = delayedMessage.extraHashMap;
            if (map != null) {
                map.remove(str + "_ct");
            }
            int iIndexOf = delayedMessage.messageObjects.indexOf(messageObject);
            ArrayList<TLRPC.InputMedia> arrayList = delayedMessage.inputMedias;
            if (arrayList != null && iIndexOf >= 0 && iIndexOf < arrayList.size()) {
                TLRPC.InputMedia inputMedia2 = delayedMessage.inputMedias.get(iIndexOf);
                if (inputMedia2 instanceof TLRPC.TL_inputMediaUploadedDocument) {
                    inputMedia2.flags |= 64;
                    inputMedia2.video_cover = tL_inputPhoto;
                }
            }
            HashMap<Object, Object> map2 = delayedMessage.extraHashMap;
            if (map2 != null) {
                if (map2.containsKey(str + "_t")) {
                    photoSize = (TLRPC.PhotoSize) delayedMessage.extraHashMap.get(str + "_t");
                }
            }
            delayedMessage.photoSize = photoSize;
            if (inputMedia.thumb == null && photoSize != null && photoSize.location != null) {
                delayedMessage.performMediaUpload = true;
                performSendDelayedMessage(delayedMessage, iIndexOf);
                return;
            } else {
                sendReadyToSendGroup(delayedMessage, false, true);
                return;
            }
        }
        delayedMessage.markAsError();
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$52(final DelayedMessage delayedMessage, final String str, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendDelayedMessage$51(tLObject, delayedMessage, str);
            }
        });
    }

    public /* synthetic */ void lambda$performSendDelayedMessage$51(TLObject tLObject, DelayedMessage delayedMessage, String str) {
        boolean z;
        if (tLObject != null) {
            TLRPC.TL_messages_stickerSet tL_messages_stickerSet = (TLRPC.TL_messages_stickerSet) tLObject;
            getMediaDataController().storeTempStickerSet(tL_messages_stickerSet);
            TLRPC.TL_documentAttributeSticker_layer55 tL_documentAttributeSticker_layer55 = (TLRPC.TL_documentAttributeSticker_layer55) delayedMessage.locationParent;
            TLRPC.TL_inputStickerSetShortName tL_inputStickerSetShortName = new TLRPC.TL_inputStickerSetShortName();
            tL_documentAttributeSticker_layer55.stickerset = tL_inputStickerSetShortName;
            tL_inputStickerSetShortName.short_name = tL_messages_stickerSet.set.short_name;
            z = true;
        } else {
            z = false;
        }
        ArrayList<DelayedMessage> arrayListRemove = this.delayedMessages.remove(str);
        if (arrayListRemove == null || arrayListRemove.isEmpty()) {
            return;
        }
        if (z) {
            getMessagesStorage().replaceMessageIfExists(arrayListRemove.get(0).obj.messageOwner, null, null, false);
        }
        SecretChatHelper secretChatHelper = getSecretChatHelper();
        TLRPC.DecryptedMessage decryptedMessage = (TLRPC.DecryptedMessage) delayedMessage.sendEncryptedRequest;
        MessageObject messageObject = delayedMessage.obj;
        secretChatHelper.performSendEncryptedRequest(decryptedMessage, messageObject.messageOwner, delayedMessage.encryptedChat, null, null, messageObject);
    }

    private void uploadMultiMedia(final DelayedMessage delayedMessage, final TLRPC.InputMedia inputMedia, TLRPC.InputEncryptedFile inputEncryptedFile, String str) {
        int i = 0;
        if (inputMedia == null) {
            if (inputEncryptedFile != null) {
                TLRPC.TL_messages_sendEncryptedMultiMedia tL_messages_sendEncryptedMultiMedia = (TLRPC.TL_messages_sendEncryptedMultiMedia) delayedMessage.sendEncryptedRequest;
                int i2 = 0;
                while (true) {
                    if (i2 >= tL_messages_sendEncryptedMultiMedia.files.size()) {
                        break;
                    }
                    if (tL_messages_sendEncryptedMultiMedia.files.get(i2) == inputEncryptedFile) {
                        putToSendingMessages(delayedMessage.messages.get(i2), delayedMessage.scheduled);
                        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.fileUploadProgressChanged, str, -1L, -1L, Boolean.FALSE);
                        break;
                    }
                    i2++;
                }
                sendReadyToSendGroup(delayedMessage, false, true);
                return;
            }
            return;
        }
        TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia = new TLRPC.TL_messages_uploadMedia();
        tL_messages_uploadMedia.media = inputMedia;
        TLObject tLObject = delayedMessage.sendRequest;
        if (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) {
            TLRPC.TL_messages_sendMultiMedia tL_messages_sendMultiMedia = (TLRPC.TL_messages_sendMultiMedia) tLObject;
            tL_messages_uploadMedia.peer = tL_messages_sendMultiMedia.peer;
            while (true) {
                if (i >= tL_messages_sendMultiMedia.multi_media.size()) {
                    break;
                }
                if (tL_messages_sendMultiMedia.multi_media.get(i).media == inputMedia) {
                    putToSendingMessages(delayedMessage.messages.get(i), delayedMessage.scheduled);
                    getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.fileUploadProgressChanged, str, -1L, -1L, Boolean.FALSE);
                    break;
                }
                i++;
            }
        } else if ((tLObject instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject).media instanceof TLRPC.TL_inputMediaPaidMedia)) {
            TLRPC.TL_messages_sendMedia tL_messages_sendMedia = (TLRPC.TL_messages_sendMedia) tLObject;
            tL_messages_uploadMedia.peer = tL_messages_sendMedia.peer;
            TLRPC.TL_inputMediaPaidMedia tL_inputMediaPaidMedia = (TLRPC.TL_inputMediaPaidMedia) tL_messages_sendMedia.media;
            while (true) {
                if (i >= tL_inputMediaPaidMedia.extended_media.size()) {
                    break;
                }
                if (tL_inputMediaPaidMedia.extended_media.get(i) == inputMedia) {
                    putToSendingMessages(delayedMessage.messages.get(i), delayedMessage.scheduled);
                    getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.fileUploadProgressChanged, str, -1L, -1L, Boolean.FALSE);
                    break;
                }
                i++;
            }
        } else if ((tLObject instanceof TLRPC.TL_messages_sendMedia) && (((TLRPC.TL_messages_sendMedia) tLObject).media instanceof TLRPC.TL_inputMediaPoll)) {
            TLRPC.TL_messages_sendMedia tL_messages_sendMedia2 = (TLRPC.TL_messages_sendMedia) tLObject;
            tL_messages_uploadMedia.peer = tL_messages_sendMedia2.peer;
            int iFindInputMedia = PollAttachedMediaPack.findInputMedia((TLRPC.TL_inputMediaPoll) tL_messages_sendMedia2.media, inputMedia);
            while (true) {
                if (i >= delayedMessage.pollIndexes.size()) {
                    break;
                }
                if (delayedMessage.pollIndexes.get(i).intValue() == iFindInputMedia) {
                    putToSendingMessages(delayedMessage.messages.get(i), delayedMessage.scheduled);
                    getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.fileUploadProgressChanged, str, -1L, -1L, Boolean.FALSE);
                    break;
                }
                i++;
            }
        }
        getConnectionsManager().sendRequest(tL_messages_uploadMedia, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda50
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                this.f$0.lambda$uploadMultiMedia$54(inputMedia, delayedMessage, tLObject2, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$uploadMultiMedia$54(final TLRPC.InputMedia inputMedia, final DelayedMessage delayedMessage, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda119
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$uploadMultiMedia$53(tLObject, inputMedia, delayedMessage);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:90:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$uploadMultiMedia$53(org.telegram.tgnet.TLObject r7, org.telegram.tgnet.TLRPC.InputMedia r8, org.telegram.messenger.SendMessagesHelper.DelayedMessage r9) {
        /*
            Method dump skipped, instruction units count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$uploadMultiMedia$53(org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$InputMedia, org.telegram.messenger.SendMessagesHelper$DelayedMessage):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:210:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendReadyToSendGroup(org.telegram.messenger.SendMessagesHelper.DelayedMessage r17, boolean r18, boolean r19) {
        /*
            Method dump skipped, instruction units count: 693
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.sendReadyToSendGroup(org.telegram.messenger.SendMessagesHelper$DelayedMessage, boolean, boolean):void");
    }

    public void putToSendingMessages(final TLRPC.Message message, final boolean z) {
        if (Thread.currentThread() != ApplicationLoader.applicationHandler.getLooper().getThread()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda127
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$putToSendingMessages$55(message, z);
                }
            });
        } else {
            putToSendingMessages(message, z, true);
        }
    }

    public /* synthetic */ void lambda$putToSendingMessages$55(TLRPC.Message message, boolean z) {
        putToSendingMessages(message, z, true);
    }

    public void putToSendingMessages(TLRPC.Message message, boolean z, boolean z2) {
        if (message == null) {
            return;
        }
        int i = message.f1271id;
        if (i > 0) {
            this.editingMessages.put(i, message);
            return;
        }
        boolean z3 = this.sendingMessages.indexOfKey(i) >= 0;
        removeFromUploadingMessages(message.f1271id, z);
        this.sendingMessages.put(message.f1271id, message);
        if (z || z3) {
            return;
        }
        long dialogId = MessageObject.getDialogId(message);
        LongSparseArray<Integer> longSparseArray = this.sendingMessagesIdDialogs;
        longSparseArray.put(dialogId, Integer.valueOf(longSparseArray.get(dialogId, 0).intValue() + 1));
        if (z2) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.sendingMessagesChanged, new Object[0]);
        }
    }

    public TLRPC.Message removeFromSendingMessages(int i, boolean z) {
        long dialogId;
        Integer num;
        if (i > 0) {
            TLRPC.Message message = this.editingMessages.get(i);
            if (message != null) {
                this.editingMessages.remove(i);
            }
            return message;
        }
        TLRPC.Message message2 = this.sendingMessages.get(i);
        if (message2 != null) {
            this.sendingMessages.remove(i);
            if (!z && (num = this.sendingMessagesIdDialogs.get((dialogId = MessageObject.getDialogId(message2)))) != null) {
                int iIntValue = num.intValue() - 1;
                LongSparseArray<Integer> longSparseArray = this.sendingMessagesIdDialogs;
                if (iIntValue <= 0) {
                    longSparseArray.remove(dialogId);
                } else {
                    longSparseArray.put(dialogId, Integer.valueOf(iIntValue));
                }
                getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.sendingMessagesChanged, new Object[0]);
            }
        }
        return message2;
    }

    public int getSendingMessageId(long j) {
        for (int i = 0; i < this.sendingMessages.size(); i++) {
            TLRPC.Message messageValueAt = this.sendingMessages.valueAt(i);
            if (messageValueAt.dialog_id == j) {
                return messageValueAt.f1271id;
            }
        }
        for (int i2 = 0; i2 < this.uploadMessages.size(); i2++) {
            TLRPC.Message messageValueAt2 = this.uploadMessages.valueAt(i2);
            if (messageValueAt2.dialog_id == j) {
                return messageValueAt2.f1271id;
            }
        }
        return 0;
    }

    public void putToUploadingMessages(MessageObject messageObject) {
        if (messageObject == null || messageObject.getId() > 0 || messageObject.scheduled) {
            return;
        }
        TLRPC.Message message = messageObject.messageOwner;
        boolean z = this.uploadMessages.indexOfKey(message.f1271id) >= 0;
        this.uploadMessages.put(message.f1271id, message);
        if (z) {
            return;
        }
        long dialogId = MessageObject.getDialogId(message);
        LongSparseArray<Integer> longSparseArray = this.uploadingMessagesIdDialogs;
        longSparseArray.put(dialogId, Integer.valueOf(longSparseArray.get(dialogId, 0).intValue() + 1));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.sendingMessagesChanged, new Object[0]);
    }

    public void removeFromUploadingMessages(int i, boolean z) {
        TLRPC.Message message;
        if (i > 0 || z || (message = this.uploadMessages.get(i)) == null) {
            return;
        }
        this.uploadMessages.remove(i);
        long dialogId = MessageObject.getDialogId(message);
        Integer num = this.uploadingMessagesIdDialogs.get(dialogId);
        if (num != null) {
            int iIntValue = num.intValue() - 1;
            LongSparseArray<Integer> longSparseArray = this.uploadingMessagesIdDialogs;
            if (iIntValue <= 0) {
                longSparseArray.remove(dialogId);
            } else {
                longSparseArray.put(dialogId, Integer.valueOf(iIntValue));
            }
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.sendingMessagesChanged, new Object[0]);
        }
    }

    public boolean isSendingMessage(int i) {
        return this.sendingMessages.indexOfKey(i) >= 0 || this.editingMessages.indexOfKey(i) >= 0;
    }

    public boolean isSendingPaidMessage(int i, int i2) {
        HashMap<String, ArrayList<DelayedMessage>> map = this.delayedMessages;
        DelayedMessage delayedMessage = null;
        if (map != null) {
            for (ArrayList<DelayedMessage> arrayList : map.values()) {
                if (arrayList != null) {
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        DelayedMessage delayedMessage2 = arrayList.get(i3);
                        i3++;
                        DelayedMessage delayedMessage3 = delayedMessage2;
                        ArrayList<TLRPC.Message> arrayList2 = delayedMessage3.messages;
                        if (arrayList2 != null) {
                            int size2 = arrayList2.size();
                            int i4 = 0;
                            while (true) {
                                if (i4 >= size2) {
                                    break;
                                }
                                TLRPC.Message message = arrayList2.get(i4);
                                i4++;
                                TLRPC.Message message2 = message;
                                if (message2 != null && message2.f1271id == i) {
                                    delayedMessage = delayedMessage3;
                                    break;
                                }
                            }
                            if (delayedMessage != null) {
                                break;
                            }
                        }
                    }
                    if (delayedMessage != null) {
                        break;
                    }
                }
            }
        }
        if (delayedMessage != null && i2 >= 0 && i2 < delayedMessage.messages.size()) {
            i = delayedMessage.messages.get(i2).f1271id;
        }
        return this.sendingMessages.indexOfKey(i) >= 0 || this.editingMessages.indexOfKey(i) >= 0;
    }

    public boolean isSendingMessageIdDialog(long j) {
        return this.sendingMessagesIdDialogs.get(j, 0).intValue() > 0;
    }

    public boolean isUploadingMessageIdDialog(long j) {
        return this.uploadingMessagesIdDialogs.get(j, 0).intValue() > 0;
    }

    /* JADX INFO: renamed from: performSendMessageRequestMulti, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$performSendMessageRequestMulti$57(final TLObject tLObject, final ArrayList<MessageObject> arrayList, final ArrayList<String> arrayList2, final ArrayList<Object> arrayList3, final DelayedMessage delayedMessage, final boolean z) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            putToSendingMessages(arrayList.get(i).messageOwner, z);
        }
        if (StarsController.getInstance(this.currentAccount).beforeSendingFinalRequest(tLObject, arrayList, new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda78
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequestMulti$56(tLObject, arrayList, arrayList2, arrayList3, delayedMessage, z);
            }
        }) && BotForumHelper.getInstance(this.currentAccount).beforeSendingFinalRequest(tLObject, arrayList, new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda79
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequestMulti$57(tLObject, arrayList, arrayList2, arrayList3, delayedMessage, z);
            }
        })) {
            getConnectionsManager().sendRequest(tLObject, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda80
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$performSendMessageRequestMulti$66(arrayList3, tLObject, arrayList, arrayList2, delayedMessage, z, tLObject2, tL_error);
                }
            }, (QuickAckDelegate) null, 68);
        }
    }

    public /* synthetic */ void lambda$performSendMessageRequestMulti$66(ArrayList arrayList, final TLObject tLObject, final ArrayList arrayList2, final ArrayList arrayList3, final DelayedMessage delayedMessage, final boolean z, final TLObject tLObject2, final TLRPC.TL_error tL_error) {
        if (tL_error != null && FileRefController.isFileRefError(tL_error.text)) {
            final int fileRefErrorIndex = FileRefController.getFileRefErrorIndex(tL_error.text);
            if (arrayList != null) {
                ArrayList arrayList4 = new ArrayList(arrayList);
                if (fileRefErrorIndex >= 0) {
                    int i = 0;
                    while (i < arrayList4.size()) {
                        arrayList4.set(i, fileRefErrorIndex == i ? arrayList4.get(i) : null);
                        i++;
                    }
                }
                getFileRefController().requestReference(arrayList4, tLObject, arrayList2, arrayList3, arrayList4, delayedMessage, Boolean.valueOf(z));
                return;
            }
            if (delayedMessage != null && !delayedMessage.getRetriedToSend(fileRefErrorIndex)) {
                delayedMessage.setRetriedToSend(fileRefErrorIndex, true);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda46
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$performSendMessageRequestMulti$58(tLObject, fileRefErrorIndex, delayedMessage, arrayList2, z);
                    }
                });
                return;
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequestMulti$65(tL_error, tLObject2, z, arrayList2, arrayList3, tLObject);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$performSendMessageRequestMulti$58(org.telegram.tgnet.TLObject r9, int r10, org.telegram.messenger.SendMessagesHelper.DelayedMessage r11, java.util.ArrayList r12, boolean r13) {
        /*
            Method dump skipped, instruction units count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$performSendMessageRequestMulti$58(org.telegram.tgnet.TLObject, int, org.telegram.messenger.SendMessagesHelper$DelayedMessage, java.util.ArrayList, boolean):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v9 */
    public /* synthetic */ void lambda$performSendMessageRequestMulti$65(TLRPC.TL_error tL_error, TLObject tLObject, final boolean z, ArrayList arrayList, ArrayList arrayList2, TLObject tLObject2) {
        ?? r8;
        boolean z2;
        int i;
        TLRPC.TL_error tL_error2;
        TLObject tLObject3;
        SendMessagesHelper sendMessagesHelper;
        String str;
        int i2;
        boolean z3;
        TLRPC.Updates updates;
        boolean z4;
        TLRPC.Message message;
        MessageObject messageObject;
        TLRPC.Message message2;
        int i3;
        TLRPC.Message message3;
        int i4;
        ArrayList arrayList3;
        int i5;
        String quickReplyName;
        TLRPC.MessageReplyHeader messageReplyHeader;
        SparseArray sparseArray;
        final SendMessagesHelper sendMessagesHelper2 = this;
        ArrayList arrayList4 = arrayList;
        TLObject tLObject4 = tLObject2;
        if (tL_error == null) {
            SparseArray sparseArray2 = new SparseArray();
            LongSparseArray longSparseArray = new LongSparseArray();
            TLRPC.Updates updates2 = (TLRPC.Updates) tLObject;
            ArrayList<TLRPC.Update> arrayList5 = updates2.updates;
            boolean z5 = z;
            int i6 = 0;
            LongSparseArray<SparseArray<TLRPC.MessageReplies>> longSparseArray2 = null;
            while (i6 < arrayList5.size()) {
                TLRPC.Update update = arrayList5.get(i6);
                if (update instanceof TL_update.TL_updateMessageID) {
                    TL_update.TL_updateMessageID tL_updateMessageID = (TL_update.TL_updateMessageID) update;
                    longSparseArray.put(tL_updateMessageID.random_id, Integer.valueOf(tL_updateMessageID.f1472id));
                    arrayList5.remove(i6);
                    i6--;
                } else if (update instanceof TL_update.TL_updateNewMessage) {
                    final TL_update.TL_updateNewMessage tL_updateNewMessage = (TL_update.TL_updateNewMessage) update;
                    TLRPC.Message message4 = tL_updateNewMessage.message;
                    sparseArray2.put(message4.f1271id, message4);
                    Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda66
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$performSendMessageRequestMulti$59(tL_updateNewMessage);
                        }
                    });
                    arrayList5.remove(i6);
                    i6--;
                    z5 = false;
                } else {
                    if (update instanceof TL_update.TL_updateNewChannelMessage) {
                        final TL_update.TL_updateNewChannelMessage tL_updateNewChannelMessage = (TL_update.TL_updateNewChannelMessage) update;
                        final long updateChannelId = MessagesController.getUpdateChannelId(tL_updateNewChannelMessage);
                        TLRPC.Chat chat = sendMessagesHelper2.getMessagesController().getChat(Long.valueOf(updateChannelId));
                        if (!(chat == null || chat.megagroup) || (messageReplyHeader = tL_updateNewChannelMessage.message.reply_to) == null || (messageReplyHeader.reply_to_top_id == 0 && messageReplyHeader.reply_to_msg_id == 0)) {
                            sparseArray = sparseArray2;
                        } else {
                            if (longSparseArray2 == null) {
                                longSparseArray2 = new LongSparseArray<>();
                            }
                            sparseArray = sparseArray2;
                            long dialogId = MessageObject.getDialogId(tL_updateNewChannelMessage.message);
                            SparseArray<TLRPC.MessageReplies> sparseArray3 = longSparseArray2.get(dialogId);
                            if (sparseArray3 == null) {
                                sparseArray3 = new SparseArray<>();
                                longSparseArray2.put(dialogId, sparseArray3);
                            }
                            TLRPC.MessageReplyHeader messageReplyHeader2 = tL_updateNewChannelMessage.message.reply_to;
                            int i7 = messageReplyHeader2.reply_to_top_id;
                            if (i7 == 0) {
                                i7 = messageReplyHeader2.reply_to_msg_id;
                            }
                            TLRPC.MessageReplies tL_messageReplies = sparseArray3.get(i7);
                            if (tL_messageReplies == null) {
                                tL_messageReplies = new TLRPC.TL_messageReplies();
                                sparseArray3.put(i7, tL_messageReplies);
                            }
                            TLRPC.Peer peer = tL_updateNewChannelMessage.message.from_id;
                            if (peer != null) {
                                tL_messageReplies.recent_repliers.add(0, peer);
                            }
                            tL_messageReplies.replies++;
                        }
                        TLRPC.Message message5 = tL_updateNewChannelMessage.message;
                        sparseArray2 = sparseArray;
                        sparseArray2.put(message5.f1271id, message5);
                        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda67
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$performSendMessageRequestMulti$60(tL_updateNewChannelMessage);
                            }
                        });
                        arrayList5.remove(i6);
                        i6--;
                        if (tL_updateNewChannelMessage.message.pinned) {
                            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda68
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$performSendMessageRequestMulti$61(tL_updateNewChannelMessage, updateChannelId);
                                }
                            });
                        }
                    } else if (update instanceof TL_update.TL_updateNewScheduledMessage) {
                        TLRPC.Message message6 = ((TL_update.TL_updateNewScheduledMessage) update).message;
                        sparseArray2.put(message6.f1271id, message6);
                        arrayList5.remove(i6);
                        i6--;
                        z5 = true;
                    } else if (update instanceof TL_update.TL_updateQuickReplyMessage) {
                        QuickRepliesController quickRepliesController = QuickRepliesController.getInstance(sendMessagesHelper2.currentAccount);
                        if (arrayList4.isEmpty()) {
                            quickReplyName = null;
                            i5 = 0;
                        } else {
                            i5 = 0;
                            quickReplyName = ((MessageObject) arrayList4.get(0)).getQuickReplyName();
                        }
                        quickRepliesController.processUpdate(update, quickReplyName, (arrayList4.isEmpty() ? null : Integer.valueOf(((MessageObject) arrayList4.get(i5)).getQuickReplyId())).intValue());
                        TLRPC.Message message7 = ((TL_update.TL_updateQuickReplyMessage) update).message;
                        sparseArray2.put(message7.f1271id, message7);
                        arrayList5.remove(i6);
                        i6--;
                        z5 = false;
                    }
                    i6++;
                }
                i6++;
            }
            if (longSparseArray2 != null) {
                i2 = 1;
                sendMessagesHelper2.getMessagesStorage().putChannelViews(null, null, longSparseArray2, true);
                sendMessagesHelper2.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didUpdateMessagesViews, null, null, longSparseArray2, Boolean.TRUE);
            } else {
                i2 = 1;
            }
            int[] iArr = new int[i2];
            iArr[0] = 0;
            int[] iArr2 = {0};
            int[] iArr3 = iArr;
            final ArrayList arrayList6 = new ArrayList();
            int i8 = 0;
            final SendMessagesHelper sendMessagesHelper3 = sendMessagesHelper2;
            while (i8 < arrayList4.size()) {
                MessageObject messageObject2 = (MessageObject) arrayList4.get(i8);
                String str2 = (String) arrayList2.get(i8);
                TLRPC.Message message8 = messageObject2.messageOwner;
                int i9 = message8.f1271id;
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = arrayList6;
                Integer num = (Integer) longSparseArray.get(message8.random_id);
                if (num == null || (message = (TLRPC.Message) sparseArray2.get(num.intValue())) == null) {
                    z3 = z;
                    updates = updates2;
                    z4 = true;
                    break;
                }
                MessageObject.getDialogId(message);
                arrayList7.add(message);
                if ((message.flags & 33554432) != 0) {
                    TLRPC.Message message9 = messageObject2.messageOwner;
                    message9.ttl_period = message.ttl_period;
                    message9.flags |= 33554432;
                }
                if (tLObject4 instanceof TLRPC.TL_messages_sendMedia) {
                    MessageObject messageObject3 = (MessageObject) arrayList4.get(0);
                    int i10 = message.f1271id;
                    HashMap<String, String> map = message.params;
                    messageObject = messageObject2;
                    message2 = message;
                    i3 = i8;
                    i4 = i9;
                    arrayList3 = arrayList7;
                    message3 = message8;
                    updateMediaPaths(messageObject3, message2, i10, arrayList2, false, -1, map);
                } else {
                    messageObject = messageObject2;
                    message2 = message;
                    i3 = i8;
                    message3 = message8;
                    i4 = i9;
                    arrayList3 = arrayList7;
                    updateMediaPaths(messageObject, message2, message2.f1271id, str2, false, message2.params);
                }
                TLRPC.Updates updates3 = updates2;
                final int mediaExistanceFlags = messageObject.getMediaExistanceFlags();
                message3.f1271id = message2.f1271id;
                int i11 = message2.quick_reply_shortcut_id;
                message3.quick_reply_shortcut_id = i11;
                if (i11 != 0) {
                    message3.flags |= TLObject.FLAG_30;
                }
                final SparseArray sparseArray4 = sparseArray2;
                LongSparseArray longSparseArray3 = longSparseArray;
                final long j = message2.grouped_id;
                if (!z) {
                    Integer numValueOf = getMessagesController().dialogs_read_outbox_max.get(Long.valueOf(message2.dialog_id));
                    if (numValueOf == null) {
                        numValueOf = Integer.valueOf(getMessagesStorage().getDialogReadMax(message2.out, message2.dialog_id));
                        getMessagesController().dialogs_read_outbox_max.put(Long.valueOf(message2.dialog_id), numValueOf);
                    }
                    message2.unread = numValueOf.intValue() < message2.f1271id;
                }
                iArr3[0] = iArr3[0] + 1;
                arrayList8.add(Integer.valueOf(i4));
                getStatsController().incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 1, 1);
                message3.send_state = 0;
                message3.errorAllowedPriceStars = 0L;
                message3.errorNewPriceStars = 0L;
                final TLRPC.Message message10 = message3;
                getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer, Integer.valueOf(i4), Integer.valueOf(message3.f1271id), message10, Long.valueOf(message3.dialog_id), Long.valueOf(j), Integer.valueOf(mediaExistanceFlags), Boolean.valueOf(z5));
                getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer2, Integer.valueOf(i4), Integer.valueOf(message3.f1271id), message10, Long.valueOf(message3.dialog_id), Long.valueOf(j), Integer.valueOf(mediaExistanceFlags), Boolean.valueOf(z5));
                final MessageObject messageObject4 = messageObject;
                final boolean z6 = z5;
                final int[] iArr4 = iArr3;
                final int i12 = i4;
                final ArrayList arrayList9 = arrayList3;
                arrayList6 = arrayList8;
                final int[] iArr5 = iArr2;
                sendMessagesHelper3 = this;
                iArr3 = iArr4;
                getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda69
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$performSendMessageRequestMulti$63(z6, message10, i12, arrayList9, iArr5, iArr4, z, messageObject4, sparseArray4, arrayList6, j, mediaExistanceFlags);
                    }
                });
                i8 = i3 + 1;
                arrayList4 = arrayList;
                tLObject4 = tLObject2;
                sparseArray2 = sparseArray4;
                longSparseArray = longSparseArray3;
                updates2 = updates3;
                z5 = z6;
                iArr2 = iArr5;
            }
            z3 = z;
            updates = updates2;
            z4 = false;
            final TLRPC.Updates updates4 = updates;
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda70
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSendMessageRequestMulti$64(updates4);
                }
            });
            tL_error2 = tL_error;
            tLObject3 = tLObject2;
            z2 = z4;
            i = 0;
            sendMessagesHelper = sendMessagesHelper3;
            r8 = z3;
        } else {
            r8 = z;
            z2 = true;
            i = 0;
            tL_error2 = tL_error;
            tLObject3 = tLObject2;
            AlertsCreator.processError(sendMessagesHelper2.currentAccount, tL_error2, null, tLObject3, new Object[0]);
            sendMessagesHelper = sendMessagesHelper2;
        }
        if (z2) {
            for (int i13 = i; i13 < arrayList.size(); i13++) {
                MessageObject messageObject5 = (MessageObject) arrayList.get(i13);
                TLRPC.Message message11 = messageObject5.messageOwner;
                sendMessagesHelper.getMessagesStorage().markMessageAsSendError(message11, r8);
                message11.send_state = 2;
                if (r8 == 0 && tL_error2 != null && (str = tL_error2.text) != null && str.startsWith("ALLOW_PAYMENT_REQUIRED_")) {
                    StarsController.getInstance(sendMessagesHelper.currentAccount);
                    message11.errorAllowedPriceStars = StarsController.getAllowedPaidStars(tLObject3);
                    message11.errorNewPriceStars = Long.parseLong(tL_error2.text.substring(23));
                    StarsController.getInstance(sendMessagesHelper.currentAccount).showPriceChangedToast(Arrays.asList(messageObject5));
                    sendMessagesHelper.getMessagesStorage().updateMessageCustomParams(MessageObject.getDialogId(message11), message11);
                }
                sendMessagesHelper.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageSendError, Integer.valueOf(message11.f1271id));
                sendMessagesHelper.processSentMessage(message11.f1271id);
                sendMessagesHelper.removeFromSendingMessages(message11.f1271id, r8);
            }
        }
    }

    public /* synthetic */ void lambda$performSendMessageRequestMulti$59(TL_update.TL_updateNewMessage tL_updateNewMessage) {
        getMessagesController().processNewDifferenceParams(-1, tL_updateNewMessage.pts, -1, tL_updateNewMessage.pts_count);
    }

    public /* synthetic */ void lambda$performSendMessageRequestMulti$60(TL_update.TL_updateNewChannelMessage tL_updateNewChannelMessage) {
        getMessagesController().processNewChannelDifferenceParams(tL_updateNewChannelMessage.pts, tL_updateNewChannelMessage.pts_count, tL_updateNewChannelMessage.message.peer_id.channel_id);
    }

    public /* synthetic */ void lambda$performSendMessageRequestMulti$61(TL_update.TL_updateNewChannelMessage tL_updateNewChannelMessage, long j) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(tL_updateNewChannelMessage.message.f1271id));
        getMessagesStorage().updatePinnedMessages(-j, arrayList, true, -1, 0, false, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [org.telegram.messenger.MessagesStorage] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1, types: [int] */
    /* JADX WARN: Type inference failed for: r19v2 */
    public /* synthetic */ void lambda$performSendMessageRequestMulti$63(final boolean z, final TLRPC.Message message, final int i, ArrayList arrayList, final int[] iArr, final int[] iArr2, final boolean z2, final MessageObject messageObject, final SparseArray sparseArray, final ArrayList arrayList2, final long j, final int i2) {
        ?? r19 = (message.quick_reply_shortcut_id == 0 && message.quick_reply_shortcut == null) ? z : 5;
        getMessagesStorage().updateMessageStateAndId(message.random_id, MessageObject.getPeerId(message.peer_id), Integer.valueOf(i), message.f1271id, 0, false, r19, message.quick_reply_shortcut_id);
        getMessagesStorage().putMessages((ArrayList<TLRPC.Message>) arrayList, true, false, false, 0, r19 == true ? 1 : 0, message.quick_reply_shortcut_id);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequestMulti$62(iArr, iArr2, z2, z, messageObject, sparseArray, arrayList2, message, i, j, i2);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequestMulti$62(int[] iArr, int[] iArr2, boolean z, boolean z2, MessageObject messageObject, SparseArray sparseArray, ArrayList arrayList, TLRPC.Message message, int i, long j, int i2) {
        TLRPC.Message message2;
        int i3 = iArr[0] + 1;
        iArr[0] = i3;
        if (i3 != iArr2[0] || z == z2) {
            message2 = message;
        } else {
            message2 = message;
            getMessagesController().deleteMessages(arrayList, null, null, messageObject.getDialogId(), false, z ? 1 : 0, false, 0L, null, 0, z2 && !z, (!z2 || sparseArray.size() <= 1) ? 0 : sparseArray.keyAt(0));
        }
        getMediaDataController().increasePeerRaiting(message2.dialog_id);
        TLRPC.Message message3 = message2;
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer, Integer.valueOf(i), Integer.valueOf(message2.f1271id), message3, Long.valueOf(message2.dialog_id), Long.valueOf(j), Integer.valueOf(i2), Boolean.valueOf(z2));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer2, Integer.valueOf(i), Integer.valueOf(message3.f1271id), message3, Long.valueOf(message3.dialog_id), Long.valueOf(j), Integer.valueOf(i2), Boolean.valueOf(z2));
        processSentMessage(i);
        removeFromSendingMessages(i, z);
    }

    public /* synthetic */ void lambda$performSendMessageRequestMulti$64(TLRPC.Updates updates) {
        getMessagesController().processUpdates(updates, false);
    }

    public void performSendMessageRequest(TLObject tLObject, MessageObject messageObject, String str, DelayedMessage delayedMessage, Object obj, HashMap<String, String> map, boolean z) {
        lambda$performSendMessageRequest$73(tLObject, messageObject, str, null, false, delayedMessage, obj, map, z);
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException
        */
    private org.telegram.messenger.SendMessagesHelper.DelayedMessage findMaxDelayedMessageForMessageId(int r10, long r11) {
        /*
            r9 = this;
            java.util.HashMap<java.lang.String, java.util.ArrayList<org.telegram.messenger.SendMessagesHelper$DelayedMessage>> r9 = r9.delayedMessages
            java.util.Set r9 = r9.entrySet()
            java.util.Iterator r9 = r9.iterator()
            r0 = 0
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
        Ld:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto L6e
            java.lang.Object r2 = r9.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r2 = r2.getValue()
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            int r3 = r2.size()
            r4 = 0
            r5 = r4
        L25:
            if (r5 >= r3) goto Ld
            java.lang.Object r6 = r2.get(r5)
            org.telegram.messenger.SendMessagesHelper$DelayedMessage r6 = (org.telegram.messenger.SendMessagesHelper.DelayedMessage) r6
            int r7 = r6.type
            r8 = 4
            if (r7 == r8) goto L34
            if (r7 != 0) goto L6b
        L34:
            long r7 = r6.peer
            int r7 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r7 != 0) goto L6b
            org.telegram.messenger.MessageObject r7 = r6.obj
            if (r7 == 0) goto L43
            int r7 = r7.getId()
            goto L61
        L43:
            java.util.ArrayList<org.telegram.messenger.MessageObject> r7 = r6.messageObjects
            if (r7 == 0) goto L60
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L60
            java.util.ArrayList<org.telegram.messenger.MessageObject> r7 = r6.messageObjects
            int r8 = r7.size()
            int r8 = r8 + (-1)
            java.lang.Object r7 = r7.get(r8)
            org.telegram.messenger.MessageObject r7 = (org.telegram.messenger.MessageObject) r7
            int r7 = r7.getId()
            goto L61
        L60:
            r7 = r4
        L61:
            if (r7 == 0) goto L6b
            if (r7 <= r10) goto L6b
            if (r0 != 0) goto L6b
            if (r1 >= r7) goto L6b
            r0 = r6
            r1 = r7
        L6b:
            int r5 = r5 + 1
            goto L25
        L6e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.findMaxDelayedMessageForMessageId(int, long):org.telegram.messenger.SendMessagesHelper$DelayedMessage");
    }

    /* JADX INFO: renamed from: performSendMessageRequest, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$performSendMessageRequest$73(final TLObject tLObject, final MessageObject messageObject, final String str, final DelayedMessage delayedMessage, final boolean z, final DelayedMessage delayedMessage2, final Object obj, final HashMap<String, String> map, final boolean z2) {
        DelayedMessage delayedMessageFindMaxDelayedMessageForMessageId;
        ArrayList<DelayedMessageSendAfterRequest> arrayList;
        if (tLObject instanceof TLRPC.TL_messages_addPollAnswer) {
            final TLRPC.TL_messages_addPollAnswer tL_messages_addPollAnswer = (TLRPC.TL_messages_addPollAnswer) tLObject;
            TLRPC.InputMedia inputMedia = tL_messages_addPollAnswer.answer.input_media;
            if ((inputMedia instanceof TLRPC.TL_inputMediaUploadedDocument) || (inputMedia instanceof TLRPC.TL_inputMediaUploadedPhoto)) {
                TLRPC.TL_messages_uploadMedia tL_messages_uploadMedia = new TLRPC.TL_messages_uploadMedia();
                tL_messages_uploadMedia.peer = tL_messages_addPollAnswer.peer;
                tL_messages_uploadMedia.media = tL_messages_addPollAnswer.answer.input_media;
                getConnectionsManager().sendRequest(tL_messages_uploadMedia, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda101
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$performSendMessageRequest$68(tL_messages_addPollAnswer, tLObject, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2, tLObject2, tL_error);
                    }
                });
                return;
            }
        }
        if (!(tLObject instanceof TLRPC.TL_messages_editMessage) && z && (delayedMessageFindMaxDelayedMessageForMessageId = findMaxDelayedMessageForMessageId(messageObject.getId(), messageObject.getDialogId())) != null) {
            delayedMessageFindMaxDelayedMessageForMessageId.addDelayedRequest(tLObject, messageObject, str, obj, delayedMessage2, delayedMessage != null ? delayedMessage.scheduled : false);
            if (delayedMessage == null || (arrayList = delayedMessage.requests) == null) {
                return;
            }
            delayedMessageFindMaxDelayedMessageForMessageId.requests.addAll(arrayList);
            return;
        }
        final TLRPC.Message message = messageObject.messageOwner;
        putToSendingMessages(message, z2);
        if (StarsController.getInstance(this.currentAccount).beforeSendingFinalRequest(tLObject, messageObject, new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda102
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$69(tLObject, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
            }
        }) && BotForumHelper.getInstance(this.currentAccount).beforeSendingFinalRequest(tLObject, messageObject, new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda103
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$70(tLObject, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
            }
        })) {
            message.reqId = getConnectionsManager().sendRequest(tLObject, new RequestDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda104
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$performSendMessageRequest$91(tLObject, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2, message, tLObject2, tL_error);
                }
            }, new QuickAckDelegate() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda105
                @Override // org.telegram.tgnet.QuickAckDelegate
                public final void run() {
                    this.f$0.lambda$performSendMessageRequest$93(message);
                }
            }, (tLObject instanceof TLRPC.TL_messages_sendMessage ? 128 : 0) | 68);
            if (delayedMessage != null) {
                delayedMessage.sendDelayedRequests();
            }
        }
    }

    public /* synthetic */ void lambda$performSendMessageRequest$68(final TLRPC.TL_messages_addPollAnswer tL_messages_addPollAnswer, final TLObject tLObject, final MessageObject messageObject, final String str, final DelayedMessage delayedMessage, final boolean z, final DelayedMessage delayedMessage2, final Object obj, final HashMap map, final boolean z2, final TLObject tLObject2, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$67(tLObject2, tL_messages_addPollAnswer, tLObject, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequest$67(TLObject tLObject, TLRPC.TL_messages_addPollAnswer tL_messages_addPollAnswer, TLObject tLObject2, MessageObject messageObject, String str, DelayedMessage delayedMessage, boolean z, DelayedMessage delayedMessage2, Object obj, HashMap map, boolean z2) {
        if (tLObject instanceof TLRPC.TL_messageMediaDocument) {
            TLRPC.Document document = ((TLRPC.TL_messageMediaDocument) tLObject).document;
            TLRPC.TL_inputDocument tL_inputDocument = new TLRPC.TL_inputDocument();
            tL_inputDocument.f1262id = document.f1253id;
            tL_inputDocument.access_hash = document.access_hash;
            tL_inputDocument.file_reference = document.file_reference;
            TLRPC.TL_inputMediaDocument tL_inputMediaDocument = new TLRPC.TL_inputMediaDocument();
            tL_inputMediaDocument.f1318id = tL_inputDocument;
            tL_messages_addPollAnswer.answer.input_media = tL_inputMediaDocument;
            lambda$performSendMessageRequest$73(tLObject2, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
            return;
        }
        if (!(tLObject instanceof TLRPC.TL_messageMediaPhoto)) {
            if (delayedMessage2 != null) {
                delayedMessage2.markAsError();
                return;
            }
            return;
        }
        TLRPC.Photo photo = ((TLRPC.TL_messageMediaPhoto) tLObject).photo;
        TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
        tL_inputPhoto.f1269id = photo.f1276id;
        tL_inputPhoto.access_hash = photo.access_hash;
        tL_inputPhoto.file_reference = photo.file_reference;
        TLRPC.TL_inputMediaPhoto tL_inputMediaPhoto = new TLRPC.TL_inputMediaPhoto();
        tL_inputMediaPhoto.f1320id = tL_inputPhoto;
        tL_messages_addPollAnswer.answer.input_media = tL_inputMediaPhoto;
        lambda$performSendMessageRequest$73(tLObject2, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:97:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$performSendMessageRequest$91(org.telegram.tgnet.TLObject r4, org.telegram.messenger.MessageObject r5, java.lang.String r6, org.telegram.messenger.SendMessagesHelper.DelayedMessage r7, boolean r8, org.telegram.messenger.SendMessagesHelper.DelayedMessage r9, java.lang.Object r10, java.util.HashMap r11, boolean r12, final org.telegram.tgnet.TLRPC.Message r13, org.telegram.tgnet.TLObject r14, org.telegram.tgnet.TLRPC.TL_error r15) {
        /*
            Method dump skipped, instruction units count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$performSendMessageRequest$91(org.telegram.tgnet.TLObject, org.telegram.messenger.MessageObject, java.lang.String, org.telegram.messenger.SendMessagesHelper$DelayedMessage, boolean, org.telegram.messenger.SendMessagesHelper$DelayedMessage, java.lang.Object, java.util.HashMap, boolean, org.telegram.tgnet.TLRPC$Message, org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$TL_error):void");
    }

    public /* synthetic */ void lambda$performSendMessageRequest$71(TLRPC.Message message, boolean z, TLObject tLObject, DelayedMessage delayedMessage) {
        removeFromSendingMessages(message.f1271id, z);
        if (tLObject instanceof TLRPC.TL_messages_addPollAnswer) {
            TLRPC.PollAnswer pollAnswer = ((TLRPC.TL_messages_addPollAnswer) tLObject).answer;
            TLRPC.InputMedia inputMedia = pollAnswer.input_media;
            if ((inputMedia instanceof TLRPC.TL_inputMediaPhoto) || (inputMedia instanceof TLRPC.TL_inputMediaDocument)) {
                pollAnswer.input_media = delayedMessage.inputUploadMedia;
            }
        } else if (tLObject instanceof TLRPC.TL_messages_sendMedia) {
            TLRPC.TL_messages_sendMedia tL_messages_sendMedia = (TLRPC.TL_messages_sendMedia) tLObject;
            TLRPC.InputMedia inputMedia2 = tL_messages_sendMedia.media;
            if ((inputMedia2 instanceof TLRPC.TL_inputMediaPhoto) || (inputMedia2 instanceof TLRPC.TL_inputMediaDocument)) {
                tL_messages_sendMedia.media = delayedMessage.inputUploadMedia;
            }
        } else if (tLObject instanceof TLRPC.TL_messages_editMessage) {
            TLRPC.TL_messages_editMessage tL_messages_editMessage = (TLRPC.TL_messages_editMessage) tLObject;
            TLRPC.InputMedia inputMedia3 = tL_messages_editMessage.media;
            if ((inputMedia3 instanceof TLRPC.TL_inputMediaPhoto) || (inputMedia3 instanceof TLRPC.TL_inputMediaDocument)) {
                tL_messages_editMessage.media = delayedMessage.inputUploadMedia;
            }
        }
        delayedMessage.performMediaUpload = true;
        performSendDelayedMessage(delayedMessage);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$72(TLObject tLObject, MessageObject messageObject, String str, DelayedMessage delayedMessage, boolean z, DelayedMessage delayedMessage2, Object obj, HashMap map, boolean z2, TLRPC.EmojiGameInfo emojiGameInfo, TLRPC.TL_error tL_error) {
        if (emojiGameInfo instanceof TLRPC.TL_emojiGameDiceInfo) {
            String str2 = ((TLRPC.TL_emojiGameDiceInfo) emojiGameInfo).game_hash;
            TLRPC.TL_messages_sendMedia tL_messages_sendMedia = (TLRPC.TL_messages_sendMedia) tLObject;
            TLRPC.InputMedia inputMedia = tL_messages_sendMedia.media;
            if (inputMedia instanceof TLRPC.TL_inputMediaStakeDice) {
                ((TLRPC.TL_inputMediaStakeDice) inputMedia).game_hash = str2;
            }
            lambda$performSendMessageRequest$73(tL_messages_sendMedia, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
        }
    }

    public /* synthetic */ void lambda$performSendMessageRequest$74(BaseFragment baseFragment, TLRPC.TL_inputMediaStakeDice tL_inputMediaStakeDice, final TLObject tLObject, final MessageObject messageObject, final String str, final DelayedMessage delayedMessage, final boolean z, final DelayedMessage delayedMessage2, final Object obj, final HashMap map, final boolean z2) {
        new TONIntroActivity.StarsNeededSheet(baseFragment.getContext(), baseFragment.getResourceProvider(), AmountUtils$Amount.fromNano(tL_inputMediaStakeDice.ton_amount, AmountUtils$Currency.TON), false, new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$73(tLObject, messageObject, str, delayedMessage, z, delayedMessage2, obj, map, z2);
            }
        }).show();
        ArrayList<MessageObject> arrayList = new ArrayList<>();
        arrayList.add(messageObject);
        cancelSendingMessage(arrayList);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$77(TLRPC.TL_error tL_error, final TLRPC.Message message, TLObject tLObject, MessageObject messageObject, String str, HashMap map, final boolean z, TLObject tLObject2) {
        if (tL_error == null) {
            String str2 = message.attachPath;
            final TLRPC.Updates updates = (TLRPC.Updates) tLObject;
            ArrayList<TLRPC.Update> arrayList = updates.updates;
            message.send_state = 0;
            ArrayList<TLRPC.Message> arrayList2 = new ArrayList<>();
            arrayList2.add(message);
            getMessagesStorage().putMessages(arrayList2, false, true, false, 0, 0, 0L);
            getMessagesController().getTopicsController().processEditedMessage(message);
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda112
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSendMessageRequest$76(updates, message, z);
                }
            });
            return;
        }
        AlertsCreator.processError(this.currentAccount, tL_error, null, tLObject2, new Object[0]);
        removeFromSendingMessages(message.f1271id, z);
        revertEditingMessageObject(messageObject);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$76(TLRPC.Updates updates, final TLRPC.Message message, final boolean z) {
        getMessagesController().processUpdates(updates, false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$75(message, z);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequest$75(TLRPC.Message message, boolean z) {
        processSentMessage(message.f1271id);
        removeFromSendingMessages(message.f1271id, z);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$80(TLRPC.TL_error tL_error, final TLRPC.Message message, TLObject tLObject, MessageObject messageObject, String str, HashMap map, final boolean z, TLObject tLObject2) {
        int i = 0;
        TLRPC.Message message2 = null;
        if (tL_error == null) {
            String str2 = message.attachPath;
            final TLRPC.Updates updates = (TLRPC.Updates) tLObject;
            ArrayList<TLRPC.Update> arrayList = updates.updates;
            while (true) {
                if (i >= arrayList.size()) {
                    break;
                }
                TLRPC.Update update = arrayList.get(i);
                if (update instanceof TL_update.TL_updateEditMessage) {
                    message2 = ((TL_update.TL_updateEditMessage) update).message;
                    break;
                }
                if (update instanceof TL_update.TL_updateEditChannelMessage) {
                    message2 = ((TL_update.TL_updateEditChannelMessage) update).message;
                    break;
                }
                if (update instanceof TL_update.TL_updateNewScheduledMessage) {
                    message2 = ((TL_update.TL_updateNewScheduledMessage) update).message;
                    break;
                } else {
                    if (update instanceof TL_update.TL_updateQuickReplyMessage) {
                        QuickRepliesController.getInstance(this.currentAccount).processUpdate(update, MessageObject.getQuickReplyName(message), MessageObject.getQuickReplyId(message));
                        message2 = ((TL_update.TL_updateQuickReplyMessage) update).message;
                        break;
                    }
                    i++;
                }
            }
            TLRPC.Message message3 = message2;
            if (message3 != null) {
                ImageLoader.saveMessageThumbs(message3);
                updateMediaPaths(messageObject, message3, message3.f1271id, str, false, map);
            }
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda97
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSendMessageRequest$79(updates, message, z);
                }
            });
            return;
        }
        AlertsCreator.processError(this.currentAccount, tL_error, null, tLObject2, new Object[0]);
        removeFromSendingMessages(message.f1271id, z);
        revertEditingMessageObject(messageObject);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$79(TLRPC.Updates updates, final TLRPC.Message message, final boolean z) {
        getMessagesController().processUpdates(updates, false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$78(message, z);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequest$78(TLRPC.Message message, boolean z) {
        processSentMessage(message.f1271id);
        removeFromSendingMessages(message.f1271id, z);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$81(TLRPC.TL_updateShortSentMessage tL_updateShortSentMessage) {
        getMessagesController().processNewDifferenceParams(-1, tL_updateShortSentMessage.pts, tL_updateShortSentMessage.date, tL_updateShortSentMessage.pts_count);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$82(TL_update.TL_updateNewMessage tL_updateNewMessage) {
        getMessagesController().processNewDifferenceParams(-1, tL_updateNewMessage.pts, -1, tL_updateNewMessage.pts_count);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$83(TL_update.TL_updateNewChannelMessage tL_updateNewChannelMessage) {
        getMessagesController().processNewChannelDifferenceParams(tL_updateNewChannelMessage.pts, tL_updateNewChannelMessage.pts_count, tL_updateNewChannelMessage.message.peer_id.channel_id);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$84(TL_update.TL_updateNewChannelMessage tL_updateNewChannelMessage, long j) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(tL_updateNewChannelMessage.message.f1271id));
        getMessagesStorage().updatePinnedMessages(-j, arrayList, true, -1, 0, false, null);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$85(TLRPC.Updates updates) {
        getMessagesController().processUpdates(updates, false);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$87(ArrayList arrayList, final boolean z, final boolean z2, final TLRPC.Message message, final ArrayList arrayList2, final ArrayList arrayList3, final int i) {
        getMessagesStorage().putMessages(arrayList, true, false, false, 0, false, !z ? 1 : 0, 0L);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$86(z2, message, arrayList2, z, arrayList3, i);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequest$86(boolean z, TLRPC.Message message, ArrayList arrayList, boolean z2, ArrayList arrayList2, int i) {
        boolean z3;
        int i2;
        if (!z || message == null) {
            z3 = false;
            i2 = 0;
        } else {
            i2 = message.f1271id;
            z3 = false;
        }
        MessagesController messagesController = getMessagesController();
        long j = message.dialog_id;
        if (!z2 && z) {
            z3 = true;
        }
        messagesController.deleteMessages(arrayList, null, null, j, false, z2 ? 1 : 0, false, 0L, null, 0, z3, i2);
        getMessagesController().updateInterfaceWithMessages(message.dialog_id, arrayList2, z ? 1 : 0);
        getMediaDataController().increasePeerRaiting(message.dialog_id);
        processSentMessage(i);
        removeFromSendingMessages(i, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v23 */
    public /* synthetic */ void lambda$performSendMessageRequest$90(final boolean z, TLRPC.TL_error tL_error, final TLRPC.Message message, TLObject tLObject, MessageObject messageObject, HashMap map, String str, TLObject tLObject2) {
        MessageObject messageObject2;
        ?? r2;
        TLObject tLObject3;
        boolean z2;
        SendMessagesHelper sendMessagesHelper;
        String str2;
        long j;
        ArrayList arrayList;
        final SendMessagesHelper sendMessagesHelper2;
        Long l;
        int i;
        final boolean z3;
        int i2;
        final SendMessagesHelper sendMessagesHelper3;
        int mediaExistanceFlags;
        boolean z4;
        Long l2;
        int i3;
        TLRPC.MessageReplyHeader messageReplyHeader;
        SparseArray<TLRPC.MessageReplies> sparseArray;
        LongSparseArray<SparseArray<TLRPC.MessageReplies>> longSparseArray;
        boolean z5;
        SendMessagesHelper sendMessagesHelper4;
        boolean z6;
        final TLRPC.Message message2 = message;
        Long l3 = 0L;
        if (tL_error == null) {
            int i4 = message2.f1271id;
            ArrayList arrayList2 = new ArrayList();
            boolean z7 = message2.date == 2147483646;
            if (tLObject instanceof TLRPC.TL_updateShortSentMessage) {
                final TLRPC.TL_updateShortSentMessage tL_updateShortSentMessage = (TLRPC.TL_updateShortSentMessage) tLObject;
                j = 0;
                arrayList = arrayList2;
                updateMediaPaths(messageObject, null, tL_updateShortSentMessage.f1406id, null, false, map);
                int mediaExistanceFlags2 = messageObject.getMediaExistanceFlags();
                int i5 = tL_updateShortSentMessage.f1406id;
                message2.f1271id = i5;
                message2.local_id = i5;
                message2.date = tL_updateShortSentMessage.date;
                message2.entities = tL_updateShortSentMessage.entities;
                message2.out = tL_updateShortSentMessage.out;
                if ((tL_updateShortSentMessage.flags & 33554432) != 0) {
                    message2.ttl_period = tL_updateShortSentMessage.ttl_period;
                    message2.flags |= 33554432;
                }
                TLRPC.MessageMedia messageMedia = tL_updateShortSentMessage.media;
                if (messageMedia != null) {
                    message2.media = messageMedia;
                    message2.flags |= 512;
                    ImageLoader.saveMessageThumbs(message2);
                }
                TLRPC.MessageMedia messageMedia2 = tL_updateShortSentMessage.media;
                if (((messageMedia2 instanceof TLRPC.TL_messageMediaGame) || (messageMedia2 instanceof TLRPC.TL_messageMediaInvoice)) && !TextUtils.isEmpty(tL_updateShortSentMessage.message)) {
                    message2.message = tL_updateShortSentMessage.message;
                }
                if (!message2.entities.isEmpty()) {
                    message2.flags |= 128;
                }
                messageObject.updateMessageText();
                messageObject.generateCaption();
                messageObject.resetLayout();
                Integer numValueOf = getMessagesController().dialogs_read_outbox_max.get(Long.valueOf(message2.dialog_id));
                if (numValueOf == null) {
                    z6 = true;
                    numValueOf = Integer.valueOf(getMessagesStorage().getDialogReadMax(message2.out, message2.dialog_id));
                    getMessagesController().dialogs_read_outbox_max.put(Long.valueOf(message2.dialog_id), numValueOf);
                } else {
                    z6 = true;
                }
                message2.unread = numValueOf.intValue() < message2.f1271id ? z6 : false;
                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda27
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$performSendMessageRequest$81(tL_updateShortSentMessage);
                    }
                });
                arrayList.add(message2);
                i2 = mediaExistanceFlags2;
                sendMessagesHelper2 = this;
                l = l3;
                i = i4;
                z3 = false;
                z2 = false;
                messageObject2 = messageObject;
            } else {
                j = 0;
                arrayList = arrayList2;
                if (tLObject instanceof TLRPC.Updates) {
                    final TLRPC.Updates updates = (TLRPC.Updates) tLObject;
                    ArrayList<TLRPC.Update> arrayList3 = updates.updates;
                    boolean z8 = z;
                    TLRPC.Message message3 = null;
                    int i6 = 0;
                    LongSparseArray<SparseArray<TLRPC.MessageReplies>> longSparseArray2 = null;
                    while (i6 < arrayList3.size()) {
                        TLRPC.Update update = arrayList3.get(i6);
                        boolean z9 = z7;
                        if (update instanceof TL_update.TL_updateNewMessage) {
                            final TL_update.TL_updateNewMessage tL_updateNewMessage = (TL_update.TL_updateNewMessage) update;
                            TLRPC.Message message4 = tL_updateNewMessage.message;
                            l2 = l3;
                            if (message4.action == null) {
                                arrayList.add(message4);
                                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda28
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$performSendMessageRequest$82(tL_updateNewMessage);
                                    }
                                });
                                arrayList3.remove(i6);
                                i6--;
                                message3 = message4;
                            }
                            i3 = i4;
                        } else {
                            l2 = l3;
                            if (update instanceof TL_update.TL_updateNewChannelMessage) {
                                final TL_update.TL_updateNewChannelMessage tL_updateNewChannelMessage = (TL_update.TL_updateNewChannelMessage) update;
                                final long updateChannelId = MessagesController.getUpdateChannelId(tL_updateNewChannelMessage);
                                TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(updateChannelId));
                                if (!(chat == null || chat.megagroup) || (messageReplyHeader = tL_updateNewChannelMessage.message.reply_to) == null || (messageReplyHeader.reply_to_top_id == 0 && messageReplyHeader.reply_to_msg_id == 0)) {
                                    i3 = i4;
                                } else {
                                    if (longSparseArray2 == null) {
                                        longSparseArray2 = new LongSparseArray<>();
                                    }
                                    long dialogId = MessageObject.getDialogId(tL_updateNewChannelMessage.message);
                                    SparseArray<TLRPC.MessageReplies> sparseArray2 = longSparseArray2.get(dialogId);
                                    i3 = i4;
                                    if (sparseArray2 == null) {
                                        sparseArray = new SparseArray<>();
                                        longSparseArray2.put(dialogId, sparseArray);
                                    } else {
                                        sparseArray = sparseArray2;
                                    }
                                    TLRPC.MessageReplyHeader messageReplyHeader2 = tL_updateNewChannelMessage.message.reply_to;
                                    int i7 = messageReplyHeader2.reply_to_top_id;
                                    if (i7 == 0) {
                                        i7 = messageReplyHeader2.reply_to_msg_id;
                                    }
                                    TLRPC.MessageReplies tL_messageReplies = sparseArray.get(i7);
                                    if (tL_messageReplies == null) {
                                        tL_messageReplies = new TLRPC.TL_messageReplies();
                                        sparseArray.put(i7, tL_messageReplies);
                                    }
                                    TLRPC.Peer peer = tL_updateNewChannelMessage.message.from_id;
                                    if (peer != null) {
                                        longSparseArray = longSparseArray2;
                                        tL_messageReplies.recent_repliers.add(0, peer);
                                    } else {
                                        longSparseArray = longSparseArray2;
                                    }
                                    tL_messageReplies.replies++;
                                    longSparseArray2 = longSparseArray;
                                }
                                TLRPC.Message message5 = tL_updateNewChannelMessage.message;
                                arrayList.add(message5);
                                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda29
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$performSendMessageRequest$83(tL_updateNewChannelMessage);
                                    }
                                });
                                arrayList3.remove(i6);
                                i6--;
                                if (tL_updateNewChannelMessage.message.pinned) {
                                    Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda30
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this.f$0.lambda$performSendMessageRequest$84(tL_updateNewChannelMessage, updateChannelId);
                                        }
                                    });
                                }
                                message3 = message5;
                            } else {
                                i3 = i4;
                                if (update instanceof TL_update.TL_updateNewScheduledMessage) {
                                    TL_update.TL_updateNewScheduledMessage tL_updateNewScheduledMessage = (TL_update.TL_updateNewScheduledMessage) update;
                                    int i8 = 0;
                                    while (true) {
                                        if (i8 >= arrayList.size()) {
                                            break;
                                        }
                                        if (((TLRPC.Message) arrayList.get(i8)).f1271id == tL_updateNewScheduledMessage.message.f1271id) {
                                            arrayList.remove(i8);
                                            break;
                                        }
                                        i8++;
                                    }
                                    message3 = tL_updateNewScheduledMessage.message;
                                    arrayList.add(message3);
                                    arrayList3.remove(i6);
                                    i6--;
                                    z8 = true;
                                } else if (update instanceof TL_update.TL_updateQuickReplyMessage) {
                                    QuickRepliesController.getInstance(this.currentAccount).processUpdate(update, messageObject.getQuickReplyName(), messageObject.getQuickReplyId());
                                    message3 = ((TL_update.TL_updateQuickReplyMessage) update).message;
                                    arrayList.add(message3);
                                    arrayList3.remove(i6);
                                    i6--;
                                } else if (update instanceof TL_update.TL_updateDeleteScheduledMessages) {
                                    TL_update.TL_updateDeleteScheduledMessages tL_updateDeleteScheduledMessages = (TL_update.TL_updateDeleteScheduledMessages) update;
                                    if (messageObject.getDialogId() == DialogObject.getPeerDialogId(tL_updateDeleteScheduledMessages.peer)) {
                                        ArrayList<Integer> arrayList4 = tL_updateDeleteScheduledMessages.messages;
                                        int size = arrayList4.size();
                                        int i9 = 0;
                                        while (i9 < size) {
                                            Integer num = arrayList4.get(i9);
                                            i9++;
                                            int iIntValue = num.intValue();
                                            ArrayList<Integer> arrayList5 = arrayList4;
                                            int i10 = 0;
                                            while (true) {
                                                if (i10 >= arrayList.size()) {
                                                    break;
                                                }
                                                if (((TLRPC.Message) arrayList.get(i10)).f1271id == iIntValue) {
                                                    arrayList.remove(i10);
                                                    break;
                                                }
                                                i10++;
                                            }
                                            arrayList4 = arrayList5;
                                        }
                                        arrayList3.remove(i6);
                                        i6--;
                                    }
                                }
                            }
                            z8 = false;
                        }
                        i6++;
                        z7 = z9;
                        l3 = l2;
                        i4 = i3;
                    }
                    boolean z10 = z7;
                    l = l3;
                    i = i4;
                    if (longSparseArray2 != null) {
                        getMessagesStorage().putChannelViews(null, null, longSparseArray2, true);
                        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didUpdateMessagesViews, null, null, longSparseArray2, Boolean.TRUE);
                    }
                    if (message3 != null) {
                        MessageObject.getDialogId(message3);
                        if (z10 && message3.date != 2147483646) {
                            z8 = false;
                        }
                        ImageLoader.saveMessageThumbs(message3);
                        if (!z8) {
                            Integer numValueOf2 = getMessagesController().dialogs_read_outbox_max.get(Long.valueOf(message3.dialog_id));
                            if (numValueOf2 == null) {
                                numValueOf2 = Integer.valueOf(getMessagesStorage().getDialogReadMax(message3.out, message3.dialog_id));
                                getMessagesController().dialogs_read_outbox_max.put(Long.valueOf(message3.dialog_id), numValueOf2);
                            }
                            message3.unread = numValueOf2.intValue() < message3.f1271id;
                        }
                        TLRPC.Message message6 = messageObject.messageOwner;
                        message6.post_author = message3.post_author;
                        if ((message3.flags & 33554432) != 0) {
                            message6.ttl_period = message3.ttl_period;
                            message6.flags |= 33554432;
                        }
                        message6.entities = message3.entities;
                        int i11 = message3.quick_reply_shortcut_id;
                        message6.quick_reply_shortcut_id = i11;
                        if (i11 != 0) {
                            message6.flags |= TLObject.FLAG_30;
                        }
                        TLRPC.Message message7 = message3;
                        updateMediaPaths(messageObject, message7, message3.f1271id, str, false, map);
                        messageObject2 = messageObject;
                        sendMessagesHelper3 = this;
                        messageObject2.updateMessageText();
                        messageObject2.generateCaption();
                        messageObject2.resetLayout();
                        mediaExistanceFlags = messageObject2.getMediaExistanceFlags();
                        message2.f1271id = message7.f1271id;
                        z4 = false;
                    } else {
                        sendMessagesHelper3 = this;
                        messageObject2 = messageObject;
                        if (BuildVars.LOGS_ENABLED) {
                            StringBuilder sb = new StringBuilder();
                            for (int i12 = 0; i12 < arrayList3.size(); i12++) {
                                sb.append(arrayList3.get(i12).getClass().getSimpleName());
                                sb.append(", ");
                            }
                            FileLog.m1045d("can't find message in updates " + ((Object) sb));
                        }
                        mediaExistanceFlags = 0;
                        z4 = true;
                    }
                    Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda31
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$performSendMessageRequest$85(updates);
                        }
                    });
                    i2 = mediaExistanceFlags;
                    z3 = z8;
                    z2 = z4;
                    sendMessagesHelper2 = sendMessagesHelper3;
                } else {
                    sendMessagesHelper2 = this;
                    l = l3;
                    i = i4;
                    messageObject2 = messageObject;
                    z3 = z;
                    i2 = 0;
                    z2 = false;
                }
            }
            if (MessageObject.isLiveLocationMessage(message2) && message2.via_bot_id == j && TextUtils.isEmpty(message2.via_bot_name)) {
                sendMessagesHelper2.getLocationController().addSharingLocation(message2);
            }
            if (z2) {
                z5 = z;
                sendMessagesHelper4 = sendMessagesHelper2;
            } else {
                sendMessagesHelper2.getStatsController().incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 1, 1);
                message2.send_state = 0;
                long j2 = j;
                message2.errorNewPriceStars = j2;
                message2.errorAllowedPriceStars = j2;
                if (z != z3) {
                    final ArrayList arrayList6 = new ArrayList();
                    arrayList6.add(Integer.valueOf(i));
                    final ArrayList arrayList7 = new ArrayList();
                    arrayList7.add(new MessageObject(messageObject2.currentAccount, messageObject2.messageOwner, true, true));
                    final ArrayList arrayList8 = arrayList;
                    final int i13 = i;
                    sendMessagesHelper2.getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda32
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$performSendMessageRequest$87(arrayList8, z, z3, message2, arrayList6, arrayList7, i13);
                        }
                    });
                    sendMessagesHelper4 = this;
                    message2 = message2;
                    z5 = z;
                } else {
                    final int i14 = i;
                    Long l4 = l;
                    getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer, Integer.valueOf(i14), Integer.valueOf(message2.f1271id), message2, Long.valueOf(message2.dialog_id), l4, Integer.valueOf(i2), Boolean.valueOf(z));
                    getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer2, Integer.valueOf(i14), Integer.valueOf(message2.f1271id), message2, Long.valueOf(message2.dialog_id), l4, Integer.valueOf(i2), Boolean.valueOf(z));
                    DispatchQueue storageQueue = getMessagesStorage().getStorageQueue();
                    final SendMessagesHelper sendMessagesHelper5 = this;
                    final boolean z11 = z;
                    final ArrayList arrayList9 = arrayList;
                    final int i15 = i2;
                    Runnable runnable = new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda33
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$performSendMessageRequest$89(z11, message, i14, arrayList9, i15);
                        }
                    };
                    message2 = message;
                    storageQueue.postRunnable(runnable);
                    sendMessagesHelper4 = sendMessagesHelper5;
                    z5 = z11;
                }
            }
            tLObject3 = tLObject2;
            sendMessagesHelper = sendMessagesHelper4;
            r2 = z5;
        } else {
            SendMessagesHelper sendMessagesHelper6 = this;
            messageObject2 = messageObject;
            r2 = z;
            tLObject3 = tLObject2;
            AlertsCreator.processError(sendMessagesHelper6.currentAccount, tL_error, null, tLObject3, new Object[0]);
            z2 = true;
            sendMessagesHelper = sendMessagesHelper6;
        }
        if (z2) {
            sendMessagesHelper.getMessagesStorage().markMessageAsSendError(message2, r2);
            message2.send_state = 2;
            if (tL_error != null && (str2 = tL_error.text) != null && str2.startsWith("ALLOW_PAYMENT_REQUIRED_")) {
                StarsController.getInstance(sendMessagesHelper.currentAccount);
                message2.errorAllowedPriceStars = StarsController.getAllowedPaidStars(tLObject3);
                message2.errorNewPriceStars = Long.parseLong(tL_error.text.substring(23));
                StarsController.getInstance(sendMessagesHelper.currentAccount).showPriceChangedToast(Arrays.asList(messageObject2));
                sendMessagesHelper.getMessagesStorage().updateMessageCustomParams(MessageObject.getDialogId(message2), message2);
            }
            sendMessagesHelper.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageSendError, Integer.valueOf(message2.f1271id));
            sendMessagesHelper.processSentMessage(message2.f1271id);
            sendMessagesHelper.removeFromSendingMessages(message2.f1271id, r2);
        }
    }

    public /* synthetic */ void lambda$performSendMessageRequest$89(final boolean z, final TLRPC.Message message, final int i, ArrayList arrayList, final int i2) {
        int i3 = (message.quick_reply_shortcut_id == 0 && message.quick_reply_shortcut == null) ? z ? 1 : 0 : 5;
        getMessagesStorage().updateMessageStateAndId(message.random_id, MessageObject.getPeerId(message.peer_id), Integer.valueOf(i), message.f1271id, 0, false, z ? 1 : 0, message.quick_reply_shortcut_id);
        getMessagesStorage().putMessages((ArrayList<TLRPC.Message>) arrayList, true, false, false, 0, i3, message.quick_reply_shortcut_id);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$88(message, i, i2, z);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequest$88(TLRPC.Message message, int i, int i2, boolean z) {
        getMediaDataController().increasePeerRaiting(message.dialog_id);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer, Integer.valueOf(i), Integer.valueOf(message.f1271id), message, Long.valueOf(message.dialog_id), 0L, Integer.valueOf(i2), Boolean.valueOf(z));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByServer2, Integer.valueOf(i), Integer.valueOf(message.f1271id), message, Long.valueOf(message.dialog_id), 0L, Integer.valueOf(i2), Boolean.valueOf(z));
        processSentMessage(i);
        removeFromSendingMessages(i, z);
    }

    public /* synthetic */ void lambda$performSendMessageRequest$93(final TLRPC.Message message) {
        final int i = message.f1271id;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda87
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$performSendMessageRequest$92(message, i);
            }
        });
    }

    public /* synthetic */ void lambda$performSendMessageRequest$92(TLRPC.Message message, int i) {
        message.send_state = 0;
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messageReceivedByAck, Integer.valueOf(i));
    }

    private boolean removeCoverFromRequest(TLObject tLObject) {
        if (tLObject instanceof TLRPC.TL_messages_sendMedia) {
            TLRPC.InputMedia inputMedia = ((TLRPC.TL_messages_sendMedia) tLObject).media;
            if (inputMedia instanceof TLRPC.TL_inputMediaUploadedDocument) {
                TLRPC.TL_inputMediaUploadedDocument tL_inputMediaUploadedDocument = (TLRPC.TL_inputMediaUploadedDocument) inputMedia;
                tL_inputMediaUploadedDocument.video_cover = null;
                tL_inputMediaUploadedDocument.flags &= -65;
                return true;
            }
            if (inputMedia instanceof TLRPC.TL_inputMediaDocument) {
                TLRPC.TL_inputMediaDocument tL_inputMediaDocument = (TLRPC.TL_inputMediaDocument) inputMedia;
                tL_inputMediaDocument.video_cover = null;
                tL_inputMediaDocument.flags &= -9;
                return true;
            }
            if (!(inputMedia instanceof TLRPC.TL_inputMediaDocumentExternal)) {
                return false;
            }
            TLRPC.TL_inputMediaDocumentExternal tL_inputMediaDocumentExternal = (TLRPC.TL_inputMediaDocumentExternal) inputMedia;
            tL_inputMediaDocumentExternal.video_cover = null;
            tL_inputMediaDocumentExternal.flags &= -5;
            return true;
        }
        if (tLObject instanceof TLRPC.TL_messages_editMessage) {
            TLRPC.InputMedia inputMedia2 = ((TLRPC.TL_messages_editMessage) tLObject).media;
            if (inputMedia2 instanceof TLRPC.TL_inputMediaUploadedDocument) {
                TLRPC.TL_inputMediaUploadedDocument tL_inputMediaUploadedDocument2 = (TLRPC.TL_inputMediaUploadedDocument) inputMedia2;
                tL_inputMediaUploadedDocument2.video_cover = null;
                tL_inputMediaUploadedDocument2.flags &= -65;
                return true;
            }
            if (inputMedia2 instanceof TLRPC.TL_inputMediaDocument) {
                TLRPC.TL_inputMediaDocument tL_inputMediaDocument2 = (TLRPC.TL_inputMediaDocument) inputMedia2;
                tL_inputMediaDocument2.video_cover = null;
                tL_inputMediaDocument2.flags &= -9;
                return true;
            }
            if (!(inputMedia2 instanceof TLRPC.TL_inputMediaDocumentExternal)) {
                return false;
            }
            TLRPC.TL_inputMediaDocumentExternal tL_inputMediaDocumentExternal2 = (TLRPC.TL_inputMediaDocumentExternal) inputMedia2;
            tL_inputMediaDocumentExternal2.video_cover = null;
            tL_inputMediaDocumentExternal2.flags &= -5;
            return true;
        }
        if (!(tLObject instanceof TLRPC.TL_messages_addPollAnswer)) {
            return false;
        }
        TLRPC.InputMedia inputMedia3 = ((TLRPC.TL_messages_addPollAnswer) tLObject).answer.input_media;
        if (inputMedia3 instanceof TLRPC.TL_inputMediaUploadedDocument) {
            TLRPC.TL_inputMediaUploadedDocument tL_inputMediaUploadedDocument3 = (TLRPC.TL_inputMediaUploadedDocument) inputMedia3;
            tL_inputMediaUploadedDocument3.video_cover = null;
            tL_inputMediaUploadedDocument3.flags &= -65;
            return true;
        }
        if (inputMedia3 instanceof TLRPC.TL_inputMediaDocument) {
            TLRPC.TL_inputMediaDocument tL_inputMediaDocument3 = (TLRPC.TL_inputMediaDocument) inputMedia3;
            tL_inputMediaDocument3.video_cover = null;
            tL_inputMediaDocument3.flags &= -9;
            return true;
        }
        if (!(inputMedia3 instanceof TLRPC.TL_inputMediaDocumentExternal)) {
            return false;
        }
        TLRPC.TL_inputMediaDocumentExternal tL_inputMediaDocumentExternal3 = (TLRPC.TL_inputMediaDocumentExternal) inputMedia3;
        tL_inputMediaDocumentExternal3.video_cover = null;
        tL_inputMediaDocumentExternal3.flags &= -5;
        return true;
    }

    private void updateMediaPaths(MessageObject messageObject, TLRPC.Message message, int i, String str, boolean z, HashMap<String, String> map) {
        updateMediaPaths(messageObject, message, i, Collections.singletonList(str), z, -1, map);
    }

    /* JADX WARN: Removed duplicated region for block: B:1099:0x0ab5  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:726:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:733:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:993:0x07ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateMediaPaths(org.telegram.messenger.MessageObject r26, org.telegram.tgnet.TLRPC.Message r27, int r28, java.util.List<java.lang.String> r29, boolean r30, int r31, java.util.HashMap<java.lang.String, java.lang.String> r32) {
        /*
            Method dump skipped, instruction units count: 2906
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.updateMediaPaths(org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$Message, int, java.util.List, boolean, int, java.util.HashMap):void");
    }

    private void putToDelayedMessages(String str, DelayedMessage delayedMessage) {
        ArrayList<DelayedMessage> arrayList = this.delayedMessages.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.delayedMessages.put(str, arrayList);
        }
        arrayList.add(delayedMessage);
    }

    public ArrayList<DelayedMessage> getDelayedMessages(String str) {
        return this.delayedMessages.get(str);
    }

    public long getNextRandomId() {
        long jNextLong = 0;
        while (jNextLong == 0) {
            jNextLong = Utilities.random.nextLong();
        }
        return jNextLong;
    }

    public void checkUnsentMessages() {
        getMessagesStorage().getUnsentMessages(MediaDataController.MAX_STYLE_RUNS_COUNT);
    }

    public void processUnsentMessages(final ArrayList<TLRPC.Message> arrayList, final ArrayList<TLRPC.Message> arrayList2, final ArrayList<TLRPC.User> arrayList3, final ArrayList<TLRPC.Chat> arrayList4, final ArrayList<TLRPC.EncryptedChat> arrayList5) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda99
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processUnsentMessages$94(arrayList3, arrayList4, arrayList5, arrayList, arrayList2);
            }
        });
    }

    public /* synthetic */ void lambda$processUnsentMessages$94(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5) {
        HashMap<String, String> map;
        getMessagesController().putUsers(arrayList, true);
        getMessagesController().putChats(arrayList2, true);
        getMessagesController().putEncryptedChats(arrayList3, true);
        int size = arrayList4.size();
        for (int i = 0; i < size; i++) {
            MessageObject messageObject = new MessageObject(this.currentAccount, (TLRPC.Message) arrayList4.get(i), false, true);
            long groupId = messageObject.getGroupId();
            if (groupId != 0 && (map = messageObject.messageOwner.params) != null && !map.containsKey("final") && (i == size - 1 || ((TLRPC.Message) arrayList4.get(i + 1)).grouped_id != groupId)) {
                messageObject.messageOwner.params.put("final", "1");
            }
            retrySendMessage(messageObject, true, 0L);
        }
        if (arrayList5 != null) {
            for (int i2 = 0; i2 < arrayList5.size(); i2++) {
                MessageObject messageObject2 = new MessageObject(this.currentAccount, (TLRPC.Message) arrayList5.get(i2), false, true);
                messageObject2.scheduled = true;
                retrySendMessage(messageObject2, true, 0L);
            }
        }
    }

    public ImportingStickers getImportingStickers(String str) {
        return this.importingStickersMap.get(str);
    }

    public ImportingHistory getImportingHistory(long j) {
        return this.importingHistoryMap.get(j);
    }

    public boolean isImportingStickers() {
        return this.importingStickersMap.size() != 0;
    }

    public boolean isImportingHistory() {
        return this.importingHistoryMap.size() != 0;
    }

    public void prepareImportHistory(long j, Uri uri, final ArrayList<Uri> arrayList, final MessagesStorage.LongCallback longCallback) {
        final SendMessagesHelper sendMessagesHelper;
        final long j2;
        final Uri uri2;
        if (this.importingHistoryMap.get(j) != null) {
            longCallback.run(0L);
            return;
        }
        if (DialogObject.isChatDialog(j)) {
            j2 = j;
            uri2 = uri;
            long j3 = -j2;
            TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(j3));
            if (chat != null && !chat.megagroup) {
                getMessagesController().convertToMegaGroup(null, j3, null, new MessagesStorage.LongCallback() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda48
                    @Override // org.telegram.messenger.MessagesStorage.LongCallback
                    public final void run(long j4) {
                        this.f$0.lambda$prepareImportHistory$95(uri2, arrayList, longCallback, j4);
                    }
                });
                return;
            }
            sendMessagesHelper = this;
        } else {
            sendMessagesHelper = this;
            j2 = j;
            uri2 = uri;
        }
        new Thread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$prepareImportHistory$100(arrayList, j2, uri2, longCallback);
            }
        }).start();
    }

    public /* synthetic */ void lambda$prepareImportHistory$95(Uri uri, ArrayList arrayList, MessagesStorage.LongCallback longCallback, long j) {
        if (j != 0) {
            prepareImportHistory(-j, uri, arrayList, longCallback);
        } else {
            longCallback.run(0L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:193:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$prepareImportHistory$100(java.util.ArrayList r17, final long r18, android.net.Uri r20, final org.telegram.messenger.MessagesStorage.LongCallback r21) {
        /*
            Method dump skipped, instruction units count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$prepareImportHistory$100(java.util.ArrayList, long, android.net.Uri, org.telegram.messenger.MessagesStorage$LongCallback):void");
    }

    public static /* synthetic */ void $r8$lambda$Vhwqqb7Dxs2mJSTBxTNS0rPJBbc(MessagesStorage.LongCallback longCallback) {
        Toast.makeText(ApplicationLoader.applicationContext, LocaleController.getString(C2797R.string.ImportFileTooLarge), 0).show();
        longCallback.run(0L);
    }

    public /* synthetic */ void lambda$prepareImportHistory$99(HashMap map, long j, ImportingHistory importingHistory, MessagesStorage.LongCallback longCallback) {
        this.importingHistoryFiles.putAll(map);
        this.importingHistoryMap.put(j, importingHistory);
        getFileLoader().uploadFile(importingHistory.historyPath, false, true, 0L, 67108864, true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(j));
        longCallback.run(j);
        try {
            ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, (Class<?>) ImportingService.class));
        } catch (Throwable th) {
            FileLog.m1048e(th);
        }
    }

    public void prepareImportStickers(final String str, final String str2, final String str3, final ArrayList<ImportingSticker> arrayList, final MessagesStorage.StringCallback stringCallback) {
        if (this.importingStickersMap.get(str2) != null) {
            stringCallback.run(null);
        } else {
            new Thread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$prepareImportStickers$103(str, str2, str3, arrayList, stringCallback);
                }
            }).start();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$prepareImportStickers$103(java.lang.String r9, final java.lang.String r10, java.lang.String r11, java.util.ArrayList r12, final org.telegram.messenger.MessagesStorage.StringCallback r13) {
        /*
            r8 = this;
            r0 = r12
            r12 = r10
            org.telegram.messenger.SendMessagesHelper$ImportingStickers r10 = new org.telegram.messenger.SendMessagesHelper$ImportingStickers
            r10.<init>()
            r10.title = r9
            r10.shortName = r12
            r10.software = r11
            java.util.HashMap r11 = new java.util.HashMap
            r11.<init>()
            int r9 = r0.size()
            r1 = 0
        L17:
            if (r1 >= r9) goto L5c
            java.lang.Object r2 = r0.get(r1)
            org.telegram.messenger.SendMessagesHelper$ImportingSticker r2 = (org.telegram.messenger.SendMessagesHelper.ImportingSticker) r2
            java.io.File r3 = new java.io.File
            java.lang.String r4 = r2.path
            r3.<init>(r4)
            boolean r4 = r3.exists()
            if (r4 == 0) goto L4e
            long r3 = r3.length()
            r5 = 0
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 != 0) goto L37
            goto L4e
        L37:
            long r5 = r10.totalSize
            long r5 = r5 + r3
            r10.totalSize = r5
            java.util.ArrayList<org.telegram.messenger.SendMessagesHelper$ImportingSticker> r3 = r10.uploadMedia
            r3.add(r2)
            java.util.HashMap<java.lang.String, org.telegram.messenger.SendMessagesHelper$ImportingSticker> r3 = r10.uploadSet
            java.lang.String r4 = r2.path
            r3.put(r4, r2)
            java.lang.String r2 = r2.path
            r11.put(r2, r10)
            goto L59
        L4e:
            if (r1 != 0) goto L59
            org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda0 r8 = new org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda0
            r8.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r8)
            return
        L59:
            int r1 = r1 + 1
            goto L17
        L5c:
            org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda1 r9 = new org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda1
            r7 = r9
            r9 = r8
            r8 = r7
            r8.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$prepareImportStickers$103(java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList, org.telegram.messenger.MessagesStorage$StringCallback):void");
    }

    public /* synthetic */ void lambda$prepareImportStickers$102(ImportingStickers importingStickers, HashMap map, String str, MessagesStorage.StringCallback stringCallback) {
        if (importingStickers.uploadMedia.get(0).item != null) {
            importingStickers.startImport();
        } else {
            this.importingStickersFiles.putAll(map);
            this.importingStickersMap.put(str, importingStickers);
            importingStickers.initImport();
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, str);
            stringCallback.run(str);
        }
        try {
            ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, (Class<?>) ImportingService.class));
        } catch (Throwable th) {
            FileLog.m1048e(th);
        }
    }

    public TLRPC.TL_photo generatePhotoSizes(String str, Uri uri) {
        return generatePhotoSizes(null, str, uri, false);
    }

    public TLRPC.TL_photo generatePhotoSizes(TLRPC.TL_photo tL_photo, String str, Uri uri, boolean z) {
        TLRPC.PhotoSize photoSizeScaleAndSaveImage;
        Bitmap bitmap;
        Bitmap bitmapLoadBitmap = ImageLoader.loadBitmap(str, uri, AndroidUtilities.getPhotoSize(z), AndroidUtilities.getPhotoSize(z), true);
        if (bitmapLoadBitmap == null) {
            bitmapLoadBitmap = ImageLoader.loadBitmap(str, uri, 800.0f, 800.0f, true);
        }
        Bitmap bitmap2 = bitmapLoadBitmap;
        ArrayList<TLRPC.PhotoSize> arrayList = new ArrayList<>();
        TLRPC.PhotoSize photoSizeScaleAndSaveImage2 = ImageLoader.scaleAndSaveImage(bitmap2, 90.0f, 90.0f, 55, true);
        if (photoSizeScaleAndSaveImage2 != null) {
            arrayList.add(photoSizeScaleAndSaveImage2);
        }
        if (z) {
            bitmap = bitmap2;
            photoSizeScaleAndSaveImage = ImageLoader.scaleAndSaveImage(null, bitmap, Bitmap.CompressFormat.JPEG, true, AndroidUtilities.getPhotoSize(z), AndroidUtilities.getPhotoSize(z), 99, false, 101, 101, false);
        } else {
            photoSizeScaleAndSaveImage = ImageLoader.scaleAndSaveImage(bitmap2, AndroidUtilities.getPhotoSize(z), AndroidUtilities.getPhotoSize(z), true, 80, false, 101, 101);
            bitmap = bitmap2;
        }
        if (photoSizeScaleAndSaveImage != null) {
            arrayList.add(photoSizeScaleAndSaveImage);
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        getUserConfig().saveConfig(false);
        TLRPC.TL_photo tL_photo2 = tL_photo == null ? new TLRPC.TL_photo() : tL_photo;
        tL_photo2.date = getConnectionsManager().getCurrentTime();
        tL_photo2.sizes = arrayList;
        tL_photo2.file_reference = new byte[0];
        return tL_photo2;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:526:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:563:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:573:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:575:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x029a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:592:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:600:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:604:0x02d7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:615:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:635:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:686:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:702:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:724:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:725:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:727:0x0534  */
    /* JADX WARN: Removed duplicated region for block: B:728:0x053a  */
    /* JADX WARN: Removed duplicated region for block: B:731:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:734:0x054e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:737:0x0557  */
    /* JADX WARN: Removed duplicated region for block: B:738:0x0560  */
    /* JADX WARN: Removed duplicated region for block: B:741:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:763:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:766:0x05ce A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:779:0x060d  */
    /* JADX WARN: Removed duplicated region for block: B:782:0x0616  */
    /* JADX WARN: Removed duplicated region for block: B:793:0x01cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:814:0x01de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:828:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int prepareSendingDocumentInternal(org.telegram.messenger.AccountInstance r38, java.lang.String r39, java.lang.String r40, android.net.Uri r41, java.lang.String r42, final long r43, final org.telegram.messenger.MessageObject r45, final org.telegram.messenger.MessageObject r46, final org.telegram.tgnet.tl.TL_stories.StoryItem r47, final org.telegram.ui.ChatActivity.ReplyQuote r48, final java.util.ArrayList<org.telegram.tgnet.TLRPC.MessageEntity> r49, final org.telegram.messenger.MessageObject r50, long[] r51, boolean r52, java.lang.CharSequence r53, final boolean r54, int r55, final int r56, java.lang.Integer[] r57, boolean r58, final java.lang.String r59, final int r60, final long r61, final boolean r63, final long r64, final long r66, final org.telegram.messenger.MessageSuggestionParams r68, final org.telegram.p035ui.Components.poll.PollSendParams r69, final int r70) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1666
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.prepareSendingDocumentInternal(org.telegram.messenger.AccountInstance, java.lang.String, java.lang.String, android.net.Uri, java.lang.String, long, org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject, org.telegram.tgnet.tl.TL_stories$StoryItem, org.telegram.ui.ChatActivity$ReplyQuote, java.util.ArrayList, org.telegram.messenger.MessageObject, long[], boolean, java.lang.CharSequence, boolean, int, int, java.lang.Integer[], boolean, java.lang.String, int, long, boolean, long, long, org.telegram.messenger.MessageSuggestionParams, org.telegram.ui.Components.poll.PollSendParams, int):int");
    }

    /* JADX INFO: renamed from: $r8$lambda$VKOZ-wI3g2foWUW-Ziuk5iJs2gA */
    public static /* synthetic */ void m6240$r8$lambda$VKOZwI3g2foWUWZiuk5iJs2gA(MessageObject messageObject, AccountInstance accountInstance, TLRPC.TL_document tL_document, String str, HashMap map, String str2, long j, MessageObject messageObject2, MessageObject messageObject3, String str3, ArrayList arrayList, boolean z, int i, int i2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, String str4, int i3, long j2, boolean z2, long j3, long j4, MessageSuggestionParams messageSuggestionParams, int i4, PollSendParams pollSendParams) {
        if (messageObject != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, null, null, tL_document, str, null, map, false, false, str2);
            return;
        }
        SendMessageParams sendMessageParamsM1080of = SendMessageParams.m1080of(tL_document, null, str, j, messageObject2, messageObject3, str3, arrayList, null, map, z, i, i2, 0, str2, null, false);
        sendMessageParamsM1080of.replyToStoryItem = storyItem;
        sendMessageParamsM1080of.replyQuote = replyQuote;
        sendMessageParamsM1080of.quick_reply_shortcut = str4;
        sendMessageParamsM1080of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1080of.effect_id = j2;
        sendMessageParamsM1080of.invert_media = z2;
        sendMessageParamsM1080of.payStars = j3;
        sendMessageParamsM1080of.monoForumPeer = j4;
        sendMessageParamsM1080of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1080of.pollIndex = i4;
        sendMessageParamsM1080of.pollSendParams = pollSendParams;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1080of);
    }

    private static boolean checkFileSize(AccountInstance accountInstance, Uri uri) {
        long j = 0;
        try {
            AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = ApplicationLoader.applicationContext.getContentResolver().openAssetFileDescriptor(uri, "r", null);
            if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                assetFileDescriptorOpenAssetFileDescriptor.getLength();
            }
            Cursor cursorQuery = ApplicationLoader.applicationContext.getContentResolver().query(uri, new String[]{"_size"}, null, null, null);
            int columnIndex = cursorQuery.getColumnIndex("_size");
            cursorQuery.moveToFirst();
            j = cursorQuery.getLong(columnIndex);
            cursorQuery.close();
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        return !FileLoader.checkUploadFileSize(accountInstance.getCurrentAccount(), j);
    }

    public static void prepareSendingArticle(AccountInstance accountInstance, ArrayList<TL_iv.PageBlock> arrayList, boolean z, long j, MessageObject messageObject, MessageObject messageObject2, boolean z2, int i, int i2, String str, int i3, long j2, long j3, long j4) {
        prepareSendingArticle(accountInstance, arrayList, null, null, null, z, j, messageObject, messageObject2, z2, i, i2, str, i3, j2, j3, j4);
    }

    public static void prepareSendingArticle(AccountInstance accountInstance, ArrayList<TL_iv.PageBlock> arrayList, ArrayList<TLRPC.InputPhoto> arrayList2, ArrayList<TLRPC.InputDocument> arrayList3, ArrayList<TLRPC.InputUser> arrayList4, boolean z, long j, MessageObject messageObject, MessageObject messageObject2, boolean z2, int i, int i2, String str, int i3, long j2, long j3, long j4) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        TL_iv.TL_inputRichMessage tL_inputRichMessage = new TL_iv.TL_inputRichMessage();
        tL_inputRichMessage.rtl = z;
        int size = arrayList.size();
        int i4 = 0;
        int i5 = 0;
        while (i5 < size) {
            TL_iv.PageBlock pageBlock = arrayList.get(i5);
            i5++;
            TL_iv.PageBlock inputPageBlock = toInputPageBlock(pageBlock);
            if (inputPageBlock != null) {
                tL_inputRichMessage.blocks.add(inputPageBlock);
            }
        }
        if (tL_inputRichMessage.blocks.isEmpty()) {
            return;
        }
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            tL_inputRichMessage.flags |= 4;
            tL_inputRichMessage.photos.addAll(arrayList2);
        }
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            tL_inputRichMessage.flags |= 8;
            tL_inputRichMessage.documents.addAll(arrayList3);
        }
        if (arrayList4 != null && !arrayList4.isEmpty()) {
            tL_inputRichMessage.flags |= 16;
            tL_inputRichMessage.users.addAll(arrayList4);
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayList<TL_iv.PageBlock> arrayList5 = tL_inputRichMessage.blocks;
        int size2 = arrayList5.size();
        while (i4 < size2) {
            TL_iv.PageBlock pageBlock2 = arrayList5.get(i4);
            i4++;
            clearRichTextParentsInBlock(pageBlock2, identityHashMap);
        }
        SendMessageParams sendMessageParamsOfRichMessage = SendMessageParams.ofRichMessage(tL_inputRichMessage, j, messageObject, messageObject2, null, null, z2, i, i2);
        sendMessageParamsOfRichMessage.quick_reply_shortcut = str;
        sendMessageParamsOfRichMessage.quick_reply_shortcut_id = i3;
        sendMessageParamsOfRichMessage.effect_id = j2;
        sendMessageParamsOfRichMessage.monoForumPeer = j3;
        sendMessageParamsOfRichMessage.payStars = j4;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsOfRichMessage);
    }

    private static TL_iv.RichMessage inputRichMessageToRichMessage(TL_iv.TL_inputRichMessage tL_inputRichMessage) {
        TL_iv.RichMessage richMessage = new TL_iv.RichMessage();
        if (tL_inputRichMessage != null) {
            richMessage.rtl = tL_inputRichMessage.rtl;
            richMessage.blocks = new ArrayList<>(tL_inputRichMessage.blocks);
        }
        return richMessage;
    }

    private static Integer tryParseInt(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static void clearRichTextParents(TL_iv.RichText richText) {
        if (richText == null) {
            return;
        }
        richText.parentRichText = null;
        TL_iv.RichText richText2 = richText.text;
        if (richText2 != null) {
            clearRichTextParents(richText2);
        }
        ArrayList<TL_iv.RichText> arrayList = richText.texts;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                TL_iv.RichText richText3 = arrayList.get(i);
                i++;
                clearRichTextParents(richText3);
            }
        }
    }

    private static void clearRichTextParentsInBlock(TL_iv.PageBlock pageBlock, IdentityHashMap<Object, Boolean> identityHashMap) {
        if (pageBlock == null || identityHashMap.put(pageBlock, Boolean.TRUE) != null) {
            return;
        }
        for (Field field : pageBlock.getClass().getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                try {
                    Object obj = field.get(pageBlock);
                    if (obj instanceof TL_iv.RichText) {
                        clearRichTextParents((TL_iv.RichText) obj);
                    } else if (obj instanceof TL_iv.PageBlock) {
                        clearRichTextParentsInBlock((TL_iv.PageBlock) obj, identityHashMap);
                    } else if (obj instanceof TL_iv.PageCaption) {
                        clearRichTextParents(((TL_iv.PageCaption) obj).text);
                        clearRichTextParents(((TL_iv.PageCaption) obj).credit);
                    } else if (obj instanceof List) {
                        for (Object obj2 : (List) obj) {
                            if (obj2 instanceof TL_iv.PageBlock) {
                                clearRichTextParentsInBlock((TL_iv.PageBlock) obj2, identityHashMap);
                            } else if (obj2 instanceof TL_iv.RichText) {
                                clearRichTextParents((TL_iv.RichText) obj2);
                            } else if ((obj2 instanceof TL_iv.PageListItem) || (obj2 instanceof TL_iv.PageListOrderedItem)) {
                                for (Field field2 : obj2.getClass().getFields()) {
                                    if (!Modifier.isStatic(field2.getModifiers())) {
                                        try {
                                            Object obj3 = field2.get(obj2);
                                            if (obj3 instanceof TL_iv.RichText) {
                                                clearRichTextParents((TL_iv.RichText) obj3);
                                            } else if (obj3 instanceof List) {
                                                for (Object obj4 : (List) obj3) {
                                                    if (obj4 instanceof TL_iv.PageBlock) {
                                                        clearRichTextParentsInBlock((TL_iv.PageBlock) obj4, identityHashMap);
                                                    }
                                                }
                                            }
                                        } catch (IllegalAccessException unused) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException unused2) {
                }
            }
        }
    }

    public static void prepareSendingDocument(AccountInstance accountInstance, String str, String str2, Uri uri, String str3, String str4, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, MessageObject messageObject3, boolean z, int i, InputContentInfoCompat inputContentInfoCompat, String str5, int i2, boolean z2) {
        ArrayList arrayList;
        if ((str == null || str2 == null) && uri == null) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (uri != null) {
            arrayList = new ArrayList();
            arrayList.add(uri);
        } else {
            arrayList = null;
        }
        if (str != null) {
            arrayList2.add(str);
            arrayList3.add(str2);
        }
        prepareSendingDocuments(accountInstance, (ArrayList<String>) arrayList2, (ArrayList<String>) arrayList3, (ArrayList<Uri>) arrayList, str3, str4, j, messageObject, messageObject2, storyItem, replyQuote, messageObject3, z, i, inputContentInfoCompat, str5, i2, 0L, z2, 0L);
    }

    public static void prepareSendingAudioDocuments(AccountInstance accountInstance, ArrayList<MessageObject> arrayList, CharSequence charSequence, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, boolean z, int i, int i2, MessageObject messageObject3, String str, int i3, long j2, boolean z2, long j3) {
        prepareSendingAudioDocuments(accountInstance, arrayList, charSequence, j, messageObject, messageObject2, storyItem, z, i, i2, messageObject3, str, i3, j2, z2, j3, null, null, false, null);
    }

    public static void prepareSendingAudioDocuments(final AccountInstance accountInstance, final ArrayList<MessageObject> arrayList, final CharSequence charSequence, final long j, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final boolean z, final int i, final int i2, final MessageObject messageObject3, final String str, final int i3, final long j2, final boolean z2, final long j3, final PollSendParams pollSendParams, final ArrayList<Integer> arrayList2, final boolean z3, final Runnable runnable) {
        new Thread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda110
            @Override // java.lang.Runnable
            public final void run() {
                SendMessagesHelper.m6232$r8$lambda$IsPeFkDQI1GJLcyEZRg7NopOHk(pollSendParams, arrayList, j, accountInstance, charSequence, z3, arrayList2, messageObject3, messageObject, messageObject2, z, i, i2, storyItem, str, i3, j2, z2, j3, runnable);
            }
        }).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:134:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x00db  */
    /* JADX INFO: renamed from: $r8$lambda$IsPeFkDQI1-GJLcyEZRg7NopOHk */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void m6232$r8$lambda$IsPeFkDQI1GJLcyEZRg7NopOHk(final org.telegram.p035ui.Components.poll.PollSendParams r34, java.util.ArrayList r35, final long r36, final org.telegram.messenger.AccountInstance r38, java.lang.CharSequence r39, boolean r40, java.util.ArrayList r41, final org.telegram.messenger.MessageObject r42, final org.telegram.messenger.MessageObject r43, final org.telegram.messenger.MessageObject r44, final boolean r45, final int r46, final int r47, final org.telegram.tgnet.tl.TL_stories.StoryItem r48, final java.lang.String r49, final int r50, final long r51, final boolean r53, final long r54, java.lang.Runnable r56) {
        /*
            Method dump skipped, instruction units count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.m6232$r8$lambda$IsPeFkDQI1GJLcyEZRg7NopOHk(org.telegram.ui.Components.poll.PollSendParams, java.util.ArrayList, long, org.telegram.messenger.AccountInstance, java.lang.CharSequence, boolean, java.util.ArrayList, org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject, boolean, int, int, org.telegram.tgnet.tl.TL_stories$StoryItem, java.lang.String, int, long, boolean, long, java.lang.Runnable):void");
    }

    /* JADX INFO: renamed from: $r8$lambda$H6UYKi400_4A_tLIL-lGuFTzo3w */
    public static /* synthetic */ void m6231$r8$lambda$H6UYKi400_4A_tLILlGuFTzo3w(MessageObject messageObject, AccountInstance accountInstance, TLRPC.TL_document tL_document, MessageObject messageObject2, HashMap map, String str, long j, MessageObject messageObject3, MessageObject messageObject4, String str2, ArrayList arrayList, boolean z, int i, int i2, TL_stories.StoryItem storyItem, String str3, int i3, long j2, boolean z2, long j3, PollSendParams pollSendParams, int i4) {
        if (messageObject != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, null, null, tL_document, messageObject2.messageOwner.attachPath, null, map, false, false, str);
            return;
        }
        SendMessageParams sendMessageParamsM1081of = SendMessageParams.m1081of(tL_document, null, messageObject2.messageOwner.attachPath, j, messageObject3, messageObject4, str2, arrayList, null, map, z, i, i2, 0, str, null, false, false);
        sendMessageParamsM1081of.replyToStoryItem = storyItem;
        sendMessageParamsM1081of.quick_reply_shortcut = str3;
        sendMessageParamsM1081of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1081of.effect_id = j2;
        sendMessageParamsM1081of.invert_media = z2;
        sendMessageParamsM1081of.payStars = j3;
        sendMessageParamsM1081of.pollSendParams = pollSendParams;
        sendMessageParamsM1081of.pollIndex = i4;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1081of);
    }

    private static void finishGroup(final AccountInstance accountInstance, final long j, final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() {
                SendMessagesHelper.m6257$r8$lambda$rk_VRMaWyrlE5Yzk7iYneAn5kk(accountInstance, j, i);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$rk_VRMaWyr-lE5Yzk7iYneAn5kk */
    public static /* synthetic */ void m6257$r8$lambda$rk_VRMaWyrlE5Yzk7iYneAn5kk(AccountInstance accountInstance, long j, int i) {
        SendMessagesHelper sendMessagesHelper = accountInstance.getSendMessagesHelper();
        ArrayList<DelayedMessage> arrayList = sendMessagesHelper.delayedMessages.get("group_" + j);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        DelayedMessage delayedMessage = arrayList.get(0);
        ArrayList<MessageObject> arrayList2 = delayedMessage.messageObjects;
        MessageObject messageObject = arrayList2.get(arrayList2.size() - 1);
        delayedMessage.finalGroupMessage = messageObject.getId();
        messageObject.messageOwner.params.put("final", "1");
        TLRPC.TL_messages_messages tL_messages_messages = new TLRPC.TL_messages_messages();
        tL_messages_messages.messages.add(messageObject.messageOwner);
        if (!delayedMessage.paidMedia && !delayedMessage.pollMedia) {
            accountInstance.getMessagesStorage().putMessages((TLRPC.messages_Messages) tL_messages_messages, delayedMessage.peer, -2, 0, false, i != 0 ? 1 : 0, 0L);
        }
        sendMessagesHelper.sendReadyToSendGroup(delayedMessage, true, true);
    }

    public static void prepareSendingDocuments(AccountInstance accountInstance, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<Uri> arrayList3, String str, String str2, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, MessageObject messageObject3, boolean z, int i, InputContentInfoCompat inputContentInfoCompat, String str3, int i2, long j2, boolean z2, long j3) {
        prepareSendingDocuments(accountInstance, arrayList, arrayList2, arrayList3, str, null, str2, j, messageObject, messageObject2, storyItem, replyQuote, messageObject3, z, i, 0, inputContentInfoCompat, str3, i2, j2, z2, j3, 0L, null);
    }

    public static void prepareSendingDocuments(AccountInstance accountInstance, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<Uri> arrayList3, CharSequence charSequence, String str, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, MessageObject messageObject3, boolean z, int i, InputContentInfoCompat inputContentInfoCompat, String str2, int i2, long j2, boolean z2, long j3) {
        CharSequence charSequence2;
        ArrayList<TLRPC.MessageEntity> arrayList4;
        if (charSequence != null) {
            CharSequence[] charSequenceArr = {charSequence};
            ArrayList<TLRPC.MessageEntity> entities = accountInstance.getMediaDataController().getEntities(charSequenceArr, true);
            charSequence2 = charSequenceArr[0];
            arrayList4 = entities;
        } else {
            charSequence2 = charSequence;
            arrayList4 = null;
        }
        prepareSendingDocuments(accountInstance, arrayList, arrayList2, arrayList3, charSequence2 != null ? charSequence2.toString() : null, arrayList4, str, j, messageObject, messageObject2, storyItem, replyQuote, messageObject3, z, i, 0, inputContentInfoCompat, str2, i2, j2, z2, j3, 0L, null);
    }

    public static void prepareSendingDocuments(AccountInstance accountInstance, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<Uri> arrayList3, String str, ArrayList<TLRPC.MessageEntity> arrayList4, String str2, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, MessageObject messageObject3, boolean z, int i, int i2, InputContentInfoCompat inputContentInfoCompat, String str3, int i3, long j2, boolean z2, long j3, long j4, MessageSuggestionParams messageSuggestionParams) {
        prepareSendingDocuments(accountInstance, arrayList, arrayList2, arrayList3, str, arrayList4, str2, j, messageObject, messageObject2, storyItem, replyQuote, messageObject3, z, i, i2, inputContentInfoCompat, str3, i3, j2, z2, j3, j4, messageSuggestionParams, null, null, null, false);
    }

    public static void prepareSendingDocuments(final AccountInstance accountInstance, final ArrayList<String> arrayList, final ArrayList<String> arrayList2, final ArrayList<Uri> arrayList3, final String str, final ArrayList<TLRPC.MessageEntity> arrayList4, final String str2, final long j, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final MessageObject messageObject3, final boolean z, final int i, final int i2, final InputContentInfoCompat inputContentInfoCompat, final String str3, final int i3, final long j2, final boolean z2, final long j3, final long j4, final MessageSuggestionParams messageSuggestionParams, final PollSendParams pollSendParams, final ArrayList<Integer> arrayList5, final ArrayList<Integer> arrayList6, final boolean z3) {
        if (arrayList == null && arrayList2 == null && arrayList3 == null) {
            return;
        }
        if (arrayList == null || arrayList2 == null || arrayList.size() == arrayList2.size()) {
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    SendMessagesHelper.$r8$lambda$a0DCtgvYqqIoqz2y6zhabhzerag(j, arrayList, str, pollSendParams, accountInstance, i, arrayList2, str2, messageObject, messageObject2, storyItem, replyQuote, arrayList4, messageObject3, z3, z, i2, inputContentInfoCompat, str3, i3, j2, z2, j3, j4, messageSuggestionParams, arrayList5, arrayList3, arrayList6);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void $r8$lambda$a0DCtgvYqqIoqz2y6zhabhzerag(long j, ArrayList arrayList, String str, PollSendParams pollSendParams, AccountInstance accountInstance, int i, ArrayList arrayList2, String str2, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, ArrayList arrayList3, MessageObject messageObject3, boolean z, boolean z2, int i2, InputContentInfoCompat inputContentInfoCompat, String str3, int i3, long j2, boolean z3, long j3, long j4, MessageSuggestionParams messageSuggestionParams, ArrayList arrayList4, ArrayList arrayList5, ArrayList arrayList6) throws Throwable {
        int i4;
        boolean z4;
        ArrayList arrayList7 = arrayList;
        int i5 = i;
        ArrayList arrayList8 = arrayList4;
        int i6 = 1;
        long[] jArr = new long[1];
        Integer[] numArr = new Integer[1];
        boolean zIsEncryptedDialog = DialogObject.isEncryptedDialog(j);
        int i7 = 10;
        if (arrayList7 != null) {
            int size = arrayList7.size();
            boolean z5 = true;
            int i8 = 0;
            i4 = 0;
            int i9 = 0;
            while (i8 < size) {
                String str4 = i8 == 0 ? str : null;
                if (!zIsEncryptedDialog && size > i6 && i9 % 10 == 0 && pollSendParams == null) {
                    long j5 = jArr[0];
                    if (j5 != 0) {
                        finishGroup(accountInstance, j5, i5);
                    }
                    jArr[0] = Utilities.random.nextLong();
                    i9 = 0;
                }
                int i10 = i9 + 1;
                long j6 = jArr[0];
                int i11 = i5;
                Integer[] numArr2 = numArr;
                int i12 = size;
                int i13 = i8;
                int iPrepareSendingDocumentInternal = prepareSendingDocumentInternal(accountInstance, (String) arrayList7.get(i8), (String) arrayList2.get(i8), null, str2, j, messageObject, messageObject2, storyItem, replyQuote, i8 == 0 ? arrayList3 : null, messageObject3, jArr, (z || !((pollSendParams == null && i10 == i7) || i8 == size + (-1))) ? 0 : i6, str4, z2, i11, i2, numArr2, inputContentInfoCompat == null ? i6 : 0, str3, i3, z5 ? j2 : 0L, z3, j3, j4, messageSuggestionParams, pollSendParams, arrayList8 != null ? ((Integer) arrayList8.get(i8)).intValue() : -1);
                long j7 = jArr[0];
                i9 = (j6 != j7 || j7 == -1) ? 1 : i10;
                i8 = i13 + 1;
                arrayList7 = arrayList;
                i4 = iPrepareSendingDocumentInternal;
                i5 = i11;
                numArr = numArr2;
                z5 = false;
                size = i12;
                i6 = 1;
                i7 = 10;
                arrayList8 = arrayList4;
            }
            z4 = z5;
        } else {
            i4 = 0;
            z4 = true;
        }
        int i14 = i5;
        Integer[] numArr3 = numArr;
        if (arrayList5 != null) {
            jArr[0] = 0;
            int size2 = arrayList5.size();
            int i15 = 0;
            int i16 = 0;
            while (i15 < arrayList5.size()) {
                String str5 = (i15 == 0 && (arrayList == null || arrayList.size() == 0)) ? str : null;
                ArrayList arrayList9 = (i15 == 0 && (arrayList == null || arrayList.size() == 0)) ? arrayList3 : null;
                if (!zIsEncryptedDialog && size2 > 1 && i16 % 10 == 0 && pollSendParams == null) {
                    long j8 = jArr[0];
                    if (j8 != 0) {
                        finishGroup(accountInstance, j8, i14);
                    }
                    jArr[0] = Utilities.random.nextLong();
                    i16 = 0;
                }
                int i17 = i16 + 1;
                long j9 = jArr[0];
                int i18 = size2;
                int i19 = i15;
                int iPrepareSendingDocumentInternal2 = prepareSendingDocumentInternal(accountInstance, null, null, (Uri) arrayList5.get(i15), str2, j, messageObject, messageObject2, storyItem, replyQuote, arrayList9, messageObject3, jArr, !z && ((pollSendParams == null && i17 == 10) || i15 == size2 + (-1)), str5, z2, i, i2, numArr3, inputContentInfoCompat == null, str3, i3, z4 ? j2 : 0L, z3, j3, j4, messageSuggestionParams, pollSendParams, arrayList6 != null ? ((Integer) arrayList6.get(i15)).intValue() : -1);
                long j10 = jArr[0];
                i16 = (j9 != j10 || j10 == -1) ? 1 : i17;
                i15 = i19 + 1;
                i14 = i;
                i4 = iPrepareSendingDocumentInternal2;
                z4 = false;
                size2 = i18;
            }
        }
        if (inputContentInfoCompat != null) {
            inputContentInfoCompat.releasePermission();
        }
        handleError(i4, accountInstance);
    }

    private static void handleError(final int i, final AccountInstance accountInstance) {
        if (i != 0) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda45
                @Override // java.lang.Runnable
                public final void run() {
                    SendMessagesHelper.$r8$lambda$lWfjWh_Fo_DsyNGS9ASLHF4d_4g(i, accountInstance);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$lWfjWh_Fo_DsyNGS9ASLHF4d_4g(int i, AccountInstance accountInstance) {
        try {
            if (i == 1) {
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 1, LocaleController.getString(C2797R.string.UnsupportedAttachment));
            } else if (i == 2) {
                NotificationCenter.getInstance(accountInstance.getCurrentAccount()).lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserShowLimitReachedDialog, 6);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
    }

    public static void prepareSendingPhoto(AccountInstance accountInstance, String str, Uri uri, long j, MessageObject messageObject, MessageObject messageObject2, ChatActivity.ReplyQuote replyQuote, CharSequence charSequence, ArrayList<TLRPC.MessageEntity> arrayList, ArrayList<TLRPC.InputDocument> arrayList2, InputContentInfoCompat inputContentInfoCompat, int i, MessageObject messageObject3, boolean z, int i2, int i3, String str2, int i4) {
        prepareSendingPhoto(accountInstance, str, null, uri, j, messageObject, messageObject2, null, null, arrayList, arrayList2, inputContentInfoCompat, i, messageObject3, null, z, i2, 0, i3, false, charSequence, str2, i4, 0L, 0L, 0L, null);
    }

    public static void prepareSendingPhoto(AccountInstance accountInstance, String str, String str2, Uri uri, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, ArrayList<TLRPC.MessageEntity> arrayList, ArrayList<TLRPC.InputDocument> arrayList2, InputContentInfoCompat inputContentInfoCompat, int i, MessageObject messageObject3, VideoEditedInfo videoEditedInfo, boolean z, int i2, int i3, boolean z2, CharSequence charSequence, String str3, int i4, long j2, long j3) {
        prepareSendingPhoto(accountInstance, str, str2, uri, j, messageObject, messageObject2, storyItem, replyQuote, arrayList, arrayList2, inputContentInfoCompat, i, messageObject3, videoEditedInfo, z, i2, 0, i3, z2, charSequence, str3, i4, j2, j3, 0L, null);
    }

    public static void prepareSendingPhoto(AccountInstance accountInstance, String str, String str2, Uri uri, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, ArrayList<TLRPC.MessageEntity> arrayList, ArrayList<TLRPC.InputDocument> arrayList2, InputContentInfoCompat inputContentInfoCompat, int i, MessageObject messageObject3, VideoEditedInfo videoEditedInfo, boolean z, int i2, int i3, int i4, boolean z2, CharSequence charSequence, String str3, int i5, long j2, long j3, long j4, MessageSuggestionParams messageSuggestionParams) {
        SendingMediaInfo sendingMediaInfo = new SendingMediaInfo();
        sendingMediaInfo.path = str;
        sendingMediaInfo.thumbPath = str2;
        sendingMediaInfo.uri = uri;
        if (charSequence != null) {
            sendingMediaInfo.caption = charSequence.toString();
        }
        sendingMediaInfo.entities = arrayList;
        sendingMediaInfo.ttl = i;
        if (arrayList2 != null) {
            sendingMediaInfo.masks = new ArrayList<>(arrayList2);
        }
        sendingMediaInfo.videoEditedInfo = videoEditedInfo;
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(sendingMediaInfo);
        prepareSendingMedia(accountInstance, arrayList3, j, messageObject, messageObject2, null, replyQuote, z2, false, messageObject3, z, i2, 0, i4, false, inputContentInfoCompat, str3, i5, j2, false, j3, j4, messageSuggestionParams);
    }

    public static void prepareSendingBotContextResult(BaseFragment baseFragment, AccountInstance accountInstance, TLRPC.BotInlineResult botInlineResult, HashMap<String, String> map, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, boolean z, int i, int i2, String str, int i3, long j2) {
        prepareSendingBotContextResult(baseFragment, accountInstance, botInlineResult, map, j, messageObject, messageObject2, storyItem, replyQuote, z, i, i2, str, i3, j2, 0L);
    }

    public static void prepareSendingBotContextResult(final BaseFragment baseFragment, final AccountInstance accountInstance, final TLRPC.BotInlineResult botInlineResult, final HashMap<String, String> map, final long j, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final boolean z, final int i, final int i2, final String str, final int i3, final long j2, final long j3) {
        SendMessageParams sendMessageParamsM1079of;
        TLRPC.TL_webPagePending tL_webPagePending;
        if (botInlineResult == null) {
            return;
        }
        TLRPC.BotInlineMessage botInlineMessage = botInlineResult.send_message;
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaAuto) {
            new Thread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda109
                @Override // java.lang.Runnable
                public final void run() {
                    SendMessagesHelper.m6251$r8$lambda$n_d5Sp_Vvx1Lr1btBQASY2oOlU(j, botInlineResult, accountInstance, map, baseFragment, messageObject, messageObject2, z, i, i2, str, i3, storyItem, replyQuote, j2, j3);
                }
            }).run();
            return;
        }
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageText) {
            if (DialogObject.isEncryptedDialog(j)) {
                for (int i4 = 0; i4 < botInlineResult.send_message.entities.size(); i4++) {
                    TLRPC.MessageEntity messageEntity = botInlineResult.send_message.entities.get(i4);
                    if (messageEntity instanceof TLRPC.TL_messageEntityUrl) {
                        tL_webPagePending = new TLRPC.TL_webPagePending();
                        String str2 = botInlineResult.send_message.message;
                        int i5 = messageEntity.offset;
                        tL_webPagePending.url = str2.substring(i5, messageEntity.length + i5);
                        break;
                    }
                }
                tL_webPagePending = null;
            } else {
                tL_webPagePending = null;
            }
            TLRPC.TL_webPagePending tL_webPagePending2 = tL_webPagePending;
            TLRPC.BotInlineMessage botInlineMessage2 = botInlineResult.send_message;
            SendMessageParams sendMessageParamsM1075of = SendMessageParams.m1075of(botInlineMessage2.message, j, messageObject, messageObject2, tL_webPagePending2, !botInlineMessage2.no_webpage, botInlineMessage2.entities, botInlineMessage2.reply_markup, map, z, i, i2, null, false);
            sendMessageParamsM1075of.quick_reply_shortcut = str;
            sendMessageParamsM1075of.quick_reply_shortcut_id = i3;
            sendMessageParamsM1075of.replyQuote = replyQuote;
            sendMessageParamsM1075of.payStars = j2;
            sendMessageParamsM1075of.monoForumPeer = j3;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1075of);
            return;
        }
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaVenue) {
            TLRPC.TL_messageMediaVenue tL_messageMediaVenue = new TLRPC.TL_messageMediaVenue();
            TLRPC.BotInlineMessage botInlineMessage3 = botInlineResult.send_message;
            tL_messageMediaVenue.geo = botInlineMessage3.geo;
            tL_messageMediaVenue.address = botInlineMessage3.address;
            tL_messageMediaVenue.title = botInlineMessage3.title;
            tL_messageMediaVenue.provider = botInlineMessage3.provider;
            tL_messageMediaVenue.venue_id = botInlineMessage3.venue_id;
            String str3 = botInlineMessage3.venue_type;
            tL_messageMediaVenue.venue_id = str3;
            tL_messageMediaVenue.venue_type = str3;
            if (str3 == null) {
                tL_messageMediaVenue.venue_type = _UrlKt.FRAGMENT_ENCODE_SET;
            }
            SendMessageParams sendMessageParamsM1079of2 = SendMessageParams.m1079of(tL_messageMediaVenue, j, messageObject, messageObject2, botInlineMessage3.reply_markup, map, z, i, i2);
            sendMessageParamsM1079of2.quick_reply_shortcut = str;
            sendMessageParamsM1079of2.quick_reply_shortcut_id = i3;
            sendMessageParamsM1079of2.replyQuote = replyQuote;
            sendMessageParamsM1079of2.payStars = j2;
            sendMessageParamsM1079of2.monoForumPeer = j3;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1079of2);
            return;
        }
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaGeo) {
            if (botInlineMessage.period != 0 || botInlineMessage.proximity_notification_radius != 0) {
                TLRPC.TL_messageMediaGeoLive tL_messageMediaGeoLive = new TLRPC.TL_messageMediaGeoLive();
                TLRPC.BotInlineMessage botInlineMessage4 = botInlineResult.send_message;
                int i6 = botInlineMessage4.period;
                if (i6 == 0) {
                    i6 = RichMessageLayout.PART_MAX_HEIGHT_DP;
                }
                tL_messageMediaGeoLive.period = i6;
                tL_messageMediaGeoLive.geo = botInlineMessage4.geo;
                tL_messageMediaGeoLive.heading = botInlineMessage4.heading;
                tL_messageMediaGeoLive.proximity_notification_radius = botInlineMessage4.proximity_notification_radius;
                sendMessageParamsM1079of = SendMessageParams.m1079of(tL_messageMediaGeoLive, j, messageObject, messageObject2, botInlineMessage4.reply_markup, map, z, i, i2);
            } else {
                TLRPC.TL_messageMediaGeo tL_messageMediaGeo = new TLRPC.TL_messageMediaGeo();
                TLRPC.BotInlineMessage botInlineMessage5 = botInlineResult.send_message;
                tL_messageMediaGeo.geo = botInlineMessage5.geo;
                tL_messageMediaGeo.heading = botInlineMessage5.heading;
                sendMessageParamsM1079of = SendMessageParams.m1079of(tL_messageMediaGeo, j, messageObject, messageObject2, botInlineMessage5.reply_markup, map, z, i, i2);
            }
            sendMessageParamsM1079of.quick_reply_shortcut = str;
            sendMessageParamsM1079of.quick_reply_shortcut_id = i3;
            sendMessageParamsM1079of.replyQuote = replyQuote;
            sendMessageParamsM1079of.payStars = j2;
            sendMessageParamsM1079of.monoForumPeer = j3;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1079of);
            return;
        }
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaContact) {
            TLRPC.TL_user tL_user = new TLRPC.TL_user();
            TLRPC.BotInlineMessage botInlineMessage6 = botInlineResult.send_message;
            tL_user.phone = botInlineMessage6.phone_number;
            tL_user.first_name = botInlineMessage6.first_name;
            tL_user.last_name = botInlineMessage6.last_name;
            TLRPC.RestrictionReason restrictionReason = new TLRPC.RestrictionReason();
            restrictionReason.text = botInlineResult.send_message.vcard;
            restrictionReason.platform = _UrlKt.FRAGMENT_ENCODE_SET;
            restrictionReason.reason = _UrlKt.FRAGMENT_ENCODE_SET;
            tL_user.restriction_reason.add(restrictionReason);
            SendMessageParams sendMessageParamsM1087of = SendMessageParams.m1087of(tL_user, j, messageObject, messageObject2, botInlineResult.send_message.reply_markup, map, z, i, i2);
            sendMessageParamsM1087of.quick_reply_shortcut = str;
            sendMessageParamsM1087of.quick_reply_shortcut_id = i3;
            sendMessageParamsM1087of.replyQuote = replyQuote;
            sendMessageParamsM1087of.payStars = j2;
            sendMessageParamsM1087of.monoForumPeer = j3;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1087of);
            return;
        }
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaInvoice) {
            if (DialogObject.isEncryptedDialog(j)) {
                return;
            }
            TLRPC.TL_botInlineMessageMediaInvoice tL_botInlineMessageMediaInvoice = (TLRPC.TL_botInlineMessageMediaInvoice) botInlineResult.send_message;
            TLRPC.TL_messageMediaInvoice tL_messageMediaInvoice = new TLRPC.TL_messageMediaInvoice();
            tL_messageMediaInvoice.shipping_address_requested = tL_botInlineMessageMediaInvoice.shipping_address_requested;
            tL_messageMediaInvoice.test = tL_botInlineMessageMediaInvoice.test;
            tL_messageMediaInvoice.title = tL_botInlineMessageMediaInvoice.title;
            tL_messageMediaInvoice.description = tL_botInlineMessageMediaInvoice.description;
            TLRPC.WebDocument webDocument = tL_botInlineMessageMediaInvoice.photo;
            if (webDocument != null) {
                tL_messageMediaInvoice.webPhoto = webDocument;
                tL_messageMediaInvoice.flags |= 1;
            }
            tL_messageMediaInvoice.currency = tL_botInlineMessageMediaInvoice.currency;
            tL_messageMediaInvoice.total_amount = tL_botInlineMessageMediaInvoice.total_amount;
            tL_messageMediaInvoice.start_param = _UrlKt.FRAGMENT_ENCODE_SET;
            SendMessageParams sendMessageParamsM1083of = SendMessageParams.m1083of(tL_messageMediaInvoice, j, messageObject, messageObject2, botInlineResult.send_message.reply_markup, map, z, i, i2);
            sendMessageParamsM1083of.quick_reply_shortcut = str;
            sendMessageParamsM1083of.quick_reply_shortcut_id = i3;
            sendMessageParamsM1083of.replyQuote = replyQuote;
            sendMessageParamsM1083of.payStars = j2;
            sendMessageParamsM1083of.monoForumPeer = j3;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1083of);
            return;
        }
        if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaWebPage) {
            TLRPC.TL_webPagePending tL_webPagePending3 = new TLRPC.TL_webPagePending();
            tL_webPagePending3.url = ((TLRPC.TL_botInlineMessageMediaWebPage) botInlineMessage).url;
            TLRPC.BotInlineMessage botInlineMessage7 = botInlineResult.send_message;
            SendMessageParams sendMessageParamsM1075of2 = SendMessageParams.m1075of(botInlineMessage7.message, j, messageObject, messageObject2, tL_webPagePending3, !botInlineMessage7.no_webpage, botInlineMessage7.entities, botInlineMessage7.reply_markup, map, z, i, i2, null, false);
            sendMessageParamsM1075of2.quick_reply_shortcut = str;
            sendMessageParamsM1075of2.quick_reply_shortcut_id = i3;
            sendMessageParamsM1075of2.replyQuote = replyQuote;
            sendMessageParamsM1075of2.payStars = j2;
            sendMessageParamsM1075of2.monoForumPeer = j3;
            sendMessageParamsM1075of2.invert_media = botInlineResult.send_message.invert_media;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1075of2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:284:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x04b3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:444:0x04be  */
    /* JADX WARN: Removed duplicated region for block: B:450:0x04fb  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0546  */
    /* JADX WARN: Removed duplicated region for block: B:459:0x0551  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x058b  */
    /* JADX WARN: Type inference failed for: r14v10, types: [org.telegram.tgnet.TLRPC$TL_photo] */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r14v9 */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.lang.String] */
    /* JADX INFO: renamed from: $r8$lambda$n_d5Sp_Vvx1Lr1btBQASY2-oOlU */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void m6251$r8$lambda$n_d5Sp_Vvx1Lr1btBQASY2oOlU(long r27, final org.telegram.tgnet.TLRPC.BotInlineResult r29, org.telegram.messenger.AccountInstance r30, final java.util.HashMap r31, final org.telegram.p035ui.ActionBar.BaseFragment r32, final org.telegram.messenger.MessageObject r33, final org.telegram.messenger.MessageObject r34, final boolean r35, final int r36, final int r37, final java.lang.String r38, final int r39, final org.telegram.tgnet.tl.TL_stories.StoryItem r40, final org.telegram.ui.ChatActivity.ReplyQuote r41, final long r42, final long r44) {
        /*
            Method dump skipped, instruction units count: 1564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.m6251$r8$lambda$n_d5Sp_Vvx1Lr1btBQASY2oOlU(long, org.telegram.tgnet.TLRPC$BotInlineResult, org.telegram.messenger.AccountInstance, java.util.HashMap, org.telegram.ui.ActionBar.BaseFragment, org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject, boolean, int, int, java.lang.String, int, org.telegram.tgnet.tl.TL_stories$StoryItem, org.telegram.ui.ChatActivity$ReplyQuote, long, long):void");
    }

    public static /* synthetic */ void $r8$lambda$gJM5pNNPdGGPHG0VF1lTuN0WNao(TLRPC.TL_document tL_document, Bitmap[] bitmapArr, String[] strArr, String str, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.BotInlineResult botInlineResult, HashMap map, boolean z, int i, int i2, TLRPC.TL_photo tL_photo, TLRPC.TL_game tL_game, String str2, int i3, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, long j2, long j3, AccountInstance accountInstance) {
        SendMessageParams sendMessageParamsM1082of;
        if (tL_document != null) {
            if (bitmapArr[0] != null && strArr[0] != null) {
                ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmapArr[0]), strArr[0], false);
            }
            TLRPC.BotInlineMessage botInlineMessage = botInlineResult.send_message;
            sendMessageParamsM1082of = SendMessageParams.m1080of(tL_document, null, str, j, messageObject, messageObject2, botInlineMessage.message, botInlineMessage.entities, botInlineMessage.reply_markup, map, z, i, i2, 0, botInlineResult, null, false);
        } else {
            sendMessageParamsM1082of = null;
            if (tL_photo != null) {
                TLRPC.WebDocument webDocument = botInlineResult.content;
                String str3 = webDocument != null ? webDocument.url : null;
                TLRPC.BotInlineMessage botInlineMessage2 = botInlineResult.send_message;
                sendMessageParamsM1082of = SendMessageParams.m1085of(tL_photo, str3, j, messageObject, messageObject2, botInlineMessage2.message, botInlineMessage2.entities, botInlineMessage2.reply_markup, map, z, i, i2, 0, botInlineResult, false);
            } else if (tL_game != null) {
                sendMessageParamsM1082of = SendMessageParams.m1082of(tL_game, j, messageObject, messageObject2, botInlineResult.send_message.reply_markup, (HashMap<String, String>) map, z, i, i2);
            }
        }
        if (sendMessageParamsM1082of != null) {
            sendMessageParamsM1082of.quick_reply_shortcut = str2;
            sendMessageParamsM1082of.quick_reply_shortcut_id = i3;
            sendMessageParamsM1082of.replyToStoryItem = storyItem;
            sendMessageParamsM1082of.replyQuote = replyQuote;
            sendMessageParamsM1082of.payStars = j2;
            sendMessageParamsM1082of.monoForumPeer = j3;
            accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1082of);
        }
    }

    public static String getTrimmedString(String str) {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return strTrim;
        }
        while (str.startsWith("\n")) {
            str = str.substring(1);
        }
        while (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static CharSequence getTrimmedString(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        CharSequence trimmedString = AndroidUtilities.getTrimmedString(charSequence);
        if (trimmedString.length() == 0) {
            return trimmedString;
        }
        while (charSequence.length() > 0 && charSequence.charAt(0) == '\n') {
            charSequence = charSequence.subSequence(1, charSequence.length());
        }
        while (charSequence.length() > 0 && charSequence.charAt(charSequence.length() - 1) == '\n') {
            charSequence = charSequence.subSequence(0, charSequence.length() - 1);
        }
        return charSequence;
    }

    public static void prepareSendingText(AccountInstance accountInstance, CharSequence charSequence, long j, boolean z, int i, int i2, long j2) {
        prepareSendingText(accountInstance, charSequence, j, 0L, z, i, i2, j2);
    }

    public static void prepareSendingText(final AccountInstance accountInstance, final CharSequence charSequence, final long j, final long j2, final boolean z, final int i, final int i2, final long j3) {
        accountInstance.getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() {
                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda100
                    @Override // java.lang.Runnable
                    public final void run() {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda120
                            @Override // java.lang.Runnable
                            public final void run() {
                                SendMessagesHelper.$r8$lambda$rZNu5KvE79LBmUZqNPvCn_hN5gQ(charSequence, j, accountInstance, j, z, i, i, j);
                            }
                        });
                    }
                });
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void $r8$lambda$rZNu5KvE79LBmUZqNPvCn_hN5gQ(java.lang.CharSequence r22, long r23, org.telegram.messenger.AccountInstance r25, long r26, boolean r28, int r29, int r30, long r31) {
        /*
            r0 = r23
            java.lang.CharSequence r2 = getTrimmedString(r22)
            if (r2 == 0) goto L9e
            int r3 = r2.length()
            if (r3 == 0) goto L9e
            int r3 = r2.length()
            float r3 = (float) r3
            r4 = 1166016512(0x45800000, float:4096.0)
            float r3 = r3 / r4
            double r3 = (double) r3
            double r3 = java.lang.Math.ceil(r3)
            int r3 = (int) r3
            r4 = 0
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L48
            org.telegram.messenger.MessagesController r4 = r25.getMessagesController()
            org.telegram.messenger.TopicsController r4 = r4.getTopicsController()
            r8 = r26
            long r10 = -r8
            org.telegram.tgnet.TLRPC$TL_forumTopic r0 = r4.findTopic(r10, r0)
            if (r0 == 0) goto L4a
            org.telegram.tgnet.TLRPC$Message r1 = r0.topicStartMessage
            if (r1 == 0) goto L4a
            org.telegram.messenger.MessageObject r1 = new org.telegram.messenger.MessageObject
            int r4 = r25.getCurrentAccount()
            org.telegram.tgnet.TLRPC$Message r0 = r0.topicStartMessage
            r1.<init>(r4, r0, r6, r6)
            r1.isTopicMainMessage = r5
        L46:
            r10 = r1
            goto L4c
        L48:
            r8 = r26
        L4a:
            r1 = 0
            goto L46
        L4c:
            r0 = r6
        L4d:
            if (r0 >= r3) goto L9e
            int r1 = r0 * 4096
            int r4 = r0 + 1
            int r7 = r4 * 4096
            int r11 = r2.length()
            int r7 = java.lang.Math.min(r7, r11)
            java.lang.CharSequence r1 = r2.subSequence(r1, r7)
            java.lang.CharSequence[] r7 = new java.lang.CharSequence[r5]
            r7[r6] = r1
            org.telegram.messenger.MediaDataController r1 = r25.getMediaDataController()
            java.util.ArrayList r1 = r1.getEntities(r7, r5)
            r7 = r7[r6]
            java.lang.String r7 = r7.toString()
            r20 = 0
            r21 = 0
            r12 = 0
            r13 = 1
            r14 = 0
            r15 = 0
            r16 = 0
            r11 = r10
            r17 = r28
            r18 = r29
            r19 = r30
            org.telegram.messenger.SendMessagesHelper$SendMessageParams r7 = org.telegram.messenger.SendMessagesHelper.SendMessageParams.m1075of(r7, r8, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            r7.entities = r1
            if (r0 != 0) goto L91
            r0 = r31
            r7.effect_id = r0
            goto L93
        L91:
            r0 = r31
        L93:
            org.telegram.messenger.SendMessagesHelper r8 = r25.getSendMessagesHelper()
            r8.sendMessage(r7)
            r8 = r26
            r0 = r4
            goto L4d
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.$r8$lambda$rZNu5KvE79LBmUZqNPvCn_hN5gQ(java.lang.CharSequence, long, org.telegram.messenger.AccountInstance, long, boolean, int, int, long):void");
    }

    public static void ensureMediaThumbExists(AccountInstance accountInstance, boolean z, TLObject tLObject, String str, Uri uri, long j) {
        ensureMediaThumbExists(accountInstance, z, tLObject, str, uri, j, false);
    }

    public static void ensureMediaThumbExists(AccountInstance accountInstance, boolean z, TLObject tLObject, String str, Uri uri, long j, boolean z2) {
        TLRPC.PhotoSize photoSizeScaleAndSaveImage;
        TLRPC.PhotoSize photoSizeScaleAndSaveImage2;
        if (tLObject instanceof TLRPC.TL_photo) {
            TLRPC.TL_photo tL_photo = (TLRPC.TL_photo) tLObject;
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tL_photo.sizes, 90);
            boolean zExists = ((closestPhotoSizeWithSize instanceof TLRPC.TL_photoStrippedSize) || (closestPhotoSizeWithSize instanceof TLRPC.TL_photoPathSize)) ? true : FileLoader.getInstance(accountInstance.getCurrentAccount()).getPathToAttach(closestPhotoSizeWithSize, true).exists();
            TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(tL_photo.sizes, AndroidUtilities.getPhotoSize(z2));
            boolean zExists2 = FileLoader.getInstance(accountInstance.getCurrentAccount()).getPathToAttach(closestPhotoSizeWithSize2, false).exists();
            if (zExists && zExists2) {
                return;
            }
            Bitmap bitmapLoadBitmap = ImageLoader.loadBitmap(str, uri, AndroidUtilities.getPhotoSize(), AndroidUtilities.getPhotoSize(), true);
            if (bitmapLoadBitmap == null) {
                bitmapLoadBitmap = ImageLoader.loadBitmap(str, uri, 800.0f, 800.0f, true);
            }
            Bitmap bitmap = bitmapLoadBitmap;
            if (!zExists2 && (photoSizeScaleAndSaveImage2 = ImageLoader.scaleAndSaveImage(closestPhotoSizeWithSize2, bitmap, Bitmap.CompressFormat.JPEG, true, AndroidUtilities.getPhotoSize(), AndroidUtilities.getPhotoSize(), 80, false, 101, 101, false)) != closestPhotoSizeWithSize2) {
                tL_photo.sizes.add(0, photoSizeScaleAndSaveImage2);
            }
            if (!zExists && (photoSizeScaleAndSaveImage = ImageLoader.scaleAndSaveImage(closestPhotoSizeWithSize, bitmap, 90.0f, 90.0f, 55, true, false)) != closestPhotoSizeWithSize) {
                tL_photo.sizes.add(0, photoSizeScaleAndSaveImage);
            }
            if (bitmap != null) {
                bitmap.recycle();
                return;
            }
            return;
        }
        if (tLObject instanceof TLRPC.TL_document) {
            TLRPC.TL_document tL_document = (TLRPC.TL_document) tLObject;
            if ((MessageObject.isVideoDocument(tL_document) || MessageObject.isNewGifDocument(tL_document)) && MessageObject.isDocumentHasThumb(tL_document)) {
                TLRPC.PhotoSize closestPhotoSizeWithSize3 = FileLoader.getClosestPhotoSizeWithSize(tL_document.thumbs, 320);
                if ((closestPhotoSizeWithSize3 instanceof TLRPC.TL_photoStrippedSize) || (closestPhotoSizeWithSize3 instanceof TLRPC.TL_photoPathSize) || FileLoader.getInstance(accountInstance.getCurrentAccount()).getPathToAttach(closestPhotoSizeWithSize3, true).exists()) {
                    return;
                }
                Bitmap bitmapCreateVideoThumbnailAtTime = createVideoThumbnailAtTime(str, j);
                if (bitmapCreateVideoThumbnailAtTime == null) {
                    bitmapCreateVideoThumbnailAtTime = createVideoThumbnail(str, 1);
                }
                int i = z ? 90 : 320;
                float f = i;
                tL_document.thumbs.set(0, ImageLoader.scaleAndSaveImage(closestPhotoSizeWithSize3, bitmapCreateVideoThumbnailAtTime, f, f, i > 90 ? 80 : 55, false, true));
            }
        }
    }

    public static String getKeyForPhotoSize(AccountInstance accountInstance, TLRPC.PhotoSize photoSize, Bitmap[] bitmapArr, boolean z, boolean z2) {
        if (photoSize == null || photoSize.location == null) {
            return null;
        }
        PointF messageSize = ChatMessageCell.getMessageSize(photoSize.f1278w, photoSize.f1277h);
        if (bitmapArr != null) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                File pathToAttach = FileLoader.getInstance(accountInstance.getCurrentAccount()).getPathToAttach(photoSize, z2);
                FileInputStream fileInputStream = new FileInputStream(pathToAttach);
                BitmapFactory.decodeStream(fileInputStream, null, options);
                fileInputStream.close();
                float fMax = Math.max(options.outWidth / messageSize.x, options.outHeight / messageSize.y);
                if (fMax < 1.0f) {
                    fMax = 1.0f;
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = (int) fMax;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                FileInputStream fileInputStream2 = new FileInputStream(pathToAttach);
                bitmapArr[0] = BitmapFactory.decodeStream(fileInputStream2, null, options);
                fileInputStream2.close();
            } catch (Throwable unused) {
            }
        }
        return String.format(Locale.US, z ? "%d_%d@%d_%d_b" : "%d_%d@%d_%d", Long.valueOf(photoSize.location.volume_id), Integer.valueOf(photoSize.location.local_id), Integer.valueOf((int) (messageSize.x / AndroidUtilities.density)), Integer.valueOf((int) (messageSize.y / AndroidUtilities.density)));
    }

    public static boolean shouldSendWebPAsSticker(String str, Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (str != null) {
            try {
                BitmapFactory.decodeFile(str, options);
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        } else {
            try {
                InputStream inputStreamOpenInputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
                try {
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                } finally {
                }
            } catch (Exception unused) {
            }
        }
        return options.outWidth < 800 && options.outHeight < 800;
    }

    public static void prepareSendingMedia(AccountInstance accountInstance, ArrayList<SendingMediaInfo> arrayList, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, boolean z, boolean z2, MessageObject messageObject3, boolean z3, int i, int i2, int i3, boolean z4, InputContentInfoCompat inputContentInfoCompat, String str, int i4, long j2, boolean z5, long j3, long j4, MessageSuggestionParams messageSuggestionParams) {
        prepareSendingMedia(accountInstance, arrayList, j, messageObject, messageObject2, storyItem, replyQuote, z, z2, messageObject3, null, z3, i, i2, i3, z4, inputContentInfoCompat, str, i4, j2, z5, j3, j4, messageSuggestionParams);
    }

    public static void prepareSendingMedia(AccountInstance accountInstance, ArrayList<SendingMediaInfo> arrayList, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, boolean z, boolean z2, MessageObject messageObject3, TLRPC.TL_inputPollAnswer tL_inputPollAnswer, boolean z3, int i, int i2, int i3, boolean z4, InputContentInfoCompat inputContentInfoCompat, String str, int i4, long j2, boolean z5, long j3, long j4, MessageSuggestionParams messageSuggestionParams) {
        prepareSendingMedia(accountInstance, arrayList, j, messageObject, messageObject2, storyItem, replyQuote, z, z2, messageObject3, tL_inputPollAnswer, z3, i, i2, i3, z4, inputContentInfoCompat, str, i4, j2, z5, j3, j4, messageSuggestionParams, null, false);
    }

    public static void prepareSendingMedia(final AccountInstance accountInstance, final ArrayList<SendingMediaInfo> arrayList, final long j, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final boolean z, boolean z2, final MessageObject messageObject3, final TLRPC.TL_inputPollAnswer tL_inputPollAnswer, final boolean z3, final int i, final int i2, int i3, final boolean z4, final InputContentInfoCompat inputContentInfoCompat, final String str, final int i4, final long j2, final boolean z5, final long j3, final long j4, final MessageSuggestionParams messageSuggestionParams, final PollSendParams pollSendParams, final boolean z6) {
        final boolean z7;
        if (arrayList.isEmpty()) {
            return;
        }
        int size = arrayList.size();
        int i5 = 0;
        while (true) {
            if (i5 >= size) {
                z7 = z2;
                break;
            } else {
                if (arrayList.get(i5).ttl > 0) {
                    z7 = false;
                    break;
                }
                i5++;
            }
        }
        final long j5 = pollSendParams != null ? pollSendParams.groupId : 0L;
        final boolean z8 = j5 != 0;
        mediaSendQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                SendMessagesHelper.lambda$prepareSendingMedia$122(arrayList, j, z, z7, z8, accountInstance, j5, messageObject3, tL_inputPollAnswer, messageObject, messageObject2, z3, i, i2, storyItem, replyQuote, str, i4, j2, z5, j3, j4, messageSuggestionParams, pollSendParams, z6, inputContentInfoCompat, z4);
            }
        });
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:1370|(1:1375)(1:1374)|1376|(4:1378|(2:1381|1379)|1832|1382)|(1:1384)|(1:1386)|(6:1809|1388|1389|(0)|(3:1402|(3:(2:1410|(0))(1:1408)|1409|1413)(1:1414)|1415)(1:1416)|1417)(1:1394)|1801|1395|(1:1397)|(0)(0)|1417) */
    /* JADX WARN: Can't wrap try/catch for region: R(7:1815|1232|(4:1803|1233|1234|(4:1787|1235|1236|(3:1783|1237|1238)))|(2:1781|1239)|(3:1775|1240|(2:1811|1242))|1805|1243) */
    /* JADX WARN: Code restructure failed: missing block: B:1399:0x0afd, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1701:0x11e8, code lost:
    
        if (r14 == r8) goto L1699;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1012:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:1026:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:1027:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:1029:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:1053:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:1056:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:1068:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:1071:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:1072:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:1135:0x04b5 A[Catch: Exception -> 0x0498, TRY_LEAVE, TryCatch #5 {Exception -> 0x0498, blocks: (B:1121:0x0490, B:1128:0x049d, B:1132:0x04ac, B:1135:0x04b5), top: B:1779:0x0490 }] */
    /* JADX WARN: Removed duplicated region for block: B:1154:0x04f3  */
    /* JADX WARN: Removed duplicated region for block: B:1155:0x051c  */
    /* JADX WARN: Removed duplicated region for block: B:1161:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:1164:0x053b  */
    /* JADX WARN: Removed duplicated region for block: B:1270:0x0846  */
    /* JADX WARN: Removed duplicated region for block: B:1272:0x0850  */
    /* JADX WARN: Removed duplicated region for block: B:1302:0x08c2  */
    /* JADX WARN: Removed duplicated region for block: B:1303:0x08ce  */
    /* JADX WARN: Removed duplicated region for block: B:1305:0x08d3  */
    /* JADX WARN: Removed duplicated region for block: B:1309:0x0925  */
    /* JADX WARN: Removed duplicated region for block: B:1358:0x0a0c  */
    /* JADX WARN: Removed duplicated region for block: B:1402:0x0b03  */
    /* JADX WARN: Removed duplicated region for block: B:1416:0x0b44  */
    /* JADX WARN: Removed duplicated region for block: B:1476:0x0d21  */
    /* JADX WARN: Removed duplicated region for block: B:1488:0x0d7d  */
    /* JADX WARN: Removed duplicated region for block: B:1535:0x0eeb  */
    /* JADX WARN: Removed duplicated region for block: B:1536:0x0ef0  */
    /* JADX WARN: Removed duplicated region for block: B:1542:0x0f04  */
    /* JADX WARN: Removed duplicated region for block: B:1564:0x0f63  */
    /* JADX WARN: Removed duplicated region for block: B:1567:0x0f7e  */
    /* JADX WARN: Removed duplicated region for block: B:1570:0x0f8f  */
    /* JADX WARN: Removed duplicated region for block: B:1573:0x0f99  */
    /* JADX WARN: Removed duplicated region for block: B:1583:0x0fec  */
    /* JADX WARN: Removed duplicated region for block: B:1585:0x0ff0  */
    /* JADX WARN: Removed duplicated region for block: B:1586:0x0ff6  */
    /* JADX WARN: Removed duplicated region for block: B:1589:0x1005  */
    /* JADX WARN: Removed duplicated region for block: B:1611:0x1056  */
    /* JADX WARN: Removed duplicated region for block: B:1613:0x105c  */
    /* JADX WARN: Removed duplicated region for block: B:1616:0x1073  */
    /* JADX WARN: Removed duplicated region for block: B:1667:0x1141  */
    /* JADX WARN: Removed duplicated region for block: B:1679:0x1165  */
    /* JADX WARN: Removed duplicated region for block: B:1680:0x1169  */
    /* JADX WARN: Removed duplicated region for block: B:1689:0x11b5  */
    /* JADX WARN: Removed duplicated region for block: B:1707:0x11fa  */
    /* JADX WARN: Removed duplicated region for block: B:1709:0x1206 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1719:0x1241 A[LOOP:5: B:1717:0x1239->B:1719:0x1241, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:1758:0x13a3  */
    /* JADX WARN: Removed duplicated region for block: B:1769:0x082c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1785:0x04a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1789:0x04c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1833:0x114d A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v167 */
    /* JADX WARN: Type inference failed for: r0v168, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v173 */
    /* JADX WARN: Type inference failed for: r0v174, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v190 */
    /* JADX WARN: Type inference failed for: r0v200 */
    /* JADX WARN: Type inference failed for: r0v201, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v310 */
    /* JADX WARN: Type inference failed for: r0v311 */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v52 */
    /* JADX WARN: Type inference failed for: r11v53 */
    /* JADX WARN: Type inference failed for: r11v54 */
    /* JADX WARN: Type inference failed for: r11v56 */
    /* JADX WARN: Type inference failed for: r11v57 */
    /* JADX WARN: Type inference failed for: r11v58, types: [org.telegram.messenger.SendMessagesHelper-IA] */
    /* JADX WARN: Type inference failed for: r11v59 */
    /* JADX WARN: Type inference failed for: r11v61 */
    /* JADX WARN: Type inference failed for: r11v67 */
    /* JADX WARN: Type inference failed for: r11v69 */
    /* JADX WARN: Type inference failed for: r11v72 */
    /* JADX WARN: Type inference failed for: r11v76 */
    /* JADX WARN: Type inference failed for: r11v77 */
    /* JADX WARN: Type inference failed for: r11v79 */
    /* JADX WARN: Type inference failed for: r11v80 */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r12v16 */
    /* JADX WARN: Type inference failed for: r12v25 */
    /* JADX WARN: Type inference failed for: r12v26, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v32 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v27 */
    /* JADX WARN: Type inference failed for: r13v28, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r13v30 */
    /* JADX WARN: Type inference failed for: r13v35 */
    /* JADX WARN: Type inference failed for: r13v38 */
    /* JADX WARN: Type inference failed for: r13v39, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r13v41 */
    /* JADX WARN: Type inference failed for: r13v44 */
    /* JADX WARN: Type inference failed for: r13v46 */
    /* JADX WARN: Type inference failed for: r13v48 */
    /* JADX WARN: Type inference failed for: r13v72 */
    /* JADX WARN: Type inference failed for: r13v78 */
    /* JADX WARN: Type inference failed for: r13v79 */
    /* JADX WARN: Type inference failed for: r13v80 */
    /* JADX WARN: Type inference failed for: r13v81 */
    /* JADX WARN: Type inference failed for: r13v82 */
    /* JADX WARN: Type inference failed for: r13v83 */
    /* JADX WARN: Type inference failed for: r13v84 */
    /* JADX WARN: Type inference failed for: r13v85 */
    /* JADX WARN: Type inference failed for: r13v86 */
    /* JADX WARN: Type inference failed for: r13v9 */
    /* JADX WARN: Type inference failed for: r14v3, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r20v20 */
    /* JADX WARN: Type inference failed for: r20v21 */
    /* JADX WARN: Type inference failed for: r22v1 */
    /* JADX WARN: Type inference failed for: r22v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r22v8 */
    /* JADX WARN: Type inference failed for: r23v0 */
    /* JADX WARN: Type inference failed for: r23v1 */
    /* JADX WARN: Type inference failed for: r23v18 */
    /* JADX WARN: Type inference failed for: r23v19 */
    /* JADX WARN: Type inference failed for: r23v21 */
    /* JADX WARN: Type inference failed for: r23v23 */
    /* JADX WARN: Type inference failed for: r23v24 */
    /* JADX WARN: Type inference failed for: r23v25 */
    /* JADX WARN: Type inference failed for: r23v26 */
    /* JADX WARN: Type inference failed for: r23v28 */
    /* JADX WARN: Type inference failed for: r23v29 */
    /* JADX WARN: Type inference failed for: r23v30 */
    /* JADX WARN: Type inference failed for: r23v4 */
    /* JADX WARN: Type inference failed for: r24v2 */
    /* JADX WARN: Type inference failed for: r30v1 */
    /* JADX WARN: Type inference failed for: r31v0 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v109 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v110 */
    /* JADX WARN: Type inference failed for: r3v111 */
    /* JADX WARN: Type inference failed for: r3v112 */
    /* JADX WARN: Type inference failed for: r3v113 */
    /* JADX WARN: Type inference failed for: r3v114 */
    /* JADX WARN: Type inference failed for: r3v115 */
    /* JADX WARN: Type inference failed for: r3v116 */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r3v74, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r3v78 */
    /* JADX WARN: Type inference failed for: r3v81 */
    /* JADX WARN: Type inference failed for: r3v82, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v86 */
    /* JADX WARN: Type inference failed for: r3v87 */
    /* JADX WARN: Type inference failed for: r3v88 */
    /* JADX WARN: Type inference failed for: r47v0 */
    /* JADX WARN: Type inference failed for: r47v1 */
    /* JADX WARN: Type inference failed for: r47v11 */
    /* JADX WARN: Type inference failed for: r47v12 */
    /* JADX WARN: Type inference failed for: r47v14 */
    /* JADX WARN: Type inference failed for: r47v15 */
    /* JADX WARN: Type inference failed for: r47v5 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v71, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v73 */
    /* JADX WARN: Type inference failed for: r4v74 */
    /* JADX WARN: Type inference failed for: r4v75, types: [org.telegram.tgnet.TLRPC$TL_photo] */
    /* JADX WARN: Type inference failed for: r4v78, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r4v92 */
    /* JADX WARN: Type inference failed for: r4v93 */
    /* JADX WARN: Type inference failed for: r50v1, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r50v2 */
    /* JADX WARN: Type inference failed for: r50v3 */
    /* JADX WARN: Type inference failed for: r50v5 */
    /* JADX WARN: Type inference failed for: r53v13 */
    /* JADX WARN: Type inference failed for: r54v0 */
    /* JADX WARN: Type inference failed for: r54v1 */
    /* JADX WARN: Type inference failed for: r54v2 */
    /* JADX WARN: Type inference failed for: r55v0 */
    /* JADX WARN: Type inference failed for: r55v1 */
    /* JADX WARN: Type inference failed for: r55v2 */
    /* JADX WARN: Type inference failed for: r56v0 */
    /* JADX WARN: Type inference failed for: r56v1 */
    /* JADX WARN: Type inference failed for: r56v2 */
    /* JADX WARN: Type inference failed for: r56v3 */
    /* JADX WARN: Type inference failed for: r56v4 */
    /* JADX WARN: Type inference failed for: r56v5 */
    /* JADX WARN: Type inference failed for: r57v0 */
    /* JADX WARN: Type inference failed for: r57v1 */
    /* JADX WARN: Type inference failed for: r57v2 */
    /* JADX WARN: Type inference failed for: r57v3 */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v106 */
    /* JADX WARN: Type inference failed for: r7v107 */
    /* JADX WARN: Type inference failed for: r7v108 */
    /* JADX WARN: Type inference failed for: r7v109 */
    /* JADX WARN: Type inference failed for: r7v110 */
    /* JADX WARN: Type inference failed for: r7v113 */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v36, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v38 */
    /* JADX WARN: Type inference failed for: r7v42 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v63 */
    /* JADX WARN: Type inference failed for: r7v64, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v66 */
    /* JADX WARN: Type inference failed for: r7v68 */
    /* JADX WARN: Type inference failed for: r7v69 */
    /* JADX WARN: Type inference failed for: r7v7, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v81 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r8v32 */
    /* JADX WARN: Type inference failed for: r8v33, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v36 */
    /* JADX WARN: Type inference failed for: r8v38 */
    /* JADX WARN: Type inference failed for: r8v39, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v41 */
    /* JADX WARN: Type inference failed for: r8v45 */
    /* JADX WARN: Type inference failed for: r8v47 */
    /* JADX WARN: Type inference failed for: r8v49 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r8v58 */
    /* JADX WARN: Type inference failed for: r8v59 */
    /* JADX WARN: Type inference failed for: r8v60 */
    /* JADX WARN: Type inference failed for: r8v61 */
    /* JADX WARN: Type inference failed for: r8v62 */
    /* JADX WARN: Type inference failed for: r8v65 */
    /* JADX WARN: Type inference failed for: r8v67 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v33 */
    /* JADX WARN: Type inference failed for: r9v37 */
    /* JADX WARN: Type inference failed for: r9v39 */
    /* JADX WARN: Type inference failed for: r9v40, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v42 */
    /* JADX WARN: Type inference failed for: r9v44 */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference failed for: r9v48 */
    /* JADX WARN: Type inference failed for: r9v53, types: [org.telegram.messenger.MediaController$PhotoEntry] */
    /* JADX WARN: Type inference failed for: r9v59, types: [org.telegram.messenger.MessagesStorage] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v60 */
    /* JADX WARN: Type inference failed for: r9v61 */
    /* JADX WARN: Type inference failed for: r9v62 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v82 */
    /* JADX WARN: Type inference failed for: r9v83 */
    /* JADX WARN: Type inference failed for: r9v84 */
    /* JADX WARN: Type inference failed for: r9v85 */
    /* JADX WARN: Type inference failed for: r9v86 */
    /* JADX WARN: Type inference failed for: r9v87 */
    /* JADX WARN: Type inference failed for: r9v88 */
    /* JADX WARN: Type inference failed for: r9v89 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /* JADX WARN: Type inference failed for: r9v90 */
    /* JADX WARN: Type inference failed for: r9v91 */
    /* JADX WARN: Type inference failed for: r9v94 */
    /* JADX WARN: Type inference failed for: r9v95 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$prepareSendingMedia$122(java.util.ArrayList r87, final long r88, boolean r90, boolean r91, boolean r92, final org.telegram.messenger.AccountInstance r93, long r94, final org.telegram.messenger.MessageObject r96, final org.telegram.tgnet.TLRPC.TL_inputPollAnswer r97, final org.telegram.messenger.MessageObject r98, final org.telegram.messenger.MessageObject r99, final boolean r100, final int r101, final int r102, final org.telegram.tgnet.tl.TL_stories.StoryItem r103, final org.telegram.ui.ChatActivity.ReplyQuote r104, final java.lang.String r105, final int r106, final long r107, final boolean r109, final long r110, final long r112, final org.telegram.messenger.MessageSuggestionParams r114, final org.telegram.p035ui.Components.poll.PollSendParams r115, boolean r116, androidx.core.view.inputmethod.InputContentInfoCompat r117, final boolean r118) {
        /*
            Method dump skipped, instruction units count: 5147
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.lambda$prepareSendingMedia$122(java.util.ArrayList, long, boolean, boolean, boolean, org.telegram.messenger.AccountInstance, long, org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$TL_inputPollAnswer, org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject, boolean, int, int, org.telegram.tgnet.tl.TL_stories$StoryItem, org.telegram.ui.ChatActivity$ReplyQuote, java.lang.String, int, long, boolean, long, long, org.telegram.messenger.MessageSuggestionParams, org.telegram.ui.Components.poll.PollSendParams, boolean, androidx.core.view.inputmethod.InputContentInfoCompat, boolean):void");
    }

    public static /* synthetic */ void $r8$lambda$cobVIMrPPs144kr8ufQpwCFhuyg(MediaSendPrepareWorker mediaSendPrepareWorker, AccountInstance accountInstance, SendingMediaInfo sendingMediaInfo, boolean z) {
        mediaSendPrepareWorker.photo = accountInstance.getSendMessagesHelper().generatePhotoSizes(null, sendingMediaInfo.path, sendingMediaInfo.uri, sendingMediaInfo.highQuality);
        if (z && sendingMediaInfo.canDeleteAfter) {
            new File(sendingMediaInfo.path).delete();
        }
        mediaSendPrepareWorker.sync.countDown();
    }

    public static /* synthetic */ void $r8$lambda$RRCXo63skWkERD9dt6bVsOnZKMs(MessageObject messageObject, TLRPC.TL_inputPollAnswer tL_inputPollAnswer, AccountInstance accountInstance, TLRPC.TL_document tL_document, String str, HashMap map, SendingMediaInfo sendingMediaInfo, String str2, long j, MessageObject messageObject2, MessageObject messageObject3, boolean z, int i, int i2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, String str3, int i3, boolean z2, long j2, boolean z3, long j3, long j4, MessageSuggestionParams messageSuggestionParams, PollSendParams pollSendParams) {
        if (messageObject != null || tL_inputPollAnswer != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, tL_inputPollAnswer, null, null, tL_document, str, null, map, false, sendingMediaInfo.hasMediaSpoilers, str2);
            return;
        }
        SendMessageParams sendMessageParamsM1081of = SendMessageParams.m1081of(tL_document, null, str, j, messageObject2, messageObject3, sendingMediaInfo.caption, sendingMediaInfo.entities, null, map, z, i, i2, 0, str2, null, false, sendingMediaInfo.hasMediaSpoilers);
        sendMessageParamsM1081of.replyToStoryItem = storyItem;
        sendMessageParamsM1081of.replyQuote = replyQuote;
        sendMessageParamsM1081of.quick_reply_shortcut = str3;
        sendMessageParamsM1081of.quick_reply_shortcut_id = i3;
        if (z2) {
            sendMessageParamsM1081of.effect_id = j2;
        }
        sendMessageParamsM1081of.invert_media = z3;
        sendMessageParamsM1081of.payStars = j3;
        sendMessageParamsM1081of.monoForumPeer = j4;
        sendMessageParamsM1081of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1081of.pollIndex = sendingMediaInfo.pollIndex;
        sendMessageParamsM1081of.pollSendParams = pollSendParams;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1081of);
    }

    public static /* synthetic */ void $r8$lambda$dbz1ZYMO4zwm4m7B6LqtrBXSqBs(MessageObject messageObject, TLRPC.TL_inputPollAnswer tL_inputPollAnswer, AccountInstance accountInstance, TLRPC.TL_photo tL_photo, boolean z, SendingMediaInfo sendingMediaInfo, HashMap map, String str, long j, MessageObject messageObject2, MessageObject messageObject3, boolean z2, int i, int i2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, int i3, String str2, long j2, boolean z3, long j3, long j4, MessageSuggestionParams messageSuggestionParams, PollSendParams pollSendParams) {
        if (messageObject != null || tL_inputPollAnswer != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, tL_inputPollAnswer, tL_photo, null, null, z ? sendingMediaInfo.searchImage.imageUrl : null, null, map, false, sendingMediaInfo.hasMediaSpoilers, str);
            return;
        }
        SendMessageParams sendMessageParamsM1086of = SendMessageParams.m1086of(tL_photo, z ? sendingMediaInfo.searchImage.imageUrl : null, j, messageObject2, messageObject3, sendingMediaInfo.caption, sendingMediaInfo.entities, null, map, z2, i, i2, sendingMediaInfo.ttl, str, false, sendingMediaInfo.hasMediaSpoilers);
        sendMessageParamsM1086of.replyToStoryItem = storyItem;
        sendMessageParamsM1086of.replyQuote = replyQuote;
        sendMessageParamsM1086of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1086of.quick_reply_shortcut = str2;
        sendMessageParamsM1086of.effect_id = j2;
        sendMessageParamsM1086of.invert_media = z3;
        sendMessageParamsM1086of.payStars = j3;
        sendMessageParamsM1086of.monoForumPeer = j4;
        sendMessageParamsM1086of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1086of.pollIndex = sendingMediaInfo.pollIndex;
        sendMessageParamsM1086of.pollSendParams = pollSendParams;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1086of);
    }

    public static /* synthetic */ void $r8$lambda$ydiLkVHhCPu1LauC7wizAwNEsjc(Bitmap bitmap, String str, MessageObject messageObject, TLRPC.TL_inputPollAnswer tL_inputPollAnswer, AccountInstance accountInstance, VideoEditedInfo videoEditedInfo, TLRPC.TL_document tL_document, String str2, HashMap map, SendingMediaInfo sendingMediaInfo, String str3, long j, MessageObject messageObject2, MessageObject messageObject3, boolean z, int i, int i2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, String str4, int i3, long j2, boolean z2, TLRPC.PhotoSize photoSize, long j3, long j4, MessageSuggestionParams messageSuggestionParams, PollSendParams pollSendParams) {
        if (bitmap != null && str != null) {
            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmap), str, false);
        }
        if (messageObject != null || tL_inputPollAnswer != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, tL_inputPollAnswer, null, videoEditedInfo, tL_document, str2, null, map, false, sendingMediaInfo.hasMediaSpoilers, str3);
            return;
        }
        SendMessageParams sendMessageParamsM1081of = SendMessageParams.m1081of(tL_document, videoEditedInfo, str2, j, messageObject2, messageObject3, sendingMediaInfo.caption, sendingMediaInfo.entities, null, map, z, i, i2, sendingMediaInfo.ttl, str3, null, false, sendingMediaInfo.hasMediaSpoilers);
        sendMessageParamsM1081of.replyToStoryItem = storyItem;
        sendMessageParamsM1081of.replyQuote = replyQuote;
        sendMessageParamsM1081of.quick_reply_shortcut = str4;
        sendMessageParamsM1081of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1081of.effect_id = j2;
        sendMessageParamsM1081of.invert_media = z2;
        sendMessageParamsM1081of.stars = sendingMediaInfo.stars;
        sendMessageParamsM1081of.pollIndex = sendingMediaInfo.pollIndex;
        sendMessageParamsM1081of.cover = photoSize;
        sendMessageParamsM1081of.payStars = j3;
        sendMessageParamsM1081of.monoForumPeer = j4;
        sendMessageParamsM1081of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1081of.isLivePhoto = sendingMediaInfo.isLivePhoto;
        sendMessageParamsM1081of.livePhotoTimestamp = sendingMediaInfo.livePhotoTimestampUs / 1000;
        sendMessageParamsM1081of.pollSendParams = pollSendParams;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1081of);
    }

    /* JADX INFO: renamed from: $r8$lambda$iSSEl5l-8noLgk0vtt5GwziS-iI */
    public static /* synthetic */ void m6247$r8$lambda$iSSEl5l8noLgk0vtt5GwziSiI(Bitmap[] bitmapArr, String[] strArr, MessageObject messageObject, TLRPC.TL_inputPollAnswer tL_inputPollAnswer, AccountInstance accountInstance, TLRPC.TL_photo tL_photo, HashMap map, SendingMediaInfo sendingMediaInfo, String str, long j, MessageObject messageObject2, MessageObject messageObject3, boolean z, int i, int i2, boolean z2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, String str2, int i3, long j2, boolean z3, long j3, long j4, MessageSuggestionParams messageSuggestionParams, boolean z4, PollSendParams pollSendParams) {
        if (bitmapArr[0] != null && strArr[0] != null) {
            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmapArr[0]), strArr[0], false);
        }
        if (messageObject != null || tL_inputPollAnswer != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, tL_inputPollAnswer, tL_photo, null, null, null, null, map, false, sendingMediaInfo.hasMediaSpoilers, str);
            return;
        }
        SendMessageParams sendMessageParamsM1086of = SendMessageParams.m1086of(tL_photo, null, j, messageObject2, messageObject3, sendingMediaInfo.caption, sendingMediaInfo.entities, null, map, z, i, i2, sendingMediaInfo.ttl, str, z2, sendingMediaInfo.hasMediaSpoilers);
        sendMessageParamsM1086of.replyToStoryItem = storyItem;
        sendMessageParamsM1086of.replyQuote = replyQuote;
        sendMessageParamsM1086of.quick_reply_shortcut = str2;
        sendMessageParamsM1086of.quick_reply_shortcut_id = i3;
        sendMessageParamsM1086of.effect_id = j2;
        sendMessageParamsM1086of.invert_media = z3;
        sendMessageParamsM1086of.stars = sendingMediaInfo.stars;
        sendMessageParamsM1086of.pollIndex = sendingMediaInfo.pollIndex;
        sendMessageParamsM1086of.payStars = j3;
        sendMessageParamsM1086of.monoForumPeer = j4;
        sendMessageParamsM1086of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1086of.sendingHighQuality = z4;
        sendMessageParamsM1086of.pollSendParams = pollSendParams;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1086of);
    }

    public static void prepareSendingPoll(final AccountInstance accountInstance, final PollSendParams pollSendParams, final long j, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final boolean z, final int i, final String str, final int i2, final long j2, final long j3, final MessageSuggestionParams messageSuggestionParams) {
        PollSendParams pollSendParams2;
        boolean z2;
        boolean z3;
        if (pollSendParams.mediaPack != null) {
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            final ArrayList arrayList3 = new ArrayList();
            final ArrayList arrayList4 = new ArrayList();
            final ArrayList arrayList5 = new ArrayList();
            final ArrayList arrayList6 = new ArrayList();
            ArrayList arrayList7 = new ArrayList();
            ArrayList arrayList8 = new ArrayList();
            final int[] iArr = new int[1];
            int size = pollSendParams.mediaPack.medias.size();
            for (int i3 = 0; i3 < size; i3++) {
                PollAttachedMedia pollAttachedMediaValueAt = pollSendParams.mediaPack.medias.valueAt(i3);
                int iKeyAt = pollSendParams.mediaPack.medias.keyAt(i3);
                if (pollAttachedMediaValueAt instanceof PollAttachedMediaMusic) {
                    arrayList7.add(((PollAttachedMediaMusic) pollAttachedMediaValueAt).messageObject);
                    arrayList8.add(Integer.valueOf(iKeyAt));
                } else if (pollAttachedMediaValueAt instanceof PollAttachedMediaGallery) {
                    SendingMediaInfo sendingMediaInfo = ((PollAttachedMediaGallery) pollAttachedMediaValueAt).sendingMediaInfo;
                    sendingMediaInfo.pollIndex = iKeyAt;
                    arrayList.add(sendingMediaInfo);
                } else if (pollAttachedMediaValueAt instanceof PollAttachedMediaFile) {
                    PollAttachedMediaFile pollAttachedMediaFile = (PollAttachedMediaFile) pollAttachedMediaValueAt;
                    if (!TextUtils.isEmpty(pollAttachedMediaFile.path)) {
                        arrayList2.add(pollAttachedMediaFile.path);
                        arrayList3.add(pollAttachedMediaFile.path);
                        arrayList4.add(Integer.valueOf(iKeyAt));
                    } else {
                        Uri uri = pollAttachedMediaFile.uri;
                        if (uri != null) {
                            arrayList5.add(uri);
                            arrayList6.add(Integer.valueOf(iKeyAt));
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                z2 = false;
            } else {
                iArr[0] = iArr[0] + 1;
                z2 = true;
            }
            if (!arrayList2.isEmpty() || !arrayList5.isEmpty()) {
                iArr[0] = iArr[0] + 1;
                z2 = true;
            }
            if (arrayList7.isEmpty()) {
                z3 = z2;
            } else {
                iArr[0] = iArr[0] + 1;
                z3 = true;
            }
            final Runnable runnable = new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    SendMessagesHelper.$r8$lambda$lcxgljTrBYxiEXE3K2ZDWTJnJ50(arrayList2, arrayList5, accountInstance, arrayList3, j, messageObject, messageObject2, storyItem, replyQuote, z, i, str, i2, j2, j3, messageSuggestionParams, pollSendParams, arrayList4, arrayList6);
                }
            };
            Runnable runnable2 = new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    SendMessagesHelper.m6243$r8$lambda$cQtqZ_gNXKAX88zUDFTt3lgleY(arrayList, iArr, accountInstance, j, messageObject, messageObject2, storyItem, replyQuote, z, i, str, i2, j2, j3, messageSuggestionParams, pollSendParams, runnable);
                }
            };
            if (!arrayList7.isEmpty()) {
                int i4 = iArr[0] - 1;
                iArr[0] = i4;
                prepareSendingAudioDocuments(accountInstance, arrayList7, _UrlKt.FRAGMENT_ENCODE_SET, j, messageObject, messageObject2, storyItem, z, i, 0, null, str, i2, 0L, false, j2, pollSendParams, arrayList8, i4 > 0, runnable2);
                pollSendParams2 = pollSendParams;
            } else {
                pollSendParams2 = pollSendParams;
                runnable2.run();
            }
            if (z3) {
                return;
            }
        } else {
            pollSendParams2 = pollSendParams;
        }
        SendMessageParams sendMessageParamsM1084of = SendMessageParams.m1084of(pollSendParams2.poll, j, messageObject, messageObject2, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, z, i, 0);
        sendMessageParamsM1084of.caption = pollSendParams2.caption;
        sendMessageParamsM1084of.invert_media = true;
        sendMessageParamsM1084of.entities = pollSendParams2.entities;
        sendMessageParamsM1084of.quick_reply_shortcut = str;
        sendMessageParamsM1084of.quick_reply_shortcut_id = i2;
        sendMessageParamsM1084of.payStars = j2;
        sendMessageParamsM1084of.monoForumPeer = j3;
        sendMessageParamsM1084of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1084of.pollSendParams = pollSendParams2;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1084of);
    }

    public static /* synthetic */ void $r8$lambda$lcxgljTrBYxiEXE3K2ZDWTJnJ50(ArrayList arrayList, ArrayList arrayList2, AccountInstance accountInstance, ArrayList arrayList3, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, boolean z, int i, String str, int i2, long j2, long j3, MessageSuggestionParams messageSuggestionParams, PollSendParams pollSendParams, ArrayList arrayList4, ArrayList arrayList5) {
        if (arrayList.isEmpty() && arrayList2.isEmpty()) {
            return;
        }
        prepareSendingDocuments(accountInstance, arrayList, arrayList3, arrayList2, null, null, null, j, messageObject, messageObject2, storyItem, replyQuote, null, z, i, 0, null, str, i2, 0L, false, j2, j3, messageSuggestionParams, pollSendParams, arrayList4, arrayList5, false);
    }

    /* JADX INFO: renamed from: $r8$lambda$cQtqZ_gNXKAX88zUDF-Tt3lgleY */
    public static /* synthetic */ void m6243$r8$lambda$cQtqZ_gNXKAX88zUDFTt3lgleY(ArrayList arrayList, int[] iArr, AccountInstance accountInstance, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, boolean z, int i, String str, int i2, long j2, long j3, MessageSuggestionParams messageSuggestionParams, PollSendParams pollSendParams, final Runnable runnable) {
        if (!arrayList.isEmpty()) {
            int i3 = iArr[0] - 1;
            iArr[0] = i3;
            prepareSendingMedia(accountInstance, arrayList, j, messageObject, messageObject2, storyItem, replyQuote, false, true, null, null, z, i, 0, 0, false, null, str, i2, 0L, false, j2, j3, messageSuggestionParams, pollSendParams, i3 > 0);
            mediaSendQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda89
                @Override // java.lang.Runnable
                public final void run() {
                    AndroidUtilities.runOnUIThread(runnable);
                }
            });
            return;
        }
        runnable.run();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:120|69|131|70|(1:72)|77|(1:79)|80|(1:82)|83|(2:85|(1:87)(4:88|(2:90|(1:92))(0)|119|112))|124|93|119|112) */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x006b, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x006c, code lost:
    
        org.telegram.messenger.FileLog.m1048e(r6);
        r2 = r2;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:92:0x005f A[Catch: all -> 0x001d, Exception -> 0x0021, TRY_LEAVE, TryCatch #8 {Exception -> 0x0021, all -> 0x001d, blocks: (B:70:0x000b, B:72:0x0016, B:77:0x0024, B:79:0x002c, B:80:0x0032, B:82:0x003a, B:83:0x0042, B:85:0x004a, B:87:0x0054, B:92:0x005f), top: B:131:0x000b }] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.media.MediaMetadataRetriever] */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.media.MediaMetadataRetriever] */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v31 */
    /* JADX WARN: Type inference failed for: r2v32 */
    /* JADX WARN: Type inference failed for: r2v33 */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v7, types: [double] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void fillVideoAttribute(java.lang.String r6, org.telegram.tgnet.TLRPC.TL_documentAttributeVideo r7, org.telegram.messenger.VideoEditedInfo r8) {
        /*
            r0 = 4652007308841189376(0x408f400000000000, double:1000.0)
            r2 = 0
            android.media.MediaMetadataRetriever r3 = new android.media.MediaMetadataRetriever     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L72
            r3.<init>()     // Catch: java.lang.Throwable -> L70 java.lang.Exception -> L72
            r3.setDataSource(r6)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            r2 = 18
            java.lang.String r2 = r3.extractMetadata(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            if (r2 == 0) goto L24
            int r2 = java.lang.Integer.parseInt(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            r7.f1256w = r2     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            goto L24
        L1d:
            r6 = move-exception
            r2 = r3
            goto Lae
        L21:
            r8 = move-exception
            r2 = r3
            goto L73
        L24:
            r2 = 19
            java.lang.String r2 = r3.extractMetadata(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            if (r2 == 0) goto L32
            int r2 = java.lang.Integer.parseInt(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            r7.f1255h = r2     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
        L32:
            r2 = 9
            java.lang.String r2 = r3.extractMetadata(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            if (r2 == 0) goto L42
            long r4 = java.lang.Long.parseLong(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            double r4 = (double) r4     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            double r4 = r4 / r0
            r7.duration = r4     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
        L42:
            r2 = 24
            java.lang.String r2 = r3.extractMetadata(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            if (r2 == 0) goto L67
            java.lang.Integer r2 = org.telegram.messenger.Utilities.parseInt(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            if (r8 == 0) goto L57
            r8.rotationValue = r2     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            goto L67
        L57:
            r8 = 90
            if (r2 == r8) goto L5f
            r8 = 270(0x10e, float:3.78E-43)
            if (r2 != r8) goto L67
        L5f:
            int r8 = r7.f1256w     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            int r2 = r7.f1255h     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            r7.f1256w = r2     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
            r7.f1255h = r8     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L21
        L67:
            r3.release()     // Catch: java.lang.Exception -> L6b
            goto Lad
        L6b:
            r6 = move-exception
            org.telegram.messenger.FileLog.m1048e(r6)
            goto Lad
        L70:
            r6 = move-exception
            goto Lae
        L72:
            r8 = move-exception
        L73:
            org.telegram.messenger.FileLog.m1048e(r8)     // Catch: java.lang.Throwable -> L70
            if (r2 == 0) goto L80
            r2.release()     // Catch: java.lang.Exception -> L7c
            goto L80
        L7c:
            r8 = move-exception
            org.telegram.messenger.FileLog.m1048e(r8)
        L80:
            android.content.Context r8 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Exception -> La9
            java.io.File r2 = new java.io.File     // Catch: java.lang.Exception -> La9
            r2.<init>(r6)     // Catch: java.lang.Exception -> La9
            android.net.Uri r6 = android.net.Uri.fromFile(r2)     // Catch: java.lang.Exception -> La9
            android.media.MediaPlayer r6 = android.media.MediaPlayer.create(r8, r6)     // Catch: java.lang.Exception -> La9
            if (r6 == 0) goto Lad
            int r8 = r6.getDuration()     // Catch: java.lang.Exception -> La9
            double r2 = (double) r8     // Catch: java.lang.Exception -> La9
            double r2 = r2 / r0
            r7.duration = r2     // Catch: java.lang.Exception -> La9
            int r8 = r6.getVideoWidth()     // Catch: java.lang.Exception -> La9
            r7.f1256w = r8     // Catch: java.lang.Exception -> La9
            int r8 = r6.getVideoHeight()     // Catch: java.lang.Exception -> La9
            r7.f1255h = r8     // Catch: java.lang.Exception -> La9
            r6.release()     // Catch: java.lang.Exception -> La9
            goto Lad
        La9:
            r6 = move-exception
            org.telegram.messenger.FileLog.m1048e(r6)
        Lad:
            return
        Lae:
            if (r2 == 0) goto Lb8
            r2.release()     // Catch: java.lang.Exception -> Lb4
            goto Lb8
        Lb4:
            r7 = move-exception
            org.telegram.messenger.FileLog.m1048e(r7)
        Lb8:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.fillVideoAttribute(java.lang.String, org.telegram.tgnet.TLRPC$TL_documentAttributeVideo, org.telegram.messenger.VideoEditedInfo):void");
    }

    public static Bitmap createVideoThumbnail(String str, int i) {
        float f = i == 2 ? 1920.0f : i == 3 ? 96.0f : 512.0f;
        Bitmap bitmapCreateVideoThumbnailAtTime = createVideoThumbnailAtTime(str, 0L);
        if (bitmapCreateVideoThumbnailAtTime == null) {
            return bitmapCreateVideoThumbnailAtTime;
        }
        int width = bitmapCreateVideoThumbnailAtTime.getWidth();
        int height = bitmapCreateVideoThumbnailAtTime.getHeight();
        float f2 = width;
        if (f2 <= f && height <= f) {
            return bitmapCreateVideoThumbnailAtTime;
        }
        float fMax = Math.max(width, height) / f;
        return Bitmap.createScaledBitmap(bitmapCreateVideoThumbnailAtTime, (int) (f2 / fMax), (int) (height / fMax), true);
    }

    public static Bitmap createVideoThumbnailAtTime(String str, long j) {
        return createVideoThumbnailAtTime(str, j, null, false);
    }

    public static Bitmap createVideoThumbnailAtTime(String str, long j, int[] iArr, boolean z) {
        if (z) {
            AnimatedFileDrawable animatedFileDrawable = new AnimatedFileDrawable(new File(str), true, 0L, 0, null, null, null, 0L, 0, true, null);
            Bitmap frameAtTime = animatedFileDrawable.getFrameAtTime(j, z);
            if (iArr != null) {
                iArr[0] = animatedFileDrawable.getOrientation();
            }
            animatedFileDrawable.recycle();
            return frameAtTime == null ? createVideoThumbnailAtTime(str, j, iArr, false) : frameAtTime;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        Bitmap frameAtTime2 = null;
        try {
            mediaMetadataRetriever.setDataSource(str);
            frameAtTime2 = mediaMetadataRetriever.getFrameAtTime(j, 1);
            if (frameAtTime2 == null) {
                frameAtTime2 = mediaMetadataRetriever.getFrameAtTime(j, 3);
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (Throwable unused2) {
            }
            throw th;
        }
        try {
            mediaMetadataRetriever.release();
        } catch (Throwable unused3) {
        }
        return frameAtTime2;
    }

    private static VideoEditedInfo createCompressionSettings(String str, long j) {
        int[] iArr = new int[11];
        AnimatedFileDrawable.getVideoInfo(str, iArr, j);
        if (iArr[0] == 0) {
            if (!BuildVars.LOGS_ENABLED) {
                return null;
            }
            FileLog.m1045d("video hasn't avc1 atom");
            return null;
        }
        long length = new File(str).length();
        int videoBitrate = MediaController.getVideoBitrate(str);
        if (videoBitrate == -1) {
            videoBitrate = iArr[3];
        }
        float f = iArr[4];
        long j2 = iArr[5];
        int i = iArr[7];
        VideoEditedInfo videoEditedInfo = new VideoEditedInfo();
        videoEditedInfo.startTime = -1L;
        videoEditedInfo.endTime = -1L;
        videoEditedInfo.bitrate = videoBitrate;
        videoEditedInfo.originalPath = str;
        videoEditedInfo.videoOffset = j;
        videoEditedInfo.framerate = i;
        videoEditedInfo.estimatedDuration = (long) Math.ceil(f);
        boolean z = true;
        int i2 = iArr[1];
        videoEditedInfo.originalWidth = i2;
        videoEditedInfo.resultWidth = i2;
        int i3 = iArr[2];
        videoEditedInfo.originalHeight = i3;
        videoEditedInfo.resultHeight = i3;
        videoEditedInfo.rotationValue = iArr[8];
        videoEditedInfo.originalDuration = (long) (f * 1000.0f);
        float fMax = Math.max(i2, i3);
        int i4 = fMax > 3840.0f ? 7 : fMax > 2560.0f ? 6 : fMax > 1920.0f ? 5 : fMax > 1280.0f ? 4 : fMax > 854.0f ? 3 : fMax > 640.0f ? 2 : 1;
        int iRound = Math.round(DownloadController.getInstance(UserConfig.selectedAccount).getMaxVideoBitrate() / (100.0f / i4));
        if (iRound > i4) {
            iRound = i4;
        }
        if (new File(str).length() < 1048576000) {
            if (iRound != i4 || Math.max(videoEditedInfo.originalWidth, videoEditedInfo.originalHeight) > 1280) {
                float f2 = iRound != 1 ? iRound != 2 ? iRound != 3 ? iRound != 5 ? iRound != 6 ? 1920.0f : 3840.0f : 2560.0f : 1280.0f : 854.0f : 480.0f;
                int i5 = videoEditedInfo.originalWidth;
                int i6 = videoEditedInfo.originalHeight;
                float f3 = f2 / (i5 > i6 ? i5 : i6);
                videoEditedInfo.resultWidth = Math.round((i5 * f3) / 2.0f) * 2;
                videoEditedInfo.resultHeight = Math.round((videoEditedInfo.originalHeight * f3) / 2.0f) * 2;
            } else {
                z = false;
            }
            videoBitrate = MediaController.makeVideoBitrate(videoEditedInfo.originalHeight, videoEditedInfo.originalWidth, videoBitrate, videoEditedInfo.resultHeight, videoEditedInfo.resultWidth);
        } else {
            z = false;
        }
        if (!z) {
            videoEditedInfo.resultWidth = videoEditedInfo.originalWidth;
            videoEditedInfo.resultHeight = videoEditedInfo.originalHeight;
            videoEditedInfo.bitrate = videoBitrate;
            videoEditedInfo.estimatedSize = length;
        } else {
            videoEditedInfo.bitrate = videoBitrate;
            videoEditedInfo.estimatedSize = (long) (j2 + (((f / 1000.0f) * MediaController.extractRealEncoderBitrate(videoEditedInfo.resultWidth, videoEditedInfo.resultHeight, videoBitrate, false)) / 8.0f));
        }
        if (videoEditedInfo.estimatedSize == 0) {
            videoEditedInfo.estimatedSize = 1L;
        }
        return videoEditedInfo;
    }

    public static void prepareSendingVideo(AccountInstance accountInstance, String str, VideoEditedInfo videoEditedInfo, String str2, TLRPC.Photo photo, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, ArrayList<TLRPC.MessageEntity> arrayList, int i, MessageObject messageObject3, boolean z, int i2, int i3, boolean z2, boolean z3, CharSequence charSequence, String str3, int i4, long j2, long j3) {
        prepareSendingVideo(accountInstance, str, videoEditedInfo, str2, photo, j, messageObject, messageObject2, storyItem, replyQuote, arrayList, i, messageObject3, z, i2, i3, z2, z3, charSequence, str3, i4, j2, j3, 0L, null);
    }

    public static void prepareSendingVideo(AccountInstance accountInstance, String str, VideoEditedInfo videoEditedInfo, String str2, TLRPC.Photo photo, long j, MessageObject messageObject, MessageObject messageObject2, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, ArrayList<TLRPC.MessageEntity> arrayList, int i, MessageObject messageObject3, boolean z, int i2, int i3, boolean z2, boolean z3, CharSequence charSequence, String str3, int i4, long j2, long j3, long j4, MessageSuggestionParams messageSuggestionParams) {
        prepareSendingVideo(accountInstance, str, videoEditedInfo, str2, photo, j, messageObject, messageObject2, storyItem, replyQuote, arrayList, i, messageObject3, z, i2, i3, z2, z3, charSequence, str3, i4, j2, j3, j4, messageSuggestionParams, false);
    }

    public static void prepareSendingVideo(final AccountInstance accountInstance, final String str, final VideoEditedInfo videoEditedInfo, final String str2, final TLRPC.Photo photo, final long j, final MessageObject messageObject, final MessageObject messageObject2, final TL_stories.StoryItem storyItem, final ChatActivity.ReplyQuote replyQuote, final ArrayList<TLRPC.MessageEntity> arrayList, final int i, final MessageObject messageObject3, final boolean z, final int i2, final int i3, final boolean z2, final boolean z3, final CharSequence charSequence, final String str3, final int i4, final long j2, final long j3, final long j4, final MessageSuggestionParams messageSuggestionParams, final boolean z4) {
        if (str == null || str.length() == 0) {
            return;
        }
        new Thread(new Runnable() { // from class: org.telegram.messenger.SendMessagesHelper$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                SendMessagesHelper.m6246$r8$lambda$g1fYmJ_zg3oJ4DyElwbdWtKCik(videoEditedInfo, str, j, i, accountInstance, str2, photo, charSequence, messageObject3, z3, messageObject, messageObject2, arrayList, z, i2, i3, storyItem, replyQuote, i4, str3, j2, j3, j4, messageSuggestionParams, z4, z2);
            }
        }).start();
    }

    /* JADX WARN: Removed duplicated region for block: B:243:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0430  */
    /* JADX INFO: renamed from: $r8$lambda$g1fYmJ_zg3oJ4DyElwbdWtKCi-k */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void m6246$r8$lambda$g1fYmJ_zg3oJ4DyElwbdWtKCik(org.telegram.messenger.VideoEditedInfo r38, java.lang.String r39, final long r40, final int r42, final org.telegram.messenger.AccountInstance r43, java.lang.String r44, org.telegram.tgnet.TLRPC.Photo r45, java.lang.CharSequence r46, final org.telegram.messenger.MessageObject r47, final boolean r48, final org.telegram.messenger.MessageObject r49, final org.telegram.messenger.MessageObject r50, final java.util.ArrayList r51, final boolean r52, final int r53, final int r54, final org.telegram.tgnet.tl.TL_stories.StoryItem r55, final org.telegram.ui.ChatActivity.ReplyQuote r56, final int r57, final java.lang.String r58, final long r59, final long r61, final long r63, final org.telegram.messenger.MessageSuggestionParams r65, final boolean r66, boolean r67) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SendMessagesHelper.m6246$r8$lambda$g1fYmJ_zg3oJ4DyElwbdWtKCik(org.telegram.messenger.VideoEditedInfo, java.lang.String, long, int, org.telegram.messenger.AccountInstance, java.lang.String, org.telegram.tgnet.TLRPC$Photo, java.lang.CharSequence, org.telegram.messenger.MessageObject, boolean, org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject, java.util.ArrayList, boolean, int, int, org.telegram.tgnet.tl.TL_stories$StoryItem, org.telegram.ui.ChatActivity$ReplyQuote, int, java.lang.String, long, long, long, org.telegram.messenger.MessageSuggestionParams, boolean, boolean):void");
    }

    /* JADX INFO: renamed from: $r8$lambda$wjJV0835j1PhKTzqyXFR7UvY-ck */
    public static /* synthetic */ void m6260$r8$lambda$wjJV0835j1PhKTzqyXFR7UvYck(Bitmap bitmap, String str, MessageObject messageObject, AccountInstance accountInstance, VideoEditedInfo videoEditedInfo, TLRPC.TL_document tL_document, String str2, TLRPC.PhotoSize photoSize, HashMap map, boolean z, String str3, long j, MessageObject messageObject2, MessageObject messageObject3, String str4, ArrayList arrayList, boolean z2, int i, int i2, int i3, TL_stories.StoryItem storyItem, ChatActivity.ReplyQuote replyQuote, int i4, String str5, long j2, long j3, long j4, MessageSuggestionParams messageSuggestionParams, boolean z3) {
        if (bitmap != null && str != null) {
            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmap), str, false);
        }
        if (messageObject != null) {
            accountInstance.getSendMessagesHelper().editMessage(messageObject, null, videoEditedInfo, tL_document, str2, photoSize, map, false, z, str3);
            return;
        }
        SendMessageParams sendMessageParamsM1081of = SendMessageParams.m1081of(tL_document, videoEditedInfo, str2, j, messageObject2, messageObject3, str4, arrayList, null, map, z2, i, i2, i3, str3, null, false, z);
        sendMessageParamsM1081of.replyToStoryItem = storyItem;
        sendMessageParamsM1081of.replyQuote = replyQuote;
        sendMessageParamsM1081of.quick_reply_shortcut_id = i4;
        sendMessageParamsM1081of.quick_reply_shortcut = str5;
        sendMessageParamsM1081of.effect_id = j2;
        sendMessageParamsM1081of.cover = photoSize;
        sendMessageParamsM1081of.payStars = j3;
        sendMessageParamsM1081of.monoForumPeer = j4;
        sendMessageParamsM1081of.suggestionParams = messageSuggestionParams;
        sendMessageParamsM1081of.invert_media = z3;
        accountInstance.getSendMessagesHelper().sendMessage(sendMessageParamsM1081of);
    }

    public static class SendMessageParams {
        public String caption;
        public TLRPC.PhotoSize cover;
        public long dice_stake;
        public TLRPC.TL_document document;
        public long effect_id;
        public ArrayList<TLRPC.MessageEntity> entities;
        public TLRPC.TL_game game;
        public boolean hasMediaSpoilers;
        public TL_iv.TL_inputRichMessage inputRichMessage;
        public boolean invert_media;
        public TLRPC.TL_messageMediaInvoice invoice;
        public boolean isLivePhoto;
        public long livePhotoTimestamp;
        public TLRPC.MessageMedia location;
        public TLRPC.TL_messageMediaWebPage mediaWebPage;
        public String message;
        public long monoForumPeer;
        public boolean notify;
        public HashMap<String, String> params;
        public Object parentObject;
        public String path;
        public long payStars;
        public long peer;
        public TLRPC.TL_photo photo;
        public TLRPC.TL_messageMediaPoll poll;
        public int pollIndex;
        public PollSendParams pollSendParams;
        public String quick_reply_shortcut;
        public int quick_reply_shortcut_id;
        public TLRPC.ReplyMarkup replyMarkup;
        public ChatActivity.ReplyQuote replyQuote;
        public MessageObject replyToMsg;
        public TL_stories.StoryItem replyToStoryItem;
        public MessageObject replyToTopMsg;
        public MessageObject retryMessageObject;
        public int scheduleDate;
        public int scheduleRepeatPeriod;
        public boolean searchLinks = true;
        public MessageObject.SendAnimationData sendAnimationData;
        public boolean sendingHighQuality;
        public TL_stories.StoryItem sendingStory;
        public long stars;
        public MessageSuggestionParams suggestionParams;
        public TLRPC.TL_messageMediaToDo todo;
        public int ttl;
        public boolean updateStickersOrder;
        public TLRPC.User user;
        public VideoEditedInfo videoEditedInfo;
        public TLRPC.WebPage webPage;

        public static SendMessageParams ofRichMessage(TL_iv.TL_inputRichMessage tL_inputRichMessage, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2) {
            SendMessageParams sendMessageParamsM1076of = m1076of(null, null, null, null, null, null, null, null, null, null, j, null, messageObject, messageObject2, null, true, null, null, replyMarkup, map, z, i, i2, 0, null, null, false);
            sendMessageParamsM1076of.inputRichMessage = tL_inputRichMessage;
            return sendMessageParamsM1076of;
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1074of(String str, long j) {
            return m1076of(str, null, null, null, null, null, null, null, null, null, j, null, null, null, null, true, null, null, null, null, false, 0, 0, 0, null, null, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1078of(MessageObject messageObject) {
            long dialogId = messageObject.getDialogId();
            TLRPC.Message message = messageObject.messageOwner;
            SendMessageParams sendMessageParamsM1076of = m1076of(null, null, null, null, null, null, null, null, null, null, dialogId, message.attachPath, null, null, null, true, messageObject, null, message.reply_markup, message.params, !message.silent, messageObject.scheduled ? message.date : 0, 0, 0, null, null, false);
            TLRPC.Message message2 = messageObject.messageOwner;
            if (message2 != null) {
                TLRPC.InputQuickReplyShortcut inputQuickReplyShortcut = message2.quick_reply_shortcut;
                if (inputQuickReplyShortcut instanceof TLRPC.TL_inputQuickReplyShortcut) {
                    sendMessageParamsM1076of.quick_reply_shortcut = ((TLRPC.TL_inputQuickReplyShortcut) inputQuickReplyShortcut).shortcut;
                }
                sendMessageParamsM1076of.quick_reply_shortcut_id = messageObject.getQuickReplyId();
                sendMessageParamsM1076of.payStars = messageObject.messageOwner.paid_message_stars;
            }
            return sendMessageParamsM1076of;
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1087of(TLRPC.User user, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2) {
            return m1076of(null, null, null, null, null, user, null, null, null, null, j, null, messageObject, messageObject2, null, true, null, null, replyMarkup, map, z, i, i2, 0, null, null, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1083of(TLRPC.TL_messageMediaInvoice tL_messageMediaInvoice, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2) {
            return m1076of(null, null, null, null, null, null, null, null, null, tL_messageMediaInvoice, j, null, messageObject, messageObject2, null, true, null, null, replyMarkup, map, z, i, i2, 0, null, null, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1080of(TLRPC.TL_document tL_document, VideoEditedInfo videoEditedInfo, String str, long j, MessageObject messageObject, MessageObject messageObject2, String str2, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2, int i3, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z2) {
            return m1076of(null, str2, null, null, videoEditedInfo, null, tL_document, null, null, null, j, str, messageObject, messageObject2, null, true, null, arrayList, replyMarkup, map, z, i, i2, i3, obj, sendAnimationData, z2);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1081of(TLRPC.TL_document tL_document, VideoEditedInfo videoEditedInfo, String str, long j, MessageObject messageObject, MessageObject messageObject2, String str2, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2, int i3, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z2, boolean z3) {
            return m1077of(null, str2, null, null, videoEditedInfo, null, tL_document, null, null, null, j, str, messageObject, messageObject2, null, true, null, arrayList, replyMarkup, map, z, i, i2, i3, obj, sendAnimationData, z2, z3);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1075of(String str, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.WebPage webPage, boolean z, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z2, int i, int i2, MessageObject.SendAnimationData sendAnimationData, boolean z3) {
            return m1076of(str, null, null, null, null, null, null, null, null, null, j, null, messageObject, messageObject2, webPage, z, null, arrayList, replyMarkup, map, z2, i, i2, 0, null, sendAnimationData, z3);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1079of(TLRPC.MessageMedia messageMedia, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2) {
            return m1076of(null, null, messageMedia, null, null, null, null, null, null, null, j, null, messageObject, messageObject2, null, true, null, null, replyMarkup, map, z, i, i2, 0, null, null, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1084of(TLRPC.TL_messageMediaPoll tL_messageMediaPoll, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2) {
            return m1076of(null, null, null, null, null, null, null, null, tL_messageMediaPoll, null, j, null, messageObject, messageObject2, null, true, null, null, replyMarkup, map, z, i, i2, 0, null, null, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1082of(TLRPC.TL_game tL_game, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2) {
            return m1076of(null, null, null, null, null, null, null, tL_game, null, null, j, null, messageObject, messageObject2, null, true, null, null, replyMarkup, map, z, i, i2, 0, null, null, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1086of(TLRPC.TL_photo tL_photo, String str, long j, MessageObject messageObject, MessageObject messageObject2, String str2, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2, int i3, Object obj, boolean z2, boolean z3) {
            return m1077of(null, str2, null, tL_photo, null, null, null, null, null, null, j, str, messageObject, messageObject2, null, true, null, arrayList, replyMarkup, map, z, i, i2, i3, obj, null, z2, z3);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1085of(TLRPC.TL_photo tL_photo, String str, long j, MessageObject messageObject, MessageObject messageObject2, String str2, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z, int i, int i2, int i3, Object obj, boolean z2) {
            return m1076of(null, str2, null, tL_photo, null, null, null, null, null, null, j, str, messageObject, messageObject2, null, true, null, arrayList, replyMarkup, map, z, i, i2, i3, obj, null, z2);
        }

        /* JADX INFO: renamed from: of */
        private static SendMessageParams m1076of(String str, String str2, TLRPC.MessageMedia messageMedia, TLRPC.TL_photo tL_photo, VideoEditedInfo videoEditedInfo, TLRPC.User user, TLRPC.TL_document tL_document, TLRPC.TL_game tL_game, TLRPC.TL_messageMediaPoll tL_messageMediaPoll, TLRPC.TL_messageMediaInvoice tL_messageMediaInvoice, long j, String str3, MessageObject messageObject, MessageObject messageObject2, TLRPC.WebPage webPage, boolean z, MessageObject messageObject3, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z2, int i, int i2, int i3, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z3) {
            return m1077of(str, str2, messageMedia, tL_photo, videoEditedInfo, user, tL_document, tL_game, tL_messageMediaPoll, tL_messageMediaInvoice, j, str3, messageObject, messageObject2, webPage, z, messageObject3, arrayList, replyMarkup, map, z2, i, i2, i3, obj, sendAnimationData, z3, false);
        }

        /* JADX INFO: renamed from: of */
        public static SendMessageParams m1077of(String str, String str2, TLRPC.MessageMedia messageMedia, TLRPC.TL_photo tL_photo, VideoEditedInfo videoEditedInfo, TLRPC.User user, TLRPC.TL_document tL_document, TLRPC.TL_game tL_game, TLRPC.TL_messageMediaPoll tL_messageMediaPoll, TLRPC.TL_messageMediaInvoice tL_messageMediaInvoice, long j, String str3, MessageObject messageObject, MessageObject messageObject2, TLRPC.WebPage webPage, boolean z, MessageObject messageObject3, ArrayList<TLRPC.MessageEntity> arrayList, TLRPC.ReplyMarkup replyMarkup, HashMap<String, String> map, boolean z2, int i, int i2, int i3, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z3, boolean z4) {
            SendMessageParams sendMessageParams = new SendMessageParams();
            sendMessageParams.message = str;
            sendMessageParams.caption = str2;
            sendMessageParams.location = messageMedia;
            sendMessageParams.photo = tL_photo;
            sendMessageParams.videoEditedInfo = videoEditedInfo;
            sendMessageParams.user = user;
            sendMessageParams.document = tL_document;
            sendMessageParams.game = tL_game;
            sendMessageParams.poll = tL_messageMediaPoll;
            sendMessageParams.invoice = tL_messageMediaInvoice;
            sendMessageParams.peer = j;
            sendMessageParams.path = str3;
            sendMessageParams.replyToMsg = messageObject;
            sendMessageParams.replyToTopMsg = messageObject2;
            sendMessageParams.webPage = webPage;
            sendMessageParams.searchLinks = z;
            sendMessageParams.retryMessageObject = messageObject3;
            sendMessageParams.entities = arrayList;
            sendMessageParams.replyMarkup = replyMarkup;
            sendMessageParams.params = map;
            sendMessageParams.notify = z2;
            sendMessageParams.scheduleDate = i;
            sendMessageParams.scheduleRepeatPeriod = i2;
            sendMessageParams.ttl = i3;
            sendMessageParams.parentObject = obj;
            sendMessageParams.sendAnimationData = sendAnimationData;
            sendMessageParams.updateStickersOrder = z3;
            sendMessageParams.hasMediaSpoilers = z4;
            return sendMessageParams;
        }
    }

    public TLRPC.Message getMessageFromUpdate(TLRPC.Update update) {
        if (update instanceof TL_update.TL_updateNewMessage) {
            return ((TL_update.TL_updateNewMessage) update).message;
        }
        if (update instanceof TL_update.TL_updateNewChannelMessage) {
            return ((TL_update.TL_updateNewChannelMessage) update).message;
        }
        if (update instanceof TL_update.TL_updateNewScheduledMessage) {
            return ((TL_update.TL_updateNewScheduledMessage) update).message;
        }
        if (update instanceof TL_update.TL_updateQuickReplyMessage) {
            return ((TL_update.TL_updateQuickReplyMessage) update).message;
        }
        return null;
    }

    private void applyMonoForumPeerId(TLRPC.TL_messages_sendInlineBotResult tL_messages_sendInlineBotResult, long j) {
        if (j != 0) {
            TLRPC.InputPeer inputPeer = getMessagesController().getInputPeer(j);
            TLRPC.InputReplyTo inputReplyTo = tL_messages_sendInlineBotResult.reply_to;
            if (inputReplyTo != null) {
                if (inputReplyTo instanceof TLRPC.TL_inputReplyToMessage) {
                    inputReplyTo.monoforum_peer_id = inputPeer;
                    inputReplyTo.flags |= 32;
                    return;
                }
                return;
            }
            TLRPC.TL_inputReplyToMonoForum tL_inputReplyToMonoForum = new TLRPC.TL_inputReplyToMonoForum();
            tL_messages_sendInlineBotResult.reply_to = tL_inputReplyToMonoForum;
            tL_inputReplyToMonoForum.monoforum_peer_id = inputPeer;
            tL_messages_sendInlineBotResult.flags |= 1;
        }
    }

    private void applyMonoForumPeerId(TLRPC.TL_messages_sendMessage tL_messages_sendMessage, long j) {
        if (j != 0) {
            TLRPC.InputPeer inputPeer = getMessagesController().getInputPeer(j);
            TLRPC.InputReplyTo inputReplyTo = tL_messages_sendMessage.reply_to;
            if (inputReplyTo != null) {
                if (inputReplyTo instanceof TLRPC.TL_inputReplyToMessage) {
                    inputReplyTo.monoforum_peer_id = inputPeer;
                    inputReplyTo.flags |= 32;
                    return;
                }
                return;
            }
            TLRPC.TL_inputReplyToMonoForum tL_inputReplyToMonoForum = new TLRPC.TL_inputReplyToMonoForum();
            tL_messages_sendMessage.reply_to = tL_inputReplyToMonoForum;
            tL_inputReplyToMonoForum.monoforum_peer_id = inputPeer;
            tL_messages_sendMessage.flags |= 1;
        }
    }

    private void applyMonoForumPeerId(TLRPC.TL_messages_sendMedia tL_messages_sendMedia, long j) {
        if (j != 0) {
            TLRPC.InputPeer inputPeer = getMessagesController().getInputPeer(j);
            TLRPC.InputReplyTo inputReplyTo = tL_messages_sendMedia.reply_to;
            if (inputReplyTo != null) {
                if (inputReplyTo instanceof TLRPC.TL_inputReplyToMessage) {
                    inputReplyTo.monoforum_peer_id = inputPeer;
                    inputReplyTo.flags |= 32;
                    return;
                }
                return;
            }
            TLRPC.TL_inputReplyToMonoForum tL_inputReplyToMonoForum = new TLRPC.TL_inputReplyToMonoForum();
            tL_messages_sendMedia.reply_to = tL_inputReplyToMonoForum;
            tL_inputReplyToMonoForum.monoforum_peer_id = inputPeer;
            tL_messages_sendMedia.flags |= 1;
        }
    }

    private void applyMonoForumPeerId(TLRPC.TL_messages_sendMultiMedia tL_messages_sendMultiMedia, long j) {
        if (j != 0) {
            TLRPC.InputPeer inputPeer = getMessagesController().getInputPeer(j);
            TLRPC.InputReplyTo inputReplyTo = tL_messages_sendMultiMedia.reply_to;
            if (inputReplyTo != null) {
                if (inputReplyTo instanceof TLRPC.TL_inputReplyToMessage) {
                    inputReplyTo.monoforum_peer_id = inputPeer;
                    inputReplyTo.flags |= 32;
                    return;
                }
                return;
            }
            TLRPC.TL_inputReplyToMonoForum tL_inputReplyToMonoForum = new TLRPC.TL_inputReplyToMonoForum();
            tL_messages_sendMultiMedia.reply_to = tL_inputReplyToMonoForum;
            tL_inputReplyToMonoForum.monoforum_peer_id = inputPeer;
            tL_messages_sendMultiMedia.flags |= 1;
        }
    }

    private void applyMonoForumPeerId(TLRPC.TL_messages_forwardMessages tL_messages_forwardMessages, long j) {
        if (j != 0) {
            TLRPC.InputPeer inputPeer = getMessagesController().getInputPeer(j);
            TLRPC.InputReplyTo inputReplyTo = tL_messages_forwardMessages.reply_to;
            if (inputReplyTo != null) {
                if (inputReplyTo instanceof TLRPC.TL_inputReplyToMessage) {
                    inputReplyTo.monoforum_peer_id = inputPeer;
                    inputReplyTo.flags |= 32;
                    return;
                }
                return;
            }
            TLRPC.TL_inputReplyToMonoForum tL_inputReplyToMonoForum = new TLRPC.TL_inputReplyToMonoForum();
            tL_messages_forwardMessages.reply_to = tL_inputReplyToMonoForum;
            tL_inputReplyToMonoForum.monoforum_peer_id = inputPeer;
        }
    }

    private static void copyRange(File file, long j, long j2, File file2) throws IOException {
        FileChannel channel = new FileInputStream(file).getChannel();
        try {
            FileChannel channel2 = new FileOutputStream(file2).getChannel();
            try {
                channel.position(j);
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(262144);
                while (j2 > 0) {
                    byteBufferAllocate.clear();
                    byteBufferAllocate.limit((int) Math.min(byteBufferAllocate.capacity(), j2));
                    int i = channel.read(byteBufferAllocate);
                    if (i <= 0) {
                        break;
                    }
                    byteBufferAllocate.flip();
                    channel2.write(byteBufferAllocate);
                    j2 -= (long) i;
                }
                channel2.force(true);
                channel2.close();
                channel.close();
            } finally {
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
