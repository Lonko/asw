package asw.socket.server.connector;

import asw.socket.server.CounterServant;

import java.net.*;
import java.io.*;

import java.util.logging.Logger;

/* remote proxy lato server per il servizio */
public class CounterServiceServerTCPProxy {

	/* logger */
	private Logger logger = Logger.getLogger("asw.socket.server.connector");

    private int port;                           // porta per il servizio

	public CounterServiceServerTCPProxy(int port) {
        this.port = port;
    }

    public void run() {
        try {
            /* crea il server socket su cui ascoltare/ricevere richieste */
        	ServerSocket listenSocket = new ServerSocket(port);
            /* per il server, disabilita il timeout */
        	listenSocket.setSoTimeout(0);
            while (true) {
                /* aspetta/accetta una richiesta, crea il relativo client socket */
				Socket clientSocket = listenSocket.accept();    // bloccante
				/* la richiesta sara' gestita in un nuovo thread, separato */
				ServantThread thread = new ServantThread(clientSocket, new CounterServant());
            }
		} catch (IOException e) {
			logger.info("Server Proxy: IO Exception: " + e.getMessage());
		}
    }

}

