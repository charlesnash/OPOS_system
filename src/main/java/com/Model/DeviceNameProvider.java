package com.Model;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceNameProvider {

    private Document dom;
    private File xmlFile;

    @PostConstruct
    public void init() {
        try {
            xmlFile = new File("src/main/resources/jpos/jpos.xml");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            dom = builder.parse(xmlFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return first logical device
    public String getDeviceName() {
        List<String> devices = getAllDeviceNames();
        return devices.isEmpty() ? null : devices.get(0);
    }

    // Return ALL logical device names
    public List<String> getAllDeviceNames() {
        List<String> list = new ArrayList<>();

        try {
            NodeList entries = dom.getElementsByTagName("JposEntry");

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);
                list.add(entry.getAttribute("logicalName"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
