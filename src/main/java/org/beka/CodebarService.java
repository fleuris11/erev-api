package org.beka;

import io.javalin.http.Context;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

// This is a service, it should be independent from Javalin
public class CodebarService {



    public static void findById(String userId, Context context) {


        String connectionUrl =
                "jdbc:sqlserver://EREVHDIPC027;integratedSecurity=true;"
                        + "database=Hyperdist_Prod;"
                        + "user=fegboou;"
                        + "password=Fleurisegb;";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT  TBLMARQUE.MARQUE_LIB + ' ' + TBLPROD.PROD_DESIGN + ' [PCB : ' + cast(PROD_PCB as varchar) + ']'  as PRODLIB,TBLPRODMAG.PROD_PXVENTEUTTC\n" +
                    " FROM         TBLPROD INNER JOIN\n" +
                    "TBLLOT ON TBLPROD.PROD_ID = TBLLOT.PROD_ID INNER JOIN\n" +
                    "TBLPRODMAG ON TBLPROD.PROD_ID = TBLPRODMAG.PROD_ID AND TBLLOT.MAGASIN_ID = TBLPRODMAG.MAGASIN_ID INNER JOIN\n" +
                    "TBLMARQUE ON TBLPROD.MARQUE_ID = TBLMARQUE.MARQUE_ID\n" +
                    " WHERE LOT_PRODBARCODE =  "+"'"+userId+"'"+";";
            resultSet = statement.executeQuery(selectSql);
            ArrayList<String> resultstring = new ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            // Print results from select statement
            while (resultSet.next()) {
                //resultstring.add("ID: " + resultSet.getInt("PROD_ID") + " | Name: " + resultSet.getString("PRODLIB"));
                //jsonObject.append("ID",resultSet.getInt("PROD_ID"));
                jsonObject.append("Name",resultSet.getString("PRODLIB"));
                jsonObject.append("Prix Produit",resultSet.getInt("PROD_PXVENTEUTTC"));

            }
            context.result(String.valueOf(jsonObject));
            ;

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}