package com.yandex.mapkit.transport.masstransit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class PaymentMedia implements Serializable {

    /* JADX INFO: renamed from: id */
    private String f701id;
    private String name;
    private MediaType type;

    public enum MediaType {
        UNKNOWN,
        CASH,
        PAPER_TICKET,
        TRANSIT_CARD,
        CONTACTLESS_PAYMENT_DEVICE,
        MOBILE_APPLICATION
    }

    public PaymentMedia(String str, String str2, MediaType mediaType) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"id\" cannot be null");
            throw null;
        }
        if (mediaType == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"type\" cannot be null");
            throw null;
        }
        this.f701id = str;
        this.name = str2;
        this.type = mediaType;
    }

    public PaymentMedia() {
    }

    public String getId() {
        return this.f701id;
    }

    public String getName() {
        return this.name;
    }

    public MediaType getType() {
        return this.type;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f701id = archive.add(this.f701id, false);
        this.name = archive.add(this.name, true);
        this.type = (MediaType) archive.add(this.type, false, (Class<MediaType>) MediaType.class);
    }
}
