package in.parser.queryparser;

import java.util.*;

public class ConditionMapping {

    List<ConditionClass<?>> conditionsList = new ArrayList<>();

    public void addCondition(String column, Object value) {
        conditionsList.add(new ConditionClass<>(column, value));
    }

    public List<ConditionClass<?>> getConditionsList() {
        return conditionsList;
    }
}