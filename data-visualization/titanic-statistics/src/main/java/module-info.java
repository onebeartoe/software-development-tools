module org.onebeartoe.development.tools.titanic
{
    requires java.sql;
    
    // you can find the 'requires' for a non-Java-module JAR by issusing this command:
    //         $ jar --file=/home/roberto/.m2/repository/com/opencsv/opencsv/5.3/opencsv-5.3.jar --describe-module
    // Then you copy and paste whatever is before the '@' symbol.
    requires  opencsv;    
    
    exports org.onebeartoe.development.tools.titanic;
    exports org.onebeartoe.development.tools.titanic.statistics;
}
