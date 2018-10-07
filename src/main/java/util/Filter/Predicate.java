package util.Filter;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;


public class Predicate<T> {

    private final Class<T> type;
    private SearchCriteria criteria;

    public Predicate(Class<T> type, SearchCriteria param) {
        this.criteria = param;
        this.type = type;
    }

    public BooleanExpression getPredicate() {
        String[] typeArray = type.toString().split("\\.");
        PathBuilder<T> entityPath = new PathBuilder<>(type, typeArray[typeArray.length - 1].toLowerCase());
 
        if (criteria.getValue().toString().matches("-?\\d+(\\.\\d+)?")) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            Integer value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                case "=":
                    return path.eq(value);
                case ">":
                    return path.gt(value);
                case "<":
                    return path.lt(value);
                case ">=":
                    return path.goe(value);
                case "<=":
                    return path.loe(value);
            }
        } 
        else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }
}