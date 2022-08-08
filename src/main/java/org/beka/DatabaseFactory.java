
package org.beka;
import java.sql.*;
import java.util.ArrayList;
import javax.sql.DataSource;

//import io.javalin.Context;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.*;

//import io.javalin.Context.*;
//import io.javalin.json.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.json.JSONObject;

public class DatabaseFactory {

    private static final String DB_DRIVER = "jdbc:sqlserver://EREVHDIPC027;databaseName=users_database;integratedSecurity=true;user=fegboou;password=Fleurisegb";
    private static final String SQLQUERY = "SELECT * FROM users;";

    private static DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(DB_DRIVER);
        return ds;
    }

  /*  public static void getUser(Context context) {
        DataSource dataSource = setupDataSource();
        String connectionUrl =
                "jdbc:sqlserver://EREVHDIPC027;integratedSecurity=true;"
                        + "database=Hyperdist_Prod;"
                        + "user=fegboou;"
                        + "password=Fleurisegb;";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT    TBLPROD.PROD_ID,TBLMARQUE.MARQUE_LIB + ' ' + TBLPROD.PROD_DESIGN + ' [PCB : ' + cast(PROD_PCB as varchar) + ']'  as PRODLIB,TBLPRODMAG.PROD_PXVENTEUTTC\n" +
                    " FROM         TBLPROD INNER JOIN\n" +
                    "TBLLOT ON TBLPROD.PROD_ID = TBLLOT.PROD_ID INNER JOIN\n" +
                    "TBLPRODMAG ON TBLPROD.PROD_ID = TBLPRODMAG.PROD_ID AND TBLLOT.MAGASIN_ID = TBLPRODMAG.MAGASIN_ID INNER JOIN\n" +
                    "TBLMARQUE ON TBLPROD.MARQUE_ID = TBLMARQUE.MARQUE_ID\n" +
                    " WHERE LOT_PRODBARCODE =  '3256226383947';";
            resultSet = statement.executeQuery(selectSql);
            ArrayList<String> resultstring = new ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            // Print results from select statement
            while (resultSet.next()) {
                resultstring.add("ID: " + resultSet.getInt("PROD_ID") + " | Name: " + resultSet.getString("PRODLIB"));
                jsonObject.append("ID",resultSet.getInt("PROD_ID"));
                jsonObject.append("Name",resultSet.getString("PRODLIB"));
                jsonObject.append("Prix",resultSet.getInt("PROD_PXVENTEUTTC"));

            }
            context.result(String.valueOf(jsonObject));
            ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    @OpenApi(
            summary = "Get user by ID",
            operationId = "getUserById",
            path = "/users/:userId",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "userId", type = Integer.class, description = "The user ID")},
            tags = {"User"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User.class)}),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void getOne(Context ctx) {

        //User user = UserService.findById(validPathParamUserId(ctx));
        CodebarService.findById(validPathParamUserId(ctx),ctx);


    }
    private static String validPathParamUserId(Context ctx) {
        return ctx.pathParamAsClass("userId",String.class).get();
    }
}
