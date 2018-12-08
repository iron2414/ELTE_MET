package app.entity;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> , QuerydslPredicateExecutor<Message>,
        QuerydslBinderCustomizer<QMessage> {
    Message findById(Integer id);
    List<Message> findByReceiver(User receiver);



    @Override
    default public void customize(
            QuerydslBindings bindings, QMessage root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Message findMessage(String id) {
        return MessageRepositoryImpl.findMessage(id);
    }
}