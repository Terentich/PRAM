package org.terentich.pram.tookit;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.jar.*;

import org.apache.commons.lang.CharEncoding;

/**
 * <b> Project: </b> Application <br>
 * <b> Description: </b> <br>
 * <b> License: </b> <br>
 * <b> Date: </b> 29.09.2010 22:41:14 <br>
 * 
 * @author <a href="mailto:terentich@gmail.com">Alexey V. Terentyev</a>
 */
public class MetaReader {

    public static final String NAME         = "Implementation-Title";
    public static final String VERSION      = "Implementation-Version";
    public static final String BUILD_DATE   = "Build-Date";
    public static final String SCM_REVISION = "SCM-Revision";
    public static final String CLASSPATH    = "Class-Path";
    public static final String BUILT_BY     = "Build-By";
    public static final String BUILD_JDK    = "Build-Jdk";
    public static final String VENDOR_ID    = "Implementation-Vendor-Id";
    public static final String UNKNOWN      = "N/a";

    public static final String VENDOR       = "Implementation-Vendor";
    public static final String LICENSE      = "License";
    public static final String DESCRIPTION  = "DESCRIPTION";

    private Manifest           manifest;
    private Attributes         attributes;

    public MetaReader() {
        this(getCurrentJarManifest());
    }

    public MetaReader(Manifest manifest) {
        this.manifest = manifest;
        readManifest();

    }

    private static Manifest getCurrentJarManifest() {

        Manifest manifest = null;
        CodeSource code = MetaReader.class.getProtectionDomain()
                .getCodeSource();

        try {
            String currentFile = URLDecoder.decode(
                    code.getLocation().getFile(), CharEncoding.UTF_8);

            if (currentFile.endsWith(".jar")) {
                JarFile jar = new JarFile(currentFile);
                manifest = jar.getManifest();
            }
        } catch (IOException e) {
            System.out.println("An exception occurs: " + e);
        }

        return manifest;
    }

    private void readManifest() {
        if (manifest != null) {
            attributes = manifest.getMainAttributes();
        }
    }

    public String getApplicationName() {
        return getValue(NAME);
    }

    public String getApplicationVersion() {
        return getValue(VERSION);
    }

    public String getApplicationBuildDate() {
        return getValue(BUILD_DATE);
    }

    public String getApplicationRevision() {
        return getValue(SCM_REVISION);
    }

    public String getApplicationClasspath() {
        return getValue(CLASSPATH);
    }

    public String getApplicationBuiltBy() {
        return getValue(BUILT_BY);
    }

    public String getApplicationBuildJDK() {
        return getValue(BUILD_JDK);
    }

    public String getApplicationVendor() {
        return getValue(VENDOR);
    }

    public String getApplicationVendorId() {
        return getValue(VENDOR_ID);
    }

    public String getApplicationLicense() {
        return getValue(LICENSE);
    }

    public String getApplicationDescription() {
        return getValue(DESCRIPTION);
    }

    public String getValue(String name) {
        String value = null;

        if (attributes != null) {
            value = attributes.getValue(name);

            if (value == null) {
                value = UNKNOWN;
            }
        } else {
            value = UNKNOWN;
        }

        return value;
    }

}
