package io.ciera.runtime.summit.interfaces;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

public abstract class HttpPort<C extends IComponent<C>> extends Port<C> {

    public HttpPort(C context, IPort<?> peer) {
        super(context, peer);
    }

    @Override
    public void send(IMessage message) throws XtumlException {
    	if (satisfied()) {
            JSONObject msg = new JSONObject();
            msg.put("heartbeat", "false");
            msg.put("componentId", getPeerId());
            msg.put("portName", getPeerName());
            msg.put("message", new JSONObject(message.serialize()));
            HttpURLConnection connection = null;
            try {
                URL url = new URL(String.format("%s/message", getEndpoint()));
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("content-type", "application/json");
                connection.setRequestProperty("Content-Type", Integer.toString(msg.toString().length()));
                connection.setRequestProperty("InvocationType", "Event");
                try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                    out.write(msg.toString().getBytes());
                }
                int resp = connection.getResponseCode();
                LOG logger = new LOGImpl<C>(context());
                logger.LogInfo(Integer.toString(resp));
            } catch (IOException e) {
                if (connection != null) {
                    connection.disconnect();
                }
                LOG logger = new LOGImpl<C>(context());
                logger.LogFailure(e.toString());
                throw new XtumlException("Failed to send message");
            }
            if (connection != null) {
                connection.disconnect();
            }
    	}
    }
    
    public abstract String getEndpoint();

}
