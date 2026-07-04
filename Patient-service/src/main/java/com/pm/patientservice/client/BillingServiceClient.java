package com.pm.patientservice.client;

import com.pm.patientservice.billing.grpc.BillingRequest;
import com.pm.patientservice.billing.grpc.BillingResponse;
import com.pm.patientservice.billing.grpc.BillingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceClient {

    @GrpcClient("billing-service")
    private BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    public BillingResponse createBillingAccount(
            String patientId,
            String name,
            String email) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        return billingServiceBlockingStub.createBillingAccount(request);
    }
}
