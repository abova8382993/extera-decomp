package com.google.android.gms.cast.internal;

import android.os.SystemClock;
import com.google.android.gms.cast.MediaError;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLiveSeekableRange;
import com.google.android.gms.cast.MediaLoadRequestData;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaSeekOptions;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.internal.media.MediaCommon;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import kotlin.time.DurationKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scilab.forge.jlatexmath.TeXSymbolParser;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes4.dex */
public final class zzar extends zzd {
    public static final String zzb;
    final zzav zzc;
    final zzav zzd;
    final zzav zze;
    final zzav zzf;
    final zzav zzg;
    final zzav zzh;
    final zzav zzi;
    final zzav zzj;
    final zzav zzk;
    final zzav zzl;
    final zzav zzm;
    final zzav zzn;
    final zzav zzo;
    final zzav zzp;
    final zzav zzq;
    final zzav zzr;
    final zzav zzs;
    final zzav zzt;
    final zzav zzu;
    private long zzv;
    private MediaStatus zzw;
    private Long zzx;
    private zzao zzy;
    private int zzz;

    static {
        int i = CastUtils.$r8$clinit;
        zzb = "urn:x-cast:com.google.cast.media";
    }

    public zzar(String str) {
        super(zzb, "MediaControlChannel", null);
        this.zzz = -1;
        zzav zzavVar = new zzav(DurationKt.MILLIS_IN_DAY, "load");
        this.zzc = zzavVar;
        zzav zzavVar2 = new zzav(DurationKt.MILLIS_IN_DAY, "pause");
        this.zzd = zzavVar2;
        zzav zzavVar3 = new zzav(DurationKt.MILLIS_IN_DAY, "play");
        this.zze = zzavVar3;
        zzav zzavVar4 = new zzav(DurationKt.MILLIS_IN_DAY, "stop");
        this.zzf = zzavVar4;
        zzav zzavVar5 = new zzav(10000L, "seek");
        this.zzg = zzavVar5;
        zzav zzavVar6 = new zzav(DurationKt.MILLIS_IN_DAY, "volume");
        this.zzh = zzavVar6;
        zzav zzavVar7 = new zzav(DurationKt.MILLIS_IN_DAY, "mute");
        this.zzi = zzavVar7;
        zzav zzavVar8 = new zzav(DurationKt.MILLIS_IN_DAY, "status");
        this.zzj = zzavVar8;
        zzav zzavVar9 = new zzav(DurationKt.MILLIS_IN_DAY, "activeTracks");
        this.zzk = zzavVar9;
        zzav zzavVar10 = new zzav(DurationKt.MILLIS_IN_DAY, "trackStyle");
        this.zzl = zzavVar10;
        zzav zzavVar11 = new zzav(DurationKt.MILLIS_IN_DAY, "queueInsert");
        this.zzm = zzavVar11;
        zzav zzavVar12 = new zzav(DurationKt.MILLIS_IN_DAY, "queueUpdate");
        this.zzn = zzavVar12;
        zzav zzavVar13 = new zzav(DurationKt.MILLIS_IN_DAY, "queueRemove");
        this.zzo = zzavVar13;
        zzav zzavVar14 = new zzav(DurationKt.MILLIS_IN_DAY, "queueReorder");
        this.zzp = zzavVar14;
        zzav zzavVar15 = new zzav(DurationKt.MILLIS_IN_DAY, "queueFetchItemIds");
        this.zzq = zzavVar15;
        zzav zzavVar16 = new zzav(DurationKt.MILLIS_IN_DAY, "queueFetchItemRange");
        this.zzs = zzavVar16;
        this.zzr = new zzav(DurationKt.MILLIS_IN_DAY, "queueFetchItems");
        zzav zzavVar17 = new zzav(DurationKt.MILLIS_IN_DAY, "setPlaybackRate");
        this.zzt = zzavVar17;
        zzav zzavVar18 = new zzav(DurationKt.MILLIS_IN_DAY, "skipAd");
        this.zzu = zzavVar18;
        zzc(zzavVar);
        zzc(zzavVar2);
        zzc(zzavVar3);
        zzc(zzavVar4);
        zzc(zzavVar5);
        zzc(zzavVar6);
        zzc(zzavVar7);
        zzc(zzavVar8);
        zzc(zzavVar9);
        zzc(zzavVar10);
        zzc(zzavVar11);
        zzc(zzavVar12);
        zzc(zzavVar13);
        zzc(zzavVar14);
        zzc(zzavVar15);
        zzc(zzavVar16);
        zzc(zzavVar16);
        zzc(zzavVar17);
        zzc(zzavVar18);
        zzZ();
    }

