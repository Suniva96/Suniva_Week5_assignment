class EnrollmentDeniedException extends Exception {
    public EnrollmentDeniedException(String message) {
        super(message);
    }
}

@FunctionalInterface
interface EligibilityRule {
    boolean isEligible(String studentId, String courseId) throws EnrollmentDeniedException;
}

public class CourseEnrollmentManager {

    public static void enrollStudent(String studentId, String courseId, EligibilityRule rule) {
        System.out.println("Attempting to enroll " + studentId + " in " + courseId + "...");
        try {
            if (rule.isEligible(studentId, courseId)) {
                System.out.println("Enrollment successful for " + studentId + " in " + courseId + "! Happy learning!");
            } else {
                System.out.println("Enrollment failed for " + studentId + ": Not eligible for the course.");
            }
        } catch (EnrollmentDeniedException e) {
            System.out.println("Enrollment failed for " + studentId + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        EligibilityRule rule = (studentId, courseId) -> {
            if ("SKILL999".equals(studentId)) {
                throw new EnrollmentDeniedException("Student account suspended due to outstanding fees, Roshan!");
            }

            if ("JAVA101".equals(courseId)) {
                if (studentId.startsWith("SKILL")) {
                    return true;
                } else {
                    throw new EnrollmentDeniedException("Invalid student ID format. Please use 'SKILL' prefix, Anisha!");
                }
            }

            return false;
        };

        
        enrollStudent("SKILL123", "JAVA101", rule);      
        enrollStudent("SKILL999", "JAVA101", rule);    
        enrollStudent("USER456", "JAVA101", rule);        
        enrollStudent("SKILL777", "PYTHON202", rule);     
        enrollStudent("USER999", "DATA101", rule);        
    }
}

