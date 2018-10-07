package exception;

import java.util.List;
import java.util.Map;

public class FormException extends Exception {

    private List<Map<String, String>> errors;
    public FormException(List<Map<String,String>> errors){
        super("Form is not valid");
        this.errors = errors;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }
}
