import java.util.*;

class InvalidGuestCountException extends FestivalPlanningException {
    public InvalidGuestCountException(String message) {
        super(message);
    }
}

class BudgetExceededException extends FestivalPlanningException {
    public BudgetExceededException(String message) {
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

class NoRouteException extends FestivalPlanningException {
    public NoRouteException(String message) {
        super(message);
    }
}

class FestivalPlanningException extends Exception {
    public FestivalPlanningException(String message) {
        super(message);
    }
}

class TikaCeremony extends FestivalActivity {
     int expectedGuests;
    String mainFamilyElder;

    public TikaCeremony(int estimatedCost, int expectedGuests, String mainFamilyElder) {
        super("Tika Ceremony", estimatedCost);
        this.expectedGuests = expectedGuests;
        this.mainFamilyElder = mainFamilyElder;
    }

    @Override
    public void planActivity() throws FestivalPlanningException {
        if (expectedGuests < 5) {
            throw new InvalidGuestCountException("Not enough guests for a lively Tika");
        }
        if (estimatedCost > 50000) {
            throw new BudgetExceededException("Tika budget too high");
        }
        System.out.println("Tika ceremony with " + mainFamilyElder + " planned successfully for " + expectedGuests + " guests!");
    }
}

class DeusiBhailo extends FestivalActivity {
    private List<String> plannedRoutes;
    private int numberOfPerformers;

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
            throw new FestivalPlanningException("Need at least  5 performers for a proper Deusi Bhailo!");
        }
        System.out.println("Deusi Bhailo program with " + numberOfPerformers +
                " performers planned for " + plannedRoutes.size() + " routes!");
    }
}


public class DashainPlanner {

    public static void executeFestivalPlan(List<FestivalActivity> activities) {
        for (FestivalActivity activity : activities) {
            System.out.println("\nOverview: " + activity.displayOverview());
            try {
                activity.planActivity();
            } catch (InvalidGuestCountException e) {
                System.out.println("Planning Warning (Guests): " + e.getMessage());
            } catch (BudgetExceededException e) {
                System.out.println("Planning Warning (Budget): " + e.getMessage());
            } catch (NoRouteException e) {
                System.out.println("Planning Warning (Routes): " + e.getMessage());
            } catch (FestivalPlanningException e) {
                System.out.println("General Planning Error: " + e.getMessage());
            } finally {
                System.out.println("Activity planning attempt for " + activity.activityName + " completed.");
            }
        }
    }

    public static void main(String[] args) {
        List<FestivalActivity> festivalActivities = new ArrayList<>();

        festivalActivities.add(new TikaCeremony(40000, 10, "Grandma Meera")); // ✅ valid
        festivalActivities.add(new TikaCeremony(60000, 8, "Uncle Krishna"));  // ❌ budget too high
        festivalActivities.add(new TikaCeremony(30000, 2, "Aunt Rupa"));      // ❌ too few guests

        festivalActivities.add(new DeusiBhailo(20000, Arrays.asList("Ward 1", "Ward 2"), 5)); // ✅ valid
        festivalActivities.add(new DeusiBhailo(25000, new ArrayList<>(), 4));                 // ❌ no routes
        festivalActivities.add(new DeusiBhailo(18000, Arrays.asList("Main Street"), 2));      // ❌ not enough performers

        System.out.println(" Dashain Festival Master Planner is Activated!");
        executeFestivalPlan(festivalActivities);
        System.out.println("\n Festival plan sucessed");
    }
}
