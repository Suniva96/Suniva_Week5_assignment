import java.util.*; 

class FestivalPlanningException extends Exception {
    public FestivalPlanningException(String message) {
        super(message);
    }
}

class NoRouteException extends FestivalPlanningException {
    public NoRouteException(String message) {
        super(message);
    }
}


abstract class FestivalActivity {
    String activityName;
    int estimatedCost;

    public FestivalActivity(String activityName, int estimatedCost) {
        this.activityName = activityName;
        this.estimatedCost = estimatedCost;
    }

    public abstract void planActivity() throws FestivalPlanningException;

    public String displayOverview() {
        return "Activity: " + activityName + ", Estimated Cost: " + estimatedCost;
    }
}


public class DeusiBhailo extends FestivalActivity {
    List<String> plannedRoutes;
    int numberOfPerformers;

    public DeusiBhailo(int estimatedCost, List<String> plannedRoutes, int numberOfPerformers) {
        super("Deusi Bhailo Program", estimatedCost);
        this.plannedRoutes = plannedRoutes;
        this.numberOfPerformers = numberOfPerformers;
    }

    @Override
    public void planActivity() throws FestivalPlanningException {
        if (plannedRoutes == null || plannedRoutes.isEmpty()) {
            throw new NoRouteException("No routes planned for Deusi Bhailo");
        }

        if (numberOfPerformers < 3) {
            throw new FestivalPlanningException("Need at least 5 performers for a proper Deusi Bhailo!");
        }

        System.out.println("Deusi Bhailo program with " + numberOfPerformers +
                " performers planned for " + plannedRoutes.size() + " routes!");
    }


    public static void main(String[] args) {
        List<DeusiBhailo> programs = new ArrayList<>();

        programs.add(new DeusiBhailo(20000, Arrays.asList("Ward 1", "Ward 2"), 5));   
        programs.add(new DeusiBhailo(15000, new ArrayList<>(), 4));                    
        programs.add(new DeusiBhailo(18000, Arrays.asList("Main Street"), 2));         

        for (DeusiBhailo program : programs) {
            System.out.println("\nOverview: " + program.displayOverview());
            try {
                program.planActivity();
            } catch (NoRouteException e) {
                System.out.println("Route Issue: " + e.getMessage());
            } catch (FestivalPlanningException e) {
                System.out.println("Planning Error: " + e.getMessage());
            } finally {
                System.out.println("Activity planning attempt for " + program.activityName + " completed.");
            }
        }
    }
}
