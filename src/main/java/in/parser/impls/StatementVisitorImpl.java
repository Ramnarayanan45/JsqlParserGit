//package in.parser.impls;
//
//import in.parser.queryparser.QueryLayer;
//import in.parser.queryparser.RestrictTablesColumns;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.statement.*;
//import net.sf.jsqlparser.statement.alter.*;
//import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
//import net.sf.jsqlparser.statement.analyze.Analyze;
//import net.sf.jsqlparser.statement.comment.Comment;
//import net.sf.jsqlparser.statement.create.index.CreateIndex;
//import net.sf.jsqlparser.statement.create.schema.CreateSchema;
//import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
//import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
//import net.sf.jsqlparser.statement.create.table.CreateTable;
//import net.sf.jsqlparser.statement.create.view.*;
//import net.sf.jsqlparser.statement.delete.*;
//import net.sf.jsqlparser.statement.drop.Drop;
//import net.sf.jsqlparser.statement.execute.Execute;
//import net.sf.jsqlparser.statement.grant.Grant;
//import net.sf.jsqlparser.statement.insert.*;
//import net.sf.jsqlparser.statement.merge.Merge;
//import net.sf.jsqlparser.statement.refresh.RefreshMaterializedViewStatement;
//import net.sf.jsqlparser.statement.select.*;
//import net.sf.jsqlparser.statement.show.*;
//import net.sf.jsqlparser.statement.truncate.Truncate;
//import net.sf.jsqlparser.statement.update.*;
//import net.sf.jsqlparser.statement.upsert.Upsert;
//
//
//public class StatementVisitorImpl implements StatementVisitor<QueryLayer> {
//    SelectVisitorImpl sv;
//    InsertVisitorImpl iv;
//    UpdateVisitorImpl uv;
//    DeleteVisitorImpl dv;
//    ExpressionVisitorImpl exv;
//
//    public StatementVisitorImpl(SelectVisitorImpl sv, RestrictTablesColumns restrictTablesColumns, ExpressionVisitorImpl exv) {
//        this.sv = sv;
//        this.exv = exv;
//        iv = new InsertVisitorImpl(sv, restrictTablesColumns);
//        uv = new UpdateVisitorImpl();
//        dv = new DeleteVisitorImpl();
//    }
//    @Override
//    public <S> QueryLayer visit(Analyze analyze, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(SavepointStatement savepointStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(RollbackStatement rollbackStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Comment comment, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Commit commit, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Delete delete, S context) {
//        QueryLayer layer=(QueryLayer)context;
//        return dv.handle(delete,layer);
//    }
//
//    @Override
//    public <S> QueryLayer visit(Update update, S context) {
//        QueryLayer layer=(QueryLayer)context;
//        return  uv.handle(update,layer);
//    }
//
//    @Override
//    public <S> QueryLayer visit(Insert insert, S context) {
//        QueryLayer layer = (QueryLayer) context;
//        if (insert.getTable() != null) {
//            iv.visit(insert.getTable(), layer);
//        }
//        if (insert.getColumns() != null) {
//            iv.visit(insert.getColumns(), layer);
//        }
//        if (insert.getSelect() != null) {
//            iv.visit(insert.getSelect(), layer);
//        }
//        else if (insert.getValues() != null) {
//            iv.visit(insert.getColumns(), insert.getValues(), layer);
//        }
//        return layer;
//    }
//    @Override
//    public <S> QueryLayer visit(Drop drop, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Truncate truncate, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateIndex createIndex, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateSchema createSchema, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateTable createTable, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateView createView, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(AlterView alterView, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(RefreshMaterializedViewStatement materializedView, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Alter alter, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Statements statements, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Execute execute, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(SetStatement set, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ResetStatement reset, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ShowColumnsStatement showColumns, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ShowIndexStatement showIndex, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ShowTablesStatement showTables, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Merge merge, S context) {
//        return null;
//    }
//
////    @Override
////    public <S> QueryLayer visit(Select select, S context) {
////
////        QueryLayer layer = (QueryLayer) context;
////        if (select.getWithItemsList() != null) {
////            for (WithItem<?> withItem : select.getWithItemsList()) {
////                sv.visit(withItem, context);
////            }
////        }
////        if (select.getPlainSelect() != null) {
////            sv.visit(select.getPlainSelect(), context);
////        }
////        else if (select.getSetOperationList() != null) {
////            sv.visit(select.getSetOperationList(), context);
////        }
////
////        return layer;
////    }
//@Override
//public <S> QueryLayer visit(Select select, S context) {
//
//    QueryLayer layer = (QueryLayer) context;
//
//    if (select.getWithItemsList() != null) {
//        for (WithItem<?> withItem : select.getWithItemsList()) {
//            sv.visit(withItem, context);
//        }
//    }
//    if (select instanceof PlainSelect plainSelect) {
//        sv.visit(plainSelect, context);
//    }
//    else if (select instanceof SetOperationList setOpList) {
//        sv.visit(setOpList, context);
//    }
//    else if (select instanceof ParenthesedSelect parenthesedSelect) {
//        Select inner = parenthesedSelect.getSelect();
//        if (inner instanceof PlainSelect innerPlain) {
//            sv.visit(innerPlain, context);
//        }
//        else if (inner instanceof SetOperationList innerSetOp) {
//            sv.visit(innerSetOp, context);
//        }
//    }
//    else {
//        try {
//            PlainSelect ps = select.getPlainSelect();
//            if (ps != null) {
//                sv.visit(ps, context);
//            }
//        }
//        catch (ClassCastException e) {
//            SetOperationList sol = select.getSetOperationList();
//            if (sol != null) {
//                sv.visit(sol, context);
//            }
//        }
//    }
//
//    return layer;
//}
//    @Override
//    public <S> QueryLayer visit(Upsert upsert, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(UseStatement use, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Block block, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(DescribeStatement describe, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ExplainStatement explainStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ShowStatement showStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(DeclareStatement declareStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(Grant grant, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateSequence createSequence, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(AlterSequence alterSequence, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateFunctionalStatement createFunctionalStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(CreateSynonym createSynonym, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(AlterSession alterSession, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(IfElseStatement ifElseStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(RenameTableStatement renameTableStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(PurgeStatement purgeStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(AlterSystemStatement alterSystemStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(UnsupportedStatement unsupportedStatement, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ParenthesedInsert parenthesedInsert, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ParenthesedUpdate parenthesedUpdate, S context) {
//        return null;
//    }
//
//    @Override
//    public <S> QueryLayer visit(ParenthesedDelete parenthesedDelete, S context) {
//        return null;
//    }
//
//}


