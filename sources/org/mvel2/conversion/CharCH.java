package org.mvel2.conversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import kotlin.coroutines.Continuation;
import org.mvel2.ConversionException;
import org.mvel2.ConversionHandler;

/* JADX INFO: loaded from: classes.dex */
public class CharCH implements ConversionHandler {
    private static final Map<Class, Converter> CNV;
    private static final Converter stringConverter;

    static {
        HashMap map = new HashMap();
        CNV = map;
        Converter converter = new Converter() { // from class: org.mvel2.conversion.CharCH.1
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                String str = (String) obj;
                if (str.length() > 1) {
                    throw new ConversionException("cannot convert a string with a length greater than 1 to java.lang.Character");
                }
                return Character.valueOf(str.charAt(0));
            }
        };
        stringConverter = converter;
        map.put(String.class, converter);
        map.put(Object.class, new Converter() { // from class: org.mvel2.conversion.CharCH.2
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return CharCH.stringConverter.convert(String.valueOf(obj));
            }
        });
        map.put(Character.class, new Converter() { // from class: org.mvel2.conversion.CharCH.3
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return new Character(((Character) obj).charValue());
            }
        });
        map.put(BigDecimal.class, new Converter() { // from class: org.mvel2.conversion.CharCH.4
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Character.valueOf((char) ((BigDecimal) obj).intValue());
            }
        });
        map.put(Integer.class, new Converter() { // from class: org.mvel2.conversion.CharCH.5
            @Override // org.mvel2.conversion.Converter
            public Object convert(Object obj) {
                return Character.valueOf((char) ((Integer) obj).intValue());
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
