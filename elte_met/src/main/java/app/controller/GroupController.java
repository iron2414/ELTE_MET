package app.controller;

import app.entity.*;
import app.form.GroupForm;
import com.querydsl.core.types.dsl.BooleanExpression;
import exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Filter.PredicatesBuilder;
import util.JsonRequestMapping;
import util.Response.Response;

import javax.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static util.Filter.Filter.filter;
import static util.Form.Form.getErrors;
import static util.Form.Form.isValid;

@Controller
@RequestMapping(path = "/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @JsonRequestMapping(path = "/group/{id}")
    //@PreAuthorize("hasRole('ROLE_GROUP_GET')")
    public @ResponseBody
    ResponseEntity<Object> getAction(@PathVariable("id") Group group) {
        try {
            if (null == group) {
                throw new Exception("Entity with this id not found");
            }
            return Response.create(group);
        } catch (Exception e) {
            return Response.create(e);
        }
    }

    @JsonRequestMapping(path = "/group", method = RequestMethod.POST)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_GROUP_CREATE')")
    public ResponseEntity<Object> postAction(@RequestBody @Valid GroupForm form,
                                             BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                Group group = form.execute();
                groupRepository.save(group);
                return Response.create(group);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/group")
    //@PreAuthorize("hasRole('ROLE_GROUP_LIST')")
    public @ResponseBody
    ResponseEntity<Object> cgetAction(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        PredicatesBuilder builder = new PredicatesBuilder<Group>(Group.class);
        BooleanExpression exp = filter(builder, search);

        return Response.create(groupRepository.findAll(exp, pageable));
    }

    @JsonRequestMapping(path = "/group/{id}", method = RequestMethod.DELETE)
    //@PreAuthorize("hasRole('ROLE_GROUP_DELETE')")
    public @ResponseBody
    ResponseEntity<Object> deleteAction(@PathVariable("id") Group group) {
        groupRepository.delete(group);
        return Response.create();
    }

    @JsonRequestMapping(path = "/group/{id}", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_GROUP_UPDATE')")
    public ResponseEntity<Object> putAction(@RequestBody @Valid GroupForm form, @PathVariable("id") Group group,
                                            BindingResult result) throws ConstraintViolationException {
        try {
            if (isValid(result)) {
                form.execute(group);
                groupRepository.save(group);
                return Response.create(group);
            }
            return Response.create(getErrors(result));
        } catch (Exception ex) {
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/group/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_GROUP_UPDATE')")
    public ResponseEntity<Object> setPermissionsAction(@RequestParam Long[] permissions,@RequestParam Long[] users,
                                                       @PathVariable("id") Group group) {
        try {
            Set<Permission> permissonSet = new HashSet<>();
            Set<User> userSet = new HashSet<>();
            for(Long id : permissions) {
                Optional<Permission> permission = permissionRepository.findById(id);
                if(permission.isPresent()) {
                    permissonSet.add(permission.get());
                } else {
                    throw new Exception("Invalid permission ID:" + id);
                }
            }
            for(Long id : users) {
                Optional<User> user = userRepository.findById(id);
                if(user.isPresent()) {
                    user.get().addGroup(group);
                    userRepository.save(user.get());
                } else {
                    throw new Exception("Invalid user ID:" + id);
                }
            }
            group.setPermissions(permissonSet);
            groupRepository.save(group);
            return Response.create(group);
        } catch (Exception ex) {
            System.err.println(ex);
            return Response.create(ex);
        }
    }

    @JsonRequestMapping(path = "/group/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_GROUP_UPDATE')")
    public ResponseEntity<Object>getUsersForGroupAction(@PathVariable("id") Group group) {
        try {
            return Response.create(group.getUsers());
        } catch (Exception ex) {
            System.err.println(ex);
            return Response.create(ex);
        }
    }
}
