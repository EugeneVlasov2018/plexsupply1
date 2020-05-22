package ua.vlasov_eugene.plexsupply1.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.vlasov_eugene.plexsupply1.service.FileService;

import java.util.HashSet;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/file")
@RequiredArgsConstructor
public class FileController {
	private final FileService fileService;

	@ApiOperation("The command to get the file path." +
			" If there is no file, an error400 will be thrown")
	@GetMapping
	private List<String> workWithFileFromPath(@RequestParam List<String> filepaths){
		return fileService.workWithFileFromPath(new HashSet<>(filepaths));
	}
}
