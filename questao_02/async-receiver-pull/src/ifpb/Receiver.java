package ifpb;

import com.google.common.util.concurrent.ListenableFuture;
import ifpb.sd.share.Message;
import ifpb.sd.share.MessageResult;
import ifpb.sd.share.ReceiverServiceGrpc;
import ifpb.sd.share.ServerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Receiver extends ReceiverServiceGrpc.ReceiverServiceImplBase {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final Executor executor = Executors.newFixedThreadPool(10);

    @Override
    public void delivery(Message request, StreamObserver<MessageResult> responseObserver) {

        LOGGER.info("Recebendo nova mensagem e tentando enviar para o server");

        // Cria canal de comunicaçao com o ServerApp
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("serverapp", 2224)
                .usePlaintext()
                .build();

        // Cria um novo stub para o receiver, o qual vai suportar chamadas de saida e entrada no serviço
        ServerServiceGrpc.ServerServiceFutureStub stub = ServerServiceGrpc.newFutureStub(channel);

        ListenableFuture<MessageResult> delivery = stub.print(request);

        delivery.addListener(() -> {

            try {
                MessageResult messageResult = delivery.get();

                // Recebe as mensagens de um fluxo observavel de mensagens, a funçao onNext() recebe o valor do fluxo
                responseObserver.onNext(messageResult);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Recebe a notificaçao de sucesso
            responseObserver.onCompleted();
        }, executor);

    }

}
