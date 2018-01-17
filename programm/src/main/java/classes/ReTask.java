package classes;

import java.util.ArrayList;

public class ReTask {
    private String name;
    private ArrayList<Date> dates;
    private Date completionDate;
    private Date creationDate;
    private ArrayList<Integer> tagList;

    public ReTask(String name, ArrayList<Date> dates, Date completionDate, Date creationDate, ArrayList<Integer> tagList){
        this.name = name;
        this.dates = dates;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
        this.tagList = tagList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> done) {
        this.dates = done;
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

    public ArrayList<Integer> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<Integer> tagList) {
        this.tagList = tagList;
    }

    public ArrayList<Task> toTask(){
        ArrayList<Task> arrayList = new ArrayList<>();
        for (Date date: this.getDates()) {
            Task task = new Task(this.getName(),date, this.completionDate, this.creationDate, 1,date.getDone(),tagList);
            arrayList.add(task);
        }

        return arrayList;
    }
}