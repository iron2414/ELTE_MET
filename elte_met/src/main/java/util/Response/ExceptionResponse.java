package util.Response;

import java.util.Arrays;

public class ExceptionResponse extends AbstractResponse {


    private class MyException {

        public MyException(Exception ex) {
            this.className = ex.getClass().getSimpleName();
            this.message = ex.getMessage();
            this.origMessage = ex.getMessage(); //TODO stuff
            this.trace = Arrays.toString(ex.getStackTrace());
        }
        private String className;
        private String message;  //TODO Translate/Auth/Etc
        private String origMessage;
        private String trace;
        private String data; //TODO stuff

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOrigMessage() {
            return origMessage;
        }

        public void setOrigMessage(String origMessage) {
            this.origMessage = origMessage;
        }

        public String getTrace() {
            return trace;
        }

        public void setTrace(String trace) {
            this.trace = trace;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

    }

    private MyException exception;
    public ExceptionResponse(Exception ex) {
        this.setSuccess(false);
        exception = new MyException(ex);
    }

    public MyException getException() {
        return exception;
    }
}
