package app.controller;

import app.entity.Permission;
import app.entity.PermissionRepository;
import app.entity.User;
import app.entity.UserRepository;
import app.form.UserForm;
import com.querydsl.core.types.dsl.BooleanExpression;
import exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Filter.PredicatesBuilder;
import util.JsonRequestMapping;
import util.Response.Response;

import javax.validation.Valid;

import java.sql.Date;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/temp")
public class TempController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @JsonRequestMapping(path = "/users", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("permitAll")
    public ResponseEntity<Object> postUser() throws ConstraintViolationException {
        try {
            User user = new User();
            user.setName("admin");
            user.setUsername("admin");
            user.setPhoneNumber("12345");
            String email = "iron2414@gmail.com";
            user.setBankAccountNumber("12314");
            user.setDateOfBirth(new java.util.Date());
            user.setDegree("bsc");
            user.setNationality("HUN");
            user.setTaxNumber("123456789");
            user.setWhichSemester(1);
            user.setEmail(email);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEnabled(true);
            user.setSuperAdmin(true);
            userRepository.save(user);
            return Response.create(user);
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/users")
    public @ResponseBody
    ResponseEntity<Object> search(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<User>(User.class);
        BooleanExpression exp = filter(builder, search);

        return Response.create(userRepository.findAll(exp, pageable));
    }

    @JsonRequestMapping(path = "/permissions")
    public @ResponseBody
    ResponseEntity<Object> searchPerm(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Permission>(Permission.class);
        BooleanExpression exp = filter(builder, search);
        return Response.create(permissionRepository.findAll(exp, pageable));
    }
}