package androidx.sharetarget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.content.p002pm.ShortcutInfoCompat;
import androidx.core.content.p002pm.ShortcutInfoCompatSaver;
import androidx.core.graphics.drawable.IconCompat;
import androidx.sharetarget.ShortcutsInfoSerialization;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import p005c.a$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes.dex */
public class ShortcutInfoCompatSaverImpl extends ShortcutInfoCompatSaver<ListenableFuture<Void>> {
    private static final Object GET_INSTANCE_LOCK = new Object();
    private static volatile ShortcutInfoCompatSaverImpl sInstance;
    final File mBitmapsDir;
    final ExecutorService mCacheUpdateService;
    final Context mContext;
    private final ExecutorService mDiskIoService;
    final File mTargetsXmlFile;
    final Map<String, ShortcutsInfoSerialization.ShortcutContainer> mShortcutsMap = new ArrayMap();
    final Map<String, ListenableFuture<?>> mScheduledBitmapTasks = new ArrayMap();

    @Override // androidx.core.content.p002pm.ShortcutInfoCompatSaver
    public /* bridge */ /* synthetic */ ListenableFuture<Void> addShortcuts(List list) {
        return addShortcuts((List<ShortcutInfoCompat>) list);
    }

    @Override // androidx.core.content.p002pm.ShortcutInfoCompatSaver
    public /* bridge */ /* synthetic */ ListenableFuture<Void> removeShortcuts(List list) {
        return removeShortcuts((List<String>) list);
    }

    public static ShortcutInfoCompatSaverImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (GET_INSTANCE_LOCK) {
                try {
                    if (sInstance == null) {
                        sInstance = new ShortcutInfoCompatSaverImpl(context, createExecutorService(), createExecutorService());
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public static ExecutorService createExecutorService() {
        return new ThreadPoolExecutor(0, 1, 20L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    public ShortcutInfoCompatSaverImpl(Context context, ExecutorService executorService, ExecutorService executorService2) {
        this.mContext = context.getApplicationContext();
        this.mCacheUpdateService = executorService;
        this.mDiskIoService = executorService2;
        final File file = new File(context.getFilesDir(), "ShortcutInfoCompatSaver_share_targets");
        this.mBitmapsDir = new File(file, "ShortcutInfoCompatSaver_share_targets_bitmaps");
        this.mTargetsXmlFile = new File(file, "targets.xml");
        executorService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ShortcutInfoCompatSaverImpl.ensureDir(file);
                    ShortcutInfoCompatSaverImpl.ensureDir(ShortcutInfoCompatSaverImpl.this.mBitmapsDir);
                    ShortcutInfoCompatSaverImpl shortcutInfoCompatSaverImpl = ShortcutInfoCompatSaverImpl.this;
                    shortcutInfoCompatSaverImpl.mShortcutsMap.putAll(ShortcutsInfoSerialization.loadFromXml(shortcutInfoCompatSaverImpl.mTargetsXmlFile, shortcutInfoCompatSaverImpl.mContext));
                    ShortcutInfoCompatSaverImpl.this.deleteDanglingBitmaps(new ArrayList(ShortcutInfoCompatSaverImpl.this.mShortcutsMap.values()));
                } catch (Exception e) {
                    Log.w("ShortcutInfoCompatSaver", "ShortcutInfoCompatSaver started with an exceptions ", e);
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.core.content.p002pm.ShortcutInfoCompatSaver
    public ListenableFuture<Void> removeShortcuts(List<String> list) {
        final ArrayList arrayList = new ArrayList(list);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mCacheUpdateService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.2
            @Override // java.lang.Runnable
            public void run() {
                for (String str : arrayList) {
                    ShortcutInfoCompatSaverImpl.this.mShortcutsMap.remove(str);
                    ListenableFuture<?> listenableFutureRemove = ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.remove(str);
                    if (listenableFutureRemove != null) {
                        listenableFutureRemove.cancel(false);
                    }
                }
                ShortcutInfoCompatSaverImpl.this.scheduleSyncCurrentState(resolvableFutureCreate);
            }
        });
        return resolvableFutureCreate;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.core.content.p002pm.ShortcutInfoCompatSaver
    public ListenableFuture<Void> removeAllShortcuts() {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mCacheUpdateService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.3
            /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Map<java.lang.String, androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer>, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.DebugProbesKt] */
            /* JADX WARN: Type inference failed for: r0v7, types: [java.util.Map<java.lang.String, com.google.common.util.concurrent.ListenableFuture<?>>, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.DebugProbesKt] */
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.util.ConcurrentModificationException
            	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1095)
            	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1049)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:358)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            /*  JADX ERROR: JadxRuntimeException in pass: FinishTypeInference
                jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r0v9 boolean
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
                	at jadx.core.dex.visitors.typeinference.FinishTypeInference.lambda$visit$0(FinishTypeInference.java:27)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.FinishTypeInference.visit(FinishTypeInference.java:22)
                */
            @Override // java.lang.Runnable
            public void run() {
                /*
                    r3 = this;
                    androidx.sharetarget.ShortcutInfoCompatSaverImpl r0 = androidx.sharetarget.ShortcutInfoCompatSaverImpl.this
                    java.util.Map<java.lang.String, androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer> r0 = r0.mShortcutsMap
                    r0.probeCoroutineCreated(r0)
                    androidx.sharetarget.ShortcutInfoCompatSaverImpl r0 = androidx.sharetarget.ShortcutInfoCompatSaverImpl.this
                    java.util.Map<java.lang.String, com.google.common.util.concurrent.ListenableFuture<?>> r0 = r0.mScheduledBitmapTasks
                    java.util.Collection r0 = r0.values()
                    java.util.Iterator r0 = r0.iterator()
                L13:
                    boolean r1 = r0.hasNext()
                    if (r1 == 0) goto L24
                    java.lang.Object r1 = r0.next()
                    com.google.common.util.concurrent.ListenableFuture r1 = (com.google.common.util.concurrent.ListenableFuture) r1
                    r2 = 0
                    r1.cancel(r2)
                    goto L13
                L24:
                    androidx.sharetarget.ShortcutInfoCompatSaverImpl r0 = androidx.sharetarget.ShortcutInfoCompatSaverImpl.this
                    java.util.Map<java.lang.String, com.google.common.util.concurrent.ListenableFuture<?>> r0 = r0.mScheduledBitmapTasks
                    r0.probeCoroutineCreated(r0)
                    androidx.sharetarget.ShortcutInfoCompatSaverImpl r0 = androidx.sharetarget.ShortcutInfoCompatSaverImpl.this
                    androidx.concurrent.futures.ResolvableFuture r3 = r2
                    r0.scheduleSyncCurrentState(r3)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.sharetarget.ShortcutInfoCompatSaverImpl.RunnableC08263.run():void");
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.core.content.p002pm.ShortcutInfoCompatSaver
    public List<ShortcutInfoCompat> getShortcuts() {
        return (List) this.mCacheUpdateService.submit(new Callable<ArrayList<ShortcutInfoCompat>>() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.4
            @Override // java.util.concurrent.Callable
            public ArrayList<ShortcutInfoCompat> call() {
                ArrayList<ShortcutInfoCompat> arrayList = new ArrayList<>();
                Iterator<ShortcutsInfoSerialization.ShortcutContainer> it = ShortcutInfoCompatSaverImpl.this.mShortcutsMap.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(new ShortcutInfoCompat.Builder(it.next().mShortcutInfo).build());
                }
                return arrayList;
            }
        }).get();
    }

    public IconCompat getShortcutIcon(final String str) {
        int identifier;
        Bitmap bitmap;
        final ShortcutsInfoSerialization.ShortcutContainer shortcutContainer = (ShortcutsInfoSerialization.ShortcutContainer) this.mCacheUpdateService.submit(new Callable<ShortcutsInfoSerialization.ShortcutContainer>() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ShortcutsInfoSerialization.ShortcutContainer call() {
                return ShortcutInfoCompatSaverImpl.this.mShortcutsMap.get(str);
            }
        }).get();
        if (shortcutContainer == null) {
            return null;
        }
        if (!TextUtils.isEmpty(shortcutContainer.mResourceName)) {
            try {
                identifier = this.mContext.getResources().getIdentifier(shortcutContainer.mResourceName, null, null);
            } catch (Exception unused) {
                identifier = 0;
            }
            if (identifier != 0) {
                return IconCompat.createWithResource(this.mContext, identifier);
            }
        }
        if (TextUtils.isEmpty(shortcutContainer.mBitmapPath) || (bitmap = (Bitmap) this.mDiskIoService.submit(new Callable<Bitmap>() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Bitmap call() {
                return BitmapFactory.decodeFile(shortcutContainer.mBitmapPath);
            }
        }).get()) == null) {
            return null;
        }
        return IconCompat.createWithBitmap(bitmap);
    }

    public void deleteDanglingBitmaps(List<ShortcutsInfoSerialization.ShortcutContainer> list) {
        ArrayList arrayList = new ArrayList();
        for (ShortcutsInfoSerialization.ShortcutContainer shortcutContainer : list) {
            if (!TextUtils.isEmpty(shortcutContainer.mBitmapPath)) {
                arrayList.add(shortcutContainer.mBitmapPath);
            }
        }
        for (File file : this.mBitmapsDir.listFiles()) {
            if (!arrayList.contains(file.getAbsolutePath())) {
                file.delete();
            }
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.core.content.p002pm.ShortcutInfoCompatSaver
    public ListenableFuture<Void> addShortcuts(List<ShortcutInfoCompat> list) {
        final ArrayList arrayList = new ArrayList(list.size());
        Iterator<ShortcutInfoCompat> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ShortcutInfoCompat.Builder(it.next()).build());
        }
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mCacheUpdateService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.7
            @Override // java.lang.Runnable
            public void run() {
                for (ShortcutInfoCompat shortcutInfoCompat : arrayList) {
                    Set<String> categories = shortcutInfoCompat.getCategories();
                    if (categories != null && !categories.isEmpty()) {
                        ShortcutsInfoSerialization.ShortcutContainer shortcutContainerContainerFrom = ShortcutInfoCompatSaverImpl.this.containerFrom(shortcutInfoCompat);
                        Bitmap bitmap = shortcutContainerContainerFrom.mBitmapPath != null ? shortcutInfoCompat.getIcon().getBitmap() : null;
                        final String id = shortcutInfoCompat.getId();
                        ShortcutInfoCompatSaverImpl.this.mShortcutsMap.put(id, shortcutContainerContainerFrom);
                        if (bitmap != null) {
                            final ListenableFuture<Void> listenableFutureScheduleBitmapSaving = ShortcutInfoCompatSaverImpl.this.scheduleBitmapSaving(bitmap, shortcutContainerContainerFrom.mBitmapPath);
                            ListenableFuture<?> listenableFuturePut = ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.put(id, listenableFutureScheduleBitmapSaving);
                            if (listenableFuturePut != null) {
                                listenableFuturePut.cancel(false);
                            }
                            listenableFutureScheduleBitmapSaving.addListener(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.7.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.remove(id);
                                    if (listenableFutureScheduleBitmapSaving.isCancelled()) {
                                        return;
                                    }
                                    try {
                                        listenableFutureScheduleBitmapSaving.get();
                                    } catch (Exception e) {
                                        resolvableFutureCreate.setException(e);
                                    }
                                }
                            }, ShortcutInfoCompatSaverImpl.this.mCacheUpdateService);
                        }
                    }
                }
                ShortcutInfoCompatSaverImpl.this.scheduleSyncCurrentState(resolvableFutureCreate);
            }
        });
        return resolvableFutureCreate;
    }

    public ListenableFuture<Void> scheduleBitmapSaving(final Bitmap bitmap, final String str) {
        return submitDiskOperation(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.8
            @Override // java.lang.Runnable
            public void run() {
                ShortcutInfoCompatSaverImpl.this.saveBitmap(bitmap, str);
            }
        });
    }

    private ListenableFuture<Void> submitDiskOperation(final Runnable runnable) {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mDiskIoService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.9
            @Override // java.lang.Runnable
            public void run() {
                if (resolvableFutureCreate.isCancelled()) {
                    return;
                }
                try {
                    runnable.run();
                    resolvableFutureCreate.set(null);
                } catch (Exception e) {
                    resolvableFutureCreate.setException(e);
                }
            }
        });
        return resolvableFutureCreate;
    }

    public void scheduleSyncCurrentState(final ResolvableFuture<Void> resolvableFuture) {
        final ArrayList arrayList = new ArrayList(this.mShortcutsMap.values());
        final ListenableFuture<Void> listenableFutureSubmitDiskOperation = submitDiskOperation(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.10
            @Override // java.lang.Runnable
            public void run() {
                ShortcutInfoCompatSaverImpl.this.deleteDanglingBitmaps(arrayList);
                ShortcutsInfoSerialization.saveAsXml(arrayList, ShortcutInfoCompatSaverImpl.this.mTargetsXmlFile);
            }
        });
        listenableFutureSubmitDiskOperation.addListener(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    listenableFutureSubmitDiskOperation.get();
                    resolvableFuture.set(null);
                } catch (Exception e) {
                    resolvableFuture.setException(e);
                }
            }
        }, this.mCacheUpdateService);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.sharetarget.ShortcutsInfoSerialization.ShortcutContainer containerFrom(androidx.core.content.p002pm.ShortcutInfoCompat r5) {
        /*
            r4 = this;
            androidx.core.graphics.drawable.IconCompat r0 = r5.getIcon()
            r1 = 0
            if (r0 == 0) goto L3b
            int r2 = r0.getType()
            r3 = 1
            if (r2 == r3) goto L25
            r3 = 2
            if (r2 == r3) goto L15
            r0 = 5
            if (r2 == r0) goto L25
            goto L3b
        L15:
            android.content.Context r4 = r4.mContext
            android.content.res.Resources r4 = r4.getResources()
            int r0 = r0.getResId()
            java.lang.String r4 = r4.getResourceName(r0)
            r0 = r1
            goto L3d
        L25:
            java.io.File r0 = new java.io.File
            java.io.File r4 = r4.mBitmapsDir
            java.util.UUID r2 = java.util.UUID.randomUUID()
            java.lang.String r2 = r2.toString()
            r0.<init>(r4, r2)
            java.lang.String r4 = r0.getAbsolutePath()
            r0 = r4
            r4 = r1
            goto L3d
        L3b:
            r4 = r1
            r0 = r4
        L3d:
            androidx.core.content.pm.ShortcutInfoCompat$Builder r2 = new androidx.core.content.pm.ShortcutInfoCompat$Builder
            r2.<init>(r5)
            androidx.core.content.pm.ShortcutInfoCompat$Builder r5 = r2.setIcon(r1)
            androidx.core.content.pm.ShortcutInfoCompat r5 = r5.build()
            androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer r1 = new androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer
            r1.<init>(r5, r4, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.sharetarget.ShortcutInfoCompatSaverImpl.containerFrom(androidx.core.content.pm.ShortcutInfoCompat):androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer");
    }

    public void saveBitmap(Bitmap bitmap, String str) {
        if (bitmap == null) {
            g$$ExternalSyntheticBUOutline1.m207m("bitmap is null");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            g$$ExternalSyntheticBUOutline1.m207m("path is empty");
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            try {
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                    Log.wtf("ShortcutInfoCompatSaver", "Unable to compress bitmap");
                    throw new RuntimeException("Unable to compress bitmap for saving " + str);
                }
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | OutOfMemoryError | RuntimeException e) {
            Log.wtf("ShortcutInfoCompatSaver", "Unable to write bitmap to file", e);
            a$$ExternalSyntheticBUOutline0.m201m("Unable to write bitmap to file ", str, e);
        }
    }

    public static boolean ensureDir(File file) {
        if (file.exists() && !file.isDirectory() && !file.delete()) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }
}
