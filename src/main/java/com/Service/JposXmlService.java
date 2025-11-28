package com.Service;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class JposXmlService {

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

    // ------------------------------------------
    // GET ALL JPOS DEVICES
    // ------------------------------------------
    public List<String> getAllDevices() {
        List<String> list = new ArrayList<>();
        NodeList entries = dom.getElementsByTagName("JposEntry");

        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            list.add(entry.getAttribute("logicalName"));
        }
        return list;
    }

    // ------------------------------------------
    // CHECK IF DEVICE EXISTS
    // ------------------------------------------
    public boolean isDeviceExists(String ldn) {
        NodeList entries = dom.getElementsByTagName("JposEntry");

        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);

            if (entry.getAttribute("logicalName").equalsIgnoreCase(ldn)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------
    // ADD A NEW USB DEVICE
    // ------------------------------------------
    public boolean addUsbDevice(String ldn) {
        try {
            if (isDeviceExists(ldn)) {
                return false;
            }

            Element root = dom.getDocumentElement();

            Element entry = dom.createElement("JposEntry");
            entry.setAttribute("logicalName", ldn);

            // 1️⃣ Creation
            Element creation = dom.createElement("creation");
            creation.setAttribute("factoryClass", "jpos.loader.simple.SimpleJPOSServiceInstanceFactory");
            creation.setAttribute("serviceClass", "jpos.services.BasePrinterService");
            entry.appendChild(creation);

            // 2️⃣ Vendor
            Element vendor = dom.createElement("vendor");
            vendor.setAttribute("name", "NashPrinter");
            vendor.setAttribute("url", "https://nashindia.com/solutions/");
            entry.appendChild(vendor);

            // 3️⃣ Jpos category
            Element jpos = dom.createElement("jpos");
            jpos.setAttribute("category", "POSPrinter");
            jpos.setAttribute("version", "1.1");
            entry.appendChild(jpos);

            // 4️⃣ Product
            Element product = dom.createElement("product");
            product.setAttribute("description", "Jpos printer driver");
            product.setAttribute("name", "POSprinter");
            product.setAttribute("url", "https://nashindia.com/solutions/");
            entry.appendChild(product);

            // 5️⃣ Properties
            addProp(entry, "PID", "String", "00B7");
            addProp(entry, "PhysicalDevice", "String", "POSprinter");
            addProp(entry, "PortType", "String", "1");
            addProp(entry, "EnableLog", "String", "0");
            addProp(entry, "StatusCommandDelay", "String", "200");
            addProp(entry, "StatusThreadDelay", "String", "2000");
            addProp(entry, "OS", "String", "1");
            addProp(entry, "Busy_Timeout", "String", "10000");
            addProp(entry, "Delay512", "String", "100");
            addProp(entry, "Status", "String", "1");
            addProp(entry, "Write_Timeout", "String", "0");

            root.appendChild(entry);

            saveXml();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ------------------------------------------
    // Helper: Add <prop> entry
    // ------------------------------------------
    private void addProp(Element parent, String name, String type, String value) {
        Element prop = dom.createElement("prop");
        prop.setAttribute("name", name);
        prop.setAttribute("type", type);
        prop.setAttribute("value", value);
        parent.appendChild(prop);
    }

    // ------------------------------------------
    // Save XML back to file
    // ------------------------------------------
    private void saveXml() throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(dom), new StreamResult(xmlFile));
    }
}