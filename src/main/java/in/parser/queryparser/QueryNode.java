package in.parser.queryparser;

public class QueryNode<T> {

    String category;
    T value;

    public QueryNode(String category,T value){
        this.category = category;
        this.value = value;
    }

    public String getCategory(){
        return category;
    }

    public T getValue(){
        return value;
    }

    public String getName(){
        if (value == null){
            return "";
        }
        return value.toString(); 
    }

}