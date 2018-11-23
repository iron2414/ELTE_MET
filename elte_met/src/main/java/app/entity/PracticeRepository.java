package app.entity;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;


@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long> , QuerydslPredicateExecutor<Practice>,
        QuerydslBinderCustomizer<QPractice> {
    Practice findById(Integer id);



    @Override
    default public void customize(
            QuerydslBindings bindings, QPractice root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Practice findPractice(String id) {
        return PracticeRepositoryImpl.findDocument(id);
    }
}