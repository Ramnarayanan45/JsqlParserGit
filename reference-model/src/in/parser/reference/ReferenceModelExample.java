package in.parser.reference;

public class ReferenceModelExample {

    public static QueryReferenceModel buildSample() {
        QueryReferenceModel model = new QueryReferenceModel();

        TableReference customer = new TableReference("customer", "c");
        TableReference orders = new TableReference("orders", "o");

        ColumnReference customerId = new ColumnReference("id", customer);
        ColumnReference orderCustomerId = new ColumnReference("customer_id", orders);
        ColumnReference orderAmount = new ColumnReference("amount", orders);

        JoinReference join = new JoinReference(
                "INNER",
                customer,
                customerId,
                orders,
                orderCustomerId
        );

        model.addTable(customer);
        model.addTable(orders);
        model.addColumn(customerId);
        model.addColumn(orderCustomerId);
        model.addColumn(orderAmount);
        model.addJoin(join);

        model.addLink(new ObjectLinkReference(
                customerId.getFullyQualifiedName(),
                orderCustomerId.getFullyQualifiedName(),
                "FK_EQ",
                "customer.id = orders.customer_id"
        ));

        return model;
    }
}
