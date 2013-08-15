/**
 * Created with IntelliJ IDEA.
 * User: gh0st
 * Date: 15.08.13
 * Time: 0:48
 */

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class scParser {

    public static void main(String args[]){
        System.out.println("Parsing M3U file...");
        String name;
        try {
            File f = new File(args[0]);
            M3U_Parser mpt = new M3U_Parser();
            M3U_Parser.M3UHolder songs = mpt.parseFile(f);
            for (int n = 0; n < songs.getSize(); n++) {
                System.out.println("Downloading " + songs.getName(n) + " (" + (n + 1) + "/" + songs.getSize() + ")");
                name = songs.getName(n);
                name = name.replaceAll("\\*", "-");
                name = name.replaceAll(":", "-");
                name = name.replaceAll("/", "-");
                name = name.replaceAll("\\\\", "-");
                name = name.replaceAll("\"", "\'\'");
                name = name.replaceAll("\\|", "-");
                name = name.replaceAll("<", "-");
                name = name.replaceAll(">", "-");
                name = name.replaceAll("\\?", "-");
                URL website = new URL(songs.getUrl(n));
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(args[1] + name + ".mp3");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
