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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Filter.PredicatesBuilder;
import util.JsonRequestMapping;
import util.Response.Response;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/subjects")
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


    @JsonRequestMapping(path = "/subject")
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

    @JsonRequestMapping(path = "/subject", method = RequestMethod.POST)
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
                subject.setTeacher(user.get());

                subjectRepository.save(subject);
                return Response.create(subject);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/subject/{id}", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_SUBJECT_UPDATE')")
    public ResponseEntity<Object> putAction(@RequestBody @Valid SubjectForm form, @PathVariable("id") Subject subject,
                                            BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                form.execute(subject);
                Optional<User> user = userRepository.findById(form.getLecturer());
                if (!user.isPresent()) {
                    throw new Exception("Lecturer not found");
                }
                subject.setTeacher(user.get());
                subjectRepository.save(subject);
                return Response.create(subject);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/subject/{id}/add", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_SUBJECT_UPDATE')")
    public ResponseEntity<Object> addStudentAction(@RequestParam Long userId, @PathVariable("id") Subject subject)
            throws ConstraintViolationException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                throw new Exception("User not found");
            }
            subject.addStudent(user.get());
            subjectRepository.save(subject);
            return Response.create();
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/subject/{id}/remove", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_SUBJECT_UPDATE')")
    public ResponseEntity<Object> removeStudentAction(@RequestParam Long userId, @PathVariable("id") Subject subject)
            throws ConstraintViolationException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                throw new Exception("User not found");
            }
            subject.removeStudent(user.get());
            subjectRepository.save(subject);
            return Response.create();
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }


    @JsonRequestMapping(path = "/mysubjects", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_PRACTICE_LIST')")
    public ResponseEntity<Object> myPracticesAction(@RequestParam(required = false) String semester) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Subject> subjects = subjectRepository.findByStudents_Username(userDetails.getUsername());
            if(null != semester) {
                List<Subject> result = new ArrayList<>();
                for(Subject s : subjects) {
                    if(s.getSemester().equals(semester)) {
                        result.add(s);
                    }
                }
                return Response.create(result);
            }
            return Response.create(subjects);
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }
}