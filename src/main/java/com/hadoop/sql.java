package com.hadoop;

import java.io.StringReader;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class sql {
    public static void main(String[] args) throws JSQLParserException {
        String sql = "select DISTINCT RB0013 from"
                + " HBASE.S003.RWA_BASIC_0001 as a,HDFS.S003.RWA_BASIC_0002 as b,HBASE.EXT003.RWA_BASIC_0003 as c"
                + " where a.RB0001 = b.RB0002 AND b.RB0002 = c.RB0003 group by RB0012,RB0013 order by RB0012,RB0013 desc";
        getTableNameBySql(sql);
        getSelectItems(sql);
        getSelectWhere(sql);
        getSelectJoin(sql);
        getSelectOrderBy(sql);
        getSelectGroupby(sql);

    }

    public static void getSelectItems(String sql) throws JSQLParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        Statement stmt = parser.parse(new StringReader(sql));
        Select select = (Select) stmt;
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plain.getSelectItems();

        for (SelectItem selectItem : selectItems) {
            System.out.println("selectItem:" + selectItem.toString());
        }
        System.out.println("List<SelectItem> : " + selectItems.toString());
        // Top top = plain.getTop();
        // System.out.println("top:" + top.toString());

        Distinct distinct = plain.getDistinct();
        System.out.println("distinct : " + distinct.toString());

    }

    public static void getSelectWhere(String sql) throws JSQLParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        Statement stmt = parser.parse(new StringReader(sql));
        Select select = (Select) stmt;
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        Expression expression = plain.getWhere();
        String str = expression.toString();
        System.out.println("where: " + str);

    }

    public static void getSelectJoin(String sql) throws JSQLParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        Select select = (Select) parser.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<Join> joins = plain.getJoins();
        for (Join join : joins) {
            System.out.println("join: " + join.toString());
        }
        System.out.println("List<Join> : " + joins.toString());
    }

    public static void getSelectOrderBy(String sql) throws JSQLParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        Select select = (Select) parser.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<OrderByElement> orderByElements = plain.getOrderByElements();
        for (OrderByElement orderByElement : orderByElements) {
            System.out.println("orderby: " + orderByElement.toString());
        }
        System.out.println("List<OrderByElement> : " + orderByElements.toString());
    }

    public static void getSelectGroupby(String sql) throws JSQLParserException {
        CCJSqlParserManager parser = new CCJSqlParserManager();
        Select select = (Select) parser.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<Expression> expressions = plain.getGroupByColumnReferences();
        // System.out.println(expressions);
        for (Expression expression : expressions) {
            System.out.println("groupby: " + expression.toString());
        }
        System.out.println("List<Expression> : " + expressions.toString());
    }

    public static void getTableNameBySql(String sql) throws JSQLParserException {

        CCJSqlParserManager parser = new CCJSqlParserManager();
        Statement stmt = parser.parse(new StringReader(sql));
        if (stmt instanceof Select) {
            Select statement = (Select) stmt;
            System.out.println("解析sql的语句：" + statement.toString());
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List<String> tableList = tablesNamesFinder.getTableList(statement);
            for (String s : tableList) {
                System.out.println("tableName: " + s);
            }
            System.out.println("List<String> : " + tableList.toString());
        }
    }

}
