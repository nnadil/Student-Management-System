import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
//import java.
public class StudentManagement {
    static final int maxStudents=100;
    static Student [] studentsArray=new Student[maxStudents];
    static int count=0;
    static String name="";
    static String id="";

    public static void printMenu(){
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\t\tMENU\t\t\t\t\t|");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("1. Check available seats: ");
        System.out.println("2. Register student(with ID): ");
        System.out.println("3. Delete student: ");
        System.out.println("4. Find student(wIth ID): ");
        System.out.println("5. Store student details in to a file: ");
        System.out.println("6. Load student details from the file to the system: ");
        System.out.println("7. View the list of students based on thier names: ");
        System.out.println("8. Add Marks with student name: ");
        System.out.println("9. Generate summary: ");
        System.out.println("10. Report: ");
        System.out.println("11. Exit: ");
    }


    public static void checkAvailableSeats(){
        System.out.println("Available seats: "+(maxStudents-count));
    }

    public static void registerStudent(Scanner input){
        boolean flag=false;
        if(count==maxStudents){
            System.out.println("Maximum Students Reached");
            return;
        }
        while(true){
            System.out.print("Enter id: ");
            id=input.next();
            for(int y=0;y<studentsArray.length;y++){
                Student student=studentsArray[y]; //Taking the count of students already in the array
                if(student!=null){
                    count++;
                }
            }
            if(id.length()==8){
                flag=false;
                for(int i=0;i<studentsArray.length;i++){
                    if(studentsArray[i]!=null){
                        Student student=studentsArray[i];
                        if(student.getStudentId().equals(id)){ //Checking entered id in the array
                            System.out.println("Already Inserted thid Id, Please Enter again.");
                            flag=true;
                            break;
                        }else{
                            break;
                        }
                    }else{
                        break;
                    }
                }

            }else{
                System.out.println("Invalid Id, Please enter a length of 8 characters.");
                continue;
            }
            if(!flag){
                break;
            }
        }
        input.nextLine(); //Consume the newline

        System.out.print("Enter student name: ");
        name=input.nextLine();

        String fML[]=name.split(" "); //Entering each word while removing the spaces
        for(int i=0;i<fML.length;i++){
            fML[i]=fML[i].substring(0,1).toUpperCase()+fML[i].substring(1); //Capitalize the letter in each word in the name
        }
        String first=fML.length>0?fML[0]:"";
        String middle=fML.length>1?fML[1]:"";
        String last=fML.length>2?fML[2]:"";
        name=first+(middle.isEmpty()?"":" "+middle)+(last.isEmpty()?"":" "+last); //Assighning the name

        Module[]modules=new Module[3];
        for(int i=0;i<modules.length;i++){
            modules[i]=new Module();

        }
        Student s1=new Student(id,name,modules);
        studentsArray[count++]=s1;
        System.out.println("Student Register is Successfully");
        System.out.print("Do you want to add student again(y/n): ");
        String choice=input.next().toUpperCase();
        if(choice.equals("Y")){
            registerStudent(input);
        }
    }

    public static void storeStudentWithDetails(){
        try {
            FileWriter file=new FileWriter("text.txt");
            for(int i=0;i<studentsArray.length;i++){
                if(studentsArray[i]!=null){ //checking student array i place is an empty
                    Student student=studentsArray[i];
                    Module[]modules=student.getModules();
                    file.write(student.getStudentId()+","+student.getStudentName());
                    for(int obj=0;obj<3;obj++){
                        if(modules[obj]!=null){
                            file.write(","+modules[obj].getModuleName()+","+modules[obj].getModuleMark());
                        }
                    }
                    file.write("\n");
                }
            }
            file.close();
            System.out.println("Details successfully stored in to the file.");
        } catch (IOException e) {
            System.out.println("An Error Occurred.");
            e.printStackTrace();
        }
    }

