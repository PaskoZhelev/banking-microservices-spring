package org.mnp.accountservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
@ToString
public class AccountsConfiguration {

    private String msg;
    private String buildVersion;
    private Map<String, String> mailDetails;
    private List<String> activeBranches;

}
