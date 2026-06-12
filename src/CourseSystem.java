// ============================================================
//  Online Course Management System
//  Using Core Java & OOP Concepts
// ============================================================
 
// ─────────────────── Course Class ───────────────────────────
class Course {
 
    // Private encapsulated fields
    private String   courseName;
    private String   courseId;
    private String   duration;
    private Student[] enrolledStudents;   // array of enrolled students
    private int      enrolledCount;
 
    // Constructor
    public Course(String courseName, String courseId, String duration) {
        this.courseName       = courseName;
        this.courseId         = courseId;
        this.duration         = duration;
        this.enrolledStudents = new Student[50];
        this.enrolledCount    = 0;
    }
 
    // Getters
    public String getCourseName() { return courseName; }
    public String getCourseId()   { return courseId;   }
    public String getDuration()   { return duration;   }
 
    // Called when a student enrolls
    public void addStudent(Student student) {
        if (enrolledCount < enrolledStudents.length) {
            enrolledStudents[enrolledCount++] = student;
        } else {
            System.out.println("  [!] Course " + courseName + " is full.");
        }
    }
 
    // Display course info + all enrolled students with progress
    public void displayDetails() {
        System.out.println("============================================");
        System.out.println("  Course   : " + courseName);
        System.out.println("  Course ID: " + courseId);
        System.out.println("  Duration : " + duration);
        System.out.println("  Enrolled Students:");
        if (enrolledCount == 0) {
            System.out.println("    (No students enrolled yet)");
        } else {
            for (int i = 0; i < enrolledCount; i++) {
                Student s = enrolledStudents[i];
                System.out.println("    " + (i+1) + ". "
                    + s.getStudentName()
                    + "  -  Progress: "
                    + s.getProgressForCourse(courseId) + "%");
            }
        }
        System.out.println("============================================");
    }
}
 
// ─────────────────── Student Class ──────────────────────────
class Student {
 
    private String   studentName;
    private String   studentId;
    private String[] enrolledCourseIds;   // parallel array: course IDs
    private int[]    progressValues;      // parallel array: progress %
    private int      courseCount;
 
    public Student(String studentName, String studentId) {
        this.studentName       = studentName;
        this.studentId         = studentId;
        this.enrolledCourseIds = new String[20];
        this.progressValues    = new int[20];
        this.courseCount       = 0;
    }
 
    public String getStudentName() { return studentName; }
    public String getStudentId()   { return studentId;   }
 
    // Enroll student in a course (prevents duplicates)
    public void enroll(Course course) {
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourseIds[i].equals(course.getCourseId())) {
                System.out.println("  [!] " + studentName
                    + " is already enrolled in " + course.getCourseName());
                return;
            }
        }
        if (courseCount < enrolledCourseIds.length) {
            enrolledCourseIds[courseCount] = course.getCourseId();
            progressValues[courseCount]    = 0;
            courseCount++;
            course.addStudent(this);
            System.out.println("  [OK] " + studentName
                + " enrolled in " + course.getCourseName());
        } else {
            System.out.println("  [!] " + studentName + " cannot enroll more.");
        }
    }
 
    // Update progress (validates 0–100)
    public void updateProgress(String courseId, int value) {
        if (value < 0 || value > 100) {
            System.out.println("  [!] Progress must be 0–100.");
            return;
        }
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourseIds[i].equals(courseId)) {
                progressValues[i] = value;
                System.out.println("  [OK] Progress updated: "
                    + studentName + " -> " + courseId + " = " + value + "%");
                return;
            }
        }
        System.out.println("  [!] " + studentName
            + " not enrolled in course " + courseId);
    }
 
    // Returns progress for a specific course ID
    public int getProgressForCourse(String courseId) {
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourseIds[i].equals(courseId)) return progressValues[i];
        }
        return -1;   // not enrolled
    }
 
    // Display all enrolled courses and progress
    public void displayDetails() {
        System.out.println("============================================");
        System.out.println("  Student   : " + studentName);
        System.out.println("  Student ID: " + studentId);
        System.out.println("  Enrolled Courses & Progress:");
        if (courseCount == 0) {
            System.out.println("    (Not enrolled in any course)");
        } else {
            for (int i = 0; i < courseCount; i++) {
                System.out.println("    - Course ID: " + enrolledCourseIds[i]
                    + "  |  Progress: " + progressValues[i] + "%");
            }
        }
        System.out.println("============================================");
    }
}
 
// ─────────────────── Main Class ─────────────────────────────
public class CourseSystem {
 
    public static void main(String[] args) {
 
        // 1. Create Course objects
        Course c1 = new Course("Java Programming",  "C101", "8 Weeks");
        Course c2 = new Course("Data Structures",   "C102", "10 Weeks");
        Course c3 = new Course("Web Development",   "C103", "6 Weeks");
        Course[] courses = { c1, c2, c3 };
 
        // 2. Create Student objects
        Student s1 = new Student("Rahul",  "S001");
        Student s2 = new Student("Priya",  "S002");
        Student s3 = new Student("Aman",   "S003");
        Student s4 = new Student("Sneha",  "S004");
        Student[] students = { s1, s2, s3, s4 };
 
        // 3. Enroll students
        System.out.println("\n========== ENROLLMENT ==========");
        s1.enroll(c1);  s1.enroll(c2);
        s2.enroll(c1);  s2.enroll(c3);
        s3.enroll(c2);  s3.enroll(c3);
        s4.enroll(c1);  s4.enroll(c2);  s4.enroll(c3);
        s1.enroll(c1);  // duplicate test
 
        // 4. Update progress
        System.out.println("\n========== PROGRESS UPDATE ==========");
        s1.updateProgress("C101", 80);   s1.updateProgress("C102", 55);
        s2.updateProgress("C101", 65);   s2.updateProgress("C103", 90);
        s3.updateProgress("C102", 40);   s3.updateProgress("C103", 75);
        s4.updateProgress("C101", 95);   s4.updateProgress("C102", 30);
        s4.updateProgress("C103", 60);
        s1.updateProgress("C101", 110);  // invalid range test
 
        // 5. Display all course details
        System.out.println("\n========== COURSE DETAILS ==========");
        for (Course c : courses)   c.displayDetails();
 
        // 6. Display all student details
        System.out.println("\n========== STUDENT DETAILS ==========");
        for (Student s : students) s.displayDetails();
    }
}

