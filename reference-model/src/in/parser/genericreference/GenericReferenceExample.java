package in.parser.genericreference;

public class GenericReferenceExample {

    public static GenericReferenceModel sample() {
        return new GenericReferenceBuilder()
                .table("table:customer", "customer", "c")
                .table("table:orders", "orders", "o")
                .column("column:customer.id", "id", "table:customer", "SELECT")
                .column("column:orders.customer_id", "customer_id", "table:orders", "JOIN")
                .column("column:orders.amount", "amount", "table:orders", "SELECT")
                .join(
                        "join:customer-orders",
                        "INNER",
                        "table:customer",
                        "table:orders",
                        "column:customer.id",
                        "column:orders.customer_id",
                        "customer.id = orders.customer_id"
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
