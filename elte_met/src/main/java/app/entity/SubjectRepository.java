package app.entity;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    List<Subject> findByStudents_Username(String userName);

    public static Subject findSubject(String id) {
        return SubjectRepositoryImpl.findSubject(id);
    }
}