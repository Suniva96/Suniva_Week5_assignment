class InvalidRouteException extends Exception {
    public InvalidRouteException(String message) {
        super(message);
    }
}

interface RouteValidator {
    boolean isValidCommuteRoute(String origin, String destination, double distanceKm) throws InvalidRouteException;
}
class SimpleRouteValidator implements RouteValidator {
    @Override
    public boolean isValidCommuteRoute(String origin, String destination, double distanceKm) throws InvalidRouteException {
        if (origin == null || destination == null || origin.isEmpty() || destination.isEmpty()) {
            throw new InvalidRouteException("Origin and destination must be non-empty.");
        }
        if (origin.equalsIgnoreCase(destination)) {
            throw new InvalidRouteException("Origin and destination cannot be the same.");
        }
        if (distanceKm <= 0) {
            throw new InvalidRouteException("Distance must be greater than 0 km.");
        }
        return true;
    }
}

public class RouteValidation{
    public static void main(String[] args) {
        RouteValidator validator = new SimpleRouteValidator();

        try {
            validator.isValidCommuteRoute("Pokhara", "Pokhara", 10);
        } catch (InvalidRouteException e) {
            System.out.println("Route validation failed: " + e.getMessage());
        }
    }
}
