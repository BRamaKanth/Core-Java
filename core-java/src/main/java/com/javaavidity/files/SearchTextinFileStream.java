package com.javaavidity.files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 
 * @author ramakanth.b
 *
 */
public class SearchTextinFileStream {

	public static void main(String[] args) throws IOException {

		// Search in a Single file
		Path path = FileSystems.getDefault().getPath("src/main/resources", "SearchFile1");

		Search search = new Search();
		search.searchFile(path);

		// Search in multiple files
		DirectoryStream<Path> dirPath = Files.newDirectoryStream(Paths.get("src/main/resources"));

		StreamSupport.stream(dirPath.spliterator(), true).filter(search::searchFile).findFirst();
	}

}

class Search {
	private static final String searchTerm = "framework";

	boolean searchFile(Path path) {

		try (Stream<String> lines = Files.lines(path).parallel()) {

			Optional<String> line = lines.filter(s -> s.contains(searchTerm)).findFirst();
			if (line.isPresent()) {
				System.out.println("Found ");
				return true;
			} else {
				System.out.println("Not found");
				return false;
			}

		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}
	}
}