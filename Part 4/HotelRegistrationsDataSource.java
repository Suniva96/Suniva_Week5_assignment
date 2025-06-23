import java.util.Arrays;
import java.util.List;

class DataSourceAccessException extends Exception {
    public DataSourceAccessException(String message) {
        super(message);
    }
    public DataSourceAccessException(String message, Throwable cause) {
        super(message, cause);
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

public class HotelRegistrationsDataSource extends TouristDataSource {

    public HotelRegistrationsDataSource() {
        super("Kathmandu Hotels Registrations");
    }

    @Override
    public List<String> fetchData() throws DataSourceAccessException {
        if (sourceName.contains("Hotels") && Math.random() < 0.2) {
            throw new AuthenticationFailedException("Hotel API authentication failed! Did someone forget the password again?");
        }
        return Arrays.asList(
            "Hotel: Yak & Yeti, Guest: Ram Thapa, NP",
            "Hotel: Annapurna, Guest: Alice Smith, AU"
        );
    }

    public static void main(String[] args) {
        HotelRegistrationsDataSource ds = new HotelRegistrationsDataSource();
        try {
            System.out.println("Fetching data from " + ds.sourceName + ":");
            List<String> data = ds.fetchData();
            data.forEach(System.out::println);
        } catch (DataSourceAccessException e) {
            System.out.println("Failed to fetch data: " + e.getMessage());
        }
    }
}

