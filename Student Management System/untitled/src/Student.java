public class Student {
    private String studentId;
    private String name;
    private Module module[];

    Student(String studentId,String name,Module modules[]){
        this.studentId=studentId;
        this.name=name;
        this.module=modules;
    }

    public String getStudentId(){
        return studentId;
    }

    public String getStudentName(){
        return name;
    }

    public Module[] getModules(){
        return module;
    }

}