package id.ic.vokta.rest.validator;

import id.ic.vokta.rest.model.EmailRequest;
import id.ic.vokta.rest.model.UserRequest;

@Validator
public class UserValidator extends BaseValidator{

    public UserValidator() {
        // Empty Constructor
    }

    public boolean validate(EmailRequest request) {
        return notNull(request) && validate(request.getMail());
    }

    public boolean validate(UserRequest request) {
        return notNull(request)
                && validate(request.getFullname())
                && validate(request.getFirstname())
                && validate(request.getLastname())
                && validate(request.getEmail())
                && validate(request.getMobileNo())
                && validate(request.getAddress());
    }
}
