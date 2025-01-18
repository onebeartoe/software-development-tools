
public class GradelDependenciesToMaven
{
    /**
     * These gradle dependencies are from this project:
     * 
     *      https://github.com/java-decompiler/jd-core/blob/master/build.gradle
     * 
     */
    static String input = """
        testCompile 'commons-codec:commons-codec:1.13'
        testCompile 'org.apache.commons:commons-collections4:4.1'
        testCompile 'org.apache.commons:commons-imaging:1.0-alpha1'
        testCompile 'org.apache.commons:commons-lang3:3.9'
        testCompile 'com.jakewharton:disklrucache:2.0.2'
        testCompile 'com.squareup:javapoet:1.11.1'
        testCompile 'com.squareup:javawriter:2.5.1'
        testCompile 'joda-time:joda-time:2.10.5'
        testCompile 'org.joda:joda-convert:2.2.1'
        testCompile 'org.jsoup:jsoup:1.12.1'
        testCompile 'junit:junit:4.12'
        testCompile 'javax.jms:javax.jms-api:2.0.1'
        testCompile 'javax.mail:javax.mail-api:1.6.2'
        testCompile 'com.squareup.mimecraft:mimecraft:1.1.1'
        testCompile 'org.scribe:scribe:1.3.7'
        testCompile 'com.sparkjava:spark-core:2.9.1'
        testCompile 'log4j:log4j:1.2.17'
        testCompile 'com.google.guava:guava:12.0'                          
                   """;
    
    static String gavFormat = """
        <dependency>
            <groupId>%s</groupId>
            <artifactId>%s</artifactId>
            <version>%s</version>
        </dependency>
                              """;
    
    public static void main(String[] args) 
    {
        input.lines()
                .forEach(l -> 
                {
                    String [] lineSplit = l.trim().split(" ");
                    
                    var gradleDep = lineSplit[1];
                    
                    var gavStr = gradleDep.replaceAll("'", "");
                    
                    String [] gavCoordinates = gavStr.split(":");
                    
                    var groupId = gavCoordinates[0];
                    var artifactId = gavCoordinates[1];
                    var version = gavCoordinates[2];
                    
                    var mavenDep = String.format(gavFormat, groupId, artifactId, version);
                    
                    System.out.println(mavenDep);
//                    System.out.println();
                });
        
        System.out.println("end of run");
    }
    
    record GavCoordinate(String groupId, String artifactId, String version)
    {
        
    }
}

