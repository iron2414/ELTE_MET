package app.controller;

import app.entity.*;
import app.form.GroupForm;
import app.form.MessageForm;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    @JsonRequestMapping(path = "/message/{id}")
    //@PreAuthorize("hasRole('ROLE_MESSAGE_GET')")
    public @ResponseBody
    ResponseEntity<Object> getAction(@PathVariable("id") Message message) {
        message.setRead(true);
        messageRepository.save(message);
        return SecurityController.getAction(message);
    }


    @JsonRequestMapping(path = "/message")
    //@PreAuthorize("hasRole('ROLE_MESSAGE_LIST')")
    public @ResponseBody
    ResponseEntity<Object> cgetAction(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Message>(Message.class);
        BooleanExpression exp = filter(builder, search);
        Page<Message> all = messageRepository.findAll(exp, pageable);
        return Response.create(all);
    }

    @JsonRequestMapping(path = "/mymessage", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_MESSAGE_MYLIST')")
    public ResponseEntity<Object> myMessageAction() throws ConstraintViolationException {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Response.create(messageRepository.findByReceiver(user));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/message", method = RequestMethod.POST)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_SUBJECT_CREATE')")
    public ResponseEntity<Object> postAction(@RequestParam Long[] users,
                                             @RequestBody @Valid MessageForm form,
                                             BindingResult result) {
        try {
            if(isValid(result)) {
                if (null == form.getPractice() && null == form.getSubject()) {
                    throw new Exception("Subject or Practice id must be present!");
                }
                Practice p = null;
                Subject s = null;
                if (null != form.getPractice()) {
                    Optional<Practice> praticeOpt = practiceRepository.findById(form.getPractice());
                    if (!praticeOpt.isPresent()) {
                        throw new Exception("Pratice id not valid:" + form.getPractice());
                    }
                    p = praticeOpt.get();
                }

                if (null != form.getSubject()) {
                    Optional<Subject> subjectOptional = subjectRepository.findById(form.getSubject());
                    if (!subjectOptional.isPresent()) {
                        throw new Exception("Subject id not valid:" + form.getSubject());
                    }
                    s = subjectOptional.get();
                }
                List<User> entityUsers = new ArrayList<>();
                for (Long userId : users) {
                    Optional<User> user = userRepository.findById(userId);
                    if (!user.isPresent()) {
                        throw new Exception("User id not valid:" + userId);
                    }
                    entityUsers.add(user.get());
                }
                User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Date now = new Date();
                for (User entityUser : entityUsers) {
                    Message m = new Message();
                    m.setReceiver(entityUser);
                    m.setRead(false);
                    m.setContent(form.getContent());
                    m.setSender(loggedInUser);
                    m.setSendingDate(now);
                    messageRepository.save(m);
                }
                return Response.create();
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }
}