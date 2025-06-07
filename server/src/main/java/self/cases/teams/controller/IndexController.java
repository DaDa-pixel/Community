package self.cases.teams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import self.cases.teams.entity.Notices;
import self.cases.teams.msg.R;
import self.cases.teams.entity.Users;
import self.cases.teams.service.NoticesService;
import self.cases.teams.service.UsersService;
import self.cases.teams.utils.IDUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    private static final Logger Log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private NoticesService noticesService;

    @GetMapping("/sys/notices")
    @ResponseBody
    public R getNoticeList(String token){
        String userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            return R.error("用户未登录");
        }
        Users user = usersService.getOne(userId);
        if(user.getType() == 0){
            List<Notices> list = noticesService.getSysNotices();
            return R.successData(list);
        } else if(user.getType() == 1){
            List<Notices> list = noticesService.getManNotices(user.getId());
            return R.successData(list);
        } else {
            List<Notices> list = noticesService.getMemNotices(user.getId());
            return R.successData(list);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(String userName, String passWord, HttpSession session){
        Log.info("用户登录，用户名：{}， 用户密码：{}", userName, passWord);
        Users user = usersService.getUserByUserName(userName);
        if(user == null) {
            return R.error("输入的用户名不存在");
        } else {
            if(passWord.equals(user.getPassWord().trim())) {
                String token = IDUtils.makeIDByUUID();
                redisTemplate.opsForValue().set(token, user.getId());
                return R.success("登录成功", token);
            } else {
                return R.error("输入的密码错误");
            }
        }
    }

    @RequestMapping("/exit")
    @ResponseBody
    public R exit(String token) {
        Log.info("用户退出系统并移除登录信息");
        redisTemplate.delete(token);
        return R.success();
    }

    @GetMapping("/info")
    @ResponseBody
    public R info(String token){
        String userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            return R.error("用户未登录");
        }
        Users user = usersService.getOne(userId);
        return R.successData(user);
    }

    @PostMapping("/info")
    @ResponseBody
    public R info(Users user){
        Log.info("修改用户信息，{}", user);
        usersService.update(user);
        return R.success();
    }

    @RequestMapping("/checkPwd")
    @ResponseBody
    public R checkPwd(String oldPwd, String token) {
        String userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            return R.error("用户未登录");
        }
        Users user = usersService.getOne(userId);
        if(oldPwd.equals(user.getPassWord())) {
            return R.success();
        } else {
            return R.warn("原始密码和输入密码不一致");
        }
    }

    @PostMapping("/pwd")
    @ResponseBody
    public R pwd(String token, String password) {
        Log.info("修改用户密码，{}", password);
        String userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            return R.error("用户未登录");
        }
        Users user = usersService.getOne(userId);
        user.setPassWord(password);
        usersService.update(user);
        return R.success();
    }
}