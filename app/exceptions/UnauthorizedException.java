package exceptions;

public class UnauthorizedException extends RuntimeException  {

    public UnauthorizedException(){
        super("Email already exists");
    }
}
