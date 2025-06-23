class FestivalPlanningException extends Exception {
    public FestivalPlanningException(String message) {
        super(message);
    }
}
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
    protected String activityName;
    protected int estimatedCost;

    public FestivalActivity(String activityName, int estimatedCost) {
        this.activityName = activityName;
        this.estimatedCost = estimatedCost;
    }

    public abstract void planActivity() throws FestivalPlanningException;
}

public class TikaCeremony extends FestivalActivity {
    private int expectedGuests;
    private String mainFamilyElder;

    public TikaCeremony(int estimatedCost, int expectedGuests, String mainFamilyElder) {
        super("Tika Ceremony", estimatedCost);
        this.expectedGuests = expectedGuests;
        this.mainFamilyElder = mainFamilyElder;
    }

    @Override
    public void planActivity() throws FestivalPlanningException {
        if (expectedGuests < 5) {
            throw new InvalidGuestCountException("Not enough guests for a lively Tika! Is everyone on vaction");
        }

        if (estimatedCost > 5000) {
            throw new BudgetExceededException("Tika budget too high! is this for the whole villages ?");
        }

        System.out.println("Tika ceremony with " + mainFamilyElder +
                           " planned successfully for " + expectedGuests + " guests!");
    }


    public static void main(String[] args) {
        TikaCeremony[] ceremonies = {
            new TikaCeremony(5000, 10, "Grandfather Ram"),
            new TikaCeremony(3000, 15, "Uncle Hari"),
            new TikaCeremony(1000, 3, "Aunt Sita")
        };

        for (TikaCeremony ceremony : ceremonies) {
            try {
                ceremony.planActivity();
            } catch (InvalidGuestCountException e) {
                System.out.println("Guest Issue: " + e.getMessage());
            } catch (BudgetExceededException e) {
                System.out.println("Budget Issue: " + e.getMessage());
            } catch (FestivalPlanningException e) {
                System.out.println("Festival Planning Error: " + e.getMessage());
            }
        }
    }
}
