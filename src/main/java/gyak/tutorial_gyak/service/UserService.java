package gyak.tutorial_gyak.service;
import gyak.tutorial_gyak.model.Message;
import gyak.tutorial_gyak.model.User;

import gyak.tutorial_gyak.repo.MessageRepo;
import gyak.tutorial_gyak.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;


@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MessageRepo messageRepo;

    public boolean registerUser(String username, String email, String password, String password2) {
        if (userRepo.existsUserByEmail(email)) {
            return false;
        }
        if (userRepo.existsUserByUsername(username)) {
            return false;
        }
        if (!password.equals(password2)) {
            return false;
        }

        User user = new User(email, username, password);

        userRepo.save(user);
        return true;
    }

    public User loginUser(String username_or_email, String password) {
        try {
            if (userRepo.existsUserByEmailAndPassword(username_or_email, password)) {
                return userRepo.getUserByEmailAndPassword(username_or_email, password);
            }
        } catch (Exception e) {
            return null;
        }
        try {
            if (userRepo.existsUserByUsernameAndPassword(username_or_email, password)) {
                return userRepo.getUserByUsernameAndPassword(username_or_email, password);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    public boolean sendMessage(String username, String password, String to, String msg) {
        User user = loginUser(username, password);

        User toUser = null;

        if (userRepo.existsUserByEmail(to)) {
            toUser = userRepo.getUserByEmail(to);
        }
        if (userRepo.existsUserByUsername(to)) {
            toUser = userRepo.getUserByUsername(to);
        }

        if (toUser != null && user != null && !toUser.equals(user) && !msg.equals("") && !msg.equals(" ")) {
            Message message = new Message(user, toUser, msg);
            messageRepo.save(message);
            return true;
        }
        return false;

    }

    public JSONObject getInbox(String username, String password) {
        try {
            User user;

            try {
                user = loginUser(username, password);
                if (user == null) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }

            JSONObject result = new JSONObject();

            for (Message message : messageRepo.findAllByReceiverOrderByLocalDateTime(user)) {
                JSONObject messageObject = new JSONObject();
                messageObject.put("sender", message.getSender().getUsername());
                messageObject.put("message", message.getText());
                result.put(message.getLocalDateTime().toString(), messageObject);
            }
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
