package gyak.tutorial_gyak.repo;

import gyak.tutorial_gyak.model.Message;
import gyak.tutorial_gyak.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findAllByReceiverOrderByLocalDateTime(User user);
}
