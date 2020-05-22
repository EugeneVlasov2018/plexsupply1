package ua.vlasov_eugene.plexsupply1.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.vlasov_eugene.plexsupply1.util.FilePathReader;
import ua.vlasov_eugene.plexsupply1.util.FileSender;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {
	private static final String SUCCESS_FILENAME = "successFile.txt";
	private static final String SUCCESS_PATH = "src/test/resources/fileDirectory/successFile.txt";
	private static final File SUCCESS_FILE = new File("src/test/resources/fileDirectory/successFile.txt");
	private static final String SUCCESS_RESULT = "SUCCESS read";
	private static final String SUCCESS_SAVE = "SUCCESS read & success save";
	@Mock
	private FileSender sender;
	@Mock
	private FilePathReader reader;
	@InjectMocks
	private FileService testedClass;

	@Test
	public void workWithFileFromPathExpectedSuccess() {
		List<String> expectedAnswer = Collections.singletonList(SUCCESS_SAVE);

		when(reader.readFile(SUCCESS_FILE,SUCCESS_FILENAME)).thenReturn(SUCCESS_RESULT);
		when(sender.sendFile(SUCCESS_FILENAME,SUCCESS_FILE,SUCCESS_RESULT)).thenReturn(SUCCESS_SAVE);

		assertEquals(expectedAnswer,
				testedClass.workWithFileFromPath(new HashSet<>(){{
					add(SUCCESS_PATH);
				}}));

		verify(reader).readFile(SUCCESS_FILE,SUCCESS_FILENAME);
		verify(sender).sendFile(SUCCESS_FILENAME,SUCCESS_FILE,SUCCESS_RESULT);

	}
}