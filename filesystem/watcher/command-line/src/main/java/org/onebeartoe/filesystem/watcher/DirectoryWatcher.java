
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.onebeartoe.application.logging.SysoutLoggerFactory;
import org.onebeartoe.io.TextFileWriter;
import org.onebeartoe.system.Commander;


//TODO: oneweb.net - Java -> NIO -> WatcherService -> Mention this as example code for java.nio.file.WatchService
//
//           https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java


/**
 * This class provides a way to watch a filesystem for changes and run  system commands 
 * when a filesystem change occurs under the user specified directory.
 * 
 * @author Roberto Marquez
 */
public class DirectoryWatcher
{
    private final WatchService watcher;

    private final Map<WatchKey,Path> keys;

    private List<WatcherItem> watchItems;
    
    private final boolean recursive;

    private boolean trace = false;

    private boolean processing = true;
    
    private Logger logger;

    /**
     * Creates a WatchService and registers the given directory
     */
    public DirectoryWatcher(DirectoryWatcherProfile profile) throws IOException 
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );
        
        this.watcher = FileSystems.getDefault().newWatchService();

        this.keys = new HashMap<WatchKey,Path>();

        this.recursive = profile.recursive;

        this.watchItems = profile.watchItems;
        
        if (recursive) 
        {
            System.out.format("Scanning %s ...\n", profile.directory);
            registerAll(profile.directory);
            System.out.println("Done.");
        } 
        else 
        {
            register(profile.directory);
        }

        // enable trace after initial registration
        this.trace = true;
    }
    
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) 
    {
        return (WatchEvent<T>)event;
    }

    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) 
        {
            Path prev = keys.get(key);
            if (prev == null) 
            {
                System.out.format("register: %s\n", dir);
            } else 
            {
                if (!dir.equals(prev)) 
                {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException 
    {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() 
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Process all events for keys queued to the watcher
     */
    public void processEvents() 
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                processEventsLoop();
            }
        };
        
        Thread thread = new Thread(runnable);
        
        thread.start();
    }
    
    private void processEventsLoop()
    {
        logger.info("processing event loop");
        
        while (processing) 
        {
System.out.println("proccessing...");   
            // wait for key to be signalled
            WatchKey key;
            try 
            {
                key = watcher.take();
            } 
            catch (InterruptedException x) 
            {
                x.printStackTrace();
                
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) 
            {
                System.err.println("WatchKey not recognized!!");
                
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) 
            {
                WatchEvent.Kind kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) 
                {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                
                Path name = ev.context();
                
                Path child = dir.resolve(name);

                // print out event
                System.out.format("event: %s - %s\n", event.kind().name(), child);

                try
                {
System.out.println("proccess modification");
                    processModification(child);
                } 
                catch (IOException | InterruptedException ex)
                {
ex.printStackTrace();
                    
                    String message = ex.getMessage();
                    
                    logger.log(Level.SEVERE, message, ex);
                }
                
                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) 
                {
                    try 
                    {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) 
                        {
                            registerAll(child);
                        }
                    } 
                    catch (IOException x) 
                    {
                        // ignore to keep sample readbale
                    }
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) 
            {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) 
                {
                    break;
                }
            }
        }
        
        System.out.println("exiting process loop");
    }

    private void processModification(Path child) throws IOException, InterruptedException
    {
        File file = child.toFile();
        
        if( file.isFile() )
        {
            String name = file.getName();
            
System.out.println("\tname: " + name);            

int watchItemCount = watchItems.size();
System.out.println("watchItemCount = " + watchItemCount);   

            for(WatcherItem item : watchItems)
            {
                String regularExpression = item.pattern.startsWith("*.") ? item.pattern.replace("*.", "\\\\*.") 
                                                              : item.pattern;
                
System.out.println("regularExpression = " + regularExpression);   
                
                Pattern pattern = Pattern.compile(regularExpression);
                     
                Matcher foundMatcher = pattern.matcher(name);
                                
                if( foundMatcher.find() )
                {
                    System.out.println("we've got a match: " + name);
                    
                    long lastQuietPeriod = item.lastQuitePeriodEnded.getTimeInMillis();
                    
                    Calendar now = Calendar.getInstance();
                    
                    long nowMillis = now.getTimeInMillis();
                    
                    boolean quietPeriodHasEnded = nowMillis > lastQuietPeriod + item.quietPeriod.toMillis();
                    
                    if(quietPeriodHasEnded)
                    {
                        scheduleCommand(item, now);
                        
                        item.lastQuitePeriodEnded = now;
                    }
                    else
                    {
                        resetSchedule(item);
                    }
                }
                else
                {
                    System.out.println("chale tamale");
                }
            }
        }
        else
        {
            System.out.println("file is not a file????   ");
        }
    }    

    void terminate()
    {
        processing = false;
    }

//TODO: move into ABC order    
    private void executeCommand(WatcherItem item) throws IOException, InterruptedException
    {
        System.out.println("executing command");
        
        Commander commander = new Commander();

        String command = item.command;

        int exitValue = commander.executeCommand(command);

        System.out.println("exitValue = " + exitValue);

        System.out.println("error:");
        commander.getStderr()
                 .forEach(System.out::println);

        System.out.println("sysout:");
        commander.getStdout()
                 .forEach(System.out::println);
    }

    private void logReset(WatcherItem item) throws IOException
    {
        String message = "quitePeriodRestartMessage " + "#" + item.resetCount;
        
        if(item.logPath == null)
        {
            System.out.println("No reset log path for item: " + item.pattern);
        }
        else
        {
            File outfile = new File(item.logPath);

            TextFileWriter appender = new TextFileWriter();

            boolean append = true;

            appender.writeText(outfile, message, append);
        }
    }

    private void scheduleCommand(WatcherItem item, Calendar when)
    {
        System.out.println("scheduling watch item");
        
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    executeCommand(item);
                } 
                catch (IOException | InterruptedException ex)
                {
                    Logger.getLogger(DirectoryWatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        item.timerTask = task;
        
        Timer timer = new Timer();
        
        Calendar now = Calendar.getInstance();
        
        long delay = when.getTimeInMillis() - now.getTimeInMillis();
     
        if(delay < 0)
        {
            // avoid negative delay values
            delay = 0;
        }
        
        timer.schedule(task, delay);
    }

    private void resetSchedule(WatcherItem item)
    {
        System.out.println("resetting schedule");
                        
        item.timerTask.cancel();
        
        item.resetCount++;
        
        Calendar now = Calendar.getInstance();
        
        long nowInMillis = now.getTimeInMillis();
        
        long delay = item.quietPeriod.toMillis();
        
        Calendar when = Calendar.getInstance();

        when.setTimeInMillis(nowInMillis + delay);
        
        try
        {
            logReset(item);
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        scheduleCommand(item, when);                
    }
}
