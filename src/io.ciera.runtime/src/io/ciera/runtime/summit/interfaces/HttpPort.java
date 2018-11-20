package io.ciera.runtime.summit.interfaces;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class HttpPort<C extends IComponent<C>> extends Port<C> {

    public HttpPort(C context, IPort<?> peer) {
        super(context, peer);
    }

    @Override
    public void send(IMessage message) throws XtumlException {
    	if (satisfied()) {
            JSONObject msg = new JSONObject();
            msg.put("componentId", context().getId());
            msg.put("portName", getPeerName());
            msg.put("message", new JSONObject(message.serialize()));
            HttpURLConnection connection = null;
            try {
                URL url = new URL(String.format("http://%s:%d/message?data=%s", getHost(), getPortNum(), URLEncoder.encode(msg.toString(), "UTF-8")));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int resp = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK != resp) {
                    throw new XtumlException("Invalid response code: " + resp);
                }
            } catch (IOException e) {
                if (connection != null) {
                    connection.disconnect();
                }
                throw new XtumlException("Failed to send message");
            }
            if (connection != null) {
                connection.disconnect();
            }
    	}
    }
    
    public abstract String getHost();
    public abstract int getPortNum();

}
