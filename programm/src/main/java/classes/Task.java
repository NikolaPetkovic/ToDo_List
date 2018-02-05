package classes;

import java.util.ArrayList;

public class Task {
    private String name;
    private Date deadline;
    private Date completionDate;
    private Date creationDate;
    private int priority;
    private Boolean done;
    private ArrayList<Integer> tagIdList;

    public Task(String name, Date deadline, Date completionDate, Date creationDate, int priority, Boolean done, ArrayList<Integer> tagIdList){
        this.name = name;
        this.deadline = deadline;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
        this.priority = priority;
        this.done = done;
        this.tagIdList = tagIdList;
    }

    public String getName() {
        return name;
    }

    public String getShortName(){
        String shortName;
        if(name.length()>14){
            shortName = name.substring(0,13)+"..";
        }else{
            shortName = name;
        }
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", deadline=" + deadline +
                ", completionDate=" + completionDate +
                ", creationDate=" + creationDate +
                ", priority=" + priority +
                ", done=" + done +
                ", tagIdList=" + tagIdList +
                '}';
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public ArrayList<Integer> gettagIdList() {
        return tagIdList;
    }

    public void settagIdList(ArrayList<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
