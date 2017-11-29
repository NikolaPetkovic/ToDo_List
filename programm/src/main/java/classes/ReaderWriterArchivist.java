package classes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class ReaderWriterArchivist {
    private String sourceUrl ="/txt/source.txt";
    private URL url = getClass().getResource(sourceUrl);

    public String getFileNameFromFile(String nameId) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
        for (int i = 0; i < 11; i++){
            if (nameId.equals("Source") && i == 0) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Task") && i == 1) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Tag") && i == 2) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("ReTask") && i == 3) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Dates") && i == 4) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("ArchTask") && i == 5) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("ArchTag") && i == 6) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Dir1") && i == 7) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Dir2") && i == 8) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Sort1") && i == 9) {
                String file = br.readLine();
                br.close();
                return  file;
            } else if (nameId.equals("Sort2") && i == 10) {
                String file = br.readLine();
                br.close();
                return  file;
            }
            br.readLine();
        }
        br.close();
        return null;
    }

    /**After here Are the File Creators and dir changers**/

    public void cancelSave () throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("/txt/source.txt", false));
        pw.println("no");
        pw.close();
    }

    public void createFilesAndSave1(String dir1, Boolean copy, Boolean replaceOld) throws IOException {
        File fileUrl = null;
        try {
            fileUrl = new File(url.toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String dir1Edit = dir1.replace("\\", "/");
        if (getFileNameFromFile("Source").equals("no")){
            createFiles1(dir1Edit);

            PrintWriter pw = new PrintWriter(new FileOutputStream(fileUrl, false));
            pw.println("no");
            pw.println(dir1Edit + "/tasks.txt");
            pw.println(dir1Edit + "/tags.txt");
            pw.println(dir1Edit + "/reTasks.txt");
            pw.println(dir1Edit + "/dates.txt");
            pw.println("");
            pw.println("");
            pw.println(dir1);
            pw.println("");
            pw.println("");
            pw.println("");
            pw.close();
        } else if (replaceOld) {
            System.out.println("Overwriting Files");

            File file = new File(dir1Edit + "/tasks.txt");
            File file1 = new File(dir1Edit + "/tags.txt");
            File file2 = new File(dir1Edit + "/reTasks.txt");
            File file3 = new File(dir1Edit + "/dates.txt");

            createOrOverwrite(file);
            createOrOverwrite(file1);
            createOrOverwrite(file2);
            createOrOverwrite(file3);
        } else {
            if (copy) {
                ArrayList<String> fileText = copy(new File(getFileNameFromFile("Task")));
                ArrayList<String> file1Text = copy(new File(getFileNameFromFile("Tag")));
                ArrayList<String> file2Text = copy(new File(getFileNameFromFile("ReTask")));
                ArrayList<String> file3Text = copy(new File(getFileNameFromFile("Dates")));
                copyFiles1(dir1Edit, fileText, file1Text, file2Text ,file3Text);
            } else {
                createFiles1(dir1Edit);
            }

            ArrayList<String> repo = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
            String line;
            while ((line = br.readLine()) != null) {
                repo.add(line);
            }
            br.close();
            URL url = getClass().getResource(sourceUrl);
            try {
                File file = new File(url.toURI().getPath());
                PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
                pw.println("yes");
                pw.println(dir1Edit + "/tasks.txt");
                pw.println(dir1Edit + "/tags.txt");
                pw.println(dir1Edit + "/reTasks.txt");
                pw.println(dir1Edit + "/dates.txt");
                pw.println(repo.get(5));
                pw.println(repo.get(6));
                pw.println(dir1);
                pw.println(repo.get(8));
                pw.println(repo.get(9));
                pw.println(repo.get(10));
                pw.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    public void createFilesAndSave2(String dir2, Boolean copy, Boolean replaceOld) throws IOException {
        String dir2Edit = dir2.replace("\\", "/");
        if (getFileNameFromFile("Source").equals("no")){
            createFiles2(dir2Edit);

            ArrayList<String> repo = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
            String line;
            while ((line = br.readLine()) != null) {
                repo.add(line);
            }
            br.close();
            URL url = getClass().getResource(sourceUrl);
            try {
                File file = new File(url.toURI().getPath());
                PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
                pw.println("yes");
                pw.println(repo.get(1));
                pw.println(repo.get(2));
                pw.println(repo.get(3));
                pw.println(repo.get(4));
                pw.println(dir2Edit + "/archTasks.txt");
                pw.println(dir2Edit + "/archTags.txt");
                pw.println(repo.get(7));
                pw.println(dir2);
                pw.println(repo.get(9));
                pw.println(repo.get(10));
                pw.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else if (replaceOld) {
            System.out.println("Overwriting Files");

            File file = new File(dir2Edit + "/archTasks.txt");
            File file1 = new File(dir2Edit + "/archTags.txt");

            createOrOverwrite(file);
            createOrOverwrite(file1);
        } else {
            if (copy) {
                ArrayList<String> fileText = copy(new File(getFileNameFromFile("ArchTask")));
                ArrayList<String> file1Text = copy(new File(getFileNameFromFile("ArchTag")));
                copyFiles2(dir2Edit, fileText, file1Text);
            } else {
                createFiles2(dir2Edit);
            }

            ArrayList<String> repo = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
            String line;
            while ((line = br.readLine()) != null) {
                repo.add(line);
            }
            br.close();
            URL url = getClass().getResource(sourceUrl);
            try {
                File file = new File(url.toURI().getPath());
                PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
                pw.println("yes");
                pw.println(repo.get(1));
                pw.println(repo.get(2));
                pw.println(repo.get(3));
                pw.println(repo.get(4));
                pw.println(dir2Edit + "/archTasks.txt");
                pw.println(dir2Edit + "/archTags.txt");
                pw.println(repo.get(7));
                pw.println(dir2);
                pw.println(repo.get(9));
                pw.println(repo.get(10));
                pw.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    private void createFiles1(String dir1) throws IOException {
        System.out.println("Creating Files for use");

        File file = new File(dir1 + "/tasks.txt");
        File file1 = new File(dir1 + "/tags.txt");
        File file2 = new File(dir1 + "/reTasks.txt");
        File file3 = new File(dir1 + "/dates.txt");

        if (file.exists() && !file.isDirectory() || file1.exists() && !file1.isDirectory() || file2.exists() && !file2.isDirectory() || file3.exists() && !file3.isDirectory()) {
            System.out.println("Found Files");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Question");
            alert.setHeaderText("There are old saves in the the selected folder. Should these be overwritten?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOne) {
                System.out.println("Overwriting Files");
                createOrOverwrite(file);
                createOrOverwrite(file1);
                createOrOverwrite(file2);
                createOrOverwrite(file3);
            } else {
                System.out.println("Not Overwriting");
                createNewFileIfNotExists(file);
                createNewFileIfNotExists(file1);
                createNewFileIfNotExists(file2);
                createNewFileIfNotExists(file3);
            }
        } else {
            System.out.println("Creating files w/o problems");
            file.createNewFile();
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
        }
    }

    private void createFiles2(String dir2) throws IOException {
        System.out.println("Creating Files for Archive");

        File file = new File(dir2 + "/archTasks.txt");
        File file1 = new File(dir2 + "/archTags.txt");

        if (file.exists() && !file.isDirectory() || file1.exists() && !file1.isDirectory()) {
            System.out.println("Found Files");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Question");
            alert.setHeaderText("There are old saves in the the selected folder. Should these be overwritten?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOne) {
                System.out.println("Overwriting Files");
                createOrOverwrite(file);
                createOrOverwrite(file1);
            } else {
                System.out.println("Not Overwriting");
                createNewFileIfNotExists(file);
                createNewFileIfNotExists(file1);
            }
        } else {
            System.out.println("Creating files w/o problems");
            file.createNewFile();
            file1.createNewFile();
        }
    }

    private void copyFiles1(String dir1, ArrayList<String> text, ArrayList<String> text1, ArrayList<String> text2, ArrayList<String> text3) throws IOException {
        System.out.println("Creating and Copying Files for use");

        File file = new File(dir1 + "/tasks.txt");
        File file1 = new File(dir1 + "/tags.txt");
        File file2 = new File(dir1 + "/reTasks.txt");
        File file3 = new File(dir1 + "/dates.txt");

        if (file.exists() && !file.isDirectory() || file1.exists() && !file1.isDirectory() || file2.exists() && !file2.isDirectory() || file3.exists() && !file3.isDirectory()) {
            System.out.println("Found Files");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Question");
            alert.setHeaderText("There are old saves in the the selected folder. Should these be overwritten?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOne) {
                System.out.println("Overwriting Files");
                createNewFileIfNotExists(file);
                createNewFileIfNotExists(file1);
                createNewFileIfNotExists(file2);
                createNewFileIfNotExists(file3);

                paste(file, text);
                paste(file1, text1);
                paste(file2, text2);
                paste(file3, text3);
            } else {
                System.out.println("Not Overwriting");
                createNewFileIfNotExists(file);
                createNewFileIfNotExists(file1);
                createNewFileIfNotExists(file2);
                createNewFileIfNotExists(file3);
            }
        } else {
            System.out.println("Creating files w/o problems + pasting");
            file.createNewFile();
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
            paste(file, text);
            paste(file1, text1);
            paste(file2, text2);
            paste(file3, text3);
        }
    }

    private void copyFiles2(String dir2, ArrayList<String> text, ArrayList<String> text1) throws IOException {
        System.out.println("Creating and Copying Files for use");

        File file = new File(dir2 + "/archTasks.txt");
        File file1 = new File(dir2 + "/archTags.txt");

        if (file.exists() && !file.isDirectory() || file1.exists() && !file1.isDirectory()) {
            System.out.println("Found Files");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Question");
            alert.setHeaderText("There are old saves in the the selected folder. Should these be overwritten?");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOne) {
                System.out.println("Overwriting Files");
                createNewFileIfNotExists(file);
                createNewFileIfNotExists(file1);

                paste(file, text);
                paste(file1, text1);
            } else {
                System.out.println("Not Overwriting");
                createNewFileIfNotExists(file);
                createNewFileIfNotExists(file1);
            }
        } else {
            System.out.println("Creating files w/o problems + pasting");
            file.createNewFile();
            file1.createNewFile();
            paste(file, text);
            paste(file1, text1);
        }
    }

    private ArrayList<String> copy (File file) throws IOException {
        ArrayList<String> ft = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            ft.add(line);
        }

        br.close();
        return ft;
    }

    private void paste (File file, ArrayList<String> ft) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));

        for (int i = 0; i < ft.size(); i++) {
            pw.println(ft.get(i));
        }

        pw.close();
    }

    private void createOrOverwrite (File f) throws IOException {
        if (f.exists() && !f.isDirectory() ){
            FileWriter fw = new FileWriter(f, false);
            fw.write("");
            fw.close();
        } else {
            f.createNewFile();
        }
    }

    private void createNewFileIfNotExists (File f) throws IOException {
        if (!f.exists()){
            f.createNewFile();
        }
    }

    public void setSortTyp(String select1, String select2) throws IOException {
        ArrayList<String> repo = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sourceUrl)));
        String line;
        while ((line = br.readLine()) != null) {
            repo.add(line);
        }
        br.close();
        URL url = getClass().getResource(sourceUrl);
        try {
            File file = new File(url.toURI().getPath());
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
            pw.println("yes");
            pw.println(repo.get(1));
            pw.println(repo.get(2));
            pw.println(repo.get(3));
            pw.println(repo.get(4));
            pw.println(repo.get(5));
            pw.println(repo.get(6));
            pw.println(repo.get(7));
            pw.println(repo.get(8));
            pw.println(select1);
            pw.println(select2);
            pw.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**After here Are the Readers**/
    public ArrayList<Task> readTasksFromFile() throws IOException {
        ArrayList<Task> readTasks = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(getFileNameFromFile("Task")));
        String line;
        while ((line = br.readLine()) != null) {
            String[] linePart = line.split(";");
            Date deadline = new Date(Integer.parseInt(linePart[1]), Integer.parseInt(linePart[2]), Integer.parseInt(linePart[3]), Integer.parseInt(linePart[4]), Integer.parseInt(linePart[5]), Boolean.parseBoolean(linePart[6]));
            Date completionDate = new Date(Integer.parseInt(linePart[7]), Integer.parseInt(linePart[8]), Integer.parseInt(linePart[9]), Integer.parseInt(linePart[10]), Integer.parseInt(linePart[11]), Boolean.parseBoolean(linePart[12]));
            Date creationDate = new Date(Integer.parseInt(linePart[13]), Integer.parseInt(linePart[14]), Integer.parseInt(linePart[15]), Integer.parseInt(linePart[16]), Integer.parseInt(linePart[17]), Boolean.parseBoolean(linePart[18]));
            ArrayList<Integer> tagIdList = new ArrayList<>();
            tagIdList.add(Integer.parseInt(linePart[21]));
            tagIdList.add(Integer.parseInt(linePart[22]));
            tagIdList.add(Integer.parseInt(linePart[23]));
            tagIdList.add(Integer.parseInt(linePart[24]));
            readTasks.add(new Task(linePart[0], deadline, completionDate, creationDate, Integer.parseInt(linePart[19]), Boolean.parseBoolean(linePart[20]), tagIdList));
        }
        br.close();
        return readTasks;
    }
    public ArrayList<Tag> readTagsFromFile() throws IOException {
        ArrayList<Tag> readTags = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(getFileNameFromFile("Tag")));
        String line;
        while ((line = br.readLine()) != null) {
            String[] linePart = line.split(";");
            readTags.add(new Tag(Integer.parseInt(linePart[0]), linePart[1], linePart[2]));
        }
        br.close();
        return readTags;
    }
    public ArrayList<ReTask> readReTasksFromFile() throws IOException {
        ArrayList<ReTask> reTasksFromFile = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(getFileNameFromFile("ReTask")));
        int i=0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] linePart = line.split(";");
            ArrayList<Date> dates = getDatesFromFile(i);
            Date completionDate = new Date(Integer.parseInt(linePart[1]), Integer.parseInt
                    (linePart[2]), Integer.parseInt(linePart[3]), Integer.parseInt(linePart[4]), Integer.parseInt
                    (linePart[5]), Boolean.parseBoolean(linePart[6]));
            Date creationDate = new Date(Integer.parseInt(linePart[7]), Integer.parseInt
                    (linePart[8]), Integer.parseInt(linePart[9]), Integer.parseInt(linePart[10]), Integer.parseInt
                    (linePart[11]), Boolean.parseBoolean(linePart[12]));
            ArrayList<Integer> tagIdList = new ArrayList<>();
            tagIdList.add(Integer.parseInt(linePart[13]));
            tagIdList.add(Integer.parseInt(linePart[14]));
            tagIdList.add(Integer.parseInt(linePart[15]));
            tagIdList.add(Integer.parseInt(linePart[16]));
            reTasksFromFile.add(new ReTask(linePart[0], dates, completionDate, creationDate, tagIdList));
            i++;
        }
        br.close();
        return reTasksFromFile;
    }
    private  ArrayList<Date> getDatesFromFile(int i) throws IOException {
        ArrayList<Date> datesFromFile = new ArrayList<>();
        LineNumberReader br = new LineNumberReader(new FileReader(getFileNameFromFile("Dates")));
        String line;


        for(int j = 0; j < i; ++j) {
            br.readLine();
        }

        line = br.readLine();
        String[] lineDates = line.split("/");
        for (String lineDate: lineDates) {
            String[] linePart = lineDate.split(";");
            datesFromFile.add(new Date(Integer.parseInt(linePart[0]), Integer.parseInt(linePart
                    [1]), Integer.parseInt(linePart[2]), Integer.parseInt(linePart[3]), Integer.parseInt(linePart
                    [4]), Boolean.parseBoolean(linePart[5])));
        }

        br.close();
        return datesFromFile;

    }

    /**After here Are the Writers**/
    public void writeTasksToFile (ArrayList<Task> tasks) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(getFileNameFromFile("Task"), false));

        for (Task task : tasks) {
            pw.print(task.getName() + ";");
            pw.print(task.getDeadline().getMinute() + ";" + task.getDeadline().getHour() + ";" + task.getDeadline().getDay() + ";" + task.getDeadline().getMonth() + ";"+ task.getDeadline().getYear() + ";" + task.getDeadline().getDone() + ";");
            if (task.getCompletionDate() == null){
                pw.print(0 + ";" + 0 + ";" + 0 + ";" + 0 + ";" + 0 + ";" + false + ";");
            }else {
                pw.print(task.getCompletionDate().getMinute() + ";" + task.getCompletionDate().getHour() + ";" + task.getCompletionDate().getDay() + ";" + task.getCompletionDate().getMonth() + ";"+ task.getCompletionDate().getYear() + ";" + task.getCompletionDate().getDone() + ";");
            }
            pw.print(task.getCreationDate().getMinute() + ";" + task.getCreationDate().getHour() + ";" + task.getCreationDate().getDay() + ";" + task.getCreationDate().getMonth() + ";"+ task.getCreationDate().getYear() + ";" + task.getCreationDate().getDone() + ";");
            pw.print(task.getPriority() + ";");
            pw.print(task.getDone() + ";");
            if (task.gettagIdList().get(0) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(0) + ";");
            }
            if (task.gettagIdList().get(1) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(1) + ";");
            }
            if (task.gettagIdList().get(2) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(2) + ";");
            }
            if (task.gettagIdList().get(3) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(3) + ";");
            }
            pw.println();
        }

        pw.close();
    }
    public void writeReTaskDatesToFile(ArrayList<ReTask> reTasks) throws IOException{
        PrintWriter pw = new PrintWriter(new FileOutputStream(getFileNameFromFile("Dates"),
                false));
        for(ReTask reTask: reTasks) {
            for (Date date: reTask.getDates()){
                pw.print(date.getMinute()+ ";" + date.getHour()+";"+date.getDay
                        ()+";"+date.getMonth()+";"+date.getYear()+";"+date.getDone()+"/");
            }
            pw.println();
        }
        pw.close();
    }
    public void writeReTasksToFile (ArrayList<ReTask> reTasks) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(getFileNameFromFile("ReTask"),
                false));
        writeReTaskDatesToFile(reTasks);

        for (ReTask reTask : reTasks) {
            pw.print(reTask.getName() + ";");


            pw.print(reTask.getCompletionDate().getMinute() + ";" + reTask.getCompletionDate
                    ().getHour() + ";" + reTask.getCompletionDate().getDay() + ";" + reTask.getCompletionDate
                    ().getMonth() + ";"+ reTask.getCompletionDate().getYear() + ";" + reTask.getCompletionDate
                    ().getDone() + ";");

            pw.print(reTask.getCreationDate().getMinute() + ";" + reTask.getCreationDate
                    ().getHour() + ";" + reTask.getCreationDate().getDay() + ";" + reTask.getCreationDate().getMonth
                    () + ";"+ reTask.getCreationDate().getYear() + ";" + reTask.getCreationDate().getDone() + ";");

            if (reTask.getTagList().get(0) == null){
                pw.print(0 + ";");
            }else {
                pw.print(reTask.getTagList().get(0) + ";");
            }
            if (reTask.getTagList().get(1) == null){
                pw.print(0 + ";");
            }else {
                pw.print(reTask.getTagList().get(1) + ";");
            }
            if (reTask.getTagList().get(2) == null){
                pw.print(0 + ";");
            }else {
                pw.print(reTask.getTagList().get(2) + ";");
            }
            if (reTask.getTagList().get(3) == null){
                pw.print(0 + ";");
            }else {
                pw.print(reTask.getTagList().get(3) + ";");
            }
            pw.println();
        }

        pw.close();
    }
    public void writeTagsToFile (ArrayList<Tag> tags) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(getFileNameFromFile("Tag"), false));

        for (Tag tag : tags) {

            pw.println(tag.getId() + ";" + tag.getColor() + ";" + tag.getName());
        }

        pw.close();
    }

    /**After here are the Archivist Readers**/

    public ArrayList<Task> readTasksFromArchive() throws IOException {
        ArrayList<Task> readTasks = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(getFileNameFromFile("ArchTask")));
        String line;
        while ((line = br.readLine()) != null) {
            String[] linePart = line.split(";");
            Date deadline = new Date(Integer.parseInt(linePart[1]), Integer.parseInt(linePart[2]), Integer.parseInt(linePart[3]), Integer.parseInt(linePart[4]), Integer.parseInt(linePart[5]), Boolean.parseBoolean(linePart[6]));
            Date completionDate = new Date(Integer.parseInt(linePart[7]), Integer.parseInt(linePart[8]), Integer.parseInt(linePart[9]), Integer.parseInt(linePart[10]), Integer.parseInt(linePart[11]), Boolean.parseBoolean(linePart[12]));
            Date creationDate = new Date(Integer.parseInt(linePart[13]), Integer.parseInt(linePart[14]), Integer.parseInt(linePart[15]), Integer.parseInt(linePart[16]), Integer.parseInt(linePart[17]), Boolean.parseBoolean(linePart[18]));
            ArrayList<Integer> tagIdList = new ArrayList<>();
            tagIdList.add(Integer.parseInt(linePart[21]));
            tagIdList.add(Integer.parseInt(linePart[22]));
            tagIdList.add(Integer.parseInt(linePart[23]));
            tagIdList.add(Integer.parseInt(linePart[24]));
            readTasks.add(new Task(linePart[0], deadline, completionDate, creationDate, Integer.parseInt(linePart[19]), Boolean.parseBoolean(linePart[20]), tagIdList));
        }
        br.close();
        return readTasks;
    }

    public ArrayList<Tag> readTagsFromArchive() throws IOException {
        ArrayList<Tag> readTags = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(getFileNameFromFile("ArchTag")));
        String line;
        while ((line = br.readLine()) != null) {
            String[] linePart = line.split(";");
            readTags.add(new Tag(Integer.parseInt(linePart[0]), linePart[1], linePart[2]));
        }
        br.close();
        return readTags;
    }

    /**After here are the Archivist Writers**/

    public void writeTasksToArchive (ArrayList<Task> tasks) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(getFileNameFromFile("ArchTask"), false));

        for (Task task : tasks) {
            pw.print(task.getName() + ";");
            pw.print(task.getDeadline().getMinute() + ";" + task.getDeadline().getHour() + ";" + task.getDeadline().getDay() + ";" + task.getDeadline().getMonth() + ";"+ task.getDeadline().getYear() + ";" + task.getDeadline().getDone() + ";");
            if (task.getCompletionDate() == null){
                pw.print(0 + ";" + 0 + ";" + 0 + ";" + 0 + ";" + 0 + ";" + false + ";");
            }else {
                pw.print(task.getCompletionDate().getMinute() + ";" + task.getCompletionDate().getHour() + ";" + task.getCompletionDate().getDay() + ";" + task.getCompletionDate().getMonth() + ";"+ task.getCompletionDate().getYear() + ";" + task.getCompletionDate().getDone() + ";");
            }
            pw.print(task.getCreationDate().getMinute() + ";" + task.getCreationDate().getHour() + ";" + task.getCreationDate().getDay() + ";" + task.getCreationDate().getMonth() + ";"+ task.getCreationDate().getYear() + ";" + task.getCreationDate().getDone() + ";");
            pw.print(task.getPriority() + ";");
            pw.print(task.getDone() + ";");
            if (task.gettagIdList().get(0) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(0) + ";");
            }
            if (task.gettagIdList().get(1) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(1) + ";");
            }
            if (task.gettagIdList().get(2) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(2) + ";");
            }
            if (task.gettagIdList().get(3) == null){
                pw.print(0 + ";");
            }else {
                pw.print(task.gettagIdList().get(3) + ";");
            }
            pw.println();
        }

        pw.close();
    }

    public void writeTagsToArchive (ArrayList<Tag> tags) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(getFileNameFromFile("ArchTag"), false));

        for (Tag tag : tags) {

            pw.println(tag.getId() + ";" + tag.getColor() + ";" + tag.getName());
        }

        pw.close();
    }
}