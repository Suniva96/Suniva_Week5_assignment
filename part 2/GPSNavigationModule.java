import java.util.*;

class NavigationFailedException extends Exception {
    public NavigationFailedException(String message) {
        super(message);
    }

    public NavigationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

class InvalidRouteException extends Exception {
    public InvalidRouteException(String message) {
        super(message);
    }
}

class SameLocationException extends Exception {
    public SameLocationException(String message) {
        super(message);
    }
}

interface RouteValidator {
    boolean isValidCommuteRoute(String origin, String destination, double distanceKm)
            throws InvalidRouteException, SameLocationException;
}

class KathmanduTrafficValidator implements RouteValidator {
    @Override
    public boolean isValidCommuteRoute(String origin, String destination, double distanceKm)
            throws InvalidRouteException, SameLocationException {
        if (origin.equalsIgnoreCase(destination)) {
            throw new SameLocationException("Origin and destination are the same.");
        }
        if (distanceKm < 1.0) {
            throw new InvalidRouteException("Distance too short to navigate.");
        }
        return true;
    }
}

interface NavigationService {
    void navigate(String startPoint, String endPoint, RouteValidator validator)
            throws NavigationFailedException;
}

public class GPSNavigationModule implements NavigationService {

    @Override
    public void navigate(String startPoint, String endPoint, RouteValidator validator)
            throws NavigationFailedException {

        System.out.println("Attempting to navigate from " + startPoint + " to " + endPoint );

        if (startPoint.equalsIgnoreCase("Kalanki")) {
            throw new NavigationFailedException("GPS signal lost near Kalanki! Welcome to Kathmandu traffic!");
        }

        try {
            double distance = simulatedDistance(startPoint, endPoint);
            if (validator.isValidCommuteRoute(startPoint, endPoint, distance)) {
                System.out.println("Navigation successful! Estimated time: 20 minutes (or 2 hours depending on traffic).");
            }
        } catch (InvalidRouteException | SameLocationException e) {
            throw new NavigationFailedException("Route validation failed!", e);
        }
    }

    private double simulatedDistance(String start, String end) {
        return 5.0; 
    }

    public static void main(String[] args) {
        GPSNavigationModule gps = new GPSNavigationModule();
        RouteValidator validator = new KathmanduTrafficValidator();

        String[][] testRoutes = {
                {"Kalanki", "Thamel"},
                {"Baneshwor", "Baneshwor"},
                {"Lalitpur", "Jawalakhel"},
                {"Bhaktapur", "Bhaktapur"},
                {"Putalisadak", "Maitighar"},
                {"Kirtipur", "Kalanki"}
        };

        for (String[] route : testRoutes) {
            System.out.println("\nTest: " + route[0] + " -> " + route[1]);
            try {
                gps.navigate(route[0], route[1], validator);
            } catch (NavigationFailedException e) {
                System.out.println("Navigation failed: " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("Caused by: " + e.getCause().getMessage());
                }
            }
        }
    }
}

