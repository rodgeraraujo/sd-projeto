package ifpb;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {

		LOGGER.info("Servidor inicializado");


		LOGGER.info("Iniciando sender receiver");

		// Cria o servido na porta 2224, adicionanod o serviço
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
