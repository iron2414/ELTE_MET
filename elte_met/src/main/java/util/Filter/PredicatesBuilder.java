package util.Filter;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;
public class PredicatesBuilder<T> {

    private final Class<T> type;
    private List<SearchCriteria> params;

    public PredicatesBuilder(Class<T> type) {
        params = new ArrayList<>();
        this.type = type;
    }

    public PredicatesBuilder with(
            String key, String operation, Object value) {

        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }

        List<BooleanExpression> predicates = new ArrayList<>();
        Predicate<T> predicate;
        for (SearchCriteria param : params) {
            predicate = new Predicate<>(type, param);
            BooleanExpression exp = predicate.getPredicate();
            if (exp != null) {
                predicates.add(exp);
            }
        }

        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;
    }
}