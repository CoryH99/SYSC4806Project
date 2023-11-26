package sysc4806.project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import sysc4806.project.demo.storage.StorageService;

@SpringBootApplication
public class DemoApplication {

	/*
	Profs can enter 4th year project topics, delete ones they no longer offer, or archive them for later use.
	For a given 4th year project topic, they can enter a description, program restrictions, and the number of students required.
	Students can look up projects and apply for one that they like. When a project is full, no more students can apply for it.
	The 4th year project coordinator can search for students without a project and send them a reminder.
	For the oral presentations, profs and students enter their availability.
	The 4th year project coordinator can allocate the oral presentations to rooms based on availability.
	This can be done manually or using a very simple “best effort” algorithm.
	For the final project, students submit their reports online by a deadline specified by the 4th year project coordinator and enforced by the system.
	 */

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
