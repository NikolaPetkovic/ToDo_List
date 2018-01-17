package classes;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

public class Printer implements Printable {
    private ArrayList<Task> dataTasks;
    private ArrayList<Tag> dataTags;
    private int tasksPerPage = 27;

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {

        if (pageIndex * tasksPerPage >= dataTasks.size()) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        Font titleFont = new Font("Courier New", Font.PLAIN, 12);
        g2d.setFont(titleFont);

        for (int i = tasksPerPage * pageIndex; i < dataTasks.size() && i < tasksPerPage * (pageIndex + 1); i++){
            if ((i < 9) || ((i>=3*9) && (i<4*9))){
                g.drawString("Task:     " + dataTasks.get(i).getName(), 10, 10 + i * 90);
                g.drawString("Created:  " + dataTasks.get(i).getCreationDate().toString(true,false), 10, 22 + i * 90);
                g.drawString("Deadline: " + dataTasks.get(i).getDeadline().toString(true,true), 10, 34 + i * 90);
                g.drawString("Compl.:   " + dataTasks.get(i).getCompletionDate().toString(true,true),10,46+i*90);

                if (dataTasks.get(i).gettagIdList().size() == 0){
                    g.drawString("Tags:     ", 10, 46 + i * 90);
                } else {
                    for (int j = 0; j < dataTasks.get(i).gettagIdList().size(); j++) {
                        for (int k = 0; k < dataTags.size(); k++) {
                            if (dataTasks.get(i).gettagIdList().get(j) == dataTags.get(k).getId()){
                                if (j == 0){
                                    g.drawString("Tags:     " + dataTags.get(k).getName(), 10, 58 + i * 90);
                                } else {
                                    g.drawString("          " + dataTags.get(k).getName(), 10, 58 + i * 90  + j * 10);
                                }
                            }
                        }
                    }
                }

            } else if ((i < 2*9) || ((i>=4*9) && (i<5*9))){
                int move1 = 202;
                g.drawString("Task:     " + dataTasks.get(i).getName(), move1, 10 + (i-9) * 90);
                g.drawString("Created:  " + dataTasks.get(i).getCreationDate().toString(true,false), move1, 22 + (i-9) * 90);
                g.drawString("Deadline: " + dataTasks.get(i).getDeadline().toString(true,true), move1, 34 + (i-9) * 90);
                g.drawString("Compl.:   "+dataTasks.get(i).getCompletionDate().toString(true,true),move1,46+(i-9)*90);

                if (dataTasks.get(i).gettagIdList().size() == 0){
                    g.drawString("Tags:     ", move1, 46 + (i-9) * 90);
                } else {
                    for (int j = 0; j < dataTasks.get(i).gettagIdList().size(); j++) {
                        for (int k = 0; k < dataTags.size(); k++) {
                            if (dataTasks.get(i).gettagIdList().get(j) == dataTags.get(k).getId()){
                                if (j == 0){
                                    g.drawString("Tags:     " + dataTags.get(k).getName(), move1, 58 + (i-9) * 90);
                                } else {
                                    g.drawString("          " + dataTags.get(k).getName(), move1, 58 + (i-9) * 90  + j * 10);
                                }
                            }
                        }
                    }
                }
            } else if ((i < 3*9) || ((i>=5*9) && (i<6*9))){
                int move2y = 392;
                int move2x = (i-18) * 90;
                g.drawString("Task:     " + dataTasks.get(i).getName(), move2y, 10 + move2x);
                g.drawString("Created:  " + dataTasks.get(i).getCreationDate().toString(true,false), move2y, 22 + move2x);
                g.drawString("Deadline: " + dataTasks.get(i).getDeadline().toString(true,true), move2y, 34 + move2x);
                g.drawString("Compl.:   "+dataTasks.get(i).getCompletionDate().toString(true,true),move2y,46+move2x);

                if (dataTasks.get(i).gettagIdList().size() == 0){
                    g.drawString("Tags:     ", move2y, 46 + move2x);
                } else {
                    for (int j = 0; j < dataTasks.get(i).gettagIdList().size(); j++) {
                        for (int k = 0; k < dataTags.size(); k++) {
                            if (dataTasks.get(i).gettagIdList().get(j) == dataTags.get(k).getId()){
                                if (j == 0){
                                    g.drawString("Tags:     " + dataTags.get(k).getName(), move2y, 58 + move2x);
                                } else {
                                    g.drawString("          " + dataTags.get(k).getName(), move2y, 58 + move2x + j * 10);
                                }
                            }
                        }
                    }
                }
            }

        }

        return PAGE_EXISTS;
    }

    public void printData(ArrayList<Task> tasks, ArrayList<Tag> tags) {
        dataTasks = new ArrayList<>();
        dataTasks.addAll(tasks);

        dataTags = new ArrayList<>();
        dataTags.addAll(tags);

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                System.out.println("Error");
            }
        }
    }
}