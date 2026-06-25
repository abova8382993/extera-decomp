package com.exteragram.messenger.api.model;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import okhttp3.internal.url._UrlKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, m877d2 = {"Lcom/exteragram/messenger/api/model/RegDateFlag;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;I)V", "EXACT", "INTERPOLATED", "LT", "ET", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class RegDateFlag {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ RegDateFlag[] $VALUES;
    public static final RegDateFlag EXACT = new RegDateFlag("EXACT", 0);
    public static final RegDateFlag INTERPOLATED = new RegDateFlag("INTERPOLATED", 1);

    /* JADX INFO: renamed from: LT */
    public static final RegDateFlag f303LT = new RegDateFlag("LT", 2);

    /* JADX INFO: renamed from: ET */
    public static final RegDateFlag f302ET = new RegDateFlag("ET", 3);

    private static final /* synthetic */ RegDateFlag[] $values() {
        return new RegDateFlag[]{EXACT, INTERPOLATED, f303LT, f302ET};
    }

    public static EnumEntries<RegDateFlag> getEntries() {
        return $ENTRIES;
    }

    public static RegDateFlag valueOf(String str) {
        return (RegDateFlag) Enum.valueOf(RegDateFlag.class, str);
    }

    public static RegDateFlag[] values() {
        return (RegDateFlag[]) $VALUES.clone();
    }

    private RegDateFlag(String str, int i) {
    }

    static {
        RegDateFlag[] regDateFlagArr$values = $values();
        $VALUES = regDateFlagArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(regDateFlagArr$values);
    }
}
