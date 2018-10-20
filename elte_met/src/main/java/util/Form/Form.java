package util.Form;

import exception.FormException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form {

    public static boolean isValid(BindingResult result) {
        return !result.hasErrors();
    }

    public static FormException getErrors(BindingResult formResult) {
        List<Map<String, String>> errors = new ArrayList<>();

        if (formResult.hasErrors()) {
            for (Object object : formResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    Map<String, String> map = new HashMap<>();
                    map.put(((FieldError) object).getField(), ((FieldError) object).getDefaultMessage());
                    errors.add(map);
                }

                //TODO probably unnecessary?
//                if (object instanceof ObjectError) {
//                    ObjectError objectError = (ObjectError) object;
//                }
            }
        }
        return new FormException(errors);

    }
}
