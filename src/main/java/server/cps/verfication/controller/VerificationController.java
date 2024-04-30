package server.cps.verfication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
import server.cps.common.Status;
import server.cps.verfication.dto.VerificationRequest;
import server.cps.verfication.dto.VerficationResponse;
import server.cps.verfication.service.LlamaApiService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class VerificationController {
    private final LlamaApiService llamaApiService;

    @PostMapping("/api/verification")
    public ResponseEntity<ResoponseBody<VerficationResponse>> verification(@RequestBody VerificationRequest verificationRequest){
        VerficationResponse verficationResponse = llamaApiService.callLLamaServer(verificationRequest);
        return CpsResponse.toResponse(Status.LLAMA,verficationResponse);
    }
}
