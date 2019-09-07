package ifpb;

import com.google.common.util.concurrent.ListenableFuture;
import ifpb.sd.share.Message;
import ifpb.sd.share.MessageResult;
import ifpb.sd.share.SenderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Executor executor = Executors.newFixedThreadPool(10);
    private static Integer threads = 0;

    private static void sendAndResultMessage(String id, String text, SenderServiceGrpc.SenderServiceFutureStub stub) {

        ListenableFuture<MessageResult> futureResonse = stub.sendMessage(
                Message.newBuilder()
                        .setId(id)
                        .setText(text)
                        .build()
        );

        futureResonse.addListener(() -> {
            try {
                MessageResult messageResult = futureResonse.get();

                LOGGER.info("Resultado da mensagem com identificador: " + id + " e resultado: " + messageResult.getHash());
                threads--;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);

    }

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Iniciando o o client");

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();

        SenderServiceGrpc.SenderServiceFutureStub stub = SenderServiceGrpc.newFutureStub(channel);

        String id = "id";
        String text = "Hello World!";

        for (int i = 0; i < 100; i++) {

            final String idx = id + "#" + i;
            final String messagex = text + "#" + i;

            LOGGER.info("Enviando messagem " + idx + " para sender pull");

            Thread thread = new Thread(() -> sendAndResultMessage(idx, messagex, stub));

            threads++;
            thread.start();
        }

        while (threads != 0) {
            Thread.sleep(2000);
            LOGGER.info("Aguardando processar todas as mensagens - restantes " + threads);
        }

        LOGGER.info("Finalizando processamento do Client");
    }

}
