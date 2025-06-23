class EnrollmentDeniedException extends Exception {
    public EnrollmentDeniedException(String message) {
        super(message);
    }
}

@FunctionalInterface
public interface EligibilityRule {
    boolean isEligible(String studentId, String courseId) throws EnrollmentDeniedException;
}
class TestEligibilityRule {
    public static void main(String[] args) {
        
        EligibilityRule rule = (studentId, courseId) -> {
            if (studentId.equals("S001") && courseId.equals("MATH101")) {
                return true;
            } else {
                throw new EnrollmentDeniedException(
                    "Enrollment denied for student " + studentId + " in course " + courseId);
            }
        };

        try {
            boolean eligible = rule.isEligible("S001", "MATH101");
            System.out.println("Enrollment allowed: " + eligible);
        } catch (EnrollmentDeniedException e) {
            System.out.println(e.getMessage());
        }

        try {
            boolean eligible = rule.isEligible("S002", "MATH101");
            System.out.println("Enrollment allowed: " + eligible);
        } catch (EnrollmentDeniedException e) {
            System.out.println(e.getMessage());
        }
    }
}
