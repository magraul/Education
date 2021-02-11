package me.client;

import me.controllers.FXMLControllerAngajat;
import me.teledon.UpdateService;
import org.apache.thrift.TException;

public class UpdateServer implements UpdateService.Iface {
    private FXMLControllerAngajat controllerAngajat = null;

    public void setControllerAngajat(FXMLControllerAngajat controllerAngajat) {
        this.controllerAngajat = controllerAngajat;
    }

    @Override
    public void update() throws TException {
        if (controllerAngajat != null) {
            controllerAngajat.setComponente();
        }
    }

}
