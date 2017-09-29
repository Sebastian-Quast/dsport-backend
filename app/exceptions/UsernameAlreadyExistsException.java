package exceptions;

public class UsernameAlreadyExistsException extends RuntimeException  {

    public UsernameAlreadyExistsException(){
        super("UserNode already exists");
    }
}
