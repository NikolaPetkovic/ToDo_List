package model;

import classes.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SourceModel {
    private ReaderWriterArchivist rwa;
    private String dir1Name;
    private String dir2Name;
    private ArrayList<Tag> tagList;
    private ArrayList<Task> taskList;
    private ArrayList<ReTask> reTaskList;
    private ArrayList<Tag> tagArchList;
    private ArrayList<Task> taskArchList;

    public SourceModel(String dir1Name, String dir2Name, ReaderWriterArchivist rwa, ArrayList<Tag> tagList , ArrayList<Task> taskList, ArrayList<ReTask> reTaskList, ArrayList<Tag> tagArchList , ArrayList<Task> taskArchList) {
        super();
        this.dir1Name = dir1Name;
        this.dir2Name = dir2Name;
        this.rwa = rwa;
        this.tagList = tagList;
        this.taskList = taskList;
        this.reTaskList = reTaskList;
        this.tagArchList = tagArchList;
        this.taskArchList = taskArchList;
    }

    public String getDir1Name() {
        return dir1Name;
    }
    public void setDir1Name(String dir1Name) {
        this.dir1Name = dir1Name;
    }
    public String getDir2Name() {
        return dir2Name;
    }
    public void setDir2Name(String dir2Name) {
        this.dir2Name = dir2Name;
    }
    public ReaderWriterArchivist getRwa() {
        return rwa;
    }
    public void setRwa(ReaderWriterArchivist rwa) {
        this.rwa = rwa;
    }
    public ArrayList<Tag> getTagList() {
        return tagList;
    }
    public void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
    public ArrayList<ReTask> getReTaskList() {
        return reTaskList;
    }
    public void setReTaskList(ArrayList<ReTask> reTaskList) {
        this.reTaskList = reTaskList;
    }
    public ArrayList<Tag> getTagArchList() {
        return tagArchList;
    }
    public void setTagArchList(ArrayList<Tag> tagArchList) {
        this.tagArchList = tagArchList;
    }
    public ArrayList<Task> getTaskArchList() {
        return taskArchList;
    }
    public void setTaskArchList(ArrayList<Task> taskArchList) {
        this.taskArchList = taskArchList;
    }

    public int getHighestTagId(){
        int max = 0;
        if (tagList.size() == 0 && tagArchList.size() == 0){

        } else if (tagArchList.size() == 0){
            for (int i = 0; i < tagList.size(); i++){
                if (tagList.get(i).getId() > max) {
                    max = tagList.get(i).getId();
                }
            }
        } else if (tagList.size() == 0){
            for (int i = 0; i < tagArchList.size(); i++){
                if (tagArchList.get(i).getId() > max) {
                    max = tagArchList.get(i).getId();
                }
            }
        } else {
            for (int i = 0; i < tagList.size(); i++){
                if (tagList.get(i).getId() > max) {
                    max = tagList.get(i).getId();
                }
            }
            for (int i = 0; i < tagArchList.size(); i++){
                if (tagArchList.get(i).getId() > max) {
                    max = tagArchList.get(i).getId();
                }
            }
        }
        return max + 1;
    }
    public void addTag(Tag tag) {
        tagList.add(tag);
    }
    public void removeTag(Tag tag) {
        tagList.remove(tag);
    }
    public void addArchTag(Tag tag) {
        tagArchList.add(tag);
    }
    public void removeArchTagWithId(int id) {
        ArrayList<Tag> removeTags = new ArrayList<>();
        for (Tag archTag : tagArchList) {
            if (archTag.getId() == id){
                removeTags.add(archTag);
            }
        }
        for (Tag rTag: removeTags) {
            tagArchList.remove(rTag);
        }
    }
    public void addTask(Task task) {
        taskList.add(task);
    }
    public void removeTask(Task task) {
        taskList.remove(task);
    }
    public void addReTask(ReTask reTask) {
        reTaskList.add(reTask);
    }
    public void removeReTask(ReTask reTask) {
        reTaskList.remove(reTask);
    }

    public String getSelect1(){
        String out = null;
        try {
            out = rwa.getFileNameFromFile("Sort1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String getSelect2(){
        String out = null;
        try {
            out = rwa.getFileNameFromFile("Sort2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void saveTasks(){
        sortIdTasks();
        try {
            rwa.writeTasksToFile(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveReTasks(){
        sortIdReTasks();
        try {
            rwa.writeReTasksToFile(reTaskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveTags(){
        try {
            rwa.writeTagsToArchive(tagList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortTags(){
        ArrayList<Tag> sortTags = new ArrayList<>();
        int max = getHighestTagId();
        for (int i = 0; i < max; i++){
            for (Tag unSortedTag : tagList){
                if (unSortedTag.getId()-1 == i){
                    sortTags.add(unSortedTag);
                }
            }
        }
        setTagList(sortTags);
    }
    public void sortTasks(){
        try {
            taskList = rwa.readTasksFromFile();
            if (!rwa.getFileNameFromFile("Sort1").equals(null) && !rwa.getFileNameFromFile("Sort2").equals(null) && !rwa.getFileNameFromFile("Sort1").equals("-") && !rwa.getFileNameFromFile("Sort1").equals("")){
                String select1 = rwa.getFileNameFromFile("Sort1");
                String select2 = rwa.getFileNameFromFile("Sort2");
                ArrayList<Task> unSortTasks = getTaskList();
                ArrayList<Task> sortTasks = new ArrayList<>();

                if (select1.equals("Priority")) {
                    if (!select2.equals("-") && !select2.equals("")){
                        if (select2.equals("Deadline")) {
                            for (int i = 0; i < 3; i++) {
                                sortTasks.addAll(sortTaskWithDeadline(sortTaskWithPriority(unSortTasks,i+1), false).get(0));
                            }
                            taskList = sortTasks;
                        } else if (select2.equals("Tags")){
                            for (int i = 0; i < 3; i++) {
                                sortTasks.addAll(sortTaskWithTag(sortTaskWithPriority(unSortTasks,i+1), false).get(0));
                            }
                            taskList = sortTasks;
                        }
                    } else {
                        taskList = sortTaskWithPriority(unSortTasks,0);
                    }
                } else if (select1.equals("Deadline")) {
                    if (!select2.equals("-") && !select2.equals("")){
                        if (select2.equals("Priority")) {
                            ArrayList<ArrayList<Task>> test = sortTaskWithDeadline(unSortTasks, true);
                            for (ArrayList<Task> day : test) {
                                sortTasks.addAll(sortTaskWithPriority(day, 0));
                            }
                            taskList = sortTasks;
                        } else if (select2.equals("Tags")){
                            for (ArrayList<Task> day : sortTaskWithDeadline(unSortTasks, true)) {
                                sortTasks.addAll(sortTaskWithTag(day, false).get(0));
                            }
                            taskList = sortTasks;
                        }
                    } else {
                        taskList = sortTaskWithDeadline(unSortTasks, false).get(0);
                    }
                } else if (select1.equals("Tags")) {
                    if (!select2.equals("-") && !select2.equals("")){
                        if (select2.equals("Priority")) {
                            for (ArrayList<Task> list : sortTaskWithTag(unSortTasks, true)) {
                                sortTasks.addAll(sortTaskWithPriority(list, 0));
                            }
                            taskList = sortTasks;
                        } else if (select2.equals("Deadline")){
                            for (ArrayList<Task> list : sortTaskWithTag(unSortTasks, true)) {
                                sortTasks.addAll(sortTaskWithDeadline(list, false).get(0));
                            }
                            taskList = sortTasks;
                        }
                    } else {
                        taskList = sortTaskWithTag(unSortTasks, false).get(0);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sortIdTasks(){
        for (Task task : taskList) {
            Integer hold;
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    if (task.gettagIdList().get(i) == 0){
                        hold = task.gettagIdList().get(i);
                        task.gettagIdList().set(i, task.gettagIdList().get(i+1));
                        task.gettagIdList().set(i+1, hold);
                    } else if (task.gettagIdList().get(i) > task.gettagIdList().get(i+1) && task.gettagIdList().get(i+1) != 0){
                        hold = task.gettagIdList().get(i);
                        task.gettagIdList().set(i, task.gettagIdList().get(i+1));
                        task.gettagIdList().set(i+1, hold);
                    }
                }
            }
        }
    }
    public void sortIdReTasks(){
        for (ReTask reTask : reTaskList) {
            Integer hold;
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    if (reTask.getTagList().get(i) == 0){
                        hold = reTask.getTagList().get(i);
                        reTask.getTagList().set(i, reTask.getTagList().get(i+1));
                        reTask.getTagList().set(i+1, hold);
                    } else if (reTask.getTagList().get(i) > reTask.getTagList().get(i+1) &&reTask.getTagList().get(i+1) != 0){
                        hold = reTask.getTagList().get(i);
                        reTask.getTagList().set(i, reTask.getTagList().get(i+1));
                        reTask.getTagList().set(i+1, hold);
                    }
                }
            }
        }
    }


    private boolean dateBefore (Task one, Task two){
        Date deadline1 = one.getDeadline();
        Date deadline2 = two.getDeadline();

        if (deadline1.getYear() < deadline2.getYear()) {
            return true;
        }else if (deadline1.getYear() == deadline2.getYear()){
            if (deadline1.getMonth() < deadline2.getMonth()){
                return true;
            }else if (deadline1.getMonth() == deadline2.getMonth()){
                if(deadline1.getDay() < deadline2.getDay()){
                    return true;
                }else if (deadline1.getDay() == deadline2.getDay()){
                    if (deadline1.getHour() < deadline2.getHour()){
                        return true;
                    }else if (deadline1.getHour() == deadline2.getHour()){
                        if (deadline1.getMinute() < deadline2.getMinute()){
                            return true;
                        }else if (deadline1.getMinute() == deadline2.getMinute()){
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private boolean sameDay (Task one, Task two){
        Date deadline1 = one.getDeadline();
        Date deadline2 = two.getDeadline();

        if (deadline1.getYear() == deadline2.getYear() && deadline1.getMonth() == deadline2.getMonth() && deadline1.getDay() == deadline2.getDay()){
            return true;
        } else {
            return false;
        }
    }
    private boolean sameDate (Date one, Date two){
        if (one.getYear() == two.getYear() && one.getMonth() == two.getMonth() && one.getDay() == two.getDay() && one.getHour() == two.getHour() && one.getMinute() == two.getMinute()){
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<ArrayList<Task>> sortTaskWithTag (ArrayList<Task> unSortTasks, Boolean oneTag){
        ArrayList<ArrayList<Task>> sortTaskForTag = new ArrayList<>();
        ArrayList<Task> sortTasks = new ArrayList<>();

        for (int i = 0; i < getTagList().size(); i++) {
            for (Task unSortTask : unSortTasks) {
                for (Integer tagId : unSortTask.gettagIdList()) {
                    if (tagId == getTagList().get(i).getId()){
                        if (oneTag){
                            if (sortTaskForTag.size() == 0){
                                if (!sortTasks.contains(unSortTask)){
                                    sortTasks.add(unSortTask);
                                }
                            } else {
                                int counter = 0;
                                for (ArrayList<Task> list : sortTaskForTag) {
                                    if (!list.contains(unSortTask)){
                                        counter++;
                                    }
                                }
                                if (counter == sortTaskForTag.size()){
                                    counter = 0;
                                    sortTasks.add(unSortTask);
                                }
                            }
                        } else {
                            if (!sortTasks.contains(unSortTask)){
                                sortTasks.add(unSortTask);
                            }
                        }
                    }
                }
            }

            if (oneTag){
                sortTaskForTag.add(sortTasks);
                sortTasks = new ArrayList<>();
            }
        }

        if (!oneTag){
            for (Task unSortTask : unSortTasks) {
                if (!sortTasks.contains(unSortTask)){
                    sortTasks.add(unSortTask);
                }
            }
            sortTaskForTag.add(sortTasks);
        } else {
            int counter;
            for (Task unSortTask : unSortTasks) {
                counter = 0;
                for (int i = 0; i < 4; i++) {
                    if (unSortTask.gettagIdList().get(i) == 0){
                        counter++;
                    }
                }
                if (counter == 4){
                    sortTasks.add(unSortTask);
                }
            }
            sortTaskForTag.add(sortTasks);
        }

        return sortTaskForTag;
    }
    private ArrayList<Task> sortTaskWithPriority (ArrayList<Task> unSortTasks, Integer prio){
        ArrayList<Task> sortTasks = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (Task unSortTask : unSortTasks) {
                if (unSortTask.getPriority() == i+1){
                    if (prio == i+1) sortTasks.add(unSortTask);
                    else if (prio == 0) sortTasks.add(unSortTask);
                }
            }
        }

        return sortTasks;
    }
    private ArrayList<ArrayList<Task>> sortTaskWithDeadline (ArrayList<Task> unSortTasks, Boolean sameDay){
        ArrayList<ArrayList<Task>> sortTaskForDays = new ArrayList<>();
        ArrayList<Task> sortTasks = new ArrayList<>();

        int counter = 0;
        for (Task unSortTask : unSortTasks) {
            if (sortTasks.size() == 0){
                sortTasks.add(unSortTask);
            } else {
                counter = 0;
                for (int i = 0; i <= sortTasks.size(); i++) {
                    if (sortTasks.size() == i){
                        sortTasks.add(unSortTask);
                        i++;
                    } else if (dateBefore(unSortTask, sortTasks.get(i))){
                        Task temp = sortTasks.get(i);
                        sortTasks.set(i,unSortTask);
                        int j = i+1;
                        counter++;
                        while(j <= sortTasks.size()){
                            if (sortTasks.size() == j){
                                sortTasks.add(temp);
                                j++;
                                counter++;
                            } else if (dateBefore(temp, sortTasks.get(j))){
                                Task temp2 = sortTasks.get(j);
                                sortTasks.set(j,temp);
                                temp = temp2;
                            }

                            counter++;
                            j++;
                        }

                        i+= counter;
                    }
                }
            }
        }

        if(sameDay){
            int i = 0;
            counter = 0;

            while (i < sortTasks.size()){
                ArrayList<Task> day = new ArrayList<>();
                day.add(sortTasks.get(i));

                for (int j = i+1; j < sortTasks.size(); j++) {
                    if (sameDay(sortTasks.get(i), sortTasks.get(j))){
                        day.add(sortTasks.get(j));
                        counter++;
                    }
                }

                if (counter != 0) i += counter;

                i++;
                counter = 0;
                sortTaskForDays.add(day);
            }
        } else {
            sortTaskForDays.add(sortTasks);
        }

        return sortTaskForDays;
    }

    public void archTask(Task task){
        if (!archContainsTask(task)){
            Task archTask = task;
            archTask.setCompletionDate(new Date(LocalTime.now().getMinute(), LocalTime.now().getHour(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), false));
            archTask.setDone(true);
            taskArchList.add(archTask);
            removeTask(task);
            System.out.println(archTask.toString());
            for (Integer integer : archTask.gettagIdList()) {
                if (integer != 0){
                    for (Tag tag : tagList) {
                       if (tag.getId() == integer){
                           archTag(tag);
                       }
                    }
                }
            }
        }

        try {
            rwa.writeTasksToArchive(taskArchList);
            rwa.writeTagsToArchive(tagArchList);
            rwa.writeTasksToFile(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
    }
    public void archTag(Tag tag){
        if (!archContainsTag(tag)){
            tagArchList.add(tag);
            System.out.println(tag.getName() + tag.getId());
        }
    }
    private Boolean archContainsTask(Task task){
        int in = 0;

        for (Task archTask : taskArchList) {
            if (task.getName().equals(archTask.getName())){
                in++;
            }
        }

        if (in > 0){
            return true;
        } else {
            return false;
        }
    }
    private Boolean archContainsTag(Tag tag){
        int in = 0;

        for (Tag archTag : tagArchList) {
            if (tag.getName().equals(archTag.getName())){
                in++;
            } else if (tag.getId() == archTag.getId()){
                in++;
            }
        }

        if (in > 0){
            return true;
        } else {
            return false;
        }
    }
}