package com.google.android.gms.measurement.internal;

import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.android.p003dx.p006io.Opcodes;
import com.chaquo.python.internal.Common;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzhv;
import com.google.android.gms.internal.measurement.zzqp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public final class zzpg implements zzjg {
    private static volatile zzpg zzb;
    private List zzA;
    private long zzB;
    private final Map zzC;
    private final Map zzD;
    private final Map zzE;
    private zzlu zzG;
    private String zzH;
    private zzay zzI;
    private long zzJ;
    long zza;
    private final zzht zzc;
    private final zzgz zzd;
    private zzav zze;
    private zzhb zzf;
    private zzok zzg;
    private zzad zzh;
    private final zzpk zzi;
    private zzlp zzj;
    private zznn zzk;
    private final zzou zzl;
    private zzhk zzm;
    private final zzic zzn;
    private boolean zzp;
    private List zzq;
    private int zzs;
    private int zzt;
    private boolean zzu;
    private boolean zzv;
    private boolean zzw;
    private FileLock zzx;
    private FileChannel zzy;
    private List zzz;
    private final AtomicBoolean zzo = new AtomicBoolean(false);
    private final Deque zzr = new LinkedList();
    private final Map zzF = new HashMap();
    private final zzpo zzK = new zzpb(this);

    zzpg(zzph zzphVar, zzic zzicVar) {
        Preconditions.checkNotNull(zzphVar);
        this.zzn = zzic.zzy(zzphVar.zza, null, null);
        this.zzB = -1L;
        this.zzl = new zzou(this);
        zzpk zzpkVar = new zzpk(this);
        zzpkVar.zzax();
        this.zzi = zzpkVar;
        zzgz zzgzVar = new zzgz(this);
        zzgzVar.zzax();
        this.zzd = zzgzVar;
        zzht zzhtVar = new zzht(this);
        zzhtVar.zzax();
        this.zzc = zzhtVar;
        this.zzC = new HashMap();
        this.zzD = new HashMap();
        this.zzE = new HashMap();
        zzaW().zzj(new zzov(this, zzphVar));
    }

    public static zzpg zza(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (zzpg.class) {
                try {
                    if (zzb == null) {
                        zzb = new zzpg((zzph) Preconditions.checkNotNull(new zzph(context)), null);
                    }
                } finally {
                }
            }
        }
        return zzb;
    }

    static final void zzaA(com.google.android.gms.internal.measurement.zzhr zzhrVar, int i, String str) {
        List listZza = zzhrVar.zza();
        for (int i2 = 0; i2 < listZza.size(); i2++) {
            if ("_err".equals(((com.google.android.gms.internal.measurement.zzhw) listZza.get(i2)).zzb())) {
                return;
            }
        }
        zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
        zzhvVarZzn.zzb("_err");
        zzhvVarZzn.zzf(i);
        com.google.android.gms.internal.measurement.zzhw zzhwVar = (com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn.zzbc();
        zzhv zzhvVarZzn2 = com.google.android.gms.internal.measurement.zzhw.zzn();
        zzhvVarZzn2.zzb("_ev");
        zzhvVarZzn2.zzd(str);
        com.google.android.gms.internal.measurement.zzhw zzhwVar2 = (com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn2.zzbc();
        zzhrVar.zzf(zzhwVar);
        zzhrVar.zzf(zzhwVar2);
    }

    static final void zzaB(com.google.android.gms.internal.measurement.zzhr zzhrVar, String str) {
        List listZza = zzhrVar.zza();
        for (int i = 0; i < listZza.size(); i++) {
            if (str.equals(((com.google.android.gms.internal.measurement.zzhw) listZza.get(i)).zzb())) {
                zzhrVar.zzj(i);
                return;
            }
        }
    }

    private final int zzaC(String str, zzan zzanVar) {
        zzjk zzjkVar;
        zzji zzjiVarZzA;
        zzht zzhtVar = this.zzc;
        if (zzhtVar.zzx(str) == null) {
            zzanVar.zzc(zzjk.AD_PERSONALIZATION, zzam.FAILSAFE);
            return 1;
        }
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu != null && zze.zzc(zzhVarZzu.zzaH()).zza() == zzji.POLICY && (zzjiVarZzA = zzhtVar.zzA(str, (zzjkVar = zzjk.AD_PERSONALIZATION))) != zzji.UNINITIALIZED) {
            zzanVar.zzc(zzjkVar, zzam.REMOTE_ENFORCED_DEFAULT);
            return zzjiVarZzA == zzji.GRANTED ? 0 : 1;
        }
        zzjk zzjkVar2 = zzjk.AD_PERSONALIZATION;
        zzanVar.zzc(zzjkVar2, zzam.REMOTE_DEFAULT);
        return zzhtVar.zzv(str, zzjkVar2) ? 0 : 1;
    }

    private final Map zzaD(com.google.android.gms.internal.measurement.zzhs zzhsVar) {
        HashMap map = new HashMap();
        zzp();
        for (Map.Entry entry : zzpk.zzH(zzhsVar, "gad_").entrySet()) {
            map.put((String) entry.getKey(), String.valueOf(entry.getValue()));
        }
        return map;
    }

    private final zzay zzaE() {
        if (this.zzI == null) {
            this.zzI = new zzoy(this, this.zzn);
        }
        return this.zzI;
    }

    /* JADX INFO: renamed from: zzaF */
    public final void zzav() {
        zzaW().zzg();
        if (this.zzr.isEmpty() || zzaE().zzc()) {
            return;
        }
        long jMax = Math.max(0L, ((long) ((Integer) zzfy.zzaB.zzb(null)).intValue()) - (zzaZ().elapsedRealtime() - this.zzJ));
        zzaV().zzk().zzb("Scheduling notify next app runnable, delay in ms", Long.valueOf(jMax));
        zzaE().zzb(jMax);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(40:551|(5:552|553|(4:555|556|(4:558|(1:565)|568|1030)(18:569|570|(2:578|(3:580|(1:587)(1:586)|588)(0))(1:577)|589|(2:591|(3:593|(4:596|(1:1035)(2:602|1032)|603|594)|1031))|604|605|(5:607|608|(3:611|(0)|(7:660|(5:664|(2:666|1046)(2:667|(2:669|1045)(1:1047))|670|661|662)|1044|671|(2:674|(3:680|(1:682)(2:683|(3:685|(3:688|(1:690)(1:1062)|686)|1063))|691)(2:678|679))(1:673)|692|(2:694|(6:(2:699|(6:701|702|736|(9:738|(4:741|(2:754|(2:756|1049)(1:1051))(5:745|(5:748|(2:751|749)|1053|752|746)|1052|753|1050)|757|739)|1048|758|(4:761|(3:1055|763|1058)(1:1057)|1056|759)|1054|764|(1:766)|1059)(1:767)|768|1029))|703|736|(0)(0)|768|1029)(7:704|705|735|736|(0)(0)|768|1029))(2:706|(8:708|(6:(2:713|(6:715|702|736|(0)(0)|768|1029))|716|736|(0)(0)|768|1029)|705|735|736|(0)(0)|768|1029)(7:717|(2:728|(2:729|(2:731|(2:1061|733)(1:734))(1:1060)))(0)|735|736|(0)(0)|768|1029)))(3:679|692|(0)(0)))|614|(0)(0))(1:615)|613|(3:616|617|(3:619|(2:621|1038)(2:622|(2:624|1037)(1:1039))|625)(1:1036))|626|(1:630)(1:629)|(1:632)|633|(1:635)(1:636)|637|(2:640|(4:642|(4:645|(2:647|1042)(2:648|(2:650|1041)(1:1043))|651|643)|1040|(1:(1:657)(1:658))(1:(1:654)(2:655|(0)(0)))))|(0)(0))|769)(1:1028)|1017|1018)|770|(3:772|(2:774|(2:776|1066)(2:777|(1:1067)(3:779|(1:781)(1:782)|(1:1069)(2:786|1065))))(0)|787)|1064|788|(3:789|790|(1:1070)(2:792|(2:1071|794)(1:1072)))|795|(1:797)(2:798|(1:800))|801|(1:803)(1:804)|805|(1:807)(1:808)|809|(6:812|(1:814)|815|(2:817|1074)(1:1075)|818|810)|1073|819|(2:824|(1:828))(1:823)|829|(1:831)|832|(1:834)|835|(1:843)|844|(10:1026|846|(7:849|850|(5:852|(1:856)|(1:873)(5:860|(1:864)|866|(1:871)(1:870)|872)|874|875)(7:877|878|(6:1024|880|881|886|(6:888|(3:891|(3:1084|893|(3:895|901|(1:903)(7:904|(6:906|(1:908)|911|(1:913)(1:915)|914|(4:917|(1:925)|926|1082)(4:927|(3:929|(1:931)|932)(5:933|(1:935)(1:936)|937|(3:939|(1:941)|942)(2:944|(1:946))|943)|947|1080))(1:910)|909|911|(0)(0)|914|(0)(0)))(2:896|(0)(0)))(1:899)|889)|1083|900|901|(0)(0))(3:900|901|(0)(0))|948)(1:885)|884|886|(0)(0)|948)|876|1081|948|847)|1079|949|(1:951)|952|(2:955|953)|1085|956)(1:957)|958|(1:960)(13:962|(9:964|(1:966)(1:967)|968|(1:970)(1:971)|972|(1:974)(1:975)|976|(1:978)(1:979)|980)|981|(4:983|984|(2:992|(1:994)(1:995))(1:989)|996)(1:997)|998|(3:(2:1002|1077)(1:1078)|1003|999)|1076|1004|(1:1006)|1007|1020|1008|1012)|961|981|(0)(0)|998|(1:999)|1076|1004|(0)|1007|1020|1008|1012) */
    /* JADX WARN: Code restructure failed: missing block: B:1010:0x0dd9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1011:0x0dda, code lost:
    
        r3.zzu.zzaV().zzb().zzc("Failed to remove unused event metadata. appId", com.google.android.gms.measurement.internal.zzgu.zzl(r1), r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1001:0x0d7b  */
    /* JADX WARN: Removed duplicated region for block: B:1006:0x0dac A[Catch: all -> 0x0d20, TryCatch #0 {all -> 0x0d20, blocks: (B:984:0x0cfc, B:986:0x0d11, B:989:0x0d18, B:996:0x0d4c, B:998:0x0d5e, B:999:0x0d75, B:1002:0x0d7d, B:1003:0x0d82, B:1004:0x0d92, B:1006:0x0dac, B:1007:0x0dc7, B:1008:0x0dcf, B:1012:0x0ded, B:1011:0x0dda, B:992:0x0d23, B:994:0x0d2f, B:995:0x0d35, B:1013:0x0df6), top: B:1019:0x002c, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:657:0x03bd A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:658:0x03d5 A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:660:0x03ee A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:679:0x045f  */
    /* JADX WARN: Removed duplicated region for block: B:694:0x04b1 A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:706:0x04fc A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:738:0x05b1 A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:767:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:777:0x0708 A[Catch: all -> 0x00f6, TryCatch #2 {all -> 0x00f6, blocks: (B:546:0x0017, B:548:0x002e, B:551:0x0038, B:552:0x004e, B:555:0x0060, B:558:0x008a, B:560:0x00bf, B:563:0x00d0, B:565:0x00da, B:769:0x06cf, B:569:0x0107, B:572:0x0119, B:574:0x011f, B:589:0x0163, B:591:0x0171, B:594:0x0191, B:596:0x0197, B:598:0x01a7, B:600:0x01b5, B:602:0x01c5, B:603:0x01d2, B:604:0x01d5, B:607:0x01eb, B:616:0x021e, B:619:0x0228, B:621:0x0236, B:625:0x027d, B:622:0x0253, B:624:0x0263, B:629:0x028a, B:632:0x02bf, B:633:0x02e9, B:635:0x0322, B:637:0x0328, B:640:0x0334, B:642:0x036b, B:643:0x0386, B:645:0x038c, B:647:0x039a, B:651:0x03af, B:648:0x03a3, B:654:0x03b6, B:657:0x03bd, B:658:0x03d5, B:660:0x03ee, B:661:0x03fa, B:664:0x0404, B:670:0x0427, B:667:0x0416, B:692:0x04a5, B:694:0x04b1, B:697:0x04c2, B:699:0x04d3, B:701:0x04df, B:736:0x05ab, B:738:0x05b1, B:739:0x05bd, B:741:0x05c3, B:743:0x05d3, B:745:0x05dd, B:746:0x05ee, B:748:0x05f4, B:749:0x060f, B:751:0x0615, B:752:0x0633, B:753:0x0640, B:757:0x0665, B:754:0x0646, B:756:0x0652, B:758:0x066c, B:759:0x0682, B:761:0x0688, B:763:0x069b, B:764:0x06a8, B:766:0x06af, B:768:0x06bd, B:706:0x04fc, B:708:0x050a, B:711:0x051d, B:713:0x052f, B:715:0x053b, B:717:0x054b, B:719:0x055a, B:722:0x0566, B:724:0x0570, B:726:0x057a, B:729:0x0585, B:731:0x058b, B:733:0x059b, B:734:0x05a6, B:674:0x042f, B:676:0x043b, B:678:0x0447, B:691:0x048d, B:683:0x0465, B:686:0x0477, B:688:0x047d, B:690:0x0487, B:578:0x012b, B:580:0x0138, B:582:0x0144, B:584:0x014a, B:588:0x0155, B:772:0x06e9, B:774:0x06f7, B:776:0x0700, B:787:0x0730, B:777:0x0708, B:779:0x0711, B:781:0x0717, B:784:0x0723, B:786:0x072b, B:788:0x0733, B:789:0x073f, B:792:0x0747, B:794:0x0759, B:795:0x0764, B:797:0x076c, B:801:0x0792, B:803:0x07ac, B:805:0x07c1, B:807:0x07db, B:809:0x07f0, B:810:0x07fe, B:812:0x0804, B:814:0x0814, B:815:0x081b, B:817:0x0827, B:818:0x082e, B:819:0x0831, B:821:0x0873, B:823:0x0879, B:829:0x08a0, B:831:0x08a8, B:832:0x08b1, B:834:0x08b7, B:835:0x08bd, B:837:0x08d2, B:839:0x08e2, B:841:0x08f2, B:843:0x08fa, B:844:0x08fd, B:852:0x0973, B:854:0x098c, B:856:0x09a2, B:858:0x09a7, B:860:0x09ab, B:862:0x09af, B:864:0x09b9, B:866:0x09c2, B:868:0x09c6, B:870:0x09cc, B:872:0x09d7, B:874:0x09e5, B:880:0x0a0d, B:883:0x0a15, B:824:0x0887, B:826:0x088d, B:828:0x0893, B:808:0x07ed, B:804:0x07be, B:798:0x0772, B:800:0x0778), top: B:1022:0x0017, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:888:0x0a4a A[Catch: all -> 0x0a7b, TryCatch #4 {all -> 0x0a7b, blocks: (B:846:0x093f, B:847:0x0952, B:849:0x0958, B:948:0x0c1b, B:878:0x09f7, B:886:0x0a30, B:888:0x0a4a, B:889:0x0a52, B:891:0x0a58, B:893:0x0a6a, B:901:0x0a85, B:903:0x0a99, B:904:0x0abc, B:906:0x0ac8, B:908:0x0ade, B:911:0x0b23, B:917:0x0b3f, B:919:0x0b4a, B:921:0x0b4e, B:923:0x0b52, B:925:0x0b56, B:926:0x0b62, B:927:0x0b6e, B:929:0x0b74, B:931:0x0b8a, B:932:0x0b8f, B:947:0x0c18, B:933:0x0ba7, B:935:0x0bab, B:939:0x0bce, B:941:0x0bee, B:942:0x0bf5, B:946:0x0c0b, B:936:0x0bb6, B:949:0x0c29, B:951:0x0c38, B:952:0x0c3e, B:953:0x0c46, B:955:0x0c4c, B:958:0x0c66, B:960:0x0c76, B:981:0x0cf4, B:962:0x0c8f, B:964:0x0c95, B:966:0x0c9d, B:968:0x0ca4, B:974:0x0cb2, B:976:0x0cb9, B:978:0x0ce5, B:980:0x0cec, B:979:0x0ce9, B:975:0x0cb6, B:967:0x0ca1), top: B:1026:0x093f }] */
    /* JADX WARN: Removed duplicated region for block: B:900:0x0a83 A[PHI: r10
  0x0a83: PHI (r10v7 java.lang.String) = (r10v6 java.lang.String), (r10v22 java.lang.String) binds: [B:887:0x0a48, B:1083:0x0a83] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:903:0x0a99 A[Catch: all -> 0x0a7b, TryCatch #4 {all -> 0x0a7b, blocks: (B:846:0x093f, B:847:0x0952, B:849:0x0958, B:948:0x0c1b, B:878:0x09f7, B:886:0x0a30, B:888:0x0a4a, B:889:0x0a52, B:891:0x0a58, B:893:0x0a6a, B:901:0x0a85, B:903:0x0a99, B:904:0x0abc, B:906:0x0ac8, B:908:0x0ade, B:911:0x0b23, B:917:0x0b3f, B:919:0x0b4a, B:921:0x0b4e, B:923:0x0b52, B:925:0x0b56, B:926:0x0b62, B:927:0x0b6e, B:929:0x0b74, B:931:0x0b8a, B:932:0x0b8f, B:947:0x0c18, B:933:0x0ba7, B:935:0x0bab, B:939:0x0bce, B:941:0x0bee, B:942:0x0bf5, B:946:0x0c0b, B:936:0x0bb6, B:949:0x0c29, B:951:0x0c38, B:952:0x0c3e, B:953:0x0c46, B:955:0x0c4c, B:958:0x0c66, B:960:0x0c76, B:981:0x0cf4, B:962:0x0c8f, B:964:0x0c95, B:966:0x0c9d, B:968:0x0ca4, B:974:0x0cb2, B:976:0x0cb9, B:978:0x0ce5, B:980:0x0cec, B:979:0x0ce9, B:975:0x0cb6, B:967:0x0ca1), top: B:1026:0x093f }] */
    /* JADX WARN: Removed duplicated region for block: B:904:0x0abc A[Catch: all -> 0x0a7b, TryCatch #4 {all -> 0x0a7b, blocks: (B:846:0x093f, B:847:0x0952, B:849:0x0958, B:948:0x0c1b, B:878:0x09f7, B:886:0x0a30, B:888:0x0a4a, B:889:0x0a52, B:891:0x0a58, B:893:0x0a6a, B:901:0x0a85, B:903:0x0a99, B:904:0x0abc, B:906:0x0ac8, B:908:0x0ade, B:911:0x0b23, B:917:0x0b3f, B:919:0x0b4a, B:921:0x0b4e, B:923:0x0b52, B:925:0x0b56, B:926:0x0b62, B:927:0x0b6e, B:929:0x0b74, B:931:0x0b8a, B:932:0x0b8f, B:947:0x0c18, B:933:0x0ba7, B:935:0x0bab, B:939:0x0bce, B:941:0x0bee, B:942:0x0bf5, B:946:0x0c0b, B:936:0x0bb6, B:949:0x0c29, B:951:0x0c38, B:952:0x0c3e, B:953:0x0c46, B:955:0x0c4c, B:958:0x0c66, B:960:0x0c76, B:981:0x0cf4, B:962:0x0c8f, B:964:0x0c95, B:966:0x0c9d, B:968:0x0ca4, B:974:0x0cb2, B:976:0x0cb9, B:978:0x0ce5, B:980:0x0cec, B:979:0x0ce9, B:975:0x0cb6, B:967:0x0ca1), top: B:1026:0x093f }] */
    /* JADX WARN: Removed duplicated region for block: B:913:0x0b36  */
    /* JADX WARN: Removed duplicated region for block: B:915:0x0b3a  */
    /* JADX WARN: Removed duplicated region for block: B:917:0x0b3f A[Catch: all -> 0x0a7b, TryCatch #4 {all -> 0x0a7b, blocks: (B:846:0x093f, B:847:0x0952, B:849:0x0958, B:948:0x0c1b, B:878:0x09f7, B:886:0x0a30, B:888:0x0a4a, B:889:0x0a52, B:891:0x0a58, B:893:0x0a6a, B:901:0x0a85, B:903:0x0a99, B:904:0x0abc, B:906:0x0ac8, B:908:0x0ade, B:911:0x0b23, B:917:0x0b3f, B:919:0x0b4a, B:921:0x0b4e, B:923:0x0b52, B:925:0x0b56, B:926:0x0b62, B:927:0x0b6e, B:929:0x0b74, B:931:0x0b8a, B:932:0x0b8f, B:947:0x0c18, B:933:0x0ba7, B:935:0x0bab, B:939:0x0bce, B:941:0x0bee, B:942:0x0bf5, B:946:0x0c0b, B:936:0x0bb6, B:949:0x0c29, B:951:0x0c38, B:952:0x0c3e, B:953:0x0c46, B:955:0x0c4c, B:958:0x0c66, B:960:0x0c76, B:981:0x0cf4, B:962:0x0c8f, B:964:0x0c95, B:966:0x0c9d, B:968:0x0ca4, B:974:0x0cb2, B:976:0x0cb9, B:978:0x0ce5, B:980:0x0cec, B:979:0x0ce9, B:975:0x0cb6, B:967:0x0ca1), top: B:1026:0x093f }] */
    /* JADX WARN: Removed duplicated region for block: B:927:0x0b6e A[Catch: all -> 0x0a7b, TryCatch #4 {all -> 0x0a7b, blocks: (B:846:0x093f, B:847:0x0952, B:849:0x0958, B:948:0x0c1b, B:878:0x09f7, B:886:0x0a30, B:888:0x0a4a, B:889:0x0a52, B:891:0x0a58, B:893:0x0a6a, B:901:0x0a85, B:903:0x0a99, B:904:0x0abc, B:906:0x0ac8, B:908:0x0ade, B:911:0x0b23, B:917:0x0b3f, B:919:0x0b4a, B:921:0x0b4e, B:923:0x0b52, B:925:0x0b56, B:926:0x0b62, B:927:0x0b6e, B:929:0x0b74, B:931:0x0b8a, B:932:0x0b8f, B:947:0x0c18, B:933:0x0ba7, B:935:0x0bab, B:939:0x0bce, B:941:0x0bee, B:942:0x0bf5, B:946:0x0c0b, B:936:0x0bb6, B:949:0x0c29, B:951:0x0c38, B:952:0x0c3e, B:953:0x0c46, B:955:0x0c4c, B:958:0x0c66, B:960:0x0c76, B:981:0x0cf4, B:962:0x0c8f, B:964:0x0c95, B:966:0x0c9d, B:968:0x0ca4, B:974:0x0cb2, B:976:0x0cb9, B:978:0x0ce5, B:980:0x0cec, B:979:0x0ce9, B:975:0x0cb6, B:967:0x0ca1), top: B:1026:0x093f }] */
    /* JADX WARN: Removed duplicated region for block: B:983:0x0cfa  */
    /* JADX WARN: Removed duplicated region for block: B:997:0x0d5c  */
    /* JADX WARN: Type inference failed for: r11v14, types: [com.google.android.gms.internal.measurement.zzhr, com.google.android.gms.internal.measurement.zzmb] */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1, types: [com.google.android.gms.internal.measurement.zzhr, com.google.android.gms.internal.measurement.zzmb] */
    /* JADX WARN: Type inference failed for: r17v16 */
    /* JADX WARN: Type inference failed for: r17v17 */
    /* JADX WARN: Type inference failed for: r17v18 */
    /* JADX WARN: Type inference failed for: r17v19 */
    /* JADX WARN: Type inference failed for: r17v20 */
    /* JADX WARN: Type inference failed for: r17v21 */
    /* JADX WARN: Type inference failed for: r17v22 */
    /* JADX WARN: Type inference failed for: r18v0 */
    /* JADX WARN: Type inference failed for: r18v1, types: [com.google.android.gms.internal.measurement.zzhr, com.google.android.gms.internal.measurement.zzmb] */
    /* JADX WARN: Type inference failed for: r18v10 */
    /* JADX WARN: Type inference failed for: r18v11 */
    /* JADX WARN: Type inference failed for: r18v12 */
    /* JADX WARN: Type inference failed for: r18v6 */
    /* JADX WARN: Type inference failed for: r18v7 */
    /* JADX WARN: Type inference failed for: r18v8 */
    /* JADX WARN: Type inference failed for: r18v9 */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r1v39 */
    /* JADX WARN: Type inference failed for: r1v43 */
    /* JADX WARN: Type inference failed for: r21v10 */
    /* JADX WARN: Type inference failed for: r21v11 */
    /* JADX WARN: Type inference failed for: r21v12 */
    /* JADX WARN: Type inference failed for: r21v13 */
    /* JADX WARN: Type inference failed for: r21v14 */
    /* JADX WARN: Type inference failed for: r21v15 */
    /* JADX WARN: Type inference failed for: r21v16 */
    /* JADX WARN: Type inference failed for: r21v17 */
    /* JADX WARN: Type inference failed for: r21v18 */
    /* JADX WARN: Type inference failed for: r21v19 */
    /* JADX WARN: Type inference failed for: r21v20 */
    /* JADX WARN: Type inference failed for: r21v21 */
    /* JADX WARN: Type inference failed for: r21v22 */
    /* JADX WARN: Type inference failed for: r21v23 */
    /* JADX WARN: Type inference failed for: r28v10 */
    /* JADX WARN: Type inference failed for: r28v14 */
    /* JADX WARN: Type inference failed for: r28v15 */
    /* JADX WARN: Type inference failed for: r28v16 */
    /* JADX WARN: Type inference failed for: r28v17 */
    /* JADX WARN: Type inference failed for: r28v18 */
    /* JADX WARN: Type inference failed for: r28v19 */
    /* JADX WARN: Type inference failed for: r28v2 */
    /* JADX WARN: Type inference failed for: r28v20 */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v115 */
    /* JADX WARN: Type inference failed for: r2v116 */
    /* JADX WARN: Type inference failed for: r2v117 */
    /* JADX WARN: Type inference failed for: r2v118 */
    /* JADX WARN: Type inference failed for: r2v119 */
    /* JADX WARN: Type inference failed for: r2v120 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v32 */
    /* JADX WARN: Type inference failed for: r2v33, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v49 */
    /* JADX WARN: Type inference failed for: r3v50 */
    /* JADX WARN: Type inference failed for: r3v59 */
    /* JADX WARN: Type inference failed for: r9v10, types: [com.google.android.gms.internal.measurement.zzic] */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v17, types: [com.google.android.gms.internal.measurement.zzic] */
    /* JADX WARN: Type inference failed for: r9v39 */
    /* JADX WARN: Type inference failed for: r9v40 */
    /* JADX WARN: Type inference failed for: r9v41 */
    /* JADX WARN: Type inference failed for: r9v42 */
    /* JADX WARN: Type inference failed for: r9v43 */
    /* JADX WARN: Type inference failed for: r9v44 */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference failed for: r9v46 */
    /* JADX WARN: Type inference failed for: r9v47 */
    /* JADX WARN: Type inference failed for: r9v6, types: [com.google.android.gms.internal.measurement.zzic, com.google.android.gms.internal.measurement.zzmb] */
    /* JADX WARN: Type inference failed for: r9v7, types: [com.google.android.gms.internal.measurement.zzic] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zzaG(java.lang.String r44, long r45) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 3598
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzaG(java.lang.String, long):boolean");
    }

    private final void zzaH(com.google.android.gms.internal.measurement.zzic zzicVar, long j, boolean z) {
        Object obj;
        String str = true != z ? "_lte" : "_se";
        zzpn zzpnVarZzm = zzj().zzm(zzicVar.zzK(), str);
        zzpn zzpnVar = (zzpnVarZzm == null || (obj = zzpnVarZzm.zze) == null) ? new zzpn(zzicVar.zzK(), "auto", str, zzaZ().currentTimeMillis(), Long.valueOf(j)) : new zzpn(zzicVar.zzK(), "auto", str, zzaZ().currentTimeMillis(), Long.valueOf(((Long) obj).longValue() + j));
        com.google.android.gms.internal.measurement.zzit zzitVarZzm = com.google.android.gms.internal.measurement.zziu.zzm();
        zzitVarZzm.zzb(str);
        zzitVarZzm.zza(zzaZ().currentTimeMillis());
        Object obj2 = zzpnVar.zze;
        zzitVarZzm.zze(((Long) obj2).longValue());
        com.google.android.gms.internal.measurement.zziu zziuVar = (com.google.android.gms.internal.measurement.zziu) zzitVarZzm.zzbc();
        int iZzx = zzpk.zzx(zzicVar, str);
        if (iZzx >= 0) {
            zzicVar.zzn(iZzx, zziuVar);
        } else {
            zzicVar.zzo(zziuVar);
        }
        if (j > 0) {
            zzj().zzl(zzpnVar);
            zzaV().zzk().zzc("Updated engagement user property. scope, value", true != z ? "lifetime" : "session-scoped", obj2);
        }
    }

    private final boolean zzaI(com.google.android.gms.internal.measurement.zzhr zzhrVar, com.google.android.gms.internal.measurement.zzhr zzhrVar2) {
        Preconditions.checkArgument("_e".equals(zzhrVar.zzk()));
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF = zzpk.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar.zzbc(), "_sc");
        String strZzd = zzhwVarZzF == null ? null : zzhwVarZzF.zzd();
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF2 = zzpk.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar2.zzbc(), "_pc");
        String strZzd2 = zzhwVarZzF2 != null ? zzhwVarZzF2.zzd() : null;
        if (strZzd2 == null || !strZzd2.equals(strZzd)) {
            return false;
        }
        Preconditions.checkArgument("_e".equals(zzhrVar.zzk()));
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF3 = zzpk.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar.zzbc(), "_et");
        if (zzhwVarZzF3 == null || !zzhwVarZzF3.zze() || zzhwVarZzF3.zzf() <= 0) {
            return true;
        }
        long jZzf = zzhwVarZzF3.zzf();
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF4 = zzpk.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar2.zzbc(), "_et");
        if (zzhwVarZzF4 != null && zzhwVarZzF4.zzf() > 0) {
            jZzf += zzhwVarZzF4.zzf();
        }
        zzp();
        zzpk.zzC(zzhrVar2, "_et", Long.valueOf(jZzf));
        zzp();
        zzpk.zzC(zzhrVar, "_fr", 1L);
        return true;
    }

    private final boolean zzaJ() {
        zzaW().zzg();
        zzu();
        return zzj().zzP() || !TextUtils.isEmpty(zzj().zzF());
    }

    private static String zzaK(Map map, String str) {
        if (map == null) {
            return null;
        }
        for (Map.Entry entry : map.entrySet()) {
            if (str.equalsIgnoreCase((String) entry.getKey())) {
                if (((List) entry.getValue()).isEmpty()) {
                    return null;
                }
                return (String) ((List) entry.getValue()).get(0);
            }
        }
        return null;
    }

    private final void zzaL() {
        long jMax;
        long jMax2;
        zzaW().zzg();
        zzu();
        if (this.zza > 0) {
            long jAbs = 3600000 - Math.abs(zzaZ().elapsedRealtime() - this.zza);
            if (jAbs > 0) {
                zzaV().zzk().zzb("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(jAbs));
                zzk().zzb();
                zzl().zzd();
                return;
            }
            this.zza = 0L;
        }
        if (!this.zzn.zzH() || !zzaJ()) {
            zzaV().zzk().zza("Nothing to upload or uploading impossible");
            zzk().zzb();
            zzl().zzd();
            return;
        }
        long jCurrentTimeMillis = zzaZ().currentTimeMillis();
        zzd();
        long jMax3 = Math.max(0L, ((Long) zzfy.zzO.zzb(null)).longValue());
        boolean z = true;
        if (!zzj().zzR() && !zzj().zzG()) {
            z = false;
        }
        if (z) {
            String strZzA = zzd().zzA();
            if (TextUtils.isEmpty(strZzA) || ".none.".equals(strZzA)) {
                zzd();
                jMax = Math.max(0L, ((Long) zzfy.zzI.zzb(null)).longValue());
            } else {
                zzd();
                jMax = Math.max(0L, ((Long) zzfy.zzJ.zzb(null)).longValue());
            }
        } else {
            zzd();
            jMax = Math.max(0L, ((Long) zzfy.zzH.zzb(null)).longValue());
        }
        long jZza = this.zzk.zzd.zza();
        long jZza2 = this.zzk.zze.zza();
        long j = 0;
        boolean z2 = z;
        long jMax4 = Math.max(zzj().zzM(), zzj().zzO());
        if (jMax4 == 0) {
            jMax2 = 0;
        } else {
            long jAbs2 = jCurrentTimeMillis - Math.abs(jMax4 - jCurrentTimeMillis);
            long jAbs3 = jCurrentTimeMillis - Math.abs(jZza - jCurrentTimeMillis);
            long jAbs4 = jCurrentTimeMillis - Math.abs(jZza2 - jCurrentTimeMillis);
            jMax2 = jMax3 + jAbs2;
            long jMax5 = Math.max(jAbs3, jAbs4);
            if (z2 && jMax5 > 0) {
                jMax2 = Math.min(jAbs2, jMax5) + jMax;
            }
            if (!zzp().zzs(jMax5, jMax)) {
                jMax2 = jMax5 + jMax;
            }
            if (jAbs4 != 0 && jAbs4 >= jAbs2) {
                int i = 0;
                while (true) {
                    zzd();
                    if (i >= Math.min(20, Math.max(0, ((Integer) zzfy.zzQ.zzb(null)).intValue()))) {
                        jMax2 = 0;
                        break;
                    }
                    zzd();
                    jMax2 += Math.max(j, ((Long) zzfy.zzP.zzb(null)).longValue()) * (1 << i);
                    if (jMax2 > jAbs4) {
                        break;
                    }
                    i++;
                    j = 0;
                }
            }
            j = 0;
        }
        if (jMax2 == j) {
            zzaV().zzk().zza("Next upload time is 0");
            zzk().zzb();
            zzl().zzd();
            return;
        }
        if (!zzi().zzb()) {
            zzaV().zzk().zza("No network");
            zzk().zza();
            zzl().zzd();
            return;
        }
        long jZza3 = this.zzk.zzc.zza();
        zzd();
        long jMax6 = Math.max(0L, ((Long) zzfy.zzF.zzb(null)).longValue());
        if (!zzp().zzs(jZza3, jMax6)) {
            jMax2 = Math.max(jMax2, jZza3 + jMax6);
        }
        zzk().zzb();
        long jCurrentTimeMillis2 = jMax2 - zzaZ().currentTimeMillis();
        if (jCurrentTimeMillis2 <= 0) {
            zzd();
            jCurrentTimeMillis2 = Math.max(0L, ((Long) zzfy.zzK.zzb(null)).longValue());
            this.zzk.zzd.zzb(zzaZ().currentTimeMillis());
        }
        zzaV().zzk().zzb("Upload scheduled in approximately ms", Long.valueOf(jCurrentTimeMillis2));
        zzl().zzc(jCurrentTimeMillis2);
    }

    private final void zzaM() {
        zzaW().zzg();
        if (this.zzu || this.zzv || this.zzw) {
            zzaV().zzk().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzu), Boolean.valueOf(this.zzv), Boolean.valueOf(this.zzw));
            return;
        }
        zzaV().zzk().zza("Stopping uploading service(s)");
        List list = this.zzq;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        ((List) Preconditions.checkNotNull(this.zzq)).clear();
    }

    private final Boolean zzaN(zzh zzhVar) {
        try {
            if (zzhVar.zzt() != -2147483648L) {
                if (zzhVar.zzt() == Wrappers.packageManager(this.zzn.zzaY()).getPackageInfo(zzhVar.zzc(), 0).versionCode) {
                    return Boolean.TRUE;
                }
            } else {
                String str = Wrappers.packageManager(this.zzn.zzaY()).getPackageInfo(zzhVar.zzc(), 0).versionName;
                String strZzr = zzhVar.zzr();
                if (strZzr != null && strZzr.equals(str)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    private final zzr zzaO(String str) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu == null || TextUtils.isEmpty(zzhVarZzu.zzr())) {
            zzaV().zzj().zzb("No app data available; dropping", str);
            return null;
        }
        Boolean boolZzaN = zzaN(zzhVarZzu);
        if (boolZzaN == null || boolZzaN.booleanValue()) {
            return new zzr(str, zzhVarZzu.zzf(), zzhVarZzu.zzr(), zzhVarZzu.zzt(), zzhVarZzu.zzv(), zzhVarZzu.zzx(), zzhVarZzu.zzz(), (String) null, zzhVarZzu.zzD(), false, zzhVarZzu.zzl(), 0L, 0, zzhVarZzu.zzac(), false, zzhVarZzu.zzae(), zzhVarZzu.zzB(), zzhVarZzu.zzag(), zzB(str).zzl(), _UrlKt.FRAGMENT_ENCODE_SET, (String) null, zzhVarZzu.zzai(), zzhVarZzu.zzak(), zzB(str).zzb(), zzx(str).zze(), zzhVarZzu.zzao(), zzhVarZzu.zzaw(), zzhVarZzu.zzay(), zzhVarZzu.zzaH(), 0L, zzhVarZzu.zzaL());
        }
        zzaV().zzb().zzb("App version does not match; dropping. appId", zzgu.zzl(str));
        return null;
    }

    private final boolean zzaP(String str, String str2) {
        zzbc zzbcVarZzf = zzj().zzf(str, str2);
        return zzbcVarZzf == null || zzbcVarZzf.zzc < 1;
    }

    public static void zzaQ(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT < 34) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, null, BroadcastOptions.makeBasic().setShareIdentityEnabled(true).toBundle());
        }
    }

    private static final boolean zzaR(zzr zzrVar) {
        return !TextUtils.isEmpty(zzrVar.zzb);
    }

    private static final zzos zzaS(zzos zzosVar) {
        if (zzosVar == null) {
            throw new IllegalStateException("Upload Component not created");
        }
        if (zzosVar.zzav()) {
            return zzosVar;
        }
        throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(zzosVar.getClass())));
    }

    private static final Boolean zzaT(zzr zzrVar) {
        Boolean bool = zzrVar.zzp;
        String str = zzrVar.zzC;
        if (!TextUtils.isEmpty(str)) {
            zzji zzjiVarZza = zze.zzc(str).zza();
            zzji zzjiVar = zzji.UNINITIALIZED;
            int iOrdinal = zzjiVarZza.ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                return null;
            }
            if (iOrdinal == 2) {
                return Boolean.TRUE;
            }
            if (iOrdinal == 3) {
                return Boolean.FALSE;
            }
        }
        return bool;
    }

    final void zzA(String str, zzjl zzjlVar) {
        zzaW().zzg();
        zzu();
        this.zzC.put(str, zzjlVar);
        zzj().zzZ(str, zzjlVar);
    }

    final zzjl zzB(String str) {
        zzjl zzjlVar = zzjl.zza;
        zzaW().zzg();
        zzu();
        zzjl zzjlVarZzX = (zzjl) this.zzC.get(str);
        if (zzjlVarZzX == null) {
            zzjlVarZzX = zzj().zzX(str);
            if (zzjlVarZzX == null) {
                zzjlVarZzX = zzjl.zza;
            }
            zzA(str, zzjlVarZzX);
        }
        return zzjlVarZzX;
    }

    final long zzC() {
        long jCurrentTimeMillis = zzaZ().currentTimeMillis();
        zznn zznnVar = this.zzk;
        zznnVar.zzaw();
        zznnVar.zzg();
        zzhe zzheVar = zznnVar.zzf;
        long jZza = zzheVar.zza();
        if (jZza == 0) {
            jZza = ((long) zznnVar.zzu.zzk().zzf().nextInt(86400000)) + 1;
            zzheVar.zzb(jZza);
        }
        return ((((jCurrentTimeMillis + jZza) / 1000) / 60) / 60) / 24;
    }

    final void zzD(zzbg zzbgVar, String str) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu == null || TextUtils.isEmpty(zzhVarZzu.zzr())) {
            zzaV().zzj().zzb("No app data available; dropping event", str);
            return;
        }
        Boolean boolZzaN = zzaN(zzhVarZzu);
        if (boolZzaN == null) {
            if (!"_ui".equals(zzbgVar.zza)) {
                zzaV().zze().zzb("Could not find package. appId", zzgu.zzl(str));
            }
        } else if (!boolZzaN.booleanValue()) {
            zzaV().zzb().zzb("App version does not match; dropping event. appId", zzgu.zzl(str));
            return;
        }
        zzE(zzbgVar, new zzr(str, zzhVarZzu.zzf(), zzhVarZzu.zzr(), zzhVarZzu.zzt(), zzhVarZzu.zzv(), zzhVarZzu.zzx(), zzhVarZzu.zzz(), (String) null, zzhVarZzu.zzD(), false, zzhVarZzu.zzl(), 0L, 0, zzhVarZzu.zzac(), false, zzhVarZzu.zzae(), zzhVarZzu.zzB(), zzhVarZzu.zzag(), zzB(str).zzl(), _UrlKt.FRAGMENT_ENCODE_SET, (String) null, zzhVarZzu.zzai(), zzhVarZzu.zzak(), zzB(str).zzb(), zzx(str).zze(), zzhVarZzu.zzao(), zzhVarZzu.zzaw(), zzhVarZzu.zzay(), zzhVarZzu.zzaH(), 0L, zzhVarZzu.zzaL()));
    }

    final void zzE(zzbg zzbgVar, zzr zzrVar) {
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        zzgv zzgvVarZza = zzgv.zza(zzbgVar);
        zzt().zzI(zzgvVarZza.zzd, zzj().zzU(str));
        zzt().zzG(zzgvVarZza, zzd().zzd(str));
        zzbg zzbgVarZzb = zzgvVarZza.zzb();
        if (!zzd().zzp(null, zzfy.zzbf) && "_cmp".equals(zzbgVarZzb.zza)) {
            zzbe zzbeVar = zzbgVarZzb.zzb;
            if ("referrer API v2".equals(zzbeVar.zzd("_cis"))) {
                String strZzd = zzbeVar.zzd("gclid");
                if (!TextUtils.isEmpty(strZzd)) {
                    zzac(new zzpl("_lgclid", zzbgVarZzb.zzd, strZzd, "auto"), zzrVar);
                }
            }
        }
        zzF(zzbgVarZzb, zzrVar);
    }

    final void zzF(zzbg zzbgVar, zzr zzrVar) {
        zzbg zzbgVar2;
        List<zzah> listZzt;
        List<zzah> listZzt2;
        List<zzah> listZzt3;
        String str;
        Preconditions.checkNotNull(zzrVar);
        String str2 = zzrVar.zza;
        Preconditions.checkNotEmpty(str2);
        zzaW().zzg();
        zzu();
        long j = zzbgVar.zzd;
        zzgv zzgvVarZza = zzgv.zza(zzbgVar);
        zzaW().zzg();
        zzpp.zzav((this.zzG == null || (str = this.zzH) == null || !str.equals(str2)) ? null : this.zzG, zzgvVarZza.zzd, false);
        zzbg zzbgVarZzb = zzgvVarZza.zzb();
        zzp();
        if (zzpk.zzD(zzbgVarZzb, zzrVar)) {
            if (!zzrVar.zzh) {
                zzao(zzrVar);
                return;
            }
            List list = zzrVar.zzr;
            if (list != null) {
                String str3 = zzbgVarZzb.zza;
                if (!list.contains(str3)) {
                    zzaV().zzj().zzd("Dropping non-safelisted event. appId, event name, origin", str2, zzbgVarZzb.zza, zzbgVarZzb.zzc);
                    return;
                } else {
                    Bundle bundleZzf = zzbgVarZzb.zzb.zzf();
                    bundleZzf.putLong("ga_safelisted", 1L);
                    zzbgVar2 = new zzbg(str3, new zzbe(bundleZzf), zzbgVarZzb.zzc, zzbgVarZzb.zzd);
                }
            } else {
                zzbgVar2 = zzbgVarZzb;
            }
            zzj().zzb();
            try {
                String str4 = zzbgVar2.zza;
                if ("_s".equals(str4) && !zzj().zzQ(str2, "_s") && zzbgVar2.zzb.zzb("_sid").longValue() != 0) {
                    if (zzj().zzQ(str2, "_f") || zzj().zzQ(str2, "_v")) {
                        zzj().zzW(str2, null, "_sid", zzG(str2, zzbgVar2));
                    } else {
                        zzj().zzW(str2, Long.valueOf(zzaZ().currentTimeMillis() - 15000), "_sid", zzG(str2, zzbgVar2));
                    }
                }
                zzav zzavVarZzj = zzj();
                Preconditions.checkNotEmpty(str2);
                zzavVarZzj.zzg();
                zzavVarZzj.zzaw();
                if (j < 0) {
                    zzavVarZzj.zzu.zzaV().zze().zzc("Invalid time querying timed out conditional properties", zzgu.zzl(str2), Long.valueOf(j));
                    listZzt = Collections.EMPTY_LIST;
                } else {
                    listZzt = zzavVarZzj.zzt("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str2, String.valueOf(j)});
                }
                for (zzah zzahVar : listZzt) {
                    if (zzahVar != null) {
                        zzaV().zzk().zzd("User property timed out", zzahVar.zza, this.zzn.zzl().zzc(zzahVar.zzc.zzb), zzahVar.zzc.zza());
                        zzbg zzbgVar3 = zzahVar.zzg;
                        if (zzbgVar3 != null) {
                            zzH(new zzbg(zzbgVar3, j), zzrVar);
                        }
                        zzj().zzr(str2, zzahVar.zzc.zzb);
                    }
                }
                zzav zzavVarZzj2 = zzj();
                Preconditions.checkNotEmpty(str2);
                zzavVarZzj2.zzg();
                zzavVarZzj2.zzaw();
                if (j < 0) {
                    zzavVarZzj2.zzu.zzaV().zze().zzc("Invalid time querying expired conditional properties", zzgu.zzl(str2), Long.valueOf(j));
                    listZzt2 = Collections.EMPTY_LIST;
                } else {
                    listZzt2 = zzavVarZzj2.zzt("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str2, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(listZzt2.size());
                for (zzah zzahVar2 : listZzt2) {
                    if (zzahVar2 != null) {
                        zzaV().zzk().zzd("User property expired", zzahVar2.zza, this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzc.zza());
                        zzj().zzk(str2, zzahVar2.zzc.zzb);
                        zzbg zzbgVar4 = zzahVar2.zzk;
                        if (zzbgVar4 != null) {
                            arrayList.add(zzbgVar4);
                        }
                        zzj().zzr(str2, zzahVar2.zzc.zzb);
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zzH(new zzbg((zzbg) obj, j), zzrVar);
                }
                zzav zzavVarZzj3 = zzj();
                Preconditions.checkNotEmpty(str2);
                Preconditions.checkNotEmpty(str4);
                zzavVarZzj3.zzg();
                zzavVarZzj3.zzaw();
                if (j < 0) {
                    zzic zzicVar = zzavVarZzj3.zzu;
                    zzicVar.zzaV().zze().zzd("Invalid time querying triggered conditional properties", zzgu.zzl(str2), zzicVar.zzl().zza(str4), Long.valueOf(j));
                    listZzt3 = Collections.EMPTY_LIST;
                } else {
                    listZzt3 = zzavVarZzj3.zzt("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str2, str4, String.valueOf(j)});
                }
                ArrayList arrayList2 = new ArrayList(listZzt3.size());
                for (zzah zzahVar3 : listZzt3) {
                    if (zzahVar3 != null) {
                        zzpl zzplVar = zzahVar3.zzc;
                        zzpn zzpnVar = new zzpn((String) Preconditions.checkNotNull(zzahVar3.zza), zzahVar3.zzb, zzplVar.zzb, j, Preconditions.checkNotNull(zzplVar.zza()));
                        if (zzj().zzl(zzpnVar)) {
                            zzaV().zzk().zzd("User property triggered", zzahVar3.zza, this.zzn.zzl().zzc(zzpnVar.zzc), zzpnVar.zze);
                        } else {
                            zzaV().zzb().zzd("Too many active user properties, ignoring", zzgu.zzl(zzahVar3.zza), this.zzn.zzl().zzc(zzpnVar.zzc), zzpnVar.zze);
                        }
                        zzbg zzbgVar5 = zzahVar3.zzi;
                        if (zzbgVar5 != null) {
                            arrayList2.add(zzbgVar5);
                        }
                        zzahVar3.zzc = new zzpl(zzpnVar);
                        zzahVar3.zze = true;
                        zzj().zzp(zzahVar3);
                    }
                }
                zzH(zzbgVar2, zzrVar);
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    zzH(new zzbg((zzbg) obj2, j), zzrVar);
                }
                zzj().zzc();
                zzj().zzd();
            } catch (Throwable th) {
                zzj().zzd();
                throw th;
            }
        }
    }

    final Bundle zzG(String str, zzbg zzbgVar) {
        Bundle bundle = new Bundle();
        bundle.putLong("_sid", zzbgVar.zzb.zzb("_sid").longValue());
        zzpn zzpnVarZzm = zzj().zzm(str, "_sno");
        if (zzpnVarZzm != null) {
            Object obj = zzpnVarZzm.zze;
            if (obj instanceof Long) {
                bundle.putLong("_sno", ((Long) obj).longValue());
            }
        }
        return bundle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:445:0x018d A[PHI: r28 r29
  0x018d: PHI (r28v6 java.lang.String) = (r28v1 java.lang.String), (r28v1 java.lang.String), (r28v7 java.lang.String) binds: [B:462:0x020c, B:464:0x021a, B:444:0x0189] A[DONT_GENERATE, DONT_INLINE]
  0x018d: PHI (r29v6 java.lang.String) = (r29v1 java.lang.String), (r29v1 java.lang.String), (r29v7 java.lang.String) binds: [B:462:0x020c, B:464:0x021a, B:444:0x0189] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:448:0x019d A[Catch: all -> 0x0176, TryCatch #3 {all -> 0x0176, blocks: (B:428:0x0155, B:431:0x0164, B:433:0x016c, B:439:0x0179, B:481:0x02f2, B:490:0x0325, B:492:0x0366, B:494:0x036b, B:495:0x0382, B:497:0x038d, B:499:0x03a6, B:501:0x03ab, B:502:0x03c2, B:505:0x03e4, B:509:0x0407, B:510:0x041e, B:512:0x042a, B:515:0x0447, B:516:0x045b, B:518:0x0463, B:520:0x046f, B:522:0x0475, B:523:0x047c, B:525:0x0489, B:527:0x0491, B:529:0x0499, B:531:0x04a1, B:532:0x04ad, B:533:0x04ba, B:539:0x04fc, B:540:0x0511, B:542:0x0533, B:545:0x054a, B:548:0x0585, B:550:0x05b0, B:552:0x05e8, B:553:0x05eb, B:555:0x05f3, B:556:0x05f6, B:558:0x05fe, B:559:0x0601, B:561:0x0609, B:562:0x060c, B:564:0x0615, B:565:0x0619, B:567:0x0626, B:568:0x0629, B:570:0x0655, B:572:0x065f, B:576:0x0674, B:581:0x0680, B:584:0x0689, B:588:0x0696, B:592:0x06a4, B:596:0x06b2, B:600:0x06c0, B:604:0x06ce, B:608:0x06d9, B:612:0x06e6, B:613:0x06f2, B:615:0x06f8, B:616:0x06fb, B:618:0x071e, B:621:0x0727, B:624:0x0730, B:625:0x074a, B:627:0x0750, B:629:0x0764, B:631:0x0770, B:633:0x077d, B:636:0x0796, B:637:0x07a6, B:641:0x07af, B:642:0x07b2, B:644:0x07bf, B:645:0x07c4, B:647:0x07e2, B:649:0x07e6, B:651:0x07f6, B:653:0x0801, B:654:0x080a, B:656:0x0814, B:658:0x0820, B:660:0x082a, B:662:0x0830, B:664:0x083f, B:666:0x0855, B:668:0x085b, B:669:0x0864, B:671:0x0872, B:673:0x08ae, B:675:0x08b8, B:676:0x08bb, B:678:0x08c5, B:680:0x08e1, B:681:0x08ec, B:683:0x0924, B:685:0x092c, B:687:0x0936, B:688:0x0943, B:690:0x094d, B:691:0x095a, B:692:0x0963, B:694:0x0969, B:696:0x09a5, B:698:0x09af, B:700:0x09c1, B:702:0x09c7, B:703:0x0a0c, B:704:0x0a17, B:705:0x0a22, B:707:0x0a28, B:716:0x0a75, B:717:0x0ac0, B:719:0x0ad1, B:733:0x0b32, B:724:0x0ae9, B:725:0x0aec, B:710:0x0a35, B:712:0x0a61, B:730:0x0b05, B:731:0x0b1c, B:732:0x0b1d, B:619:0x0721, B:549:0x05a2, B:536:0x04e3, B:484:0x0306, B:485:0x030d, B:487:0x0313, B:489:0x031f, B:446:0x0191, B:448:0x019d, B:450:0x01b2, B:456:0x01d2, B:461:0x0208, B:463:0x020e, B:465:0x021c, B:467:0x022a, B:470:0x0236, B:478:0x02bb, B:480:0x02c5, B:472:0x025f, B:473:0x0278, B:477:0x029e, B:476:0x028b, B:459:0x01de, B:460:0x01fc), top: B:745:0x0155, inners: #0, #1, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:460:0x01fc A[Catch: all -> 0x0176, TryCatch #3 {all -> 0x0176, blocks: (B:428:0x0155, B:431:0x0164, B:433:0x016c, B:439:0x0179, B:481:0x02f2, B:490:0x0325, B:492:0x0366, B:494:0x036b, B:495:0x0382, B:497:0x038d, B:499:0x03a6, B:501:0x03ab, B:502:0x03c2, B:505:0x03e4, B:509:0x0407, B:510:0x041e, B:512:0x042a, B:515:0x0447, B:516:0x045b, B:518:0x0463, B:520:0x046f, B:522:0x0475, B:523:0x047c, B:525:0x0489, B:527:0x0491, B:529:0x0499, B:531:0x04a1, B:532:0x04ad, B:533:0x04ba, B:539:0x04fc, B:540:0x0511, B:542:0x0533, B:545:0x054a, B:548:0x0585, B:550:0x05b0, B:552:0x05e8, B:553:0x05eb, B:555:0x05f3, B:556:0x05f6, B:558:0x05fe, B:559:0x0601, B:561:0x0609, B:562:0x060c, B:564:0x0615, B:565:0x0619, B:567:0x0626, B:568:0x0629, B:570:0x0655, B:572:0x065f, B:576:0x0674, B:581:0x0680, B:584:0x0689, B:588:0x0696, B:592:0x06a4, B:596:0x06b2, B:600:0x06c0, B:604:0x06ce, B:608:0x06d9, B:612:0x06e6, B:613:0x06f2, B:615:0x06f8, B:616:0x06fb, B:618:0x071e, B:621:0x0727, B:624:0x0730, B:625:0x074a, B:627:0x0750, B:629:0x0764, B:631:0x0770, B:633:0x077d, B:636:0x0796, B:637:0x07a6, B:641:0x07af, B:642:0x07b2, B:644:0x07bf, B:645:0x07c4, B:647:0x07e2, B:649:0x07e6, B:651:0x07f6, B:653:0x0801, B:654:0x080a, B:656:0x0814, B:658:0x0820, B:660:0x082a, B:662:0x0830, B:664:0x083f, B:666:0x0855, B:668:0x085b, B:669:0x0864, B:671:0x0872, B:673:0x08ae, B:675:0x08b8, B:676:0x08bb, B:678:0x08c5, B:680:0x08e1, B:681:0x08ec, B:683:0x0924, B:685:0x092c, B:687:0x0936, B:688:0x0943, B:690:0x094d, B:691:0x095a, B:692:0x0963, B:694:0x0969, B:696:0x09a5, B:698:0x09af, B:700:0x09c1, B:702:0x09c7, B:703:0x0a0c, B:704:0x0a17, B:705:0x0a22, B:707:0x0a28, B:716:0x0a75, B:717:0x0ac0, B:719:0x0ad1, B:733:0x0b32, B:724:0x0ae9, B:725:0x0aec, B:710:0x0a35, B:712:0x0a61, B:730:0x0b05, B:731:0x0b1c, B:732:0x0b1d, B:619:0x0721, B:549:0x05a2, B:536:0x04e3, B:484:0x0306, B:485:0x030d, B:487:0x0313, B:489:0x031f, B:446:0x0191, B:448:0x019d, B:450:0x01b2, B:456:0x01d2, B:461:0x0208, B:463:0x020e, B:465:0x021c, B:467:0x022a, B:470:0x0236, B:478:0x02bb, B:480:0x02c5, B:472:0x025f, B:473:0x0278, B:477:0x029e, B:476:0x028b, B:459:0x01de, B:460:0x01fc), top: B:745:0x0155, inners: #0, #1, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:463:0x020e A[Catch: all -> 0x0176, TryCatch #3 {all -> 0x0176, blocks: (B:428:0x0155, B:431:0x0164, B:433:0x016c, B:439:0x0179, B:481:0x02f2, B:490:0x0325, B:492:0x0366, B:494:0x036b, B:495:0x0382, B:497:0x038d, B:499:0x03a6, B:501:0x03ab, B:502:0x03c2, B:505:0x03e4, B:509:0x0407, B:510:0x041e, B:512:0x042a, B:515:0x0447, B:516:0x045b, B:518:0x0463, B:520:0x046f, B:522:0x0475, B:523:0x047c, B:525:0x0489, B:527:0x0491, B:529:0x0499, B:531:0x04a1, B:532:0x04ad, B:533:0x04ba, B:539:0x04fc, B:540:0x0511, B:542:0x0533, B:545:0x054a, B:548:0x0585, B:550:0x05b0, B:552:0x05e8, B:553:0x05eb, B:555:0x05f3, B:556:0x05f6, B:558:0x05fe, B:559:0x0601, B:561:0x0609, B:562:0x060c, B:564:0x0615, B:565:0x0619, B:567:0x0626, B:568:0x0629, B:570:0x0655, B:572:0x065f, B:576:0x0674, B:581:0x0680, B:584:0x0689, B:588:0x0696, B:592:0x06a4, B:596:0x06b2, B:600:0x06c0, B:604:0x06ce, B:608:0x06d9, B:612:0x06e6, B:613:0x06f2, B:615:0x06f8, B:616:0x06fb, B:618:0x071e, B:621:0x0727, B:624:0x0730, B:625:0x074a, B:627:0x0750, B:629:0x0764, B:631:0x0770, B:633:0x077d, B:636:0x0796, B:637:0x07a6, B:641:0x07af, B:642:0x07b2, B:644:0x07bf, B:645:0x07c4, B:647:0x07e2, B:649:0x07e6, B:651:0x07f6, B:653:0x0801, B:654:0x080a, B:656:0x0814, B:658:0x0820, B:660:0x082a, B:662:0x0830, B:664:0x083f, B:666:0x0855, B:668:0x085b, B:669:0x0864, B:671:0x0872, B:673:0x08ae, B:675:0x08b8, B:676:0x08bb, B:678:0x08c5, B:680:0x08e1, B:681:0x08ec, B:683:0x0924, B:685:0x092c, B:687:0x0936, B:688:0x0943, B:690:0x094d, B:691:0x095a, B:692:0x0963, B:694:0x0969, B:696:0x09a5, B:698:0x09af, B:700:0x09c1, B:702:0x09c7, B:703:0x0a0c, B:704:0x0a17, B:705:0x0a22, B:707:0x0a28, B:716:0x0a75, B:717:0x0ac0, B:719:0x0ad1, B:733:0x0b32, B:724:0x0ae9, B:725:0x0aec, B:710:0x0a35, B:712:0x0a61, B:730:0x0b05, B:731:0x0b1c, B:732:0x0b1d, B:619:0x0721, B:549:0x05a2, B:536:0x04e3, B:484:0x0306, B:485:0x030d, B:487:0x0313, B:489:0x031f, B:446:0x0191, B:448:0x019d, B:450:0x01b2, B:456:0x01d2, B:461:0x0208, B:463:0x020e, B:465:0x021c, B:467:0x022a, B:470:0x0236, B:478:0x02bb, B:480:0x02c5, B:472:0x025f, B:473:0x0278, B:477:0x029e, B:476:0x028b, B:459:0x01de, B:460:0x01fc), top: B:745:0x0155, inners: #0, #1, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x0306 A[Catch: all -> 0x0176, TryCatch #3 {all -> 0x0176, blocks: (B:428:0x0155, B:431:0x0164, B:433:0x016c, B:439:0x0179, B:481:0x02f2, B:490:0x0325, B:492:0x0366, B:494:0x036b, B:495:0x0382, B:497:0x038d, B:499:0x03a6, B:501:0x03ab, B:502:0x03c2, B:505:0x03e4, B:509:0x0407, B:510:0x041e, B:512:0x042a, B:515:0x0447, B:516:0x045b, B:518:0x0463, B:520:0x046f, B:522:0x0475, B:523:0x047c, B:525:0x0489, B:527:0x0491, B:529:0x0499, B:531:0x04a1, B:532:0x04ad, B:533:0x04ba, B:539:0x04fc, B:540:0x0511, B:542:0x0533, B:545:0x054a, B:548:0x0585, B:550:0x05b0, B:552:0x05e8, B:553:0x05eb, B:555:0x05f3, B:556:0x05f6, B:558:0x05fe, B:559:0x0601, B:561:0x0609, B:562:0x060c, B:564:0x0615, B:565:0x0619, B:567:0x0626, B:568:0x0629, B:570:0x0655, B:572:0x065f, B:576:0x0674, B:581:0x0680, B:584:0x0689, B:588:0x0696, B:592:0x06a4, B:596:0x06b2, B:600:0x06c0, B:604:0x06ce, B:608:0x06d9, B:612:0x06e6, B:613:0x06f2, B:615:0x06f8, B:616:0x06fb, B:618:0x071e, B:621:0x0727, B:624:0x0730, B:625:0x074a, B:627:0x0750, B:629:0x0764, B:631:0x0770, B:633:0x077d, B:636:0x0796, B:637:0x07a6, B:641:0x07af, B:642:0x07b2, B:644:0x07bf, B:645:0x07c4, B:647:0x07e2, B:649:0x07e6, B:651:0x07f6, B:653:0x0801, B:654:0x080a, B:656:0x0814, B:658:0x0820, B:660:0x082a, B:662:0x0830, B:664:0x083f, B:666:0x0855, B:668:0x085b, B:669:0x0864, B:671:0x0872, B:673:0x08ae, B:675:0x08b8, B:676:0x08bb, B:678:0x08c5, B:680:0x08e1, B:681:0x08ec, B:683:0x0924, B:685:0x092c, B:687:0x0936, B:688:0x0943, B:690:0x094d, B:691:0x095a, B:692:0x0963, B:694:0x0969, B:696:0x09a5, B:698:0x09af, B:700:0x09c1, B:702:0x09c7, B:703:0x0a0c, B:704:0x0a17, B:705:0x0a22, B:707:0x0a28, B:716:0x0a75, B:717:0x0ac0, B:719:0x0ad1, B:733:0x0b32, B:724:0x0ae9, B:725:0x0aec, B:710:0x0a35, B:712:0x0a61, B:730:0x0b05, B:731:0x0b1c, B:732:0x0b1d, B:619:0x0721, B:549:0x05a2, B:536:0x04e3, B:484:0x0306, B:485:0x030d, B:487:0x0313, B:489:0x031f, B:446:0x0191, B:448:0x019d, B:450:0x01b2, B:456:0x01d2, B:461:0x0208, B:463:0x020e, B:465:0x021c, B:467:0x022a, B:470:0x0236, B:478:0x02bb, B:480:0x02c5, B:472:0x025f, B:473:0x0278, B:477:0x029e, B:476:0x028b, B:459:0x01de, B:460:0x01fc), top: B:745:0x0155, inners: #0, #1, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:492:0x0366 A[Catch: all -> 0x0176, TryCatch #3 {all -> 0x0176, blocks: (B:428:0x0155, B:431:0x0164, B:433:0x016c, B:439:0x0179, B:481:0x02f2, B:490:0x0325, B:492:0x0366, B:494:0x036b, B:495:0x0382, B:497:0x038d, B:499:0x03a6, B:501:0x03ab, B:502:0x03c2, B:505:0x03e4, B:509:0x0407, B:510:0x041e, B:512:0x042a, B:515:0x0447, B:516:0x045b, B:518:0x0463, B:520:0x046f, B:522:0x0475, B:523:0x047c, B:525:0x0489, B:527:0x0491, B:529:0x0499, B:531:0x04a1, B:532:0x04ad, B:533:0x04ba, B:539:0x04fc, B:540:0x0511, B:542:0x0533, B:545:0x054a, B:548:0x0585, B:550:0x05b0, B:552:0x05e8, B:553:0x05eb, B:555:0x05f3, B:556:0x05f6, B:558:0x05fe, B:559:0x0601, B:561:0x0609, B:562:0x060c, B:564:0x0615, B:565:0x0619, B:567:0x0626, B:568:0x0629, B:570:0x0655, B:572:0x065f, B:576:0x0674, B:581:0x0680, B:584:0x0689, B:588:0x0696, B:592:0x06a4, B:596:0x06b2, B:600:0x06c0, B:604:0x06ce, B:608:0x06d9, B:612:0x06e6, B:613:0x06f2, B:615:0x06f8, B:616:0x06fb, B:618:0x071e, B:621:0x0727, B:624:0x0730, B:625:0x074a, B:627:0x0750, B:629:0x0764, B:631:0x0770, B:633:0x077d, B:636:0x0796, B:637:0x07a6, B:641:0x07af, B:642:0x07b2, B:644:0x07bf, B:645:0x07c4, B:647:0x07e2, B:649:0x07e6, B:651:0x07f6, B:653:0x0801, B:654:0x080a, B:656:0x0814, B:658:0x0820, B:660:0x082a, B:662:0x0830, B:664:0x083f, B:666:0x0855, B:668:0x085b, B:669:0x0864, B:671:0x0872, B:673:0x08ae, B:675:0x08b8, B:676:0x08bb, B:678:0x08c5, B:680:0x08e1, B:681:0x08ec, B:683:0x0924, B:685:0x092c, B:687:0x0936, B:688:0x0943, B:690:0x094d, B:691:0x095a, B:692:0x0963, B:694:0x0969, B:696:0x09a5, B:698:0x09af, B:700:0x09c1, B:702:0x09c7, B:703:0x0a0c, B:704:0x0a17, B:705:0x0a22, B:707:0x0a28, B:716:0x0a75, B:717:0x0ac0, B:719:0x0ad1, B:733:0x0b32, B:724:0x0ae9, B:725:0x0aec, B:710:0x0a35, B:712:0x0a61, B:730:0x0b05, B:731:0x0b1c, B:732:0x0b1d, B:619:0x0721, B:549:0x05a2, B:536:0x04e3, B:484:0x0306, B:485:0x030d, B:487:0x0313, B:489:0x031f, B:446:0x0191, B:448:0x019d, B:450:0x01b2, B:456:0x01d2, B:461:0x0208, B:463:0x020e, B:465:0x021c, B:467:0x022a, B:470:0x0236, B:478:0x02bb, B:480:0x02c5, B:472:0x025f, B:473:0x0278, B:477:0x029e, B:476:0x028b, B:459:0x01de, B:460:0x01fc), top: B:745:0x0155, inners: #0, #1, #4, #6, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:496:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x086f  */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzH(com.google.android.gms.measurement.internal.zzbg r45, com.google.android.gms.measurement.internal.zzr r46) {
        /*
            Method dump skipped, instruction units count: 2923
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzH(com.google.android.gms.measurement.internal.zzbg, com.google.android.gms.measurement.internal.zzr):void");
    }

    final void zzI(zzh zzhVar, com.google.android.gms.internal.measurement.zzic zzicVar) {
        com.google.android.gms.internal.measurement.zziu zziuVar;
        zzaW().zzg();
        zzu();
        zzan zzanVarZzd = zzan.zzd(zzicVar.zzaA());
        String strZzc = zzhVar.zzc();
        zzaW().zzg();
        zzu();
        zzjl zzjlVarZzB = zzB(strZzc);
        zzji zzjiVar = zzji.UNINITIALIZED;
        int iOrdinal = zzjlVarZzB.zzp().ordinal();
        if (iOrdinal == 1) {
            zzanVarZzd.zzc(zzjk.AD_STORAGE, zzam.REMOTE_ENFORCED_DEFAULT);
        } else if (iOrdinal == 2 || iOrdinal == 3) {
            zzanVarZzd.zzb(zzjk.AD_STORAGE, zzjlVarZzB.zzb());
        } else {
            zzanVarZzd.zzc(zzjk.AD_STORAGE, zzam.FAILSAFE);
        }
        int iOrdinal2 = zzjlVarZzB.zzq().ordinal();
        if (iOrdinal2 == 1) {
            zzanVarZzd.zzc(zzjk.ANALYTICS_STORAGE, zzam.REMOTE_ENFORCED_DEFAULT);
        } else if (iOrdinal2 == 2 || iOrdinal2 == 3) {
            zzanVarZzd.zzb(zzjk.ANALYTICS_STORAGE, zzjlVarZzB.zzb());
        } else {
            zzanVarZzd.zzc(zzjk.ANALYTICS_STORAGE, zzam.FAILSAFE);
        }
        String strZzc2 = zzhVar.zzc();
        zzaW().zzg();
        zzu();
        zzaz zzazVarZzz = zzz(strZzc2, zzx(strZzc2), zzB(strZzc2), zzanVarZzd);
        zzicVar.zzaD(((Boolean) Preconditions.checkNotNull(zzazVarZzz.zzj())).booleanValue());
        if (!TextUtils.isEmpty(zzazVarZzz.zzk())) {
            zzicVar.zzaF(zzazVarZzz.zzk());
        }
        zzaW().zzg();
        zzu();
        Iterator it = zzicVar.zzk().iterator();
        while (true) {
            if (it.hasNext()) {
                zziuVar = (com.google.android.gms.internal.measurement.zziu) it.next();
                if ("_npa".equals(zziuVar.zzc())) {
                    break;
                }
            } else {
                zziuVar = null;
                break;
            }
        }
        if (zziuVar != null) {
            zzjk zzjkVar = zzjk.AD_PERSONALIZATION;
            if (zzanVarZzd.zza(zzjkVar) == zzam.UNSET) {
                zzpn zzpnVarZzm = zzj().zzm(zzhVar.zzc(), "_npa");
                if (zzpnVarZzm != null) {
                    String str = zzpnVarZzm.zzb;
                    if ("tcf".equals(str)) {
                        zzanVarZzd.zzc(zzjkVar, zzam.TCF);
                    } else if (Common.ASSET_APP.equals(str)) {
                        zzanVarZzd.zzc(zzjkVar, zzam.API);
                    } else {
                        zzanVarZzd.zzc(zzjkVar, zzam.MANIFEST);
                    }
                } else {
                    Boolean boolZzae = zzhVar.zzae();
                    if (boolZzae == null || ((boolZzae.booleanValue() && zziuVar.zzg() != 1) || !(boolZzae.booleanValue() || zziuVar.zzg() == 0))) {
                        zzanVarZzd.zzc(zzjkVar, zzam.API);
                    } else {
                        zzanVarZzd.zzc(zzjkVar, zzam.MANIFEST);
                    }
                }
            }
        } else {
            int iZzaC = zzaC(zzhVar.zzc(), zzanVarZzd);
            com.google.android.gms.internal.measurement.zzit zzitVarZzm = com.google.android.gms.internal.measurement.zziu.zzm();
            zzitVarZzm.zzb("_npa");
            zzitVarZzm.zza(zzaZ().currentTimeMillis());
            zzitVarZzm.zze(iZzaC);
            zzicVar.zzo((com.google.android.gms.internal.measurement.zziu) zzitVarZzm.zzbc());
            zzaV().zzk().zzc("Setting user property", "non_personalized_ads(_npa)", Integer.valueOf(iZzaC));
        }
        zzicVar.zzaB(zzanVarZzd.toString());
        boolean zZzy = this.zzc.zzy(zzhVar.zzc());
        List listZzb = zzicVar.zzb();
        int i = 0;
        for (int i2 = 0; i2 < listZzb.size(); i2++) {
            if ("_tcf".equals(((com.google.android.gms.internal.measurement.zzhs) listZzb.get(i2)).zzd())) {
                com.google.android.gms.internal.measurement.zzhr zzhrVar = (com.google.android.gms.internal.measurement.zzhr) ((com.google.android.gms.internal.measurement.zzhs) listZzb.get(i2)).zzcl();
                List listZza = zzhrVar.zza();
                int i3 = 0;
                while (true) {
                    if (i3 >= listZza.size()) {
                        break;
                    }
                    if ("_tcfd".equals(((com.google.android.gms.internal.measurement.zzhw) listZza.get(i3)).zzb())) {
                        String strZzd = ((com.google.android.gms.internal.measurement.zzhw) listZza.get(i3)).zzd();
                        if (zZzy && strZzd.length() > 4) {
                            char[] charArray = strZzd.toCharArray();
                            int i4 = 1;
                            while (true) {
                                if (i4 >= 64) {
                                    break;
                                }
                                if (charArray[4] == "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".charAt(i4)) {
                                    i = i4;
                                    break;
                                }
                                i4++;
                            }
                            charArray[4] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".charAt(i | 1);
                            strZzd = String.valueOf(charArray);
                        }
                        zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
                        zzhvVarZzn.zzb("_tcfd");
                        zzhvVarZzn.zzd(strZzd);
                        zzhrVar.zze(i3, zzhvVarZzn);
                    } else {
                        i3++;
                    }
                }
                zzicVar.zzf(i2, zzhrVar);
                return;
            }
        }
    }

    final void zzJ(zzh zzhVar, com.google.android.gms.internal.measurement.zzic zzicVar) {
        zzaW().zzg();
        zzu();
        com.google.android.gms.internal.measurement.zzgx zzgxVarZzr = com.google.android.gms.internal.measurement.zzha.zzr();
        byte[] bArrZzaJ = zzhVar.zzaJ();
        if (bArrZzaJ != null) {
            try {
                zzgxVarZzr = (com.google.android.gms.internal.measurement.zzgx) zzpk.zzw(zzgxVarZzr, bArrZzaJ);
            } catch (com.google.android.gms.internal.measurement.zzmr unused) {
                zzaV().zze().zzb("Failed to parse locally stored ad campaign info. appId", zzgu.zzl(zzhVar.zzc()));
            }
        }
        for (com.google.android.gms.internal.measurement.zzhs zzhsVar : zzicVar.zzb()) {
            if (zzhsVar.zzd().equals("_cmp")) {
                String str = (String) zzpk.zzJ(zzhsVar, "gclid", _UrlKt.FRAGMENT_ENCODE_SET);
                String str2 = (String) zzpk.zzJ(zzhsVar, "gbraid", _UrlKt.FRAGMENT_ENCODE_SET);
                String str3 = (String) zzpk.zzJ(zzhsVar, "gad_source", _UrlKt.FRAGMENT_ENCODE_SET);
                String[] strArrSplit = ((String) zzfy.zzbg.zzb(null)).split(",");
                zzp();
                if (!zzpk.zzG(zzhsVar, strArrSplit).isEmpty()) {
                    long jLongValue = ((Long) zzpk.zzJ(zzhsVar, "click_timestamp", 0L)).longValue();
                    if (jLongValue <= 0) {
                        jLongValue = zzhsVar.zzf();
                    }
                    if ("referrer API v2".equals(zzpk.zzI(zzhsVar, "_cis"))) {
                        if (jLongValue > zzgxVarZzr.zzo()) {
                            if (str.isEmpty()) {
                                zzgxVarZzr.zzj();
                            } else {
                                zzgxVarZzr.zzi(str);
                            }
                            if (str2.isEmpty()) {
                                zzgxVarZzr.zzl();
                            } else {
                                zzgxVarZzr.zzk(str2);
                            }
                            if (str3.isEmpty()) {
                                zzgxVarZzr.zzn();
                            } else {
                                zzgxVarZzr.zzm(str3);
                            }
                            zzgxVarZzr.zzp(jLongValue);
                            zzgxVarZzr.zzs();
                            zzgxVarZzr.zzt(zzaD(zzhsVar));
                        }
                    } else if (jLongValue > zzgxVarZzr.zzg()) {
                        if (str.isEmpty()) {
                            zzgxVarZzr.zzb();
                        } else {
                            zzgxVarZzr.zza(str);
                        }
                        if (str2.isEmpty()) {
                            zzgxVarZzr.zzd();
                        } else {
                            zzgxVarZzr.zzc(str2);
                        }
                        if (str3.isEmpty()) {
                            zzgxVarZzr.zzf();
                        } else {
                            zzgxVarZzr.zze(str3);
                        }
                        zzgxVarZzr.zzh(jLongValue);
                        zzgxVarZzr.zzq();
                        zzgxVarZzr.zzr(zzaD(zzhsVar));
                    }
                }
            }
        }
        if (!((com.google.android.gms.internal.measurement.zzha) zzgxVarZzr.zzbc()).equals(com.google.android.gms.internal.measurement.zzha.zzs())) {
            zzicVar.zzaM((com.google.android.gms.internal.measurement.zzha) zzgxVarZzr.zzbc());
        }
        zzhVar.zzaI(((com.google.android.gms.internal.measurement.zzha) zzgxVarZzr.zzbc()).zzcc());
        if (zzhVar.zza()) {
            zzj().zzv(zzhVar, false, false);
        }
        if (zzd().zzp(null, zzfy.zzbf)) {
            zzj().zzk(zzhVar.zzc(), "_lgclid");
        }
    }

    final String zzK(zzjl zzjlVar) {
        if (!zzjlVar.zzo(zzjk.ANALYTICS_STORAGE)) {
            return null;
        }
        byte[] bArr = new byte[16];
        zzt().zzf().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    final void zzL(List list) {
        Preconditions.checkArgument(!list.isEmpty());
        if (this.zzz != null) {
            zzaV().zzb().zza("Set uploading progress before finishing the previous upload");
        } else {
            this.zzz = new ArrayList(list);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.google.android.gms.measurement.internal.zzpg] */
    /* JADX WARN: Type inference failed for: r1v12, types: [long] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v22, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v25, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2, types: [android.database.Cursor] */
    final void zzM() {
        SQLiteException e;
        zzh zzhVarZzu;
        zzaW().zzg();
        zzu();
        this.zzw = true;
        try {
            zzic zzicVar = this.zzn;
            zzicVar.zzaU();
            Boolean boolZzJ = zzicVar.zzt().zzJ();
            if (boolZzJ == null) {
                zzaV().zze().zza("Upload data called on the client side before use of service was decided");
            } else if (boolZzJ.booleanValue()) {
                zzaV().zzb().zza("Upload called in the client side when service should be used");
            } else if (this.zza > 0) {
                zzaL();
            } else {
                zzaW().zzg();
                if (this.zzz != null) {
                    zzaV().zzk().zza("Uploading requested multiple times");
                } else if (zzi().zzb()) {
                    ?? CurrentTimeMillis = zzaZ().currentTimeMillis();
                    ?? r7 = 0;
                    cursorRawQuery = null;
                    Cursor cursorRawQuery = null;
                    string = null;
                    string = null;
                    String string = null;
                    int iZzm = zzd().zzm(null, zzfy.zzai);
                    zzd();
                    long jZzF = CurrentTimeMillis - zzal.zzF();
                    for (int i = 0; i < iZzm && zzaG(null, jZzF); i++) {
                    }
                    zzqp.zza();
                    zzaW().zzg();
                    zzav();
                    long jZza = this.zzk.zzd.zza();
                    if (jZza != 0) {
                        zzaV().zzj().zzb("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(CurrentTimeMillis - jZza)));
                    }
                    String strZzF = zzj().zzF();
                    long j = -1;
                    if (TextUtils.isEmpty(strZzF)) {
                        try {
                            this.zzB = -1L;
                            zzav zzavVarZzj = zzj();
                            zzd();
                            long jZzF2 = CurrentTimeMillis - zzal.zzF();
                            zzavVarZzj.zzg();
                            zzavVarZzj.zzaw();
                            try {
                                CurrentTimeMillis = zzavVarZzj.zze().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(jZzF2)});
                            } catch (SQLiteException e2) {
                                e = e2;
                                CurrentTimeMillis = 0;
                            } catch (Throwable th) {
                                th = th;
                                if (r7 == 0) {
                                    throw th;
                                }
                                throw th;
                            }
                            try {
                                if (CurrentTimeMillis.moveToFirst()) {
                                    string = CurrentTimeMillis.getString(0);
                                } else {
                                    zzavVarZzj.zzu.zzaV().zzk().zza("No expired configs for apps with pending events");
                                }
                            } catch (SQLiteException e3) {
                                e = e3;
                                CurrentTimeMillis = CurrentTimeMillis;
                                zzavVarZzj.zzu.zzaV().zzb().zzb("Error selecting expired configs", e);
                                if (CurrentTimeMillis != 0) {
                                }
                                if (!TextUtils.isEmpty(string)) {
                                    zzW(zzhVarZzu);
                                }
                                this.zzw = false;
                                zzaM();
                            }
                            CurrentTimeMillis.close();
                            if (!TextUtils.isEmpty(string) && (zzhVarZzu = zzj().zzu(string)) != null) {
                                zzW(zzhVarZzu);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            r7 = CurrentTimeMillis;
                        }
                    } else {
                        if (this.zzB == -1) {
                            zzav zzavVarZzj2 = zzj();
                            try {
                                try {
                                    cursorRawQuery = zzavVarZzj2.zze().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
                                    if (cursorRawQuery.moveToFirst()) {
                                        j = cursorRawQuery.getLong(0);
                                    }
                                } catch (SQLiteException e4) {
                                    zzavVarZzj2.zzu.zzaV().zzb().zzb("Error querying raw events", e4);
                                    if (cursorRawQuery != null) {
                                    }
                                    this.zzB = j;
                                    zzN(strZzF, CurrentTimeMillis);
                                    this.zzw = false;
                                    zzaM();
                                }
                                cursorRawQuery.close();
                                this.zzB = j;
                            } finally {
                                if (cursorRawQuery != null) {
                                    cursorRawQuery.close();
                                }
                            }
                        }
                        zzN(strZzF, CurrentTimeMillis);
                    }
                } else {
                    zzaV().zzk().zza("Network not connected, ignoring upload request");
                    zzaL();
                }
            }
            this.zzw = false;
            zzaM();
        } catch (Throwable th3) {
            this.zzw = false;
            zzaM();
            throw th3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:494:0x006b A[PHI: r0 r10 r23
  0x006b: PHI (r0v117 java.util.List) = (r0v8 java.util.List), (r0v141 java.util.List) binds: [B:585:0x022a, B:493:0x0069] A[DONT_GENERATE, DONT_INLINE]
  0x006b: PHI (r10v57 android.database.Cursor) = (r10v5 android.database.Cursor), (r10v59 android.database.Cursor) binds: [B:585:0x022a, B:493:0x0069] A[DONT_GENERATE, DONT_INLINE]
  0x006b: PHI (r23v28 long) = (r23v2 long), (r23v29 long) binds: [B:585:0x022a, B:493:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:590:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:654:0x045e A[PHI: r10 r16 r23
  0x045e: PHI (r10v39 java.util.List) = (r10v53 java.util.List), (r10v38 java.util.List) binds: [B:661:0x0484, B:653:0x045c] A[DONT_GENERATE, DONT_INLINE]
  0x045e: PHI (r16v7 java.util.List) = (r16v12 java.util.List), (r16v13 java.util.List) binds: [B:661:0x0484, B:653:0x045c] A[DONT_GENERATE, DONT_INLINE]
  0x045e: PHI (r23v12 android.database.Cursor) = (r23v18 android.database.Cursor), (r23v22 android.database.Cursor) binds: [B:661:0x0484, B:653:0x045c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:666:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x055c  */
    /* JADX WARN: Removed duplicated region for block: B:853:0x09b7  */
    /* JADX WARN: Removed duplicated region for block: B:861:0x0a05  */
    /* JADX WARN: Removed duplicated region for block: B:952:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:953:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzN(java.lang.String r33, long r34) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 2569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzN(java.lang.String, long):void");
    }

    final boolean zzO(String str, String str2) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu != null && zzt().zzaa(str, zzhVarZzu.zzay())) {
            this.zzF.remove(str2);
            return true;
        }
        zzpe zzpeVar = (zzpe) this.zzF.get(str2);
        if (zzpeVar == null) {
            return true;
        }
        return zzpeVar.zzb();
    }

    final void zzP(String str) {
        com.google.android.gms.internal.measurement.zzib zzibVarZzd;
        zzaW().zzg();
        zzu();
        this.zzw = true;
        try {
            zzic zzicVar = this.zzn;
            zzicVar.zzaU();
            Boolean boolZzJ = zzicVar.zzt().zzJ();
            if (boolZzJ == null) {
                zzaV().zze().zza("Upload data called on the client side before use of service was decided");
            } else if (boolZzJ.booleanValue()) {
                zzaV().zzb().zza("Upload called in the client side when service should be used");
            } else if (this.zza > 0) {
                zzaL();
            } else if (!zzi().zzb()) {
                zzaV().zzk().zza("Network not connected, ignoring upload request");
                zzaL();
            } else if (zzj().zzD(str)) {
                zzav zzavVarZzj = zzj();
                Preconditions.checkNotEmpty(str);
                zzavVarZzj.zzg();
                zzavVarZzj.zzaw();
                List listZzC = zzavVarZzj.zzC(str, zzoo.zza(zzls.GOOGLE_SIGNAL), 1);
                zzpj zzpjVar = listZzC.isEmpty() ? null : (zzpj) listZzC.get(0);
                if (zzpjVar != null && (zzibVarZzd = zzpjVar.zzd()) != null) {
                    zzaV().zzk().zzd("[sgtm] Uploading data from upload queue. appId, type, url", str, zzpjVar.zzf(), zzpjVar.zze());
                    byte[] bArrZzcc = zzibVarZzd.zzcc();
                    if (Log.isLoggable(zzaV().zzn(), 2)) {
                        zzaV().zzk().zzd("[sgtm] Uploading data from upload queue. appId, uncompressed size, data", str, Integer.valueOf(bArrZzcc.length), zzp().zzi(zzibVarZzd));
                    }
                    zzot zzotVarZza = zzpjVar.zza();
                    this.zzv = true;
                    zzi().zzc(str, zzotVarZza, zzibVarZzd, new zzox(this, str, zzpjVar));
                }
            } else {
                zzaV().zzk().zzb("[sgtm] Upload queue has no batches for appId", str);
            }
            this.zzw = false;
            zzaM();
        } catch (Throwable th) {
            this.zzw = false;
            zzaM();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x001e A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:32:0x000d, B:47:0x005a, B:50:0x0080, B:41:0x001e, B:43:0x0048, B:45:0x0052, B:46:0x0056), top: B:55:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzQ(java.lang.String r4, int r5, java.lang.Throwable r6, byte[] r7, com.google.android.gms.measurement.internal.zzpj r8) {
        /*
            r3 = this;
            com.google.android.gms.measurement.internal.zzhz r0 = r3.zzaW()
            r0.zzg()
            r3.zzu()
            r0 = 0
            if (r7 != 0) goto L13
            byte[] r7 = new byte[r0]     // Catch: java.lang.Throwable -> L10
            goto L13
        L10:
            r4 = move-exception
            goto L9b
        L13:
            r1 = 200(0xc8, float:2.8E-43)
            if (r5 == r1) goto L1c
            r1 = 204(0xcc, float:2.86E-43)
            if (r5 != r1) goto L5a
            r5 = r1
        L1c:
            if (r6 != 0) goto L5a
            com.google.android.gms.measurement.internal.zzav r6 = r3.zzj()     // Catch: java.lang.Throwable -> L10
            long r7 = r8.zzc()     // Catch: java.lang.Throwable -> L10
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> L10
            r6.zzE(r7)     // Catch: java.lang.Throwable -> L10
            com.google.android.gms.measurement.internal.zzgu r6 = r3.zzaV()     // Catch: java.lang.Throwable -> L10
            com.google.android.gms.measurement.internal.zzgs r6 = r6.zzk()     // Catch: java.lang.Throwable -> L10
            java.lang.String r7 = "Successfully uploaded batch from upload queue. appId, status"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L10
            r6.zzc(r7, r4, r5)     // Catch: java.lang.Throwable -> L10
            com.google.android.gms.measurement.internal.zzgz r5 = r3.zzi()     // Catch: java.lang.Throwable -> L10
            boolean r5 = r5.zzb()     // Catch: java.lang.Throwable -> L10
            if (r5 == 0) goto L56
            com.google.android.gms.measurement.internal.zzav r5 = r3.zzj()     // Catch: java.lang.Throwable -> L10
            boolean r5 = r5.zzD(r4)     // Catch: java.lang.Throwable -> L10
            if (r5 == 0) goto L56
            r3.zzP(r4)     // Catch: java.lang.Throwable -> L10
            goto L95
        L56:
            r3.zzaL()     // Catch: java.lang.Throwable -> L10
            goto L95
        L5a:
            java.lang.String r1 = new java.lang.String     // Catch: java.lang.Throwable -> L10
            java.nio.charset.Charset r2 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L10
            r1.<init>(r7, r2)     // Catch: java.lang.Throwable -> L10
            int r7 = r1.length()     // Catch: java.lang.Throwable -> L10
            r2 = 32
            int r7 = java.lang.Math.min(r2, r7)     // Catch: java.lang.Throwable -> L10
            java.lang.String r7 = r1.substring(r0, r7)     // Catch: java.lang.Throwable -> L10
            com.google.android.gms.measurement.internal.zzgu r1 = r3.zzaV()     // Catch: java.lang.Throwable -> L10
            com.google.android.gms.measurement.internal.zzgs r1 = r1.zzh()     // Catch: java.lang.Throwable -> L10
            java.lang.String r2 = "Network upload failed. Will retry later. appId, status, error"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L10
            if (r6 != 0) goto L80
            r6 = r7
        L80:
            r1.zzd(r2, r4, r5, r6)     // Catch: java.lang.Throwable -> L10
            com.google.android.gms.measurement.internal.zzav r4 = r3.zzj()     // Catch: java.lang.Throwable -> L10
            long r5 = r8.zzc()     // Catch: java.lang.Throwable -> L10
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L10
            r4.zzK(r5)     // Catch: java.lang.Throwable -> L10
            r3.zzaL()     // Catch: java.lang.Throwable -> L10
        L95:
            r3.zzv = r0
            r3.zzaM()
            return
        L9b:
            r3.zzv = r0
            r3.zzaM()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzQ(java.lang.String, int, java.lang.Throwable, byte[], com.google.android.gms.measurement.internal.zzpj):void");
    }

    final void zzR(String str, boolean z, Long l, Long l2) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu != null) {
            zzhVarZzu.zzar(z);
            zzhVarZzu.zzat(l);
            zzhVarZzu.zzav(l2);
            if (zzhVarZzu.zza()) {
                zzj().zzv(zzhVarZzu, false, false);
            }
        }
    }

    final void zzS(String str, com.google.android.gms.internal.measurement.zzic zzicVar) {
        int iZzx;
        int iIndexOf;
        Set setZzl = zzh().zzl(str);
        if (setZzl != null) {
            zzicVar.zzaw(setZzl);
        }
        if (zzh().zzp(str)) {
            zzicVar.zzG();
        }
        if (zzh().zzq(str)) {
            String strZzD = zzicVar.zzD();
            if (!TextUtils.isEmpty(strZzD) && (iIndexOf = strZzD.indexOf(".")) != -1) {
                zzicVar.zzE(strZzD.substring(0, iIndexOf));
            }
        }
        if (zzh().zzr(str) && (iZzx = zzpk.zzx(zzicVar, "_id")) != -1) {
            zzicVar.zzr(iZzx);
        }
        if (zzh().zzs(str)) {
            zzicVar.zzan();
        }
        if (zzh().zzt(str)) {
            zzicVar.zzX();
            if (zzB(str).zzo(zzjk.ANALYTICS_STORAGE)) {
                Map map = this.zzE;
                zzpd zzpdVar = (zzpd) map.get(str);
                if (zzpdVar == null || zzpdVar.zzb + zzd().zzl(str, zzfy.zzak) < zzaZ().elapsedRealtime()) {
                    zzpdVar = new zzpd(this, (byte[]) null);
                    map.put(str, zzpdVar);
                }
                zzicVar.zzax(zzpdVar.zza);
            }
        }
        if (zzh().zzu(str)) {
            zzicVar.zzav();
        }
    }

    final void zzT(com.google.android.gms.internal.measurement.zzic zzicVar, zzpc zzpcVar) {
        for (int i = 0; i < zzicVar.zzc(); i++) {
            com.google.android.gms.internal.measurement.zzhr zzhrVar = (com.google.android.gms.internal.measurement.zzhr) zzicVar.zzd(i).zzcl();
            Iterator it = zzhrVar.zza().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if ("_c".equals(((com.google.android.gms.internal.measurement.zzhw) it.next()).zzb())) {
                    if (zzpcVar.zza.zzar() >= zzd().zzm(zzpcVar.zza.zzA(), zzfy.zzal)) {
                        int iZzm = zzd().zzm(zzpcVar.zza.zzA(), zzfy.zzay);
                        String strZzaw = null;
                        if (iZzm <= 0) {
                            if (zzd().zzp(zzpcVar.zza.zzA(), zzfy.zzaR)) {
                                strZzaw = zzt().zzaw();
                                zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
                                zzhvVarZzn.zzb("_tu");
                                zzhvVarZzn.zzd(strZzaw);
                                zzhrVar.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn.zzbc());
                            }
                            zzhv zzhvVarZzn2 = com.google.android.gms.internal.measurement.zzhw.zzn();
                            zzhvVarZzn2.zzb("_tr");
                            zzhvVarZzn2.zzf(1L);
                            zzhrVar.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn2.zzbc());
                            zzoh zzohVarZzf = zzp().zzf(zzpcVar.zza.zzA(), zzicVar, zzhrVar, strZzaw);
                            if (zzohVarZzf != null) {
                                zzaV().zzk().zzc("Generated trigger URI. appId, uri", zzpcVar.zza.zzA(), zzohVarZzf.zza);
                                zzj().zzY(zzpcVar.zza.zzA(), zzohVarZzf);
                                Deque deque = this.zzr;
                                if (!deque.contains(zzpcVar.zza.zzA())) {
                                    deque.add(zzpcVar.zza.zzA());
                                }
                            }
                        } else if (zzj().zzw(zzC(), zzpcVar.zza.zzA(), false, false, false, false, false, false, true).zzg > iZzm) {
                            zzhv zzhvVarZzn3 = com.google.android.gms.internal.measurement.zzhw.zzn();
                            zzhvVarZzn3.zzb("_tnr");
                            zzhvVarZzn3.zzf(1L);
                            zzhrVar.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn3.zzbc());
                        } else {
                            if (zzd().zzp(zzpcVar.zza.zzA(), zzfy.zzaR)) {
                                strZzaw = zzt().zzaw();
                                zzhv zzhvVarZzn4 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                zzhvVarZzn4.zzb("_tu");
                                zzhvVarZzn4.zzd(strZzaw);
                                zzhrVar.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn4.zzbc());
                            }
                            zzhv zzhvVarZzn5 = com.google.android.gms.internal.measurement.zzhw.zzn();
                            zzhvVarZzn5.zzb("_tr");
                            zzhvVarZzn5.zzf(1L);
                            zzhrVar.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn5.zzbc());
                            zzoh zzohVarZzf2 = zzp().zzf(zzpcVar.zza.zzA(), zzicVar, zzhrVar, strZzaw);
                            if (zzohVarZzf2 != null) {
                                zzaV().zzk().zzc("Generated trigger URI. appId, uri", zzpcVar.zza.zzA(), zzohVarZzf2.zza);
                                zzj().zzY(zzpcVar.zza.zzA(), zzohVarZzf2);
                                Deque deque2 = this.zzr;
                                if (!deque2.contains(zzpcVar.zza.zzA())) {
                                    deque2.add(zzpcVar.zza.zzA());
                                }
                            }
                        }
                    }
                    zzicVar.zze(i, (com.google.android.gms.internal.measurement.zzhs) zzhrVar.zzbc());
                }
            }
        }
    }

    final void zzU(String str, zzhv zzhvVar, Bundle bundle, String str2) {
        List listListOf = CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"});
        long jZzf = (zzpp.zzZ(zzhvVar.zza()) || zzpp.zzZ(str)) ? zzd().zzf(str2, true) : zzd().zze(str2, true);
        long jCodePointCount = zzhvVar.zzc().codePointCount(0, zzhvVar.zzc().length());
        zzpp zzppVarZzt = zzt();
        String strZza = zzhvVar.zza();
        zzd();
        String strZzC = zzppVarZzt.zzC(strZza, 40, true);
        if (jCodePointCount <= jZzf || listListOf.contains(zzhvVar.zza())) {
            return;
        }
        if ("_ev".equals(zzhvVar.zza())) {
            bundle.putString("_ev", zzt().zzC(zzhvVar.zzc(), zzd().zzf(str2, true), true));
            return;
        }
        zzaV().zzh().zzc("Param value is too long; discarded. Name, value length", strZzC, Long.valueOf(jCodePointCount));
        if (bundle.getLong("_err") == 0) {
            bundle.putLong("_err", 4L);
            if (bundle.getString("_ev") == null) {
                bundle.putString("_ev", strZzC);
                bundle.putLong("_el", jCodePointCount);
            }
        }
        bundle.remove(zzhvVar.zza());
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x0071 A[Catch: all -> 0x0016, TryCatch #2 {all -> 0x0016, blocks: (B:119:0x0013, B:123:0x001b, B:131:0x0034, B:136:0x0080, B:135:0x0071, B:137:0x008c, B:139:0x00a3, B:142:0x00b6, B:144:0x00c4, B:146:0x00e4, B:188:0x021f, B:190:0x0232, B:192:0x023c, B:200:0x025c, B:194:0x0242, B:196:0x024c, B:198:0x0252, B:199:0x0256, B:201:0x025f, B:202:0x0266, B:145:0x00d7, B:203:0x0267), top: B:210:0x0013, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x008c A[Catch: all -> 0x0016, PHI: r0
  0x008c: PHI (r0v2 int) = (r0v0 int), (r0v37 int) binds: [B:124:0x0027, B:130:0x0032] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #2 {all -> 0x0016, blocks: (B:119:0x0013, B:123:0x001b, B:131:0x0034, B:136:0x0080, B:135:0x0071, B:137:0x008c, B:139:0x00a3, B:142:0x00b6, B:144:0x00c4, B:146:0x00e4, B:188:0x021f, B:190:0x0232, B:192:0x023c, B:200:0x025c, B:194:0x0242, B:196:0x024c, B:198:0x0252, B:199:0x0256, B:201:0x025f, B:202:0x0266, B:145:0x00d7, B:203:0x0267), top: B:210:0x0013, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x00c4 A[Catch: all -> 0x0016, SQLiteException -> 0x00b3, TryCatch #1 {SQLiteException -> 0x00b3, blocks: (B:139:0x00a3, B:142:0x00b6, B:144:0x00c4, B:146:0x00e4, B:188:0x021f, B:190:0x0232, B:192:0x023c, B:200:0x025c, B:194:0x0242, B:196:0x024c, B:198:0x0252, B:199:0x0256, B:201:0x025f, B:202:0x0266, B:145:0x00d7), top: B:209:0x00a3, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x00d7 A[Catch: all -> 0x0016, SQLiteException -> 0x00b3, TryCatch #1 {SQLiteException -> 0x00b3, blocks: (B:139:0x00a3, B:142:0x00b6, B:144:0x00c4, B:146:0x00e4, B:188:0x021f, B:190:0x0232, B:192:0x023c, B:200:0x025c, B:194:0x0242, B:196:0x024c, B:198:0x0252, B:199:0x0256, B:201:0x025f, B:202:0x0266, B:145:0x00d7), top: B:209:0x00a3, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x00fc A[Catch: all -> 0x0155, TryCatch #0 {all -> 0x0155, blocks: (B:147:0x00eb, B:148:0x00f4, B:150:0x00fc, B:152:0x0113, B:156:0x013d, B:158:0x0147, B:162:0x0158, B:163:0x015d, B:165:0x0163, B:167:0x017a, B:169:0x019f, B:171:0x01ba, B:173:0x01dd, B:174:0x01ee, B:175:0x01f2, B:177:0x01f8, B:178:0x01ff, B:181:0x020c, B:183:0x0210, B:186:0x0217, B:187:0x0218), top: B:208:0x00eb, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0163 A[Catch: all -> 0x0155, TryCatch #0 {all -> 0x0155, blocks: (B:147:0x00eb, B:148:0x00f4, B:150:0x00fc, B:152:0x0113, B:156:0x013d, B:158:0x0147, B:162:0x0158, B:163:0x015d, B:165:0x0163, B:167:0x017a, B:169:0x019f, B:171:0x01ba, B:173:0x01dd, B:174:0x01ee, B:175:0x01f2, B:177:0x01f8, B:178:0x01ff, B:181:0x020c, B:183:0x0210, B:186:0x0217, B:187:0x0218), top: B:208:0x00eb, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x01ba A[Catch: all -> 0x0155, TryCatch #0 {all -> 0x0155, blocks: (B:147:0x00eb, B:148:0x00f4, B:150:0x00fc, B:152:0x0113, B:156:0x013d, B:158:0x0147, B:162:0x0158, B:163:0x015d, B:165:0x0163, B:167:0x017a, B:169:0x019f, B:171:0x01ba, B:173:0x01dd, B:174:0x01ee, B:175:0x01f2, B:177:0x01f8, B:178:0x01ff, B:181:0x020c, B:183:0x0210, B:186:0x0217, B:187:0x0218), top: B:208:0x00eb, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01f8 A[Catch: all -> 0x0155, TRY_LEAVE, TryCatch #0 {all -> 0x0155, blocks: (B:147:0x00eb, B:148:0x00f4, B:150:0x00fc, B:152:0x0113, B:156:0x013d, B:158:0x0147, B:162:0x0158, B:163:0x015d, B:165:0x0163, B:167:0x017a, B:169:0x019f, B:171:0x01ba, B:173:0x01dd, B:174:0x01ee, B:175:0x01f2, B:177:0x01f8, B:178:0x01ff, B:181:0x020c, B:183:0x0210, B:186:0x0217, B:187:0x0218), top: B:208:0x00eb, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0242 A[Catch: all -> 0x0016, SQLiteException -> 0x00b3, TryCatch #1 {SQLiteException -> 0x00b3, blocks: (B:139:0x00a3, B:142:0x00b6, B:144:0x00c4, B:146:0x00e4, B:188:0x021f, B:190:0x0232, B:192:0x023c, B:200:0x025c, B:194:0x0242, B:196:0x024c, B:198:0x0252, B:199:0x0256, B:201:0x025f, B:202:0x0266, B:145:0x00d7), top: B:209:0x00a3, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0256 A[Catch: all -> 0x0016, SQLiteException -> 0x00b3, TryCatch #1 {SQLiteException -> 0x00b3, blocks: (B:139:0x00a3, B:142:0x00b6, B:144:0x00c4, B:146:0x00e4, B:188:0x021f, B:190:0x0232, B:192:0x023c, B:200:0x025c, B:194:0x0242, B:196:0x024c, B:198:0x0252, B:199:0x0256, B:201:0x025f, B:202:0x0266, B:145:0x00d7), top: B:209:0x00a3, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzV(boolean r19, int r20, java.lang.Throwable r21, byte[] r22, java.lang.String r23, java.util.List r24) {
        /*
            Method dump skipped, instruction units count: 669
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzV(boolean, int, java.lang.Throwable, byte[], java.lang.String, java.util.List):void");
    }

    final void zzW(zzh zzhVar) {
        zzaW().zzg();
        if (TextUtils.isEmpty(zzhVar.zzf())) {
            zzX((String) Preconditions.checkNotNull(zzhVar.zzc()), Opcodes.SUB_DOUBLE_2ADDR, null, null, null);
            return;
        }
        String str = (String) Preconditions.checkNotNull(zzhVar.zzc());
        zzaV().zzk().zzb("Fetching remote configuration", str);
        com.google.android.gms.internal.measurement.zzgl zzglVarZzb = zzh().zzb(str);
        String strZzd = zzh().zzd(str);
        ArrayMap arrayMap = null;
        if (zzglVarZzb != null) {
            if (!TextUtils.isEmpty(strZzd)) {
                arrayMap = new ArrayMap();
                arrayMap.put("If-Modified-Since", strZzd);
            }
            String strZze = zzh().zze(str);
            if (!TextUtils.isEmpty(strZze)) {
                if (arrayMap == null) {
                    arrayMap = new ArrayMap();
                }
                arrayMap.put("If-None-Match", strZze);
            }
        }
        this.zzu = true;
        zzi().zzd(zzhVar, arrayMap, new zzgw() { // from class: com.google.android.gms.measurement.internal.zzpf
            @Override // com.google.android.gms.measurement.internal.zzgw
            public final /* synthetic */ void zza(String str2, int i, Throwable th, byte[] bArr, Map map) {
                this.zza.zzX(str2, i, th, bArr, map);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzX(java.lang.String r7, int r8, java.lang.Throwable r9, byte[] r10, java.util.Map r11) {
        /*
            Method dump skipped, instruction units count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzX(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    final void zzY(Runnable runnable) {
        zzaW().zzg();
        if (this.zzq == null) {
            this.zzq = new ArrayList();
        }
        this.zzq.add(runnable);
    }

    final void zzZ() {
        zzaW().zzg();
        zzu();
        if (this.zzp) {
            return;
        }
        this.zzp = true;
        if (zzaa()) {
            FileChannel fileChannel = this.zzy;
            zzaW().zzg();
            int i = 0;
            if (fileChannel == null || !fileChannel.isOpen()) {
                zzaV().zzb().zza("Bad channel to read from");
            } else {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
                try {
                    fileChannel.position(0L);
                    int i2 = fileChannel.read(byteBufferAllocate);
                    if (i2 == 4) {
                        byteBufferAllocate.flip();
                        i = byteBufferAllocate.getInt();
                    } else if (i2 != -1) {
                        zzaV().zze().zzb("Unexpected data length. Bytes read", Integer.valueOf(i2));
                    }
                } catch (IOException e) {
                    zzaV().zzb().zzb("Failed to read from channel", e);
                }
            }
            int iZzm = this.zzn.zzv().zzm();
            zzaW().zzg();
            if (i > iZzm) {
                zzaV().zzb().zzc("Panic: can't downgrade version. Previous, current version", Integer.valueOf(i), Integer.valueOf(iZzm));
                return;
            }
            if (i < iZzm) {
                FileChannel fileChannel2 = this.zzy;
                zzaW().zzg();
                if (fileChannel2 == null || !fileChannel2.isOpen()) {
                    zzaV().zzb().zza("Bad channel to read from");
                } else {
                    ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(4);
                    byteBufferAllocate2.putInt(iZzm);
                    byteBufferAllocate2.flip();
                    try {
                        fileChannel2.truncate(0L);
                        fileChannel2.write(byteBufferAllocate2);
                        fileChannel2.force(true);
                        if (fileChannel2.size() != 4) {
                            zzaV().zzb().zzb("Error writing to channel. Bytes written", Long.valueOf(fileChannel2.size()));
                        }
                        zzaV().zzk().zzc("Storage version upgraded. Previous, current version", Integer.valueOf(i), Integer.valueOf(iZzm));
                        return;
                    } catch (IOException e2) {
                        zzaV().zzb().zzb("Failed to write to channel", e2);
                    }
                }
                zzaV().zzb().zzc("Storage version upgrade failed. Previous, current version", Integer.valueOf(i), Integer.valueOf(iZzm));
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzjg
    public final zzae zzaU() {
        return this.zzn.zzaU();
    }

    @Override // com.google.android.gms.measurement.internal.zzjg
    public final zzgu zzaV() {
        return ((zzic) Preconditions.checkNotNull(this.zzn)).zzaV();
    }

    @Override // com.google.android.gms.measurement.internal.zzjg
    public final zzhz zzaW() {
        return ((zzic) Preconditions.checkNotNull(this.zzn)).zzaW();
    }

    @Override // com.google.android.gms.measurement.internal.zzjg
    public final Context zzaY() {
        return this.zzn.zzaY();
    }

    @Override // com.google.android.gms.measurement.internal.zzjg
    public final Clock zzaZ() {
        return ((zzic) Preconditions.checkNotNull(this.zzn)).zzaZ();
    }

    final boolean zzaa() {
        zzaW().zzg();
        FileLock fileLock = this.zzx;
        if (fileLock != null && fileLock.isValid()) {
            zzaV().zzk().zza("Storage concurrent access okay");
            return true;
        }
        this.zze.zzu.zzc();
        File filesDir = this.zzn.zzaY().getFilesDir();
        com.google.android.gms.internal.measurement.zzbv.zza();
        int i = com.google.android.gms.internal.measurement.zzca.$r8$clinit;
        try {
            FileChannel channel = new RandomAccessFile(new File(new File(filesDir, "google_app_measurement.db").getPath()), "rw").getChannel();
            this.zzy = channel;
            FileLock fileLockTryLock = channel.tryLock();
            this.zzx = fileLockTryLock;
            if (fileLockTryLock != null) {
                zzaV().zzk().zza("Storage concurrent access okay");
                return true;
            }
            zzaV().zzb().zza("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            zzaV().zzb().zzb("Failed to acquire storage lock", e);
            return false;
        } catch (IOException e2) {
            zzaV().zzb().zzb("Failed to access storage lock file", e2);
            return false;
        } catch (OverlappingFileLockException e3) {
            zzaV().zze().zzb("Storage lock already acquired", e3);
            return false;
        }
    }

    final void zzab(zzr zzrVar) {
        if (this.zzz != null) {
            ArrayList arrayList = new ArrayList();
            this.zzA = arrayList;
            arrayList.addAll(this.zzz);
        }
        zzav zzavVarZzj = zzj();
        String str = (String) Preconditions.checkNotNull(zzrVar.zza);
        Preconditions.checkNotEmpty(str);
        zzavVarZzj.zzg();
        zzavVarZzj.zzaw();
        try {
            SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj.zze();
            String[] strArr = {str};
            int iDelete = sQLiteDatabaseZze.delete("apps", "app_id=?", strArr) + sQLiteDatabaseZze.delete("events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("events_snapshot", "app_id=?", strArr) + sQLiteDatabaseZze.delete("user_attributes", "app_id=?", strArr) + sQLiteDatabaseZze.delete("conditional_properties", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events_metadata", "app_id=?", strArr) + sQLiteDatabaseZze.delete("queue", "app_id=?", strArr) + sQLiteDatabaseZze.delete("audience_filter_values", "app_id=?", strArr) + sQLiteDatabaseZze.delete("main_event_params", "app_id=?", strArr) + sQLiteDatabaseZze.delete("default_event_params", "app_id=?", strArr) + sQLiteDatabaseZze.delete("trigger_uris", "app_id=?", strArr) + sQLiteDatabaseZze.delete("upload_queue", "app_id=?", strArr);
            com.google.android.gms.internal.measurement.zzpo.zza();
            zzic zzicVar = zzavVarZzj.zzu;
            if (zzicVar.zzc().zzp(null, zzfy.zzbh)) {
                iDelete += sQLiteDatabaseZze.delete("no_data_mode_events", "app_id=?", strArr);
            }
            if (iDelete > 0) {
                zzicVar.zzaV().zzk().zzc("Reset analytics data. app, records", str, Integer.valueOf(iDelete));
            }
        } catch (SQLiteException e) {
            zzavVarZzj.zzu.zzaV().zzb().zzc("Error resetting analytics data. appId, error", zzgu.zzl(str), e);
        }
        if (zzrVar.zzh) {
            zzah(zzrVar);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x00d1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void zzac(com.google.android.gms.measurement.internal.zzpl r22, com.google.android.gms.measurement.internal.zzr r23) {
        /*
            Method dump skipped, instruction units count: 496
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzac(com.google.android.gms.measurement.internal.zzpl, com.google.android.gms.measurement.internal.zzr):void");
    }

    final void zzad(String str, zzr zzrVar) {
        zzaW().zzg();
        zzu();
        if (zzaR(zzrVar)) {
            if (!zzrVar.zzh) {
                zzao(zzrVar);
                return;
            }
            Boolean boolZzaT = zzaT(zzrVar);
            if ("_npa".equals(str) && boolZzaT != null) {
                zzaV().zzj().zza("Falling back to manifest metadata value for ad personalization");
                zzac(new zzpl("_npa", zzaZ().currentTimeMillis(), Long.valueOf(true != boolZzaT.booleanValue() ? 0L : 1L), "auto"), zzrVar);
                return;
            }
            zzgs zzgsVarZzj = zzaV().zzj();
            zzic zzicVar = this.zzn;
            zzgsVarZzj.zzb("Removing user property", zzicVar.zzl().zzc(str));
            zzj().zzb();
            try {
                zzao(zzrVar);
                if ("_id".equals(str)) {
                    zzj().zzk((String) Preconditions.checkNotNull(zzrVar.zza), "_lair");
                }
                zzj().zzk((String) Preconditions.checkNotNull(zzrVar.zza), str);
                zzj().zzc();
                zzaV().zzj().zzb("User property removed", zzicVar.zzl().zzc(str));
                zzj().zzd();
            } catch (Throwable th) {
                zzj().zzd();
                throw th;
            }
        }
    }

    final void zzae() {
        this.zzs++;
    }

    final void zzaf() {
        this.zzt++;
    }

    final zzic zzag() {
        return this.zzn;
    }

    final void zzah(zzr zzrVar) {
        long j;
        long j2;
        zzbc zzbcVarZzf;
        boolean z;
        String str;
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        long j3;
        boolean z2;
        zzaW().zzg();
        zzu();
        Preconditions.checkNotNull(zzrVar);
        String str2 = zzrVar.zza;
        Preconditions.checkNotEmpty(str2);
        if (zzaR(zzrVar)) {
            zzh zzhVarZzu = zzj().zzu(str2);
            if (zzhVarZzu != null && TextUtils.isEmpty(zzhVarZzu.zzf()) && !TextUtils.isEmpty(zzrVar.zzb)) {
                zzhVarZzu.zzI(0L);
                zzj().zzv(zzhVarZzu, false, false);
                zzh().zzh(str2);
            }
            if (!zzrVar.zzh) {
                zzao(zzrVar);
                return;
            }
            long jCurrentTimeMillis = zzrVar.zzl;
            if (jCurrentTimeMillis == 0) {
                jCurrentTimeMillis = zzaZ().currentTimeMillis();
            }
            long j4 = jCurrentTimeMillis;
            int i = zzrVar.zzm;
            if (i != 0 && i != 1) {
                zzaV().zze().zzc("Incorrect app type, assuming installed app. appId, appType", zzgu.zzl(str2), Integer.valueOf(i));
                i = 0;
            }
            zzj().zzb();
            try {
                zzpn zzpnVarZzm = zzj().zzm(str2, "_npa");
                Boolean boolZzaT = zzaT(zzrVar);
                if (zzpnVarZzm != null && !"auto".equals(zzpnVarZzm.zzb)) {
                    j = j4;
                    j2 = 1;
                } else if (boolZzaT != null) {
                    zzpl zzplVar = new zzpl("_npa", j4, Long.valueOf(true != boolZzaT.booleanValue() ? 0L : 1L), "auto");
                    j2 = 1;
                    j = j4;
                    if (zzpnVarZzm == null || !zzpnVarZzm.zze.equals(zzplVar.zzd)) {
                        zzac(zzplVar, zzrVar);
                    }
                } else {
                    j = j4;
                    j2 = 1;
                    if (zzpnVarZzm != null) {
                        zzad("_npa", zzrVar);
                    }
                }
                if (zzd().zzp(null, zzfy.zzbb)) {
                    zzan(zzrVar, zzrVar.zzD);
                } else {
                    zzan(zzrVar, j);
                }
                zzao(zzrVar);
                if (i == 0) {
                    zzbcVarZzf = zzj().zzf(str2, "_f");
                    z = false;
                } else {
                    zzbcVarZzf = zzj().zzf(str2, "_v");
                    z = true;
                }
                if (zzbcVarZzf == null) {
                    long j5 = ((j / 3600000) + j2) * 3600000;
                    if (z) {
                        long j6 = j;
                        zzac(new zzpl("_fvt", j6, Long.valueOf(j5), "auto"), zzrVar);
                        zzaW().zzg();
                        zzu();
                        Bundle bundle = new Bundle();
                        bundle.putLong("_c", 1L);
                        bundle.putLong("_r", 1L);
                        bundle.putLong("_et", 1L);
                        if (zzrVar.zzo) {
                            bundle.putLong("_dac", 1L);
                        }
                        if (zzd().zzp(null, zzfy.zzbj)) {
                            bundle.putLong("_elt", zzaZ().currentTimeMillis());
                        }
                        zzE(new zzbg("_v", new zzbe(bundle), "auto", j6), zzrVar);
                    } else {
                        Long lValueOf = Long.valueOf(j5);
                        long j7 = j;
                        zzac(new zzpl("_fot", j7, lValueOf, "auto"), zzrVar);
                        zzaW().zzg();
                        zzhk zzhkVar = (zzhk) Preconditions.checkNotNull(this.zzm);
                        if (str2 == null || str2.isEmpty()) {
                            str = "_elt";
                            zzhkVar.zza.zzaV().zzf().zza("Install Referrer Reporter was called with invalid app package name");
                        } else {
                            zzic zzicVar = zzhkVar.zza;
                            zzicVar.zzaW().zzg();
                            if (zzhkVar.zza()) {
                                zzhj zzhjVar = new zzhj(zzhkVar, str2);
                                zzicVar.zzaW().zzg();
                                Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
                                str = "_elt";
                                intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
                                PackageManager packageManager = zzicVar.zzaY().getPackageManager();
                                if (packageManager == null) {
                                    zzicVar.zzaV().zzf().zza("Failed to obtain Package Manager to verify binding conditions for Install Referrer");
                                } else {
                                    List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
                                    if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
                                        zzicVar.zzaV().zzi().zza("Play Service for fetching Install Referrer is unavailable on device");
                                    } else {
                                        ServiceInfo serviceInfo = listQueryIntentServices.get(0).serviceInfo;
                                        if (serviceInfo != null) {
                                            String str3 = serviceInfo.packageName;
                                            if (serviceInfo.name != null && "com.android.vending".equals(str3) && zzhkVar.zza()) {
                                                try {
                                                    zzicVar.zzaV().zzk().zzb("Install Referrer Service is", ConnectionTracker.getInstance().bindService(zzicVar.zzaY(), new Intent(intent), zzhjVar, 1) ? "available" : "not available");
                                                } catch (RuntimeException e) {
                                                    zzhkVar.zza.zzaV().zzb().zzb("Exception occurred while binding to Install Referrer Service", e.getMessage());
                                                }
                                            } else {
                                                zzicVar.zzaV().zze().zza("Play Store version 8.3.73 or higher required for Install Referrer");
                                            }
                                        }
                                    }
                                }
                            } else {
                                zzicVar.zzaV().zzi().zza("Install Referrer Reporter is not available");
                                str = "_elt";
                            }
                        }
                        zzaW().zzg();
                        zzu();
                        Bundle bundle2 = new Bundle();
                        long j8 = j2;
                        bundle2.putLong("_c", j8);
                        bundle2.putLong("_r", j8);
                        bundle2.putLong("_uwa", 0L);
                        bundle2.putLong("_pfo", 0L);
                        bundle2.putLong("_sys", 0L);
                        bundle2.putLong("_sysu", 0L);
                        bundle2.putLong("_et", j8);
                        if (zzrVar.zzo) {
                            bundle2.putLong("_dac", j8);
                        }
                        String str4 = (String) Preconditions.checkNotNull(zzrVar.zza);
                        zzav zzavVarZzj = zzj();
                        Preconditions.checkNotEmpty(str4);
                        zzavVarZzj.zzg();
                        zzavVarZzj.zzaw();
                        long jZzN = zzavVarZzj.zzN(str4, "first_open_count");
                        zzic zzicVar2 = this.zzn;
                        if (zzicVar2.zzaY().getPackageManager() == null) {
                            zzaV().zzb().zzb("PackageManager is null, first open report might be inaccurate. appId", zzgu.zzl(str4));
                        } else {
                            try {
                                packageInfo = Wrappers.packageManager(zzicVar2.zzaY()).getPackageInfo(str4, 0);
                            } catch (PackageManager.NameNotFoundException e2) {
                                zzaV().zzb().zzc("Package info is null, first open report might be inaccurate. appId", zzgu.zzl(str4), e2);
                                packageInfo = null;
                            }
                            if (packageInfo != null) {
                                long j9 = packageInfo.firstInstallTime;
                                if (j9 != 0) {
                                    if (j9 != packageInfo.lastUpdateTime) {
                                        if (!zzd().zzp(null, zzfy.zzaI)) {
                                            bundle2.putLong("_uwa", 1L);
                                        } else if (jZzN == 0) {
                                            bundle2.putLong("_uwa", 1L);
                                            jZzN = 0;
                                        }
                                        z2 = false;
                                    } else {
                                        z2 = true;
                                    }
                                    zzac(new zzpl("_fi", j7, Long.valueOf(true != z2 ? 0L : 1L), "auto"), zzrVar);
                                }
                            }
                            try {
                                applicationInfo = Wrappers.packageManager(this.zzn.zzaY()).getApplicationInfo(str4, 0);
                            } catch (PackageManager.NameNotFoundException e3) {
                                zzaV().zzb().zzc("Application info is null, first open report might be inaccurate. appId", zzgu.zzl(str4), e3);
                                applicationInfo = null;
                            }
                            if (applicationInfo != null) {
                                if ((applicationInfo.flags & 1) != 0) {
                                    j3 = 1;
                                    bundle2.putLong("_sys", 1L);
                                } else {
                                    j3 = 1;
                                }
                                if ((applicationInfo.flags & 128) != 0) {
                                    bundle2.putLong("_sysu", j3);
                                }
                            }
                        }
                        if (jZzN >= 0) {
                            bundle2.putLong("_pfo", jZzN);
                        }
                        if (zzd().zzp(null, zzfy.zzbj)) {
                            bundle2.putLong(str, zzaZ().currentTimeMillis());
                        }
                        zzE(new zzbg("_f", new zzbe(bundle2), "auto", j7), zzrVar);
                    }
                } else {
                    long j10 = j;
                    if (zzrVar.zzi) {
                        zzE(new zzbg("_cd", new zzbe(new Bundle()), "auto", j10), zzrVar);
                    }
                }
                zzj().zzc();
                zzj().zzd();
            } catch (Throwable th) {
                zzj().zzd();
                throw th;
            }
        }
    }

    final void zzai(zzr zzrVar) throws Throwable {
        zzaW().zzg();
        zzu();
        Preconditions.checkNotNull(zzrVar);
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        int i = 0;
        if (zzd().zzp(null, zzfy.zzaz)) {
            long jCurrentTimeMillis = zzaZ().currentTimeMillis();
            int iZzm = zzd().zzm(null, zzfy.zzai);
            zzd();
            long jZzF = jCurrentTimeMillis - zzal.zzF();
            while (i < iZzm && zzaG(null, jZzF)) {
                i++;
            }
        } else {
            zzd();
            long jZzH = zzal.zzH();
            while (i < jZzH && zzaG(str, 0L)) {
                i++;
            }
        }
        if (zzd().zzp(null, zzfy.zzaA)) {
            zzaW().zzg();
            zzav();
        }
        if (this.zzl.zzc(str, com.google.android.gms.internal.measurement.zzin.zzb(zzrVar.zzE))) {
            zzaV().zzk().zzb("[sgtm] Going background, trigger client side upload. appId", str);
            zzN(str, zzaZ().currentTimeMillis());
        }
    }

    final void zzaj(zzah zzahVar) {
        zzr zzrVarZzaO = zzaO((String) Preconditions.checkNotNull(zzahVar.zza));
        if (zzrVarZzaO != null) {
            zzak(zzahVar, zzrVarZzaO);
        }
    }

    final void zzak(zzah zzahVar, zzr zzrVar) {
        Preconditions.checkNotNull(zzahVar);
        Preconditions.checkNotEmpty(zzahVar.zza);
        Preconditions.checkNotNull(zzahVar.zzb);
        Preconditions.checkNotNull(zzahVar.zzc);
        Preconditions.checkNotEmpty(zzahVar.zzc.zzb);
        zzaW().zzg();
        zzu();
        if (zzaR(zzrVar)) {
            if (!zzrVar.zzh) {
                zzao(zzrVar);
                return;
            }
            zzah zzahVar2 = new zzah(zzahVar);
            boolean z = false;
            zzahVar2.zze = false;
            zzj().zzb();
            try {
                zzah zzahVarZzq = zzj().zzq((String) Preconditions.checkNotNull(zzahVar2.zza), zzahVar2.zzc.zzb);
                if (zzahVarZzq != null && !zzahVarZzq.zzb.equals(zzahVar2.zzb)) {
                    zzaV().zze().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzb, zzahVarZzq.zzb);
                }
                if (zzahVarZzq != null && zzahVarZzq.zze) {
                    zzahVar2.zzb = zzahVarZzq.zzb;
                    zzahVar2.zzd = zzahVarZzq.zzd;
                    zzahVar2.zzh = zzahVarZzq.zzh;
                    zzahVar2.zzf = zzahVarZzq.zzf;
                    zzahVar2.zzi = zzahVarZzq.zzi;
                    zzahVar2.zze = true;
                    zzpl zzplVar = zzahVar2.zzc;
                    zzahVar2.zzc = new zzpl(zzplVar.zzb, zzahVarZzq.zzc.zzc, zzplVar.zza(), zzahVarZzq.zzc.zzf);
                } else if (TextUtils.isEmpty(zzahVar2.zzf)) {
                    zzpl zzplVar2 = zzahVar2.zzc;
                    zzahVar2.zzc = new zzpl(zzplVar2.zzb, zzahVar2.zzd, zzplVar2.zza(), zzahVar2.zzc.zzf);
                    zzahVar2.zze = true;
                    z = true;
                }
                if (zzahVar2.zze) {
                    zzpl zzplVar3 = zzahVar2.zzc;
                    zzpn zzpnVar = new zzpn((String) Preconditions.checkNotNull(zzahVar2.zza), zzahVar2.zzb, zzplVar3.zzb, zzplVar3.zzc, Preconditions.checkNotNull(zzplVar3.zza()));
                    if (zzj().zzl(zzpnVar)) {
                        zzaV().zzj().zzd("User property updated immediately", zzahVar2.zza, this.zzn.zzl().zzc(zzpnVar.zzc), zzpnVar.zze);
                    } else {
                        zzaV().zzb().zzd("(2)Too many active user properties, ignoring", zzgu.zzl(zzahVar2.zza), this.zzn.zzl().zzc(zzpnVar.zzc), zzpnVar.zze);
                    }
                    if (z && zzahVar2.zzi != null) {
                        zzH(new zzbg(zzahVar2.zzi, zzahVar2.zzd), zzrVar);
                    }
                }
                if (zzj().zzp(zzahVar2)) {
                    zzaV().zzj().zzd("Conditional property added", zzahVar2.zza, this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzc.zza());
                } else {
                    zzaV().zzb().zzd("Too many conditional properties, ignoring", zzgu.zzl(zzahVar2.zza), this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzc.zza());
                }
                zzj().zzc();
                zzj().zzd();
            } catch (Throwable th) {
                zzj().zzd();
                throw th;
            }
        }
    }

    final void zzal(zzah zzahVar) {
        zzr zzrVarZzaO = zzaO((String) Preconditions.checkNotNull(zzahVar.zza));
        if (zzrVarZzaO != null) {
            zzam(zzahVar, zzrVarZzaO);
        }
    }

    final void zzam(zzah zzahVar, zzr zzrVar) {
        Preconditions.checkNotNull(zzahVar);
        Preconditions.checkNotEmpty(zzahVar.zza);
        Preconditions.checkNotNull(zzahVar.zzc);
        Preconditions.checkNotEmpty(zzahVar.zzc.zzb);
        zzaW().zzg();
        zzu();
        if (zzaR(zzrVar)) {
            if (!zzrVar.zzh) {
                zzao(zzrVar);
                return;
            }
            zzj().zzb();
            try {
                zzao(zzrVar);
                String str = (String) Preconditions.checkNotNull(zzahVar.zza);
                zzah zzahVarZzq = zzj().zzq(str, zzahVar.zzc.zzb);
                if (zzahVarZzq != null) {
                    zzaV().zzj().zzc("Removing conditional user property", zzahVar.zza, this.zzn.zzl().zzc(zzahVar.zzc.zzb));
                    zzj().zzr(str, zzahVar.zzc.zzb);
                    if (zzahVarZzq.zze) {
                        zzj().zzk(str, zzahVar.zzc.zzb);
                    }
                    zzbg zzbgVar = zzahVar.zzk;
                    if (zzbgVar != null) {
                        zzbe zzbeVar = zzbgVar.zzb;
                        zzH((zzbg) Preconditions.checkNotNull(zzt().zzac(str, ((zzbg) Preconditions.checkNotNull(zzbgVar)).zza, zzbeVar != null ? zzbeVar.zzf() : null, zzahVarZzq.zzb, zzbgVar.zzd, true, true)), zzrVar);
                    }
                } else {
                    zzaV().zze().zzc("Conditional user property doesn't exist", zzgu.zzl(zzahVar.zza), this.zzn.zzl().zzc(zzahVar.zzc.zzb));
                }
                zzj().zzc();
                zzj().zzd();
            } catch (Throwable th) {
                zzj().zzd();
                throw th;
            }
        }
    }

    final void zzan(zzr zzrVar, long j) {
        zzh zzhVarZzu = zzj().zzu((String) Preconditions.checkNotNull(zzrVar.zza));
        if (zzhVarZzu != null && zzt().zzB(zzrVar.zzb, zzhVarZzu.zzf())) {
            zzaV().zze().zzb("New GMP App Id passed in. Removing cached database data. appId", zzgu.zzl(zzhVarZzu.zzc()));
            zzav zzavVarZzj = zzj();
            String strZzc = zzhVarZzu.zzc();
            zzavVarZzj.zzaw();
            zzavVarZzj.zzg();
            Preconditions.checkNotEmpty(strZzc);
            try {
                SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj.zze();
                String[] strArr = {strZzc};
                int iDelete = sQLiteDatabaseZze.delete("events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("user_attributes", "app_id=?", strArr) + sQLiteDatabaseZze.delete("conditional_properties", "app_id=?", strArr) + sQLiteDatabaseZze.delete("apps", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events_metadata", "app_id=?", strArr) + sQLiteDatabaseZze.delete("event_filters", "app_id=?", strArr) + sQLiteDatabaseZze.delete("property_filters", "app_id=?", strArr) + sQLiteDatabaseZze.delete("audience_filter_values", "app_id=?", strArr) + sQLiteDatabaseZze.delete("consent_settings", "app_id=?", strArr) + sQLiteDatabaseZze.delete("default_event_params", "app_id=?", strArr) + sQLiteDatabaseZze.delete("trigger_uris", "app_id=?", strArr);
                com.google.android.gms.internal.measurement.zzpo.zza();
                zzic zzicVar = zzavVarZzj.zzu;
                if (zzicVar.zzc().zzp(null, zzfy.zzbh)) {
                    iDelete += sQLiteDatabaseZze.delete("no_data_mode_events", "app_id=?", strArr);
                }
                if (iDelete > 0) {
                    zzicVar.zzaV().zzk().zzc("Deleted application data. app, records", strZzc, Integer.valueOf(iDelete));
                }
            } catch (SQLiteException e) {
                zzavVarZzj.zzu.zzaV().zzb().zzc("Error deleting application data. appId, error", zzgu.zzl(strZzc), e);
            }
            zzhVarZzu = null;
        }
        if (zzhVarZzu != null) {
            boolean z = (zzhVarZzu.zzt() == -2147483648L || zzhVarZzu.zzt() == zzrVar.zzj) ? false : true;
            String strZzr = zzhVarZzu.zzr();
            if (z || ((zzhVarZzu.zzt() != -2147483648L || strZzr == null || strZzr.equals(zzrVar.zzc)) ? false : true)) {
                Bundle bundle = new Bundle();
                bundle.putString("_pv", strZzr);
                zzbg zzbgVar = new zzbg("_au", new zzbe(bundle), "auto", j);
                if (zzd().zzp(null, zzfy.zzbc)) {
                    zzE(zzbgVar, zzrVar);
                } else {
                    zzF(zzbgVar, zzrVar);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final com.google.android.gms.measurement.internal.zzh zzao(com.google.android.gms.measurement.internal.zzr r13) {
        /*
            Method dump skipped, instruction units count: 491
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzao(com.google.android.gms.measurement.internal.zzr):com.google.android.gms.measurement.internal.zzh");
    }

    final String zzap(zzr zzrVar) {
        try {
            return (String) zzaW().zzh(new zzoz(this, zzrVar)).get(30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzaV().zzb().zzc("Failed to get app instance id. appId", zzgu.zzl(zzrVar.zza), e);
            return null;
        }
    }

    final List zzaq(zzr zzrVar, Bundle bundle) {
        zzaW().zzg();
        zzqp.zza();
        zzal zzalVarZzd = zzd();
        String str = zzrVar.zza;
        if (!zzalVarZzd.zzp(str, zzfy.zzaP) || str == null) {
            return new ArrayList();
        }
        if (bundle != null) {
            int[] intArray = bundle.getIntArray("uriSources");
            long[] longArray = bundle.getLongArray("uriTimestamps");
            if (intArray != null) {
                if (longArray == null || longArray.length != intArray.length) {
                    zzaV().zzb().zza("Uri sources and timestamps do not match");
                } else {
                    for (int i = 0; i < intArray.length; i++) {
                        zzav zzavVarZzj = zzj();
                        int i2 = intArray[i];
                        long j = longArray[i];
                        Preconditions.checkNotEmpty(str);
                        zzavVarZzj.zzg();
                        zzavVarZzj.zzaw();
                        try {
                            int iDelete = zzavVarZzj.zze().delete("trigger_uris", "app_id=? and source=? and timestamp_millis<=?", new String[]{str, String.valueOf(i2), String.valueOf(j)});
                            zzgs zzgsVarZzk = zzavVarZzj.zzu.zzaV().zzk();
                            StringBuilder sb = new StringBuilder(String.valueOf(iDelete).length() + 46);
                            sb.append("Pruned ");
                            sb.append(iDelete);
                            sb.append(" trigger URIs. appId, source, timestamp");
                            zzgsVarZzk.zzd(sb.toString(), str, Integer.valueOf(i2), Long.valueOf(j));
                        } catch (SQLiteException e) {
                            zzavVarZzj.zzu.zzaV().zzb().zzc("Error pruning trigger URIs. appId", zzgu.zzl(str), e);
                        }
                    }
                }
            }
        }
        zzav zzavVarZzj2 = zzj();
        String str2 = zzrVar.zza;
        Preconditions.checkNotEmpty(str2);
        zzavVarZzj2.zzg();
        zzavVarZzj2.zzaw();
        List arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = zzavVarZzj2.zze().query("trigger_uris", new String[]{"trigger_uri", "timestamp_millis", "source"}, "app_id=?", new String[]{str2}, null, null, "rowid", null);
                if (cursorQuery.moveToFirst()) {
                    do {
                        String string = cursorQuery.getString(0);
                        if (string == null) {
                            string = _UrlKt.FRAGMENT_ENCODE_SET;
                        }
                        arrayList.add(new zzoh(string, cursorQuery.getLong(1), cursorQuery.getInt(2)));
                    } while (cursorQuery.moveToNext());
                }
            } catch (SQLiteException e2) {
                zzavVarZzj2.zzu.zzaV().zzb().zzc("Error querying trigger uris. appId", zzgu.zzl(str2), e2);
                arrayList = Collections.EMPTY_LIST;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    final void zzar(String str, zzaf zzafVar) {
        zzaW().zzg();
        zzu();
        zzav zzavVarZzj = zzj();
        long j = zzafVar.zza;
        zzpj zzpjVarZzB = zzavVarZzj.zzB(j);
        if (zzpjVarZzB == null) {
            zzaV().zze().zzc("[sgtm] Queued batch doesn't exist. appId, rowId", str, Long.valueOf(j));
            return;
        }
        String strZze = zzpjVarZzB.zze();
        if (zzafVar.zzb != zzlr.SUCCESS.zza()) {
            if (zzafVar.zzb == zzlr.BACKOFF.zza()) {
                Map map = this.zzF;
                zzpe zzpeVar = (zzpe) map.get(strZze);
                if (zzpeVar == null) {
                    zzpeVar = new zzpe(this);
                    map.put(strZze, zzpeVar);
                } else {
                    zzpeVar.zza();
                }
                zzaV().zzk().zzd("[sgtm] Putting sGTM server in backoff mode. appId, destination, nextRetryInSeconds", str, strZze, Long.valueOf((zzpeVar.zzc() - zzaZ().currentTimeMillis()) / 1000));
            }
            zzav zzavVarZzj2 = zzj();
            Long lValueOf = Long.valueOf(zzafVar.zza);
            zzavVarZzj2.zzK(lValueOf);
            zzaV().zzk().zzc("[sgtm] increased batch retry count after failed client upload. appId, rowId", str, lValueOf);
            return;
        }
        Map map2 = this.zzF;
        if (map2.containsKey(strZze)) {
            map2.remove(strZze);
        }
        zzav zzavVarZzj3 = zzj();
        Long lValueOf2 = Long.valueOf(j);
        zzavVarZzj3.zzE(lValueOf2);
        zzaV().zzk().zzc("[sgtm] queued batch deleted after successful client upload. appId, rowId", str, lValueOf2);
        long j2 = zzafVar.zzc;
        if (j2 > 0) {
            zzav zzavVarZzj4 = zzj();
            zzavVarZzj4.zzg();
            zzavVarZzj4.zzaw();
            Long lValueOf3 = Long.valueOf(j2);
            Preconditions.checkNotNull(lValueOf3);
            ContentValues contentValues = new ContentValues();
            contentValues.put("upload_type", Integer.valueOf(zzls.GOOGLE_SIGNAL.zza()));
            zzic zzicVar = zzavVarZzj4.zzu;
            contentValues.put("creation_timestamp", Long.valueOf(zzicVar.zzaZ().currentTimeMillis()));
            try {
                if (zzavVarZzj4.zze().update("upload_queue", contentValues, "rowid=? AND app_id=? AND upload_type=?", new String[]{String.valueOf(j2), str, String.valueOf(zzls.GOOGLE_SIGNAL_PENDING.zza())}) != 1) {
                    zzicVar.zzaV().zze().zzc("Google Signal pending batch not updated. appId, rowId", str, lValueOf3);
                }
                zzaV().zzk().zzc("[sgtm] queued Google Signal batch updated. appId, signalRowId", str, Long.valueOf(zzafVar.zzc));
                zzP(str);
            } catch (SQLiteException e) {
                zzavVarZzj4.zzu.zzaV().zzb().zzd("Failed to update google Signal pending batch. appid, rowId", str, Long.valueOf(j2), e);
                throw e;
            }
        }
    }

    final void zzas(boolean z) {
        zzaL();
    }

    public final void zzat(String str, zzlu zzluVar) {
        zzaW().zzg();
        String str2 = this.zzH;
        if (str2 == null || str2.equals(str) || zzluVar != null) {
            this.zzH = str;
            this.zzG = zzluVar;
        }
    }

    final /* synthetic */ void zzau(zzph zzphVar) {
        zzaW().zzg();
        this.zzm = new zzhk(this);
        zzav zzavVar = new zzav(this);
        zzavVar.zzax();
        this.zze = zzavVar;
        zzd().zza((zzak) Preconditions.checkNotNull(this.zzc));
        zznn zznnVar = new zznn(this);
        zznnVar.zzax();
        this.zzk = zznnVar;
        zzad zzadVar = new zzad(this);
        zzadVar.zzax();
        this.zzh = zzadVar;
        zzlp zzlpVar = new zzlp(this);
        zzlpVar.zzax();
        this.zzj = zzlpVar;
        zzok zzokVar = new zzok(this);
        zzokVar.zzax();
        this.zzg = zzokVar;
        this.zzf = new zzhb(this);
        if (this.zzs != this.zzt) {
            zzaV().zzb().zzc("Not all upload components initialized", Integer.valueOf(this.zzs), Integer.valueOf(this.zzt));
        }
        this.zzo.set(true);
        zzaV().zzk().zza("UploadController is now fully initialized");
    }

    final /* synthetic */ zzic zzax() {
        return this.zzn;
    }

    final /* synthetic */ Deque zzay() {
        return this.zzr;
    }

    final /* synthetic */ void zzaz(long j) {
        this.zzJ = j;
    }

    protected final void zzc() {
        zzaW().zzg();
        zzj().zzI();
        zzav zzavVarZzj = zzj();
        zzavVarZzj.zzg();
        zzavVarZzj.zzaw();
        if (zzavVarZzj.zzag()) {
            zzfx zzfxVar = zzfy.zzav;
            if (((Long) zzfxVar.zzb(null)).longValue() != 0) {
                SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj.zze();
                zzic zzicVar = zzavVarZzj.zzu;
                int iDelete = sQLiteDatabaseZze.delete("trigger_uris", "abs(timestamp_millis - ?) > cast(? as integer)", new String[]{String.valueOf(zzicVar.zzaZ().currentTimeMillis()), String.valueOf(zzfxVar.zzb(null))});
                if (iDelete > 0) {
                    zzicVar.zzaV().zzk().zzb("Deleted stale trigger uris. rowsDeleted", Integer.valueOf(iDelete));
                }
            }
        }
        if (this.zzk.zzd.zza() == 0) {
            this.zzk.zzd.zzb(zzaZ().currentTimeMillis());
        }
        zzaL();
    }

    public final zzal zzd() {
        return ((zzic) Preconditions.checkNotNull(this.zzn)).zzc();
    }

    public final zzou zzf() {
        return this.zzl;
    }

    public final zzht zzh() {
        zzht zzhtVar = this.zzc;
        zzaS(zzhtVar);
        return zzhtVar;
    }

    public final zzgz zzi() {
        zzgz zzgzVar = this.zzd;
        zzaS(zzgzVar);
        return zzgzVar;
    }

    public final zzav zzj() {
        zzav zzavVar = this.zze;
        zzaS(zzavVar);
        return zzavVar;
    }

    public final zzhb zzk() {
        zzhb zzhbVar = this.zzf;
        if (zzhbVar != null) {
            return zzhbVar;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    public final zzok zzl() {
        zzok zzokVar = this.zzg;
        zzaS(zzokVar);
        return zzokVar;
    }

    public final zzad zzm() {
        zzad zzadVar = this.zzh;
        zzaS(zzadVar);
        return zzadVar;
    }

    public final zzlp zzn() {
        zzlp zzlpVar = this.zzj;
        zzaS(zzlpVar);
        return zzlpVar;
    }

    public final zzpk zzp() {
        zzpk zzpkVar = this.zzi;
        zzaS(zzpkVar);
        return zzpkVar;
    }

    public final zznn zzq() {
        return this.zzk;
    }

    public final zzgn zzs() {
        return this.zzn.zzl();
    }

    public final zzpp zzt() {
        return ((zzic) Preconditions.checkNotNull(this.zzn)).zzk();
    }

    final void zzu() {
        if (!this.zzo.get()) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    final void zzv(zzr zzrVar) {
        zzaW().zzg();
        zzu();
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        zzjl zzjlVarZzf = zzjl.zzf(zzrVar.zzs, zzrVar.zzx);
        zzB(str);
        zzaV().zzk().zzc("Setting storage consent for package", str, zzjlVarZzf);
        zzA(str, zzjlVarZzf);
    }

    final void zzw(zzr zzrVar) {
        zzaW().zzg();
        zzu();
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        zzaz zzazVarZzg = zzaz.zzg(zzrVar.zzy);
        zzaV().zzk().zzc("Setting DMA consent for package", str, zzazVarZzg);
        zzaW().zzg();
        zzu();
        zzji zzjiVarZzc = zzaz.zzh(zzy(str), 100).zzc();
        this.zzD.put(str, zzazVarZzg);
        zzj().zzab(str, zzazVarZzg);
        zzji zzjiVarZzc2 = zzaz.zzh(zzy(str), 100).zzc();
        zzaW().zzg();
        zzu();
        zzji zzjiVar = zzji.DENIED;
        boolean z = zzjiVarZzc == zzjiVar && zzjiVarZzc2 == zzji.GRANTED;
        boolean z2 = zzjiVarZzc == zzji.GRANTED && zzjiVarZzc2 == zzjiVar;
        if (z || z2) {
            zzaV().zzk().zzb("Generated _dcu event for", str);
            Bundle bundle = new Bundle();
            if (zzj().zzw(zzC(), str, false, false, false, false, false, false, false).zzf < zzd().zzm(str, zzfy.zzam)) {
                bundle.putLong("_r", 1L);
                zzaV().zzk().zzc("_dcu realtime event count", str, Long.valueOf(zzj().zzw(zzC(), str, false, false, false, false, false, true, false).zzf));
            }
            this.zzK.zza(str, "_dcu", bundle);
        }
    }

    final zzaz zzx(String str) {
        zzaW().zzg();
        zzu();
        Map map = this.zzD;
        zzaz zzazVar = (zzaz) map.get(str);
        if (zzazVar != null) {
            return zzazVar;
        }
        zzaz zzazVarZzaa = zzj().zzaa(str);
        map.put(str, zzazVarZzaa);
        return zzazVarZzaa;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    final Bundle zzy(String str) {
        zzaW().zzg();
        zzu();
        if (zzh().zzx(str) == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        zzjl zzjlVarZzB = zzB(str);
        bundle.putAll(zzjlVarZzB.zzn());
        bundle.putAll(zzz(str, zzx(str), zzjlVarZzB, new zzan()).zzf());
        zzpn zzpnVarZzm = zzj().zzm(str, "_npa");
        bundle.putString("ad_personalization", 1 != (zzpnVarZzm != null ? zzpnVarZzm.zze.equals(1L) : zzaC(str, new zzan())) ? "granted" : "denied");
        return bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final com.google.android.gms.measurement.internal.zzaz zzz(java.lang.String r10, com.google.android.gms.measurement.internal.zzaz r11, com.google.android.gms.measurement.internal.zzjl r12, com.google.android.gms.measurement.internal.zzan r13) {
        /*
            r9 = this;
            com.google.android.gms.measurement.internal.zzht r0 = r9.zzh()
            com.google.android.gms.internal.measurement.zzgf r0 = r0.zzx(r10)
            java.lang.String r1 = "-"
            r2 = 90
            if (r0 != 0) goto L31
            com.google.android.gms.measurement.internal.zzji r10 = r11.zzc()
            com.google.android.gms.measurement.internal.zzji r12 = com.google.android.gms.measurement.internal.zzji.DENIED
            if (r10 != r12) goto L20
            int r2 = r11.zzb()
            com.google.android.gms.measurement.internal.zzjk r10 = com.google.android.gms.measurement.internal.zzjk.AD_USER_DATA
            r13.zzb(r10, r2)
            goto L27
        L20:
            com.google.android.gms.measurement.internal.zzjk r10 = com.google.android.gms.measurement.internal.zzjk.AD_USER_DATA
            com.google.android.gms.measurement.internal.zzam r11 = com.google.android.gms.measurement.internal.zzam.FAILSAFE
            r13.zzc(r10, r11)
        L27:
            com.google.android.gms.measurement.internal.zzaz r10 = new com.google.android.gms.measurement.internal.zzaz
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            java.lang.Boolean r12 = java.lang.Boolean.TRUE
            r10.<init>(r11, r2, r12, r1)
            return r10
        L31:
            com.google.android.gms.measurement.internal.zzji r0 = r11.zzc()
            com.google.android.gms.measurement.internal.zzji r3 = com.google.android.gms.measurement.internal.zzji.GRANTED
            if (r0 == r3) goto L85
            com.google.android.gms.measurement.internal.zzji r4 = com.google.android.gms.measurement.internal.zzji.DENIED
            if (r0 != r4) goto L3e
            goto L85
        L3e:
            com.google.android.gms.measurement.internal.zzji r11 = com.google.android.gms.measurement.internal.zzji.POLICY
            if (r0 != r11) goto L55
            com.google.android.gms.measurement.internal.zzht r11 = r9.zzc
            com.google.android.gms.measurement.internal.zzjk r0 = com.google.android.gms.measurement.internal.zzjk.AD_USER_DATA
            com.google.android.gms.measurement.internal.zzji r11 = r11.zzA(r10, r0)
            com.google.android.gms.measurement.internal.zzji r5 = com.google.android.gms.measurement.internal.zzji.UNINITIALIZED
            if (r11 == r5) goto L55
            com.google.android.gms.measurement.internal.zzam r12 = com.google.android.gms.measurement.internal.zzam.REMOTE_ENFORCED_DEFAULT
            r13.zzc(r0, r12)
            r0 = r11
            goto L8e
        L55:
            com.google.android.gms.measurement.internal.zzht r11 = r9.zzc
            com.google.android.gms.measurement.internal.zzjk r0 = com.google.android.gms.measurement.internal.zzjk.AD_USER_DATA
            com.google.android.gms.measurement.internal.zzjk r5 = r11.zzw(r10, r0)
            com.google.android.gms.measurement.internal.zzji r12 = r12.zzp()
            r6 = 1
            if (r12 == r3) goto L66
            if (r12 != r4) goto L68
        L66:
            r7 = r6
            goto L69
        L68:
            r7 = 0
        L69:
            com.google.android.gms.measurement.internal.zzjk r8 = com.google.android.gms.measurement.internal.zzjk.AD_STORAGE
            if (r5 != r8) goto L76
            if (r7 == 0) goto L76
            com.google.android.gms.measurement.internal.zzam r11 = com.google.android.gms.measurement.internal.zzam.REMOTE_DELEGATION
            r13.zzc(r0, r11)
            r0 = r12
            goto L8e
        L76:
            com.google.android.gms.measurement.internal.zzam r12 = com.google.android.gms.measurement.internal.zzam.REMOTE_DEFAULT
            r13.zzc(r0, r12)
            boolean r11 = r11.zzv(r10, r0)
            if (r6 == r11) goto L83
            r0 = r4
            goto L8e
        L83:
            r0 = r3
            goto L8e
        L85:
            int r2 = r11.zzb()
            com.google.android.gms.measurement.internal.zzjk r11 = com.google.android.gms.measurement.internal.zzjk.AD_USER_DATA
            r13.zzb(r11, r2)
        L8e:
            com.google.android.gms.measurement.internal.zzht r11 = r9.zzc
            boolean r11 = r11.zzy(r10)
            com.google.android.gms.measurement.internal.zzht r12 = r9.zzh()
            java.util.SortedSet r10 = r12.zzz(r10)
            com.google.android.gms.measurement.internal.zzji r12 = com.google.android.gms.measurement.internal.zzji.DENIED
            if (r0 == r12) goto Lbb
            boolean r12 = r10.isEmpty()
            if (r12 == 0) goto La7
            goto Lbb
        La7:
            com.google.android.gms.measurement.internal.zzaz r12 = new com.google.android.gms.measurement.internal.zzaz
            java.lang.Boolean r13 = java.lang.Boolean.TRUE
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r11)
            java.lang.String r1 = ""
            if (r11 == 0) goto Lb7
            java.lang.String r1 = android.text.TextUtils.join(r1, r10)
        Lb7:
            r12.<init>(r13, r2, r0, r1)
            return r12
        Lbb:
            com.google.android.gms.measurement.internal.zzaz r10 = new com.google.android.gms.measurement.internal.zzaz
            java.lang.Boolean r12 = java.lang.Boolean.FALSE
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)
            r10.<init>(r12, r2, r11, r1)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzpg.zzz(java.lang.String, com.google.android.gms.measurement.internal.zzaz, com.google.android.gms.measurement.internal.zzjl, com.google.android.gms.measurement.internal.zzan):com.google.android.gms.measurement.internal.zzaz");
    }
}
