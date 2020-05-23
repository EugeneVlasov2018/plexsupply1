package ua.vlasov_eugene.plexsupply1.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.vlasov_eugene.plexsupply1.exceptions.FTPWriteException;
import ua.vlasov_eugene.plexsupply1.util.FilePathReader;
import ua.vlasov_eugene.plexsupply1.util.FileSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {
	private static final String FILENAME = "successFile.txt";
	private static final String PATH = "src/test/resources/fileDirectory/successFile.txt";
	private static final File FILE = new File("src/test/resources/fileDirectory/successFile.txt");
	private static final String SUCCESS_READ = "SUCCESS read";
	private static final String EMPTY_READ = "EMPTY_READ";
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

		when(reader.readFile(FILE, FILENAME)).thenReturn(SUCCESS_READ);
		when(sender.sendFile(FILENAME, FILE, SUCCESS_READ)).thenReturn(SUCCESS_SAVE);

		assertEquals(expectedAnswer,
				testedClass.workWithFileFromPath(new HashSet<>(){{
					add(PATH);
				}}));

		verify(reader).readFile(FILE, FILENAME);
		verify(sender).sendFile(FILENAME, FILE, SUCCESS_READ);
	}

	@Test
	public void workWithFileFromPathExpectedSuccessInReadWithoutSave(){
		List<String> expectedAnswer = Collections.singletonList(EMPTY_READ);
		when(reader.readFile(FILE, FILENAME)).thenReturn(EMPTY_READ);

		assertEquals(expectedAnswer,
				testedClass.workWithFileFromPath(new HashSet<>(){{
					add(PATH);
				}}));

		verify(reader).readFile(FILE, FILENAME);
		verify(sender,times(0)).sendFile(anyString(), any(), anyString());
	}

	@Test(expected = FTPWriteException.class)
	public void workWithFileFromPathExpectedExceptionFromReader(){
		when(reader.readFile(FILE, FILENAME)).thenReturn(SUCCESS_READ);
		when(sender.sendFile(FILENAME, FILE, SUCCESS_READ)).thenThrow(new FTPWriteException("some message"));

		testedClass.workWithFileFromPath(new HashSet<>(){{
			add(PATH);
		}});

		verify(reader).readFile(FILE, FILENAME);
		verify(sender).sendFile(FILENAME, FILE, SUCCESS_READ);
	}

	@Test
	public void workWithFileFromPathExpectedEmptyAnswer(){
		assertEquals(Collections.emptyList(), testedClass.workWithFileFromPath(new HashSet<>(){{
					add("");
				}}));
	}
}