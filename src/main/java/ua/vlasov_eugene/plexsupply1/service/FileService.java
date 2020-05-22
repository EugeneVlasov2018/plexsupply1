package ua.vlasov_eugene.plexsupply1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.vlasov_eugene.plexsupply1.util.FilePathReader;
import ua.vlasov_eugene.plexsupply1.util.FileSender;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
	private final FilePathReader reader;
	private final FileSender sender;

	public List<String> workWithFileFromPath(Set<String> filepath) {
		List<File> files = filepath.stream()
				.filter(x->!x.isEmpty())
				.map(File::new)
				.collect(Collectors.toList());

		return files.parallelStream()
				.map(file -> {
					String resultOfRead;
					String filename = file.getName();
					resultOfRead = reader.readFile(file, filename);

					if(resultOfRead.startsWith("SUCCESS")) {
						resultOfRead = sender.sendFile(filename,file, resultOfRead);
					}
					return resultOfRead;
				})
				.collect(Collectors.toList());
	}
}
