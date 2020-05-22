package ua.vlasov_eugene.plexsupply1.util;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileSenderTest {
	private static final FastDateFormat MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM");
	private static final String SUCCESS_FILENAME = "successFile.txt";
	private static final File SUCCESS_FILE = new File("src/test/resources/fileDirectory/successFile.txt");
	private static final String SUCCESS_RESULT = "success read";
	@Value("${ftpHost}")
	private String hostAddress;
	@Value("${ftpLogin}")
	private String login;
	@Value("${ftpPassword}")
	private String password;
	@Mock
	private DataCreator creator;
	@Mock
	private FTPClient client;
	@InjectMocks
	private FileSender testedClass;

	@Test
	public void sendFileExpectedSuccess() throws IOException {
		Date currentDate = new Date();
		String UUID = java.util.UUID.randomUUID().toString();
		String expectedMessage = "success read\n" +
				" File successFile.txt was upload successfully! " +
				"The link on file is: file/" +
				UUID + "/" +
				MONTH_FORMAT.format(currentDate) + "/successFile.txt";


		when(creator.getCurrentDate()).thenReturn(currentDate);
		when(creator.getUUID()).thenReturn(UUID);

		assertEquals(expectedMessage,testedClass.sendFile(SUCCESS_FILENAME,SUCCESS_FILE,SUCCESS_RESULT));

		verify(client).connect(hostAddress);
		verify(client).enterLocalPassiveMode();
		verify(client).login(login,password);
		verify(client).storeFile(anyString(),any());
		verify(client).logout();
		verify(client).disconnect();
	}
}