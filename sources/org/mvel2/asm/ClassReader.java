package org.mvel2.asm;

import com.android.p006dx.p007cf.attrib.AttAnnotationDefault;
import com.android.p006dx.p007cf.attrib.AttBootstrapMethods;
import com.android.p006dx.p007cf.attrib.AttCode;
import com.android.p006dx.p007cf.attrib.AttConstantValue;
import com.android.p006dx.p007cf.attrib.AttDeprecated;
import com.android.p006dx.p007cf.attrib.AttEnclosingMethod;
import com.android.p006dx.p007cf.attrib.AttExceptions;
import com.android.p006dx.p007cf.attrib.AttInnerClasses;
import com.android.p006dx.p007cf.attrib.AttRuntimeInvisibleAnnotations;
import com.android.p006dx.p007cf.attrib.AttRuntimeInvisibleParameterAnnotations;
import com.android.p006dx.p007cf.attrib.AttRuntimeVisibleAnnotations;
import com.android.p006dx.p007cf.attrib.AttRuntimeVisibleParameterAnnotations;
import com.android.p006dx.p007cf.attrib.AttSignature;
import com.android.p006dx.p007cf.attrib.AttSourceDebugExtension;
import com.android.p006dx.p007cf.attrib.AttSourceFile;
import com.android.p006dx.p007cf.attrib.AttSynthetic;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import okio.Segment$$ExternalSyntheticBUOutline0;
import org.vosk.Model$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes5.dex */
public class ClassReader {
    static final int EXPAND_ASM_INSNS = 256;
    public static final int EXPAND_FRAMES = 8;
    private static final int INPUT_STREAM_DATA_CHUNK_SIZE = 4096;
    private static final int MAX_BUFFER_SIZE = 1048576;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;

    /* JADX INFO: renamed from: b */
    @Deprecated
    public final byte[] f1057b;
    private final int[] bootstrapMethodOffsets;
    final byte[] classFileBuffer;
    private final ConstantDynamic[] constantDynamicValues;
    private final String[] constantUtf8Values;
    private final int[] cpInfoOffsets;
    public final int header;
    private final int maxStringLength;

    public void readBytecodeInstructionOffset(int i) {
    }

