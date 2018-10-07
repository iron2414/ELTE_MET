package util.Response;

import exception.FormException;

import java.util.List;
import java.util.Map;


public class FormResponse extends AbstractResponse {


    private class Message {

        private Form form;

        public Message(FormException ex) {
            this.form = new Form(ex);
        }

        public Form getForm() {
            return form;
        }
    }

    private class Form {
        public List<Map<String,String>> errors;

        public Form(FormException ex) {
            this.errors = ex.getErrors();
        }
    }

    private Message message;

    public FormResponse(FormException ex) {
        this.setSuccess(false);
        this.message = new Message(ex);
    }

    public Message getMessage() {
        return message;
    }
}
