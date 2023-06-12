package com.abed.bucket_testing.users;

import com.abed.bucket_testing.common_services.RedisService;
import com.abed.bucket_testing.dto.ListServiceResponse;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserService manages business logic for User data
 */
@Service
public class UserService {

  @Autowired UserRepository userRepository;

  @Autowired RedisService redisService;

  public UserModel createUser(UserCreateRequest req)
      throws InvalidRequestException {
    req.Validate();
    UserModel user = new UserModel(req.getName(), req.getEmail());
    userRepository.save(user);
    return user;
  }

  public ListServiceResponse<UserModel> listUsers(String query) {
    List<UserModel> userList = userRepository.findByQuery(query);
    return new ListServiceResponse<>(userList.size(), userList);
  }

  public UserModel retrieveUser(long id) throws Exception {
    Optional<UserModel> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException();
    }
    return user.get();
  }

  public UserModel deleteUser(long id) throws Exception {
    Optional<UserModel> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException();
    }
    userRepository.deleteById(id);
    return user.get();
  }

  public HashMap<String, Long> getExperimentVariant(long experimentId,
                                                    long userId) {
    HashMap<String, Long> resp = new HashMap<String, Long>();
    String redisKey = "experiment:" + experimentId;
    Double variant = redisService.getScore(redisKey, String.valueOf(userId));
    System.out.println(variant);
    if (variant.isNaN()) {
      redisService.addToOrderedSet(redisKey, String.valueOf(userId), 200);
    }
    resp.put("variant", variant.longValue());
    resp.put("experiment", experimentId);
    resp.put("userId", userId);
    return resp;
  }
}