    public ClassReader(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public ClassReader(byte[] bArr, int i, int i2) {
        this(bArr, i, true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0052 A[PHI: r8
  0x0052: PHI (r8v3 int) = (r8v0 int), (r8v1 int), (r8v4 int) binds: [B:52:0x0042, B:62:0x005e, B:58:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ClassReader(byte[] r11, int r12, boolean r13) {
        /*
            r10 = this;
            r10.<init>()
            r10.classFileBuffer = r11
            r10.f1057b = r11
            if (r13 == 0) goto L1f
            int r13 = r12 + 6
            short r0 = r10.readShort(r13)
            r1 = 65
            if (r0 > r1) goto L14
            goto L1f
        L14:
            java.lang.String r11 = "Unsupported class file major version "
            short r10 = r10.readShort(r13)
            com.sun.jna.IntegerType$$ExternalSyntheticBUOutline0.m547m(r11, r10)
            r10 = 0
            throw r10
        L1f:
            int r13 = r12 + 8
            int r13 = r10.readUnsignedShort(r13)
            int[] r0 = new int[r13]
            r10.cpInfoOffsets = r0
            java.lang.String[] r0 = new java.lang.String[r13]
            r10.constantUtf8Values = r0
            int r12 = r12 + 10
            r0 = 0
            r1 = 1
            r2 = r0
            r3 = r2
            r4 = r1
        L34:
            if (r4 >= r13) goto L64
            int[] r5 = r10.cpInfoOffsets
            int r6 = r4 + 1
            int r7 = r12 + 1
            r5[r4] = r7
            r5 = r11[r12]
            r8 = 3
            r9 = 5
            switch(r5) {
                case 1: goto L59;
                case 2: goto L45;
                case 3: goto L4b;
                case 4: goto L4b;
                case 5: goto L54;
                case 6: goto L54;
                case 7: goto L52;
                case 8: goto L52;
                case 9: goto L4b;
                case 10: goto L4b;
                case 11: goto L4b;
                case 12: goto L4b;
                case 13: goto L45;
                case 14: goto L45;
                case 15: goto L51;
                case 16: goto L52;
                case 17: goto L4e;
                case 18: goto L4a;
                case 19: goto L52;
                case 20: goto L52;
                default: goto L45;
            }
        L45:
            okio.Segment$$ExternalSyntheticBUOutline0.m991m()
            r10 = 0
            throw r10
        L4a:
            r3 = r1
        L4b:
            r4 = r6
            r8 = r9
            goto L62
        L4e:
            r2 = r1
            r3 = r2
            goto L4b
        L51:
            r8 = 4
        L52:
            r4 = r6
            goto L62
        L54:
            int r4 = r4 + 2
            r8 = 9
            goto L62
        L59:
            int r4 = r10.readUnsignedShort(r7)
            int r8 = r8 + r4
            if (r8 <= r0) goto L52
            r4 = r6
            r0 = r8
        L62:
            int r12 = r12 + r8
            goto L34
        L64:
            r10.maxStringLength = r0
            r10.header = r12
            r11 = 0
            if (r2 == 0) goto L6e
            org.mvel2.asm.ConstantDynamic[] r12 = new org.mvel2.asm.ConstantDynamic[r13]
            goto L6f
        L6e:
            r12 = r11
        L6f:
            r10.constantDynamicValues = r12
            if (r3 == 0) goto L77
            int[] r11 = r10.readBootstrapMethodsAttribute(r0)
        L77:
            r10.bootstrapMethodOffsets = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.asm.ClassReader.<init>(byte[], int, boolean):void");
    }

    public ClassReader(InputStream inputStream) {
        this(readStream(inputStream, false));
    }

    public ClassReader(String str) {
        this(readStream(ClassLoader.getSystemResourceAsStream(str.replace('.', '/') + ".class"), true));
    }

    private static byte[] readStream(InputStream inputStream, boolean z) throws IOException {
        if (inputStream == null) {
            Model$$ExternalSyntheticBUOutline0.m1247m("Class not found");
            return null;
        }
        int iComputeBufferSize = computeBufferSize(inputStream);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[iComputeBufferSize];
                int i = 0;
                while (true) {
                    int i2 = inputStream.read(bArr, 0, iComputeBufferSize);
                    if (i2 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                    i++;
                }
                byteArrayOutputStream.flush();
                if (i != 1) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    if (z) {
                        inputStream.close();
                    }
                    return byteArray;
                }
                byteArrayOutputStream.close();
                return bArr;
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable unused) {
                }
                throw th;
            }
        } finally {
            if (z) {
                inputStream.close();
            }
        }
    }

    private static int computeBufferSize(InputStream inputStream) throws IOException {
        int iAvailable = inputStream.available();
        if (iAvailable < 256) {
            return 4096;
        }
        return Math.min(iAvailable, 1048576);
    }

    public int getAccess() {
        return readUnsignedShort(this.header);
    }

    public String getClassName() {
        return readClass(this.header + 2, new char[this.maxStringLength]);
    }

    public String getSuperName() {
        return readClass(this.header + 4, new char[this.maxStringLength]);
    }

    public String[] getInterfaces() {
        int i = this.header + 6;
        int unsignedShort = readUnsignedShort(i);
        String[] strArr = new String[unsignedShort];
        if (unsignedShort > 0) {
            char[] cArr = new char[this.maxStringLength];
            for (int i2 = 0; i2 < unsignedShort; i2++) {
                i += 2;
                strArr[i2] = readClass(i, cArr);
            }
        }
        return strArr;
    }

    public void accept(ClassVisitor classVisitor, int i) {
        accept(classVisitor, new Attribute[0], i);
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attributeArr, int i) {
        Context context;
        ClassReader classReader;
        Context context2;
        String str;
        int i2;
        int i3;
        String utf8;
        int i4;
        String str2;
        String str3;
        int i5;
        Context context3 = new Context();
        context3.attributePrototypes = attributeArr;
        context3.parsingOptions = i;
        char[] cArr = new char[this.maxStringLength];
        context3.charBuffer = cArr;
        int i6 = this.header;
        int unsignedShort = readUnsignedShort(i6);
        String str4 = readClass(i6 + 2, cArr);
        String str5 = readClass(i6 + 4, cArr);
        int unsignedShort2 = readUnsignedShort(i6 + 6);
        String[] strArr = new String[unsignedShort2];
        int i7 = i6 + 8;
        for (int i8 = 0; i8 < unsignedShort2; i8++) {
            strArr[i8] = readClass(i7, cArr);
            i7 += 2;
        }
        int firstAttributeOffset = getFirstAttributeOffset();
        int unsignedShort3 = readUnsignedShort(firstAttributeOffset - 2);
        String str6 = null;
        String utf = null;
        String str7 = null;
        int i9 = 0;
        int i10 = 0;
        String utf82 = null;
        int i11 = 0;
        int i12 = 0;
        String str8 = null;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        Attribute attribute = null;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        while (unsignedShort3 > 0) {
            int i20 = firstAttributeOffset;
            String utf83 = readUTF8(i20, cArr);
            int i21 = readInt(i20 + 2);
            String str9 = str6;
            int i22 = i20 + 6;
            String str10 = utf;
            if (AttSourceFile.ATTRIBUTE_NAME.equals(utf83)) {
                utf8 = readUTF8(i22, cArr);
                i5 = unsignedShort;
                i3 = i22;
                str2 = str4;
                utf = str10;
                i4 = i21;
                context2 = context3;
            } else {
                if (AttInnerClasses.ATTRIBUTE_NAME.equals(utf83)) {
                    i5 = unsignedShort;
                    i3 = i22;
                    i18 = i3;
                } else if (AttEnclosingMethod.ATTRIBUTE_NAME.equals(utf83)) {
                    i5 = unsignedShort;
                    i3 = i22;
                    i10 = i3;
                } else {
                    if ("NestHost".equals(utf83)) {
                        str7 = readClass(i22, cArr);
                    } else if ("NestMembers".equals(utf83)) {
                        i5 = unsignedShort;
                        i3 = i22;
                        i16 = i3;
                    } else if ("PermittedSubclasses".equals(utf83)) {
                        i5 = unsignedShort;
                        i3 = i22;
                        i17 = i3;
                    } else if (AttSignature.ATTRIBUTE_NAME.equals(utf83)) {
                        utf82 = readUTF8(i22, cArr);
                    } else if (AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME.equals(utf83)) {
                        i5 = unsignedShort;
                        i3 = i22;
                        i9 = i3;
                    } else if ("RuntimeVisibleTypeAnnotations".equals(utf83)) {
                        i5 = unsignedShort;
                        i3 = i22;
                        i14 = i3;
                    } else {
                        if (AttDeprecated.ATTRIBUTE_NAME.equals(utf83)) {
                            i5 = 131072 | unsignedShort;
                        } else if (AttSynthetic.ATTRIBUTE_NAME.equals(utf83)) {
                            i5 = unsignedShort | 4096;
                        } else if (AttSourceDebugExtension.ATTRIBUTE_NAME.equals(utf83)) {
                            if (i21 > this.classFileBuffer.length - i22) {
                                Segment$$ExternalSyntheticBUOutline0.m991m();
                                return;
                            }
                            utf = readUtf(i22, i21, new char[i21]);
                            i5 = unsignedShort;
                            i3 = i22;
                            context2 = context3;
                            utf8 = str9;
                            i4 = i21;
                            str2 = str4;
                        } else if (AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME.equals(utf83)) {
                            i5 = unsignedShort;
                            i3 = i22;
                            i13 = i3;
                        } else if ("RuntimeInvisibleTypeAnnotations".equals(utf83)) {
                            i5 = unsignedShort;
                            i3 = i22;
                            i15 = i3;
                        } else if ("Record".equals(utf83)) {
                            i5 = 65536 | unsignedShort;
                            i3 = i22;
                            i19 = i3;
                        } else if ("Module".equals(utf83)) {
                            i5 = unsignedShort;
                            i3 = i22;
                            i11 = i3;
                        } else if ("ModuleMainClass".equals(utf83)) {
                            str8 = readClass(i22, cArr);
                        } else if ("ModulePackages".equals(utf83)) {
                            i5 = unsignedShort;
                            i3 = i22;
                            i12 = i3;
                        } else {
                            if (AttBootstrapMethods.ATTRIBUTE_NAME.equals(utf83)) {
                                context2 = context3;
                                str = str10;
                                i2 = unsignedShort;
                                i3 = i22;
                                utf8 = str9;
                                i4 = i21;
                                str2 = str4;
                                str3 = str7;
                            } else {
                                context2 = context3;
                                str = str10;
                                utf8 = str9;
                                str2 = str4;
                                str3 = str7;
                                i2 = unsignedShort;
                                i3 = i22;
                                i4 = i21;
                                Attribute attribute2 = readAttribute(attributeArr, utf83, i3, i4, cArr, -1, null);
                                attribute2.nextAttribute = attribute;
                                attribute = attribute2;
                            }
                            utf = str;
                            str7 = str3;
                            i5 = i2;
                        }
                        i3 = i22;
                    }
                    i5 = unsignedShort;
                    i3 = i22;
                }
                utf = str10;
                utf8 = str9;
                i4 = i21;
                context2 = context3;
                str2 = str4;
            }
            int i23 = i3 + i4;
            unsignedShort3--;
            unsignedShort = i5;
            str6 = utf8;
            context3 = context2;
            str4 = str2;
            firstAttributeOffset = i23;
        }
        String str11 = str6;
        Context context4 = context3;
        String str12 = str4;
        String str13 = utf;
        String str14 = str7;
        Attribute attribute3 = attribute;
        classVisitor.visit(readInt(this.cpInfoOffsets[1] - 7), unsignedShort, str12, utf82, str5, strArr);
        if ((i & 2) == 0 && (str11 != null || str13 != null)) {
            classVisitor.visitSource(str11, str13);
        }
        if (i11 != 0) {
            context = context4;
            classReader = this;
            classReader.readModuleAttributes(classVisitor, context, i11, i12, str8);
        } else {
            context = context4;
            classReader = this;
        }
        if (str14 != null) {
            classVisitor.visitNestHost(str14);
        }
        if (i10 != 0) {
            String str15 = classReader.readClass(i10, cArr);
            int unsignedShort4 = classReader.readUnsignedShort(i10 + 2);
            classVisitor.visitOuterClass(str15, unsignedShort4 == 0 ? null : classReader.readUTF8(classReader.cpInfoOffsets[unsignedShort4], cArr), unsignedShort4 == 0 ? null : classReader.readUTF8(classReader.cpInfoOffsets[unsignedShort4] + 2, cArr));
        }
        if (i9 != 0) {
            int unsignedShort5 = classReader.readUnsignedShort(i9);
            int elementValues = i9 + 2;
            while (true) {
                int i24 = unsignedShort5 - 1;
                if (unsignedShort5 <= 0) {
                    break;
                }
                elementValues = classReader.readElementValues(classVisitor.visitAnnotation(classReader.readUTF8(elementValues, cArr), true), elementValues + 2, true, cArr);
                unsignedShort5 = i24;
            }
        }
        int i25 = i13;
        if (i25 != 0) {
            int unsignedShort6 = classReader.readUnsignedShort(i25);
            int elementValues2 = i25 + 2;
            while (true) {
                int i26 = unsignedShort6 - 1;
                if (unsignedShort6 <= 0) {
                    break;
                }
                elementValues2 = classReader.readElementValues(classVisitor.visitAnnotation(classReader.readUTF8(elementValues2, cArr), false), elementValues2 + 2, true, cArr);
                unsignedShort6 = i26;
            }
        }
        int i27 = i14;
        if (i27 != 0) {
            int unsignedShort7 = classReader.readUnsignedShort(i27);
            int elementValues3 = i27 + 2;
            while (true) {
                int i28 = unsignedShort7 - 1;
                if (unsignedShort7 <= 0) {
                    break;
                }
                int typeAnnotationTarget = classReader.readTypeAnnotationTarget(context, elementValues3);
                elementValues3 = classReader.readElementValues(classVisitor.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, classReader.readUTF8(typeAnnotationTarget, cArr), true), typeAnnotationTarget + 2, true, cArr);
                unsignedShort7 = i28;
            }
        }
        int i29 = i15;
        if (i29 != 0) {
            int unsignedShort8 = classReader.readUnsignedShort(i29);
            int elementValues4 = i29 + 2;
            while (true) {
                int i30 = unsignedShort8 - 1;
                if (unsignedShort8 <= 0) {
                    break;
                }
                int typeAnnotationTarget2 = classReader.readTypeAnnotationTarget(context, elementValues4);
                elementValues4 = classReader.readElementValues(classVisitor.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, classReader.readUTF8(typeAnnotationTarget2, cArr), false), typeAnnotationTarget2 + 2, true, cArr);
                unsignedShort8 = i30;
            }
        }
        while (attribute3 != null) {
            Attribute attribute4 = attribute3.nextAttribute;
            attribute3.nextAttribute = null;
            classVisitor.visitAttribute(attribute3);
            attribute3 = attribute4;
        }
        int i31 = i16;
        if (i31 != 0) {
            int unsignedShort9 = classReader.readUnsignedShort(i31);
            int i32 = i31 + 2;
            while (true) {
                int i33 = unsignedShort9 - 1;
                if (unsignedShort9 <= 0) {
                    break;
                }
                classVisitor.visitNestMember(classReader.readClass(i32, cArr));
                i32 += 2;
                unsignedShort9 = i33;
            }
        }
        int i34 = i17;
        if (i34 != 0) {
            int unsignedShort10 = classReader.readUnsignedShort(i34);
            int i35 = i34 + 2;
            while (true) {
                int i36 = unsignedShort10 - 1;
                if (unsignedShort10 <= 0) {
                    break;
                }
                classVisitor.visitPermittedSubclass(classReader.readClass(i35, cArr));
                i35 += 2;
                unsignedShort10 = i36;
            }
        }
        int i37 = i18;
        if (i37 != 0) {
            int unsignedShort11 = classReader.readUnsignedShort(i37);
            int i38 = i37 + 2;
            while (true) {
                int i39 = unsignedShort11 - 1;
                if (unsignedShort11 <= 0) {
                    break;
                }
                classVisitor.visitInnerClass(classReader.readClass(i38, cArr), classReader.readClass(i38 + 2, cArr), classReader.readUTF8(i38 + 4, cArr), classReader.readUnsignedShort(i38 + 6));
                i38 += 8;
                unsignedShort11 = i39;
            }
        }
        int i40 = i19;
        if (i40 != 0) {
            int unsignedShort12 = classReader.readUnsignedShort(i40);
            int recordComponent = i40 + 2;
            while (true) {
                int i41 = unsignedShort12 - 1;
                if (unsignedShort12 <= 0) {
                    break;
                }
                recordComponent = classReader.readRecordComponent(classVisitor, context, recordComponent);
                unsignedShort12 = i41;
            }
        }
        int unsignedShort13 = classReader.readUnsignedShort(i7);
        int field = i7 + 2;
        while (true) {
            int i42 = unsignedShort13 - 1;
            if (unsignedShort13 <= 0) {
                break;
            }
            field = classReader.readField(classVisitor, context, field);
            unsignedShort13 = i42;
        }
        int unsignedShort14 = classReader.readUnsignedShort(field);
        int method = field + 2;
        while (true) {
            int i43 = unsignedShort14 - 1;
            if (unsignedShort14 > 0) {
                method = classReader.readMethod(classVisitor, context, method);
                unsignedShort14 = i43;
            } else {
                classVisitor.visitEnd();
                return;
            }
        }
    }

    private void readModuleAttributes(ClassVisitor classVisitor, Context context, int i, int i2, String str) {
        String[] strArr;
        char[] cArr = context.charBuffer;
        int i3 = i + 6;
        ModuleVisitor moduleVisitorVisitModule = classVisitor.visitModule(readModule(i, cArr), readUnsignedShort(i + 2), readUTF8(i + 4, cArr));
        if (moduleVisitorVisitModule == null) {
            return;
        }
        if (str != null) {
            moduleVisitorVisitModule.visitMainClass(str);
        }
        if (i2 != 0) {
            int unsignedShort = readUnsignedShort(i2);
            int i4 = i2 + 2;
            while (true) {
                int i5 = unsignedShort - 1;
                if (unsignedShort <= 0) {
                    break;
                }
                moduleVisitorVisitModule.visitPackage(readPackage(i4, cArr));
                i4 += 2;
                unsignedShort = i5;
            }
        }
        int unsignedShort2 = readUnsignedShort(i3);
        int i6 = i + 8;
        while (true) {
            int i7 = unsignedShort2 - 1;
            if (unsignedShort2 <= 0) {
                break;
            }
            String module = readModule(i6, cArr);
            int unsignedShort3 = readUnsignedShort(i6 + 2);
            String utf8 = readUTF8(i6 + 4, cArr);
            i6 += 6;
            moduleVisitorVisitModule.visitRequire(module, unsignedShort3, utf8);
            unsignedShort2 = i7;
        }
        int unsignedShort4 = readUnsignedShort(i6);
        int i8 = i6 + 2;
        while (true) {
            int i9 = unsignedShort4 - 1;
            String[] strArr2 = null;
            if (unsignedShort4 <= 0) {
                break;
            }
            String str2 = readPackage(i8, cArr);
            int unsignedShort5 = readUnsignedShort(i8 + 2);
            int unsignedShort6 = readUnsignedShort(i8 + 4);
            i8 += 6;
            if (unsignedShort6 != 0) {
                strArr2 = new String[unsignedShort6];
                for (int i10 = 0; i10 < unsignedShort6; i10++) {
                    strArr2[i10] = readModule(i8, cArr);
                    i8 += 2;
                }
            }
            moduleVisitorVisitModule.visitExport(str2, unsignedShort5, strArr2);
            unsignedShort4 = i9;
        }
        int unsignedShort7 = readUnsignedShort(i8);
        int i11 = i8 + 2;
        while (true) {
            int i12 = unsignedShort7 - 1;
            if (unsignedShort7 <= 0) {
                break;
            }
            String str3 = readPackage(i11, cArr);
            int unsignedShort8 = readUnsignedShort(i11 + 2);
            int unsignedShort9 = readUnsignedShort(i11 + 4);
            i11 += 6;
            if (unsignedShort9 != 0) {
                strArr = new String[unsignedShort9];
                for (int i13 = 0; i13 < unsignedShort9; i13++) {
                    strArr[i13] = readModule(i11, cArr);
                    i11 += 2;
                }
            } else {
                strArr = null;
            }
            moduleVisitorVisitModule.visitOpen(str3, unsignedShort8, strArr);
            unsignedShort7 = i12;
        }
        int unsignedShort10 = readUnsignedShort(i11);
        int i14 = i11 + 2;
        while (true) {
            int i15 = unsignedShort10 - 1;
            if (unsignedShort10 <= 0) {
                break;
            }
            moduleVisitorVisitModule.visitUse(readClass(i14, cArr));
            i14 += 2;
            unsignedShort10 = i15;
        }
        int unsignedShort11 = readUnsignedShort(i14);
        int i16 = i14 + 2;
        while (true) {
            int i17 = unsignedShort11 - 1;
            if (unsignedShort11 > 0) {
                String str4 = readClass(i16, cArr);
                int unsignedShort12 = readUnsignedShort(i16 + 2);
                i16 += 4;
                String[] strArr3 = new String[unsignedShort12];
                for (int i18 = 0; i18 < unsignedShort12; i18++) {
                    strArr3[i18] = readClass(i16, cArr);
                    i16 += 2;
                }
                moduleVisitorVisitModule.visitProvide(str4, strArr3);
                unsignedShort11 = i17;
            } else {
                moduleVisitorVisitModule.visitEnd();
                return;
            }
        }
    }

    private int readRecordComponent(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        int i3;
        Attribute attribute;
        char[] cArr = context.charBuffer;
        String utf8 = readUTF8(i, cArr);
        String utf82 = readUTF8(i + 2, cArr);
        int unsignedShort = readUnsignedShort(i + 4);
        int i4 = i + 6;
        int i5 = 0;
        Attribute attribute2 = null;
        int i6 = 0;
        String utf83 = null;
        int i7 = 0;
        int i8 = 0;
        while (true) {
            int i9 = unsignedShort - 1;
            if (unsignedShort <= 0) {
                break;
            }
            String utf84 = readUTF8(i4, cArr);
            int i10 = readInt(i4 + 2);
            int i11 = i4 + 6;
            if (AttSignature.ATTRIBUTE_NAME.equals(utf84)) {
                utf83 = readUTF8(i11, cArr);
                int i12 = i5;
                i2 = i11;
                i11 = i12;
            } else {
                if (AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME.equals(utf84)) {
                    i7 = i11;
                    attribute = attribute2;
                    i3 = i10;
                    i11 = i5;
                    i2 = i7;
                } else if ("RuntimeVisibleTypeAnnotations".equals(utf84)) {
                    i2 = i11;
                } else if (AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME.equals(utf84)) {
                    i8 = i11;
                    attribute = attribute2;
                    i3 = i10;
                    i11 = i5;
                    i2 = i8;
                } else if ("RuntimeInvisibleTypeAnnotations".equals(utf84)) {
                    i6 = i11;
                    attribute = attribute2;
                    i3 = i10;
                    i11 = i5;
                    i2 = i6;
                } else {
                    int i13 = i5;
                    i2 = i11;
                    Attribute attribute3 = attribute2;
                    i3 = i10;
                    Attribute attribute4 = readAttribute(context.attributePrototypes, utf84, i2, i3, cArr, -1, null);
                    attribute4.nextAttribute = attribute3;
                    attribute = attribute4;
                    i11 = i13;
                    i6 = i6;
                }
                int i14 = i2 + i3;
                i5 = i11;
                i4 = i14;
                attribute2 = attribute;
                unsignedShort = i9;
            }
            attribute = attribute2;
            i3 = i10;
            int i142 = i2 + i3;
            i5 = i11;
            i4 = i142;
            attribute2 = attribute;
            unsignedShort = i9;
        }
        int i15 = i5;
        Attribute attribute5 = attribute2;
        int i16 = i6;
        RecordComponentVisitor recordComponentVisitorVisitRecordComponent = classVisitor.visitRecordComponent(utf8, utf82, utf83);
        if (recordComponentVisitorVisitRecordComponent == null) {
            return i4;
        }
        if (i7 != 0) {
            int unsignedShort2 = readUnsignedShort(i7);
            int elementValues = i7 + 2;
            while (true) {
                int i17 = unsignedShort2 - 1;
                if (unsignedShort2 <= 0) {
                    break;
                }
                elementValues = readElementValues(recordComponentVisitorVisitRecordComponent.visitAnnotation(readUTF8(elementValues, cArr), true), elementValues + 2, true, cArr);
                unsignedShort2 = i17;
            }
        }
        if (i8 != 0) {
            int unsignedShort3 = readUnsignedShort(i8);
            int elementValues2 = i8 + 2;
            while (true) {
                int i18 = unsignedShort3 - 1;
                if (unsignedShort3 <= 0) {
                    break;
                }
                elementValues2 = readElementValues(recordComponentVisitorVisitRecordComponent.visitAnnotation(readUTF8(elementValues2, cArr), false), elementValues2 + 2, true, cArr);
                unsignedShort3 = i18;
            }
        }
        if (i15 != 0) {
            int unsignedShort4 = readUnsignedShort(i15);
            int elementValues3 = i15 + 2;
            while (true) {
                int i19 = unsignedShort4 - 1;
                if (unsignedShort4 <= 0) {
                    break;
                }
                int typeAnnotationTarget = readTypeAnnotationTarget(context, elementValues3);
                elementValues3 = readElementValues(recordComponentVisitorVisitRecordComponent.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(typeAnnotationTarget, cArr), true), typeAnnotationTarget + 2, true, cArr);
                unsignedShort4 = i19;
            }
        }
        if (i16 != 0) {
            int unsignedShort5 = readUnsignedShort(i16);
            int elementValues4 = i16 + 2;
            while (true) {
                int i20 = unsignedShort5 - 1;
                if (unsignedShort5 <= 0) {
                    break;
                }
                int typeAnnotationTarget2 = readTypeAnnotationTarget(context, elementValues4);
                elementValues4 = readElementValues(recordComponentVisitorVisitRecordComponent.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(typeAnnotationTarget2, cArr), false), typeAnnotationTarget2 + 2, true, cArr);
                unsignedShort5 = i20;
            }
        }
        Attribute attribute6 = attribute5;
        while (attribute6 != null) {
            Attribute attribute7 = attribute6.nextAttribute;
            attribute6.nextAttribute = null;
            recordComponentVisitorVisitRecordComponent.visitAttribute(attribute6);
            attribute6 = attribute7;
        }
        recordComponentVisitorVisitRecordComponent.visitEnd();
        return i4;
    }

    private int readField(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        int i3;
        int i4;
        Context context2 = context;
        char[] cArr = context2.charBuffer;
        int unsignedShort = readUnsignedShort(i);
        String utf8 = readUTF8(i + 2, cArr);
        String utf82 = readUTF8(i + 4, cArr);
        int unsignedShort2 = readUnsignedShort(i + 6);
        int i5 = i + 8;
        int i6 = unsignedShort;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        String utf83 = null;
        Object obj = null;
        Attribute attribute = null;
        while (true) {
            int i11 = unsignedShort2 - 1;
            if (unsignedShort2 <= 0) {
                break;
            }
            int i12 = i7;
            String utf84 = readUTF8(i5, cArr);
            int i13 = readInt(i5 + 2);
            int i14 = i5 + 6;
            if (AttConstantValue.ATTRIBUTE_NAME.equals(utf84)) {
                int unsignedShort3 = readUnsignedShort(i14);
                obj = unsignedShort3 == 0 ? null : readConst(unsignedShort3, cArr);
            } else if (AttSignature.ATTRIBUTE_NAME.equals(utf84)) {
                utf83 = readUTF8(i14, cArr);
            } else {
                if (AttDeprecated.ATTRIBUTE_NAME.equals(utf84)) {
                    i4 = 131072 | i6;
                } else if (AttSynthetic.ATTRIBUTE_NAME.equals(utf84)) {
                    i4 = i6 | 4096;
                } else {
                    if (AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME.equals(utf84)) {
                        i7 = i14;
                        i2 = i7;
                        i14 = i8;
                        i3 = i13;
                    } else {
                        if ("RuntimeVisibleTypeAnnotations".equals(utf84)) {
                            i2 = i14;
                            i9 = i2;
                        } else if (AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME.equals(utf84)) {
                            i2 = i14;
                            i3 = i13;
                            i7 = i12;
                        } else if ("RuntimeInvisibleTypeAnnotations".equals(utf84)) {
                            i2 = i14;
                            i10 = i2;
                        } else {
                            i2 = i14;
                            int i15 = i8;
                            i3 = i13;
                            Attribute attribute2 = readAttribute(context2.attributePrototypes, utf84, i2, i3, cArr, -1, null);
                            attribute2.nextAttribute = attribute;
                            attribute = attribute2;
                            i9 = i9;
                            i14 = i15;
                            i7 = i12;
                            i10 = i10;
                        }
                        i14 = i8;
                        i3 = i13;
                        i7 = i12;
                    }
                    int i16 = i2 + i3;
                    context2 = context;
                    i8 = i14;
                    i5 = i16;
                    unsignedShort2 = i11;
                }
                i2 = i14;
                i6 = i4;
                i14 = i8;
                i3 = i13;
                i7 = i12;
                int i162 = i2 + i3;
                context2 = context;
                i8 = i14;
                i5 = i162;
                unsignedShort2 = i11;
            }
            i2 = i14;
            i14 = i8;
            i3 = i13;
            i7 = i12;
            int i1622 = i2 + i3;
            context2 = context;
            i8 = i14;
            i5 = i1622;
            unsignedShort2 = i11;
        }
        int i17 = i7;
        int i18 = i8;
        int i19 = i9;
        int i20 = i10;
        FieldVisitor fieldVisitorVisitField = classVisitor.visitField(i6, utf8, utf82, utf83, obj);
        if (fieldVisitorVisitField == null) {
            return i5;
        }
        if (i17 != 0) {
            int unsignedShort4 = readUnsignedShort(i17);
            int elementValues = i17 + 2;
            while (true) {
                int i21 = unsignedShort4 - 1;
                if (unsignedShort4 <= 0) {
                    break;
                }
                elementValues = readElementValues(fieldVisitorVisitField.visitAnnotation(readUTF8(elementValues, cArr), true), elementValues + 2, true, cArr);
                unsignedShort4 = i21;
            }
        }
        if (i18 != 0) {
            int unsignedShort5 = readUnsignedShort(i18);
            int elementValues2 = i18 + 2;
            while (true) {
                int i22 = unsignedShort5 - 1;
                if (unsignedShort5 <= 0) {
                    break;
                }
                elementValues2 = readElementValues(fieldVisitorVisitField.visitAnnotation(readUTF8(elementValues2, cArr), false), elementValues2 + 2, true, cArr);
                unsignedShort5 = i22;
            }
        }
        if (i19 != 0) {
            int unsignedShort6 = readUnsignedShort(i19);
            int elementValues3 = i19 + 2;
            while (true) {
                int i23 = unsignedShort6 - 1;
                if (unsignedShort6 <= 0) {
                    break;
                }
                int typeAnnotationTarget = readTypeAnnotationTarget(context, elementValues3);
                elementValues3 = readElementValues(fieldVisitorVisitField.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(typeAnnotationTarget, cArr), true), typeAnnotationTarget + 2, true, cArr);
                unsignedShort6 = i23;
            }
        }
        if (i20 != 0) {
            int unsignedShort7 = readUnsignedShort(i20);
            int elementValues4 = i20 + 2;
            while (true) {
                int i24 = unsignedShort7 - 1;
                if (unsignedShort7 <= 0) {
                    break;
                }
                int typeAnnotationTarget2 = readTypeAnnotationTarget(context, elementValues4);
                elementValues4 = readElementValues(fieldVisitorVisitField.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, readUTF8(typeAnnotationTarget2, cArr), false), typeAnnotationTarget2 + 2, true, cArr);
                unsignedShort7 = i24;
            }
        }
        while (attribute != null) {
            Attribute attribute3 = attribute.nextAttribute;
            attribute.nextAttribute = null;
            fieldVisitorVisitField.visitAttribute(attribute);
            attribute = attribute3;
        }
        fieldVisitorVisitField.visitEnd();
        return i5;
    }

    private int readMethod(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        char[] cArr;
        int i3;
        int i4;
        int i5;
        char[] cArr2;
        ClassReader classReader = this;
        char[] cArr3 = context.charBuffer;
        context.currentMethodAccessFlags = classReader.readUnsignedShort(i);
        context.currentMethodName = classReader.readUTF8(i + 2, cArr3);
        int i6 = i + 4;
        context.currentMethodDescriptor = classReader.readUTF8(i6, cArr3);
        int unsignedShort = classReader.readUnsignedShort(i + 6);
        int i7 = i + 8;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        Attribute attribute = null;
        int unsignedShort2 = 0;
        int i12 = 0;
        int i13 = 0;
        String[] strArr = null;
        boolean z = false;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        while (true) {
            int i18 = unsignedShort - 1;
            if (unsignedShort <= 0) {
                break;
            }
            int i19 = i8;
            String utf8 = classReader.readUTF8(i7, cArr3);
            int i20 = classReader.readInt(i7 + 2);
            int i21 = i9;
            int i22 = i7 + 6;
            int i23 = i19;
            if (AttCode.ATTRIBUTE_NAME.equals(utf8)) {
                if ((context.parsingOptions & 1) == 0) {
                    i17 = i22;
                }
            } else if (AttExceptions.ATTRIBUTE_NAME.equals(utf8)) {
                int unsignedShort3 = classReader.readUnsignedShort(i22);
                int i24 = i7 + 8;
                strArr = new String[unsignedShort3];
                for (int i25 = 0; i25 < unsignedShort3; i25++) {
                    strArr[i25] = classReader.readClass(i24, cArr3);
                    i24 += 2;
                }
                i14 = i22;
            } else if (AttSignature.ATTRIBUTE_NAME.equals(utf8)) {
                unsignedShort2 = classReader.readUnsignedShort(i22);
            } else if (AttDeprecated.ATTRIBUTE_NAME.equals(utf8)) {
                context.currentMethodAccessFlags |= 131072;
            } else if (AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME.equals(utf8)) {
                i23 = i22;
            } else if ("RuntimeVisibleTypeAnnotations".equals(utf8)) {
                i4 = i22;
                cArr2 = cArr3;
                i5 = i20;
                i7 = i22 + i5;
                cArr3 = cArr2;
                unsignedShort = i18;
                i9 = i21;
                i8 = i23;
                i10 = i4;
            } else if (AttAnnotationDefault.ATTRIBUTE_NAME.equals(utf8)) {
                i13 = i22;
            } else {
                if (AttSynthetic.ATTRIBUTE_NAME.equals(utf8)) {
                    context.currentMethodAccessFlags |= 4096;
                    i4 = i10;
                    cArr2 = cArr3;
                    i5 = i20;
                    z = true;
                } else if (AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME.equals(utf8)) {
                    i21 = i22;
                } else if ("RuntimeInvisibleTypeAnnotations".equals(utf8)) {
                    i11 = i22;
                } else if (AttRuntimeVisibleParameterAnnotations.ATTRIBUTE_NAME.equals(utf8)) {
                    i15 = i22;
                } else if (AttRuntimeInvisibleParameterAnnotations.ATTRIBUTE_NAME.equals(utf8)) {
                    i16 = i22;
                } else if ("MethodParameters".equals(utf8)) {
                    i12 = i22;
                } else {
                    i4 = i10;
                    i5 = i20;
                    Attribute attribute2 = classReader.readAttribute(context.attributePrototypes, utf8, i22, i5, cArr3, -1, null);
                    cArr2 = cArr3;
                    attribute2.nextAttribute = attribute;
                    attribute = attribute2;
                    i11 = i11;
                }
                i7 = i22 + i5;
                cArr3 = cArr2;
                unsignedShort = i18;
                i9 = i21;
                i8 = i23;
                i10 = i4;
            }
            i4 = i10;
            cArr2 = cArr3;
            i5 = i20;
            i7 = i22 + i5;
            cArr3 = cArr2;
            unsignedShort = i18;
            i9 = i21;
            i8 = i23;
            i10 = i4;
        }
        int i26 = i8;
        int i27 = i9;
        int i28 = i10;
        char[] cArr4 = cArr3;
        int i29 = i11;
        int i30 = i13;
        int i31 = i12;
        MethodVisitor methodVisitorVisitMethod = classVisitor.visitMethod(context.currentMethodAccessFlags, context.currentMethodName, context.currentMethodDescriptor, unsignedShort2 == 0 ? null : classReader.readUtf(unsignedShort2, cArr4), strArr);
        if (methodVisitorVisitMethod == null) {
            return i7;
        }
        if (methodVisitorVisitMethod instanceof MethodWriter) {
            MethodWriter methodWriter = (MethodWriter) methodVisitorVisitMethod;
            i3 = i30;
            boolean z2 = (context.currentMethodAccessFlags & 131072) != 0;
            int unsignedShort4 = classReader.readUnsignedShort(i6);
            int i32 = unsignedShort2;
            i2 = i31;
            cArr = cArr4;
            boolean zCanCopyMethodAttributes = methodWriter.canCopyMethodAttributes(classReader, z, z2, unsignedShort4, i32, i14);
            classReader = classReader;
            if (zCanCopyMethodAttributes) {
                methodWriter.setMethodAttributesSource(i, i7 - i);
                return i7;
            }
        } else {
            i2 = i31;
            cArr = cArr4;
            i3 = i30;
        }
        if (i2 != 0 && (context.parsingOptions & 2) == 0) {
            int i33 = classReader.readByte(i2);
            int i34 = i2 + 1;
            while (true) {
                int i35 = i33 - 1;
                if (i33 <= 0) {
                    break;
                }
                methodVisitorVisitMethod.visitParameter(classReader.readUTF8(i34, cArr), classReader.readUnsignedShort(i34 + 2));
                i34 += 4;
                i33 = i35;
            }
        }
        if (i3 != 0) {
            AnnotationVisitor annotationVisitorVisitAnnotationDefault = methodVisitorVisitMethod.visitAnnotationDefault();
            classReader.readElementValue(annotationVisitorVisitAnnotationDefault, i3, null, cArr);
            if (annotationVisitorVisitAnnotationDefault != null) {
                annotationVisitorVisitAnnotationDefault.visitEnd();
            }
        }
        if (i26 != 0) {
            int unsignedShort5 = classReader.readUnsignedShort(i26);
            int elementValues = i26 + 2;
            while (true) {
                int i36 = unsignedShort5 - 1;
                if (unsignedShort5 <= 0) {
                    break;
                }
                elementValues = classReader.readElementValues(methodVisitorVisitMethod.visitAnnotation(classReader.readUTF8(elementValues, cArr), true), elementValues + 2, true, cArr);
                unsignedShort5 = i36;
            }
        }
        if (i27 != 0) {
            int unsignedShort6 = classReader.readUnsignedShort(i27);
            int elementValues2 = i27 + 2;
            while (true) {
                int i37 = unsignedShort6 - 1;
                if (unsignedShort6 <= 0) {
                    break;
                }
                elementValues2 = classReader.readElementValues(methodVisitorVisitMethod.visitAnnotation(classReader.readUTF8(elementValues2, cArr), false), elementValues2 + 2, true, cArr);
                unsignedShort6 = i37;
            }
        }
        if (i28 != 0) {
            int unsignedShort7 = classReader.readUnsignedShort(i28);
            int elementValues3 = i28 + 2;
            while (true) {
                int i38 = unsignedShort7 - 1;
                if (unsignedShort7 <= 0) {
                    break;
                }
                int typeAnnotationTarget = classReader.readTypeAnnotationTarget(context, elementValues3);
                elementValues3 = classReader.readElementValues(methodVisitorVisitMethod.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, classReader.readUTF8(typeAnnotationTarget, cArr), true), typeAnnotationTarget + 2, true, cArr);
                unsignedShort7 = i38;
            }
        }
        if (i29 != 0) {
            int unsignedShort8 = classReader.readUnsignedShort(i29);
            int elementValues4 = i29 + 2;
            while (true) {
                int i39 = unsignedShort8 - 1;
                if (unsignedShort8 <= 0) {
                    break;
                }
                int typeAnnotationTarget2 = classReader.readTypeAnnotationTarget(context, elementValues4);
                elementValues4 = classReader.readElementValues(methodVisitorVisitMethod.visitTypeAnnotation(context.currentTypeAnnotationTarget, context.currentTypeAnnotationTargetPath, classReader.readUTF8(typeAnnotationTarget2, cArr), false), typeAnnotationTarget2 + 2, true, cArr);
                unsignedShort8 = i39;
            }
        }
        int i40 = i15;
        if (i40 != 0) {
            classReader.readParameterAnnotations(methodVisitorVisitMethod, context, i40, true);
        }
        int i41 = i16;
        if (i41 != 0) {
            classReader.readParameterAnnotations(methodVisitorVisitMethod, context, i41, false);
        }
        while (attribute != null) {
            Attribute attribute3 = attribute.nextAttribute;
            attribute.nextAttribute = null;
            methodVisitorVisitMethod.visitAttribute(attribute);
            attribute = attribute3;
        }
        int i42 = i17;
        if (i42 != 0) {
            methodVisitorVisitMethod.visitCode();
            classReader.readCode(methodVisitorVisitMethod, context, i42);
        }
        methodVisitorVisitMethod.visitEnd();
        return i7;
    }

    /* JADX WARN: Removed duplicated region for block: B:520:0x03c3  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:530:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:533:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:548:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:551:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:552:0x047a  */
    /* JADX WARN: Removed duplicated region for block: B:558:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:563:0x0520  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0572  */
    /* JADX WARN: Removed duplicated region for block: B:580:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:584:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:585:0x0601  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x0616  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x062b  */
    /* JADX WARN: Removed duplicated region for block: B:589:0x063d  */
    /* JADX WARN: Removed duplicated region for block: B:591:0x0650  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0665  */
    /* JADX WARN: Removed duplicated region for block: B:593:0x0679  */
    /* JADX WARN: Removed duplicated region for block: B:594:0x068a  */
    /* JADX WARN: Removed duplicated region for block: B:595:0x0698  */
    /* JADX WARN: Removed duplicated region for block: B:597:0x06a4  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x06ab  */
    /* JADX WARN: Removed duplicated region for block: B:602:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:606:0x06dc  */
    /* JADX WARN: Removed duplicated region for block: B:712:0x03e7 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readCode(org.mvel2.asm.MethodVisitor r40, org.mvel2.asm.Context r41, int r42) {
        /*
            Method dump skipped, instruction units count: 3118
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.asm.ClassReader.readCode(org.mvel2.asm.MethodVisitor, org.mvel2.asm.Context, int):void");
    }

    public Label readLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            labelArr[i] = new Label();
        }
        return labelArr[i];
    }

    private Label createLabel(int i, Label[] labelArr) {
        Label label = readLabel(i, labelArr);
        label.flags = (short) (label.flags & (-2));
        return label;
    }

    private void createDebugLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            Label label = readLabel(i, labelArr);
            label.flags = (short) (label.flags | 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x004c A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int[] readTypeAnnotations(org.mvel2.asm.MethodVisitor r12, org.mvel2.asm.Context r13, int r14, boolean r15) {
        /*
            r11 = this;
            char[] r0 = r13.charBuffer
            int r1 = r11.readUnsignedShort(r14)
            int[] r2 = new int[r1]
            int r14 = r14 + 2
            r3 = 0
        Lb:
            if (r3 >= r1) goto L82
            r2[r3] = r14
            int r4 = r11.readInt(r14)
            int r5 = r4 >>> 24
            r6 = 23
            r7 = 0
            if (r5 == r6) goto L4c
            switch(r5) {
                case 16: goto L4c;
                case 17: goto L4c;
                case 18: goto L4c;
                default: goto L1d;
            }
        L1d:
            switch(r5) {
                case 64: goto L27;
                case 65: goto L27;
                case 66: goto L4c;
                case 67: goto L4c;
                case 68: goto L4c;
                case 69: goto L4c;
                case 70: goto L4c;
                case 71: goto L24;
                case 72: goto L24;
                case 73: goto L24;
                case 74: goto L24;
                case 75: goto L24;
                default: goto L20;
            }
        L20:
            okio.Segment$$ExternalSyntheticBUOutline0.m991m()
            return r7
        L24:
            int r14 = r14 + 4
            goto L4e
        L27:
            int r6 = r14 + 1
            int r6 = r11.readUnsignedShort(r6)
            int r14 = r14 + 3
        L2f:
            int r8 = r6 + (-1)
            if (r6 <= 0) goto L4e
            int r6 = r11.readUnsignedShort(r14)
            int r9 = r14 + 2
            int r9 = r11.readUnsignedShort(r9)
            int r14 = r14 + 6
            org.mvel2.asm.Label[] r10 = r13.currentMethodLabels
            r11.createLabel(r6, r10)
            int r6 = r6 + r9
            org.mvel2.asm.Label[] r9 = r13.currentMethodLabels
            r11.createLabel(r6, r9)
            r6 = r8
            goto L2f
        L4c:
            int r14 = r14 + 3
        L4e:
            int r6 = r11.readByte(r14)
            r8 = 66
            r9 = 1
            if (r5 != r8) goto L76
            if (r6 != 0) goto L5a
            goto L61
        L5a:
            org.mvel2.asm.TypePath r7 = new org.mvel2.asm.TypePath
            byte[] r5 = r11.classFileBuffer
            r7.<init>(r5, r14)
        L61:
            int r6 = r6 * 2
            int r6 = r6 + r9
            int r14 = r14 + r6
            java.lang.String r5 = r11.readUTF8(r14, r0)
            int r14 = r14 + 2
            r4 = r4 & (-256(0xffffffffffffff00, float:NaN))
            org.mvel2.asm.AnnotationVisitor r4 = r12.visitTryCatchAnnotation(r4, r7, r5, r15)
            int r14 = r11.readElementValues(r4, r14, r9, r0)
            goto L7f
        L76:
            int r6 = r6 * 2
            int r6 = r6 + 3
            int r14 = r14 + r6
            int r14 = r11.readElementValues(r7, r14, r9, r0)
        L7f:
            int r3 = r3 + 1
            goto Lb
        L82:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.asm.ClassReader.readTypeAnnotations(org.mvel2.asm.MethodVisitor, org.mvel2.asm.Context, int, boolean):int[]");
    }

    private int getTypeAnnotationBytecodeOffset(int[] iArr, int i) {
        if (iArr == null || i >= iArr.length || readByte(iArr[i]) < 67) {
            return -1;
        }
        return readUnsignedShort(iArr[i] + 1);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:45:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readTypeAnnotationTarget(org.mvel2.asm.Context r10, int r11) {
        /*
            r9 = this;
            int r0 = r9.readInt(r11)
            int r1 = r0 >>> 24
            r2 = 1
            if (r1 == 0) goto L6e
            if (r1 == r2) goto L6e
            r3 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            switch(r1) {
                case 16: goto L6b;
                case 17: goto L6b;
                case 18: goto L6b;
                case 19: goto L68;
                case 20: goto L68;
                case 21: goto L68;
                case 22: goto L6e;
                case 23: goto L6b;
                default: goto L10;
            }
        L10:
            r4 = 0
            switch(r1) {
                case 64: goto L23;
                case 65: goto L23;
                case 66: goto L6b;
                case 67: goto L1f;
                case 68: goto L1f;
                case 69: goto L1f;
                case 70: goto L1f;
                case 71: goto L18;
                case 72: goto L18;
                case 73: goto L18;
                case 74: goto L18;
                case 75: goto L18;
                default: goto L14;
            }
        L14:
            okio.Segment$$ExternalSyntheticBUOutline0.m991m()
            return r4
        L18:
            r1 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            r0 = r0 & r1
            int r11 = r11 + 4
            goto L73
        L1f:
            r0 = r0 & r3
        L20:
            int r11 = r11 + 3
            goto L73
        L23:
            r0 = r0 & r3
            int r1 = r11 + 1
            int r1 = r9.readUnsignedShort(r1)
            int r11 = r11 + 3
            org.mvel2.asm.Label[] r3 = new org.mvel2.asm.Label[r1]
            r10.currentLocalVariableAnnotationRangeStarts = r3
            org.mvel2.asm.Label[] r3 = new org.mvel2.asm.Label[r1]
            r10.currentLocalVariableAnnotationRangeEnds = r3
            int[] r3 = new int[r1]
            r10.currentLocalVariableAnnotationRangeIndices = r3
        L38:
            if (r4 >= r1) goto L73
            int r3 = r9.readUnsignedShort(r11)
            int r5 = r11 + 2
            int r5 = r9.readUnsignedShort(r5)
            int r6 = r11 + 4
            int r6 = r9.readUnsignedShort(r6)
            int r11 = r11 + 6
            org.mvel2.asm.Label[] r7 = r10.currentLocalVariableAnnotationRangeStarts
            org.mvel2.asm.Label[] r8 = r10.currentMethodLabels
            org.mvel2.asm.Label r8 = r9.createLabel(r3, r8)
            r7[r4] = r8
            org.mvel2.asm.Label[] r7 = r10.currentLocalVariableAnnotationRangeEnds
            int r3 = r3 + r5
            org.mvel2.asm.Label[] r5 = r10.currentMethodLabels
            org.mvel2.asm.Label r3 = r9.createLabel(r3, r5)
            r7[r4] = r3
            int[] r3 = r10.currentLocalVariableAnnotationRangeIndices
            r3[r4] = r6
            int r4 = r4 + 1
            goto L38
        L68:
            r0 = r0 & r3
            int r11 = r11 + r2
            goto L73
        L6b:
            r0 = r0 & (-256(0xffffffffffffff00, float:NaN))
            goto L20
        L6e:
            r1 = -65536(0xffffffffffff0000, float:NaN)
            r0 = r0 & r1
            int r11 = r11 + 2
        L73:
            r10.currentTypeAnnotationTarget = r0
            int r0 = r9.readByte(r11)
            if (r0 != 0) goto L7d
            r9 = 0
            goto L85
        L7d:
            org.mvel2.asm.TypePath r1 = new org.mvel2.asm.TypePath
            byte[] r9 = r9.classFileBuffer
            r1.<init>(r9, r11)
            r9 = r1
        L85:
            r10.currentTypeAnnotationTargetPath = r9
            int r11 = r11 + r2
            int r0 = r0 * 2
            int r11 = r11 + r0
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.asm.ClassReader.readTypeAnnotationTarget(org.mvel2.asm.Context, int):int");
    }

    private void readParameterAnnotations(MethodVisitor methodVisitor, Context context, int i, boolean z) {
        int elementValues = i + 1;
        int i2 = this.classFileBuffer[i] & UByte.MAX_VALUE;
        methodVisitor.visitAnnotableParameterCount(i2, z);
        char[] cArr = context.charBuffer;
        for (int i3 = 0; i3 < i2; i3++) {
            int unsignedShort = readUnsignedShort(elementValues);
            elementValues += 2;
            while (true) {
                int i4 = unsignedShort - 1;
                if (unsignedShort > 0) {
                    elementValues = readElementValues(methodVisitor.visitParameterAnnotation(i3, readUTF8(elementValues, cArr), z), elementValues + 2, true, cArr);
                    unsignedShort = i4;
                }
            }
        }
    }

    private int readElementValues(AnnotationVisitor annotationVisitor, int i, boolean z, char[] cArr) {
        int unsignedShort = readUnsignedShort(i);
        int elementValue = i + 2;
        if (!z) {
            while (true) {
                int i2 = unsignedShort - 1;
                if (unsignedShort <= 0) {
                    break;
                }
                elementValue = readElementValue(annotationVisitor, elementValue, null, cArr);
                unsignedShort = i2;
            }
        } else {
            while (true) {
                int i3 = unsignedShort - 1;
                if (unsignedShort <= 0) {
                    break;
                }
                elementValue = readElementValue(annotationVisitor, elementValue + 2, readUTF8(elementValue, cArr), cArr);
                unsignedShort = i3;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return elementValue;
    }

    private int readElementValue(AnnotationVisitor annotationVisitor, int i, String str, char[] cArr) {
        Object obj;
        byte[] bArr = this.classFileBuffer;
        int i2 = 0;
        if (annotationVisitor == null) {
            int i3 = bArr[i] & UByte.MAX_VALUE;
            if (i3 == 64) {
                return readElementValues(null, i + 3, true, cArr);
            }
            if (i3 != 91) {
                return i3 != 101 ? i + 3 : i + 5;
            }
            return readElementValues(null, i + 1, false, cArr);
        }
        int i4 = i + 1;
        int i5 = bArr[i] & UByte.MAX_VALUE;
        if (i5 != 64) {
            if (i5 != 70) {
                if (i5 == 83) {
                    annotationVisitor.visit(str, Short.valueOf((short) readInt(this.cpInfoOffsets[readUnsignedShort(i4)])));
                    return i + 3;
                }
                if (i5 == 99) {
                    annotationVisitor.visit(str, Type.getType(readUTF8(i4, cArr)));
                    return i + 3;
                }
                if (i5 == 101) {
                    annotationVisitor.visitEnum(str, readUTF8(i4, cArr), readUTF8(i + 3, cArr));
                    return i + 5;
                }
                if (i5 == 115) {
                    annotationVisitor.visit(str, readUTF8(i4, cArr));
                    return i + 3;
                }
                if (i5 != 73 && i5 != 74) {
                    if (i5 == 90) {
                        if (readInt(this.cpInfoOffsets[readUnsignedShort(i4)]) == 0) {
                            obj = Boolean.FALSE;
                        } else {
                            obj = Boolean.TRUE;
                        }
                        annotationVisitor.visit(str, obj);
                        return i + 3;
                    }
                    if (i5 != 91) {
                        switch (i5) {
                            case 66:
                                annotationVisitor.visit(str, Byte.valueOf((byte) readInt(this.cpInfoOffsets[readUnsignedShort(i4)])));
                                return i + 3;
                            case 67:
                                annotationVisitor.visit(str, Character.valueOf((char) readInt(this.cpInfoOffsets[readUnsignedShort(i4)])));
                                return i + 3;
                            case 68:
                                break;
                            default:
                                Segment$$ExternalSyntheticBUOutline0.m991m();
                                return 0;
                        }
                    } else {
                        int unsignedShort = readUnsignedShort(i4);
                        int i6 = i + 3;
                        if (unsignedShort == 0) {
                            return readElementValues(annotationVisitor.visitArray(str), i + 1, false, cArr);
                        }
                        int i7 = this.classFileBuffer[i6] & UByte.MAX_VALUE;
                        if (i7 == 70) {
                            float[] fArr = new float[unsignedShort];
                            while (i2 < unsignedShort) {
                                fArr[i2] = Float.intBitsToFloat(readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]));
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, fArr);
                            return i6;
                        }
                        if (i7 == 83) {
                            short[] sArr = new short[unsignedShort];
                            while (i2 < unsignedShort) {
                                sArr[i2] = (short) readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, sArr);
                            return i6;
                        }
                        if (i7 == 90) {
                            boolean[] zArr = new boolean[unsignedShort];
                            for (int i8 = 0; i8 < unsignedShort; i8++) {
                                zArr[i8] = readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]) != 0;
                                i6 += 3;
                            }
                            annotationVisitor.visit(str, zArr);
                            return i6;
                        }
                        if (i7 == 73) {
                            int[] iArr = new int[unsignedShort];
                            while (i2 < unsignedShort) {
                                iArr[i2] = readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                i6 += 3;
                                i2++;
                            }
                            annotationVisitor.visit(str, iArr);
                            return i6;
                        }
                        if (i7 != 74) {
                            switch (i7) {
                                case 66:
                                    byte[] bArr2 = new byte[unsignedShort];
                                    while (i2 < unsignedShort) {
                                        bArr2[i2] = (byte) readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                        i6 += 3;
                                        i2++;
                                    }
                                    annotationVisitor.visit(str, bArr2);
                                    return i6;
                                case 67:
                                    char[] cArr2 = new char[unsignedShort];
                                    while (i2 < unsignedShort) {
                                        cArr2[i2] = (char) readInt(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                                        i6 += 3;
                                        i2++;
                                    }
                                    annotationVisitor.visit(str, cArr2);
                                    return i6;
                                case 68:
                                    double[] dArr = new double[unsignedShort];
                                    while (i2 < unsignedShort) {
                                        dArr[i2] = Double.longBitsToDouble(readLong(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]));
                                        i6 += 3;
                                        i2++;
                                    }
                                    annotationVisitor.visit(str, dArr);
                                    return i6;
                                default:
                                    return readElementValues(annotationVisitor.visitArray(str), i + 1, false, cArr);
                            }
                        }
                        long[] jArr = new long[unsignedShort];
                        while (i2 < unsignedShort) {
                            jArr[i2] = readLong(this.cpInfoOffsets[readUnsignedShort(i6 + 1)]);
                            i6 += 3;
                            i2++;
                        }
                        annotationVisitor.visit(str, jArr);
                        return i6;
                    }
                }
            }
            annotationVisitor.visit(str, readConst(readUnsignedShort(i4), cArr));
            return i + 3;
        }
        return readElementValues(annotationVisitor.visitAnnotation(str, readUTF8(i4, cArr)), i + 3, true, cArr);
    }

    private void computeImplicitFrame(Context context) {
        int i;
        String str = context.currentMethodDescriptor;
        Object[] objArr = context.currentFrameLocalTypes;
        int i2 = 0;
        if ((context.currentMethodAccessFlags & 8) == 0) {
            if ("<init>".equals(context.currentMethodName)) {
                objArr[0] = Opcodes.UNINITIALIZED_THIS;
            } else {
                objArr[0] = readClass(this.header + 2, context.charBuffer);
            }
            i2 = 1;
        }
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt = str.charAt(i3);
            if (cCharAt == 'F') {
                i = i2 + 1;
                objArr[i2] = Opcodes.FLOAT;
            } else if (cCharAt != 'L') {
                if (cCharAt != 'S' && cCharAt != 'I') {
                    if (cCharAt == 'J') {
                        i = i2 + 1;
                        objArr[i2] = Opcodes.LONG;
                    } else if (cCharAt != 'Z') {
                        if (cCharAt != '[') {
                            switch (cCharAt) {
                                case 'B':
                                case 'C':
                                    break;
                                case 'D':
                                    i = i2 + 1;
                                    objArr[i2] = Opcodes.DOUBLE;
                                    break;
                                default:
                                    context.currentFrameLocalCount = i2;
                                    return;
                            }
                        } else {
                            while (str.charAt(i4) == '[') {
                                i4++;
                            }
                            if (str.charAt(i4) == 'L') {
                                do {
                                    i4++;
                                } while (str.charAt(i4) != ';');
                            }
                            int i5 = i4 + 1;
                            objArr[i2] = str.substring(i3, i5);
                            i3 = i5;
                            i2++;
                        }
                    }
                }
                i = i2 + 1;
                objArr[i2] = Opcodes.INTEGER;
            } else {
                int i6 = i4;
                while (str.charAt(i6) != ';') {
                    i6++;
                }
                objArr[i2] = str.substring(i4, i6);
                i2++;
                i3 = i6 + 1;
            }
            i2 = i;
            i3 = i4;
        }
    }

    private int readStackMapFrame(int i, boolean z, boolean z2, Context context) {
        int verificationTypeInfo;
        int i2;
        ClassReader classReader;
        char[] cArr = context.charBuffer;
        Label[] labelArr = context.currentMethodLabels;
        if (z) {
            verificationTypeInfo = i + 1;
            i2 = this.classFileBuffer[i] & UByte.MAX_VALUE;
        } else {
            context.currentFrameOffset = -1;
            verificationTypeInfo = i;
            i2 = 255;
        }
        context.currentFrameLocalCountDelta = 0;
        if (i2 < 64) {
            context.currentFrameType = 3;
            context.currentFrameStackCount = 0;
            classReader = this;
        } else if (i2 < 128) {
            i2 -= 64;
            classReader = this;
            verificationTypeInfo = classReader.readVerificationTypeInfo(verificationTypeInfo, context.currentFrameStackTypes, 0, cArr, labelArr);
            context.currentFrameType = 4;
            context.currentFrameStackCount = 1;
        } else if (i2 >= 247) {
            int unsignedShort = readUnsignedShort(verificationTypeInfo);
            int i3 = verificationTypeInfo;
            verificationTypeInfo = i3 + 2;
            if (i2 == 247) {
                classReader = this;
                verificationTypeInfo = classReader.readVerificationTypeInfo(verificationTypeInfo, context.currentFrameStackTypes, 0, cArr, labelArr);
                context.currentFrameType = 4;
                context.currentFrameStackCount = 1;
            } else {
                if (i2 >= 248 && i2 < 251) {
                    context.currentFrameType = 2;
                    int i4 = 251 - i2;
                    context.currentFrameLocalCountDelta = i4;
                    context.currentFrameLocalCount -= i4;
                    context.currentFrameStackCount = 0;
                } else if (i2 == 251) {
                    context.currentFrameType = 3;
                    context.currentFrameStackCount = 0;
                } else if (i2 < 255) {
                    int i5 = i2 - 251;
                    int i6 = z2 ? context.currentFrameLocalCount : 0;
                    int i7 = i5;
                    while (i7 > 0) {
                        verificationTypeInfo = readVerificationTypeInfo(verificationTypeInfo, context.currentFrameLocalTypes, i6, cArr, labelArr);
                        i7--;
                        i6++;
                    }
                    classReader = this;
                    context.currentFrameType = 1;
                    context.currentFrameLocalCountDelta = i5;
                    context.currentFrameLocalCount += i5;
                    context.currentFrameStackCount = 0;
                } else {
                    classReader = this;
                    int unsignedShort2 = classReader.readUnsignedShort(verificationTypeInfo);
                    int verificationTypeInfo2 = i3 + 4;
                    context.currentFrameType = 0;
                    context.currentFrameLocalCountDelta = unsignedShort2;
                    context.currentFrameLocalCount = unsignedShort2;
                    for (int i8 = 0; i8 < unsignedShort2; i8++) {
                        verificationTypeInfo2 = classReader.readVerificationTypeInfo(verificationTypeInfo2, context.currentFrameLocalTypes, i8, cArr, labelArr);
                    }
                    int unsignedShort3 = classReader.readUnsignedShort(verificationTypeInfo2);
                    verificationTypeInfo = verificationTypeInfo2 + 2;
                    context.currentFrameStackCount = unsignedShort3;
                    for (int i9 = 0; i9 < unsignedShort3; i9++) {
                        verificationTypeInfo = classReader.readVerificationTypeInfo(verificationTypeInfo, context.currentFrameStackTypes, i9, cArr, labelArr);
                    }
                }
                classReader = this;
            }
            i2 = unsignedShort;
        } else {
            Segment$$ExternalSyntheticBUOutline0.m991m();
            return 0;
        }
        int i10 = context.currentFrameOffset + i2 + 1;
        context.currentFrameOffset = i10;
        classReader.createLabel(i10, labelArr);
        return verificationTypeInfo;
    }

    private int readVerificationTypeInfo(int i, Object[] objArr, int i2, char[] cArr, Label[] labelArr) {
        int i3 = i + 1;
        switch (this.classFileBuffer[i] & UByte.MAX_VALUE) {
            case 0:
                objArr[i2] = Opcodes.TOP;
                return i3;
            case 1:
                objArr[i2] = Opcodes.INTEGER;
                return i3;
            case 2:
                objArr[i2] = Opcodes.FLOAT;
                return i3;
            case 3:
                objArr[i2] = Opcodes.DOUBLE;
                return i3;
            case 4:
                objArr[i2] = Opcodes.LONG;
                return i3;
            case 5:
                objArr[i2] = Opcodes.NULL;
                return i3;
            case 6:
                objArr[i2] = Opcodes.UNINITIALIZED_THIS;
                return i3;
            case 7:
                objArr[i2] = readClass(i3, cArr);
                break;
            case 8:
                objArr[i2] = createLabel(readUnsignedShort(i3), labelArr);
                break;
            default:
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return 0;
        }
        return i + 3;
    }

    public final int getFirstAttributeOffset() {
        int i = this.header;
        int unsignedShort = i + 8 + (readUnsignedShort(i + 6) * 2);
        int unsignedShort2 = readUnsignedShort(unsignedShort);
        int i2 = unsignedShort + 2;
        while (true) {
            int i3 = unsignedShort2 - 1;
            if (unsignedShort2 <= 0) {
                break;
            }
            int unsignedShort3 = readUnsignedShort(i2 + 6);
            i2 += 8;
            while (true) {
                int i4 = unsignedShort3 - 1;
                if (unsignedShort3 > 0) {
                    i2 += readInt(i2 + 2) + 6;
                    unsignedShort3 = i4;
                }
            }
            unsignedShort2 = i3;
        }
        int unsignedShort4 = readUnsignedShort(i2);
        int i5 = i2 + 2;
        while (true) {
            int i6 = unsignedShort4 - 1;
            if (unsignedShort4 <= 0) {
                return i5 + 2;
            }
            int unsignedShort5 = readUnsignedShort(i5 + 6);
            i5 += 8;
            while (true) {
                int i7 = unsignedShort5 - 1;
                if (unsignedShort5 > 0) {
                    i5 += readInt(i5 + 2) + 6;
                    unsignedShort5 = i7;
                }
            }
            unsignedShort4 = i6;
        }
    }

    private int[] readBootstrapMethodsAttribute(int i) {
        char[] cArr = new char[i];
        int firstAttributeOffset = getFirstAttributeOffset();
        for (int unsignedShort = readUnsignedShort(firstAttributeOffset - 2); unsignedShort > 0; unsignedShort--) {
            String utf8 = readUTF8(firstAttributeOffset, cArr);
            int i2 = readInt(firstAttributeOffset + 2);
            int i3 = firstAttributeOffset + 6;
            if (AttBootstrapMethods.ATTRIBUTE_NAME.equals(utf8)) {
                int unsignedShort2 = readUnsignedShort(i3);
                int[] iArr = new int[unsignedShort2];
                int unsignedShort3 = firstAttributeOffset + 8;
                for (int i4 = 0; i4 < unsignedShort2; i4++) {
                    iArr[i4] = unsignedShort3;
                    unsignedShort3 += (readUnsignedShort(unsignedShort3 + 2) * 2) + 4;
                }
                return iArr;
            }
            firstAttributeOffset = i3 + i2;
        }
        Segment$$ExternalSyntheticBUOutline0.m991m();
        return null;
    }

    private Attribute readAttribute(Attribute[] attributeArr, String str, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        for (Attribute attribute : attributeArr) {
            if (attribute.type.equals(str)) {
                return attribute.read(this, i, i2, cArr, i3, labelArr);
            }
        }
        return new Attribute(str).read(this, i, i2, null, -1, null);
    }

    public int getItemCount() {
        return this.cpInfoOffsets.length;
    }

    public int getItem(int i) {
        return this.cpInfoOffsets[i];
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public int readByte(int i) {
        return this.classFileBuffer[i] & UByte.MAX_VALUE;
    }

    public int readUnsignedShort(int i) {
        byte[] bArr = this.classFileBuffer;
        return (bArr[i + 1] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 8);
    }

    public short readShort(int i) {
        byte[] bArr = this.classFileBuffer;
        return (short) ((bArr[i + 1] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 8));
    }

    public int readInt(int i) {
        byte[] bArr = this.classFileBuffer;
        return (bArr[i + 3] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 24) | ((bArr[i + 1] & UByte.MAX_VALUE) << 16) | ((bArr[i + 2] & UByte.MAX_VALUE) << 8);
    }

    public long readLong(int i) {
        return (((long) readInt(i + 4)) & 4294967295L) | (((long) readInt(i)) << 32);
    }

    public String readUTF8(int i, char[] cArr) {
        int unsignedShort = readUnsignedShort(i);
        if (i == 0 || unsignedShort == 0) {
            return null;
        }
        return readUtf(unsignedShort, cArr);
    }

    public final String readUtf(int i, char[] cArr) {
        String[] strArr = this.constantUtf8Values;
        String str = strArr[i];
        if (str != null) {
            return str;
        }
        int i2 = this.cpInfoOffsets[i];
        String utf = readUtf(i2 + 2, readUnsignedShort(i2), cArr);
        strArr[i] = utf;
        return utf;
    }

    private String readUtf(int i, int i2, char[] cArr) {
        int i3;
        int i4 = i2 + i;
        byte[] bArr = this.classFileBuffer;
        int i5 = 0;
        while (i < i4) {
            int i6 = i + 1;
            byte b2 = bArr[i];
            if ((b2 & ByteCompanionObject.MIN_VALUE) == 0) {
                cArr[i5] = (char) (b2 & ByteCompanionObject.MAX_VALUE);
                i5++;
                i = i6;
            } else {
                if ((b2 & 224) == 192) {
                    i3 = i5 + 1;
                    i += 2;
                    cArr[i5] = (char) (((b2 & 31) << 6) + (bArr[i6] & 63));
                } else {
                    i3 = i5 + 1;
                    int i7 = i + 2;
                    i += 3;
                    cArr[i5] = (char) (((b2 & 15) << 12) + ((bArr[i6] & 63) << 6) + (bArr[i7] & 63));
                }
                i5 = i3;
            }
        }
        return new String(cArr, 0, i5);
    }

    private String readStringish(int i, char[] cArr) {
        return readUTF8(this.cpInfoOffsets[readUnsignedShort(i)], cArr);
    }

    public String readClass(int i, char[] cArr) {
        return readStringish(i, cArr);
    }

    public String readModule(int i, char[] cArr) {
        return readStringish(i, cArr);
    }

    public String readPackage(int i, char[] cArr) {
        return readStringish(i, cArr);
    }

    private ConstantDynamic readConstantDynamic(int i, char[] cArr) {
        ConstantDynamic constantDynamic = this.constantDynamicValues[i];
        if (constantDynamic != null) {
            return constantDynamic;
        }
        int[] iArr = this.cpInfoOffsets;
        int i2 = iArr[i];
        int i3 = iArr[readUnsignedShort(i2 + 2)];
        String utf8 = readUTF8(i3, cArr);
        String utf82 = readUTF8(i3 + 2, cArr);
        int i4 = this.bootstrapMethodOffsets[readUnsignedShort(i2)];
        Handle handle = (Handle) readConst(readUnsignedShort(i4), cArr);
        int unsignedShort = readUnsignedShort(i4 + 2);
        Object[] objArr = new Object[unsignedShort];
        int i5 = i4 + 4;
        for (int i6 = 0; i6 < unsignedShort; i6++) {
            objArr[i6] = readConst(readUnsignedShort(i5), cArr);
            i5 += 2;
        }
        ConstantDynamic[] constantDynamicArr = this.constantDynamicValues;
        ConstantDynamic constantDynamic2 = new ConstantDynamic(utf8, utf82, handle, objArr);
        constantDynamicArr[i] = constantDynamic2;
        return constantDynamic2;
    }

    public Object readConst(int i, char[] cArr) {
        int i2 = this.cpInfoOffsets[i];
        byte b2 = this.classFileBuffer[i2 - 1];
        switch (b2) {
            case 3:
                return Integer.valueOf(readInt(i2));
            case 4:
                return Float.valueOf(Float.intBitsToFloat(readInt(i2)));
            case 5:
                return Long.valueOf(readLong(i2));
            case 6:
                return Double.valueOf(Double.longBitsToDouble(readLong(i2)));
            case 7:
                return Type.getObjectType(readUTF8(i2, cArr));
            case 8:
                return readUTF8(i2, cArr);
            default:
                switch (b2) {
                    case 15:
                        int i3 = readByte(i2);
                        int i4 = this.cpInfoOffsets[readUnsignedShort(i2 + 1)];
                        int i5 = this.cpInfoOffsets[readUnsignedShort(i4 + 2)];
                        return new Handle(i3, readClass(i4, cArr), readUTF8(i5, cArr), readUTF8(i5 + 2, cArr), this.classFileBuffer[i4 - 1] == 11);
                    case 16:
                        return Type.getMethodType(readUTF8(i2, cArr));
                    case 17:
                        return readConstantDynamic(i, cArr);
                    default:
                        Segment$$ExternalSyntheticBUOutline0.m991m();
                        return null;
                }
        }
    }
}
