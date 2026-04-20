package in.parser.queryparser;

import java.util.*;

public class ParamGenerator {

    List<ConditionMapping<?>> conditionsList = new ArrayList<>();

    public void addParam(String column, List<Object> value) {
        conditionsList.add(new ConditionMapping<>(column, value));
    }

    public List<ConditionMapping<?>> getParamList() {
        return conditionsList;
    }
}