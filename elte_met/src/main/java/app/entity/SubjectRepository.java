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
public interface SubjectRepository extends JpaRepository<Subject, Long> , QuerydslPredicateExecutor<Subject>,
        QuerydslBinderCustomizer<QSubject> {
    Subject findById(Integer id);



    @Override
    default public void customize(
            QuerydslBindings bindings, QSubject root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Subject findSubject(String id) {
        return SubjectRepositoryImpl.findSubject(id);
    }
}