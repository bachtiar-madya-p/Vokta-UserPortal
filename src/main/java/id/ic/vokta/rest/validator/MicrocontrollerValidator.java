package id.ic.vokta.rest.validator;

import id.ic.vokta.rest.model.MicrocontrollerRequest;

@Validator
public class MicrocontrollerValidator extends BaseValidator{

    public MicrocontrollerValidator() {
        // Empty Constructor
    }
    public boolean validateCreate(MicrocontrollerRequest request) {
        return notNull(request)
                && validate(request.getSensorId())
                && validate(request.getSensorName())
                && validate(request.getTankBrand())
                && validate(request.getTankType())
                && validate(request.getCapacity())
                && validate(request.getDiameter())
                && validate(request.getHeight());
    }
    public boolean validateUpdate(MicrocontrollerRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getSensorId())
                && validate(request.getSensorName())
                && validate(request.getTankBrand())
                && validate(request.getTankType())
                && validate(request.getCapacity())
                && validate(request.getDiameter())
                && validate(request.getHeight());
    }
}
