package mypackage;

import models.Assignment;
import models.Student;
import models.Trainer;
import models.Course;
import models.Trainer_per_course;
import models.Student_per_course;
import models.Assignment_per_course;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database();

        System.out.println("*******************SELECT * FROM courses**********************************");
        ResultSet rs = db.getResults("SELECT * FROM courses");
        printCoursesResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************SELECT * FROM trainers**********************************");
        rs = db.getResults("SELECT * FROM trainers");
        printTrainersResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************SELECT * FROM students**********************************");
        rs = db.getResults("SELECT * FROM students");
        printStudentsResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************SELECT * FROM assignments**********************************");
        rs = db.getResults("SELECT * FROM assignments");
        printAssignmentsResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************All the students per course**********************************");
        rs = db.getResults("SELECT `courses`.`stream`, `courses`.`type`, `students`.`first_name`, `students`.`last_name` FROM `courses` JOIN `students_per_course` ON  `courses`.`id`=`students_per_course`.`courses_id` JOIN `students` ON  `students`.`id`=`students_per_course`.`students_id`;");
        printStudentsPerCourseResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************All the trainers per course**********************************");
        rs = db.getResults("SELECT `courses`.`stream`, `courses`.`type`, `trainers`.`first_name`, `trainers`.`last_name` FROM `courses` JOIN `trainers_per_course` ON  `courses`.`id`=`trainers_per_course`.`courses_id` JOIN `trainers` ON  `trainers`.`id`=`trainers_per_course`.`trainers_id`;");
        printTrainersPerCourseResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************All the assignments per course**********************************");
        rs = db.getResults("SELECT `courses`.`stream`, `courses`.`type`, `assignments`.`title`, `assignments`.`description` FROM `courses` JOIN `assignments_per_course` ON  `courses`.`id`=`assignments_per_course`.`courses_id` JOIN `assignments` ON  `assignments`.`id`=`assignments_per_course`.`assignments_id`;");
        printAssignmentsPerCourseResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************All the assignments per course per student**********************************");
        rs = db.getResults("SELECT `courses`.`stream`, `courses`.`type`, `students`.`first_name`, `students`.`last_name`, `assignments`.`title`, `assignments`.`description`  FROM `assignments` JOIN `assignments_per_course` ON  `assignments`.`id`=`assignments_per_course`.`assignments_id` JOIN `courses` ON  `courses`.`id`=`assignments_per_course`.`courses_id` JOIN `students_per_course` ON `courses`.`id`=`students_per_course`.`courses_id` JOIN `students` ON `students_per_course`.`students_id`=`students`.`id`;");
        printAssignmentsPerStudentPerCourseResults(rs);

        System.out.println("**********************************************************");
        System.out.println("*******************A list of students that belong to more than one courses**********************************");
        rs = db.getResults("SELECT `students`.`first_name`, `students`.`last_name` FROM `courses` JOIN `students_per_course` ON  `courses`.`id`=`students_per_course`.`courses_id` JOIN `students` ON  `students`.`id`=`students_per_course`.`students_id` GROUP BY `students`.`last_name` HAVING COUNT(`students`.`first_name` AND `students`.`last_name`)>1;");
        printStudentsBelongMoreThanOneCourseResults(rs);
        System.out.println("");

        System.out.println("**********************************************");

        System.out.println(" What would you like to insert?(Type Course/Student/Trainer/Assignment/Course and Assignment)");
        System.out.println("(You have the choice to insert more than one elements if you type for example Courses and Assignments or Courses and assignments and students e.t.c.)");
        String whatToInsert = scanner.nextLine();
        whatToInsert = whatToInsert.toLowerCase();
        while (!whatToInsert.contains("course") && !whatToInsert.contains("student") && !whatToInsert.contains("trainer") && !whatToInsert.contains("assignment")) {
            System.out.println("Invalid word, please try again");
            whatToInsert = scanner.nextLine();
        }

        if (whatToInsert.indexOf("course") != -1) {
            int numOfCourses;
            do {
                System.out.println("How many courses would you like to insert?(if you type 0,the program should use synthetic data)");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfCourses = scanner.nextInt();
            } while (numOfCourses < 0);
            System.out.printf("You have entered the valid number %d.\n", numOfCourses);
            if (numOfCourses != 0) {
                for (int i = 0; i < numOfCourses; i++) {
                    System.out.println("Please insert title of course eg CB1 (until 5 letters)");
                    String titleOfCourse = scanner.next();
                    System.out.println("Please insert stream of course");
                    String streamOfCourse = scanner.next();
                    System.out.println("Please insert type of course");
                    scanner.nextLine();
                    String typeOfCourse = scanner.nextLine();
                    String startingDateOfCourse = "b";
                    int day;
                    do {
                        System.out.print("Please insert day of the starting date of the course (e.g 15): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        day = scanner.nextInt();
                    } while (day < 01 || day > 31);

                    int month;
                    do {
                        System.out.print("Please insert month of the starting date of the course (e.g 04): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        month = scanner.nextInt();
                    } while (month < 01 || month > 13);
                    int year;
                    do {
                        System.out.print("Please insert year of the starting date of the course (e.g 2020): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        year = scanner.nextInt();
                    } while (year < 2020);
                    if (day > 9 && month > 9) {
                        startingDateOfCourse = String.format("%d-%d-%d", year, month, day);
                    } else if (day < 10 && month > 9) {
                        startingDateOfCourse = String.format("%d-%d-0%d", year, month, day);
                    } else if (day < 10 && month < 10) {
                        startingDateOfCourse = String.format("%d-0%d-0%d", year, month, day);
                    } else if (day > 9 && month < 10) {
                        startingDateOfCourse = String.format("%d-0%d-%d", year, month, day);
                    }

                    Course course = new Course(titleOfCourse, streamOfCourse, typeOfCourse, startingDateOfCourse);
                    System.out.println("Records inserted: " + insertRecordToCourses(course, db));
                    rs = db.getResults("SELECT * FROM Courses");
                    printCoursesResults(rs);
                }
            }
        }

        if (whatToInsert.indexOf("trainer") != -1) {
            int numOfTrainers;
            do {
                System.out.println("How many trainers would you like to insert?(if you type 0,the program should use synthetic data)");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfTrainers = scanner.nextInt();
            } while (numOfTrainers < 0);
            System.out.printf("You have entered the valid number %d.\n", numOfTrainers);
            if (numOfTrainers != 0) {
                ///trancate
                for (int i = 0; i < numOfTrainers; i++) {
                    System.out.println("Please insert the first name of trainer");
                    String firstNameOfTrainer = scanner.next();
                    System.out.println("Please insert the last name of trainer");
                    String lastNameOfTrainer = scanner.next();
                    System.out.println("Please insert the subject of trainer");
                    String subjectOfTrainer = scanner.next();
                    Trainer trainer = new Trainer(firstNameOfTrainer, lastNameOfTrainer, subjectOfTrainer);
                    System.out.println("Records inserted: " + insertRecordToTrainers(trainer, db));
                    rs = db.getResults("SELECT * FROM Trainers");
                    printTrainersResults(rs);
                }
            }
        }

        if (whatToInsert.indexOf("assignment") != -1) {
            int numOfAssignments;
            do {
                System.out.println("How many assignments would you like to insert?(if you type 0,the program should use synthetic data)");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfAssignments = scanner.nextInt();
            } while (numOfAssignments < 0);
            System.out.printf("You have entered the valid number %d.\n", numOfAssignments);
            if (numOfAssignments != 0) {
                ///trancate
                for (int i = 0; i < numOfAssignments; i++) {
                    System.out.println("Please insert the title of assignment");
                    scanner.nextLine();
                    String titleOfAssignment = scanner.nextLine();
                    System.out.println("Please insert the description of assignment");
                    String descriptionOfAssignment = scanner.nextLine();
                    int day;
                    do {
                        System.out.print("Please insert day of the submission date of the assignment (e.g 15): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        day = scanner.nextInt();
                    } while (day < 01 || day > 31);

                    int month;
                    String subDateOfAssignment = "b";
                    do {
                        System.out.print("Please insert month of the submission date of the assignment (e.g 04): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        month = scanner.nextInt();
                    } while (month < 01 || month > 13);
                    int year;
                    do {
                        System.out.print("Please insert year of the submission date of the assignment (e.g 2020): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        year = scanner.nextInt();
                    } while (year < 2020);
                    if (day > 9 && month > 9) {
                        subDateOfAssignment = String.format("%d-%d-%d 00:00:00", year, month, day);
                    } else if (day < 10 && month > 9) {
                        subDateOfAssignment = String.format("%d-%d-0%d 00:00:00", year, month, day);
                    } else if (day < 10 && month < 10) {
                        subDateOfAssignment = String.format("%d-0%d-0%d 00:00:00", year, month, day);
                    } else if (day > 9 && month < 10) {
                        subDateOfAssignment = String.format("%d-0%d-%d 00:00:00", year, month, day);
                    }

                    int totalMarkOfAssignment;
                    do {
                        System.out.print("Please insert the total mark of assignment(0-10)(if it is not given yet please insert number -1):  ");
                        while (!scanner.hasNextInt()) {
                            String input = scanner.next();
                            System.out.printf("\"%s\" is not a number.Try again\n", input);
                        }
                        totalMarkOfAssignment = scanner.nextInt();
                    } while (totalMarkOfAssignment < -1 || totalMarkOfAssignment > 10);

                    Assignment assignment = new Assignment(titleOfAssignment, descriptionOfAssignment, subDateOfAssignment, totalMarkOfAssignment);
                    System.out.println("Records inserted: " + insertRecordToAssignments(assignment, db));
                    rs = db.getResults("SELECT * FROM Assignments");
                    printAssignmentsResults(rs);
                }
            }
        }

        System.out.println("**********************************************************");
        System.out.println("*******************Insertions in the students table**********************************");
        if (whatToInsert.indexOf("student") != -1) {
            int numOfStudents;
            do {
                System.out.println("How many students would you like to insert?(if you type 0,the program should use synthetic data)");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfStudents = scanner.nextInt();
            } while (numOfStudents < 0);
            System.out.printf("You have entered the valid number %d.\n", numOfStudents);
            if (numOfStudents != 0) {
                ///trancate
                for (int i = 0; i < numOfStudents; i++) {
                    System.out.println("Please insert the first name of student");
                    String firstNameOfStudent = scanner.next();
                    System.out.println("Please insert the last name of student");
                    String lastNameOfStudent = scanner.next();

                    String dateOfBirthOfStudent = "b";
                    int day;
                    do {
                        System.out.print("Please insert day of the starting date of birth of the student (e.g 15): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        day = scanner.nextInt();
                    } while (day < 01 || day > 31);

                    int month;
                    do {
                        System.out.print("Please insert month of the starting date of birth of the student (e.g 04): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        month = scanner.nextInt();
                    } while (month < 01 || month > 13);

                    int year;
                    do {
                        System.out.print("Please insert year of the starting date of birth of the student (e.g 1992): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Please enter a number:");
                            scanner.next();
                        }
                        year = scanner.nextInt();
                    } while (year > 2020 || year < 1900);
                    if (day > 9 && month > 9) {
                        dateOfBirthOfStudent = String.format("%d-%d-%d", year, month, day);
                    } else if (day < 10 && month > 9) {
                        dateOfBirthOfStudent = String.format("%d-%d-0%d", year, month, day);
                    } else if (day < 10 && month < 10) {
                        dateOfBirthOfStudent = String.format("%d-0%d-0%d", year, month, day);
                    } else if (day > 9 && month < 10) {
                        dateOfBirthOfStudent = String.format("%d-0%d-%d", year, month, day);
                    }

                    int tuitionFeesOfStudent;
                    do {
                        System.out.print("Please insert the tuition fees of student:  ");
                        while (!scanner.hasNextInt()) {
                            String input = scanner.next();
                            System.out.printf("\"%s\" is not a number.Try again\n", input);
                        }
                        tuitionFeesOfStudent = scanner.nextInt();
                    } while (tuitionFeesOfStudent < 0);

                    Student student = new Student(firstNameOfStudent, lastNameOfStudent, dateOfBirthOfStudent, tuitionFeesOfStudent);
                    System.out.println("Records inserted: " + insertRecordToStudents(student, db));
                    rs = db.getResults("SELECT * FROM students");
                    rs = db.getOneResultByField2("students", firstNameOfStudent, lastNameOfStudent, dateOfBirthOfStudent, tuitionFeesOfStudent);
                    int timer = 0;
                    try {
                        while (rs.next()) {
                            String t = String.format(rs.getString(1));
                            int result = Integer.parseInt(t);
                            System.out.println(t);
                            timer = timer + 1;
                            if (timer > 1) {
                                System.out.println("Records deleted: " + deleteRecordFromStudentsWithid(result, db));
                                System.out.println("The insertion is duplicated and deleted");
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    rs = db.getResults("SELECT * FROM students");
                    printStudentsResults(rs);
                }
            }
        }

        System.out.println("**********************************************************");
        System.out.println("*******************Insertions in the students per course table**********************************");
        System.out.println("Would you like to add students per course?(Yes/No)");
        String answer = scanner.next();
        answer = answer.toLowerCase();
        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.println("Invalid word, please try again");
            answer = scanner.next();
        }
        if (answer.equals("yes")) {
            int numOfStudentsPerCourse;
            do {
                System.out.println("How many insertions in the table students per course, would you like to insert?");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfStudentsPerCourse = scanner.nextInt();
            } while (numOfStudentsPerCourse <= 0);
            System.out.printf("You have entered the valid number %d.\n", numOfStudentsPerCourse);

            for (int i = 0; i < numOfStudentsPerCourse; i++) {
                int idOfCourse;
                do {
                    System.out.println("Please insert the id of course");
                    while (!scanner.hasNextInt()) {
                        String input = scanner.next();
                        System.out.printf("\"%s\" is not a number.Try again\n", input);
                    }
                    idOfCourse = scanner.nextInt();
                } while (idOfCourse < 1);

                int idOfStudent;
                do {
                    System.out.println("Please insert the id of student that belongs in the previous course");
                    while (!scanner.hasNextInt()) {
                        String input = scanner.next();
                        System.out.printf("\"%s\" is not a number.Try again\n", input);
                    }
                    idOfStudent = scanner.nextInt();
                } while (idOfStudent < 1);

                Student_per_course student_per_course = new Student_per_course(idOfCourse, idOfStudent);
                System.out.println("Records inserted: " + insertRecordToStudents_per_course(student_per_course, db));
                rs = db.getResults("SELECT * FROM students_per_course");
                printStudent_per_courseResults(rs);
            }
            System.out.println("Would you like to search for the id of course(s) of a student?(Yes/No)");
            String answer4 = scanner.next();
            answer4 = answer4.toLowerCase();
            while (!answer4.equals("yes") && !answer4.equals("no")) {
                System.out.println("Invalid word, please try again");
                answer4 = scanner.next();
            }
            if (answer4.equals("yes")) {
                System.out.println("Please type the id of student");
                int idOfStudent = scanner.nextInt();
                rs = db.getOneResultByField("students_per_course", "students_id", idOfStudent);
                System.out.println("Results");
                printStudent_per_courseResults(rs);
            }
        }

        System.out.println("**********************************************************");
        System.out.println("*******************Insertions in the trainers per course table**********************************");
        System.out.println("Would you like to add trainers per course?(Yes/No)");
        String answer1 = scanner.next();
        answer1 = answer1.toLowerCase();
        while (!answer1.equals("yes") && !answer1.equals("no")) {
            System.out.println("Invalid word, please try again");
            answer1 = scanner.next();
        }
        if (answer1.equals("yes")) {
            int numOfTrainersPerCourse;
            do {
                System.out.println("How many insertions in the table trainers per course, would you like to insert?");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfTrainersPerCourse = scanner.nextInt();
            } while (numOfTrainersPerCourse <= 0);
            System.out.printf("You have entered the valid number %d.\n", numOfTrainersPerCourse);

            for (int i = 0; i < numOfTrainersPerCourse; i++) {
                int idOfCourse;
                do {
                    System.out.println("Please insert the id of course");
                    while (!scanner.hasNextInt()) {
                        String input = scanner.next();
                        System.out.printf("\"%s\" is not a number.Try again\n", input);
                    }
                    idOfCourse = scanner.nextInt();
                } while (idOfCourse < 1);

                int idOfTrainer;
                do {
                    System.out.println("Please insert the id of trainer that belongs in the previous course");
                    while (!scanner.hasNextInt()) {
                        String input = scanner.next();
                        System.out.printf("\"%s\" is not a number.Try again\n", input);
                    }
                    idOfTrainer = scanner.nextInt();
                } while (idOfTrainer < 1);

                Trainer_per_course trainer_per_course = new Trainer_per_course(idOfCourse, idOfTrainer);
                System.out.println("Records inserted: " + insertRecordToTrainers_per_course(trainer_per_course, db));
                rs = db.getResults("SELECT * FROM trainers_per_course");
                printTrainer_per_courseResults(rs);
            }
            System.out.println("Would you like to search for the id of course(s) that teach every trainer?(Yes/No)");
            String answer5 = scanner.next();
            answer5 = answer5.toLowerCase();
            while (!answer5.equals("yes") && !answer5.equals("no")) {
                System.out.println("Invalid word, please try again");
                answer5 = scanner.next();
            }
            if (answer5.equals("yes")) {
                System.out.println("Please type the id of trainer");
                int idOfTrainer = scanner.nextInt();
                rs = db.getOneResultByField("trainers_per_course", "trainers_id", idOfTrainer);
                System.out.println("Results");
                printTrainer_per_courseResults(rs);
            }
        }

        System.out.println("**********************************************************");
        System.out.println("*******************Insertions in the assignments per student per course table**********************************");
        System.out.println("Would you like to add assignments per course and after that to find the insertions in the assignments per student per course table(Yes/No)");
        String answer2 = scanner.next();
        answer2 = answer2.toLowerCase();
        while (!answer2.equals("yes") && !answer2.equals("no")) {
            System.out.println("Invalid word, please try again");
            answer2 = scanner.next();
        }
        if (answer2.equals("yes")) {
            int numOfAssignmentsPerCourse;
            do {
                System.out.println("How many insertions in the table assignments per course, would you like to insert?");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a number.Try again\n", input);
                }
                numOfAssignmentsPerCourse = scanner.nextInt();
            } while (numOfAssignmentsPerCourse <= 0);
            System.out.printf("You have entered the valid number %d.\n", numOfAssignmentsPerCourse);

            for (int i = 0; i < numOfAssignmentsPerCourse; i++) {
                int idOfCourse;
                do {
                    System.out.println("Please insert the id of course");
                    while (!scanner.hasNextInt()) {
                        String input = scanner.next();
                        System.out.printf("\"%s\" is not a number.Try again\n", input);
                    }
                    idOfCourse = scanner.nextInt();
                } while (idOfCourse < 1);

                int idOfAssignment;
                do {
                    System.out.println("Please insert the id of assignment that belongs in the previous course");
                    while (!scanner.hasNextInt()) {
                        String input = scanner.next();
                        System.out.printf("\"%s\" is not a number.Try again\n", input);
                    }
                    idOfAssignment = scanner.nextInt();
                } while (idOfAssignment < 1);

                Assignment_per_course assignment_per_course = new Assignment_per_course(idOfCourse, idOfAssignment);
                System.out.println("Records inserted: " + insertRecordToAssignments_per_course(assignment_per_course, db));
                rs = db.getResults("SELECT * FROM assignments_per_course");
                printAssignment_per_courseResults(rs);
            }
            rs = db.getResults("SELECT `courses`.`stream`, `courses`.`type`, `students`.`first_name`, `students`.`last_name`, `assignments`.`title`, `assignments`.`description`  FROM `assignments` JOIN `assignments_per_course` ON  `assignments`.`id`=`assignments_per_course`.`assignments_id` JOIN `courses` ON  `courses`.`id`=`assignments_per_course`.`courses_id` JOIN `students_per_course` ON `courses`.`id`=`students_per_course`.`courses_id` JOIN `students` ON `students_per_course`.`students_id`=`students`.`id`;");
            printAssignmentsPerStudentPerCourseResults(rs);
            System.out.println("Would you like to find the id of course(s) per student per assignment?(Yes/No)");
            String answer6 = scanner.next();
            answer6 = answer6.toLowerCase();
            while (!answer6.equals("yes") && !answer6.equals("no")) {
                System.out.println("Invalid word, please try again");
                answer6 = scanner.next();
            }
            if (answer6.equals("yes")) {
                System.out.println("Please type the id of the student");
                int idOfstudent1 = scanner.nextInt();
                System.out.println("Please type the id of the assignment");
                int idOfAssignment1 = scanner.nextInt();
                rs = db.getResults("SELECT `students`.`id`,`assignments`.`id`,`courses`.`stream`, `courses`.`type`, `students`.`first_name`, `students`.`last_name`, `assignments`.`title`, `assignments`.`description`  FROM `assignments` JOIN `assignments_per_course` ON  `assignments`.`id`=`assignments_per_course`.`assignments_id` \n"
                        + "JOIN `courses` ON  `courses`.`id`=`assignments_per_course`.`courses_id`\n"
                        + "JOIN `students_per_course` ON `courses`.`id`=`students_per_course`.`courses_id`\n"
                        + "JOIN `students` ON `students_per_course`.`students_id`=`students`.`id`\n"
                        + "HAVING `students`.`id`=" + idOfstudent1 + " AND `assignments`.`id`=" + idOfAssignment1 + " ORDER BY `students`.`id`;");//To order by το έβαλα γιατί δεν μπόρεσα να κάνω αυτό ";" μετά το +titleOfAssignment+. Το γνωρίζω ότι δεν έχει λογική, αλλά δεν αλλάζει το αποτέλεσμα. 
                System.out.println("Results");
                printAssignmentsPerStudentPerCourseResults2(rs);
            }
        }
    }

    public static int insertRecordToCourses(Course course, Database db) {
        int result = 0;
        String course_data = "'" + course.getTitle() + "','" + course.getStream() + "','" + course.getType() + "','" + course.getStart_date() + "'";
        String sql = "INSERT INTO `courses` (`title`,`stream`,`type`,`start_date`) VALUES (" + course_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static int insertRecordToTrainers(Trainer trainer, Database db) {
        int result = 0;
        String trainer_data = "'" + trainer.getFirst_name() + "','" + trainer.getLast_name() + "','" + trainer.getSubject() + "'";
        String sql = "INSERT INTO `trainers` (`first_name`,`last_name`,`subject`) VALUES (" + trainer_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static int insertRecordToAssignments(Assignment assignment, Database db) {
        int result = 0;
        String assignment_data = "'" + assignment.getTitle() + "','" + assignment.getDescription() + "','" + assignment.getSub_date_time() + "','" + assignment.getTotal_mark() + "'";
        String sql = "INSERT INTO `assignments` (`title`,`description`,`sub_date_time`,`total_mark`) VALUES (" + assignment_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static int insertRecordToStudents(Student student, Database db) {
        int result = 0;
        String student_data = "'" + student.getFirst_name() + "','" + student.getLast_name() + "','" + student.getDate_of_birth() + "','" + student.getTuition_fees() + "'";
        String sql = "INSERT INTO `students` (`first_name`,`last_name`,`date_of_birth`,`tuition_fees`) VALUES (" + student_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static int insertRecordToStudents_per_course(Student_per_course student_per_course, Database db) {
        int result = 0;
        String student_per_course_data = "'" + student_per_course.getId() + "','" + student_per_course.getCourses_id() + "','" + student_per_course.getStudents_id() + "'";
        String sql = "INSERT INTO `students_per_course` (`id`,`courses_id`,`students_id`) VALUES (" + student_per_course_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static int insertRecordToTrainers_per_course(Trainer_per_course trainer_per_course, Database db) {
        int result = 0;
        String trainer_per_course_data = "'" + trainer_per_course.getId() + "','" + trainer_per_course.getCourses_id() + "','" + trainer_per_course.getTrainers_id() + "'";
        String sql = "INSERT INTO `trainers_per_course` (`id`,`courses_id`,`trainers_id`) VALUES (" + trainer_per_course_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static int insertRecordToAssignments_per_course(Assignment_per_course assignment_per_course, Database db) {
        int result = 0;
        String assignment_per_course_data = "'" + assignment_per_course.getId() + "','" + assignment_per_course.getCourses_id() + "','" + assignment_per_course.getAssignments_id() + "'";
        String sql = "INSERT INTO `assignments_per_course` (`id`,`courses_id`,`assignments_id`) VALUES (" + assignment_per_course_data + ");";
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }

    }

    public static void printCoursesResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", title: " + rs.getString(2)
                        + ", stream: " + rs.getString(3)
                        + ", type: " + rs.getString(4)
                        + ", start_date: " + rs.getString(5)
                        + ", end_date: " + rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printTrainersResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", first_name: " + rs.getString(2)
                        + ", last_name: " + rs.getString(3)
                        + ", subject: " + rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", first_name: " + rs.getString(2)
                        + ", last_name: " + rs.getString(3)
                        + ", date_of_birth: " + rs.getString(4)
                        + ", tuition_fees: " + rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", title: " + rs.getString(2)
                        + ", description: " + rs.getString(3)
                        + ", sub_date_time: " + rs.getString(4)
                        + ", total_mark: " + rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsPerCourseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "stream: " + rs.getString(1)
                        + ", type: " + rs.getString(2)
                        + ", first_name: " + rs.getString(3)
                        + ", last_name: " + rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printTrainersPerCourseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "stream: " + rs.getString(1)
                        + ", type: " + rs.getString(2)
                        + ", first_name: " + rs.getString(3)
                        + ", last_name: " + rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsPerCourseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "stream: " + rs.getString(1)
                        + ", type: " + rs.getString(2)
                        + ", title: " + rs.getString(3)
                        + ", description: " + rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsPerCoursePerStudentResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "stream: " + rs.getString(1)
                        + ", type: " + rs.getString(2)
                        + ", title: " + rs.getString(3)
                        + ", description: " + rs.getString(4)
                        + ", first_name: " + rs.getString(5)
                        + ", last_name: " + rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsBelongMoreThanOneCourseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "first_name: " + rs.getString(1)
                        + ", last_name: " + rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printSchoolResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", Course's Id: " + rs.getString(2)
                        + ", Trainer's Id: " + rs.getString(3)
                        + ", Student's Id: " + rs.getString(4)
                        + ", Assignment's Id: " + rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void printStudent_per_courseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", Course's Id: " + rs.getString(2)
                        + ", Student's Id: " + rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void printTrainer_per_courseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", Course's Id: " + rs.getString(2)
                        + ", Trainer's Id: " + rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void printAssignment_per_courseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id: " + rs.getString(1)
                        + ", Course's Id: " + rs.getString(2)
                        + ", Assignment's Id: " + rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void printAssignmentsPerStudentPerCourseResults(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "stream: " + rs.getString(1)
                        + ", type: " + rs.getString(2)
                        + ", first_name: " + rs.getString(3)
                        + ", last_name: " + rs.getString(4)
                        + ", title: " + rs.getString(5)
                        + ", description: " + rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsPerStudentPerCourseResults2(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(
                        "id of student: " + rs.getString(1)
                        + ", id of assignment: " + rs.getString(2)
                        + ", stream: " + rs.getString(3)
                        + ", type: " + rs.getString(4)
                        + ", first_name: " + rs.getString(5)
                        + ", last_name: " + rs.getString(6)
                        + ", title: " + rs.getString(7)
                        + ", description: " + rs.getString(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int deleteRecordFromStudentsWithid(int id, Database db) {
        int result = 0;
        String sql = String.format("DELETE FROM `students` WHERE `id` = '%s'", id);
        db.setStatementNonStatic();
        Statement st = db.getStatementNonStatic();
        try {
            result = st.executeUpdate(sql);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
