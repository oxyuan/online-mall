package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 18:31 2019/5/7
 **/
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    List<User> findUsersByUsernameOrPhoneOrEmail(String username, String phone, String email);

    User findUserByUsername(String username);

    User findUserByPhone(String phone);

    User findUserByEmail(String email);
}
