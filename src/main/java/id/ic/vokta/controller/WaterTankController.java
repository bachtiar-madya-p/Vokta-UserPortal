package id.ic.vokta.controller;

import id.ic.vokta.model.WaterTankBrand;
import id.ic.vokta.model.WaterTankDetail;
import id.ic.vokta.util.property.Constant;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WaterTankController extends BaseController {

    public static final String TANK_NOT_FOUND = "Tank not found!";
    public WaterTankController() {
        log = getLogger(this.getClass());
    }

    public List<WaterTankBrand> getTankBrands() {
        final String methodName = "getTankBrands";
        start(methodName);

        List<WaterTankBrand> brands = new ArrayList<>();

        String sql = "SELECT uid, name, createDt, modifyDt FROM tank_brand ORDER BY name;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            brands = q.mapToBean(WaterTankBrand.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, TANK_NOT_FOUND);
            }
        }
        completed(methodName);
        return brands;
    }

    public List<WaterTankDetail> getWaterTankType(String brandUid, String filter) {
        final String methodName = "getWaterTankType";
        start(methodName);

        List<WaterTankDetail> list = new ArrayList<>();

        String sql = "SELECT uid, name, capacity, diameter, height FROM tank_type WHERE brandUid = :brandUid ";
        if(filter != null){
            sql += "AND name LIKE '%" + filter + "%'";
        }
        sql += " ORDER BY name;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("brandUid", brandUid);
            list = q.mapToBean(WaterTankDetail.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, TANK_NOT_FOUND);
            }
        }
        completed(methodName);
        return list;
    }

    public WaterTankDetail getTankDetail(String uid) {
        final String methodName = "getTankDetail";
        start(methodName);

        WaterTankDetail tankDetail = new WaterTankDetail();

        String sql = "SELECT uid, brandUid, name, capacity, diameter, height FROM tank_type WHERE uid = :uid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", uid);
            tankDetail = q.mapToBean(WaterTankDetail.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, TANK_NOT_FOUND);
            }
        }
        completed(methodName);
        return tankDetail;
    }
}
