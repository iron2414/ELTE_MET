package app.controller;

import app.entity.*;
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

    @JsonRequestMapping(path = "/message", method = RequestMethod.POST)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_MESSAGE_CREATE')")
    public ResponseEntity<Object> postAction(@RequestBody @Valid MessageForm form,
                                             BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                Message message = form.execute();
                messageRepository.save(message);
                return Response.create(message);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }
}