package org.mvel2.conversion;

import java.util.HashMap;
import java.util.Map;
import kotlin.coroutines.Continuation;
import org.mvel2.ConversionHandler;

/* JADX INFO: loaded from: classes.dex */
public class StringArrayCH implements ConversionHandler {
    private static final Map<Class, Converter> CNV;

    static {
        HashMap map = new HashMap();
        CNV = map;
        map.put(Object[].class, new Converter() { // from class: org.mvel2.conversion.StringArrayCH.1
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                Object[] objArr = (Object[]) obj;
                String[] strArr = new String[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    strArr[i] = String.valueOf(objArr[i]);
                }
                return strArr;
            }
        });
    }

    @Override // org.mvel2.ConversionHandler
    public Object convertFrom(Object obj) {
        if (obj.getClass().isArray()) {
            Object[] objArr = (Object[]) obj;
            String[] strArr = new String[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                strArr[i] = String.valueOf(objArr[i]);
            }
            return strArr;
        }
        return new String[]{String.valueOf(obj)};
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [boolean, void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.mvel2.ConversionHandler
    public boolean canConvertFrom(Class cls) {
        return CNV.probeCoroutineSuspended((Continuation<?>) cls);
    }
}
