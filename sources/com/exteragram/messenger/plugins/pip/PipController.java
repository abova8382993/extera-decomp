package com.exteragram.messenger.plugins.pip;

import com.chaquo.python.internal.Common;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.pip.PipController;
import com.exteragram.messenger.utils.network.ExteraHttpClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.p028io.CloseableKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.mvel2.asm.signature.SignatureVisitor;
import org.simplifiles.SimpliFiles;
import org.simplifiles.archive.ArchiveExtractionOptions;
import org.simplifiles.archive.CancellationToken;
import org.simplifiles.archive.ExtractionTargetPolicy;
import org.simplifiles.archive.security.SecurityPolicy;
import org.simplifiles.files.OverwritePolicy;
import org.simplifiles.files.SimpliFile;
import org.telegram.messenger.FileLog;
import org.vosk.Model$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bÇ\u0002\u0018\u00002\u00020\u0001:\u0005rstuvB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010+\u001a\u00020\u00102\u0006\u0010,\u001a\u00020\u0010H\u0002J\b\u0010-\u001a\u00020.H\u0002J&\u0010/\u001a \u0012\u0004\u0012\u00020\u0010\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0010010000H\u0002J.\u00102\u001a\u00020.2$\u00103\u001a \u0012\u0004\u0012\u00020\u0010\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0010010000H\u0002J\b\u00104\u001a\u00020.H\u0002J\u0006\u00105\u001a\u00020.J.\u00107\u001a\b\u0012\u0004\u0012\u00020\u0010082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u0010082\u0006\u0010:\u001a\u00020\u00102\n\b\u0002\u0010;\u001a\u0004\u0018\u00010<JV\u0010=\u001a\u00020.2\u0006\u0010>\u001a\u00020\u00102\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@082\u0018\u0010A\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@0\u00112\u0006\u0010:\u001a\u00020\u00102\b\u0010;\u001a\u0004\u0018\u00010<H\u0002J*\u0010B\u001a\u00020.2\u0006\u0010:\u001a\u00020\u00102\u0018\u0010C\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@01H\u0002J\b\u0010D\u001a\u00020.H\u0002J\b\u0010E\u001a\u00020.H\u0002J\u000e\u0010F\u001a\u00020.2\u0006\u0010:\u001a\u00020\u0010J4\u0010G\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u00102\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@082\b\u0010;\u001a\u0004\u0018\u00010<H\u0002J\u001a\u0010H\u001a\u0004\u0018\u00010I2\u0006\u0010J\u001a\u00020\u00102\u0006\u0010K\u001a\u00020LH\u0002J\u0016\u0010M\u001a\u0004\u0018\u00010\u0010*\u00020L2\u0006\u0010,\u001a\u00020\u0010H\u0002J\u0010\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020\u0010H\u0002J\u0010\u0010Q\u001a\u00020O2\u0006\u0010R\u001a\u00020\u0010H\u0002J\u001a\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020V2\b\u0010;\u001a\u0004\u0018\u00010<H\u0002J,\u0010W\u001a\u0004\u0018\u00010\u00102\u0006\u0010>\u001a\u00020\u00102\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@08H\u0002J,\u0010X\u001a\u0004\u0018\u00010\u00102\u0006\u0010>\u001a\u00020\u00102\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@08H\u0002J\u0018\u0010Y\u001a\u00020\t2\u0006\u0010>\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u0010H\u0002J\u0018\u0010Z\u001a\u00020.2\u0006\u0010>\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u0010H\u0002J.\u0010[\u001a \u0012\u0004\u0012\u00020\u0010\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@080@2\u0006\u0010\\\u001a\u00020\u0010H\u0002J\"\u0010]\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@082\u0006\u0010^\u001a\u00020\u0010H\u0002J\u0016\u0010_\u001a\b\u0012\u0004\u0012\u00020\u0010082\u0006\u0010`\u001a\u00020\tH\u0002J\u0010\u0010a\u001a\u00020O2\u0006\u0010b\u001a\u00020\u0010H\u0002J\u001a\u0010c\u001a\u0004\u0018\u00010\t2\u0006\u0010>\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u0010H\u0002J*\u0010d\u001a\u00020O2\u0006\u0010J\u001a\u00020\u00102\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@08H\u0002J\u0010\u0010e\u001a\u00020O2\u0006\u0010f\u001a\u00020\u0010H\u0002J6\u0010g\u001a\b\u0012\u0004\u0012\u00020\u0010082\f\u0010h\u001a\b\u0012\u0004\u0012\u00020\u0010082\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@08H\u0002J\"\u0010i\u001a\u00020O2\u0018\u0010?\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100@08H\u0002J\u0010\u0010j\u001a\u00020O2\u0006\u0010J\u001a\u00020\u0010H\u0002J\u0018\u0010k\u001a\u00020O2\u0006\u0010J\u001a\u00020\u00102\u0006\u0010f\u001a\u00020\u0010H\u0002J\u0016\u0010l\u001a\b\u0012\u0004\u0012\u00020m082\u0006\u0010J\u001a\u00020\u0010H\u0002J\u0010\u0010n\u001a\u00020\u00102\u0006\u0010o\u001a\u00020\tH\u0002J\u0010\u0010p\u001a\u00020q2\u0006\u0010J\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR4\u0010\u000e\u001a(\u0012\u0004\u0012\u00020\u0010\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00110\u000fj\u0002`\u00120\u000fj\u0002`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001dX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\u001001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006w"}, m877d2 = {"Lcom/exteragram/messenger/plugins/pip/PipController;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "client", "Lokhttp3/OkHttpClient;", "gson", "Lcom/google/gson/Gson;", "libsDir", "Ljava/io/File;", "getLibsDir", "()Ljava/io/File;", "registryFile", "getRegistryFile", "registry", "Ljava/util/concurrent/ConcurrentHashMap;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/exteragram/messenger/plugins/pip/VersionMap;", "Lcom/exteragram/messenger/plugins/pip/RegistryMap;", "installLocks", "wheelArchivePolicy", "Lorg/simplifiles/archive/security/SecurityPolicy;", "pythonVersion", "getPythonVersion", "()Ljava/lang/String;", "setPythonVersion", "(Ljava/lang/String;)V", "MAX_REGISTRY_BYTES", _UrlKt.FRAGMENT_ENCODE_SET, "MAX_METADATA_BYTES", "ENV_SYS_PLATFORM", "ENV_PLATFORM_SYSTEM", "ENV_OS_NAME", "REGEX_NORMALIZE", "Lkotlin/text/Regex;", "REGEX_REQ_PARSE", "REGEX_REQ_SPECS", "REGEX_REQ_EXTRA", "REGEX_REQ_PAREN", "REGEX_VERSION_SPLIT", "REGEX_MARKER_TOKEN", "REGEX_VERSION_WILDCARD", "normalizePackageName", "name", "loadRegistry", _UrlKt.FRAGMENT_ENCODE_SET, "snapshotRegistry", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "restoreRegistry", "snapshot", "saveRegistry", "cleanup", "PREINSTALLED_PACKAGES", "installDependencies", _UrlKt.FRAGMENT_ENCODE_SET, Common.ASSET_REQUIREMENTS, "pluginId", "delegate", "Lcom/exteragram/messenger/plugins/pip/PipController$InstallerDelegate;", "resolveAndInstall", "pkg", "specs", "Lkotlin/Pair;", "installedAccumulator", "updateRegistryForPlugin", "currentlyNeeded", "cleanupInternal", "removeOrphanedDirectories", "uninstallDependencies", "installPackage", "selectWheelCandidate", "Lcom/exteragram/messenger/plugins/pip/PipController$WheelCandidate;", "version", "artifact", "Lcom/google/gson/JsonObject;", "getStringOrNull", "isPurePythonWheelCompatible", _UrlKt.FRAGMENT_ENCODE_SET, "filename", "isPythonTagCompatible", "tag", "executeWithRetry", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "findVersionOnDisk", "findInstalledVersion", "getLibPath", "deletePackage", "parseRequirement", "req", "parseSpecs", "specsString", "parseDependenciesFromMetadata", "metadataFile", "isMarkerCompatible", "marker", "findMetadataFile", "checkVersionSatisfies", "isWildcardVersionSpec", "spec", "filterPreReleases", "versions", "specsAllowPreRelease", "isPreReleaseVersion", "matchesVersionWildcard", "parseVersionReleaseParts", _UrlKt.FRAGMENT_ENCODE_SET, "calculateSha256", "file", "parseVersion", "Lcom/exteragram/messenger/plugins/pip/PipController$ParsedVersion;", "InstallerDelegate", "WheelCandidate", "ParsedVersion", "MarkerParser", "VersionComparator", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPipController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PipController.kt\ncom/exteragram/messenger/plugins/pip/PipController\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 6 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,1033:1\n221#2:1034\n221#2,2:1035\n222#2:1037\n221#2:1049\n221#2,2:1050\n222#2:1052\n221#2:1056\n221#2,2:1057\n222#2:1059\n221#2:1060\n221#2,2:1061\n222#2:1063\n221#2:1066\n222#2:1069\n221#2:1072\n221#2,2:1073\n222#2:1075\n493#3:1038\n442#3:1039\n493#3:1042\n442#3:1043\n1266#4,2:1040\n1266#4,4:1044\n1269#4:1048\n296#4,2:1053\n1915#4,2:1064\n1915#4,2:1067\n1915#4,2:1076\n777#4:1078\n873#4,2:1079\n1807#4,3:1081\n777#4:1084\n873#4,2:1085\n832#4:1087\n862#4,2:1088\n1807#4,3:1090\n1807#4,3:1093\n1786#4,3:1096\n1642#4,10:1099\n1915#4:1109\n1916#4:1111\n1652#4:1112\n1586#4:1113\n1661#4,3:1114\n1#5:1055\n1#5:1110\n14048#6,2:1070\n*S KotlinDebug\n*F\n+ 1 PipController.kt\ncom/exteragram/messenger/plugins/pip/PipController\n*L\n118#1:1034\n121#1:1035,2\n118#1:1037\n152#1:1049\n154#1:1050,2\n152#1:1052\n301#1:1056\n302#1:1057,2\n301#1:1059\n314#1:1060\n315#1:1061,2\n314#1:1063\n335#1:1066\n335#1:1069\n362#1:1072\n363#1:1073,2\n362#1:1075\n144#1:1038\n144#1:1039\n145#1:1042\n145#1:1043\n144#1:1040,2\n145#1:1044,4\n144#1:1048\n234#1:1053,2\n322#1:1064,2\n336#1:1067,2\n373#1:1076,2\n415#1:1078\n415#1:1079,2\n538#1:1081,3\n592#1:1084\n592#1:1085,2\n907#1:1087\n907#1:1088,2\n912#1:1090,3\n916#1:1093,3\n945#1:1096,3\n951#1:1099,10\n951#1:1109\n951#1:1111\n951#1:1112\n178#1:1113\n178#1:1114,3\n951#1:1110\n344#1:1070,2\n*E\n"})
public final class PipController {
    public static final PipController INSTANCE;
    private static final long MAX_METADATA_BYTES = 4194304;
    private static final long MAX_REGISTRY_BYTES = 4194304;
    private static final Set<String> PREINSTALLED_PACKAGES;
    private static final Regex REGEX_MARKER_TOKEN;
    private static final Regex REGEX_NORMALIZE;
    private static final Regex REGEX_REQ_EXTRA;
    private static final Regex REGEX_REQ_PAREN;
    private static final Regex REGEX_REQ_PARSE;
    private static final Regex REGEX_REQ_SPECS;
    private static final Regex REGEX_VERSION_SPLIT;
    private static final Regex REGEX_VERSION_WILDCARD;
    private static final OkHttpClient client;
    private static final Gson gson;
    private static final ConcurrentHashMap<String, Object> installLocks;
    private static String pythonVersion;
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Set<String>>> registry;
    private static final SecurityPolicy wheelArchivePolicy;
    private static final String ENV_SYS_PLATFORM = Deobfuscator$exteraGramDev$TMessagesProj.getString(-65558757758777L);
    private static final String ENV_PLATFORM_SYSTEM = Deobfuscator$exteraGramDev$TMessagesProj.getString(-65584527562553L);
    private static final String ENV_OS_NAME = Deobfuscator$exteraGramDev$TMessagesProj.getString(-65610297366329L);

