package com.pm.billingservice.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    @Override
    public void createBillingAccount(
            BillingRequest request,
            StreamObserver<BillingResponse> responseObserver) {

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("ACC-" + request.getPatientId())
                .setStatus("CREATED")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}