    public static void loadDetails(){
        int counter=0;
        try {
            File file =new File("text.txt");
            Scanner file_reader=new Scanner(file);
            while(file_reader.hasNextLine()){   //Checking there is a line to read
                String text=file_reader.nextLine();
                String[] listOfDetails=text.split(","); //Removing "," fom the line and inserted in to a array
                String id=listOfDetails[0].trim(); // Removing white spaces
                name=listOfDetails[1];
                Module modules[]=new Module[3];
                for(int num=0;num<modules.length;num++){
                    String moduleName=listOfDetails[2+num*2].trim();
                    double moduleMark=Double.parseDouble(listOfDetails[3+num*2].trim()); //Converting String to a Double
                    modules[num]=new Module(moduleName,moduleMark);

                }
                Student student=new Student(id,name,modules);
                studentsArray[counter++]=student;
            }
            file_reader.close();
            System.out.println("Details successfully load in to the array.");
            count=counter;

        } catch (Exception e) {
            System.out.println("An Error Occurred.");
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Scanner input){
        int count=0;
        String del;
        boolean flag=false;
        Student temp[]=new Student[studentsArray.length];   //Created a temporary object array
        while(true){
            System.out.print("Enter Student ID to remove: ");
            del=input.next();
            if(del.length()==8){
                break;
            }else{
                System.out.println("Invalid Id, Please enter a length of 8 characters.");
            }
        }
        for(int i=0;i<studentsArray.length;i++){
            if(studentsArray[i]!=null){
                Student student=studentsArray[i];
                if(student.getStudentId().equals(del)){ //Checking entered id in the array
                    count=i;
                    flag=true;
                    break;
                }
            }
        }
        if(!flag){
            System.out.println("No Such Student Registered.");
            System.out.print("Do you want to delete another student.");
            String choice=input.next().toUpperCase();
            if(choice.equals("Y")){
                deleteStudent(input);
            }
        }else{
            for(int front=0;front<count;front++){  //Entering student objects to temporary array before the del object
                temp[front]=studentsArray[front];
            }
            for(int last=count+1;last<studentsArray.length;last++){  //Entering student objects to temporary array after the del object
                temp[last]=studentsArray[last];
            }
            studentsArray=temp;
            System.out.println("Student Deleted Successfully.");
        }
    }


    public static void findStudent(Scanner input){
        boolean flag=false;
        boolean flag2=false;
        String studentId="";
        if(studentsArray[0]==null){
            System.out.println("No students registered.");
            return;
        }
        while(true){
            System.out.print("Enter Student ID to find: ");
            studentId=input.next();
            if(studentId.length()==8){
                break;
            }else{
                System.out.println("Invalid Id, Please enter a length of 8 characters.");
            }
        }

        for(int i=0;i<studentsArray.length;i++){
            Student student=studentsArray[i];
            if(student!=null){
                if(student.getStudentId().equals(studentId)){
                    flag=true;
                    Module modules[]=student.getModules();
                    System.out.println("Student ID: "+student.getStudentId());
                    System.out.println("Student Name: "+student.getStudentName());
                    for(int x=0;x<modules.length;x++){
                        if(modules[x]!=null){
                            System.out.println("Module Name: "+modules[x].getModuleName());
                            System.out.println(modules[x].getModuleName()+" Mark: "+modules[x].getModuleMark());
                        }
                    }
                }
                if(flag){
                    System.out.println("Student find successfully.");
                    flag2=true;
                    break;
                }
            }else{
                break;
            }
        }
        if(!flag2){
            System.out.println("No such student registered.");
        }
        System.out.print("Do you want to find a student again(y/n): ");
        String choice=input.next().toUpperCase();
        if(choice.equals("Y")){
            findStudent(input);
        }
    }


    public static void viewStudent(){
        int studentCount=0;
        for(Student student:studentsArray){ //Taking the count of students in the studentsArray
            if(student!=null){
                studentCount++;
            }
            if(studentsArray[0]==null){
                System.out.println("No students registered yet.");
                return;
            }
        }

        String studentNames[]=new String[studentCount];

        for(int i=0;i<studentsArray.length;i++){  //Entering names to the newly created array
            Student student=studentsArray[i];
            if(student!=null){
                studentNames[i]=student.getStudentName();
            }
        }

        for(int i=0;i<studentNames.length;i++){ //Sorting in the alphabetical order
            for(int x=i;x<studentNames.length;x++){
                if(studentNames[i].compareTo(studentNames[x])>0){ //Taking the unicode value and comparing
                    String temp=studentNames[i];
                    studentNames[i]=studentNames[x];
                    studentNames[x]=temp;
                }
            }
        }

        System.out.print("Names in alphabetical order: ");
        for(int z=0;z<studentNames.length;z++){  //Printing the sorted array
            System.out.print(studentNames[z]+", ");
        }
        System.out.println("\b\b ");
        System.out.println("Student view successfully.");
        System.out.println();
    }


    public static void addStudentNameWithMarks(Scanner input){
        String moduleName;
        double moduleMark;
        Student student;
        boolean flag=false;

        if(studentsArray[0]==null){
            System.out.println("Students not registered yet.");
            return;
        }

        input.nextLine();
        while(true){
            System.out.print("Enter student name: ");
            name=input.nextLine();
            if(name.isEmpty()){
                System.out.println("Student name cannot be empty, Please enter again.");
            }else{
                break;
            }
        }

        String fML[]=name.split(" "); //Entering each word while removing the spaces
        for(int i=0;i<fML.length;i++){
            fML[i]=fML[i].substring(0,1).toUpperCase()+fML[i].substring(1); //Capitalize the letter in each word in the name
        }
        String first=fML.length>0?fML[0]:"";
        String middle=fML.length>1?fML[1]:"";
        String last=fML.length>2?fML[2]:"";
        name=first+" "+(middle.isEmpty()?"":middle)+" "+(last.isEmpty()?"":last); //Assighning the name

        for(int i=0;i<studentsArray.length;i++){
            flag=false;
            student=studentsArray[i];
            if(studentsArray[0]==null){
                System.out.println("No such student name registered.");
                break;
            }else if(studentsArray[i]==null){
                break;
            }
            if(student.getStudentName().equals(name)){
                flag=true;
                Module modulesArr[]=student.getModules();
                if(modulesArr[0]==null){ //If marks already added.
                    System.out.println("Marks are already added.");
                    break;
                }
                flag=true;
                for(int num=0;num<3;num++){
                    while(true){
                        System.out.print("Enter Module "+(num+1)+" Name: ");
                        moduleName=input.next();
                        moduleName=moduleName.substring(0,1).toUpperCase()+moduleName.substring(1); //Capitalize the first letter of the word
                        if(moduleName.isEmpty()){ //Checking the name is empty or not
                            System.out.println("Module Name cannot be empty.");
                        }else{
                            break;
                        }
                    }
                    while(true){
                        try {
                            System.out.print(moduleName+" Mark: ");
                            moduleMark=input.nextDouble();
                        } catch (Exception e) {
                            System.out.println("Ïnvalid Mark.");
                            input.next(); // Clearing the input
                            continue;
                        }
                        if(moduleMark>=0 && moduleMark<=100){
                            break;
                        }else{
                            System.out.println("Module Mark cannot be minus");
                        }
                    }
                    Module module=new Module(moduleName,moduleMark);
                    Module modules[]=student.getModules();
                    modules[num]=module; //Adding the object module to the array
                }
                System.out.println("Successfully marks are added.");
                break;

            }
        }
        if(!flag){
            System.out.println("No such student name registered.");
        }
        System.out.print("Do you want to add marks again(y/n): ");
        String choice=input.next().toUpperCase();
        if(choice.equals("Y")){
            addStudentNameWithMarks(input); //Calling the method again
        }
    }

    public static void generateSummary(){
        if(studentsArray[0]==null){ //Checking at least one student registered or not
            System.out.println("No students registered yet.");
            return;
        }
        System.out.println("Total students registered: "+count);
        int studentCount=0;
        boolean flag=false; //Flag for take more than 40 marks in all modules
        for(int i=0;i<studentsArray.length;i++){
            Student student=studentsArray[i];
            if(student!=null){
                flag=false;
                Module modules[]=student.getModules();
                for(int y=0;y<modules.length;y++){
                    if(modules[y]!=null){
                        Module module=modules[y];
                        if(module.getModuleMark()>40) {
                            flag=true;
                        }else{
                            break;
                        }
                    }
                }
                if(flag){
                    studentCount++;
                }
            }
        }
        System.out.println("Total students who scored more than 40 marks for each module: "+studentCount);
    }


    public static void report() {
        if (studentsArray[0] == null) {
            System.out.println("No students registered yet.");
            return;
        }

        Student tempStudentArr[] = new Student[studentsArray.length]; // Created a temporary array for sort, to sort without changing the original arr
        for (int i = 0; i < tempStudentArr.length; i++) {
            if (studentsArray[i] != null) {
                tempStudentArr[i] = studentsArray[i];
            }
        }

        // Bubble Sort in ascending order based on average marks
        for (int x = 0; x < tempStudentArr.length; x++) {
            for (int y = 0; y < tempStudentArr.length - 1; y++) {
                Student student1 = tempStudentArr[y];
                Student student2 = tempStudentArr[y + 1];

                if (student1 != null && student2 != null) {
                    double avg1 = calculateAverage(student1);
                    double avg2 = calculateAverage(student2);

                    if (avg1 > avg2) {
                        Student temp = tempStudentArr[y];
                        tempStudentArr[y] = tempStudentArr[y + 1];
                        tempStudentArr[y + 1] = temp;
                    }
                }
            }
        }

        for (Student student : tempStudentArr) {
            if (student != null) {
                double total = 0;
                double avg = 0;
                String grade = "";

                String id = student.getStudentId();
                String name = student.getStudentName();
                System.out.println("Student ID: "+id);
                System.out.println("Student Name: "+name);
                Module modules[] = student.getModules();
                for (Module module : modules) {
                    if (module != null) { // If module marks not added
                        System.out.println("Module Name: "+module.getModuleName());
                        System.out.println(module.getModuleName()+" Mark: "+module.getModuleMark()); // Printing each module mark
                        total += module.getModuleMark();
                    }
                }

                avg = total / modules.length; // Assuming each student has 3 modules

                if (avg >= 80) {
                    grade = "Distinction";
                } else if (avg >= 70) {
                    grade = "Merit";
                } else if (avg >= 40) {
                    grade = "Pass";
                } else {
                    grade = "Fail";
                }

                System.out.println("Total: "+total);
                System.out.println("Averge: "+avg);
                System.out.println("Grade: "+grade);
                System.out.println();
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println();
            }
        }
    }

    private static double calculateAverage(Student student) {
        double total = 0;
        int moduleCount = 0;
        for (Module module : student.getModules()) {
            if (module != null) { //Checking module object is null
                total += module.getModuleMark();
                moduleCount++;
            }
        }
        return moduleCount > 0 ? total / moduleCount : 0;
    }


    public static void main(String args[]){
        Scanner input=new Scanner(System.in);
        boolean exit=false;
        while(true){
            printMenu();
            try {
                System.out.print("Enter your choice: ");
                int choice=input.nextInt();
                switch(choice){
                    case 1: checkAvailableSeats(); break;
                    case 2: registerStudent(input); break;
                    case 3: deleteStudent(input); break;
                    case 4: findStudent(input); break;
                    case 5: storeStudentWithDetails();break;
                    case 6: loadDetails(); break;
                    case 7: viewStudent(); break;
                    case 8: addStudentNameWithMarks(input);break;
                    case 9: generateSummary(); break;
                    case 10: report(); break;
                    case 11: exit=true; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Enter a valid integer.");
                input.next(); //Clear the invalid input
            }
            System.out.println();
            if(exit){
                System.out.println("Exiting...");
                break;
            }

        }
    }
}