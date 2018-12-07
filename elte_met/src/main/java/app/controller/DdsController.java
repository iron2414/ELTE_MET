package app.controller;

import app.entity.*;
import app.form.DdsForm;
import com.querydsl.core.types.dsl.BooleanExpression;
import exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/dds")
public class DdsController {
    @Autowired
    private DdsRepository ddsRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    @JsonRequestMapping(path = "/dds/{id}")
    //@PreAuthorize("hasRole('ROLE_DDS_GET')")
    public @ResponseBody
    ResponseEntity<Object> getAction(@PathVariable("id") Dds dds) {
        return SecurityController.getAction(dds);
    }


    @JsonRequestMapping(path = "/dds")
    //@PreAuthorize("hasRole('ROLE_DDS_LIST')")
    public @ResponseBody
    ResponseEntity<Object> cgetAction(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Dds>(Dds.class);
        BooleanExpression exp = filter(builder, search);
        Page<Dds> all = ddsRepository.findAll(exp, pageable);
        return Response.create(all);
    }

    @JsonRequestMapping(path = "/dds/{id}", method = RequestMethod.DELETE)
    //@PreAuthorize("hasRole('ROLE_DDS_DELETE')")
    public @ResponseBody
    ResponseEntity<Object> deleteAction(@PathVariable("id") Dds dds) {
        ddsRepository.delete(dds);
        return Response.create();
    }

    @JsonRequestMapping(path = "/dds", method = RequestMethod.POST)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_DDS_CREATE')")
    public ResponseEntity<Object> postAction(@RequestBody @Valid DdsForm form,
                                             BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                Dds dds = form.execute();
                int counter = 0;
                if(null !=form.getSubject()) {
                    Optional<Subject> subject = subjectRepository.findById(form.getSubject());
                    if (!subject.isPresent()) {
                        throw new Exception("Subject not found");
                    }
                    dds.setSubject(subject.get());
                    counter++;
                }
                if(null !=form.getPractice()) {
                    Optional<Practice> practice = practiceRepository.findById(form.getPractice());
                    if (!practice.isPresent()) {
                        throw new Exception("Practice not found");
                    }
                    dds.setPractice(practice.get());
                    counter++;
                }
                if(null !=form.getExam()) {
                    Optional<Exam> exam = examRepository.findById(form.getExam());
                    if (!exam.isPresent()) {
                        throw new Exception("Exam not found");
                    }
                    dds.setExam(exam.get());
                    counter++;
                }
                if(counter != 1) {
                    throw new Exception("Only 1 event should be set");
                }
                ddsRepository.save(dds);
                return Response.create(dds);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/dds/{id}", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_DDS_UPDATE')")
    public ResponseEntity<Object> putAction(@RequestBody @Valid DdsForm form, @PathVariable("id") Dds dds,
                                            BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                form.execute(dds);
                int counter = 0;
                dds.setExam(null);
                dds.setSubject(null);
                dds.setPractice(null);
                if(null !=form.getSubject()) {
                    Optional<Subject> subject = subjectRepository.findById(form.getSubject());
                    if (!subject.isPresent()) {
                        throw new Exception("Subject not found");
                    }
                    dds.setSubject(subject.get());
                    counter++;
                }
                if(null !=form.getPractice()) {
                    Optional<Practice> practice = practiceRepository.findById(form.getPractice());
                    if (!practice.isPresent()) {
                        throw new Exception("Practice not found");
                    }
                    dds.setPractice(practice.get());
                    counter++;
                }
                if(null !=form.getExam()) {
                    Optional<Exam> exam = examRepository.findById(form.getExam());
                    if (!exam.isPresent()) {
                        throw new Exception("Exam not found");
                    }
                    dds.setExam(exam.get());
                    counter++;
                }
                if(counter != 1) {
                    throw new Exception("Only 1 event should be set");
                }
                ddsRepository.save(dds);
                return Response.create(dds);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/timetable", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_DDS_LIST')")
    public ResponseEntity<Object> getTimeTable(@RequestParam String semester) {
        try {
            List<Dds> result = new ArrayList<>();
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Subject> subjects = subjectRepository.findByStudents_Username(userDetails.getUsername());
            List<Practice> practices = practiceRepository.findByStudents_Username(userDetails.getUsername());
            List<Subject> timeTableSubjects = subjects.stream().filter(s -> s.getSemester().equals(semester)).collect(Collectors.toList());
            List<Practice> timeTablePractices = practices.stream().filter(p -> p.getSubject().getSemester().equals(semester)).collect(Collectors.toList());
            for (Subject s : timeTableSubjects) {
                List<Dds> ddsSubjects = ddsRepository.findBySubject(s);
                result.addAll(ddsSubjects);
            }
            for (Practice p : timeTablePractices) {
                List<Dds> ddsSubjects = ddsRepository.findByPractice(p);
                result.addAll(ddsSubjects);
            }
            return Response.create(result);
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }
}