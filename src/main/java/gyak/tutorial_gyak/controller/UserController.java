package gyak.tutorial_gyak.controller;

import gyak.tutorial_gyak.service.UserService;
import org.apache.catalina.connector.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String registerUser(String username, String email, String password, String password2) {
        boolean user = userService.registerUser(username, email, password, password2);
        if (user) {
            return "Sikeres regisztracio";
        }
        return "Sikertelen regisztracio";
    }

    @PostMapping("/login")
    public String loginUser(String username_or_email, String password) {
        if (userService.loginUser(username_or_email, password) != null) {
            return "Sikeres bejelentkezes";
        }
        return "Sikertelen bejelentkezes";
    }

    @PostMapping("/send")
    public String sendMessage(String username, String password, String to, String msg) {

        boolean sended = userService.sendMessage(username, password, to, msg);
        if (sended){
            return "Sikeres uzenet kuldes " + to + " felhasznalonak";
        }
        return "Sikertelen uzenet kuldes";
    }

    @PostMapping(value="/inbox", produces = "application/json")
    public String getInbox(String username, String password) {
        return userService.getInbox(username, password).toString();
    }
}