    private final long zzR(double d, long j, long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.zzv;
        if (jElapsedRealtime < 0) {
            jElapsedRealtime = 0;
        }
        if (jElapsedRealtime == 0) {
            return j;
        }
        long j3 = j + ((long) (jElapsedRealtime * d));
        if (j2 > 0 && j3 > j2) {
            return j2;
        }
        if (j3 >= 0) {
            return j3;
        }
        return 0L;
    }

    private final boolean zzS() {
        return this.zzz != -1;
    }

    private final void zzT(JSONObject jSONObject, String str) {
        if (jSONObject.has("sequenceNumber")) {
            this.zzz = jSONObject.optInt("sequenceNumber", -1);
        } else {
            this.zza.m339w(str.concat(" message is missing a sequence number."), new Object[0]);
        }
    }

    private static int[] zzU(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        int[] iArr = new int[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            iArr[i] = jSONArray.getInt(i);
        }
        return iArr;
    }

    private final void zzV() {
        zzao zzaoVar = this.zzy;
        if (zzaoVar != null) {
            zzaoVar.zza();
        }
    }

    private final void zzW() {
        zzao zzaoVar = this.zzy;
        if (zzaoVar != null) {
            zzaoVar.zzb();
        }
    }

    private final void zzX() {
        zzao zzaoVar = this.zzy;
        if (zzaoVar != null) {
            zzaoVar.zzc();
        }
    }

    private final void zzY() {
        zzao zzaoVar = this.zzy;
        if (zzaoVar != null) {
            zzaoVar.zzd();
        }
    }

    private final void zzZ() {
        this.zzv = 0L;
        this.zzw = null;
        Iterator it = zzb().iterator();
        while (it.hasNext()) {
            ((zzav) it.next()).zze(2002);
        }
    }

    private static zzaq zzaa(JSONObject jSONObject) {
        MediaError mediaErrorZza = MediaError.zza(jSONObject);
        zzaq zzaqVar = new zzaq();
        int i = CastUtils.$r8$clinit;
        zzaqVar.zza = jSONObject.has("customData") ? jSONObject.optJSONObject("customData") : null;
        zzaqVar.zzb = mediaErrorZza;
        return zzaqVar;
    }

    public final MediaStatus zzA() {
        return this.zzw;
    }

