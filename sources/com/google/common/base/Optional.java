package com.google.common.base;

import java.io.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Optional<T> implements Serializable {
    public abstract boolean equals(Object obj);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    /* JADX INFO: renamed from: or */
    public abstract T mo499or(Supplier<? extends T> supplier);

    public abstract T orNull();

    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    /* JADX INFO: renamed from: of */
    public static <T> Optional<T> m503of(T t) {
        return new Present(Preconditions.checkNotNull(t));
    }
}
