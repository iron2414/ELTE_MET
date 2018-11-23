package app.controller;

import app.entity.Subject;
import app.entity.SubjectRepository;
import app.entity.User;
import app.entity.UserRepository;
import app.form.SubjectForm;
import com.querydsl.core.types.dsl.BooleanExpression;
import exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Filter.PredicatesBuilder;
import util.JsonRequestMapping;
import util.Response.Response;

import javax.validation.Valid;

import java.util.Optional;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/subject")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;


    @JsonRequestMapping(path = "/subject/{id}")
    //@PreAuthorize("hasRole('ROLE_SUBJECT_GET')")
    public @ResponseBody
    ResponseEntity<Object> getAction(@PathVariable("id") Subject subject) {
        return SecurityController.getAction(subject);
    }


    @JsonRequestMapping(path = "/subjects")
    //@PreAuthorize("hasRole('ROLE_SUBJECT_LIST')")
    public @ResponseBody
    ResponseEntity<Object> cgetAction(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Subject>(Subject.class);
        BooleanExpression exp = filter(builder, search);
        Page<Subject> all = subjectRepository.findAll(exp, pageable);
        return Response.create(all);
    }

    @JsonRequestMapping(path = "/subject/{id}", method = RequestMethod.DELETE)
    //@PreAuthorize("hasRole('ROLE_SUBJECT_DELETE')")
    public @ResponseBody
    ResponseEntity<Object> deleteAction(@PathVariable("id") Subject subject) {
        subjectRepository.delete(subject);
        return Response.create();
    }

    @JsonRequestMapping(path = "/subjects", method = RequestMethod.POST)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_SUBJECT_CREATE')")
    public ResponseEntity<Object> postAction(@RequestBody @Valid SubjectForm form,
                                             BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                Subject subject = form.execute();
                Optional<User> user = userRepository.findById(form.getLecturer());
                if (!user.isPresent()) {
                    throw new Exception("Lecturer not found");
                }
                //subject.setLecturer(user.get());
                subjectRepository.save(subject);
                return Response.create();
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }
}