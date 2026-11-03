package com.CrmUserServiceWave.Util;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class OTPGenerateService {
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateOTP() {
        int otp = secureRandom.nextInt(1_000_000);
        return String.format("%06d", otp);
    }
}
