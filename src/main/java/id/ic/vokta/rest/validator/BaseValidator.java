package id.ic.vokta.rest.validator;

import id.ic.vokta.util.string.StringHelper;

public class BaseValidator {

    public BaseValidator() {
        // Empty Constructor
    }

    public boolean notNull(Object obj) {
        return null != obj;
    }

    public boolean validate(String str) {
        return StringHelper.validate(str);
    }

    public boolean validateMissingProperty(String str) {
        return StringHelper.validateMissingProperty(str);
    }

    public boolean validate(String... strs) {
        for (String str : strs)
            if (!StringHelper.validate(str)) {
                return false;
            }
        return true;
    }
}
