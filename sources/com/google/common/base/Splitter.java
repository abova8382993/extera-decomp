package com.google.common.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public final class Splitter {
    private final int limit;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final CharMatcher trimmer;

    public interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence charSequence);
    }

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean z, CharMatcher charMatcher, int i) {
        this.strategy = strategy;
        this.omitEmptyStrings = z;
        this.trimmer = charMatcher;
        this.limit = i;
    }

    /* JADX INFO: renamed from: on */
    public static Splitter m504on(char c2) {
        return m505on(CharMatcher.m500is(c2));
    }

    /* JADX INFO: renamed from: on */
    public static Splitter m505on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy() { // from class: com.google.common.base.Splitter$$ExternalSyntheticLambda1
            @Override // com.google.common.base.Splitter.Strategy
            public final Iterator iterator(Splitter splitter, CharSequence charSequence) {
                return Splitter.m3402$r8$lambda$CK_F_WMWUv6g4fX5TCs4Q17sbA(charMatcher, splitter, charSequence);
            }
        });
    }

    /* JADX INFO: renamed from: com.google.common.base.Splitter$1 */
    public class C18241 extends SplittingIterator {
        final /* synthetic */ CharMatcher val$separatorMatcher;

        @Override // com.google.common.base.Splitter.SplittingIterator
        public int separatorEnd(int i) {
            return i + 1;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C18241(Splitter splitter, CharSequence charSequence, CharMatcher charMatcher) {
            super(splitter, charSequence);
            charMatcher = charMatcher;
        }

        @Override // com.google.common.base.Splitter.SplittingIterator
        public int separatorStart(int i) {
            return charMatcher.indexIn(this.toSplit, i);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$CK-_F_WMWUv6g4fX5TCs4Q17sbA */
    public static /* synthetic */ Iterator m3402$r8$lambda$CK_F_WMWUv6g4fX5TCs4Q17sbA(CharMatcher charMatcher, Splitter splitter, CharSequence charSequence) {
        return new SplittingIterator(splitter, charSequence) { // from class: com.google.common.base.Splitter.1
            final /* synthetic */ CharMatcher val$separatorMatcher;

            @Override // com.google.common.base.Splitter.SplittingIterator
            public int separatorEnd(int i) {
                return i + 1;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C18241(Splitter splitter2, CharSequence charSequence2, CharMatcher charMatcher2) {
                super(splitter2, charSequence2);
                charMatcher = charMatcher2;
            }

            @Override // com.google.common.base.Splitter.SplittingIterator
            public int separatorStart(int i) {
                return charMatcher.indexIn(this.toSplit, i);
            }
        };
    }

    /* JADX INFO: renamed from: on */
    public static Splitter m506on(final String str) {
        Preconditions.checkArgument(!str.isEmpty(), "The separator may not be the empty string.");
        if (str.length() == 1) {
            return m504on(str.charAt(0));
        }
        return new Splitter(new Strategy() { // from class: com.google.common.base.Splitter$$ExternalSyntheticLambda0
            @Override // com.google.common.base.Splitter.Strategy
            public final Iterator iterator(Splitter splitter, CharSequence charSequence) {
                return Splitter.$r8$lambda$e00L1vhkadM2rBAapSaIfxSYJ7k(str, splitter, charSequence);
            }
        });
    }

    /* JADX INFO: renamed from: com.google.common.base.Splitter$2 */
    public class C18252 extends SplittingIterator {
        final /* synthetic */ String val$separator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C18252(Splitter splitter, CharSequence charSequence, String str) {
            super(splitter, charSequence);
            str = str;
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0022, code lost:
        
            r6 = r6 + 1;
         */
        @Override // com.google.common.base.Splitter.SplittingIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int separatorStart(int r6) {
            /*
                r5 = this;
                java.lang.String r0 = r3
                int r0 = r0.length()
                java.lang.CharSequence r1 = r5.toSplit
                int r1 = r1.length()
                int r1 = r1 - r0
            Ld:
                if (r6 > r1) goto L29
                r2 = 0
            L10:
                if (r2 >= r0) goto L28
                java.lang.CharSequence r3 = r5.toSplit
                int r4 = r2 + r6
                char r3 = r3.charAt(r4)
                java.lang.String r4 = r3
                char r4 = r4.charAt(r2)
                if (r3 == r4) goto L25
                int r6 = r6 + 1
                goto Ld
            L25:
                int r2 = r2 + 1
                goto L10
            L28:
                return r6
            L29:
                r5 = -1
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Splitter.C18252.separatorStart(int):int");
        }

        @Override // com.google.common.base.Splitter.SplittingIterator
        public int separatorEnd(int i) {
            return i + str.length();
        }
    }

    public static /* synthetic */ Iterator $r8$lambda$e00L1vhkadM2rBAapSaIfxSYJ7k(String str, Splitter splitter, CharSequence charSequence) {
        return new SplittingIterator(splitter, charSequence) { // from class: com.google.common.base.Splitter.2
            final /* synthetic */ String val$separator;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C18252(Splitter splitter2, CharSequence charSequence2, String str2) {
                super(splitter2, charSequence2);
                str = str2;
            }

            @Override // com.google.common.base.Splitter.SplittingIterator
            public int separatorStart(int v) {
                /*
                    this = this;
                    java.lang.String r0 = r3
                    int r0 = r0.length()
                    java.lang.CharSequence r1 = r5.toSplit
                    int r1 = r1.length()
                    int r1 = r1 - r0
                Ld:
                    if (r6 > r1) goto L29
                    r2 = 0
                L10:
                    if (r2 >= r0) goto L28
                    java.lang.CharSequence r3 = r5.toSplit
                    int r4 = r2 + r6
                    char r3 = r3.charAt(r4)
                    java.lang.String r4 = r3
                    char r4 = r4.charAt(r2)
                    if (r3 == r4) goto L25
                    int r6 = r6 + 1
                    goto Ld
                L25:
                    int r2 = r2 + 1
                    goto L10
                L28:
                    return r6
                L29:
                    r5 = -1
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Splitter.C18252.separatorStart(int):int");
            }

            @Override // com.google.common.base.Splitter.SplittingIterator
            public int separatorEnd(int i) {
                return i + str.length();
            }
        };
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    public Iterable<String> split(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>(this) { // from class: com.google.common.base.Splitter.5
            final /* synthetic */ Splitter this$0;
            final /* synthetic */ CharSequence val$sequence;

            public C18265(Splitter this, CharSequence charSequence2) {
                charSequence = charSequence2;
                this.this$0 = this;
            }

            @Override // java.lang.Iterable
            public Iterator<String> iterator() {
                return this.this$0.splittingIterator(charSequence);
            }

            public String toString() {
                Joiner joinerM502on = Joiner.m502on(", ");
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                StringBuilder sbAppendTo = joinerM502on.appendTo(sb, this);
                sbAppendTo.append(']');
                return sbAppendTo.toString();
            }
        };
    }

    /* JADX INFO: renamed from: com.google.common.base.Splitter$5 */
    public class C18265 implements Iterable<String> {
        final /* synthetic */ Splitter this$0;
        final /* synthetic */ CharSequence val$sequence;

        public C18265(Splitter this, CharSequence charSequence2) {
            charSequence = charSequence2;
            this.this$0 = this;
        }

        @Override // java.lang.Iterable
        public Iterator<String> iterator() {
            return this.this$0.splittingIterator(charSequence);
        }

        public String toString() {
            Joiner joinerM502on = Joiner.m502on(", ");
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            StringBuilder sbAppendTo = joinerM502on.appendTo(sb, this);
            sbAppendTo.append(']');
            return sbAppendTo.toString();
        }
    }

    public Iterator<String> splittingIterator(CharSequence charSequence) {
        return this.strategy.iterator(this, charSequence);
    }

    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator<String> itSplittingIterator = splittingIterator(charSequence);
        ArrayList arrayList = new ArrayList();
        while (itSplittingIterator.hasNext()) {
            arrayList.add(itSplittingIterator.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static abstract class SplittingIterator extends AbstractIterator<String> {
        int limit;
        int offset = 0;
        final boolean omitEmptyStrings;
        final CharSequence toSplit;
        final CharMatcher trimmer;

        public abstract int separatorEnd(int i);

        public abstract int separatorStart(int i);

        public SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = charSequence;
        }

        /* JADX WARN: Code restructure failed: missing block: B:81:0x0060, code lost:
        
            r3 = r6.limit;
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x0063, code lost:
        
            if (r3 != 1) goto L88;
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0065, code lost:
        
            r1 = r6.toSplit.length();
            r6.offset = -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x006d, code lost:
        
            if (r1 <= r0) goto L108;
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x007d, code lost:
        
            if (r6.trimmer.matches(r6.toSplit.charAt(r1 - 1)) == false) goto L109;
         */
        /* JADX WARN: Code restructure failed: missing block: B:87:0x007f, code lost:
        
            r1 = r1 - 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:88:0x0082, code lost:
        
            r6.limit = r3 - 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:90:0x008f, code lost:
        
            return r6.toSplit.subSequence(r0, r1).toString();
         */
        @Override // com.google.common.base.AbstractIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String computeNext() {
            /*
                r6 = this;
                int r0 = r6.offset
            L2:
                int r1 = r6.offset
                r2 = -1
                if (r1 == r2) goto L90
                int r1 = r6.separatorStart(r1)
                if (r1 != r2) goto L16
                java.lang.CharSequence r1 = r6.toSplit
                int r1 = r1.length()
                r6.offset = r2
                goto L1c
            L16:
                int r3 = r6.separatorEnd(r1)
                r6.offset = r3
            L1c:
                int r3 = r6.offset
                if (r3 != r0) goto L2f
                int r3 = r3 + 1
                r6.offset = r3
                java.lang.CharSequence r1 = r6.toSplit
                int r1 = r1.length()
                if (r3 <= r1) goto L2
                r6.offset = r2
                goto L2
            L2f:
                if (r0 >= r1) goto L42
                com.google.common.base.CharMatcher r3 = r6.trimmer
                java.lang.CharSequence r4 = r6.toSplit
                char r4 = r4.charAt(r0)
                boolean r3 = r3.matches(r4)
                if (r3 == 0) goto L42
                int r0 = r0 + 1
                goto L2f
            L42:
                if (r1 <= r0) goto L57
                com.google.common.base.CharMatcher r3 = r6.trimmer
                java.lang.CharSequence r4 = r6.toSplit
                int r5 = r1 + (-1)
                char r4 = r4.charAt(r5)
                boolean r3 = r3.matches(r4)
                if (r3 == 0) goto L57
                int r1 = r1 + (-1)
                goto L42
            L57:
                boolean r3 = r6.omitEmptyStrings
                if (r3 == 0) goto L60
                if (r0 != r1) goto L60
                int r0 = r6.offset
                goto L2
            L60:
                int r3 = r6.limit
                r4 = 1
                if (r3 != r4) goto L82
                java.lang.CharSequence r1 = r6.toSplit
                int r1 = r1.length()
                r6.offset = r2
            L6d:
                if (r1 <= r0) goto L85
                com.google.common.base.CharMatcher r2 = r6.trimmer
                java.lang.CharSequence r3 = r6.toSplit
                int r4 = r1 + (-1)
                char r3 = r3.charAt(r4)
                boolean r2 = r2.matches(r3)
                if (r2 == 0) goto L85
                int r1 = r1 + (-1)
                goto L6d
            L82:
                int r3 = r3 - r4
                r6.limit = r3
            L85:
                java.lang.CharSequence r6 = r6.toSplit
                java.lang.CharSequence r6 = r6.subSequence(r0, r1)
                java.lang.String r6 = r6.toString()
                return r6
            L90:
                java.lang.Object r6 = r6.endOfData()
                java.lang.String r6 = (java.lang.String) r6
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Splitter.SplittingIterator.computeNext():java.lang.String");
        }
    }
}
