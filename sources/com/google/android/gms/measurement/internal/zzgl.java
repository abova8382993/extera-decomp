package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;

/* JADX INFO: loaded from: classes5.dex */
public final class zzgl extends zzg {
    private static final String[] zza = {"app_version", "ALTER TABLE messages ADD COLUMN app_version TEXT;", "app_version_int", "ALTER TABLE messages ADD COLUMN app_version_int INTEGER;"};
    private final zzgj zzb;
    private boolean zzc;

    public zzgl(zzic zzicVar) {
        super(zzicVar);
        Context contextZzaZ = this.zzu.zzaZ();
        this.zzu.zzc();
        this.zzb = new zzgj(this, contextZzaZ, "google_app_measurement_local.db");
    }

    /* JADX WARN: Removed duplicated region for block: B:168:0x00b4 A[Catch: SQLiteException -> 0x0099, SQLiteDatabaseLockedException -> 0x00a0, SQLiteFullException -> 0x00a4, all -> 0x0165, TRY_ENTER, TryCatch #2 {all -> 0x0165, blocks: (B:155:0x008e, B:157:0x0094, B:168:0x00b4, B:170:0x00d8, B:172:0x00e2, B:174:0x00ea, B:184:0x0104, B:198:0x012c, B:200:0x0132, B:201:0x0135, B:218:0x016c, B:208:0x0155), top: B:232:0x012c }] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0161 A[PHI: r8 r10 r17
  0x0161: PHI (r8v5 int) = (r8v3 int), (r8v3 int), (r8v6 int) binds: [B:204:0x014d, B:221:0x0183, B:212:0x015f] A[DONT_GENERATE, DONT_INLINE]
  0x0161: PHI (r10v8 android.database.sqlite.SQLiteDatabase) = 
  (r10v6 android.database.sqlite.SQLiteDatabase)
  (r10v7 android.database.sqlite.SQLiteDatabase)
  (r10v9 android.database.sqlite.SQLiteDatabase)
 binds: [B:204:0x014d, B:221:0x0183, B:212:0x015f] A[DONT_GENERATE, DONT_INLINE]
  0x0161: PHI (r17v7 boolean) = (r17v4 boolean), (r17v5 boolean), (r17v8 boolean) binds: [B:204:0x014d, B:221:0x0183, B:212:0x015f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0186 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0186 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0186 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzs(int r19, byte[] r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgl.zzs(int, byte[]):boolean");
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    public final boolean zze() {
        return false;
    }

    public final void zzh() {
        int iDelete;
        zzg();
        try {
            SQLiteDatabase sQLiteDatabaseZzp = zzp();
            if (sQLiteDatabaseZzp == null || (iDelete = sQLiteDatabaseZzp.delete("messages", null, null)) <= 0) {
                return;
            }
            this.zzu.zzaW().zzk().zzb("Reset local analytics data. records", Integer.valueOf(iDelete));
        } catch (SQLiteException e) {
            this.zzu.zzaW().zzb().zzb("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zzi(zzbh zzbhVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzbi.zza(zzbhVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzs(0, bArrMarshall);
        }
        this.zzu.zzaW().zzc().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zzj(zzpl zzplVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzpm.zza(zzplVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzs(1, bArrMarshall);
        }
        this.zzu.zzaW().zzc().zza("User property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzk(zzah zzahVar) {
        zzic zzicVar = this.zzu;
        byte[] bArrZzah = zzicVar.zzk().zzah(zzahVar);
        if (bArrZzah.length <= 131072) {
            return zzs(2, bArrZzah);
        }
        zzicVar.zzaW().zzc().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzl(zzbf zzbfVar) {
        zzic zzicVar = this.zzu;
        byte[] bArrZzah = zzicVar.zzk().zzah(zzbfVar);
        if (bArrZzah == null) {
            zzicVar.zzaW().zzc().zza("Null default event parameters; not writing to database");
            return false;
        }
        if (bArrZzah.length <= 131072) {
            return zzs(4, bArrZzah);
        }
        zzicVar.zzaW().zzc().zza("Default event parameters too long for local database. Sending directly to service");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:457:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0366 A[PHI: r6 r11 r13 r17 r19 r21
  0x0366: PHI (r6v14 int) = (r6v7 int), (r6v10 int), (r6v15 int) binds: [B:458:0x034d, B:475:0x038b, B:466:0x0364] A[DONT_GENERATE, DONT_INLINE]
  0x0366: PHI (r11v3 int) = (r11v1 int), (r11v1 int), (r11v4 int) binds: [B:458:0x034d, B:475:0x038b, B:466:0x0364] A[DONT_GENERATE, DONT_INLINE]
  0x0366: PHI (r13v9 ??) = (r13v5 ??), (r13v7 ??), (r13v10 ??) binds: [B:458:0x034d, B:475:0x038b, B:466:0x0364] A[DONT_GENERATE, DONT_INLINE]
  0x0366: PHI (r17v8 java.lang.String) = (r17v3 java.lang.String), (r17v5 java.lang.String), (r17v9 java.lang.String) binds: [B:458:0x034d, B:475:0x038b, B:466:0x0364] A[DONT_GENERATE, DONT_INLINE]
  0x0366: PHI (r19v8 java.lang.String) = (r19v3 java.lang.String), (r19v5 java.lang.String), (r19v9 java.lang.String) binds: [B:458:0x034d, B:475:0x038b, B:466:0x0364] A[DONT_GENERATE, DONT_INLINE]
  0x0366: PHI (r21v8 java.lang.String) = (r21v3 java.lang.String), (r21v5 java.lang.String), (r21v9 java.lang.String) binds: [B:458:0x034d, B:475:0x038b, B:466:0x0364] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x032f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:534:0x038e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:535:0x038e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:537:0x038e A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12, types: [android.database.sqlite.SQLiteClosable, android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v17 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [android.database.sqlite.SQLiteClosable] */
    /* JADX WARN: Type inference failed for: r13v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r13v9, types: [android.database.sqlite.SQLiteClosable] */
    /* JADX WARN: Type inference failed for: r15v5, types: [com.google.android.gms.measurement.internal.zzal] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r17v15 */
    /* JADX WARN: Type inference failed for: r17v16, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r17v43 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.String, java.util.List] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zzm(int r30) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 953
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgl.zzm(int):java.util.List");
    }

    public final boolean zzn() {
        return zzs(3, new byte[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0069 A[PHI: r4
  0x0069: PHI (r4v4 int) = (r4v2 int), (r4v1 int), (r4v1 int) binds: [B:75:0x0067, B:79:0x007d, B:72:0x0060] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzo() {
        /*
            r10 = this;
            java.lang.String r0 = "Error deleting app launch break from local database"
            r10.zzg()
            boolean r1 = r10.zzc
            r2 = 0
            if (r1 == 0) goto Lc
            goto L98
        Lc:
            boolean r1 = r10.zzq()
            if (r1 == 0) goto L98
            r1 = 5
            r4 = r1
            r3 = r2
        L15:
            if (r3 >= r1) goto L89
            r5 = 0
            r6 = 1
            android.database.sqlite.SQLiteDatabase r5 = r10.zzp()     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            if (r5 != 0) goto L23
            r10.zzc = r6     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            goto L98
        L23:
            r5.beginTransaction()     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            java.lang.String r7 = "messages"
            java.lang.String r8 = "type == ?"
            r9 = 3
            java.lang.String r9 = java.lang.Integer.toString(r9)     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            java.lang.String[] r9 = new java.lang.String[]{r9}     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            r5.delete(r7, r8, r9)     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            r5.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            r5.endTransaction()     // Catch: java.lang.Throwable -> L41 android.database.sqlite.SQLiteException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L61 android.database.sqlite.SQLiteFullException -> L6d
            r5.close()
            return r6
        L41:
            r10 = move-exception
            goto L83
        L43:
            r7 = move-exception
            if (r5 == 0) goto L4f
            boolean r8 = r5.inTransaction()     // Catch: java.lang.Throwable -> L41
            if (r8 == 0) goto L4f
            r5.endTransaction()     // Catch: java.lang.Throwable -> L41
        L4f:
            com.google.android.gms.measurement.internal.zzic r8 = r10.zzu     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.measurement.internal.zzgu r8 = r8.zzaW()     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.measurement.internal.zzgs r8 = r8.zzb()     // Catch: java.lang.Throwable -> L41
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L41
            r10.zzc = r6     // Catch: java.lang.Throwable -> L41
            if (r5 == 0) goto L80
            goto L69
        L61:
            long r6 = (long) r4     // Catch: java.lang.Throwable -> L41
            android.os.SystemClock.sleep(r6)     // Catch: java.lang.Throwable -> L41
            int r4 = r4 + 20
            if (r5 == 0) goto L80
        L69:
            r5.close()
            goto L80
        L6d:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzic r8 = r10.zzu     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.measurement.internal.zzgu r8 = r8.zzaW()     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.measurement.internal.zzgs r8 = r8.zzb()     // Catch: java.lang.Throwable -> L41
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L41
            r10.zzc = r6     // Catch: java.lang.Throwable -> L41
            if (r5 == 0) goto L80
            goto L69
        L80:
            int r3 = r3 + 1
            goto L15
        L83:
            if (r5 == 0) goto L88
            r5.close()
        L88:
            throw r10
        L89:
            com.google.android.gms.measurement.internal.zzic r10 = r10.zzu
            com.google.android.gms.measurement.internal.zzgu r10 = r10.zzaW()
            com.google.android.gms.measurement.internal.zzgs r10 = r10.zze()
            java.lang.String r0 = "Error deleting app launch break from local database in reasonable time"
            r10.zza(r0)
        L98:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgl.zzo():boolean");
    }

    public final SQLiteDatabase zzp() {
        if (this.zzc) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzb.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzc = true;
        return null;
    }

    public final boolean zzq() {
        zzic zzicVar = this.zzu;
        Context contextZzaZ = zzicVar.zzaZ();
        zzicVar.zzc();
        return contextZzaZ.getDatabasePath("google_app_measurement_local.db").exists();
    }
}
