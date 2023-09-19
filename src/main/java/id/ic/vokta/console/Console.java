package id.ic.vokta.console;

import id.ic.vokta.manager.EncryptionManager;
import id.ic.vokta.util.log.AppLogger;
import id.ic.vokta.util.property.Constant;

public class Console {

    static AppLogger log = new AppLogger(Constant.class);

    public static void main(String[] args) {
        String methodName = "main";
        log.debug(methodName, "Password : " + EncryptionManager.getInstance().encrypt("yzznPd372I96BLJSX8FAPLjUGgDBXnTiJnxVbUX1"));
        log.debug(methodName, "Decrypt  : " + EncryptionManager.getInstance().decrypt("31QTmAGMdFCbuRXzxHUGNfIEWPri/Kxpx4Yn/VjbMW4="));
    }
}
