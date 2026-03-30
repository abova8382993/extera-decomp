package org.telegram.p026ui.Stories.recorder;

import android.graphics.RectF;
import android.text.TextUtils;
import java.util.ArrayList;
import org.telegram.messenger.BuildVars;

/* JADX INFO: loaded from: classes6.dex */
public class CollageLayout {
    private static ArrayList layouts;
    public final int[] columns;

    /* JADX INFO: renamed from: h */
    public final int f2136h;
    public final ArrayList parts = new ArrayList();
    private final String src;

    /* JADX INFO: renamed from: w */
    public final int f2137w;

    public static ArrayList getLayouts() {
        if (layouts == null) {
            ArrayList arrayList = new ArrayList();
            layouts = arrayList;
            arrayList.add(new CollageLayout("./."));
            layouts.add(new CollageLayout(".."));
            layouts.add(new CollageLayout("../."));
            layouts.add(new CollageLayout("./.."));
            layouts.add(new CollageLayout("././."));
            layouts.add(new CollageLayout("..."));
            layouts.add(new CollageLayout("../.."));
            layouts.add(new CollageLayout("./../.."));
            layouts.add(new CollageLayout("../../."));
            layouts.add(new CollageLayout("../../.."));
            if (BuildVars.DEBUG_PRIVATE_VERSION) {
                layouts.add(new CollageLayout("../../../.."));
                layouts.add(new CollageLayout(".../.../..."));
                layouts.add(new CollageLayout("..../..../...."));
                layouts.add(new CollageLayout(".../.../.../..."));
            }
        }
        return layouts;
    }

    /* JADX INFO: renamed from: of */
    public static CollageLayout m1273of(int i) {
        ArrayList layouts2 = getLayouts();
        int size = layouts2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = layouts2.get(i2);
            i2++;
            CollageLayout collageLayout = (CollageLayout) obj;
            if (collageLayout.parts.size() >= i) {
                return collageLayout;
            }
        }
        return null;
    }

    public static int getMaxCount() {
        ArrayList layouts2 = getLayouts();
        int size = layouts2.size();
        int iMax = 0;
        int i = 0;
        while (i < size) {
            Object obj = layouts2.get(i);
            i++;
            iMax = Math.max(iMax, ((CollageLayout) obj).parts.size());
        }
        return iMax;
    }

    public CollageLayout(String str) {
        str = str == null ? "." : str;
        this.src = str;
        String[] strArrSplit = str.split("/");
        int length = strArrSplit.length;
        this.f2136h = length;
        this.columns = new int[length];
        int iMax = 0;
        for (int i = 0; i < strArrSplit.length; i++) {
            this.columns[i] = strArrSplit[i].length();
            iMax = Math.max(iMax, strArrSplit[i].length());
        }
        this.f2137w = iMax;
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            for (int i3 = 0; i3 < strArrSplit[i2].length(); i3++) {
                this.parts.add(new Part(i3, i2));
            }
        }
    }

    public CollageLayout delete(int i) {
        if (i < 0 || i >= this.parts.size()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(this.parts);
        arrayList.remove(i);
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            Part part = (Part) arrayList.get(i3);
            if (part.f2139y != i2) {
                sb.append("/");
                i2 = part.f2139y;
            }
            sb.append(".");
        }
        return new CollageLayout(sb.toString());
    }

    public static class Part {
        public final CollageLayout layout;

        /* JADX INFO: renamed from: x */
        public final int f2138x;

        /* JADX INFO: renamed from: y */
        public final int f2139y;

        private Part(CollageLayout collageLayout, int i, int i2) {
            this.layout = collageLayout;
            this.f2138x = i;
            this.f2139y = i2;
        }

        /* JADX INFO: renamed from: l */
        public final float m1276l(float f) {
            return (f / this.layout.columns[this.f2139y]) * this.f2138x;
        }

        /* JADX INFO: renamed from: t */
        public final float m1278t(float f) {
            return (f / this.layout.f2136h) * this.f2139y;
        }

        /* JADX INFO: renamed from: r */
        public final float m1277r(float f) {
            return (f / this.layout.columns[this.f2139y]) * (this.f2138x + 1);
        }

        /* JADX INFO: renamed from: b */
        public final float m1274b(float f) {
            return (f / this.layout.f2136h) * (this.f2139y + 1);
        }

        /* JADX INFO: renamed from: w */
        public final float m1279w(float f) {
            return f / this.layout.columns[this.f2139y];
        }

        /* JADX INFO: renamed from: h */
        public final float m1275h(float f) {
            return f / this.layout.f2136h;
        }

        public final void bounds(RectF rectF, float f, float f2) {
            rectF.set(m1276l(f), m1278t(f2), m1277r(f), m1274b(f2));
        }
    }

    public String toString() {
        return this.src;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CollageLayout) {
            return TextUtils.equals(this.src, ((CollageLayout) obj).src);
        }
        return false;
    }
}
