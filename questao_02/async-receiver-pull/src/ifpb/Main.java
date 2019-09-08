package ifpb;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Iniciando sender receiver");

        // Cria o servidor na porta 2223, adicionando o serviço de comunicaçao (receiver)
        Server server = ServerBuilder
                .forPort(2223)
                .addService(new Receiver())
                .build();

        try {
            // Inicia o serviço de envio
            server.start();
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
