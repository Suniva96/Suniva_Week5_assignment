import java.util.ArrayList;
import java.util.List;

class ConnectionLostException extends Exception {
    public ConnectionLostException(String message) {
        super(message);
    }
}

abstract class TouristDataSource {
    protected String sourceName;

    public abstract List<String> fetchData() throws DataSourceAccessException;
}

class DataSourceAccessException extends Exception {
    public DataSourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class AirportArrivalsDataSource extends TouristDataSource {

    public AirportArrivalsDataSource() {
        this.sourceName = "Tribhuvan Airport Arrivals";
    }

    @Override
    public List<String> fetchData() throws DataSourceAccessException {
        try {
        
            if (sourceName.contains("Tribhuvan") && Math.random() < 0.3) {
                throw new ConnectionLostException("Airport data connection lost! Maybe a pigeon sat on the antenna?");
            }

            
            List<String> arrivals = new ArrayList<>();
            arrivals.add("Visitor: John Doe, USA");
            arrivals.add("Visitor: Emily White, UK");
            return arrivals;

        } catch (ConnectionLostException e) {
            throw new DataSourceAccessException("Failed to fetch data from " + sourceName, e);
        }
    }

    public static void main(String[] args) {
        AirportArrivalsDataSource dataSource = new AirportArrivalsDataSource();

        try {
            List<String> arrivals = dataSource.fetchData();
            System.out.println("Arrivals Data:");
            for (String arrival : arrivals) {
                System.out.println(arrival);
            }
        } catch (DataSourceAccessException e) {
            System.err.println("Error accessing data source: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Cause: " + e.getCause().getMessage());
            }
        }
    }
}
