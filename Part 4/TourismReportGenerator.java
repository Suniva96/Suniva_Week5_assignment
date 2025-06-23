import java.util.List;
import java.util.ArrayList;

class DataSourceAccessException extends Exception {
    public DataSourceAccessException(String message) {
        super(message);
    }
    public DataSourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ConnectionLostException extends DataSourceAccessException {
    public ConnectionLostException(String message) {
        super(message);
    }
}

class AuthenticationFailedException extends DataSourceAccessException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}

abstract class TouristDataSource {
    protected String sourceName;

    public TouristDataSource(String sourceName) {
        this.sourceName = sourceName;
    }

    public abstract List<String> fetchData() throws DataSourceAccessException;
}

class AirportArrivalsDataSource extends TouristDataSource {

    public AirportArrivalsDataSource() {
        super("Tribhuvan Airport Arrivals");
    }

    @Override
    public List<String> fetchData() throws DataSourceAccessException {
        if (sourceName.contains("Tribhuvan") && Math.random() < 0.3) {
            throw new ConnectionLostException("Airport data connection lost.");
        }
        return List.of(
            "Visitor: Bahadur Thapa, USA",
            "Visitor: HAri Sharma, UK",
            "Visitor: Priya Lama, Spain"
        );
    }
}

class HotelRegistrationsDataSource extends TouristDataSource {

    public HotelRegistrationsDataSource() {
        super("Kathmandu Hotels Registrations");
    }

    @Override
    public List<String> fetchData() throws DataSourceAccessException {
        if (sourceName.contains("Hotels") && Math.random() < 0.2) {
            throw new AuthenticationFailedException("Hotel API authentication failed");
        }
        return List.of(
            "Hotel: Yak & Yeti, Guest: Ram Thapa, NP",
            "Hotel: Annapurna, Guest: Alice Smith, AU"
        );
    }
}

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

class UniqueVisitorCounter implements DataProcessor {

    @Override
    public List<String> process(List<String> rawData) throws DataProcessingException {
        if (rawData == null || rawData.isEmpty()) {
            throw new EmptyDataException("No raw data to process.");
        }
        var uniqueNames = new java.util.HashSet<String>();
        for (String entry : rawData) {
            String name = extractName(entry);
            if (name != null && !name.isEmpty()) {
                uniqueNames.add(name);
            }
        }
        return List.of("Unique Visitors: " + uniqueNames.size());
    }

    private String extractName(String entry) {
        int colonIndex = entry.indexOf(':');
        int commaIndex = entry.indexOf(',', colonIndex);
        if (colonIndex == -1 || commaIndex == -1) return null;
        return entry.substring(colonIndex + 1, commaIndex).trim();
    }
}
public class TourismReportGenerator {

    public static void generateOverallReport(List<TouristDataSource> dataSources, DataProcessor processor) {
        System.out.println("Generating overall tourism report...");

        for (TouristDataSource dataSource : dataSources) {
            List<String> rawData = null;

            try {
                rawData = dataSource.fetchData();
            } catch (DataSourceAccessException e) {
                System.out.println("Could not fetch data from " + dataSource.sourceName + ": " + e.getMessage() + ". Skipping this source.");
                Throwable cause = e.getCause();
                if (cause != null) {
                    System.out.println("Reason: " + cause.getMessage());
                } else if (e instanceof ConnectionLostException || e instanceof AuthenticationFailedException) {
                    System.out.println("Reason: " + e.getMessage());
                }
                continue;
            }

            try {
                List<String> processed = processor.process(rawData);
                processed.forEach(System.out::println);
            } catch (DataProcessingException e) {
                System.out.println("Error processing data from " + dataSource.sourceName + ": " + e.getMessage() + ". Skipping this data.");
            } finally {
                System.out.println("Data handling from " + dataSource.sourceName + " completed.");
            }
        }
    }

    public static void main(String[] args) {
        List<TouristDataSource> sources = new ArrayList<>();
        // Add multiple instances to increase chance of exceptions
        sources.add(new AirportArrivalsDataSource());
        sources.add(new AirportArrivalsDataSource());
        sources.add(new HotelRegistrationsDataSource());
        sources.add(new HotelRegistrationsDataSource());

        UniqueVisitorCounter processor = new UniqueVisitorCounter();

        generateOverallReport(sources, processor);
    }
}
