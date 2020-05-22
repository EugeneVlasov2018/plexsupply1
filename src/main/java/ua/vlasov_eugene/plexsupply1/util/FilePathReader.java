package ua.vlasov_eugene.plexsupply1.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Slf4j
@Component
public class FilePathReader {
	private final String FILE_NOT_FOUND = "ERROR! File on path %s was not found";
	private final String SUCCESS = "SUCCESS)) File %s was readed successfully \n" +
			" Result of read: %s";
	private final String FILE_IS_EMPTY = "ERROR! File %s is empty";

	public String readFile(File currentFile, String fileName) {
		String result;
		try (Scanner scanner = new Scanner(currentFile)){
			if(scanner.hasNext()) {
				String firstLineOfFile = scanner.nextLine();
				//todo логгирую вывод в консоль только тут, потому что не было задачи выводить строку из пустого файла
				log.info(firstLineOfFile);
				result = String.format(SUCCESS, fileName, firstLineOfFile);
			} else {
				result = String.format(FILE_IS_EMPTY, currentFile.getName());
			}

		} catch (FileNotFoundException e) {

			result = String.format(FILE_NOT_FOUND,
					e.getMessage().substring(0,e.getMessage().indexOf(" ")));
			//todo холиварный момент, - по хорошему в логи исключение добавлять не стоит, т.к. тут это часть логики
			// я ожидаю, что по вине пользователя такая кака тоже может быть.
			// Так что в лог с ERROR-уровнем лучше пущу просто сообщение
			//log.error(result,e);
			log.error(result);
		}

		return result;
	}
}
