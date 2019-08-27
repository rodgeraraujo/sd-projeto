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
    private static Integer countThread = 0;

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
                countThread--;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, executor);

    }

    public static void main(String[] args) throws InterruptedException {

        String name = "clientapp";

        LOGGER.info("Iniciando o o client: " + name);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(name, 10990)
                .usePlaintext()
                .build();

        ifpb.sd.share.SenderServiceGrpc.SenderServiceFutureStub stub = ifpb.sd.share.SenderServiceGrpc.newFutureStub(channel);

        String id = "askjdlkasjd";
        String text = "Hello World!";

        for (int i = 0; i < 100; i++) {

            final String ix = id + "#" + i;
            final String mx = text + "#" + i;

            Thread t = new Thread(() -> sendAndResultMessage(ix, mx, stub));
            countThread++;
            t.start();
        }

        while (countThread != 0) {
            Thread.sleep(2000);
            LOGGER.info("Aguardando processar todas as mensagens (Restantes =  " + countThread + ")");
        }
        LOGGER.info("Finalizando processamento do Client");
    }

}
