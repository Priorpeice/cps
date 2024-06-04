package server.cps.verfication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import server.cps.verfication.dto.VerificationResponse;
import server.cps.verfication.dto.VerificationRequest;

@Service
@RequiredArgsConstructor
public class LlamaApiService {
    private final ApiService<VerificationResponse> apiService;

    public VerificationResponse callLLamaServer(VerificationRequest verificationRequest){
        return apiService.post("http://220.67.153.63:8000/api/verification", HttpHeaders.EMPTY,verificationRequest, VerificationResponse.class).getBody();
    }
}
