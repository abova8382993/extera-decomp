package org.commonmark.parser;

import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.commonmark.Extension;
import org.commonmark.internal.DocumentParser;
import org.commonmark.internal.InlineParserContextImpl;
import org.commonmark.internal.InlineParserImpl;
import org.commonmark.node.Block;
import org.commonmark.node.Node;
import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.parser.delimiter.DelimiterProcessor;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes5.dex */
public class Parser {
    private final List<BlockParserFactory> blockParserFactories;
    private final List<DelimiterProcessor> delimiterProcessors;
    private final InlineParserFactory inlineParserFactory;
    private final List<Object> postProcessors;

    public interface ParserExtension extends Extension {
        void extend(Builder builder);
    }

    public /* synthetic */ Parser(Builder builder, C25691 c25691) {
        this(builder);
    }

    private Parser(Builder builder) {
        this.blockParserFactories = DocumentParser.calculateBlockParserFactories(builder.blockParserFactories, builder.enabledBlockTypes);
        InlineParserFactory inlineParserFactory = builder.getInlineParserFactory();
        this.inlineParserFactory = inlineParserFactory;
        this.postProcessors = builder.postProcessors;
        List<DelimiterProcessor> list = builder.delimiterProcessors;
        this.delimiterProcessors = list;
        inlineParserFactory.create(new InlineParserContextImpl(list, Collections.EMPTY_MAP));
    }

    public static Builder builder() {
        return new Builder();
    }

    public Node parse(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline2.m208m("input must not be null");
            return null;
        }
        return postProcess(createDocumentParser().parse(str));
    }

    private DocumentParser createDocumentParser() {
        return new DocumentParser(this.blockParserFactories, this.inlineParserFactory, this.delimiterProcessors);
    }

    private Node postProcess(Node node) {
        Iterator<Object> it = this.postProcessors.iterator();
        if (!it.hasNext()) {
            return node;
        }
        MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
        throw null;
    }

    public static class Builder {
        private InlineParserFactory inlineParserFactory;
        private final List<BlockParserFactory> blockParserFactories = new ArrayList();
        private final List<DelimiterProcessor> delimiterProcessors = new ArrayList();
        private final List<Object> postProcessors = new ArrayList();
        private Set<Class<? extends Block>> enabledBlockTypes = DocumentParser.getDefaultBlockParserTypes();

        public Parser build() {
            return new Parser(this);
        }

        public Builder extensions(Iterable<? extends Extension> iterable) {
            if (iterable == null) {
                g$$ExternalSyntheticBUOutline2.m208m("extensions must not be null");
                return null;
            }
            for (Extension extension : iterable) {
                if (extension instanceof ParserExtension) {
                    ((ParserExtension) extension).extend(this);
                }
            }
            return this;
        }

        public Builder customBlockParserFactory(BlockParserFactory blockParserFactory) {
            if (blockParserFactory == null) {
                g$$ExternalSyntheticBUOutline2.m208m("blockParserFactory must not be null");
                return null;
            }
            this.blockParserFactories.add(blockParserFactory);
            return this;
        }

        public Builder customDelimiterProcessor(DelimiterProcessor delimiterProcessor) {
            if (delimiterProcessor == null) {
                g$$ExternalSyntheticBUOutline2.m208m("delimiterProcessor must not be null");
                return null;
            }
            this.delimiterProcessors.add(delimiterProcessor);
            return this;
        }

        public Builder inlineParserFactory(InlineParserFactory inlineParserFactory) {
            this.inlineParserFactory = inlineParserFactory;
            return this;
        }

        public InlineParserFactory getInlineParserFactory() {
            InlineParserFactory inlineParserFactory = this.inlineParserFactory;
            return inlineParserFactory != null ? inlineParserFactory : new InlineParserFactory() { // from class: org.commonmark.parser.Parser.Builder.1
                public C25701() {
                }

                @Override // org.commonmark.parser.InlineParserFactory
                public InlineParser create(InlineParserContext inlineParserContext) {
                    return new InlineParserImpl(inlineParserContext);
                }
            };
        }

        /* JADX INFO: renamed from: org.commonmark.parser.Parser$Builder$1 */
        public class C25701 implements InlineParserFactory {
            public C25701() {
            }

            @Override // org.commonmark.parser.InlineParserFactory
            public InlineParser create(InlineParserContext inlineParserContext) {
                return new InlineParserImpl(inlineParserContext);
            }
        }
    }
}
