package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import DAO.AccountDAO;
import DAO.MessageDAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    AccountDAO accountDAO;
    MessageDAO messageDAO;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
        messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::insertAccountHandler);
        app.post("/login", this::verifyLoginHandler);
        app.post("/messages", this::messageCreationHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAllMessagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUser);




        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    // Handler to post new account
    private void insertAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(),Account.class);
        Account addedaccount = accountService.userRegistration(account);
        if(addedaccount == null){
            ctx.status(400);
        }else{
            ctx.json(addedaccount);
            ctx.status(200);
        }
    }

    private void verifyLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(),Account.class);
        if( accountDAO.doesUserExist(account)){
            ctx.json(account);

    }else{
        ctx.status(401);
    }
}

    private void messageCreationHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
            Message addedMessage = messageService.messageRegistration(message); 
            if(addedMessage == null){
                ctx.status(400);

            } else{
                ctx.json(addedMessage);
            }
    }
    // get all messages handler
    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageDAO.getAllMessages());
    }

    // get all messages by i.d handler
    private void getAllMessagesByIdHandler(Context ctx){
        int messageid = Integer.parseInt(ctx.pathParam("message_id"));
        if(messageDAO.doesMessageIdExist(messageid)){
        ctx.json(messageDAO.getMessageById(messageid));
        }else{
            ctx.status(200);
            ctx.result("");
        }
    }

    // delete message by i.d handler
    private void deleteMessageById(Context ctx){
        int messageid = Integer.parseInt(ctx.pathParam("message_id"));
        if(messageDAO.doesMessageIdExist(messageid)){

        messageDAO.deleteMessageById(messageid);
        ctx.json(messageDAO.getMessageById(messageid));
        }else{

        
        ctx.status(200);
        ctx.result("");
        }


    }

    // update message by i.d handler
    private void updateMessageById(Context ctx)throws JsonProcessingException{
        int messageid = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message addedMessage = messageService.messageRegistration(message);
        if(addedMessage == null){
            ctx.status(400);
        }else{
            messageDAO.updateMessage(messageid, message);
            ctx.json(message);
        }
    }
    //get all messages by user
    private void getAllMessagesByUser(Context ctx){
        int accountid = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageDAO.getAllMessagesByAccountId(accountid));
    }


}
