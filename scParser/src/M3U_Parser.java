/**
 * Created with IntelliJ IDEA.
 * User: gh0st
 * Date: 15.08.13
 * Time: 1:06
 */

// Original - https://code.google.com/p/java-m3u-file-parser/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class M3U_Parser {

    public M3U_Parser() throws Exception {

    }

    public String convertStreamToString(java.io.InputStream is) {
        try {
            return new java.util.Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

    public M3UHolder parseFile(File f) throws FileNotFoundException {
        if (f.exists()) {
            String stream = convertStreamToString(new FileInputStream(f));
            stream = stream.replaceAll("#EXTM3U", "").trim();
            String[] arr = stream.split("#EXTINF.*,");
            String urls = "", data = "";
            // clean
            {
                for (String element : arr)
                    if (element.contains("http")) {
                        String nu = element.substring(element.indexOf("http://"), element.indexOf("?client_id=") + 43); //,arr[n].indexOf(".mp3") + 4

                        data = data.concat(element.replaceAll("http://.*", "").trim()).concat("&&&&");
                        urls = urls.concat(nu);
                        urls = urls.concat("####");
                    }
            }
            return new M3UHolder(data.split("&&&&"), urls.split("####"));
        }
        return null;
    }

    public class M3UHolder {
        private String[] data, url;

        public M3UHolder(String[] names, String[] urls) {
            this.data = names;
            this.url = urls;
        }

        int getSize() {
            if (url != null)
                return url.length;
            return 0;
        }

        String getName(int n) {
            return data[n];
        }

        String getUrl(int n) {
            return url[n];
        }
    }
}