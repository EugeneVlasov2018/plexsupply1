package ua.vlasov_eugene.plexsupply1.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.vlasov_eugene.plexsupply1.exceptions.FTPWriteException;

import java.io.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSender {
	private final String ERROR = "Error working with file %s";
	private static final FastDateFormat MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM");
	@Value("${ftpHost}")
	private String hostAddress;
	@Value("${ftpLogin}")
	private String login;
	@Value("${ftpPassword}")
	private String password;
	private final FTPClient client;
	private final DataCreator creator;

	public String sendFile(String filename, File currentFile, String resultOfLastOperation){
		StringBuilder resultBuilder = new StringBuilder(resultOfLastOperation);
		String url = prepareUrl(filename);

		try(InputStream stream = new DataInputStream(new FileInputStream(currentFile))){
			client.connect(hostAddress);
			client.enterLocalPassiveMode();
			client.login(login,password);
			client.storeFile(url,stream);
			client.logout();
			client.disconnect();
			resultBuilder.append(String.format("\n File %s was upload successfully! The link on file is: %s",
					filename,url));
		} catch (IOException e) {
			log.error(String.format(ERROR,filename),e);
			//todo вообще то нет смысла отправлять именно такой месседж клиенту,
			// лучше сказать что-то типа "зайдите попозже", но это на случай если при ревью не пропишите проперти)
			throw new FTPWriteException("Some goes wrong in FTPClient-lay "+e.getMessage());
		}

		return resultBuilder.toString();
	}

	 // Parameter generation is included in a separate class for ease of testing
	private String prepareUrl(String filename) {
		return String.format("file/%s/%s/%s",
				creator.getUUID(),
				MONTH_FORMAT.format(creator.getCurrentDate()),
				filename);
	}
}
