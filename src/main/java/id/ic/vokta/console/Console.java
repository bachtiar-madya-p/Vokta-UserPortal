package id.ic.vokta.console;

import id.ic.vokta.manager.EncryptionManager;

public class Console {

    public static void main(String[] args) {
        System.out.println("Password: " + EncryptionManager.getInstance().encrypt("yzznPd372I96BLJSX8FAPLjUGgDBXnTiJnxVbUX1"));
        System.out.println("Decrypt: " + EncryptionManager.getInstance().decrypt("31QTmAGMdFCbuRXzxHUGNfIEWPri/Kxpx4Yn/VjbMW4="));
    }
}
