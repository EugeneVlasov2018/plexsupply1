package ua.vlasov_eugene.plexsupply1.util;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import ua.vlasov_eugene.plexsupply1.exceptions.FTPWriteException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileSenderTest {
	private static Date date;
	private static String uuid;
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

	@BeforeClass
	public static void init(){
		date = new Date();
		uuid = java.util.UUID.randomUUID().toString();
	}

	@Test
	public void sendFileExpectedSuccess() throws IOException {
		String expectedMessage = "success read\n" +
				" File successFile.txt was upload successfully! " +
				"The link on file is: file/" +
				uuid + "/" +
				MONTH_FORMAT.format(date) + "/successFile.txt";


		when(creator.getCurrentDate()).thenReturn(date);
		when(creator.getUUID()).thenReturn(uuid);

		assertEquals(expectedMessage,testedClass.sendFile(SUCCESS_FILENAME,SUCCESS_FILE,SUCCESS_RESULT));

		verify(client).connect(hostAddress);
		verify(client).enterLocalPassiveMode();
		verify(client).login(login,password);
		verify(client).storeFile(anyString(),any());
		verify(client).logout();
		verify(client).disconnect();
	}

	@Test(expected = FTPWriteException.class)
	public void sendFileExpectdException() throws IOException {
		when(creator.getCurrentDate()).thenReturn(date);
		when(creator.getUUID()).thenReturn(uuid);
		when(client.storeFile(anyString(),any())).thenThrow(new IOException("some message"));

		testedClass.sendFile(SUCCESS_FILENAME,SUCCESS_FILE,SUCCESS_RESULT);

		verify(client).connect(hostAddress);
		verify(client).enterLocalPassiveMode();
		verify(client).login(login,password);
		verify(client).storeFile(anyString(),any());
		verify(client,times(0)).logout();
		verify(client,times(0)).disconnect();
	}
}