    public final MediaInfo zzB() {
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus == null) {
            return null;
        }
        return mediaStatus.getMediaInfo();
    }

    public final long zzH(zzat zzatVar) {
        JSONObject jSONObject = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject.put("requestId", jZzg);
            jSONObject.put(TeXSymbolParser.TYPE_ATTR, "QUEUE_GET_ITEM_IDS");
            jSONObject.put("mediaSessionId", zzM());
        } catch (JSONException unused) {
        }
        zzf(jSONObject.toString(), jZzg, null);
        this.zzq.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzJ(zzat zzatVar, int[] iArr) {
        JSONObject jSONObject = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject.put("requestId", jZzg);
            jSONObject.put(TeXSymbolParser.TYPE_ATTR, "QUEUE_GET_ITEMS");
            jSONObject.put("mediaSessionId", zzM());
            JSONArray jSONArray = new JSONArray();
            for (int i : iArr) {
                jSONArray.put(i);
            }
            jSONObject.put("itemIds", jSONArray);
        } catch (JSONException unused) {
        }
        zzf(jSONObject.toString(), jZzg, null);
        this.zzr.zza(jZzg, zzatVar);
        return jZzg;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0099 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a3 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b6 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c6 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d2 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dc A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e3 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ea A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f1 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0102 A[Catch: JSONException -> 0x0054, TryCatch #0 {JSONException -> 0x0054, blocks: (B:3:0x000d, B:9:0x0038, B:11:0x0044, B:13:0x004e, B:19:0x005f, B:21:0x006b, B:23:0x007e, B:34:0x0099, B:37:0x009e, B:39:0x00b2, B:41:0x00b6, B:43:0x00c2, B:45:0x00c6, B:47:0x00ce, B:49:0x00d2, B:50:0x00d8, B:52:0x00dc, B:53:0x00df, B:55:0x00e3, B:56:0x00e6, B:58:0x00ea, B:59:0x00ed, B:61:0x00f1, B:63:0x00fb, B:64:0x00fe, B:66:0x0102, B:68:0x010b, B:70:0x011d, B:71:0x0125, B:73:0x012b, B:38:0x00a3, B:27:0x0087, B:29:0x008f, B:69:0x010f, B:77:0x013d, B:78:0x014c, B:80:0x0152, B:84:0x0168, B:86:0x0174, B:88:0x0188, B:93:0x0198, B:97:0x01a6, B:99:0x01bb, B:101:0x01d7, B:105:0x01e5, B:109:0x01f3, B:113:0x0201, B:114:0x0209, B:116:0x020f, B:117:0x021d, B:119:0x0221, B:123:0x0233, B:127:0x0245, B:128:0x0254, B:130:0x025a, B:134:0x0272, B:137:0x027f, B:138:0x028b, B:140:0x0291, B:141:0x02a3, B:145:0x02b1), top: B:149:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzL(java.lang.String r12) {
        /*
            Method dump skipped, instruction units count: 774
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.internal.zzar.zzL(java.lang.String):void");
    }

    public final long zzM() throws zzap {
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus != null) {
            return mediaStatus.zza();
        }
        throw new zzap();
    }

    public final void zzN(long j, int i) {
        Iterator it = zzb().iterator();
        while (it.hasNext()) {
            ((zzav) it.next()).zzd(j, i, null);
        }
    }

    public final /* synthetic */ void zzO(Long l) {
        this.zzx = null;
    }

    public final /* synthetic */ zzao zzP() {
        return this.zzy;
    }

    public final /* synthetic */ int zzQ() {
        return this.zzz;
    }

    @Override // com.google.android.gms.cast.internal.zzq
    public final void zzh() {
        zza();
        zzZ();
    }

    public final void zzi(zzao zzaoVar) {
        this.zzy = zzaoVar;
    }

    public final long zzj(zzat zzatVar, MediaLoadRequestData mediaLoadRequestData) {
        if (mediaLoadRequestData.getMediaInfo() == null && mediaLoadRequestData.getQueueData() == null) {
            g$$ExternalSyntheticBUOutline1.m207m("MediaInfo and MediaQueueData should not be both null");
            return 0L;
        }
        JSONObject json = mediaLoadRequestData.toJson();
        if (json == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Failed to jsonify the load request due to malformed request");
            return 0L;
        }
        long jZzg = zzg();
        try {
            json.put("requestId", jZzg);
            json.put(TeXSymbolParser.TYPE_ATTR, "LOAD");
        } catch (JSONException unused) {
        }
        zzf(json.toString(), jZzg, null);
        this.zzc.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzk(zzat zzatVar, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject2.put("requestId", jZzg);
            jSONObject2.put(TeXSymbolParser.TYPE_ATTR, "PAUSE");
            jSONObject2.put("mediaSessionId", zzM());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject2.toString(), jZzg, null);
        this.zzd.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzm(zzat zzatVar, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject2.put("requestId", jZzg);
            jSONObject2.put(TeXSymbolParser.TYPE_ATTR, "PLAY");
            jSONObject2.put("mediaSessionId", zzM());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject2.toString(), jZzg, null);
        this.zze.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzn(zzat zzatVar, MediaSeekOptions mediaSeekOptions) {
        JSONObject jSONObject = new JSONObject();
        long jZzg = zzg();
        long position = mediaSeekOptions.isSeekToInfinite() ? 4294967296000L : mediaSeekOptions.getPosition();
        try {
            jSONObject.put("requestId", jZzg);
            jSONObject.put(TeXSymbolParser.TYPE_ATTR, "SEEK");
            jSONObject.put("mediaSessionId", zzM());
            jSONObject.put("currentTime", CastUtils.millisecToSec(position));
            if (mediaSeekOptions.getResumeState() == 1) {
                jSONObject.put("resumeState", "PLAYBACK_START");
            } else if (mediaSeekOptions.getResumeState() == 2) {
                jSONObject.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (mediaSeekOptions.getCustomData() != null) {
                jSONObject.put("customData", mediaSeekOptions.getCustomData());
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject.toString(), jZzg, null);
        this.zzx = Long.valueOf(position);
        this.zzg.zza(jZzg, new zzam(this, zzatVar));
        return jZzg;
    }

    public final long zzp(zzat zzatVar, double d, JSONObject jSONObject) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            StringBuilder sb = new StringBuilder(String.valueOf(d).length() + 17);
            sb.append("Volume cannot be ");
            sb.append(d);
            throw new IllegalArgumentException(sb.toString());
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject2.put("requestId", jZzg);
            jSONObject2.put(TeXSymbolParser.TYPE_ATTR, "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zzM());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("level", d);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject2.toString(), jZzg, null);
        this.zzh.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzr(zzat zzatVar, double d, JSONObject jSONObject) throws zzap {
        if (this.zzw == null) {
            throw new zzap();
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject2.put("requestId", jZzg);
            jSONObject2.put(TeXSymbolParser.TYPE_ATTR, "SET_PLAYBACK_RATE");
            jSONObject2.put("playbackRate", d);
            Preconditions.checkNotNull(this.zzw, "mediaStatus should not be null");
            jSONObject2.put("mediaSessionId", this.zzw.zza());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject2.toString(), jZzg, null);
        this.zzt.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzs(zzat zzatVar) {
        JSONObject jSONObject = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject.put("requestId", jZzg);
            jSONObject.put(TeXSymbolParser.TYPE_ATTR, "GET_STATUS");
            MediaStatus mediaStatus = this.zzw;
            if (mediaStatus != null) {
                jSONObject.put("mediaSessionId", mediaStatus.zza());
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject.toString(), jZzg, null);
        this.zzj.zza(jZzg, zzatVar);
        return jZzg;
    }

    public final long zzv() {
        MediaStatus mediaStatus;
        MediaInfo mediaInfoZzB = zzB();
        if (mediaInfoZzB == null || (mediaStatus = this.zzw) == null) {
            return 0L;
        }
        Long l = this.zzx;
        if (l == null) {
            if (this.zzv == 0) {
                return 0L;
            }
            double playbackRate = mediaStatus.getPlaybackRate();
            long streamPosition = mediaStatus.getStreamPosition();
            return (playbackRate == 0.0d || mediaStatus.getPlayerState() != 2) ? streamPosition : zzR(playbackRate, streamPosition, mediaInfoZzB.getStreamDuration());
        }
        if (l.equals(4294967296000L)) {
            if (this.zzw.getLiveSeekableRange() != null) {
                return Math.min(l.longValue(), zzx());
            }
            if (zzz() >= 0) {
                return Math.min(l.longValue(), zzz());
            }
        }
        return l.longValue();
    }

    public final long zzx() {
        MediaLiveSeekableRange liveSeekableRange;
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus == null || (liveSeekableRange = mediaStatus.getLiveSeekableRange()) == null) {
            return 0L;
        }
        long endTime = liveSeekableRange.getEndTime();
        return !liveSeekableRange.isLiveDone() ? zzR(1.0d, endTime, -1L) : endTime;
    }

    public final long zzz() {
        MediaInfo mediaInfoZzB = zzB();
        if (mediaInfoZzB != null) {
            return mediaInfoZzB.getStreamDuration();
        }
        return 0L;
    }

    public final long zzE(zzat zzatVar, int i, long j, MediaQueueItem[] mediaQueueItemArr, int i2, Boolean bool, Integer num, JSONObject jSONObject) {
        if (j != -1 && j < 0) {
            zzar$$ExternalSyntheticBUOutline0.m341m(String.valueOf(j).length() + 33, "playPosition cannot be negative: ", j);
            return 0L;
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzg = zzg();
        try {
            jSONObject2.put("requestId", jZzg);
            jSONObject2.put(TeXSymbolParser.TYPE_ATTR, "QUEUE_UPDATE");
            jSONObject2.put("mediaSessionId", zzM());
            if (i != 0) {
                jSONObject2.put("currentItemId", i);
            }
            if (i2 != 0) {
                jSONObject2.put("jump", i2);
            }
            if (mediaQueueItemArr != null && mediaQueueItemArr.length > 0) {
                JSONArray jSONArray = new JSONArray();
                for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                    jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
                }
                jSONObject2.put("items", jSONArray);
            }
            if (bool != null) {
                jSONObject2.put("shuffle", bool);
            }
            String strZza = MediaCommon.zza(num);
            if (strZza != null) {
                jSONObject2.put("repeatMode", strZza);
            }
            if (j != -1) {
                jSONObject2.put("currentTime", CastUtils.millisecToSec(j));
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
            if (zzS()) {
                jSONObject2.put("sequenceNumber", this.zzz);
            }
        } catch (JSONException unused) {
        }
        zzf(jSONObject2.toString(), jZzg, null);
        this.zzn.zza(jZzg, new zzan(this, zzatVar));
        return jZzg;
    }
}
