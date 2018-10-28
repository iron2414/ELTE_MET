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

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path="/user")
public class SubjectController {
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @JsonRequestMapping(path="/user/{id}")
    @PreAuthorize("hasRole('ROLE_PERM')")
    public @ResponseBody ResponseEntity<Object> getUser( @PathVariable("id") User user) {
        try {
            if(null == user) {
                //TODO BusinessLogic Exception
                throw new Exception("Entity with this id not found");
            }
            return Response.create(user);
        } catch (Exception e) {
            return Response.create(e);
        }
    }
        
    @JsonRequestMapping(path = "/users", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> postUser(@RequestBody @Valid UserForm form,
                                                     BindingResult result) throws ConstraintViolationException {
        try {
            if(isValid(result)) {
                User user = form.execute();
                user.setPassword(passwordEncoder.encode("aA112233"));
                user.setEnabled(true);
                user.setSuperAdmin(true);
                userRepository.save(user);
                return Response.create(user);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

	@JsonRequestMapping(path = "/users")
    public @ResponseBody ResponseEntity<Object> cgetUser(@RequestParam(value = "search",required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<User>(User.class);
        BooleanExpression exp = filter(builder,search);

        return Response.create(userRepository.findAll(exp,pageable));
    }

    @JsonRequestMapping(path = "/permissions")
    public @ResponseBody ResponseEntity<Object> searchPerm(@RequestParam(value = "search",required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Permission>(Permission.class);
        BooleanExpression exp =  filter(builder,search);
        return Response.create(permissionRepository.findAll(exp,pageable));
    }
}