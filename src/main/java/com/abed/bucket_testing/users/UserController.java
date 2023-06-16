package com.abed.bucket_testing.users;

import com.abed.bucket_testing.dto.ListServiceResponse;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller to control API relating to Users
 */
@RestController
public class UserController {

  @Autowired UserService userService;

  @Autowired UserRepository userRepository;

  @RequestMapping(method = RequestMethod.POST, value = "/users")
  public ResponseEntity<UserModel>
  createUser(@Valid @RequestBody UserCreateRequest userReq) throws Exception {
    userReq.setUserRepository(userRepository);
    return new ResponseEntity<UserModel>(userService.createUser(userReq),
                                         HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/users")
  public ResponseEntity<List<UserModel>>
  listUsers(@RequestParam(name = "q", required = false) String query) {
    ListServiceResponse<UserModel> details = userService.listUsers(query);
    List<UserModel> content = details.getContent();
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("X-Total-Count", Long.toString(details.getCount()));
    return new ResponseEntity<List<UserModel>>(content, responseHeaders,
                                               HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
  public ResponseEntity<UserModel> getUser(@PathVariable("id") long id)
      throws Exception {
    try {
      return new ResponseEntity<UserModel>(userService.retrieveUser(id),
                                           HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      throw e;
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/variant")
  public HashMap<String, Long> getUserVariant(@RequestParam long experimentId,
                                              @PathVariable("id") long userId) {
    return userService.getExperimentVariant(experimentId, userId);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
  public ResponseEntity<UserModel> deleteUser(@PathVariable("id") long id)
      throws Exception {
    try {
      return new ResponseEntity<UserModel>(userService.deleteUser(id),
                                           HttpStatus.OK);
    } catch (NotFoundException n) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      throw e;
    }
  }
}
