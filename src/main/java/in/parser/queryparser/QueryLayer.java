package in.parser.queryparser;

import java.util.*;

public class QueryLayer {

    public List<QueryNode<?>> nodes = new ArrayList<>();
    public List<QueryLayer> subLayers = new ArrayList<>();

    public <T> void add(String category,T value){
        if (value == null) {
            return;
        }
        nodes.add(new QueryNode<>(category,value));
    }

    public List<QueryNode<?>> getNodes(){
        return nodes;
    }
    
}