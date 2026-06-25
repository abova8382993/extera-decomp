package org.mvel2.conversion;

import java.util.HashMap;
import java.util.Map;
import kotlin.coroutines.Continuation;
import org.mvel2.ConversionHandler;

/* JADX INFO: loaded from: classes.dex */
public class CharArrayCH implements ConversionHandler {
    private static final Map<Class, Converter> CNV;

    static {
        HashMap map = new HashMap();
        CNV = map;
        map.put(String.class, new Converter() { // from class: org.mvel2.conversion.CharArrayCH.1
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return ((String) obj).toCharArray();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Class, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r0v1, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.mvel2.ConversionHandler
    public Object convertFrom(Object obj) {
        Map<Class, Converter> map = CNV;
        if (map.probeCoroutineSuspended((Continuation<?>) obj.getClass()) == 0) {
            ByteCH$$ExternalSyntheticBUOutline0.m1014m(obj.getClass().getName(), Boolean.class.getName());
            return null;
        }
        return map.get(obj.getClass()).convert(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [boolean, void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.mvel2.ConversionHandler
    public boolean canConvertFrom(Class cls) {
        return CNV.probeCoroutineSuspended((Continuation<?>) cls);
    }
}
