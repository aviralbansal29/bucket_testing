package com.abed.bucket_testing.users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * UserRepository defines repository for UserModel
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

  @Query("SELECT EXISTS(Select 1 from UserModel u where LOWER(u.email) = LOWER(:email))")
  boolean existsByEmail(String email);

  @Query("SELECT u from UserModel u where name ILIKE '%:query%' OR email ILIKE '%:query%'")
  List<UserModel> findByQuery(String query);
}
