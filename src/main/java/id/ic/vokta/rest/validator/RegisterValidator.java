package id.ic.vokta.rest.validator;

import id.ic.vokta.rest.model.EmailRequest;
import id.ic.vokta.rest.model.RegisterVerifyRequest;
import id.ic.vokta.rest.model.UserRequest;

@Validator
public class RegisterValidator extends BaseValidator{

    public RegisterValidator() {
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

    public boolean validate(RegisterVerifyRequest request) {
        return notNull(request)
                && validate(request.getEmail())
                && validate(request.getOtp());
    }
}
