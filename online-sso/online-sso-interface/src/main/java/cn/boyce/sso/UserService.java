package cn.boyce.sso;

import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.User;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:04 2019/5/7
 **/
public interface UserService {

    R checkData(String param, Integer type);

    R register(User user);

    R login(String username, String password);

    R getUserByToken(String token);

}
