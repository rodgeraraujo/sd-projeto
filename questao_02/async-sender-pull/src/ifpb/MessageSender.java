package ifpb;

import com.google.common.util.concurrent.ListenableFuture;
import ifpb.sd.share.Message;
import ifpb.sd.share.MessageResult;
import ifpb.sd.share.ReceiverServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MessageSender extends ifpb.sd.share.SenderServiceGrpc.SenderServiceImplBase {

    private final Executor executor = Executors.newFixedThreadPool(10);
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void sendMessage(Message request, StreamObserver<MessageResult> responseObserver) {

        String name = "serverapp";

        LOGGER.info("Criando canal de comunicaçao com MessageReceiver no ServerApp");

        // Cria o canal de comunicaçao entre o servidor e o serviço de envio de mensagens
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(name, 2223)
                .usePlaintext()
                .build();

        // Cria um novo stub para o receiver, o qual vai suportar chamadas de saida e entrada no serviço
        ReceiverServiceGrpc.ReceiverServiceFutureStub stub = ReceiverServiceGrpc.newFutureStub(channel);

        ListenableFuture<MessageResult> delivery = stub.delivery(request);

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
