package mx.tec.mining;

import org.deckfour.xes.model.*;
import org.deckfour.xes.model.impl.XLogImpl;
import org.deckfour.xes.out.XSerializer;
import org.deckfour.xes.out.XesXmlSerializer;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        XLog log = XLogReader.openLog("simulation_logs.mxml.gz");
        for (XTrace trace: log) {
            Iterator<XEvent> it = trace.iterator();
            Map<String, XAttribute> map = new HashMap<>();
            while (it.hasNext()) {
                XEvent event = it.next();
                XAttributeMap attributes = event.getAttributes();
                String label = attributes.get("concept:name").toString();
                if(attributes.containsKey("org:resource")) {
                    map.put(label, attributes.get("org:resource"));
                }
                if (map.containsKey(label)) {
                    attributes.put("org:resource", map.get(label));
                }
                else{
                    it.remove();
                }
            }
        }
        XSerializer serializer = new XesXmlSerializer();
        serializer.serialize(log, new FileOutputStream("simulation_logs.xes"));
        // Add namespace xmlns="http://www.xes-standard.org" if there is no namespace
        System.out.println("Process Finished");
    }
}
