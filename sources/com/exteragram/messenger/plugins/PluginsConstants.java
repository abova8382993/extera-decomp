package com.exteragram.messenger.plugins;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\b\u0007\u0018\u0000 \u00042\u00020\u0001:\b\u0004\u0005\u0006\u0007\b\t\n\u000bB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\f"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "Settings", "Strategy", "Xposed", "DevServer", "MenuItemTypes", "MenuItemProperties", "HookFilterTypes", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public final class PluginsConstants {
    public static final String PYTHON = Deobfuscator$exteraGramDev$TMessagesProj.getString(-52811294824249L);
    public static final String SEND_MESSAGE_HOOK = Deobfuscator$exteraGramDev$TMessagesProj.getString(-52841359595321L);
    public static final String STRATEGY = Deobfuscator$exteraGramDev$TMessagesProj.getString(-52918669006649L);
    public static final String PARAMS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-52957323712313L);
    public static final String UPDATE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-52987388483385L);
    public static final String UPDATES = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53017453254457L);
    public static final String REQUEST = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53051812992825L);
    public static final String RESPONSE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53086172731193L);
    public static final String ERROR = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53124827436857L);
    public static final String PLUGINS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53150597240633L);
    public static final String PLUGINS_EXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53184956979001L);
    public static final String PLUGINS_SDK = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53219316717369L);
    public static final String CREATE_SETTINGS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53270856324921L);
    public static final String APP_START = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53339575801657L);
    public static final String APP_STOP = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53382525474617L);
    public static final String APP_PAUSE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53421180180281L);
    public static final String APP_RESUME = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53464129853241L);
    public static final String ON_APP_EVENT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53511374493497L);
    public static final String ON_PLUGIN_LOAD = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53567209068345L);
    public static final String ON_PLUGIN_UNLOAD = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53631633577785L);

    private PluginsConstants() {
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$Settings;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Settings {
        public static final String TYPE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67332579252025L);
        public static final String KEY = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67354054088505L);
        public static final String TEXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67371233957689L);
        public static final String SUBTEXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67392708794169L);
        public static final String ICON = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67427068532537L);
        public static final String ACCENT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67448543369017L);
        public static final String RED = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67478608140089L);
        public static final String ON_CLICK = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67495788009273L);
        public static final String DEFAULT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67534442714937L);
        public static final String ITEMS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67568802453305L);
        public static final String HINT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67594572257081L);
        public static final String MULTILINE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67616047093561L);
        public static final String MAX_LENGTH = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67658996766521L);
        public static final String MASK = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67706241406777L);
        public static final String ON_CHANGE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67727716243257L);
        public static final String TYPE_SWITCH = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67770665916217L);
        public static final String TYPE_INPUT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67800730687289L);
        public static final String TYPE_SELECTOR = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67826500491065L);
        public static final String TYPE_HEADER = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67865155196729L);
        public static final String TYPE_DIVIDER = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67895219967801L);
        public static final String TYPE_TEXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67929579706169L);
        public static final String TYPE_EDIT_TEXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67951054542649L);
        public static final String TYPE_CUSTOM = Deobfuscator$exteraGramDev$TMessagesProj.getString(-67994004215609L);
        public static final String VIEW = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68024068986681L);
        public static final String ITEM = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68045543823161L);
        public static final String FACTORY = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68067018659641L);
        public static final String FACTORY_ARGS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68101378398009L);
        public static final String CREATE_SUB_FRAGMENT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68157212972857L);
        public static final String ON_LONG_CLICK = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68243112318777L);
        public static final String LINK_ALIAS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-68303241860921L);

        private Settings() {
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$Strategy;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Strategy {
        public static final String MODIFY = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53704648021817L);
        public static final String CANCEL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53734712792889L);
        public static final String DEFAULT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53764777563961L);
        public static final String MODIFY_FINAL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53799137302329L);

        private Strategy() {
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$Xposed;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Xposed {
        public static final String REPLACE_HOOKED_METHOD = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39286442809145L);
        public static final String BEFORE_HOOKED_METHOD = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39380932089657L);
        public static final String AFTER_HOOKED_METHOD = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39471126402873L);
        public static final String HOOK_FILTERS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39557025748793L);

        private Xposed() {
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$DevServer;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class DevServer {
        public static final String MODULE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38826881308473L);
        public static final String CLASS = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38874125948729L);
        public static final String START_SERVER = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38917075621689L);
        public static final String STOP_SERVER = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38972910196537L);

        private DevServer() {
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$MenuItemTypes;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class MenuItemTypes {
        public static final String MESSAGE_CONTEXT_MENU = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38483283924793L);
        public static final String DRAWER_MENU = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38573478238009L);
        public static final String MAIN_MENU = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38625017845561L);
        public static final String CHAT_ACTION_MENU = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38667967518521L);
        public static final String PROFILE_ACTION_MENU = Deobfuscator$exteraGramDev$TMessagesProj.getString(-38740981962553L);

        private MenuItemTypes() {
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$MenuItemProperties;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class MenuItemProperties {
        public static final String MENU_TYPE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39630040192825L);
        public static final String ITEM_ID = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39672989865785L);
        public static final String TEXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39707349604153L);
        public static final String SUBTEXT = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39728824440633L);
        public static final String ICON = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39763184179001L);
        public static final String ON_CLICK = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39784659015481L);
        public static final String CONDITION = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39823313721145L);
        public static final String PRIORITY = Deobfuscator$exteraGramDev$TMessagesProj.getString(-39866263394105L);

        private MenuItemProperties() {
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PluginsConstants$HookFilterTypes;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class HookFilterTypes {
        public static final String RESULT_IS_NULL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-75862384301881L);
        public static final String RESULT_IS_TRUE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-75926808811321L);
        public static final String RESULT_IS_FALSE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-75991233320761L);
        public static final String RESULT_NOT_NULL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76059952797497L);
        public static final String RESULT_IS_INSTANCE_OF = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76128672274233L);
        public static final String RESULT_EQUAL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76223161554745L);
        public static final String RESULT_NOT_EQUAL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76278996129593L);
        public static final String ARGUMENT_IS_NULL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76352010573625L);
        public static final String ARGUMENT_IS_TRUE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76425025017657L);
        public static final String ARGUMENT_IS_FALSE = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76498039461689L);
        public static final String ARGUMENT_NOT_NULL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76575348873017L);
        public static final String ARGUMENT_IS_INSTANCE_OF = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76652658284345L);
        public static final String ARGUMENT_EQUAL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76755737499449L);
        public static final String ARGUMENT_NOT_EQUAL = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76820162008889L);
        public static final String CONDITION = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76901766387513L);

        /* JADX INFO: renamed from: OR */
        public static final String f341OR = Deobfuscator$exteraGramDev$TMessagesProj.getString(-76944716060473L);

        private HookFilterTypes() {
        }
    }
}
