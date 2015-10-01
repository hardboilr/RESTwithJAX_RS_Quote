package facade;

import exception.QuoteNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Tobias Jacobsen
 */
public class Facade {

    private static int index;
    private static Random random = new Random();

    private static Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };

    public static String getQuote(int index) throws QuoteNotFoundException {
        if (quotes.containsKey(index)) {
            return quotes.get(index);
        } else {
            throw new QuoteNotFoundException("Quote with requested id not found");
        }
    }

    public static String getRandomQuote() throws QuoteNotFoundException {
        if (!quotes.isEmpty()) {
            int randomIndex = random.nextInt(quotes.size()) + 1;
            return quotes.get(randomIndex);
        } else {
            throw new QuoteNotFoundException("No Quotes Created yet");
        }
    }

    public static int getQuotesSize() {
        return quotes.size();
    }

    public static void createQuote(String quote) {
        int localId = 0;
        boolean foundEmpty = false;
        for (Map.Entry<Integer, String> entry : quotes.entrySet()) {
            localId = entry.getKey();
            String value = entry.getValue();
            if (value.equals("")) { //found empty spot 
                quotes.put(localId, quote);
                index = localId;
                foundEmpty = true;
            }
        }
        if (!foundEmpty) {
            localId = quotes.size() + 1;
            index = localId;
            quotes.put(localId, quote);
        }
    }

    public static void updateQuote(int id, String quote) throws QuoteNotFoundException {
        if (quotes.containsKey(id)) {
            quotes.put(id, quote);
            index = id;
        } else {
            throw new QuoteNotFoundException("Quote with requested id not found");
        }
    }

    public static void deleteQuote(int id) throws QuoteNotFoundException {
        if (quotes.containsKey(id)) {
            quotes.remove(id);
        } else {
            throw new QuoteNotFoundException("Quote with requested id not found");
        }
    }

    public static int getLastIndex() {
        return index;
    }

    public static void resetQuotes() {
        System.out.println("Resetting map");
        quotes = new HashMap() {
            {
                put(1, "Friends are kisses blown to us by angels");
                put(2, "Do not take life too seriously. You will never get out of it alive");
                put(3, "Behind every great man, is a woman rolling her eyes");
            }
        };
        System.out.println("Value 1 is: " + quotes.get(1));
        System.out.println("Value 2 is: " + quotes.get(2));
        System.out.println("Value 3 is: " + quotes.get(3));
    }
}
