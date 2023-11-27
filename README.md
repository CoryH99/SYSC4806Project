# Sysc4806Project
Authors: Justin Yeh, Jackie Zhuang, Giancarlo Salvador, Cory Helm
Group: 10
Date: 11/12/2023
Version: 1.0
=====================================================================
# Description
This software package is a 4th year project selection website
Profs can enter 4th year project topics, delete ones they no longer offer, or archive them for later use.
For a given 4th year project topic, they can enter a description, program restrictions, and the number of students required.
Students can look up projects and apply for one that they like. When a project is full, no more students can apply for it.
The 4th year project coordinator can search for students without a project and send them a reminder.
For the oral presentations, profs and students enter their availability.
The 4th year project coordinator can allocate the oral presentations to rooms based on availability.
This can be done manually or using a very simple “best effort” algorithm.
For the final project, students submit their reports online by a deadline specified by the 4th year project coordinator and enforced by the system.
======================================================================
# Usage
Clone the repo at https://github.com/acegiansal/Sysc4806Project
run the pom.xml file 
a JAR file should be generated that will start the website
======================================================================
# Changelog
Version 1.0
- Added basic classes with CRUD functionality for professor, student, and project
- Added basic controllers for the professor, student, and project classes
- Added basic UI
- Added CI functionality
- Added basic tests
- Basic Azure deployment
Version 2.0
- Added tests for version 1.0 controllers
- Added functionality to login, create student and professor accounts
- Added ability for student to sign up for a project
- Added ability for a professor to archive/delete/unarchive projects
- Cleaned up UI
- Added functionality to submit a report for a project
======================================================================
# Next Steps
- Add circuit breaker pattern across all critical points
- Add oral presentation functionality to project coordinator class
- Finish all features
- Finalize UI

Er Diagram for Milestone 2:
![ER_DiagramM2](https://github.com/acegiansal/Sysc4806Project/assets/91341372/487adb8d-5345-49c0-9815-48ce438ea1cb)



UML class diagram for the models in milestone 2:
![UMLDiagramM2](https://github.com/acegiansal/Sysc4806Project/assets/91341372/ab6e0713-dc03-48e8-b5b1-af8dc5a86e8a)


