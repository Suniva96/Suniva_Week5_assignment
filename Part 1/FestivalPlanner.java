class FestivalPlanningException extends Exception {
    public FestivalPlanningException(String message) {
        super(message);
    }
}


abstract class FestivalActivity {
    String activityName;
    double estimatedCost;

    public FestivalActivity(String activityName, double estimatedCost) { 
        this.activityName = activityName;
        this.estimatedCost = estimatedCost;
    }

    abstract void planActivity() throws FestivalPlanningException;

    public void displayOverview() {
        System.out.println("Activity Name: " + activityName);
        System.out.println("Estimated Cost: Rs. " + estimatedCost);
    }
}


class DashainFeast extends FestivalActivity {
    public DashainFeast(String activityName, double estimatedCost) {
        super(activityName, estimatedCost);
    }

    @Override
    public void planActivity() throws FestivalPlanningException {
        if (estimatedCost > 10000) {  
            throw new FestivalPlanningException("Dashain Feast is going over budget");
        }
        System.out.println("Dashain Feast is under budget");
    }
}


public class FestivalPlanner {
    public static void main(String[] args) throws FestivalPlanningException {  
        FestivalActivity feast = new DashainFeast("Dashain Feast", 8000);

        feast.displayOverview();
        feast.planActivity();
    }
}
