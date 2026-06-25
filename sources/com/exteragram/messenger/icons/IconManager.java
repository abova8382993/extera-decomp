package com.exteragram.messenger.icons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import androidx.view.result.PickVisualMediaRequestKt;
import androidx.view.result.contract.ActivityResultContracts$PickVisualMedia;
import com.caverock.androidsvg.SVG;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.IconPackType;
import com.exteragram.messenger.export.output.FileManager;
import com.exteragram.messenger.icons.IconPackStorageResult;
import com.exteragram.messenger.icons.p015ui.components.InstallIconPackBottomSheet;
import com.exteragram.messenger.icons.p015ui.components.ReplaceIconBottomSheet;
import com.exteragram.messenger.utils.chats.ChatUtils;
import com.sun.jna.Callback;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.p028io.CloseableKt;
import kotlin.p028io.FilesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.SupervisorKt;
import okhttp3.internal.p030ws.RealWebSocket;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;
import org.simplifiles.SimpliFiles;
import org.simplifiles.files.OverwritePolicy;
import org.simplifiles.files.SimpliDirectory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.LaunchActivity;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000ø\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0004\u0080\u0001\u0083\u0001\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0092\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\b\u001a\u00020\u00072\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000b\u0010\u0003J%\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002¢\u0006\u0004\b\u0010\u0010\u0011J!\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0016\u0010\u0017JC\u0010 \u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\f\u0010\u001d\u001a\b\u0018\u00010\u001bR\u00020\u001c2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0013H\u0002¢\u0006\u0004\b \u0010!J\u000f\u0010#\u001a\u00020\"H\u0007¢\u0006\u0004\b#\u0010$J\u000f\u0010&\u001a\u00020%H\u0007¢\u0006\u0004\b&\u0010'J\u0017\u0010*\u001a\u00020\u00132\u0006\u0010)\u001a\u00020(H\u0002¢\u0006\u0004\b*\u0010+J\u001f\u0010.\u001a\u00020\n2\u0006\u0010-\u001a\u00020,2\u0006\u0010)\u001a\u00020(H\u0002¢\u0006\u0004\b.\u0010/J\u0015\u00101\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u0013¢\u0006\u0004\b1\u00102J\r\u00103\u001a\u00020\n¢\u0006\u0004\b3\u0010\u0003J-\u00105\u001a\u0004\u0018\u0001042\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\f\u0010\u001d\u001a\b\u0018\u00010\u001bR\u00020\u001c¢\u0006\u0004\b5\u00106J\u001f\u00107\u001a\u0004\u0018\u0001042\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0018¢\u0006\u0004\b7\u00108J5\u0010;\u001a\u0004\u0018\u00010\u001f2\u0006\u00109\u001a\u00020\u00132\u0006\u0010:\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\f\u0010\u001d\u001a\b\u0018\u00010\u001bR\u00020\u001c¢\u0006\u0004\b;\u0010<J/\u0010@\u001a\u00020\n2\u0006\u0010=\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010>\u001a\u00020\u00152\b\u0010?\u001a\u0004\u0018\u00010\u0013¢\u0006\u0004\b@\u0010AJ\u001d\u0010B\u001a\u00020\n2\u0006\u0010=\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0018¢\u0006\u0004\bB\u0010CJ\u0015\u0010D\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018¢\u0006\u0004\bD\u0010EJ\u0017\u0010G\u001a\u00020\n2\b\b\u0002\u0010F\u001a\u00020\u0007¢\u0006\u0004\bG\u0010HJ\u0017\u0010I\u001a\u00020\n2\b\u0010=\u001a\u0004\u0018\u00010\u0013¢\u0006\u0004\bI\u0010JJ\u0017\u0010K\u001a\u0004\u0018\u00010\u00052\u0006\u0010=\u001a\u00020\u0013¢\u0006\u0004\bK\u0010LJ\u0017\u0010M\u001a\u0004\u0018\u00010\u00152\u0006\u0010=\u001a\u00020\u0013¢\u0006\u0004\bM\u0010NJ\u0015\u0010P\u001a\u00020\n2\u0006\u0010O\u001a\u00020\u0005¢\u0006\u0004\bP\u0010QJ\u0015\u0010R\u001a\u00020\n2\u0006\u0010=\u001a\u00020\u0013¢\u0006\u0004\bR\u0010JJ\u0017\u0010U\u001a\u00020\u00072\b\u0010T\u001a\u0004\u0018\u00010S¢\u0006\u0004\bU\u0010VJ\u001d\u0010W\u001a\u00020\n2\u0006\u0010-\u001a\u00020,2\u0006\u0010T\u001a\u00020S¢\u0006\u0004\bW\u0010XJ\u001d\u0010W\u001a\u00020\n2\u0006\u0010-\u001a\u00020,2\u0006\u00109\u001a\u00020\u0013¢\u0006\u0004\bW\u0010YJ'\u0010^\u001a\u00020\u00072\u0006\u0010Z\u001a\u00020\u00182\u0006\u0010[\u001a\u00020\u00182\b\u0010]\u001a\u0004\u0018\u00010\\¢\u0006\u0004\b^\u0010_J3\u0010f\u001a\u00020\n2\u0006\u0010a\u001a\u00020`2\u0006\u0010b\u001a\u00020\u00072\u0014\u0010e\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010d\u0012\u0004\u0012\u00020\n0c¢\u0006\u0004\bf\u0010gJ+\u0010j\u001a\u00020\n2\u0006\u0010i\u001a\u00020h2\u0006\u0010\u0019\u001a\u00020\u00182\n\b\u0002\u0010O\u001a\u0004\u0018\u00010\u0005H\u0007¢\u0006\u0004\bj\u0010kJ\u0015\u0010n\u001a\u00020\u00072\u0006\u0010m\u001a\u00020l¢\u0006\u0004\bn\u0010oR\u0014\u0010q\u001a\u00020p8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bq\u0010rR\u001a\u0010t\u001a\b\u0012\u0004\u0012\u00020\u00130s8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bt\u0010uR#\u0010w\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00180v8\u0006¢\u0006\f\n\u0004\bw\u0010x\u001a\u0004\by\u0010zR#\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00130v8\u0006¢\u0006\f\n\u0004\b{\u0010x\u001a\u0004\b|\u0010zR\u0014\u0010}\u001a\u00020\u00188\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b}\u0010~R\u0014\u0010\u007f\u001a\u00020\u00188\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u007f\u0010~R\u001a\u0010\u0081\u0001\u001a\u00030\u0080\u00018\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0081\u0001\u0010\u0082\u0001R\u001a\u0010\u0084\u0001\u001a\u00030\u0083\u00018\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0084\u0001\u0010\u0085\u0001R\u001e\u0010\u0087\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0086\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0087\u0001\u0010\u0088\u0001R\"\u0010\u0089\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00050v8\u0002X\u0082\u0004¢\u0006\u0007\n\u0005\b\u0089\u0001\u0010xR\u001c\u0010\u008b\u0001\u001a\u0005\u0018\u00010\u008a\u00018\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008b\u0001\u0010\u008c\u0001R\u0019\u0010\u008d\u0001\u001a\u00020\f8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008d\u0001\u0010\u008e\u0001R,\u0010\u0090\u0001\u001a\u0017\u0012\u0012\u0012\u0010\u0012\u0006\u0012\u0004\u0018\u00010d\u0012\u0004\u0012\u00020\n0c0\u008f\u00018\u0002X\u0082\u0004¢\u0006\b\n\u0006\b\u0090\u0001\u0010\u0091\u0001¨\u0006\u0093\u0001"}, m877d2 = {"Lcom/exteragram/messenger/icons/IconManager;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/exteragram/messenger/icons/IconPack;", "packs", _UrlKt.FRAGMENT_ENCODE_SET, "syncInstalledCustomPacks", "(Ljava/util/List;)Z", _UrlKt.FRAGMENT_ENCODE_SET, "rebuildOwnerMap", _UrlKt.FRAGMENT_ENCODE_SET, "generation", "newActivePacks", "Lcom/exteragram/messenger/icons/IconManager$ActivePacksUpdate;", "updateActivePacks", "(JLjava/util/List;)Lcom/exteragram/messenger/icons/IconManager$ActivePacksUpdate;", "pack", _UrlKt.FRAGMENT_ENCODE_SET, "iconFileName", "Ljava/io/File;", "resolvePackIconFile", "(Lcom/exteragram/messenger/icons/IconPack;Ljava/lang/String;)Ljava/io/File;", _UrlKt.FRAGMENT_ENCODE_SET, "resId", "density", "Landroid/content/res/Resources$Theme;", "Landroid/content/res/Resources;", "theme", "knownResourceName", "Landroid/graphics/Bitmap;", "getPackIconBitmap", "(Lcom/exteragram/messenger/icons/IconPack;IILandroid/content/res/Resources$Theme;Ljava/lang/String;)Landroid/graphics/Bitmap;", "Landroidx/core/graphics/drawable/IconCompat;", "getNotificationIcon", "()Landroidx/core/graphics/drawable/IconCompat;", "Landroid/graphics/drawable/Icon;", "getNotificationSystemIcon", "()Landroid/graphics/drawable/Icon;", "Lcom/exteragram/messenger/icons/IconPackStorageError;", "error", "iconPackErrorText", "(Lcom/exteragram/messenger/icons/IconPackStorageError;)Ljava/lang/String;", "Lorg/telegram/ui/ActionBar/BaseFragment;", "baseFragment", "showIconPackError", "(Lorg/telegram/ui/ActionBar/BaseFragment;Lcom/exteragram/messenger/icons/IconPackStorageError;)V", "name", "isBlacklisted", "(Ljava/lang/String;)Z", "prefetchCustomPacks", "Landroid/graphics/drawable/Drawable;", "getDrawable", "(IILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;", "getPackIconDrawable", "(Lcom/exteragram/messenger/icons/IconPack;I)Landroid/graphics/drawable/Drawable;", "path", "originalResId", "createBitmapFromFile", "(Ljava/lang/String;IILandroid/content/res/Resources$Theme;)Landroid/graphics/Bitmap;", "packId", "tempFile", "originalName", "saveCustomIcon", "(Ljava/lang/String;ILjava/io/File;Ljava/lang/String;)V", "resetCustomIcon", "(Ljava/lang/String;I)V", "getIcon", "(I)I", "update", "initialize", "(Z)V", "setActiveCustomPack", "(Ljava/lang/String;)V", "findPackById", "(Ljava/lang/String;)Lcom/exteragram/messenger/icons/IconPack;", "bundlePackBlocking", "(Ljava/lang/String;)Ljava/io/File;", "iconPack", "saveIconPackMetadata", "(Lcom/exteragram/messenger/icons/IconPack;)V", "deletePack", "Lorg/telegram/messenger/MessageObject;", "messageObject", "isIconPack", "(Lorg/telegram/messenger/MessageObject;)Z", "handleIconPack", "(Lorg/telegram/ui/ActionBar/BaseFragment;Lorg/telegram/messenger/MessageObject;)V", "(Lorg/telegram/ui/ActionBar/BaseFragment;Ljava/lang/String;)V", "requestCode", "resultCode", "Landroid/content/Intent;", "data", "onActivityResult", "(IILandroid/content/Intent;)Z", "Landroid/app/Activity;", "activity", "selectFromFiles", "Lkotlin/Function1;", "Landroid/net/Uri;", Callback.METHOD_NAME, "startIconPicker", "(Landroid/app/Activity;ZLkotlin/jvm/functions/Function1;)V", "Landroid/content/Context;", "context", "showReplaceAlert", "(Landroid/content/Context;ILcom/exteragram/messenger/icons/IconPack;)V", "Lcom/exteragram/messenger/IconPackType;", "basePackType", "isBasePackOnly", "(Lcom/exteragram/messenger/IconPackType;)Z", "Lkotlinx/coroutines/CoroutineScope;", "scope", "Lkotlinx/coroutines/CoroutineScope;", _UrlKt.FRAGMENT_ENCODE_SET, "blacklistedIcons", "Ljava/util/Set;", "Ljava/util/concurrent/ConcurrentHashMap;", "systemIcons", "Ljava/util/concurrent/ConcurrentHashMap;", "getSystemIcons", "()Ljava/util/concurrent/ConcurrentHashMap;", "systemNames", "getSystemNames", "maxMemory", "I", "cacheSize", "com/exteragram/messenger/icons/IconManager$resolvedCache$1", "resolvedCache", "Lcom/exteragram/messenger/icons/IconManager$resolvedCache$1;", "com/exteragram/messenger/icons/IconManager$sourceCache$1", "sourceCache", "Lcom/exteragram/messenger/icons/IconManager$sourceCache$1;", "Ljava/util/concurrent/CopyOnWriteArrayList;", "activePacks", "Ljava/util/concurrent/CopyOnWriteArrayList;", "iconOwnerMap", "Lkotlinx/coroutines/Job;", "initializationJob", "Lkotlinx/coroutines/Job;", "initializationGeneration", "J", "Landroid/util/SparseArray;", "resultCallbacks", "Landroid/util/SparseArray;", "ActivePacksUpdate", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nIconManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager\n+ 2 BitmapDrawable.kt\nandroidx/core/graphics/drawable/BitmapDrawableKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Bitmap.kt\nandroidx/core/graphics/BitmapKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 6 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,754:1\n27#2:755\n27#2:756\n27#2:757\n27#2:758\n1#3:759\n83#4,6:760\n71#4:766\n296#5,2:767\n296#5,2:769\n296#5,2:775\n296#5,2:777\n2792#5,3:779\n184#6,2:771\n184#6,2:773\n*S KotlinDebug\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager\n*L\n215#1:755\n229#1:756\n237#1:757\n242#1:758\n295#1:760,6\n322#1:766\n339#1:767,2\n398#1:769,2\n741#1:775,2\n743#1:777,2\n751#1:779,3\n461#1:771,2\n478#1:773,2\n*E\n"})
public final class IconManager {
    public static final IconManager INSTANCE;
    private static final CopyOnWriteArrayList<IconPack> activePacks;
    private static final Set<String> blacklistedIcons;
    private static final int cacheSize;
    private static final ConcurrentHashMap<String, IconPack> iconOwnerMap;
    private static volatile long initializationGeneration;
    private static Job initializationJob;
    private static final int maxMemory;
    private static IconManager$resolvedCache$1 resolvedCache;
    private static final SparseArray<Function1<Uri, Unit>> resultCallbacks;
    private static final CoroutineScope scope;
    private static IconManager$sourceCache$1 sourceCache;
    private static final ConcurrentHashMap<String, Integer> systemIcons;
    private static final ConcurrentHashMap<Integer, String> systemNames;

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IconPackStorageError.values().length];
            try {
                iArr[IconPackStorageError.INVALID_ARCHIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IconPackStorageError.MISSING_METADATA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IconPackStorageError.METADATA_TOO_LARGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IconPackStorageError.INVALID_METADATA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[IconPackStorageError.TOO_MANY_FILES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[IconPackStorageError.ARCHIVE_TOO_LARGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[IconPackStorageError.FILE_TOO_LARGE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[IconPackStorageError.COMPRESSION_RATIO_TOO_HIGH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[IconPackStorageError.STORAGE_ERROR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[IconPackStorageError.UNKNOWN.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmOverloads
    public final void showReplaceAlert(Context context, int i) {
        showReplaceAlert$default(this, context, i, null, 4, null);
    }

    private IconManager() {
    }

    /* JADX WARN: Type inference failed for: r4v6, types: [com.exteragram.messenger.icons.IconManager$sourceCache$1] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.exteragram.messenger.icons.IconManager$resolvedCache$1] */
    static {
        IconManager iconManager = new IconManager();
        INSTANCE = iconManager;
        scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default(null, 1, null)));
        blacklistedIcons = SetsKt.setOf((Object[]) new String[]{"blockpanel", "vd_flip", "system", "smiles_popup", "camera_btn", "cancel_big", "chats_archive_box", "chats_archive_arrow", "chats_archive_muted", "chats_archive_pin", "chats_widget_preview", "circle_big", "clone", "contacts_widget_preview", "equals", "etg_splash", "ev_minus", "ev_plus", "fast_scroll_empty", "filled_chatlink_large", "field_carret_empty", "finalize", "dice", "dino_pic", "circle", "widgets_light_badgebg", "greydivider", "greydivider_bottom", "greydivider_top", "groups_limit1", "ic_ab_new", "ic_ab_reply_2", "ic_chatlist_add_2", "ic_foreground", "ic_foreground_monet", "ic_player", "ic_reply_icon", "icon_background_clip", "icon_background_clip_round", "icon_plane", "icplaceholder", "large_ads_info", "large_away", "large_greeting", "large_log_actions", "large_monetize", "large_quickreplies", "list_selector_ex", "livepin", "load_big", "location_empty", "login_arrow1", "login_phone1", "logo_middle", "map_pin3", "map_pin_photo", "msg_media_gallery", "music_empty", "no_passport", "no_password", "nophotos", "notify", "paint_elliptical_brush", "paint_neon_brush", "paint_radial_brush", "phone_activate", "photo_placeholder_in", "photo_tooltip2", "photoview_placeholder", "screencast_big", "screencast_big_remix", "screencast_solar", "scrollbar_vertical_thumb", "scrollbar_vertical_thumb_inset", "places_btn", "newmsg_divider", "ic_launcher_dr", "smiles_info", "sms_bubble", "sms_devices", "stats_tooltip", "sticker", "story_camera", "theme_preview_image", "ton", "transparent", "venue_tooltip", "wait", "videopreview"});
        systemIcons = new ConcurrentHashMap<>();
        systemNames = new ConcurrentHashMap<>();
        int iMaxMemory = (int) (Runtime.getRuntime().maxMemory() / RealWebSocket.DEFAULT_MINIMUM_DEFLATE_SIZE);
        maxMemory = iMaxMemory;
        int iMax = Math.max(1024, iMaxMemory / 8);
        cacheSize = iMax;
        final int i = iMax / 2;
        resolvedCache = new LruCache<Integer, Bitmap>(i) { // from class: com.exteragram.messenger.icons.IconManager$resolvedCache$1
            @Override // androidx.collection.LruCache
            public /* bridge */ /* synthetic */ int sizeOf(Integer num, Bitmap bitmap) {
                return sizeOf(num.intValue(), bitmap);
            }

            public int sizeOf(int key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
        final int i2 = iMax / 2;
        sourceCache = new LruCache<String, Bitmap>(i2) { // from class: com.exteragram.messenger.icons.IconManager$sourceCache$1
            @Override // androidx.collection.LruCache
            public int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
        activePacks = new CopyOnWriteArrayList<>();
        iconOwnerMap = new ConcurrentHashMap<>();
        initialize$default(iconManager, false, 1, null);
        resultCallbacks = new SparseArray<>();
    }

    public final boolean isBlacklisted(String name) {
        return blacklistedIcons.contains(name) || StringsKt.contains$default((CharSequence) name, (CharSequence) "avd", false, 2, (Object) null) || StringsKt.endsWith$default(name, "_solar", false, 2, (Object) null) || StringsKt.endsWith$default(name, "_remix", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) name, (CharSequence) "$", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) name, (CharSequence) "animationpin", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) name, (CharSequence) "googlepay", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) name, (CharSequence) "shadow", false, 2, (Object) null) || StringsKt.startsWith$default(name, "ic_monochrome", false, 2, (Object) null) || StringsKt.startsWith$default(name, "nocover", false, 2, (Object) null) || StringsKt.startsWith$default(name, "gradient_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "stickers_back_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "media_doc_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "loading_animation", false, 2, (Object) null) || StringsKt.startsWith$default(name, "intro_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "minibubble_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "book_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "call_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "groupsintro", false, 2, (Object) null) || StringsKt.startsWith$default(name, "profile_level", false, 2, (Object) null) || StringsKt.startsWith$default(name, "widget_", false, 2, (Object) null) || StringsKt.startsWith$default(name, "zoom_slide", false, 2, (Object) null) || StringsKt.startsWith$default(name, "zoom_round", false, 2, (Object) null) || StringsKt.startsWith$default(name, "popup_fixed_alert", false, 2, (Object) null) || StringsKt.startsWith$default(name, "search_dark", false, 2, (Object) null) || StringsKt.startsWith$default(name, "bar_selector", false, 2, (Object) null);
    }

    public final ConcurrentHashMap<String, Integer> getSystemIcons() {
        return systemIcons;
    }

    public final ConcurrentHashMap<Integer, String> getSystemNames() {
        return systemNames;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m877d2 = {"Lcom/exteragram/messenger/icons/IconManager$ActivePacksUpdate;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;I)V", "STALE", "UNCHANGED", "CHANGED", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class ActivePacksUpdate {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ActivePacksUpdate[] $VALUES;
        public static final ActivePacksUpdate STALE = new ActivePacksUpdate("STALE", 0);
        public static final ActivePacksUpdate UNCHANGED = new ActivePacksUpdate("UNCHANGED", 1);
        public static final ActivePacksUpdate CHANGED = new ActivePacksUpdate("CHANGED", 2);

        private static final /* synthetic */ ActivePacksUpdate[] $values() {
            return new ActivePacksUpdate[]{STALE, UNCHANGED, CHANGED};
        }

        public static ActivePacksUpdate valueOf(String str) {
            return (ActivePacksUpdate) Enum.valueOf(ActivePacksUpdate.class, str);
        }

        public static ActivePacksUpdate[] values() {
            return (ActivePacksUpdate[]) $VALUES.clone();
        }

        private ActivePacksUpdate(String str, int i) {
        }

        static {
            ActivePacksUpdate[] activePacksUpdateArr$values = $values();
            $VALUES = activePacksUpdateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(activePacksUpdateArr$values);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$prefetchCustomPacks$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$prefetchCustomPacks$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C11411 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C11411(Continuation<? super C11411> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11411(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                IconManager.INSTANCE.syncInstalledCustomPacks(IconPackStorage.INSTANCE.getCustomPacks());
                return Unit.INSTANCE;
            }
            Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
    }

    public final void prefetchCustomPacks() {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C11411(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized boolean syncInstalledCustomPacks(List<IconPack> packs) {
        boolean z;
        try {
            Iterator<IconPack> it = packs.iterator();
            z = false;
            while (it.hasNext()) {
                String id = it.next().getId();
                if (!ExteraConfig.getIconPacksLayout().contains(id) && !ExteraConfig.getIconPacksHidden().contains(id)) {
                    ExteraConfig.getIconPacksHidden().add(id);
                    z = true;
                }
            }
            if (z) {
                ExteraConfig.saveIconPacksLayout();
            }
        } catch (Throwable th) {
            throw th;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void rebuildOwnerMap() {
        iconOwnerMap.clear();
        int size = activePacks.size() - 1;
        if (size < 0) {
            return;
        }
        while (true) {
            int i = size - 1;
            IconPack iconPack = activePacks.get(size);
            if (!iconPack.isBase()) {
                Iterator<String> it = iconPack.getIcons().keySet().iterator();
                while (it.hasNext()) {
                    iconOwnerMap.put(it.next(), iconPack);
                }
            }
            if (i < 0) {
                return;
            } else {
                size = i;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized ActivePacksUpdate updateActivePacks(long generation, List<IconPack> newActivePacks) {
        if (generation != initializationGeneration) {
            return ActivePacksUpdate.STALE;
        }
        CopyOnWriteArrayList<IconPack> copyOnWriteArrayList = activePacks;
        if (Intrinsics.areEqual(copyOnWriteArrayList, newActivePacks)) {
            return ActivePacksUpdate.UNCHANGED;
        }
        copyOnWriteArrayList.clear();
        copyOnWriteArrayList.addAll(newActivePacks);
        rebuildOwnerMap();
        resolvedCache.evictAll();
        sourceCache.evictAll();
        return ActivePacksUpdate.CHANGED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File resolvePackIconFile(IconPack pack, String iconFileName) {
        File location = pack.getLocation();
        if (location == null) {
            location = new File(IconPackStorage.INSTANCE.getIconPacksDirectory(), pack.getId());
        }
        try {
            return SimpliFiles.directory(location).file(iconFileName).getFile();
        } catch (Exception e) {
            FileLog.m1047e("Failed to resolve icon file for pack " + pack.getId(), e);
            return null;
        }
    }

    public final Drawable getDrawable(int resId, int density, Resources.Theme theme) {
        Bitmap packIconBitmap;
        Bitmap bitmap = resolvedCache.get(Integer.valueOf(resId));
        if (bitmap != null) {
            return new BitmapDrawable(ApplicationLoader.applicationContext.getResources(), bitmap);
        }
        String resourceEntryName = systemNames.get(Integer.valueOf(resId));
        if (resourceEntryName == null) {
            try {
                resourceEntryName = ApplicationLoader.applicationContext.getResources().getResourceEntryName(resId);
            } catch (Exception unused) {
                return null;
            }
        }
        String str = resourceEntryName;
        IconPack iconPack = iconOwnerMap.get(str);
        if (iconPack == null || (packIconBitmap = getPackIconBitmap(iconPack, resId, density, theme, str)) == null) {
            return null;
        }
        resolvedCache.put(Integer.valueOf(resId), packIconBitmap);
        return new BitmapDrawable(ApplicationLoader.applicationContext.getResources(), packIconBitmap);
    }

    public final Drawable getPackIconDrawable(IconPack pack, int resId) {
        Bitmap packIconBitmap$default = getPackIconBitmap$default(this, pack, resId, AndroidUtilities.displayMetrics.densityDpi, null, null, 16, null);
        if (packIconBitmap$default != null) {
            return new BitmapDrawable(ApplicationLoader.applicationContext.getResources(), packIconBitmap$default);
        }
        return null;
    }

    public static /* synthetic */ Bitmap getPackIconBitmap$default(IconManager iconManager, IconPack iconPack, int i, int i2, Resources.Theme theme, String str, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            str = null;
        }
        return iconManager.getPackIconBitmap(iconPack, i, i2, theme, str);
    }

    private final Bitmap getPackIconBitmap(IconPack pack, int resId, int density, Resources.Theme theme, String knownResourceName) {
        StringBuilder sb;
        String id;
        File fileResolvePackIconFile;
        if (pack.getLocation() != null) {
            sb = new StringBuilder();
            sb.append(pack.getId());
            sb.append(':');
            id = pack.getLocation().getAbsolutePath();
        } else {
            sb = new StringBuilder();
            id = pack.getId();
        }
        sb.append(id);
        sb.append(':');
        sb.append(resId);
        String string = sb.toString();
        Bitmap bitmap = sourceCache.get(string);
        if (bitmap != null) {
            return bitmap;
        }
        if (knownResourceName == null && (knownResourceName = systemNames.get(Integer.valueOf(resId))) == null) {
            try {
                knownResourceName = ApplicationLoader.applicationContext.getResources().getResourceEntryName(resId);
            } catch (Exception unused) {
                return null;
            }
        }
        String str = pack.getIcons().get(knownResourceName);
        if (str == null || (fileResolvePackIconFile = resolvePackIconFile(pack, str)) == null) {
            return null;
        }
        Bitmap bitmapCreateBitmapFromFile = createBitmapFromFile(fileResolvePackIconFile.getAbsolutePath(), resId, density, theme);
        if (bitmapCreateBitmapFromFile != null) {
            sourceCache.put(string, bitmapCreateBitmapFromFile);
        }
        return bitmapCreateBitmapFromFile;
    }

    public final Bitmap createBitmapFromFile(String path, int originalResId, int density, Resources.Theme theme) {
        Drawable drawableForDensity;
        try {
            Resources resources = ApplicationLoader.applicationContext.getResources();
            ExteraResources exteraResources = resources instanceof ExteraResources ? (ExteraResources) resources : null;
            if (exteraResources == null || (drawableForDensity = exteraResources.getOriginalDrawable(originalResId)) == null) {
                drawableForDensity = ResourcesCompat.getDrawableForDensity(ApplicationLoader.applicationContext.getResources(), originalResId, density, theme);
            }
            int intrinsicWidth = drawableForDensity != null ? drawableForDensity.getIntrinsicWidth() : AndroidUtilities.m1036dp(24.0f);
            int intrinsicHeight = drawableForDensity != null ? drawableForDensity.getIntrinsicHeight() : AndroidUtilities.m1036dp(24.0f);
            if (StringsKt.endsWith(path, ".svg", true)) {
                FileInputStream fileInputStream = new FileInputStream(path);
                try {
                    SVG fromInputStream = SVG.getFromInputStream(fileInputStream);
                    CloseableKt.closeFinally(fileInputStream, null);
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    fromInputStream.setDocumentWidth(intrinsicWidth);
                    fromInputStream.setDocumentHeight(intrinsicHeight);
                    fromInputStream.renderToCanvas(canvas);
                    bitmapCreateBitmap.setDensity(density);
                    return bitmapCreateBitmap;
                } finally {
                }
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                options.inSampleSize = 1;
                int i = options.outHeight;
                if (i > intrinsicHeight || options.outWidth > intrinsicWidth) {
                    int i2 = i / 2;
                    int i3 = options.outWidth / 2;
                    while (true) {
                        int i4 = options.inSampleSize;
                        if (i2 / i4 < intrinsicHeight || i3 / i4 < intrinsicWidth) {
                            break;
                        }
                        options.inSampleSize = i4 * 2;
                    }
                }
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(path, options);
                if (bitmapDecodeFile == null) {
                    return null;
                }
                if (bitmapDecodeFile.getWidth() == intrinsicWidth && bitmapDecodeFile.getHeight() == intrinsicHeight) {
                    bitmapDecodeFile.setDensity(density);
                    return bitmapDecodeFile;
                }
                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeFile, intrinsicWidth, intrinsicHeight, true);
                if (!Intrinsics.areEqual(bitmapCreateScaledBitmap, bitmapDecodeFile)) {
                    bitmapDecodeFile.recycle();
                }
                bitmapCreateScaledBitmap.setDensity(density);
                return bitmapCreateScaledBitmap;
            }
        } catch (Exception e) {
            FileLog.m1047e("Error loading icon bitmap: " + path, e);
            return null;
        }
    }

    public final void saveCustomIcon(String packId, int resId, File tempFile, String originalName) {
        Object next;
        Iterator<T> it = activePacks.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(((IconPack) next).getId(), packId)) {
                    break;
                }
            }
        }
        IconPack iconPack = (IconPack) next;
        if (iconPack == null) {
            return;
        }
        String resourceEntryName = systemNames.get(Integer.valueOf(resId));
        if (resourceEntryName == null) {
            try {
                resourceEntryName = ApplicationLoader.applicationContext.getResources().getResourceEntryName(resId);
            } catch (Exception unused) {
                return;
            }
        }
        BuildersKt__Builders_commonKt.launch$default(scope, Dispatchers.getIO(), null, new C11431(tempFile, resourceEntryName, originalName, iconPack, resId, null), 2, null);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$saveCustomIcon$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$saveCustomIcon$1", m896f = "IconManager.kt", m897i = {0, 0, 0, 0, 0, 0, 0}, m898l = {378}, m899m = "invokeSuspend", m900n = {"ext", "finalName", "packDirectory", "finalDest", "updatedMap", "updatedPack", "preDecoded"}, m902s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6"}, m903v = 1)
    public static final class C11431 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $originalName;
        final /* synthetic */ IconPack $packToEdit;
        final /* synthetic */ int $resId;
        final /* synthetic */ String $resourceName;
        final /* synthetic */ File $tempFile;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11431(File file, String str, String str2, IconPack iconPack, int i, Continuation<? super C11431> continuation) {
            super(2, continuation);
            this.$tempFile = file;
            this.$resourceName = str;
            this.$originalName = str2;
            this.$packToEdit = iconPack;
            this.$resId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11431(this.$tempFile, this.$resourceName, this.$originalName, this.$packToEdit, this.$resId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11431) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                String extension = FilesKt.getExtension(this.$tempFile);
                String str = this.$resourceName;
                String str2 = this.$originalName;
                if (str2 != null) {
                    String strFileNameFromUserString = FileManager.fileNameFromUserString(str2);
                    if (strFileNameFromUserString.length() > 0) {
                        if (StringsKt.endsWith(strFileNameFromUserString, "." + extension, true)) {
                            str = strFileNameFromUserString;
                        } else {
                            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) strFileNameFromUserString, '.', 0, false, 6, (Object) null);
                            if (iLastIndexOf$default != -1) {
                                strFileNameFromUserString = strFileNameFromUserString.substring(0, iLastIndexOf$default);
                            }
                            str = strFileNameFromUserString + '.' + extension;
                        }
                    }
                } else {
                    str = str + '.' + extension;
                }
                IconPackStorage iconPackStorage = IconPackStorage.INSTANCE;
                SimpliDirectory simpliDirectoryCreate = SimpliFiles.directory(new File(iconPackStorage.getIconPacksDirectory(), this.$packToEdit.getId())).create();
                File file = simpliDirectoryCreate.file(str).getFile();
                SimpliFiles.file(this.$tempFile).copyTo(file, OverwritePolicy.REPLACE);
                SimpliFiles.file(this.$tempFile).delete();
                Map mutableMap = MapsKt.toMutableMap(this.$packToEdit.getIcons());
                mutableMap.put(this.$resourceName, file.getName());
                IconPack iconPackCopy$default = IconPack.copy$default(this.$packToEdit, null, null, null, null, mutableMap, null, null, 111, null);
                iconPackStorage.saveIconPackMetadata(iconPackCopy$default);
                Bitmap bitmapCreateBitmapFromFile = IconManager.INSTANCE.createBitmapFromFile(file.getAbsolutePath(), this.$resId, AndroidUtilities.displayMetrics.densityDpi, null);
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(bitmapCreateBitmapFromFile, this.$resId, this.$packToEdit, iconPackCopy$default, null);
                this.L$0 = SpillingKt.nullOutSpilledVariable(extension);
                this.L$1 = SpillingKt.nullOutSpilledVariable(str);
                this.L$2 = SpillingKt.nullOutSpilledVariable(simpliDirectoryCreate);
                this.L$3 = SpillingKt.nullOutSpilledVariable(file);
                this.L$4 = SpillingKt.nullOutSpilledVariable(mutableMap);
                this.L$5 = SpillingKt.nullOutSpilledVariable(iconPackCopy$default);
                this.L$6 = SpillingKt.nullOutSpilledVariable(bitmapCreateBitmapFromFile);
                this.label = 1;
                if (BuildersKt.withContext(main, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$saveCustomIcon$1$1, reason: invalid class name */
        @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$saveCustomIcon$1$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
        @SourceDebugExtension({"SMAP\nIconManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$saveCustomIcon$1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,754:1\n363#2,7:755\n*S KotlinDebug\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$saveCustomIcon$1$1\n*L\n386#1:755,7\n*E\n"})
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ IconPack $packToEdit;
            final /* synthetic */ Bitmap $preDecoded;
            final /* synthetic */ int $resId;
            final /* synthetic */ IconPack $updatedPack;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Bitmap bitmap, int i, IconPack iconPack, IconPack iconPack2, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$preDecoded = bitmap;
                this.$resId = i;
                this.$packToEdit = iconPack;
                this.$updatedPack = iconPack2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$preDecoded, this.$resId, this.$packToEdit, this.$updatedPack, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
                if (this.$preDecoded != null) {
                    IconManager.resolvedCache.put(Boxing.boxInt(this.$resId), this.$preDecoded);
                } else {
                    IconManager.resolvedCache.remove(Boxing.boxInt(this.$resId));
                }
                IconManager.sourceCache.remove(this.$packToEdit.getId() + ':' + this.$resId);
                CopyOnWriteArrayList copyOnWriteArrayList = IconManager.activePacks;
                IconPack iconPack = this.$updatedPack;
                Iterator it = copyOnWriteArrayList.iterator();
                int i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    }
                    if (Intrinsics.areEqual(((IconPack) it.next()).getId(), iconPack.getId())) {
                        break;
                    }
                    i++;
                }
                if (i != -1) {
                    IconManager.activePacks.set(i, this.$updatedPack);
                    IconManager.INSTANCE.rebuildOwnerMap();
                }
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.iconPackUpdated, new Object[0]);
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r11v10, types: [java.util.Map, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference failed for: r11v11, types: [void] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, java.lang.String, kotlin.coroutines.Continuation] */
    public final void resetCustomIcon(String packId, int resId) {
        Object next;
        Iterator<T> it = activePacks.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(((IconPack) next).getId(), packId)) {
                    break;
                }
            }
        }
        IconPack iconPack = (IconPack) next;
        if (iconPack == null) {
            return;
        }
        String resourceEntryName = systemNames.get(Integer.valueOf(resId));
        if (resourceEntryName == null) {
            try {
                resourceEntryName = ApplicationLoader.applicationContext.getResources().getResourceEntryName(resId);
            } catch (Exception unused) {
                return;
            }
        }
        ?? r3 = resourceEntryName;
        if (iconPack.getIcons().probeCoroutineSuspended(r3) != 0) {
            BuildersKt__Builders_commonKt.launch$default(scope, Dispatchers.getIO(), null, new C11421(iconPack, r3, iconPack.getIcons().get(r3), resId, null), 2, null);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$resetCustomIcon$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$resetCustomIcon$1", m896f = "IconManager.kt", m897i = {0, 0}, m898l = {425}, m899m = "invokeSuspend", m900n = {"updatedMap", "updatedPack"}, m902s = {"L$0", "L$1"}, m903v = 1)
    @SourceDebugExtension({"SMAP\nIconManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$resetCustomIcon$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,754:1\n1807#2,3:755\n*S KotlinDebug\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$resetCustomIcon$1\n*L\n413#1:755,3\n*E\n"})
    public static final class C11421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $iconFileName;
        final /* synthetic */ IconPack $packToEdit;
        final /* synthetic */ int $resId;
        final /* synthetic */ String $resourceName;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11421(IconPack iconPack, String str, String str2, int i, Continuation<? super C11421> continuation) {
            super(2, continuation);
            this.$packToEdit = iconPack;
            this.$resourceName = str;
            this.$iconFileName = str2;
            this.$resId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11421(this.$packToEdit, this.$resourceName, this.$iconFileName, this.$resId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            File fileResolvePackIconFile;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Map mutableMap = MapsKt.toMutableMap(this.$packToEdit.getIcons());
                mutableMap.remove(this.$resourceName);
                if (this.$iconFileName != null) {
                    Collection collectionValues = mutableMap.values();
                    String str = this.$iconFileName;
                    if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                        fileResolvePackIconFile = IconManager.INSTANCE.resolvePackIconFile(this.$packToEdit, this.$iconFileName);
                        if (fileResolvePackIconFile != null) {
                            SimpliFiles.file(fileResolvePackIconFile).delete();
                        }
                    } else {
                        Iterator it = collectionValues.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual((String) it.next(), str)) {
                                break;
                            }
                        }
                        fileResolvePackIconFile = IconManager.INSTANCE.resolvePackIconFile(this.$packToEdit, this.$iconFileName);
                        if (fileResolvePackIconFile != null && fileResolvePackIconFile.exists()) {
                            SimpliFiles.file(fileResolvePackIconFile).delete();
                        }
                    }
                }
                IconPack iconPackCopy$default = IconPack.copy$default(this.$packToEdit, null, null, null, null, mutableMap, null, null, 111, null);
                IconPackStorage.INSTANCE.saveIconPackMetadata(iconPackCopy$default);
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$resId, this.$packToEdit, iconPackCopy$default, null);
                this.L$0 = SpillingKt.nullOutSpilledVariable(mutableMap);
                this.L$1 = SpillingKt.nullOutSpilledVariable(iconPackCopy$default);
                this.label = 1;
                if (BuildersKt.withContext(main, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$resetCustomIcon$1$1, reason: invalid class name */
        @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$resetCustomIcon$1$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
        @SourceDebugExtension({"SMAP\nIconManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$resetCustomIcon$1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,754:1\n363#2,7:755\n*S KotlinDebug\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$resetCustomIcon$1$1\n*L\n429#1:755,7\n*E\n"})
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ IconPack $packToEdit;
            final /* synthetic */ int $resId;
            final /* synthetic */ IconPack $updatedPack;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(int i, IconPack iconPack, IconPack iconPack2, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$resId = i;
                this.$packToEdit = iconPack;
                this.$updatedPack = iconPack2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$resId, this.$packToEdit, this.$updatedPack, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    IconManager.resolvedCache.remove(Boxing.boxInt(this.$resId));
                    IconManager.sourceCache.remove(this.$packToEdit.getId() + ':' + this.$resId);
                    CopyOnWriteArrayList copyOnWriteArrayList = IconManager.activePacks;
                    IconPack iconPack = this.$updatedPack;
                    Iterator it = copyOnWriteArrayList.iterator();
                    int i = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i = -1;
                            break;
                        }
                        if (Intrinsics.areEqual(((IconPack) it.next()).getId(), iconPack.getId())) {
                            break;
                        }
                        i++;
                    }
                    if (i != -1) {
                        IconManager.activePacks.set(i, this.$updatedPack);
                        IconManager.INSTANCE.rebuildOwnerMap();
                    }
                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.iconPackUpdated, new Object[0]);
                    return Unit.INSTANCE;
                }
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        }
    }

    public final int getIcon(int resId) {
        int i;
        for (IconPack iconPack : activePacks) {
            if (iconPack.isBase() && iconPack.getPreinstalledMap() != null && (i = iconPack.getPreinstalledMap().get(resId, -1)) != -1) {
                return i;
            }
        }
        return resId;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070 A[RETURN] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.util.Map, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference failed for: r2v8, types: [void] */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.core.graphics.drawable.IconCompat getNotificationIcon() {
        /*
            Method dump skipped, instruction units count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.icons.IconManager.getNotificationIcon():androidx.core.graphics.drawable.IconCompat");
    }

    public static SparseIntArray $r8$lambda$XcViHtMKy9sgd3Qw8wHxNP7dlig(String str) {
        IconPack basePack = BaseIconPacks.INSTANCE.getBasePack(str);
        if (basePack != null) {
            return basePack.getPreinstalledMap();
        }
        return null;
    }

    @JvmStatic
    public static final Icon getNotificationSystemIcon() {
        return getNotificationIcon().toIcon(ApplicationLoader.applicationContext);
    }

    public static /* synthetic */ void initialize$default(IconManager iconManager, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        iconManager.initialize(z);
    }

    public final synchronized void initialize(boolean update) {
        Job job;
        Job job2 = initializationJob;
        if (job2 == null || !job2.isActive() || update) {
            if (update && (job = initializationJob) != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            initializationGeneration++;
            initializationJob = BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C11401(initializationGeneration, update, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$initialize$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$initialize$1", m896f = "IconManager.kt", m897i = {0, 1}, m898l = {542, 555}, m899m = "invokeSuspend", m900n = {"newActivePacks", "newActivePacks"}, m902s = {"L$0", "L$0"}, m903v = 1)
    public static final class C11401 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $generation;
        final /* synthetic */ boolean $update;
        Object L$0;
        int label;

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$initialize$1$WhenMappings */
        /* JADX INFO: loaded from: classes4.dex */
        @Metadata(m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        public static final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ActivePacksUpdate.values().length];
                try {
                    iArr[ActivePacksUpdate.STALE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ActivePacksUpdate.UNCHANGED.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ActivePacksUpdate.CHANGED.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11401(long j, boolean z, Continuation<? super C11401> continuation) {
            super(2, continuation);
            this.$generation = j;
            this.$update = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11401(this.$generation, this.$update, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:45:0x00f1, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r1, r4, r12) == r0) goto L55;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0116, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r12) == r0) goto L55;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) {
            /*
                Method dump skipped, instruction units count: 287
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.icons.IconManager.C11401.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$initialize$1$1, reason: invalid class name */
        /* JADX INFO: loaded from: classes4.dex */
        @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$initialize$1$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ long $generation;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(long j, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$generation = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$generation, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
                if (this.$generation != IconManager.initializationGeneration) {
                    return Unit.INSTANCE;
                }
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.iconPackUpdated, new Object[0]);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$initialize$1$2, reason: invalid class name */
        @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$initialize$1$2", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
        @SourceDebugExtension({"SMAP\nIconManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$initialize$1$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,754:1\n296#2,2:755\n296#2,2:757\n*S KotlinDebug\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$initialize$1$2\n*L\n560#1:755,2\n561#1:757,2\n*E\n"})
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ long $generation;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(long j, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$generation = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$generation, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:40:0x0091  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r5) {
                /*
                    Method dump skipped, instruction units count: 211
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.icons.IconManager.C11401.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    public final void setActiveCustomPack(String packId) {
        if (packId == null || ExteraConfig.getIconPacksLayout().contains(packId)) {
            return;
        }
        ExteraConfig.getIconPacksLayout().add(packId);
        ExteraConfig.getIconPacksHidden().remove(packId);
        ExteraConfig.saveIconPacksLayout();
        initialize(true);
    }

    public final IconPack findPackById(String packId) {
        return IconPackStorage.INSTANCE.findPackById(packId);
    }

    public final File bundlePackBlocking(String packId) {
        return IconPackStorage.INSTANCE.bundlePackBlocking(packId);
    }

    public final void saveIconPackMetadata(IconPack iconPack) {
        IconPackStorage.INSTANCE.saveIconPackMetadata(iconPack);
        BuildersKt__Builders_commonKt.launch$default(scope, Dispatchers.getMain(), null, new C11441(iconPack, null), 2, null);
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$saveIconPackMetadata$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$saveIconPackMetadata$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    @SourceDebugExtension({"SMAP\nIconManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$saveIconPackMetadata$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,754:1\n363#2,7:755\n*S KotlinDebug\n*F\n+ 1 IconManager.kt\ncom/exteragram/messenger/icons/IconManager$saveIconPackMetadata$1\n*L\n609#1:755,7\n*E\n"})
    public static final class C11441 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ IconPack $iconPack;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11441(IconPack iconPack, Continuation<? super C11441> continuation) {
            super(2, continuation);
            this.$iconPack = iconPack;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11441(this.$iconPack, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11441) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                CopyOnWriteArrayList copyOnWriteArrayList = IconManager.activePacks;
                IconPack iconPack = this.$iconPack;
                Iterator it = copyOnWriteArrayList.iterator();
                int i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    }
                    if (Intrinsics.areEqual(((IconPack) it.next()).getId(), iconPack.getId())) {
                        break;
                    }
                    i++;
                }
                if (i != -1) {
                    IconManager.activePacks.set(i, this.$iconPack);
                    IconManager.INSTANCE.rebuildOwnerMap();
                    IconManager.resolvedCache.evictAll();
                    IconManager.sourceCache.evictAll();
                }
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.iconPackUpdated, new Object[0]);
                return Unit.INSTANCE;
            }
            Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$deletePack$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$deletePack$1", m896f = "IconManager.kt", m897i = {}, m898l = {624}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C11371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $packId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11371(String str, Continuation<? super C11371> continuation) {
            super(2, continuation);
            this.$packId = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11371(this.$packId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Exception {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                IconPackStorage.INSTANCE.deletePack(this.$packId);
                MainCoroutineDispatcher main = Dispatchers.getMain();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$packId, null);
                this.label = 1;
                if (BuildersKt.withContext(main, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$deletePack$1$1, reason: invalid class name */
        @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$deletePack$1$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $packId;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(String str, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$packId = str;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$packId, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
                if (ExteraConfig.getIconPacksLayout().contains(this.$packId) || ExteraConfig.getIconPacksHidden().contains(this.$packId)) {
                    ExteraConfig.getIconPacksLayout().remove(this.$packId);
                    ExteraConfig.getIconPacksHidden().remove(this.$packId);
                    ExteraConfig.saveIconPacksLayout();
                }
                IconManager.INSTANCE.initialize(true);
                return Unit.INSTANCE;
            }
        }
    }

    public final void deletePack(String packId) {
        BuildersKt__Builders_commonKt.launch$default(scope, Dispatchers.getIO(), null, new C11371(packId, null), 2, null);
    }

    public final boolean isIconPack(MessageObject messageObject) {
        String pathToMessage = ChatUtils.getInstance().getPathToMessage(messageObject);
        return (messageObject == null || messageObject.getDocumentName() == null || TextUtils.isEmpty(pathToMessage) || !StringsKt.endsWith$default(pathToMessage, ".icons", false, 2, (Object) null)) ? false : true;
    }

    public final void handleIconPack(BaseFragment baseFragment, MessageObject messageObject) {
        handleIconPack(baseFragment, ChatUtils.getInstance().getPathToMessage(messageObject));
    }

    private final String iconPackErrorText(IconPackStorageError error) {
        int i;
        switch (WhenMappings.$EnumSwitchMapping$0[error.ordinal()]) {
            case 1:
                i = C2797R.string.IconPackErrorInvalidArchive;
                break;
            case 2:
                i = C2797R.string.IconPackErrorMissingMetadata;
                break;
            case 3:
                i = C2797R.string.IconPackErrorMetadataTooLarge;
                break;
            case 4:
                i = C2797R.string.IconPackErrorInvalidMetadata;
                break;
            case 5:
                i = C2797R.string.IconPackErrorTooManyFiles;
                break;
            case 6:
                i = C2797R.string.IconPackErrorArchiveTooLarge;
                break;
            case 7:
                i = C2797R.string.IconPackErrorFileTooLarge;
                break;
            case 8:
                i = C2797R.string.IconPackErrorCompressionRatioTooHigh;
                break;
            case 9:
                i = C2797R.string.IconPackErrorStorage;
                break;
            case 10:
                i = C2797R.string.UnknownError;
                break;
            default:
                LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m();
                return null;
        }
        return LocaleController.getString(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showIconPackError(BaseFragment baseFragment, IconPackStorageError error) {
        BulletinFactory.m1143of(baseFragment).createSimpleBulletin(C2797R.raw.error, iconPackErrorText(error)).show();
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$handleIconPack$1 */
    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$handleIconPack$1", m896f = "IconManager.kt", m897i = {0, 1, 1}, m898l = {669, 670}, m899m = "invokeSuspend", m900n = {"file", "file", "packResult"}, m902s = {"L$0", "L$0", "L$1"}, m903v = 1)
    public static final class C11381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseFragment $baseFragment;
        final /* synthetic */ String $path;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11381(String str, BaseFragment baseFragment, Continuation<? super C11381> continuation) {
            super(2, continuation);
            this.$path = str;
            this.$baseFragment = baseFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11381(this.$path, this.$baseFragment, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0060, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r3, r4, r7) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L29
                if (r1 == r3) goto L21
                if (r1 != r2) goto L1a
                java.lang.Object r0 = r7.L$1
                com.exteragram.messenger.icons.IconPackStorageResult r0 = (com.exteragram.messenger.icons.IconPackStorageResult) r0
                java.lang.Object r7 = r7.L$0
                java.io.File r7 = (java.io.File) r7
                kotlin.ResultKt.throwOnFailure(r8)
                goto L63
            L1a:
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                okio.Segment$$ExternalSyntheticBUOutline1.m992m(r7)
                r7 = 0
                return r7
            L21:
                java.lang.Object r1 = r7.L$0
                java.io.File r1 = (java.io.File) r1
                kotlin.ResultKt.throwOnFailure(r8)
                goto L40
            L29:
                kotlin.ResultKt.throwOnFailure(r8)
                java.io.File r1 = new java.io.File
                java.lang.String r8 = r7.$path
                r1.<init>(r8)
                com.exteragram.messenger.icons.IconPackStorage r8 = com.exteragram.messenger.icons.IconPackStorage.INSTANCE
                r7.L$0 = r1
                r7.label = r3
                java.lang.Object r8 = r8.parsePackFromZip(r1, r7)
                if (r8 != r0) goto L40
                goto L62
            L40:
                com.exteragram.messenger.icons.IconPackStorageResult r8 = (com.exteragram.messenger.icons.IconPackStorageResult) r8
                kotlinx.coroutines.MainCoroutineDispatcher r3 = kotlinx.coroutines.Dispatchers.getMain()
                com.exteragram.messenger.icons.IconManager$handleIconPack$1$1 r4 = new com.exteragram.messenger.icons.IconManager$handleIconPack$1$1
                org.telegram.ui.ActionBar.BaseFragment r5 = r7.$baseFragment
                r6 = 0
                r4.<init>(r8, r5, r1, r6)
                java.lang.Object r1 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r1)
                r7.L$0 = r1
                java.lang.Object r8 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r8)
                r7.L$1 = r8
                r7.label = r2
                java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r7)
                if (r7 != r0) goto L63
            L62:
                return r0
            L63:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.icons.IconManager.C11381.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$handleIconPack$1$1, reason: invalid class name */
        @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
        @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$handleIconPack$1$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ BaseFragment $baseFragment;
            final /* synthetic */ File $file;
            final /* synthetic */ IconPackStorageResult<IconPack> $packResult;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(IconPackStorageResult<IconPack> iconPackStorageResult, BaseFragment baseFragment, File file, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$packResult = iconPackStorageResult;
                this.$baseFragment = baseFragment;
                this.$file = file;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$packResult, this.$baseFragment, this.$file, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                ResultKt.throwOnFailure(obj);
                IconPackStorageResult<IconPack> iconPackStorageResult = this.$packResult;
                if (iconPackStorageResult instanceof IconPackStorageResult.Success) {
                    final IconPack iconPack = (IconPack) ((IconPackStorageResult.Success) iconPackStorageResult).getValue();
                    Activity parentActivity = this.$baseFragment.getParentActivity();
                    final File file = this.$file;
                    final BaseFragment baseFragment = this.$baseFragment;
                    this.$baseFragment.showDialog(new InstallIconPackBottomSheet(parentActivity, iconPack, new InstallIconPackBottomSheet.InstallDelegate() { // from class: com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$$ExternalSyntheticLambda0
                        @Override // com.exteragram.messenger.icons.ui.components.InstallIconPackBottomSheet.InstallDelegate
                        public final void onInstall(boolean z, boolean z2) {
                            BuildersKt__Builders_commonKt.launch$default(IconManager.scope, null, null, new IconManager$handleIconPack$1$1$bottomSheet$1$1(file, baseFragment, z2, iconPack, z, null), 3, null);
                        }
                    }));
                } else if (iconPackStorageResult instanceof IconPackStorageResult.Failure) {
                    IconManager.INSTANCE.showIconPackError(this.$baseFragment, ((IconPackStorageResult.Failure) iconPackStorageResult).getError());
                } else {
                    LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m();
                    return null;
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void handleIconPack(BaseFragment baseFragment, String path) {
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C11381(path, baseFragment, null), 3, null);
    }

    public final boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        SparseArray<Function1<Uri, Unit>> sparseArray = resultCallbacks;
        Function1<Uri, Unit> function1 = sparseArray.get(requestCode);
        if (function1 == null) {
            return false;
        }
        sparseArray.remove(requestCode);
        function1.invoke(data != null ? data.getData() : null);
        return true;
    }

    public final void startIconPicker(Activity activity, boolean selectFromFiles, Function1<? super Uri, Unit> callback) {
        Intent intentCreateIntent;
        resultCallbacks.put(43, callback);
        if (ActivityResultContracts$PickVisualMedia.INSTANCE.isPhotoPickerAvailable(activity) && !selectFromFiles) {
            intentCreateIntent = new ActivityResultContracts$PickVisualMedia().createIntent((Context) activity, PickVisualMediaRequestKt.PickVisualMediaRequest(ActivityResultContracts$PickVisualMedia.ImageOnly.INSTANCE));
        } else {
            Intent intent = new Intent(selectFromFiles ? "android.intent.action.OPEN_DOCUMENT" : "android.intent.action.GET_CONTENT");
            intent.setType("*/*");
            intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/*", "image/svg+xml"});
            if (selectFromFiles) {
                intent.addCategory("android.intent.category.OPENABLE");
            }
            intentCreateIntent = intent;
        }
        activity.startActivityForResult(intentCreateIntent, 43);
    }

    public static /* synthetic */ void showReplaceAlert$default(IconManager iconManager, Context context, int i, IconPack iconPack, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            iconPack = null;
        }
        iconManager.showReplaceAlert(context, i, iconPack);
    }

    @JvmOverloads
    public final void showReplaceAlert(Context context, int resId, IconPack iconPack) {
        if (iconPack == null) {
            Object obj = null;
            if (ExteraConfig.getEditingIconPackId() != null) {
                Iterator<T> it = activePacks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if (Intrinsics.areEqual(((IconPack) next).getId(), ExteraConfig.getEditingIconPackId())) {
                        obj = next;
                        break;
                    }
                }
                iconPack = (IconPack) obj;
            } else {
                Iterator<T> it2 = activePacks.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Object next2 = it2.next();
                    if (!((IconPack) next2).isBase()) {
                        obj = next2;
                        break;
                    }
                }
                iconPack = (IconPack) obj;
            }
            if (iconPack == null) {
                return;
            }
        }
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return;
        }
        safeLastFragment.showDialog(new ReplaceIconBottomSheet(context, resId, iconPack));
    }

    public final boolean isBasePackOnly(IconPackType basePackType) {
        if (ExteraConfig.getIconPack() != basePackType) {
            return false;
        }
        CopyOnWriteArrayList<IconPack> copyOnWriteArrayList = activePacks;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.isEmpty()) {
            return true;
        }
        Iterator<T> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (!((IconPack) it.next()).isBase()) {
                return false;
            }
        }
        return true;
    }
}
