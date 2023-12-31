package eu.merrymake.service.java;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * MimeType is used to specify the ContentType of a payload. For convenience the
 * most common file extensions are defined as constants.
 *
 * @author Merrymake.eu (Chirstian Clausen, Nicolaj Græsholt)
 */
public class MimeType {

    public static final MimeType aac = new MimeType("audio", "aac");
    public static final MimeType abw = new MimeType("application", "x-abiword");
    public static final MimeType arc = new MimeType("application", "x-freearc");
    public static final MimeType avif = new MimeType("image", "avif");
    public static final MimeType avi = new MimeType("video", "x-msvideo");
    public static final MimeType azw = new MimeType("application", "vnd.amazon.ebook");
    public static final MimeType bin = new MimeType("application", "octet-stream");
    public static final MimeType bmp = new MimeType("image", "bmp");
    public static final MimeType bz = new MimeType("application", "x-bzip");
    public static final MimeType bz2 = new MimeType("application", "x-bzip2");
    public static final MimeType cda = new MimeType("application", "x-cdf");
    public static final MimeType csh = new MimeType("application", "x-csh");
    public static final MimeType css = new MimeType("text", "css");
    public static final MimeType csv = new MimeType("text", "csv");
    public static final MimeType doc = new MimeType("application", "msword");
    public static final MimeType docx = new MimeType("application",
            "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MimeType eot = new MimeType("application", "vnd.ms-fontobject");
    public static final MimeType epub = new MimeType("application", "epub+zip");
    public static final MimeType gz = new MimeType("application", "gzip");
    public static final MimeType gif = new MimeType("image", "gif");
    public static final MimeType htm = new MimeType("text", "html");
    public static final MimeType html = new MimeType("text", "html");
    public static final MimeType ico = new MimeType("image", "vnd.microsoft.icon");
    public static final MimeType ics = new MimeType("text", "calendar");
    public static final MimeType jar = new MimeType("application", "java-archive");
    public static final MimeType jpeg = new MimeType("image", "jpeg");
    public static final MimeType jpg = new MimeType("image", "jpeg");
    public static final MimeType js = new MimeType("text", "javascript");
    public static final MimeType json = new MimeType("application", "json");
    public static final MimeType jsonld = new MimeType("application", "ld+json");
    public static final MimeType mid = new MimeType("audio", "midi");
    public static final MimeType midi = new MimeType("audio", "midi");
    public static final MimeType mjs = new MimeType("text", "javascript");
    public static final MimeType mp3 = new MimeType("audio", "mpeg");
    public static final MimeType mp4 = new MimeType("video", "mp4");
    public static final MimeType mpeg = new MimeType("video", "mpeg");
    public static final MimeType mpkg = new MimeType("application", "vnd.apple.installer+xml");
    public static final MimeType odp = new MimeType("application", "vnd.oasis.opendocument.presentation");
    public static final MimeType ods = new MimeType("application", "vnd.oasis.opendocument.spreadsheet");
    public static final MimeType odt = new MimeType("application", "vnd.oasis.opendocument.text");
    public static final MimeType oga = new MimeType("audio", "ogg");
    public static final MimeType ogv = new MimeType("video", "ogg");
    public static final MimeType ogx = new MimeType("application", "ogg");
    public static final MimeType opus = new MimeType("audio", "opus");
    public static final MimeType otf = new MimeType("font", "otf");
    public static final MimeType png = new MimeType("image", "png");
    public static final MimeType pdf = new MimeType("application", "pdf");
    public static final MimeType php = new MimeType("application", "x-httpd-php");
    public static final MimeType ppt = new MimeType("application", "vnd.ms-powerpoint");
    public static final MimeType pptx = new MimeType("application",
            "vnd.openxmlformats-officedocument.presentationml.presentation");
    public static final MimeType rar = new MimeType("application", "vnd.rar");
    public static final MimeType rtf = new MimeType("application", "rtf");
    public static final MimeType sh = new MimeType("application", "x-sh");
    public static final MimeType svg = new MimeType("image", "svg+xml");
    public static final MimeType tar = new MimeType("application", "x-tar");
    public static final MimeType tif = new MimeType("image", "tiff");
    public static final MimeType tiff = new MimeType("image", "tiff");
    public static final MimeType ts = new MimeType("video", "mp2t");
    public static final MimeType ttf = new MimeType("font", "ttf");
    public static final MimeType txt = new MimeType("text", "plain");
    public static final MimeType vsd = new MimeType("application", "vnd.visio");
    public static final MimeType wav = new MimeType("audio", "wav");
    public static final MimeType weba = new MimeType("audio", "webm");
    public static final MimeType webm = new MimeType("video", "webm");
    public static final MimeType webp = new MimeType("image", "webp");
    public static final MimeType woff = new MimeType("font", "woff");
    public static final MimeType woff2 = new MimeType("font", "woff2");
    public static final MimeType xhtml = new MimeType("application", "xhtml+xml");
    public static final MimeType xls = new MimeType("application", "vnd.ms-excel");
    public static final MimeType xlsx = new MimeType("application",
            "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MimeType xml = new MimeType("application", "xml");
    public static final MimeType xul = new MimeType("application", "vnd.mozilla.xul+xml");
    public static final MimeType zip = new MimeType("application", "zip");
    public static final MimeType _3gp = new MimeType("video", "3gpp");
    public static final MimeType _3g2 = new MimeType("video", "3gpp2");
    public static final MimeType _7z = new MimeType("application", "x-7z-compressed");

    static final Map<String, MimeType> ext2mime = Collections.unmodifiableMap(new HashMap<String, MimeType>() {
        {
            put("aac", aac);
            put("abw", abw);
            put("arc", arc);
            put("avif", avif);
            put("avi", avi);
            put("azw", azw);
            put("bin", bin);
            put("bmp", bmp);
            put("bz", bz);
            put("bz2", bz2);
            put("cda", cda);
            put("csh", csh);
            put("css", css);
            put("csv", csv);
            put("doc", doc);
            put("docx", docx);
            put("eot", eot);
            put("epub", epub);
            put("gz", gz);
            put("gif", gif);
            put("htm", htm);
            put("html", html);
            put("ico", ico);
            put("ics", ics);
            put("jar", jar);
            put("jpeg", jpeg);
            put("jpg", jpg);
            put("js", js);
            put("json", json);
            put("jsonld", jsonld);
            put("mid", mid);
            put("midi", midi);
            put("mjs", mjs);
            put("mp3", mp3);
            put("mp4", mp4);
            put("mpeg", mpeg);
            put("mpkg", mpkg);
            put("odp", odp);
            put("ods", ods);
            put("odt", odt);
            put("oga", oga);
            put("ogv", ogv);
            put("ogx", ogx);
            put("opus", opus);
            put("otf", otf);
            put("png", png);
            put("pdf", pdf);
            put("php", php);
            put("ppt", ppt);
            put("pptx", pptx);
            put("rar", rar);
            put("rtf", rtf);
            put("sh", sh);
            put("svg", svg);
            put("tar", tar);
            put("tif", tif);
            put("tiff", tiff);
            put("ts", ts);
            put("ttf", ttf);
            put("txt", txt);
            put("vsd", vsd);
            put("wav", wav);
            put("weba", weba);
            put("webm", webm);
            put("webp", webp);
            put("woff", woff);
            put("woff2", woff2);
            put("xhtml", xhtml);
            put("xls", xls);
            put("xlsx", xlsx);
            put("xml", xml);
            put("xul", xul);
            put("zip", zip);
            put("3gp", _3gp);
            put("3g2", _3g2);
            put("7z", _7z);
        }
    });

    private final String type;
    private final String tail;

    /**
     * Specify a content-type like application/json
     *
     * @param type the part before /
     * @param tail the part after /
     */
    public MimeType(String type, String tail) {
        this.type = type;
        this.tail = tail;
    }

    @Override
    public String toString() {
        return this.type + "/" + this.tail;
    }
}
