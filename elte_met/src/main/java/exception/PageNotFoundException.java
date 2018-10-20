package exception;

public class PageNotFoundException extends Exception {
    public PageNotFoundException(){
        super("Page not found");
    }
}
