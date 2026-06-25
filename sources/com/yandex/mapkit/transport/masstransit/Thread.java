package com.yandex.mapkit.transport.masstransit;

import com.yandex.mapkit.BaseMetadata;
import com.yandex.runtime.NativeObject;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.ArchivingHandler;
import com.yandex.runtime.bindings.ClassHandler;
import com.yandex.runtime.bindings.Serializable;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Thread implements BaseMetadata, Serializable {
    private String description;
    private boolean description__is_initialized;
    private List<Stop> essentialStops;
    private boolean essentialStops__is_initialized;

    /* JADX INFO: renamed from: id */
    private String f704id;
    private boolean id__is_initialized;
    private NativeObject nativeObject;

    private native String getDescription__Native();

    private native List<Stop> getEssentialStops__Native();

    private native String getId__Native();

    private native NativeObject init(String str, List<Stop> list, String str2);

    public Thread() {
        this.id__is_initialized = false;
        this.essentialStops__is_initialized = false;
        this.description__is_initialized = false;
    }

    public Thread(String str, List<Stop> list, String str2) {
        this.id__is_initialized = false;
        this.essentialStops__is_initialized = false;
        this.description__is_initialized = false;
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"id\" cannot be null");
            throw null;
        }
        if (list == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"essentialStops\" cannot be null");
            throw null;
        }
        this.nativeObject = init(str, list, str2);
        this.f704id = str;
        this.id__is_initialized = true;
        this.essentialStops = list;
        this.essentialStops__is_initialized = true;
        this.description = str2;
        this.description__is_initialized = true;
    }

    private Thread(NativeObject nativeObject) {
        this.id__is_initialized = false;
        this.essentialStops__is_initialized = false;
        this.description__is_initialized = false;
        this.nativeObject = nativeObject;
    }

    public synchronized String getId() {
        try {
            if (!this.id__is_initialized) {
                this.f704id = getId__Native();
                this.id__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f704id;
    }

    public synchronized List<Stop> getEssentialStops() {
        try {
            if (!this.essentialStops__is_initialized) {
                this.essentialStops = getEssentialStops__Native();
                this.essentialStops__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.essentialStops;
    }

    public synchronized String getDescription() {
        try {
            if (!this.description__is_initialized) {
                this.description = getDescription__Native();
                this.description__is_initialized = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.description;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        if (archive.isReader()) {
            this.f704id = archive.add(this.f704id, false);
            this.id__is_initialized = true;
            this.essentialStops = archive.add((List) this.essentialStops, false, (ArchivingHandler) new ClassHandler(Stop.class));
            this.essentialStops__is_initialized = true;
            String strAdd = archive.add(this.description, true);
            this.description = strAdd;
            this.description__is_initialized = true;
            this.nativeObject = init(this.f704id, this.essentialStops, strAdd);
            return;
        }
        archive.add(getId(), false);
        archive.add((List) getEssentialStops(), false, (ArchivingHandler) new ClassHandler(Stop.class));
        archive.add(getDescription(), true);
    }

    public static String getNativeName() {
        return "yandex::maps::mapkit::transport::masstransit::Thread";
    }
}
