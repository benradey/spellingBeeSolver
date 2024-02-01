import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String filePath = "words_alpha.txt";
        String letters = "pittance";

        Map<String, Set<String>> map = new HashMap<>();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }

            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(streamReader);

            String line;
            while ((line = br.readLine()) != null) {
                String key = wordToKey(line);
                Set<String> words = map.computeIfAbsent(key, k -> new HashSet<>());
                words.add(line);
            }

            Set<Integer> keySet = new HashSet<>();
            letters.chars().forEach(keySet::add);
            List<String> words = new LinkedList<>();
            map.keySet().stream().filter(key -> keySet.containsAll(key.chars().boxed().toList())).forEach(key -> words.addAll(map.get(key)));
            words.stream().sorted().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String wordToKey(String word) {
        Set<String> set = new HashSet<>(Arrays.asList(word.split("")));

        List<String> list = new LinkedList<>(set);
        list.sort(Comparator.naturalOrder());

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }

        return sb.toString();
    }
}