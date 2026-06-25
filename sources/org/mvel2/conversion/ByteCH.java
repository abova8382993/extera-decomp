package org.mvel2.conversion;

import java.util.HashMap;
import java.util.Map;
import kotlin.coroutines.Continuation;
import org.mvel2.ConversionHandler;

/* JADX INFO: loaded from: classes.dex */
public class ByteCH implements ConversionHandler {
    private static final Map<Class, Converter> CNV;
    private static Converter stringConverter;

    static {
        HashMap map = new HashMap();
        CNV = map;
        Converter converter = new Converter() { // from class: org.mvel2.conversion.ByteCH.1
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Byte.valueOf(Byte.parseByte((String) obj));
            }
        };
        stringConverter = converter;
        map.put(String.class, converter);
        map.put(Object.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.2
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return ByteCH.stringConverter.convert(String.valueOf(obj));
            }
        });
        map.put(Byte.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.3
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return new Byte(((Byte) obj).byteValue());
            }
        });
        map.put(Integer.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.4
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Byte.valueOf(((Integer) obj).byteValue());
            }
        });
        map.put(Long.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.5
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Byte.valueOf(((Long) obj).byteValue());
            }
        });
        map.put(Double.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.6
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Byte.valueOf(((Double) obj).byteValue());
            }
        });
        map.put(Float.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.7
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Byte.valueOf(((Float) obj).byteValue());
            }
        });
        map.put(Short.class, new Converter() { // from class: org.mvel2.conversion.ByteCH.8
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Byte.valueOf(((Short) obj).byteValue());
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
            ByteCH$$ExternalSyntheticBUOutline0.m1014m(obj.getClass().getName(), Integer.class.getName());
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
