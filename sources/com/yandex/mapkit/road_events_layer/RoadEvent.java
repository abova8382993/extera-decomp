package com.yandex.mapkit.road_events_layer;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.road_events.EventTag;
import com.yandex.runtime.NativeObject;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.ArchivingHandler;
import com.yandex.runtime.bindings.EnumHandler;
import com.yandex.runtime.bindings.Serializable;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class RoadEvent implements Serializable {
    private String caption;
    private boolean caption__is_initialized;

    /* JADX INFO: renamed from: id */
    private String f683id;
    private boolean id__is_initialized;
    private boolean isInFuture;
    private boolean isInFuture__is_initialized;
    private NativeObject nativeObject;
    private Point position;
    private boolean position__is_initialized;
    private List<EventTag> tags;
    private boolean tags__is_initialized;

    private native String getCaption__Native();

    private native String getId__Native();

    private native boolean getIsInFuture__Native();

    private native Point getPosition__Native();

    private native List<EventTag> getTags__Native();

    private native NativeObject init(String str, Point point, List<EventTag> list, String str2, boolean z);

    public RoadEvent() {
        this.id__is_initialized = false;
        this.position__is_initialized = false;
        this.tags__is_initialized = false;
        this.caption__is_initialized = false;
        this.isInFuture__is_initialized = false;
    }

    public RoadEvent(String str, Point point, List<EventTag> list, String str2, boolean z) {
        this.id__is_initialized = false;
        this.position__is_initialized = false;
        this.tags__is_initialized = false;
        this.caption__is_initialized = false;
        this.isInFuture__is_initialized = false;
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"id\" cannot be null");
            throw null;
        }
        if (point == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"position\" cannot be null");
            throw null;
        }
        if (list == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"tags\" cannot be null");
            throw null;
        }
        if (str2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"caption\" cannot be null");
            throw null;
        }
        this.nativeObject = init(str, point, list, str2, z);
        this.f683id = str;
        this.id__is_initialized = true;
        this.position = point;
        this.position__is_initialized = true;
        this.tags = list;
        this.tags__is_initialized = true;
        this.caption = str2;
        this.caption__is_initialized = true;
        this.isInFuture = z;
        this.isInFuture__is_initialized = true;
    }

    private RoadEvent(NativeObject nativeObject) {
        this.id__is_initialized = false;
        this.position__is_initialized = false;
        this.tags__is_initialized = false;
        this.caption__is_initialized = false;
        this.isInFuture__is_initialized = false;
        this.nativeObject = nativeObject;
    }

    public synchronized String getId() {
        try {
            if (!this.id__is_initialized) {
                this.f683id = getId__Native();
                this.id__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f683id;
    }

    public synchronized Point getPosition() {
        try {
            if (!this.position__is_initialized) {
                this.position = getPosition__Native();
                this.position__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.position;
    }

    public synchronized List<EventTag> getTags() {
        try {
            if (!this.tags__is_initialized) {
                this.tags = getTags__Native();
                this.tags__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.tags;
    }

    public synchronized String getCaption() {
        try {
            if (!this.caption__is_initialized) {
                this.caption = getCaption__Native();
                this.caption__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.caption;
    }

    public synchronized boolean getIsInFuture() {
        try {
            if (!this.isInFuture__is_initialized) {
                this.isInFuture = getIsInFuture__Native();
                this.isInFuture__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.isInFuture;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        if (archive.isReader()) {
            this.f683id = archive.add(this.f683id, false);
            this.id__is_initialized = true;
            this.position = (Point) archive.add(this.position, false, (Class<Point>) Point.class);
            this.position__is_initialized = true;
            this.tags = archive.add((List) this.tags, false, (ArchivingHandler) new EnumHandler(EventTag.class));
            this.tags__is_initialized = true;
            this.caption = archive.add(this.caption, false);
            this.caption__is_initialized = true;
            boolean zAdd = archive.add(this.isInFuture);
            this.isInFuture = zAdd;
            this.isInFuture__is_initialized = true;
            this.nativeObject = init(this.f683id, this.position, this.tags, this.caption, zAdd);
            return;
        }
        archive.add(getId(), false);
        archive.add(getPosition(), false, (Class<Point>) Point.class);
        archive.add((List) getTags(), false, (ArchivingHandler) new EnumHandler(EventTag.class));
        archive.add(getCaption(), false);
        archive.add(getIsInFuture());
    }

    public static String getNativeName() {
        return "yandex::maps::mapkit::road_events_layer::RoadEvent";
    }
}
