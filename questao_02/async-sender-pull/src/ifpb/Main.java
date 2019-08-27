package ifpb;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Iniciando servico sender");

        LOGGER.info("Iniciando o servico MessageSender para o ClientApp");

        Server server = ServerBuilder
                .forPort(10990)
                .addService((BindableService) new MessageSender())
                .build();

        try {
            server.start();
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