    @Metadata(m876d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&¨\u0006\bÀ\u0006\u0003"}, m877d2 = {"Lcom/exteragram/messenger/plugins/pip/PipController$InstallerDelegate;", _UrlKt.FRAGMENT_ENCODE_SET, "onProgress", _UrlKt.FRAGMENT_ENCODE_SET, "text", _UrlKt.FRAGMENT_ENCODE_SET, "isCancelled", _UrlKt.FRAGMENT_ENCODE_SET, "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public interface InstallerDelegate {
        boolean isCancelled();

        void onProgress(String text);
    }

    private PipController() {
    }

    static {
        PipController pipController = new PipController();
        INSTANCE = pipController;
        client = ExteraHttpClient.INSTANCE.getClient();
        gson = new Gson();
        registry = new ConcurrentHashMap<>();
        installLocks = new ConcurrentHashMap<>();
        wheelArchivePolicy = SecurityPolicy.INSTANCE.builder().maxEntries(50000L).maxTotalUncompressedSize(524288000L).maxSingleFileSize(262144000L).maxCompressionRatio(500.0d).build();
        pythonVersion = Deobfuscator$exteraGramDev$TMessagesProj.getString(-65636067170105L);
        REGEX_NORMALIZE = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65670426908473L));
        REGEX_REQ_PARSE = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65700491679545L));
        REGEX_REQ_SPECS = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65799275927353L));
        REGEX_REQ_EXTRA = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65941009848121L));
        REGEX_REQ_PAREN = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65971074619193L));
        REGEX_VERSION_SPLIT = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65992549455673L));
        REGEX_MARKER_TOKEN = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-66082743768889L));
        REGEX_VERSION_WILDCARD = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-66495060629305L));
        pipController.loadRegistry();
        Set of = SetsKt.setOf((Object[]) new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-66580959975225L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-66645384484665L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-66679744223033L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-66701219059513L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-66744168732473L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-66774233503545L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-66812888209209L)});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(of, 10));
        Iterator it = of.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.normalizePackageName((String) it.next()));
        }
        PREINSTALLED_PACKAGES = CollectionsKt.toSet(arrayList);
    }

    private final File getLibsDir() {
        return SimpliFiles.directory(new File(PluginsController.INSTANCE.getInstance().getPluginsDir(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-56917283559225L))).create().getFile();
    }

    private final File getRegistryFile() {
        return new File(getLibsDir(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-56938758395705L));
    }

    public final String getPythonVersion() {
        return pythonVersion;
    }

    public final void setPythonVersion(String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-56998887937849L);
        pythonVersion = str;
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J)\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, m877d2 = {"Lcom/exteragram/messenger/plugins/pip/PipController$WheelCandidate;", _UrlKt.FRAGMENT_ENCODE_SET, "version", _UrlKt.FRAGMENT_ENCODE_SET, "downloadUrl", "expectedSha256", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getVersion", "()Ljava/lang/String;", "getDownloadUrl", "getExpectedSha256", "component1", "component2", "component3", "copy", "equals", _UrlKt.FRAGMENT_ENCODE_SET, "other", "hashCode", _UrlKt.FRAGMENT_ENCODE_SET, "toString", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final /* data */ class WheelCandidate {
        private final String downloadUrl;
        private final String expectedSha256;
        private final String version;

        public static /* synthetic */ WheelCandidate copy$default(WheelCandidate wheelCandidate, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                str = wheelCandidate.version;
            }
            if ((i & 2) != 0) {
                str2 = wheelCandidate.downloadUrl;
            }
            if ((i & 4) != 0) {
                str3 = wheelCandidate.expectedSha256;
            }
            return wheelCandidate.copy(str, str2, str3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getVersion() {
            return this.version;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getDownloadUrl() {
            return this.downloadUrl;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getExpectedSha256() {
            return this.expectedSha256;
        }

        public final WheelCandidate copy(String version, String downloadUrl, String expectedSha256) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-56586571077433L);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-56620930815801L);
            return new WheelCandidate(version, downloadUrl, expectedSha256);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof WheelCandidate)) {
                return false;
            }
            WheelCandidate wheelCandidate = (WheelCandidate) other;
            return Intrinsics.areEqual(this.version, wheelCandidate.version) && Intrinsics.areEqual(this.downloadUrl, wheelCandidate.downloadUrl) && Intrinsics.areEqual(this.expectedSha256, wheelCandidate.expectedSha256);
        }

        public int hashCode() {
            int iHashCode = ((this.version.hashCode() * 31) + this.downloadUrl.hashCode()) * 31;
            String str = this.expectedSha256;
            return iHashCode + (str == null ? 0 : str.hashCode());
        }

        public String toString() {
            return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56672470423353L) + this.version + Deobfuscator$exteraGramDev$TMessagesProj.getString(-56775549638457L) + this.downloadUrl + Deobfuscator$exteraGramDev$TMessagesProj.getString(-56839974147897L) + this.expectedSha256 + ')';
        }

        public WheelCandidate(String str, String str2, String str3) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-56500671731513L);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-56535031469881L);
            this.version = str;
            this.downloadUrl = str2;
            this.expectedSha256 = str3;
        }

        public final String getVersion() {
            return this.version;
        }

        public final String getDownloadUrl() {
            return this.downloadUrl;
        }

        public final String getExpectedSha256() {
            return this.expectedSha256;
        }
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, m877d2 = {"Lcom/exteragram/messenger/plugins/pip/PipController$ParsedVersion;", _UrlKt.FRAGMENT_ENCODE_SET, "epoch", _UrlKt.FRAGMENT_ENCODE_SET, "publicVersion", _UrlKt.FRAGMENT_ENCODE_SET, "parts", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(ILjava/lang/String;Ljava/util/List;)V", "getEpoch", "()I", "getPublicVersion", "()Ljava/lang/String;", "getParts", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", _UrlKt.FRAGMENT_ENCODE_SET, "other", "hashCode", "toString", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final /* data */ class ParsedVersion {
        private final int epoch;
        private final List<String> parts;
        private final String publicVersion;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ParsedVersion copy$default(ParsedVersion parsedVersion, int i, String str, List list, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = parsedVersion.epoch;
            }
            if ((i2 & 2) != 0) {
                str = parsedVersion.publicVersion;
            }
            if ((i2 & 4) != 0) {
                list = parsedVersion.parts;
            }
            return parsedVersion.copy(i, str, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getEpoch() {
            return this.epoch;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getPublicVersion() {
            return this.publicVersion;
        }

        public final List<String> component3() {
            return this.parts;
        }

        public final ParsedVersion copy(int epoch, String publicVersion, List<String> parts) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-54116964882233L);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-54177094424377L);
            return new ParsedVersion(epoch, publicVersion, parts);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ParsedVersion)) {
                return false;
            }
            ParsedVersion parsedVersion = (ParsedVersion) other;
            return this.epoch == parsedVersion.epoch && Intrinsics.areEqual(this.publicVersion, parsedVersion.publicVersion) && Intrinsics.areEqual(this.parts, parsedVersion.parts);
        }

        public int hashCode() {
            return (((Integer.hashCode(this.epoch) * 31) + this.publicVersion.hashCode()) * 31) + this.parts.hashCode();
        }

        public String toString() {
            return Deobfuscator$exteraGramDev$TMessagesProj.getString(-54202864228153L) + this.epoch + Deobfuscator$exteraGramDev$TMessagesProj.getString(-54293058541369L) + this.publicVersion + Deobfuscator$exteraGramDev$TMessagesProj.getString(-54366072985401L) + this.parts + ')';
        }

        public ParsedVersion(int i, String str, List<String> list) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-54031065536313L);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-54091195078457L);
            this.epoch = i;
            this.publicVersion = str;
            this.parts = list;
        }

        public final int getEpoch() {
            return this.epoch;
        }

        public final String getPublicVersion() {
            return this.publicVersion;
        }

        public final List<String> getParts() {
            return this.parts;
        }
    }

    private final String normalizePackageName(String name) {
        String lowerCase = name.toLowerCase(Locale.ROOT);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-57033247676217L);
        return REGEX_NORMALIZE.replace(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-57106262120249L));
    }

    private final synchronized void loadRegistry() {
        Object objM3494constructorimpl;
        if (!getRegistryFile().exists()) {
            registry.clear();
            return;
        }
        try {
            Object objFromJson = gson.fromJson(SimpliFile.readText$default(SimpliFiles.file(getRegistryFile()), 4194304L, null, 2, null), new TypeToken<Map<String, ? extends Map<String, ? extends Set<? extends String>>>>() { // from class: com.exteragram.messenger.plugins.pip.PipController$loadRegistry$type$1
            }.getType());
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-57114852054841L);
            registry.clear();
            for (Map.Entry entry : ((Map) objFromJson).entrySet()) {
                String str = (String) entry.getKey();
                Map map = (Map) entry.getValue();
                String strNormalizePackageName = INSTANCE.normalizePackageName(str);
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                for (Map.Entry entry2 : map.entrySet()) {
                    String str2 = (String) entry2.getKey();
                    Set set = (Set) entry2.getValue();
                    ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet();
                    keySetViewNewKeySet.addAll(set);
                    concurrentHashMap.put(str2, keySetViewNewKeySet);
                }
                registry.put(strNormalizePackageName, (ConcurrentHashMap<String, Set<String>>) concurrentHashMap);
            }
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-57174981596985L), e);
            registry.clear();
            try {
                Result.Companion companion = Result.INSTANCE;
                objM3494constructorimpl = Result.m3494constructorimpl(SimpliFiles.file(getRegistryFile()).moveTo(new File(getLibsDir(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-57342485321529L)), OverwritePolicy.REPLACE));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM3497exceptionOrNullimpl = Result.m3497exceptionOrNullimpl(objM3494constructorimpl);
            if (thM3497exceptionOrNullimpl != null) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-57419794732857L), thM3497exceptionOrNullimpl);
            }
        }
    }

    private final synchronized Map<String, Map<String, Set<String>>> snapshotRegistry() {
        LinkedHashMap linkedHashMap;
        try {
            ConcurrentHashMap<String, ConcurrentHashMap<String, Set<String>>> concurrentHashMap = registry;
            linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(concurrentHashMap.size()));
            for (Object obj : concurrentHashMap.entrySet()) {
                Object key = ((Map.Entry) obj).getKey();
                ConcurrentHashMap concurrentHashMap2 = (ConcurrentHashMap) ((Map.Entry) obj).getValue();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(concurrentHashMap2.size()));
                for (Object obj2 : concurrentHashMap2.entrySet()) {
                    linkedHashMap2.put(((Map.Entry) obj2).getKey(), CollectionsKt.toSet((Set) ((Map.Entry) obj2).getValue()));
                }
                linkedHashMap.put(key, linkedHashMap2);
            }
        } catch (Throwable th) {
            throw th;
        }
        return linkedHashMap;
    }

    private final synchronized void restoreRegistry(Map<String, ? extends Map<String, ? extends Set<String>>> snapshot) {
        try {
            registry.clear();
            for (Map.Entry<String, ? extends Map<String, ? extends Set<String>>> entry : snapshot.entrySet()) {
                String key = entry.getKey();
                Map<String, ? extends Set<String>> value = entry.getValue();
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                for (Map.Entry<String, ? extends Set<String>> entry2 : value.entrySet()) {
                    String key2 = entry2.getKey();
                    Set<String> value2 = entry2.getValue();
                    ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet();
                    keySetViewNewKeySet.addAll(value2);
                    concurrentHashMap.put(key2, keySetViewNewKeySet);
                }
                registry.put(key, (ConcurrentHashMap<String, Set<String>>) concurrentHashMap);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private final synchronized void saveRegistry() {
        try {
            SimpliFile simpliFileFile = SimpliFiles.file(getRegistryFile());
            String json = gson.toJson(snapshotRegistry());
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-57630248130361L);
            SimpliFile.writeTextAtomic$default(simpliFileFile, json, null, 2, null);
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-57681787737913L), e);
        }
    }

    public final void cleanup() {
        cleanupInternal();
    }

    public static /* synthetic */ List installDependencies$default(PipController pipController, List list, String str, InstallerDelegate installerDelegate, int i, Object obj) {
        if ((i & 4) != 0) {
            installerDelegate = null;
        }
        return pipController.installDependencies(list, str, installerDelegate);
    }

    public final List<String> installDependencies(List<String> list, String pluginId, InstallerDelegate delegate) {
        PipController pipController;
        String str;
        Exception exc;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-57849291462457L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-57905126037305L);
        ArrayList arrayList = new ArrayList();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Map<String, Map<String, Set<String>>> mapSnapshotRegistry = snapshotRegistry();
        try {
            for (String str2 : list) {
                if (delegate != null) {
                    try {
                        if (delegate.isCancelled()) {
                            throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-57943780742969L));
                        }
                    } catch (Exception e) {
                        exc = e;
                        pipController = this;
                        str = pluginId;
                        FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-58132759303993L) + str + Deobfuscator$exteraGramDev$TMessagesProj.getString(-58351802636089L) + list, exc);
                        pipController.restoreRegistry(mapSnapshotRegistry);
                        pipController.removeOrphanedDirectories();
                        throw exc;
                    }
                }
                if (!StringsKt.isBlank(str2)) {
                    Pair<String, List<Pair<String, String>>> requirement = this.parseRequirement(str2);
                    String strComponent1 = requirement.component1();
                    List<Pair<String, String>> listComponent2 = requirement.component2();
                    String strNormalizePackageName = this.normalizePackageName(strComponent1);
                    if (!PREINSTALLED_PACKAGES.contains(strNormalizePackageName)) {
                        pipController = this;
                        str = pluginId;
                        InstallerDelegate installerDelegate = delegate;
                        try {
                            pipController.resolveAndInstall(strNormalizePackageName, listComponent2, linkedHashSet, str, installerDelegate);
                            this = pipController;
                            pluginId = str;
                            delegate = installerDelegate;
                        } catch (Exception e2) {
                            e = e2;
                            exc = e;
                            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-58132759303993L) + str + Deobfuscator$exteraGramDev$TMessagesProj.getString(-58351802636089L) + list, exc);
                            pipController.restoreRegistry(mapSnapshotRegistry);
                            pipController.removeOrphanedDirectories();
                            throw exc;
                        }
                    }
                }
            }
            pipController = this;
            str = pluginId;
            pipController.updateRegistryForPlugin(str, linkedHashSet);
            for (Pair<String, String> pair : linkedHashSet) {
                String absolutePath = pipController.getLibPath(pair.component1(), pair.component2()).getAbsolutePath();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-58042564990777L);
                arrayList.add(absolutePath);
            }
            pipController.saveRegistry();
            return arrayList;
        } catch (Exception e3) {
            e = e3;
            pipController = this;
            str = pluginId;
        }
    }

    private final void resolveAndInstall(String pkg, List<Pair<String, String>> specs, Set<Pair<String, String>> installedAccumulator, String pluginId, InstallerDelegate delegate) throws IOException {
        Object obj;
        Map.Entry entry;
        String strInstallPackage;
        Set<Map.Entry<String, Set<String>>> setEntrySet;
        Object next;
        PipController pipController = this;
        Set<Pair<String, String>> set = installedAccumulator;
        String str = pluginId;
        InstallerDelegate installerDelegate = delegate;
        if (installerDelegate != null && installerDelegate.isCancelled()) {
            Model$$ExternalSyntheticBUOutline0.m1247m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-58364687537977L));
            return;
        }
        ConcurrentHashMap<String, Object> concurrentHashMap = installLocks;
        final Function1 function1 = new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return PipController.$r8$lambda$nM3N8RYnbiCU76VyVnmjZ3F9qY8((String) obj2);
            }
        };
        Object objComputeIfAbsent = concurrentHashMap.computeIfAbsent(pkg, new Function() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return function1.invoke(obj2);
            }
        });
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-58463471785785L);
        synchronized (objComputeIfAbsent) {
            if (installerDelegate != null) {
                try {
                    if (installerDelegate.isCancelled()) {
                        throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-58553666099001L));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            ConcurrentHashMap<String, Set<String>> concurrentHashMap2 = registry.get(pkg);
            obj = null;
            if (concurrentHashMap2 == null || (setEntrySet = concurrentHashMap2.entrySet()) == null) {
                entry = null;
            } else {
                Iterator<T> it = setEntrySet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    Object value = ((Map.Entry) next).getValue();
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-58652450346809L);
                    if (!((Collection) value).isEmpty()) {
                        break;
                    }
                }
                entry = (Map.Entry) next;
            }
            if (entry != null) {
                Object key = entry.getKey();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-58725464790841L);
                strInstallPackage = (String) key;
                Object value2 = entry.getValue();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-58789889300281L);
                String strJoinToString$default = CollectionsKt.joinToString$default((Iterable) value2, Deobfuscator$exteraGramDev$TMessagesProj.getString(-58862903744313L), null, null, 0, null, null, 62, null);
                if (!INSTANCE.checkVersionSatisfies(strInstallPackage, specs)) {
                    throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-59189321258809L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59300990408505L) + strInstallPackage + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59382594787129L) + strJoinToString$default + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59429839427385L) + (!specs.isEmpty() ? CollectionsKt.joinToString$default(specs, Deobfuscator$exteraGramDev$TMessagesProj.getString(-59142076618553L), null, null, 0, null, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda5
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return PipController.resolveAndInstall$lambda$2$1((Pair) obj2);
                        }
                    }, 30, null) : Deobfuscator$exteraGramDev$TMessagesProj.getString(-59150666553145L)) + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59571573348153L));
                }
                FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-58875788646201L) + strInstallPackage + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59043292370745L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59064767207225L) + strJoinToString$default + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59112011847481L) + str);
            } else {
                PipController pipController2 = INSTANCE;
                String strFindInstalledVersion = pipController2.findInstalledVersion(pkg, specs);
                if (strFindInstalledVersion != null) {
                    strInstallPackage = strFindInstalledVersion;
                } else {
                    strFindInstalledVersion = pipController2.findVersionOnDisk(pkg, specs);
                    if (strFindInstalledVersion != null) {
                        FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-59584458250041L) + pkg + ' ' + strFindInstalledVersion + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59678947530553L));
                        strInstallPackage = strFindInstalledVersion;
                    } else {
                        strInstallPackage = pipController2.installPackage(pkg, specs, installerDelegate);
                    }
                }
            }
        }
        Iterator<T> it2 = set.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next2 = it2.next();
            if (Intrinsics.areEqual(((Pair) next2).getFirst(), pkg)) {
                obj = next2;
                break;
            }
        }
        Pair pair = (Pair) obj;
        if (pair != null) {
            String str2 = (String) pair.getSecond();
            if (VersionComparator.INSTANCE.compare(strInstallPackage, str2) <= 0) {
                return;
            }
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-59764846876473L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59876516026169L) + str2 + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59906580797241L) + strInstallPackage + Deobfuscator$exteraGramDev$TMessagesProj.getString(-59928055633721L) + str);
            set.remove(pair);
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, Set<String>>> concurrentHashMap3 = registry;
        final Function1 function12 = new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return PipController.m2535$r8$lambda$cKu7WIfB65dGzootwJY_PzblQ((String) obj2);
            }
        };
        ConcurrentHashMap<String, Set<String>> concurrentHashMapComputeIfAbsent = concurrentHashMap3.computeIfAbsent(pkg, new Function() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return PipController.m2532$r8$lambda$4GaAqKHHkDxhUEp6Ixv2955PQ8(function12, obj2);
            }
        });
        final Function1 function13 = new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return PipController.$r8$lambda$ba_CzsM0FMRcDjro_ht7norbT1M((String) obj2);
            }
        };
        concurrentHashMapComputeIfAbsent.computeIfAbsent(strInstallPackage, new Function() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return PipController.$r8$lambda$XFN9dt32lYY3xCJ9NHZLBcosePw(function13, obj2);
            }
        }).add(str);
        set.add(TuplesKt.m884to(pkg, strInstallPackage));
        File fileFindMetadataFile = pipController.findMetadataFile(pkg, strInstallPackage);
        if (fileFindMetadataFile == null || !fileFindMetadataFile.exists()) {
            return;
        }
        for (String str3 : pipController.parseDependenciesFromMetadata(fileFindMetadataFile)) {
            if (installerDelegate != null && installerDelegate.isCancelled()) {
                Model$$ExternalSyntheticBUOutline0.m1247m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-59953825437497L));
                return;
            }
            Pair<String, List<Pair<String, String>>> requirement = pipController.parseRequirement(str3);
            pipController.resolveAndInstall(pipController.normalizePackageName(requirement.component1()), requirement.component2(), set, str, installerDelegate);
            pipController = this;
            set = installedAccumulator;
            str = pluginId;
            installerDelegate = delegate;
        }
    }

    public static Object $r8$lambda$nM3N8RYnbiCU76VyVnmjZ3F9qY8(String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-64983232141113L);
        return new Object();
    }

    public static final CharSequence resolveAndInstall$lambda$2$1(Pair pair) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-64996117043001L);
        return ((String) pair.getFirst()) + ((String) pair.getSecond());
    }

    /* JADX INFO: renamed from: $r8$lambda$4GaAqKHHkDxhUEp6Ixv2955-PQ8 */
    public static ConcurrentHashMap m2532$r8$lambda$4GaAqKHHkDxhUEp6Ixv2955PQ8(Function1 function1, Object obj) {
        return (ConcurrentHashMap) function1.invoke(obj);
    }

    /* JADX INFO: renamed from: $r8$lambda$cKu7WIfB65dGzoot-wJY-_PzblQ */
    public static ConcurrentHashMap m2535$r8$lambda$cKu7WIfB65dGzootwJY_PzblQ(String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65009001944889L);
        return new ConcurrentHashMap();
    }

    public static Set $r8$lambda$XFN9dt32lYY3xCJ9NHZLBcosePw(Function1 function1, Object obj) {
        return (Set) function1.invoke(obj);
    }

    public static Set $r8$lambda$ba_CzsM0FMRcDjro_ht7norbT1M(String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65021886846777L);
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    private final void updateRegistryForPlugin(String pluginId, Set<Pair<String, String>> currentlyNeeded) {
        for (Map.Entry<String, ConcurrentHashMap<String, Set<String>>> entry : registry.entrySet()) {
            String key = entry.getKey();
            for (Map.Entry<String, Set<String>> entry2 : entry.getValue().entrySet()) {
                String key2 = entry2.getKey();
                Set<String> value = entry2.getValue();
                Pair pairM884to = TuplesKt.m884to(key, key2);
                if (value.contains(pluginId) && !currentlyNeeded.contains(pairM884to)) {
                    value.remove(pluginId);
                }
            }
        }
    }

    private final void cleanupInternal() throws IOException {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, ConcurrentHashMap<String, Set<String>>> entry : registry.entrySet()) {
            String key = entry.getKey();
            for (Map.Entry<String, Set<String>> entry2 : entry.getValue().entrySet()) {
                String key2 = entry2.getKey();
                if (entry2.getValue().isEmpty()) {
                    arrayList.add(TuplesKt.m884to(key, key2));
                }
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Pair pair = (Pair) obj;
            String str = (String) pair.component1();
            String str2 = (String) pair.component2();
            ConcurrentHashMap<String, ConcurrentHashMap<String, Set<String>>> concurrentHashMap = registry;
            ConcurrentHashMap<String, Set<String>> concurrentHashMap2 = concurrentHashMap.get(str);
            if (concurrentHashMap2 != null) {
                concurrentHashMap2.remove(str2);
            }
            ConcurrentHashMap<String, Set<String>> concurrentHashMap3 = concurrentHashMap.get(str);
            if (concurrentHashMap3 != null && concurrentHashMap3.isEmpty()) {
                concurrentHashMap.remove(str);
            }
            INSTANCE.deletePackage(str, str2);
        }
        removeOrphanedDirectories();
    }

    private final void removeOrphanedDirectories() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Map.Entry<String, ConcurrentHashMap<String, Set<String>>> entry : registry.entrySet()) {
            String key = entry.getKey();
            Set<String> setKeySet = entry.getValue().keySet();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-60052609685305L);
            Iterator<T> it = setKeySet.iterator();
            while (it.hasNext()) {
                try {
                    linkedHashSet.add(INSTANCE.getLibPath(key, (String) it.next()).getCanonicalPath());
                } catch (IOException unused) {
                }
            }
        }
        File[] fileArrListFiles = getLibsDir().listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    try {
                        if (!linkedHashSet.contains(file.getCanonicalPath())) {
                            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60121329162041L) + file.getName());
                            SimpliFiles.directory(file).deleteRecursively();
                        }
                    } catch (Exception e) {
                        FileLog.m1048e(e);
                    }
                }
            }
        }
    }

    public final void uninstallDependencies(String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-60306012755769L);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        boolean z = false;
        for (Map.Entry<String, ConcurrentHashMap<String, Set<String>>> entry : registry.entrySet()) {
            String key = entry.getKey();
            for (Map.Entry<String, Set<String>> entry2 : entry.getValue().entrySet()) {
                String key2 = entry2.getKey();
                Set<String> value = entry2.getValue();
                if (value.remove(pluginId)) {
                    if (value.isEmpty()) {
                        arrayList.add(TuplesKt.m884to(key, key2));
                    }
                    z = true;
                }
            }
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Pair pair = (Pair) obj;
            String str = (String) pair.component1();
            String str2 = (String) pair.component2();
            ConcurrentHashMap<String, ConcurrentHashMap<String, Set<String>>> concurrentHashMap = registry;
            ConcurrentHashMap<String, Set<String>> concurrentHashMap2 = concurrentHashMap.get(str);
            if (concurrentHashMap2 != null) {
                concurrentHashMap2.remove(str2);
            }
            ConcurrentHashMap<String, Set<String>> concurrentHashMap3 = concurrentHashMap.get(str);
            if (concurrentHashMap3 != null && concurrentHashMap3.isEmpty()) {
                concurrentHashMap.remove(str);
            }
            INSTANCE.deletePackage(str, str2);
        }
        if (z) {
            saveRegistry();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$iez_jk-cSyBPRjplKRoDAzlgN2I */
    public static CharSequence m2536$r8$lambda$iez_jkcSyBPRjplKRoDAzlgN2I(Pair pair) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65034771748665L);
        return ((String) pair.getFirst()) + ((String) pair.getSecond());
    }

    /* JADX WARN: Finally extract failed */
    private final String installPackage(String pkg, List<Pair<String, String>> specs, final InstallerDelegate delegate) throws IOException {
        Object objM3494constructorimpl;
        Object objM3494constructorimpl2;
        FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60391912101689L) + pkg + ' ' + (!specs.isEmpty() ? CollectionsKt.joinToString$default(specs, Deobfuscator$exteraGramDev$TMessagesProj.getString(-60344667461433L), null, null, 0, null, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PipController.m2536$r8$lambda$iez_jkcSyBPRjplKRoDAzlgN2I((Pair) obj);
            }
        }, 30, null) : Deobfuscator$exteraGramDev$TMessagesProj.getString(-60353257396025L)));
        if (delegate != null) {
            delegate.onProgress(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60507876218681L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-60555120858937L));
        }
        Response responseExecuteWithRetry = executeWithRetry(new Request.Builder().url(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60572300728121L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-60671084975929L)).build(), delegate);
        try {
            if (!responseExecuteWithRetry.getIsSuccessful()) {
                if (responseExecuteWithRetry.code() == 404) {
                    throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60696854779705L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-60735509485369L));
                }
                throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60817113863993L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-60945962882873L) + responseExecuteWithRetry.code());
            }
            JsonObject jsonObject = (JsonObject) gson.fromJson(responseExecuteWithRetry.body().string(), JsonObject.class);
            JsonObject asJsonObject = jsonObject.getAsJsonObject(Deobfuscator$exteraGramDev$TMessagesProj.getString(-60958847784761L));
            Set<String> setKeySet = asJsonObject.keySet();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-60997502490425L);
            List list = CollectionsKt.toList(setKeySet);
            PipController pipController = INSTANCE;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (INSTANCE.checkVersionSatisfies((String) obj, specs)) {
                    arrayList.add(obj);
                }
            }
            WheelCandidate wheelCandidate = null;
            for (String str : CollectionsKt.sortedWith(pipController.filterPreReleases(arrayList, specs), new Comparator() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return PipController.installPackage$lambda$1$1((String) obj2, (String) obj3);
                }
            })) {
                JsonArray asJsonArray = asJsonObject.getAsJsonArray(str);
                if (asJsonArray != null) {
                    Iterator<JsonElement> it = asJsonArray.iterator();
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-61049042097977L);
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        WheelCandidate wheelCandidateSelectWheelCandidate = INSTANCE.selectWheelCandidate(str, it.next().getAsJsonObject());
                        if (wheelCandidateSelectWheelCandidate != null) {
                            wheelCandidate = wheelCandidateSelectWheelCandidate;
                            break;
                        }
                    }
                    if (wheelCandidate != null) {
                        break;
                    }
                }
            }
            if (wheelCandidate == null) {
                JsonObject asJsonObject2 = jsonObject.getAsJsonObject(Deobfuscator$exteraGramDev$TMessagesProj.getString(-61109171640121L));
                String stringOrNull = asJsonObject2 != null ? INSTANCE.getStringOrNull(asJsonObject2, Deobfuscator$exteraGramDev$TMessagesProj.getString(-61130646476601L)) : null;
                if (stringOrNull != null) {
                    PipController pipController2 = INSTANCE;
                    if (!pipController2.checkVersionSatisfies(pythonVersion, pipController2.parseSpecs(stringOrNull))) {
                        throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-61199365953337L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-61238020659001L) + stringOrNull + Deobfuscator$exteraGramDev$TMessagesProj.getString(-61315330070329L) + pythonVersion);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(responseExecuteWithRetry, null);
            if (wheelCandidate == null) {
                throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-61392639481657L) + pkg + Deobfuscator$exteraGramDev$TMessagesProj.getString(-61530078435129L));
            }
            File libPath = getLibPath(pkg, wheelCandidate.getVersion());
            File file = SimpliFiles.directory(new File(getLibsDir(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-61688992225081L) + pkg + '_' + System.currentTimeMillis())).create().getFile();
            File file2 = new File(file, Deobfuscator$exteraGramDev$TMessagesProj.getString(-61710467061561L));
            try {
                if (delegate != null) {
                    try {
                        delegate.onProgress(Deobfuscator$exteraGramDev$TMessagesProj.getString(-61753416734521L) + pkg + ' ' + wheelCandidate.getVersion() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-61809251309369L));
                    } catch (Exception e) {
                        FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-62337532286777L) + pkg, e);
                        throw e;
                    }
                }
                File file3 = new File(file, Deobfuscator$exteraGramDev$TMessagesProj.getString(-61826431178553L));
                responseExecuteWithRetry = executeWithRetry(new Request.Builder().url(wheelCandidate.getDownloadUrl()).build(), delegate);
                try {
                    if (!responseExecuteWithRetry.getIsSuccessful()) {
                        throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-61877970786105L) + responseExecuteWithRetry.code());
                    }
                    SimpliFile.writeFrom$default(SimpliFiles.file(file3), responseExecuteWithRetry.body().byteStream(), 0L, 2, null);
                    CloseableKt.closeFinally(responseExecuteWithRetry, null);
                    if (wheelCandidate.getExpectedSha256() != null && !StringsKt.equals(calculateSha256(file3), wheelCandidate.getExpectedSha256(), true)) {
                        throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-61955280197433L));
                    }
                    if (delegate != null) {
                        delegate.onProgress(Deobfuscator$exteraGramDev$TMessagesProj.getString(-62032589608761L) + pkg + ' ' + wheelCandidate.getVersion() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-62084129216313L));
                    }
                    SimpliFiles.archive(file3).withPolicy(wheelArchivePolicy).extractToDirectory(file2, ArchiveExtractionOptions.INSTANCE.builder().cancellationToken(new CancellationToken() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda2
                        @Override // org.simplifiles.archive.CancellationToken
                        public final boolean isCancellationRequested() {
                            return PipController.$r8$lambda$WCaEarcnhdtxiKzUVomzfeStLN0(delegate);
                        }
                    }).targetPolicy(ExtractionTargetPolicy.REPLACE).build());
                    if (libPath.exists()) {
                        SimpliFiles.directory(libPath).deleteRecursively();
                    }
                    SimpliFiles.directory(file2).moveTo(libPath, OverwritePolicy.REPLACE);
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objM3494constructorimpl2 = Result.m3494constructorimpl(Boolean.valueOf(SimpliFiles.directory(file).deleteRecursively()));
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM3494constructorimpl2 = Result.m3494constructorimpl(ResultKt.createFailure(th));
                    }
                    Throwable thM3497exceptionOrNullimpl = Result.m3497exceptionOrNullimpl(objM3494constructorimpl2);
                    if (thM3497exceptionOrNullimpl != null) {
                        FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-62101309085497L), thM3497exceptionOrNullimpl);
                    }
                    return wheelCandidate.getVersion();
                } finally {
                    try {
                        throw th;
                    } finally {
                    }
                }
            } catch (Throwable th2) {
                try {
                    Result.Companion companion3 = Result.INSTANCE;
                    objM3494constructorimpl = Result.m3494constructorimpl(Boolean.valueOf(SimpliFiles.directory(file).deleteRecursively()));
                } catch (Throwable th3) {
                    Result.Companion companion4 = Result.INSTANCE;
                    objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th3));
                }
                Throwable thM3497exceptionOrNullimpl2 = Result.m3497exceptionOrNullimpl(objM3494constructorimpl);
                if (thM3497exceptionOrNullimpl2 == null) {
                    throw th2;
                }
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-62522215880505L), thM3497exceptionOrNullimpl2);
                throw th2;
            }
        } catch (Throwable th4) {
        }
    }

    public static final int installPackage$lambda$1$1(String str, String str2) {
        return VersionComparator.INSTANCE.compare(str2, str);
    }

    public static boolean $r8$lambda$WCaEarcnhdtxiKzUVomzfeStLN0(InstallerDelegate installerDelegate) {
        return installerDelegate != null && installerDelegate.isCancelled();
    }

    private final WheelCandidate selectWheelCandidate(String version, JsonObject artifact) {
        String stringOrNull;
        String stringOrNull2;
        String stringOrNull3 = getStringOrNull(artifact, Deobfuscator$exteraGramDev$TMessagesProj.getString(-62758439081785L));
        if (stringOrNull3 == null || !Intrinsics.areEqual(stringOrNull3, Deobfuscator$exteraGramDev$TMessagesProj.getString(-62809978689337L)) || (stringOrNull = getStringOrNull(artifact, Deobfuscator$exteraGramDev$TMessagesProj.getString(-62861518296889L))) == null || !isPurePythonWheelCompatible(stringOrNull)) {
            return null;
        }
        String stringOrNull4 = getStringOrNull(artifact, Deobfuscator$exteraGramDev$TMessagesProj.getString(-62900173002553L));
        if ((stringOrNull4 != null && !checkVersionSatisfies(pythonVersion, parseSpecs(stringOrNull4))) || (stringOrNull2 = getStringOrNull(artifact, Deobfuscator$exteraGramDev$TMessagesProj.getString(-62968892479289L))) == null) {
            return null;
        }
        JsonObject asJsonObject = artifact.getAsJsonObject(Deobfuscator$exteraGramDev$TMessagesProj.getString(-62986072348473L));
        return new WheelCandidate(version, stringOrNull2, asJsonObject != null ? getStringOrNull(asJsonObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63020432086841L)) : null);
    }

    private final String getStringOrNull(JsonObject jsonObject, String str) {
        JsonElement jsonElement;
        if (!jsonObject.has(str) || (jsonElement = jsonObject.get(str)) == null || jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement.getAsString();
    }

    private final boolean isPurePythonWheelCompatible(String filename) {
        if (!StringsKt.endsWith$default(filename, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63050496857913L), false, 2, (Object) null)) {
            return false;
        }
        List listSplit$default = StringsKt.split$default((CharSequence) StringsKt.removeSuffix(filename, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-63071971694393L)), new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-63093446530873L)}, false, 0, 6, (Object) null);
        if (listSplit$default.size() < 5) {
            return false;
        }
        String str = (String) listSplit$default.get(listSplit$default.size() - 3);
        String str2 = (String) listSplit$default.get(listSplit$default.size() - 2);
        String str3 = (String) listSplit$default.get(listSplit$default.size() - 1);
        if (Intrinsics.areEqual(str2, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63102036465465L)) && Intrinsics.areEqual(str3, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63123511301945L))) {
            List listSplit$default2 = StringsKt.split$default((CharSequence) str, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-63140691171129L)}, false, 0, 6, (Object) null);
            if ((listSplit$default2 instanceof Collection) && listSplit$default2.isEmpty()) {
                return false;
            }
            Iterator it = listSplit$default2.iterator();
            while (it.hasNext()) {
                if (INSTANCE.isPythonTagCompatible((String) it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isPythonTagCompatible(String tag) {
        Integer intOrNull;
        Integer intOrNull2;
        List listSplit$default = StringsKt.split$default((CharSequence) pythonVersion, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-63149281105721L)}, false, 0, 6, (Object) null);
        String str = (String) CollectionsKt.getOrNull(listSplit$default, 0);
        if (str != null && (intOrNull = StringsKt.toIntOrNull(str)) != null) {
            int iIntValue = intOrNull.intValue();
            String str2 = (String) CollectionsKt.getOrNull(listSplit$default, 1);
            if (str2 != null && (intOrNull2 = StringsKt.toIntOrNull(str2)) != null) {
                int iIntValue2 = intOrNull2.intValue();
                String lowerCase = tag.toLowerCase(Locale.ROOT);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-63157871040313L);
                if (Intrinsics.areEqual(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63230885484345L) + iIntValue)) {
                    return true;
                }
                if (Intrinsics.areEqual(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63243770386233L) + iIntValue + iIntValue2)) {
                    return true;
                }
                if (Intrinsics.areEqual(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-63256655288121L) + iIntValue + iIntValue2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final Response executeWithRetry(Request request, InstallerDelegate delegate) throws IOException {
        int i = 0;
        IOException e = null;
        while (i < 3) {
            if (delegate != null && delegate.isCancelled()) {
                Model$$ExternalSyntheticBUOutline0.m1247m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-63269540190009L));
                return null;
            }
            try {
                return client.newCall(request).execute();
            } catch (IOException e2) {
                e = e2;
                i++;
                FileLog.m1049w(Deobfuscator$exteraGramDev$TMessagesProj.getString(-63368324437817L) + i + Deobfuscator$exteraGramDev$TMessagesProj.getString(-63544418096953L) + e.getMessage());
                try {
                    Thread.sleep(((long) i) * 1000);
                } catch (InterruptedException unused) {
                }
            }
        }
        if (delegate != null && delegate.isCancelled()) {
            Model$$ExternalSyntheticBUOutline0.m1247m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-63570187900729L));
            return null;
        }
        if (e != null) {
            throw e;
        }
        throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-63668972148537L));
    }

    private final String findVersionOnDisk(String pkg, final List<Pair<String, String>> specs) {
        Sequence sequenceAsSequence;
        Sequence sequenceFilter;
        Sequence map;
        Sequence sequenceFilter2;
        List<String> list;
        final String str = pkg + SignatureVisitor.SUPER;
        File[] fileArrListFiles = getLibsDir().listFiles();
        if (fileArrListFiles == null || (sequenceAsSequence = ArraysKt.asSequence(fileArrListFiles)) == null || (sequenceFilter = SequencesKt.filter(sequenceAsSequence, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(PipController.m2531$r8$lambda$3qb6Nezx6TG2Tac1y7Xa35y3cA(str, (File) obj));
            }
        })) == null || (map = SequencesKt.map(sequenceFilter, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PipController.m2534$r8$lambda$LmFvS3nVC4K08Dx0OuVVeARUQM(str, (File) obj);
            }
        })) == null || (sequenceFilter2 = SequencesKt.filter(map, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(PipController.m2533$r8$lambda$GBc8aKnKaBWkdqFW6gQZ9PEPeQ(specs, (String) obj));
            }
        })) == null || (list = SequencesKt.toList(sequenceFilter2)) == null) {
            return null;
        }
        return (String) CollectionsKt.maxWithOrNull(filterPreReleases(list, specs), VersionComparator.INSTANCE);
    }

    /* JADX INFO: renamed from: $r8$lambda$3qb6Nezx6TG2Tac-1y7Xa35y3cA */
    public static boolean m2531$r8$lambda$3qb6Nezx6TG2Tac1y7Xa35y3cA(String str, File file) {
        if (file.isDirectory()) {
            String name = file.getName();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-65047656650553L);
            if (StringsKt.startsWith$default(name, str, false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: $r8$lambda$Lm-FvS3nVC4K08Dx0OuVVeARUQM */
    public static String m2534$r8$lambda$LmFvS3nVC4K08Dx0OuVVeARUQM(String str, File file) {
        String name = file.getName();
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65103491225401L);
        String strSubstring = name.substring(str.length());
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65159325800249L);
        return strSubstring;
    }

    /* JADX INFO: renamed from: $r8$lambda$GBc-8aKnKaBWkdqFW6gQZ9PEPeQ */
    public static boolean m2533$r8$lambda$GBc8aKnKaBWkdqFW6gQZ9PEPeQ(List list, String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65223750309689L);
        return str.length() > 0 && INSTANCE.checkVersionSatisfies(str, list);
    }

    private final String findInstalledVersion(String pkg, List<Pair<String, String>> specs) {
        ConcurrentHashMap<String, Set<String>> concurrentHashMap = registry.get(pkg);
        if (concurrentHashMap == null) {
            return null;
        }
        Set<String> setKeySet = concurrentHashMap.keySet();
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-63763461429049L);
        ArrayList arrayList = new ArrayList();
        for (Object obj : setKeySet) {
            if (INSTANCE.checkVersionSatisfies((String) obj, specs)) {
                arrayList.add(obj);
            }
        }
        return (String) CollectionsKt.maxWithOrNull(filterPreReleases(arrayList, specs), VersionComparator.INSTANCE);
    }

    private final File getLibPath(String pkg, String version) throws IOException {
        if (StringsKt.contains$default((CharSequence) version, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-63832180905785L), false, 2, (Object) null) || StringsKt.contains$default((CharSequence) version, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-63840770840377L), false, 2, (Object) null) || StringsKt.contains$default((CharSequence) version, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-63849360774969L), false, 2, (Object) null)) {
            throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-63862245676857L) + version);
        }
        return new File(getLibsDir(), pkg + SignatureVisitor.SUPER + version);
    }

    private final void deletePackage(String pkg, String version) throws IOException {
        Object objM3494constructorimpl;
        File libPath = getLibPath(pkg, version);
        if (libPath.exists()) {
            try {
                Result.Companion companion = Result.INSTANCE;
                SimpliFiles.directory(libPath).deleteRecursively();
                FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-63939555088185L) + libPath.getName());
                objM3494constructorimpl = Result.m3494constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM3497exceptionOrNullimpl = Result.m3497exceptionOrNullimpl(objM3494constructorimpl);
            if (thM3497exceptionOrNullimpl != null) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64076994041657L) + libPath.getName(), thM3497exceptionOrNullimpl);
            }
        }
    }

    private final Pair<String, List<Pair<String, String>>> parseRequirement(String req) {
        String string = StringsKt.trim((CharSequence) REGEX_REQ_PAREN.replace(StringsKt.trim((CharSequence) REGEX_REQ_EXTRA.replace(StringsKt.trim((CharSequence) StringsKt.split$default((CharSequence) req, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-64253087700793L)}, false, 0, 6, (Object) null).get(0)).toString(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-64261677635385L))).toString(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-64265972602681L))).toString();
        MatchResult matchResultFind$default = Regex.find$default(REGEX_REQ_PARSE, string, 0, 2, null);
        if (matchResultFind$default == null) {
            return TuplesKt.m884to(string, CollectionsKt.emptyList());
        }
        MatchResult.Destructured destructured = matchResultFind$default.getDestructured();
        return TuplesKt.m884to(destructured.getMatch().getGroupValues().get(1), parseSpecs(destructured.getMatch().getGroupValues().get(2)));
    }

    private final List<Pair<String, String>> parseSpecs(String specsString) {
        ArrayList arrayList = new ArrayList();
        if (!StringsKt.isBlank(specsString)) {
            Iterator it = StringsKt.split$default((CharSequence) specsString, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-64270267569977L)}, false, 0, 6, (Object) null).iterator();
            while (it.hasNext()) {
                MatchResult matchResultFind$default = Regex.find$default(REGEX_REQ_SPECS, StringsKt.trim((CharSequence) it.next()).toString(), 0, 2, null);
                if (matchResultFind$default != null) {
                    MatchResult.Destructured destructured = matchResultFind$default.getDestructured();
                    arrayList.add(TuplesKt.m884to(destructured.getMatch().getGroupValues().get(1), StringsKt.trim((CharSequence) destructured.getMatch().getGroupValues().get(2)).toString()));
                }
            }
        }
        return arrayList;
    }

    private final List<String> parseDependenciesFromMetadata(File metadataFile) {
        final ArrayList arrayList = new ArrayList();
        try {
            SimpliFile.forEachLine$default(SimpliFiles.file(metadataFile), 4194304L, null, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda10
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PipController.$r8$lambda$3nEd9lhKtncIBzZ9CAoypYcXEk4(arrayList, (String) obj);
                }
            }, 2, null);
            return arrayList;
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64278857504569L), e);
            return arrayList;
        }
    }

    public static Unit $r8$lambda$3nEd9lhKtncIBzZ9CAoypYcXEk4(List list, String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65258110048057L);
        if (StringsKt.startsWith$default(str, Deobfuscator$exteraGramDev$TMessagesProj.getString(-65279584884537L), false, 2, (Object) null)) {
            String strSubstring = str.substring(14);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-65344009393977L);
            String string = StringsKt.trim((CharSequence) strSubstring).toString();
            if (StringsKt.contains$default((CharSequence) string, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-65408433903417L), false, 2, (Object) null)) {
                List listSplit$default = StringsKt.split$default((CharSequence) string, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-65417023838009L)}, false, 2, 2, (Object) null);
                if (!INSTANCE.isMarkerCompatible(StringsKt.trim((CharSequence) listSplit$default.get(1)).toString())) {
                    return Unit.INSTANCE;
                }
                list.add(StringsKt.trim((CharSequence) listSplit$default.get(0)).toString());
            } else {
                list.add(string);
            }
        }
        return Unit.INSTANCE;
    }

    private final boolean isMarkerCompatible(String marker) {
        return new MarkerParser(marker).parse();
    }

    private final File findMetadataFile(String pkg, String version) {
        File[] fileArrListFiles = getLibPath(pkg, version).listFiles(new FilenameFilter() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda11
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return StringsKt.endsWith$default(str, Deobfuscator$exteraGramDev$TMessagesProj.getString(-65425613772601L), false, 2, (Object) null);
            }
        });
        File file = fileArrListFiles != null ? (File) ArraysKt.firstOrNull(fileArrListFiles) : null;
        if (file != null) {
            return new File(file, Deobfuscator$exteraGramDev$TMessagesProj.getString(-64450656196409L));
        }
        return null;
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000bH\u0002J\b\u0010\u000e\u001a\u00020\u000bH\u0002J\b\u0010\u000f\u001a\u00020\u000bH\u0002J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u0002J \u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003H\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00032\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u0002J\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003H\u0002J\u0010\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0003H\u0002J\u0010\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0003H\u0002J\u0010\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0003H\u0002J\n\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u0002J\u0014\u0010 \u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010!\u001a\u00020\tH\u0002R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, m877d2 = {"Lcom/exteragram/messenger/plugins/pip/PipController$MarkerParser;", _UrlKt.FRAGMENT_ENCODE_SET, "marker", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;)V", "tokens", _UrlKt.FRAGMENT_ENCODE_SET, "position", _UrlKt.FRAGMENT_ENCODE_SET, "parse", _UrlKt.FRAGMENT_ENCODE_SET, "parseOr", "parseAnd", "parseFactor", "parseComparison", "nextOperator", "evaluateComparison", "left", "operator", "right", "markerVariableName", "token", "markerValue", "name", "invertVersionOperator", "unquote", "value", "match", "expected", "matchKeyword", "next", "peek", "offset", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class MarkerParser {
        private int position;
        private final List<String> tokens;

        public MarkerParser(String str) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-54404727691065L);
            this.tokens = SequencesKt.toList(SequencesKt.map(Regex.findAll$default(PipController.REGEX_MARKER_TOKEN, str, 0, 2, null), new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$MarkerParser$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PipController.MarkerParser.$r8$lambda$2nxjO8cTPCazwOsauAZxX68kyv8((MatchResult) obj);
                }
            }));
        }

        public static String $r8$lambda$2nxjO8cTPCazwOsauAZxX68kyv8(MatchResult matchResult) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-56487786829625L);
            return matchResult.getValue();
        }

        public final boolean parse() {
            if (this.tokens.isEmpty()) {
                return true;
            }
            try {
                return parseOr();
            } catch (Exception e) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54434792462137L) + CollectionsKt.joinToString$default(this.tokens, Deobfuscator$exteraGramDev$TMessagesProj.getString(-54619476055865L), null, null, 0, null, null, 62, null), e);
                return true;
            }
        }

        private final boolean parseOr() {
            boolean and = parseAnd();
            while (matchKeyword(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54628065990457L))) {
                and = and || parseAnd();
            }
            return and;
        }

        private final boolean parseAnd() {
            boolean factor = parseFactor();
            while (matchKeyword(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54640950892345L))) {
                factor = factor && parseFactor();
            }
            return factor;
        }

        private final boolean parseFactor() {
            if (match(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54658130761529L))) {
                boolean or = parseOr();
                match(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54666720696121L));
                return or;
            }
            return parseComparison();
        }

        private final boolean parseComparison() {
            String strNextOperator;
            String next;
            String next2 = next();
            if (next2 == null || (strNextOperator = nextOperator()) == null || (next = next()) == null) {
                return true;
            }
            return evaluateComparison(next2, strNextOperator, next);
        }

        private final String nextOperator() {
            String lowerCase;
            String strPeek$default = peek$default(this, 0, 1, null);
            if (strPeek$default == null) {
                return null;
            }
            Locale locale = Locale.ROOT;
            String lowerCase2 = strPeek$default.toLowerCase(locale);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-54675310630713L);
            if (Intrinsics.areEqual(lowerCase2, Deobfuscator$exteraGramDev$TMessagesProj.getString(-54748325074745L))) {
                String strPeek = peek(1);
                if (strPeek != null) {
                    lowerCase = strPeek.toLowerCase(locale);
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-54765504943929L);
                } else {
                    lowerCase = null;
                }
                if (Intrinsics.areEqual(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-54838519387961L))) {
                    this.position += 2;
                    return Deobfuscator$exteraGramDev$TMessagesProj.getString(-54851404289849L);
                }
            }
            int iHashCode = lowerCase2.hashCode();
            if (iHashCode == 60 ? !lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54933008668473L)) : !(iHashCode == 62 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54958778472249L)) : iHashCode == 1084 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54920123766585L)) : iHashCode == 1921 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54894353962809L)) : iHashCode == 1952 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54881469060921L)) : iHashCode == 1983 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54967368406841L)) : iHashCode == 3365 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54907238864697L)) : iHashCode == 3967 ? lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54980253308729L)) : iHashCode == 60573 && lowerCase2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-54941598603065L)))) {
                return null;
            }
            this.position++;
            return lowerCase2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:128:0x00c6, code lost:
        
            if (r9.equals(org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj.getString(-55362505398073L)) == false) goto L149;
         */
        /* JADX WARN: Code restructure failed: missing block: B:136:0x00eb, code lost:
        
            if (r9.equals(org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj.getString(-55293785921337L)) == false) goto L149;
         */
        /* JADX WARN: Code restructure failed: missing block: B:139:0x00f2, code lost:
        
            return kotlin.text.StringsKt.equals(r2, r8, true);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private final boolean evaluateComparison(java.lang.String r8, java.lang.String r9, java.lang.String r10) {
            /*
                Method dump skipped, instruction units count: 291
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.pip.PipController.MarkerParser.evaluateComparison(java.lang.String, java.lang.String, java.lang.String):boolean");
        }

        private final String markerVariableName(String token) {
            if (!StringsKt.startsWith$default(token, Deobfuscator$exteraGramDev$TMessagesProj.getString(-55379685267257L), false, 2, (Object) null) && !StringsKt.startsWith$default(token, Deobfuscator$exteraGramDev$TMessagesProj.getString(-55388275201849L), false, 2, (Object) null)) {
                String lowerCase = token.toLowerCase(Locale.ROOT);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-55396865136441L);
                if (new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55469879580473L)).matches(lowerCase)) {
                    return lowerCase;
                }
            }
            return null;
        }

        private final String markerValue(String name) {
            if (name == null) {
                return null;
            }
            switch (name.hashCode()) {
                case -1182845946:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55774822258489L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56148484413241L);
                    }
                    return null;
                case -920496011:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55684627945273L))) {
                        return CollectionsKt.joinToString$default(CollectionsKt.take(StringsKt.split$default((CharSequence) PipController.INSTANCE.getPythonVersion(), new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-56174254217017L)}, false, 0, 6, (Object) null), 2), Deobfuscator$exteraGramDev$TMessagesProj.getString(-56182844151609L), null, null, 0, null, null, 62, null);
                    }
                    return null;
                case -193632853:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-56011045459769L))) {
                        return PipController.INSTANCE.getPythonVersion();
                    }
                    return null;
                case 96965648:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55749052454713L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56260153562937L);
                    }
                    return null;
                case 117846437:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55628793370425L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56096944805689L);
                    }
                    return null;
                case 178915064:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55542894024505L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56191434086201L);
                    }
                    return null;
                case 1653682043:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55942325983033L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56122714609465L);
                    }
                    return null;
                case 1746933417:
                    if (name.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-55809181996857L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56225793824569L);
                    }
                    return null;
                default:
                    return null;
            }
        }

        private final String invertVersionOperator(String operator) {
            int iHashCode = operator.hashCode();
            if (iHashCode != 60) {
                if (iHashCode != 62) {
                    if (iHashCode != 1921) {
                        if (iHashCode == 1983 && operator.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-56294513301305L))) {
                            return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56315988137785L);
                        }
                    } else if (operator.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-56264448530233L))) {
                        return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56337462974265L);
                    }
                } else if (operator.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-56285923366713L))) {
                    return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56307398203193L);
                }
            } else if (operator.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-56277333432121L))) {
                return Deobfuscator$exteraGramDev$TMessagesProj.getString(-56328873039673L);
            }
            return operator;
        }

        private final String unquote(String value) {
            if (value.length() >= 2) {
                char cFirst = StringsKt.first(value);
                char cLast = StringsKt.last(value);
                if ((cFirst == '\'' && cLast == '\'') || (cFirst == '\"' && cLast == '\"')) {
                    String strSubstring = value.substring(1, value.length() - 1);
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-56350347876153L);
                    return strSubstring;
                }
            }
            return value;
        }

        private final boolean match(String expected) {
            if (!Intrinsics.areEqual(peek$default(this, 0, 1, null), expected)) {
                return false;
            }
            this.position++;
            return true;
        }

        private final boolean matchKeyword(String expected) {
            String lowerCase = null;
            String strPeek$default = peek$default(this, 0, 1, null);
            if (strPeek$default != null) {
                lowerCase = strPeek$default.toLowerCase(Locale.ROOT);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-56414772385593L);
            }
            if (!Intrinsics.areEqual(lowerCase, expected)) {
                return false;
            }
            this.position++;
            return true;
        }

        private final String next() {
            String strPeek$default = peek$default(this, 0, 1, null);
            if (strPeek$default == null) {
                return null;
            }
            this.position++;
            return strPeek$default;
        }

        public static /* synthetic */ String peek$default(MarkerParser markerParser, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = 0;
            }
            return markerParser.peek(i);
        }

        private final String peek(int offset) {
            return (String) CollectionsKt.getOrNull(this.tokens, this.position + offset);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x00ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkVersionSatisfies(java.lang.String r23, java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> r24) {
        /*
            Method dump skipped, instruction units count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.pip.PipController.checkVersionSatisfies(java.lang.String, java.util.List):boolean");
    }

    private final boolean isWildcardVersionSpec(String spec) {
        return REGEX_VERSION_WILDCARD.containsMatchIn(StringsKt.trim((CharSequence) spec).toString());
    }

    private final List<String> filterPreReleases(List<String> versions, List<Pair<String, String>> specs) {
        if (versions.isEmpty() || specsAllowPreRelease(specs)) {
            return versions;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : versions) {
            if (!INSTANCE.isPreReleaseVersion((String) obj)) {
                arrayList.add(obj);
            }
        }
        if (!arrayList.isEmpty()) {
            versions = arrayList;
        }
        return versions;
    }

    private final boolean specsAllowPreRelease(List<Pair<String, String>> specs) {
        List<Pair<String, String>> list = specs;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (INSTANCE.isPreReleaseVersion((String) ((Pair) it.next()).component2())) {
                return true;
            }
        }
        return false;
    }

    private final boolean isPreReleaseVersion(String version) {
        List<String> parts = parseVersion(version).getParts();
        if ((parts instanceof Collection) && parts.isEmpty()) {
            return false;
        }
        for (String str : parts) {
            switch (str.hashCode()) {
                case 97:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64605275019065L))) {
                        return true;
                    }
                    break;
                    break;
                case 98:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64626749855545L))) {
                        return true;
                    }
                    break;
                    break;
                case 99:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64635339790137L))) {
                        return true;
                    }
                    break;
                    break;
                case 3633:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64613864953657L))) {
                        return true;
                    }
                    break;
                    break;
                case 99349:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64643929724729L))) {
                        return true;
                    }
                    break;
                    break;
                case 3020272:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64686879397689L))) {
                        return true;
                    }
                    break;
                    break;
                case 92909918:
                    if (str.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64661109593913L))) {
                        return true;
                    }
                    break;
                    break;
            }
        }
        return false;
    }

    private final boolean matchesVersionWildcard(String version, String spec) {
        String strTrimEnd = StringsKt.trimEnd(StringsKt.removeSuffix(StringsKt.removeSuffix(StringsKt.removeSuffix(StringsKt.substringBefore$default(StringsKt.trim((CharSequence) spec).toString(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-64708354234169L), (String) null, 2, (Object) null), (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-64716944168761L)), (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-64729829070649L)), (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-64742713972537L)), '.', SignatureVisitor.SUPER, '_');
        ParsedVersion version2 = parseVersion(strTrimEnd);
        ParsedVersion version3 = parseVersion(version);
        if (StringsKt.contains$default((CharSequence) strTrimEnd, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-64755598874425L), false, 2, (Object) null) && version2.getEpoch() != version3.getEpoch()) {
            return false;
        }
        List<Integer> versionReleaseParts = parseVersionReleaseParts(strTrimEnd);
        if (versionReleaseParts.isEmpty()) {
            return true;
        }
        List<Integer> versionReleaseParts2 = parseVersionReleaseParts(version);
        if (versionReleaseParts2.size() < versionReleaseParts.size()) {
            return false;
        }
        Iterable indices = CollectionsKt.getIndices(versionReleaseParts);
        if ((indices instanceof Collection) && ((Collection) indices).isEmpty()) {
            return true;
        }
        Iterator it = indices.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            if (versionReleaseParts2.get(iNextInt).intValue() != versionReleaseParts.get(iNextInt).intValue()) {
                return false;
            }
        }
        return true;
    }

    private final List<Integer> parseVersionReleaseParts(String version) {
        List<String> listSplit = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64764188809017L)).split(parseVersion(version).getPublicVersion(), 0);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listSplit.iterator();
        while (it.hasNext()) {
            Integer intOrNull = StringsKt.toIntOrNull((String) it.next());
            if (intOrNull != null) {
                arrayList.add(intOrNull);
            }
        }
        return arrayList;
    }

    private final String calculateSha256(File file) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(Deobfuscator$exteraGramDev$TMessagesProj.getString(-64794253580089L));
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    messageDigest.update(bArr, 0, i);
                } else {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileInputStream, null);
                    byte[] bArrDigest = messageDigest.digest();
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-64828613318457L);
                    return ArraysKt.joinToString$default(bArrDigest, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-64880152926009L), (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda12
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return PipController.$r8$lambda$7CZkhP2Pq3JYvkGUBl8vqtaliPk(((Byte) obj).byteValue());
                        }
                    }, 30, (Object) null);
                }
            }
        } finally {
        }
    }

    public static CharSequence $r8$lambda$7CZkhP2Pq3JYvkGUBl8vqtaliPk(byte b2) {
        String str = String.format(Deobfuscator$exteraGramDev$TMessagesProj.getString(-65472858412857L), Arrays.copyOf(new Object[]{Byte.valueOf(b2)}, 1));
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65494333249337L);
        return str;
    }

    public final ParsedVersion parseVersion(String version) {
        Integer intOrNull;
        String lowerCase = StringsKt.trim((CharSequence) version).toString().toLowerCase(Locale.ROOT);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-64884447893305L);
        List listSplit$default = StringsKt.split$default((CharSequence) StringsKt.substringBefore$default(StringsKt.removePrefix(lowerCase, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-64957462337337L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-64966052271929L), (String) null, 2, (Object) null), new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-64974642206521L)}, false, 2, 2, (Object) null);
        int iIntValue = (listSplit$default.size() != 2 || (intOrNull = StringsKt.toIntOrNull((String) listSplit$default.get(0))) == null) ? 0 : intOrNull.intValue();
        String str = (String) (listSplit$default.size() == 2 ? listSplit$default.get(1) : listSplit$default.get(0));
        return new ParsedVersion(iIntValue, str, SequencesKt.toList(SequencesKt.map(Regex.findAll$default(REGEX_VERSION_SPLIT, str, 0, 2, null), new Function1() { // from class: com.exteragram.messenger.plugins.pip.PipController$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PipController.m2530$r8$lambda$0MJ3GR4HlLQMQMAksazHxJXIE((MatchResult) obj);
            }
        })));
    }

    /* JADX INFO: renamed from: $r8$lambda$0MJ3GR4HlLQMQMAks-azHx-JXIE */
    public static String m2530$r8$lambda$0MJ3GR4HlLQMQMAksazHxJXIE(MatchResult matchResult) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-65545872856889L);
        return matchResult.getValue();
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\bÂ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\t\b\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0002H\u0002¨\u0006\f"}, m877d2 = {"Lcom/exteragram/messenger/plugins/pip/PipController$VersionComparator;", "Ljava/util/Comparator;", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlin/Comparator;", "<init>", "()V", "compare", _UrlKt.FRAGMENT_ENCODE_SET, "v1", "v2", "getWeight", "s", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class VersionComparator implements Comparator<String> {
        public static final VersionComparator INSTANCE = new VersionComparator();

        private VersionComparator() {
        }

        @Override // java.util.Comparator
        public int compare(String v1, String v2) {
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-53854971877177L);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-53867856779065L);
            PipController pipController = PipController.INSTANCE;
            ParsedVersion version = pipController.parseVersion(v1);
            ParsedVersion version2 = pipController.parseVersion(v2);
            int iCompare = Intrinsics.compare(version.getEpoch(), version2.getEpoch());
            if (iCompare != 0) {
                return iCompare;
            }
            List<String> parts = version.getParts();
            List<String> parts2 = version2.getParts();
            int iMax = Math.max(parts.size(), parts2.size());
            for (int i = 0; i < iMax; i++) {
                String string = (String) CollectionsKt.getOrNull(parts, i);
                String string2 = (String) CollectionsKt.getOrNull(parts2, i);
                if (string == null) {
                    if ((string2 != null ? StringsKt.toIntOrNull(string2) : null) != null) {
                        string = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53880741680953L);
                    }
                }
                if (string2 == null) {
                    if ((string != null ? StringsKt.toIntOrNull(string) : null) != null) {
                        string2 = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53889331615545L);
                    }
                }
                if (string == null) {
                    string = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53897921550137L);
                }
                if (string2 == null) {
                    string2 = Deobfuscator$exteraGramDev$TMessagesProj.getString(-53902216517433L);
                }
                if (!Intrinsics.areEqual(string, string2)) {
                    Integer intOrNull = StringsKt.toIntOrNull(string);
                    Integer intOrNull2 = StringsKt.toIntOrNull(string2);
                    if (intOrNull != null && intOrNull2 != null) {
                        int iCompare2 = Intrinsics.compare(intOrNull.intValue(), intOrNull2.intValue());
                        if (iCompare2 != 0) {
                            return iCompare2;
                        }
                    } else {
                        if (intOrNull != null) {
                            return 1;
                        }
                        if (intOrNull2 != null) {
                            return -1;
                        }
                        int weight = getWeight(string);
                        int weight2 = getWeight(string2);
                        if (weight != weight2) {
                            return Intrinsics.compare(weight, weight2);
                        }
                        int iCompareTo = string.compareTo(string2);
                        if (iCompareTo != 0) {
                            return iCompareTo;
                        }
                    }
                }
            }
            return 0;
        }

        private final int getWeight(String s) {
            if (Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-53906511484729L))) {
                return 110;
            }
            if (s.length() == 0) {
                return 100;
            }
            if (Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-53927986321209L)) || Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-53940871223097L))) {
                return 80;
            }
            if (Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-53949461157689L)) || Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-53970935994169L))) {
                return 70;
            }
            if (Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-53979525928761L)) || Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-54005295732537L))) {
                return 60;
            }
            return Intrinsics.areEqual(s, Deobfuscator$exteraGramDev$TMessagesProj.getString(-54013885667129L)) ? 50 : 0;
        }
    }
}
