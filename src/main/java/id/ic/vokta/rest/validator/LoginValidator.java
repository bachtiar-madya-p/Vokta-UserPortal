package id.ic.vokta.rest.validator;

import id.ic.vokta.rest.model.LoginRequest;

@Validator
public class LoginValidator extends BaseValidator{

    public LoginValidator() {
        // Empty Constructor
    }

    public boolean validate(LoginRequest request) {
        return notNull(request)
                && validate(request.getEmail())
                && validate(request.getPassword());
    }

}
