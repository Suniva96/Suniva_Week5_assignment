
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

public class KathmanduTrafficValidator implements RouteValidator {

    @Override
    public boolean isValidCommuteRoute(String origin, String destination, double distanceKm)
            throws InvalidRouteException, SameLocationException {

        if (origin == null || destination == null || origin.isEmpty() || destination.isEmpty()) {
            throw new InvalidRouteException("Origin and destination must be non-empty.");
        }

        if (origin.equalsIgnoreCase(destination)) {
            throw new SameLocationException(
                    "Origin and destination cannot be the same");
        }

        if (distanceKm < 0.1 || distanceKm > 30) {
            throw new InvalidRouteException(
                    "Distance " + distanceKm + "km is unrealistic for kathmandu commute");
        }

        return true;
    }

    public static void main(String[] args) {
        KathmanduTrafficValidator validator = new KathmanduTrafficValidator();

        String[][] tests = {
                { "Thamel", "Thamel", "5" },   
                { "Balaju chowk ", "MAchapokhari ", "0.30" },  
                { "Bhaktapur", "Kathmandu", "40" },  
                { "Lazimpat", "Kalimati", "10" },  
                { "", "Kalimati", "10" },  
                { "Lazimpat", "", "10" }   
        };

        for (String[] test : tests) {
            String origin = test[0];
            String destination = test[1];
            double distance = Double.parseDouble(test[2]);

            System.out.println("Testing route: " + origin + " -> " + destination + ", Distance: " + distance + " km");

            try {
                boolean result = validator.isValidCommuteRoute(origin, destination, distance);
                System.out.println("Route is valid: " + result);
            } catch (SameLocationException e) {
                System.out.println("SameLocationException: " + e.getMessage());
            } catch (InvalidRouteException e) {
                System.out.println("InvalidRouteException: " + e.getMessage());
            }

            
        }
    }
}

