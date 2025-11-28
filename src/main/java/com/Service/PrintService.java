package com.Service;
import com.Model.DeviceNameProvider;
import jpos.POSPrinter;
import jpos.JposException;
import jpos.POSPrinterConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Service
public class PrintService {

    @Autowired
    private DeviceNameProvider deviceNameProvider;

    public void printSample() throws JposException {

        URL url = getClass().getClassLoader().getResource("jpos/jpos.xml");
        System.setProperty("jpos.config.file", new File(url.getPath()).getAbsolutePath());

        POSPrinter printer = new POSPrinter();

        String logicalName = deviceNameProvider.getDeviceName();

        printer.open(logicalName);
        printer.claim(1000);
        printer.setDeviceEnabled(true);

        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                "Hello from Spring Boot!\n");

        printer.cutPaper(90);

        printer.release();
        printer.close();
    }

}
