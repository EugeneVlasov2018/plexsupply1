package ua.vlasov_eugene.plexsupply1.util;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class DataCreator {
	public Date getCurrentDate(){
		return new Date();
	}

	public String getUUID(){
		return UUID.randomUUID().toString();
	}
}
