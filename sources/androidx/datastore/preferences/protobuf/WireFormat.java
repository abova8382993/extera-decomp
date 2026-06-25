package androidx.datastore.preferences.protobuf;

import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
public abstract class WireFormat {
    static final int MESSAGE_SET_ITEM_TAG = makeTag(1, 3);
    static final int MESSAGE_SET_ITEM_END_TAG = makeTag(1, 4);
    static final int MESSAGE_SET_TYPE_ID_TAG = makeTag(2, 0);
    static final int MESSAGE_SET_MESSAGE_TAG = makeTag(3, 2);

    public static int getTagFieldNumber(int i) {
        return i >>> 3;
    }

    public static int getTagWireType(int i) {
        return i & 7;
    }

    public static int makeTag(int i, int i2) {
        return (i << 3) | i2;
    }

    public enum JavaType {
        INT(0),
        LONG(0L),
        FLOAT(Float.valueOf(0.0f)),
        DOUBLE(Double.valueOf(0.0d)),
        BOOLEAN(Boolean.FALSE),
        STRING(_UrlKt.FRAGMENT_ENCODE_SET),
        BYTE_STRING(ByteString.EMPTY),
        ENUM(null),
        MESSAGE(null);

        private final Object defaultDefault;

        JavaType(Object obj) {
            this.defaultDefault = obj;
        }
    }

    public static class FieldType extends Enum<FieldType> {
        private static final /* synthetic */ FieldType[] $VALUES;
        public static final FieldType BOOL;
        public static final FieldType BYTES;
        public static final FieldType DOUBLE;
        public static final FieldType ENUM;
        public static final FieldType FIXED32;
        public static final FieldType FIXED64;
        public static final FieldType FLOAT;
        public static final FieldType GROUP;
        public static final FieldType INT32;
        public static final FieldType INT64;
        public static final FieldType MESSAGE;
        public static final FieldType SFIXED32;
        public static final FieldType SFIXED64;
        public static final FieldType SINT32;
        public static final FieldType SINT64;
        public static final FieldType STRING;
        public static final FieldType UINT32;
        public static final FieldType UINT64;
        private final JavaType javaType;
        private final int wireType;

        public /* synthetic */ FieldType(String str, int i, JavaType javaType, int i2, C05761 c05761) {
            this(str, i, javaType, i2);
        }

        public static FieldType valueOf(String str) {
            return (FieldType) Enum.valueOf(FieldType.class, str);
        }

        public static FieldType[] values() {
            return (FieldType[]) $VALUES.clone();
        }

        static {
            FieldType fieldType = new FieldType("DOUBLE", 0, JavaType.DOUBLE, 1);
            DOUBLE = fieldType;
            FieldType fieldType2 = new FieldType("FLOAT", 1, JavaType.FLOAT, 5);
            FLOAT = fieldType2;
            JavaType javaType = JavaType.LONG;
            FieldType fieldType3 = new FieldType("INT64", 2, javaType, 0);
            INT64 = fieldType3;
            FieldType fieldType4 = new FieldType("UINT64", 3, javaType, 0);
            UINT64 = fieldType4;
            JavaType javaType2 = JavaType.INT;
            FieldType fieldType5 = new FieldType("INT32", 4, javaType2, 0);
            INT32 = fieldType5;
            FieldType fieldType6 = new FieldType("FIXED64", 5, javaType, 1);
            FIXED64 = fieldType6;
            FieldType fieldType7 = new FieldType("FIXED32", 6, javaType2, 5);
            FIXED32 = fieldType7;
            FieldType fieldType8 = new FieldType("BOOL", 7, JavaType.BOOLEAN, 0);
            BOOL = fieldType8;
            C05771 c05771 = new FieldType("STRING", 8, JavaType.STRING, 2) { // from class: androidx.datastore.preferences.protobuf.WireFormat.FieldType.1
                public C05771(String str, int i, JavaType javaType3, int i2) {
                    super(str, i, javaType3, i2);
                }
            };
            STRING = c05771;
            JavaType javaType3 = JavaType.MESSAGE;
            C05782 c05782 = new FieldType("GROUP", 9, javaType3, 3) { // from class: androidx.datastore.preferences.protobuf.WireFormat.FieldType.2
                public C05782(String str, int i, JavaType javaType32, int i2) {
                    super(str, i, javaType32, i2);
                }
            };
            GROUP = c05782;
            C05793 c05793 = new FieldType("MESSAGE", 10, javaType32, 2) { // from class: androidx.datastore.preferences.protobuf.WireFormat.FieldType.3
                public C05793(String str, int i, JavaType javaType32, int i2) {
                    super(str, i, javaType32, i2);
                }
            };
            MESSAGE = c05793;
            C05804 c05804 = new FieldType("BYTES", 11, JavaType.BYTE_STRING, 2) { // from class: androidx.datastore.preferences.protobuf.WireFormat.FieldType.4
                public C05804(String str, int i, JavaType javaType4, int i2) {
                    super(str, i, javaType4, i2);
                }
            };
            BYTES = c05804;
            FieldType fieldType9 = new FieldType("UINT32", 12, javaType2, 0);
            UINT32 = fieldType9;
            FieldType fieldType10 = new FieldType("ENUM", 13, JavaType.ENUM, 0);
            ENUM = fieldType10;
            FieldType fieldType11 = new FieldType("SFIXED32", 14, javaType2, 5);
            SFIXED32 = fieldType11;
            FieldType fieldType12 = new FieldType("SFIXED64", 15, javaType, 1);
            SFIXED64 = fieldType12;
            FieldType fieldType13 = new FieldType("SINT32", 16, javaType2, 0);
            SINT32 = fieldType13;
            FieldType fieldType14 = new FieldType("SINT64", 17, javaType, 0);
            SINT64 = fieldType14;
            $VALUES = new FieldType[]{fieldType, fieldType2, fieldType3, fieldType4, fieldType5, fieldType6, fieldType7, fieldType8, c05771, c05782, c05793, c05804, fieldType9, fieldType10, fieldType11, fieldType12, fieldType13, fieldType14};
        }

        /* JADX INFO: renamed from: androidx.datastore.preferences.protobuf.WireFormat$FieldType$1 */
        public enum C05771 extends FieldType {
            public C05771(String str, int i, JavaType javaType3, int i2) {
                super(str, i, javaType3, i2);
            }
        }

        /* JADX INFO: renamed from: androidx.datastore.preferences.protobuf.WireFormat$FieldType$2 */
        public enum C05782 extends FieldType {
            public C05782(String str, int i, JavaType javaType32, int i2) {
                super(str, i, javaType32, i2);
            }
        }

        /* JADX INFO: renamed from: androidx.datastore.preferences.protobuf.WireFormat$FieldType$3 */
        public enum C05793 extends FieldType {
            public C05793(String str, int i, JavaType javaType32, int i2) {
                super(str, i, javaType32, i2);
            }
        }

        /* JADX INFO: renamed from: androidx.datastore.preferences.protobuf.WireFormat$FieldType$4 */
        public enum C05804 extends FieldType {
            public C05804(String str, int i, JavaType javaType4, int i2) {
                super(str, i, javaType4, i2);
            }
        }

        private FieldType(String str, int i, JavaType javaType, int i2) {
            super(str, i);
            this.javaType = javaType;
            this.wireType = i2;
        }

        public JavaType getJavaType() {
            return this.javaType;
        }

        public int getWireType() {
            return this.wireType;
        }
    }
}
