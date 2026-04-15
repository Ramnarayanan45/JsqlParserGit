package in.parser.graphreference;

public class QueryGraphExample {

    public static QueryGraph buildSampleGraph() {
        return new QueryGraphBuilder()
                .table("table:customer", "customer", "c")
                .table("table:orders", "orders", "o")
                .column("column:customer.id", "id", "table:customer")
                .column("column:orders.customer_id", "customer_id", "table:orders")
                .column("column:orders.amount", "amount", "table:orders")
                .join(
                        "join:customer-orders",
                        "INNER",
                        "table:customer",
                        "table:orders",
                        "column:customer.id",
                        "column:orders.customer_id"
                )
                .link(
                        "link:fk-customer-orders",
                        "FK_EQ",
                        "column:customer.id",
                        "column:orders.customer_id",
                        "customer.id = orders.customer_id"
                )
                .build();
    }
}