package in.parser.impls;

import in.parser.queryparser.QueryLayer;
import in.parser.queryparser.RestrictTablesColumns;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.*;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.analyze.Analyze;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.*;
import net.sf.jsqlparser.statement.delete.*;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.*;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.refresh.RefreshMaterializedViewStatement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.show.*;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.*;
import net.sf.jsqlparser.statement.upsert.Upsert;


public class StatementVisitorImpl implements StatementVisitor<QueryLayer> {
    SelectVisitorImpl sv;
    InsertVisitorImpl iv;
    UpdateVisitorImpl uv;
    DeleteVisitorImpl dv;
    ExpressionVisitorImpl exv;

    public StatementVisitorImpl(SelectVisitorImpl sv, RestrictTablesColumns restrictTablesColumns, ExpressionVisitorImpl exv) {
        this.sv = sv;
        this.exv = exv;
        iv = new InsertVisitorImpl(sv, restrictTablesColumns);
        uv = new UpdateVisitorImpl();
        dv = new DeleteVisitorImpl();
    }
    @Override
    public <S> QueryLayer visit(Analyze analyze, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(SavepointStatement savepointStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(RollbackStatement rollbackStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Comment comment, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Commit commit, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Delete delete, S context) {
        QueryLayer layer=(QueryLayer)context;
        return dv.handle(delete,layer);
    }

    @Override
    public <S> QueryLayer visit(Update update, S context) {
        QueryLayer layer=(QueryLayer)context;
        return  uv.handle(update,layer);
    }

    @Override
    public <S> QueryLayer visit(Insert insert, S context) {
        QueryLayer layer = (QueryLayer) context;
        if (insert.getTable() != null) {
            iv.visit(insert.getTable(), layer);
        }
        if (insert.getColumns() != null) {
            iv.visit(insert.getColumns(), layer);
        }
        if (insert.getSelect() != null) {
            iv.visit(insert.getSelect(), layer);
        }
        else if (insert.getValues() != null) {
            iv.visit(insert.getColumns(), insert.getValues(), layer);
        }
        return layer;
    }
    @Override
    public <S> QueryLayer visit(Drop drop, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Truncate truncate, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateIndex createIndex, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateSchema createSchema, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateTable createTable, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateView createView, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AlterView alterView, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(RefreshMaterializedViewStatement materializedView, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Alter alter, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Statements statements, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Execute execute, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(SetStatement set, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ResetStatement reset, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ShowColumnsStatement showColumns, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ShowIndexStatement showIndex, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ShowTablesStatement showTables, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Merge merge, S context) {
        return null;
    }

    //    @Override
//    public <S> QueryLayer visit(Select select, S context) {
//
//        QueryLayer layer = (QueryLayer) context;
//        if (select.getWithItemsList() != null) {
//            for (WithItem<?> withItem : select.getWithItemsList()) {
//                sv.visit(withItem, context);
//            }
//        }
//        if (select.getPlainSelect() != null) {
//            sv.visit(select.getPlainSelect(), context);
//        }
//        else if (select.getSetOperationList() != null) {
//            sv.visit(select.getSetOperationList(), context);
//        }
//
//        return layer;
//    }
    @Override
    public <S> QueryLayer visit(Select select, S context) {

        QueryLayer layer = (QueryLayer) context;

        // Handle WITH clause items first
        if (select.getWithItemsList() != null) {
            for (WithItem<?> withItem : select.getWithItemsList()) {
                sv.visit(withItem, context);
            }
        }

        // Dispatch the main body. For a top-level WITH query, JSQLParser 5.x wraps
        // the outer SELECT in a Select whose subclass is NOT PlainSelect — the actual
        // PlainSelect is stored via getPlainSelect(). We must visit it directly.
        // For CTE bodies re-entering this method, they ARE a subclass (SetOperationList
        // or ParenthesedSelect), so instanceof catches them first and getPlainSelect()
        // is never called on them (avoiding ClassCastException).
        if (select instanceof PlainSelect plainSelect) {
            sv.visit(plainSelect, context);
        }
        else if (select instanceof SetOperationList setOpList) {
            sv.visit(setOpList, context);
        }
        else if (select instanceof ParenthesedSelect parenthesedSelect) {
            // This is a CTE body (e.g. a UNION wrapped in parens) — visit its inner select
            Select inner = parenthesedSelect.getSelect();
            if (inner instanceof PlainSelect innerPlain) {
                sv.visit(innerPlain, context);
            }
            else if (inner instanceof SetOperationList innerSetOp) {
                sv.visit(innerSetOp, context);
            }
        }
        else {
            // Top-level WITH query: bare Select holding PlainSelect or SetOperationList
            // via getters — safe to call here because instanceof already excluded subclasses
            try {
                PlainSelect ps = select.getPlainSelect();
                if (ps != null) {
                    sv.visit(ps, context);
                }
            } catch (ClassCastException e) {
                // body is a SetOperationList
                SetOperationList sol = select.getSetOperationList();
                if (sol != null) {
                    sv.visit(sol, context);
                }
            }
        }

        return layer;
    }

    @Override
    public <S> QueryLayer visit(Upsert upsert, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(UseStatement use, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Block block, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(DescribeStatement describe, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ExplainStatement explainStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ShowStatement showStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(DeclareStatement declareStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(Grant grant, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateSequence createSequence, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AlterSequence alterSequence, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateFunctionalStatement createFunctionalStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(CreateSynonym createSynonym, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AlterSession alterSession, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(IfElseStatement ifElseStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(RenameTableStatement renameTableStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(PurgeStatement purgeStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(AlterSystemStatement alterSystemStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(UnsupportedStatement unsupportedStatement, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedInsert parenthesedInsert, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedUpdate parenthesedUpdate, S context) {
        return null;
    }

    @Override
    public <S> QueryLayer visit(ParenthesedDelete parenthesedDelete, S context) {
        return null;
    }

}
