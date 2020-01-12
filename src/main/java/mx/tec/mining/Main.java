package mx.tec.mining;

import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.deckfour.xes.model.impl.XLogImpl;
import org.deckfour.xes.out.XSerializer;
import org.deckfour.xes.out.XesXmlSerializer;

import java.io.FileOutputStream;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {

        XLog log = XLogReader.openLog("simulation_logs.mxml.gz");
        for (XTrace trace: log) {
            Iterator<XEvent> it = trace.iterator();
            while (it.hasNext()) {
                XEvent event = it.next();
                XAttributeMap attributes = event.getAttributes();
                if(attributes.containsKey("org:resource")){
                    // System.out.println(attributes);
                }else{
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
