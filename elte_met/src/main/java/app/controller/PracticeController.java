package app.controller;

import app.entity.*;
import app.form.PracticeForm;
import com.querydsl.core.types.dsl.BooleanExpression;
import exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Filter.PredicatesBuilder;
import util.JsonRequestMapping;
import util.Response.Response;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/practices")
public class PracticeController {
    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;


    @JsonRequestMapping(path = "/practice/{id}")
    //@PreAuthorize("hasRole('ROLE_PRACTICE_GET')")
    public @ResponseBody
    ResponseEntity<Object> getAction(@PathVariable("id") Practice practice) {
        return SecurityController.getAction(practice);
    }


    @JsonRequestMapping(path = "/practice")
    //@PreAuthorize("hasRole('ROLE_PRACTICE_LIST')")
    public @ResponseBody
    ResponseEntity<Object> cgetAction(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Practice>(Practice.class);
        BooleanExpression exp = filter(builder, search);
        Page<Practice> all = practiceRepository.findAll(exp, pageable);
        return Response.create(all);
    }

    @JsonRequestMapping(path = "/practice/{id}", method = RequestMethod.DELETE)
    //@PreAuthorize("hasRole('ROLE_PRACTICE_DELETE')")
    public @ResponseBody
    ResponseEntity<Object> deleteAction(@PathVariable("id") Practice practice) {
        practiceRepository.delete(practice);
        return Response.create();
    }

    @JsonRequestMapping(path = "/practice", method = RequestMethod.POST)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_PRACTICE_CREATE')")
    public ResponseEntity<Object> postAction(@RequestBody @Valid PracticeForm form,
                                             BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                Practice practice = form.execute();
                Optional<User> user = userRepository.findById(form.getTeacher());
                if (!user.isPresent()) {
                    throw new Exception("Teacher not found");
                }
                Optional<Subject> subject = subjectRepository.findById(form.getSubject());
                if (!subject.isPresent()) {
                    throw new Exception("Subject not found");
                }
                practice.setSubject(subject.get());
                practice.setTeacher(user.get());
                practiceRepository.save(practice);

                return Response.create(practice);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/practice/{id}", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_PRACTICE_UPDATE')")
    public ResponseEntity<Object> putAction(@RequestBody @Valid PracticeForm form, @PathVariable("id") Practice practice,
                                            BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                form.execute(practice);
                Optional<User> user = userRepository.findById(form.getTeacher());
                if (!user.isPresent()) {
                    throw new Exception("Teacher not found");
                }
                Optional<Subject> subject = subjectRepository.findById(form.getSubject());
                if (!subject.isPresent()) {
                    throw new Exception("Subject not found");
                }
                practice.setSubject(subject.get());
                practice.setTeacher(user.get());
                practiceRepository.save(practice);
                return Response.create(practice);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/practice/{id}/add", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_PRACTICE_UPDATE')")
    public ResponseEntity<Object> addStudentAction(@RequestParam Long userId, @PathVariable("id") Practice practice)
            throws ConstraintViolationException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                throw new Exception("User not found");
            }
            practice.addStudent(user.get());
            practiceRepository.save(practice);
            return Response.create();
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/practice/{id}/remove", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_PRACTICE_UPDATE')")
    public ResponseEntity<Object> removeStudentAction(@RequestParam Long userId, @PathVariable("id") Practice practice)
            throws ConstraintViolationException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                throw new Exception("User not found");
            }
            practice.removeStudent(user.get());
            practiceRepository.save(practice);
            return Response.create();
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/mypractices", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_PRACTICE_LIST')")
    public ResponseEntity<Object> myPracticesAction(@RequestParam(required = false) String semester) {
        try {
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Practice> practices = practiceRepository.findByStudents_Username(userDetails.getUsername());
            if(null != semester) {
                List<Practice> result = new ArrayList<>();
                for(Practice p : practices) {
                    if(p.getSubject().getSemester().equals(semester)) {
                        result.add(p);
                    }
                }
                return Response.create(result);
            }
            return Response.create(practices);
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }
}