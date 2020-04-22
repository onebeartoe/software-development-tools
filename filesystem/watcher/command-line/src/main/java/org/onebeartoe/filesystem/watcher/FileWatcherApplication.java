
package org.onebeartoe.filesystem.watcher;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.cli.Options;
import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.RunProfile;
import org.onebeartoe.application.logging.SysoutLoggerFactory;

public class FileWatcherApplication extends CommandLineInterfaceApplet
{
	@Override
	public Options buildOptions() 
	{
		return new Options();
	}

	public static void main(String [] args) throws IOException, Exception
    {
		CommandLineInterfaceApplet app = new FileWatcherApplication();
		
		app.execute(args);
	}

	@Override
	protected FileWatcherProfile parseRunProfile(final String[] args, Options options) throws InvalidFileWatcherParamsException
    {
		System.out.println("^^^^^^^^^^^^invalid watcher params");

		return null;
	}
}
