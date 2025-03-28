package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    // Returns all messages

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "Select * from Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e ){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Returns messages by id

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "Select * from Message where message_id = ?; ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    // Delete message by message id

    public void deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "Delete from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Get all messages posted by a user

    public List<Message> getMessagesByUser(int id){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            String sql = "select * from message where posted_by = ? ;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery(sql);

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);

            }} catch(SQLException e ){
                System.out.println(e.getMessage());
            }
            return messages;

        }

        // Update message body specified by I.D

        public void updateMessage(int id, Message message){
            Connection connection = ConnectionUtil.getConnection();

            try{
                String sql = "Update message set message_text = ? where message_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1,message.getMessage_text());
                preparedStatement.setInt(2,id);

                preparedStatement.executeUpdate();

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }

        }

        // Persisting a message to the DB

        public Message insertMessage(Message message){
            Connection connection = ConnectionUtil.getConnection();

            try{
                String sql = "INSERT INTO message(posted_by,message_text,time_posted_epoch) VALUES (?,?,?); ";

                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1,message.getPosted_by());
                ps.setString(2,message.getMessage_text());
                ps.setLong(3,message.getTime_posted_epoch());

                ps.executeUpdate();
                ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;

            }

            public Boolean doesMessageIdExist(int id){
                Connection connection = ConnectionUtil.getConnection();
        
                try{
                    String sql = "SELECT 1 from message WHERE message_id = ? LIMIT 1;";
        
                    PreparedStatement ps = connection.prepareStatement(sql);
        
                    ps.setInt(1,id);
        
                    ResultSet rs = ps.executeQuery();
        
                    return rs.next();
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                    return false;
                }


        }

        public List<Message> getAllMessagesByAccountId(int id){
            Connection connection = ConnectionUtil.getConnection();
            List<Message> messages = new ArrayList<>();
    
            try {
                String sql = "Select * from message WHERE account_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                    messages.add(message);
                }
            } catch(SQLException e ){
                System.out.println(e.getMessage());
            }
            return messages;
        }
    


    }

    



