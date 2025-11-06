import java.util.*;
import java.util.regex.*;

public class MapReduceWordCount {

    // --- Step 1: Mapper Function ---
    // Takes a document and emits (word, 1) pairs
    public static List<AbstractMap.SimpleEntry<String, Integer>> mapper(String document) {
        List<AbstractMap.SimpleEntry<String, Integer>> pairs = new ArrayList<>();

        // Normalize text: lowercase and remove punctuation
        String cleanedText = document.toLowerCase().replaceAll("[^\w\s]", "");
        String[] words = cleanedText.split("\\s+");

        // Emit (word, 1)
        for (String word : words) {
            if (!word.isEmpty()) {
                pairs.add(new AbstractMap.SimpleEntry<>(word, 1));
            }
        }

        return pairs;
    }

    // --- Step 3: Reducer Function ---
    // Takes a key (word) and list of counts, returns total count
    public static AbstractMap.SimpleEntry<String, Integer> reducer(String key, List<Integer> values) {
        int total = 0;
        for (int val : values) {
            total += val;
        }
        return new AbstractMap.SimpleEntry<>(key, total);
    }

    // --- Main Function to Simulate MapReduce Flow ---
    public static void main(String[] args) {
        // Input Documents
        String[] documents = {
            "Data science is the future of technology and business intelligence.",
            "Machine learning algorithms can analyze massive amounts of data efficiently.",
            "Big data analytics helps companies make better business decisions.",
            "Python programming is essential for data science and machine learning projects."
        };

        System.out.println("--- Input Documents ---");
        for (String doc : documents) {
            System.out.println("- " + doc);
        }
        System.out.println("
" + "=".repeat(30) + "
");

        // --- 1. MAP PHASE ---
        System.out.println("--- 1. Map Phase ---");
        List<AbstractMap.SimpleEntry<String, Integer>> mappedPairs = new ArrayList<>();

        for (String doc : documents) {
            List<AbstractMap.SimpleEntry<String, Integer>> pairs = mapper(doc);
            for (AbstractMap.SimpleEntry<String, Integer> pair : pairs) {
                mappedPairs.add(pair);
                System.out.println("  Mapper output: " + pair);
            }
        }

        System.out.println("
" + "=".repeat(30) + "
");

        // --- 2. SHUFFLE & SORT PHASE ---
        System.out.println("--- 2. Shuffle & Sort Phase (Grouping) ---");
        Map<String, List<Integer>> shuffledData = new TreeMap<>();

        for (AbstractMap.SimpleEntry<String, Integer> pair : mappedPairs) {
            String key = pair.getKey();
            int value = pair.getValue();
            shuffledData.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        for (Map.Entry<String, List<Integer>> entry : shuffledData.entrySet()) {
            System.out.println("  Grouped: ('" + entry.getKey() + "', " + entry.getValue() + ")");
        }

        System.out.println("
" + "=".repeat(30) + "
");

        // --- 3. REDUCE PHASE ---
        System.out.println("--- 3. Reduce Phase ---");
        Map<String, Integer> finalCounts = new TreeMap<>();

        for (Map.Entry<String, List<Integer>> entry : shuffledData.entrySet()) {
            AbstractMap.SimpleEntry<String, Integer> result = reducer(entry.getKey(), entry.getValue());
            finalCounts.put(result.getKey(), result.getValue());
            System.out.println("  Reducer output: ('" + result.getKey() + "', " + result.getValue() + ")");
        }

        System.out.println("
" + "=".repeat(30) + "
");

        // --- Final Output ---
        System.out.println("--- Final Word Counts ---");
        for (Map.Entry<String, Integer> entry : finalCounts.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}