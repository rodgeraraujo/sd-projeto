package ifpb;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {

		System.out.println("Servidor inicializado");


		LOGGER.info("Iniciando sender receiver");

		Server server = ServerBuilder
				.forPort(2224)
				.addService(new ServerApp())
				.build();

		try {
			server.start();
			server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
