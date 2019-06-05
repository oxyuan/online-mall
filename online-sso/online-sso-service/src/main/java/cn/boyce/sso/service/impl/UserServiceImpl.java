package cn.boyce.sso.service.impl;

import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.User;
import cn.boyce.manager.repo.UserDao;
import cn.boyce.sso.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:17 2019/5/7
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    /**
     * 根据数据类型校验数据
     *
     * @param param
     * @param type
     * @return
     */
    @Override
    public R checkData(String param, Integer type) {
        // type=1，验证用户名；type=2，验证手机；type=3，验证邮箱
        User user;
        switch (type) {
            case 1:
                user = userDao.findUserByUsername(param);
                break;
            case 2:
                user = userDao.findUserByPhone(param);
                break;
            case 3:
                user = userDao.findUserByEmail(param);
                break;
            default:
                return R.error("false");
        }
        // 判断查询的结果是否为空
        if (null == user) {
            // 数据库中不存在该类型
            return R.ok(true);
        } else {
            // 数据已经存在
            return R.ok(false);
        }
    }

    @Override
    public R register(User user) {
        // 再次校验数据
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())) {
            return R.build(400, " 用户名或密码不能为空 ");
        }
        // 再次校验用户名
        R result = checkData(user.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return R.build(400, " 用户名重复 ");
        }
        if (null != user.getPhone()) {
            result = checkData(user.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return R.build(400, " 手机号重复 ");
            }
        }
        if (null != user.getEmail()) {
            result = checkData(user.getEmail(), 3);
            if (!(boolean) result.getData()) {
                return R.build(400, " 邮箱重复 ");
            }
        }
        // 插入数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userDao.save(user);

        return R.ok();
    }

    @Override
    public R login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return R.error(" 参数错误 ");
        }
        User user = userDao.findUserByUsername(username);
        if (null == user) {
            return R.build(400, " 该用户不存在 ");
        }
        // 校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return R.build(400, " 密码错误 ");
        }
        // 登录成功
        String token = UUID.randomUUID().toString();
        // 密码不能相应给前端
        user.setPassword(null);
        redisTemplate.opsForValue().set(REDIS_SESSION_KEY + ":" + token, user);
        redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);

        return R.ok(token);
    }

    @Override
    public R getUserByToken(String token) {
        User user = (User) redisTemplate.opsForValue().get(REDIS_SESSION_KEY + ":" + token);
        if (null == user) {
            return R.build(201, " 用户登录信息已经过期！");
        }
        redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);
        return R.ok(user);
    }
}
