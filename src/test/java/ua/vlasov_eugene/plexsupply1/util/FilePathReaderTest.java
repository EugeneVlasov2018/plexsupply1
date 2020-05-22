package ua.vlasov_eugene.plexsupply1.util;

import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FilePathReaderTest {
	private static final String FILE_NAME = "testedFile.txt";
	private static final File SUCCESS_FILE = new File("src/test/resources/fileDirectory/successFile.txt");
	private static final File EMPTY_FILE = new File("src/test/resources/fileDirectory/emptyFile.txt");
	private static final File NULLABLE_FILE = new File("src/test/resources/fileDirectory/");

	private FilePathReader testedClass = new FilePathReader();

	@Test
	public void readFileExpectedSuccess() {
		String expectedResult = prepareResult().get("Success");
		assertEquals(expectedResult,testedClass.readFile(SUCCESS_FILE,FILE_NAME));
	}

	@Test
	public void readFileExpectedFailBecauseFileIsEmpty(){
		String expectedResult = prepareResult().get("Empty file");
		assertEquals(expectedResult,testedClass.readFile(EMPTY_FILE,FILE_NAME));
	}

	@Test
	public void readFileExpectedFailBecauseFilePathIsWrong(){
		String expectedResult = prepareResult().get("Nullable file");
		assertEquals(expectedResult,testedClass.readFile(NULLABLE_FILE,FILE_NAME));
	}

	private Map<String,String> prepareResult() {
		return new HashMap<>(){{
			put("Success", "SUCCESS)) File testedFile.txt was readed successfully \n" +
					" Result of read: success)) (づ｡◕‿‿◕｡)づ");
			put("Empty file", "ERROR! File emptyFile.txt is empty");
			put("Nullable file", "ERROR! File on path src\\test\\resources\\fileDirectory was not found");
		}};
	}
}