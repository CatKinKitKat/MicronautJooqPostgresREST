package com.optiply.endpoint.controllers;

import com.optiply.endpoint.controllers.shared.GrpcBaseController;
import com.optiply.endpoint.protobuf.WebshopProtos;
import io.grpc.stub.StreamObserver;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import lombok.extern.java.Log;

/**
 * The type Grpc controller.
 */
@Log
@Validated
@Controller("/")
public class GrpcController extends GrpcBaseController {

	@Override
	public void addEmail(WebshopProtos.EmailRequest request, StreamObserver<WebshopProtos.TextResponse> responseObserver) {

		log.info("Received request to add email");

		WebshopProtos.TextResponse response = WebshopProtos.TextResponse.newBuilder()
				.setText("Email added successfully")
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}


}


