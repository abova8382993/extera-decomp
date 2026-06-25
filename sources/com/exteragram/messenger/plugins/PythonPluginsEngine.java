package com.exteragram.messenger.plugins;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import androidx.core.content.FileProvider;
import com.android.p006dx.rop.code.RegisterSpec;
import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.PythonPluginsEngine;
import com.exteragram.messenger.plugins.hooks.PluginsHooks;
import com.exteragram.messenger.plugins.models.CustomSetting;
import com.exteragram.messenger.plugins.models.DividerSetting;
import com.exteragram.messenger.plugins.models.EditTextSetting;
import com.exteragram.messenger.plugins.models.HeaderSetting;
import com.exteragram.messenger.plugins.models.InputSetting;
import com.exteragram.messenger.plugins.models.SelectorSetting;
import com.exteragram.messenger.plugins.models.SettingItem;
import com.exteragram.messenger.plugins.models.SwitchSetting;
import com.exteragram.messenger.plugins.models.TextSetting;
import com.exteragram.messenger.plugins.p018ui.PluginSettingsActivity;
import com.exteragram.messenger.plugins.p018ui.components.InstallPluginBottomSheet;
import com.exteragram.messenger.plugins.p018ui.components.PluginFileViewer;
import com.exteragram.messenger.plugins.pip.PipController;
import com.exteragram.messenger.plugins.utils.PyObjectUtils;
import com.exteragram.messenger.preferences.utils.SettingsRegistry;
import com.exteragram.messenger.utils.AppUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.sun.jna.Callback;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.p028io.CloseableKt;
import kotlin.random.RandomKt$$ExternalSyntheticBUOutline0;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.time.DurationKt;
import okhttp3.HttpUrl$$ExternalSyntheticBUOutline0;
import okhttp3.internal.url._UrlKt;
import org.lsposed.lsparanoid.Deobfuscator$exteraGramDev$TMessagesProj;
import org.mvel2.util.Make$Map$$ExternalSyntheticBUOutline0;
import org.simplifiles.SimpliFiles;
import org.simplifiles.archive.ArchiveExtractionOptions;
import org.simplifiles.archive.ExtractionTargetPolicy;
import org.simplifiles.archive.security.SecurityPolicy;
import org.simplifiles.files.OverwritePolicy;
import org.simplifiles.files.SimpliFile;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.LaunchActivity;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000ä\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u0000 \u0088\u00012\u00020\u0001:\u0006\u0088\u0001\u0089\u0001\u008a\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\n\u0010\u0019\u001a\u0004\u0018\u00010\rH\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u001c\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u001dH\u0016J\u0018\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u001dH\u0002J\b\u0010&\u001a\u00020\u001dH\u0002J\b\u0010'\u001a\u00020\u001bH\u0002J \u0010(\u001a\u00020\u001b2\u0006\u0010)\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u0006H\u0002J\u0010\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020\u001bH\u0016J\b\u0010/\u001a\u00020\u001bH\u0002J\b\u00100\u001a\u00020\u001bH\u0002J\u0010\u00101\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020-H\u0016J\u0010\u00102\u001a\u00020\u001b2\b\u0010,\u001a\u0004\u0018\u00010-J\u0016\u00103\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u0006J \u00103\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u00062\b\u00106\u001a\u0004\u0018\u000107J*\u00103\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u00062\b\u00106\u001a\u0004\u0018\u0001072\b\u00108\u001a\u0004\u0018\u000109J\u000e\u0010:\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u0006J\u001a\u0010;\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\b\u0010<\u001a\u0004\u0018\u00010\u001fH\u0002J(\u0010=\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u001d2\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010?H\u0016J \u0010@\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010?H\u0016J\u0010\u0010A\u001a\u00020\u00062\u0006\u0010B\u001a\u00020\u0006H\u0016J\b\u0010C\u001a\u00020\u001dH\u0016J\u0010\u0010D\u001a\u00020\u001b2\u0006\u0010B\u001a\u00020\u0006H\u0016J\u0010\u0010E\u001a\u00020\u001b2\u0006\u0010B\u001a\u00020\u0006H\u0016J(\u0010F\u001a\u00020\u001b2\u0006\u00105\u001a\u00020\u00062\b\u0010G\u001a\u0004\u0018\u0001072\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010?J2\u0010F\u001a\u00020\u001b2\u0006\u00105\u001a\u00020\u00062\b\u0010G\u001a\u0004\u0018\u0001072\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010?2\b\u00108\u001a\u0004\u0018\u000109J\u000e\u0010H\u001a\u00020I2\u0006\u00105\u001a\u00020\u0006J\u001a\u0010J\u001a\b\u0012\u0004\u0012\u00020L0K2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00070KJ\u0018\u0010N\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010K2\u0006\u0010B\u001a\u00020\u0006H\u0016J\u0010\u0010O\u001a\u00020\u001b2\u0006\u0010P\u001a\u00020\u0006H\u0016J_\u0010Q\u001a\b\u0012\u0004\u0012\u0002HS0R\"\u0004\b\u0000\u0010S2\b\u0010T\u001a\u0004\u0018\u00010\u00072\b\u0010U\u001a\u0004\u0018\u0001HS2\f\u0010V\u001a\b\u0012\u0004\u0012\u0002HS0W2\u0006\u0010X\u001a\u00020\u00062\f\u0010Y\u001a\b\u0012\u0004\u0012\u0002HS0Z2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\\0?H\u0002¢\u0006\u0002\u0010]J]\u0010Q\u001a\b\u0012\u0004\u0012\u0002HS0R\"\u0004\b\u0000\u0010S2\u0006\u00104\u001a\u00020\u00062\b\u0010U\u001a\u0004\u0018\u0001HS2\f\u0010V\u001a\b\u0012\u0004\u0012\u0002HS0W2\u0006\u0010X\u001a\u00020\u00062\f\u0010Y\u001a\b\u0012\u0004\u0012\u0002HS0Z2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\\0?H\u0002¢\u0006\u0002\u0010^J0\u0010_\u001a\b\u0012\u0004\u0012\u00020`0R2\u0006\u0010a\u001a\u00020\u00062\u0006\u0010b\u001a\u00020c2\b\u0010d\u001a\u0004\u0018\u00010`2\u0006\u00104\u001a\u00020\u0006H\u0016J:\u0010e\u001a\b\u0012\u0004\u0012\u00020f0R2\u0006\u0010a\u001a\u00020\u00062\u0006\u0010b\u001a\u00020c2\b\u0010g\u001a\u0004\u0018\u00010`2\b\u0010h\u001a\u0004\u0018\u00010i2\b\u0010T\u001a\u0004\u0018\u00010\u0007J:\u0010e\u001a\b\u0012\u0004\u0012\u00020f0R2\u0006\u0010a\u001a\u00020\u00062\u0006\u0010b\u001a\u00020c2\b\u0010g\u001a\u0004\u0018\u00010`2\b\u0010h\u001a\u0004\u0018\u00010i2\u0006\u00104\u001a\u00020\u0006H\u0016J0\u0010j\u001a\b\u0012\u0004\u0012\u00020k0R2\u0006\u0010l\u001a\u00020\u00062\u0006\u0010b\u001a\u00020c2\b\u0010m\u001a\u0004\u0018\u00010k2\u0006\u00104\u001a\u00020\u0006H\u0016J0\u0010n\u001a\b\u0012\u0004\u0012\u00020o0R2\u0006\u0010p\u001a\u00020\u00062\u0006\u0010b\u001a\u00020c2\b\u0010q\u001a\u0004\u0018\u00010o2\u0006\u00104\u001a\u00020\u0006H\u0016J(\u0010r\u001a\b\u0012\u0004\u0012\u00020s0R2\u0006\u0010b\u001a\u00020c2\b\u0010t\u001a\u0004\u0018\u00010s2\u0006\u00104\u001a\u00020\u0006H\u0016J\u001a\u0010u\u001a\u0004\u0018\u00010\u00062\b\u00105\u001a\u0004\u0018\u00010\u00062\u0006\u0010v\u001a\u00020\u0006J\u001c\u0010w\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060x2\b\u00105\u001a\u0004\u0018\u00010\u0006J$\u0010y\u001a\u0004\u0018\u00010\u000b2\u0006\u00104\u001a\u00020\u00062\u0006\u0010z\u001a\u00020\u00062\b\u0010{\u001a\u0004\u0018\u00010\u000bH\u0016J\"\u0010|\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u0006\u0010z\u001a\u00020\u00062\b\u0010}\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010~\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u0006H\u0016J\u001c\u0010\u007f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0002\b\u0003\u0018\u00010x2\u0006\u00104\u001a\u00020\u0006H\u0016J\u001c\u0010\u0080\u0001\u001a\u00020\u001b2\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0007\u0010t\u001a\u00030\u0083\u0001H\u0016J\u001b\u0010\u0084\u0001\u001a\u00020\u001b2\u0006\u0010B\u001a\u00020\u00062\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H\u0016J\u001c\u0010\u0084\u0001\u001a\u00020\u001b2\u0007\u0010\u0085\u0001\u001a\u0002072\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H\u0016J%\u0010\u0086\u0001\u001a\u00020\u001b2\u0007\u0010\u0085\u0001\u001a\u0002072\u0007\u0010\u0087\u0001\u001a\u00020\u00062\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H\u0016J$\u0010\u0086\u0001\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u00062\u0007\u0010\u0087\u0001\u001a\u00020\u00062\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H\u0016R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR(\u0010\n\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013¨\u0006\u008b\u0001"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PythonPluginsEngine;", "Lcom/exteragram/messenger/plugins/PluginsController$PluginsEngine;", "<init>", "()V", "pluginInstances", "Ljava/util/concurrent/ConcurrentHashMap;", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/chaquo/python/PyObject;", "getPluginInstances", "()Ljava/util/concurrent/ConcurrentHashMap;", "settingsCache", _UrlKt.FRAGMENT_ENCODE_SET, "python", "Lcom/chaquo/python/Python;", "devServerClass", "debuggerListener", "getDebuggerListener", "()Lcom/chaquo/python/PyObject;", "setDebuggerListener", "(Lcom/chaquo/python/PyObject;)V", "basePluginClass", "getBasePluginClass", "setBasePluginClass", "getPluginsController", "Lcom/exteragram/messenger/plugins/PluginsController;", "getPython", "initPython", _UrlKt.FRAGMENT_ENCODE_SET, "isPlugin", _UrlKt.FRAGMENT_ENCODE_SET, "file", "Ljava/io/File;", "messageObject", "Lorg/telegram/messenger/MessageObject;", "isEngineAvailable", "installSdkArchive", "archiveFile", "fromApk", "initSdk", "stopAndUnloadSdk", "removeModulesRecursive", "sysModules", "prefix", "init", Callback.METHOD_NAME, "Ljava/lang/Runnable;", "checkDevServer", "runDevServer", "stopDevServer", "shutdown", "loadPlugins", "loadPlugin", "pluginId", "filePath", "metadata", "Lcom/exteragram/messenger/plugins/Plugin;", "delegate", "Lcom/exteragram/messenger/plugins/pip/PipController$InstallerDelegate;", "unloadPlugin", "refreshImportCaches", "moduleDir", "setPluginEnabled", "enabled", "Lorg/telegram/messenger/Utilities$Callback;", "deletePlugin", "getPluginPath", "id", "canOpenInExternalApp", "openInExternalApp", "sharePlugin", "loadPluginFromFile", "pluginMetadata", "validatePluginFromFile", "Lcom/exteragram/messenger/plugins/PluginsController$PluginValidationResult;", "parsePySettingDefinitions", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/exteragram/messenger/plugins/models/SettingItem;", "pyDefinitionsList", "loadPluginSettings", "executeOnAppEvent", "eventType", "executeHook", "Lcom/exteragram/messenger/plugins/PluginsController$HookResult;", "T", "pluginInstance", "initialValue", "valueClass", "Ljava/lang/Class;", "pyResultKey", "caller", "Lcom/exteragram/messenger/plugins/PythonPluginsEngine$PyMethodCaller;", "errorLogger", "Lcom/chaquo/python/PyException;", "(Lcom/chaquo/python/PyObject;Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;Lcom/exteragram/messenger/plugins/PythonPluginsEngine$PyMethodCaller;Lorg/telegram/messenger/Utilities$Callback;)Lcom/exteragram/messenger/plugins/PluginsController$HookResult;", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;Lcom/exteragram/messenger/plugins/PythonPluginsEngine$PyMethodCaller;Lorg/telegram/messenger/Utilities$Callback;)Lcom/exteragram/messenger/plugins/PluginsController$HookResult;", "executePreRequestHook", "Lorg/telegram/tgnet/TLObject;", "requestName", "account", _UrlKt.FRAGMENT_ENCODE_SET, "request", "executePostRequestHook", "Lcom/exteragram/messenger/plugins/hooks/PluginsHooks$PostRequestResult;", "response", "error", "Lorg/telegram/tgnet/TLRPC$TL_error;", "executeUpdateHook", "Lorg/telegram/tgnet/TLRPC$Update;", "updateName", "update", "executeUpdatesHook", "Lorg/telegram/tgnet/TLRPC$Updates;", "containerName", "updates", "executeSendMessageHook", "Lorg/telegram/messenger/SendMessagesHelper$SendMessageParams;", "params", "fetchParameterValue", "parameterName", "parsePluginMetadata", _UrlKt.FRAGMENT_ENCODE_SET, "getPluginSetting", "key", "defaultValue", "setPluginSetting", "value", "clearPluginSettings", "getAllPluginSettings", "showInstallDialog", "fragment", "Lorg/telegram/ui/ActionBar/BaseFragment;", "Lcom/exteragram/messenger/plugins/ui/components/InstallPluginBottomSheet$PluginInstallParams;", "openPluginSettings", "plugin", "openPluginSetting", "linkAlias", "Companion", "PyMethodCaller", "Updater", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPythonPluginsEngine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PythonPluginsEngine.kt\ncom/exteragram/messenger/plugins/PythonPluginsEngine\n+ 2 SharedPreferences.kt\nandroidx/core/content/SharedPreferencesKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,2505:1\n41#2,12:2506\n41#2,12:2519\n41#2,12:2531\n1#3:2518\n1586#4:2543\n1661#4,3:2544\n777#4:2547\n873#4,2:2548\n1834#4,4:2550\n37#5,2:2554\n*S KotlinDebug\n*F\n+ 1 PythonPluginsEngine.kt\ncom/exteragram/messenger/plugins/PythonPluginsEngine\n*L\n452#1:2506,12\n805#1:2519,12\n832#1:2531,12\n1125#1:2543\n1125#1:2544,3\n1126#1:2547\n1126#1:2548,2\n605#1:2550,4\n1829#1:2554,2\n*E\n"})
public final class PythonPluginsEngine implements PluginsController.PluginsEngine {
    private static final long MAX_SDK_VERSION_BYTES = 65536;
    private static boolean SDK_BETA;
    private static File SDK_DIR;
    private static String SDK_VERSION;
    private static volatile boolean sdkInitialized;
    private PyObject basePluginClass;
    private PyObject debuggerListener;
    private PyObject devServerClass;
    private volatile Python python;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Pattern VERSION_PATTERN = Pattern.compile(Deobfuscator$exteraGramDev$TMessagesProj.getString(-38216995952441L));
    private static final String[] SDK_REQUIRED_MODULES = {Deobfuscator$exteraGramDev$TMessagesProj.getString(-38307190265657L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-38363024840505L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-38414564448057L)};
    private static final SecurityPolicy SDK_ARCHIVE_POLICY = SecurityPolicy.INSTANCE.builder().maxEntries(100000).maxTotalUncompressedSize(2147483648L).maxSingleFileSize(536870912).maxCompressionRatio(500.0d).build();
    private static final ArchiveExtractionOptions SDK_ARCHIVE_OPTIONS = ArchiveExtractionOptions.INSTANCE.builder().targetPolicy(ExtractionTargetPolicy.REPLACE).build();
    private final ConcurrentHashMap<String, PyObject> pluginInstances = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> settingsCache = new ConcurrentHashMap<>();

