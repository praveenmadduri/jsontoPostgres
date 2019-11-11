package com.pratice;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
 
public class Java8WatchServiceExample {
 
    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    public  ArrayList<Long> Timetamp;
     String lastmodifed ;
     Long previousnumber ;
    Long inputnumber;
    Long Currentnumber;
     
   
  private String newdate;
 
    /**
     * Creates a WatchService and registers the given directory
     */
    Java8WatchServiceExample(Path dir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
       
 
        walkAndRegisterDirectories(dir);
    }
 
    /**
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     */
    private void registerDirectory(Path dir) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void walkAndRegisterDirectories(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
 
    /**
     * Process all events for keys queued to the watcher
     * @throws IOException 
     */
    void processEvents() throws IOException {
        for (;;) {
 
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
 
            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
 
            for (WatchEvent<?> event : key.pollEvents()) {
                @SuppressWarnings("rawtypes")
                WatchEvent.Kind kind = event.kind();
 
                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                FileTime fileTime;
                
               /* fileTime = Files.getLastModifiedTime(child);
                System.out.println("filetime is "+fileTime);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
                System.out.println(dateFormat.format(fileTime.toMillis()));
                System.out.println("Action Taken"+event.kind().name()+"--->File Name<----"+ child.getFileName()+"------->LastModifed time is<-----------"+dateFormat.format(fileTime.toMillis()));
                // print out event
*/                System.out.format("%s: %s\n", event.kind().name(), child);
if (kind == ENTRY_DELETE) {
	System.out.format("%s: %s\n", event.kind().name(), child);
}
else {
fileTime = Files.getLastModifiedTime(child);
System.out.println("filetime is "+fileTime);
DateFormat dateFormat = new SimpleDateFormat("YYYYMMDDHHMMSS");
//dd/MM/yyyy - hh:mm:ss
System.out.println(dateFormat.format(fileTime.toMillis()));
String times = dateFormat.format(fileTime.toMillis());
 long timesteamp1=Long.parseLong(times);
//Stamps timestamps = new Stamps();
//Stamps timestamps = new Stamps();
 
String lastdate;

lastdate=dateFormat.format(fileTime.toMillis());

long l=Long.parseLong(times); 
/*timestamps.Stem(lastdate);
lastmodifed=timestamps.Stem(lastdate);*/
this.Timetamp =new ArrayList<Long>();
Timetamp.add(l);

Long inputNumber =(long) 12345;
this.previousnumber = Currentnumber;
this.Currentnumber = Timetamp.get(0);

System.out.println("<------previous number is --->"+previousnumber +"<----- Current number is"+Currentnumber);
Long timestamp2 = Timetamp.get(0);

/*if ( timesteamp1 < timestamp2 ) {
	  //timestamp2 is later than timestamp1
	System.out.println(" newModifed time new value--->"+Timetamp.get(0) +" Old timestamp is  timestamp1 --> " +timesteamp1 +"new timestamp is timse stamp2-->"+ timestamp2 );
	}*/
//Timestamp  tez= 
//lastdate = timestamps.setLastmodified(dateFormat.format(fileTime.toMillis()));

lastmodifed=times;

//System.out.println("Last Date is"+lastdate);
System.out.println("Action Taken"+event.kind().name()+"--->File Name<----"+ child.getFileName()+"------->LastModifed time is<-----------"+times); }
// print out event
Map<List<String>,String> Stampes = new HashMap<List<String>,String>();
List<String> list = new ArrayList<>();
list.add(lastmodifed);
   Stampes.put(list,child.getFileName().toString());

   
System.out.println("Action Taken in map is"+ Stampes.get(child.getFileName().toString()));
                // if directory is created, and watching recursively, then register it and its sub-directories
                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child)) {
                            walkAndRegisterDirectories(child);
                        }
                    } catch (IOException x) {
                        // do something useful
                    }
                }
                
               if (kind == ENTRY_DELETE) {
            	   
               }
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
 
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }
 
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("E://Report_Sonar/");
        new Java8WatchServiceExample(dir).processEvents();
    }
}