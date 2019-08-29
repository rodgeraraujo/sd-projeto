package ifpb;

import ifpb.sd.share.Message;
import ifpb.sd.share.MessageResult;
import ifpb.sd.share.ServerServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class ServerApp extends ServerServiceGrpc.ServerServiceImplBase{

	private static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());

	@Override
	public void print(Message request, StreamObserver<MessageResult> responseObserver) {

		MessageDigest msd;

		try {
			msd = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.info("MD5 error:");
			throw new RuntimeException(e);
		}

		byte[] bhash = msd.digest(request.getText().getBytes());
		BigInteger bi = new BigInteger(bhash);

		try {
			MessageResult result = MessageResult
					.newBuilder()
					.setId(request.getId())
					.setHash(bi.toString(16))
					.build();

			LOGGER.info("Identificador: " + request.getId() + ", Texto: " + request.getText());

			responseObserver.onNext(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		responseObserver.onCompleted();

	}


}
