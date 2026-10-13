
[x] do not look outside of this directory. update the pom.xml to specify Java 8 as the release target.

[x] Create Java application test infrastructure with one simple unit test.  Use AssertJ as the testing framework dependency.

[] This command line application takes two arguments.  The first is the path to a Tomcat webapps/ directory.  The second is the search target string.

[] The application takes the webapps/ directory path and finds any directories directly under it.

	[] for each directory under webapps/, the application looks for a directory path of 'WEB-INF/lib/'.
	
		[] If 'WEB-INF/lib/ exists, then the application next keeps track of evey JAR file under 'WEB-INF/lib/' that has the search target string in the JAR file name.

[] Implement the features so that all unit tests pass.

[] The output of the application is a list of any files matching the search target, broken down by each directory under webapps/

