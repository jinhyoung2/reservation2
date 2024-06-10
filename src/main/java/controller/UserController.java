package controller;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 사용자를 등록하는 메서드입니다.
     *
     * @param user 등록할 사용자 정보
     * @return 등록된 사용자 정보를 담은 HTTP 응답
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // 사용자 역할을 기본적으로 "ROLE_USER"로 설정
        user.setRole("ROLE_USER");
        // 사용자 등록 후 결과를 HTTP 응답으로 반환
        return ResponseEntity.ok(userService.register(user));
    }
}