package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {

    MessageDAO messageDAO;

    //Constructor for MessageService which instantiates the messageDAO

    public MessageService(){
        messageDAO = new MessageDAO();

    }
    // Constructor for when message DAO is provided

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message messageRegistration(Message message){
        if(message.getMessage_text().length() >= 1 && message.getMessage_text().length() <= 255){
            if(messageDAO.doesMessageIdExist(message.getMessage_id())){
                Message addedmessage = messageDAO.insertMessage(message);
                return addedmessage;
            }else{
                return null;
            }
        }else{
            return null;
        }



    }

  

}
