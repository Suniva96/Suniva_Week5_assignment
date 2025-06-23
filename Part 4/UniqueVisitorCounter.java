import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// Custom checked exception for data processing errors
class DataProcessingException extends Exception {
    public DataProcessingException(String message) {
        super(message);
    }
}

class EmptyDataException extends DataProcessingException {
    public EmptyDataException(String message) {
        super(message);
    }
}

interface DataProcessor {
    List<String> process(List<String> rawData) throws DataProcessingException;
}


public class UniqueVisitorCounter implements DataProcessor {

    @Override
    public List<String> process(List<String> rawData) throws DataProcessingException {
        if (rawData == null || rawData.isEmpty()) {
            throw new EmptyDataException("No raw data to process! Did all tourists go missing?");
        }

        Set<String> uniqueNames = new HashSet<>();

        for (String entry : rawData) {
            String name = extractName(entry);
            if (name != null && !name.isEmpty()) {
                uniqueNames.add(name);
            }
        }

        List<String> result = new ArrayList<>();
        result.add("Unique Visitors: " + uniqueNames.size());
        return result;
    }

    private String extractName(String entry) {
       
        int colonIndex = entry.indexOf(':');
        int commaIndex = entry.indexOf(',', colonIndex);
        if (colonIndex == -1 || commaIndex == -1) {
            return null;
        }
        return entry.substring(colonIndex + 1, commaIndex).trim();
    }

    
    public static void main(String[] args) {
        UniqueVisitorCounter counter = new UniqueVisitorCounter();

        
        try {
            System.out.println("Testing empty data...");
            counter.process(new ArrayList<>()).forEach(System.out::println);
        } catch (DataProcessingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        
        try {
            System.out.println("\nTesting with dummy data...");
            List<String> data = List.of(
                "Visitor: hari Sharma, USA",
                "Guest: Ram sharma, AU",
                "Visitor: Bhadhur Thapa, USA",
                "Guest: Devi Maharjan, ES",
                "Visitor: Bhide Lama, AU"
            );
            counter.process(data).forEach(System.out::println);
        } catch (DataProcessingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

