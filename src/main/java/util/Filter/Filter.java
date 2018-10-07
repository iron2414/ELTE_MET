package util.Filter;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
    private final static Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|<=|>=|=)(\\w+?),");

    public static BooleanExpression filter(PredicatesBuilder builder, String search) {
        if (search != null) {
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        return builder.build();
    }
}
