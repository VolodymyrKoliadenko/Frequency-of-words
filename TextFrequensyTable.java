package textfrequensytable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;

/**
 *
 * @author Koliadenko
 */
public class TextFrequensyTable {

    static HashMap<String, Integer> words = new HashMap<>();
    static LinkedHashMap<String, Integer> sortedMap;   //linked - iterator
    static long itogo = 0;
    static int unicalWords = 0;
    static String METHOD = "internet";

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        List<String> lines = new ArrayList<>();
        Path path = FileSystems.getDefault().getPath("d:/2.txt");

        if (METHOD.equals("internet")) {
            URL url = new URL("http://samlib.ru/r/runa_a/dorian.shtml");
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), "CP1251"))) {
                boolean text = false;
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("<body")) {
                        text = true;
                    }
                    if (line.contains("</body>")) {
                        break;
                    }
                    if (text) {
                        line = line.replaceAll("&nbsp;", "");
                        line = line.replaceAll("<dd>", "");
                        line = line.replaceAll("html", "");
                        //System.out.println(line);
                        lines.add(line);
                    }
                }
            }

        } else {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            path = f.toPath();
            lines = Files.readAllLines(path, Charset.forName("UTF-8"));
        }
        StringTokenizer st;
        for (String str : lines) {
            st = new StringTokenizer(str, " .,!?\"()«»—–:");
            while (st.hasMoreTokens()) {
                addWord(st.nextToken());
            }
        }
        sortedMap = words.entrySet().stream().
                sorted(Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        int maxCount = 40;
        for (Map.Entry<String, Integer> e : sortedMap.entrySet()) {
            if (maxCount-- < 0) {
                break;
            }
            double freq = Math.round(e.getValue() * 10000 / itogo) / 100.0;
            System.out.println(e.getKey() + " : " + freq + " %");
        }
        System.out.println(itogo + " total (long) words. Unical words: " + unicalWords);
        new frameDiagramm();

        //add Histogram
    }

    public static void addWord(String s) {
        s = s.toLowerCase();
        if (s.length() < 3) { //раньше отсекались только 0
            return;
        }
        itogo++;
        Integer i = words.get(s);
        if (i != null) {
            words.put(s, i + 1);
        } else {
            words.put(s, 1);
            unicalWords++;
        }
    }

}
