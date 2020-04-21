package framevpm.project;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CProjectsInfo {

    public static String focussedVersion;

    public static String[] focussedLinuxKernelVersions = {
        "v3.0",
        "v3.1",
        "v3.2",
        "v3.3",
        "v3.4",
        "v3.5",
        "v3.6",
        "v3.7",
        "v3.8",
        "v3.9",
        "v3.10",
        "v3.11",
        "v3.12",
        "v3.13",
        "v3.14",
        "v3.15",
        "v3.16",
        "v3.17",
        "v3.18",
        "v3.19",
        "v4.0",
        "v4.1",
        "v4.2",
        "v4.3",
        "v4.4",
        "v4.5",
        "v4.6",
        "v4.7",
        "v4.8",
        "v4.9",
        "v4.10",
        "v4.11",
        "v4.12",
        "v4.13",
        "v4.14"
    };

    public static String[] focussedWiresharkVersions = {
        "wireshark-1.8.0", "wireshark-1.10.0", "v1.11.0", "v1.12.0", "v1.99.0",
        "v2.0.0", "v2.1.0", "v2.2.0", "v2.4.0"
    };

    public static String[] focussedOpenSSLVersions = {
        "OpenSSL_0_9_3", "OpenSSL_0_9_4", "OpenSSL_0_9_5", "OpenSSL_0_9_6", "OpenSSL_0_9_7",
        "OpenSSL_0_9_8", "OpenSSL_1_0_0", "OpenSSL_1_0_1", "OpenSSL_1_0_2"
    };

    public static final TreeMap<Long, String> LINUX_VERSIONS() {
        TreeMap<Long, String> versions = new TreeMap<>();
        if (focussedVersion == "v3.0") {
            versions.put(1311268649L, "v3.0");
            versions.put(1319440251L, "v3.1");
        }
        if (focussedVersion == "v3.1") {
            versions.put(1319440251L, "v3.1");
            versions.put(1325688950L, "v3.2");
        }
        if (focussedVersion == "v3.2") {
            versions.put(1325688950L, "v3.2");
            versions.put(1332083742L, "v3.3");
        }
        if (focussedVersion == "v3.3") {
            versions.put(1332083742L, "v3.3");
            versions.put(1337520565L, "v3.4");
        }
        if (focussedVersion == "v3.4") {
            versions.put(1337520565L, "v3.4");
            versions.put(1342871923L, "v3.5");
        }
        if (focussedVersion == "v3.5") {
            versions.put(1342871923L, "v3.5");
            versions.put(1349016490L, "v3.6");
        }
        if (focussedVersion == "v3.6") {
            versions.put(1349016490L, "v3.6");
            versions.put(1355164261L, "v3.7");
        }
        if (focussedVersion == "v3.7") {
            versions.put(1355164261L, "v3.7");
            versions.put(1361199545L, "v3.8");
        }
        if (focussedVersion == "v3.8") {
            versions.put(1361199545L, "v3.8");
            versions.put(1367163369L, "v3.9");
        }
        if (focussedVersion == "v3.9") {
            versions.put(1367163369L, "v3.9");
            versions.put(1372598022L, "v3.10");
        }
        if (focussedVersion == "v3.10") {
            versions.put(1372598022L, "v3.10");
            versions.put(1378122378L, "v3.11");
        }
        if (focussedVersion == "v3.11") {
            versions.put(1378122378L, "v3.11");
            versions.put(1383489719L, "v3.12");
        }
        if (focussedVersion == "v3.12") {
            versions.put(1383489719L, "v3.12");
            versions.put(1390153223L, "v3.13");
        }
        if (focussedVersion == "v3.13") {
            versions.put(1390153223L, "v3.13");
            versions.put(1396204823L, "v3.14");
        }
        if (focussedVersion == "v3.14") {
            versions.put(1396204823L, "v3.14");
            versions.put(1402219202L, "v3.15");
        }
        if (focussedVersion == "v3.15") {
            versions.put(1402219202L, "v3.15");
            versions.put(1407072325L, "v3.16");
        }
        if (focussedVersion == "v3.16") {
            versions.put(1407072325L, "v3.16");
            versions.put(1412504600L, "v3.17");
        }
        if (focussedVersion == "v3.17") {
            versions.put(1412504600L, "v3.17");
            versions.put(1417958473L, "v3.18");
        }
        if (focussedVersion == "v3.18") {
            versions.put(1417958473L, "v3.18");
            versions.put(1423418078L, "v3.19");
        }
        if (focussedVersion == "v3.19") {
            versions.put(1423418078L, "v3.19");
            versions.put(1428844383L, "v4.0");
        }
        if (focussedVersion == "v4.0") {
            versions.put(1428844383L, "v4.0");
            versions.put(1434917160L, "v4.1");
        }
        if (focussedVersion == "v4.1") {
            versions.put(1434917160L, "v4.1");
            versions.put(1440927257L, "v4.2");
        }
        if (focussedVersion == "v4.2") {
            versions.put(1440927257L, "v4.2");
            versions.put(1446390339L, "v4.3");
        }
        if (focussedVersion == "v4.3") {
            versions.put(1446390339L, "v4.3");
            versions.put(1452434514L, "v4.4");
        }
        if (focussedVersion == "v4.4") {
            versions.put(1452434514L, "v4.4");
            versions.put(1457900945L, "v4.5");
        }
        if (focussedVersion == "v4.5") {
            versions.put(1457900945L, "v4.5");
            versions.put(1463319803L, "v4.6");
        }
        if (focussedVersion == "v4.6") {
            versions.put(1463319803L, "v4.6");
            versions.put(1469355842L, "v4.7");
        }
        if (focussedVersion == "v4.7") {
            versions.put(1469355842L, "v4.7");
            versions.put(1475418280L, "v4.8");
        }
        if (focussedVersion == "v4.8") {
            versions.put(1475418280L, "v4.8");
            versions.put(1481451482L, "v4.9");
        }
        if (focussedVersion == "v4.9") {
            versions.put(1481451482L, "v4.9");
            versions.put(1487511248L, "v4.10");
        }
        if (focussedVersion == "v4.10") {
            versions.put(1487511248L, "v4.10");
            versions.put(1493574480L, "v4.11");
        }
        if (focussedVersion == "v4.11") {
            versions.put(1493574480L, "v4.11");
            versions.put(1499004431L, "v4.12");
        }
        if (focussedVersion == "v4.12") {
            versions.put(1499004431L, "v4.12");
            versions.put(1504439788L, "v4.13");
        }
        if (focussedVersion == "v4.13") {
            versions.put(1504439788L, "v4.13");
            versions.put(1510479981L, "v4.14");
        }
        if (focussedVersion == "v4.14") {
            versions.put(1510479981L, "v4.14");
            versions.put(1517142041L, "v4.15");
        }
        return versions;
    }

    public static final Map<String, String> LINUX_CVE_VERSIONS() {
        HashMap<String, String> versions = new HashMap<>();
        if (focussedVersion == "v3.0") {
            versions.put("3.0", "v3.0");
            versions.put("3.1", "v3.1");
        }
        if (focussedVersion == "v3.1") {
            versions.put("3.1", "v3.1");
            versions.put("3.2", "v3.2");
        }
        if (focussedVersion == "v3.2") {
            versions.put("3.2", "v3.2");
            versions.put("3.3", "v3.3");
        }
        if (focussedVersion == "v3.3") {
            versions.put("3.3", "v3.3");
            versions.put("3.4", "v3.4");
        }
        if (focussedVersion == "v3.4") {
            versions.put("3.4", "v3.4");
            versions.put("3.5", "v3.5");
        }
        if (focussedVersion == "v3.5") {
            versions.put("3.5", "v3.5");
            versions.put("3.6", "v3.6");
        }
        if (focussedVersion == "v3.6") {
            versions.put("3.6", "v3.6");
            versions.put("3.7", "v3.7");
        }
        if (focussedVersion == "v3.7") {
            versions.put("3.7", "v3.7");
            versions.put("3.8", "v3.8");
        }
        if (focussedVersion == "v3.8") {
            versions.put("3.8", "v3.8");
            versions.put("3.9", "v3.9");
        }
        if (focussedVersion == "v3.9") {
            versions.put("3.9", "v3.9");
            versions.put("3.10", "v3.10");
        }
        if (focussedVersion == "v3.10") {
            versions.put("3.10", "v3.10");
            versions.put("3.11", "v3.11");
        }
        if (focussedVersion == "v3.11") {
            versions.put("3.11", "v3.11");
            versions.put("3.12", "v3.12");
        }
        if (focussedVersion == "v3.12") {
            versions.put("3.12", "v3.12");
            versions.put("3.13", "v3.13");
        }
        if (focussedVersion == "v3.13") {
            versions.put("3.13", "v3.13");
            versions.put("3.14", "v3.14");
        }
        if (focussedVersion == "v3.14") {
            versions.put("3.14", "v3.14");
            versions.put("3.15", "v3.15");
        }
        if (focussedVersion == "v3.15") {
            versions.put("3.15", "v3.15");
            versions.put("3.16", "v3.16");
        }
        if (focussedVersion == "v3.16") {
            versions.put("3.16", "v3.16");
            versions.put("3.17", "v3.17");
        }
        if (focussedVersion == "v3.17") {
            versions.put("3.17", "v3.17");
            versions.put("3.18", "v3.18");
        }
        if (focussedVersion == "v3.18") {
            versions.put("3.18", "v3.18");
            versions.put("3.19", "v3.19");
        }
        if (focussedVersion == "v3.19") {
            versions.put("3.19", "v3.19");
            versions.put("4.0", "v4.0");
        }
        if (focussedVersion == "v4.0") {
            versions.put("4.0", "v4.0");
            versions.put("4.1", "v4.1");
        }
        if (focussedVersion == "v4.1") {
            versions.put("4.1", "v4.1");
            versions.put("4.2", "v4.2");
        }
        if (focussedVersion == "v4.2") {
            versions.put("4.2", "v4.2");
            versions.put("4.3", "v4.3");
        }
        if (focussedVersion == "v4.3") {
            versions.put("4.3", "v4.3");
            versions.put("4.4", "v4.4");
        }
        if (focussedVersion == "v4.4") {
            versions.put("4.4", "v4.4");
            versions.put("4.5", "v4.5");
        }
        if (focussedVersion == "v4.5") {
            versions.put("4.5", "v4.5");
            versions.put("4.6", "v4.6");
        }
        if (focussedVersion == "v4.6") {
            versions.put("4.6", "v4.6");
            versions.put("4.7", "v4.7");
        }
        if (focussedVersion == "v4.7") {
            versions.put("4.7", "v4.7");
            versions.put("4.8", "v4.8");
        }
        if (focussedVersion == "v4.8") {
            versions.put("4.8", "v4.8");
            versions.put("4.9", "v4.9");
        }
        if (focussedVersion == "v4.9") {
            versions.put("4.9", "v4.9");
            versions.put("4.10", "v4.10");
        }
        if (focussedVersion == "v4.10") {
            versions.put("4.10", "v4.10");
            versions.put("4.11", "v4.11");
        }
        if (focussedVersion == "v4.11") {
            versions.put("4.11", "v4.11");
            versions.put("4.12", "v4.12");
        }
        if (focussedVersion == "v4.12") {
            versions.put("4.12", "v4.12");
            versions.put("4.13", "v4.13");
        }
        if (focussedVersion == "v4.13") {
            versions.put("4.13", "v4.13");
            versions.put("4.14", "v4.14");
        }
        if (focussedVersion == "v4.14") {
            versions.put("4.14", "v4.14");
            versions.put("4.15", "v4.15");
        }
        return versions;
    }

    public static final TreeMap<Long, String> SYSTEMD_VERSION() {
        TreeMap<Long, String> versions = new TreeMap<>();
        versions.put(1278453600L, "v1");
        versions.put(1284415200L, "v10");
        versions.put(1299538800L, "v20");
        versions.put(1310508000L, "v30");
        versions.put(1328569200L, "v40");
        versions.put(1348092000L, "v190");
        versions.put(1364511600L, "v200");
        versions.put(1393196400L, "v210");
        versions.put(1432159200L, "v220");
        versions.put(1463868000L, "v230");
        versions.put(1507240800L, "v235");
        return versions;
    }

    public static final TreeMap<Long, String> WIRESHARK_VERSION() {
        TreeMap<Long, String> versions = new TreeMap<>();
        switch (focussedVersion) {
            case "ethereal-0.3.15":
                versions.put(905909955L, "ethereal-0.3.15");
                versions.put(911433534L, "ethereal-0.5.0");
                break;
            case "ethereal-0.5.0":
                versions.put(911433534L, "ethereal-0.5.0");
                versions.put(925500716L, "ethereal-0.6.0");
                break;
            case "ethereal-0.6.0":
                versions.put(925500716L, "ethereal-0.6.0");
                versions.put(933717600L, "ethereal-0.7.0");
                break;
            case "ethereal-0.7.0":
                versions.put(933717600L, "ethereal-0.7.0");
                versions.put(946508400L, "ethereal-0.8.0");
                break;
            case "ethereal-0.8.0":
                versions.put(946508400L, "ethereal-0.8.0");
                versions.put(1009148400L, "ethereal-0.9.0");
                break;
            case "ethereal-0.9.0":
                versions.put(1009148400L, "ethereal-0.9.0");
                versions.put(1071270000L, "ethereal-0.10.0");
                break;
            case "ethereal-0.10.0":
                versions.put(1071270000L, "ethereal-0.10.0");
                versions.put(1145829600L, "ethereal-0.99.0");
                break;
            case "ethereal-0.99.0":
                versions.put(1145829600L, "ethereal-0.99.0");
                versions.put(1206745200L, "wireshark-1.0.0");
                break;
            case "wireshark-1.0.0":
                versions.put(1206745200L, "wireshark-1.0.0");
                versions.put(1245103200L, "wireshark-1.2.0");
                break;
            case "wireshark-1.2.0":
                versions.put(1245103200L, "wireshark-1.2.0");
                versions.put(1283119200L, "wireshark-1.4.0");
                break;
            case "wireshark-1.4.0":
                versions.put(1283119200L, "wireshark-1.4.0");
                versions.put(1307397600L, "wireshark-1.6.0");
                break;
            case "wireshark-1.6.0":
                versions.put(1307397600L, "wireshark-1.6.0");
                versions.put(1340229600L, "wireshark-1.8.0");
                break;
            case "wireshark-1.8.0":
                versions.put(1340229600L, "wireshark-1.8.0");
                versions.put(1370383200L, "wireshark-1.10.0");
                break;
            case "wireshark-1.10.0":
                versions.put(1370383200L, "wireshark-1.10.0");
                versions.put(1392159600L, "v1.11.0");
                break;
            case "v1.11.0":
                versions.put(1392159600L, "v1.11.0");
                versions.put(1406757600L, "v1.12.0");
                break;
            case "v1.12.0":
                versions.put(1406757600L, "v1.12.0");
                versions.put(1412632800L, "v1.99.0");
                break;
            case "v1.99.0":
                versions.put(1412632800L, "v1.99.0");
                versions.put(1447801200L, "v2.0.0");
                break;
            case "v2.0.0":
                versions.put(1447801200L, "v2.0.0");
                versions.put(1465336800L, "v2.1.0");
                break;
            case "v2.1.0":
                versions.put(1465336800L, "v2.1.0");
                versions.put(1473199200L, "v2.2.0");
                break;
            case "v2.2.0":
                versions.put(1473199200L, "v2.2.0");
                versions.put(1500415200L, "v2.4.0");
                break;
            case "v2.4.0":
                versions.put(1500415200L, "v2.4.0");
                versions.put(1517871600L, "v2.5.0");
                break;
        }
        return versions;
    }

    public static final TreeMap<Long, String> OPENSSL_VERSION() {
        TreeMap<Long, String> versions = new TreeMap<>();
        switch (focussedVersion) {
            case "OpenSSL_0_9_3":
                versions.put(927496800L, "OpenSSL_0_9_3");
                versions.put(934149600L, "OpenSSL_0_9_4");
                break;
            case "OpenSSL_0_9_4":
                versions.put(934149600L, "OpenSSL_0_9_4");
                versions.put(959205600L, "OpenSSL_0_9_5");
                break;
            case "OpenSSL_0_9_5":
                versions.put(959205600L, "OpenSSL_0_9_5");
                versions.put(971128800L, "OpenSSL_0_9_6");
                break;
            case "OpenSSL_0_9_6":
                versions.put(971128800L, "OpenSSL_0_9_6");
                versions.put(1041289200L, "OpenSSL_0_9_7");
                break;
            case "OpenSSL_0_9_7":
                versions.put(1041289200L, "OpenSSL_0_9_7");
                versions.put(1120514400L, "OpenSSL_0_9_8");
                break;
            case "OpenSSL_0_9_8":
                versions.put(1120514400L, "OpenSSL_0_9_8");
                versions.put(1269813600L, "OpenSSL_1_0_0");
                break;
            case "OpenSSL_1_0_0":
                versions.put(1269813600L, "OpenSSL_1_0_0");
                versions.put(1331679600L, "OpenSSL_1_0_1");
                break;
            case "OpenSSL_1_0_1":
                versions.put(1331679600L, "OpenSSL_1_0_1");
                versions.put(1421881200L, "OpenSSL_1_0_2");
                break;
            case "OpenSSL_1_0_2":
                versions.put(1421881200L, "OpenSSL_1_0_2");
                versions.put(1472076000L, "OpenSSL_1_1_0");
                break;
        }
        return versions;
    }

    public static final Map<String, String> LINUX_CVE_PREVIOUS_VERSIONS() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("v3.0", "v2.6.39");
        versions.put("v3.1", "v3.0");
        versions.put("v3.2", "v3.1");
        versions.put("v3.3", "v3.2");
        versions.put("v3.4", "v3.3");
        versions.put("v3.5", "v3.4");
        versions.put("v3.6", "v3.5");
        versions.put("v3.7", "v3.6");
        versions.put("v3.8", "v3.7");
        versions.put("v3.9", "v3.8");
        versions.put("v3.10", "v3.9");
        versions.put("v3.11", "v3.10");
        versions.put("v3.12", "v3.11");
        versions.put("v3.13", "v3.12");
        versions.put("v3.14", "v3.13");
        versions.put("v3.15", "v3.14");
        versions.put("v3.16", "v3.15");
        versions.put("v3.17", "v3.16");
        versions.put("v3.18", "v3.17");
        versions.put("v3.19", "v3.18");
        versions.put("v4.0", "v3.19");
        versions.put("v4.1", "v4.0");
        versions.put("v4.2", "v4.1");
        versions.put("v4.3", "v4.2");
        versions.put("v4.4", "v4.3");
        versions.put("v4.5", "v4.4");
        versions.put("v4.6", "v4.5");
        versions.put("v4.7", "v4.6");
        versions.put("v4.8", "v4.7");
        versions.put("v4.9", "v4.8");
        versions.put("v4.10", "v4.9");
        versions.put("v4.11", "v4.10");
        versions.put("v4.12", "v4.11");
        versions.put("v4.13", "v4.12");
        versions.put("v4.14", "v4.13");
        versions.put("v4.15", "v4.14");
        return versions;
    }
    
    public static final Map<String, String> WIRESHARK_CVE_PREVIOUS_VERSIONS() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("ethereal-0.5.0", "ethereal-0.3.15");
        versions.put("ethereal-0.6.0", "ethereal-0.5.0");
        versions.put("ethereal-0.7.0", "ethereal-0.6.0");
        versions.put("ethereal-0.8.0", "ethereal-0.7.0");
        versions.put("ethereal-0.9.0", "ethereal-0.8.0");
        versions.put("ethereal-0.10.0", "ethereal-0.9.0");
        versions.put("ethereal-0.99.0", "ethereal-0.10.0");
        versions.put("wireshark-1.0.0", "ethereal-0.99.0");
        versions.put("wireshark-1.2.0", "wireshark-1.0.0");
        versions.put("wireshark-1.4.0", "wireshark-1.2.0");
        versions.put("wireshark-1.6.0", "wireshark-1.4.0");
        versions.put("wireshark-1.8.0", "wireshark-1.6.0");
        versions.put("wireshark-1.10.0", "wireshark-1.8.0");
        versions.put("v1.11.0", "wireshark-1.10.0");
        versions.put("v1.12.0", "v1.11.0");
        versions.put("v1.99.0", "v1.12.0");
        versions.put("v2.0.0", "v1.99.0");
        versions.put("v2.1.0", "v2.0.0");
        versions.put("v2.2.0", "v2.1.0");
        versions.put("v2.4.0", "v2.2.0");
        versions.put("v2.5.0", "v2.4.0");
        return versions;
    }
    
    public static final Map<String, String> OPENSSL_CVE_PREVIOUS_VERSIONS() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("OpenSSL_0_9_4", "OpenSSL_0_9_3");
        versions.put("OpenSSL_0_9_5", "OpenSSL_0_9_4");
        versions.put("OpenSSL_0_9_6", "OpenSSL_0_9_5");
        versions.put("OpenSSL_0_9_7", "OpenSSL_0_9_6");
        versions.put("OpenSSL_0_9_8", "OpenSSL_0_9_7");
        versions.put("OpenSSL_1_0_0", "OpenSSL_0_9_8");
        versions.put("OpenSSL_1_0_1", "OpenSSL_1_0_0");
        versions.put("OpenSSL_1_0_2", "OpenSSL_1_0_1");
        versions.put("OpenSSL_1_1_0", "OpenSSL_1_0_2");
        return versions;
    }

    public static final Map<String, String> SYSTEMD_CVE_VERSION() {
        Map<String, String> versions = new HashMap<>();
        versions.put("1", "v1");
        versions.put("10", "v10");
        versions.put("20", "v20");
        versions.put("30", "v30");
        versions.put("40", "v40");
        versions.put("190", "v190");
        versions.put("200", "v200");
        versions.put("210", "v210");
        versions.put("220", "v220");
        versions.put("230", "v230");
        versions.put("235", "v235");
        return versions;
    }

    public static final Map<String, String> WIRESHARK_CVE_VERSION() {
        Map<String, String> versions = new HashMap<>();
        switch (focussedVersion) {
            case "ethereal-0.3.15":
                versions.put("0.3.15", "ethereal-0.3.15");
                versions.put("0.5.0", "ethereal-0.5.0");
                break;
            case "ethereal-0.5.0":
                versions.put("0.5.0", "ethereal-0.5.0");
                versions.put("0.6.0", "ethereal-0.6.0");
                break;
            case "ethereal-0.6.0":
                versions.put("0.6.0", "ethereal-0.6.0");
                versions.put("0.7.0", "ethereal-0.7.0");
                break;
            case "ethereal-0.7.0":
                versions.put("0.7.0", "ethereal-0.7.0");
                versions.put("0.8.0", "ethereal-0.8.0");
                break;
            case "ethereal-0.8.0":
                versions.put("0.8.0", "ethereal-0.8.0");
                versions.put("0.9.0", "ethereal-0.9.0");
                break;
            case "ethereal-0.9.0":
                versions.put("0.9.0", "ethereal-0.9.0");
                versions.put("0.10.0", "ethereal-0.10.0");
                break;
            case "ethereal-0.10.0":
                versions.put("0.10.0", "ethereal-0.10.0");
                versions.put("0.99.0", "ethereal-0.99.0");
                break;
            case "ethereal-0.99.0":
                versions.put("0.99.0", "ethereal-0.99.0");
                versions.put("1.0.0", "wireshark-1.0.0");
                break;
            case "wireshark-1.0.0":
                versions.put("1.0.0", "wireshark-1.0.0");
                versions.put("1.2.0", "wireshark-1.2.0");
                break;
            case "wireshark-1.2.0":
                versions.put("1.2.0", "wireshark-1.2.0");
                versions.put("1.4.0", "wireshark-1.4.0");
                break;
            case "wireshark-1.4.0":
                versions.put("1.4.0", "wireshark-1.4.0");
                versions.put("1.6.0", "wireshark-1.6.0");
                break;
            case "wireshark-1.6.0":
                versions.put("1.6.0", "wireshark-1.6.0");
                versions.put("1.8.0", "wireshark-1.8.0");
                break;
            case "wireshark-1.8.0":
                versions.put("1.8.0", "wireshark-1.8.0");
                versions.put("1.10.0", "wireshark-1.10.0");
                break;
            case "wireshark-1.10.0":
                versions.put("1.10.0", "wireshark-1.10.0");
                versions.put("1.11.0", "v1.11.0");
                break;
            case "v1.11.0":
                versions.put("1.11.0", "v1.11.0");
                versions.put("1.12.0", "v1.12.0");
                break;
            case "v1.12.0":
                versions.put("1.12.0", "v1.12.0");
                versions.put("1.99.0", "v1.99.0");
                break;
            case "v1.99.0":
                versions.put("1.99.0", "v1.99.0");
                versions.put("2.0.0", "v2.0.0");
                break;
            case "v2.0.0":
                versions.put("2.0.0", "v2.0.0");
                versions.put("2.1.0", "v2.1.0");
                break;
            case "v2.1.0":
                versions.put("2.1.0", "v2.1.0");
                versions.put("2.2.0", "v2.2.0");
                break;
            case "v2.2.0":
                versions.put("2.2.0", "v2.2.0");
                versions.put("2.4.0", "v2.4.0");
                break;
            case "v2.4.0":
                versions.put("2.4.0", "v2.4.0");
                versions.put("2.5.0", "v2.5.0");
                break;
        }
        return versions;
    }

    public static final Map<String, String> OPENSSL_CVE_VERSION() {
        Map<String, String> versions = new HashMap<>();
        switch (focussedVersion) {
            case "OpenSSL_0_9_3":
                versions.put("0.9.3", "OpenSSL_0_9_3");
                versions.put("0.9.4", "OpenSSL_0_9_4");
                break;
            case "OpenSSL_0_9_4":
                versions.put("0.9.4", "OpenSSL_0_9_4");
                versions.put("0.9.5", "OpenSSL_0_9_5");
                break;
            case "OpenSSL_0_9_5":
                versions.put("0.9.5", "OpenSSL_0_9_5");
                versions.put("0.9.6", "OpenSSL_0_9_6");
                break;
            case "OpenSSL_0_9_6":
                versions.put("0.9.6", "OpenSSL_0_9_6");
                versions.put("0.9.7", "OpenSSL_0_9_7");
                break;
            case "OpenSSL_0_9_7":
                versions.put("0.9.7", "OpenSSL_0_9_7");
                versions.put("0.9.8", "OpenSSL_0_9_8");
                break;
            case "OpenSSL_0_9_8":
                versions.put("0.9.8", "OpenSSL_0_9_8");
                versions.put("1.0.0", "OpenSSL_1_0_0");
                break;
            case "OpenSSL_1_0_0":
                versions.put("1.0.0", "OpenSSL_1_0_0");
                versions.put("1.0.1", "OpenSSL_1_0_1");
                break;
            case "OpenSSL_1_0_1":
                versions.put("1.0.1", "OpenSSL_1_0_1");
                versions.put("1.0.2", "OpenSSL_1_0_2");
                break;
            case "OpenSSL_1_0_2":
                versions.put("1.0.2", "OpenSSL_1_0_2");
                versions.put("1.1.0", "OpenSSL_1_1_0");
                break;
        }
        return versions;
    }

    /*
    public static final TreeMap<Long, String> LINUX_VERSIONS() {
        TreeMap<Long, String> versions = new TreeMap<>();
        versions.put(1119005309L, "v2.6.12");
        versions.put(1125240148L, "v2.6.13");
        versions.put(1130425353L, "v2.6.14");
        versions.put(1136226087L, "v2.6.15");
        versions.put(1142801623L, "v2.6.16");
        versions.put(1150562984L, "v2.6.17");
        versions.put(1158691335L, "v2.6.18");
        versions.put(1164805065L, "v2.6.19");
        versions.put(1170582305L, "v2.6.20");
        versions.put(1177524520L, "v2.6.21");
        versions.put(1183905146L, "v2.6.22");
        versions.put(1191929512L, "v2.6.23");
        versions.put(1201183133L, "v2.6.24");
        versions.put(1208368197L, "v2.6.25");
        versions.put(1215953498L, "v2.6.26");
        versions.put(1223558041L, "v2.6.27");
        versions.put(1230128818L, "v2.6.28");
        versions.put(1237821147L, "v2.6.29");
        versions.put(1244570737L, "v2.6.30");
        versions.put(1252502047L, "v2.6.31");
        versions.put(1259779889L, "v2.6.32");
        versions.put(1267005144L, "v2.6.33");
        versions.put(1274012264L, "v2.6.34");
        versions.put(1280668283L, "v2.6.35");
        versions.put(1287574278L, "v2.6.36");
        versions.put(1294156226L, "v2.6.37");
        versions.put(1300123239L, "v2.6.38");
        versions.put(1305745602L, "v2.6.39");
        versions.put(1311268649L, "v3.0");
        versions.put(1319440251L, "v3.1");
        versions.put(1325688950L, "v3.2");
        versions.put(1332083742L, "v3.3");
        versions.put(1337520565L, "v3.4");
        versions.put(1342871923L, "v3.5");
        versions.put(1349016490L, "v3.6");
        versions.put(1355164261L, "v3.7");
        versions.put(1361199545L, "v3.8");
        versions.put(1367163369L, "v3.9");
        versions.put(1372598022L, "v3.10");
        versions.put(1378122378L, "v3.11");
        versions.put(1383489719L, "v3.12");
        versions.put(1390153223L, "v3.13");
        versions.put(1396204823L, "v3.14");
        versions.put(1402219202L, "v3.15");
        versions.put(1407072325L, "v3.16");
        versions.put(1412504600L, "v3.17");
        versions.put(1417958473L, "v3.18");
        versions.put(1423418078L, "v3.19");
        versions.put(1428844383L, "v4.0");
        versions.put(1434917160L, "v4.1");
        versions.put(1440927257L, "v4.2");
        versions.put(1446390339L, "v4.3");
        versions.put(1452434514L, "v4.4");
        versions.put(1457900945L, "v4.5");
        versions.put(1463319803L, "v4.6");
        versions.put(1469355842L, "v4.7");
        versions.put(1475418280L, "v4.8");
        versions.put(1481451482L, "v4.9");
        versions.put(1487511248L, "v4.10");
        versions.put(1493574480L, "v4.11");
        versions.put(1499004431L, "v4.12");
        versions.put(1504439788L, "v4.13");
        versions.put(1510479981L, "v4.14");
        versions.put(1517142041L, "v4.15");
        return versions;
    }
    
    public static final Map<String, String> LINUX_CVE_VERSIONS() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("2.6.12", "v2.6.12");
        versions.put("2.6.13", "v2.6.13");
        versions.put("2.6.14", "v2.6.14");
        versions.put("2.6.15", "v2.6.15");
        versions.put("2.6.16", "v2.6.16");
        versions.put("2.6.17", "v2.6.17");
        versions.put("2.6.18", "v2.6.18");
        versions.put("2.6.19", "v2.6.19");
        versions.put("2.6.20", "v2.6.20");
        versions.put("2.6.21", "v2.6.21");
        versions.put("2.6.22", "v2.6.22");
        versions.put("2.6.23", "v2.6.23");
        versions.put("2.6.24", "v2.6.24");
        versions.put("2.6.25", "v2.6.25");
        versions.put("2.6.26", "v2.6.26");
        versions.put("2.6.27", "v2.6.27");
        versions.put("2.6.28", "v2.6.28");
        versions.put("2.6.29", "v2.6.29");
        versions.put("2.6.30", "v2.6.30");
        versions.put("2.6.31", "v2.6.31");
        versions.put("2.6.32", "v2.6.32");
        versions.put("2.6.33", "v2.6.33");
        versions.put("2.6.34", "v2.6.34");
        versions.put("2.6.35", "v2.6.35");
        versions.put("2.6.36", "v2.6.36");
        versions.put("2.6.37", "v2.6.37");
        versions.put("2.6.38", "v2.6.38");
        versions.put("2.6.39", "v2.6.39");
        versions.put("3.0", "v3.0");
        versions.put("3.1", "v3.1");
        versions.put("3.2", "v3.2");
        versions.put("3.3", "v3.3");
        versions.put("3.4", "v3.4");
        versions.put("3.5", "v3.5");
        versions.put("3.6", "v3.6");
        versions.put("3.7", "v3.7");
        versions.put("3.8", "v3.8");
        versions.put("3.9", "v3.9");
        versions.put("3.10", "v3.10");
        versions.put("3.11", "v3.11");
        versions.put("3.12", "v3.12");
        versions.put("3.13", "v3.13");
        versions.put("3.14", "v3.14");
        versions.put("3.15", "v3.15");
        versions.put("3.16", "v3.16");
        versions.put("3.17", "v3.17");
        versions.put("3.18", "v3.18");
        versions.put("3.19", "v3.19");
        versions.put("4.0", "v4.0");
        versions.put("4.1", "v4.1");
        versions.put("4.2", "v4.2");
        versions.put("4.3", "v4.3");
        versions.put("4.4", "v4.4");
        versions.put("4.5", "v4.5");
        versions.put("4.6", "v4.6");
        versions.put("4.7", "v4.7");
        versions.put("4.8", "v4.8");
        versions.put("4.9", "v4.9");
        versions.put("4.10", "v4.10");
        versions.put("4.11", "v4.11");
        versions.put("4.12", "v4.12");
        versions.put("4.13", "v4.13");
        versions.put("4.14", "v4.14");
        versions.put("4.15", "v4.15");
        return versions;
    }
    
    public static final TreeMap<Long, String> WIRESHARK_VERSION() {
        TreeMap<Long, String> versions = new TreeMap<>();
        versions.put(905909955L, "ethereal-0.3.15");
        versions.put(911433534L, "ethereal-0.5.0");
        versions.put(925500716L, "ethereal-0.6.0");
        versions.put(933717600L, "ethereal-0.7.0");
        versions.put(946508400L, "ethereal-0.8.0");
        versions.put(1009148400L, "ethereal-0.9.0");
        versions.put(1071270000L, "ethereal-0.10.0");
        versions.put(1145829600L, "ethereal-0.99.0");
        versions.put(1206745200L, "wireshark-1.0.0");
        versions.put(1245103200L, "wireshark-1.2.0");
        versions.put(1283119200L, "wireshark-1.4.0");
        versions.put(1307397600L, "wireshark-1.6.0");
        versions.put(1340229600L, "wireshark-1.8.0");
        versions.put(1370383200L, "wireshark-1.10.0");
        versions.put(1392159600L, "v1.11.0");
        versions.put(1406757600L, "v1.12.0");
        versions.put(1412632800L, "v1.99.0");
        versions.put(1447801200L, "v2.0.0");
        versions.put(1465336800L, "v2.1.0");
        versions.put(1473199200L, "v2.2.0");
        versions.put(1500415200L, "v2.4.0");
        versions.put(1517871600L, "v2.5.0");

        return versions;
    }

    public static final TreeMap<Long, String> OPENSSL_VERSION() {
        TreeMap<Long, String> versions = new TreeMap<>();
        versions.put(927496800L, "OpenSSL_0_9_3");
        versions.put(934149600L, "OpenSSL_0_9_4");
        versions.put(959205600L, "OpenSSL_0_9_5");
        versions.put(971128800L, "OpenSSL_0_9_6");
        versions.put(1041289200L, "OpenSSL_0_9_7");
        versions.put(1120514400L, "OpenSSL_0_9_8");
        versions.put(1269813600L, "OpenSSL_1_0_0");
        versions.put(1331679600L, "OpenSSL_1_0_1");
        versions.put(1421881200L, "OpenSSL_1_0_2");
        versions.put(1472076000L, "OpenSSL_1_1_0");
        return versions;
    }
    
    public static final Map<String, String> WIRESHARK_CVE_VERSION() {
        Map<String, String> versions = new HashMap<>();
        versions.put("0.3.15", "ethereal-0.3.15");
        versions.put("0.5.0", "ethereal-0.5.0");
        versions.put("0.6.0", "ethereal-0.6.0");
        versions.put("0.7.0", "ethereal-0.7.0");
        versions.put("0.8.0", "ethereal-0.8.0");
        versions.put("0.9.0", "ethereal-0.9.0");
        versions.put("0.10.0", "ethereal-0.10.0");
        versions.put("0.99.0", "ethereal-0.99.0");
        versions.put("1.0.0", "wireshark-1.0.0");
        versions.put("1.2.0", "wireshark-1.2.0");
        versions.put("1.4.0", "wireshark-1.4.0");
        versions.put("1.6.0", "wireshark-1.6.0");
        versions.put("1.8.0", "wireshark-1.8.0");
        versions.put("1.10.0", "wireshark-1.10.0");
        versions.put("1.11.0", "v1.11.0");
        versions.put("1.12.0", "v1.12.0");
        versions.put("1.99.0", "v1.99.0");
        versions.put("2.0.0", "v2.0.0");
        versions.put("2.1.0", "v2.1.0");
        versions.put("2.2.0", "v2.2.0");
        versions.put("2.4.0", "v2.4.0");
        versions.put("2.5.0", "v2.5.0");

        return versions;
    }

    public static final Map<String, String> OPENSSL_CVE_VERSION() {
        Map<String, String> versions = new HashMap<>();
        versions.put("0.9.3", "OpenSSL_0_9_3");
        versions.put("0.9.4", "OpenSSL_0_9_4");
        versions.put("0.9.5", "OpenSSL_0_9_5");
        versions.put("0.9.6", "OpenSSL_0_9_6");
        versions.put("0.9.7", "OpenSSL_0_9_7");
        versions.put("0.9.8", "OpenSSL_0_9_8");
        versions.put("1.0.0", "OpenSSL_1_0_0");
        versions.put("1.0.1", "OpenSSL_1_0_1");
        versions.put("1.0.2", "OpenSSL_1_0_2");
        versions.put("1.1.0", "OpenSSL_1_1_0");
        return versions;
    }
     */
}
