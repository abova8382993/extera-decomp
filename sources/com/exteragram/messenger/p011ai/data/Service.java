package com.exteragram.messenger.p011ai.data;

import android.text.TextUtils;
import com.exteragram.messenger.p011ai.AiConfig;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
public class Service implements Serializable {

    /* JADX INFO: renamed from: id */
    private String f298id;
    private String key;
    private String model;
    private String url;

    public Service(String str, String str2, String str3) {
        this(UUID.randomUUID().toString(), str, str2, str3);
    }

    public Service(String str, String str2, String str3, String str4) {
        this.f298id = str;
        this.url = str2;
        this.model = str3;
        this.key = str4;
    }

    public String getId() {
        ensureId();
        return this.f298id;
    }

    public void setId(String str) {
        this.f298id = str;
    }

    public boolean ensureId() {
        String str = this.f298id;
        if (str != null && !str.isEmpty()) {
            return false;
        }
        this.f298id = UUID.randomUUID().toString();
        return true;
    }

    public String getUrl() {
        return this.url;
    }

    public String getModel() {
        return this.model;
    }

    public String getShortModel() {
        String str = this.model;
        if (str == null) {
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        String str2 = str.split("/")[r2.length - 1];
        int iIndexOf = str2.indexOf(58);
        return iIndexOf != -1 ? str2.substring(0, iIndexOf) : str2;
    }

    public String getKey() {
        return this.key;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Service service = (Service) obj;
            if (Objects.equals(this.url, service.url) && Objects.equals(this.model, service.model) && Objects.equals(this.key, service.key)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.url, this.model, this.key);
    }

    public int getLegacyHash() {
        return (this.url + this.model + this.key).hashCode();
    }

    public boolean isSelected() {
        String selectedServiceId = AiConfig.getSelectedServiceId();
        if (!TextUtils.isEmpty(selectedServiceId)) {
            return Objects.equals(selectedServiceId, getId());
        }
        return Objects.equals(AiConfig.getSelectedService().getId(), getId());
    }
}
