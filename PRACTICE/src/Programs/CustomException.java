package Programs;

class DefinedException extends Exception {
    private String field;
    
    public DefinedException(String message, String field) {
        super(message);
        this.field = field;
    }
    
    public String getField() { return field; }
}

class Validator {
    static void validateAge(int age) throws DefinedException {
        if (age < 0) throw new DefinedException("Age cannot be negative", "age");
        if (age > 150) throw new DefinedException("Age unrealistic", "age");
    }
}

public class CustomException {
    public static void main(String[] args) {
        try {
            Validator.validateAge(-5);
        } catch (DefinedException e) {
            System.out.println("Error: " + e.getMessage() + " (Field: " + e.getField() + ")");
        }
    }
}
