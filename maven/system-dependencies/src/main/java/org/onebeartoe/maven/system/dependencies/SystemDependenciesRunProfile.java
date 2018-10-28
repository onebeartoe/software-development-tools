
package org.onebeartoe.maven.system.dependencies;

import org.onebeartoe.application.RunProfile;

public class SystemDependenciesRunProfile extends RunProfile
{
    private String projectRoot;

    private String jarSubpath;

    public String getProjectRoot() {
        return projectRoot;
    }

    public void setProjectRoot(String projectRoot) {
        this.projectRoot = projectRoot;
    }

    public String getJarSubpath() {
        return jarSubpath;
    }

    public void setJarSubpath(String jarSubpath) {
        this.jarSubpath = jarSubpath;
    }
}
