public class CommutePlanner {
    static class InvalidRouteException extends Exception {
        public InvalidRouteException(String message) {
            super(message);
        }
    }

    static class NavigationFailedException extends Exception {
        public NavigationFailedException(String message) {
            super(message);
        }
        public NavigationFailedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    interface RouteValidator {
        boolean isValidCommuteRoute(String origin, String destination, double distanceKm) 
            throws InvalidRouteException;
    }

    interface NavigationService {
        void navigate(String origin, String destination, RouteValidator validator) 
            throws NavigationFailedException;
    }
    static class KathmanduTrafficValidator implements RouteValidator {
        @Override
        public boolean isValidCommuteRoute(String origin, String destination, double distanceKm) 
                throws InvalidRouteException {
            if (origin.equals(destination)) {
                throw new InvalidRouteException("You're already there ");
            }
            if (distanceKm > 50) {
                throw new InvalidRouteException("That's too far for a Kathmandu commute!");
            }
            return true;
        }
    }

    static class GPSNavigationModule implements NavigationService {
        @Override
        public void navigate(String origin, String destination, RouteValidator validator) 
                throws NavigationFailedException {
            try {
                
                validator.isValidCommuteRoute(origin, destination, calculateDistance(origin, destination));
                
                
                if (origin.equals("Kalanki") && destination.equals("Balaju")) {
                    throw new Exception("GPS Error: Traffic jam at Kalanki-Balaju route!");
                }
                
                System.out.println("Navigation successful! Avoid peak hours if possible.");
            } catch (InvalidRouteException e) {
                throw new NavigationFailedException("Route validation failed", e);
            } catch (Exception e) {
                throw new NavigationFailedException("Navigation failed: " + e.getMessage());
            }
        }
        
        private double calculateDistance(String origin, String destination) {
    
            return Math.abs(origin.hashCode() % 20) + Math.abs(destination.hashCode() % 20);
        }
    }

    public static void planMyCommute(String origin, String destination, 
                                    RouteValidator validator, NavigationService navigator) {
        System.out.println("\n🚗 Planning your commute from " + origin + " to " + destination + "...");
        
        try {
            navigator.navigate(origin, destination, validator);
            System.out.println(" Route is valid and navigation started!");
        } catch (NavigationFailedException e) {
            System.err.println("Route is invalid" + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("   Reason: " + e.getCause().getMessage());
            }
        } finally {
            System.out.println("Commute planning for " + origin + " to " + destination + " completed.");
        }
    }

    public static void main(String[] args) {
        RouteValidator validator = new KathmanduTrafficValidator();
        NavigationService navigator = new GPSNavigationModule();

        System.out.println(" Kathmandu Commute Planner ");
        System.out.println("(This app won't fix traffic, but will tell you why you're stuck)\n");
        
        planMyCommute("Baneshwor", "Baneshwor", validator, navigator);  
        planMyCommute("Thamel", "Patan", validator, navigator);        
        planMyCommute("Kalanki", "Balaju", validator, navigator);     
        planMyCommute("Koteshwor", "Nagarkot", validator, navigator);  
    }
}