    @Metadata(m876d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bâ\u0080\u0001\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J!\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0007¨\u0006\bÀ\u0006\u0003"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PythonPluginsEngine$PyMethodCaller;", "T", _UrlKt.FRAGMENT_ENCODE_SET, "call", "Lcom/chaquo/python/PyObject;", "instance", "value", "(Lcom/chaquo/python/PyObject;Ljava/lang/Object;)Lcom/chaquo/python/PyObject;", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public interface PyMethodCaller<T> {
        PyObject call(PyObject instance, T value);
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public boolean canOpenInExternalApp() {
        return true;
    }

    /* JADX INFO: loaded from: classes4.dex */
    @Metadata(m876d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\tH\u0002J\u0012\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u001dH\u0002J\u0012\u0010%\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u001dH\u0002R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PythonPluginsEngine$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "VERSION_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "SDK_REQUIRED_MODULES", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "[Ljava/lang/String;", "SDK_ARCHIVE_POLICY", "Lorg/simplifiles/archive/security/SecurityPolicy;", "SDK_ARCHIVE_OPTIONS", "Lorg/simplifiles/archive/ArchiveExtractionOptions;", "MAX_SDK_VERSION_BYTES", _UrlKt.FRAGMENT_ENCODE_SET, "SDK_VERSION", "getSDK_VERSION", "()Ljava/lang/String;", "setSDK_VERSION", "(Ljava/lang/String;)V", "SDK_BETA", _UrlKt.FRAGMENT_ENCODE_SET, "getSDK_BETA", "()Z", "setSDK_BETA", "(Z)V", "SDK_DIR", "Ljava/io/File;", "sdkInitialized", "sdkModuleExists", "sdkDir", "moduleName", "deleteFileIfExists", _UrlKt.FRAGMENT_ENCODE_SET, "file", "isSdkDirValid", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getSDK_VERSION() {
            return PythonPluginsEngine.SDK_VERSION;
        }

        public final void setSDK_VERSION(String str) {
            PythonPluginsEngine.SDK_VERSION = str;
        }

        public final boolean getSDK_BETA() {
            return PythonPluginsEngine.SDK_BETA;
        }

        public final void setSDK_BETA(boolean z) {
            PythonPluginsEngine.SDK_BETA = z;
        }

        private final boolean sdkModuleExists(File sdkDir, String moduleName) {
            if (new File(sdkDir, moduleName + Deobfuscator$exteraGramDev$TMessagesProj.getString(-683276752697L)).exists()) {
                return true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(moduleName);
            sb.append(Deobfuscator$exteraGramDev$TMessagesProj.getString(-700456621881L));
            return new File(sdkDir, sb.toString()).exists();
        }

        public final void deleteFileIfExists(File file) {
            Object objM3494constructorimpl;
            if (file == null || !file.exists()) {
                return;
            }
            try {
                Result.Companion companion = Result.INSTANCE;
                objM3494constructorimpl = Result.m3494constructorimpl(Boolean.valueOf(SimpliFiles.file(file).delete()));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM3497exceptionOrNullimpl = Result.m3497exceptionOrNullimpl(objM3494constructorimpl);
            if (thM3497exceptionOrNullimpl != null) {
                FileLog.m1048e(thM3497exceptionOrNullimpl);
            }
        }

        public final boolean isSdkDirValid(File sdkDir) {
            if (sdkDir == null || !sdkDir.isDirectory()) {
                return false;
            }
            for (String str : PythonPluginsEngine.SDK_REQUIRED_MODULES) {
                if (!sdkModuleExists(sdkDir, str)) {
                    return false;
                }
            }
            return true;
        }
    }

    public final ConcurrentHashMap<String, PyObject> getPluginInstances() {
        return this.pluginInstances;
    }

    public final PyObject getDebuggerListener() {
        return this.debuggerListener;
    }

    public final void setDebuggerListener(PyObject pyObject) {
        this.debuggerListener = pyObject;
    }

    public final PyObject getBasePluginClass() {
        return this.basePluginClass;
    }

    public final void setBasePluginClass(PyObject pyObject) {
        this.basePluginClass = pyObject;
    }

    private final PluginsController getPluginsController() {
        return PluginsController.INSTANCE.getInstance();
    }

    private final synchronized Python getPython() {
        if (this.python == null) {
            initPython();
            if (this.python == null) {
                FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-12395652568889L));
                return null;
            }
        }
        return this.python;
    }

    private final void initPython() {
        try {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(ApplicationLoader.applicationContext));
            }
            this.python = Python.getInstance();
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-12606105966393L), e);
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public boolean isPlugin(File file, MessageObject messageObject) {
        if (file == null) {
            return false;
        }
        String name = file.getName();
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-12726365050681L);
        String lowerCase = name.toLowerCase(Locale.ROOT);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-12782199625529L);
        return StringsKt.endsWith$default(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-12855214069561L), false, 2, (Object) null);
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public boolean isEngineAvailable() {
        return getPython() != null && Python.isStarted() && sdkInitialized;
    }

    private final void installSdkArchive(File archiveFile, boolean fromApk) {
        Object objM3494constructorimpl;
        File file = SDK_DIR;
        if (file == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-12889573807929L));
            return;
        }
        File file2 = new File(file.getParentFile(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-12996947990329L));
        try {
            SimpliFiles.archive(archiveFile).withPolicy(SDK_ARCHIVE_POLICY).extractToDirectory(file2, SDK_ARCHIVE_OPTIONS);
            if (!INSTANCE.isSdkDirValid(file2)) {
                throw new IOException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-13065667467065L));
            }
            SimpliFiles.directory(file2).moveTo(file, OverwritePolicy.REPLACE);
            Updater.INSTANCE.setBuildFromApk(fromApk);
        } finally {
            if (file2.exists()) {
                try {
                    Result.Companion companion = Result.INSTANCE;
                    objM3494constructorimpl = Result.m3494constructorimpl(Boolean.valueOf(SimpliFiles.directory(file2).deleteRecursively()));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
                }
                Throwable thM3497exceptionOrNullimpl = Result.m3497exceptionOrNullimpl(objM3494constructorimpl);
                if (thM3497exceptionOrNullimpl != null) {
                    FileLog.m1048e(thM3497exceptionOrNullimpl);
                }
            }
            if (!file.exists()) {
                SimpliFiles.directory(file).create();
            }
        }
    }

    private final boolean initSdk() {
        String str;
        Boolean bool;
        BufferedReader bufferedReader;
        StringBuilder sb;
        sdkInitialized = false;
        Python python = this.python;
        if (python == null) {
            return false;
        }
        if (SDK_DIR == null) {
            File file = new File(new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-13306185635641L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-13344840341305L));
            SDK_DIR = file;
            if (!file.exists()) {
                File file2 = SDK_DIR;
                if (file2 == null) {
                    RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-13503754131257L));
                    return false;
                }
                SimpliFiles.directory(file2).create();
            }
        }
        File file3 = SDK_DIR;
        if (file3 == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-13611128313657L));
            return false;
        }
        Updater.Companion companion = Updater.INSTANCE;
        File fileRequestSdkFromApkFile = companion.requestSdkFromApkFile();
        File pythonSdkUpdateFile = companion.getPythonSdkUpdateFile();
        File pythonCurrentSdkFile = companion.getPythonCurrentSdkFile();
        boolean zExists = fileRequestSdkFromApkFile.exists();
        if (zExists) {
            SimpliFiles.directory(file3).clean();
            INSTANCE.deleteFileIfExists(fileRequestSdkFromApkFile);
        }
        boolean z = true;
        if (!zExists && pythonSdkUpdateFile.exists()) {
            try {
                SimpliFiles.file(pythonSdkUpdateFile).copyTo(pythonCurrentSdkFile, OverwritePolicy.REPLACE);
                installSdkArchive(pythonCurrentSdkFile, false);
            } catch (IOException e) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-13718502496057L), e);
                zExists = true;
            }
        }
        File file4 = new File(file3, Deobfuscator$exteraGramDev$TMessagesProj.getString(-13911776024377L));
        if (file4.exists()) {
            String string = StringsKt.trim((CharSequence) SimpliFile.readText$default(SimpliFiles.file(file4), MAX_SDK_VERSION_BYTES, null, 2, null)).toString();
            boolean zEndsWith$default = StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-13937545828153L), false, 2, (Object) null);
            String strSubstringBefore$default = StringsKt.substringBefore$default(string, '|', (String) null, 2, (Object) null);
            try {
                InputStream inputStreamOpen = ApplicationLoader.applicationContext.getAssets().open(Deobfuscator$exteraGramDev$TMessagesProj.getString(-13950430730041L));
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpen, StandardCharsets.UTF_8));
                    try {
                        sb = new StringBuilder();
                    } finally {
                    }
                } finally {
                }
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    sb.append('\n');
                }
                String string2 = sb.toString();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-14036330075961L);
                try {
                } catch (NumberFormatException e2) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-14117934454585L), e2);
                }
                if (AppUtils.compareVersions(zEndsWith$default ? Deobfuscator$exteraGramDev$TMessagesProj.getString(-14096459618105L) : Deobfuscator$exteraGramDev$TMessagesProj.getString(-14109344519993L), StringsKt.trim((CharSequence) string2).toString(), strSubstringBefore$default)) {
                    zExists = true;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedReader, null);
                CloseableKt.closeFinally(inputStreamOpen, null);
            } catch (IOException e3) {
                Make$Map$$ExternalSyntheticBUOutline0.m1024m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-14362747590457L), e3);
                return false;
            }
        }
        if (zExists || !INSTANCE.isSdkDirValid(file3)) {
            if (!zExists) {
                FileLog.m1049w(Deobfuscator$exteraGramDev$TMessagesProj.getString(-14611855693625L));
            }
            SimpliFiles.directory(file3).clean();
            try {
                InputStream inputStreamSdkFromApk = Updater.INSTANCE.sdkFromApk();
                try {
                    SimpliFile.writeFromAtomic$default(SimpliFiles.file(pythonCurrentSdkFile), inputStreamSdkFromApk, 0L, 2, null);
                    CloseableKt.closeFinally(inputStreamSdkFromApk, null);
                    installSdkArchive(pythonCurrentSdkFile, true);
                } finally {
                }
            } catch (IOException e4) {
                Make$Map$$ExternalSyntheticBUOutline0.m1024m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-14921093338937L), e4);
                return false;
            }
        }
        Updater.INSTANCE.deleteSdkUpdateFile();
        PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15084302096185L));
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-15101481965369L);
        PyObject pyObject = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-15165906474809L));
        if (pyObject != null) {
            pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15187381311289L), file3.getAbsolutePath());
        }
        try {
            PyObject module2 = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15217446082361L));
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-15273280657209L);
            PyObject pyObjectCallAttr = module2.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15337705166649L), new Object[0]);
            if (pyObjectCallAttr == null || !pyObjectCallAttr.toBoolean()) {
                z = false;
            }
            sdkInitialized = z;
            PyObject pyObject2 = (PyObject) module2.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-15380654839609L));
            if (pyObject2 == null || (str = (String) pyObject2.toJava(String.class)) == null) {
                str = SDK_VERSION;
            }
            SDK_VERSION = str;
            PyObject pyObject3 = (PyObject) module2.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-15432194447161L));
            SDK_BETA = (pyObject3 == null || (bool = (Boolean) pyObject3.toJava(Boolean.TYPE)) == null) ? SDK_BETA : bool.booleanValue();
            if (this.basePluginClass == null && sdkInitialized && !ExteraConfig.getPluginsSafeMode()) {
                try {
                    this.basePluginClass = (PyObject) python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15853101242169L)).get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-15904640849721L));
                } catch (PyException e5) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15951885489977L), e5);
                }
            }
            return sdkInitialized;
        } catch (Throwable th) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15470849152825L), th);
            try {
                Updater.INSTANCE.restoreSdkFromApk();
            } catch (Throwable th2) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-15651237779257L), th2);
            }
            return false;
        }
    }

    private final void stopAndUnloadSdk() {
        PyObject pyObject;
        Python python = this.python;
        if (python == null) {
            return;
        }
        this.basePluginClass = null;
        PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16089324443449L));
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-16106504312633L);
        PyObject pyObject2 = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-16170928822073L));
        if (pyObject2 != null) {
            try {
                PyObject pyObjectCallAttr = pyObject2.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16205288560441L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-16261123135289L));
                if (pyObjectCallAttr != null && pyObjectCallAttr.toBoolean() && (pyObject = (PyObject) pyObject2.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-16316957710137L))) != null) {
                    pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16372792284985L), new Object[0]);
                }
            } catch (Throwable th) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16411446990649L), th);
            }
        }
        sdkInitialized = false;
        File file = SDK_DIR;
        PyObject pyObject3 = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-16566065813305L));
        if (file != null && pyObject3 != null && pyObject3.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16587540649785L), file.getAbsolutePath()).toBoolean()) {
            pyObject3.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16643375224633L), file.getAbsolutePath());
        }
        if (file == null || pyObject2 == null) {
            return;
        }
        removeModulesRecursive(pyObject2, file, Deobfuscator$exteraGramDev$TMessagesProj.getString(-16673439995705L));
    }

    private final void removeModulesRecursive(PyObject sysModules, File file, String prefix) {
        if (Intrinsics.areEqual(prefix, Deobfuscator$exteraGramDev$TMessagesProj.getString(-16677734963001L))) {
            prefix = Deobfuscator$exteraGramDev$TMessagesProj.getString(-16733569537849L);
        }
        if (file.isDirectory()) {
            if (sysModules.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16737864505145L), prefix + file.getName()).toBoolean()) {
                sysModules.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16793699079993L), prefix + file.getName());
            }
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                removeModulesRecursive(sysModules, file2, file.getName() + '.');
            }
        }
        String name = file.getName();
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-16810878949177L);
        String strSubstringBefore$default = StringsKt.substringBefore$default(name, '.', (String) null, 2, (Object) null);
        if (file.isDirectory()) {
            return;
        }
        if (sysModules.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16866713524025L), prefix + strSubstringBefore$default).toBoolean()) {
            sysModules.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16922548098873L), prefix + strSubstringBefore$default);
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void init(Runnable runnable) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-16939727968057L);
        if (getPython() == null) {
            runnable.run();
            return;
        }
        try {
            if (!initSdk()) {
                runnable.run();
                return;
            }
            final Updater.Companion companion = Updater.INSTANCE;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    companion.checkUpdates();
                }
            }, 5000L);
            Python python = this.python;
            if (python == null) {
                runnable.run();
                return;
            }
            if (!ExteraConfig.getPluginsSafeMode()) {
                try {
                    PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17163066267449L));
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-17231785744185L);
                    Object java = module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17296210253625L), getPluginsController().getPluginsDir().getAbsolutePath(), getPluginsController().getPreferences().getAll()).toJava(String[].class);
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-17317685090105L);
                    String[] strArr = (String[]) java;
                    if (!(strArr.length == 0)) {
                        SharedPreferences.Editor editorEdit = getPluginsController().getPreferences().edit();
                        for (String str : strArr) {
                            editorEdit.remove(str);
                        }
                        editorEdit.apply();
                        FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17369224697657L) + strArr.length + Deobfuscator$exteraGramDev$TMessagesProj.getString(-17412174370617L));
                    }
                } catch (PyException e) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17622627768121L), e);
                }
            }
            PipController.INSTANCE.cleanup();
            loadPlugins(runnable);
            checkDevServer();
        } catch (Throwable th) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-16978382673721L), th);
            runnable.run();
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void checkDevServer() {
        if (ExteraConfig.getPluginsSafeMode()) {
            return;
        }
        if (ExteraConfig.getPluginsDevMode()) {
            runDevServer();
        } else {
            stopDevServer();
        }
    }

    private final void runDevServer() {
        Python python;
        if (ExteraConfig.getPluginsSafeMode() || (python = getPython()) == null) {
            return;
        }
        if (this.devServerClass != null) {
            stopDevServer();
        }
        try {
            PyObject pyObject = (PyObject) python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17811606329145L)).get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-17858850969401L));
            this.devServerClass = pyObject;
            if (pyObject == null) {
                return;
            }
            if (pyObject != null) {
                pyObject.callAttrThrows(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17901800642361L), new Object[0]);
            }
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-17957635217209L));
        } catch (Throwable th) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-18099369137977L), th);
            this.devServerClass = null;
        }
    }

    private final void stopDevServer() {
        PyObject pyObject;
        if (ExteraConfig.getPluginsSafeMode() || (pyObject = this.devServerClass) == null) {
            return;
        }
        try {
            pyObject.callAttrThrows(Deobfuscator$exteraGramDev$TMessagesProj.getString(-18236808091449L), new Object[0]);
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-18288347699001L));
        } catch (Throwable th) {
            try {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-18430081619769L), th);
            } finally {
                this.devServerClass = null;
            }
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void shutdown(Runnable runnable) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18541750769465L);
        if (getPython() == null) {
            runnable.run();
            return;
        }
        try {
            Iterator it = new ArrayList(this.pluginInstances.keySet()).iterator();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-18580405475129L);
            while (it.hasNext()) {
                unloadPlugin((String) it.next());
            }
            PyObject pyObject = this.debuggerListener;
            if (pyObject != null) {
                pyObject.close();
            }
            this.debuggerListener = null;
            this.pluginInstances.clear();
            synchronized (this) {
                stopAndUnloadSdk();
                this.python = null;
                sdkInitialized = false;
                Unit unit = Unit.INSTANCE;
            }
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-18640535017273L));
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        runnable.run();
    }

    public final void loadPlugins(final Runnable runnable) {
        PluginsController.INSTANCE.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                PythonPluginsEngine.m2519$r8$lambda$2QnMYM5kpMDhr3l1kM17QcThy8(this.f$0, runnable);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$2QnMYM5kpMDhr3l1k-M17QcThy8 */
    public static void m2519$r8$lambda$2QnMYM5kpMDhr3l1kM17QcThy8(PythonPluginsEngine pythonPluginsEngine, Runnable runnable) {
        PluginsController.PluginValidationResult pluginValidationResultValidatePluginFromFile;
        Plugin plugin;
        Python python = pythonPluginsEngine.getPython();
        if (python == null) {
            if (runnable != null) {
                AndroidUtilities.runOnUIThread(runnable);
                return;
            }
            return;
        }
        try {
            PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-34437424731961L));
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-34454604601145L);
            PyObject pyObject = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-34519029110585L));
            if (pyObject != null) {
                pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-34540503947065L), pythonPluginsEngine.getPluginsController().getPluginsDir().getAbsolutePath());
            }
            module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-34570568718137L), Double.valueOf(0.001d));
            File[] fileArrListFiles = pythonPluginsEngine.getPluginsController().getPluginsDir().listFiles(new FilenameFilter() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda5
                @Override // java.io.FilenameFilter
                public final boolean accept(File file, String str) {
                    return PythonPluginsEngine.loadPlugins$lambda$0$0(file, str);
                }
            });
            if (fileArrListFiles == null) {
                pythonPluginsEngine.getPluginsController().notifyPluginsChanged();
                if (runnable != null) {
                    AndroidUtilities.runOnUIThread(runnable);
                    return;
                }
                return;
            }
            int i = 0;
            for (File file : fileArrListFiles) {
                String name = file.getName();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-34849741592377L);
                String strSubstring = name.substring(0, file.getName().length() - 3);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-34905576167225L);
                try {
                    String absolutePath = file.getAbsolutePath();
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-34970000676665L);
                    pluginValidationResultValidatePluginFromFile = pythonPluginsEngine.validatePluginFromFile(absolutePath);
                } catch (Throwable th) {
                    th = th;
                    pluginValidationResultValidatePluginFromFile = null;
                }
                try {
                    if (pluginValidationResultValidatePluginFromFile.getError() != null) {
                        throw new Exception(pluginValidationResultValidatePluginFromFile.getError());
                    }
                    String absolutePath2 = file.getAbsolutePath();
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-35060194989881L);
                    pythonPluginsEngine.loadPlugin(strSubstring, absolutePath2, pluginValidationResultValidatePluginFromFile.getPlugin());
                } catch (Throwable th2) {
                    th = th2;
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-35150389303097L) + file.getName() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-35249173550905L) + th.getMessage(), th);
                    if ((pluginValidationResultValidatePluginFromFile != null ? pluginValidationResultValidatePluginFromFile.getPlugin() : null) != null) {
                        plugin = pluginValidationResultValidatePluginFromFile.getPlugin();
                        if (plugin == null) {
                            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-35296418191161L));
                            return;
                        }
                    } else {
                        plugin = new Plugin(strSubstring, strSubstring);
                        plugin.setAuthor(LocaleController.getString(C2797R.string.PluginNoAuthor));
                        plugin.setVersion(Deobfuscator$exteraGramDev$TMessagesProj.getString(-35403792373561L));
                        plugin.setEngine(Deobfuscator$exteraGramDev$TMessagesProj.getString(-35420972242745L));
                    }
                    plugin.setError(th);
                    plugin.setEnabled(false);
                    pythonPluginsEngine.getPluginsController().getPlugins().put(strSubstring, plugin);
                }
            }
            pythonPluginsEngine.getPluginsController().notifyPluginsChanged();
            Collection<Plugin> collectionValues = pythonPluginsEngine.getPluginsController().getPlugins().values();
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-35451037013817L);
            Collection<Plugin> collection = collectionValues;
            if (!collection.isEmpty()) {
                for (Plugin plugin2 : collection) {
                    if (plugin2.isEnabled() && !plugin2.hasError() && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-35528346425145L) + pythonPluginsEngine.getPluginsController().getPlugins().size() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-35708735051577L) + i);
            if (runnable != null) {
                AndroidUtilities.runOnUIThread(runnable);
            }
        } catch (PyException e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-34647878129465L), e);
            if (runnable != null) {
                AndroidUtilities.runOnUIThread(runnable);
            }
        }
    }

    public static final boolean loadPlugins$lambda$0$0(File file, String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-35760274659129L);
        return StringsKt.endsWith$default(lowerCase, Deobfuscator$exteraGramDev$TMessagesProj.getString(-35833289103161L), false, 2, (Object) null);
    }

    public final void loadPlugin(String pluginId, String filePath) throws Exception {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18777973970745L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18816628676409L);
        loadPlugin(pluginId, filePath, null, null);
    }

    public final void loadPlugin(String pluginId, String filePath, Plugin metadata) throws Exception {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18855283382073L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18893938087737L);
        loadPlugin(pluginId, filePath, metadata, null);
    }

    public final void loadPlugin(String pluginId, String filePath, Plugin metadata, PipController.InstallerDelegate delegate) throws Exception {
        PyObject module;
        PyObject pyObjectCallAttr;
        PyObject module2;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18932592793401L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-18971247499065L);
        boolean z = getPluginsController().getPreferences().getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19009902204729L) + pluginId, false);
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new Exception(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19078621681465L) + filePath);
        }
        if (metadata == null) {
            PluginsController.PluginValidationResult pluginValidationResultValidatePluginFromFile = validatePluginFromFile(filePath);
            if (pluginValidationResultValidatePluginFromFile.getError() != null) {
                throw new Exception(pluginValidationResultValidatePluginFromFile.getError());
            }
            metadata = pluginValidationResultValidatePluginFromFile.getPlugin();
        }
        if (metadata == null) {
            RandomKt$$ExternalSyntheticBUOutline0.m936m(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19181700896569L));
            return;
        }
        if (!Intrinsics.areEqual(pluginId, metadata.getId())) {
            throw new Exception(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19289075078969L) + pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-19422219065145L) + metadata.getId() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-19482348607289L));
        }
        if (this.pluginInstances.containsKey(pluginId)) {
            unloadPlugin(pluginId);
        }
        List<String> requirements = metadata.getRequirements();
        List<String> list = requirements;
        if (list != null && !list.isEmpty()) {
            List<String> listInstallDependencies = PipController.INSTANCE.installDependencies(requirements, pluginId, delegate);
            Python python = getPython();
            PyObject pyObject = (python == null || (module2 = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19542478149433L))) == null) ? null : (PyObject) module2.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-19559658018617L));
            if (pyObject != null) {
                for (int size = listInstallDependencies.size() - 1; -1 < size; size--) {
                    String str = listInstallDependencies.get(size);
                    if (pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19581132855097L), str).toBoolean()) {
                        pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19636967429945L), str);
                    }
                    pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19667032201017L), 0, str);
                }
            }
        }
        refreshImportCaches(pluginId, new File(filePath).getParentFile());
        try {
            Python python2 = getPython();
            if (python2 == null || (module = python2.getModule(pluginId)) == null) {
                throw new Exception(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19697096972089L) + pluginId);
            }
            PyObject pyObject2 = this.basePluginClass;
            if (pyObject2 == null || (pyObjectCallAttr = pyObject2.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19980564813625L), module)) == null) {
                throw new Exception(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20053579257657L) + pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-20285507491641L));
            }
            PyObject pyObjectCall = pyObjectCallAttr.call(new Object[0]);
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20534615594809L), (Object) metadata.getId());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20547500496697L), (Object) metadata.getName());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20568975333177L), (Object) metadata.getDescription());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20620514940729L), (Object) metadata.getAuthor());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20650579711801L), (Object) metadata.getVersion());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20684939450169L), (Object) metadata.getIcon());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20706414286649L), (Object) metadata.getAppVersion());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20757953894201L), (Object) metadata.getSdkVersion());
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20809493501753L), (Object) metadata.getRequirements());
            String string = Deobfuscator$exteraGramDev$TMessagesProj.getString(-20865328076601L);
            Boolean bool = Boolean.FALSE;
            pyObjectCall.put(string, (Object) bool);
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20899687814969L), (Object) bool);
            pyObjectCall.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-20951227422521L), (Object) null);
            getPluginsController().getPlugins().put(pluginId, metadata);
            this.pluginInstances.put(pluginId, pyObjectCall);
            if (z) {
                setPluginEnabled(pluginId, true, null);
            }
        } catch (PyException e) {
            throw new Exception(Deobfuscator$exteraGramDev$TMessagesProj.getString(-19838830892857L) + e.getMessage(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0092 A[Catch: all -> 0x004c, PyException -> 0x004f, TryCatch #2 {PyException -> 0x004f, blocks: (B:80:0x001d, B:82:0x0027, B:85:0x0040, B:86:0x0044, B:94:0x006e, B:96:0x0074, B:97:0x007f, B:99:0x0082, B:101:0x0092, B:102:0x00a0, B:104:0x00b0, B:105:0x00b3, B:107:0x00bf, B:109:0x00ce, B:112:0x00e1, B:114:0x00f4, B:115:0x0104, B:117:0x0120, B:120:0x0127, B:122:0x012d, B:124:0x0131, B:127:0x0160, B:129:0x0165, B:130:0x016a), top: B:144:0x001d, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x00b0 A[Catch: all -> 0x004c, PyException -> 0x004f, TryCatch #2 {PyException -> 0x004f, blocks: (B:80:0x001d, B:82:0x0027, B:85:0x0040, B:86:0x0044, B:94:0x006e, B:96:0x0074, B:97:0x007f, B:99:0x0082, B:101:0x0092, B:102:0x00a0, B:104:0x00b0, B:105:0x00b3, B:107:0x00bf, B:109:0x00ce, B:112:0x00e1, B:114:0x00f4, B:115:0x0104, B:117:0x0120, B:120:0x0127, B:122:0x012d, B:124:0x0131, B:127:0x0160, B:129:0x0165, B:130:0x016a), top: B:144:0x001d, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0131 A[Catch: all -> 0x004c, PyException -> 0x004f, TryCatch #2 {PyException -> 0x004f, blocks: (B:80:0x001d, B:82:0x0027, B:85:0x0040, B:86:0x0044, B:94:0x006e, B:96:0x0074, B:97:0x007f, B:99:0x0082, B:101:0x0092, B:102:0x00a0, B:104:0x00b0, B:105:0x00b3, B:107:0x00bf, B:109:0x00ce, B:112:0x00e1, B:114:0x00f4, B:115:0x0104, B:117:0x0120, B:120:0x0127, B:122:0x012d, B:124:0x0131, B:127:0x0160, B:129:0x0165, B:130:0x016a), top: B:144:0x001d, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0165 A[Catch: all -> 0x004c, PyException -> 0x004f, TryCatch #2 {PyException -> 0x004f, blocks: (B:80:0x001d, B:82:0x0027, B:85:0x0040, B:86:0x0044, B:94:0x006e, B:96:0x0074, B:97:0x007f, B:99:0x0082, B:101:0x0092, B:102:0x00a0, B:104:0x00b0, B:105:0x00b3, B:107:0x00bf, B:109:0x00ce, B:112:0x00e1, B:114:0x00f4, B:115:0x0104, B:117:0x0120, B:120:0x0127, B:122:0x012d, B:124:0x0131, B:127:0x0160, B:129:0x0165, B:130:0x016a), top: B:144:0x001d, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01a5 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0163 A[EDGE_INSN: B:146:0x0163->B:128:0x0163 BREAK  A[LOOP:0: B:123:0x012f->B:127:0x0160], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0082 A[Catch: all -> 0x004c, PyException -> 0x004f, TryCatch #2 {PyException -> 0x004f, blocks: (B:80:0x001d, B:82:0x0027, B:85:0x0040, B:86:0x0044, B:94:0x006e, B:96:0x0074, B:97:0x007f, B:99:0x0082, B:101:0x0092, B:102:0x00a0, B:104:0x00b0, B:105:0x00b3, B:107:0x00bf, B:109:0x00ce, B:112:0x00e1, B:114:0x00f4, B:115:0x0104, B:117:0x0120, B:120:0x0127, B:122:0x012d, B:124:0x0131, B:127:0x0160, B:129:0x0165, B:130:0x016a), top: B:144:0x001d, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void unloadPlugin(java.lang.String r12) {
        /*
            Method dump skipped, instruction units count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.PythonPluginsEngine.unloadPlugin(java.lang.String):void");
    }

    private final void refreshImportCaches(String pluginId, File moduleDir) {
        Python python = this.python;
        if (python == null) {
            return;
        }
        try {
            PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-21831695718201L));
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-21848875587385L);
            PyObject pyObject = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-21913300096825L));
            if (pyObject != null && pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-21947659835193L), pluginId).toBoolean()) {
                pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22003494410041L), pluginId, null);
            }
            PyObject pyObject2 = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-22020674279225L));
            if (moduleDir != null && pyObject2 != null) {
                pyObject2.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22106573625145L), moduleDir.getAbsolutePath(), null);
            }
            python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22123753494329L)).callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22166703167289L), new Object[0]);
        } catch (PyException e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22244012578617L) + pluginId, e);
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void setPluginEnabled(String pluginId, boolean enabled, final Utilities.Callback<String> callback) {
        PluginsController pluginsController;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-22402926368569L);
        try {
            Plugin plugin = getPluginsController().getPlugins().get(pluginId);
            PyObject pyObject = this.pluginInstances.get(pluginId);
            if (plugin == null || pyObject == null) {
                throw new Exception(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22441581074233L) + pluginId);
            }
            if (PyObjectUtils.getBoolean(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-22523185452857L), false) == enabled && !plugin.hasError()) {
                if (callback != null) {
                    callback.run(null);
                    return;
                }
                return;
            }
            if (enabled) {
                getPluginsController().cleanupPlugin(pluginId);
                getPluginsController().getWatchdog().onPluginExecutionStarted(pluginId);
                try {
                    pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22574725060409L), new Object[0]);
                    getPluginsController().getWatchdog().onPluginExecutionFinished(pluginId);
                    pyObject.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22639149569849L), (Object) Boolean.TRUE);
                    pyObject.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22690689177401L), (Object) null);
                    plugin.setError(null);
                } finally {
                }
            } else {
                if (PyObjectUtils.getBoolean(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-22750818719545L), false)) {
                    getPluginsController().getWatchdog().onPluginExecutionStarted(pluginId);
                    try {
                        pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22802358327097L), new Object[0]);
                        pluginsController = getPluginsController();
                    } catch (Throwable th) {
                        try {
                            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-22875372771129L) + pluginId, th);
                            pluginsController = getPluginsController();
                        } finally {
                        }
                    }
                    pluginsController.getWatchdog().onPluginExecutionFinished(pluginId);
                }
                pyObject.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23025696626489L), (Object) Boolean.FALSE);
                getPluginsController().cleanupPlugin(pluginId);
            }
            plugin.setEnabled(enabled);
            pyObject.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23077236234041L), (Object) Boolean.valueOf(enabled));
            SharedPreferences.Editor editorEdit = getPluginsController().getPreferences().edit();
            editorEdit.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23111595972409L) + pluginId, enabled);
            editorEdit.apply();
            if (enabled) {
                getPluginsController().loadPluginSettings(pluginId);
            } else {
                getPluginsController().invalidatePluginSettings(pluginId);
            }
            getPluginsController().notifyPluginsChanged();
            if (callback != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback.run(null);
                    }
                });
            }
        } catch (Throwable th2) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23180315449145L) + pluginId, th2);
            if (enabled) {
                Plugin plugin2 = getPluginsController().getPlugins().get(pluginId);
                if (plugin2 != null) {
                    plugin2.setEnabled(false);
                    plugin2.setError(th2);
                }
                PyObject pyObject2 = this.pluginInstances.get(pluginId);
                if (pyObject2 != null) {
                    pyObject2.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23369294010169L), (Object) Boolean.FALSE);
                    pyObject2.put(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23403653748537L), (Object) th2.getMessage());
                }
                SharedPreferences.Editor editorEdit2 = getPluginsController().getPreferences().edit();
                editorEdit2.putBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23463783290681L) + pluginId, false);
                editorEdit2.apply();
                getPluginsController().cleanupPlugin(pluginId);
            }
            if (callback != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback.run(AppUtils.stackTraceToString(th2));
                    }
                });
            }
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void deletePlugin(String pluginId, final Utilities.Callback<String> callback) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-23532502767417L);
        unloadPlugin(pluginId);
        try {
            PipController.INSTANCE.uninstallDependencies(pluginId);
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23571157473081L) + pluginId, e);
        }
        INSTANCE.deleteFileIfExists(new File(getPluginsController().getPluginsDir(), pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-23734366230329L)));
        PluginsController.Companion companion = PluginsController.INSTANCE;
        if (companion.isPluginPinned(pluginId)) {
            companion.setPluginPinned(pluginId, false);
        }
        getPluginsController().clearPluginSettingsPreferences(pluginId, true);
        getPluginsController().getPlugins().remove(pluginId);
        getPluginsController().notifyPluginsChanged();
        if (callback != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    callback.run(null);
                }
            });
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public String getPluginPath(String id) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-23751546099513L);
        return getPluginsController().getPluginsDir().getAbsolutePath() + File.separator + id + Deobfuscator$exteraGramDev$TMessagesProj.getString(-23764431001401L);
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void openInExternalApp(String id) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-23781610870585L);
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return;
        }
        File file = new File(getPluginPath(id));
        if (file.exists()) {
            PluginFileViewer.INSTANCE.open(safeLastFragment, file, id + Deobfuscator$exteraGramDev$TMessagesProj.getString(-23794495772473L));
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void sharePlugin(String id) {
        Activity parentActivity;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-23828855510841L);
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null || (parentActivity = safeLastFragment.getParentActivity()) == null) {
            return;
        }
        String pluginPath = getPluginPath(id);
        File file = new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-23841740412729L));
        SimpliFiles.directory(file).create();
        File file2 = new File(file, id + Deobfuscator$exteraGramDev$TMessagesProj.getString(-23863215249209L));
        try {
            SimpliFiles.file(pluginPath).copyTo(file2, OverwritePolicy.REPLACE);
            Uri uriForFile = FileProvider.getUriForFile(parentActivity, ApplicationLoader.getApplicationId() + Deobfuscator$exteraGramDev$TMessagesProj.getString(-23897574987577L), file2);
            Intent intent = new Intent(Deobfuscator$exteraGramDev$TMessagesProj.getString(-23940524660537L));
            intent.setFlags(1);
            intent.putExtra(Deobfuscator$exteraGramDev$TMessagesProj.getString(-24056488777529L), uriForFile);
            intent.setType(Deobfuscator$exteraGramDev$TMessagesProj.getString(-24176747861817L));
            safeLastFragment.startActivityForResult(Intent.createChooser(intent, LocaleController.getString(C2797R.string.ShareFile)), 500);
            file2.deleteOnExit();
        } catch (IOException e) {
            FileLog.m1048e(e);
        } catch (IllegalArgumentException e2) {
            FileLog.m1048e(e2);
        } catch (Exception e3) {
            FileLog.m1048e(e3);
        }
    }

    public final void loadPluginFromFile(String filePath, Plugin pluginMetadata, Utilities.Callback<String> callback) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-24266942175033L);
        loadPluginFromFile(filePath, pluginMetadata, callback, null);
    }

    public final void loadPluginFromFile(final String filePath, final Plugin pluginMetadata, final Utilities.Callback<String> callback, final PipController.InstallerDelegate delegate) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-24305596880697L);
        PluginsController.INSTANCE.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                PythonPluginsEngine.$r8$lambda$Uz7P7RWb8TcyDXFz8YyO_rscMmI(pluginMetadata, this, filePath, delegate, callback);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x00cf A[Catch: all -> 0x00a6, TRY_LEAVE, TryCatch #2 {all -> 0x00a6, blocks: (B:92:0x0060, B:94:0x0066, B:101:0x00a9, B:103:0x00cf), top: B:139:0x0060 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:148:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0066 A[Catch: all -> 0x00a6, TRY_LEAVE, TryCatch #2 {all -> 0x00a6, blocks: (B:92:0x0060, B:94:0x0066, B:101:0x00a9, B:103:0x00cf), top: B:139:0x0060 }] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void $r8$lambda$Uz7P7RWb8TcyDXFz8YyO_rscMmI(com.exteragram.messenger.plugins.Plugin r9, com.exteragram.messenger.plugins.PythonPluginsEngine r10, java.lang.String r11, com.exteragram.messenger.plugins.pip.PipController.InstallerDelegate r12, final org.telegram.messenger.Utilities.Callback r13) {
        /*
            Method dump skipped, instruction units count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.PythonPluginsEngine.$r8$lambda$Uz7P7RWb8TcyDXFz8YyO_rscMmI(com.exteragram.messenger.plugins.Plugin, com.exteragram.messenger.plugins.PythonPluginsEngine, java.lang.String, com.exteragram.messenger.plugins.pip.PipController$InstallerDelegate, org.telegram.messenger.Utilities$Callback):void");
    }

    public static final void loadPluginFromFile$lambda$0$1(Utilities.Callback callback, Throwable th) {
        callback.run(AppUtils.stackTraceToString(th));
    }

    public final PluginsController.PluginValidationResult validatePluginFromFile(String filePath) {
        String string;
        String string2;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-24344251586361L);
        if (!new File(filePath).exists()) {
            return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-24382906292025L));
        }
        try {
            Map<String, String> pluginMetadata = parsePluginMetadata(filePath);
            String str = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-24481690539833L));
            String str2 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-24494575441721L));
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (str != null) {
                    if (!new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-24898302367545L)).matches(str)) {
                        return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-25027151386425L));
                    }
                    String str3 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-25594087069497L));
                    if (str3 != null) {
                        Matcher matcher = VERSION_PATTERN.matcher(str3);
                        if (!matcher.matches()) {
                            return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-25645626677049L) + str3);
                        }
                        String strGroup = matcher.group(1);
                        if (strGroup == null) {
                            return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-25735820990265L) + str3);
                        }
                        String strGroup2 = matcher.group(2);
                        if (strGroup2 != null && (string2 = StringsKt.trim((CharSequence) strGroup2).toString()) != null) {
                            if (!AppUtils.compareVersions(strGroup, BuildVars.BUILD_VERSION_STRING, string2)) {
                                return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-25916209616697L) + str3 + Deobfuscator$exteraGramDev$TMessagesProj.getString(-26040763668281L) + BuildVars.BUILD_VERSION_STRING);
                            }
                        }
                        return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-25826015303481L) + str3);
                    }
                    String str4 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26118073079609L));
                    if (str4 != null) {
                        Matcher matcher2 = VERSION_PATTERN.matcher(str4);
                        if (!matcher2.matches()) {
                            return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-26169612687161L) + str4);
                        }
                        String strGroup3 = matcher2.group(1);
                        if (strGroup3 == null) {
                            return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-26259807000377L) + str4);
                        }
                        String strGroup4 = matcher2.group(2);
                        if (strGroup4 != null && (string = StringsKt.trim((CharSequence) strGroup4).toString()) != null) {
                            if (!AppUtils.compareVersions(strGroup3, SDK_VERSION, string)) {
                                return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-26440195626809L) + str4 + Deobfuscator$exteraGramDev$TMessagesProj.getString(-26564749678393L) + SDK_VERSION);
                            }
                        }
                        return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-26350001313593L) + str4);
                    }
                    if (str2 != null) {
                        Plugin plugin = new Plugin(str, str2);
                        plugin.setEngine(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26749433272121L));
                        String string3 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26779498043193L));
                        if (string3 == null) {
                            string3 = LocaleController.getString(C2797R.string.PluginNoAuthor);
                        }
                        plugin.setAuthor(string3);
                        String string4 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26809562814265L));
                        if (string4 == null) {
                            string4 = LocaleController.getString(C2797R.string.PluginNoDescription);
                        }
                        plugin.setDescription(string4);
                        plugin.setIcon(pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26861102421817L)));
                        String string5 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26882577258297L));
                        if (string5 == null) {
                            string5 = Deobfuscator$exteraGramDev$TMessagesProj.getString(-26916936996665L);
                        }
                        plugin.setVersion(string5);
                        plugin.setAppVersion(str3);
                        plugin.setSdkVersion(str4);
                        String str5 = pluginMetadata.get(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26934116865849L));
                        if (str5 != null && str5.length() != 0) {
                            List<String> listSplit = new Regex(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26989951440697L)).split(str5, 0);
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit, 10));
                            Iterator<T> it = listSplit.iterator();
                            while (it.hasNext()) {
                                arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
                            }
                            ArrayList arrayList2 = new ArrayList();
                            int size = arrayList.size();
                            int i = 0;
                            while (i < size) {
                                Object obj = arrayList.get(i);
                                i++;
                                if (((String) obj).length() > 0) {
                                    arrayList2.add(obj);
                                }
                            }
                            plugin.setRequirements(arrayList2);
                        }
                        plugin.setEnabled(getPluginsController().getPreferences().getBoolean(Deobfuscator$exteraGramDev$TMessagesProj.getString(-27062965884729L) + str, false));
                        return new PluginsController.PluginValidationResult(plugin, null);
                    }
                    throw new IllegalArgumentException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-26642059089721L).toString());
                }
                throw new IllegalArgumentException(Deobfuscator$exteraGramDev$TMessagesProj.getString(-24790928185145L).toString());
            }
            return new PluginsController.PluginValidationResult(null, Deobfuscator$exteraGramDev$TMessagesProj.getString(-24516050278201L));
        } catch (PyException e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-27131685361465L) + filePath + Deobfuscator$exteraGramDev$TMessagesProj.getString(-27264829347641L) + e.getMessage(), e);
            return new PluginsController.PluginValidationResult(null, e.getMessage());
        } catch (Throwable th) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-27307779020601L) + filePath, th);
            return new PluginsController.PluginValidationResult(null, th.getMessage());
        }
    }

    public final List<SettingItem> parsePySettingDefinitions(List<? extends PyObject> pyDefinitionsList) throws Throwable {
        Object editTextSetting;
        String string;
        View view;
        Object customSetting;
        String string2;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-27462397843257L);
        ArrayList arrayList = new ArrayList(pyDefinitionsList.size());
        for (PyObject pyObject : pyDefinitionsList) {
            Object headerSetting = null;
            String string3 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-27539707254585L), null);
            if (string3 == null) {
                FileLog.m1049w(Deobfuscator$exteraGramDev$TMessagesProj.getString(-27561182091065L));
            } else {
                String string4 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-27818880128825L), null);
                String string5 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-27836059998009L), null);
                String string6 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-27857534834489L), null);
                String string7 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-27891894572857L), null);
                PyObject pyObject2 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-27913369409337L));
                PyObject pyObject3 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-27956319082297L));
                String string8 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28016448624441L), null);
                PyObject pyObject4 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28063693264697L));
                PyObject pyObject5 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28098053003065L));
                PyObject pyObject6 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28136707708729L));
                switch (string3.hashCode()) {
                    case -1866021310:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28248376858425L))) {
                            String string9 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28506074896185L), null);
                            boolean z = PyObjectUtils.getBoolean(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28527549732665L), false);
                            int i = PyObjectUtils.getInt(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28570499405625L), 256);
                            String string10 = PyObjectUtils.getString(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28617744045881L), null);
                            if (string4 != null && string9 != null) {
                                if (pyObject4 == null || (string = pyObject4.toString()) == null) {
                                    string = Deobfuscator$exteraGramDev$TMessagesProj.getString(-28639218882361L);
                                }
                                editTextSetting = new EditTextSetting(string4, string9, string, z, i, string10, pyObject2);
                                headerSetting = editTextSetting;
                            }
                        }
                        break;
                    case -1349088399:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28325686269753L))) {
                            PyObject pyObject7 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28690758489913L));
                            PyObject pyObject8 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28712233326393L));
                            PyObject pyObject9 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28733708162873L));
                            PyObject pyObject10 = (PyObject) pyObject.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-28768067901241L));
                            if (pyObject9 != null) {
                                CustomSetting.Factory factory = (CustomSetting.Factory) PyObjectUtils.toJavaCompat(pyObject9, CustomSetting.Factory.class);
                                if (factory != null) {
                                    customSetting = pyObject10 == null ? new CustomSetting((CustomSetting.Factory<?>) factory, pyObject5, pyObject6, pyObject3, string8) : new CustomSetting(factory, pyObject10, pyObject5, pyObject6, pyObject3, string8);
                                    headerSetting = customSetting;
                                }
                            } else if (pyObject8 != null) {
                                UItem uItem = (UItem) PyObjectUtils.toJavaCompat(pyObject8, UItem.class);
                                if (uItem != null) {
                                    customSetting = new CustomSetting(uItem, pyObject5, pyObject6, pyObject3, string8);
                                    headerSetting = customSetting;
                                }
                            } else if (pyObject7 != null && (view = (View) PyObjectUtils.toJavaCompat(pyObject7, View.class)) != null) {
                                customSetting = new CustomSetting(view, pyObject5, pyObject6, pyObject3, string8);
                                headerSetting = customSetting;
                            }
                        }
                        break;
                    case -1221270899:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28355751040825L)) && string5 != null) {
                            headerSetting = new HeaderSetting(string5);
                        }
                        break;
                    case -889473228:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28445945354041L)) && string4 != null && string5 != null && pyObject4 != null) {
                            editTextSetting = new SwitchSetting(string4, string5, pyObject4.toBoolean(), string6, string7, pyObject2, pyObject3, string8);
                            headerSetting = editTextSetting;
                        }
                        break;
                    case 3556653:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28424470517561L))) {
                            boolean z2 = PyObjectUtils.getBoolean(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28643513849657L), false);
                            boolean z3 = PyObjectUtils.getBoolean(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28673578620729L), false);
                            if (string5 != null) {
                                headerSetting = new TextSetting(string5, string6, string7, z2, z3, pyObject5, pyObject6, pyObject3, string8);
                            }
                        }
                        break;
                    case 100358090:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28222607054649L)) && string4 != null && string5 != null) {
                            if (pyObject4 == null || (string2 = pyObject4.toString()) == null) {
                                string2 = Deobfuscator$exteraGramDev$TMessagesProj.getString(-28501779928889L);
                            }
                            editTextSetting = new InputSetting(string4, string5, string2, string6, string7, pyObject2, pyObject3, string8);
                            headerSetting = editTextSetting;
                        }
                        break;
                    case 1191572447:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28385815811897L))) {
                            String[] stringArray = PyObjectUtils.getStringArray(pyObject, Deobfuscator$exteraGramDev$TMessagesProj.getString(-28476010125113L), null);
                            if (string4 != null && string5 != null && stringArray != null && stringArray.length != 0 && pyObject4 != null) {
                                editTextSetting = new SelectorSetting(string4, string5, pyObject4.toInt(), stringArray, string7, pyObject2, pyObject3, string8);
                                headerSetting = editTextSetting;
                            }
                        }
                        break;
                    case 1674318617:
                        if (string3.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28291326531385L))) {
                            headerSetting = new DividerSetting(string5);
                        }
                        break;
                }
                if (headerSetting != null) {
                    arrayList.add(headerSetting);
                }
            }
        }
        return arrayList;
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public List<SettingItem> loadPluginSettings(String id) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-28823902476089L);
        try {
            Plugin plugin = getPluginsController().getPlugins().get(id);
            PyObject pyObject = this.pluginInstances.get(id);
            if (plugin != null && plugin.isEnabled() && !plugin.hasError() && pyObject != null) {
                PyObject pyObjectCallAttr = pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28836787377977L), new Object[0]);
                if (pyObjectCallAttr == null) {
                    return null;
                }
                List<PyObject> listAsList = pyObjectCallAttr.asList();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-28905506854713L);
                if (listAsList.isEmpty()) {
                    return null;
                }
                return parsePySettingDefinitions(listAsList);
            }
            getPluginsController().invalidatePluginSettings(id);
            return null;
        } catch (Exception e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-28957046462265L), e);
            return null;
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void executeOnAppEvent(String eventType) throws Exception {
        Python python;
        PyObject module;
        PyObject pyObject;
        PluginsController pluginsController;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-29090190448441L);
        if (!sdkInitialized || ExteraConfig.getPluginsSafeMode() || (python = getPython()) == null || (module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-29133140121401L))) == null || (pyObject = (PyObject) module.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-29184679728953L))) == null) {
            return;
        }
        PyObject pyObjectCall = pyObject.call(eventType);
        try {
            PyObject pyObject2 = this.debuggerListener;
            if (pyObject2 != null) {
                try {
                    pyObject2.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-29223334434617L), pyObjectCall);
                } catch (PyException e) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-29279169009465L), e);
                }
            }
            for (Map.Entry<String, PyObject> entry : this.pluginInstances.entrySet()) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-29493917374265L);
                Map.Entry<String, PyObject> entry2 = entry;
                String key = entry2.getKey();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-29536867047225L);
                String str = key;
                PyObject value = entry2.getValue();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-29605586523961L);
                PyObject pyObject3 = value;
                if (PyObjectUtils.getBoolean(pyObject3, Deobfuscator$exteraGramDev$TMessagesProj.getString(-29674306000697L), false) && PyObjectUtils.getString(pyObject3, Deobfuscator$exteraGramDev$TMessagesProj.getString(-29708665739065L), null) == null) {
                    getPluginsController().getWatchdog().onPluginExecutionStarted(str);
                    try {
                        try {
                            pyObject3.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-29768795281209L), pyObjectCall);
                            pluginsController = getPluginsController();
                        } catch (PyException e2) {
                            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-29824629856057L) + eventType + Deobfuscator$exteraGramDev$TMessagesProj.getString(-29923414103865L) + str, e2);
                            pluginsController = getPluginsController();
                        }
                        pluginsController.getWatchdog().onPluginExecutionFinished(str);
                    } catch (Throwable th) {
                        getPluginsController().getWatchdog().onPluginExecutionFinished(str);
                        throw th;
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(pyObjectCall, null);
        } catch (Throwable th2) {
            try {
                throw th2;
            } catch (Throwable th3) {
                AutoCloseableKt.closeFinally(pyObjectCall, th2);
                throw th3;
            }
        }
    }

    private final <T> PluginsController.HookResult<T> executeHook(PyObject pluginInstance, T initialValue, Class<T> valueClass, String pyResultKey, PyMethodCaller<T> caller, Utilities.Callback<PyException> errorLogger) {
        if (pluginInstance != null) {
            try {
                PyObject pyObjectCall = caller.call(pluginInstance, initialValue);
                if (pyObjectCall != null) {
                    String string = PyObjectUtils.getString(pyObjectCall, Deobfuscator$exteraGramDev$TMessagesProj.getString(-29949183907641L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-29987838613305L));
                    if (string != null && StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30022198351673L), false, 2, (Object) null)) {
                        return new PluginsController.HookResult<>(null, true, false);
                    }
                    if (string != null && (StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30052263122745L), false, 2, (Object) null) || StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30082327893817L), false, 2, (Object) null))) {
                        PyObject pyObject = (PyObject) pyObjectCall.get((Object) pyResultKey);
                        if (pyObject != null) {
                            initialValue = (T) pyObject.toJava(valueClass);
                        }
                        if (StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30138162468665L), false, 2, (Object) null)) {
                            return new PluginsController.HookResult<>(initialValue, false, true);
                        }
                    }
                }
            } catch (PyException e) {
                errorLogger.run(e);
            }
        }
        return new PluginsController.HookResult<>(initialValue, false, false);
    }

    private final <T> PluginsController.HookResult<T> executeHook(String pluginId, T initialValue, Class<T> valueClass, String pyResultKey, PyMethodCaller<T> caller, Utilities.Callback<PyException> errorLogger) throws Throwable {
        PythonPluginsEngine pythonPluginsEngine;
        getPluginsController().getWatchdog().onPluginExecutionStarted(pluginId);
        try {
            pythonPluginsEngine = this;
        } catch (Throwable th) {
            th = th;
            pythonPluginsEngine = this;
        }
        try {
            PluginsController.HookResult<T> hookResultExecuteHook = pythonPluginsEngine.executeHook(this.pluginInstances.get(pluginId), initialValue, valueClass, pyResultKey, caller, errorLogger);
            pythonPluginsEngine.getPluginsController().getWatchdog().onPluginExecutionFinished(pluginId);
            return hookResultExecuteHook;
        } catch (Throwable th2) {
            th = th2;
            Throwable th3 = th;
            pythonPluginsEngine.getPluginsController().getWatchdog().onPluginExecutionFinished(pluginId);
            throw th3;
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public PluginsController.HookResult<TLObject> executePreRequestHook(final String requestName, final int account, TLObject request, final String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-30193997043513L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-30245536651065L);
        return executeHook(pluginId, request, (Class<TLObject>) TLObject.class, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30284191356729L), (PyMethodCaller<TLObject>) new PyMethodCaller() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda22
            @Override // com.exteragram.messenger.plugins.PythonPluginsEngine.PyMethodCaller
            public final PyObject call(PyObject pyObject, Object obj) {
                return PythonPluginsEngine.$r8$lambda$3HuOhQN3SQ64XZXjHG3mX3zdIqY(requestName, account, pyObject, (TLObject) obj);
            }
        }, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda23
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-36902735959865L) + pluginId + Deobfuscator$exteraGramDev$TMessagesProj.getString(-37070239684409L) + requestName, (PyException) obj);
            }
        });
    }

    public static PyObject $r8$lambda$3HuOhQN3SQ64XZXjHG3mX3zdIqY(String str, int i, PyObject pyObject, TLObject tLObject) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-36791066810169L);
        return pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-36829721515833L), str, Integer.valueOf(i), tLObject);
    }

    public final PluginsController.HookResult<PluginsHooks.PostRequestResult> executePostRequestHook(String requestName, int account, TLObject response, TLRPC.TL_error error, PyObject pluginInstance) {
        String string;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-30318551095097L);
        if (pluginInstance != null) {
            try {
                PyObject pyObjectCallAttr = pluginInstance.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-30370090702649L), requestName, Integer.valueOf(account), response, error);
                if (pyObjectCallAttr != null && (string = PyObjectUtils.getString(pyObjectCallAttr, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30447400113977L), Deobfuscator$exteraGramDev$TMessagesProj.getString(-30486054819641L))) != null && (StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30490349786937L), false, 2, (Object) null) || StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30520414558009L), false, 2, (Object) null))) {
                    PyObject pyObject = (PyObject) pyObjectCallAttr.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-30576249132857L));
                    if (pyObject != null) {
                        response = (TLObject) pyObject.toJava(TLObject.class);
                    }
                    PyObject pyObject2 = (PyObject) pyObjectCallAttr.get((Object) Deobfuscator$exteraGramDev$TMessagesProj.getString(-30614903838521L));
                    if (pyObject2 != null) {
                        error = (TLRPC.TL_error) pyObject2.toJava(TLRPC.TL_error.class);
                    }
                    if (StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-30640673642297L), false, 2, (Object) null)) {
                        return new PluginsController.HookResult<>(new PluginsHooks.PostRequestResult(response, error), false, true);
                    }
                }
            } catch (PyException e) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-30696508217145L) + requestName, e);
            }
        }
        return new PluginsController.HookResult<>(new PluginsHooks.PostRequestResult(response, error), false, false);
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public PluginsController.HookResult<PluginsHooks.PostRequestResult> executePostRequestHook(String requestName, int account, TLObject response, TLRPC.TL_error error, String pluginId) throws Throwable {
        PythonPluginsEngine pythonPluginsEngine;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-30872601876281L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-30924141483833L);
        getPluginsController().getWatchdog().onPluginExecutionStarted(pluginId);
        try {
            pythonPluginsEngine = this;
        } catch (Throwable th) {
            th = th;
            pythonPluginsEngine = this;
        }
        try {
            PluginsController.HookResult<PluginsHooks.PostRequestResult> hookResultExecutePostRequestHook = pythonPluginsEngine.executePostRequestHook(requestName, account, response, error, this.pluginInstances.get(pluginId));
            pythonPluginsEngine.getPluginsController().getWatchdog().onPluginExecutionFinished(pluginId);
            return hookResultExecutePostRequestHook;
        } catch (Throwable th2) {
            th = th2;
            Throwable th3 = th;
            pythonPluginsEngine.getPluginsController().getWatchdog().onPluginExecutionFinished(pluginId);
            throw th3;
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public PluginsController.HookResult<TLRPC.Update> executeUpdateHook(final String updateName, final int account, TLRPC.Update update, String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-30962796189497L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-31010040829753L);
        return executeHook(pluginId, update, (Class<TLRPC.Update>) TLRPC.Update.class, Deobfuscator$exteraGramDev$TMessagesProj.getString(-31048695535417L), (PyMethodCaller<TLRPC.Update>) new PyMethodCaller() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda2
            @Override // com.exteragram.messenger.plugins.PythonPluginsEngine.PyMethodCaller
            public final PyObject call(PyObject pyObject, Object obj) {
                return PythonPluginsEngine.$r8$lambda$gogZ8_uy9hbJzmvkveYPLtlEcFs(updateName, account, pyObject, (TLRPC.Update) obj);
            }
        }, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda3
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37199088703289L) + updateName, (PyException) obj);
            }
        });
    }

    public static PyObject $r8$lambda$gogZ8_uy9hbJzmvkveYPLtlEcFs(String str, int i, PyObject pyObject, TLRPC.Update update) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-37096009488185L);
        return pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37134664193849L), str, Integer.valueOf(i), update);
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public PluginsController.HookResult<TLRPC.Updates> executeUpdatesHook(final String containerName, final int account, TLRPC.Updates updates, String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-31078760306489L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-31138889848633L);
        return executeHook(pluginId, updates, (Class<TLRPC.Updates>) TLRPC.Updates.class, Deobfuscator$exteraGramDev$TMessagesProj.getString(-31177544554297L), (PyMethodCaller<TLRPC.Updates>) new PyMethodCaller() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda9
            @Override // com.exteragram.messenger.plugins.PythonPluginsEngine.PyMethodCaller
            public final PyObject call(PyObject pyObject, Object obj) {
                return PythonPluginsEngine.$r8$lambda$66nPF4OSjZKgz4yasnWGOzZ1Egk(containerName, account, pyObject, (TLRPC.Updates) obj);
            }
        }, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda10
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37469671642937L) + containerName, (PyException) obj);
            }
        });
    }

    public static PyObject $r8$lambda$66nPF4OSjZKgz4yasnWGOzZ1Egk(String str, int i, PyObject pyObject, TLRPC.Updates updates) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-37362297460537L);
        return pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37400952166201L), str, Integer.valueOf(i), updates);
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public PluginsController.HookResult<SendMessagesHelper.SendMessageParams> executeSendMessageHook(final int account, SendMessagesHelper.SendMessageParams params, final String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-31211904292665L);
        return executeHook(pluginId, params, (Class<SendMessagesHelper.SendMessageParams>) SendMessagesHelper.SendMessageParams.class, Deobfuscator$exteraGramDev$TMessagesProj.getString(-31250558998329L), (PyMethodCaller<SendMessagesHelper.SendMessageParams>) new PyMethodCaller() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda17
            @Override // com.exteragram.messenger.plugins.PythonPluginsEngine.PyMethodCaller
            public final PyObject call(PyObject pyObject, Object obj) {
                return PythonPluginsEngine.$r8$lambda$_D6PxQm0hg98qBk_zHO3yV4BGHE(account, pyObject, (SendMessagesHelper.SendMessageParams) obj);
            }
        }, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda18
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37766024386361L) + pluginId, (PyException) obj);
            }
        });
    }

    public static PyObject $r8$lambda$_D6PxQm0hg98qBk_zHO3yV4BGHE(int i, PyObject pyObject, SendMessagesHelper.SendMessageParams sendMessageParams) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-37637175367481L);
        return pyObject.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37675830073145L), Integer.valueOf(i), sendMessageParams);
    }

    public final String fetchParameterValue(String filePath, String parameterName) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-31280623769401L);
        if (filePath == null) {
            return null;
        }
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                return parsePluginMetadata(filePath).get(parameterName);
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public final Map<String, String> parsePluginMetadata(String filePath) {
        HashMap map = new HashMap();
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                Python python = getPython();
                if (python == null) {
                    FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-31340753311545L) + filePath);
                    return map;
                }
                try {
                    PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-31589861414713L));
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-31714415466297L);
                    PyObject pyObjectCallAttr = module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-31778839975737L), filePath);
                    if (pyObjectCallAttr != null) {
                        Map<PyObject, PyObject> mapAsMap = pyObjectCallAttr.asMap();
                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-31834674550585L);
                        for (Map.Entry<PyObject, PyObject> entry : mapAsMap.entrySet()) {
                            map.put(entry.getKey().toString(), entry.getValue().toString());
                        }
                    }
                } catch (PyException e) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-31881919190841L) + filePath + Deobfuscator$exteraGramDev$TMessagesProj.getString(-32015063177017L) + e.getMessage(), e);
                    throw e;
                }
            }
        }
        return map;
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public Object getPluginSetting(String pluginId, String key, Object defaultValue) {
        Object java;
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32058012849977L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32096667555641L);
        ConcurrentHashMap<String, Object> concurrentHashMap = this.settingsCache.get(pluginId);
        if (concurrentHashMap != null && concurrentHashMap.containsKey(key)) {
            return concurrentHashMap.get(key);
        }
        Python python = getPython();
        if (python != null) {
            try {
                PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-32113847424825L));
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-32182566901561L);
                PyObject pyObjectCallAttr = module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-32246991411001L), pluginId, key, defaultValue);
                if (pyObjectCallAttr != null) {
                    if (defaultValue instanceof Boolean) {
                        java = Boolean.valueOf(pyObjectCallAttr.toBoolean());
                    } else if (defaultValue instanceof Integer) {
                        java = Integer.valueOf(pyObjectCallAttr.toInt());
                    } else if (defaultValue instanceof String) {
                        java = pyObjectCallAttr.toString();
                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32298531018553L);
                    } else if (defaultValue instanceof Float) {
                        java = Float.valueOf(pyObjectCallAttr.toFloat());
                    } else if (defaultValue instanceof Long) {
                        java = Long.valueOf(pyObjectCallAttr.toLong());
                    } else if (defaultValue == null) {
                        java = pyObjectCallAttr.toJava(Object.class);
                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32358660560697L);
                    } else {
                        java = pyObjectCallAttr.toJava(defaultValue.getClass());
                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32410200168249L);
                    }
                    ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> concurrentHashMap2 = this.settingsCache;
                    final Function1 function1 = new Function1() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda24
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return PythonPluginsEngine.$r8$lambda$QERKwSD6yL4J8XJCKdmRa2I5DR8((String) obj);
                        }
                    };
                    ConcurrentHashMap<String, Object> concurrentHashMapComputeIfAbsent = concurrentHashMap2.computeIfAbsent(pluginId, new Function() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda25
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return PythonPluginsEngine.$r8$lambda$T5aUeMCGEOtaNiCJriFIVhbHSkA(function1, obj);
                        }
                    });
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-32461739775801L);
                    concurrentHashMapComputeIfAbsent.put(key, java);
                    return java;
                }
            } catch (PyException e) {
                FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-32551934089017L) + pluginId + '/' + key, e);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static ConcurrentHashMap $r8$lambda$QERKwSD6yL4J8XJCKdmRa2I5DR8(String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-37955002947385L);
        return new ConcurrentHashMap();
    }

    public static ConcurrentHashMap $r8$lambda$T5aUeMCGEOtaNiCJriFIVhbHSkA(Function1 function1, Object obj) {
        return (ConcurrentHashMap) function1.invoke(obj);
    }

    public static ConcurrentHashMap $r8$lambda$H3KBjR7kR4EblHmumAscDlVGoa4(Function1 function1, Object obj) {
        return (ConcurrentHashMap) function1.invoke(obj);
    }

    public static ConcurrentHashMap $r8$lambda$M02QGUTSwEr0Np6WtwhMk889TKc(String str) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-37967887849273L);
        return new ConcurrentHashMap();
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void setPluginSetting(String pluginId, String key, Object value) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32680783107897L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32719437813561L);
        ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> concurrentHashMap = this.settingsCache;
        final Function1 function1 = new Function1() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PythonPluginsEngine.$r8$lambda$M02QGUTSwEr0Np6WtwhMk889TKc((String) obj);
            }
        };
        ConcurrentHashMap<String, Object> concurrentHashMapComputeIfAbsent = concurrentHashMap.computeIfAbsent(pluginId, new Function() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PythonPluginsEngine.$r8$lambda$H3KBjR7kR4EblHmumAscDlVGoa4(function1, obj);
            }
        });
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-32736617682745L);
        concurrentHashMapComputeIfAbsent.put(key, value);
        Python python = getPython();
        if (python == null) {
            return;
        }
        try {
            PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-32826811995961L));
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-32895531472697L);
            module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-32959955982137L), pluginId, key, value);
        } catch (PyException e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33011495589689L) + pluginId + '/' + key, e);
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void clearPluginSettings(String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-33140344608569L);
        this.settingsCache.remove(pluginId);
        Python python = getPython();
        if (python == null) {
            return;
        }
        try {
            PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33178999314233L));
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-33247718790969L);
            module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33312143300409L), pluginId);
        } catch (PyException e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33376567809849L) + pluginId, e);
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public Map<String, ?> getAllPluginSettings(String pluginId) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-33535481599801L);
        Python python = getPython();
        if (python == null) {
            return null;
        }
        try {
            PyObject module = python.getModule(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33574136305465L));
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-33642855782201L);
            PyObject pyObjectCallAttr = module.callAttr(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33707280291641L), pluginId);
            if (pyObjectCallAttr != null) {
                HashMap map = new HashMap();
                Map<PyObject, PyObject> mapAsMap = pyObjectCallAttr.asMap();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-33780294735673L);
                for (Map.Entry<PyObject, PyObject> entry : mapAsMap.entrySet()) {
                    PyObject key = entry.getKey();
                    PyObject value = entry.getValue();
                    if (key != null) {
                        map.put(key.toString(), value != null ? value.toJava(Object.class) : null);
                    }
                }
                this.settingsCache.put(pluginId, new ConcurrentHashMap<>(map));
                return map;
            }
        } catch (PyException e) {
            FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-33827539375929L) + pluginId, e);
        }
        return null;
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void showInstallDialog(final BaseFragment fragment, InstallPluginBottomSheet.PluginInstallParams params) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-33995043100473L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34033697806137L);
        File file = new File(params.getFilePath());
        final String strFetchParameterValue = fetchParameterValue(params.getFilePath(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-34063762577209L));
        if (TextUtils.isEmpty(strFetchParameterValue) && file.exists()) {
            strFetchParameterValue = file.getName();
        }
        final PluginsController.PluginValidationResult pluginValidationResultValidatePluginFromFile = validatePluginFromFile(params.getFilePath());
        if (pluginValidationResultValidatePluginFromFile.getPlugin() != null) {
            new InstallPluginBottomSheet(fragment, pluginValidationResultValidatePluginFromFile, params).show();
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    BaseFragment baseFragment = fragment;
                    BulletinFactory.m1143of(baseFragment).createSimpleBulletin(C2797R.raw.error, LocaleController.formatString(C2797R.string.PluginInstallError, strFetchParameterValue), LocaleUtils.createCopySpan(baseFragment), new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda16
                        @Override // java.lang.Runnable
                        public final void run() {
                            PythonPluginsEngine.showInstallDialog$lambda$0$0(pluginValidationResult, baseFragment);
                        }
                    }).show();
                }
            });
        }
    }

    public static final void showInstallDialog$lambda$0$0(PluginsController.PluginValidationResult pluginValidationResult, BaseFragment baseFragment) {
        if (AndroidUtilities.addToClipboard(pluginValidationResult.getError())) {
            BulletinFactory.m1143of(baseFragment).createCopyBulletin(LocaleController.getString(C2797R.string.TextCopied)).show();
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void openPluginSettings(String id, BaseFragment fragment) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34085237413689L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34098122315577L);
        Plugin plugin = getPluginsController().getPlugins().get(id);
        if (plugin != null) {
            openPluginSettings(plugin, fragment);
        }
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void openPluginSettings(final Plugin plugin, final BaseFragment fragment) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34136777021241L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34166841792313L);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                fragment.presentFragment(new PluginSettingsActivity(plugin));
            }
        });
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void openPluginSetting(final Plugin plugin, final String linkAlias, final BaseFragment fragment) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34205496497977L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34235561269049L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34278510942009L);
        PluginsController.INSTANCE.runOnPluginsQueue(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                PythonPluginsEngine.$r8$lambda$XQrX3nNpWeXz7pQ_tyFSPmyO8xo(plugin, linkAlias, this, fragment);
            }
        });
    }

    public static void $r8$lambda$XQrX3nNpWeXz7pQ_tyFSPmyO8xo(Plugin plugin, String str, PythonPluginsEngine pythonPluginsEngine, final BaseFragment baseFragment) throws Throwable {
        final PluginSettingsActivity settingsLinkPrefix;
        FileLog.m1045d(Deobfuscator$exteraGramDev$TMessagesProj.getString(-37980772751161L) + plugin.getId() + '/' + str);
        if (!StringsKt.contains$default((CharSequence) str, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-38088146933561L), false, 2, (Object) null)) {
            settingsLinkPrefix = new PluginSettingsActivity(plugin, str);
        } else {
            List<SettingItem> pySettingDefinitions = pythonPluginsEngine.getPluginsController().getSettings().get(plugin.getId());
            if (pySettingDefinitions == null) {
                return;
            }
            String[] strArr = (String[]) StringsKt.split$default((CharSequence) str, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-38096736868153L)}, false, 0, 6, (Object) null).toArray(new String[0]);
            int length = strArr.length - 1;
            TextSetting textSetting = null;
            for (int i = 0; i < length; i++) {
                String str2 = strArr[i];
                Iterator<SettingItem> it = pySettingDefinitions.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SettingItem next = it.next();
                    if ((next instanceof TextSetting) && Intrinsics.areEqual(str2, next.getLinkAlias())) {
                        textSetting = (TextSetting) next;
                        try {
                            PyObject createSubFragmentCallback = ((TextSetting) next).getCreateSubFragmentCallback();
                            PyObject pyObjectCall = createSubFragmentCallback != null ? createSubFragmentCallback.call(new Object[0]) : null;
                            if (pyObjectCall != null) {
                                List<PyObject> listAsList = pyObjectCall.asList();
                                Deobfuscator$exteraGramDev$TMessagesProj.getString(-38105326802745L);
                                try {
                                    pySettingDefinitions = pythonPluginsEngine.parsePySettingDefinitions(listAsList);
                                } catch (Exception unused) {
                                }
                            }
                        } catch (Exception unused2) {
                        }
                    }
                }
                if (textSetting == null && pySettingDefinitions.isEmpty()) {
                    SettingsRegistry.getInstance().onSettingNotFound(baseFragment);
                    return;
                }
            }
            if (textSetting == null) {
                return;
            }
            PluginSettingsActivity pluginSettingsActivity = new PluginSettingsActivity(plugin, textSetting.getText(), pySettingDefinitions, textSetting.getCreateSubFragmentCallback(), strArr[strArr.length - 1]);
            Object[] objArrCopyOf = Arrays.copyOf(strArr, strArr.length - 1);
            Deobfuscator$exteraGramDev$TMessagesProj.getString(-38156866410297L);
            settingsLinkPrefix = pluginSettingsActivity.setSettingsLinkPrefix(ArraysKt.joinToString$default(objArrCopyOf, Deobfuscator$exteraGramDev$TMessagesProj.getString(-38208406017849L), (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                baseFragment.presentFragment(settingsLinkPrefix);
            }
        });
    }

    @Override // com.exteragram.messenger.plugins.PluginsController.PluginsEngine
    public void openPluginSetting(String pluginId, String linkAlias, BaseFragment fragment) {
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34317165647673L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34355820353337L);
        Deobfuscator$exteraGramDev$TMessagesProj.getString(-34398770026297L);
        Plugin plugin = getPluginsController().getPlugins().get(pluginId);
        if (plugin != null) {
            openPluginSetting(plugin, linkAlias, fragment);
        }
    }

    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PythonPluginsEngine$Updater;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "Companion", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
    public static final class Updater {
        private static int TAG;
        private static boolean isLoading;
        private static long lastCheckUpdateTime;
        private static boolean notifyWhenChangeStatus;
        private static int status;

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final Pattern PYTHON_SDK_APP_VERSION_PATTERN = Pattern.compile(Deobfuscator$exteraGramDev$TMessagesProj.getString(-39024449804089L));
        private static final Pattern PYTHON_SDK_APP_VERSION_CODE_PATTERN = Pattern.compile(Deobfuscator$exteraGramDev$TMessagesProj.getString(-39144708888377L));
        private static final Runnable notifyRunnable = new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$Updater$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pluginsPySdkInfoChanged, new Object[0]);
            }
        };

        @JvmStatic
        public static final void checkUpdates() {
            INSTANCE.checkUpdates();
        }

        @JvmStatic
        public static final void checkUpdates(boolean z) {
            INSTANCE.checkUpdates(z);
        }

        @JvmStatic
        public static final void deleteSdkUpdateFile() {
            INSTANCE.deleteSdkUpdateFile();
        }

        @JvmStatic
        public static final File getPythonCurrentSdkFile() {
            return INSTANCE.getPythonCurrentSdkFile();
        }

        @JvmStatic
        public static final File getPythonSdkUpdateFile() {
            return INSTANCE.getPythonSdkUpdateFile();
        }

        @JvmStatic
        public static final CharSequence getStateString() {
            return INSTANCE.getStateString();
        }

        @JvmStatic
        public static final CharSequence getVersion() {
            return INSTANCE.getVersion();
        }

        @JvmStatic
        public static final String hashBytes(InputStream inputStream) {
            return INSTANCE.hashBytes(inputStream);
        }

        @JvmStatic
        public static final boolean isAppVersionCodeCompatible(String str, String str2) {
            return INSTANCE.isAppVersionCodeCompatible(str, str2);
        }

        @JvmStatic
        public static final boolean isAppVersionCompatible(String str, String str2) {
            return INSTANCE.isAppVersionCompatible(str, str2);
        }

        @JvmStatic
        public static final boolean isSdkFromApk() {
            return INSTANCE.isSdkFromApk();
        }

        @JvmStatic
        public static final boolean isSdkVersionNewer(String str, boolean z) {
            return INSTANCE.isSdkVersionNewer(str, z);
        }

        @JvmStatic
        public static final Companion.PythonSdkUpdateInfo parsePythonSdkUpdateResponse(TLRPC.messages_Messages messages_messages) {
            return INSTANCE.parsePythonSdkUpdateResponse(messages_messages);
        }

        @JvmStatic
        public static final File requestSdkFromApkFile() {
            return INSTANCE.requestSdkFromApkFile();
        }

        @JvmStatic
        public static final void restoreSdkFromApk() {
            INSTANCE.restoreSdkFromApk();
        }

        @JvmStatic
        public static final void savePythonSdkArchive(TLRPC.Message message, TLRPC.Document document) {
            INSTANCE.savePythonSdkArchive(message, document);
        }

        @JvmStatic
        public static final void savePythonSdkArchive(TLRPC.Message message, TLRPC.Document document, boolean z) {
            INSTANCE.savePythonSdkArchive(message, document, z);
        }

        @JvmStatic
        public static final InputStream sdkFromApk() {
            return INSTANCE.sdkFromApk();
        }

        @JvmStatic
        public static final void setBuildFromApk(boolean z) {
            INSTANCE.setBuildFromApk(z);
        }

        @JvmStatic
        public static final void zipFolder(File file, File file2) {
            INSTANCE.zipFolder(file, file2);
        }

        /* JADX INFO: loaded from: classes4.dex */
        @Metadata(m876d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001GB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u001a\u001a\u00020\u001bH\u0007J\n\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0007J\b\u0010\u001d\u001a\u00020\u001eH\u0007J\b\u0010\u001f\u001a\u00020\u000bH\u0007J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000bH\u0007J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001eH\u0007J\b\u0010&\u001a\u00020'H\u0007J\b\u0010(\u001a\u00020'H\u0007J\b\u0010)\u001a\u00020'H\u0007J\b\u0010*\u001a\u00020!H\u0007J\b\u0010+\u001a\u00020!H\u0007J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020\u000bH\u0007J\b\u0010-\u001a\u00020!H\u0007J\u0010\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u00020'H\u0002J\u0010\u00100\u001a\u00020!2\u0006\u00101\u001a\u00020\rH\u0002J\u0012\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u000205H\u0007J\u0018\u00106\u001a\u00020\u000b2\u0006\u00107\u001a\u00020$2\u0006\u00108\u001a\u00020\u000bH\u0007J\u0018\u00109\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020$2\u0006\u0010;\u001a\u00020$H\u0007J\u0018\u0010<\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020$2\u0006\u0010;\u001a\u00020$H\u0007J\u0018\u0010=\u001a\u00020!2\u0006\u0010>\u001a\u00020'2\u0006\u0010?\u001a\u00020'H\u0007J\u0018\u0010@\u001a\u00020!2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u000bH\u0002J\u001c\u0010D\u001a\u00020!2\b\u0010E\u001a\u0004\u0018\u00010F2\b\u0010A\u001a\u0004\u0018\u00010BH\u0007J$\u0010D\u001a\u00020!2\b\u0010E\u001a\u0004\u0018\u00010F2\b\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010C\u001a\u00020\u000bH\u0007R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006H"}, m877d2 = {"Lcom/exteragram/messenger/plugins/PythonPluginsEngine$Updater$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "PYTHON_SDK_APP_VERSION_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "PYTHON_SDK_APP_VERSION_CODE_PATTERN", "notifyRunnable", "Ljava/lang/Runnable;", "isLoading", _UrlKt.FRAGMENT_ENCODE_SET, "status", _UrlKt.FRAGMENT_ENCODE_SET, "getStatus", "()I", "setStatus", "(I)V", "notifyWhenChangeStatus", "getNotifyWhenChangeStatus", "()Z", "setNotifyWhenChangeStatus", "(Z)V", "lastCheckUpdateTime", _UrlKt.FRAGMENT_ENCODE_SET, "TAG", "getVersion", _UrlKt.FRAGMENT_ENCODE_SET, "getStateString", "sdkFromApk", "Ljava/io/InputStream;", "isSdkFromApk", "setBuildFromApk", _UrlKt.FRAGMENT_ENCODE_SET, "fromApk", "hashBytes", _UrlKt.FRAGMENT_ENCODE_SET, "inputStream", "getPythonSdkUpdateFile", "Ljava/io/File;", "getPythonCurrentSdkFile", "requestSdkFromApkFile", "deleteSdkUpdateFile", "checkUpdates", "force", "restoreSdkFromApk", "touchFile", "file", "updateStatus", "newStatus", "parsePythonSdkUpdateResponse", "Lcom/exteragram/messenger/plugins/PythonPluginsEngine$Updater$Companion$PythonSdkUpdateInfo;", "res", "Lorg/telegram/tgnet/TLRPC$messages_Messages;", "isSdkVersionNewer", "remoteVersion", "isBeta", "isAppVersionCompatible", "operator", "targetVersion", "isAppVersionCodeCompatible", "zipFolder", "sourceDir", "zipFile", "copyArchiveToPluginsDirectory", "document", "Lorg/telegram/tgnet/TLRPC$Document;", "autoRestartEngine", "savePythonSdkArchive", "msg", "Lorg/telegram/tgnet/TLRPC$Message;", "PythonSdkUpdateInfo", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final int getStatus() {
                return Updater.status;
            }

            public final void setStatus(int i) {
                Updater.status = i;
            }

            public final boolean getNotifyWhenChangeStatus() {
                return Updater.notifyWhenChangeStatus;
            }

            public final void setNotifyWhenChangeStatus(boolean z) {
                Updater.notifyWhenChangeStatus = z;
            }

            @JvmStatic
            public final CharSequence getVersion() {
                String sdk_version = (ExteraConfig.getPluginsEngine() && PythonPluginsEngine.sdkInitialized) ? PythonPluginsEngine.INSTANCE.getSDK_VERSION() : null;
                boolean sdk_beta = PythonPluginsEngine.INSTANCE.getSDK_BETA();
                if (sdk_version == null && PythonPluginsEngine.SDK_DIR != null) {
                    File file = new File(PythonPluginsEngine.SDK_DIR, Deobfuscator$exteraGramDev$TMessagesProj.getString(-68350486501177L));
                    if (file.exists()) {
                        String string = StringsKt.trim((CharSequence) SimpliFile.readText$default(SimpliFiles.file(file), PythonPluginsEngine.MAX_SDK_VERSION_BYTES, null, 2, null)).toString();
                        sdk_beta = StringsKt.endsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-68376256304953L), false, 2, (Object) null);
                        sdk_version = StringsKt.substringBefore$default(string, '|', (String) null, 2, (Object) null);
                    }
                }
                if (sdk_version == null) {
                    return Deobfuscator$exteraGramDev$TMessagesProj.getString(-68419205977913L);
                }
                StringBuilder sb = new StringBuilder(RegisterSpec.PREFIX);
                sb.append(sdk_version);
                sb.append(Deobfuscator$exteraGramDev$TMessagesProj.getString(sdk_beta ? -68389141206841L : -68414911010617L));
                return sb.toString();
            }

            @JvmStatic
            public final CharSequence getStateString() {
                int status = getStatus();
                if (status == 0) {
                    return getVersion();
                }
                if (status == 1) {
                    return LocaleController.getString(C2797R.string.CheckingForUpdates);
                }
                if (status != 2) {
                    if (status == 3) {
                        return LocaleController.getString(C2797R.string.LoadingUpdate);
                    }
                    if (status != 4) {
                        return null;
                    }
                    return LocaleController.getString(C2797R.string.RestartPluginSystemToApplyUpdate);
                }
                return LocaleController.getString(C2797R.string.LatestVersionInstalled) + Deobfuscator$exteraGramDev$TMessagesProj.getString(-68492220421945L) + ((Object) getVersion()) + ')';
            }

            @Metadata(m876d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010%\u001a\u00020&J\u0006\u0010'\u001a\u00020\u000bR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0013\"\u0004\b\u001e\u0010\u0015R\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0013\"\u0004\b$\u0010\u0015¨\u0006("}, m877d2 = {"Lcom/exteragram/messenger/plugins/PythonPluginsEngine$Updater$Companion$PythonSdkUpdateInfo;", "Lorg/telegram/tgnet/TLRPC$TL_help_appUpdate;", "<init>", "()V", "message", "Lorg/telegram/tgnet/TLRPC$Message;", "getMessage", "()Lorg/telegram/tgnet/TLRPC$Message;", "setMessage", "(Lorg/telegram/tgnet/TLRPC$Message;)V", "available", _UrlKt.FRAGMENT_ENCODE_SET, "getAvailable", "()Z", "setAvailable", "(Z)V", "channel", _UrlKt.FRAGMENT_ENCODE_SET, "getChannel", "()Ljava/lang/String;", "setChannel", "(Ljava/lang/String;)V", "appVersionOperator", "getAppVersionOperator", "setAppVersionOperator", "appVersion", "getAppVersion", "setAppVersion", "appVersionCodeOperator", "getAppVersionCodeOperator", "setAppVersionCodeOperator", "appVersionCode", "getAppVersionCode", "setAppVersionCode", "abi", "getAbi", "setAbi", "clear", _UrlKt.FRAGMENT_ENCODE_SET, "canInstall", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
            public static final class PythonSdkUpdateInfo extends TLRPC.TL_help_appUpdate {
                private String abi;
                private String appVersion;
                private String appVersionCode;
                private String appVersionCodeOperator;
                private String appVersionOperator;
                private boolean available;
                private String channel;
                private TLRPC.Message message;

                public PythonSdkUpdateInfo() {
                    clear();
                }

                public final TLRPC.Message getMessage() {
                    return this.message;
                }

                public final void setMessage(TLRPC.Message message) {
                    this.message = message;
                }

                public final boolean getAvailable() {
                    return this.available;
                }

                public final void setAvailable(boolean z) {
                    this.available = z;
                }

                public final String getChannel() {
                    return this.channel;
                }

                public final void setChannel(String str) {
                    this.channel = str;
                }

                public final String getAppVersionOperator() {
                    return this.appVersionOperator;
                }

                public final void setAppVersionOperator(String str) {
                    this.appVersionOperator = str;
                }

                public final String getAppVersion() {
                    return this.appVersion;
                }

                public final void setAppVersion(String str) {
                    this.appVersion = str;
                }

                public final String getAppVersionCodeOperator() {
                    return this.appVersionCodeOperator;
                }

                public final void setAppVersionCodeOperator(String str) {
                    this.appVersionCodeOperator = str;
                }

                public final String getAppVersionCode() {
                    return this.appVersionCode;
                }

                public final void setAppVersionCode(String str) {
                    this.appVersionCode = str;
                }

                public final String getAbi() {
                    return this.abi;
                }

                public final void setAbi(String str) {
                    this.abi = str;
                }

                public final void clear() {
                    this.message = null;
                    this.available = false;
                    this.can_not_skip = false;
                    this.channel = null;
                    this.version = null;
                    this.appVersion = null;
                    this.appVersionOperator = null;
                    this.appVersionCode = null;
                    this.appVersionCodeOperator = null;
                    this.document = null;
                    this.abi = null;
                }

                public final boolean canInstall() {
                    String str = this.appVersion;
                    String str2 = this.appVersionOperator;
                    String str3 = this.appVersionCode;
                    String str4 = this.appVersionCodeOperator;
                    String str5 = this.version;
                    return (str == null || str2 == null || Updater.INSTANCE.isAppVersionCompatible(str2, str)) && (str3 == null || str4 == null || Updater.INSTANCE.isAppVersionCodeCompatible(str4, str3)) && ((str5 == null || Updater.INSTANCE.isSdkVersionNewer(str5, Intrinsics.areEqual(this.channel, Deobfuscator$exteraGramDev$TMessagesProj.getString(-77872428996409L)))) && this.document != null && Intrinsics.areEqual(this.abi, Build.SUPPORTED_ABIS[0]));
                }
            }

            @JvmStatic
            public final InputStream sdkFromApk() throws IOException {
                InputStream inputStreamOpen = ApplicationLoader.applicationContext.getAssets().open(Deobfuscator$exteraGramDev$TMessagesProj.getString(-68505105323833L) + Build.SUPPORTED_ABIS[0] + Deobfuscator$exteraGramDev$TMessagesProj.getString(-68586709702457L));
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-68608184538937L);
                return inputStreamOpen;
            }

            @JvmStatic
            public final boolean isSdkFromApk() {
                return new File(new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-68651134211897L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-68689788917561L)).exists() || requestSdkFromApkFile().exists();
            }

            @JvmStatic
            public final void setBuildFromApk(boolean fromApk) {
                File file = new File(new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-68771393296185L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-68810048001849L));
                if (file.exists() && !fromApk) {
                    PythonPluginsEngine.INSTANCE.deleteFileIfExists(file);
                }
                if (file.exists() || !fromApk) {
                    return;
                }
                touchFile(file);
            }

            @JvmStatic
            public final String hashBytes(InputStream inputStream) {
                int i;
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-68891652380473L);
                try {
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance(Deobfuscator$exteraGramDev$TMessagesProj.getString(-68943191988025L));
                        byte[] bArr = new byte[1048576];
                        while (true) {
                            int i2 = inputStream.read(bArr);
                            if (i2 <= 0) {
                                break;
                            }
                            messageDigest.update(bArr, 0, i2);
                        }
                        byte[] bArrDigest = messageDigest.digest();
                        StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
                        for (byte b2 : bArrDigest) {
                            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                            String str = String.format(Deobfuscator$exteraGramDev$TMessagesProj.getString(-68968961791801L), Arrays.copyOf(new Object[]{Byte.valueOf(b2)}, 1));
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-68990436628281L);
                            sb.append(str);
                        }
                        String string = sb.toString();
                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-69041976235833L);
                        CloseableKt.closeFinally(inputStream, null);
                        return string;
                    } finally {
                    }
                } catch (IOException e) {
                    HttpUrl$$ExternalSyntheticBUOutline0.m958m(e);
                    return null;
                } catch (NoSuchAlgorithmException e2) {
                    HttpUrl$$ExternalSyntheticBUOutline0.m958m(e2);
                    return null;
                }
            }

            @JvmStatic
            public final File getPythonSdkUpdateFile() {
                return new File(new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-69102105777977L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-69140760483641L));
            }

            @JvmStatic
            public final File getPythonCurrentSdkFile() {
                return new File(new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-69170825254713L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-69209479960377L));
            }

            @JvmStatic
            public final File requestSdkFromApkFile() {
                return new File(new File(ApplicationLoader.getFilesDirFixed(), Deobfuscator$exteraGramDev$TMessagesProj.getString(-69256724600633L)), Deobfuscator$exteraGramDev$TMessagesProj.getString(-69295379306297L));
            }

            @JvmStatic
            public final void deleteSdkUpdateFile() {
                File pythonSdkUpdateFile = getPythonSdkUpdateFile();
                if (pythonSdkUpdateFile.exists()) {
                    PythonPluginsEngine.INSTANCE.deleteFileIfExists(pythonSdkUpdateFile);
                    updateStatus(0);
                }
            }

            @JvmStatic
            public final void checkUpdates() {
                checkUpdates(false);
            }

            @JvmStatic
            public final void checkUpdates(boolean force) {
                if ((getStatus() != 1 || Math.abs(System.currentTimeMillis() - Updater.lastCheckUpdateTime) >= 6000) && getStatus() <= 2) {
                    if (ExteraConfig.getPluginsPySdkAutoUpdate() || force || Math.abs(System.currentTimeMillis() - ExteraConfig.getSdkUpdateScheduleTimestamp()) >= DurationKt.MILLIS_IN_HOUR) {
                        updateStatus(1);
                        Updater.lastCheckUpdateTime = System.currentTimeMillis();
                        RemoteUtils.searchMessages(Deobfuscator$exteraGramDev$TMessagesProj.getString(-69346918913849L), new TLRPC.TL_inputMessagesFilterDocument(), new Utilities.Callback2() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$Updater$Companion$$ExternalSyntheticLambda1
                            @Override // org.telegram.messenger.Utilities.Callback2
                            public final void run(Object obj, Object obj2) {
                                PythonPluginsEngine.Updater.Companion.m2527$r8$lambda$us7HlP4jQnbH6ikxQnBYzBZPGU((TLRPC.messages_Messages) obj, (TLRPC.TL_error) obj2);
                            }
                        }, 3000);
                    }
                }
            }

            /* JADX INFO: renamed from: $r8$lambda$us7HlP4jQnbH-6ikxQnBYzBZPGU */
            public static void m2527$r8$lambda$us7HlP4jQnbH6ikxQnBYzBZPGU(TLRPC.messages_Messages messages_messages, TLRPC.TL_error tL_error) {
                Companion companion;
                final PythonSdkUpdateInfo pythonSdkUpdateResponse;
                boolean z = false;
                if (tL_error != null) {
                    FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-70970416551737L) + tL_error.text);
                } else if (messages_messages != null && (pythonSdkUpdateResponse = (companion = Updater.INSTANCE).parsePythonSdkUpdateResponse(messages_messages)) != null) {
                    if (!ExteraConfig.getPluginsPySdkAutoUpdate() && !pythonSdkUpdateResponse.can_not_skip) {
                        final BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
                        if (safeLastFragment != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$Updater$Companion$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PythonPluginsEngine.Updater.Companion.checkUpdates$lambda$0$0(safeLastFragment, pythonSdkUpdateResponse);
                                }
                            });
                        }
                        z = true;
                    }
                    if (!z) {
                        try {
                            companion.savePythonSdkArchive(pythonSdkUpdateResponse.getMessage(), pythonSdkUpdateResponse.document);
                            z = true;
                        } catch (IOException e) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(Deobfuscator$exteraGramDev$TMessagesProj.getString(-70725603415865L));
                            sb.append(pythonSdkUpdateResponse.getChannel());
                            sb.append(Deobfuscator$exteraGramDev$TMessagesProj.getString(-70901697075001L));
                            TLRPC.Message message = pythonSdkUpdateResponse.getMessage();
                            sb.append(message != null ? Integer.valueOf(message.f1271id) : null);
                            sb.append(')');
                            FileLog.m1047e(sb.toString(), e);
                        }
                    }
                }
                if (z) {
                    return;
                }
                Updater.INSTANCE.updateStatus(2);
            }

            public static final void checkUpdates$lambda$0$0(BaseFragment baseFragment, PythonSdkUpdateInfo pythonSdkUpdateInfo) {
                baseFragment.showDialog(new PythonPluginsEngine$Updater$Companion$checkUpdates$1$1$1(pythonSdkUpdateInfo, baseFragment.getParentActivity(), baseFragment.getCurrentAccount()));
            }

            @JvmStatic
            public final void restoreSdkFromApk() {
                touchFile(requestSdkFromApkFile());
            }

            private final void touchFile(File file) {
                Object objM3494constructorimpl;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    objM3494constructorimpl = Result.m3494constructorimpl(SimpliFiles.file(file).touch());
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
                }
                Throwable thM3497exceptionOrNullimpl = Result.m3497exceptionOrNullimpl(objM3494constructorimpl);
                if (thM3497exceptionOrNullimpl != null) {
                    FileLog.m1048e(thM3497exceptionOrNullimpl);
                }
            }

            public final void updateStatus(int newStatus) {
                setStatus(newStatus);
                if (getNotifyWhenChangeStatus()) {
                    AndroidUtilities.cancelRunOnUIThread(Updater.notifyRunnable);
                    AndroidUtilities.runOnUIThread(Updater.notifyRunnable, newStatus == 1 ? 0L : 600L);
                }
            }

            @JvmStatic
            public final PythonSdkUpdateInfo parsePythonSdkUpdateResponse(TLRPC.messages_Messages res) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-69394163554105L);
                PythonSdkUpdateInfo pythonSdkUpdateInfo = new PythonSdkUpdateInfo();
                Iterator<TLRPC.Message> it = res.messages.iterator();
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-69411343423289L);
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    TLRPC.Message next = it.next();
                    if (next instanceof TLRPC.TL_message) {
                        TLRPC.TL_message tL_message = (TLRPC.TL_message) next;
                        if (!TextUtils.isEmpty(tL_message.message) && (tL_message.media instanceof TLRPC.TL_messageMediaDocument)) {
                            String str = tL_message.message;
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-69471472965433L);
                            boolean zContains$default = StringsKt.contains$default((CharSequence) str, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-69505832703801L), false, 2, (Object) null);
                            String str2 = tL_message.message;
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-69583142115129L);
                            boolean zContains$default2 = StringsKt.contains$default((CharSequence) str2, (CharSequence) Deobfuscator$exteraGramDev$TMessagesProj.getString(-69617501853497L), false, 2, (Object) null);
                            if (zContains$default || zContains$default2) {
                                if (!zContains$default2 || ExteraConfig.getPluginsPySdkBetaVersions()) {
                                    StringBuilder sb = new StringBuilder();
                                    String str3 = tL_message.message;
                                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-69686221330233L);
                                    Iterator it2 = StringsKt.split$default((CharSequence) str3, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-69720581068601L)}, false, 0, 6, (Object) null).iterator();
                                    boolean z = false;
                                    while (it2.hasNext()) {
                                        String string = StringsKt.trim((CharSequence) it2.next()).toString();
                                        if (!TextUtils.isEmpty(string) || !z) {
                                            if (StringsKt.startsWith$default(string, Deobfuscator$exteraGramDev$TMessagesProj.getString(-69729171003193L), false, 2, (Object) null)) {
                                                pythonSdkUpdateInfo.setChannel(Deobfuscator$exteraGramDev$TMessagesProj.getString(zContains$default2 ? -69780710610745L : -69802185447225L));
                                                z = true;
                                            } else if (z) {
                                                Matcher matcher = Updater.PYTHON_SDK_APP_VERSION_PATTERN.matcher(string);
                                                if (!matcher.matches()) {
                                                    Matcher matcher2 = Updater.PYTHON_SDK_APP_VERSION_CODE_PATTERN.matcher(string);
                                                    if (matcher2.matches()) {
                                                        pythonSdkUpdateInfo.setAppVersionCodeOperator(matcher2.group(1));
                                                        String strGroup = matcher2.group(2);
                                                        pythonSdkUpdateInfo.setAppVersionCode(strGroup != null ? StringsKt.trim((CharSequence) strGroup).toString() : null);
                                                    } else {
                                                        List listSplit$default = StringsKt.split$default((CharSequence) string, new String[]{Deobfuscator$exteraGramDev$TMessagesProj.getString(-69840840152889L)}, false, 2, 2, (Object) null);
                                                        if (listSplit$default.size() == 2) {
                                                            String string2 = StringsKt.trim((CharSequence) listSplit$default.get(0)).toString();
                                                            String string3 = StringsKt.trim((CharSequence) listSplit$default.get(1)).toString();
                                                            int iHashCode = string2.hashCode();
                                                            if (iHashCode != -1085916422) {
                                                                if (iHashCode != 96360) {
                                                                    if (iHashCode == 351608024 && string2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-69922444531513L))) {
                                                                        pythonSdkUpdateInfo.version = string3;
                                                                    }
                                                                } else if (string2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-69849430087481L))) {
                                                                    pythonSdkUpdateInfo.setAbi(string3);
                                                                }
                                                            } else if (string2.equals(Deobfuscator$exteraGramDev$TMessagesProj.getString(-69866609956665L))) {
                                                                pythonSdkUpdateInfo.can_not_skip = Boolean.parseBoolean(string3);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    pythonSdkUpdateInfo.setAppVersionOperator(matcher.group(1));
                                                    String strGroup2 = matcher.group(2);
                                                    pythonSdkUpdateInfo.setAppVersion(strGroup2 != null ? StringsKt.trim((CharSequence) strGroup2).toString() : null);
                                                }
                                            } else {
                                                sb.append(string);
                                                sb.append(Deobfuscator$exteraGramDev$TMessagesProj.getString(-69832250218297L));
                                            }
                                        }
                                    }
                                    pythonSdkUpdateInfo.document = tL_message.media.document;
                                    if (!pythonSdkUpdateInfo.canInstall()) {
                                        pythonSdkUpdateInfo.clear();
                                    } else {
                                        pythonSdkUpdateInfo.text = sb.toString();
                                        ArrayList<TLRPC.MessageEntity> arrayList = new ArrayList<>();
                                        Deobfuscator$exteraGramDev$TMessagesProj.getString(-69956804269881L);
                                        for (TLRPC.MessageEntity messageEntity : tL_message.entities) {
                                            if (!(messageEntity instanceof TLRPC.TL_messageEntityPre)) {
                                                arrayList.add(messageEntity);
                                            }
                                        }
                                        pythonSdkUpdateInfo.entities = arrayList;
                                        pythonSdkUpdateInfo.setMessage(next);
                                    }
                                }
                            }
                        }
                    }
                }
                if (pythonSdkUpdateInfo.getMessage() == null) {
                    return null;
                }
                pythonSdkUpdateInfo.setAvailable((pythonSdkUpdateInfo.document == null || TextUtils.isEmpty(pythonSdkUpdateInfo.version)) ? false : true);
                return pythonSdkUpdateInfo;
            }

            @JvmStatic
            public final boolean isSdkVersionNewer(String remoteVersion, boolean isBeta) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70016933812025L);
                if (!ExteraConfig.getPluginsPySdkBetaVersions() && PythonPluginsEngine.INSTANCE.getSDK_BETA()) {
                    return !isBeta;
                }
                Companion companion = PythonPluginsEngine.INSTANCE;
                if (companion.getSDK_VERSION() != null) {
                    return AppUtils.compareVersions(Deobfuscator$exteraGramDev$TMessagesProj.getString(-70077063354169L), remoteVersion, companion.getSDK_VERSION());
                }
                return false;
            }

            @JvmStatic
            public final boolean isAppVersionCompatible(String operator, String targetVersion) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70085653288761L);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70124307994425L);
                return AppUtils.compareVersions(operator, BuildVars.BUILD_VERSION_STRING, targetVersion);
            }

            @JvmStatic
            public final boolean isAppVersionCodeCompatible(String operator, String targetVersion) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70184437536569L);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70223092242233L);
                return AppUtils.compareVersions(operator, BuildVars.BUILD_VERSION, Integer.parseInt(targetVersion));
            }

            @JvmStatic
            public final void zipFolder(File sourceDir, File zipFile) {
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70283221784377L);
                Deobfuscator$exteraGramDev$TMessagesProj.getString(-70326171457337L);
                SimpliFiles.directory(sourceDir).zipTo(zipFile, OverwritePolicy.REPLACE);
            }

            public final void copyArchiveToPluginsDirectory(TLRPC.Document document, boolean autoRestartEngine) {
                File pythonSdkUpdateFile = getPythonSdkUpdateFile();
                try {
                    File pathToAttach = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(document);
                    Deobfuscator$exteraGramDev$TMessagesProj.getString(-70360531195705L);
                    SimpliFiles.file(pathToAttach).copyTo(pythonSdkUpdateFile, OverwritePolicy.REPLACE);
                    if (autoRestartEngine) {
                        PluginsController.INSTANCE.getInstance().restart();
                    } else {
                        updateStatus(4);
                    }
                } catch (IOException e) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-70450725508921L), e);
                    updateStatus(2);
                } catch (Exception e2) {
                    FileLog.m1047e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-70588164462393L), e2);
                    updateStatus(2);
                }
                Updater.isLoading = false;
            }

            @JvmStatic
            public final void savePythonSdkArchive(TLRPC.Message msg, TLRPC.Document document) {
                savePythonSdkArchive(msg, document, false);
            }

            @JvmStatic
            public final void savePythonSdkArchive(TLRPC.Message msg, final TLRPC.Document document, final boolean autoRestartEngine) {
                if (Updater.isLoading || msg == null || document == null) {
                    return;
                }
                MessageObject messageObject = new MessageObject(UserConfig.selectedAccount, msg, false, true);
                Updater.isLoading = true;
                updateStatus(3);
                if (!messageObject.mediaExists) {
                    Updater.TAG = DownloadController.getInstance(UserConfig.selectedAccount).generateObserverTag();
                    FileLoader.getInstance(UserConfig.selectedAccount).loadFile(document, messageObject, 1, 0);
                    DownloadController.getInstance(UserConfig.selectedAccount).addLoadingFileObserver(FileLoader.getAttachFileName(document), messageObject, new DownloadController.FileDownloadProgressListener() { // from class: com.exteragram.messenger.plugins.PythonPluginsEngine$Updater$Companion$savePythonSdkArchive$1
                        @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
                        public void onProgressDownload(String fileName, long downloadSize, long totalSize) {
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-75785074890553L);
                        }

                        @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
                        public void onProgressUpload(String fileName, long downloadSize, long totalSize, boolean isEncrypted) {
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-75823729596217L);
                        }

                        @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
                        public void onFailedDownload(String fileName, boolean canceled) {
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-75570326525753L);
                            FileLog.m1046e(Deobfuscator$exteraGramDev$TMessagesProj.getString(-75608981231417L));
                            PythonPluginsEngine.Updater.isLoading = false;
                            PythonPluginsEngine.Updater.INSTANCE.updateStatus(2);
                        }

                        @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
                        public void onSuccessDownload(String fileName) {
                            Deobfuscator$exteraGramDev$TMessagesProj.getString(-75746420184889L);
                            PythonPluginsEngine.Updater.INSTANCE.copyArchiveToPluginsDirectory(document, autoRestartEngine);
                        }

                        @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
                        public int getObserverTag() {
                            return PythonPluginsEngine.Updater.TAG;
                        }
                    });
                    return;
                }
                copyArchiveToPluginsDirectory(document, autoRestartEngine);
            }
        }

        private Updater() {
        }
    }
}
