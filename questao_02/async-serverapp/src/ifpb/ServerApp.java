package ifpb;

import ifpb.sd.share.Message;
import ifpb.sd.share.MessageResult;
import ifpb.sd.share.ServerServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class ServerApp extends ServerServiceGrpc.ServerServiceImplBase{

	private static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());

	@Override
	public void print(Message request, StreamObserver<MessageResult> responseObserver) {

		try {
			// Cria a mensagem, passando um hash da mensagem recuperada na requisiçao
			MessageResult result = MessageResult
					.newBuilder()
					.setId(request.getId())
					.setHash("hash: " + request.getText().getBytes().hashCode())
					.build();

			LOGGER.info("Identificador: " + request.getId() + ", Texto: " + request.getText());

			// Recebe as mensagens de um fluxo observavel de mensagens, a funçao onNext() recebe o valor do fluxo
			responseObserver.onNext(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Recebe a notificaçao de sucesso
		responseObserver.onCompleted();

	}


}
