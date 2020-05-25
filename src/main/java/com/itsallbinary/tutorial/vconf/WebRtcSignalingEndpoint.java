package com.itsallbinary.tutorial.vconf;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/signal")
public class WebRtcSignalingEndpoint {

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void whenOpening(Session session) throws IOException, EncodeException {
		System.out.println("Open!");
		sessions.add(session);
	}

	@OnMessage
	public void process(String data, Session session) throws IOException {
		System.out.println("Got signal - " + data);
		for (Session sess : sessions) {
			if (!sess.equals(session)) {
				sess.getBasicRemote().sendText(data);
			}
		}
	}

	@OnClose
	public void whenClosing(Session session) {
		System.out.println("Close!");
		sessions.remove(session);
	}

}
