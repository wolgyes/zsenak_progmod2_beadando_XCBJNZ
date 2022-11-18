package gyak.tutorial_gyak.repo;

import gyak.tutorial_gyak.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    boolean existsUserByEmail(String email);
    boolean existsUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User getUserByUsernameAndPassword(String username, String password);
    User getUserByEmailAndPassword(String email, String password);

    boolean existsUserByUsernameAndPassword(String username, String password);

    boolean existsUserByEmailAndPassword(String email, String password);
}
