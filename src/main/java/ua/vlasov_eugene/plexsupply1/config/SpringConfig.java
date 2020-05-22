package ua.vlasov_eugene.plexsupply1.config;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
	@Bean
	public FTPClient ftpClient(){
		return new FTPClient();
	}
}
