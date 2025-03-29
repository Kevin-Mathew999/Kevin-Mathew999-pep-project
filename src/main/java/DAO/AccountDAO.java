package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.security.ServerAuthException;

public class AccountDAO {

    // New account creation

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "Insert into account(username,password) Values(?,?);";

            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,account.getUsername());
            ps.setString(2,account.getPassword());

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;

}
    public Boolean doesUsernameExist(String username){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT 1 from account WHERE username = ? LIMIT 1;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,username);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public Boolean doesUserExist(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT 1 from account WHERE username = ? AND password = ? LIMIT 1;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            return rs.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public Account returnAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT 1 from account WHERE username = ? AND password = ? LIMIT 1;";

            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,account.getUsername());
            ps.setString(2,account.getPassword());

            ps.executeQuery();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;

}

    }
