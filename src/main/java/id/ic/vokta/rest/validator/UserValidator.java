package id.ic.vokta.rest.validator;

import id.ic.vokta.rest.model.EmailRequest;
import id.ic.vokta.rest.model.UserRequest;
import id.ic.vokta.rest.model.UserUpdateRequest;

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
                && validate(request.getFirstname())
                && request.getLastname()!= null || !request.getLastname().isEmpty()
                && validate(request.getEmail())
                && validate(request.getMobileNo())
                && validate(request.getAddress())
                && validate(request.getPassword());
    }

    public boolean validate(UserUpdateRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getFirstname())
                && request.getLastname()!= null || !request.getLastname().isEmpty()
                && validate(request.getEmail())
                && validate(request.getMobileNo())
                && validate(request.getAddress());
    }
}
