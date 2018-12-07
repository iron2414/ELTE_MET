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
public interface DdsRepository extends JpaRepository<Dds, Long> , QuerydslPredicateExecutor<Dds>,
        QuerydslBinderCustomizer<QDds> {
    Dds findById(Integer id);
    List<Dds> findBySubject(Subject subject);
    List<Dds> findByPractice(Practice practice);



    @Override
    default public void customize(
            QuerydslBindings bindings, QDds root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Dds findDds(String id) {
        return DdsRepositoryImpl.findDds(id);
    }
}