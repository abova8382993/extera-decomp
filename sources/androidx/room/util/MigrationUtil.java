package androidx.room.util;

import androidx.room.DatabaseConfiguration;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmName;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u00000\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0000\u001a\u001c\u0010\u0006\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0000\u001a$\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\u00072\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004H\u0000\u001a:\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\u00072\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004H\u0002¨\u0006\u0013"}, m877d2 = {"isMigrationRequired", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/room/DatabaseConfiguration;", "fromVersion", _UrlKt.FRAGMENT_ENCODE_SET, "toVersion", "contains", "Landroidx/room/RoomDatabase$MigrationContainer;", "startVersion", "endVersion", "findMigrationPath", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/room/migration/Migration;", "start", "end", "findUpMigrationPath", "result", _UrlKt.FRAGMENT_ENCODE_SET, "upgrade", "room-runtime"}, m878k = 2, m879mv = {2, 1, 0}, m881xi = 48)
@JvmName(name = "MigrationUtil")
public abstract class MigrationUtil {
    public static final boolean isMigrationRequired(DatabaseConfiguration databaseConfiguration, int i, int i2) {
        if (i > i2 && databaseConfiguration.allowDestructiveMigrationOnDowngrade) {
            return false;
        }
        Set<Integer> migrationNotRequiredFrom$room_runtime = databaseConfiguration.getMigrationNotRequiredFrom$room_runtime();
        return databaseConfiguration.requireMigration && (migrationNotRequiredFrom$room_runtime == null || !migrationNotRequiredFrom$room_runtime.contains(Integer.valueOf(i)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Integer, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r0v1, types: [void] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Map, kotlin.coroutines.jvm.internal.DebugProbesKt] */
    /* JADX WARN: Type inference failed for: r1v6, types: [boolean, void] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Integer, kotlin.coroutines.Continuation] */
    public static final boolean contains(RoomDatabase.MigrationContainer migrationContainer, int i, int i2) {
        ?? migrations = migrationContainer.getMigrations();
        if (migrations.probeCoroutineSuspended(Integer.valueOf(i)) == 0) {
            return false;
        }
        Map map = (Map) migrations.get(Integer.valueOf(i));
        DebugProbesKt debugProbesKtEmptyMap = map;
        if (map == null) {
            debugProbesKtEmptyMap = MapsKt.emptyMap();
        }
        return debugProbesKtEmptyMap.probeCoroutineSuspended(Integer.valueOf(i2));
    }

    public static final List<Migration> findMigrationPath(RoomDatabase.MigrationContainer migrationContainer, int i, int i2) {
        if (i == i2) {
            return CollectionsKt.emptyList();
        }
        return findUpMigrationPath(migrationContainer, new ArrayList(), i2 > i, i, i2);
    }

    private static final List<Migration> findUpMigrationPath(RoomDatabase.MigrationContainer migrationContainer, List<Migration> list, boolean z, int i, int i2) {
        Pair<Map<Integer, Migration>, Iterable<Integer>> sortedNodes$room_runtime;
        int iIntValue;
        boolean z2;
        while (true) {
            if (z) {
                if (i >= i2) {
                    return list;
                }
            } else if (i <= i2) {
                return list;
            }
            if (z) {
                sortedNodes$room_runtime = migrationContainer.getSortedDescendingNodes$room_runtime(i);
            } else {
                sortedNodes$room_runtime = migrationContainer.getSortedNodes$room_runtime(i);
            }
            if (sortedNodes$room_runtime == null) {
                return null;
            }
            Map<Integer, Migration> mapComponent1 = sortedNodes$room_runtime.component1();
            Iterator<Integer> it = sortedNodes$room_runtime.component2().iterator();
            while (it.hasNext()) {
                iIntValue = it.next().intValue();
                if (!z) {
                    if (i2 <= iIntValue && iIntValue < i) {
                        list.add(mapComponent1.get(Integer.valueOf(iIntValue)));
                        z2 = true;
                        break;
                    }
                } else if (i + 1 <= iIntValue && iIntValue <= i2) {
                    list.add(mapComponent1.get(Integer.valueOf(iIntValue)));
                    z2 = true;
                    break;
                }
            }
            iIntValue = i;
            z2 = false;
            if (!z2) {
                return null;
            }
            i = iIntValue;
        }
    }
}
