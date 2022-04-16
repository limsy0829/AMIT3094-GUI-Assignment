<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.domain.Payment"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>

<html>
    <head>
          <%@ include file="HTML_parts/AdminHeader.jsp" %>
        <title>Annual Sales Report</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            html{
                 background-color:#fff7ed ;
            }
            h1{
                text-align: center;
                border-style: outset;
                font-family: Arial;
                border: 5px solid black;
                width:500px;
                margin: auto;
                justify-content: center;
                margin-top: 60px;
                background-color: #ffda75;
            }
            
            table.record{
                border:4px solid black;
                margin:auto;
                font-size:15px;
                overflow: hidden;
                border-spacing: 0;
            }
            
            table.record tr, table.record td, table.record th{
                border: 2pt solid black;
                width: 200px;
                padding:0px 10px;
                height: 40px;
            }
            
            table.record tr:nth-child(odd){
                background-color: #fafafa;
            }
            table.record tr:nth-child(even){
                background-color: #ffedbd;
            }

            ul{
                text-align: justify;
                border-style: outset;
                font-family: fantasy;
                border: 5px solid black;
                width:100px;
                justify-content: flex-end;
                margin-top: 0px;
                background-color: #ffda75;
            }
            
            .all{
                margin: 50px 0px 30px 230px;
                width: 1100px;
            }
        </style>
    </head>
    
    <body>
            <h1>Annual Sales Report</h1>
                <div class="all">
                </div>  
            <br/>
            <%

    String host = "jdbc:derby://localhost:1527/MCD";
    String user = "nbuser";
    String password = "nbuser";
    String tableName = "PAYMENT";

    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
     %>
                <table class="record">
                <tr style="background-color: #ffda75">
                    <th>Month</th>
                    <th>Total Annual Sales</th>
                </tr>
                
                  <%
try{ 
conn = DriverManager.getConnection(host, user, password);
System.out.println("***TRACE: Connection established.");
stmt = conn.createStatement();
String sql ="SELECT * FROM PAYMENT";

resultSet = stmt.executeQuery(sql);
while(resultSet.next()){
%>
                    <tr>
                        <td><%=resultSet.getString("DATE_TIME") %></td>
                        <td><%=resultSet.getDouble("TOTAL_PAYMENT_AMOUNT") %></td>
                    </tr>
                    
                    <% 
}
 stmt.close();
} catch (Exception e) {
e.printStackTrace();
}
%>
   
<ul>
   <li><a href="report.jsp">Home</a></li>
</ul>


                </body>
</html>
