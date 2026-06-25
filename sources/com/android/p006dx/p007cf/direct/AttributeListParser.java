package com.android.p006dx.p007cf.direct;

import com.android.p006dx.p007cf.iface.Attribute;
import com.android.p006dx.p007cf.iface.ParseException;
import com.android.p006dx.p007cf.iface.ParseObserver;
import com.android.p006dx.p007cf.iface.StdAttributeList;
import com.android.p006dx.util.ByteArray;
import com.android.p006dx.util.Hex;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AttributeListParser {
    private final AttributeFactory attributeFactory;

    /* JADX INFO: renamed from: cf */
    private final DirectClassFile f98cf;
    private final int context;
    private int endOffset;
    private final StdAttributeList list;
    private ParseObserver observer;
    private final int offset;

    public AttributeListParser(DirectClassFile directClassFile, int i, int i2, AttributeFactory attributeFactory) {
        if (directClassFile == null) {
            g$$ExternalSyntheticBUOutline2.m208m("cf == null");
            throw null;
        }
        if (attributeFactory == null) {
            g$$ExternalSyntheticBUOutline2.m208m("attributeFactory == null");
            throw null;
        }
        int unsignedShort = directClassFile.getBytes().getUnsignedShort(i2);
        this.f98cf = directClassFile;
        this.context = i;
        this.offset = i2;
        this.attributeFactory = attributeFactory;
        this.list = new StdAttributeList(unsignedShort);
        this.endOffset = -1;
    }

    public void setObserver(ParseObserver parseObserver) {
        this.observer = parseObserver;
    }

    public int getEndOffset() {
        parseIfNecessary();
        return this.endOffset;
    }

    public StdAttributeList getList() {
        parseIfNecessary();
        return this.list;
    }

    private void parseIfNecessary() {
        if (this.endOffset < 0) {
            parse();
        }
    }

    private void parse() {
        int size = this.list.size();
        int iByteLength = this.offset + 2;
        ByteArray bytes = this.f98cf.getBytes();
        ParseObserver parseObserver = this.observer;
        if (parseObserver != null) {
            parseObserver.parsed(bytes, this.offset, 2, "attributes_count: " + Hex.m231u2(size));
        }
        for (int i = 0; i < size; i++) {
            try {
                ParseObserver parseObserver2 = this.observer;
                if (parseObserver2 != null) {
                    parseObserver2.parsed(bytes, iByteLength, 0, "\nattributes[" + i + "]:\n");
                    this.observer.changeIndent(1);
                }
                Attribute attribute = this.attributeFactory.parse(this.f98cf, this.context, iByteLength, this.observer);
                iByteLength += attribute.byteLength();
                this.list.set(i, attribute);
                ParseObserver parseObserver3 = this.observer;
                if (parseObserver3 != null) {
                    parseObserver3.changeIndent(-1);
                    this.observer.parsed(bytes, iByteLength, 0, "end attributes[" + i + "]\n");
                }
            } catch (ParseException e) {
                e.addContext("...while parsing attributes[" + i + "]");
                throw e;
            } catch (RuntimeException e2) {
                ParseException parseException = new ParseException(e2);
                parseException.addContext("...while parsing attributes[" + i + "]");
                throw parseException;
            }
        }
        this.endOffset = iByteLength;
    }
}
