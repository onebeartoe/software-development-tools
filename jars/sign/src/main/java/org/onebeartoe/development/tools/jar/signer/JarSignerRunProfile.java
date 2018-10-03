/*
 */
package org.onebeartoe.development.tools.jar.signer;

import org.onebeartoe.network.mail.RunProfile;

/**
 *
 * @author Roberto Marquez
 */
public class JarSignerRunProfile extends RunProfile
{
    private String jarsPath;
    
    private String alias;
    
    private String keystore;
    
    private String storepass;
    
    private String keypass;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStorepass() {
        return storepass;
    }

    public void setStorepass(String storepass) {
        this.storepass = storepass;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getKeypass() {
        return keypass;
    }

    public void setKeypass(String keypass) {
        this.keypass = keypass;
    }
    
    public String getJarsPath()
    {
        return jarsPath;
    }

    public void setJarsPath(String path)
    {
        this.jarsPath = path;
    }
}
