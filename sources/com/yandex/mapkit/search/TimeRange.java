package com.yandex.mapkit.search;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public class TimeRange implements Serializable {
    private Integer from;
    private Boolean isTwentyFourHours;

    /* JADX INFO: renamed from: to */
    private Integer f694to;

    public TimeRange(Boolean bool, Integer num, Integer num2) {
        this.isTwentyFourHours = bool;
        this.from = num;
        this.f694to = num2;
    }

    public TimeRange() {
    }

    public Boolean getIsTwentyFourHours() {
        return this.isTwentyFourHours;
    }

    public Integer getFrom() {
        return this.from;
    }

    public Integer getTo() {
        return this.f694to;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.isTwentyFourHours = archive.add(this.isTwentyFourHours, true);
        this.from = archive.add(this.from, true);
        this.f694to = archive.add(this.f694to, true);
    }
}
